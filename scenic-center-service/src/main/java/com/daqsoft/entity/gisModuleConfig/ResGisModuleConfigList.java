package com.daqsoft.entity.gisModuleConfig;

import java.util.Date;

/**
 * @Title: ResGisModuleConfig
 * @Author: superziy .
 * @Date: Created in 16:01 2018/2/6.
 * @Version: 4.0.0
 * @describe: gis配置
 * @since:JDK 1.8
 */
public class ResGisModuleConfigList {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 模块名称
     */
    private String name;

    /**
     * 关联模块id
     */
    private String moudleId;

    /**
     * 地图类型：0 gis地图  1 百度地图 2高德地图
     */
    private String mapType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态1 启用 0禁用
     */
    private Integer status;

    /**
     * 景区编码
     */
    private String vcode;

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

    public String getMoudleId() {
        return moudleId;
    }

    public void setMoudleId(String moudleId) {
        this.moudleId = moudleId;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}