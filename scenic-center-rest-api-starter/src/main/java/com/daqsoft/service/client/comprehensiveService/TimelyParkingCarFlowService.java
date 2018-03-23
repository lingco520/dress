package com.daqsoft.service.client.comprehensiveService;


import com.daqsoft.vo.client.madeVoBean.CarFromProvinceVo;
import com.daqsoft.vo.client.madeVoBean.TimelyParkingSurplusVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:停车场数据service
 */


public interface TimelyParkingCarFlowService {
    //主页停车场数据
    List<TimelyParkingSurplusVo> getJqTimelyParking(String vcode);

    //主页停车场数据(返回object)非空设置为0
    TimelyParkingSurplusVo getJqTimelyParkingData(Map<String, Object> parMap);


    //车辆来源地按时间段统计
    List<CarFromProvinceVo> getCarFromByMonth(Map map);


    //主页停车场车辆类型
    List<Map> getCarTypeParkByDay(Map map);

    // 停车场使用情况
    TimelyParkingSurplusVo getJqTimelyParkingOne(String vcode);
}
