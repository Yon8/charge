package com.lxy.charge.mapper.system;


import com.lxy.charge.pojo.system.SysRole;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysRoleMapper {

    List<SysRole> getRoleIdAndName();

    List<SysRole> getSysRoleList(SysRole sysRole);
    //根据用户名获取用户信息
    //按用户id查找某一个角色信息
    int sysRoleDelete(Integer id);

    int sysRoleAdd(SysRole sysRole);

    int sysRoleEdit(SysRole sysRole);

    int sysRoleDeleteByIds(List<Integer> ids);
}