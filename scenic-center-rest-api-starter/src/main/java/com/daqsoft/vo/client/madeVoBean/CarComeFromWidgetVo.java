package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by lianch on 16/8/2.
 */
public class CarComeFromWidgetVo implements Serializable {
    private String carNum;
    private long num;
    private String province;

    public CarComeFromWidgetVo(){
    }

    public CarComeFromWidgetVo(String carNum, long num) {

        this.carNum = carNum;
        this.num = num;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
