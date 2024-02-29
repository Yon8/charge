package com.lxy.charge.service.charge;

import com.lxy.charge.mapper.charge.WardenMapper;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.charge.Warden;
import com.lxy.charge.utils.RedisUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class WardenService {
    private final WardenMapper wardenMapper;
    private final RedisUtil redisUtil;
    //redis缓存wardenList键名
    private final String listCacheKey = "wardenListCache";
    private final String idAndNameCacheKey =  "wardenIdAndNameCache";

    @Autowired
    public WardenService(WardenMapper wardenMapper,RedisUtil redisUtil) {
        this.wardenMapper = wardenMapper;
        this.redisUtil = redisUtil;

    }
    //仅从数据库查询
    public List<Warden> getWardenList(Warden warden) {
        return wardenMapper.getWardenList(warden);
    }
    //整合redis查询WardenList
    public List<Warden> getWardenList(Warden warden, PageVo page) {
        //查询缓存
        List<Warden> cachedWardenList = (List<Warden>) redisUtil.get(listCacheKey + page.getCurrent());
        //如果缓存存在 直接返回缓存
        if (cachedWardenList != null && !cachedWardenList.isEmpty()) {
            return cachedWardenList;
        }
        List<Warden> wardenList = wardenMapper.getWardenList(warden);
        //设置缓存
        redisUtil.set(listCacheKey + page.getCurrent(), wardenList,60*60);
        return wardenList;
    }
    public List<Warden> getWardenIdAndName() {
        //查询缓存
        List<Warden> cachedWardenIdAndName = (List<Warden>) redisUtil.get(idAndNameCacheKey);
        //如果缓存存在 直接返回缓存
        if (cachedWardenIdAndName != null && !cachedWardenIdAndName.isEmpty()) {
            return cachedWardenIdAndName;
        }
        //从数据库中获取
        List<Warden> wardenIdAndName = wardenMapper.getWardenIdAndName();
        //设置缓存
        redisUtil.set( idAndNameCacheKey, wardenIdAndName,60*60);
        return wardenIdAndName;
    }

    public Boolean wardenDelete(Warden warden) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);

        return wardenMapper.wardenDelete(warden.getId()) == 1;
    }

    public Warden wardenAdd(Warden warden) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);

        wardenMapper.wardenAdd(warden);
        return warden;
    }

    public Boolean wardenEdit(Warden warden) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);

        return wardenMapper.wardenEdit(warden) == 1;
    }

    public Boolean wardenDeleteByIds(List<Integer> ids) {
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete( idAndNameCacheKey);
        return wardenMapper.wardenDeleteByIds(ids)!=0;
    }
    //声明SQL会话工厂并自动填充
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public void saveBatch(List<Warden> list) {

        // 关闭session的自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            WardenMapper userMapper = sqlSession.getMapper(WardenMapper.class);
            list.forEach(user -> userMapper.wardenAdd(user));
            // 提交数据
            sqlSession.commit();

            //删除相关缓存
            redisUtil.deleteAllRelatedCache(listCacheKey);
            redisUtil.delete(idAndNameCacheKey);
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

}
