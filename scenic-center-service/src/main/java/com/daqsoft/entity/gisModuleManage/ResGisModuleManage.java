package com.daqsoft.entity.gisModuleManage;

import java.util.Date;

/**
 * @Title: gis地图资源模块
 * @Author: superziy .
 * @Date: Created in 14:03 2018/2/5.
 * @Version: 4.0.0
 * @describe: ResGisModuleManage
 * @since:JDK 1.8
 */
public class ResGisModuleManage {
    /**
     * 
     */
    private Long id;

    /**
     * gis模块名称
     */
    private String name;

    /**
     * 对应方法名
     */
    private String code;

    /**
     * 状态
     */
    private Integer status;
    /**
     *  点击类型
     */
    private String clickType;

    /**
     * 图片
     */
    private String image;

    /**
     * 更新时间
     */
    private Date updateTime;

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
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }
}