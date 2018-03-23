package com.daqsoft.vo.client.mysqlBean;

import java.io.Serializable;
import java.util.Date;

/**
 * WIFI实体
 * Created by liliangyu on 16/8/3.	WIFI实体
 */
public class JqWifiAnalysis implements Serializable {
    private Long id;
    private int status;
    private String apMacAddress;
    private String vendorld;
    private String muMacAddress;
    private String radioType;
    private String channel;
    private String isAssociated;
    private String asspciatedApMac;
    private String muType;
    private String rssi;
    private String noiseFloor;
    private String age;
    private String mulpv4;
    private String reserved;
    private Date addTime;
    private String vcode;
    private int subgroup;
    private String scenicId;
    private String mark;

    public JqWifiAnalysis() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getApMacAddress() {
        return apMacAddress;
    }

    public void setApMacAddress(String apMacAddress) {
        this.apMacAddress = apMacAddress;
    }

    public String getVendorld() {
        return vendorld;
    }

    public void setVendorld(String vendorld) {
        this.vendorld = vendorld;
    }

    public String getMuMacAddress() {
        return muMacAddress;
    }

    public void setMuMacAddress(String muMacAddress) {
        this.muMacAddress = muMacAddress;
    }

    public String getRadioType() {
        return radioType;
    }

    public void setRadioType(String radioType) {
        this.radioType = radioType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIsAssociated() {
        return isAssociated;
    }

    public void setIsAssociated(String isAssociated) {
        this.isAssociated = isAssociated;
    }

    public String getAsspciatedApMac() {
        return asspciatedApMac;
    }

    public void setAsspciatedApMac(String asspciatedApMac) {
        this.asspciatedApMac = asspciatedApMac;
    }

    public String getMuType() {
        return muType;
    }

    public void setMuType(String muType) {
        this.muType = muType;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getNoiseFloor() {
        return noiseFloor;
    }

    public void setNoiseFloor(String noiseFloor) {
        this.noiseFloor = noiseFloor;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMulpv4() {
        return mulpv4;
    }

    public void setMulpv4(String mulpv4) {
        this.mulpv4 = mulpv4;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public int getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(int subgroup) {
        this.subgroup = subgroup;
    }

    public String getScenicId() {
        return scenicId;
    }

    public void setScenicId(String scenicId) {
        this.scenicId = scenicId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
