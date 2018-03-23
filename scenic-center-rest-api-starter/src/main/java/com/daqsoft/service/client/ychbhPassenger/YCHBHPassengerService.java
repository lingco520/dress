package com.daqsoft.service.client.ychbhPassenger;

/**
 * @Title: 银川花博会人流service
 * @Author: lyl
 * @Date: 2017/10/24 10:25
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
public interface YCHBHPassengerService {


    /**
     * 景点存储
     *
     */
    void getPassengerByScenic();


    /**
     * 获取景区当天的实时人数 推送redis
     * @param time
     * @param year
     * @param month
     * @return
     */
    void  getPassengerFlowScenicByDay(String time, String year, String month);


    /**
     * 按小时获取景区数据
     * @param time
     */
    void getPassengerFlowScenicByHour(String time);

    /**
     * 按月获取景区数据
     *
     * @param month
     * @param startTime
     * @param endTime
     */
    void getPassengerFlowScenicByMonth(String month, String startTime, String endTime);

    /**
     * 按季度获取景区数据
     *
     * @param year
     */
    void getPassengerFlowScenicByQuarter(String year);

    /**
     * 按年获取景区数据
     *
     * @param year
     */
    void getPassengerFlowScenicByYear(String year);

}
