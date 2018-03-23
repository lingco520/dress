package com.daqsoft.entity.scenicEntity;

import java.io.Serializable;
import java.util.Date;

public class SysUserScenic implements Serializable {
    //id
    private Long id;
    //景区电子邮件
    private String email;
    //账号
    private String account;
    //登陆ip
    private String loginip;
    //登录时间
    private Date logintime;
    //名称
    private String name;
    //密码
    private String password;
    //状态
    private Long status;
    //电话
    private String phone;
    //地区编码
    private Long region;
    //帐号有效期
    private Date validity;
    //创建者id
    private Long creater;
    //景区唯一标示
    private String vcode;
    //用户app角色
    private Long appUserRole;
    //用户app类型(员工，领导，巡检人员等)
    private String appUserType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip == null ? null : loginip.trim();
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public Long getCreater() {
        return creater;
    }

    public void setCreater(Long creater) {
        this.creater = creater;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    public Long getAppUserRole() {
        return appUserRole;
    }

    public void setAppUserRole(Long appUserRole) {
        this.appUserRole = appUserRole;
    }

    public String getAppUserType() {
        return appUserType;
    }

    public void setAppUserType(String appUserType) {
        this.appUserType = appUserType;
    }
}