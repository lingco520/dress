package com.daqsoft.timer;

import com.daqsoft.config.SysLog;
import com.daqsoft.redis.ExecutorServiceUtils;
import com.daqsoft.service.client.commonService.CommonService;
import com.daqsoft.service.client.passengerFlowService.PassengerService;
import com.daqsoft.utils.client.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author lrd  lyl
 * @version V3.0.0
 * @date 2017-08-11
 * 实时人数获取类。
 * 获取小时、天
 * 增量出月，季度，年
 *
 */
//@Component
public class PassengerTimer {
    @Autowired
    private PassengerService passengerService;
    //耗时
    private long timeCount=0;
    @Autowired
    private CommonService commonService;//公共方法service

    /**
     * 1.景区大数据-通过运营商获取的数据
     * 2.景区大数据-省内游客TOP10
     * 初始化加载一次当前的年份时间点的数据
     */
    @SysLog
    @Async
//    @Scheduled(initialDelay = 60000L , fixedRate = 3650000L)
    @Scheduled(initialDelay = 120000L , fixedRate = 1200000L)
    public  void bigCarFlowNumbers() throws Exception{
        List<Map<String,Object>> listVcode = commonService.getPassengerVcode();
        //使用多线程去拉取每天的数据.
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
        for (Map<String, Object> entry : listVcode) {
            if(es.isShutdown()){
                es = ExecutorServiceUtils.getFixedThreadPool();
            }
            futureList.add(es.submit(new bigCarFlowTask(entry)));
        }
        es.shutdown();
    }
    /**
     * 当天实时数据获取-用于更新天、月、年
     * 5分钟读一次
     * 这个是最实时的数据
     */
    @SysLog
    @Async
    @Scheduled(initialDelay = 30000L , fixedRate = 300000L)
    public void getPassengerFlowByThisHors() throws Exception{
        List<Map<String,Object>> listVcode = commonService.getPassengerVcode();
        //使用多线程去拉取每天的数据.
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
        for (Map<String, Object> entry : listVcode) {
//            Map<String, Object> entry = new HashMap<>();
//            entry.put("vcode","a02dbca3d06409ba7fbf444e0565242d");
            if(es.isShutdown()){
                es = ExecutorServiceUtils.getFixedThreadPool();
            }
            futureList.add(es.submit(new passengerFlowHorsTask(entry)));
        }
        es.shutdown();
    }

    /**
     * 节假日数据的获取。
     * 每24小时走一次。只走当前日期之前的节假日日期
     * 在另外一个接口来判断当前日期是否为节假日，如果是。或取当天缓存累加节假日缓存返回。如果不是。直接读这个缓存。
     * @throws Exception
     */
    @SysLog
    @Async
    @Scheduled(initialDelay = 800000L , fixedRate = 85000000L)
    public void getPassengerFlowHoliday() throws Exception {
        String time = DateUtil.getCurDateStr();
        String year = DateUtil.getCurYearStr();
        List<Map<String, Object>> listVocde = commonService.getPassengerVcode();
        if (listVocde.size() > 0) {
            for (int i = 0; i < listVocde.size(); i++) {
            String vcode = listVocde.get(i).get("vcode") + "";
            String name = listVocde.get(i).get("name") + "";
            System.out.println(String.format("景区节假日游客量获取--------24小时" + "---" + name + year + "年"));
            passengerService.getPassengerFlowByHoliay(vcode, time, year);
        }
      }
    }

    /**
     * 存储本年最大峰值即日期。
     * 每填23点50执行
     * @throws Exception
     */
    @SysLog
    @Async
    @Scheduled(cron = "0 50 23 * * ?")
//    @Scheduled(initialDelay = 1000L , fixedRate = 300000L)
    public void getMaxPassengerFlow() throws Exception {
        String time = DateUtil.getCurDateStr();
        String year = DateUtil.getCurYearStr();
        List<Map<String, Object>> listVocde = commonService.getPassengerVcode();
        if (listVocde.size() > 0) {
            for (int i = 0; i < listVocde.size(); i++) {
                String vcode = listVocde.get(i).get("vcode") + "";
                String name = listVocde.get(i).get("name") + "";
                System.out.println(String.format("存储本年最大峰值即日期--------24小时" + "---" + name + year + "年" + time));
                passengerService.getMaxPassengerFlowvoid(vcode, time, year);
            }
        }
    }

    /**
     * 存储每年有游客数的天数
     * 每填23点40执行
     * @throws Exception
     */
    @SysLog
    @Async
    @Scheduled(cron = "0 50 23 * * ?")
//    @Scheduled(initialDelay = 1000L , fixedRate = 300000L)
    public void getAvergePassengerYear() throws Exception {
        String year = DateUtil.getCurYearStr();
        List<Map<String, Object>> listVocde = commonService.getPassengerVcode();
        if (listVocde.size() > 0) {
            for (int i = 0; i < listVocde.size(); i++) {
                String vcode = listVocde.get(i).get("vcode") + "";
                String name = listVocde.get(i).get("name") + "";
                System.out.println(String.format("存储本年最大峰值即日期--------24小时" + "---" + name + year + "年"));
                passengerService.getAvergePassengerYear(vcode, year);
            }
        }
    }


    private class passengerTask implements Callable<String> {

        private Map<String, Object> entry;

        public passengerTask() {
        }

        public passengerTask(Map<String, Object> entry) {
            this.entry = entry;
        }

        public Map<String, Object> getEntry() {
            return entry;
        }

        public void setEntry(Map<String, Object> entry) {
            this.entry = entry;
        }

        @Override
        public String call() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("线程:%s 正在处理[实时人数分析]数据 vcode:%s", Thread.currentThread().getName(), entry.get("name")));
            return runTask(entry).toString();//返回耗时数
        }
    }
    private class bigCarFlowTask implements Callable<String> {

        private Map<String, Object> entry;

        public bigCarFlowTask() {
        }

        public bigCarFlowTask(Map<String, Object> entry) {
            this.entry = entry;
        }

        public Map<String, Object> getEntry() {
            return entry;
        }

        public void setEntry(Map<String, Object> entry) {
            this.entry = entry;
        }

        @Override
        public String call() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("线程:%s 正在处理[景区大数据]数据 vcode:%s", Thread.currentThread().getName(), entry.get("name")));
            return bigCarFlowTask(entry).toString();//返回耗时数
        }
    }
    private class passengerFlowHorsTask implements Callable<String> {

        private Map<String, Object> entry;

        public passengerFlowHorsTask() {
        }

        public passengerFlowHorsTask(Map<String, Object> entry) {
            this.entry = entry;
        }

        public Map<String, Object> getEntry() {
            return entry;
        }

        public void setEntry(Map<String, Object> entry) {
            this.entry = entry;
        }

        @Override
        public String call() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("线程:%s 正在处理[景区大数据]数据 vcode:%s", Thread.currentThread().getName(), entry.get("name")));
            return passengerFlowHorsRun(entry).toString();//返回耗时数
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
//    @Scheduled(cron = "0 0 * * * ?")
    @Scheduled(initialDelay = 1000L , fixedRate = 120000L)
    public void carFlowScheduled() throws Exception {
        boolean isTure = DateUtil.getHoursByMinite();
        //早7到晚8执行
        if(isTure==true){
            taskManger();
        }

    }

    /***
     * 线程调用接口方法
     */
    @Async
    public void taskManger() {
        List<Map<String,Object>> listVcode = commonService.getPassengerVcode();
        //使用多线程去拉取每天的数据.
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
        for (Map<String, Object> entry : listVcode) {
//        Map<String, Object> entry = new HashMap<>();
//        entry.put("vcode","a02dbca3d06409ba7fbf444e0565242d");
            if(es.isShutdown()){
                es = ExecutorServiceUtils.getFixedThreadPool();
            }
            futureList.add(es.submit(new passengerTask(entry)));
        }
        try {
            getPassengerFlowByMonth();//统计月份缓存
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Future<String> listFuture : futureList) {
            try {
                timeCount += Long.parseLong(listFuture.get());//累计耗时
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        es.shutdown();
    }


    @Async
    public Long runTask(Map<String, Object> entry) {
        ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
        Date s = new Date();
        String startTime = DateUtil.getDateBefore() + " 00:00:00";
        String endTime  = DateUtil.getCurDateStr()+" 23:59:59";
        String time = DateUtil.getCurDateStr();
        String sTime = DateUtil.getDateBefore();
        String vcode = entry.get("vcode")+"";
        //1、当前时间段，3天的实时人数获取。补偿3天
        passengerService.getPassengerFlowByDay(vcode,startTime,endTime);
        //当前时间段，3天的每小时实时人数。补偿3天
        passengerService.getPassengerFlowByHour(vcode,sTime,time);
        //当前时间段，景区景点的实时人数。补偿3天
        passengerService.getPassengerFlowByScenic(vcode);
        es.shutdown();
        Date e = new Date();
        return e.getTime() - s.getTime();
    }
    @Async
    public Long bigCarFlowTask(Map<String, Object> entry) {
        Date s = new Date();
        String vcode = entry.get("vcode")+"";
        String name = entry.get("name")+"";
        //早7到晚8执行
        boolean isTure = DateUtil.getHoursByMinite();
        isTure = true;
        if(isTure==true) {
            String year = DateUtil.getCurYearStr();
            System.out.print(String.format("景区游客来源分析数据获取--地图展示------"+name));
            passengerService.getPeopleYearCommon(vcode, year, "yes");//通用的
        }

        //景区大数据-省内游客TOP10
        //早7到晚8执行
        if(isTure==true){
            String year = DateUtil.getCurYearStr();
            System.out.print(String.format("省内游客TOP10数据初始化拉取" + "-------" + name + "--------"));
            passengerService.getProvinceLicensePlateRank(year, vcode);
        }
        Date e = new Date();
        return e.getTime() - s.getTime();
    }
    @Async
    public Long passengerFlowHorsRun(Map<String, Object> entry) {
        Date s = new Date();
        String vcode = entry.get("vcode")+"";
        String name = entry.get("name")+"";
        //早上7点到晚上8点才执行。
        boolean isTure = DateUtil.getHoursByMinite();
        if(isTure==true){
            String startTime = DateUtil.getCurDateStr() + " 00:00:00";
            String endTime  = DateUtil.getCurDateStr()+" 23:59:59";
            String time = DateUtil.getCurDateStr();
            String year = DateUtil.getCurYearStr();
            String month = DateUtil.getCurMonthStr();
            System.out.println(String.format("景区当日最新实时人数获取--------300S" + "---" + name));
            passengerService.getPassengerFlowByThisHours(vcode, startTime, endTime, time, year, month);
        }
        Date e = new Date();
        return e.getTime() - s.getTime();
    }



    /**
     * 景区大数据-按月实时游客数据(所有景区)
     * 获取当月景区的每天的缓存进行累加
     */
    public void getPassengerFlowByMonth() throws Exception{
        String month = DateUtil.getCurMonthStr();
        String startTime = month+"-01";
        String endTime = DateUtil.getCurDateStr();
        List<Map<String, Object>> listVocde = commonService.getPassengerVcode();
        if (listVocde.size() > 0) {
            for (int i = 0; i < listVocde.size(); i++) {
                String vcode = listVocde.get(i).get("vcode") + "";
                String name = listVocde.get(i).get("name") + "";
                System.out.print(String.format("景区当“月”实时游客数据------" + name + "------开始"));
                passengerService.getPassengerFlowByMonth(vcode, month, startTime, endTime);
            }
        }
        //月抓完，抓季度
        getPassengerFlowByQuarter();
    }

    /**
     * 景区大数据-按季度实时游客数据(所有景区)
     * 这个方法不走DB，直接拉取月份缓存进行累加，然后存入季度缓存
     */
    public void getPassengerFlowByQuarter() throws Exception{
        String year = DateUtil.getCurYearStr();
        List<Map<String, Object>> listVocde = commonService.getPassengerVcode();
        if (listVocde.size() > 0) {
            for (int i = 0; i < listVocde.size(); i++) {
                String vcode = listVocde.get(i).get("vcode") + "";
                String name = listVocde.get(i).get("name") + "";
                System.out.print(String.format("景区当“年”实时游客数据------" + name + "------开始"));
                passengerService.getPassengerFlowByQuarter(vcode, year);
            }
        }
        //季度抓完，抓年份
        getPassengerFlowByYear();
    }

    /**
     * 景区大数据-按年实时游客数据(所有景区)
     * 这个方法不走DB，直接拉取月份缓存进行累加，然后存入年份缓存
     */
    public void getPassengerFlowByYear() throws Exception{
        String year = DateUtil.getCurYearStr();
        List<Map<String, Object>> listVocde = commonService.getPassengerVcode();
        if (listVocde.size() > 0) {
            for (int i = 0; i < listVocde.size(); i++) {
                String vcode = listVocde.get(i).get("vcode") + "";
                String name = listVocde.get(i).get("name") + "";
                System.out.print(String.format("景区当“年”实时游客数据------" + name + "------开始"));
                passengerService.getPassengerFlowByYear(vcode, year);
            }
        }
    }
    /**
     * 按小时统计各景区的实时人数
     * 8:00  9:00  10:00  格式
     * 每隔20分钟
     */
    @Scheduled(initialDelay = 250000L, fixedRate = 60000L * 20L)
    public void scenicSpotsDataAnalysisTimeAll() {
        List<Map<String, Object>> listVocde = commonService.getPassengerVcode();
        if (listVocde.size() > 0) {
            for (int i = 0; i < listVocde.size(); i++) {
                String vcode = listVocde.get(i).get("vcode") + "";
                passengerService.getScenicSpotsDataAnalysisTimeAll(vcode);
            }
        }
    }
}
