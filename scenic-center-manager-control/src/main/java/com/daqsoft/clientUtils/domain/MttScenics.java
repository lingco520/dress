package com.daqsoft.clientUtils.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: 景区分封装
 * @Author: lyl
 * @Date: 2017/12/20 11:32
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("mttScenics")
public class MttScenics implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamImplicit
    private List<Scenic> scenic = new ArrayList();

    public List<Scenic> getScenic() {
        return this.scenic;
    }

    public void setScenic(List<Scenic> scenic) {
        this.scenic = scenic;
    }

    public MttScenics() {
    }

    public MttScenics(List<Scenic> scenic) {
        this.scenic = scenic;
    }

    public String toString() {
        return "MttScenics [scenic=" + this.scenic + "]";
    }
}