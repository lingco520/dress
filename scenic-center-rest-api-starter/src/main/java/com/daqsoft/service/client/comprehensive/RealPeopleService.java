package com.daqsoft.service.client.comprehensive;


import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.RealPeople;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:实时人数service
 */


public interface RealPeopleService {
    //今日累计
    List<RealPeopleIndexSumVo> getCountPeopleSum(String vcode);

    List<RealPeople> getSyncPeopleSum(String vcode);

    Map<Object, Object> getMaxQuantity(String vcode);

    //昨日累计人数
    List<RealPeople> getCountPeopleSumYesterday(String vcode);

    //今天负荷
    List<RealPeople> getScenicTimeOnTime(Map map);

    //昨天负荷
    List<RealPeople> getScenicTimeOnTimeYesterday(Map map);

    //30天负荷，峰值
    List<RealPeople> getPeopleAvgByMonth(String vcode);

    //客流按日期段分析
    List<RealPeopleChoiceTimeVo> getAllDateEnd(String date, String vcode, String type, String end);

    //客流按日期段分析
    List<RealPeopleChoiceTimeVo> getAllDate(String date, String vcode, String type);

    //客流按季度分析
    List<RealPeopleChoiceTimeVo> getQuarterDate(String date, String vcode, String type);

    //大数据小客车与散客分析
    List<JqScenicCarAndPeopleRelationVo> findScenicTimePeople(String time, String type, String vcode);

    //主页实时人数
    List<RealPeopleIndexTodayVo> getPeopleAtToday(Map map);

    //主页实时人数 昨天数据统计
    List<RealPeopleIndexYesterdayVo> getPeopleAtYesterday(Map map);

    //客流分析 每月客流分析
    List<RealPeopleChoiceTimeVo> getPeopleFlowMonth(String date, String vcode);

    //主页实时人数
    List<RealPeopleIndexSumVo> getPeopleRealTime(Map map);

    //主页实时人数查询今日景点人数
    List<RealPeopleIndexTodayVo> getScenicPeopleToday(Map map);

    //主页去年今日人数
    List<RealPeopleIndexSumVo> getPeopleSumBylastyear(Map map);

    //主页前年今日人数
    List<RealPeopleIndexSumVo> getPeopleSumBeforelast(Map map);

    //主页最大承载量
    List<RealPeopleIndexYesterdayVo> getMaxCountPeople(Map map);

    //实时人数按年统计
    List<RealPeopleChoiceTimeVo> getRealTimeMethYear(String vcode);

    //实时人数按月统计
    List<RealPeopleChoiceTimeVo> getRealTimeMethMonth(String vcode, String dateTime);

    //实时人数按天统计
    List<RealPeopleChoiceTimeVo> getRealTimeMethDay(String vcode, String dateTime);

    //实时人数按时统计
    List<RealPeopleChoiceTimeVo> getRealTimeMethTime(String vcode, String dateTime);

    //按年月日  时间段查询实时人数
    List<RealPeopleChoiceTimeVo> getRealTimeSlotMeth(Map map);

    //主页团队按天查询
    List<TourCountVo> getTourCountDay(Map map);

    //峰谷值（最高与最低销量峰谷值）
    List<RealPeopleChoiceTimeVo> getRealPeakValleyValueMeth(Map map);

    //峰谷值（销售量金额占比）
    List<RealPeopleChoiceTimeVo> getMoneyPeakValleyValueMeth(Map map);

    //历史客流最高（历史客流最高值）
    List<RealPeopleChoiceTimeVo> getRealHistoryMeth(Map map);

    //大数据（客流分析-团队与散客对比）团队
    String getTeamFitMeth(Map map);

    //大数据（客流分析-团队与散客对比）散客
    String getMapStartTimeFitMeth(Map map);

    //景区实时舒适度
    List<RealPeopleChoiceTimeVo> getComfortDegreeMeth(Map map);

    //景点舒适度
    List<ScenicSpotComfortDegreeVo> getScenicSpotComfortDegreeMeth(Map map);

    //景点舒适度（咂机统计）
    List<jQScenicSpotZJ> getScenicSpotZJMeth(Map map);

    //景点舒适度（统计景点数量与最大承载量）
    List<jQScenicSpotZJList> getScenicSpotZJCountMeth(Map map);
    // 查询实时人数，根据日期 2017-06-21
    Long getPeopleRealByDate(Map map);
    // 实时旅行团人数
    Long getToursByDate(String year, String vcode);
    // 查询景区实时票务数据
    BigDecimal getTicketMoneyByDate(Map map);
    // 查询年累计游客数
    Long getYearTotalPeople(String year, String vcode);
    // 查询年票务收入
    BigDecimal getYearTicketMoney(Map map);
    // 景区实时人数
    List<ScenicPassengerVo> getPassengerList(Map map);
}
