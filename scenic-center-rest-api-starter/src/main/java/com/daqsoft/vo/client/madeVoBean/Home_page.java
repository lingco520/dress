package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @Author: caol.
 * @Date: Created in 2017/6/4.
 * @Version: V3.0.0.
 * @describe:
 */
public class Home_page implements Serializable {
    private String name;
    private String images;
    private String url;
    private String class_s;
    private String class_hover;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClass_s() {
        return class_s;
    }

    public void setClass_s(String class_s) {
        this.class_s = class_s;
    }

    public String getClass_hover() {
        return class_hover;
    }

    public void setClass_hover(String class_hover) {
        this.class_hover = class_hover;
    }
}
