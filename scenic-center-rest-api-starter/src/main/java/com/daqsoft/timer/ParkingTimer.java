package com.daqsoft.timer;

import com.daqsoft.config.SysLog;
import com.daqsoft.redis.ExecutorServiceUtils;
import com.daqsoft.service.client.commonService.CommonService;
import com.daqsoft.service.client.forecastwarning.ScenicMonitorService;
import com.daqsoft.utils.client.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author tanggm
 * @version V3.0.0
 * @date 2017-08-22
 * 统计停车场
 */
@Component
public class ParkingTimer {
    @Autowired
    private CommonService commonService;
    @Autowired
    private ScenicMonitorService scenicMonitorService;
    /**
     * 更新停车场天的数据，用于跨时间查询
     * 1小时更新一次
     */
    @SysLog
    @Async
    @Scheduled(initialDelay = 11000L , fixedRate = 3600000L)
//    @Scheduled(cron = "0 0 * * * ?")
    public void getParkingByThisHors() throws Exception{
        List<Map<String, Object>> vcodeList = commonService.getParkingVcode();
        //使用多线程去拉取每天的数据.
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
        if(vcodeList.size() > 0){
            for (Map map : vcodeList){
                if(es.isShutdown()){
                    es = ExecutorServiceUtils.getFixedThreadPool();
                }
                futureList.add(es.submit(new ParkingTask(map)));
            }
        }

        es.shutdown();
    }

    /**
     * 计算月的数据
     * @throws Exception
     */
    public void getParkingByMonth(String year, String month, String vcode) throws Exception{
        String startTime = year+"-"+month+"-01";
        int days = DateUtil.getDaysByYearMonth(Integer.valueOf(year), Integer.valueOf(month));
        String endTime = year+"-"+month+"-"+days;
        scenicMonitorService.getParkingByMonth(vcode, year, month,startTime, endTime);
    }


    /**
     * 计算年的数据
     * @throws Exception
     */
    public void getParkingByYear(String year, String vcode) throws Exception{
        scenicMonitorService.getParkingByYear(vcode,year);
    }
    /**
     * 多线程处理
     */
    private class ParkingTask implements Callable<String> {

        private Map<String, String> entry;

        public ParkingTask() {
        }

        public ParkingTask(Map<String, String> entry) {
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
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("线程:%s 正在处理[停车场]数据 vcode:%s", Thread.currentThread().getName(), entry.get("vcode")));
            return runTask(entry).toString();//返回耗时数
        }
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
        String startTime = DateUtil.getDateBefore(6);
        String endTime  = DateUtil.getCurDateStr();
        scenicMonitorService.getParkingByThisHors(vcode,startTime,endTime);

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
                        getParkingByMonth(year,month,vcode);
                    }
                }
            }else{
                for(String month : months){
                    getParkingByMonth(year,month,vcode);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        // 计算年的数据
        try {
            getParkingByYear(year,vcode);
        } catch (Exception e){
            e.printStackTrace();
        }

        // 按车辆类型统计车辆数量的redis
        String startT =DateUtil.getCurDateStr()+" 00:00:00";
        String endT =DateUtil.getCurDateStr()+" 23:59:59";
        scenicMonitorService.getCarNumByType(startT, endT, vcode);

        Date e = new Date();
        return e.getTime() - s.getTime();
    }
}
