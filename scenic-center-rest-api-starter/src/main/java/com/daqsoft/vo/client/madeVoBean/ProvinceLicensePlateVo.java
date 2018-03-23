package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:客流分析-年月日
 */
public class ProvinceLicensePlateVo implements java.io.Serializable {
    private Integer SUMCAR;
    private String CARFROM;
    private String Percentage;

    public Integer getSUMCAR() {
        return SUMCAR;
    }

    public void setSUMCAR(Integer SUMCAR) {
        this.SUMCAR = SUMCAR;
    }

    public String getCARFROM() {
        return CARFROM;
    }

    public void setCARFROM(String CARFROM) {
        this.CARFROM = CARFROM;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }
}
