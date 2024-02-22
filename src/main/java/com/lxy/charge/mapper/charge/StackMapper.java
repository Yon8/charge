package com.lxy.charge.mapper.charge;

import com.lxy.charge.pojo.charge.Stack;
import com.lxy.charge.pojo.charge.Station;
import com.lxy.charge.pojo.charge.Warden;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StackMapper {
    List<Stack> getStackList(Stack stack);
    int stackDelete(Integer id);

    int stackAdd(Stack stack);
    List<Station> getStationIdAndName();

    int stackEdit(Stack stack);

    int stackDeleteByIds(List<Integer> ids);

}
