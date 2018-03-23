package com.daqsoft.vo.client.madeVoBean;

/**
 * Created by superziy on 2017-06-06.
 */
public class RealPeopleDayVo {
    private String newTotal;//新增总游客
    private String leaveTotal;//	离开总游客
    private String  currentTime;//	时间
    private String currentDate;//	日期

    public RealPeopleDayVo(){

    }

    public String getNewTotal() {
        return newTotal;
    }

    public void setNewTotal(String newTotal) {
        this.newTotal = newTotal;
    }

    public String getLeaveTotal() {
        return leaveTotal;
    }

    public void setLeaveTotal(String leaveTotal) {
        this.leaveTotal = leaveTotal;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
