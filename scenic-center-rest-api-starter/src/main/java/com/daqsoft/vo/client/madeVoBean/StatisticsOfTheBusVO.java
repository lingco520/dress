package com.daqsoft.vo.client.madeVoBean;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-27 16:28
 * @Version:
 * @Describe:
 */
public class StatisticsOfTheBusVO {
    //年份
    private String year;
    //数量
    private int num;
    //去年同期
    private double yearOnYear;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getYearOnYear() {
        return yearOnYear;
    }

    public void setYearOnYear(double yearOnYear) {
        this.yearOnYear = yearOnYear;
    }
}
