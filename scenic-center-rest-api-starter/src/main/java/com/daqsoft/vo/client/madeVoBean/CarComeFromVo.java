package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by lianch on 16/8/2.
 */
public class CarComeFromVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String carNum;
    private long num;

    public CarComeFromVo(){
    }

    public CarComeFromVo(String carNum, long num) {
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
}
