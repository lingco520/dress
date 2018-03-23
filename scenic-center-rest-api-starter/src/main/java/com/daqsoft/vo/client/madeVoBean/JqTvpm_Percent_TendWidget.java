package com.daqsoft.vo.client.madeVoBean;


import java.io.Serializable;

/**
 * Created by zf on 16/7/29.
 */
public class JqTvpm_Percent_TendWidget implements Serializable {
   private String numCar;
    private String num;
    private String province;

    public JqTvpm_Percent_TendWidget(String numCar, String num) {
        this.numCar = numCar;
        this.num = num;
    }

    public JqTvpm_Percent_TendWidget() {
    }

    public String getNumCar() {
        return numCar;
    }

    public void setNumCar(String numCar) {
        this.numCar = numCar;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
