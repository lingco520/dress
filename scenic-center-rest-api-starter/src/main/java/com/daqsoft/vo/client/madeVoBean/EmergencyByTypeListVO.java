package com.daqsoft.vo.client.madeVoBean;

/**
 * @author tanggm
 * @version V1.0.0
 * @description
 * @date 2017-06-09 18:46
 */
public class EmergencyByTypeListVO {

    private Integer ID;
    private String HAPPENTIME;
    private String status;
    private String NAME;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getHAPPENTIME() {
        return HAPPENTIME;
    }

    public void setHAPPENTIME(String HAPPENTIME) {
        this.HAPPENTIME = HAPPENTIME;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
