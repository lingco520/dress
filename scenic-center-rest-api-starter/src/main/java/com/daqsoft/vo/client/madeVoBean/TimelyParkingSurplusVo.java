package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:主页停车场数据
 */


public class TimelyParkingSurplusVo implements java.io.Serializable {
    private Long id;
    private String total;//使用数量
    private String surplusPaking;//剩余停车位
    private String parkingTotal;//总数停车位

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSurplusPaking() {
        return surplusPaking;
    }

    public void setSurplusPaking(String surplusPaking) {
        this.surplusPaking = surplusPaking;
    }

    public String getParkingTotal() {
        return parkingTotal;
    }

    public void setParkingTotal(String parkingTotal) {
        this.parkingTotal = parkingTotal;
    }
}
