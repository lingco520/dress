package com.daqsoft.mapper.resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DataBusinessMapper {

    /**
     * 保存景区游客画像
     *
     * @param insertSql
     */
    void savePortrayalData(@Param("sql") String insertSql);

    /**
     * 保存模拟数据
     *
     * @param insertSql
     */
    void saveSimulationData(@Param("sql") String insertSql);


    /**
     * 删除模拟数据
     *
     * @param sql
     */
    void deleteData(@Param("sql") String sql);

    /**
     * 查询所有省份
     */
    List<Map> getAddressNameList();


}