package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by cl on 2016/8/4 0004. 团队总数 与 游客总数
 */
public class Tours_cl_total implements Serializable {
    private String tours_num;
    private String touts_totalNum;


    /**
     * 团队总数量
     */
    public String getTours_num() {
        return tours_num;
    }

    public void setTours_num(String tours_num) {
        this.tours_num = tours_num;
    }

    /**
     * 团队总数量人数
     */
    public String getTouts_totalNum() {
        return touts_totalNum;
    }

    public void setTouts_totalNum(String touts_totalNum) {
        this.touts_totalNum = touts_totalNum;
    }
}
