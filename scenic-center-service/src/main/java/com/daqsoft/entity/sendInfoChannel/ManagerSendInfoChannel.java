package com.daqsoft.entity.sendInfoChannel;

import java.util.Date;

/**
 *  信息发布渠道配置
 */
public class ManagerSendInfoChannel {
    /**
     *  主键id
     */
    private Long id;
    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 渠道名称
     */
    private String ditchName;

    /**
     * 信息类型
     */
    private String informationType;

    /**
     * 资讯类型
     */
    private String type;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 是否开启：1 启用，0 禁用
     */
    private Integer status;

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

    public String getDitchName() {
        return ditchName;
    }

    public void setDitchName(String ditchName) {
        this.ditchName = ditchName == null ? null : ditchName.trim();
    }

    public String getInformationType() {
        return informationType;
    }

    public void setInformationType(String informationType) {
        this.informationType = informationType == null ? null : informationType.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
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

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
}