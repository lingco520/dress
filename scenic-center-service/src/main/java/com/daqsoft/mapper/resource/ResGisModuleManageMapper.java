package com.daqsoft.mapper.resource;


import com.daqsoft.entity.gisClickManage.ResGisClickManage;
import com.daqsoft.entity.gisModuleManage.ResGisModuleManage;
import com.daqsoft.mapper.common.BaseCommonMapper;
import com.daqsoft.vo.resource.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: gis资源模块管理
 * @Author: superziy .
 * @Date: Created in 10:10 2018/1/10.
 * @Version: 4.0.0
 * @describe: gis资源模块管理
 * @since:JDK 1.8
 */
@Repository
public interface ResGisModuleManageMapper extends BaseCommonMapper<ResGisModuleManage>{

    /**
     * 查询列表数据
     *
     * @param map
     * @return
     */
    List<ResGisModuleManage> selectList(Map map);

    /**
     * 验证gis资源模块是否重复
     *
     * @param gisName
     * @return
     */
    Integer isCheckGisName(String gisName);

    /**
     * 查询点击类型
     * @return
     */
    List<ResGisClickManage> getClickType();

    /**
     * 查询所有模块
     * @return
     */
    List<GisModule> getGisModule(String vcode);

    /**
     * 景点gis
     * @return
     */
    List<GisScenicSpots> getGisScenic(String vcode);
    /**
     * 景点厕所
     * @return
     */
    List<GisScenicToilet> getGisToilet(String vcode);
    /**
     * 景点餐饮场所
     * @return
     */
    List<GisScenicDining> getGisDining(String vcode);
    /**
     * 景点停车场
     * @return
     */
    List<GisScenicParking> getGisParking(String vcode);
    /**
     * 宾馆酒店
     * @return
     */
    List<GisCommonVo> getGisHotel(String vcode);
    /**
     * 娱乐场所
     * @return
     */
    List<GisCommonVo> getGisEntertainment(String vcode);
    /**
     * 景点人员定位信息
     * @return
     */
    List<GisScenicPersonLoca> getPersonLocation(Map map);
}