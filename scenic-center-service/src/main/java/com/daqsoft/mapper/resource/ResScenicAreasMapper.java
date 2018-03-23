package com.daqsoft.mapper.resource;


import com.daqsoft.entity.scenicAreas.ResScenicAreas;
import com.daqsoft.mapper.common.BaseCommonMapper;
import com.daqsoft.vo.resource.ScenicAreaVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: 景点信息
 * @Author: superziy .
 * @Date: Created in 10:10 2018/1/10.
 * @Version: 4.0.0
 * @describe:景区信息
 * @since:JDK 1.8
 */
@Repository
public interface ResScenicAreasMapper extends BaseCommonMapper<ResScenicAreas>{

    /**
     * 查询列表数据
     *
     * @param map
     * @return
     */
    List<ResScenicAreas> selectList(Map map);

    /**
     * 查询景区是否已经有数据
     * @param vcode
     * @return
     */
    Integer getAreaData(String vcode);

    /**
     * 根据景区vcode查询景区表中地图中心点经纬度
     * @param vcode
     * @return
     */
    ScenicAreaVo getScenicArea(String vcode);
}