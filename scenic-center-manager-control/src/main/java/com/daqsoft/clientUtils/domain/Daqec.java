package com.daqsoft.clientUtils.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: 屏幕微件
 * @Author: lyl
 * @Date: 2018-02-06 11:31
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("screenConf")
public class Daqec implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamImplicit
    private List<Widgts> widgts = new ArrayList();

    public List<Widgts> getWidgts() {
        return this.widgts;
    }

    public void setWidgts(List<Widgts> widgts) {
        this.widgts = widgts;
    }

    public Daqec() {
    }

    public Daqec(List<Widgts> widgts) {
        this.widgts = widgts;
    }

    public String toString() {
        return "Daqec [widgts=" + this.widgts + "]";
    }
}
