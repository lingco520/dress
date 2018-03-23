package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:主页人数
 */
public class SumPeopleIndexVo implements java.io.Serializable {
    private String todayPeople;//今日人数
    private String lastYearPeople;//去年今日人数
    private String beforeYearPeople;//前年今日人数
    private String lastYearOnYear;//去年总数同比
    private String beforeYearOnYear;//前年总数同比
    private String lastYearOnYearType;//去年总数同比箭头类型
    private String beforeYearOnYearType;//前年总数同比箭头类型
    private String source;//来源
    private String yesterDayPeople;//昨天的人数


    public String getTodayPeople() {
        return todayPeople;
    }

    public void setTodayPeople(String todayPeople) {
        this.todayPeople = todayPeople;
    }

    public String getLastYearPeople() {
        return lastYearPeople;
    }

    public void setLastYearPeople(String lastYearPeople) {
        this.lastYearPeople = lastYearPeople;
    }

    public String getBeforeYearPeople() {
        return beforeYearPeople;
    }

    public void setBeforeYearPeople(String beforeYearPeople) {
        this.beforeYearPeople = beforeYearPeople;
    }

    public String getLastYearOnYear() {
        return lastYearOnYear;
    }

    public void setLastYearOnYear(String lastYearOnYear) {
        this.lastYearOnYear = lastYearOnYear;
    }

    public String getBeforeYearOnYear() {
        return beforeYearOnYear;
    }

    public void setBeforeYearOnYear(String beforeYearOnYear) {
        this.beforeYearOnYear = beforeYearOnYear;
    }

    public String getLastYearOnYearType() {
        return lastYearOnYearType;
    }

    public void setLastYearOnYearType(String lastYearOnYearType) {
        this.lastYearOnYearType = lastYearOnYearType;
    }

    public String getBeforeYearOnYearType() {
        return beforeYearOnYearType;
    }

    public void setBeforeYearOnYearType(String beforeYearOnYearType) {
        this.beforeYearOnYearType = beforeYearOnYearType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getYesterDayPeople() {
        return yesterDayPeople;
    }

    public void setYesterDayPeople(String yesterDayPeople) {
        this.yesterDayPeople = yesterDayPeople;
    }
}
