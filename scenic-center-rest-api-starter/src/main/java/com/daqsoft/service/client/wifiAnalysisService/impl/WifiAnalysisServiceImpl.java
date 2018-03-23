package com.daqsoft.service.client.wifiAnalysisService.impl;

import com.daqsoft.mapper.client.wifiAnalysis.WifiAnalysisMapper;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.wifiAnalysisService.WifiAnalysisService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.vo.client.mysqlBean.WifiAnalysisVo;
import com.daqsoft.vo.client.mysqlBean.WifiSumVo;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanggm
 * @version V3.1
 * @description
 * @date 2017-06-29 9:58
 */
@Service
public class WifiAnalysisServiceImpl implements WifiAnalysisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private WifiAnalysisMapper wifiAnalysisMapper;
    @Override
    public List<WifiAnalysisVo> getWifiDays(Map<String, String> map) {
        return wifiAnalysisMapper.getWifiDays(map);
    }
    // redis中的key值
    private static final String TABLE_NAME = "wifi:";
    private static final String BIG_TABLENAME = "big_wifi:";
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TIME = "time";
    private static final String QUARTER = "quarter";
    private static final String COLON = ":";
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月");
    SimpleDateFormat sdf11 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf22 = new SimpleDateFormat("yyyy年MM月dd日");

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<WifiAnalysisVo> getWifiDay(Map<String, String> map) {
        List<WifiAnalysisVo> oldWifiVo = new ArrayList<>();
        try {
            oldWifiVo = wifiAnalysisMapper.getWifiHoursByDay(map);
            if(oldWifiVo == null){
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldWifiVo;
    }

    @Override
    public List<WifiAnalysisVo> getWifiMonth(Map<String, String> map) {
        List<WifiAnalysisVo> oldWifiVo = new ArrayList<>();
        try {
            String oldtime = map.get("time");
            String time = sdf2.format(sdf1.parse(oldtime));
            String key = time + DigestUtils.md5Hex(map.get("vcode"));
            Object resultStr = getDataFromRedis(TABLE_NAME + BIG_TABLENAME+DAY+COLON, key);
            if (resultStr == null) {
                return oldWifiVo;
            }
            JSONObject resultJson = JSONObject.fromObject(resultStr);
            if (resultJson == null) {
                return oldWifiVo;
            }
            for (Object jsonKey : resultJson.keySet()) {
                WifiAnalysisVo dldWifiVo = new WifiAnalysisVo();
                Object value = resultJson.get(jsonKey);
                // 若key值为空，则直接不用往前端返数据
                if (jsonKey == null) {
                    continue;
                }
                dldWifiVo.setTime(oldtime+"-"+jsonKey.toString().replace("日", ""));
                dldWifiVo.setNum(value == null ? 0l : Long.parseLong(value+""));
                oldWifiVo.add(dldWifiVo);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return oldWifiVo;
    }

    @Override
    public List<WifiAnalysisVo> getWifiYear(Map<String, String> map) {
        List<WifiAnalysisVo> oldWifiVo = new ArrayList<>();
        String key = map.get("time")+"年" + DigestUtils.md5Hex(map.get("vcode"));
        Object resultStr = getDataFromRedis(TABLE_NAME + BIG_TABLENAME+MONTH+COLON, key);
        if (resultStr == null) {
            return oldWifiVo;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return oldWifiVo;
        }
        List<String> list = DateUtil.getTimeList("month");
        oldWifiVo = jsonToListMonth(resultJson, list, map.get("time"));
        return oldWifiVo;
    }

    @Override
    public List<WifiAnalysisVo> getWifiYear2Year(Map<String, String> map) {
        return wifiAnalysisMapper.year2year(map);
    }




    @Override
    public List<WifiAnalysisVo> getWifiQuarter(Map<String, String> map) {
        List<WifiAnalysisVo> resList = new ArrayList<>();
        List<WifiAnalysisVo> oldWifiVo = new ArrayList<>();
        String key = map.get("time")+"年" + DigestUtils.md5Hex(map.get("vcode"));
        Object resultStr = getDataFromRedis(TABLE_NAME + BIG_TABLENAME+QUARTER+COLON, key);
        if (resultStr == null) {
            return oldWifiVo;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return oldWifiVo;
        }
        List<String> list = DateUtil.getTimeList(null);
        oldWifiVo = jsonToList(resultJson, list);
        String[] quarters = {"第一季度", "第二季度", "第三季度", "第四季度"};
        for(int k = 0; k <quarters.length; k++){
            WifiAnalysisVo wifiAnalysisVo = new WifiAnalysisVo();
            long num = 0;
            for(WifiAnalysisVo wifiVo : oldWifiVo){
                if(quarters[k].equals(wifiVo.getTime())){
                    num = wifiVo.getNum();
                    break;
                }
            }
            wifiAnalysisVo.setNum(num);
            wifiAnalysisVo.setTime(quarters[k]);
            resList.add(wifiAnalysisVo);
        }

        return resList;
    }


    /**
     * 先排序,
     * @param map
     * @return
     */
    @Override
    public List<WifiSumVo> getWifiSum(Map<String, String> map) {
        return null;
//	        List<WifiHour> days = dao.getOneHour(map);
//	        List<WifiSumVo> list = new LinkedList<>();
//	        long sum=0;
//	        for (WifiHour day : days) {
//	            WifiSumVo vo = new WifiSumVo();
//	            vo.setTime(day.getTime().substring(11,13));
//	            sum=day.getNum()+sum;
//	            vo.setSum(sum);
//	            list.add(vo);
//	        }
//
//	        return list;
    }

    @Override
    public List<String> getCalendarYear(Map<String, String> map) {
        return wifiAnalysisMapper.getCalendarYear(map);
    }

    @Override
    public List<String> getCalendarMonth(Map<String, String> map) {
        return wifiAnalysisMapper.getCalendarMonth(map);
    }

    @Override
    public List<String> getCalendarDay(Map<String, String> map) {
        return wifiAnalysisMapper.getCalendarDay(map);
    }

    @Override
    public List<String> getCalendarQuarter(Map<String, String> map) {
        return wifiAnalysisMapper.getCalendarQuarter(map);
    }

    @Override
    public void getWifiQuarterByYear(String year, Map<String, String> parMap) {
        List<Map> wifiQuartersList = wifiAnalysisMapper.getWifiQuarterByYear(parMap);
        String md5key = DigestUtils.md5Hex(parMap.get("vcode"));
        String h = "wifi:wifi:quarter:getWifiByQuarter:";
        RedisCache.putHash(redisTemplate, h, year+"|"+md5key, wifiQuartersList);
    }

    @Override
    public void getWifiMonthByYear(String vcode, String year, String month) {
        String startTime = year+"-"+month+"-01";
        int days = DateUtil.getDaysByYearMonth(Integer.valueOf(year), Integer.valueOf(month));
        String endTime = year+"-"+month+"-"+days;

    }

    @Override
    public void getWifiByDay(String vcode, String startTime, String endTime) {
        String md5key = DigestUtils.md5Hex(vcode);
        String h = "wifi:wifi:day:getWifiByDay:";
        List<String> timeList = DateUtil.getSubsectionDateList(startTime,endTime);
        for (int i = 0; i <timeList.size() ; i++) {
            String hk = timeList.get(i) + "|" + md5key;
                Map parMap = new HashMap();
                parMap.put("startTime", timeList.get(i)+" 00:00:00");
                parMap.put("endTime", timeList.get(i)+" 23:59:59");
                parMap.put("vcode", vcode);
                Integer count = wifiAnalysisMapper.getWifiByDay(parMap);
                Map resultMap = new HashMap();
                resultMap.put("num", count);
            RedisCache.putHash(redisTemplate, h, hk, resultMap);
        }
    }

    @Override
    public void getWifiByMonth(String vcode, String year, String month, String startTime, String endTime) {
        String md5Vcode = DigestUtils.md5Hex(vcode);
        //获取当前日期时间段的所有日期，查询每日缓存，进行累加得出月缓存
        List<String> list = DateUtil.getSubsectionDateList(startTime,endTime);
            Integer monthCount =0;
            if(list.size()>0 && list!=null){
                //日统计的主KEY
                String datKey = "wifi:wifi:day:getWifiByDay:";
                for (int i = 0; i <list.size() ; i++) {
                    String hk = list.get(i) + "|" + md5Vcode;
                    Map dayMap = new HashMap();
                    dayMap = (Map) RedisCache.getHash(redisTemplate, datKey, hk);
                    if (dayMap != null && dayMap.get("num") != null) {
                        monthCount += Integer.valueOf(dayMap.get("num")+"");
                    }
                }
            }
            Map resultMap = new HashMap();
            resultMap.put("num", monthCount);
        //月统计主KEY
        String monthKey = "wifi:wifi:month:getWifiByMonth:";
        //月统计小KEY
        String montHk = year + "-" + month+"|"+md5Vcode;
        RedisCache.putHash(redisTemplate,monthKey,montHk,resultMap);
    }

    @Override
    public void getWifiByYear(String vcode, String year) {
        String md5Vcode = DigestUtils.md5Hex(vcode);
        String[] month = {"01","02","03","04","05","06","07","08","09","10","11","12"};
            String monthKey = "wifi:wifi:month:getWifiByMonth:";
            int yearCount =0;
            for (int i=0;i<month.length;i++){
                //日统计每天实时人数的小KEY
                String hk = year + "-" + month[i] + "|" + md5Vcode;
                Map dayMap = new HashMap();
                dayMap = (Map) RedisCache.getHash(redisTemplate, monthKey, hk);
                if (dayMap != null && dayMap.get("num") != null) {
                    yearCount += Integer.valueOf(dayMap.get("num")+"");
                }
            }
            Map resultMap = new HashMap();
            resultMap.put("num", yearCount);
        //年统计主KEY
        String yearKey = "wifi:wifi:year:getWifiByYear:";
        //年统计小KEY
        String yearHk = year +"|"+md5Vcode;
        RedisCache.putHash(redisTemplate,yearKey,yearHk,resultMap);
    }

    @Override
    public void getWifiByQuarter(String vcode, String year) {
        List<Map> quarterMonthList = DateUtil.getQuarterMonthByYear(year);
        String md5Vcode = DigestUtils.md5Hex(vcode);
        List<Map> resList = new ArrayList<>();
        for(Map quarter : quarterMonthList){
                List<String> months = (List<String>) quarter.get("months");
                String monthKey = "wifi:wifi:month:getWifiByMonth:";
                int quarterCount =0;
                for (int i=0;i<months.size();i++){
                    //日统计每天实时人数的小KEY
                    String hk = year + "-" + months.get(i) + "|" + md5Vcode;
                    Map dayMap = new HashMap();
                    dayMap = (Map) RedisCache.getHash(redisTemplate, monthKey, hk);
                    if (dayMap != null && dayMap.get("num") != null) {
                        quarterCount += Integer.valueOf(dayMap.get("num")+"");
                    }
                }
                Map resultMap = new HashMap();
                resultMap.put("num", quarterCount);
                resultMap.put("time", quarter.get("quarter"));
                resList.add(resultMap);
        }
        //年统计主KEY
        String yearKey = "wifi:wifi:quarter:getWifiByQuarter:";
        //年统计小KEY
        String yearHk = year +"|"+md5Vcode;
        RedisCache.putHash(redisTemplate,yearKey,yearHk,resList);
    }
    /**
     * 根据表名和key值获取对应的redis数据
     *
     * @param table
     *            redis的key
     * @param key
     *            获取redis数据的hashKey
     * @return
     */
    private Object getDataFromRedis(String table, String key) {
        HashOperations hash = stringRedisTemplate.opsForHash();
        Object str = hash.get(table, key);
        System.out.println(table);
        System.out.println(key);
        return str;
    }

    /**
     * 将json对象转存到list中（大数据）
     *
     * @param resultJson
     *            json对象
     * @return
     */
    private List<WifiAnalysisVo> jsonToList(JSONObject resultJson, List<String> list) {
        List<WifiAnalysisVo> OldWifiVoList = new ArrayList<>();
        // 若list不为空，则需要以list进行循环作为key返回给前端，无数据的补0
        if (list == null || list.size() == 0) {
            for (Object jsonKey : resultJson.keySet()) {
                WifiAnalysisVo dldWifiVo = new WifiAnalysisVo();
                Object value = resultJson.get(jsonKey);
                // 若key值为空，则直接不用往前端返数据
                if (jsonKey == null) {
                    continue;
                }
                dldWifiVo.setTime(jsonKey.toString());
                dldWifiVo.setNum(value == null ? 0l : Long.parseLong(value+""));
                OldWifiVoList.add(dldWifiVo);
            }
        } else {
            for (String time : list) {
                WifiAnalysisVo dldWifiVo = new WifiAnalysisVo();
                Object value = resultJson.get(time);
                dldWifiVo.setTime(time);
                dldWifiVo.setNum(value == null ? 0l : Long.parseLong(value+""));
                OldWifiVoList.add(dldWifiVo);
            }
        }
        return OldWifiVoList;
    }
    /**
     * 将json对象转存到list中（大数据）
     *
     * @param resultJson
     *            json对象
     * @return
     */
    private List<WifiAnalysisVo> jsonToListMonth(JSONObject resultJson, List<String> list, String year) {
        List<WifiAnalysisVo> OldWifiVoList = new ArrayList<>();
        // 若list不为空，则需要以list进行循环作为key返回给前端，无数据的补0
        if (list == null || list.size() == 0) {
            for (Object jsonKey : resultJson.keySet()) {
                WifiAnalysisVo dldWifiVo = new WifiAnalysisVo();
                Object value = resultJson.get(jsonKey);
                // 若key值为空，则直接不用往前端返数据
                if (jsonKey == null) {
                    continue;
                }
                dldWifiVo.setTime(year+"-"+jsonKey.toString().replace("月", ""));
                dldWifiVo.setNum(value == null ? 0l : Long.parseLong(value+""));
                OldWifiVoList.add(dldWifiVo);
            }
        } else {
            for (String time : list) {
                WifiAnalysisVo dldWifiVo = new WifiAnalysisVo();
                Object value = resultJson.get(time);
                dldWifiVo.setTime(year+"-"+time.replace("月",""));
                dldWifiVo.setNum(value == null ? 0l : Long.parseLong(value+""));
                OldWifiVoList.add(dldWifiVo);
            }
        }
        return OldWifiVoList;
    }
}
