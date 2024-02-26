package com.lxy.charge.service.charge;

import com.lxy.charge.mapper.charge.StationMapper;
import com.lxy.charge.pojo.charge.Station;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    private final StationMapper stationMapper;
    @Autowired
    public StationService(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    public List<Station> getStationList(Station station) {
        return stationMapper.getStationList(station);
    }
    public List<Station> getStationIdAndName() {
        return stationMapper.getStationIdAndName();
    }

    public Boolean stationDelete(Station station) {
        return stationMapper.stationDelete(station.getId()) == 1;
    }

    public Station stationAdd(Station station) {
        stationMapper.stationAdd(station);
        return station;
    }

    public Boolean stationEdit(Station station) {
        return stationMapper.stationEdit(station) == 1;
    }

    public Boolean stationDeleteByIds(List<Integer> ids) {
        return stationMapper.stationDeleteByIds(ids)!=0;
    }

    //声明SQL会话工厂并自动填充
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public void saveBatch(List<Station> list) {

        // 关闭session的自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            StationMapper userMapper = sqlSession.getMapper(StationMapper.class);
            list.forEach(user -> userMapper.stationAdd(user));
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
