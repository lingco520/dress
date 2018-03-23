package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-06 16:44.
 */
public class TicketClTendVo implements Serializable {
    private String ticket_time = "";
    private String ticket_num = "0";
    private String ticket_totalPrice = "0";

    public String getTicket_totalPrice() {
        return ticket_totalPrice;
    }

    public void setTicket_totalPrice(String ticket_totalPrice) {
        this.ticket_totalPrice = ticket_totalPrice;
    }

    public String getTicket_time() {
        return ticket_time;
    }

    public void setTicket_time(String ticket_time) {
        this.ticket_time = ticket_time;
    }

    public String getTicket_num() {
        return ticket_num;
    }

    public void setTicket_num(String ticket_num) {
        this.ticket_num = ticket_num;
    }

    @Override
    public String toString() {
        return "TicketClTendVo{" +
                "ticket_time='" + ticket_time + '\'' +
                ", ticket_num='" + ticket_num + '\'' +
                ", ticket_totalPrice='" + ticket_totalPrice + '\'' +
                '}';
    }

    public TicketClTendVo(String ticket_time, String ticket_num) {
        this.ticket_time = ticket_time;
        this.ticket_num = ticket_num;
        this.ticket_totalPrice = "0";
    }




    public TicketClTendVo(String ticket_time, String ticket_num, String ticket_totalPrice) {
        this.ticket_time = ticket_time;
        this.ticket_num = ticket_num;
        this.ticket_totalPrice = ticket_totalPrice;
    }

    public TicketClTendVo() {
    }
}
