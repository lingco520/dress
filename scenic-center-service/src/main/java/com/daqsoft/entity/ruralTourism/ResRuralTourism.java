package com.daqsoft.entity.ruralTourism;

import java.util.Date;

/**
 * @Title:乡村旅游
 * @Author: superziy .
 * @Date: Created in 16:40 2018/1/24.
 * @Version: 4.0.0
 * @describe: 乡村旅游
 * @since:JDK 1.8
 */
public class ResRuralTourism {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 腾讯微博
     */
    private String tencentWeibo;

    /**
     * 新浪微博
     */
    private String sinaWeibo;

    /**
     * 微信
     */
    private String weixin;

    /**
     * 乡村旅游
     */
    private String ruralTourismType;

    /**
     * 农家乐等级
     */
    private String ruralTourismLevel;

    /**
     * 行政区划
     */
    private String region;

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
     * 标志图片
     */
    private String corporate;

    /**
     * logo图片
     */
    private String logo;

    /**
     * 视频地址
     */
    private String video;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 网址
     */
    private String webSite;

    /**
     * 
     */
    private Integer sort;

    /**
     * 状态 1启用 0禁用 -1删除
     */
    private Integer status;

    /**
     * 景区编码
     */
    private String vcode;

    /**
     * 更新时间
     */
    private Date updateTime;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTencentWeibo() {
        return tencentWeibo;
    }

    public void setTencentWeibo(String tencentWeibo) {
        this.tencentWeibo = tencentWeibo;
    }

    public String getSinaWeibo() {
        return sinaWeibo;
    }

    public void setSinaWeibo(String sinaWeibo) {
        this.sinaWeibo = sinaWeibo == null ? null : sinaWeibo.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getRuralTourismType() {
        return ruralTourismType;
    }

    public void setRuralTourismType(String ruralTourismType) {
        this.ruralTourismType = ruralTourismType == null ? null : ruralTourismType.trim();
    }

    public String getRuralTourismLevel() {
        return ruralTourismLevel;
    }

    public void setRuralTourismLevel(String ruralTourismLevel) {
        this.ruralTourismLevel = ruralTourismLevel == null ? null : ruralTourismLevel.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
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

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate == null ? null : corporate.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video == null ? null : video.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite == null ? null : webSite.trim();
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

    public Integer getMapType() {
        return mapType;
    }

    public void setMapType(Integer mapType) {
        this.mapType = mapType;
    }
}