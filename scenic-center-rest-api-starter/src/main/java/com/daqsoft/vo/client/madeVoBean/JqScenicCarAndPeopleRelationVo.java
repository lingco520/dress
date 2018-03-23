package com.daqsoft.vo.client.madeVoBean;


import java.io.Serializable;

/**
 * Created by zjy .
 */
public class JqScenicCarAndPeopleRelationVo implements Serializable {
    private String type;//门票类型
    private String num;//景区团散人数
    private String time;//时间

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
