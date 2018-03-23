package com.daqsoft.service.client.passengerFlowService;

import com.daqsoft.vo.client.mysqlBean.HolidayFiveYearCount;
import com.daqsoft.vo.client.mysqlBean.PassengerFlowByDay;
import com.daqsoft.vo.client.mysqlBean.RealPeople;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/28 0028. 客流分析——年月日
 * lrd：清除多余方法
 */
public interface PassengerFlowAnalysisService {
	/**
	 * 每日客流趋势
	 *
	 * @param date
	 * @param vcode
	 * @return
	 */
	List<PassengerFlowByDay> find_oneday_date(String date, String vcode);

	/**
	 * 节假日人数统计（统计的是五年的。比如2011年劳动节，2012年劳动）
	 * @param date
	 * @param vcode
	 * @return
	 */
	List<HolidayFiveYearCount> countHolidayPeople(String date, String type, String vcode);

	/**
	 * 近五年的节假日人数（精确到节假日）统计
	 *
	 * @param date
	 * @param vcode
	 * @return
	 */
	List<HolidayFiveYearCount> countFiveYearHoliday(String date, String type, String vcode, Integer numyear);



	/**
	 * 今天负荷
	 *
	 * @param map
	 * @return
     */
	List<RealPeople> getScenicTimeOnTime(Map map);


	/**
	 * 昨天负荷
	 *
	 * @param map
	 * @return
     */
	List<RealPeople> getScenicTimeOnTimeYesterday(Map map);


	/**
	 * 近三十日平均值和最大值
	 *
	 * @param vcode
	 * @param start
	 * @param end
     * @return
     */
	Map getAverageAndMaxThrity(String vcode, String start, String end);

}
