package com.daqsoft.mapper.client.ticket;

import com.daqsoft.vo.client.madeVoBean.TicketClSsTendVO;
import com.daqsoft.vo.client.madeVoBean.TicketClTendVo;
import com.daqsoft.vo.client.madeVoBean.TicketClTypeVO;
import com.daqsoft.vo.client.madeVoBean.TicketPriceClSsTendVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zhouq
 * @version V3.0.0
 * @date 2017-07-27 11:12.
 * @description 票务分析微件 dao
 */
@Repository
public interface TicketDao {

    /**
     * 获取当前票务总数及金额
     * @param vcode 景区代码
     * @return
     */
    TicketClTendVo getTicketCountAndPriceByDay(@Param("vcode") String vcode, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询当天每小时票务数据
     * @param vcode 景区编码
     * @param date 日期
     * @return
     */
    List<TicketClSsTendVO> getRealTimeTicketListByDay(@Param("vcode") String vcode, @Param("date") String date);

    /**
     * 查询当天每小时票务、金额数据
     * @param vcode 景区编码
     * @param date 日期
     * @return
     */
    List<TicketPriceClSsTendVO> getRealTimeTicketAllListByDay(@Param("vcode") String vcode, @Param("date") String date);


    /**
     * 通过传入时间段获取对应景区该时间的数据，
     * @param vcode
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param dateFormat 用于指定获取的数据是按照什么时间类型进行分组 值可以为：%Y   %Y-%m  %Y-%m-%d  这种格式，用于对数据库中时间进行format
     * @return 返回列表数据
     */
    List<TicketClTendVo> getTicketNumByTimeSlot(@Param("vcode") String vcode, @Param("startTime") String startTime,
                                                @Param("endTime") String endTime, @Param("dateFormat") String dateFormat);


    /**
     * 获取票务类型按时间段统计数据
     * @param vcode
     * @param startTime
     * @param endTime
     * @return
     */
    List<TicketClTypeVO> getTicketTypeByTimeSlot(@Param("vcode") String vcode, @Param("startTime") String startTime,
                                                 @Param("endTime") String endTime);

    /**
     * 获取游客构成数据统计按传入时间段
     * @param vcode
     * @param startTime
     * @param endTime
     * @return
     */
    List<TicketClTypeVO> getTouristNumByTimeSlot(@Param("vcode") String vcode, @Param("startTime") String startTime,
                                                 @Param("endTime") String endTime);



    /**
     * 线上票务数据
     * @param map
     * @return
     */
    Map getOnLineByDay(Map map);

    /**
     * 线下票务数据
     * @param map
     * @return
     */
    Map getOffLineByDay(Map map);
}
