package com.daqsoft.vo.resource;

import java.io.Serializable;
import java.util.Date;

public class GisModule implements Serializable {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 模块名称
     */
    private String name;
    /**
     * 模块编码
     */
    private String code;
    /**
     * 图标
     */
    private String image;
    /**
     * 电机类型
     */
    private String clickType;
    /**
     * 数量
     */
    private Long num;
    /**
     * 景区编码
     */
    private String vcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}