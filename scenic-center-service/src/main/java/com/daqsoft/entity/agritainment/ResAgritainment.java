package com.daqsoft.entity.agritainment;

import java.util.Date;

/**
 * @Title: 农家乐
 * @Author: superziy .
 * @Date: Created in 15:12 2018/1/25.
 * @Version: 4.0.0
 * @describe:农家乐
 * @since:JDK 1.8
 */
public class ResAgritainment {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 农家乐等级
     */
    private String level;

    /**
     * 许可证号
     */
    private String license;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 地址
     */
    private String address;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 法人代表
     */
    private String legalPerson;

    /**
     * 法人代表电话
     */
    private String legalPhone;

    /**
     * 负责人
     */
    private String preside;

    /**
     * 负责人电话
     */
    private String presidePhone;

    /**
     * 农家乐价格
     */
    private Long checkPrice;

    /**
     * 房间数量
     */
    private Integer roomCount;

    /**
     * 可容纳人数
     */
    private Integer holdCount;

    /**
     * 行政编码
     */
    private String region;

    /**
     * 电话
     */
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 雅间总数
     */
    private Integer diningRoom;

    /**
     * 相关图片
     */
    private String image;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 工商注册时间
     */
    private Date registerDate;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 地图类型：0 gis地图  1 百度地图 2高德地图
     */
    private Integer mapType;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 景区唯一标示
     */
    private String vcode;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 简介
     */
    private String introduce;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getLegalPhone() {
        return legalPhone;
    }

    public void setLegalPhone(String legalPhone) {
        this.legalPhone = legalPhone == null ? null : legalPhone.trim();
    }

    public String getPreside() {
        return preside;
    }

    public void setPreside(String preside) {
        this.preside = preside == null ? null : preside.trim();
    }

    public String getPresidePhone() {
        return presidePhone;
    }

    public void setPresidePhone(String presidePhone) {
        this.presidePhone = presidePhone == null ? null : presidePhone.trim();
    }

    public Long getCheckPrice() {
        return checkPrice;
    }

    public void setCheckPrice(Long checkPrice) {
        this.checkPrice = checkPrice;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Integer getHoldCount() {
        return holdCount;
    }

    public void setHoldCount(Integer holdCount) {
        this.holdCount = holdCount;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public Integer getDiningRoom() {
        return diningRoom;
    }

    public void setDiningRoom(Integer diningRoom) {
        this.diningRoom = diningRoom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public Integer getMapType() {
        return mapType;
    }

    public void setMapType(Integer mapType) {
        this.mapType = mapType;
    }
}