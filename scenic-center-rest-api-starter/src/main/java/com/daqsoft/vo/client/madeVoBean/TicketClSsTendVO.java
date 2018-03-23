package com.daqsoft.vo.client.madeVoBean;

import java.io.Serializable;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-06 15:31.
 */
public class TicketClSsTendVO implements Serializable {
    //时间
    private String ticket_time;
    //实时张数
    private String ticket_num;
    //实时累积张数
    private String ticket_nums;


    public String getTicket_time() {
        return ticket_time;
    }

    public void setTicket_time(String ticket_time) {
        this.ticket_time = ticket_time;
    }

    /**
     * 实时张数
     */
    public String getTicket_num() {
        return ticket_num;
    }

    public void setTicket_num(String ticket_num) {
        this.ticket_num = ticket_num;
    }

    /**
     * 实时累积张数
     */
    public String getTicket_nums() {
        return ticket_nums;
    }

    public void setTicket_nums(String ticket_nums) {
        this.ticket_nums = ticket_nums;
    }
}
