package com.daqsoft.vo.client.mysqlBean;

import java.io.Serializable;

/**
 * Created by cl on 2016/7/30 0030. 客流分析——年月日
 */
public class BigRealPersonTend implements Serializable {
    private String num;
    private String time;
    /**
     * 人次
     */
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
