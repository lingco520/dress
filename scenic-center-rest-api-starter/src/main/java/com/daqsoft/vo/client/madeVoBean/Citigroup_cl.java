package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/1 0001. 客流分析团队与散客
 */
public class Citigroup_cl implements Serializable {
    private Long ts_num;
    private String ts_type;
    private long money;


    public Long getTs_num() {
        return ts_num;
    }

    public void setTs_num(Long ts_num) {
        this.ts_num = ts_num;
    }

    public String getTs_type() {
        return ts_type;
    }

    public void setTs_type(String ts_type) {
        this.ts_type = ts_type;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
