package com.daqsoft.mapper.resource;


import com.daqsoft.entity.hospital.Hospital;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResHospitalMapper extends BaseCommonMapper<Hospital> {
    List<Hospital> selectList(Map map);
}