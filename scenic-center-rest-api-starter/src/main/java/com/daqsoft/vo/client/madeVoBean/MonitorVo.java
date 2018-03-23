package com.daqsoft.vo.client.madeVoBean;

/**
 * 印象云台实时监控Vo
 * Created by lyl on 2017/4/18 0018.
 */
public class MonitorVo implements java.io.Serializable{
    private int id;
    private String name;//名称
    private String image;//图片链接
    private String url;//播放链接
    private int status;//状态

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
