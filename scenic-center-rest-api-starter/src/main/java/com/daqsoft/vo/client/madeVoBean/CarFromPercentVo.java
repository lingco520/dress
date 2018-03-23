package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:车辆类型占比
 */


public class CarFromPercentVo implements java.io.Serializable {
    private String SUMCAR;//汽车数量
    private  String CARFROM;//汽车类型
    private  String PERCENT;//汽车占比

    public String getSUMCAR() {
        return SUMCAR;
    }

    public void setSUMCAR(String SUMCAR) {
        this.SUMCAR = SUMCAR;
    }

    public String getCARFROM() {
        return CARFROM;
    }

    public void setCARFROM(String CARFROM) {
        this.CARFROM = CARFROM;
    }

    public String getPERCENT() {
        return PERCENT;
    }

    public void setPERCENT(String PERCENT) {
        this.PERCENT = PERCENT;
    }
}
