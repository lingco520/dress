package com.daqsoft.service.client.trafficAnalysisService;


import com.daqsoft.vo.client.madeVoBean.CarComeFromWidgetVo;

import java.util.List;

/**
 * 车辆来源地
 * Created by  lianch on 2017/6/7.
 */
public interface CarComeToService {

    /**
     * 当日车辆来源排行
     *
     * @param vcode 景区编码
     * @param day   时间
     * @return
     */
    List<CarComeFromWidgetVo> getCarComeFromDay(String vcode, String day);

    /**
     * 选择日期车辆来源排行
     *
     * @param vcode    景区编码
     * @param beginDay 开始日期
     * @param endDay   结束日期
     * @return
     * @update zf 20170822
     */
    Object getCarComeFromDay2Day(String vcode, String beginDay, String endDay);

    /**
     * 季度城市车辆来源
     *
     * @param vcode   景区编码
     * @param quarter 年份季度
     * @return
     * @update zf 20170822
     */
    Object getCarComeFromQuarter(String vcode, String quarter);

    /**
     * 当年城市车辆来源
     *
     * @param vcode 景区编码
     * @param year  年份
     * @return
     */
    List<CarComeFromWidgetVo> getCarComeFromYear(String vcode, String year);

    /**
     * 选择年城市车辆来源
     *
     * @param vcode     景区编码
     * @param beginYear 开始年份
     * @param endYear   结束年份
     * @return
     * @update zf 20170822
     */
    Object getCarComeFromYear2Year(String vcode, String beginYear, String endYear);

    /**
     * 当月城市车辆来源
     *
     * @param vcode 景区编码
     * @param month 月份
     * @return
     * @update zf 20170822
     */
    Object getCarComeFromMonth(String vcode, String month);

}
