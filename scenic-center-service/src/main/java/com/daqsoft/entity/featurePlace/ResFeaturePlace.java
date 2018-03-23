package com.daqsoft.entity.featurePlace;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: tanggm
 * @Date: 2018/01/22 15:00
 * @Description: TODO 基础资源-特色旅游场所
 */
public class ResFeaturePlace implements Serializable{
    /**
     *记录ID
     */
    private Long id;
    /**
     *营业执照号码
     */
    private String bizlicense;
    /**
     *场所名称(中文全称)
     */
    private String name;
    /**
     *旅游场所简称
     */
    private String shortName;
    /**
     *车位
     */
    private Integer parkingLot;
    /**
     *人均消费
     */
    private String consumption;
    /**
     *企业工商注册全称
     */
    private String icname;
    /**
     *企业个性标识图
     */
    private String individualitylogo;
    /**
     *法人代表
     */
    private String legal;
    /**
     *许可证号
     */
    private String licenseno;
    /**
     *企业标识大图
     */
    private String logobig;
    /**
     *企业标识小图
     */
    private String logosmall;
    /**
     *经营负责人
     */
    private String manager;
    /**
     *开放时间
     */
    private String opentime;
    /**
     *宣传片路径
     */
    private String videoPath;
    /**
     *宣传片名称
     */
    private String videoName;
    /**
     *电子邮箱
     */
    private String mailBox;
    /**
     *邮编
     */
    private String zipCode;
    /**
     *在线购票地址
     */
    private String zxticket;
    /**
     *排序
     */
    private Integer sort;
    /**
     *数据状态 1 正常，0 禁用， -1 删除
     */
    private Integer status;
    /**
     *更新时间
     */
    private Date updateTime;
    /**
     *景区编码
     */
    private String vcode;
    /**
     *内容
     */
    private String content;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getBizlicense() {
        return bizlicense;
    }

    public void setBizlicense(String bizlicense) {
        this.bizlicense = bizlicense == null ? null : bizlicense.trim();
    }

    public Integer getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(Integer parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption == null ? null : consumption.trim();
    }

    public String getIcname() {
        return icname;
    }

    public void setIcname(String icname) {
        this.icname = icname == null ? null : icname.trim();
    }

    public String getIndividualitylogo() {
        return individualitylogo;
    }

    public void setIndividualitylogo(String individualitylogo) {
        this.individualitylogo = individualitylogo == null ? null : individualitylogo.trim();
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal == null ? null : legal.trim();
    }

    public String getLicenseno() {
        return licenseno;
    }

    public void setLicenseno(String licenseno) {
        this.licenseno = licenseno == null ? null : licenseno.trim();
    }

    public String getLogobig() {
        return logobig;
    }

    public void setLogobig(String logobig) {
        this.logobig = logobig == null ? null : logobig.trim();
    }

    public String getLogosmall() {
        return logosmall;
    }

    public void setLogosmall(String logosmall) {
        this.logosmall = logosmall == null ? null : logosmall.trim();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime == null ? null : opentime.trim();
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getMailBox() {
        return mailBox;
    }

    public void setMailBox(String mailBox) {
        this.mailBox = mailBox == null ? null : mailBox.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getZxticket() {
        return zxticket;
    }

    public void setZxticket(String zxticket) {
        this.zxticket = zxticket == null ? null : zxticket.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getMapType() {
        return mapType;
    }

    public void setMapType(Integer mapType) {
        this.mapType = mapType;
    }
}