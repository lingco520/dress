package com.daqsoft.vo.client.mysqlBean;

import java.io.Serializable;

/**
 * WIFI每季度接入量
 * Created by liliangyu on 16/8/3.
 */
public class WifiQuarterVo implements Serializable {
    private long num;
    private String time;
    private String vcode;

    public WifiQuarterVo() {
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}
