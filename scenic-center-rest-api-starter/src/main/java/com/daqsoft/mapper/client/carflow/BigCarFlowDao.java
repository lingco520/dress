package com.daqsoft.mapper.client.carflow;

import com.daqsoft.vo.client.madeVoBean.Big_CarFlow_Tend;
import com.daqsoft.vo.client.madeVoBean.CarComeFromCarVo;
import com.daqsoft.vo.client.madeVoBean.CarFlow_Change_Time_Tend;
import com.daqsoft.vo.client.madeVoBean.CarFlow_Type_Tend;

import java.util.List;
import java.util.Map;

/**
 * Created by lianch on 2017/6/6.
 */
public interface BigCarFlowDao {

    List<Big_CarFlow_Tend> findCarFlowByDay(Map map);

    List<Big_CarFlow_Tend> findCarFlowByYear(Map map);

    /**
     * 各类车辆总体数量分布 (年)
     *
     * @param map
     * @return
     */
    List<CarFlow_Type_Tend> findCarTypePrecentByYear(Map map);

    /**
     * 各类车辆总体数量分布 (月)
     *
     * @param map
     * @return
     */
    List<CarFlow_Type_Tend> findCarTypePrecentByMonth(Map map);

    /**
     * 各类车辆总体数量分布 (日)
     *
     * @param map
     * @return
     */
    List<CarFlow_Type_Tend> findCarTypePrecentByDay(Map map);

    /**
     * 各类车辆总体数量分布 (日期段)
     *
     * @param map
     * @return
     */
    List<CarFlow_Type_Tend> findCarTypePrecentByTimeQuantum(Map map);

    /**
     * 车流分析各类车型分析，总体数量分布 （年）
     *
     * @param map
     * @return
     */
    List<CarFlow_Type_Tend> findCarTypePrecentByYearTime(Map map);

    /**
     * 车流分析各类车型分析，总体数量分布 （月）
     *
     * @param map
     * @return
     */
    List<CarFlow_Type_Tend> findCarTypePrecentByMonthTime(Map map);

    /**
     * 车流分析各类车型分析，总体数量分布 （日）
     *
     * @param map
     * @return
     */
    List<CarFlow_Type_Tend> findCarTypePrecentByDayTime(Map map);

    /**
     * 汽车趋势（月份）
     *
     * @param map
     * @return
     */
    List<CarFlow_Change_Time_Tend> findChangeCarSumByMonth(Map map);

    /**
     * 车流分析;各类车辆实时变动趋势（日）
     *
     * @param map
     * @return
     */
    List<CarFlow_Change_Time_Tend> findChangeCarSum(Map map);

    /**
     * 各类车辆变动趋势（每日）
     *
     * @param map
     * @return
     */
    List<CarFlow_Change_Time_Tend> getCarTypeHour(Map map);

    /**
     * 各类车辆变动趋势（月）
     *
     * @param map
     * @return
     */
    List<CarFlow_Change_Time_Tend> getCarTypeMonth(Map map);

    /**
     * 查询景区省份名
     * @param vcode
     * @return
     */
    String getRegionByVcode(String vcode);

    /**
     * 查询景区所在省份的每个市车辆来源
     * @param parMap
     * @return
     */
    List<CarComeFromCarVo> getCarComeFromYearCar(Map<String, Object> parMap);

}
