package com.daqsoft.service.client.statisticAnalysisService;

import java.util.List;
import java.util.Map;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-27 10:55
 * @Version:
 * @Describe: 统计分析微件service
 */
public interface StatisticAnalysisServie {

    /**
     * 获取景区过去5年人数统计
     *
     * @param vcode 景区代码
     * @return
     */
    Map<String, Object> getNumberOfScenicSpots(String vcode);

    /**
     * 获取景区过去5年大巴统计
     *
     * @param vcode 景区代码
     * @return
     */
    Map<String, Object> getStatisticsOfTheBus(String vcode);

    /**
     * 景区客流来源地分析
     *
     * @param vcode     景区代码
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    List<Map> getSourceOfPassengerFlow(String vcode, String startDate, String endDate);


    /**
     * 景区车辆来源统计分析
     *
     * @param vcode     景区代码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    Map<String, Object> getVehicleStatistics(String vcode, String startDate, String endDate);

    /**
     * 获取景区年度访问人数
     *
     * @param vcode
     * @return
     */
    Map totalVisitorNumbers(String vcode, String stime, String etime);

    /**
     * 获取景区
     *
     * @param vcode
     * @return
     */
    Map totalParkingNum(String vcode, String stime, String etime);

    List getParks(String vcode);

    /**
     * 获取团队数量
     *
     * @param vcode
     * @return
     */
    List totalTeamNum(String vcode, String stime, String etime);

    /**
     * 获取线上评轮信息
     *
     * @param vcode
     * @return
     */
    List onlineComment(String vcode, String startTime, String endTime);

    /**
     * 线上评轮评分统计
     *
     * @param vcode
     * @return
     */
    List onlineCommentGrade(String vcode);

    /**
     * 年度总接纳数
     * 取的当年和去年
     * @param vcode
     * @return
     */
    Map totalReceptionNum(String vcode, String year);

    Map commentAnalysis(String vcode, String year, String month, String pageSize, String page, String emotion);
    /**
     * 通过日期（月份）查询景区停车场总的使用情况
     * @param vcode
     * @param month
     * @return
     */
    List<Map> getParksByMonth(String vcode, String month);
}
