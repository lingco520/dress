package com.daqsoft.entity.guide;

import java.io.Serializable;
import java.util.Date;

/**
 * 导游实体
 */
public class ResGuide implements Serializable{
    /**
     *记录ID
     */
    private Long id;
    /**
     *导游名称
     */
    private String name;
    /**
     *导游照片
     */
    private String image;
    /**
     *等级
     */
    private String guideLevel;
    /**
     *导游编号
     */
    private String identification;
    /**
     *所属公司
     */
    private String company;
    /**
     *会的语言
     */
    private String language;
    /**
     *状态
     */
    private Integer status;
    /**
     *景区编码
     */
    private String vcode;
    /**
     *更新时间
     */
    private Date updateTime;
    /**
     *导游性别
     */
    private String gender;
    /**
     *地区
     */
    private String region;
    /**
     *身份证号
     */
    private String idcard;
    /**
     *出生日期
     */
    private Date birthday;
    /**
     *学历
     */
    private String education;
    /**
     *民族
     */
    private String nationality;
    /**
     *导游介绍
     */
    private String content;
    /**
     *排序
     */
    private Integer sort;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getGuideLevel() {
        return guideLevel;
    }

    public void setGuideLevel(String guideLevel) {
        this.guideLevel = guideLevel == null ? null : guideLevel.trim();
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification == null ? null : identification.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}