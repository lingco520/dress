package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * 实时停车产类
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-07 14:19.
 */
public class JqTimely_Parking_TendWidget implements Serializable {
    private Long id;
    private Long total;//使用数量
    private String surplusPaking;//剩余停车位
    private String parkingTotal;//总数停车位

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
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

