package com.daqsoft.mapper.client.forecastwarning;

import com.daqsoft.vo.client.madeVoBean.TrafficWarningVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Author znb
 * on 2017/7/10.
 */
@Repository
public interface ForecastWarningDao {
    List<TrafficWarningVo> getTrafficForecast(Map map);
    int getTrafficCount(Map map);

    Map<String,Object> getMaps(String vcode);

    /**
     * 查询景区今日团队运行数
     * @param parMap
     * @return
     */
    Integer getTodayTeams(Map<String, Object> parMap);
}
