package com.daqsoft.entity.parking;

import java.util.Date;

/**
 * 停车场实体
 */
public class ResParking {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 停车场中文名称
     */
    private String name;

    /**
     * 停车场详细地址
     */
    private String address;

    /**
     * 停车场类型：露天停车场、室内停车场....
     */
    private String parkingType;

    /**
     * 停车场车位数
     */
    private Integer parkingLot;

    /**
     * 停车计费方式和费用
     */
    private String chargeStandard;

    /**
     * 停车场开放时间段
     */
    private String opentime;

    /**
     * 停车场经度
     */
    private String longitude;

    /**
     * 停车场纬度
     */
    private String latitude;

    /**
     * 地图类型：0 gis地图  1 百度地图 2高德地图
     */
    private Integer mapType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态：1 启用，0 禁用，-1 删除
     */
    private Integer status;

    /**
     * 景区编码
     */
    private String vcode;

    /**
     * 地区region
     */
    private String region;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 内容
     */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType == null ? null : parkingType.trim();
    }

    public Integer getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(Integer parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getChargeStandard() {
        return chargeStandard;
    }

    public void setChargeStandard(String chargeStandard) {
        this.chargeStandard = chargeStandard == null ? null : chargeStandard.trim();
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getMapType() {
        return mapType;
    }

    public void setMapType(Integer mapType) {
        this.mapType = mapType;
    }
}