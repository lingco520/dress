package com.daqsoft.timer;

import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.ychbhPassenger.YCHBHPassengerService;
import com.daqsoft.utils.client.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Title: 银川花博会人流缓存定时推送
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
public class YCHBHPassengerTime {

    @Autowired
    private YCHBHPassengerService ychbhPassengerService;

    /**
     * 实时人数景点缓存
     * 每天走三次
     *
     */
    @SysLog
    @Async
    @Scheduled(initialDelay = 150000L , fixedRate = 3600000L)
    public void YCHBHscenic() throws Exception{
        boolean isTure = DateUtil.getHoursByMinite();
        //早7到晚8执行
        if(isTure==true){
            ychbhPassengerService.getPassengerByScenic();
          }
        }




    /**
     * 启动后20秒执行
     * 1小时执行一次
     * 用于抓当前的实时人数数据,这个是避免缓存丢失情况
     * 并且补偿前6天的数据
     * 1、当前时间段，7天的实时人数获取。补偿6天
     * 2、当前时间段，每小时获取一次实时人数
     * 3、景区景点数据，每小时获取一次
     */
    @SysLog("景区实时人数定时器~~~")
    @Scheduled(initialDelay = 1200000L, fixedRate = 3600000L)
    public void carFlowScheduled() throws Exception {
        boolean isTure = DateUtil.getHoursByMinite();
        //早7到晚8执行
        if(isTure==true){
            String time = DateUtil.getCurDateStr();
            String year = DateUtil.getCurYearStr();
            String month = DateUtil.getCurMonthStr();
            //天景点数据+缓存补偿
            ychbhPassengerService.getPassengerFlowScenicByDay(time,year,month);//TODO
            //增量月缓存
            getPassengerFlowScenicByMonth();
            //小时景点数据
            ychbhPassengerService.getPassengerFlowScenicByHour(time);
        }

    }



    ////////////////////////////以下方法做缓存增量，不走DB////////////////////////////////////////


    /**
     * 景区大数据-按月实时游客数据(所有景区)
     * 获取当月景区的每天的缓存进行累加
     */
    public void getPassengerFlowScenicByMonth() throws Exception{
        String month = DateUtil.getCurMonthStr();
        String startTime = month+"-01";
        String endTime = DateUtil.getCurDateStr();
            System.out.print(String.format("景区当“月”实时游客数据------银川花博会" + "------开始"));
            ychbhPassengerService.getPassengerFlowScenicByMonth(month, startTime, endTime);
        //月抓完，抓季度
        getPassengerFlowScenicByQuarter();
    }


    /**
     * 景区大数据-按季度实时游客数据(所有景区)
     * 这个方法不走DB，直接拉取月份缓存进行累加，然后存入季度缓存
     */
    public void getPassengerFlowScenicByQuarter() throws Exception{
        String year = DateUtil.getCurYearStr();
            System.out.print(String.format("景区当“季度”实时游客数据------银川花博会" + "------开始"));
            ychbhPassengerService.getPassengerFlowScenicByQuarter(year);
        //季度抓完，抓年份
        getPassengerFlowScenicByYear();
    }

    /**
     * 景区大数据-按年实时游客数据(所有景区)
     * 这个方法不走DB，直接拉取月份缓存进行累加，然后存入年份缓存
     */
    public void getPassengerFlowScenicByYear() throws Exception{
        String year = DateUtil.getCurYearStr();
            System.out.print(String.format("景区当“年”实时游客数据------银川花博会" + "------开始"));
            ychbhPassengerService.getPassengerFlowScenicByYear(year);
    }


    ////////////////////////////以下方法做缓存增量，不走DB////////////////////////////////////////

}