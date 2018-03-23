package com.daqsoft.mapper.client.wifiAnalysis;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author tanggm
 * @version V3.1
 * @description
 * @date 2017-06-29 9:58
 */
@Repository
public interface YCHBHWifiAnalysisMapper {
    /**
     * 获取wifi天的数据
     * @param parMap
     * @return
     */
    List<Map> getWifiByDay(Map parMap);

    /**
     * 查询银川花博会景点列表
     * @param vcode
     * @return
     */
    List<String> getYCHBHApNameList(String vcode);

    /**
     * 查询wifi天中小时数量
     * @param map
     * @return
     */
    List<Map> getWifiHours(Map<String, String> map);
}
