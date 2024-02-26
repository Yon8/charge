package com.lxy.charge.service.charge;

import com.lxy.charge.mapper.charge.UserMapper;
import com.lxy.charge.pojo.charge.User;
import com.lxy.charge.pojo.charge.Station;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> getUserList(User user) {
        return userMapper.getUserList(user);
    }
    public List<User> getUserIdAndName() {
        return userMapper.getUserIdAndName();
    }

    public Boolean userDelete(User user) {
        return userMapper.userDelete(user.getId()) == 1;
    }

    public User userAdd(User user) {
        userMapper.userAdd(user);
        return user;
    }

    public Boolean userEdit(User user) {
        return userMapper.userEdit(user) == 1;
    }

    public Boolean userDeleteByIds(List<Integer> ids) {
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
            sqlSession.rollback();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

}
