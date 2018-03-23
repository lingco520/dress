package com.daqsoft.service.client.passengerFlowService.impl;

import com.daqsoft.mapper.client.scenicspotinfo.BigDataPageDao;
import com.daqsoft.service.client.passengerFlowService.BigDataPageService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.vo.client.madeVoBean.*;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-08 09:57.
 * @Description 大数据页业务处理
 */
@Service
public class BigDataPageServiceImpl implements BigDataPageService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BigDataPageDao bigDataPageDao;

    @Autowired
    private RedisTemplate redisTemplate;



    //redis中的key值
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TIME = "time";
    private static final String QUARTER = "quarter";
    private static final String COLON = ":";

    /**
     * 根据传入参数获取对应的客流数据统计
     *
     * @param time  时间 根据type类型决定其格式 如：year：2017
     * @param vcode
     * @param type  类型  year/month/day
     * @return
     */
    @Override
    public List<Big_RealP_Tend> fundAll_date(String time, String vcode, String type) {
        List<Big_RealP_Tend> result = new ArrayList<>();
        if (isNullStr(time) || isNullStr(vcode) || isNullStr(type)) {
            return result;
        }
        String date = "";
        String tableKey = "";
        if (type.equals(MONTH)) {
            date = strToFormatStrMonth(time);
            tableKey = "people:big_people:" + DAY + COLON;
        } else if (type.equals(YEAR)) {
            date = strToFormatStrYear(time);
            tableKey = "people:big_people:" + MONTH + COLON;
        }
        //拼接redis的hashKey
        String key = date + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis(tableKey, key);
        if (resultStr == null) {
            return result;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return result;
        }
        if (YEAR.equals(type)) {
            result = jsonToList(resultJson, null);
        } else if (MONTH.equals(type)) {
            for (Object jsonKey : resultJson.keySet()) {
                Big_RealP_Tend big_realP_tend = new Big_RealP_Tend();
                Object value = resultJson.get(jsonKey);
                //若key值为空，则直接不用往前端返数据
                if (jsonKey == null) {
                    continue;
                }
                big_realP_tend.setTime(jsonKey.toString().replace("日", ""));
                big_realP_tend.setNum(value == null ? "0" : String.valueOf(value));
                result.add(big_realP_tend);
            }
        }
        return result;
    }

    /**
     * 获取大数据人数统计（日）
     *
     * @param time  时间 2017-01-01
     * @param vcode 景区编码
     * @return
     */
    @Override
    public List<Tend> fundAll_day(String time, String vcode) {
        List<Tend> tendList = new LinkedList<Tend>();
        Map<String, String> map = new HashMap();
        //封装where条件
        map.put("time", time);
        map.put("vcode", vcode);
        List<RealPeopleOld> realPeopleList = bigDataPageDao.isFindPopulationHour(map);
        long gt_num = 0;
        for (RealPeopleOld real : realPeopleList) {
            Tend tend = new Tend();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH");
            Date d2 = null;
            try {
                d2 = sdf2.parse(real.getTimesheet());
            } catch (Exception e) {
                e.printStackTrace();
            }
            DateFormat df2 = new SimpleDateFormat("HH");
            tend.setTime(df2.format(d2));
            String percent = Double.valueOf(real.getQuantity()) / Double.valueOf(real.getMaxquantity()) * 100.0D + "";
            tend.setNum(real.getQuantity());
            tend.setPercent(percent);

            gt_num = gt_num + Long.valueOf(real.getQuantity());
            tend.setSum(String.valueOf(gt_num));
            tendList.add(tend);
        }
        return tendList;
    }

    /**
     * 获取大数据人数统计（季度）
     *
     * @param time  时间 2017
     * @param vcode 景区编码
     * @return
     */
    @Override
    public List<Big_RealP_Tend> fundAll_quarter(String time, String vcode) {
        List<Big_RealP_Tend> result = new ArrayList<>();
        if (isNullStr(time) || isNullStr(vcode)) {
            return result;
        }
        String tableKey = "people:big_people:quarter:";
        //拼接redis的hashKey
        String key = time + "年" + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis(tableKey, key);
        if (resultStr == null) {
            return result;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return result;
        }
        for (Object jsonKey : resultJson.keySet()) {
            Big_RealP_Tend big_realP_tend = new Big_RealP_Tend();
            Object value = resultJson.get(jsonKey);
            //若key值为空，则直接不用往前端返数据
            if (jsonKey == null) {
                continue;
            }

            if (jsonKey.toString().contains("一")) {//季度1
                big_realP_tend.setTime("1");
            } else if (jsonKey.toString().contains("二")) {//季度2
                big_realP_tend.setTime("2");
            } else if (jsonKey.toString().contains("三")) {//季度3
                big_realP_tend.setTime("3");
            } else if (jsonKey.toString().contains("四")) {//季度4
                big_realP_tend.setTime("4");
            }

            big_realP_tend.setNum(value == null ? "0" : String.valueOf(value));
            result.add(big_realP_tend);
        }
        return result;
    }

    /**
     * 将json对象转存到list中（大数据）
     *
     * @param resultJson json对象
     * @return
     */
    private List<Big_RealP_Tend> jsonToList(JSONObject resultJson, List<String> list) {
        List<Big_RealP_Tend> ticketIndexSumVoList = new ArrayList<>();
        // 若list不为空，则需要以list进行循环作为key返回给前端，无数据的补0
        if (list == null || list.size() == 0) {
            for (Object jsonKey : resultJson.keySet()) {
                Big_RealP_Tend ticketIndexSumVo = new Big_RealP_Tend();
                Object value = resultJson.get(jsonKey);
                //若key值为空，则直接不用往前端返数据
                if (jsonKey == null) {
                    continue;
                }
                ticketIndexSumVo.setTime(strToFormatStr(jsonKey.toString(), "MM月", "MM"));
                ticketIndexSumVo.setNum(value == null ? "0" : value.toString());
                ticketIndexSumVoList.add(ticketIndexSumVo);
            }
        } else {
            for (String time : list) {
                Big_RealP_Tend ticketIndexSumVo = new Big_RealP_Tend();
                Object value = resultJson.get(time);
                ticketIndexSumVo.setTime(strToFormatStr(time, "MM月", "MM"));
                ticketIndexSumVo.setNum(value == null ? "0" : value.toString());
                ticketIndexSumVoList.add(ticketIndexSumVo);
            }
        }
        return ticketIndexSumVoList;
    }

    /**
     * 获取WiFi数据统计
     *
     * @param vcode
     * @param time  时间 年份2017
     * @return
     */
    @Override
    public List<Big_RealP_Tend> getWifiYear(String vcode, String time) {
        List<Big_RealP_Tend> result = new ArrayList<>();
        if (isNullStr(time) || isNullStr(vcode)) {
            return result;
        }
        String date = strToFormatStrYear(time);
        String tableKey = "wifi:big_wifi:" + MONTH + COLON;
        //拼接redis的hashKey
        String key = date + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis(tableKey, key);
        if (resultStr == null) {
            return result;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return result;
        }
        result = jsonToList(resultJson, null);
        return result;
    }

    /**
     * 获取WiFi按月份进行统计
     *
     * @param vcode 景区编码
     * @param time  时间 2017-01
     * @return
     */
    @Override
    public List<WifiVo> getWifiMonth(String vcode, String time) {
        List<WifiVo> result = new ArrayList();
        if (isNullStr(time) || isNullStr(vcode)) {
            return result;
        }
        String date = strToFormatStrMonth(time);
        String tableKey = "wifi:big_wifi:day:";
        //拼接redis的hashKey
        String key = date + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis(tableKey, key);
        if (resultStr == null) {
            return result;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return result;
        }
        for (Object jsonKey : resultJson.keySet()) {
            WifiVo wifiVo = new WifiVo();
            Object value = resultJson.get(jsonKey);
            //若key值为空，则直接不用往前端返数据
            if (jsonKey == null) {
                continue;
            }
            wifiVo.setTime(jsonKey.toString().replace("日", ""));
            wifiVo.setNum(value == null ? 0 : Integer.valueOf(String.valueOf(value)));
            result.add(wifiVo);
        }
        return result;
    }

    /**
     * 获取WiFi按天进行统计
     *
     * @param vcode 景区编码
     * @param time  时间 2017-01-01
     * @return
     */
    @Override
    public List<WifiVo> getWifiDay(String vcode, String time) {
        List<WifiVo> result = new ArrayList();
        if (isNullStr(time) || isNullStr(vcode)) {
            return result;
        }
        String date = new String();
        String[] strArr = time.split("-");
        //拼接date
        date += strArr[0] + "年" + strArr[1] + "月" + strArr[2] + "日";
        String tableKey = "wifi:wifi:";
        //拼接redis的hashKey
        String key = date + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis(tableKey, key);
        if (resultStr == null) {
            return result;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return result;
        }
        for (Object jsonKey : resultJson.keySet()) {
            WifiVo wifiVo = new WifiVo();
            Object value = resultJson.get(jsonKey);
            //若key值为空，则直接不用往前端返数据
            if (jsonKey == null) {
                continue;
            }
            wifiVo.setTime(jsonKey.toString());
            wifiVo.setNum(value == null ? 0 : Integer.valueOf(String.valueOf(value)));
            result.add(wifiVo);
        }
        return result;
    }

    /**
     * 获取WiFi按季度进行统计
     *
     * @param vcode 景区编码
     * @param time  时间 2017
     * @return
     */
    @Override
    public List<WifiVo> getWifiQuarter(String vcode, String time) {
        List<WifiVo> result = new ArrayList();
        if (isNullStr(time) || isNullStr(vcode)) {
            return result;
        }
        String tableKey = "wifi:big_wifi:quarter:";
        //拼接redis的hashKey
        String key = time + "年" + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis(tableKey, key);
        if (resultStr == null) {
            return result;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return result;
        }
        for (Object jsonKey : resultJson.keySet()) {
            if ("year".equals(jsonKey.toString())) {
                continue;
            }
            WifiVo wifiVo = new WifiVo();
            Object value = resultJson.get(jsonKey);
            //若key值为空，则直接不用往前端返数据
            if (jsonKey == null) {
                continue;
            }

            if (jsonKey.toString().contains("一")) { //季度1
                wifiVo.setTime("1");
            } else if (jsonKey.toString().contains("二")) { //季度2
                wifiVo.setTime("2");
            } else if (jsonKey.toString().contains("三")) { //季度3
                wifiVo.setTime("3");
            } else if (jsonKey.toString().contains("四")) { //季度4
                wifiVo.setTime("4");
            }

            wifiVo.setNum(value == null ? 0 : Integer.valueOf(String.valueOf(value)));
            result.add(wifiVo);
        }
        return result;
    }

    /**
     * 大数据页面。团体与散客对比
     *
     * @param vcode
     * @param date
     * @return
     */
    public Map classIfication(String vcode, String date) {
        Map map = new HashMap<>();
        if (isNullStr(vcode) || isNullStr(date)) {
            return map;
        }
        map.put("date", date);
        map.put("vcode", vcode);

        Map<String, String> map2 = new HashMap<>();
        String Total = bigDataPageDao.classIfication(map);
        String group = bigDataPageDao.tdClass(map);
        if (Total == null || Total == "") {
            Total = "0";
        }
        if (group == null || group == "") {
            group = "0";
        }
        map2.put("typetd", group);
        map2.put("typesk", Total);
        return map2;
    }

    /**
     * 散客变化关系
     *
     * @param vcode
     * @param date
     * @return
     */
    public List<PopulationSeal> populationFit(String vcode, String date) {
        List<PopulationSeal> populationSealList = new ArrayList<>();
        Map map = new HashMap<>();
        if (isNullStr(vcode) || isNullStr(date)) {
            return populationSealList;
        }
        map.put("date", date);
        map.put("vcode", vcode);
        populationSealList = bigDataPageDao.populationFit(map);
        return populationSealList;
    }

    /**
     * 判断字符串是否为空或为空字符串
     *
     * @param str
     * @return 为空返回true
     */
    private boolean isNullStr(String str) {
        return str == null || str.equals("");
    }

    /**
     * 将日期天转化为标准格式
     *
     * @param dateStr 格式：2017-09-09
     * @return 格式：2017年09月09日
     */
    private String strToFormatStrDay(String dateStr) {
        String str1 = "yyyy-MM-dd";
        String str2 = "yyyy年MM月dd日";
        return strToFormatStr(dateStr, str1, str2);
    }

    /**
     * 将日期月转化为标准格式
     *
     * @param dateStr 格式：2017-09
     * @return 格式：2017年09月
     */
    private String strToFormatStrMonth(String dateStr) {
        String str1 = "yyyy-MM";
        String str2 = "yyyy年MM月";
        return strToFormatStr(dateStr, str1, str2);
    }

    /**
     * 将日期年转化为标准格式
     *
     * @param dateStr 格式：2017
     * @return 格式：2017年
     */
    private String strToFormatStrYear(String dateStr) {
        String str1 = "yyyy";
        String str2 = "yyyy年";
        return strToFormatStr(dateStr, str1, str2);
    }

    /**
     * 将时间格式1换成2
     *
     * @param str
     * @param format1
     * @param format2
     * @return 返回format2格式的字符串
     */
    private String strToFormatStr(String str, String format1, String format2) {
        Date date = DateUtil.strToDate(str, format1);
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
        String res = sdf2.format(date);
        //若长度不相等，则说明传入的时间格式不对 例如：传入2017-01-01 format2:yyyy年  返回的结果则为2017年
        //此处作为对其传值格式的验证
        if (res.length() != format2.length()) {
            return "";
        }
        return res;
    }


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
}
