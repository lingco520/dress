package com.daqsoft.service.client.wifiAnalysisService;


import com.daqsoft.vo.client.mysqlBean.WifiAnalysisVo;
import com.daqsoft.vo.client.mysqlBean.WifiSumVo;

import java.util.List;
import java.util.Map;

/**
 * @author tanggm
 * @description
 * @date 2017-06-29 9:56
 */
public interface WifiAnalysisService {
    /**
     * 查询wifi客流量day-day
     * @param map
     * @return
     */
    List<WifiAnalysisVo> getWifiDays(Map<String, String> map);
    List<WifiAnalysisVo> getWifiDay(Map<String, String> map);

    List<WifiAnalysisVo> getWifiMonth(Map<String, String> map);

    List<WifiAnalysisVo> getWifiYear(Map<String, String> map);

    List<WifiAnalysisVo> getWifiQuarter(Map<String, String> map);

    List<WifiAnalysisVo> getWifiYear2Year(Map<String, String> map);
    List<WifiSumVo> getWifiSum(Map<String, String> map);

    List<String> getCalendarYear(Map<String, String> map);
    List<String> getCalendarMonth(Map<String, String> map);
    List<String> getCalendarDay(Map<String, String> map);
    List<String> getCalendarQuarter(Map<String, String> map);

    /**
     * 推送redis数据，wifi季度
     * @param map
     * @param year
     */
    void getWifiQuarterByYear(String year, Map<String, String> map);

    /**
     * 推送redis数据，wifi月份
     * @param vcode
     * @param year
     * @param month
     */
    void getWifiMonthByYear(String vcode, String year, String month);

    /**
     * wifi 天的数据
     * @param vcode
     * @param startTime
     * @param endTime
     */
    void getWifiByDay(String vcode, String startTime, String endTime);

    /**
     * wifi月份数据
     * @param vcode
     * @param year
     * @param month
     * @param startTime
     * @param endTime
     */
    void getWifiByMonth(String vcode, String year, String month, String startTime, String endTime);

    /**
     * wifi 年的数据
     * @param vcode
     * @param year
     */
    void getWifiByYear(String vcode, String year);

    /**
     * wifi季度数据
     * @param vcode
     * @param year
     */
    void getWifiByQuarter(String vcode, String year);
}
