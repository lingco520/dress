package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * 停车场domain 类
 * zf
 */
public class JqTvpmCheckoutWidget implements Serializable {
    private Long id;
    private String DEDUCTIONMONEY;
    private String ENTRANCETIME;
    private String ENTRANCEVEHICLENO;
    private String EXITTIME;
    private String EXITVEHICLENO;
    private String PAYMENTMONEY;
    private String TOTALMONEY;
    private String VCODE;
    private String VEHICLETYPE;
    private String memo;
    private String num;//车牌总数
    private String carNum;//车牌号

    private String province;

    public JqTvpmCheckoutWidget(String num, String carNum, String province) {
        this.num = num;
        this.carNum = carNum;
        this.province=province;
    }

    public JqTvpmCheckoutWidget() {
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDEDUCTIONMONEY() {
        return DEDUCTIONMONEY;
    }

    public void setDEDUCTIONMONEY(String DEDUCTIONMONEY) {
        this.DEDUCTIONMONEY = DEDUCTIONMONEY;
    }

    public String getENTRANCETIME() {
        return ENTRANCETIME;
    }

    public void setENTRANCETIME(String ENTRANCETIME) {
        this.ENTRANCETIME = ENTRANCETIME;
    }

    public String getENTRANCEVEHICLENO() {
        return ENTRANCEVEHICLENO;
    }

    public void setENTRANCEVEHICLENO(String ENTRANCEVEHICLENO) {
        this.ENTRANCEVEHICLENO = ENTRANCEVEHICLENO;
    }

    public String getEXITTIME() {
        return EXITTIME;
    }

    public void setEXITTIME(String EXITTIME) {
        this.EXITTIME = EXITTIME;
    }

    public String getEXITVEHICLENO() {
        return EXITVEHICLENO;
    }

    public void setEXITVEHICLENO(String EXITVEHICLENO) {
        this.EXITVEHICLENO = EXITVEHICLENO;
    }

    public String getPAYMENTMONEY() {
        return PAYMENTMONEY;
    }

    public void setPAYMENTMONEY(String PAYMENTMONEY) {
        this.PAYMENTMONEY = PAYMENTMONEY;
    }

    public String getTOTALMONEY() {
        return TOTALMONEY;
    }

    public void setTOTALMONEY(String TOTALMONEY) {
        this.TOTALMONEY = TOTALMONEY;
    }

    public String getVCODE() {
        return VCODE;
    }

    public void setVCODE(String VCODE) {
        this.VCODE = VCODE;
    }

    public String getVEHICLETYPE() {
        return VEHICLETYPE;
    }

    public void setVEHICLETYPE(String VEHICLETYPE) {
        this.VEHICLETYPE = VEHICLETYPE;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
