package com.daqsoft.service.client.forecastwarning;

import com.daqsoft.vo.client.madeVoBean.PassengerFlowVO;
import com.daqsoft.vo.client.madeVoBean.TicketForecastVO;
import com.daqsoft.vo.client.madeVoBean.TrafficWarningVo;

import java.util.List;
import java.util.Map;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-22 16:16
 * @Version: V1.0.0
 * @Describe: 预测预警 service
 */
public interface ForecastWarningService {

    /**
     * 客流预测预警
     * @param vcode 景区代码
     * @return
     */
    PassengerFlowVO getPassengerFlow(String vcode);


    /**
     * 票务预警预测
     * @param vcode 景区代码
     * @return
     */
    TicketForecastVO getTicketForecast(String vcode);


    /**
     * 节假客流预测
     * @param vcode 景区代码
     * @return
     */
    PassengerFlowVO getHolidayForecast(String vcode);

    /**
     * 团队个数预测
     * @param vcode 景区代码
     * @return
     */
    Map<String,Object> getTeamFarecast(String vcode);

    List<TrafficWarningVo> getTrafficForecast(Map map);

    int getTrafficCount(Map map);

    /**
     * 获取景区经纬度，渲染地图中心点
     * @param vcode
     * @return
     */
    Map<String,Object> getMaps(String vcode);
}
