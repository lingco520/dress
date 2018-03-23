package com.daqsoft.vo.client.mysqlBean;

import java.io.Serializable;

/**
 * Created by cl on 16/7/29.  近五年节日人数统计
 */
public class HolidayFiveYearCount implements Serializable {
   private String name;//节日名称
   private String num;//节日人数
   private String time;//节日时间
   public HolidayFiveYearCount(){

   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}