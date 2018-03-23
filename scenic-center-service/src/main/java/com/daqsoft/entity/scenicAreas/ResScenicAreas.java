package com.daqsoft.entity.scenicAreas;

public class ResScenicAreas {
    //主键
    private Long id;
    //景区详细地址
    private String address;
    //纬度
    private String latitude;
    //经度
    private String longitude;
    //景区等级
    private String level;
    //景区名称
    private String name;
    //别名
    private String alias;
    //英文名称
    private String englishname;
    //开放时间
    private String openTime;
    //旅游咨询电话
    private String phone;
    //客服传真电话
    private String faxphone;
    //行政区划
    private String region;
    //建议游玩时间
    private Integer playTime;
    //景区类型
    private String scenicType;
    //景区最大承载量
    private Integer maxquantity;
    //唯一标示
    private String vcode;
    //状态
    private Integer status;
    //略缩图
    private String sketchPicture;
    //景区门票价格
    private Integer ticketprice;
    //相关荣誉资质称号
    private String relatedtitles;
    //邮政编码
    private Integer postcode;
    //景区电子地图
    private String electronimg;
    //景区网站网址
    private String scenicWeb;
    //景区微博
    private String scenicWeibo;
    //景区电子邮箱
    private String scenicEmail;
    //景区游乐设施
    private String recreationFacility;
    //服务设施
    private String serviceFacility;
    //特色活动
    private String specialevents;
    //导游设备租金
    private Integer guidedevicerental;
    //景区游览示意图
    private String sketchMap;
    //标志图片
    private String brandImg;
    //介绍
    private String introduce;
    //景区服务内容
    private String serviceContent;
    //常规旅游路线
    private String tourRoute;
    //注意事项
    private String announcements;

    public ResScenicAreas() {

    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getTourRoute() {
        return tourRoute;
    }

    public void setTourRoute(String tourRoute) {
        this.tourRoute = tourRoute;
    }

    public String getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(String announcements) {
        this.announcements = announcements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname == null ? null : englishname.trim();
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime == null ? null : openTime.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFaxphone() {
        return faxphone;
    }

    public void setFaxphone(String faxphone) {
        this.faxphone = faxphone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public String getScenicType() {
        return scenicType;
    }

    public void setScenicType(String scenicType) {
        this.scenicType = scenicType == null ? null : scenicType.trim();
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSketchPicture() {
        return sketchPicture;
    }

    public void setSketchPicture(String sketchPicture) {
        this.sketchPicture = sketchPicture == null ? null : sketchPicture.trim();
    }

    public Integer getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(Integer ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getRelatedtitles() {
        return relatedtitles;
    }

    public void setRelatedtitles(String relatedtitles) {
        this.relatedtitles = relatedtitles == null ? null : relatedtitles.trim();
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public String getElectronimg() {
        return electronimg;
    }

    public void setElectronimg(String electronimg) {
        this.electronimg = electronimg == null ? null : electronimg.trim();
    }

    public String getScenicWeb() {
        return scenicWeb;
    }

    public void setScenicWeb(String scenicWeb) {
        this.scenicWeb = scenicWeb == null ? null : scenicWeb.trim();
    }

    public String getScenicWeibo() {
        return scenicWeibo;
    }

    public void setScenicWeibo(String scenicWeibo) {
        this.scenicWeibo = scenicWeibo == null ? null : scenicWeibo.trim();
    }

    public String getScenicEmail() {
        return scenicEmail;
    }

    public void setScenicEmail(String scenicEmail) {
        this.scenicEmail = scenicEmail == null ? null : scenicEmail.trim();
    }

    public String getRecreationFacility() {
        return recreationFacility;
    }

    public void setRecreationFacility(String recreationFacility) {
        this.recreationFacility = recreationFacility == null ? null : recreationFacility.trim();
    }

    public String getServiceFacility() {
        return serviceFacility;
    }

    public void setServiceFacility(String serviceFacility) {
        this.serviceFacility = serviceFacility == null ? null : serviceFacility.trim();
    }

    public String getSpecialevents() {
        return specialevents;
    }

    public void setSpecialevents(String specialevents) {
        this.specialevents = specialevents == null ? null : specialevents.trim();
    }

    public Integer getGuidedevicerental() {
        return guidedevicerental;
    }

    public void setGuidedevicerental(Integer guidedevicerental) {
        this.guidedevicerental = guidedevicerental;
    }

    public String getSketchMap() {
        return sketchMap;
    }

    public void setSketchMap(String sketchMap) {
        this.sketchMap = sketchMap == null ? null : sketchMap.trim();
    }

    public String getBrandImg() {
        return brandImg;
    }

    public void setBrandImg(String brandImg) {
        this.brandImg = brandImg == null ? null : brandImg.trim();
    }

    public Integer getMaxquantity() {
        return maxquantity;
    }

    public void setMaxquantity(Integer maxquantity) {
        this.maxquantity = maxquantity;
    }
}