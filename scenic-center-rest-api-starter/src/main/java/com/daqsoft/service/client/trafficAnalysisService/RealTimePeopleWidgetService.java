package com.daqsoft.service.client.trafficAnalysisService;

/**
 * 小客车与散客关系
 *
 * @author zf
 * @version V1.0.0
 * @date 2017-07-28
 */
public interface RealTimePeopleWidgetService {
    /**
     * 车流分析，小客车与散客流关系
     *
     * @param time
     * @param type
     * @param vcode
     * @return
     * @update zf 20170802
     * @update zf 20170822
     */
    Object findScenicTimePeople(String vcode, String time, String type);

}
