package com.daqsoft.vo;

import java.io.Serializable;
import java.util.Date;

public class test implements Serializable{
    /**
     * 主键【宾馆、住宿表】
     */
    private Long id;
    /**
     * 住宿点的中文名称
     */
    private String name;

    /**
     * 住宿点的中文别名
     */
    private String zhName;

    /**
     * 住宿点的英文或拼音全称
     */
    private String usName;

    /**
     * 住宿点的分类
     */
    private String acommodationType;

    /**
     * 白金五星级、五星级、四星级、三星级、二星级、一星级；未评定的为无星级；根据GB/T 14308—2010确定
     */
    private String touristHotelLevel;

    /**
     * 住宿点对应的品牌
     */
    private String brand;

    /**
     * 住宿点加入的饭店组织
     */
    private String hotelOrganization;

    /**
     * 住宿点获得的资质荣誉
     */
    private String honorQualification;

    /**
     * 住宿点咨询和预订电话
     */
    private String phone;

    /**
     * 住宿点传真号码
     */
    private String fax;

    /**
     * 住宿点网站网址
     */
    private String websiteUrl;

    /**
     * 住宿点WAP网站网址
     */
    private String wapUrl;

    /**
     * 住宿点官方微博、公共服务空间或帐号
     */
    private String offocoalPublicSpace;

    /**
     * 住宿点所在的行政区划
     */
    private String region;

    /**
     * 住宿点的详细地址
     */
    private String address;

    /**
     * 住宿点的邮政编码
     */
    private String postcode;

    /**
     * 住宿点电子地图
     */
    private String electronicMap;

    /**
     * 到达住宿点的交通方式和说明
     */
    private String trafficDescription;

    /**
     * 拥有的客房总数
     */
    private Integer guestRoomNumber;

    /**
     * 拥有的总床位数量
     */
    private Integer totalBedNumber;

    /**
     * 营业时间段
     */
    private String openingTime;

    /**
     * 住宿点最近一次装修的时间
     */
    private String decorateTime;

    /**
     * 住宿点的建筑风格说明
     */
    private String architecturalStyle;

    /**
     * 支持的付款方式和卡种说明
     */
    private String payMethod;

    /**
     * 会议室、会议设施和会议相关服务介绍
     */
    private String faccilitiesServices;

    /**
     * 餐厅和相关服务介绍
     */
    private String diningServices;

    /**
     * 提供的娱乐服务类型和相关服务介绍
     */
    private String entertainmentServices;

    /**
     * 提供的商务服务设施和相关服务介绍
     */
    private String businessServices;

    /**
     * 客房相关设施和服务介绍
     */
    private String roomServices;

    /**
     * 住宿点自有及周边停车场和车位情况
     */
    private String parkingInfomation;

    /**
     * 对应的徽标或商标图片
     */
    private String corporate;

    /**
     * 带有对象明显特征的缩略规格的图片
     */
    private String sketchPicture;

    /**
     * 相关图片组合
     */
    private String pictureLibrary;

    /**
     * 相关视频组合
     */
    private String videoLibrary;

    /**
     * 基于三维或虚拟实景方式的展示素材
     */
    private String virtualMaterial;

    /**
     *
     */
    private String vcode;

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
     * 许可证号
     */
    private String license;

    /**
     * 简称
     */
    private String simpleName;

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
     * 大床房总数
     */
    private Integer bigbed;

    /**
     * 双人间总数
     */
    private Integer doubleRoom;

    /**
     * 三人间总数
     */
    private Integer threePersonRoom;

    /**
     * 四人或以上的房间
     */
    private Integer manyPersonRoom;

    /**
     * 残疾人房间总数
     */
    private Integer disabledPersonRoom;

    /**
     * 中餐厅个数
     */
    private Integer chineseRestaurant;

    /**
     * 西餐厅总数
     */
    private Integer wersternRestaurant;

    /**
     * 餐厅总数
     */
    private Integer resturantCount;

    /**
     * 1：从景区数据中心添加、2：从诚信系统获取的
     */
    private Integer datefrom;

    /**
     * 多功能餐厅总数
     */
    private Integer multiFunctionalCanteen;

    /**
     * 座位数
     */
    private Integer seats;

    /**
     * 登记注册类型
     */
    private String registerType;

    /**
     * 工商注册时间
     */
    private String registerTime;

    /**
     * 评定时间
     */
    private String decideTime;

    /**
     * 添加时间
     */
    private String updateTime;

    /**
     *
     */
    private Integer sort;

    /**
     *
     */
    private String apartmentType;

    /**
     * 最低价格
     */
    private Double checkPrice;

    /**
     * 最低价格时间
     */
    private Date checkPriceTime;

    /**
     * 可定标志
     */
    private Integer playSign;

    /**
     * 720标志
     */
    private Integer sign720;

    /**
     * 资源编码
     */
    private String resourceCode;

    /**
     * 360图片链接
     */
    private String picUrl360;

    /**
     * 数据状态 1 正常，0 禁用， -1 删除
     */
    private Integer status;

    /**
     * 住宿点简要介绍
     */
    private String introduction;

    /**
     *
     */
    private String hotelVhtype;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName == null ? null : zhName.trim();
    }

    public String getUsName() {
        return usName;
    }

    public void setUsName(String usName) {
        this.usName = usName == null ? null : usName.trim();
    }

    public String getAcommodationType() {
        return acommodationType;
    }

    public void setAcommodationType(String acommodationType) {
        this.acommodationType = acommodationType == null ? null : acommodationType.trim();
    }

    public String getTouristHotelLevel() {
        return touristHotelLevel;
    }

    public void setTouristHotelLevel(String touristHotelLevel) {
        this.touristHotelLevel = touristHotelLevel == null ? null : touristHotelLevel.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getHotelOrganization() {
        return hotelOrganization;
    }

    public void setHotelOrganization(String hotelOrganization) {
        this.hotelOrganization = hotelOrganization == null ? null : hotelOrganization.trim();
    }

    public String getHonorQualification() {
        return honorQualification;
    }

    public void setHonorQualification(String honorQualification) {
        this.honorQualification = honorQualification == null ? null : honorQualification.trim();
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

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl == null ? null : websiteUrl.trim();
    }

    public String getWapUrl() {
        return wapUrl;
    }

    public void setWapUrl(String wapUrl) {
        this.wapUrl = wapUrl == null ? null : wapUrl.trim();
    }

    public String getOffocoalPublicSpace() {
        return offocoalPublicSpace;
    }

    public void setOffocoalPublicSpace(String offocoalPublicSpace) {
        this.offocoalPublicSpace = offocoalPublicSpace == null ? null : offocoalPublicSpace.trim();
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
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

    public Integer getGuestRoomNumber() {
        return guestRoomNumber;
    }

    public void setGuestRoomNumber(Integer guestRoomNumber) {
        this.guestRoomNumber = guestRoomNumber;
    }

    public Integer getTotalBedNumber() {
        return totalBedNumber;
    }

    public void setTotalBedNumber(Integer totalBedNumber) {
        this.totalBedNumber = totalBedNumber;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime == null ? null : openingTime.trim();
    }

    public String getDecorateTime() {
        return decorateTime;
    }

    public void setDecorateTime(String decorateTime) {
        this.decorateTime = decorateTime == null ? null : decorateTime.trim();
    }

    public String getArchitecturalStyle() {
        return architecturalStyle;
    }

    public void setArchitecturalStyle(String architecturalStyle) {
        this.architecturalStyle = architecturalStyle == null ? null : architecturalStyle.trim();
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public String getFaccilitiesServices() {
        return faccilitiesServices;
    }

    public void setFaccilitiesServices(String faccilitiesServices) {
        this.faccilitiesServices = faccilitiesServices == null ? null : faccilitiesServices.trim();
    }

    public String getDiningServices() {
        return diningServices;
    }

    public void setDiningServices(String diningServices) {
        this.diningServices = diningServices == null ? null : diningServices.trim();
    }

    public String getEntertainmentServices() {
        return entertainmentServices;
    }

    public void setEntertainmentServices(String entertainmentServices) {
        this.entertainmentServices = entertainmentServices == null ? null : entertainmentServices.trim();
    }

    public String getBusinessServices() {
        return businessServices;
    }

    public void setBusinessServices(String businessServices) {
        this.businessServices = businessServices == null ? null : businessServices.trim();
    }

    public String getRoomServices() {
        return roomServices;
    }

    public void setRoomServices(String roomServices) {
        this.roomServices = roomServices == null ? null : roomServices.trim();
    }

    public String getParkingInfomation() {
        return parkingInfomation;
    }

    public void setParkingInfomation(String parkingInfomation) {
        this.parkingInfomation = parkingInfomation == null ? null : parkingInfomation.trim();
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

    public String getPictureLibrary() {
        return pictureLibrary;
    }

    public void setPictureLibrary(String pictureLibrary) {
        this.pictureLibrary = pictureLibrary == null ? null : pictureLibrary.trim();
    }

    public String getVideoLibrary() {
        return videoLibrary;
    }

    public void setVideoLibrary(String videoLibrary) {
        this.videoLibrary = videoLibrary == null ? null : videoLibrary.trim();
    }

    public String getVirtualMaterial() {
        return virtualMaterial;
    }

    public void setVirtualMaterial(String virtualMaterial) {
        this.virtualMaterial = virtualMaterial == null ? null : virtualMaterial.trim();
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
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

    public Integer getMapType() {
        return mapType;
    }

    public void setMapType(Integer mapType) {
        this.mapType = mapType;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName == null ? null : simpleName.trim();
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

    public Integer getBigbed() {
        return bigbed;
    }

    public void setBigbed(Integer bigbed) {
        this.bigbed = bigbed;
    }

    public Integer getDoubleRoom() {
        return doubleRoom;
    }

    public void setDoubleRoom(Integer doubleRoom) {
        this.doubleRoom = doubleRoom;
    }

    public Integer getThreePersonRoom() {
        return threePersonRoom;
    }

    public void setThreePersonRoom(Integer threePersonRoom) {
        this.threePersonRoom = threePersonRoom;
    }

    public Integer getManyPersonRoom() {
        return manyPersonRoom;
    }

    public void setManyPersonRoom(Integer manyPersonRoom) {
        this.manyPersonRoom = manyPersonRoom;
    }

    public Integer getDisabledPersonRoom() {
        return disabledPersonRoom;
    }

    public void setDisabledPersonRoom(Integer disabledPersonRoom) {
        this.disabledPersonRoom = disabledPersonRoom;
    }

    public Integer getChineseRestaurant() {
        return chineseRestaurant;
    }

    public void setChineseRestaurant(Integer chineseRestaurant) {
        this.chineseRestaurant = chineseRestaurant;
    }

    public Integer getWersternRestaurant() {
        return wersternRestaurant;
    }

    public void setWersternRestaurant(Integer wersternRestaurant) {
        this.wersternRestaurant = wersternRestaurant;
    }

    public Integer getResturantCount() {
        return resturantCount;
    }

    public void setResturantCount(Integer resturantCount) {
        this.resturantCount = resturantCount;
    }

    public Integer getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(Integer datefrom) {
        this.datefrom = datefrom;
    }

    public Integer getMultiFunctionalCanteen() {
        return multiFunctionalCanteen;
    }

    public void setMultiFunctionalCanteen(Integer multiFunctionalCanteen) {
        this.multiFunctionalCanteen = multiFunctionalCanteen;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType == null ? null : registerType.trim();
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime == null ? null : registerTime.trim();
    }

    public String getDecideTime() {
        return decideTime;
    }

    public void setDecideTime(String decideTime) {
        this.decideTime = decideTime == null ? null : decideTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType == null ? null : apartmentType.trim();
    }

    public Double getCheckPrice() {
        return checkPrice;
    }

    public void setCheckPrice(Double checkPrice) {
        this.checkPrice = checkPrice;
    }

    public Date getCheckPriceTime() {
        return checkPriceTime;
    }

    public void setCheckPriceTime(Date checkPriceTime) {
        this.checkPriceTime = checkPriceTime;
    }

    public Integer getPlaySign() {
        return playSign;
    }

    public void setPlaySign(Integer playSign) {
        this.playSign = playSign;
    }

    public Integer getSign720() {
        return sign720;
    }

    public void setSign720(Integer sign720) {
        this.sign720 = sign720;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode == null ? null : resourceCode.trim();
    }

    public String getPicUrl360() {
        return picUrl360;
    }

    public void setPicUrl360(String picUrl360) {
        this.picUrl360 = picUrl360 == null ? null : picUrl360.trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getHotelVhtype() {
        return hotelVhtype;
    }

    public void setHotelVhtype(String hotelVhtype) {
        this.hotelVhtype = hotelVhtype;
    }
}