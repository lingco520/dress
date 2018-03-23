package com.daqsoft.vo.client.madeVoBean;

/**
 * Created by ziy
 * 值班人员信息
 */
public class DutyPersonEmerVo {
    private String dutyleaderNames;
    private String dutyPhone;
    private String dutyuserNames;
    private String emerPhone;
    private String reportPhone;

    public DutyPersonEmerVo(){

    }

    public DutyPersonEmerVo(String dutyleaderNames, String dutyPhone, String dutyuserNames, String emerPhone, String reportPhone) {
        this.dutyleaderNames = dutyleaderNames;
        this.dutyPhone = dutyPhone;
        this.dutyuserNames = dutyuserNames;
        this.emerPhone = emerPhone;
        this.reportPhone = reportPhone;
    }

    public String getDutyleaderNames() {
        return dutyleaderNames;
    }

    public void setDutyleaderNames(String dutyleaderNames) {
        this.dutyleaderNames = dutyleaderNames;
    }

    public String getDutyPhone() {
        return dutyPhone;
    }

    public void setDutyPhone(String dutyPhone) {
        this.dutyPhone = dutyPhone;
    }

    public String getDutyuserNames() {
        return dutyuserNames;
    }

    public void setDutyuserNames(String dutyuserNames) {
        this.dutyuserNames = dutyuserNames;
    }

    public String getEmerPhone() {
        return emerPhone;
    }

    public void setEmerPhone(String emerPhone) {
        this.emerPhone = emerPhone;
    }

    public String getReportPhone() {
        return reportPhone;
    }

    public void setReportPhone(String reportPhone) {
        this.reportPhone = reportPhone;
    }
}
