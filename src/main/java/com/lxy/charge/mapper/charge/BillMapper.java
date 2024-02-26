package com.lxy.charge.mapper.charge;

import com.lxy.charge.pojo.charge.Bill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BillMapper {
    List<Bill> getBillList(Bill bill);
    int billDelete(Integer id);

    int billAdd(Bill bill);

    int billEdit(Bill bill);

    int billDeleteByIds(List<Integer> ids);

}
