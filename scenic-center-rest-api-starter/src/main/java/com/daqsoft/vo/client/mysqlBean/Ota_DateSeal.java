package com.daqsoft.vo.client.mysqlBean;

import java.io.Serializable;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-07 11:14.
 */
public class Ota_DateSeal implements Serializable {
    private  long count;
    private  String name;
    public Ota_DateSeal(){}

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
