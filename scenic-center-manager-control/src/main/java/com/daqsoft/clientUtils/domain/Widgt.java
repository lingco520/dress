package com.daqsoft.clientUtils.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * @Title: 单模板实体
 * @Author: lyl
 * @Date: 2018-02-06 11:38
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("Widgt")
public class Widgt implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name = "";
    @XStreamAsAttribute
    @XStreamAlias("url")
    private String url = "";
    @XStreamAsAttribute
    @XStreamAlias("screen")
    private String screen = "";
    @XStreamAsAttribute
    @XStreamAlias("id")
    private String id = "";
    @XStreamAsAttribute
    @XStreamAlias("screenMax")
    private String screenMax = "";
    @XStreamAsAttribute
    @XStreamAlias("slide")
    private String slide = "";

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

    public String getScreen() {
        return this.screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScreenMax() {
        return this.screenMax;
    }

    public void setScreenMax(String screenMax) {
        this.screenMax = screenMax;
    }

    public String getSlide() {
        return this.slide;
    }

    public void setSlide(String slide) {
        this.slide = slide;
    }

    public Widgt() {
    }

    public Widgt(String name, String url, String id, String screen, String screenMax) {
        if (name == null) {
            name = "";
        }

        if (url == null) {
            url = "";
        }

        if (id == null) {
            id = "";
        }

        if (screen == null) {
            screen = "";
        }

        if (screenMax == null) {
            screenMax = "";
        }

        this.name = name;
        this.url = url;
        this.id = id;
        this.screen = screen;
        this.screenMax = screenMax;
    }

    public Widgt(String name, String url, String id, String screen, String screenMax, String slide) {
        if (name == null) {
            name = "";
        }

        if (url == null) {
            url = "";
        }

        if (id == null) {
            id = "";
        }

        if (screen == null) {
            screen = "";
        }

        if (screenMax == null) {
            screenMax = "";
        }

        if (slide == null) {
            slide = "";
        }

        this.name = name;
        this.url = url;
        this.id = id;
        this.screen = screen;
        this.screenMax = screenMax;
        this.slide = slide;
    }

    public String toString() {
        return "Widgt [name=" + this.name + ", url=" + this.url + ", screen=" + this.screen + ", id=" + this.id + ", screenMax=" + this.screenMax + ", slide=" + this.slide + "]";
    }
}
