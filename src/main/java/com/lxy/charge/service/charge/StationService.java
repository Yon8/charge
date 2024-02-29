package com.lxy.charge.service.charge;

import com.lxy.charge.mapper.charge.StationMapper;
import com.lxy.charge.pojo.PageVo;
import com.lxy.charge.pojo.charge.Station;
import com.lxy.charge.utils.RedisUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class StationService {
    private final StationMapper stationMapper;
    private final RedisUtil redisUtil;
    //redis缓存list键名
    private final String listCacheKey = "stationListCache";
    private final String idAndNameCacheKey =  "stationIdAndNameCache";
    @Autowired
    public StationService(StationMapper stationMapper,RedisUtil redisUtil) {
        this.stationMapper = stationMapper;
        this.redisUtil = redisUtil;
    }

    public List<Station> getStationList(Station station) {
        return stationMapper.getStationList(station);
    }

    public List<Station> getStationList(Station station, PageVo page) {
        List<Station>  cachedStationList = (List<Station>) redisUtil.get(listCacheKey + page.getCurrent());
        if (cachedStationList != null && !cachedStationList.isEmpty()) {
            return cachedStationList;
        }
        List<Station> stationList = stationMapper.getStationList(station);
        redisUtil.set(listCacheKey + page.getCurrent(), stationList,60*60);
        return stationList;
    }
    public List<Station> getStationIdAndName() {
        List<Station> cachedStationIdAndName = (List<Station>) redisUtil.get(idAndNameCacheKey);
        if (cachedStationIdAndName != null && !cachedStationIdAndName.isEmpty()) {
            return cachedStationIdAndName;
        }
        List<Station> stationIdAndName = stationMapper.getStationIdAndName();
        redisUtil.set( idAndNameCacheKey, stationIdAndName,60*60);
        return stationIdAndName;
    }

    public Boolean stationDelete(Station station) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        return stationMapper.stationDelete(station.getId()) == 1;
    }

    public Station stationAdd(Station station) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        stationMapper.stationAdd(station);
        return station;
    }

    public Boolean stationEdit(Station station) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
        return stationMapper.stationEdit(station) == 1;
    }

    public Boolean stationDeleteByIds(List<Integer> ids) {
        //删除相关缓存
        redisUtil.deleteAllRelatedCache(listCacheKey);
        redisUtil.delete(idAndNameCacheKey);
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
            //删除相关缓存
            redisUtil.deleteAllRelatedCache(listCacheKey);
            redisUtil.delete(idAndNameCacheKey);
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

}
