package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by llianch on 16/7/30.
 */
public class CarFlowVos implements Serializable {
    private String time;
    private long num;


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
}
