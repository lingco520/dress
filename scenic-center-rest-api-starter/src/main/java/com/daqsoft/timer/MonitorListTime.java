package com.daqsoft.timer;

import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.commonService.CommonService;
import com.daqsoft.service.client.slmhPassengerTicketService.SLMHPassengerTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;


/**
 * @Title: 监控缓存存储
 * @Author: lyl
 * @Date: 2018/01/23 19:37
 * @Description: TODO
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
//@Component
public class MonitorListTime {

    @Autowired
    private SLMHPassengerTicketService slmhPassengerTicketService;
    @Autowired
    private CommonService commonService;//公共方法service
    //耗时
    private long timeCount=0;

    /**
     * 赛里木湖OTA票务模拟缓存
     * 每天下午10点半点走
     *
     */
    @SysLog
    @Async
//    @Scheduled(initialDelay = 20000L , fixedRate = 3600000L)
    @Scheduled(cron="0 30 22 * * ?")
    public void getMonitor() throws Exception{
        List<Map<String, Object>> listVocde = commonService.getPassengerVcode();
        if (listVocde.size() > 0) {
            for (int i = 0; i < listVocde.size(); i++) {
                String vcode = listVocde.get(i).get("vcode") + "";
                slmhPassengerTicketService.getMonitor(vcode);
            }
        }
    }

    /**
     * 赛里木湖OTA票务模拟缓存
     * 每天下午10点半点走
     *
     */
    @SysLog
    @Async
//    @Scheduled(initialDelay = 20000L , fixedRate = 3600000L)
//    @Scheduled(cron="0 30 22 * * ?")
    public void test() throws Exception{
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

        String dbURL = "jdbc:sqlserver://112.74.128.109:1433;DatabaseName=gserver_synth";

        String userName = "sa";

        String userPwd = "DAQsoft2017";

        Connection dbConn = null;

        try

        {

            Class.forName(driverName);

            System.out.println("加载驱动成功！!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);

            System.out.println("连接数据库成功");

        }

        catch (Exception e)

        {

            e.printStackTrace();

            System.out.print("连接失败");

        }

    }



}