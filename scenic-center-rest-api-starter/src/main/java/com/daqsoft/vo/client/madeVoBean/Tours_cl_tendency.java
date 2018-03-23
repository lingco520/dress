package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by cl on 2016/8/4 0004. 团队趋势
 */
public class Tours_cl_tendency implements Serializable {
    private String tours_num;
    private String touts_personNum;
    private String touts_time;


    /**
     * 团队数量
     */
    public String getTours_num() {
        return tours_num;
    }

    public void setTours_num(String tours_num) {
        this.tours_num = tours_num;
    }

    /**
     * 团队人数
     */
    public String getTouts_personNum() {
        return touts_personNum;
    }

    public void setTouts_personNum(String touts_personNum) {
        this.touts_personNum = touts_personNum;
    }

    /**
     * 时间
     */
    public String getTouts_time() {
        return touts_time;
    }

    public void setTouts_time(String touts_time) {
        this.touts_time = touts_time;
    }
}
