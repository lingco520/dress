package com.daqsoft.service.client.ticketanalys;


import com.daqsoft.vo.client.madeVoBean.TicketClTendVo;
import com.daqsoft.vo.client.madeVoBean.TicketClTypeVO;

import java.util.List;
import java.util.Map;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-09-01 9:05
 * @Version: v1.0.0
 * @Describe: 票务分析 OTA(线上)微件 service
 */
public interface TicketOtaAndOnlineService {


    /**
     * 按天获取线上票务销售数量及金额
     *
     * @param vcode 景区编码
     * @param day   日期
     * @return 返回当日票务数量及金额
     */
    TicketClTendVo getOnlineTicketByDay(String vcode, String day);


    /**
     * 按年获取线上票务销售数量及金额
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param vcode     景区编码
     * @return
     */
    TicketClTendVo getOnLineTicketBYDayRange(String vcode, String startTime, String endTime);

    /**
     * 按月份获取线上票务销售数量及金额
     *
     * @param vcode 景区编码
     * @param month 月份
     * @return 返回当月票务数量及金额
     */
    TicketClTendVo getOnlineTicketByMonth(String vcode, String month);


    /**
     * 按季度获取线上票务销售数量及金额
     *
     * @param vcode   景区编码
     * @param year    月份
     * @param quarter 季度
     * @return 返回当月票务数量及金额
     */
    TicketClTendVo getOnlineTicketByQuarter(String vcode, String year, String quarter);


    /**
     * 按年获取线上票务销售数量及金额
     *
     * @param vcode 景区编码
     * @param year  月份
     * @return 返回当月票务数量及金额
     */
    TicketClTendVo getOnlineTicketByYear(String vcode, String year);


    /**
     * 按年份获取  线上 各月份票务销售数据.
     * @param vcode
     * @param year
     * @return
     */
    List<TicketClTendVo> getMonthsOfOnlineTicketListByYear(String vcode, String year);


    /**
     * 按年获取线上票务销售数量及金额
     *
     * @param vcode 景区编码
     * @param start
     * @param end  
     * @return 返回当月票务数量及金额
     */
    TicketClTendVo getOnlineTicketByYearRange(String vcode, int start, int end);


    
    /**
     * 按天 获取 各ota 销售情况
     *
     * @param vcode
     * @param day   日期 格式:yyyy-MM-dd
     * @return
     */
    List<TicketClTypeVO> getOtaSaleListByDay(String vcode, String day);


    /**
     * 按天区间获取 各OTA 销售数据
     *
     * @param vcode
     * @param startDate 开始日期 格式:yyyy-MM-dd
     * @param endDate   结束日期 格式:yyyy-MM-dd
     * @return
     */
    List<TicketClTypeVO> getOtaSaleListByDayRange(String vcode, String startDate, String endDate);


    /**
     * 按月份 获取 各ota 销售情况
     *
     * @param vcode
     * @param month 月份 格式:yyyy-MM
     * @return
     */
    List<TicketClTypeVO> getOtaSaleListByMonth(String vcode, String month);


    /**
     * 按季度获取 各ota 销售情况
     *
     * @param vcode   景区编码
     * @param year    年份
     * @param quarter 季度 1/2/3/4
     * @return 返回按年度中季度 的票务类型统计数据
     */
    List<TicketClTypeVO> getOtaSaleListByQuarter(String vcode, String year, String quarter);

    /**
     * 按年 获取 各ota 销售情况
     *
     * @param vcode
     * @param year  年 格式:yyyy
     * @return
     */
    List<TicketClTypeVO> getOtaSaleListByYear(String vcode, int year);

    /**
     * 按年区间 获取 各ota 销售情况
     *
     * @param vcode
     * @param year  年 格式:yyyy
     * @return
     */
    List<TicketClTypeVO> getOtaSaleListByYearRange(String vcode, int start, int end);

    /**
     * ota数据全年分类统计
     *
     * @param vcode
     * @param year
     * @return
     */
    List<Map> otaSaleByYear(String vcode, int year);

    /**
     * ota数据去年今年季度分类统计
     *
     * @param vcode
     * @param year
     * @return
     */
    List<Map> otaSaleByQuarter(String vcode, String year);


}
