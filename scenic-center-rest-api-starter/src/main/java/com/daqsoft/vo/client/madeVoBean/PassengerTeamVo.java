package com.daqsoft.vo.client.madeVoBean;

/**
 * @description 景区团队运行监测统计
 * @author tanggm
 * @version
 * @date 2017-06-20 20:43
 */
public class PassengerTeamVo {
    // 名称
    private String name;
    // 今日数量
    private String todayNum;
    // 昨日数量
    private String yesterdayNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTodayNum() {
        return todayNum;
    }

    public void setTodayNum(String todayNum) {
        this.todayNum = todayNum;
    }

    public String getYesterdayNum() {
        return yesterdayNum;
    }

    public void setYesterdayNum(String yesterdayNum) {
        this.yesterdayNum = yesterdayNum;
    }
}
