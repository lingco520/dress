package com.daqsoft.vo.client.monitorConfig;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: 景区
 * @Author: lyl
 * @Date: 2017/12/20 11:33
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("scenic")
public class Scenic implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamAsAttribute
    private String code = "";
    @XStreamAsAttribute
    private String name = "";
    @XStreamAsAttribute
    private String monitorCount = "";
    @XStreamAsAttribute
    private String normal = "";
    @XStreamAsAttribute
    private String spinyin = "";
    @XStreamImplicit
    private List<Item> item = new ArrayList();

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonitorCount() {
        return this.monitorCount;
    }

    public void setMonitorCount(String monitorCount) {
        this.monitorCount = monitorCount;
    }

    public String getNormal() {
        return this.normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getSpinyin() {
        return spinyin;
    }

    public void setSpinyin(String spinyin) {
        this.spinyin = spinyin;
    }

    public List<Item> getItem() {
        return this.item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public Scenic() {
    }

    public Scenic(String code, String name, String monitorCount, String normal, String spinyin, List<Item> item) {

        if (code == null) {
            code = "";
        }

        if (name == null) {
            name = "";
        }

        if (monitorCount == null) {
            monitorCount = "";
        }

        if (normal == null) {
            normal = "";
        }

        if (spinyin == null) {
            spinyin = "";
        }

        this.code = code;
        this.name = name;
        this.monitorCount = monitorCount;
        this.normal = normal;
        this.spinyin = spinyin;
        this.item = item;
    }


    public String toString() {
        return "Scenic [code=" + this.code + ", name=" + this.name + ", monitorCount=" + this.monitorCount + ", normal=" + this.normal + ", spinyin=" + this.spinyin + ", item=" + this.item + "]";
    }
}