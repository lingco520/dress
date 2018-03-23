package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author tanggm
 * @version V3.0.0
 * @date 2017-07-14 17:26.
 * @Description 值班领导信息
 */
public class Sys_Duty_TendVo implements Serializable {
    /**
     * 值班人员名称
     */
    private String NAME;
    private String LANDLINE;
    /**
     * 值班人员手机
     */
    private String PHONE;
    /**
     * 值班时间
     */
    private String dutytime;

    public String getDutytime() {
        return dutytime;
    }

    public void setDutytime(String dutytime) {
        this.dutytime = dutytime;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getLANDLINE() {
        return LANDLINE;
    }

    public void setLANDLINE(String LANDLINE) {
        this.LANDLINE = LANDLINE;
    }
}