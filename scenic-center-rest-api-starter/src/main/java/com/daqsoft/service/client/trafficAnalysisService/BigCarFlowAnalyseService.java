package com.daqsoft.service.client.trafficAnalysisService;

/**
 * 车流趋势service
 *
 * @author lianch
 *         Created by Administrator on 2017/6/6.
 */
public interface BigCarFlowAnalyseService {

    /**
     * 分段日期车流趋势
     *
     * @param startTime
     * @param endTime
     * @param type
     * @param vcode
     * @return
     * @update zf 20170822
     */
    Object findCarFlowByDay(String startTime, String endTime, String type, String vcode);

    /**
     * 每年季度车流趋势
     *
     * @param date
     * @param vcode
     * @return
     * @update zf 20170822
     */
    Object findCarFlowQuarter(String date, String vcode);
}
