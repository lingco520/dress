package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by lianch on 16/7/29.
 */
public class CarFlow_Type_Tend implements Serializable {
    private String SumCar;//汽车数量
    private  String CarType;//汽车类型


    public String getSumCar() {
        return SumCar;
    }

    public void setSumCar(String sumCar) {
        SumCar = sumCar;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }
}
