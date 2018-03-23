/**
 * @Copyright: <a htef="http://www.daqsoft.com">成都中科大旗软件有限公司Copyright © 2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.timer;

import com.daqsoft.config.SysLog;
import com.daqsoft.redis.ExecutorServiceUtils;
import com.daqsoft.service.client.wifiAnalysisService.YCHBHWifiAnalysisService;
import com.daqsoft.utils.client.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * @Title: Wifi定时推送缓存
 * @Author: yangmei
 * @Date: 2017/08/15 10:15
 * @Comment：主要为Wifi相关统计的数据缓存
 * @Version: V3.0.0
 * @since JDK 1.8
 */
//@Component
public class YCHBHWifiTimer {

    @Autowired
    private YCHBHWifiAnalysisService ychbhWifiAnalysisService;
    /**
     * wifi 天 月 季度 年 数据redis推送
     */
    @SysLog
    @Async
    @Scheduled(initialDelay = 300000L , fixedRate = 600000L)
    public void getWifiByDay() throws Exception{
        //Map<String,String> mapVcode=props.getCommonScenic();
        //使用多线程去拉取每天的数据.
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
        /*for (Map.Entry<String,String> entryday: mapVcode.entrySet()) {
            if(es.isShutdown()){
                es = ExecutorServiceUtils.getFixedThreadPool();
            }
            futureList.add(es.submit(new YCHBHWifiTimer.WifiTask(entryday)));
        }*/
        // 这个微件只对银川花博会使用，后期后台配置功能开放了，使用查询的方式
        if(es.isShutdown()){
            es = ExecutorServiceUtils.getFixedThreadPool();
        }
        String vcode = "fcb456dac4f41a08d5d12943ae61665f";
        futureList.add(es.submit(new YCHBHWifiTimer.WifiTask(vcode)));
    }
    /**
     * 调用接口
     *
     * @param vcode
     * @return
     */
    public Long runTask(String vcode) {
        Date s = new Date();
        // 因为这个接口只服务银川花博会，所以这里vcode写死
        // 计算天的数据,补偿3天数据
        String startTime = DateUtil.getDateBefore(2);
        String endTime  = DateUtil.getCurDateStr();
        ychbhWifiAnalysisService.getWifiByDay(vcode,startTime,endTime);
        // 计算月的数据
        // 传入时间的年份
        String year = DateUtil.getCurYearStr();
        try {
            // 当前年份
            String nowyear = DateUtil.getCurYearStr();
            String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
            // 这里需要判断是不是当前年，如果是，则需要对月份做处理，不能统计当前月份之后的数据
            if(Integer.valueOf(year).compareTo(Integer.valueOf(nowyear)) >= 0){
                String nowmonth = DateUtil.getCurMonthNumStr();
                for(String month : months){
                    if(Integer.valueOf(month).compareTo(Integer.valueOf(nowmonth)) <= 0){
                        getWifiByMonth(year,month);
                    }
                }
            }else{
                for(String month : months){
                    getWifiByMonth(year,month);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // 计算季度的数据
        try {
            getWifiByQuarter(year);
        } catch (Exception e){
            e.printStackTrace();
        }
        // 计算年的数据
        try {
            getWifiByYear(year);
        } catch (Exception e){
            e.printStackTrace();
        }
        Date e = new Date();
        return e.getTime() - s.getTime();
    }
    /**
     * 计算月的数据
     * @throws Exception
     */
    public void getWifiByMonth(String year, String month) throws Exception{
        String startTime = year+"-"+month+"-01";
        int days = DateUtil.getDaysByYearMonth(Integer.valueOf(year), Integer.valueOf(month));
        String endTime = year+"-"+month+"-"+days;
        /*Map<String,String> map=props.getCommonScenic();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            String vcode = entry.getValue();
            ychbhWifiAnalysisService.getWifiByMonth(vcode, year, month,startTime, endTime);
        }*/
        String vcode = "fcb456dac4f41a08d5d12943ae61665f";
        ychbhWifiAnalysisService.getWifiByMonth(vcode, year, month,startTime, endTime);
    }
    /**
     * 计算季度的数据
     * @throws Exception
     */
    public void getWifiByQuarter(String year) throws Exception{
        /*Map<String,String> map=props.getCommonScenic();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            String vcode = entry.getValue();
            ychbhWifiAnalysisService.getWifiByQuarter(vcode,year);
        }*/
        String vcode = "fcb456dac4f41a08d5d12943ae61665f";
        ychbhWifiAnalysisService.getWifiByQuarter(vcode,year);
    }
    /**
     * 计算年的数据
     * @throws Exception
     */
    public void getWifiByYear(String year) throws Exception{
        /*Map<String,String> map=props.getCommonScenic();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            String vcode = entry.getValue();
            ychbhWifiAnalysisService.getWifiByYear(vcode,year);
        }*/
        String vcode = "fcb456dac4f41a08d5d12943ae61665f";
        ychbhWifiAnalysisService.getWifiByYear(vcode,year);
    }
    /**
     * 多线程处理
     */
    private class WifiTask implements Callable<String> {

        private String vcode;

        public WifiTask() {
        }

        public WifiTask(String vcode) {
            this.vcode = vcode;
        }

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        @Override
        public String call() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("线程:%s 正在处理[银川花博会wifi]数据 vcode:%s", Thread.currentThread().getName(), vcode));
            return runTask(vcode).toString();//返回耗时数
        }
    }
}
