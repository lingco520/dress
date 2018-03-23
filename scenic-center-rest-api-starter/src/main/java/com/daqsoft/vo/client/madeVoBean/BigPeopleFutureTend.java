package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by cl on 2016/7/30 0030. 客流分析——年月日
 */
public class BigPeopleFutureTend implements Serializable {
    private String historyPeople;//历史人数
    private String futurePeople;//未来人数
    private String time;//时间
    /**
     * 人次
     */
    public BigPeopleFutureTend(){

    }

    public String getHistoryPeople() {
        return historyPeople;
    }

    public void setHistoryPeople(String historyPeople) {
        this.historyPeople = historyPeople;
    }

    public String getFuturePeople() {
        return futurePeople;
    }

    public void setFuturePeople(String futurePeople) {
        this.futurePeople = futurePeople;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
