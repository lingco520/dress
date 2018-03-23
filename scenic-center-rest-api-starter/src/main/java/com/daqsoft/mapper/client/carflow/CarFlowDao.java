package com.daqsoft.mapper.client.carflow;

import com.daqsoft.vo.client.madeVoBean.CarFlowVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: lyl .
 * @Date: Created in 2017/6/01.
 * @Version: V3.0.0.
 * @describe:车流量数据dao
 */
@Repository
public interface CarFlowDao {
    /**
     * 根据时间段查询车流量(天)
     *
     * @param map
     * @return
     */
    List<CarFlowVO> getVehicleFowTimeRangeDay(Map map);

    /**
     * 根据时间段查询车流量(年)
     *
     * @param map
     * @return
     */
    List<CarFlowVO> getVehicleFowTimeRangeYear(Map map);

    /**
     * 根据时间段查询车流量(月)
     *
     * @param map
     * @return
     */
    List<CarFlowVO> getVehicleFowTimeRangeMonth(Map map);

    /**
     * 根据时间段查询车流量(时)
     *
     * @param map
     * @return
     */
    List<CarFlowVO> getVehicleFowTimeRangeHour(Map map);

    /**
     * 查询车辆类型车辆数量
     * @param mapMapper
     * @return
     */
    List<Map> getCarNumByType(Map mapMapper);
}
