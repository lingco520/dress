package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-07 15:48.
 */
public class JqticketXxSeal implements Serializable {
    private  long counts;
    private  long moneys;
    public JqticketXxSeal(){}

    public long getCount() {
        return counts;
    }

    public void setCount(long counts) {
        this.counts = counts;
    }

    public long getMoneys() {
        return moneys;
    }

    public void setMoneys(long moneys) {
        this.moneys = moneys;
    }
}
