package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: caol.
 * @Date: Created in 2017/6/9.
 * @Version: V3.0.0.
 * @describe:流量统计
 */
public class TrafficStatisticsVo {
    private String source;//来源
    private String today;//今天
    private String yesterday;//昨天
    private String compare;//比较：1 上升，0 持平，-1 下降

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getYesterday() {
        return yesterday;
    }

    public void setYesterday(String yesterday) {
        this.yesterday = yesterday;
    }

    public String getCompare() {
        return compare;
    }

    public void setCompare(String compare) {

        this.compare = compare;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
