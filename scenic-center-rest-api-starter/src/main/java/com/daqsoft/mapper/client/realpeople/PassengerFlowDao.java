package com.daqsoft.mapper.client.realpeople;

import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.ScenicSpots;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:
 */

@Repository
public interface PassengerFlowDao {

    /**
     * 获取当年有游客数的总天数
     *
     * @param map
     * @return
     */
    Map getYearPassengerDays(Map map);


    /**
     * 散客统计
     * @param map
     * @return
     */
    List<TeamTypeVo> getClassIfication(Map map);

    /**
     * 团队统计
     * @param map
     * @return
     */
    String getTdClass(Map map);

    /**
     * 团队统计
     * @param map
     * @return
     */
    List<ProvinceLicensePlateVo> getProvinceLicensePlate(Map map);

    /**
     * 年统计省外客流
     * @param map
     * @return
     * @update zf 20170904
     */
    List<ProvinceLicensePlateVo> getProvinceOutside(Map map);

    /**
     * 实时人数统计
     * @param map
     * @return
     */
    RealPeopleIndexSumVo getPeopleRealTime(Map map);

    /**
     * 统计景区所有年份的景区总数
     * 按年份进行分组
     * lrd添加：2017-08-11
     * @param map
     * vcode  year
     * @return
     */
    List<Map<String,Object>> getRealPeopleSumByYear(Map map);


    /**
     * 2018-08-11
     * lrd:获取所有景区当天的实时人数
     * 查询范围是当前时间的前天，因为考虑推数据的延迟，需要补偿之前的缓存数据
     * 此方法为共用方法。
     * @return
     */
    List<Map<String,Object>> getPassengerFlowByDay(Map map);

    /**
     * 按小时统计景区实时人数
     * @param map
     * @return
     */
    List<Map<String,Object>>  getPassengerFlowByHour(Map map);

    /**
     * 景点实时人数获取
     * @param map
     * @return
     */
    Map<String,Object> getPassengerFlowByScenic(Map map);


    /**
     * 按小时拉取景区实时人数
     * @param map
     * @return
     */
    List<Map<String,Object>> getPassengerFlowByThisDay(Map map);

    /**
     * 按小时拉取景区实时离开人数
     * @param map
     * @return
     */
    List<Map<String,Object>> getPassengerFlowByThisDayLeave(Map map);

    /**
     * 查询线上票务数据---天
     * @param map
     * @return
     */
    String getOnlineByDay(Map map);
    /**
     * 查询线下票务数据---天
     * @param map
     * @return
     */
    String getOfflineByDay(Map map);

    /**
     * 查询景区景点数据
     * @param vcode
     * @return
     */
    List<Map<String, Object>> getScenicNames(String vcode);

    /**
     * 通过景区编码查询某天的景点人数
     * @return
     */
    List<Map<String,Object>> getScenicPopulation(Map<String, Object> parMap);

    /**
     * 银川花博会当天检票数和出票数
     * @return
     */
    Map getYCHBHPassengerToday(Map map);

    /**
     * 银川花博会累计检票数
     * @return
     */
    Map getYCHBHPassengerAll(Map map);


    /**
     * 银川花博会景区景点数据分析
     * @param map
     * @return
     */
    List<Map<String, Object>> getYCHBHSpotPassenger(Map map);

    /**
     * 银川花博会景区景点数据分析
     * @param map
     * @return
     */
    List<Map<String, Object>> getYCHBHSpotPassengerS(Map map);


    /**
     * 银川花博会wifi与客流
     *
     * @param map
     * @return
     */
    List<YCHBHWifiVo> getYCHBHWifiPassenger(Map map);


    /**
     * 银川花博会查询场馆
     *
     * @return
     */
    List<Map<String, Object>> getYCHBHSpot(Map map);

    /**
     * 查询今日团队数
     * @param map
     * @return
     */
    List<Map> getTodayTeams(Map map);

    /**
     * 银川花博会WIFI
     * 精确到小时段
     */
    List<Map<String,Object>> getWIFIByYCHBH(Map map);

    /**
     *
     * @param map
     * @return
     */
    Integer getNumDays(Map map);

    /**
     * 查询时间范围人数
     * @param daysMap
     * @return
     */
    Integer getPeopleNum(Map daysMap);

    /**
     * 查询时间范围中某天数据量最大的一天值
     * @param daysMap
     * @return
     */
    Map getMaxDayNum(Map daysMap);
    /**
     * 资源编码模糊匹配大法
     * @return
     */
    List<Map<String,Object>> getScenicOne();



    /**
     * 资源编码模糊匹配大法
     * @return
     */
    List<Map<String,Object>> getScenicTwo();

    /**
     * 资源编码更新插入大法
     */
    void  insertScenicOne(Map map);

    /**
     * 通用地图微件接口
     * 景区游客来源分析
     * 2017-09-08
     * @param map
     * @return
     */
    List<Map<String,Object>> getPeopleComeTopTenCommon(Map map);

    /**
     * 查询景点小时人数
     * @param mapMapper
     * @return
     */
    List<ScenicSpotsTimeVo> getScenicSpotsDataTime(Map mapMapper);
    /**
     * 查询景点小时人数--历史数据方法
     * @param mapMapper
     * @return
     */
    List<ScenicSpots> getScenicSpotsDataTimeHistory(Map mapMapper);


    /**
     * 云台山小时段客流数据模拟(插入)
     * @param map
     */
    void saveYtsPassengerByDay(Map map);
    /**
     * 云台山小时段客流数据模拟(查询)
     * @param map
     */
    List  getYtsPassengerByDay(Map map);


    /**
     * 云台山日客流趋势(插入)
     * @param map
     */
    void savePassengerByDayYts(Map map);

    /**
     * 云台山日客流趋势(查询)
     * @param map
     */
    List getYtsPassengerDetailByDay(Map map);


    /**
     * 云台山日客流驻留时长分析--天段(插入)
     * @param map
     */
    void  savePassengerByTimeYts(Map map);


    /**
     * 云台山日客流驻留时长分析--天段(查询)
     * @param map
     */
    List getYtsPassengerDetailByTime(Map map);


    /**
     * 云台山日客流驻留时长--小时段(插入)
     * @param map
     */
    void savePeopleRemainByYts(Map map);

    /**
     * 云台山日客流驻留时长--小时段(查询)
     * @param map
     */
    List getPeopleRemainByYts(Map map);


    /**
     * 银川花博会年度客流趋势
     *
     * @return
     */
   Map getYCHBHPassengerFlowByYear(Map map);

    /**
     * 银川花博会按年月度客流趋势
     *
     * @param map
     * @return
     */
    List<Map> getYCHBHPassengerFlowByYMonth(Map map);

    /**
     * 银川花博会按年分段日期客流趋势
     *
     * @param map
     * @return
     */
    List<Map> getYCHBHPassengerFlowByTime(Map map);

    /**
     * 银川花博会最大值客流趋势
     *
     * @return
     */
    List<Map> getYCHBHPassengerFlowByMax(Map map);


    /**
     * 银川花博会季度客流趋势
     *
     * @return
     */
    Map getYCHBHPassengerFlowByQuarter(Map map);
    /**
     * 银川花博会按月客流趋势
     *
     * @param map
     * @return
     */
    Map getYCHBHPassengerFlowByMonth(Map map);

    /**
     * 银川花博会近七天客流趋势
     *
     * @param time
     * @return
     */
    Map getYCHBHPassengerFlowBySeven(String time);

    /**
     * 银川花博会景点缓存
     *
     * @param vcode
     * @return
     */
    List<Map> getPassengerByScenic(String vcode);

    /**
     * 银川花博会当天数据获取
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> getPassengerFlowScenicByThisDay(Map map);

    /**
     * 银川花博会天数据获取
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> getPassengerFlowScenicByDay(Map map);

    /**
     * 银川花博会小时数据获取
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> getPassengerFlowScenicByHour(Map map);

    /**
     * 查询车载系统车辆列表（车速不为0）
     * @return
     */
    List<YlxGpsCarLocationVo> getGpsCarList();

    /**
     * 获取模拟团队来源地名称
     *
     * @param map
     * @return
     */
    Map getSimulateTour(Map map);
    /**
     * 查询车辆总数
     * @return
     */
    int getGpsCarTotal();

    /**
     * 根据名称查询地区经纬度
     * @param sourceCity
     * @return
     */
    Map getRegionByName(@Param("sourceCity") String sourceCity);
}
