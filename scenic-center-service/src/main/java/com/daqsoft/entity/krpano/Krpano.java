package com.daqsoft.entity.krpano;

import java.util.Date;

/**
 * 全景实体
 */
public class Krpano {
    /**
     *记录ID
     */
    private Long id;
    /**
     *标题
     */
    private String title;
    /**
     *景区名称
     */
    private String scenicName;
    /**
     *连接
     */
    private String url;
    /**
     *图片
     */
    private String img;
    /**
     *所属终端
     */
    private String terminal;
    /**
     *更新时间
     */
    private Date updateTime;
    /**
     *状态
     */
    private Integer status;
    /**
     *景区唯一标示
     */
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

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName == null ? null : scenicName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal == null ? null : terminal.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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