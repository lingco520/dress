package com.daqsoft.mapper.resource;


import com.daqsoft.entity.scenicSpots.ResScenicSpots;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: 景点信息
 * @Author: superziy .
 * @Date: Created in 10:10 2018/1/10.
 * @Version: 4.0.0
 * @describe:景点信息
 * @since:JDK 1.8
 */
@Repository
public interface ResScenicSpotsMapper extends BaseCommonMapper<ResScenicSpots>{

    /**
     * 查询列表数据
     *
     * @param map
     * @return
     */
    List<ResScenicSpots> selectList(Map map);
}