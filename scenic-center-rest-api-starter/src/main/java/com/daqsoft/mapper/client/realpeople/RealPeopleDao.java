package com.daqsoft.mapper.client.realpeople;

import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.JqScenicTimelyPopulation;
import com.daqsoft.vo.client.mysqlBean.RealPeople;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:实时人数dao
 */

@Repository
public interface RealPeopleDao {
    List<RealPeopleIndexSumVo> getCountPeopleSum(String vcode);

    List<RealPeople> getCountPeopleSumYesterday(String vcode);

    List<RealPeople> getScenicTimeOnTime(Map map);

    List<RealPeople> getNowLoad(Map map);

    List<RealPeople> getScenicTimeOnTimeYesterday(Map map);

    List<RealPeople> getYesterdayLoad(Map map);

    List<RealPeople> getPeopleAvgByMonth(String vcode);

    List<JqScenicTimelyPopulation> getScenicTimePeople(Map map);

    List<JqScenicTimelyPopulation> getScenicTimePeopleByMonth(Map map);

    List<JqScenicTimelyPopulation> getScenicTimePeopleByYear(Map map);

    List<RealPeopleIndexTodayVo> getPeopleAtToday(Map map);

    List<RealPeopleIndexYesterdayVo> getPeopleAtYesterday(Map map);

    List<RealPeopleChoiceTimeVo> getPeopleFlowMonth(Map map);

    List<RealPeopleIndexSumVo> getPeopleRealTime(Map map);

    List<RealPeopleIndexSumVo> getPeopleSumBylastyear(Map map);

    List<RealPeopleIndexSumVo> getPeopleSumBeforelast(Map map);

    List<RealPeopleIndexYesterdayVo> getMaxCountPeople(Map map);

    List<RealPeopleChoiceTimeVo> getRealTimeSlotMeth(Map map);

    List<RealPeopleChoiceTimeVo> getRealPeakValleyValueMeth(Map map);

    List<RealPeopleChoiceTimeVo> getMoneyPeakValleyValueMeth(Map map);

    List<RealPeopleChoiceTimeVo> getRealHistoryMeth(Map map);

    String getTeamFitMeth(Map map);

    String getMapStartTimeFitMeth(Map map);

    List<RealPeopleChoiceTimeVo> getComfortDegreeMeth(Map map);

    List<ScenicSpotComfortDegreeVo> getScenicSpotComfortDegreeMeth(Map map);

    List<jQScenicSpotZJ> getScenicSpotZJMeth(Map map);

    List<jQScenicSpotZJ> getScenicSpotBTPeople(Map map);

    List<jQScenicSpotZJ> getScenicSum(Map map);

    String getComfortMaxCount(Map map);

    List<jQScenicSpotZJList> getScenicSpotZJCountMeth(Map map);

    List<RealPeopleIndexTodayVo> getScenicPeopleToday(Map map);

    List<TourCountVo> getTourCountDay(Map map);

    Long getPeopleRealByDate(Map map);

    Long getToursByDate(Map map);

    BigDecimal getTicketMoneyByDate(Map map);

    Long getyearTotalPeople(Map map);

    BigDecimal getYearTicketMoney(Map map);

    List<ScenicPassengerVo> getPassengerList(Map map);

    Long getScenicMaxCount(String vcode);

    List<DeviceVo> getMonitorList(String vcode);

    /**
     * 获取监控列表
     *
     * @param vcode
     * @return
     */
    List<Map> getMonitorStatus(String vcode);

    /**
     * 获取正常异常监控数量
     *
     * @param vcode
     * @return
     */
    Map getMonitorStatusSum(String vcode);

    /**
     * 按年统计旅行团的数量
     * tours=12,13,14 需要拆分哦
     * @param map
     * @return
     */
    List<Map<String,Object>> getToursByDateYear(Map map);
    /**
     * 查询景区停车场车位情况
     */
    List<ParkingVo> getParkingInfo(Map parMap);
    /**
     * 计算停车场使用率
     * @param map
     * @return
     */
    BigDecimal getParkingUsePoint(Map map);

    /**
     * 查询停车场使用情况，返回具体数量
     * @param map
     * @return
     */
    Map getParkingUseInfo(Map map);

    /**
     * 计算景区舒适度
     * @param parMap
     * @return
     */
    Map<String,Object> getScenicComfort(Map parMap);

    /**
     * 查询近30日总人数
     *
     * @param map
     * @return
     */
    int getSumThrity(Map map);


    /**
     * 查询近30日每天人数
     *
     * @param map
     * @return
     */
    Map getMaxThrity(Map map);

    /**
     * 查询景区景点人数
     * @param smap
     * @return
     */
    List<Map> getSpotsNumList(Map<String, String> smap);

    /**
     * 查询景区景点列表
     * @param vcode
     * @return
     */
    List<Map> getSpotsList(String vcode);
}
