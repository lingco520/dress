package com.daqsoft.vo.client.madeVoBean;

/**
 * @author tanggm
 * @version V1.0.0
 * @description 景点设备情况
 * @date 2017-06-23 17:39
 */
public class DeviceVo {
    // 景点名称
    private String spotName;
    // 正常
    private String normalNum;
    // 异常数
    private String abnormalNum;

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getNormalNum() {
        return normalNum;
    }

    public void setNormalNum(String normalNum) {
        this.normalNum = normalNum;
    }

    public String getAbnormalNum() {
        return abnormalNum;
    }

    public void setAbnormalNum(String abnormalNum) {
        this.abnormalNum = abnormalNum;
    }
}
