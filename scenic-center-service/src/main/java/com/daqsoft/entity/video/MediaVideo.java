package com.daqsoft.entity.video;

/**
 * @Title:视频库实体
 * @Author: superziy .
 * @Date: Created in 14:59 2018/1/18.
 * @Version: 4.0.0
 * @describe: 视频库实体
 * @since:JDK 1.8
 */
public class MediaVideo {
    //主键id
    private Long id;
    //标题
    private String title;
    //图片链接
    private String imageUrl;
    //视频链接
    private String videoUrl;
    //视频类型
    private String videoType;
    //景点
    private String scenicId;
    //内容
    private String content;
    //排序
    private Integer sort;
    //语言类型
    private String languageType;
    //状态
    private Integer status;
    //景区标示
    private String vcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType == null ? null : videoType.trim();
    }

    public String getScenicId() {
        return scenicId;
    }

    public void setScenicId(String scenicId) {
        this.scenicId = scenicId;
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

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType == null ? null : languageType.trim();
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
}