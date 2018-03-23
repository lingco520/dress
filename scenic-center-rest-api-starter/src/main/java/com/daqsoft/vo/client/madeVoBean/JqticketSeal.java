package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author chenxia
 * @version V3.0.0
 * @date 2017-06-08 13:55.
 * @Description
 */
public class JqticketSeal implements Serializable {
    private  String name;
    private  long number;
    private  String proportion;
    public JqticketSeal(){}
    public long getNumber() {
        return number;
    }
    public void setNumber(long number) {
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getProportion() {
        return proportion;
    }
    public void setProportion(String proportion) {
        this.proportion = proportion;
    }
}