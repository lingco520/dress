package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-07 16:03.
 */
public class JqTicket implements Serializable {
    private  long id;
    private  double money;
    private  String  type;
    private Date systemtime;
    private  double totalprice;
    private  Long  count;
    private  String vcode;
    private int status;
    public JqTicket(){}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getSystemtime() {
        return systemtime;
    }

    public void setSystemtime(Date systemtime) {
        this.systemtime = systemtime;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

