package com.daqsoft.utils.client;

/**
 * Created  on 16/8/13.
 */

public class Growth {
    private String time;//时间
    private String persent;//百分比

    public Growth() {
    }

    public String getTime() {
        return time;
    }

    public Growth(String time, String persent) {
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