package com.daqsoft.mapper.resource;


import com.daqsoft.entity.gisMapTypeManage.ResGisMapTypeManage;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: gis地图类型管理
 * @Author: superziy .
 * @Date: Created in 10:10 2018/1/10.
 * @Version: 4.0.0
 * @describe: gis地图类型管理
 * @since:JDK 1.8
 */
@Repository
public interface ResGisMapTypeManageMapper extends BaseCommonMapper<ResGisMapTypeManage>{

    /**
     * 查询列表数据
     *
     * @param map
     * @return
     */
    List<ResGisMapTypeManage> selectList(Map map);

    /**
     * 验证gis点击类型是否重复
     *
     * @param gisName
     * @return
     */
    Integer checkGisMapType(String gisName);
}