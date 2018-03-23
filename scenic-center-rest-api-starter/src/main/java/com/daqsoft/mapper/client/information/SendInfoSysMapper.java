package com.daqsoft.mapper.client.information;


import com.daqsoft.vo.client.madeVoBean.JqSendInfoSystem;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface SendInfoSysMapper {
    /**
     * 查询信息发布列表
     * @param map
     * @return
     */
    JqSendInfoSystem sendInfoList(Map map);


}