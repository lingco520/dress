package com.daqsoft.vo.client.mysqlBean;

import java.io.Serializable;

/**
 * WIFI日实时接入量
 * Created by liliangyu on 16/8/3.
 */
public class WifiAnalysisVo implements Serializable {
    private String time;
    private long num;

    public WifiAnalysisVo() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }
}
