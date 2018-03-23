package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author chenxia
 * @version V3.0.0
 * @date 2017-06-08 15:05.
 * @Description
 */
public class PopulationSeal implements Serializable {
    private String month;
    private Long number;

    public PopulationSeal() {
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

