package com.daqsoft.service.client.forecastwarning;


import com.daqsoft.vo.client.madeVoBean.ParkingNewVo;
import com.daqsoft.vo.client.madeVoBean.ParkingVo;
import com.daqsoft.vo.client.madeVoBean.SpotsWifiVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author tanggm
 * @description
 * @date 2017-06-23 14:42
 */
public interface ScenicMonitorService{
    // 查询实时人数，根据日期 2017-06-21
    Object getPeopleRealByDate(Map map);
    // 实时旅行团人数
    int getToursByDate(String year, String vcode);
    // 查询景区实时票务数据
    BigDecimal getTicketMoneyByDate(Map map);
    // 查询年累计游客数
    Object getYearTotalPeople(String year, String vcode);
    /**
     * 景区景点实时人数
     * @param vcode
     * @param date
     * @return
     */
    List actualPassenger(String vcode, String date);

    /**
     * 景区实时游客量
     * @param date
     * @param mdfVcode
     * @return
     */
    Map<String,Object> scenicPassenger(String date, String mdfVcode);

    /**
     * 景区投诉
     * @param nowyear
     * @param mdfVcode
     * @return
     */
    Map<String,Object> complaints(String nowyear, String mdfVcode, String vcode);

    /**
     * 查询景区停车场列表
     * @param nowdate
     * @param mdfVcode
     * @return
     */
    List parkingList(String nowdate, String mdfVcode) throws Exception;

    /**
     * 景区舒适度
     * @param vcode
     * @return
     */
    Map<String,Object> spotCrowList(String vcode, String mdfvcode, String year) throws Exception;

    /**
     * 车载系统情况
     * @param date
     * @param vcode
     * @return
     */
    Map<String,Object> crowdStatus(String date, String vcode);

    /**
     * 车载系统情况
     * @param date
     * @param vcode
     * @return
     */
    Map<String,Object> carStatusHQG(String date, String vcode);


    /**
     * 设备运行情况
     * @param date
     * @param vcode
     * @return
     */
    Map<String, Object> deviceStatus(String date, String vcode);

    /**
     * 监控运行情况
     *
     * @param vcode
     * @return
     */
    Map monitorStatus(String vcode);

    /**
     * 信息发布情况
     * @param date
     * @param vcode
     * @return
     */
    Map<String, Object> getInformation(String date, String vcode) throws Exception;

    /**
     * 获取舒适度名称
     *
     * @param map
     * @return
     */
    Map<String, Object> getComfortInfo(Map map);


    /**
     * 人流热力图
     * @param parMap
     * @return
     */
    List<SpotsWifiVo> getSpotsWifiList(Map<String, Object> parMap);

    List<Map> getScenicNamesList(String vcode);

    ParkingVo getParkingInfo(Map parMap);

    ParkingNewVo getParkingNewInfo(Map map);

    Integer getParkingCount(Map parMap);

    Map getScenicParkingCount(String vcode, String sttime, String endtime);

    /**
     * 停车场天的数据redis更新
     * @param vcode
     * @param startTime
     * @param endTime
     */
    void getParkingByThisHors(String vcode, String startTime, String endTime);
    /**
     * 停车场月的数据redis更新
     * @param vcode
     * @param startTime
     * @param endTime
     */
    void getParkingByMonth(String vcode, String year, String month, String startTime, String endTime);
    /**
     * 停车场年的数据redis更新
     * @param vcode
     */
    void getParkingByYear(String vcode, String year);

    /**
     * 线下票务数量
     * @param mdfVcode 加密vcode
     * @param date 日期
     * @return
     */
    Object getRealPeopleByTicket(String mdfVcode, String date);

    /**
     * 景点实时人数-小时统计 历史数据补偿方法
     * @param vcode
     * @param startTime
     * @param endTime
     */
    void getScenicSpotsDataAnalysisTimeAllHistory(String vcode, String startTime, String endTime);

    /**
     * 车辆类型数量统计
     * @param startT
     * @param endT
     * @param vcode
     */
    void getCarNumByType(String startT, String endT, String vcode);


}
