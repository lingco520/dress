package com.daqsoft.entity.diningPlace;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: 餐饮场所
 * @Author: superziy .
 * @Date: Created in 16:40 2018/1/24.
 * @Version: 4.0.0
 * @describe: 餐饮场所
 * @since:JDK 1.8
 */
public class ResDiningPlace implements Serializable{
    /**
     * 主键id
     */
    private Long id;

    /**
     * 餐饮名称
     */
    private String name;

    /**
     * 英文名称
     */
    private String usname;

    /**
     * 许可证号
     */
    private String license;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 餐饮等级
     */
    private String restaurantLevel;

    /**
     * 餐饮类型
     */
    private String restaurantType;

    /**
     * 所获荣誉说明
     */
    private String honorQualification;

    /**
     * 对应品牌
     */
    private String brand;

    /**
     * 饭店风格
     */
    private String diningStyle;

    /**
     * 所属菜系
     */
    private String styleCooking;

    /**
     * 特色菜
     */
    private String specialtyDish;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 网站地址
     */
    private String webUrl;

    /**
     * 官方微博，微信等
     */
    private String officialLine;

    /**
     * 行政区划
     */
    private String region;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 电子地图
     */
    private String electronicMap;

    /**
     * 交通方式描述
     */
    private String trafficDescription;

    /**
     * 营业时间
     */
    private String businessHours;

    /**
     * 可容纳就餐人数
     */
    private Integer diningNumber;

    /**
     * 包间数量
     */
    private Integer rooms;

    /**
     * 人均消费
     */
    private Integer perCapitaConsumption;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 停车场停车位
     */
    private Integer parkingNum;

    /**
     * 对应商标
     */
    private String corporate;

    /**
     * 略缩图
     */
    private String sketchPicture;

    /**
     * 相关图片
     */
    private String picturelibrary;

    /**
     * 相关视频
     */
    private String video;

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
     * 工商注册时间
     */
    private Date registerDate;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 地图类型：0 gis地图  1 百度地图 2高德地图
     */
    private Integer mapType;

    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;

    /**
     * 景区编码
     */
    private String vcode;

    /**
     * 简介
     */
    private String introduction;

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

    public String getUsname() {
        return usname;
    }

    public void setUsname(String usname) {
        this.usname = usname == null ? null : usname.trim();
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord == null ? null : keyWord.trim();
    }

    public String getRestaurantLevel() {
        return restaurantLevel;
    }

    public void setRestaurantLevel(String restaurantLevel) {
        this.restaurantLevel = restaurantLevel == null ? null : restaurantLevel.trim();
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType == null ? null : restaurantType.trim();
    }

    public String getHonorQualification() {
        return honorQualification;
    }

    public void setHonorQualification(String honorQualification) {
        this.honorQualification = honorQualification == null ? null : honorQualification.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getDiningStyle() {
        return diningStyle;
    }

    public void setDiningStyle(String diningStyle) {
        this.diningStyle = diningStyle == null ? null : diningStyle.trim();
    }

    public String getStyleCooking() {
        return styleCooking;
    }

    public void setStyleCooking(String styleCooking) {
        this.styleCooking = styleCooking == null ? null : styleCooking.trim();
    }

    public String getSpecialtyDish() {
        return specialtyDish;
    }

    public void setSpecialtyDish(String specialtyDish) {
        this.specialtyDish = specialtyDish == null ? null : specialtyDish.trim();
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

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl == null ? null : webUrl.trim();
    }

    public String getOfficialLine() {
        return officialLine;
    }

    public void setOfficialLine(String officialLine) {
        this.officialLine = officialLine == null ? null : officialLine.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getElectronicMap() {
        return electronicMap;
    }

    public void setElectronicMap(String electronicMap) {
        this.electronicMap = electronicMap == null ? null : electronicMap.trim();
    }

    public String getTrafficDescription() {
        return trafficDescription;
    }

    public void setTrafficDescription(String trafficDescription) {
        this.trafficDescription = trafficDescription == null ? null : trafficDescription.trim();
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours == null ? null : businessHours.trim();
    }

    public Integer getDiningNumber() {
        return diningNumber;
    }

    public void setDiningNumber(Integer diningNumber) {
        this.diningNumber = diningNumber;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getPerCapitaConsumption() {
        return perCapitaConsumption;
    }

    public void setPerCapitaConsumption(Integer perCapitaConsumption) {
        this.perCapitaConsumption = perCapitaConsumption;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public Integer getParkingNum() {
        return parkingNum;
    }

    public void setParkingNum(Integer parkingNum) {
        this.parkingNum = parkingNum;
    }

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate == null ? null : corporate.trim();
    }

    public String getSketchPicture() {
        return sketchPicture;
    }

    public void setSketchPicture(String sketchPicture) {
        this.sketchPicture = sketchPicture == null ? null : sketchPicture.trim();
    }

    public String getPicturelibrary() {
        return picturelibrary;
    }

    public void setPicturelibrary(String picturelibrary) {
        this.picturelibrary = picturelibrary == null ? null : picturelibrary.trim();
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video == null ? null : video.trim();
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getMapType() {
        return mapType;
    }

    public void setMapType(Integer mapType) {
        this.mapType = mapType;
    }
}