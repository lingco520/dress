package com.daqsoft.vo.client.madeVoBean;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-22 17:41
 * @Version:
 * @Describe: 今日票务预测数据总数 vo
 */
public class TicketForecastIndexOnAndOffVO {
    private String onLine;//线上数据
    private String offLine;//线下数据
    private String count;//总量
    private String time;//时间

    public String getOnLine() {
        return onLine;
    }

    public void setOnLine(String onLine) {
        this.onLine = onLine;
    }

    public String getOffLine() {
        return offLine;
    }

    public void setOffLine(String offLine) {
        this.offLine = offLine;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
