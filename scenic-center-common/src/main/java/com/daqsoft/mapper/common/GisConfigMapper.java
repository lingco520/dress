package com.daqsoft.mapper.common;

import com.daqsoft.vo.ScenicGisConfigVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: GisConfigMapper
 * @Author: superziy .
 * @Date: Created in 10:53 2018/1/30.
 * @Version: 4.0.0
 * @describe: supermap mapper
 * @since:JDK 1.8
 */
@Repository
public interface GisConfigMapper {


    /**
     * 通过vcode查询gis地图配置
     * @param region
     * @return
     */
    ScenicGisConfigVo getGisConfig(String region);
}