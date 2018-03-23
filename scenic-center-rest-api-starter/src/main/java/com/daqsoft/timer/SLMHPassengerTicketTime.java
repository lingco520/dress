package com.daqsoft.timer;

import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.slmhPassengerTicketService.SLMHPassengerTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @Title: 赛里木湖OTA票务模拟缓存
 * @Author: lyl
 * @Date: 2017/10/23 19:37
 * @Description: TODO
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
//@Component
public class SLMHPassengerTicketTime {

    @Autowired
    private SLMHPassengerTicketService slmhPassengerTicketService;
    //耗时
    private long timeCount=0;

    /**
     * 赛里木湖OTA票务模拟缓存
     * 每天下午6点走
     *
     */
    @SysLog
    @Async
//    @Scheduled(initialDelay = 20000L , fixedRate = 3600000L)
    @Scheduled(cron="0 30 0 * * ?")
    public void YCHBHscenic() throws Exception{

        slmhPassengerTicketService.getPassengerTicketOta();
    }





}