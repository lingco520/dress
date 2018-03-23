package com.daqsoft.service.client.trafficAnalysisService;


/**
 * @Author: zf.
 * @Date: Created in 2017/07/28.
 * @Version: V3.0.0.
 * @describe: 车辆类型Servive
 */
public interface CarTypeWidgetOpService {

    /**
     * 获取汽车数量小时数据
     *
     * @param vcode 景区编码
     * @param day   日期
     * @param type  汽车类型
     * @return
     * @update zf 20170822
     */
    Object getCarTypeHour(String vcode, String day, String type);

    /**
     * 获取汽车数量月数据
     *
     * @param vcode 景区编码
     * @param year  日期
     * @param type  汽车类型
     * @return
     * @update zf 20170822
     */
    Object getCarTypeMonth(String vcode, String year, String type);
}
