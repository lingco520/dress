package com.daqsoft.vo.client.madeVoBean;

/**
 * Created by superziy on 2017-06-06.
 */
public class RealPeopleDayTrendVo {
    private String resident;//驻留人数
    private String newadd ="0";//新增总游客
    private String  leave = "0";//	离开总游客
    private String day;//	日期
    private String avgtime;//	日期

    public RealPeopleDayTrendVo(){

    }

    public String getResident() {
        return resident;
    }

    public void setResident(String resident) {
        this.resident = resident;
    }

    public String getNewadd() {
        return newadd;
    }

    public void setNewadd(String newadd) {
        this.newadd = newadd;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAvgtime() {
        return avgtime;
    }

    public void setAvgtime(String avgtime) {
        this.avgtime = avgtime;
    }
}
