package com.daqsoft.service.client.passengerFlowService.impl;

import com.daqsoft.mapper.client.carflow.BigCarFlowDao;
import com.daqsoft.service.client.passengerFlowService.CarFlowService;
import com.daqsoft.vo.client.madeVoBean.*;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 车流趋势每年月度每日每月统计
 * Created by lianch on 2017/6/6.
 */
@Service
public class CarFlowServiceImpl implements CarFlowService {

    @Autowired
    private BigCarFlowDao bigCarFlowDao;

    //redis模板
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据表名和key值获取对应的redis数据
     *
     * @param table redis的key
     * @param key   获取redis数据的hashKey
     * @return
     */
    private Object getDataFromRedis(String table, String key) {
        HashOperations hash = stringRedisTemplate.opsForHash();
        Object str = hash.get(table, key);
        return str;
    }

    /**
     * 每年月度统计
     *
     * @param vcode 景区编码
     * @param year  年份
     * @return
     */
    @Override
    public List<CarFlowVos> getCarFlowMonth(String vcode, String year) {
        List<CarFlowVos> carFlowVos = new ArrayList();
        if (vcode == null || year == null) {
            return carFlowVos;
        }
        //拼接redis的hashKey
        String key = strToFormatStr(year, "month") + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis("carflow:big_carflow:month:", key);
        if (resultStr == null) {
            return carFlowVos;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return carFlowVos;
        }
        for (Object jsonKey : resultJson.keySet()) {
            CarFlowVos carFlowVo = new CarFlowVos();
            Object value = resultJson.get(jsonKey);
            if (jsonKey == null) {
                continue;
            }
            String regex = "月";
            carFlowVo.setTime(jsonKey.toString().replace(regex, ""));
            carFlowVo.setNum(value == null ? 0 : Integer.parseInt(String.valueOf(value)));
            carFlowVos.add(carFlowVo);
        }
        return carFlowVos;
    }

    /**
     * 每月统计
     *
     * @param vcode 景区编码
     * @param month 月份
     * @return
     */
    @Override
    public List<CarFlowVos> getCarFlowDay(String vcode, String month) {
        List<CarFlowVos> carFlowVos = new ArrayList();
        if (vcode == null || month == null) {
            return carFlowVos;
        }
        //拼接redis的hashKey
        String key = strToFormatStr(month, "day") + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis("carflow:big_carflow:day:", key);
        if (resultStr == null) {
            return carFlowVos;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return carFlowVos;
        }
        for (Object jsonKey : resultJson.keySet()) {
            CarFlowVos carFlowVo = new CarFlowVos();
            Object value = resultJson.get(jsonKey);
            if (jsonKey == null) {
                continue;
            }
            String regex = "日";
            carFlowVo.setTime(jsonKey.toString().replace(regex, ""));
            carFlowVo.setNum(value == null ? 0 : Integer.parseInt(String.valueOf(value)));
            carFlowVos.add(carFlowVo);
        }
        return carFlowVos;
    }

    /**
     * 车流分析，小客车与散客流关系每月统计使用
     *
     * @param vcode 景区编码
     * @param month 月份
     * @return
     */
    @Override
    public List<CarFlowVos> getCarFlowDayByPeople(String vcode, String month) {
        List<CarFlowVos> carFlowVos = new ArrayList();
        if (vcode == null || month == null) {
            return carFlowVos;
        }
        //拼接redis的hashKey
        String key = strToFormatStr(month, "day") + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis("carflow:big_carflow:day:", key);
        if (resultStr == null) {
            return carFlowVos;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return carFlowVos;
        }
        for (Object jsonKey : resultJson.keySet()) {
            CarFlowVos carFlowVo = new CarFlowVos();
            Object value = resultJson.get(jsonKey);
            if (jsonKey == null) {
                continue;
            }
            String regex = "日";
            carFlowVo.setTime(month + "-" + jsonKey.toString().replace(regex, ""));
            carFlowVo.setNum(value == null ? 0 : Integer.parseInt(String.valueOf(value)));
            carFlowVos.add(carFlowVo);
        }
        return carFlowVos;
    }

    /**
     * 每日统计
     *
     * @param vcode 景区编码
     * @param day   天
     * @return
     */
    @Override
    public List<CarFlowVos> getCarFlowHour(String vcode, String day) {
        List<CarFlowVos> carFlowVos = new ArrayList();
        if (vcode == null || day == null) {
            return carFlowVos;
        }
        //拼接redis的hashKey
        String key = strToFormatStr(day, "time") + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis("carflow:big_carflow:time:", key);
        if (resultStr == null) {
            return carFlowVos;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return carFlowVos;
        }
        for (Object jsonKey : resultJson.keySet()) {
            CarFlowVos carFlowVo = new CarFlowVos();
            Object value = resultJson.get(jsonKey);
            if (jsonKey == null) {
                continue;
            }
            carFlowVo.setTime(jsonKey.toString());
            carFlowVo.setNum(value == null ? 0 : Integer.parseInt(String.valueOf(value)));
            carFlowVos.add(carFlowVo);
        }
        return carFlowVos;
    }

    /**
     * 每日统计
     *
     * @param vcode 景区编码
     * @param day   天
     * @return
     */
    @Override
    public List<CarFlowSumVo> getCarFlowSum(String vcode, String day) {
        List<CarFlowSumVo> carFlowSumVos = new ArrayList();
        long sum = 0;
        if (vcode == null || day == null) {
            return carFlowSumVos;
        }
        //拼接redis的hashKey
        String key = strToFormatStr(day, "time") + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis("carflow:big_carflow:time:", key);
        if (resultStr == null) {
            return carFlowSumVos;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return carFlowSumVos;
        }
        for (Object jsonKey : resultJson.keySet()) {
            CarFlowSumVo carFlowSumVo = new CarFlowSumVo();
            Object value = resultJson.get(jsonKey);
            sum += (value == null ? 0 : Integer.parseInt(String.valueOf(value)));
            if (jsonKey == null) {
                continue;
            }
            carFlowSumVo.setTime(jsonKey.toString());
            carFlowSumVo.setSum(sum);
            carFlowSumVos.add(carFlowSumVo);
        }
        return carFlowSumVos;
    }

    /**
     * 转换为相应的日期
     *
     * @param str
     * @param type
     * @return
     */
    private String strToFormatStr(String str, String type) {
        String str1 = "";
        String str2 = "";
        if ("time".equals(type)) {
            str1 = "yyyy-MM-dd";
            str2 = "yyyy年MM月dd日";
        } else if ("day".equals(type)) {
            str1 = "yyyy-MM";
            str2 = "yyyy年MM月";
        } else if ("month".equals(type)) {
            str1 = "yyyy";
            str2 = "yyyy年";
        }
        Date date = null;
        SimpleDateFormat sdf1 = new SimpleDateFormat(str1);
        try {
            date = sdf1.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat(str2);
        return sdf2.format(date);
    }

    /**
     * 车流分析各类车型分析，总体数量分布 （年 月 日 日期段）
     *
     * @param date    开始日期
     * @param endDate 结束日期
     * @param type    类型
     * @param vcode   景区编码
     * @return
     */
    @Override
    public List<CarFlow_Type_Tend> findCarTypePrecent(String date, String endDate, String type, String vcode) {
        Map<String, String> map = new HashMap();
        List<CarFlow_Type_Tend> carFlow_type_tends = new ArrayList();
        //封装where条件
        map.put("startTime", date);
        map.put("endTime", endDate);
        map.put("vcode", vcode);
        //根据不同的类型调用不同的dao
        if ("year".equals(type)) {
            List<CarFlow_Type_Tend> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByYear(map);
            carTypeConvert(carFlow_type_tends, carFlowTypeTends);
        } else if ("month".equals(type)) {
            List<CarFlow_Type_Tend> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByMonth(map);
            carTypeConvert(carFlow_type_tends, carFlowTypeTends);
        } else if ("day".equals(type)) {
            List<CarFlow_Type_Tend> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByDay(map);
            carTypeConvert(carFlow_type_tends, carFlowTypeTends);
        } else if ("timeQuantum".equals(type)) {
            List<CarFlow_Type_Tend> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByTimeQuantum(map);
            carTypeConvert(carFlow_type_tends, carFlowTypeTends);
        }
        return carFlow_type_tends;
    }

    /**
     * 车流分析各类车型分析，总体数量分布 （日,月,年）
     *
     * @param time  时间
     * @param type  时间类型
     * @param vcode 景区编码
     * @return
     */
    @Override
    public List<CarFlow_Type_Tend> findCarTypePrecentByTime(String time, String type, String vcode) {
        Map<String, String> map = new HashMap();
        List<CarFlow_Type_Tend> carFlow_type_tends = new ArrayList();
        //封装where条件
        map.put("time", time);
        map.put("vcode", vcode);
        //根据不同的类型调用不同的dao
        if ("year".equals(type)) {
            List<CarFlow_Type_Tend> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByYearTime(map);
            carTypeConvert(carFlow_type_tends, carFlowTypeTends);
        } else if ("month".equals(type)) {
            List<CarFlow_Type_Tend> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByMonthTime(map);
            carTypeConvert(carFlow_type_tends, carFlowTypeTends);
        } else if ("day".equals(type)) {
            List<CarFlow_Type_Tend> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByDayTime(map);
            carTypeConvert(carFlow_type_tends, carFlowTypeTends);
        }
        return carFlow_type_tends;
    }

    /**
     * 汽车类型转换
     *
     * @param carFlow_type_tends 需要输出的集合
     * @param carFlowTypeTends   查询出来的集合
     */
    private void carTypeConvert(List<CarFlow_Type_Tend> carFlow_type_tends, List<CarFlow_Type_Tend> carFlowTypeTends) {
        for (CarFlow_Type_Tend ob : carFlowTypeTends) {
            if ("0".equals(ob.getCarType())) {
                ob.setCarType("其他车辆");
            } else if ("1".equals(ob.getCarType())) {
                ob.setCarType("小客车");
            } else if ("2".equals(ob.getCarType())) {
                ob.setCarType("大巴车");
            }
            carFlow_type_tends.add(ob);
        }
    }

    /**
     * 汽车趋势（月份）
     *
     * @param time  时间
     * @param type  时间类型
     * @param vcode 编码
     * @return
     */
    @Override
    public List<CarFlow_Change_Tend> findChangeCarSumByMonth(String time, String type, String vcode) {
        List<CarFlow_Change_Tend> carFlow_change_tends = new ArrayList();
        Map<String, String> map = new HashMap();
        //封装where条件数据
        map.put("time", time);
        map.put("vcode", vcode);
        int dayNum = getDaysByYearMonth(Integer.valueOf(time.substring(0, 4)), Integer.valueOf(time.substring(6, 7)));
        if ("month".equals(type)) {
            List<CarFlow_Change_Time_Tend> carFlow_type_tends = bigCarFlowDao.findChangeCarSumByMonth(map);
            //循环日期封装数据
            for (int i = 1; i <= dayNum; i++) {
                getCarDayNum(time, carFlow_change_tends, carFlow_type_tends, i);
            }

            return carFlow_change_tends;
        }
        return carFlow_change_tends;
    }

    /**
     * 车流分析;各类车辆实时变动趋势(日期段) 暂时只支持日时间段
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param type      类型
     * @param vcode     编码
     * @return
     */
    @Override
    public List<CarFlow_Change_Tend> findChangeCarSum(String startTime, String endTime, String type, String vcode) {
        List<CarFlow_Change_Tend> carFlow_change_tends = new ArrayList();
        Map<String, String> map = new HashMap();
        //封装where条件数据
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("vcode", vcode);
        int startDayNum = getDaysByYearMonth(Integer.valueOf(startTime.substring(0, 4)), Integer.valueOf(startTime.substring(6, 7)));
        if ("timeQuantum".equals(type)) {
            List<CarFlow_Change_Time_Tend> carFlow_type_tends = bigCarFlowDao.findChangeCarSum(map);
            if (carFlow_type_tends.size() > 0) {
                //循环日期封装数据
                int startIndex = 0;
                int endIndex = 0;
                if ("0".equals(startTime.substring(8))) {
                    startIndex = Integer.valueOf(startTime.substring(9));
                } else {
                    startIndex = Integer.valueOf(startTime.substring(8, 10));
                }
                if ("0".equals(startTime.substring(8))) {
                    endIndex = Integer.valueOf(endTime.substring(9));
                } else {
                    endIndex = Integer.valueOf(endTime.substring(8, 10));
                }
                //如果开始时间和结束时间的年月一样则调用一次就行
                if (startTime.substring(0, 8).equals(endTime.substring(0, 8))) {
                    for (int i = startIndex; i <= endIndex; i++) {
                        getCarDayNum(startTime, carFlow_change_tends, carFlow_type_tends, i);
                    }
                } else {
                    for (int i = startIndex; i <= startDayNum; i++) {
                        getCarDayNum(startTime, carFlow_change_tends, carFlow_type_tends, i);
                    }
                    for (int i = 1; i <= endIndex; i++) {
                        getCarDayNum(endTime, carFlow_change_tends, carFlow_type_tends, i);
                    }
                }


                return carFlow_change_tends;
            }

        }
        return carFlow_change_tends;
    }
    @Override
    public String getRegionByVcode(String vcode) {
        return bigCarFlowDao.getRegionByVcode(vcode);
    }

    @Override
    public List<CarComeFromCarVo> getCarComeFromYearCar(Map<String, Object> parMap) {
        return bigCarFlowDao.getCarComeFromYearCar(parMap);
    }

    /**
     * 根据日期循环获取数据
     *
     * @param startTime            循环的日期
     * @param carFlow_change_tends 返回的数据集合
     * @param carFlow_type_tends   查询出来的数据集合
     * @param i                    循环的第几次
     * @return
     */
    private void getCarDayNum(String startTime, List<CarFlow_Change_Tend> carFlow_change_tends, List<CarFlow_Change_Time_Tend> carFlow_type_tends, int i) {
        startTime = startTime.substring(0, 7);
        if (i < 10) {
            startTime += "-0" + i;
        } else {
            startTime += "-" + i;
        }

        CarFlow_Change_Tend carFlow_change_tend = new CarFlow_Change_Tend();
        carFlow_change_tend.setTime(startTime);
        for (CarFlow_Change_Time_Tend obj : carFlow_type_tends) {
            if (startTime.equals(obj.getTime())) {
                if ("0".equals(obj.getSource())) {//其他车辆
                    carFlow_change_tend.setOtherCar(obj.getNum());
                } else if ("1".equals(obj.getSource())) {//小客车
                    carFlow_change_tend.setSmallCar(obj.getNum());
                } else if ("2".equals(obj.getSource())) {//大巴车
                    carFlow_change_tend.setBusCar(obj.getNum());
                }
            }
        }
        //如果没有，则返回0
        if (StringUtils.isBlank(carFlow_change_tend.getBusCar())) {
            carFlow_change_tend.setBusCar("0");
        }
        if (StringUtils.isBlank(carFlow_change_tend.getOtherCar())) {
            carFlow_change_tend.setOtherCar("0");
        }
        if (StringUtils.isBlank(carFlow_change_tend.getSmallCar())) {
            carFlow_change_tend.setSmallCar("0");
        }
        carFlow_change_tends.add(carFlow_change_tend);
    }


    /**
     * 根据年 月 获取对应的月份 天数
     *
     * @param year  年
     * @param month 月
     * @return
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
