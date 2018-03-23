package com.daqsoft.entity.dataconfig;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础数据模拟配置参数
 */
public class DataBasisConfig implements Serializable{
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 景区名称
     */
    private String dstpName;

    /**
     * 单票人数范围最小值
     */
    private String dstpTotalMin;

    /**
     * 单票人数范围最小值
     */
    private String dstpTotalMax;

    /**
     * 票务类型
     */
    private String dstpSource;

    /**
     * 类型: 1 进园，2 出园
     */
    private String dstpType;

    /**
     * 实时人数记录数范围最小值
     */
    private String dstpRandomMin;

    /**
     * 实时人数记录范围最大值
     */
    private String dstpRandomMax;
    /**
     * 记录模拟开始时间
     */
    private Date dstpStartDate;
    /**
     * 记录模拟结束时间
     */
    private Date dstpEndDate;

    /**
     * 线下票务票名称
     */
    private String dofftName;

    /**
     * 票单价
     */
    private String dofftPrice;

    /**
     * 票数量
     */
    private String dofftNumber;

    /**
     * 线下票务记录范围最小值
     */
    private String dofftRandomMin;

    /**
     * 线下票务记录范围最大值
     */
    private String dofftRandomMax;
    /**
     * 记录模拟开始时间
     */
    private Date dofftStartDate;
    /**
     * 记录模拟结束时间
     */
    private Date dofftEndDate;

    /**
     * 线上票务票名称
     */
    private String dontName;

    /**
     * 票单价
     */
    private String dontPrice;

    /**
     * 票数量
     */
    private String dontNumber;

    /**
     * 线上票务记录范围最小值
     */
    private String dontRandomMin;

    /**
     * 线上票务记录范围最大值
     */
    private String dontRandomMax;
    /**
     * 记录模拟开始时间
     */
    private Date dontStartDate;
    /**
     * 记录模拟结束时间
     */
    private Date dontEndDate;

    /**
     * 车辆所在地名称
     */
    private String dtvpRegionName;

    /**
     * 车辆所在地区region
     */
    private String dtvpRegion;

    /**
     * 车流量记录范围最小值
     */
    private String dtvpRandomMin;

    /**
     * 车流量记录范围最大值
     */
    private String dtvpRandomMax;
    /**
     * 记录模拟开始时间
     */
    private Date dtvpStartDate;
    /**
     * 记录模拟结束时间
     */
    private Date dtvpEndDate;

    /**
     * 停车场ID
     */
    private String dtpParkingId;

    /**
     * 车位总数
     */
    private String dtpTotal;

    /**
     * 已停车位数范围最小值
     */
    private String dtpUsedMin;

    /**
     * 已停车位数范围最大值(必须小于等于总停车位)
     */
    private String dtpUsedMax;

    /**
     * 实时停车位记录范围最小值
     */
    private String dtpRandomMin;

    /**
     * 实时停车位记录范围最大值
     */
    private String dtpRandomMax;
    /**
     * 记录模拟开始时间
     */
    private Date dtpStartDate;
    /**
     * 记录模拟结束时间
     */
    private Date dtpEndDate;

    /**
     * 停车场ID
     */
    private String dpcParkingId;

    /**
     * 车辆所在地区名称
     */
    private String dpcRegionName;
    /**
     * 车辆所在地城市region
     */
    private String dpcRegion;

    /**
     * 停车场结算记录最小值
     */
    private String dpcRandomMin;

    /**
     * 停车场结算记录最大值
     */
    private String dpcRandomMax;
    /**
     * 记录模拟开始时间
     */
    private Date dpcStartDate;
    /**
     * 记录模拟结束时间
     */
    private Date dpcEndDate;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 记录操作人
     */
    private String operateAccount;
    /**
     * 景区编码
     */
    private String vcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDstpName() {
        return dstpName;
    }

    public void setDstpName(String dstpName) {
        this.dstpName = dstpName;
    }

    public String getDstpTotalMin() {
        return dstpTotalMin;
    }

    public void setDstpTotalMin(String dstpTotalMin) {
        this.dstpTotalMin = dstpTotalMin;
    }

    public String getDstpTotalMax() {
        return dstpTotalMax;
    }

    public void setDstpTotalMax(String dstpTotalMax) {
        this.dstpTotalMax = dstpTotalMax;
    }

    public String getDstpSource() {
        return dstpSource;
    }

    public void setDstpSource(String dstpSource) {
        this.dstpSource = dstpSource;
    }

    public String getDstpType() {
        return dstpType;
    }

    public void setDstpType(String dstpType) {
        this.dstpType = dstpType;
    }

    public String getDstpRandomMin() {
        return dstpRandomMin;
    }

    public void setDstpRandomMin(String dstpRandomMin) {
        this.dstpRandomMin = dstpRandomMin;
    }

    public String getDstpRandomMax() {
        return dstpRandomMax;
    }

    public void setDstpRandomMax(String dstpRandomMax) {
        this.dstpRandomMax = dstpRandomMax;
    }

    public Date getDstpStartDate() {
        return dstpStartDate;
    }

    public void setDstpStartDate(Date dstpStartDate) {
        this.dstpStartDate = dstpStartDate;
    }

    public Date getDstpEndDate() {
        return dstpEndDate;
    }

    public void setDstpEndDate(Date dstpEndDate) {
        this.dstpEndDate = dstpEndDate;
    }

    public String getDofftName() {
        return dofftName;
    }

    public void setDofftName(String dofftName) {
        this.dofftName = dofftName;
    }

    public String getDofftPrice() {
        return dofftPrice;
    }

    public void setDofftPrice(String dofftPrice) {
        this.dofftPrice = dofftPrice;
    }

    public String getDofftNumber() {
        return dofftNumber;
    }

    public void setDofftNumber(String dofftNumber) {
        this.dofftNumber = dofftNumber;
    }

    public String getDofftRandomMin() {
        return dofftRandomMin;
    }

    public void setDofftRandomMin(String dofftRandomMin) {
        this.dofftRandomMin = dofftRandomMin;
    }

    public String getDofftRandomMax() {
        return dofftRandomMax;
    }

    public void setDofftRandomMax(String dofftRandomMax) {
        this.dofftRandomMax = dofftRandomMax;
    }

    public Date getDofftStartDate() {
        return dofftStartDate;
    }

    public void setDofftStartDate(Date dofftStartDate) {
        this.dofftStartDate = dofftStartDate;
    }

    public Date getDofftEndDate() {
        return dofftEndDate;
    }

    public void setDofftEndDate(Date dofftEndDate) {
        this.dofftEndDate = dofftEndDate;
    }

    public String getDontName() {
        return dontName;
    }

    public void setDontName(String dontName) {
        this.dontName = dontName;
    }

    public String getDontPrice() {
        return dontPrice;
    }

    public void setDontPrice(String dontPrice) {
        this.dontPrice = dontPrice;
    }

    public String getDontNumber() {
        return dontNumber;
    }

    public void setDontNumber(String dontNumber) {
        this.dontNumber = dontNumber;
    }

    public String getDontRandomMin() {
        return dontRandomMin;
    }

    public void setDontRandomMin(String dontRandomMin) {
        this.dontRandomMin = dontRandomMin;
    }

    public String getDontRandomMax() {
        return dontRandomMax;
    }

    public void setDontRandomMax(String dontRandomMax) {
        this.dontRandomMax = dontRandomMax;
    }

    public Date getDontStartDate() {
        return dontStartDate;
    }

    public void setDontStartDate(Date dontStartDate) {
        this.dontStartDate = dontStartDate;
    }

    public Date getDontEndDate() {
        return dontEndDate;
    }

    public void setDontEndDate(Date dontEndDate) {
        this.dontEndDate = dontEndDate;
    }

    public String getDtvpRandomMin() {
        return dtvpRandomMin;
    }

    public void setDtvpRandomMin(String dtvpRandomMin) {
        this.dtvpRandomMin = dtvpRandomMin;
    }

    public String getDtvpRandomMax() {
        return dtvpRandomMax;
    }

    public void setDtvpRandomMax(String dtvpRandomMax) {
        this.dtvpRandomMax = dtvpRandomMax;
    }

    public Date getDtvpStartDate() {
        return dtvpStartDate;
    }

    public void setDtvpStartDate(Date dtvpStartDate) {
        this.dtvpStartDate = dtvpStartDate;
    }

    public Date getDtvpEndDate() {
        return dtvpEndDate;
    }

    public void setDtvpEndDate(Date dtvpEndDate) {
        this.dtvpEndDate = dtvpEndDate;
    }

    public String getDtpParkingId() {
        return dtpParkingId;
    }

    public void setDtpParkingId(String dtpParkingId) {
        this.dtpParkingId = dtpParkingId;
    }

    public String getDtpTotal() {
        return dtpTotal;
    }

    public void setDtpTotal(String dtpTotal) {
        this.dtpTotal = dtpTotal;
    }

    public String getDtpUsedMin() {
        return dtpUsedMin;
    }

    public void setDtpUsedMin(String dtpUsedMin) {
        this.dtpUsedMin = dtpUsedMin;
    }

    public String getDtpUsedMax() {
        return dtpUsedMax;
    }

    public void setDtpUsedMax(String dtpUsedMax) {
        this.dtpUsedMax = dtpUsedMax;
    }

    public String getDtpRandomMin() {
        return dtpRandomMin;
    }

    public void setDtpRandomMin(String dtpRandomMin) {
        this.dtpRandomMin = dtpRandomMin;
    }

    public String getDtpRandomMax() {
        return dtpRandomMax;
    }

    public void setDtpRandomMax(String dtpRandomMax) {
        this.dtpRandomMax = dtpRandomMax;
    }

    public Date getDtpStartDate() {
        return dtpStartDate;
    }

    public void setDtpStartDate(Date dtpStartDate) {
        this.dtpStartDate = dtpStartDate;
    }

    public Date getDtpEndDate() {
        return dtpEndDate;
    }

    public void setDtpEndDate(Date dtpEndDate) {
        this.dtpEndDate = dtpEndDate;
    }

    public String getDpcParkingId() {
        return dpcParkingId;
    }

    public void setDpcParkingId(String dpcParkingId) {
        this.dpcParkingId = dpcParkingId;
    }

    public String getDpcRandomMin() {
        return dpcRandomMin;
    }

    public void setDpcRandomMin(String dpcRandomMin) {
        this.dpcRandomMin = dpcRandomMin;
    }

    public String getDpcRandomMax() {
        return dpcRandomMax;
    }

    public void setDpcRandomMax(String dpcRandomMax) {
        this.dpcRandomMax = dpcRandomMax;
    }

    public Date getDpcStartDate() {
        return dpcStartDate;
    }

    public void setDpcStartDate(Date dpcStartDate) {
        this.dpcStartDate = dpcStartDate;
    }

    public Date getDpcEndDate() {
        return dpcEndDate;
    }

    public void setDpcEndDate(Date dpcEndDate) {
        this.dpcEndDate = dpcEndDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperateAccount() {
        return operateAccount;
    }

    public void setOperateAccount(String operateAccount) {
        this.operateAccount = operateAccount;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getDtvpRegionName() {
        return dtvpRegionName;
    }

    public void setDtvpRegionName(String dtvpRegionName) {
        this.dtvpRegionName = dtvpRegionName;
    }

    public String getDtvpRegion() {
        return dtvpRegion;
    }

    public void setDtvpRegion(String dtvpRegion) {
        this.dtvpRegion = dtvpRegion;
    }

    public String getDpcRegionName() {
        return dpcRegionName;
    }

    public void setDpcRegionName(String dpcRegionName) {
        this.dpcRegionName = dpcRegionName;
    }

    public String getDpcRegion() {
        return dpcRegion;
    }

    public void setDpcRegion(String dpcRegion) {
        this.dpcRegion = dpcRegion;
    }
}