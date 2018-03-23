package com.daqsoft.service.client.trafficAnalysisService;


/**
 * 停车场service
 *
 * @author zf
 * @date Created on 2017/7/27.
 * @Version: V3.0.0.
 */
public interface JqTvpmCheckoutWidgetService {

    /**
     * 停车场区域统计
     *
     * @param time  时间
     * @param type  时间类型
     * @param vcode 景区编码
     * @return
     * @update zf 20170822
     */
    Object getCheckoutNum(String time, String type, String vcode);

    /**
     * 停车场区域统计时间段
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param vcode     景区编码
     * @return
     * @update zf 20170822
     */
    Object getCheckoutBetween(String startTime, String endTime, String vcode);


}
