package com.daqsoft.mapper.client.travelResource;


import com.daqsoft.vo.client.madeVoBean.HumanResourcesVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Author znb
 * on 2017/6/28.
 */
@Repository
public interface TravelResourceDao {

    /**
     * 查询景区基础设施
     *
     * @param map
     * @return
     */
    List getBasicResource(Map map);
    List getBasicResourceHQG(Map map);

    List getMultimedia(String vcode);

    List getEquipment(String vcode);

    List getEquipmentDamage(Map map);

    List getEquipmentDetail(Map map);

    String getSourceByVcode(String vcode);

    //获取人力资源类型数量
    List getHR(String vcode);

    //获取人力资源人数详细信息
    List<HumanResourcesVO> getHRDetail(String vcode);

    //获取人力资源人数离职人数
    List<HumanResourcesVO> getLeavePeople(String vcode);

    String getRegionByVcode(String vcode);
    //物资统计后台录入
    List<Map<String,Object>> getEmergencyGoods(Map map);

    /**
     * 建筑物的公共资源
     *
     * @param vcode
     * @return
     */
    List getPublicResource(String vcode);

    /**
     * 无寺庙的公共资源
     *
     * @param vcode
     * @return
     */
    List getPublicResourceTemple(String vcode);

    /**
     * 寺庙的公共资源
     *
     * @param vcode
     * @return
     */
    List getPublicResourceYTS(String vcode);

    /**
     * 通过数据库查询景区设备信息
     * @param vcode
     * @return
     */
    List getMultimediaBySql(String vcode);

    /**
     * 通过数据库查询景区设备信息 720 -- 360(尉犁县)
     *
     * @param vcode
     * @return
     */
    List getMultimediaBySqlYLX(String vcode);

    /**
     * 查询资源新增，损坏
     * @param parMap
     * @return
     */
    Map getTravelResource(Map parMap);

    /**
     * 查询银川花博会二维码数量，专用微件
     * @param vcode
     * @return
     */
    Integer getQrcodeNum(String vcode);

    /**
     * 员工统计学历
     *
     * @param vcode
     * @return
     */
    List<Map> getEducationList(String vcode);

    /**
     * 员工统计年龄
     *
     * @param vcode
     * @return
     */
    List<Map> getAgeList(String vcode);

    /**
     * 员工统计工龄
     *
     * @param vcode
     * @return
     */
    List<Map> getWorkTimeList(String vcode);

    /**
     * 获取人力资源部门员工统计信息
     *
     * @param vcode
     * @return
     */
    List<Map> getDepartmentInfo(String vcode);
}
