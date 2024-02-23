package com.lxy.charge.service.charge;

import com.lxy.charge.mapper.charge.FaultMapper;
import com.lxy.charge.pojo.charge.Fault;
import com.lxy.charge.pojo.charge.Stack;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaultService {
    private final FaultMapper faultMapper;
    @Autowired
    public FaultService(FaultMapper faultMapper) {
        this.faultMapper = faultMapper;
    }

    public List<Fault> getFaultList(Fault fault) {
        return faultMapper.getFaultList(fault);
    }
    public List<Stack> getStackIdAndName() {
        return faultMapper.getStackIdAndName();
    }

    public Boolean faultDelete(Fault fault) {
        return faultMapper.faultDelete(fault.getId()) == 1;
    }

    public Fault faultAdd(Fault fault) {
        faultMapper.faultAdd(fault);
        return fault;
    }

    public Boolean faultEdit(Fault fault) {
        return faultMapper.faultEdit(fault) == 1;
    }

    public Boolean faultDeleteByIds(List<Integer> ids) {
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
            sqlSession.rollback();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

}
