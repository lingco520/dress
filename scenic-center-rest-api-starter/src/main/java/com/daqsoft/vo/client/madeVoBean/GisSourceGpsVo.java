package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: superziy .
 * @Date: Created in 13:43 2017/6/6.
 * @Version: 3.0.0
 * @describe: gis地图景区景点
 */


public class GisSourceGpsVo implements java.io.Serializable {
    private String types;//类型
    private String id;//id
    private String introduction;//名称
    private String pictureLibrary;//名称
    private String name;//名称
    private String longitude = "";//经度
    private String latitude = "";//纬度

    public GisSourceGpsVo() {

    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPictureLibrary() {
        return pictureLibrary;
    }

    public void setPictureLibrary(String pictureLibrary) {
        this.pictureLibrary = pictureLibrary;
    }
}
