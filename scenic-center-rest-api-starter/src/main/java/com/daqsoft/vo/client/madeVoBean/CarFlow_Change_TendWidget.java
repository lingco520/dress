package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by lianch on 17/6/8.
 */
public class CarFlow_Change_TendWidget implements Serializable {
    private String smallCar;//汽车数量
    private  String BusCar;
    private  String OtherCar;
    private  String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSmallCar() {
        return smallCar;
    }

    public void setSmallCar(String smallCar) {
        this.smallCar = smallCar;
    }

    public String getBusCar() {
        return BusCar;
    }

    public void setBusCar(String busCar) {
        BusCar = busCar;
    }

    public String getOtherCar() {
        return OtherCar;
    }

    public void setOtherCar(String otherCar) {
        OtherCar = otherCar;
    }
}
