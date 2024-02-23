package com.lxy.charge.mapper.charge;

import com.lxy.charge.pojo.charge.Bill;
import com.lxy.charge.pojo.charge.Stack;
import com.lxy.charge.pojo.charge.Station;
import com.lxy.charge.pojo.charge.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BillMapper {
    List<Bill> getBillList(Bill bill);
    int billDelete(Integer id);

    int billAdd(Bill bill);
    List<Station> getStationIdAndName();

    List<Stack> getStackIdAndName();

    List<User> getUserIdAndName();

    int billEdit(Bill bill);

    int billDeleteByIds(List<Integer> ids);

}
