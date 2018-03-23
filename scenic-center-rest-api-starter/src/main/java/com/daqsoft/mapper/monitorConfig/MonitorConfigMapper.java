package com.daqsoft.mapper.monitorConfig;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: 客户端监控配置供外部使用接口
 * @Author: lyl
 * @Date: 2018/03/21 17:39
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Repository
public interface MonitorConfigMapper {

    /**
     * 统计有多少监控分组
     *
     * @param vcode
     * @return
     */
    int getMonitorGroupNum(String vcode);

    /**
     * 按照分组查询监控
     *
     * @param vocde
     * @return
     */
    List<Map> getMonitorGroupName(String vocde);

    /**
     * 获取监控类表数据，区分内外网(外)
     *
     * @param param
     * @return
     */
    List<Map> getMonitorOutInfos(Map param);

    /**
     * 获取监控类表数据，区分内外网(内)
     *
     * @param param
     * @return
     */
    List<Map> getMonitorInfos(Map param);

    /**
     * 获取监控数量
     *
     * @param param
     * @return
     */
    int getCountMonitor(Map param);


    /**
     * 未分组则默认景区名称
     *
     * @param vcode
     * @return
     */
    String getScenicName(String vcode);

    /**
     * 获取区域名称
     *
     * @param vcode
     * @return
     */
    List<Map> getRegion(String vcode);


}
