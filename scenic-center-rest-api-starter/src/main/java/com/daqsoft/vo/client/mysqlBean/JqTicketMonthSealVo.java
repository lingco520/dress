package com.daqsoft.vo.client.mysqlBean;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:车辆类型
 */


public class JqTicketMonthSealVo implements java.io.Serializable {
    private  String month;
    private  Long number;
    public JqTicketMonthSealVo(){

    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
