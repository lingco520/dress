package com.daqsoft.vo.resource;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/28 0028.
 */

public class GisScenicParking implements Serializable {
    /**
     * 主键id
     */
    private  Long id;
    /**
     * 名称
     */
    private  String  name;
    /**
     *  车位数量
     */
    private  String  parking_lot;
    /**
     * 经度
     */
    private  String  longitude;
    /**
     * 纬度
     */
    private  String  latitude;
    /**
     * 地址
     */
    private  String  address;

    public GisScenicParking(){}

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
        this.name = name;
    }

    public String getParking_lot() {
        return parking_lot;
    }

    public void setParking_lot(String parking_lot) {
        this.parking_lot = parking_lot;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
