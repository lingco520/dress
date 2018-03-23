package com.daqsoft.mapper.client.ticket;

import com.daqsoft.vo.client.madeVoBean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-06 15:45.
 */
@Repository
public interface TicketBigDao {

    /**
     * 获取每天的实时票务数
     * @param paramMap
     * @return
     */
    List<TicketClSsTendVO> getRealTimeData(Map<String, String> paramMap);

    /**
     * 通过传入时间段获取对应景区该时间的数据，
     * @param vcode
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param dateFormat 用于指定获取的数据是按照什么时间类型进行分组 值可以为：%Y   %Y-%m  %Y-%m-%d  这种格式，用于对数据库中时间进行format
     * @return 返回列表数据
     */
    List<TicketClTendVo> getBigTicketNumByTimeSlot(@Param("vcode") String vcode, @Param("startTime") String startTime,
                                                   @Param("endTime") String endTime, @Param("dateFormat") String dateFormat);

    /**
     * 获取线下票务类型按时间段统计数据
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
     * 客流分析——团队按天分析分页
     * @param paramMap
     * @return
     */
    List<Tours_cl_Tend> find_Tours_limit_dd(Map paramMap);

    /**
     * 客流分析——团队按月分析分页
     * @param paramMap
     * @return
     */
    List<Tours_cl_Tend> find_Tours_limit_mm(Map paramMap);

    /**
     * 客流分析——团队按年分析分页
     * @param paramMap
     * @return
     */
    List<Tours_cl_Tend> find_Tours_limit_yy(Map paramMap);

    /**
     * 查询总条数  日
     * @param paramMap
     * @return
     */
    Integer count_tours_dd(Map paramMap);

    /**
     * 查询总条数 月
     * @param paramMap
     * @return
     */
    Integer count_tours_mm(Map paramMap);

    /**
     * 查询总条数  年
     * @param paramMap
     * @return
     */
    Integer count_tours_yy(Map paramMap);


    /**
     * 团队总数 与 游客总数  日
     * @return
     */
    List<Tours_cl_total> find_Tours_cl_total_dd(Map paramMap);

    /**
     * 团队总数 与 游客总数  月
     * @return
     */
    List<Tours_cl_total> find_Tours_cl_total_mm(Map paramMap);

    /**
     * 团队总数 与 游客总数  年
     * @return
     */
    List<Tours_cl_total> find_Tours_cl_total_yy(Map paramMap);

    /**
     * 团队趋势  日
     * @return
     */
    List<Tours_cl_tendency>find_Tours_cl_tendency_dd(Map paramMap);


    /**
     * 团队趋势  月
     * @return
     */
    List<Tours_cl_tendency>find_Tours_cl_tendency_mm(Map paramMap);

    /**
     * 团队趋势   年
     * @return
     */
    List<Tours_cl_tendency>find_Tours_cl_tendency_yy(Map paramMap);


    /**
     * ota在线票务统计按天
     * @param map
     * @return
     */
    List<Ota_Date> otaSaleByDay(Map map);
    /**
     * ota在线票务统计按月
     * @param map
     * @return
     */
    List<Ota_Date> otaSaleByMonth(Map map);
    /**
     * ota在线票务统计按年
     * @param map
     * @return
     */
    List<Ota_Date> otaSaleByYear(Map map);
    /**
     * ota在线票务统计按季度
     * @param map
     * @return
     */
    List<Ota_Date> otaSaleByQuarter(Map map);

    /**
     * 票务分析线下
     * @param map
     * @return
     */
    List<JqticketXxSeal> offLine(Map map);
    /**
     * 票务分析线上
     * @param map
     * @return
     */
    List<JqticketXsSeal> onOffLine(Map map);
    /**
     * 票务分析线下月
     * @param map
     * @return
     */
    List<JqticketXxSeal> offLineMonth(Map map);

    /**
     * 票务分析线上月
     * @param map
     * @return
     */
    List<JqticketXsSeal> onOffLineMonth(Map map);

    /**
     * 票务分析线下年
     * @param map
     * @return
     */
    List<JqticketXxSeal> offLineYear(Map map);
    /**
     * 票务分析线年
     * @param map
     * @return
     */
    List<JqticketXsSeal> onOffLineYear(Map map);

    /**
     * 票务分析线下季度
     * @param map
     * @return
     */
    List<JqticketXxSeal> offLineQuarter(Map map);

    /**
     * 票务分析线上季度
     * @param map
     * @return
     */
    List<JqticketXsSeal> onOffLineQuarter(Map map);

    /**
     * 获取实时票务
     * @param vcode
     * @return
     */
    List<JqTicket_Tend> findJqTicketNow(String vcode);

    /**
     * 获取实时线上和线下票务数据
     * @param vcode
     * @return
     */
    List<JqTicket> findJqTicketOnLine(String vcode);

    /**
     * 查询除华蓥山以外的景区票务数据
     * @param vcode
     * @return
     */
    List<JqTicket> findJqTicketOnLineOtherScreen(String vcode);

    /**
     * 按天查询团队数列表
     * @param parMap
     * @return
     */
    List<ToursVo> findToursByDay(Map parMap);

    /**
     * 按天查询团队总数
     * @param parMap
     * @return
     */
    int findToursNumsByDay(Map parMap);

    /**
     * 按天查询团队总人数
     * @param parMap
     * @return
     */
    int findToursPeopleNumsByDay(Map parMap);
}
