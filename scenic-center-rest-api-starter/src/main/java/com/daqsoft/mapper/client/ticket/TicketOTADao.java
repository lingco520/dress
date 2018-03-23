package com.daqsoft.mapper.client.ticket;

import com.daqsoft.vo.client.madeVoBean.TicketClTendVo;
import com.daqsoft.vo.client.madeVoBean.TicketClTypeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-09-01 9:26
 * @Version:
 * @Describe:
 */
@Repository
public interface TicketOTADao {

    /**
     * 获取当前ota总数及金额
     * @param vcode 景区代码
     * @return
     */
    TicketClTendVo getOtaSale(@Param("vcode") String vcode, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 获取各OTA 销售数据
     * @param vcode
     * @param startTime
     * @param endTime
     * @return
     */
    List<TicketClTypeVO> getOtaSaleListByTimeSlot(@Param("vcode") String vcode, @Param("startTime") String startTime, @Param("endTime") String endTime);



}
