package com.daqsoft.entity.department;

import java.util.Date;

public class SysDepartment {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门电话
     */
    private String phone;

    /**
     * 办公室
     */
    private String office;

    /**
     * 状态
     */
    private Long status;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 景区编码
     */
    private String vcode;

    /**
     * 是否为巡检部门，且唯一，1：是，0：不是
     */
    private String isinspection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office == null ? null : office.trim();
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    public String getIsinspection() {
        return isinspection;
    }

    public void setIsinspection(String isinspection) {
        this.isinspection = isinspection == null ? null : isinspection.trim();
    }
}