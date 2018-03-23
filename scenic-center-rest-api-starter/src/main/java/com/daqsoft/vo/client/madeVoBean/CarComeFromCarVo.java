package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author tanggm
 * 车流分析，返回数量和对应城市
 */
public class CarComeFromCarVo implements Serializable {
    private String carNum;
    private long num;
    private String city;

    public CarComeFromCarVo(){
    }
    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
