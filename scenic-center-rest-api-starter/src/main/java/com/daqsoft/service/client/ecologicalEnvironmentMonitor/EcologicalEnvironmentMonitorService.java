package com.daqsoft.service.client.ecologicalEnvironmentMonitor;

import java.util.Map;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-28 9:28
 * @Version:
 * @Describe: 生态系统监测 service
 */
public interface EcologicalEnvironmentMonitorService {

    /**
     * 获取景区实时天气与未来七天的天气信息
     *
     * @param vcode 景区编码
     * @return
     */
    Map<String, Object> getWeatherForecast(String vcode);


    /**
     * 获取景区气象预警
     *
     * @param vcode
     * @return
     */
    Map<String, Object> getWeatherWarning(String vcode);

    /**
     * 获取整点AQI 实况 过去24小时
     *
     * @param vcode 景区编码
     * @return
     */
    Map<String, Object> getFQILive(String vcode);

    /**
     * 获取负氧离子指数 过去24小时
     *
     * @param vocde 景区编码
     * @param time  时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    Map<String, Object> getNegativeOxygenIonIndex(String vocde, String time);


    /**
     * 获取景区整点水文监测
     * @param vcode vcode
     * @param time 时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    Map<String,Object> getHydrologicalMonitor(String vcode, String time);


    /**
     * 获取景区雷电监测
     * @param vcode vcode
     * @param time 时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    Map<String,Object> getThunderstormMonitor(String vcode, String time);


    /**
     * 获取景区空气湿度 过去24小时
     * @param vcode 景区编码
     * @param time  时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    Map<String,Object> getAirMonitor(String vcode, String time);

    /**
     * 获取景区风力风向 过去24小时
     * @param vcode 景区代码
     * @param time 时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    Map<String,Object> getWindDirectionMonitor(String vcode, String time);


    /**
     * 获取景区景点天气信息
     * @param vcode
     * @param time 时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    Map<String,Object> getScenicSpotWeather(String vcode, String time);


    /**
     * 获取景区降水量 过去24小时
     * @param vcode 景区代码
     * @param time 时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    Map<String,Object> getAmountOfPrecipitation(String vcode, String time);

    /**
     * 获取景区紫外线 过去24小时
     * @param vcode 景区代码
     * @param time 时间 (整点:格式 2017-07-01 09:00)
     * @return
     */
    Map<String,Object> getUV(String vcode, String time);
}
