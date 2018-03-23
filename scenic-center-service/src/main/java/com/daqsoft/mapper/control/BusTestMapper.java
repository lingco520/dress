package com.daqsoft.mapper.control;

import com.daqsoft.entity.test.BusTest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BusTestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusTest record);

    BusTest selectByPrimaryKey(Long id);

    int updateByPrimaryKey(BusTest record);

    List<Map<String,Object>> getDictsByType(String carLevel);

    List<BusTest> selectList(Map parMap);
}