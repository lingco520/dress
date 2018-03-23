package com.daqsoft.service.client.passengerFlowService;

import com.daqsoft.vo.client.madeVoBean.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: lyl .
 * @Date: Created in 2017/6/18.
 * @Version: V3.0.0.
 * @describe:景区客流人数分析service
 */
public interface PassengerService {


    /**
     * 获取随机数据
     *
     * @param map
     * @return
     */
    Map getSimulateTour(Map map);

    /**
     * 银川花博会客流与票务
     *
     * @param open
     * @return
     */
    Map<String,Object> getYCHBHPassenger(String open);


    /**
     * 银川花博会景区景点数据分析
     *
     * @return
     */
    List<Map<String, Object>> getYCHBHSpotPassenger(String vcode);

    /**
     * 银川花博会wifi与客流关系
     *
     * @param time
     * @return
     */
    //List<List<YCHBHWifiVo>> getYCHBHWifiPassenger(String time);


    /**
     * 银川花博会wifi与客流关系
     * 重构时间：2017-09-06
     * @param time
     * @return
     */
    List<List<YCHBHWifiVo>> getYCHBHWifiPassengerOne(String time);

    /**
     * 今年总人数（redis)
     *
     * @param vcode
     * @return
     */
    List<Map<String,Object>> getRealTimeMethYear(String vcode, String dateTime);

    /**
     * 节假日人数统计
     *
     * @param vcode
     * @param date
     * @return
     */
    HolidayPeopleVo getHolidayPeople(String vcode, String date);

    /**
     * 地图客流来源
     *
     * @param vcode
     * @param date
     * @return
     */
    List<RealPeopleComeFromTopVo> getPeopleComeTopTen(String vcode, String date);

    /**
     * 景区省内客流来源
     *
     * @param vcode
     * @param date
     * @return
     */
    List<ProvinceLicensePlateVo> getProvinceLicensePlate(String vcode, String date);

    /**
     * 景区省内客流来源排名前十
     *
     * @param vcode
     * @param year
     * @return
     */
    List<ProvinceLicensePlateVo> getProvinceLicensePlateRank(String year, String vcode);

    /**
     * 省外游客
     * @param vcode
     * @param year
     * @return
     * @update zf 20170904
     */
    List<ProvinceLicensePlateVo> getProvinceOutside(String year, String vcode) ;

    /**
     * 景区省内客流来源
     *
     * @param vcode
     * @param date
     * @return
     */
    List<RealPeopleComeFromVo> getPeopleComeFrom(String vcode, String date);

    /**
     * 景区日客流（运营商数据）
     *
     * @param vcode
     * @param date
     * @return
     */
    List<RealPeopleDayVo> getPeopleDay(String vcode, String date);

    /**
     * 景区日客流趋势分析（运营商数据）
     * @param startTime
     * @param endTime
     * @param vcode
     * @return
     */
    List<RealPeopleDayTrendVo> getPeopleDayTrend(String startTime, String endTime, String vcode);

    /**
     * 景区日客流驻留时长分析（运营商数据）
     *
     * @param startTime
     * @param endTime
     * @param vcode
     * @return
     */
    List<RealPeopleDayTrendVo> getVisitorAnalyse(String startTime, String endTime, String vcode);

    /**
     * 景区游客驻留时长（运营商数据）
     *
     * @param dateTime
     * @param vcode
     * @return
     */
    Map<String,Object> getPeopleRemain(String dateTime, String vcode);

    /**
     * 景区省外客流来源排名前十 （运营商数据）
     *
     * @param year
     * @param vcode
     * @return
     */
    List<RealPeopleComeFromVo> getPeopleComeFromRank(String year, String vcode);

    /**
     * 获取景区大数据--景区游客来源分析 定時器在走
     * 2017-08-11
     * lrd：此方法为特定方法，数据来源部分获取移动公司。
     * 云台山定制接口
     * @param vcode
     * @param dateTime   当前的年份
     * @return
     */
    Map<String,Object>  getBigPassengerAll(String vcode, String dateTime, String open);


    /**
     * 获取景区大数据--景区游客来源分析 定時器在走
     * 2017-09-09  通用微件
     * @param vcode
     * @param dateTime
     * @return
     */
    Map<String,Object>  getPeopleYearCommon(String vcode, String dateTime, String open);

    /**
     * 2017-08-11
     * lrd:获取所有景区当天的实时人数 推送redis
     * 查询范围是当前时间的前6天，因为考虑推数据的延迟，需要补偿之前的缓存数据
     * @param vcode
     * @param startTime
     * @param endTime
     * @return
     */
    void getPassengerFlowByDay(String vcode, String startTime, String endTime);
    /**
     * 2017-08-11
     * lrd:获取所有景区当月的实时人数 推送redis
     * @param startTime
     * @param endTime
     * @return
     */
    void getPassengerFlowByMonth(String vcode, String month, String startTime, String endTime);
    /**
     * 2017-08-11
     * lrd:获取景区当年的实时人数(读月份缓存进行累加) 推送redis
     * @return
     */
    void getPassengerFlowByYear(String vcode, String year);

    /**
     * 按小时获取景区数据
     * 2017-08-16
     * @param vcode
     * @param time
     */
    void getPassengerFlowByHour(String vcode, String stime, String time);


    /**
     * 获取景区景点数据人数
     */
    void  getPassengerFlowByScenic(String vcode);


    /**
     * 获取当天段内的实时人数
     * @param vcode
     * @param startTime
     * @param endTime
     */
    void getPassengerFlowByThisHours(String vcode, String startTime, String endTime, String time, String year, String month);


    /**
     * 抓举节假日的实时人数
     */
    void getPassengerFlowByHoliay(String vcode, String time, String year);

    /**
     * 存储本年最大峰值即日期
     * @param vcode
     * @param time
     * @param year
     */
    void getMaxPassengerFlowvoid(String vcode, String time, String year);

    /**
     * 存储本年最大峰值即日期
     *
     * @param vcode
     * @param year
     */
    void getAvergePassengerYear(String vcode, String year);

    /**
     * 查询景区wifi列表
     * @param vcode
     * @return
     */
    List<Map> getYCHBHWifiList(String vcode);

    /**
     * 查询具体wifi连接数
     * @param parMap
     * @return
     */
    Map getYCHBHWifiNums(Map parMap);

    /**
     * 银川花博会年度客流趋势
     *
     * @return
     */
    List<Map> getYCHBHPassengerFlowByYear(String strTime, String endTime);

    /**
     * 银川花博会按年月度客流趋势
     * @param time
     * @return
     */
    Map getYCHBHPassengerFlowByYMonth(String time);

    /**
     * 银川花博会按月度客流趋势
     *
     * @param year
     * @param month
     * @return
     */
    List<Map> getYCHBHPassengerFlowByMonth(String year, String month);

    /**
     * 银川花博会按季度客流趋势
     *
     * @param year
     * @return
     */
    List<Map> getYCHBHPassengerFlowByQuarter(String year);

    /**
     * 银川花博会按时间段客流趋势
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> getYCHBHPassengerFlowByTime(String startTime, String endTime);

    /**
     * 银川花博会最大值客流趋势
     *
     * @return
     */
    List<Map> getYCHBHPassengerFlowByMax(String startTime, String endTime);

    /**
     * 银川花博会近七天客流趋势
     *
     * @return
     */
    List<Map> getYCHBHPassengerFlowBySeven();

    /**
     * 银川花博会人流量按年趋势
     *
     * @return
     */
    Map getYCHBHVisitorsFlowByYear(String startTime, String endTime, String name, String vcodes);

    /**
     * 银川花博会人流量按季度趋势
     *
     * @return
     */
    Map getYCHBHVisitorsFlowByQuarter(String year, String name, String vcodes);

    /**
     * 银川花博会人流量按月趋势
     *
     * @param year
     * @param month
     * @param name
     * @param vcodes
     * @return
     */
    Map getYCHBHVisitorsFlowByMonth(String year, String month, String name, String vcodes);

    /**
     * 银川花博会人流量按小时趋势
     *
     * @param time
     * @param name
     * @param vcodes
     * @return
     */
    Map getYCHBHVisitorsFlowByDay(String time, String name, String vcodes);

    /**
     * 银川花博会人流量按时间段趋势
     *
     * @param startTime
     * @param endTime
     * @param name
     * @param vcodes
     * @return
     */
    Map getYCHBHVisitorsFlowByTime(String startTime, String endTime, String name, String vcodes);

    /**
     * 银川花博会人流量按年月度趋势
     *
     * @param year
     * @param name
     * @param vcodes
     * @return
     */
    Map getYCHBHVisitorsFlowByYMonth(String year, String name, String vcodes);

    /**
     * 景区资源编码匹配法01
     */
    List<Map<String,Object>> getScenicOne();

    /**
     * 景区资源编码匹配法02
     */
    List<Map<String,Object>> getScenicTwo();

    void insertScenicOne(Map map);

    void getScenicSpotsDataAnalysisTimeAll(String vcode);

    /**
     * 通过年份存入季度数据
     * @param vcode
     * @param year
     */
    void getPassengerFlowByQuarter(String vcode, String year);

    /**
     * 定制 -- 云台山模拟  日客流 -- 每小时执行一次
     * @param map
     */
    void saveYtsPassengerByDay(Map map);

    /**
     * 定制 -- 云台山模拟  日客流小时段 - 每天下午6点执行一次
     * @param map
     */
    void  savePassengerByDayYts(Map map);

    /**
     * 定制 -- 云台山模拟  日客流驻留时长分析（天段） - 每天下午6点执行一次
     * @param map
     */
    void savePassengerByTimeYts(Map map);

    /**
     * 定制 -- 云台山模拟  日客流驻留时长分析（小时段） - 每天下午6点执行一次
     * @param map
     */
    void savePeopleRemainByYts(Map map);

    /**
     * 通过名称查询地区经纬度
     * @param sourceCity
     * @return
     */
    Map getRegionByName(String sourceCity);
}
