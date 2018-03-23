package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-06 18:26.
 */
public class TicketClTypeVO implements Serializable {
    //数量
    private long nums;
    //类型
    private String types;
    //金额
    private int typecal;


    public long getNums() {
        return nums;
    }
    public void setNums(long nums) {
        this.nums = nums;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public int getTypecal() {
        return typecal;
    }

    public void setTypecal(int typecal) {
        this.typecal = typecal;
    }

    public TicketClTypeVO(long nums, String types, int typecal) {
        this.nums = nums;
        this.types = types;
        this.typecal = typecal;
    }

    public TicketClTypeVO() {
    }

    @Override
    public String toString() {
        return "TicketClTypeVO{" +
                "nums=" + nums +
                ", types='" + types + '\'' +
                ", typecal=" + typecal +
                '}';
    }
}

