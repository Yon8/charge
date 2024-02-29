package com.lxy.charge.service.system;


import com.lxy.charge.mapper.system.SysRoleMapper;
import com.lxy.charge.pojo.system.SysRole;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SysRoleService {

 

    private final SysRoleMapper sysRoleMapper;
    @Autowired
    public SysRoleService(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    public List<SysRole> getSysRoleList(SysRole sysRole) {
        return sysRoleMapper.getSysRoleList(sysRole);
    }
    public List<SysRole> getRoleIdAndName() {
        return sysRoleMapper.getRoleIdAndName();
    }
    public Boolean sysRoleDelete(SysRole sysRole) {
        return sysRoleMapper.sysRoleDelete(sysRole.getId()) == 1;
    }

    public SysRole sysRoleAdd(SysRole sysRole) {
        sysRoleMapper.sysRoleAdd(sysRole);
        return sysRole;
    }

    public Boolean sysRoleEdit(SysRole sysRole) {
        return sysRoleMapper.sysRoleEdit(sysRole) == 1;
    }

    public Boolean sysRoleDeleteByIds(List<Integer> ids) {
        return sysRoleMapper.sysRoleDeleteByIds(ids)!=0;
    }

    //声明SQL会话工厂并自动填充
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public void saveBatch(List<SysRole> list) {

        // 关闭session的自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            list.forEach(sysRole -> sysRoleMapper.sysRoleAdd(sysRole));
            // 提交数据
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }
}