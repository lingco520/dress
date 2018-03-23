package com.daqsoft.service.client.passengerFlowService;


import com.daqsoft.vo.client.madeVoBean.Big_RealP_Tend;
import com.daqsoft.vo.client.madeVoBean.PopulationSeal;
import com.daqsoft.vo.client.madeVoBean.Tend;
import com.daqsoft.vo.client.madeVoBean.WifiVo;

import java.util.List;
import java.util.Map;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-08 09:56.
 */
public interface BigDataPageService {

    /**
     * 获取大数据人数统计
     *
     * @param time  时间 格式：type=year time=2017   type=month  time=2017-07
     * @param vcode
     * @param type  时间类型
     * @return
     */
    List<Big_RealP_Tend> fundAll_date(String time, String vcode, String type);

    /**
     * 获取大数据人数统计（日）
     *
     * @param time  时间 2017-01-01
     * @param vcode 景区编码
     * @return
     */
    List<Tend> fundAll_day(String time, String vcode);

    /**
     * 获取大数据人数统计（季度）
     *
     * @param time  时间 2017
     * @param vcode 景区编码
     * @return
     */
    List<Big_RealP_Tend> fundAll_quarter(String time, String vcode);

    /**
     * 获取WiFi按年进行统计
     *
     * @param vcode
     * @param time  时间 年份2017
     * @return
     */
    List<Big_RealP_Tend> getWifiYear(String vcode, String time);

    /**
     * 获取WiFi按月份进行统计
     *
     * @param vcode 景区编码
     * @param time  时间 2017-01
     * @return
     */
    List<WifiVo> getWifiMonth(String vcode, String time);

    /**
     * 获取WiFi按天进行统计
     *
     * @param vcode 景区编码
     * @param time  时间 2017-01-01
     * @return
     */
    List<WifiVo> getWifiDay(String vcode, String time);

    /**
     * 获取WiFi按季度进行统计
     *
     * @param vcode 景区编码
     * @param time  时间 2017
     * @return
     */
    List<WifiVo> getWifiQuarter(String vcode, String time);

    /**
     * 大数据页面。团体与散客对比
     *
     * @param vcode
     * @param date
     * @return
     */
    Map classIfication(String vcode, String date);

    /**
     * 车流量与散客量变化分析
     *
     * @param vcode
     * @param date
     * @return
     */
    List<PopulationSeal> populationFit(String vcode, String date);


}
