package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 /**
 * @Author: ym
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:车流量VO
 */

public class CarFlowVO implements Serializable {
    private String carnum; //数量

    private String date;    //时间//来源地

    public CarFlowVO() {
    }

    public CarFlowVO(String carnum, String date) {
        this.carnum = carnum;
        this.date = date;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
