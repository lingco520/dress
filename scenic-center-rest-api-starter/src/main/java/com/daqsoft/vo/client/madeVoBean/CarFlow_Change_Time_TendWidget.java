package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by lianch on 17/6/8.
 */
public class CarFlow_Change_Time_TendWidget implements Serializable {
    private String num;//汽车数量
    private  String source;//汽车类型
    private  String time;//时间

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
