package com.daqsoft.entity.roadTraffic;

/**
 * @Title: 道路交通
 * @Author: lyl .
 * @Date: Created in 15:41 2018/3/8.
 * @Version: 4.0.0
 * @describe:道路交通
 * @since:JDK 1.8
 */

public class ResScenicRoadTraffic {
    /**
     * 
     */
    private Long id;

    /**
     * 开始地点名称
     */
    private String startSection;

    /**
     * 开始地点纬度
     */
    private String startLatitude;

    /**
     * 开始点位经度
     */
    private String startLongitude;

    /**
     * 结束点位名称
     */
    private String endSection;

    /**
     * 结束点位纬度
     */
    private String endLatitude;

    /**
     * 结束点位经度
     */
    private String endLongitude;

    /**
     * 
     */
    private String vcode;

    /**
     * 路段名称
     */
    private String name;

    private String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartSection() {
        return startSection;
    }

    public void setStartSection(String startSection) {
        this.startSection = startSection == null ? null : startSection.trim();
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude == null ? null : startLatitude.trim();
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude == null ? null : startLongitude.trim();
    }

    public String getEndSection() {
        return endSection;
    }

    public void setEndSection(String endSection) {
        this.endSection = endSection == null ? null : endSection.trim();
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude == null ? null : endLatitude.trim();
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude == null ? null : endLongitude.trim();
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}