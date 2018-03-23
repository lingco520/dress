package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * Created by lianch on 2016/7/27 0027.
 */
public class RealPeopleOld implements Serializable {
    private String id;
    private String vcode;
    private String timesheet;
    private String quantity;
    private String maxquantity;
    public RealPeopleOld() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(String timesheet) {
        this.timesheet = timesheet;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMaxquantity() {
        return maxquantity;
    }

    public void setMaxquantity(String maxquantity) {
        this.maxquantity = maxquantity;
    }
}
