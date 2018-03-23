package com.daqsoft.vo.client.madeVoBean;

/**
 * @description 景区人数统计
 * @author tanggm
 * @version
 * @date 2017-06-20 15:43
 */
public class PassengerVo {
    // 景区名称
    private String name;
    // 最大承载量
    private String maxCount;
    // 今日累计人数
    private String todayNum;
    // 昨日累计人数
    private String yesterdayNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
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
