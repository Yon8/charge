package com.lxy.charge.mapper.system;



import com.lxy.charge.pojo.system.SysRole;
import com.lxy.charge.pojo.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    List<SysUser> getSysUserList(SysUser sysUser);
    //根据用户名获取用户信息
    public SysUser findOneByUserName(String username) ;
    //按用户id查找某一个角色信息
    SysRole getRoleByUserId(Integer id);
    List<SysUser> getUserIdAndName();
    int sysUserDelete(Integer id);

    int sysUserAdd(SysUser sysUser);

    int sysUserEdit(SysUser sysUser);

    int sysUserDeleteByIds(List<Integer> ids);


}