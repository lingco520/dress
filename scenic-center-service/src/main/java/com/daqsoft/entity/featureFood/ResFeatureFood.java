package com.daqsoft.entity.featureFood;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 特色美食
 */
public class ResFeatureFood implements Serializable{
    /**
     *数据ID
     */
    private Long id;
    /**
     *美食图片
     */
    private String image;
    /**
     *美食名称
     */
    private String name;
    /**
     *数据状态 1 正常，0 禁用， -1 删除
     */
    private Integer status;
    /**
     *景区编码
     */
    private String vcode;
    /**
     *来源
     */
    private String source;
    /**
     *作者
     */
    private String author;
    /**
     *浏览次数
     */
    private Integer scans;
    /**
     *美食价格
     */
    private BigDecimal checkPrice;
    /**
     *发布时间
     */
    private Date updateTime;
    /**
     *排序
     */
    private Integer sort;
    /**
     *语言版本：中文 zh，英文 en，韩语 ko，日语 ja
     */
    private String languageType;
    /**
     *美食内容
     */
    private String content;
    /**
     *摘要
     */
    private String introduction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
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

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
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

    public Integer getScans() {
        return scans;
    }

    public void setScans(Integer scans) {
        this.scans = scans;
    }

    public BigDecimal getCheckPrice() {
        return checkPrice;
    }

    public void setCheckPrice(BigDecimal checkPrice) {
        this.checkPrice = checkPrice;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}