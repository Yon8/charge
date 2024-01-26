package com.lxy.charge.mapper.charge;

import com.lxy.charge.pojo.charge.Station;
import com.lxy.charge.pojo.charge.Warden;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StationMapper {
    List<Station> getStationList(Station station);
    List<Warden> getWardenIdAndName();
    int stationDelete(Integer id);

    int stationAdd(Station station);

    int stationEdit(Station station);

    int stationDeleteByIds(List<Integer> ids);

}
