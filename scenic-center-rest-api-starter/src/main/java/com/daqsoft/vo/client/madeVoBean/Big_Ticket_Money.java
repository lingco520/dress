package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class Big_Ticket_Money implements Serializable {
    private long moneys;
    private String dates;


    /**
     * 峰谷值金额
     */
    public long getMoneys() {
        return moneys;
    }

    public void setMoneys(long moneys) {
        this.moneys = moneys;
    }

    /**
     * 峰谷值日期
     */
    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }
}
