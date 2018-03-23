package com.daqsoft.mapper.resource;


import com.daqsoft.entity.guide.ResGuide;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * @Title: ResGuideMapper
 * @Author: tanggm
 * @Date: 2018/01/09 17:00
 * @Description: TODO 基础资源-导游信息
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Repository
public interface ResGuideMapper extends BaseCommonMapper<ResGuide>{
    /**
     * 查询数据列表
     * @param parMap
     * @return
     */
    List<ResGuide> selectList(Map parMap);
}