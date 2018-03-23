package com.daqsoft.service.client.trafficAnalysisService;


import com.daqsoft.vo.client.madeVoBean.JqTimely_Parking_TendWidget;

import java.util.List;

/**
 * @author lianch
 * @version V3.0.0
 * @date 2017-06-07.
 * @describe:景区实时停车场
 */
public interface JqTimelyParkingWidgetServiceOp {
    /**
     * 实时停车场数量
     *
     * @param vcode 景区编码
     * @return
     */
    List<JqTimely_Parking_TendWidget> findJqTimelyParking(String vcode);

    /**
     * 实时停车场所占百分比 年月日
     *
     * @param vcode 景区编码
     * @param time  时间
     * @param type  时间类型
     * @return
     * @update zf 20170822
     */
    Object findJqTimelyPercent(String vcode, String time, String type);

    /**
     * 实时停车场所占百分比 时间段
     *
     * @param vcode     景区编码
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @update zf 20170822
     */
    Object findJqTimelyBetweenTime(String vcode, String startTime, String endTime);

    /**
     * 实时停车场所占百分比 趋势图
     *
     * @param time  时间
     * @param type  时间类型
     * @param vcode 景区编码
     * @return
     * @update zf 20170822
     */
    Object findCountParkingTrend(String time, String type, String vcode);
}

