package com.daqsoft.mapper.resource;

import com.daqsoft.entity.featurePlace.ResFeaturePlace;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Title: ResFeaturePlaceMapper
 * @Author: tanggm
 * @Date: 2018/01/22 16:00
 * @Description: TODO
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Repository
public interface ResFeaturePlaceMapper extends BaseCommonMapper<ResFeaturePlace>{
    /**
     * 查询数据列表
     * @param name
     * @param vcode
     * @return
     */
    List<ResFeaturePlace> selectList(@Param("name") String name, @Param("vcode") String vcode, @Param("status") Integer status);
}