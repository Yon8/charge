package com.lxy.charge.security.service;



import com.lxy.charge.mapper.system.SysUserMapper;
import com.lxy.charge.pojo.system.SysRole;
import com.lxy.charge.pojo.system.SysUser;
import com.lxy.charge.security.pojo.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SecurityService implements UserDetailsService {

    @Autowired
    SysUserMapper sysUserMapper;


    //根据用户名查询数据库中该用户的信息，准备用以跟登录信息比对
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户
        SysUser user = sysUserMapper.findOneByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("身份错误！"); //抛出异常
        }
        //根据用户id查询该用户的身份，并设置身份
        SysRole role = sysUserMapper.getRoleByUserId(user.getId());
        user.setRole(String.valueOf(role.getName()));
        return new SecurityUser(user);
    }
}