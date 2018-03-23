package com.daqsoft.vo.client.mysqlBean;

import java.io.Serializable;

/**
 * Created by cl on 16/7/29.  客流分析——每日客流趋势
 */
public class PassengerFlowByDay implements Serializable {
    /**每日客流趋势——负荷度*/
    private String percent;
    /**每日客流趋势——实施游客数*/
    private String num;
    /**每日客流趋势——累计游客量*/
    private String sum;
    /**
     *
     * 每日客流趋势时间*/
    private String time;

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}