package com.daqsoft.mapper.client.timelyparking;


import com.daqsoft.vo.client.mysqlBean.JqScenicTimelyPopulation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 散客统计
 * @author zf
 * @version V1.0.0
 * @date 2017-07-28
 */
@Repository
public interface JqScenicTimelyPopulationWidgetDao {
    /***
     * @update zf 20170822
     * @param map
     * @return
     */
    List<JqScenicTimelyPopulation> getScenicTimePeople(Map map);
    List<JqScenicTimelyPopulation> getScenicTimePeopleByMonth(Map map);
    List<JqScenicTimelyPopulation> getScenicTimePeopleByYear(Map map);

}
