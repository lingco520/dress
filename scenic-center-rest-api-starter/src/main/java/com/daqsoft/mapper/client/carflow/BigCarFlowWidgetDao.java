package com.daqsoft.mapper.client.carflow;

import com.daqsoft.vo.client.madeVoBean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/***
 * 车流分析
 * @update zf 20170822
 */
@Repository
public interface BigCarFlowWidgetDao {

    /***
     *车流趋势日数据 基础天 zf
     * @param map
     * @return
     */
    List<Big_CarFlow_WidgetTend> findCarFlowQsByDay(Map map);

    List<Big_CarFlow_WidgetTend> findCarFlowByDay(Map map);

    List<Big_CarFlow_WidgetTend> findCarFlowByYear(Map map);

    /**
     * 各类车辆总体数量分布 (年)
     *@update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Type_TendWidget> findCarTypePrecentByYear(Map map);

    /**
     * 各类车辆总体数量分布 (月)
     *@update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Type_TendWidget> findCarTypePrecentByMonth(Map map);

    /**
     * 各类车辆总体数量分布 (日)
     *@update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Type_TendWidget> findCarTypePrecentByDay(Map map);

    /**
     * 各类车辆总体数量分布 (日期段)
     *@update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Type_TendWidget> findCarTypePrecentByTimeQuantum(Map map);

    /**
     * 车流分析各类车型分析，总体数量分布 （年）
     * @update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Type_TendWidget> findCarTypePrecentByYearTime(Map map);

    /**
     * 车流分析各类车型分析，总体数量分布 （月）
     *@update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Type_TendWidget> findCarTypePrecentByMonthTime(Map map);

    /**
     * 车流分析各类车型分析，总体数量分布 （日）
     *@update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Type_TendWidget> findCarTypePrecentByDayTime(Map map);

    /**
     * 汽车趋势（月份）
     *@update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Change_Time_TendWidget> findChangeCarSumByMonth(Map map);

    /**
     * 汽车趋势（日）
     *@update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Change_Time_TendWidget> findChangeCarSumByDay(Map map);

    /**
     * 汽车趋势（年）
     *@update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Change_Time_TendWidget> findChangeCarSumByYear(Map map);

    /**
     * 车流分析;各类车辆实时变动趋势（日）
     *@update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Change_Time_TendWidget> findChangeCarSum(Map map);

    /**
     * 各类车辆变动趋势（每日）
     *@update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Change_Time_TendWidget> getCarTypeHour(Map map);

    /**
     * 各类车辆变动趋势（月）
     * @update zf 20170822
     * @param map
     * @return
     */
    List<CarFlow_Change_Time_TendWidget> getCarTypeMonth(Map map);

    /**
     * 查询景区省份名
     *
     * @param vcode
     * @return
     */
    String getRegionByVcode(String vcode);

    /**
     * 查询景区所在省份的每个市车辆来源
     *
     * @param parMap
     * @return
     */
    List<CarComeFromCarVo> getCarComeFromYearCar(Map<String, Object> parMap);


    /**
     * 获取每天的 按景区编码和小时进行分组的车流统计数据 zf
     * @update zf 20170822
     * @param vcode
     * @param day
     * @return
     */
    List<CarFlowVosWidget> getCarFlowDailyTocalCountGroupByVcodeAndHour(@Param("vcode") String vcode, @Param("day") String day);

    /***
     * 获取每月车流趋势 zf
     * @param vcode
     * @param day
     * @return
     */
    List<CarFlowVosWidget> getCarFlowBymonth(@Param("vcode") String vcode, @Param("day") String day);

    /***
     * 获取每年月度车流趋势 zf
     * @param vcode
     * @param day
     * @return
     */
    List<CarFlowVosWidget> getCarFlowByyear(@Param("vcode") String vcode, @Param("day") String day);


    /***
     * 每年季度车流趋势zf
     * @param map
     * @return
     */
    Map<String, Object> find_quarter_date(Map map);

}
