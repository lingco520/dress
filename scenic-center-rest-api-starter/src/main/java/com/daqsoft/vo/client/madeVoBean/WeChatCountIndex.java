package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @Author: superziy .
 * @Date: Created in 11:45 2017/6/9.
 * @Version: 主页微信访问量
 * @describe:
 */
public class WeChatCountIndex implements Serializable {
    private String todayCount;//今日统计
    private String yesterdayCount;//昨日统计
    private String d_value;//较昨日
    private String source;//来源
    private String type;//箭头类型

    public WeChatCountIndex() {

    }

    public String getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(String todayCount) {
        this.todayCount = todayCount;
    }

    public String getYesterdayCount() {
        return yesterdayCount;
    }

    public void setYesterdayCount(String yesterdayCount) {
        this.yesterdayCount = yesterdayCount;
    }

    public String getD_value() {
        return d_value;
    }

    public void setD_value(String d_value) {
        this.d_value = d_value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
