package com.daqsoft.mapper.app.gis;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PersonPositionMapper {

    /**
     * 新增人员点位
     * @param parMap
     * @return
     */
    Integer insertLocation(Map parMap);

    /**
     * 人员点位信息
     * @param parMap
     * @return
     */
    List<Map> personLocaInfo(Map parMap);

    /**
     * 景点信息
     * @param vcode
     * @return
     */
    List<Map> getGisScenic(String vcode);


}