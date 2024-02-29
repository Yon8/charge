package com.lxy.charge.service.charge;

import com.lxy.charge.mapper.charge.StackMapper;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.charge.Stack;
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
public class StackService {
    private final StackMapper stackMapper;
    private final RedisUtil redisUtil;
    //redis缓存list键名
    private final String listCacheKey = "stackListCache";
    private final String idAndNameCacheKey =  "stackIdAndNameCache";
    @Autowired
    public StackService(StackMapper stackMapper,RedisUtil redisUtil) {
        this.stackMapper = stackMapper;
        this.redisUtil = redisUtil;
    }

    public List<Stack> getStackList(Stack stack) {
        return stackMapper.getStackList(stack);
    }
    public List<Stack> getStackList(Stack stack, PageVo page) {
        List<Stack>  cachedStackList = (List<Stack>) redisUtil.get(listCacheKey + page.getCurrent());
        if (cachedStackList != null && !cachedStackList.isEmpty()) {
            return cachedStackList;
        }
        List<Stack> stackList = stackMapper.getStackList(stack);
        redisUtil.set(listCacheKey + page.getCurrent(), stackList,60*60);
        return stackList;
    }
    public List<Stack> getStackIdAndName() {
        List<Stack> cachedStackIdAndName = (List<Stack>) redisUtil.get(idAndNameCacheKey);
        if (cachedStackIdAndName != null && !cachedStackIdAndName.isEmpty()) {
            return cachedStackIdAndName;
        }
        List<Stack> stackIdAndName = stackMapper.getStackIdAndName();
        redisUtil.set( idAndNameCacheKey, stackIdAndName,60*60);
        return stackIdAndName;
    }

    public Boolean stackDelete(Stack stack) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        return stackMapper.stackDelete(stack.getId()) == 1;
    }

    public Stack stackAdd(Stack stack) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        stackMapper.stackAdd(stack);
        return stack;
    }

    public Boolean stackEdit(Stack stack) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        return stackMapper.stackEdit(stack) == 1;
    }

    public Boolean stackDeleteByIds(List<Integer> ids) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        return stackMapper.stackDeleteByIds(ids)!=0;
    }

    //声明SQL会话工厂并自动填充
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public void saveBatch(List<Stack> list) {

        // 关闭session的自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            StackMapper userMapper = sqlSession.getMapper(StackMapper.class);
            list.forEach(user -> userMapper.stackAdd(user));
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
