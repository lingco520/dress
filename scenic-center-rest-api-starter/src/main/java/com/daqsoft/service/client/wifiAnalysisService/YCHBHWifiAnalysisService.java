package com.daqsoft.service.client.wifiAnalysisService;


/**
 * @author tanggm
 * @description
 * @date 2017-06-29 9:56
 */
public interface YCHBHWifiAnalysisService {
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
