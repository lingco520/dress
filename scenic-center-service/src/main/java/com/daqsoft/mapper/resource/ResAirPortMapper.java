package com.daqsoft.mapper.resource;

import com.daqsoft.entity.airPort.AirPort;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: 景区基础资源-机场
 * @Author: lrd
 * @Date: Created in 10:10 2018/1/22.
 * @Version: 4.0.0
 * @describe:景区基础资源-机场
 * @since:JDK 1.8
 */
@Repository
public interface ResAirPortMapper extends BaseCommonMapper<AirPort> {

    List<AirPort> selectList(Map map);
}