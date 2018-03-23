package com.daqsoft.mapper.resource;


import com.daqsoft.entity.entertainment.ResEntertainment;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: 娱乐场所
 * @Author: superziy .
 * @Date: Created in 10:10 2018/1/10.
 * @Version: 4.0.0
 * @describe:娱乐场所
 * @since:JDK 1.8
 */
@Repository
public interface ResEntertainmentMapper extends BaseCommonMapper<ResEntertainment>{

    /**
     * 查询列表数据
     *
     * @param map
     * @return
     */
    List<ResEntertainment> selectList(Map map);
}