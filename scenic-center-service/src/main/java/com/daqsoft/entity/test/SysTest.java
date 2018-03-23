package com.daqsoft.entity.test;

import java.util.Date;

/**
 * 公共示例
 */
public class SysTest {
    /**
     * id
     */
    private Long id;

    /**
     * 测试名称
     */
    private String name;

    /**
     * 单图
     */
    private String img;

    /**
     * 图片高度
     */
    private String imgHeight;

    /**
     * 图片宽度
     */
    private String imgWidth;

    /**
     * 多图
     */
    private String images;

    /**
     * 景区编码
     */
    private String vcode;

    /**
     * 数据状态
     */
    private Integer status;

    /**
     * 音频名称
     */
    private String audioName;

    /**
     * 音频路径
     */
    private String audioPath;

    /**
     * 音频大小
     */
    private String audioSize;

    /**
     * 音频时长
     */
    private String audioTime;

    /**
     * 视频路径
     */
    private String videoPath;

    /**
     * 视频大小
     */
    private String videoSize;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 视频时长
     */
    private String videoTime;

    /**
     * 地区region
     */
    private String region;

    /**
     * 时间
     */
    private Date updateTime;

    /**
     * 详情
     */
    private String content;

    /**
     * 简介
     */
    private String introduction;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(String imgHeight) {
        this.imgHeight = imgHeight == null ? null : imgHeight.trim();
    }

    public String getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(String imgWidth) {
        this.imgWidth = imgWidth == null ? null : imgWidth.trim();
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
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

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName == null ? null : audioName.trim();
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath == null ? null : audioPath.trim();
    }

    public String getAudioSize() {
        return audioSize;
    }

    public void setAudioSize(String audioSize) {
        this.audioSize = audioSize == null ? null : audioSize.trim();
    }

    public String getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(String audioTime) {
        this.audioTime = audioTime == null ? null : audioTime.trim();
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath == null ? null : videoPath.trim();
    }

    public String getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize == null ? null : videoSize.trim();
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime == null ? null : videoTime.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
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
        this.content = content;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}