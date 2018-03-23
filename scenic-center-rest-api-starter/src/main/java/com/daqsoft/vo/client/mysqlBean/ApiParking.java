package com.daqsoft.vo.client.mysqlBean;

/**
 * Created by yangk on 2017-5-23.
 * 停车场
 */
public class ApiParking {
    private Long id;
    private String name;//停车场中文名称
    private String address;//停车场详细地址
    private String electronicMap;//停车场电子地图
    private String parkingType;//露天停车场、室内停车场、停车楼
    private int parkingTotal;//可对外提供服务的车位数量
    private String chargeStandard;//停车计费方式和费用
    private String openingHours;//停车站可进出的时间
    private int status;//经度
    private String vcode;//纬度
    private String longitude;
    private String latitude;
    private String publisher;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getElectronicMap() {
        return electronicMap;
    }

    public void setElectronicMap(String electronicMap) {
        this.electronicMap = electronicMap;
    }

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public int getParkingTotal() {
        return parkingTotal;
    }

    public void setParkingTotal(int parkingTotal) {
        this.parkingTotal = parkingTotal;
    }

    public String getChargeStandard() {
        return chargeStandard;
    }

    public void setChargeStandard(String chargeStandard) {
        this.chargeStandard = chargeStandard;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
