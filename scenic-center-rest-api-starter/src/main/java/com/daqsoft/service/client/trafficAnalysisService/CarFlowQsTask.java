package com.daqsoft.service.client.trafficAnalysisService;

import com.daqsoft.utils.client.SpringContextUtils;
import com.daqsoft.vo.client.madeVoBean.Big_CarFlow_WidgetTend;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;


/**
 * @Title: 车流趋势线程
 * @Author: zf
 * @Date: 2017/08/21 10:49
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Component
public class CarFlowQsTask implements Callable<List<Big_CarFlow_WidgetTend>> {
    private Logger logger = Logger.getLogger(getClass());

    private String date;
    private String vcode;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public CarFlowQsTask(String vcode, String date) {
        this.date = date;
        this.vcode = vcode;
    }

    public CarFlowQsTask() {
    }

    /**
     * @return 返回  天
     * @throws Exception if unable to compute a result
     */
    @Override
    public List<Big_CarFlow_WidgetTend> call() throws Exception {
        System.out.println(String.format("线程:%s 正在处理[车流趋势]数据 vcode:%s,date:%s", Thread.currentThread().getName(), vcode, date));
        CarFlowWidgetService carFlowWidgetService = SpringContextUtils.getBean(CarFlowWidgetService.class);
        List<Big_CarFlow_WidgetTend> list = carFlowWidgetService.getByDay(vcode, date);
        return list;
    }
}