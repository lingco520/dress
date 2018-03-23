package com.daqsoft.service.client.slmhPassengerTicketService;

/**
 * @Title: 赛里木湖OTA票务模拟缓存
 * @Author: lyl
 * @Date: 2017/10/24 10:25
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
public interface SLMHPassengerTicketService {


    /**
     * 赛里木湖OTA票务模拟缓存
     *
     */
    void getPassengerTicketOta();

    /**
     * 存储监控
     *
     */
    void getMonitor(String vcode);


}
