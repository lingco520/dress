package com.daqsoft.vo.client.madeVoBean;

/**
 * @author tanggm
 * @version V3.1
 * @description
 * @date 2017-06-28 20:13
 */
public class InformationVideoVo {
    // id
    private Integer id;
    // 视频名称
    private String title;
    // url地址
    private String url;
    // 视频名称
    private String scenicName;
    // 图片
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
