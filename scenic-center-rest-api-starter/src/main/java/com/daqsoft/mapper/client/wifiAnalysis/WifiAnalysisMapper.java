package com.daqsoft.mapper.client.wifiAnalysis;


import com.daqsoft.vo.client.mysqlBean.*;

import java.util.List;
import java.util.Map;

/**
 * @author tanggm
 * @version V3.1
 * @description
 * @date 2017-06-29 9:58
 */
public interface WifiAnalysisMapper {
    List<WifiAnalysisVo> getWifiDays(Map<String, String> map);
    List<WifiHourVo> getHour(Map<String, String> map);

    List<WifiDayVo> getDay(Map<String, String> map);

    List<WifiMonthVo> getMonth(Map<String, String> map);

    List<WifiQuarterVo> getQuarter(Map<String, String> map);

    List<WifiYear> getYear(Map<String, String> map);

    List<String> getCalendarYear(Map map);

    List<String> getCalendarMonth(Map map);

    List<String> getCalendarDay(Map map);

    List<String> getCalendarQuarter(Map map);

    JqWifiAnalysis isFindData(Map map);

    List<JqWifiAnalysis> findWifiData(Map map);

    void insertWifiData(Map map);

    List<WifiAnalysisVo> day2day(Map<String, String> map);

    List<WifiAnalysisVo> year2year(Map<String, String> map);

    List<Map> getWifiQuarterByYear(Map map);

    Integer getWifiByDay(Map parMap);

    List<WifiAnalysisVo> getWifiHoursByDay(Map<String, String> map);
}
