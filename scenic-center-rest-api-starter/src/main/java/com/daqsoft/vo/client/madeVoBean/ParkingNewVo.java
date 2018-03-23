package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * 停车场车位使用情况,已使用未使用
 */
public class ParkingNewVo implements Serializable{
    private String pname;//停车场名称
    private String maxParkSpace;//总车位
    private String totalParking;//剩余车位
    private String total;//已使用车位

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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
