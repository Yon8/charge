package com.lxy.charge.mapper.charge;

import com.lxy.charge.pojo.charge.Fault;
import com.lxy.charge.pojo.charge.Stack;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaultMapper {
    List<Fault> getFaultList(Fault fault);
    int faultDelete(Integer id);

    int faultAdd(Fault fault);
    List<Stack> getStackIdAndName();

    int faultEdit(Fault fault);

    int faultDeleteByIds(List<Integer> ids);

}
