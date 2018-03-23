package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 实时停车产类
 * @author lianch
 * @date created on 2017/06/07
 */
public class JqTimelyParkingWidget implements Serializable {
    private Long id;
    private String paking_id;
    private Long total;//使用数量
    private Long usings;
    private Date time;
    private Long source;
    private String vcode;
    private Long sort;
    private String VEHICLETYPE;
    private Long status;
    private String surplusPaking;//剩余停车位
    private String parkingTotal;//总数停车位
    private String parkingUseFul;//总数停车位
    private String parkingSurplusPercent;//剩余停车位百分比
    private String parkingUsefulPercent;//使用停车位百分比
    private String choiceTime;//图形显示时间
    private String name;//停车场名称

    public JqTimelyParkingWidget() {
    }

    public JqTimelyParkingWidget(String surplusPaking, String parkingUseFul, String name, String choiceTime) {
        this.surplusPaking = surplusPaking;
        this.parkingUseFul = parkingUseFul;
        this.name = name;
        this.choiceTime=choiceTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChoiceTime() {
        return choiceTime;
    }

    public void setChoiceTime(String choiceTime) {
        this.choiceTime = choiceTime;
    }

    public String getParkingUseFul() {
        return parkingUseFul;
    }

    public void setParkingUseFul(String parkingUseFul) {
        this.parkingUseFul = parkingUseFul;
    }

    public String getSurplusPaking() {
        return surplusPaking;
    }

    public void setSurplusPaking(String surplusPaking) {
        this.surplusPaking = surplusPaking;
    }

    public String getParkingSurplusPercent() {
        return parkingSurplusPercent;
    }

    public void setParkingSurplusPercent(String parkingSurplusPercent) {
        this.parkingSurplusPercent = parkingSurplusPercent;
    }

    public String getParkingUsefulPercent() {
        return parkingUsefulPercent;
    }

    public void setParkingUsefulPercent(String parkingUsefulPercent) {
        this.parkingUsefulPercent = parkingUsefulPercent;
    }


    public String getParkingTotal() {
        return parkingTotal;
    }

    public void setParkingTotal(String parkingTotal) {
        this.parkingTotal = parkingTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaking_id() {
        return paking_id;
    }

    public void setPaking_id(String paking_id) {
        this.paking_id = paking_id;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getUsings() {
        return usings;
    }

    public void setUsings(Long usings) {
        this.usings = usings;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getVEHICLETYPE() {
        return VEHICLETYPE;
    }

    public void setVEHICLETYPE(String VEHICLETYPE) {
        this.VEHICLETYPE = VEHICLETYPE;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
