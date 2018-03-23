package com.daqsoft.service.client.passengerFlowService;

import com.daqsoft.vo.client.madeVoBean.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/28 0028. 客流分析——年月日
 */
public interface RealBigPeopleService {

//	/**
//	 * 客流分析 团队 季度
//	 */
//	Object find_ts_date_jidu(String date, String vcode, String type);
//
//	/**
//	 * 客流分析 团队 季度
//	 */
//    Object findSumTeamPeople_jidu(String date, String vcode, String type);
//
//	/**
//	 * 客流分析 散客 季度
//	 */
//    Object find_sk_date_jidu(String date, String vcode, String end);
//
//	/**
//	 * 客流分析 散客 季度
//	 */
//    Object findNotTeamPeople_jidu(String date, String vcode, String end);
//
//	/**
//	 * 客流分析 团队 日
//	 */
//    Object find_ts_date(String date, String vcode, String end);
//
//	/**
//	 * 客流分析 团队 日
//	 */
//    Object findSumTeamPeople_day(String date, String vcode, String end);
//
//	/**
//	 * 客流分析 团队 月
//	 */
//    Object find_ts_date_mm(String date, String vcode);
//
//	/**
//	 * 客流分析 团队 月
//	 */
//	Object findSumTeamPeople_month(String date, String vcode);
//
//	/**
//	 * 客流分析 团队 年
//	 */
//    Object find_ts_date_year(String date, String vcode, String end);
//
//	/**
//	 * 客流分析 团队 年
//	 */
//    Object findNotTeamPeople(String date, String vcode, String end);
//
//	/**
//	 * 客流分析 散客 年
//	 */
//    Object findSumPeople(String date, String vcode, String end);
//
//	/**
//	 * 客流分析 散客 年
//	 */
//    Object find_sk_date_year(String date, String vcode, String end);
//
//	/**
//	 * 客流分析 散客 日
//	 */
//    Object find_sk_date(String date, String vcode, String end);
//
//	/**
//	 * 客流分析 散客 日
//	 */
//    Object findNotTeamPeople_day(String date, String vcode, String end);
//
//	/**
//	 * 客流分析 散客 月
//	 */
//    Object find_sk_date_mm(String date, String vcode);
//
//	/**
//	 * 客流分析 散客 月
//	 */
//    Object findNotTeamPeople_month(String date, String vcode);

	/**
	 * 按年月日不同时段查询客流人次
	 *
	 * @return
	 */
	Object getPassengerFlowByMonth(String date, String vcode);

	/**
	 * 按年月日时间段查询-按天
	 */
	List<Big_RealP_Tend> getPassengerFlowByDay(String start, String vcode, String end);

	/**
	 * 按年月日时间段查询-按年
	 */
	List<Map> getPassengerFlowByYear(String start, String vcode, String end);

	/**
	 * 按季度查询
	 */
	Object getPassengerFlowByQuarter(String year, String vcode);

	/**
	 * 全屏导航页
	 *
	 * @param vcode
	 * @return
	 */
	List<Home_page> find_home_page(String vcode);

	/**
	 * 根据年份筛选日期中最大人数峰值与最小人数峰值
	 */
	List<Big_RealP_Tend> findPeakByDay(Map map);


	/**
	 * 根据年份筛选月份中的最人数峰值与最小人数峰值
	 */
	List<Big_RealP_Tend> findPeakByMonth(Map map);

	/**
	 * 根据时间段筛选历史客流最高的10天
	 * @param start
	 * @param vcode
	 * @param end
	 * @return
	 */
	List<Big_RealP_Tend> findHistoryPassengerByDay(String start, String vcode, String end);

	List<Big_Ticket_Money> findMoneyByDay(Map<String, String> map);

	List<Big_Ticket_Money> findMoneyByMonth(Map<String, String> map);

	/**
	 * 每日客流趋势
	 * 
	 * @param date
	 * @param vcode
	 * @return
	 */
	List<Tend> getPassengerFlowByHours(String date, String vcode);

	/**
	 * 大数据客流来源分析
	 * @param vcode 景区编码
	 * @param year 年份
	 * @return
	 */
	List<CarComeFromVo> getBigPeopleYear(String vcode, String year);

	/**
	 * 通过vcode查询所在省份客流分析
	 * @param parMap
	 * @return
	 */
	List<CarComeFromCarVo> getBigPeopleYearCity(Map<String, Object> parMap);
	/**
	 * 实时客流监测 景区舒适度分析
	 * @param vcode
	 * @return
	 * @Comment:
	 */
	Map<String,Object> find_oneday_dateAndReal(String date, String vcode);


	/***
	 * 每月客流趋势
	 * @update lrd 20170920
	 * @param vcode
	 * @return
	 */
	Object getPassengerFlowByMonth_day(String month, String vcode) ;

	/**
	 * 获取景区年实时人数
	 * @param vcode
	 * @return
	 */
	int  getYearPeople(String vcode);
	/**
	 * 获取景区日实时人数
	 * @param vcode
	 * @return
	 */
	int  dayPeople(String vcode);


	/**
	 * 获取景区节假日人数
	 * 高频率
	 * @param vcode
	 * @return
	 */
	int holidayPeople(String vcode);

	/**
	 * 七个法定节假日游客数
	 * @param vcode
	 * @return
	 */
	List<Map> legalHolidayPeople(String vcode);

	/**
	 * 本年人数峰值
	 *
	 * @param vcode
	 * @return
	 */
	Map maxPeople(String vcode);


	/**
	 * 本年团队数
	 *
	 * @param vcode
	 * @return
	 */
	Map teamSumYear(String vcode);

	/**
	 * 年日均接待游客数量
	 *
	 * @param vcode
	 * @return
	 */
	Map avergePassengerYear(String vcode);

	/**
	 * 微件：景区景点数据分析
	 * 今日景点游客数
	 * 昨日景点游客数
	 * 景点最大承载量
	 * @param vcode
	 * @return
	 */
	List<Map<String,Object>> getScenicPeople(String vcode);
	/**
	 * 微件：景区景点数据分析
	 * 今日景点游客数
	 * 昨日景点游客数
	 * 景点平均游客数
	 * @param vcode
	 * @return
	 */
	List<Map<String,Object>> getScenicPeopleAvg(String vcode);
 }
