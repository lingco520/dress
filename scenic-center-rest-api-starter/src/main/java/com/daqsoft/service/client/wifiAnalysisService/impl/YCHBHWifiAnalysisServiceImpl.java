/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.client.wifiAnalysisService.impl;

import com.daqsoft.mapper.client.wifiAnalysis.YCHBHWifiAnalysisMapper;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.wifiAnalysisService.YCHBHWifiAnalysisService;
import com.daqsoft.utils.client.DateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: YCHBHWifiAnalysisServiceImpl
 * @Author: tanggm
 * @Date: 2017/10/19 17:26
 * @Description: TODO
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Service
public class YCHBHWifiAnalysisServiceImpl implements YCHBHWifiAnalysisService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private YCHBHWifiAnalysisMapper ychbhWifiAnalysisMapper;

    @Override
    public void getWifiByDay(String vcode, String startTime, String endTime) {
        String md5key = DigestUtils.md5Hex(vcode);
        String h = "wifi:ychbh_wifi:day:getWifiByDay:";
        List<String> timeList = DateUtil.getSubsectionDateList(startTime,endTime);
        for (int i = 0; i <timeList.size() ; i++) {
            String hk = timeList.get(i) + "|" + md5key;
            Map parMap = new HashMap();
            parMap.put("startTime", timeList.get(i)+" 00:00:00");
            parMap.put("endTime", timeList.get(i)+" 23:59:59");
            parMap.put("vcode", vcode);
            List<Map> wifiDayList = ychbhWifiAnalysisMapper.getWifiByDay(parMap);
            RedisCache.putHash(redisTemplate, h, hk, wifiDayList);
        }
    }

    @Override
    public void getWifiByMonth(String vcode, String year, String month, String startTime, String endTime) {
        String md5Vcode = DigestUtils.md5Hex(vcode);
        //获取当前日期时间段的所有日期，查询每日缓存，进行累加得出月缓存
        List<String> list = DateUtil.getSubsectionDateList(startTime,endTime);
        // 查询景区列表
        List<String> apNameList = ychbhWifiAnalysisMapper.getYCHBHApNameList(vcode);
        List<Map> wifiMonthList = new ArrayList<>();
        for(String apName : apNameList){
            Map apNameWifiMap = new HashMap();
            Integer monthCount =0;
            if(list.size()>0 && list!=null){
                //日统计的主KEY
                String datKey = "wifi:ychbh_wifi:day:getWifiByDay:";
                for (int i = 0; i <list.size() ; i++) {
                    String hk = list.get(i) + "|" + md5Vcode;
                    List<Map> wifiDayList = (List<Map>) RedisCache.getHash(redisTemplate, datKey, hk);
                    if(wifiDayList != null && wifiDayList.size() > 0){
                        for(Map dayMap : wifiDayList){
                            if(apName.equals(dayMap.get("apName"))){
                                monthCount += Integer.valueOf(dayMap.get("num")+"");
                                // 某一天的的数据里面，只会存一次景点的数量，找到就跳出循环
                                break;
                            }
                        }
                    }
                }
            }
            apNameWifiMap.put("num", monthCount);
            apNameWifiMap.put("apName", apName);
            wifiMonthList.add(apNameWifiMap);
        }
        //月统计主KEY
        String monthKey = "wifi:ychbh_wifi:month:getWifiByMonth:";
        //月统计小KEY
        String montHk = year + "-" + month+"|"+md5Vcode;
        RedisCache.putHash(redisTemplate,monthKey,montHk,wifiMonthList);
    }

    @Override
    public void getWifiByYear(String vcode, String year) {
        String md5Vcode = DigestUtils.md5Hex(vcode);
        String[] month = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        String monthKey = "wifi:ychbh_wifi:month:getWifiByMonth:";
        // 查询景区列表
        List<String> apNameList = ychbhWifiAnalysisMapper.getYCHBHApNameList(vcode);
        List<Map> yearWifiList = new ArrayList<>();
        for(String apName : apNameList){
            Map apNameMap = new HashMap();
            int yearCount =0;
            for (int i=0;i<month.length;i++){
                //日统计每天实时人数的小KEY
                String hk = year + "-" + month[i] + "|" + md5Vcode;
                List<Map> monthMapList = (List<Map>) RedisCache.getHash(redisTemplate, monthKey, hk);
                if(monthMapList != null && monthMapList.size() > 0){
                    for (Map monthMap : monthMapList){
                        if(apName.equals(monthMap.get("apName"))){
                            yearCount += Integer.valueOf(monthMap.get("num")+"");
                            // 某一天的的数据里面，只会存一次景点的数量，找到就跳出循环
                            break;
                        }
                    }
                }
            }
            apNameMap.put("num", yearCount);
            apNameMap.put("apName", apName);
            yearWifiList.add(apNameMap);
        }
        //年统计主KEY
        String yearKey = "wifi:ychbh_wifi:year:getWifiByYear:";
        //年统计小KEY
        String yearHk = year +"|"+md5Vcode;
        RedisCache.putHash(redisTemplate,yearKey,yearHk,yearWifiList);
    }

    @Override
    public void getWifiByQuarter(String vcode, String year) {
        List<Map> quarterMonthList = DateUtil.getQuarterMonthByYear(year);
        String md5Vcode = DigestUtils.md5Hex(vcode);
        // 查询景区列表
        List<String> apNameList = ychbhWifiAnalysisMapper.getYCHBHApNameList(vcode);
        List<Map> wifiQuarterList = new ArrayList<>();
        for(String apName : apNameList){
            Map apNameWifiMap = new HashMap();
            List<Map> resList = new ArrayList<>();
            for(Map quarter : quarterMonthList){
                List<String> months = (List<String>) quarter.get("months");
                String monthKey = "wifi:ychbh_wifi:month:getWifiByMonth:";
                int quarterCount =0;
                for (int i=0;i<months.size();i++){
                    //日统计每天实时人数的小KEY
                    String hk = year + "-" + months.get(i) + "|" + md5Vcode;
                    List<Map> wifiMonthList = (List<Map>) RedisCache.getHash(redisTemplate, monthKey, hk);
                    if(wifiMonthList != null && wifiMonthList.size() > 0){
                        for(Map monthMap : wifiMonthList){
                            if(apName.equals(monthMap.get("apName"))){
                                quarterCount += Integer.valueOf(monthMap.get("num")+"");
                                // 某一天的的数据里面，只会存一次景点的数量，找到就跳出循环
                                break;
                            }
                        }
                    }
                }
                Map resultMap = new HashMap();
                resultMap.put("num", quarterCount);
                resultMap.put("time", quarter.get("quarter"));
                resList.add(resultMap);
            }
            apNameWifiMap.put("apName", apName);
            apNameWifiMap.put("nums", resList);
            wifiQuarterList.add(apNameWifiMap);
        }

        //年统计主KEY
        String yearKey = "wifi:ychbh_wifi:quarter:getWifiByQuarter:";
        //年统计小KEY
        String yearHk = year +"|"+md5Vcode;
        RedisCache.putHash(redisTemplate,yearKey,yearHk,wifiQuarterList);
    }
}
