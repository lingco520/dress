package com.daqsoft.clientUtils.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: 景区大类
 * @Author: lyl
 * @Date: 2017/12/20 11:33
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("scenics")
public class Scenics implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamImplicit
    private List<City> city = new ArrayList();

    public List<City> getCity() {
        return this.city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public Scenics() {
    }

    public Scenics(List<City> city) {
        this.city = city;
    }

    public String toString() {
        return "Scenics [city=" + this.city + "]";
    }
}