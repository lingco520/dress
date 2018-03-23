package com.daqsoft.mapper.client.scenicspotinfo;

import com.daqsoft.vo.client.madeVoBean.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 客流量分析
 * @author fy
 *
 */
@Repository
public interface BigRealPeopleDao {
    /**
     * 客流分析 团散 日
     */
    List<Citigroup_cl> find_ts_date_day(Map paramMap);
    /**
     * 客流分析 团散 日
     */
    List<Citigroup_cl> findSumTeamPeople_day(Map paramMap);

    /**
     * 客流分析 团散 月
     */
    List<Citigroup_cl> find_ts_date_mm(Map paramMap);

    /**
     * 客流分析 团散 月
     */
    List<Citigroup_cl> findSumTeamPeople_month(Map paramMap);

    /**
     * 客流分析 团散 年
     */
    List<Citigroup_cl> find_ts_date_year(Map paramMap);

    /**
     * 客流分析 团散 年
     */
    List<Citigroup_cl> findNotTeamPeople(Map paramMap);

    /**
     * 客流分析 团散 季度
     */
    List<Citigroup_cl> find_ts_date_jidu(Map paramMap);

    /**
     * 客流分析 团散 季度
     */
    List<Citigroup_cl> findSumTeamPeople_jidu(Map paramMap);

    /**
     * 客流分析 散客 日
     */
    List<Citigroup_cl> find_sk_date_day(Map paramMap);
    /**
     * 客流分析 散客 日
     */
    List<Citigroup_cl> findNotTeamPeople_day(Map paramMap);

    /**
     * 客流分析 散客 月
     */
    List<Citigroup_cl> find_sk_date_mm(Map paramMap);
    /**
     * 客流分析 散客 月
     */
    List<Citigroup_cl> findNotTeamPeople_month(Map paramMap);

    /**
     * 客流分析 散客 年
     */
    List<Citigroup_cl> find_sk_date_year(Map paramMap);

    /**
     * 客流分析 散客 年
     */
    List<Citigroup_cl> findSumPeople(Map paramMap);

    /**
     * 客流分析 散客 季度
     */
    List<Citigroup_cl> find_sk_date_jidu(Map paramMap);

    /**
     * 客流分析 散客 季度
     */
    List<Citigroup_cl> findNotTeamPeople_jidu(Map paramMap);

    /**
     * 根据年份筛选  日中最大销售金额的在峰谷值
     *
     * @param paramMap
     * @return
     */
     List<Big_Ticket_Money> find_money_date_dd_max(Map paramMap);

    /**
     * 根据年份筛选  日中最小销售金额的在峰谷值
     *
     * @param paramMap
     * @return
     */
     List<Big_Ticket_Money> find_money_date_dd_min(Map paramMap);

    /**
     * 根据年份筛选  月中最大销售金额的在峰谷值
     *
     * @param paramMap
     * @return
     */
     List<Big_Ticket_Money> find_money_date_yy_max(Map paramMap);

    /**
     * 根据年份筛选  月中最小销售金额的在峰谷值
     *
     * @param paramMap
     * @return
     */
     List<Big_Ticket_Money> find_money_date_yy_min(Map paramMap);


    /**
     * 全屏导航页
     * @param paramMap
     * @return
     */
     List<Home_page> find_home_page(Map paramMap);
    /**
     * 分段客流趋势
     * @param paramMap
     * @return
     */
	 List<Big_RealP_Tend> fundAll_date_end(Map paramMap);
	 List<Big_Ticket_Money> find_money_date(Map<String, String> map);

    /**
     * 大数据客流来源分析
     * @param map
     * @return
     */
    List<CarComeFromVo> getBigPeopleYear(Map map);

    /**
     * 获取景区所在省份的
     * @param parMap
     * @return
     */
    List<CarComeFromCarVo> getBigPeopleYearCity(Map<String, Object> parMap);

    /***
     * 每年季度客流趋势
     * @param map
     * @return
     */
    Map<String,Object> find_quarter_date(Map map);

    /**
     * 每年月度客流趋势
     * @param map
     * @return
     */
    List<Big_RealP_Tend> fundAll_date(Map map);

    /***
     * 每日客流趋势
     * @param map
     * @return
     */
    List<Big_RealP_Tend> find_oneday_date(Map map);

    /***
     * 每月客流趋势
     * @param map
     * @return
     */
    List<Big_RealP_Tend> fundAll_datebymonth(Map map);
}
