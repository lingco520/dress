package com.daqsoft.service.client.ecologicalEnvironmentMonitor.impl;

import com.daqsoft.mapper.client.scenicspotinfo.ScenicSpotInfoDao;
import com.daqsoft.service.client.ecologicalEnvironmentMonitor.EcologicalEnvironmentMonitorService;
import com.daqsoft.service.client.passengerFlowService.JqScenicMsgService;
import com.daqsoft.utils.client.DateTools;
import com.daqsoft.utils.client.HttpRequestUtil;
import com.daqsoft.utils.client.RandomNumberUtils;
import com.daqsoft.vo.client.madeVoBean.BaseCommonVo;
import com.daqsoft.vo.client.madeVoBean.JqScenicAreas;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-28 11:44
 * @Version:
 * @Describe:
 */
@Service
public class EcologicalEnvironmentMonitorServiceImpl implements EcologicalEnvironmentMonitorService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String url = "http://weather.service.geeker.com.cn/weather";

    @Autowired
    JqScenicMsgService jqScenicMsgService;
    @Autowired
    ScenicSpotInfoDao scenicSpotInfoDao;

    /**
     * 获取景区实时天气与未来七天的天气信息
     *
     * @param vcode 景区编码
     * @return
     */
    @Override
    public Map<String, Object> getWeatherForecast(String vcode) {

        List<JqScenicAreas> lists = jqScenicMsgService.getJqScenicRegion(vcode);
        JSONObject jsonObject = null;
        if(lists.size() > 0) {
            String region = lists.get(0).getRegion();
            String respStr = HttpRequestUtil.sendGet(url, "code=" + region);
            jsonObject = JSONObject.fromObject(respStr);
            // 如果没有查询到，对region做处理，查询到了，就原样返回
            if ("0".equals(jsonObject.get("msg"))) {
                // 如果区的region没有查询到天气数据，则用市的region去查询数据
                String cityRegion = lists.get(0).getCityRegion();
                String respCityStr = HttpRequestUtil.sendGet(url, "code=" + cityRegion);
                jsonObject = JSONObject.fromObject(respCityStr);
            }
        }
        //String url = "http://weather.service.geeker.com.cn/weather";
        //暂时使用
//        String str = HttpRequestUtil.sendGet(url, "code=540100");
        //转换为json
//        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        String value = null;
        //创建对象与封装
        value = jsonObject.getString("weatherInfo");

        Map<String, Object> resultMap = new HashMap<>();

        if (!value.equals("[]")) {
            try {
                JSONObject object = JSONObject.fromObject(value);
                //JSONObject object = jsonArray.getJSONObject(0);
                if (object != null) {
                    //获取七天天气预报
                    JSONArray daily_forecast = (JSONArray) object.get("daily_forecast");
                    resultMap.put("dataList", daily_forecast);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }

        return resultMap;
    }

    /**
     * 获取景区气象预警
     *
     * @param vcode
     * @return
     */
    @Override
    public Map<String, Object> getWeatherWarning(String vcode) {

        String urlStr = "https://appapi.daqsoft.com/weatherServer/getAllAlarms";

        //暂时使用
        String str = HttpRequestUtil.sendGet(urlStr, "");
        //转换为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        String value = null;
        //创建对象与封装
        value = jasonObject.getString("weatherInfo");

        JSONArray array = new JSONArray();
        Map<String, Object> resultMap = new HashMap<>();

        if (!value.equals("[]")) {
            try {
                JSONArray array1 = JSONArray.fromObject(value);
                if (array1.size() > 0) {
                    array.add(array1.get(0));
                }
                if (array1.size() > 1) {
                    array.add(array1.get(1));
                }
                resultMap.put("dataList", array);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }

        return resultMap;
    }

    /**
     * 获取整点AQI 实况 过去24小时
     *
     * @param vcode 景区编码
     * @return
     */
    @Override
    public Map<String, Object> getFQILive(String vcode) {
        Map<String, Object> resultMap = new HashMap<>();
        //组装获取24 小时 AQI
        int sum = 0;
        int tem = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,-25);
        List<BaseCommonVo> list = new ArrayList<>();
        for(int j = 1; j <=25;j++){
            calendar.add(Calendar.HOUR,1);
            BaseCommonVo baseCommonVo = new BaseCommonVo();
            baseCommonVo.setName(String.format("%02d",calendar.get(Calendar.HOUR_OF_DAY)));
            tem = RandomNumberUtils.RandomNumber(150, 10);
            sum += tem;
            baseCommonVo.setValue(tem);
            list.add(baseCommonVo);
        }
        resultMap.put("aqiList", list);
        resultMap.put("avg", sum / 24);
        return resultMap;
    }

    /**
     * 获取负氧离子指数
     *
     * @param vcode 景区编码
     * @param time
     * @return
     */
    @Override
    public Map<String, Object> getNegativeOxygenIonIndex(String vcode, String time) {
        Map<String, Object> resultMap = new HashMap<>();
        if("a02dbca3d06409ba7fbf444e0565242d".equals(vcode)){
            resultMap.put("num", 1500);
        }else{
            resultMap.put("num", 20000);
        }
        return resultMap;
    }

    /**
     * 获取景区整点水文监测
     *
     * @param vcode vcode
     * @param time  时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    @Override
    public Map<String, Object> getHydrologicalMonitor(String vcode, String time) {

        /**
         * 暂时模拟数据.
         */
        JSONObject object;
        List<JSONObject> array = new ArrayList<>();
        List<String> scenicNames = scenicSpotInfoDao.getScenicNames(vcode);
        if(scenicNames.size() == 0 || scenicNames.equals(null)) {
            for(int i = 1;i<5; i++ ) {
                scenicNames.add("景点" + i);
            }
        }else {
        }
        for(String name :scenicNames) {
            object = new JSONObject();
            object.put("name", name);
            //水温
            object.put("waterTemperature", RandomNumberUtils.RandomNumber(35, 15));
            //流量
            object.put("waterFlow", RandomNumberUtils.RandomNumber(2000, 500));
            //流速
            object.put("waterFlowVelocity", RandomNumberUtils.RandomNumber(50, 20));
            //水深
            object.put("waterFlowDepth", RandomNumberUtils.RandomNumber(600, 100));
            //水宽
            object.put("waterFlowWidth", RandomNumberUtils.RandomNumber(500, 100));
            array.add(object);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", array);
        return resultMap;
    }

    /**
     * 获取景区雷电监测
     *
     * @param vcode vcode
     * @param time  时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    @Override
    public Map<String, Object> getThunderstormMonitor(String vcode, String time) {

        /**
         * 暂时模拟数据.
         */
        JSONObject object;
        List<JSONObject> array = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            object = new JSONObject();
            object.put("name", "xxxxxx" + i);
            object.put("time", RandomNumberUtils.RandomNumber(24, 0));
            List<JqScenicAreas> scenicList = jqScenicMsgService.getJqScenicList(vcode);
            String longtitude = null;
            String latitude = null;
            if(scenicList.size() > 0 &&scenicList !=null) {
                longtitude = scenicList.get(0).getLongitude();
                latitude = scenicList.get(0).getLatitude();
            }
            //经度
            object.put("longitude", longtitude);
            //纬度
            object.put("latitude", latitude);
            array.add(object);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", array);
        return resultMap;
    }

    /**
     * 获取景区空气湿度 过去24小时
     *
     * @param vcode 景区编码
     * @param time  时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    @Override
    public Map<String, Object> getAirMonitor(String vcode, String time) {
        Map<String, Object> resultMap = new HashMap<>();
        //组装获取24 小时 AQI
        JSONObject object;
        int tem = 0;
        int max = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,-25);
        List<BaseCommonVo> list = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            calendar.add(Calendar.HOUR,1);
            BaseCommonVo baseCommonVo = new BaseCommonVo();
            baseCommonVo.setName(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
            tem = RandomNumberUtils.RandomNumber(100, 1);
            baseCommonVo.setValue(tem);
            max = max > tem ? max : tem;
            list.add(baseCommonVo);
        }

        resultMap.put("dataList", list);
        resultMap.put("max", max);
        return resultMap;
    }

    /**
     * 获取景区风力风向 过去24小时
     *
     * @param vcode 景区代码
     * @param time  时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    @Override
    public Map<String, Object> getWindDirectionMonitor(String vcode, String time) {
        String urlStr = "http://weather.service.geeker.com.cn/directlyInfo";

        List<JqScenicAreas> lists = jqScenicMsgService.getJqScenicRegion(vcode);
        JSONObject jsonObject = null;
        if(lists.size() > 0) {
            String region = lists.get(0).getRegion();
            String respStr = HttpRequestUtil.sendGet(urlStr, "code=" + region);
            jsonObject = JSONObject.fromObject(respStr);
            // 如果没有查询到，对region做处理，查询到了，就原样返回
            if ("0".equals(jsonObject.get("msg"))) {
                // 如果区的region没有查询到天气数据，则用市的region去查询数据
                String cityRegion = lists.get(0).getCityRegion();
                String respCityStr = HttpRequestUtil.sendGet(urlStr, "code=" + cityRegion);
                jsonObject = JSONObject.fromObject(respCityStr);
            }
        }

//        String str = HttpRequestUtil.sendGet(urlStr, "code=510000");
        //转换为json
//        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        String value = null;
        //创建对象与封装
        value = jsonObject.getString("weatherInfo");

        Map<String, Object> resultMap = new HashMap<>();
        if (!value.equals("[]")) {
            try {
                JSONArray jsonArray = JSONArray.fromObject(value);
                JSONObject object = jsonArray.getJSONObject(0);
                if (object != null) {
                    //获取七天天气预报
                    JSONArray daily_forecast = (JSONArray) object.get("hourly_forecast");
                    resultMap.put("dataList", daily_forecast);
                    // 如果是云台山，就特殊处理
                    if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
                        List<Map> resList = new ArrayList<>();
                        List<Map> list = (List<Map>)daily_forecast;
                        for(Map map : list){
                            Map resMap = new HashMap();
                            resMap.put("cond", map.get("cond"));
                            resMap.put("date", map.get("date"));
                            resMap.put("hum", map.get("hum"));
                            resMap.put("pop", map.get("pop"));
                            resMap.put("pres", map.get("pres"));
                            resMap.put("tmp", map.get("tmp"));
                            resMap.put("week", map.get("week"));
                            resMap.put("whether_id", map.get("whether_id"));
                            Map rrmap = new HashMap();
                            Map windMap = (Map)map.get("wind");
                            rrmap.put("deg", windMap.get("deg"));
                            rrmap.put("dir", windMap.get("dir"));
                            rrmap.put("sc", windMap.get("sc"));
                            rrmap.put("spd", new Random().nextInt(2)%(2-1+1) + 1);
                            resMap.put("wind", rrmap);
                            resList.add(resMap);
                        }
                        resultMap.put("dataList", resList);
                    }
                    int max = 0;
                    for (int i = 0; i < daily_forecast.size(); i++) {
                        JSONObject jsonObjects = daily_forecast.getJSONObject(i);
                        int spd = jsonObjects.getJSONObject("wind").getInt("spd");
                        max = max >= spd ? max : spd;
                    }
                    resultMap.put("maxSpd", max);
                }
                // 云台山特殊处理
                if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
                    resultMap.put("maxSpd", 2);
                }
            } catch (Exception e) {
                resultMap.put("dataList", new JSONArray());
                resultMap.put("maxSpd", 0);
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }

        return resultMap;
    }

    /**
     * 获取景区景点天气信息
     *
     * @param vcode
     * @param time  时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    @Override
    public Map<String, Object> getScenicSpotWeather(String vcode, String time) {

        //暂时使用
        String str = HttpRequestUtil.sendGet(url, "code=540100");
        //转换为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        String value = null;
        //创建对象与封装
        value = jasonObject.getString("weatherInfo");

        Map<String, Object> resultMap = new HashMap<>();

        if (!value.equals("[]")) {
            try {
                JSONObject object = JSONObject.fromObject(value);
                //JSONObject object = jsonArray.getJSONObject(0);
                if (object != null) {
                    //获取七天天气预报
                    JSONArray daily_forecast = (JSONArray) object.get("daily_forecast");
                    JSONObject object1 = new JSONObject();
                    JSONArray arr2 = new JSONArray();
                    for (int i = 1; i <= 4; i++) {
                        object1 = new JSONObject();
                        object1.put("name", "景点" + i);
                        object1.put("data", daily_forecast);
                        arr2.add(object1);
                    }
                    resultMap.put("dataList", arr2);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }

        return resultMap;
    }

    /**
     * 获取景区降水量 过去24小时
     *
     * @param vcode 景区代码
     * @return
     */
    @Override
    public Map<String, Object> getAmountOfPrecipitation(String vcode, String time) {
        Map<String, Object> resultMap = new HashMap<>();
        int sum = 0;
        int tem = 0;
        //组装获取24 小时 AQI
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,-25);
        List<BaseCommonVo> list = new ArrayList<>();
        if (vcode.equals("fcb456dac4f41a08d5d12943ae61665f")) {
            for (int i = 1; i <= 25; i++) {
                calendar.add(Calendar.HOUR,1);
                BaseCommonVo baseCommonVo = new BaseCommonVo();
                baseCommonVo.setName(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
                tem = 0;
                sum += tem;
                baseCommonVo.setValue(tem);
                list.add(baseCommonVo);
            }
        } else {
            for (int i = 1; i <= 25; i++) {
                calendar.add(Calendar.HOUR, 1);
                BaseCommonVo baseCommonVo = new BaseCommonVo();
                baseCommonVo.setName(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
                tem = RandomNumberUtils.RandomNumber(10, 0);
                sum += tem;
                baseCommonVo.setValue(tem);
                list.add(baseCommonVo);
            }
        }

        resultMap.put("dataList", list);
        resultMap.put("sum", sum);
        return resultMap;
    }

    /**
     * 获取景区紫外线 过去24小时
     *
     * @param vcode 景区代码
     * @param time  时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    @Override
    public Map<String, Object> getUV(String vcode, String time) {
        Map<String, Object> resultMap = new HashMap<>();
        //组装获取24 小时 紫外线
        List<BaseCommonVo> list = new ArrayList<>();
        int sum = 0;
        int tem = 0;
        //定制的 没数据模拟
        if(vcode.equals("f561aaf6ef0bf14d4208bb46a4ccb3ad")) {
            String now = DateTools.getCurrentHH();
            int nowTime = Integer.valueOf(now);
            if (nowTime < 10) {
                resultMap.put("dataList", list);
                resultMap.put("svg", 0);
            }else {
                if(nowTime >15){
                    nowTime = 15;
                }
                String hours[] = {"10","11","12","13","14","15"};
                for (int i = 0; i <= nowTime - 10; i++) {
                    BaseCommonVo baseCommonVo = new BaseCommonVo();
                    baseCommonVo.setName(hours[i]);
                    tem = RandomNumberUtils.RandomNumber(60 + Integer.parseInt(hours[i]), 10 + Integer.parseInt(hours[i]));
                    sum += tem;
                    baseCommonVo.setValue(tem);
                    list.add(baseCommonVo);
                }
                resultMap.put("dataList", list);
                resultMap.put("svg", sum / (nowTime - 9));
            }

        }else if(vcode.equals("df18633fa6ae3ac8a11e6a6317312336")){
            //启封特殊处理
            String now = DateTools.getCurrentHH();
            int nowTime = Integer.valueOf(now);
            if (nowTime < 10) {
                resultMap.put("dataList", list);
                resultMap.put("svg", 0);
            }else {
                if(nowTime >15){
                    nowTime = 15;
                }
                String hours[] = {"10","11","12","13","14","15"};
                for (int i = 0; i <= nowTime - 10; i++) {
                    BaseCommonVo baseCommonVo = new BaseCommonVo();
                    baseCommonVo.setName(hours[i]);
                    tem = RandomNumberUtils.RandomNumber(30 + Integer.parseInt(hours[i]), 10 + Integer.parseInt(hours[i]));
                    sum += tem;
                    baseCommonVo.setValue(tem);
                    list.add(baseCommonVo);
                }
                resultMap.put("dataList", list);
                resultMap.put("svg", sum / (nowTime - 9));
            }
        }
        else {
            //一样定制的  模拟数据
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, -25);
            for (int i = 1; i <= 25; i++) {
                calendar.add(Calendar.HOUR, 1);
                BaseCommonVo baseCommonVo = new BaseCommonVo();
                baseCommonVo.setName(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
                tem = RandomNumberUtils.RandomNumber(200, 50);
                sum += tem;
                baseCommonVo.setValue(tem);
                list.add(baseCommonVo);
            }
            resultMap.put("dataList", list);
            resultMap.put("svg", sum / 24);
        }
        return resultMap;
    }
}
