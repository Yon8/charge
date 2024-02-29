package com.lxy.charge.mapper.system;


import com.lxy.charge.pojo.system.LoginRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginRecordMapper {

    List<LoginRecord> getRoleIdAndName();

    List<LoginRecord> getLoginRecordList(LoginRecord loginRecord);
    //根据用户名获取用户信息
    //按用户id查找某一个角色信息
    int loginRecordDelete(Integer id);

    int loginRecordAdd(LoginRecord loginRecord);

    int loginRecordEdit(LoginRecord loginRecord);

    int loginRecordDeleteByIds(List<Integer> ids);
}