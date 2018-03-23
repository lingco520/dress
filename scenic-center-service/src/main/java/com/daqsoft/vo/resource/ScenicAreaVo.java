package com.daqsoft.vo.resource;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/28 0028.
 */

public class ScenicAreaVo implements Serializable {
    /**
     * 主键id
     */
    private  Long id;
    /**
     * 名称
     */
    private  String  name;
    /**
     * 经度
     */
    private  String  longitude;
    /**
     * 纬度
     */
    private  String  latitude;

    public ScenicAreaVo(){}

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
}
