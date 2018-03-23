package com.daqsoft.mapper.resource;

import com.daqsoft.entity.krpano.Krpano;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface MediaKrpanoMapper extends BaseCommonMapper<Krpano> {
    /**
     * 全景列表数据查询
     * @param map
     * @return
     */
    List<Krpano> selectList(Map map);
}