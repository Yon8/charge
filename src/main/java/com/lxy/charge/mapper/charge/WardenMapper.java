package com.lxy.charge.mapper.charge;

import com.lxy.charge.pojo.charge.Station;
import com.lxy.charge.pojo.charge.Warden;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface WardenMapper {
    List<Warden> getWardenList(Warden warden);
    List<Station> getStationIdAndName();
    int wardenDelete(Integer id);

    int wardenAdd(Warden warden);

    int wardenEdit(Warden warden);

    int wardenDeleteByIds(List<Integer> ids);
}
