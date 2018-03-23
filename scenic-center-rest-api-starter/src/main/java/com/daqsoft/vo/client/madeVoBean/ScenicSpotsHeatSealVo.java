package com.daqsoft.vo.client.madeVoBean;

/**
 * Created by yangk on 2017-5-24.
 * 三维地图热电开关vo
 */
public class ScenicSpotsHeatSealVo {
    private long id;
    private long sumPeople;
    private String name;
    private long personCount;
    private String longitude;
    private String latitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSumPeople() {
        return sumPeople;
    }

    public void setSumPeople(long sumPeople) {
        this.sumPeople = sumPeople;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPersonCount() {
        return personCount;
    }

    public void setPersonCount(long personCount) {
        this.personCount = personCount;
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
