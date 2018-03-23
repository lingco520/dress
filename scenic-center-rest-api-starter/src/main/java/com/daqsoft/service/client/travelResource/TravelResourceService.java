package com.daqsoft.service.client.travelResource;

import java.util.List;
import java.util.Map;

/**
 * Author znb
 * on 2017/6/28.
 * 旅游资源管理Service
 */
public interface TravelResourceService {

    /**
     * 获取公共资源
     * @param vcode
     * @param region
     * @return
     */
    List getBasicResource(String vcode, String region);

    /**
     * 获取景区设备
     * @param vcode
     * @return
     */
    List getEquipment(String vcode);

    /**
     * 景区设备详情
     * @param vcode
     * @return
     */
    List getEquipmentDetail(String vcode, String year);

    /**
     * 景区设备月份损坏详情
     * @param vcode
     * @param year
     * @return
     */
    List getEquipmentDamage(String vcode, Integer year);

    /**
     * 获取多媒体信息
     * @param vcode
     * @return
     */
    List getMultimedia(String vcode);

    /**
     * 获取人力资源信息
     * @param vcode
     * @return
     */
    List getHR(String vcode);

    /**
     * 获取人力资源详情
     * @param vcode
     * @return
     */
    List getHRDetail(String vcode);

    /**
     * 获取应急资源详情-应急指挥系统
     * @param vcode
     * @return
     */
    Map getEmergenciesResource(String vcode);

    /**
     *  获取应急资源-后台
     * @param vcode
     * @return
     */
    Map getEmergencyGoods(String vcode);

    /**
     * 获取公共资源
     * @param vcode
     * @return
     */
    List getPublicResource(String vcode);

    /**
     * 获取公共资源详情
     * @param vcode
     * @return
     */
    List<Map> getPublicDetail(String vcode);

    /**
     * 获取人力资源员工统计信息
     *
     * @param vcode
     * @return
     */
    Map getStaffInfo(String vcode);

    /**
     * 获取人力资源部门员工统计信息
     *
     * @param vcode
     * @return
     */
    List<Map> getDepartmentInfo(String vcode);

    Map delRedis(String rawKey, String rawHashKeys);
}
