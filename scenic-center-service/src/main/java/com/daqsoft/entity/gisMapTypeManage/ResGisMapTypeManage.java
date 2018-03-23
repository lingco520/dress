package com.daqsoft.entity.gisMapTypeManage;

import java.util.Date;

/**
 * @Title: ResGisMapTypeManage
 * @Author: superziy .
 * @Date: Created in 14:24 2018/2/6.
 * @Version: 4.0.0
 * @describe: gis地图类型
 * @since:JDK 1.8
 */
public class ResGisMapTypeManage {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 地图类型名称
     */
    private String name;
    /**
     * 地图展示类型名称
     */
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}