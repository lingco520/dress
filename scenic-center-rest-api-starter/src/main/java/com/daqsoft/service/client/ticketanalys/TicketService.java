package com.daqsoft.service.client.ticketanalys;


import com.daqsoft.vo.client.madeVoBean.*;

import java.util.List;

/**
 * @author zhouq
 * @version V1.0.0
 * @date 2017-07-27 11:12.
 * @description 票务分析 service 迁移后 最新版本.
 */
public interface TicketService {


    /**
     * 按天获取票务总数及金额
     *
     * @param vcode 景区代码
     * @param date  日期
     * @return
     */
    TicketClTendVo getTicketCountAndPriceByDay(String vcode, String date);


    /**
     * 按天获取票务总数及金额
     *
     * @param vcode     景区代码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    TicketClTendVo getTicketCountAndPriceByDayRange(String vcode, String startDate, String endDate);

    /**
     * 按月份获取票务总数及金额
     *
     * @param vcode 景区代码
     * @param month 月份
     * @return
     */
    TicketClTendVo getTicketCountAndPriceByMonth(String vcode, String month);

    /**
     * 按季度获取票务总数及金额
     *
     * @param vcode   景区代码
     * @param year    年
     * @param quarter 季度
     * @return
     */
    TicketClTendVo getTicketCountAndPriceByQuarter(String vcode, String year, String quarter);

    /**
     * 按年获取票务总数及金额
     *
     * @param vcode 景区代码
     * @param year  年
     * @return
     */
    TicketClTendVo getTicketCountAndPriceByYear(String vcode, String year);


    /**
     * 按年区间获取票务总数及金额
     *
     * @param vcode
     * @param start
     * @param end
     * @return
     */
    TicketClTendVo getTicketCountAndPriceByYearRange(String vcode, int start, int end);

    /**
     * 查询当天每小时票务数据
     *
     * @param vcode
     * @param date
     * @return
     */
    List<TicketClSsTendVO> getRealTimeTicketListByDay(String vcode, String date);

    /**
     * 查询当天每小时票务、金额数据
     *
     * @param vcode
     * @param date
     * @return
     */
    List<TicketPriceClSsTendVO> getRealTimeTicketAllListByDay(String vcode, String date);



    /**
     * 按月份获取每天票务总数 及金额
     *
     * @param vcode 景区代码
     * @param month 月份
     * @return
     */
    List<TicketClTendVo> getDayTicketListByMonth(String vcode, String month);

    /**
     * 按年份获取每月票务总数 及金额
     *
     * @param vcode 景区代码
     * @param year  年份
     * @return
     */
    List<TicketClTendVo> getMonthTicketListByYear(String vcode, String year);


    /**
     * 按年份获取每季度票务总数 及金额
     *
     * @param vcode
     * @param year
     * @return
     */
    List<TicketClTendVo> getQuarterTicketListByYear(String vcode, String year);

    /**
     * 获取传入时间段的数据按天进行统计返回
     *
     * @param vcode
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回对应时间段按天统计数据的list
     */
    List<TicketClTendVo> getTicketNumSlotGroupByDay(String vcode, String startTime, String endTime);

    /**
     * 获取传入时间段的数据按月进行统计返回
     *
     * @param vcode
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回对应时间段按月统计数据的list
     */
    List<TicketClTendVo> getTicketNumByTimeSlotGroupByMonth(String vcode, String startTime, String endTime);

    /**
     * 获取传入时间段的数据按年进行统计返回
     *
     * @param vcode
     * @param startYear 开始年份
     * @param endYear   结束年份
     * @return 返回对应时间段按年统计数据的list
     */
    List<TicketClTendVo> getTicketNumByTimeSlotGroupByYear(String vcode, int startYear, int endYear);

    /**
     * 根据年份获取景区票务数量
     *
     * @param vcode
     * @param year
     * @return 返回当前的票务数量及金额
     */
    TicketClTendVo getTicketNumByYear(String vcode, int year);


    //================================================================================


    /**
     * 2017-08-04 zhouq
     * 按天获取票务类型及金额数据 按照通用规则分类.
     *
     * @param vcode
     * @param day   天
     * @return
     */
    List<TicketClTypeVO> getTicketTypeListByDay(String vcode, String day);

    /**
     * 2017-09-06 zhouq
     * 按天获取票务类型及金额数据 数据按照实际票务类型进行分类统计
     *
     * @param vcode
     * @param day
     * @return
     */
    List<TicketClTypeVO> getTicketBaseTypeListByDay(String vcode, String day);


    /**
     * 2017-09-06 zhouq
     * 按天获取票务类型及金额数据 按照通用规则分类.
     *
     * @param vcode
     * @param month
     * @return
     */
    List<TicketClTypeVO> getTicketTypeListByMonth(String vcode, String month);

    /**
     * 2017-09-06 zhouq
     * 按天获取票务类型及金额数据 数据按照实际票务类型进行分类统计
     *
     * @param vcode
     * @param month
     * @return
     */
    List<TicketClTypeVO> getTicketBaseTypeListByMonth(String vcode, String month);

    /**
     * 查询指定年、月、日 的票务类型统计数据
     *
     * @param date  时间
     * @param vcode 景区代码
     * @param type  day/month/year
     * @return 返回按 日、月、年 统计的票务类型数据list
     */
    List<TicketClTypeVO> getTicketTypeList(String vcode, String date, String type);


    /**
     * 按年统计 票务类型数量及占比
     *
     * @param vcode 景区代码
     * @param year  年份 2017
     * @return 返回 年度票务类型销售数量及占比
     */
    List<JqticketSeal> getTicketTypesAndProportionByYear(String vcode, String year);


    /**
     * 按天区间 获取票务类型统计数据 按通用规则进行分类统计
     *
     * @param vcode
     * @param startDate
     * @param endDate
     * @return
     */
    List<TicketClTypeVO> getTicketTypeByDaySlot(String vcode, String startDate, String endDate);


    /**
     * 按天区间 获取票务类型统计数据 按原始票务类型进行分组
     *
     * @param vcode
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    List<TicketClTypeVO> getTicketBaseTypeByDaySlot(String vcode, String startDate, String endDate);

    /**
     * 查询 指定时间段 票务类型统计数据. 按通用规则进行分类统计
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param vcode 景区编码
     * @param type  类型 day/month/year
     * @return 返回按 日、月、年 区间 统计的票务类型数据list
     */
    List<TicketClTypeVO> getTicketTypeListBySlot(String vcode, String start, String end, String type);


    /**
     * 按年区间 获取 票务类型统计数据. 按照通用规则分类统计
     *
     * @param vcode
     * @param startYear
     * @param endYear
     * @return
     */
    List<TicketClTypeVO> getTicketTypeListByYearSlot(String vcode, int startYear, int endYear);

    /**
     * 按年区间 获取 票务类型统计数据.  按原始票务类型进行分组
     *
     * @param vcode
     * @param startYear
     * @param endYear
     * @return
     */
    List<TicketClTypeVO> getTicketBaseTypeListByYearSlot(String vcode, int startYear, int endYear);


    /**
     * 按季度获取票务类型统计数据 按照原始票务类型进行分类统计
     *
     * @param vcode   景区编码
     * @param year    年份
     * @param quarter 季度 1/2/3/4
     * @return 返回按年度中季度 的票务类型统计数据
     */
    List<TicketClTypeVO> getTicketBaseTypeListByQuarter(String vcode, String year, String quarter);

    /**
     * 按季度获取票务类型统计数据 数据按照通用规则进行分组统计
     *
     * @param vcode   景区编码
     * @param year    年份
     * @param quarter 季度 1/2/3/4
     * @return 返回按年度中季度 的票务类型统计数据
     */
    List<TicketClTypeVO> getTicketTypeListByQuarter(String vcode, String year, String quarter);


    /**
     * 按年份获取票务类型及金额数据 按照通用规则分类统计
     *
     * @param vcode
     * @param year
     * @return
     */
    List<TicketClTypeVO> getTicketTypeListByYear(String vcode, String year);

    /**
     * 按年获取票务类型统计数据 数据按照实际票务类型进行分类统计
     *
     * @param vcode 景区代码
     * @param year  年
     * @return
     */
    List<TicketClTypeVO> getTicketBaseTypeListByYear(String vcode, String year);

    //===========================================================================


    /**
     * 按天获取游客统计数据 按原始类型进行分组
     *
     * @param vcode
     * @param day   日期 格式: yyyy-MM-dd
     * @return
     */
    List<TicketClTypeVO> getTouristBaseTypeListByDay(String vcode, String day);




    /**
     * 按月份获取游客统计数据 按原始类型进行分组
     *
     * @param vcode
     * @param month 月份 格式: yyyy-MM
     * @return 返回月份游客构成
     */
    List<TicketClTypeVO> getTouristBaseTypeListByMonth(String vcode, String month);


    /**
     * 按季度获取游客统计数据 按原始类型进行分组
     *
     * @param vcode   景区代码
     * @param year    年份
     * @param quarter 季度 1/2/3/4
     * @return 返回季度游客构成
     */
    List<TicketClTypeVO> getTouristBaseTypeListByQuarter(String vcode, String year, String quarter);


    /**
     * 按年获取游客统计数据 按原始类型进行分组
     * @param vcode
     * @param year
     * @return
     */
    List<TicketClTypeVO> getTouristBaseTypeListByYear(String vcode, String year);

    /**
     * 按 指定时间 获取 游客构成统计（年月日）
     *
     * @param vcode 景区编码
     * @param type  类型 day/month/year
     * @param time  时间
     * @return 返回按 日、月、年统计的 游客构成统计数据
     */
    List<TicketClTypeVO> getTouristBaseTypeList(String vcode, String type, String time);


    /**
     * 按 指定时间段 获取 游客构成统计(年月日)
     *
     * @param start 开始
     * @param end   结束
     * @param vcode 景区编码
     * @param type  类型 day/month/year
     * @return 返回按 日、月、年 时间段 统计的 游客构成统计数据
     */
    List<TicketClTypeVO> getTouristBaseTypeListBySlot(String vcode, String type, String start, String end);


    /**
     * 2017-08-17 zhouq
     * 为兼容老版本.返回实体vo 为 JqticketGcSeal.
     * 获取大数据页面游客统计
     *
     * @param vcode
     * @param year
     * @return
     */
    List<JqticketGcSeal> getBigDateTouristTypeByYear(String vcode, String year);


}
