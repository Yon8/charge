package com.lxy.charge.service.charge;

import com.lxy.charge.mapper.charge.FaultMapper;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.charge.Fault;
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
public class FaultService {
    private final FaultMapper faultMapper;
    private final RedisUtil redisUtil;
    //redis缓存list键名
    private final String listCacheKey = "faultListCache";
    private final String idAndNameCacheKey =  "faultIdAndNameCache";
    @Autowired
    public FaultService(FaultMapper faultMapper,RedisUtil redisUtil) {
        this.faultMapper = faultMapper;
        this.redisUtil = redisUtil;
    }

    public List<Fault> getFaultList(Fault fault) {
        return faultMapper.getFaultList(fault);
    }

    public List<Fault> getFaultList(Fault fault, PageVo page) {
        List<Fault>  cachedFaultList = (List<Fault>) redisUtil.get(listCacheKey + page.getCurrent());
        if (cachedFaultList != null && !cachedFaultList.isEmpty()) {
            return cachedFaultList;
        }
        List<Fault> faultList = faultMapper.getFaultList(fault);
        redisUtil.set(listCacheKey + page.getCurrent(), faultList,60*60);
        return faultList;
    }

    public Boolean faultDelete(Fault fault) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        return faultMapper.faultDelete(fault.getId()) == 1;
    }

    public Fault faultAdd(Fault fault) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        faultMapper.faultAdd(fault);
        return fault;
    }

    public Boolean faultEdit(Fault fault) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        return faultMapper.faultEdit(fault) == 1;
    }

    public Boolean faultDeleteByIds(List<Integer> ids) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        return faultMapper.faultDeleteByIds(ids)!=0;
    }

    //声明SQL会话工厂并自动填充
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public void saveBatch(List<Fault> list) {

        // 关闭session的自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            FaultMapper userMapper = sqlSession.getMapper(FaultMapper.class);
            list.forEach(user -> userMapper.faultAdd(user));
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
