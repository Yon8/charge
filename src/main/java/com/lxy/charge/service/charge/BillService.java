package com.lxy.charge.service.charge;

import com.lxy.charge.mapper.charge.BillMapper;
import com.lxy.charge.pojo.charge.Bill;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class BillService {
    private final BillMapper billMapper;
    @Autowired
    public BillService(BillMapper billMapper) {
        this.billMapper = billMapper;
    }

    public List<Bill> getBillList(Bill bill) {
        return billMapper.getBillList(bill);
    }

    public Boolean billDelete(Bill bill) {
        return billMapper.billDelete(bill.getId()) == 1;
    }

    public Bill billAdd(Bill bill) {
        billMapper.billAdd(bill);
        return bill;
    }

    public Boolean billEdit(Bill bill) {
        return billMapper.billEdit(bill) == 1;
    }

    public Boolean billDeleteByIds(List<Integer> ids) {
        return billMapper.billDeleteByIds(ids)!=0;
    }

    //声明SQL会话工厂并自动填充
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public void saveBatch(List<Bill> list) {

        // 关闭session的自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            BillMapper userMapper = sqlSession.getMapper(BillMapper.class);
            list.forEach(user -> userMapper.billAdd(user));
            // 提交数据
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

}
