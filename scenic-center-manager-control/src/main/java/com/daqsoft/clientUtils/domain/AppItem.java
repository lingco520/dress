package com.daqsoft.clientUtils.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * @Title: 导航实体
 * @Author: lyl
 * @Date: 2018-02-06 11:23
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("app")
public class AppItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamAsAttribute
    private String id = "";
    @XStreamAsAttribute
    private String sys = "";
    @XStreamAsAttribute
    private String icon = "";
    @XStreamAsAttribute
    private String newWindow = "";
    @XStreamAsAttribute
    private String name = "";
    @XStreamAsAttribute
    private String url = "";

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSys() {
        return this.sys;
    }

    public void setSys(String sys) {
        if (sys == null) {
            this.sys = "";
        } else {
            this.sys = sys;
        }

    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNewWindow() {
        return this.newWindow;
    }

    public void setNewWindow(String newWindow) {
        this.newWindow = newWindow;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AppItem() {
    }

    public AppItem(String url, String name, String newWindow, String icon, String sys, String id) {
        if (url == null) {
            url = "";
        }

        if (name == null) {
            name = "";
        }

        if (newWindow == null) {
            newWindow = "";
        }

        if (icon == null) {
            icon = "";
        }

        if (sys == null) {
            sys = "";
        }

        if (id == null) {
            id = "";
        }

        this.url = url;
        this.name = name;
        this.newWindow = newWindow;
        this.icon = icon;
        this.sys = sys;
        this.id = id;
    }

    public String toString() {
        return "AppItem [id=" + this.id + ", sys=" + this.sys + ", icon=" + this.icon + ", newWindow=" + this.newWindow + ", name=" + this.name + ", url=" + this.url + "]";
    }
}
