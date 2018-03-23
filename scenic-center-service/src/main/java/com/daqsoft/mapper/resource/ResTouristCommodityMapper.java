package com.daqsoft.mapper.resource;


import com.daqsoft.entity.touristCommodity.ResTouristCommodity;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Title: ResTouristCommodityMapper
 * @Author: tanggm
 * @Date: 2018/01/09 17:00
 * @Description: TODO 基础资源-旅游商品
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Repository
public interface ResTouristCommodityMapper extends BaseCommonMapper<ResTouristCommodity>{
    /**
     * 查询列表数据
     * @param name
     * @return
     */
    List<ResTouristCommodity> selectList(@Param("name") String name, @Param("status") Integer status, @Param("vcode") String vcode);
}