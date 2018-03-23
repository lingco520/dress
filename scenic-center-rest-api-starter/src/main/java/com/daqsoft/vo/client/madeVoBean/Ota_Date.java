package com.daqsoft.vo.client.madeVoBean;

import java.util.Date;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-07 15:29.
 */
public class Ota_Date {
    private  Long id;
    private  int count;
    private  String name;
    private Date time;
    private  String vcode;
    private  Long TOTALPRICE;
    private  Long PRICE;
    public Ota_Date(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public Long getTOTALPRICE() {
        return TOTALPRICE;
    }

    public void setTOTALPRICE(Long TOTALPRICE) {
        this.TOTALPRICE = TOTALPRICE;
    }

    public Long getPRICE() {
        return PRICE;
    }

    public void setPRICE(Long PRICE) {
        this.PRICE = PRICE;
    }
}
