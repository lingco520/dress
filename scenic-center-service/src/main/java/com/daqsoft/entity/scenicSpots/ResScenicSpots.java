package com.daqsoft.entity.scenicSpots;

import java.io.Serializable;

/**
 * @Title:景点信息entity
 * @Author: superziy .
 * @Date: Created in 10:04 2018/1/10.
 * @Version: 4.0.0
 * @describe:  景点信息entity
 * @since:JDK 1.8
 */
public class ResScenicSpots implements Serializable{
    //主键
    private Long id;
    //景区景点的中文名称
    private String name;
    //   景区景点的中文别名
    private String zhName;
    //景区景点的英文或拼音全称
    private String usName;
    //可概括性定义对象的一个或多个词或短语
    private String keyWord;
    //景区所属的行政区划
    private String region;
    //所属的景区类型；根据 GB/T 18972确定
    private String scenicType;
    //景点最大承载量
    private Integer maxquantity;
    //AAAAA级旅游区(点)、AAAA级旅游区(点)、AAA级旅游区(点)、AA级旅游区(点)、A级旅游区(点)；非A级旅游区(点)；根据GB/T 17775-2003确定
    private String scenicLevel;
    //景区景点门票价格
    private String ticketPrice;
    //景区门票针对价格说明和优惠打折办法
    private String ticketDescription;
    //景区的详细地址
    private String scenicAddress;
    //景区电子地图
    private String electronicMap;
    //到达景点的交通方式说明
    private String trafficDescription;
    //附近的标志性建筑、地点或点域
    private String surroundingLandmark;
    //景点景点对游客开放的时间
    private String businessHours;
    //游客在景点景点游览一般所需的时间
    private String longTour;
    //景点适合游客游览的时间，一般以月份和季度表示
    private String recommendedTravelTime;
    //景点游览示意图
    private String tourMap;
    //对应的徽标或商标图片
    private String corporate;
    //带有对象明显特征的缩略规格的图片
    private String sketchPicture;
    //景点相关图片
    private String pictureLibrary;
    //相关视频
    private String videoLibrary;
    //基于三维或虚拟实景方式的展示素材
    private String virtualMaterial;
    //状态
    private Integer status;
    //排序
    private Integer sort;
    //景点编码
    private String vcode;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //地图类型：0 gis地图  1 百度地图 2高德地图
    private Integer mapType;
    //景点基本情况介绍
    private String introduction;
    //景点景点导游词
    private String tourGuideWord;
    //景点内特色景观介绍
    private String characteristicLandscape;

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

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord == null ? null : keyWord.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getScenicType() {
        return scenicType;
    }

    public void setScenicType(String scenicType) {
        this.scenicType = scenicType == null ? null : scenicType.trim();
    }

    public String getScenicLevel() {
        return scenicLevel;
    }

    public void setScenicLevel(String scenicLevel) {
        this.scenicLevel = scenicLevel == null ? null : scenicLevel.trim();
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice == null ? null : ticketPrice.trim();
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription == null ? null : ticketDescription.trim();
    }

    public String getScenicAddress() {
        return scenicAddress;
    }

    public void setScenicAddress(String scenicAddress) {
        this.scenicAddress = scenicAddress == null ? null : scenicAddress.trim();
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

    public String getSurroundingLandmark() {
        return surroundingLandmark;
    }

    public void setSurroundingLandmark(String surroundingLandmark) {
        this.surroundingLandmark = surroundingLandmark == null ? null : surroundingLandmark.trim();
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours == null ? null : businessHours.trim();
    }

    public String getLongTour() {
        return longTour;
    }

    public void setLongTour(String longTour) {
        this.longTour = longTour == null ? null : longTour.trim();
    }

    public String getRecommendedTravelTime() {
        return recommendedTravelTime;
    }

    public void setRecommendedTravelTime(String recommendedTravelTime) {
        this.recommendedTravelTime = recommendedTravelTime == null ? null : recommendedTravelTime.trim();
    }

    public String getTourMap() {
        return tourMap;
    }

    public void setTourMap(String tourMap) {
        this.tourMap = tourMap == null ? null : tourMap.trim();
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTourGuideWord() {
        return tourGuideWord;
    }

    public void setTourGuideWord(String tourGuideWord) {
        this.tourGuideWord = tourGuideWord;
    }

    public String getCharacteristicLandscape() {
        return characteristicLandscape;
    }

    public void setCharacteristicLandscape(String characteristicLandscape) {
        this.characteristicLandscape = characteristicLandscape;
    }

    public Integer getMaxquantity() {
        return maxquantity;
    }

    public void setMaxquantity(Integer maxquantity) {
        this.maxquantity = maxquantity;
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