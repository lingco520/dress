package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * 实时停车场类
 * @author lianch
 * @date 2017-06-07.
 */
public class JqTimely_TimeOnTime_TendWidget implements Serializable {
    private String parkingSurplusPercent;//剩余停车位百分比
    private String parkingUsefulPercent;//使用停车位百分比
    private String choiceTime;
    private String name;//停车场名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChoiceTime() {
        return choiceTime;
    }

    public void setChoiceTime(String choiceTime) {
        this.choiceTime = choiceTime;
    }

    public String getParkingSurplusPercent() {
        return parkingSurplusPercent;
    }

    public void setParkingSurplusPercent(String parkingSurplusPercent) {
        this.parkingSurplusPercent = parkingSurplusPercent;
    }

    public String getParkingUsefulPercent() {
        return parkingUsefulPercent;
    }

    public void setParkingUsefulPercent(String parkingUsefulPercent) {
        this.parkingUsefulPercent = parkingUsefulPercent;
    }
}
