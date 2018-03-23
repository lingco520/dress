package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author lianch
 * @date 2017-06-06
 */
public class Big_CarFlow_WidgetTend implements Serializable {
    private String SumCar;//汽车数量
    private String Time;//时间

    public String getSumCar() {
        return SumCar;
    }

    public void setSumCar(String sumCar) {
        SumCar = sumCar;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
