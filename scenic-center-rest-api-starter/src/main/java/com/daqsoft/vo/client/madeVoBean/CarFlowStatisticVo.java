package com.daqsoft.vo.client.madeVoBean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Author: yangkang.
 * @Date: Created in 2017/6/8.
 * @Version: V3.0.0.
 * @describe:车流量统计
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarFlowStatisticVo {
    /**数量*/
    private String quantity;
    /**景区编码*/
    private String vcode;
    /**来源*/
    private String source;
    /**时间*/
    private String timesheet;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(String timesheet) {
        this.timesheet = timesheet;
    }

}
