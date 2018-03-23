/**
 * @Copyright: <a htef="http://www.daqsoft.com">成都中科大旗软件有限公司Copyright © 2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.timer;

import com.daqsoft.config.SysLog;
import com.daqsoft.redis.ExecutorServiceUtils;
import com.daqsoft.service.client.commonService.CommonService;
import com.daqsoft.service.client.wifiAnalysisService.WifiAnalysisService;
import com.daqsoft.utils.client.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
public class WifiTimer {

    @Autowired
    private WifiAnalysisService wifiAnalysisService;
    @Autowired
    private CommonService commonService;
    /**
     * wifi 天 月 季度 年 数据redis推送
     */
    @SysLog
    @Async
    @Scheduled(initialDelay = 480000L , fixedRate = 300000L)
    public void getWifiByDay() throws Exception{
        List<Map<String, String>> vcodeList = commonService.getWifiVcode();
        //使用多线程去拉取每天的数据.
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
        if(vcodeList.size() > 0){
            for(Map map : vcodeList){
                if(es.isShutdown()){
                    es = ExecutorServiceUtils.getFixedThreadPool();
                }
                futureList.add(es.submit(new WifiTimer.WifiTask(map)));
            }
        }
        es.shutdown();
    }
    /**
     * 调用接口
     *
     * @param entry
     * @return
     */
    public Long runTask(Map<String, String> entry) {
        Date s = new Date();
        String vcode = entry.get("vcode");
        // 计算天的数据
        String startTime = DateUtil.getDateBefore();
        String endTime  = DateUtil.getCurDateStr();
        wifiAnalysisService.getWifiByDay(vcode,startTime,endTime);
        // 计算月的数据
        // 传入时间的年份
        String year = DateUtil.getCurYearStr();
        try {
            // 当前年份
            String nowyear = DateUtil.getCurYearStr();
            String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
            if(Integer.valueOf(year).compareTo(Integer.valueOf(nowyear)) >= 0){
                String nowmonth = DateUtil.getCurMonthNumStr();
                for(String month : months){
                    if(Integer.valueOf(month).compareTo(Integer.valueOf(nowmonth)) <= 0){
                        getWifiByMonth(year,month,vcode);
                    }
                }
            }else{
                for(String month : months){
                    getWifiByMonth(year,month,vcode);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // 计算季度的数据
        try {
            getWifiByQuarter(year,vcode);
        } catch (Exception e){
            e.printStackTrace();
        }
        // 计算年的数据
        try {
            getWifiByYear(year,vcode);
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
    public void getWifiByMonth(String year, String month, String vcode) throws Exception{
        String startTime = year+"-"+month+"-01";
        int days = DateUtil.getDaysByYearMonth(Integer.valueOf(year), Integer.valueOf(month));
        String endTime = year+"-"+month+"-"+days;
        wifiAnalysisService.getWifiByMonth(vcode, year, month,startTime, endTime);
    }
    /**
     * 计算季度的数据
     * @throws Exception
     */
    public void getWifiByQuarter(String year, String vcode) throws Exception{
        wifiAnalysisService.getWifiByQuarter(vcode,year);
    }
    /**
     * 计算年的数据
     * @throws Exception
     */
    public void getWifiByYear(String year, String vcode) throws Exception{
        wifiAnalysisService.getWifiByYear(vcode,year);
    }
    /**
     * 多线程处理
     */
    private class WifiTask implements Callable<String> {

        private Map<String, String> entry;

        public WifiTask() {
        }

        public WifiTask(Map<String, String> entry) {
            this.entry = entry;
        }

        public Map<String, String> getEntry() {
            return entry;
        }

        public void setEntry(Map<String, String> entry) {
            this.entry = entry;
        }

        @Override
        public String call() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("线程:%s 正在处理[wifi]数据 vcode:%s", Thread.currentThread().getName(), entry.get("vcode")));
            return runTask(entry).toString();//返回耗时数
        }
    }
}
