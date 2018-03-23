package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-07 15:58.
 */
public class JqTicket_Tend implements Serializable {
    private String count;
    private String time;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
