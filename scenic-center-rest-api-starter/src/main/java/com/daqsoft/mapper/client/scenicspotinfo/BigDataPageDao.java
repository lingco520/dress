package com.daqsoft.mapper.client.scenicspotinfo;

import com.daqsoft.vo.client.madeVoBean.PopulationSeal;
import com.daqsoft.vo.client.madeVoBean.RealPeopleOld;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-08 10:22.
 * @Description 客流数据查询
 */
@Repository
public interface BigDataPageDao {

    /**
     * 查询某年散客的数量
     *
     * @param map
     * @return
     */
    String classIfication(Map map);

    /**
     * 查询某年团队的数量
     *
     * @param map
     * @return
     */
    String tdClass(Map map);

    /**
     * 查询散客某年按月份进行统计数据列表
     *
     * @param map
     * @return
     */
    List<PopulationSeal> populationFit(Map map);

    /**
     * 实时人数数据按小时查询
     *
     * @param map
     * @return
     */
    List<RealPeopleOld> isFindPopulationHour(Map map);
}
