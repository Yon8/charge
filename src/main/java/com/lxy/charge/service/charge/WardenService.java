package com.lxy.charge.service.charge;

import com.lxy.charge.mapper.charge.WardenMapper;
import com.lxy.charge.pojo.charge.Warden;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WardenService {
    private final WardenMapper wardenMapper;
    @Autowired
    public WardenService(WardenMapper wardenMapper) {
        this.wardenMapper = wardenMapper;
    }

    public List<Warden> getWardenList(Warden warden) {
        return wardenMapper.getWardenList(warden);
    }

    public Boolean wardenDelete(Warden warden) {
        return wardenMapper.wardenDelete(warden.getId()) == 1;
    }

    public Warden wardenAdd(Warden warden) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        warden.setId(uuid.substring(0,6));
        wardenMapper.wardenAdd(warden);
        return warden;
    }

    public Boolean wardenEdit(Warden warden) {
        return wardenMapper.wardenEdit(warden) == 1;
    }

    public Boolean wardenDeleteByIds(List<String> ids) {
        return wardenMapper.wardenDeleteByIds(ids)!=0;
    }
    //声明SQL会话工厂并自动填充
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public void saveBatch(List<Warden> list) {

        // 关闭session的自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            WardenMapper userMapper = sqlSession.getMapper(WardenMapper.class);
            list.forEach(user -> userMapper.wardenAdd(user));
            // 提交数据
            sqlSession.commit();
            sqlSession.rollback();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

}
