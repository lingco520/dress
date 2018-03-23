package com.daqsoft.vo.client.madeVoBean;

/**
 * @author tanggm
 * @version V3.1
 * @description 景点wifi VO
 * @date 2017-06-28 15:02
 */
public class SpotsWifiVo {
    // 景点名称
    private String spotsName;
    // wifi数量
    private Long wifiNum;

    public String getSpotsName() {
        return spotsName;
    }

    public void setSpotsName(String spotsName) {
        this.spotsName = spotsName;
    }

    public Long getWifiNum() {
        return wifiNum;
    }

    public void setWifiNum(Long wifiNum) {
        this.wifiNum = wifiNum;
    }
}
