package com.daqsoft.mapper.client.realwifi;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-05-23 15:11.
 */
@Repository
public interface RealWifiDao {

    /**
     * 获取时间段内景区各景点实时WiFi数据统计
     * @return
     */
    List<Map<Object, Object>> getWifiPart(@Param("vcode") String vcode, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 获取实时景点WiFi数  （和getWifiPart查询的是不同的表）
     * @param vcode
     * @return
     */
    List<Map<Object, Object>> getScenicWifi(String vcode);
    
    /**
     *  查询景区有多少个对应的WIFI点位
     */
    List<Map<Object, Object>> getWifiSites(@Param("vcode") String vcode);
    
    /**
     *   查询景区当天最新时间统计的驻留时长 
     */
    List<Map<Object, Object>> getWifiStay(@Param("vcode") String vcode, @Param("BuildingId") String BuildingId);

    /**
     * 查询景区wifi列表
     * @param vcode
     * @return
     */
    List<Map> getYCHBHWifiList(String vcode);

    /**
     * 查询景区wifi设备连接数
     * @param parMap
     * @return
     */
    Map getYCHBHWifiNums(Map parMap);

}
