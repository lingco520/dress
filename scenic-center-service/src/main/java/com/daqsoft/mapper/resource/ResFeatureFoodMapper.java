package com.daqsoft.mapper.resource;

import com.daqsoft.entity.featureFood.ResFeatureFood;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Title: ResFeatureFoodMapper
 * @Author: tanggm
 * @Date: 2018/01/09 17:00
 * @Description: TODO
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Repository
public interface ResFeatureFoodMapper extends BaseCommonMapper<ResFeatureFood>{
    /**
     * 查询数据列表
     * @param name
     * @param vcode
     * @return
     */
    List<ResFeatureFood> selectList(@Param("name") String name, @Param("vcode") String vcode, @Param("status") Integer status);
}