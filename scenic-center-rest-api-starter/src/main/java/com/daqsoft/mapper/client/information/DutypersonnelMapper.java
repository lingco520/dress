package com.daqsoft.mapper.client.information;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author tanggm
 * @version V3.0.0
 * @date 2017-07-14 17:30
 * @Description
 */
@Repository
public interface DutypersonnelMapper {
    /**
     * 景区值班人员
     *
     * @param ids
     * @return
     */
    List<Map> getDutypersonnel(String[] ids);

    /**
     * 查询值班人员id
     * @param vcode
     * @return
     */
    String getDutyPersonId(String vcode);
}
