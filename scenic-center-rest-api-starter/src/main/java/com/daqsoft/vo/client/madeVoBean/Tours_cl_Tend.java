package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-06 18:05.
 */
public class Tours_cl_Tend implements Serializable {
    private String id;
    private String  tourNum;
    private String  arriveTime;
    private String  tName;
    private String  name_t;
    private String  amount;


    /**
     * 团队口号
     */
    public String getTourNum() {
        return tourNum;
    }

    public void setTourNum(String tourNum) {
        this.tourNum = tourNum;
    }

    /**
     * 游览时间
     */
    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    /**
     * 团队名称
     */
    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    /**
     * 团队所属旅行社名称
     */
    public String getName_t() {
        return name_t;
    }

    public void setName_t(String name_t) {
        this.name_t = name_t;
    }

    /**
     * 团队游客数量
     */
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
