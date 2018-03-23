package com.daqsoft.vo.client.madeVoBean;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 信息渠道实体
 * @author tanggm
 * @date 2017-06-27 14:30
 */
public class JqInformationDitch {
    // id
    private Integer id;
    // 渠道名称
    private String ditchName;
    // 信息类型
    private String informationType;
    // 资讯类型
    private String type;
    // 发布时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    // 数据状态 1 正常 -99禁用
    private Integer status;
    // 状态 1 启用 0 禁用
    private Integer state;
    // 景区编码
    private String vcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDitchName() {
        return ditchName;
    }

    public void setDitchName(String ditchName) {
        this.ditchName = ditchName == null ? null : ditchName.trim();
    }

    public String getInformationType() {
        return informationType;
    }

    public void setInformationType(String informationType) {
        this.informationType = informationType == null ? null : informationType.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}