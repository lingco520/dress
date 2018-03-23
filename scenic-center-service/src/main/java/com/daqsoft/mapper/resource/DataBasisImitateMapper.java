package com.daqsoft.mapper.resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DataBasisImitateMapper{
    /**
     * 生成实时人数模拟数据
     * @param insertSql
     * @return
     */
    int saveDataScenicTimelyPopulation(@Param("sql") String insertSql);

    /**
     * 删除实时人数数据
     * @param vcode
     * @param startDate
     * @param endDate
     */
    void deleteOldDataScenicTimelyPopulation(@Param("vcode") String vcode, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("dataType") String dataType);
    /**
     * 生成线下票务模拟数据
     * @param insertSql
     */
    int saveDataOfflineTicket(@Param("sql") String insertSql);

    /**
     * 删除线下票务数据
     * @param vcode
     * @param startDate
     * @param endDate
     */
    void deleteOldDataOfflineTicket(@Param("vcode") String vcode, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("dataType") String dataType);
    /**
     * 生成线上票务模拟数据
     * @param insertSql
     */
    int saveDataOnlineTicket(@Param("sql") String insertSql);
    /**
     * 删除线上票务数据
     * @param vcode
     * @param startDate
     * @param endDate
     */
    void deleteOldDataOnlineTicket(@Param("vcode") String vcode, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("dataType") String dataType);
    /**
     * 生成车流量模拟数据
     * @param insertSql
     */
    int saveDataTvpmVehiclelogPart(@Param("sql") String insertSql);
    /**
     * 删除车流量数据
     * @param vcode
     * @param startDate
     * @param endDate
     */
    void deleteOldDataTvpmVehiclelogPart(@Param("vcode") String vcode, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("dataType") String dataType);

    /**
     * 查询城市名称列表，只查询城市，不查询省份和区
     * @param provinceName
     * @return
     */
    List<Map> getCityCarList(@Param("provinceName") String provinceName);

    /**
     * 只查询省份名称
     * @param subRegion
     * @return
     */
    String getProvinceName(@Param("subRegion") String subRegion);
    /**
     * 只查询城市名称
     * @param subRegion
     * @return
     */
    String getCityName(@Param("subRegion") String subRegion);

    /**
     * 通过城市名查询城市车牌等信息
     * @param cityName
     * @return
     */
    List<Map> getCityCarMap(@Param("cityName") String cityName);

    /**
     * 生成实时停车位模拟数据
     * @param insertSql
     */
    int saveDataTimelyParking(@Param("sql") String insertSql);
    /**
     * 删除实时停车位数据
     * @param vcode
     * @param startDate
     * @param endDate
     */
    void deleteOldDataTimelyParking(@Param("vcode") String vcode, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("dataType") String dataType);

    /**
     * 删除实时停车位数据
     * @param vcode
     * @param startDate
     * @param endDate
     */
    void deleteOldDataParkingCheckout(@Param("vcode") String vcode, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("dataType") String dataType);

    /**
     * 查询景区最大承载量
     * @param vcode
     * @return
     */
    List<Integer> getScenicMaxQuantity(@Param("vcode") String vcode);
}