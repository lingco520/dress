package com.daqsoft.service.client.comprehensiveService.impl;

import com.daqsoft.mapper.client.carflow.CarFlowDao;
import com.daqsoft.mapper.client.timelyparking.TimelyparkingDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.comprehensiveService.TimelyParkingCarFlowService;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.CarFromProvinceVo;
import com.daqsoft.vo.client.madeVoBean.TimelyParkingSurplusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:停车场数据逻辑实现
 */


@Service
public class TimelyParkingCarFlowServiceImpl implements TimelyParkingCarFlowService {

    @Autowired
    private TimelyparkingDao timelyparkingDao;
    @Autowired
    private CarFlowDao carFlowDao;
    @Autowired
    private RedisTemplate redisTemplate;

    //主页停车场数据
    @Override
    public List<TimelyParkingSurplusVo> getJqTimelyParking(String vcode) {

        return timelyparkingDao.getJqTimelyParking(vcode);
    }

    //主页停车场数据
    @Override
    public TimelyParkingSurplusVo getJqTimelyParkingData(Map map) {
        return timelyparkingDao.getJqParkingData(map);
    }

    //主页车辆来源数据前十
    @Override
    public List<CarFromProvinceVo> getCarFromByMonth(Map map) {
        //缓存添加
        String k= RedisKey.MOUDEL_CARFLOW+RedisKey.CLASS_CARCOMETOCITY+"getCarFromByDay:getCarFromByMonth:";
        //hashkey
        String hk=map.get("startTime")+"--"+map.get("endTime")+"|"+map.get("vcode");
        Object obj= RedisCache.getHash(redisTemplate,k, hk);
        List<CarFromProvinceVo> list = null;
        if(obj==null){
            list = timelyparkingDao.getCarFromByMonth(map);
            RedisCache.putHash(redisTemplate,k,hk,list);
        }else{
            list = (List<CarFromProvinceVo>)obj;
        }
        return list;
    }


    //主页停车场车辆类型按时间查询
    @Override
    public List<Map> getCarTypeParkByDay(Map map) {
        String k= "carflow:carType:day:";
        //hashkey
        String hk="day|"+ map.get("date")+"|"+map.get("vcode");
        Object obj= RedisCache.getHash(redisTemplate,k, hk);
        List<Map> list = null;
        if(obj==null){
            Map mapMapper = new HashMap();
            mapMapper.put("vcode",map.get("vcode"));
            mapMapper.put("startTime", map.get("date")+" 00:00:00");
            mapMapper.put("endTime", map.get("date")+" 23:59:59");
            list = carFlowDao.getCarNumByType(mapMapper);
            RedisCache.putHash(redisTemplate,k,hk,list);
        }else{
            list = (List<Map>)obj;
        }
        return list;
    }

    @Override
    public TimelyParkingSurplusVo getJqTimelyParkingOne(String vcode) {
        return timelyparkingDao.getJqTimelyParkingOne(vcode);
    }

}
