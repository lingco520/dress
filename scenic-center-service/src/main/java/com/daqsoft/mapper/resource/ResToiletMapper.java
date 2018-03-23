package com.daqsoft.mapper.resource;

import com.daqsoft.entity.toilet.Toilet;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * @Title: 景区基础资源-厕所
 * @Author: lrd
 * @Date: Created in 10:10 2018/1/22.
 * @Version: 4.0.0
 * @describe:景区基础资源-厕所
 * @since:JDK 1.8
 */
@Repository
public interface ResToiletMapper extends BaseCommonMapper<Toilet> {
    List<Toilet> selectList(Map map);
}