package com.daqsoft.vo.client.madeVoBean;

/**
 * Created by yangk on 2017-5-26.
 * 三维地图模块显示地图
 */
public class SysConfigTendVo {
    private String bodyBackgroundColor = "";
    private String mapDefaulSize = "";
    private String mapTitle = "";
    private String scales = "";
    private String centerLon = "";
    private String centerLat = "";
    private String mapurl = "";
    private String mapboundsleft = "";
    private String mapboundsbottom = "";
    private String mapboundsright = "";
    private String mapboundstop = "";
    private String mapScale = "";
    // GIS地图 弹出框 距离底部距离
    private String popupBottom  = "";
    /**
     * 地图类型（百度 bd,高德 gd,超图 ct,以前默认的 空字符串）
     */
    private String mapType = "";

    public String getBodyBackgroundColor() {
        return bodyBackgroundColor;
    }

    public void setBodyBackgroundColor(String bodyBackgroundColor) {
        this.bodyBackgroundColor = bodyBackgroundColor;
    }

    public String getMapDefaulSize() {
        return mapDefaulSize;
    }

    public void setMapDefaulSize(String mapDefaulSize) {
        this.mapDefaulSize = mapDefaulSize;
    }

    public String getMapTitle() {
        return mapTitle;
    }

    public void setMapTitle(String mapTitle) {
        this.mapTitle = mapTitle;
    }

    public String getScales() {
        return scales;
    }

    public void setScales(String scales) {
        this.scales = scales;
    }

    public String getCenterLon() {
        return centerLon;
    }

    public void setCenterLon(String centerLon) {
        this.centerLon = centerLon;
    }

    public String getCenterLat() {
        return centerLat;
    }

    public void setCenterLat(String centerLat) {
        this.centerLat = centerLat;
    }

    public String getMapurl() {
        return mapurl;
    }

    public void setMapurl(String mapurl) {
        this.mapurl = mapurl;
    }

    public String getMapboundsleft() {
        return mapboundsleft;
    }

    public void setMapboundsleft(String mapboundsleft) {
        this.mapboundsleft = mapboundsleft;
    }

    public String getMapboundsbottom() {
        return mapboundsbottom;
    }

    public void setMapboundsbottom(String mapboundsbottom) {
        this.mapboundsbottom = mapboundsbottom;
    }

    public String getMapboundsright() {
        return mapboundsright;
    }

    public void setMapboundsright(String mapboundsright) {
        this.mapboundsright = mapboundsright;
    }

    public String getMapboundstop() {
        return mapboundstop;
    }

    public void setMapboundstop(String mapboundstop) {
        this.mapboundstop = mapboundstop;
    }

    public String getMapScale() {
        return mapScale;
    }

    public void setMapScale(String mapScale) {
        this.mapScale = mapScale;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public String getPopupBottom() {
        return popupBottom;
    }

    public void setPopupBottom(String popupBottom) {
        this.popupBottom = popupBottom;
    }
}
