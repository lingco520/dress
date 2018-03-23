package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * auth tanggm
 * 停车场车位使用情况
 */
public class ParkingVo implements Serializable{
    private String pname;//停车场名称
    private String maxParkSpace;//总车位
    private String totalParking;//剩余车位

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getMaxParkSpace() {
        return maxParkSpace;
    }

    public void setMaxParkSpace(String maxParkSpace) {
        this.maxParkSpace = maxParkSpace;
    }

    public String getTotalParking() {
        return totalParking;
    }

    public void setTotalParking(String totalParking) {
        this.totalParking = totalParking;
    }
}
