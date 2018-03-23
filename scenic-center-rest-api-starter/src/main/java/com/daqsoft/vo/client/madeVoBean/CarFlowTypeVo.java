package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:车辆类型
 */


public class CarFlowTypeVo implements java.io.Serializable {
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
