package com.daqsoft.service.client.trafficAnalysisService;

import com.daqsoft.vo.client.madeVoBean.Big_CarFlow_WidgetTend;
import com.daqsoft.vo.client.madeVoBean.CarComeFromCarVo;
import com.daqsoft.vo.client.madeVoBean.CarFlowVosWidget;

import java.util.List;
import java.util.Map;

/**
 * 车流趋势每年月度每日每月统计
 * Created by lianch on 2017/6/6.
 */
public interface CarFlowWidgetService {

    /**
     * 每年月度统计
     *
     * @param vcode 景区编码
     * @param year  年份
     * @return
     * @update zf 20170822
     */
    Object getCarFlowMonth(String vcode, String year);

    /**
     * 每月统计
     *
     * @param vcode 景区编码
     * @param month 月份
     * @return
     * @update zf 20170822
     */
    Object getCarFlowDay(String vcode, String month);

    /**
     * 车流分析，小客车与散客流关系每月统计使用
     *
     * @param vcode 景区编码
     * @param month 月份
     * @return
     * @update zf 20170822
     */
    List<CarFlowVosWidget> getCarFlowDayByPeople(String vcode, String month);

    /**
     * 每日统计
     *
     * @param vcode 景区编码
     * @param day   天
     * @return
     * @update zf 20170822
     */
    Object getCarFlowHour(String vcode, String day);

    /**
     * 配合每日统计使用
     *
     * @param vcode 景区编码
     * @param day   天
     * @return
     * @update zf 20170822
     */
    Object getCarFlowSum(String vcode, String day);

    /**
     * 每日汽车分布百分比日期段
     *
     * @param date    开始日期
     * @param endDate 结束日期
     * @param type    类型
     * @param vcode   景区编码
     * @return
     * @update zf 20170822
     */
    Object findCarTypePrecent(String date, String endDate, String type, String vcode);

    /**
     * 车流分析各类车型分析，总体数量分布 （日,月,年）
     *
     * @param time  时间
     * @param type  时间类型
     * @param vcode 景区编码
     * @return
     * @update zf 20170822
     */
    Object findCarTypePrecentByTime(String time, String type, String vcode);

    /**
     * 汽车趋势（月份）
     *
     * @param time  时间
     * @param type  时间类型
     * @param vcode 编码
     * @return
     * @update zf 20170822
     */
    Object findChangeCarSumByMonth(String time, String type, String vcode);

    /**
     * 车流分析;各类车辆实时变动趋势(日期段) 暂时只支持日时间段
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param type      类型
     * @param vcode     编码
     * @return
     * @update zf 20170822
     */
    Object findChangeCarSum(String startTime, String endTime, String type, String vcode);

    /**
     * 通过vcode查询省份名称
     *
     * @param vcode
     * @return
     */
    String getRegionByVcode(String vcode);

    /**
     * 车流分析，查询对应省份城市车辆来源
     *
     * @param parMap 查询条件
     * @return
     */
    List<CarComeFromCarVo> getCarComeFromYearCar(Map<String, Object> parMap);


    /**
     * 2017-08-21 zf
     * 年
     *
     * @param vcode
     * @param year  年份
     * @return
     */
    List<CarFlowVosWidget> getByYear(String vcode, String year);

    /**
     * 2017-08-21 zf
     * 月
     *
     * @param vcode
     * @param month 月份
     * @return
     */
    List<Big_CarFlow_WidgetTend> getByMonth(String vcode, String month);


    /***
     *   天
     * 2017-08-21 zf
     * @param vcode
     * @param day
     * @return
     */
    List<Big_CarFlow_WidgetTend> getByDay(String vcode, String day);
}
