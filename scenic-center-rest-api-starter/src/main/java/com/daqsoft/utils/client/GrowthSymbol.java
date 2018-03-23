package com.daqsoft.utils.client;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/19.
 * @Version: V3.0.0.
 * @describe:同比环比
 */



public class GrowthSymbol {
    private String time;
    private String persent;

    public GrowthSymbol() {
    }

    public String getTime() {
        return time;
    }

    public GrowthSymbol(String time, String persent) {
        this.time = time;
        this.persent = persent;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPersent() {
        return persent;
    }

    public void setPersent(String persent) {
        this.persent = persent;
    }
}