package com.daqsoft.entity.dutypersonnel;

import java.util.Date;

public class SysDutypersonnel {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 职位类型type对应前端图标
     */
    private String dutytype;

    /**
     *  添加时间
     */
    private Date addtime;

    /**
     * 职位
     */
    private String duty;

    /**
     *  联系电话
     */
    private String landline;

    /**
     * 姓名
     */
    private String name;

    /**
     * 
     */
    private String office;

    /**
     * 电话
     */
    private String phone;

    /**
     * 是否领导
     */
    private Long slevel;

    /**
     * 状态
     */
    private Long status;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 部门
     */
    private Long departmentId;

    /**
     * 景区编码
     */
    private String vcode;

    /**
     * 
     */
    private Date locationTime;

    /**
     * 
     */
    private String lon;

    /**
     * 
     */
    private String lat;

    /**
     * 
     */
    private String sex;

    /**
     * 
     */
    private String age;

    /**
     * 
     */
    private String gid;

    /**
     * 
     */
    private String birthday;

    /**
     * 关联sys_manager_l 外键
     */
    private String manager;

    /**
     * 动态验证码
     */
    private String security;

    /**
     * 登录状态
     */
    private String loginStatus;

    /**
     * 终端账号
     */
    private String usernum;

    /**
     * 终端密码
     */
    private String pwd;

    /**
     * 新的景区资源编码，同步统一应急子系统
     */
    private String resourcecode;

    /**
     * 常用联系人标识，1：是，0：不是
     */
    private String common;

    /**
     * 是否离职 0未离职，1已离职
     */
    private Integer isdeparture;

    /**
     * 离职时间
     */
    private Date departuredate;

    /**
     * 学历
     */
    private String edu;

    /**
     * 入职时间
     */
    private Date entry;

    /**
     * 工龄
     */
    private String workingtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDutytype() {
        return dutytype;
    }

    public void setDutytype(String dutytype) {
        this.dutytype = dutytype == null ? null : dutytype.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline == null ? null : landline.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office == null ? null : office.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Long getSlevel() {
        return slevel;
    }

    public void setSlevel(Long slevel) {
        this.slevel = slevel;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    public Date getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(Date locationTime) {
        this.locationTime = locationTime;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon == null ? null : lon.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid == null ? null : gid.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security == null ? null : security.trim();
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus == null ? null : loginStatus.trim();
    }

    public String getUsernum() {
        return usernum;
    }

    public void setUsernum(String usernum) {
        this.usernum = usernum == null ? null : usernum.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getResourcecode() {
        return resourcecode;
    }

    public void setResourcecode(String resourcecode) {
        this.resourcecode = resourcecode == null ? null : resourcecode.trim();
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common == null ? null : common.trim();
    }

    public Integer getIsdeparture() {
        return isdeparture;
    }

    public void setIsdeparture(Integer isdeparture) {
        this.isdeparture = isdeparture;
    }

    public Date getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(Date departuredate) {
        this.departuredate = departuredate;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu == null ? null : edu.trim();
    }

    public Date getEntry() {
        return entry;
    }

    public void setEntry(Date entry) {
        this.entry = entry;
    }

    public String getWorkingtime() {
        return workingtime;
    }

    public void setWorkingtime(String workingtime) {
        this.workingtime = workingtime == null ? null : workingtime.trim();
    }
}