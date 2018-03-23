package com.daqsoft.vo.client.mysqlBean;

import java.io.Serializable;

/**
 * WIFI日累积接入量
 * Created by liliangyu on 16/8/3.
 */
public class WifiSumVo implements Serializable {

    private String time;
    private long sum;

    public WifiSumVo() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}
