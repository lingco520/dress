package com.daqsoft.vo.client.madeVoBean;

public class SendInfoEmergency {

    //实时游客
    private String realpeople;
    //人流拥挤度
    private String visitorscrowd;
    //车流拥挤度
    private String carflowcrowd;
    //其他信息
    private String otherinfo;

    public String getRealpeople() {
        return realpeople;
    }

    public void setRealpeople(String realpeople) {
        this.realpeople = realpeople;
    }

    public String getVisitorscrowd() {
        return visitorscrowd;
    }

    public void setVisitorscrowd(String visitorscrowd) {
        this.visitorscrowd = visitorscrowd;
    }

    public String getCarflowcrowd() {
        return carflowcrowd;
    }

    public void setCarflowcrowd(String carflowcrowd) {
        this.carflowcrowd = carflowcrowd;
    }

    public String getOtherinfo() {
        return otherinfo;
    }

    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }
}