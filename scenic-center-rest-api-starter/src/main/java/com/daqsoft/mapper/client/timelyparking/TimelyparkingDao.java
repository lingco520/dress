package com.daqsoft.mapper.client.timelyparking;

import com.daqsoft.vo.client.madeVoBean.CarFlowTypeVo;
import com.daqsoft.vo.client.madeVoBean.CarFromProvinceVo;
import com.daqsoft.vo.client.madeVoBean.TimelyParkingSurplusVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:停车场数据dao
 */

@Repository
public interface TimelyparkingDao {

    List<TimelyParkingSurplusVo> getJqTimelyParking(String vcode);

    TimelyParkingSurplusVo getJqTimelyParkingData(Map map);

    List<CarFromProvinceVo> getCarFromByMonth(Map map);

    List<CarFromProvinceVo> getCarFromByAll(Map map);

    List<CarFromProvinceVo> getCarFromByYear(Map map);

    List<CarFlowTypeVo> getCarTypeParkByDay(Map map);

    TimelyParkingSurplusVo getJqTimelyParkingOne(String vcode);

    List<Map> getParkByDate(Map map);

    TimelyParkingSurplusVo getJqParkingData(Map map);

    /**
     * 查询景区停车场车辆类型数量，根据进入时间和出去时间综合查询
     * @param map
     * @return
     */
    List<CarFlowTypeVo> getCarTypeParkByDayDates(Map map);
}
