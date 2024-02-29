package com.lxy.charge.service.charge;

import com.lxy.charge.mapper.charge.UserMapper;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.charge.User;
import com.lxy.charge.pojo.charge.Station;
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
public class UserService {
    private final UserMapper userMapper;
    private final RedisUtil redisUtil;
    //redis缓存list键名
    private final String listCacheKey = "userListCache";
    private final String idAndNameCacheKey =  "userIdAndNameCache";
    @Autowired
    public UserService(UserMapper userMapper,RedisUtil redisUtil) {
        this.userMapper = userMapper;
        this.redisUtil = redisUtil;
    }

    public List<User> getUserList(User user) {
        return userMapper.getUserList(user);
    }
    //整合Redis缓存
    public List<User> getUserList(User user, PageVo page) {
        List<User> cachedUserList = (List<User>) redisUtil.get(listCacheKey + page.getCurrent());
        if (cachedUserList != null && !cachedUserList.isEmpty()) {
            return cachedUserList;
        }
        List<User> userList = userMapper.getUserList(user);
        redisUtil.set(listCacheKey + page.getCurrent(), userList,60*60);
        return userList;
    }
    public List<User> getUserIdAndName() {
        List<User> cachedUserIdAndName = (List<User>) redisUtil.get(idAndNameCacheKey);
        if (cachedUserIdAndName != null && !cachedUserIdAndName.isEmpty()) {
            return cachedUserIdAndName;
        }
        List<User> userIdAndName = userMapper.getUserIdAndName();
        redisUtil.set(idAndNameCacheKey, userIdAndName,60*60);
        return userIdAndName;
    }

    public Boolean userDelete(User user) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        return userMapper.userDelete(user.getId()) == 1;
    }

    public User userAdd(User user) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        userMapper.userAdd(user);
        return user;
    }

    public Boolean userEdit(User user) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        return userMapper.userEdit(user) == 1;
    }

    public Boolean userDeleteByIds(List<Integer> ids) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        return userMapper.userDeleteByIds(ids)!=0;
    }

    //声明SQL会话工厂并自动填充
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public void saveBatch(List<User> list) {

        // 关闭session的自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            list.forEach(user -> userMapper.userAdd(user));
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
