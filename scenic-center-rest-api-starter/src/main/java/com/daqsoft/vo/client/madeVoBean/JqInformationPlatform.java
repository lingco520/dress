package com.daqsoft.vo.client.madeVoBean;

public class JqInformationPlatform {
    // id
    private Integer id;
    // 平台名称
    private String name;
    // 数据状态 1 正常 -99禁用
    private Integer status;
    // 景区编码
    private String vcode;
    // 状态 1 启用 0 禁用
    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}