package com.daqsoft.service.client.trafficAnalysisService.impl;

import com.daqsoft.mapper.client.carflow.CarComeFromWidgetDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.passengerFlowService.CarFlowService;
import com.daqsoft.service.client.trafficAnalysisService.CarComeToService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.ProvinceUtil;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.CarComeFromWidgetVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆来源地实现类
 * <p>
 * Created by lianch on 2017/6/7.
 *
 * @update zf 20170802
 */
@Service
public class CarComeToServiceImpl implements CarComeToService {

    @Autowired
    private CarComeFromWidgetDao carComeFromDao;
    @Autowired
    private CarFlowService carFlowService;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 当日车辆来源排行
     *
     * @param vcode 景区编码
     * @param day   时间
     * @return
     */
    @Override
    public List<CarComeFromWidgetVo> getCarComeFromDay(String vcode, String day) {
        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("day", day);
        List<CarComeFromWidgetVo> carComeFromVos = carComeFromDao.getCarComeFromDay(map);
        return carComeFromVos;
    }

    /**
     * 选择日期车辆来源排行
     *
     * @param vcode    景区编码
     * @param beginDay 开始日期
     * @param endDay   结束日期
     * @return
     * @update zf 20170822
     * @update zf 20170802
     */
    @Override
    public Object getCarComeFromDay2Day(String vcode, String beginDay, String endDay) {
        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("beginDay", beginDay + " 00:00:00");
        map.put("endDay", endDay + " 23:59:59");
//        List<CarComeFromVo> carComeFromVos = carComeFromDao.getCarComeFromDay2Day(map);

        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARCOMETOCITY + "dayToday:" + "getCarComeFromDay2Day:";
        //hashkey
        String hk = "day" + beginDay + endDay + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarComeFromWidgetVo> list = carComeFromDao.getCarComeFromDay2Day(map);
            RedisCache.putHash(redisTemplate, k, hk, list);
            obj = list;
        }
        //查询景区region
        String region = carComeFromDao.getRegion(vcode);
        // 查询景区省份简称
        String pname = carFlowService.getRegionByVcode(vcode);
        // 转换成前端的地图文件名称
        String shortName = ProvinceUtil.provinceNameToShort(pname);
        Map<String, Object> dataMap = new HashMap();
        dataMap.put("region", region);
        dataMap.put("CarComeFrom", obj);
        dataMap.put("provinceEnName", shortName);

        return dataMap;

    }


    /**
     * 季度城市车辆来源
     *
     * @param vcode   景区编码
     * @param quarter 年份季度
     * @return
     * @update zf 20170822
     */
    @Override
    public Object getCarComeFromQuarter(String vcode, String quarter) {

        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("quarter", quarter);
        Map m = DateUtil.getTimeByQuarters(quarter);
        map.put("startTime", m.get("startTime").toString());
        map.put("endTime", m.get("endTime").toString());
        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARCOMETOCITY + "quarter:" + "getCarComeFromQuarter:";
        //hashkey
        String hk = "quarter" + quarter + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarComeFromWidgetVo> list = carComeFromDao.getCarComeFromQuarter(map);
            RedisCache.putHash(redisTemplate, k, hk, list);
            obj = list;
        }
        //查询景区region
        String region = carComeFromDao.getRegion(vcode);
        // 查询景区省份简称
        String pname = carFlowService.getRegionByVcode(vcode);
        // 转换成前端的地图文件名称
        String shortName = ProvinceUtil.provinceNameToShort(pname);
        Map<String, Object> dataMap = new HashMap();
        dataMap.put("region", region);
        dataMap.put("CarComeFrom", obj);
        dataMap.put("provinceEnName", shortName);


        return dataMap;


    }

    /**
     * 当年城市车辆来源
     *
     * @param vcode 景区编码
     * @param year  年份
     * @return
     */
    @Override
    public List<CarComeFromWidgetVo> getCarComeFromYear(String vcode, String year) {
        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("year", year);
        List<CarComeFromWidgetVo> carComeFromVos = carComeFromDao.getCarComeFromYear(map);
        return carComeFromVos;
    }

    /**
     * 选择年城市车辆来源
     *
     * @param vcode     景区编码
     * @param beginYear 开始年份
     * @param endYear   结束年份
     * @return
     * @update zf 20170822
     * @update zf 20170802
     */
    @Override
    public Object getCarComeFromYear2Year(String vcode, String beginYear, String endYear) {
        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("beginYear", beginYear + "-01-01 00:00:00");
        map.put("endYear", endYear + "-12-31 23:59:59");
//        List<CarComeFromWidgetVo> carComeFromVos = carComeFromDao.getCarComeFromYear2Year(map);

        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARCOMETOCITY + "yearToyear:" + "getCarComeFromYear2Year:";
        //hashkey
        String hk = "year" + beginYear + endYear + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarComeFromWidgetVo> list = carComeFromDao.getCarComeFromYear2Year(map);
            RedisCache.putHash(redisTemplate, k, hk, list);
            obj = list;
        }
        //查询景区region
        String region = carComeFromDao.getRegion(vcode);
        // 查询景区省份简称
        String pname = carFlowService.getRegionByVcode(vcode);
        // 转换成前端的地图文件名称
        String shortName = ProvinceUtil.provinceNameToShort(pname);
        Map<String, Object> dataMap = new HashMap();
        dataMap.put("region", region);
        dataMap.put("CarComeFrom", obj);
        dataMap.put("provinceEnName", shortName);


        return dataMap;
    }

    /**
     * 当月城市车辆来源
     *
     * @param vcode 景区编码
     * @param month 月份
     * @return
     * @update zf 20170822
     * @update zf 20170802
     */
    @Override
    public Object getCarComeFromMonth(String vcode, String month) {
        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("month", month);
        map.put("startTime", month + "-01 00:00:00");
        map.put("endTime", month + "-31 23:59:59");
//        List<CarComeFromWidgetVo> carComeFromVos = carComeFromDao.getCarComeFromMonth(map);

        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARCOMETOCITY + "month:" + "getCarComeFromMonth:";
        //hashkey
        String hk ="month"+ month + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarComeFromWidgetVo> list = carComeFromDao.getCarComeFromMonth(map);
            RedisCache.putHash(redisTemplate, k, hk, list);
            obj = list;
        }
        // 查询景区省份简称
        String pname = carFlowService.getRegionByVcode(vcode);
        //查询景区region
        String region = carComeFromDao.getRegion(vcode);
        // 转换成前端的地图文件名称
        String shortName = ProvinceUtil.provinceNameToShort(pname);
        Map<String, Object> dataMap = new HashMap();
        dataMap.put("region", region);
        dataMap.put("CarComeFrom", obj);
        dataMap.put("provinceEnName", shortName);
        return dataMap;
    }
}
