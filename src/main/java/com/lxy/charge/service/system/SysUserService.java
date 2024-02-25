package com.lxy.charge.service.system;


import com.lxy.charge.mapper.system.SysUserMapper;
import com.lxy.charge.pojo.system.SysUser;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SysUserService {

    private final SysUserMapper sysUserMapper;
    @Autowired
    public SysUserService(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    public List<SysUser> getSysUserList(SysUser sysUser) {
        return sysUserMapper.getSysUserList(sysUser);
    }
    public List<SysUser> getUserIdAndName() {
        return sysUserMapper.getUserIdAndName();
    }

    public Boolean sysUserDelete(SysUser sysUser) {
        return sysUserMapper.sysUserDelete(sysUser.getId()) == 1;
    }

    public SysUser sysUserAdd(SysUser sysUser) {
        sysUserMapper.sysUserAdd(sysUser);
        return sysUser;
    }

    public Boolean sysUserEdit(SysUser sysUser) {
        return sysUserMapper.sysUserEdit(sysUser) == 1;
    }

    public Boolean sysUserDeleteByIds(List<Integer> ids) {
        return sysUserMapper.sysUserDeleteByIds(ids)!=0;
    }

    //声明SQL会话工厂并自动填充
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public void saveBatch(List<SysUser> list) {

        // 关闭session的自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            list.forEach(sysUser -> sysUserMapper.sysUserAdd(sysUser));
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