package com.daqsoft.mapper.control;

import com.daqsoft.entity.test.SysTest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface SysTestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysTest record);

    SysTest selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SysTest record);

    List<SysTest> selectList(Map parMap);
}