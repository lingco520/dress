package com.daqsoft.entity.sendInfoContent;

import java.util.Date;

public class ManagerSendInfoContent {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 渠道id
     */
    private String ditchName;

    /**
     * 平台id
     */
    private String platformName;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    private String autho;

    /**
     * 来源
     */
    private String source;

    /**
     * 文章标签
     */
    private String label;

    /**
     * 发布时间
     */
    private String publishTime;

    /**
     * 链接
     */
    private String link;

    /**
     * 状态
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

    /**
     * 文章内容
     */
    private String content;

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
        this.ditchName = ditchName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAutho() {
        return autho;
    }

    public void setAutho(String autho) {
        this.autho = autho == null ? null : autho.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}