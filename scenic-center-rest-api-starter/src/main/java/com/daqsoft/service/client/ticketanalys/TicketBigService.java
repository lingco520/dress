package com.daqsoft.service.client.ticketanalys;


import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.CutPageBean;
import com.daqsoft.vo.client.mysqlBean.JqTicketOnline_Tend;
import com.daqsoft.vo.client.mysqlBean.Ota_DateSale_Tend;
import com.daqsoft.vo.client.mysqlBean.Ota_DateSeal;

import java.util.List;
import java.util.Map;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-06 15:37.
 */
public interface TicketBigService {

    /**
     * 查询当天每小时票务数据
     *
     * @param vcode
     * @param date
     * @return
     */
    List<TicketClSsTendVO> getRealTimeTicketByDay(String vcode, String date);

    /**
     * 根据传入类型统计票务（年月日）
     *
     * @param date
     * @param vcode
     * @param type
     * @return
     */
    List<TicketClTendVo> fundAll_date(String date, String vcode, String type);

    /**
     * 根据开始和结束时间查询对应type的统计票数
     *
     * @param date  date根据对应数据 传入相应参数 如month：2017-06
     * @param vcode
     * @param type  day/month/year
     * @param end
     * @return
     */
    List<TicketClTendVo> fundAll_date_end(String date, String vcode, String type, String end);

    /**
     * 查询每年季度票务趋势
     *
     * @param date
     * @param vcode
     * @return
     */
    List<TicketClTendVo> find_quarter_date(String date, String vcode);


//    List<CutPageBean> find_Tours_limit(String page, String date, String vcode, String type);

    /**
     * 查询指定月份票务类型统计数据
     *
     * @param date
     * @param vcode
     * @param type
     * @return
     */
    List<TicketClTypeVO> find_Ticket_cl_Type_s(String date, String vcode, String type);

    /**
     * 查询指定时间段票务统计数据
     *
     * @param date
     * @param end
     * @param vcode
     * @param type
     * @return
     */
    List<TicketClTypeVO> find_Ticket_cl_Type_se(String date, String end, String vcode, String type);

    /**
     * 查询指定季度票务统计数据
     *
     * @param date
     * @param vcode
     * @param type
     * @return
     */
    List<TicketClTypeVO> find_Ticket_cl_Type_quarter(String date, String vcode, String type);

    /**
     * 查询指定时间游客构成统计（年月日）
     *
     * @param date
     * @param type
     * @param vcode
     * @return
     */
    List<TicketClTypeVO> find_tourist_cl_Type_s(String date, String type, String vcode);


    /**
     * 查询指定时间段游客构成统计
     *
     * @param date
     * @param end
     * @param vcode
     * @param type
     * @return
     */
    List<TicketClTypeVO> find_tourist_cl_Type_se(String date, String end, String vcode, String type);

    /** 按季度查询 游客构成统计
     * @param year 年份
     * @param vcode 景区代码
     * @param quarter 季度
     * @return
     */
    List<TicketClTypeVO> find_tourist_cl_Type_quarter(String year, String vcode, String quarter);


    /**
     * 团队总数与游客总数
     * 按日
     * @param day
     * @param vcode
     * @return
     */
    List<Tours_cl_total> findToursTotalByDay(String day, String vcode);


    /**
     * 团队总数与游客总数
     * 按月
     * @param month
     * @param vcode
     * @return
     */
    List<Tours_cl_total> findToursTotalByMonth(String month, String vcode);


    /**
     * 团队总数与游客总数
     * 按年
     * @param year
     * @param vcode
     * @return
     */
    List<Tours_cl_total> findToursTotalByYear(String year, String vcode);



    /**
     * 团队趋势--日
     * @param day
     * @param vcode
     * @return
     */
    List<Tours_cl_tendency> findToursTendencyByDay(String day, String vcode);

    /**
     * 团队趋势--月份
     *  find_Tours_cl_tendency
     * @param month
     * @param vcode
     * @return
     */
    List<Tours_cl_tendency> findToursTendencyByMonth(String month, String vcode);

    /**
     * 团队趋势--年份
     *  find_Tours_cl_tendency
     * @param year
     * @param vcode
     * @return
     */
    List<Tours_cl_tendency> findToursTendencyByYear(String year, String vcode);

    /**
     * 客流分析——团队分析分页
     * 按日
     */
    List<CutPageBean> findToursLimitByDay(Integer page, String day, String vcode);


    /**
     * 客流分析——团队分析分页
     * 按月
     */
    List<CutPageBean> findToursLimitByMonth(Integer page, String month, String vcode);

    /**
     * 客流分析——团队分析分页
     * 按年
     */
    List<CutPageBean> findToursLimitByYear(Integer page, String year, String vcode);

    /**
     * OTA销售按季度查询
     *
     * @param time
     * @param season
     * @param vcode
     * @return
     */
    List<Ota_DateSale_Tend> otaSaleByQuarter(String time, String season, String vcode);

    /**
     * 票务OTA销售日
     *
     * @param startTime
     * @param endTime
     * @param vcode
     * @return
     */
    List<Ota_DateSale_Tend> otaSaleByDay(String startTime, String endTime, String vcode);

    /**
     * 票务OTA销售月
     *
     * @param time
     * @param vcode
     * @return
     */
    List<Ota_DateSale_Tend> otaSaleByMonth(String time, String vcode);

    /**
     * 票务OTA销售年
     *
     * @param startYear
     * @param endYear
     * @param vcode
     * @return
     */
    List<Ota_DateSale_Tend> otaSaleByYear(String startYear, String endYear, String vcode);

    /**
     * 票务分析线上线下日
     */
    Map<String, Object> onLineOffLine(String startTime, String endTime, String vcode);

    /**
     * 票务分析线上线下月
     */
    Map<String, Object> onLineOffLineMonth(String startTime, String vcode);

    /**
     * 票务分析线上线下年
     */
    Map<String, Object> onLineOffLineYear(String startTime, String endTime, String vcode);

    /**
     * 票务分析线上线下季度
     */
    Map<String, Object> onLineOffLineQuarter(String startTime, String vcode, String type);

    /**
     * 主页总票数
     *
     * @param vcode
     * @return
     */
    List<JqTicket_Tend> findJqTicketNow(String vcode);

    /**
     * 线上
     *
     * @param vcode
     * @return
     */
    List<JqTicketOnline_Tend> findJqTicketOnLine(String vcode);

    /**
     * OTA销售分析 按年份
     *
     * @param date  2017
     * @param vcode
     * @return
     */
    List<Ota_DateSeal> queryYears(String vcode, String date);

    /**
     *
     * 按年统计 票务类型数量及占比
     *
     * @param vcode
     * @param year  年份 2017
     * @return
     */
    List<JqticketSeal> getTicketTypeAndProportionByYear(String vcode, String year);

    /**
     * 获取游客构成统计按年统计
     *
     * @param vcode
     * @param date  日期 2017
     * @return
     */
    List<JqticketGcSeal> touristList(String vcode, String date);


    /**
     * 获取大数据票分析——线上线下对比
     *
     * @param vcode
     * @param date  日期 2017
     * @return
     */
    Map<String, Object> getOnlineAndOffline(String vcode, String date);


    /**
     * 根据年份获取景区票务数量
     * @param vcode
     * @param year
     * @return
     */
    TicketClTendVo getBigTicketNumByYear(String vcode, int year);

    /**
     * 票务分析-线上线下对比，通过不用的时间维度查询景区在ota上的销量数据
     * @param paramMap
     * @return
     */
    List<Map> getOnlineOfflineTikCompare(Map<String, Object> paramMap);

    /**
     * 查询团队信息
     * @param day
     * @param vcode
     * @return
     */
    Map findToursByDay(String day, String vcode) throws Exception;
}
