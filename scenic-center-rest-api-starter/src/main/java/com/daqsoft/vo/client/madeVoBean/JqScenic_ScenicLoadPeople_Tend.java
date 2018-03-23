package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-07 11:56.
 */
public class JqScenic_ScenicLoadPeople_Tend implements Serializable {
    private  String people;
    private String scenicName;
    private String loadPeople;

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getLoadPeople() {
        return loadPeople;
    }

    public void setLoadPeople(String loadPeople) {
        this.loadPeople = loadPeople;
    }
}