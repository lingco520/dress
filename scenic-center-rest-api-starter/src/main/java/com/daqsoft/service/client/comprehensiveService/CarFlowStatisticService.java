package com.daqsoft.service.client.comprehensiveService;

import com.daqsoft.vo.client.madeVoBean.CarFlowStatisticVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: yangkang.
 * @Date: Created in 2017/6/8.
 * @Version: V3.0.0.
 * @describe:车流量统计
 */
public interface CarFlowStatisticService {
    List<CarFlowStatisticVo> getCarFlow(String vcode);

    /**
     * 时段内车流量的统计 -- 分卡口
     * @param map
     * @return
     */
    List<CarFlowStatisticVo> getCarFlowCountByGate(Map map);
}
