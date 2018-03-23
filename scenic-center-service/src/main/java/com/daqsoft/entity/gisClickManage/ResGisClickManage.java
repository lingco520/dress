package com.daqsoft.entity.gisClickManage;

import java.util.Date;

/**
 * @Title: ResGisClickManage
 * @Author: superziy .
 * @Date: Created in 10:41 2018/2/6.
 * @Version: 4.0.0
 * @describe: gis地图点击事件类别
 * @since:JDK 1.8
 */
public class ResGisClickManage {
    /**
     * 
     */
    private Long id;

    /**
     * 点击事件名称
     */
    private String name;

    /**
     * 事件编码
     */
    private String code;

    /**
     * 状态
     */
    private Integer status;

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}