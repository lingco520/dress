package com.daqsoft.entity.travelAgency;

/**
 * @Title: ResTravelAgency
 * @Author: superziy .
 * @Date: Created in 11:18 2018/1/19.
 * @Version: 4.0.0
 * @describe: 旅行社实体类
 * @since:JDK 1.8
 */
public class ResTravelAgency {
    //主键
    private Long id;
    //名称
    private String name;
    //略缩名字
    private String shortName;
    //许可证号
    private String licenseno;
    //电子邮箱
    private String email;
    //qq微博
    private String qqWeibo;
    //新浪微博
    private String sinaWeibo;
    //微信
    private String wechat;
    //地区编码
    private String region;
    //标志
    private String logo;
    //视频
    private String video;
    //地址
    private String address;
    //电话
    private String phone;
    //传真
    private String fax;
    //旅行社类型
    private String travelType;
    //状态
    private Integer status;
    //景区编码
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getLicenseno() {
        return licenseno;
    }

    public void setLicenseno(String licenseno) {
        this.licenseno = licenseno == null ? null : licenseno.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getQqWeibo() {
        return qqWeibo;
    }

    public void setQqWeibo(String qqWeibo) {
        this.qqWeibo = qqWeibo == null ? null : qqWeibo.trim();
    }

    public String getSinaWeibo() {
        return sinaWeibo;
    }

    public void setSinaWeibo(String sinaWeibo) {
        this.sinaWeibo = sinaWeibo == null ? null : sinaWeibo.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video == null ? null : video.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
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

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }
}