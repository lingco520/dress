package com.daqsoft.vo.client.madeVoBean;
/**
 * @Title: 投诉建议Vo
 * @Author: lyl
 * @Date: 2017/09/30 11:50
 * @Description: TODO 
 * @Comment： 
 * @see 
 * @Version:  
 * @since JDK 1.8
 * @Warning:
 */
public class ComplantsVo {

    private String name;
    private String num;
    private String dateTime;

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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}