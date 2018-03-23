package com.daqsoft.service.client.trafficAnalysisService.impl;

import com.daqsoft.mapper.client.carflow.BigCarFlowWidgetDao;
import com.daqsoft.redis.ExecutorServiceUtils;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.trafficAnalysisService.CarFlowQsTask;
import com.daqsoft.service.client.trafficAnalysisService.CarFlowWidgetService;
import com.daqsoft.utils.client.DateTools;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.*;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 车流趋势每年月度每日每月统计
 * Created by lianch on 2017/6/6.
 */
@Service
public class CarFlowWidgetServiceImpl implements CarFlowWidgetService {

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private BigCarFlowWidgetDao bigCarFlowDao;

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
     * @update zf 20170822
     */
    @Override
    public Object getCarFlowMonth(String vcode, String year) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date s_date = null;
        try {
            s_date = sdf.parse(year);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DateFormat df = null;
        df = new SimpleDateFormat("yyyy");
        year=df.format(s_date);
        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_BIGCARFLOW + "year:getCarFlowMonth:";
        //hashkey
        String hk = "year" + year + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarFlowVosWidget> carFlowVoWidgets = this.getByYear(vcode, year);
            RedisCache.putHash(redisTemplate, k, hk, carFlowVoWidgets);
            obj = carFlowVoWidgets;
        }
        return obj;
    }

    /**
     * 每月统计
     *
     * @param vcode 景区编码
     * @param month 月份
     * @return
     * @update zf 20170822
     */
    @Override
    public Object getCarFlowDay(String vcode, String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String now = DateTools.getCurrentMonth();
        Date s_date = null;
        Date nowDate = null;
        try {
            s_date = sdf.parse(month);
            nowDate = sdf.parse(now);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(s_date.getTime() <= nowDate.getTime()) {
            DateFormat df = null;
            df = new SimpleDateFormat("yyyy-MM");
            month = df.format(s_date);
            //大key
            String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_BIGCARFLOW + "month:getCarFlowDay:";
            //hashkey
            String hk = "month" + month + DigestUtils.md5Hex(vcode);
            Object obj = RedisCache.getHash(redisTemplate, k, hk);
            if (null == obj) {
                List<CarFlowVosWidget> carFlowVoWidgets = new ArrayList<>();
                List<Big_CarFlow_WidgetTend> list = this.getByMonth(vcode, month);
                for (Big_CarFlow_WidgetTend vo : list) {
                    CarFlowVosWidget po = new CarFlowVosWidget();
                    po.setTime(vo.getTime());
                    po.setNum(Long.parseLong(vo.getSumCar()));
                    carFlowVoWidgets.add(po);
                }
                RedisCache.putHash(redisTemplate, k, hk, carFlowVoWidgets);
                obj = carFlowVoWidgets;
            }
            return obj;
        } else {
            List<Map> list = new ArrayList();
            return list;
        }
    }

    /**
     * 车流分析，小客车与散客流关系每月统计使用
     *
     * @param vcode 景区编码
     * @param month 月份
     * @return
     * @update zf 20170822
     */
    @Override
    public List<CarFlowVosWidget> getCarFlowDayByPeople(String vcode, String month) {
        List<CarFlowVosWidget> carFlowVoWidgets = new ArrayList();
        if (vcode == null || month == null) {
            return carFlowVoWidgets;
        }
        //拼接redis的hashKey
        String key = strToFormatStr(month, "day") + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis("carflow:big_carflow:day:", key);
        if (resultStr == null) {
            return carFlowVoWidgets;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return carFlowVoWidgets;
        }
        for (Object jsonKey : resultJson.keySet()) {
            CarFlowVosWidget carFlowVo = new CarFlowVosWidget();
            Object value = resultJson.get(jsonKey);
            if (jsonKey == null) {
                continue;
            }
            String regex = "日";
            carFlowVo.setTime(month + "-" + jsonKey.toString().replace(regex, ""));
            carFlowVo.setNum(value == null ? 0 : Integer.parseInt(String.valueOf(value)));
            carFlowVoWidgets.add(carFlowVo);
        }
        return carFlowVoWidgets;
    }

    /**
     * 每日统计
     *
     * @param vcode 景区编码
     * @param day   天
     * @return
     * @update zf 20170822
     */
    @Override
    public Object getCarFlowHour(String vcode, String day) {
        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARSFLOW + "day:getCarFlowHour:";
        //hashkey
        String hk = "day" + day + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarFlowVosWidget> carFlowVoWidgets = bigCarFlowDao.getCarFlowDailyTocalCountGroupByVcodeAndHour(vcode, day);
            RedisCache.putHash(redisTemplate, k, hk, carFlowVoWidgets);
            obj = carFlowVoWidgets;
        }

        /*List<CarFlowVosWidget> carFlowVoWidgets = new ArrayList();
        if (vcode == null || day == null) {
            return carFlowVoWidgets;
        }
        //拼接redis的hashKey
        String key = strToFormatStr(day, "time") + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis("carflow:big_carflow:time:", key);
        if (resultStr == null) {
            return carFlowVoWidgets;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return carFlowVoWidgets;
        }
        for (Object jsonKey : resultJson.keySet()) {
            CarFlowVosWidget carFlowVo = new CarFlowVosWidget();
            Object value = resultJson.get(jsonKey);
            if (jsonKey == null) {
                continue;
            }
            carFlowVo.setTime(jsonKey.toString());
            carFlowVo.setNum(value == null ? 0 : Integer.parseInt(String.valueOf(value)));
            carFlowVoWidgets.add(carFlowVo);
        }*/
        return obj;
    }

    /**
     * 每日统计
     *
     * @param vcode 景区编码
     * @param day   天
     * @return
     * @update zf 20170822
     */
    @Override
    public Object getCarFlowSum(String vcode, String day) {
        List<CarFlowSumVoWidget> list = new ArrayList<>();
        long sum = 0;
        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARSFLOW + "day:getCarFlowSum:";
        //hashkey
        String hk = "day" + day + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarFlowVosWidget> carFlowSumVoWidgets = bigCarFlowDao.getCarFlowDailyTocalCountGroupByVcodeAndHour(vcode, day);
            for (CarFlowVosWidget vo : carFlowSumVoWidgets) {
                CarFlowSumVoWidget carvo = new CarFlowSumVoWidget();
                sum += vo.getNum();//累计求和
                carvo.setSum(sum);
                carvo.setTime(vo.getTime());
                list.add(carvo);
            }
            RedisCache.putHash(redisTemplate, k, hk, list);
            obj = list;
        }

        /*List<CarFlowSumVoWidget> carFlowSumVoWidgets = new ArrayList();
        long sum = 0;
        if (vcode == null || day == null) {
            return carFlowSumVoWidgets;
        }
        //拼接redis的hashKey
        String key = strToFormatStr(day, "time") + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis("carflow:big_carflow:time:", key);
        if (resultStr == null) {
            return carFlowSumVoWidgets;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return carFlowSumVoWidgets;
        }
        for (Object jsonKey : resultJson.keySet()) {
            CarFlowSumVoWidget carFlowSumVoWidget = new CarFlowSumVoWidget();
            Object value = resultJson.get(jsonKey);
            sum += (value == null ? 0 : Integer.parseInt(String.valueOf(value)));
            if (jsonKey == null) {
                continue;
            }
            carFlowSumVoWidget.setTime(jsonKey.toString());
            carFlowSumVoWidget.setSum(sum);
            carFlowSumVoWidgets.add(carFlowSumVoWidget);
        }*/
        return obj;
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
     * @update zf 20170822
     */
    @Override
    public Object findCarTypePrecent(String date, String endDate, String type, String vcode) {
        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARSFLOW + "getFlowCarPercent:findCarTypePrecent:";
        //hashkey
        String hk = type + date + endDate + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            Map<String, String> map = new HashMap();
            List<CarFlow_Type_TendWidget> carFlow_type_tends = new ArrayList();
            //封装where条件

            map.put("vcode", vcode);
            //根据不同的类型调用不同的dao
            if ("year".equals(type)) {
                map.put("startTime", date + "-01-01 00:00:00");
                map.put("endTime", endDate + "-12-31 23:59:59");
                List<CarFlow_Type_TendWidget> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByYear(map);
                carFlow_type_tends=carTypeConvert(carFlow_type_tends, carFlowTypeTends);
            } else if ("month".equals(type)) {
                map.put("startTime", date + "-01 00:00:00");
                map.put("endTime", endDate + "-31 23:59:59");
                List<CarFlow_Type_TendWidget> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByMonth(map);
                carFlow_type_tends=carTypeConvert(carFlow_type_tends, carFlowTypeTends);
            } else if ("day".equals(type)) {
                map.put("startTime", date + " 00:00:00");
                map.put("endTime", endDate + " 23:59:59");
                List<CarFlow_Type_TendWidget> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByDay(map);
                carFlow_type_tends=carTypeConvert(carFlow_type_tends, carFlowTypeTends);
            } else if ("timeQuantum".equals(type)) {
                map.put("startTime", date + " 00:00:00");
                map.put("endTime", endDate + " 23:59:59");
                List<CarFlow_Type_TendWidget> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByTimeQuantum(map);
                carFlow_type_tends=carTypeConvert(carFlow_type_tends, carFlowTypeTends);
            }
            RedisCache.putHash(redisTemplate, k, hk, carFlow_type_tends);
            obj = carFlow_type_tends;
        }


        return obj;
    }

    /**
     * 车流分析各类车型分析，总体数量分布 （日,月,年）
     *
     * @param time  时间
     * @param type  时间类型
     * @param vcode 景区编码
     * @return
     * @update zf 20170822
     */
    @Override
    public Object findCarTypePrecentByTime(String time, String type, String vcode) {
        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARSFLOW + "quarter:getCarComeFromQuarter:";
        //hashkey
        String hk = type + time + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            Map<String, String> map = new HashMap();
            List<CarFlow_Type_TendWidget> carFlow_type_tends = new ArrayList();
            //封装where条件
            map.put("vcode", vcode);
            //根据不同的类型调用不同的dao
            if ("year".equals(type)) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date s_date = null;
                try {
                    s_date = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DateFormat df = null;
                df = new SimpleDateFormat("yyyy");
                map.put("time", df.format(s_date));
                map.put("startTime",df.format(s_date)+"-01-01 00:00:00");
                map.put("endTime",df.format(s_date)+"-12-31 23:59:59");
                List<CarFlow_Type_TendWidget> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByYearTime(map);
                carFlow_type_tends=carTypeConvert(carFlow_type_tends, carFlowTypeTends);


            } else if ("month".equals(type)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date s_date = null;
                try {
                    s_date = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DateFormat df = null;
                df = new SimpleDateFormat("yyyy-MM");
                map.put("time", df.format(s_date));
                map.put("startTime",df.format(s_date)+"-01 00:00:00");
                map.put("endTime",df.format(s_date)+"-31 23:59:59");
                List<CarFlow_Type_TendWidget> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByMonthTime(map);
                carFlow_type_tends=carTypeConvert(carFlow_type_tends, carFlowTypeTends);
            } else if ("day".equals(type)) {
                map.put("time", time);
                map.put("startTime",time+" 00:00:00");
                map.put("endTime",time+" 23:59:59");
                List<CarFlow_Type_TendWidget> carFlowTypeTends = bigCarFlowDao.findCarTypePrecentByDayTime(map);
                carFlow_type_tends=carTypeConvert(carFlow_type_tends, carFlowTypeTends);
            }
            RedisCache.putHash(redisTemplate, k, hk, carFlow_type_tends);
            obj = carFlow_type_tends;
        }


        return obj;
    }

    /**
     * 汽车类型转换
     *
     * @param carFlow_type_tends 需要输出的集合
     * @param carFlowTypeTends   查询出来的集合
     */
    private List<CarFlow_Type_TendWidget> carTypeConvert(List<CarFlow_Type_TendWidget> carFlow_type_tends, List<CarFlow_Type_TendWidget> carFlowTypeTends) {
        for (CarFlow_Type_TendWidget ob : carFlowTypeTends) {
            if ("0".equals(ob.getCarType())
                    ||"3".equals(ob.getCarType())
                    ||"4".equals(ob.getCarType())
                    ||"5".equals(ob.getCarType())
                    ||"6".equals(ob.getCarType())
                    ||"7".equals(ob.getCarType())
                    ||"8".equals(ob.getCarType())) {
                ob.setCarType("其他车辆");
            } else if ("1".equals(ob.getCarType())) {
                ob.setCarType("小客车");
            } else if ("2".equals(ob.getCarType())) {
                ob.setCarType("大巴车");
            }
            carFlow_type_tends.add(ob);
        }


        Map<String,Integer> m=new HashMap();
        carFlow_type_tends.forEach(item -> {
            if (m.containsKey(item.getCarType())){
                m.put(item.getCarType(),Integer.parseInt(item.getSumCar())+m.get(item.getCarType()));
            }else{
                m.put(item.getCarType(),Integer.parseInt(item.getSumCar()));
            }
        });
       List<CarFlow_Type_TendWidget> list =new ArrayList<>();

        for (Map.Entry<String, Integer> stringIntegerEntry : m.entrySet()) {
            list.add(new CarFlow_Type_TendWidget(String.valueOf(stringIntegerEntry.getValue()),stringIntegerEntry.getKey()));
        }

        return list;
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
    public Object findChangeCarSumByMonth(String time, String type, String vcode) {
        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARSFLOW + "getChangeCarSumByMonth:findChangeCarSumByMonth:";
        //hashkey
        String hk = type + time + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarFlow_Change_TendWidget> carFlow_change_tends = new ArrayList();
            Map<String, String> map = new HashMap();
            //封装where条件数据
            map.put("time", time);
            map.put("vcode", vcode);
            int dayNum = 0;
            if ("month".equals(type)) {
                String nowM = DateTools.getCurrentMonth();
                if(nowM.equals(time)) {
                    dayNum = Integer.parseInt(DateTools.getCurrentdd());
                    String day = DateTools.getCurrentDate();
                    map.put("endTime",day +" 23:59:59");

                } else {
                    dayNum = getDaysByYearMonth(Integer.valueOf(time.substring(0, 4)), Integer.valueOf(time.substring(6, 7)));
                    map.put("endTime",time+"-31 23:59:59");
                }
                map.put("startTime",time+"-01 00:00:00");
                List<CarFlow_Change_Time_TendWidget> carFlow_type_tends = bigCarFlowDao.findChangeCarSumByMonth(map);
                //循环日期封装数据
                for (int i = 1; i <= dayNum; i++) {
                    getCarDayNum(time, carFlow_change_tends, carFlow_type_tends, i);
                }

//                return carFlow_change_tends;
            }else if("day".equals(type)){
                map.put("startTime",time+" 00:00:00");
                map.put("endTime",time+" 23:59:59");
                List<CarFlow_Change_Time_TendWidget> carFlow_type_tends = bigCarFlowDao.findChangeCarSumByDay(map);

                //封装一下数据 天
                getCarDayNumByDay(carFlow_change_tends, carFlow_type_tends);

            }else if("year".equals(type)){
                map.put("startTime",time+"-01-01 00:00:00");
                map.put("endTime",time+"-12-31 23:59:59");
                List<CarFlow_Change_Time_TendWidget> carFlow_type_tends = bigCarFlowDao.findChangeCarSumByYear(map);

                int y = 12;
                String nowY = DateTools.getCurrentYear();
                if(nowY.equals(time)) {
                    y = Integer.parseInt(DateTools.getCurrentMonths());
                } else {
                   y = 12;
                }

                //封装一下数据 年
                for (int i=1;i<=y;i++){
                    getCarDayNumByYear(time,carFlow_change_tends, carFlow_type_tends,i);
                }

            }
            RedisCache.putHash(redisTemplate, k, hk, carFlow_change_tends);
            obj = carFlow_change_tends;
        }


        return obj;
    }

    /**
     * 车流分析;各类车辆实时变动趋势(日期段) 暂时只支持日时间段
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param type      类型
     * @param vcode     编码
     * @return
     * @update zf 20170822
     */
    @Override
    public Object findChangeCarSum(String startTime, String endTime, String type, String vcode) {
        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARSFLOW + "getChangeCarSum:findChangeCarSum:";
        //hashkey
        String hk = type + startTime + endTime + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarFlow_Change_TendWidget> carFlow_change_tends = new ArrayList();
            Map<String, String> map = new HashMap();
            //封装where条件数据
            map.put("startTime", startTime+" 00:00:00");
            map.put("endTime", endTime+" 23:59:59");
            map.put("vcode", vcode);
            int startDayNum = getDaysByYearMonth(Integer.valueOf(startTime.substring(0, 4)), Integer.valueOf(startTime.substring(6, 7)));
            if ("day".equals(type)) {
                List<CarFlow_Change_Time_TendWidget> carFlow_type_tends = bigCarFlowDao.findChangeCarSum(map);
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


                }

            }
            RedisCache.putHash(redisTemplate, k, hk, carFlow_change_tends);
            obj = carFlow_change_tends;
        }


        return obj;
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
    private void getCarDayNum(String startTime, List<CarFlow_Change_TendWidget> carFlow_change_tends, List<CarFlow_Change_Time_TendWidget> carFlow_type_tends, int i) {
        startTime = startTime.substring(0, 7);
        if (i < 10) {
            startTime += "-0" + i;
        } else {
            startTime += "-" + i;
        }

        CarFlow_Change_TendWidget carFlow_change_tend = new CarFlow_Change_TendWidget();
        carFlow_change_tend.setTime(startTime);
        for (CarFlow_Change_Time_TendWidget obj : carFlow_type_tends) {
            if (startTime.equals(obj.getTime())) {
                if ("0".equals(obj.getSource())
                        ||"3".equals(obj.getSource())
                        ||"4".equals(obj.getSource())
                        ||"5".equals(obj.getSource())
                        ||"6".equals(obj.getSource())
                        ||"7".equals(obj.getSource())
                        ||"8".equals(obj.getSource())) {//其他车辆
                    carFlow_change_tend.setOtherCar(obj.getNum());
                } else if ("2".equals(obj.getSource())) {//小客车
                    carFlow_change_tend.setSmallCar(obj.getNum());
                } else if ("1".equals(obj.getSource())) {//大巴车
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

    /***
     * 封装年的数据
     * @param carFlow_change_tends
     * @param carFlow_type_tends
     */
    private void getCarDayNumByYear(String startTime,List<CarFlow_Change_TendWidget> carFlow_change_tends, List<CarFlow_Change_Time_TendWidget> carFlow_type_tends,int i) {
        if (i < 10) {
            startTime += "-0" + i;
        } else {
            startTime += "-" + i;
        }
        CarFlow_Change_TendWidget carFlow_change_tend = new CarFlow_Change_TendWidget();
        carFlow_change_tend.setTime(startTime);
        for (CarFlow_Change_Time_TendWidget obj : carFlow_type_tends) {
            if (startTime.equals(obj.getTime())) {
                if ("0".equals(obj.getSource())
                        || "3".equals(obj.getSource())
                        || "4".equals(obj.getSource())
                        || "5".equals(obj.getSource())
                        || "6".equals(obj.getSource())
                        || "7".equals(obj.getSource())
                        || "8".equals(obj.getSource())) {//其他车辆
                    carFlow_change_tend.setOtherCar(obj.getNum());
                } else if ("2".equals(obj.getSource())) {//小客车
                    carFlow_change_tend.setSmallCar(obj.getNum());
                } else if ("1".equals(obj.getSource())) {//大巴车
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



    /***
     * 封装天的数据
     * @param carFlow_change_tends
     * @param carFlow_type_tends
     */
    private void getCarDayNumByDay(List<CarFlow_Change_TendWidget> carFlow_change_tends, List<CarFlow_Change_Time_TendWidget> carFlow_type_tends) {

        for (CarFlow_Change_Time_TendWidget obj : carFlow_type_tends) {
            CarFlow_Change_TendWidget carFlow_change_tend = new CarFlow_Change_TendWidget();
            if ("0".equals(obj.getSource())
                        ||"3".equals(obj.getSource())
                        ||"4".equals(obj.getSource())
                        ||"5".equals(obj.getSource())
                        ||"6".equals(obj.getSource())
                        ||"7".equals(obj.getSource())
                        ||"8".equals(obj.getSource())) {//其他车辆
                    carFlow_change_tend.setOtherCar(obj.getNum());
                } else if ("2".equals(obj.getSource())) {//小客车
                    carFlow_change_tend.setSmallCar(obj.getNum());
                } else if ("1".equals(obj.getSource())) {//大巴车
                    carFlow_change_tend.setBusCar(obj.getNum());
                }
            carFlow_change_tend.setTime(obj.getTime());
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


    /**
     * 2017-08-17 zf
     * 组装停车场结算表  年
     *
     * @param vcode
     * @param year  年份
     * @return
     */
    public List<CarFlowVosWidget> getByYear(String vcode, String year) {
        String md5key = DigestUtils.md5Hex(vcode);
        List<CarFlowVosWidget> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_BIGCARFLOW + "call:year:";
        String hk = "year" + "|" + year + "|" + md5key;

        //读取缓存
        list = (List<CarFlowVosWidget>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        list = new ArrayList<>();
        String thisMonth = DateUtil.getCurMonthNumStr();

        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        for (String month : months) {
            //只算当前月份和之前的
            if(Integer.parseInt(month)<=Integer.parseInt(thisMonth)){
                List<Big_CarFlow_WidgetTend> ticketTypeByMonth = getByMonth(vcode, year + "-" + month);
                long num = 0;

                for (Big_CarFlow_WidgetTend t : ticketTypeByMonth) {
                    num += Long.parseLong(t.getSumCar());

                }
                CarFlowVosWidget big_carFlow_widgetTend = new CarFlowVosWidget();
                big_carFlow_widgetTend.setTime(year + "-" + month);
                big_carFlow_widgetTend.setNum(num);
                list.add(big_carFlow_widgetTend);
            }
        }
        //写入缓存
        if (list.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, list);
        }

        return list != null ? list : new ArrayList<>();

    }

    /**
     * 2017-08-17 zf
     * 组装停车场结算表  月
     *
     * @param vcode
     * @param month 月份
     * @return
     */
    public List<Big_CarFlow_WidgetTend> getByMonth(String vcode, String month) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<Big_CarFlow_WidgetTend> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_BIGCARFLOW + "call:month:";
        String hk = "month" + "|" + month + "|" + md5key;

        //读取缓存
        list = (List<Big_CarFlow_WidgetTend>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        list = new ArrayList<>();
        try {
            Date parse = DateUtil.MONTH_SDF.parse(month);
            //得到此月份的所有天
//            List<String> dayReport = DateUtil.getDayReport(parse);
            List<String> daynNowReport = DateUtil.getNowDayReport(parse, month);
            List<Future<List<Big_CarFlow_WidgetTend>>> futureList = new ArrayList<>();
            //使用多线程去拉取每天的数据.
            ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
            for (String dateStr : daynNowReport) {
                if(es.isShutdown()){
                    es = ExecutorServiceUtils.getFixedThreadPool();
                }
                futureList.add(es.submit(new CarFlowQsTask(vcode, dateStr)));
            }

            for (Future<List<Big_CarFlow_WidgetTend>> listFuture : futureList) {
                try {
                    list.addAll(listFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            es.shutdown();
            /*while(!es.isTerminated()) {
                try {
                    Thread.sleep(100);
                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //list=groupByDataJqTvpmSum(list);

        List<Big_CarFlow_WidgetTend> arrlist = new ArrayList<>();
        for (Big_CarFlow_WidgetTend t : list) {
            t.setTime(t.getTime());
            t.setSumCar(t.getSumCar());
            arrlist.add(t);
        }

        //写入缓存
        if (arrlist.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, arrlist);
        }

        return arrlist != null ? arrlist : new ArrayList<>();
    }


    /***
     *   天
     * 2017-08-21 zf
     * @param vcode
     * @param day
     * @return
     */
    public List<Big_CarFlow_WidgetTend> getByDay(String vcode, String day) {
        String md5key = DigestUtils.md5Hex(vcode);
        List<Big_CarFlow_WidgetTend> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_BIGCARFLOW + "call:day:";
        String hk = "day" + "|" + day + "|" + md5key;

        //读取缓存
        list = (List<Big_CarFlow_WidgetTend>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("startTime", day + " 00:00:00");
        map.put("endTime", day + " 23:59:59");
        map.put("date", day);
        //读取db
        list = bigCarFlowDao.findCarFlowQsByDay(map);

        //写入缓存
        if (list.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, list);
        }

        return list != null ? list : new ArrayList<>();
    }


//    public class CarFlowQsTask implements Callable<List<Big_CarFlow_WidgetTend>> {
//        private Logger logger = Logger.getLogger(getClass());
//
//        private String date;
//        private String vcode;
//
//        public String getDate() {
//            return date;
//        }
//
//        public void setDate(String date) {
//            this.date = date;
//        }
//
//        public String getVcode() {
//            return vcode;
//        }
//
//        public void setVcode(String vcode) {
//            this.vcode = vcode;
//        }
//
//        public CarFlowQsTask(String vcode, String date) {
//            this.date = date;
//            this.vcode = vcode;
//        }
//
//        public CarFlowQsTask() {
//        }
//
//        /**
//         *
//         * @return 返回  天
//         * @throws Exception if unable to compute a result
//         */
//        @Override
//        public List<Big_CarFlow_WidgetTend> call() throws Exception {
//            logger.info(String.format("线程:%s 正在处理[车流趋势]数据 vcode:%s,date:%s", Thread.currentThread().getName(), vcode, date));
//            List<Big_CarFlow_WidgetTend> list =getByDay(vcode, date);
//            return list;
//        }
//    }


}
