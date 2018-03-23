package com.daqsoft.mapper.resource;

import com.daqsoft.entity.parking.ResParking;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResParkingMapper extends BaseCommonMapper<ResParking>{
    /**
     * 查询数据列表
     * @param parMap 查询条件参数
     * @return
     */
    List<ResParking> selectList(Map parMap);
}