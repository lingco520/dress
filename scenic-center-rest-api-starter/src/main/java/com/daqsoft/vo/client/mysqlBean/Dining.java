package com.daqsoft.vo.client.mysqlBean;

/**
 * @Author: yangkang .
 * @Date: Created in 2017-6-7.
 * @Version: V3.0.0.
 * @describe:餐饮
 */
public class Dining {
    private int id;
    private String name;
    private String longitude = "";
    private String latitude = "";

    public Dining() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
