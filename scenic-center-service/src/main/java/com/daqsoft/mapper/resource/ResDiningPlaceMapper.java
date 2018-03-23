package com.daqsoft.mapper.resource;


import com.daqsoft.entity.diningPlace.ResDiningPlace;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: 餐饮场所
 * @Author: superziy .
 * @Date: Created in 10:10 2018/1/10.
 * @Version: 4.0.0
 * @describe: 餐饮场所
 * @since:JDK 1.8
 */
@Repository
public interface ResDiningPlaceMapper extends BaseCommonMapper<ResDiningPlace>{

    /**
     * 查询列表数据
     *
     * @param map
     * @return
     */
    List<ResDiningPlace> selectList(Map map);
}