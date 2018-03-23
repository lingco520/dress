package com.daqsoft.vo.client.monitorConfig;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: 地区
 * @Author: lyl
 * @Date: 2018-02-06 11:30
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("city")
public class City implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamAsAttribute
    private String name = "";
    @XStreamAsAttribute
    private String region = "";
    @XStreamAsAttribute
    private String scenicCount = "";
    @XStreamImplicit
    private List<Scenic> scenic = new ArrayList();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getScenicCount() {
        return this.scenicCount;
    }

    public void setScenicCount(String scenicCount) {
        this.scenicCount = scenicCount;
    }

    public List<Scenic> getScenic() {
        return this.scenic;
    }

    public void setScenic(List<Scenic> scenic) {
        this.scenic = scenic;
    }

    public City() {
    }

    public City(String name, String region, String scenicCount, List<Scenic> scenic) {
        if (name == null) {
            name = "";
        }

        if (region == null) {
            region = "";
        }

        if (scenicCount == null) {
            scenicCount = "";
        }

        this.name = name;
        this.region = region;
        this.scenicCount = scenicCount;
        this.scenic = scenic;
    }

    public String toString() {
        return "City [name=" + this.name + ", region=" + this.region + ", scenicCount=" + this.scenicCount + ", scenic=" + this.scenic + "]";
    }
}
