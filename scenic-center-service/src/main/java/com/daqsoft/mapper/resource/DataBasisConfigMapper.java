package com.daqsoft.mapper.resource;

import com.daqsoft.entity.dataconfig.DataBasisConfig;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DataBasisConfigMapper extends BaseCommonMapper<DataBasisConfig> {

    /**
     * 查询景区停车场列表
     * @param vcode
     * @return
     */
    List<Map> getParkingList(@Param("vcode") String vcode);

    /**
     * 查询停车场信息
     * @param parkingId 停车场ID
     * @return
     */
    Map getParkingById(@Param("parkingId") Long parkingId);

    /**
     * 通过vcode查询数据记录
     * @param vcode
     */
    DataBasisConfig findByVcode(@Param("vcode") String vcode);
}