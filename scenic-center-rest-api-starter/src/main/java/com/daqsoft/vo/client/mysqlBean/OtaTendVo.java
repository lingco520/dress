package com.daqsoft.vo.client.mysqlBean;

import java.io.Serializable;

/**
 * @author yangmei
 * @version V1.0.0
 * @date 2017-06-06 16:44.
 */
public class OtaTendVo implements Serializable {
    private String ota_time;
    private String ota_num = "0";
    private String ota_totalPrice = "0";

    public OtaTendVo() {
    }

    public OtaTendVo(String ota_time, String ota_num, String ota_totalPrice) {
        this.ota_time = ota_time;
        this.ota_num = ota_num;
        this.ota_totalPrice = ota_totalPrice;
    }

    public String getOta_time() {
        return ota_time;
    }

    public void setOta_time(String ota_time) {
        this.ota_time = ota_time;
    }

    public String getOta_num() {
        return ota_num;
    }

    public void setOta_num(String ota_num) {
        this.ota_num = ota_num;
    }

    public String getOta_totalPrice() {
        return ota_totalPrice;
    }

    public void setOta_totalPrice(String ota_totalPrice) {
        this.ota_totalPrice = ota_totalPrice;
    }
}
