package com.daqsoft.mapper.resource;


import com.daqsoft.entity.roadTraffic.ResScenicRoadTraffic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface ResScenicRoadTrafficMapper {

    List<ResScenicRoadTraffic> selectList(Map map);

    int deleteByPrimaryKey(Long id);

    int insert(ResScenicRoadTraffic record);

    int insertSelective(ResScenicRoadTraffic record);

    ResScenicRoadTraffic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ResScenicRoadTraffic record);

    int updateByPrimaryKey(ResScenicRoadTraffic record);
}