package com.daqsoft.clientUtils.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * @Title: 监控
 * @Author: lyl
 * @Date: 2018-02-06 11:32
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("item")
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamAsAttribute
    private String id = "";
    @XStreamAsAttribute
    private String name = "";
    @XStreamAsAttribute
    private String imgUrl = "";
    @XStreamAsAttribute
    private String normal = "";
    @XStreamAsAttribute
    private String numCode = "";
    @XStreamAsAttribute
    private String address = "";
    @XStreamAsAttribute
    private String ocx = "";
    @XStreamAsAttribute
    private String port = "";
    @XStreamAsAttribute
    private String pwd = "";
    @XStreamAsAttribute
    private String userName = "";
    @XStreamAsAttribute
    private String sort = "";
    @XStreamAsAttribute
    private String spinyin = "";

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNormal() {
        return this.normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getNumCode() {
        return this.numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getOcx() {
        return this.ocx;
    }

    public void setOcx(String ocx) {
        this.ocx = ocx;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpinyin() {
        return spinyin;
    }

    public void setSpinyin(String spinyin) {
        this.spinyin = spinyin;
    }

    public Item() {
    }

    public Item(String id, String name, String imgUrl, String normal, String numCode, String address, String ocx, String port, String pwd, String userName, String sort, String spinyin) {
        if (id == null) {
            id = "";
        }

        if (name == null) {
            name = "";
        }

        if (imgUrl == null) {
            imgUrl = "";
        }

        if (normal == null) {
            normal = "";
        }

        if (numCode == null) {
            numCode = "";
        }

        if (address == null) {
            address = "";
        }

        if (ocx == null) {
            ocx = "";
        }

        if (port == null) {
            port = "";
        }

        if (pwd == null) {
            pwd = "";
        }

        if (userName == null) {
            userName = "";
        }

        if (sort == null) {
            sort = "";
        }

        if (spinyin == null) {
            spinyin = "";
        }

        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.normal = normal;
        this.numCode = numCode;
        this.address = address;
        this.ocx = ocx;
        this.port = port;
        this.pwd = pwd;
        this.userName = userName;
        this.sort = sort;
        this.spinyin = spinyin;
    }

    public String toString() {
        return "Item [id=" + this.id + ", name=" + this.name + ", spinyin=" + this.spinyin + ", imgUrl=" + this.imgUrl + ", normal=" + this.normal + ", numCode=" + this.numCode + ", address=" + this.address + ", ocx=" + this.ocx + ", port=" + this.port + ", pwd=" + this.pwd + ", userName=" + this.userName + ", sort=" + this.sort + "]";
    }
}
