package com.lxy.charge.service.system;


import com.lxy.charge.mapper.system.LoginRecordMapper;
import com.lxy.charge.pojo.system.LoginRecord;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class LoginRecordService {

 

    private final LoginRecordMapper loginRecordMapper;
    @Autowired
    public LoginRecordService(LoginRecordMapper loginRecordMapper) {
        this.loginRecordMapper = loginRecordMapper;
    }

    public List<LoginRecord> getLoginRecordList(LoginRecord loginRecord) {
        return loginRecordMapper.getLoginRecordList(loginRecord);
    }
    public List<LoginRecord> getRoleIdAndName() {
        return loginRecordMapper.getRoleIdAndName();
    }
    public Boolean loginRecordDelete(LoginRecord loginRecord) {
        return loginRecordMapper.loginRecordDelete(loginRecord.getId()) == 1;
    }

    public LoginRecord loginRecordAdd(LoginRecord loginRecord) {
        loginRecordMapper.loginRecordAdd(loginRecord);
        return loginRecord;
    }

    public Boolean loginRecordEdit(LoginRecord loginRecord) {
        return loginRecordMapper.loginRecordEdit(loginRecord) == 1;
    }

    public Boolean loginRecordDeleteByIds(List<Integer> ids) {
        return loginRecordMapper.loginRecordDeleteByIds(ids)!=0;
    }

    //声明SQL会话工厂并自动填充
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public void saveBatch(List<LoginRecord> list) {

        // 关闭session的自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            LoginRecordMapper loginRecordMapper = sqlSession.getMapper(LoginRecordMapper.class);
            list.forEach(loginRecord -> loginRecordMapper.loginRecordAdd(loginRecord));
            // 提交数据
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }
}