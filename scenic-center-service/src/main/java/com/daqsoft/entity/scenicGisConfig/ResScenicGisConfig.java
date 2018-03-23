package com.daqsoft.entity.scenicGisConfig;

import java.util.Date;

/**
 * @Title:ResScenicGisConfig
 * @Author: superziy .
 * @Date: Created in 9:57 2018/1/30.
 * @Version: 4.0.0
 * @describe: 景区gis地图配置
 * @since:JDK 1.8
 */
public class ResScenicGisConfig {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 地图链接
     */
    private String url;

    /**
     * 中心点经度
     */
    private String centerLongitude;

    /**
     * 中心点纬度
     */
    private String centerLatitude;

    /**
     * 缩放比例
     */
    private Integer mapScale;

    /**
     * 底部距离
     */
    private String popupBottom;

    /**
     * 背景色
     */
    private String backgroundColor;

    /**
     * 景区唯一编码
     */
    private String vcode;

    /**
     * 状态
     */
    private Integer status;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(String centerLongitude) {
        this.centerLongitude = centerLongitude == null ? null : centerLongitude.trim();
    }

    public String getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(String centerLatitude) {
        this.centerLatitude = centerLatitude == null ? null : centerLatitude.trim();
    }

    public Integer getMapScale() {
        return mapScale;
    }

    public void setMapScale(Integer mapScale) {
        this.mapScale = mapScale;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor == null ? null : backgroundColor.trim();
    }

    public String getPopupBottom() {
        return popupBottom;
    }

    public void setPopupBottom(String popupBottom) {
        this.popupBottom = popupBottom;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}