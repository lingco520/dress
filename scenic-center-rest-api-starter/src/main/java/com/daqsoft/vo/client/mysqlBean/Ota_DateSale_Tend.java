package com.daqsoft.vo.client.mysqlBean;

import java.io.Serializable;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-07 15:05.
 */
public class Ota_DateSale_Tend implements Serializable {
    private  int count;
    private  String name;
    private  Long totalprice;
    public Ota_DateSale_Tend(){}


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

    public Long getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Long totalprice) {
        this.totalprice = totalprice;
    }


    public Ota_DateSale_Tend(int count, String name, Long totalprice) {
        this.count = count;
        this.name = name;
        this.totalprice = totalprice;
    }
}