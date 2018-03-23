package com.daqsoft.vo.client.madeVoBean;

public class JqSendInfoSystem {
    private Long id;
    //所属平台
    private String platform;
    //标题
    private String title;
    //简介
    private String content;
    //天气
    private String weather;
    //温度
    private String temperature;
    //湿度
    private String humidity;
    //天气图片
    private String weathericon;
    //光照
    private String illumination;
    //紫外线
    private String ultraviolet;
    //空气质量
    private String air;
    //水温
    private String watertemperature;
    //水质
    private String waterquality;
    //停车场剩余位置
    private String parkingsurplus;
    //交通是否畅通
    private String trafficconditions;
    //实时游客
    private String realpeople;
    //人流拥挤度
    private String visitorscrowd;
    //车流拥挤度
    private String carflowcrowd;
    //其他信息
    private String otherinfo;
    //诚信信息
    private String integrityinfo;
    //旅游信息
    private String travelinfo;
    //政治信息
    private String politicsinfo;
    //知识类信息
    private String knowledgeinfo;
    //新闻信息
    private String newsinfo;
    //添加时间
    private String date;
    //状态
    private Integer status;

    private String vcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather == null ? null : weather.trim();
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature == null ? null : temperature.trim();
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity == null ? null : humidity.trim();
    }

    public String getIllumination() {
        return illumination;
    }

    public void setIllumination(String illumination) {
        this.illumination = illumination == null ? null : illumination.trim();
    }

    public String getUltraviolet() {
        return ultraviolet;
    }

    public void setUltraviolet(String ultraviolet) {
        this.ultraviolet = ultraviolet == null ? null : ultraviolet.trim();
    }

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air == null ? null : air.trim();
    }

    public String getWatertemperature() {
        return watertemperature;
    }

    public void setWatertemperature(String watertemperature) {
        this.watertemperature = watertemperature == null ? null : watertemperature.trim();
    }

    public String getWaterquality() {
        return waterquality;
    }

    public void setWaterquality(String waterquality) {
        this.waterquality = waterquality == null ? null : waterquality.trim();
    }

    public String getParkingsurplus() {
        return parkingsurplus;
    }

    public void setParkingsurplus(String parkingsurplus) {
        this.parkingsurplus = parkingsurplus == null ? null : parkingsurplus.trim();
    }

    public String getTrafficconditions() {
        return trafficconditions;
    }

    public void setTrafficconditions(String trafficconditions) {
        this.trafficconditions = trafficconditions == null ? null : trafficconditions.trim();
    }

    public String getRealpeople() {
        return realpeople;
    }

    public void setRealpeople(String realpeople) {
        this.realpeople = realpeople == null ? null : realpeople.trim();
    }

    public String getVisitorscrowd() {
        return visitorscrowd;
    }

    public void setVisitorscrowd(String visitorscrowd) {
        this.visitorscrowd = visitorscrowd == null ? null : visitorscrowd.trim();
    }

    public String getCarflowcrowd() {
        return carflowcrowd;
    }

    public void setCarflowcrowd(String carflowcrowd) {
        this.carflowcrowd = carflowcrowd == null ? null : carflowcrowd.trim();
    }

    public String getOtherinfo() {
        return otherinfo;
    }

    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo == null ? null : otherinfo.trim();
    }

    public String getIntegrityinfo() {
        return integrityinfo;
    }

    public void setIntegrityinfo(String integrityinfo) {
        this.integrityinfo = integrityinfo == null ? null : integrityinfo.trim();
    }

    public String getTravelinfo() {
        return travelinfo;
    }

    public void setTravelinfo(String travelinfo) {
        this.travelinfo = travelinfo == null ? null : travelinfo.trim();
    }

    public String getPoliticsinfo() {
        return politicsinfo;
    }

    public void setPoliticsinfo(String politicsinfo) {
        this.politicsinfo = politicsinfo == null ? null : politicsinfo.trim();
    }

    public String getKnowledgeinfo() {
        return knowledgeinfo;
    }

    public void setKnowledgeinfo(String knowledgeinfo) {
        this.knowledgeinfo = knowledgeinfo == null ? null : knowledgeinfo.trim();
    }

    public String getNewsinfo() {
        return newsinfo;
    }

    public void setNewsinfo(String newsinfo) {
        this.newsinfo = newsinfo == null ? null : newsinfo.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getWeathericon() {
        return weathericon;
    }

    public void setWeathericon(String weathericon) {
        this.weathericon = weathericon;
    }
}