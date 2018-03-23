package com.daqsoft.service.client.ychbhPassenger.Impl;

import com.daqsoft.mapper.client.realpeople.PassengerFlowDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.ychbhPassenger.YCHBHPassengerService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ${file_name}
 * @Author: lyl
 * @Date: 2017/10/24 10:26
 * @Description: TODO 
 * @Comment： 
 * @see 
 * @Version:  
 * @since JDK 1.8
 * @Warning:
 */
@Service
public class YCHBHPassengerServiceImpl implements YCHBHPassengerService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PassengerFlowDao passengerFlowDao;

    private String vcode = "fcb456dac4f41a08d5d12943ae61665f";

    @Override
    public void getPassengerByScenic() {
        String dayKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenicName:";
        String dayhk = vcode;
        List<Map> list = passengerFlowDao.getPassengerByScenic(vcode);
        RedisCache.putHash(redisTemplate,dayKey,dayhk,list);
    }



    @Override
    public void getPassengerFlowScenicByDay(String time,String year,String month) {
        String scenicDayKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenicName:";
        String scenicDayhk = vcode;
        Object scenicDayObject= RedisCache.getHash(redisTemplate,scenicDayKey, scenicDayhk);
        List<Map> scenic = (List<Map>) scenicDayObject;

//        List<String> lists = DateUtil.getSubsectionDateList("2017-12-07","2018-02-07");
//        for(int j = 0; j<lists.size(); j++) {
        Map map = new HashMap();
        map.put("vcode", vcode);
//            map.put("startTime", lists.get(j) + " 00:00:00");
//            map.put("endTime", lists.get(j) + " 23:59:59");
        map.put("startTime", time + " 00:00:00");
        map.put("endTime", time + " 23:59:59");
        for (Map mapScenic : scenic) {
            map.put("scenic", mapScenic.get("name") + "");
            List<Map<String, Object>> list = passengerFlowDao.getPassengerFlowScenicByDay(map);
            if (list.size() > 0 && list != null) {
                String k = RedisKey.MOUDEL_PASSENGERFLOW + "scenicTime:" + "day:";
                for (int i = 0; i < list.size(); i++) {
                    String hk = mapScenic.get("name") + "|" + list.get(i).get("time") + "|" + vcode;
                    RedisCache.putHash(redisTemplate, k, hk, list.get(i));
                }
            }
        }
//        }
    }

    @Override
    public void getPassengerFlowScenicByHour(String time) {
        String scenicDayKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicName:";
        String scenicDayhk = vcode;
        Object scenicDayObject = RedisCache.getHash(redisTemplate, scenicDayKey, scenicDayhk);
        List<Map> scenic = (List<Map>) scenicDayObject;
        for (Map mapScenic : scenic) {
            //小时统计主KEY
            String hoursKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicTime:" + "hour:";
            Map map = new HashMap();
            map.put("time", time);
            map.put("vcode", vcode);
            map.put("scenic",mapScenic.get("name") + "");
            List<Map<String, Object>> list = passengerFlowDao.getPassengerFlowScenicByHour(map);
            String hourHk = mapScenic.get("name") + "" + "|" + time + "|" + vcode;
            if (list.size() > 0 && list != null) {
                RedisCache.putHash(redisTemplate, hoursKey, hourHk, list);
            }
        }
    }

    @Override
    public void getPassengerFlowScenicByMonth(String month, String startTime, String endTime) {

        String scenicDayKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicName:";
        String scenicDayhk = vcode;
        Object scenicDayObject = RedisCache.getHash(redisTemplate, scenicDayKey, scenicDayhk);
        List<Map> scenic = (List<Map>) scenicDayObject;

        for (Map mapScenic : scenic) {
            //获取当前日期时间段的所有日期，查询每日缓存，进行累加得出月缓存
            List<String> list = DateUtil.getSubsectionDateList(startTime, endTime);
            int result = 0;
            if (list.size() > 0 && list != null) {
                //日统计的主KEY
                String datKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicTime:" + "day:";
                for (int i = 0; i < list.size(); i++) {
                    //日统计每天实时人数的小KEY
                    String hk = mapScenic.get("name") + "|" + list.get(i) + "|" + vcode;
                    Object obj = RedisCache.getHash(redisTemplate, datKey, hk);
                    if (obj != null) {
                        Map map = (Map) obj;
                        result += Integer.parseInt(map.get("num") + "");
                    }
                }

                //月统计主KEY
                String monthKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicTime:" + "month:";
                //月统计小KEY
                String montHk = mapScenic.get("name") + "|" + month + "|" + vcode;
                Map resultMap = new HashMap();
                resultMap.put("num", result);
                RedisCache.putHash(redisTemplate, monthKey, montHk, resultMap);
            }
        }
    }

    @Override
    public void getPassengerFlowScenicByQuarter(String year) {
        String scenicDayKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicName:";
        String scenicDayhk = vcode;
        Object scenicDayObject = RedisCache.getHash(redisTemplate, scenicDayKey, scenicDayhk);
        List<Map> scenic = (List<Map>) scenicDayObject;

        for (Map mapScenic : scenic) {

            //季度统计主KEY
            String monthKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicTime:" + "month:";
            List<Map> quarterMonth = DateUtil.getQuarterMonthByYear(year);
            List<Map> resList = new ArrayList<>();
            for (Map qm : quarterMonth) {
                Map resMap = new HashMap();
                Integer quarter = Integer.valueOf(qm.get("quarter") + "");
                int result = 0;
                //月份统计小KEY
                for (String month : (List<String>) qm.get("months")) {
                    String monthHk = mapScenic.get("name") + "|" + year + "-" + month + "|" + vcode;
                    Object obj = RedisCache.getHash(redisTemplate, monthKey, monthHk);
                    if (obj != null) {
                        Map map = (Map) obj;
                        if (map.get("num") != null && !"".equals(map.get("num"))) {
                            result += Integer.parseInt(map.get("num") + "");
                        }
                    }
                }
                resMap.put("time", quarter);
                resMap.put("num", result);
                resList.add(resMap);
            }

            //年份统计主KEY
            String quarterKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicTime:" + "quarter:";
            String yearHk = mapScenic.get("name") + "|" + year + "|" + vcode;
            RedisCache.putHash(redisTemplate, quarterKey, yearHk, resList);
        }
    }

    @Override
    public void getPassengerFlowScenicByYear(String year) {
        String scenicDayKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicName:";
        String scenicDayhk = vcode;
        Object scenicDayObject = RedisCache.getHash(redisTemplate, scenicDayKey, scenicDayhk);
        List<Map> scenic = (List<Map>) scenicDayObject;

        for (Map mapScenic : scenic) {
            //月份统计主KEY
            String monthKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicTime:" + "month:";
            int result = 0;
            String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
            for (int i = 0; i < month.length; i++) {
                //月份统计小KEY
                String monthHk = mapScenic.get("name") + "|" + year + "-" + month[i] + "|" + vcode;
                Object obj = RedisCache.getHash(redisTemplate, monthKey, monthHk);
                if (obj != null) {
                    Map map = (Map) obj;
                    result += Integer.parseInt(map.get("num").toString());
                }
            }
            //年份统计主KEY
            String yearKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicTime:" + "year:";
            String yearHk = mapScenic.get("name") + "|" + year + "|" + vcode;
            Map<String, Object> resultMap = new HashMap();
            resultMap.put("num", result);
            RedisCache.putHash(redisTemplate, yearKey, yearHk, resultMap);
        }
    }
}