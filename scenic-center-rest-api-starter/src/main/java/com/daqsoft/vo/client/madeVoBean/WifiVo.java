package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/19.
 * @Version: V3.0.0.
 * @describe:wifivo
 */


public class WifiVo implements Serializable {
    private String time;//时间
    private Integer num;//数量
    private Integer sum;//数量

    public WifiVo() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
