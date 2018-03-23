package com.daqsoft.mapper.client.carflowstatistic;

import com.daqsoft.vo.client.madeVoBean.CarFlowStatisticVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yangkang.
 * @Date: Created in 2017/6/8.
 * @Version: V3.0.0.
 * @describe:车流量统计Dao
 */
@Repository
public interface CarFlowStatisticDao {
    List<CarFlowStatisticVo> getCarFlow(String vcode);

    /**
     * 时段内车流量的统计 -- 分卡口
     * @param paramMap
     * @return
     */
    List<CarFlowStatisticVo> getCarFlowCountByGate(Map paramMap);
}
