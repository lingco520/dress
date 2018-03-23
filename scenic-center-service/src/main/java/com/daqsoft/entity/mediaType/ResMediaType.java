package com.daqsoft.entity.mediaType;

/**
 * @Title: 景区多媒体类型实体
 * @Author: superziy .
 * @Date: Created in 17:16 2018/1/17.
 * @Version: 4.0.0
 * @describe: 景区多媒体类型实体
 * @since:JDK 1.8
 */
public class ResMediaType {
    //主键id
    private Long id;
//类型名称
    private String name;
//所属类型，关联字典表的类型 key：mediaType
    private String type;
//是否微官网展示，1展示 0不展示
    private Integer showWeb;
//是否触摸屏展示，1 展示 0 不展示
    private Integer showTouch;
//状态1启用 -1 删除 0 禁用
    private Integer status;
//景区唯一编码
    private String vcode;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getShowWeb() {
        return showWeb;
    }

    public void setShowWeb(Integer showWeb) {
        this.showWeb = showWeb;
    }

    public Integer getShowTouch() {
        return showTouch;
    }

    public void setShowTouch(Integer showTouch) {
        this.showTouch = showTouch;
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