package com.daqsoft.mapper.resource;


import com.daqsoft.entity.gisMapTypeManage.ResGisMapTypeManage;
import com.daqsoft.entity.gisModuleConfig.ResGisModuleConfig;
import com.daqsoft.entity.gisModuleManage.ResGisModuleManage;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: gis资源模块配置
 * @Author: superziy .
 * @Date: Created in 10:10 2018/2/10.
 * @Version: 4.0.0
 * @describe: gis资源模块配置
 * @since:JDK 1.8
 */
@Repository
public interface ResGisModuleConfigMapper extends BaseCommonMapper<ResGisModuleConfig>{

    /**
     * 查询列表数据
     *
     * @param map
     * @return
     */
    List<ResGisModuleConfig> selectList(Map map);

    /**
     * 验证gis资源模块是否重复
     *
     * @param gisName
     * @return
     */
    Integer isCheckGisName(String gisName);

    /**
     * 查询所有gis模块
     * @return
     */
    List<ResGisModuleManage> getGisModule();

    /**
     * 查询所有gis模块
     * @return
     */
    List<ResGisMapTypeManage> getGisMapType();

    /**
     * gis模块名称
     * @return
     */
    ResGisModuleManage getGisName(String id);

    /**
     * gis是否有该模块
     * @return
     */
    Integer getIsTrue(Map map);

    /**
     * 删除该景区下所有gis模块
     * @return
     */
    void removeGisMoudle(String vcode);

    void updateSort(ResGisModuleConfig dto);
}