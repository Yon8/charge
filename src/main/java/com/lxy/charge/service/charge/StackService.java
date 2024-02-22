package com.lxy.charge.service.charge;

import com.lxy.charge.mapper.charge.StackMapper;
import com.lxy.charge.pojo.charge.Stack;
import com.lxy.charge.pojo.charge.Station;
import com.lxy.charge.pojo.charge.Warden;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StackService {
    private final StackMapper stackMapper;
    @Autowired
    public StackService(StackMapper stackMapper) {
        this.stackMapper = stackMapper;
    }

    public List<Stack> getStackList(Stack stack) {
        return stackMapper.getStackList(stack);
    }
    public List<Station> getStationIdAndName() {
        return stackMapper.getStationIdAndName();
    }

    public Boolean stackDelete(Stack stack) {
        return stackMapper.stackDelete(stack.getId()) == 1;
    }

    public Stack stackAdd(Stack stack) {
        stackMapper.stackAdd(stack);
        return stack;
    }

    public Boolean stackEdit(Stack stack) {
        return stackMapper.stackEdit(stack) == 1;
    }

    public Boolean stackDeleteByIds(List<Integer> ids) {
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
            sqlSession.rollback();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

}
