package com.daqsoft.vo.client.madeVoBean;


import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 超图配置类
 * @author: PGmm
 * @date: 2017/8/25
 * @version: 1.0.0
 * @since JDK 1.8
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuperMapConfig {
    private Long id;
    private String mapboundsTop; //	地图向上间距
    private String mapboundsRight; //		地图向右间距
    private String mapboundsBottom; //		地图向下间距
    private String mapboundsLeft; //		地图向左间距
    private String mapUrl; //		地图链接
    private String centerLat; //		纬度
    private String centerLon; //		经度
    private String scales; //
    private String mapTitle; //		标题
    private String mapDefaulSize; //		-地图大小
    private String bodyBackgroundColor; //		-背景色
    private String userPlatform; //	 //使用平台
    private String status;
    private String vcode;
    private String scenic;

    public String getScenic() {
        return scenic;
    }

    public void setScenic(String scenic) {
        this.scenic = scenic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public SuperMapConfig() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMapboundsTop() {
        return mapboundsTop;
    }

    public void setMapboundsTop(String mapboundsTop) {
        this.mapboundsTop = mapboundsTop;
    }

    public String getMapboundsRight() {
        return mapboundsRight;
    }

    public void setMapboundsRight(String mapboundsRight) {
        this.mapboundsRight = mapboundsRight;
    }

    public String getMapboundsBottom() {
        return mapboundsBottom;
    }

    public void setMapboundsBottom(String mapboundsBottom) {
        this.mapboundsBottom = mapboundsBottom;
    }

    public String getMapboundsLeft() {
        return mapboundsLeft;
    }

    public void setMapboundsLeft(String mapboundsLeft) {
        this.mapboundsLeft = mapboundsLeft;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getCenterLat() {
        return centerLat;
    }

    public void setCenterLat(String centerLat) {
        this.centerLat = centerLat;
    }

    public String getCenterLon() {
        return centerLon;
    }

    public void setCenterLon(String centerLon) {
        this.centerLon = centerLon;
    }

    public String getScales() {
        return scales;
    }

    public void setScales(String scales) {
        this.scales = scales;
    }

    public String getMapTitle() {
        return mapTitle;
    }

    public void setMapTitle(String mapTitle) {
        this.mapTitle = mapTitle;
    }

    public String getMapDefaulSize() {
        return mapDefaulSize;
    }

    public void setMapDefaulSize(String mapDefaulSize) {
        this.mapDefaulSize = mapDefaulSize;
    }

    public String getBodyBackgroundColor() {
        return bodyBackgroundColor;
    }

    public void setBodyBackgroundColor(String bodyBackgroundColor) {
        this.bodyBackgroundColor = bodyBackgroundColor;
    }

    public String getUserPlatform() {
        return userPlatform;
    }

    public void setUserPlatform(String userPlatform) {
        this.userPlatform = userPlatform;
    }
}