package com.daqsoft.mapper.resource;


import com.daqsoft.entity.agritainment.ResAgritainment;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: 乡村旅游
 * @Author: superziy .
 * @Date: Created in 10:10 2018/1/10.
 * @Version: 4.0.0
 * @describe: 乡村旅游
 * @since:JDK 1.8
 */
@Repository
public interface ResAgritainmentMapper extends BaseCommonMapper<ResAgritainment>{

    /**
     * 查询列表数据
     *
     * @param map
     * @return
     */
    List<ResAgritainment> selectList(Map map);
}