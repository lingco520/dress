package com.daqsoft.entity.touristCommodity;

import java.io.Serializable;
import java.util.Date;

/**
 * 旅游商品
 */
public class ResTouristCommodity implements Serializable {
    /**
     *记录ID
     */
    private Long id;
    /**
     *名称
     */
    private String name;
    /**
     *图片
     */
    private String image;
    /**
     *简介
     */
    private String introduction;
    /**
     *来源
     */
    private String source;
    /**
     *发布者
     */
    private String author;
    /**
     *发布时间
     */
    private Date updateTime;
    /**
     *景区编码
     */
    private String vcode;
    /**
     *状态
     */
    private Integer status;
    /**
     *排序
     */
    private Integer sort;
    /**
     *语言版本：中文 zh，英文 en，韩语 ko，日语 ja
     */
    private String languageType;
    /**
     *内容
     */
    private String content;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}