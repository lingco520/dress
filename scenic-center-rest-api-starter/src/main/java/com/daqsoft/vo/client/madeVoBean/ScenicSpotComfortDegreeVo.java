package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: caol.
 * @Date: Created in 2017/6/4.
 * @Version: V3.0.0.
 * @describe:景点舒适度（WIFI统计）
 */
public class ScenicSpotComfortDegreeVo implements java.io.Serializable {
    private String id ;//景点id主键
    private String sumPeople;//景点当前客流量
    private String name;//景点名称
    private String longitude;//景点经度
    private String latitude;//景点纬度
    private String vcode;//景区编码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSumPeople() {
        return sumPeople;
    }

    public void setSumPeople(String sumPeople) {
        this.sumPeople = sumPeople;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}
