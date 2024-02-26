package com.lxy.charge.mapper.charge;

import com.lxy.charge.pojo.charge.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> getUserList(User user);
    List<User> getUserIdAndName();
    int userDelete(Integer id);

    int userAdd(User user);

    int userEdit(User user);

    int userDeleteByIds(List<Integer> ids);

}
