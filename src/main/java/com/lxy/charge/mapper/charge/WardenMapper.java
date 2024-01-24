package com.lxy.charge.mapper.charge;

import com.lxy.charge.pojo.charge.Warden;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface WardenMapper {
    List<Warden> getWardenList(Warden warden);

    int wardenDelete(String id);

    int wardenAdd(Warden warden);

    int wardenEdit(Warden warden);

    int wardenDeleteByIds(List<String> ids);
}
