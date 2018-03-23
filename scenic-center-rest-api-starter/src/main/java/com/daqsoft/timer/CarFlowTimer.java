package com.daqsoft.timer;

import com.daqsoft.config.SysLog;
import com.daqsoft.redis.ExecutorServiceUtils;
import com.daqsoft.service.client.commonService.CommonService;
import com.daqsoft.service.client.trafficAnalysisService.*;
import com.daqsoft.utils.client.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 车流分析定时器
 *
 * @author zf
 * @version V3.0.0
 * @date 2017-08-17
 */
//@Component
public class CarFlowTimer {

    //耗时
    private long hs=0;

    @Autowired
    private CarFlowWidgetService carFlowService;

    @Autowired
    private CarTypeWidgetOpService carTypeServiceOp;

    @Autowired
    private RealTimePeopleWidgetService realTimePeopleService;

    @Autowired
    private BigCarFlowAnalyseService bigCarFlowService;

    @Autowired
    private CarComeToService carComeFromService;

    @Autowired
    private CarProvinceService carProvinceService;

    @Autowired
    private JqTvpmCheckoutWidgetService jqTvpmCheckoutService;

    @Autowired
    private JqTimelyParkingWidgetServiceOp jqTimelyParkingServiceOp;

    @Autowired
    private CommonService commonService;

    /**
     * 调用接口
     *
     * @param entry
     * @return
     */
    public Long runTask(Map<String, String> entry) {
        Date s = new Date();



        String year = DateUtil.getCurYearStr();
        String month = DateUtil.getCurMonthStr();
        String quarter = year + "_" + DateUtil.getNumberCurrentQuarter();
        String startTime = DateUtil.getDateBefore();
        String endTime = DateUtil.getCurDateStr();
        String vcode = entry.get("vcode");


        System.out.println(String.format("车流分析-各类车辆变动趋势" + "---" + entry.entrySet()));

        //年
        carFlowService.findChangeCarSumByMonth(endTime, "year", vcode);
        /*carTypeServiceOp.getCarTypeMonth(vcode, year, "0");
        carTypeServiceOp.getCarTypeMonth(vcode, year, "1");
        carTypeServiceOp.getCarTypeMonth(vcode, year, "2");*/


        System.out.println(String.format("车流分析-各类车辆总体数量分布" + "---" + entry.get("name")));
        //年
        carFlowService.findCarTypePrecentByTime(endTime, "year", vcode);

        System.out.println(String.format("车流分析-小客车与散客量关系" + "---" + entry.get("name")));

        //年
        carFlowService.getCarFlowMonth(vcode, endTime);
        realTimePeopleService.findScenicTimePeople(endTime, "year", vcode);


        System.out.println(String.format("车流分析-车流趋势" + "---" + entry.get("name")));
        //年度
        bigCarFlowService.findCarFlowByDay(year, year, "year", vcode);
        //每年季度
        bigCarFlowService.findCarFlowQuarter(endTime, vcode);
        //每年月度
        carFlowService.getCarFlowMonth(vcode, year);
        carFlowService.getCarFlowMonth(vcode, String.valueOf(Integer.parseInt(year) - 1));


        System.out.println(String.format("车流分析-车流来源地" + "---" + entry.get("name")));
        //区间年 地图
        carComeFromService.getCarComeFromYear2Year(vcode, year, year);

        //车辆来源地年分（省份）排行榜
        carProvinceService.getCarComeFromYear2Year(vcode, year, year);


        System.out.println(String.format("车流分析-停车场使用" + "---" + entry.get("name")));

        //年
        jqTvpmCheckoutService.getCheckoutNum(endTime, "year", vcode);

        //车位使用趋势（年 ）
        jqTimelyParkingServiceOp.findCountParkingTrend(endTime, "year", vcode);


        /*findByDay(endTime,"day",vcode);
        findByMonth(endTime,"month",vcode);
        findByDayToDay(startTime,endTime,"day",vcode);
        findByQuarter(vcode,quarter);*/


        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
        //线程跑30日度+区间数据
        for (int i = 0; i < 30; i++) {
            try {
                String d = DateUtil.getHistDate(i);
                futureList.add(es.submit(new FindCountParkingTrend(d, "day", vcode)));
                futureList.add(es.submit(new FindByDayToDay(d, endTime, "day", vcode)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //线程跑3个月度数据
        for (int i = 0; i < 3; i++) {
            try {
                String d = DateUtil.getHistMonth(i);
                futureList.add(es.submit(new FindByMonth(d, "month", vcode)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //线程跑4个季度数据
        for (int i = 1; i <= 4; i++) {
             String d = year + "_" + i;
             futureList.add(es.submit(new FindByQuarter(d, "quarter", vcode)));
        }
        Date e = new Date();
        return e.getTime() - s.getTime();
    }

    /***
     * 线程调用接口方法
     */
    @Async
    public void taskManger() {
        List<Map<String, Object>> vcodeList = commonService.getCarFlowVcode();
        //使用多线程去拉取每天的数据.
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
        if(vcodeList.size() > 0){
            for (Map map : vcodeList){
                if(es.isShutdown()){
                    es = ExecutorServiceUtils.getFixedThreadPool();
                }
                futureList.add(es.submit(new JqTask(map)));
            }
        }
        es.shutdown();
        System.out.println("*******车流分析已完成***");


    }

    /**
     * 车流分析
     * tomcat启动后1小时执行，每1个小时触发一次
     */
    @SysLog("车流分析定时器~~~")
    @Scheduled(initialDelay = 120000L, fixedRate = 3600000L)
    public void carFlowScheduled() throws Exception {
        taskManger();
        /*//大key
        String k = "scheduled:";
        //hashkey
        String hk = "carflow"  ;

        String str = (String) RedisCache.getHash(redisTemplate, k, hk);
        if("0".equals(str)){
            RedisCache.putHash(redisTemplate, k, hk, "1");//已执行

            System.out.println("执行一次车流定时任务成功-已执行");
            //50分钟后修改初始状态
            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
            scheduledThreadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("修改车流定时任务状态-未执行");
                    RedisCache.putHash(redisTemplate, k, hk, "0");//未执行
                    scheduledThreadPool.shutdown();
                }
            }, 8,  TimeUnit.SECONDS);


        }*/
        //System.out.println("***************");
//        System.out.println("推送【车流分析】 到redis 缓存-------耗时:%s ",  hs);
//        hs=0;//执行完毕，耗时清零
    }

    /**
     * 多线程处理
     */
    private class JqTask implements Callable<String> {

        private Map<String, String> entry;

        public JqTask() {
        }

        public JqTask(Map<String, String> entry) {
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
            System.out.println(String.format("线程:%s 正在处理[车流分析]数据 vcode:%s", Thread.currentThread().getName(), entry.get("vcode")));
            return runTask(entry).toString();//返回耗时数
        }
    }

    /**
     * 多线程处理  日度
     */
    private class FindCountParkingTrend implements Callable<String> {

        private String endTime;
        private String type;
        private String vcode;

        public FindCountParkingTrend(String endTime, String type, String vcode) {
            this.endTime = endTime;
            this.type = type;
            this.vcode = vcode;
        }

        public FindCountParkingTrend() {
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        @Override
        public String call() {
            Date s=new Date();
            System.out.println(String.format("线程:%s 正在处理[车流分析30天内]数据 vcode:%s", Thread.currentThread().getName(), vcode));

            //车位使用趋势
            jqTimelyParkingServiceOp.findCountParkingTrend(endTime, type, vcode);

            //停车场车位（日期段）总体使用情况
            jqTimelyParkingServiceOp.findJqTimelyPercent(vcode, endTime, type);

            //车流分析-停车场使用
            jqTvpmCheckoutService.getCheckoutNum(endTime, type, vcode);

            //车流分析-车流趋势
            carFlowService.getCarFlowSum(vcode, endTime);
            carFlowService.getCarFlowHour(vcode, endTime);

            //车流分析-小客车与散客量关系
            realTimePeopleService.findScenicTimePeople(endTime, type, vcode);
            carFlowService.getCarFlowHour(vcode, endTime);

            //车流分析-各类车辆总体数量分布
            carFlowService.findCarTypePrecentByTime(endTime, type, vcode);

            //车流分析-各类车辆变动趋势
            carFlowService.findChangeCarSumByMonth(endTime, type, vcode);
            /*carTypeServiceOp.getCarTypeHour(vcode, endTime, "0");
            carTypeServiceOp.getCarTypeHour(vcode, endTime, "1");
            carTypeServiceOp.getCarTypeHour(vcode, endTime, "2");*/

            Date e=new Date();
            return String.valueOf(e.getTime()-s.getTime());
        }
    }

    public void findByDay(String endTime,String type,String vcode){
        //车位使用趋势
        jqTimelyParkingServiceOp.findCountParkingTrend(endTime, type, vcode);

        //停车场车位（日期段）总体使用情况
        jqTimelyParkingServiceOp.findJqTimelyPercent(vcode, endTime, type);

        //车流分析-停车场使用
        jqTvpmCheckoutService.getCheckoutNum(endTime, type, vcode);

        //车流分析-车流趋势
        carFlowService.getCarFlowSum(vcode, endTime);
        carFlowService.getCarFlowHour(vcode, endTime);

        //车流分析-小客车与散客量关系
        realTimePeopleService.findScenicTimePeople(endTime, type, vcode);
        carFlowService.getCarFlowHour(vcode, endTime);

        //车流分析-各类车辆总体数量分布
        carFlowService.findCarTypePrecentByTime(endTime, type, vcode);

        //车流分析-各类车辆变动趋势
        carTypeServiceOp.getCarTypeHour(vcode, endTime, "0");
        carTypeServiceOp.getCarTypeHour(vcode, endTime, "1");
        carTypeServiceOp.getCarTypeHour(vcode, endTime, "2");
    }

    /***
     * 多线程处理 月度
     */
    private class FindByMonth implements Callable<String>  {

        private String endTime;
        private String type;
        private String vcode;

        public FindByMonth(String endTime, String type, String vcode) {
            this.endTime = endTime;
            this.type = type;
            this.vcode = vcode;
        }

        public FindByMonth() {
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        @Override
        public String call() {
            Date s=new Date();
            System.out.println(String.format("线程:%s 正在处理[车流分析3个月度内]数据 vcode:%s", Thread.currentThread().getName(), vcode));

            ////////车流分析-各类车辆变动趋势
            //月
            carFlowService.findChangeCarSumByMonth(endTime, type, vcode);

            ////////车流分析-各类车辆总体数量分布
            //月
            carFlowService.findCarTypePrecentByTime(endTime, type, vcode);

            ////////车流分析-小客车与散客量关系
            //月
            realTimePeopleService.findScenicTimePeople(endTime, type, vcode);
            //carFlowService.getCarFlowDay(vcode, endTime);


            ////////车流分析-车流趋势
            //每月
            carFlowService.getCarFlowDay(vcode, endTime);

            ////////车流分析-车流来源地

            //月 地图（按每天）车流来源地（按每天日期分析）
            carComeFromService.getCarComeFromMonth(vcode, endTime);
            //车辆来源地按月查询（省份）排行榜
            carProvinceService.getCarComeFromMonth(vcode, endTime);

            ////////车流分析-停车场使用
            //月
            jqTvpmCheckoutService.getCheckoutNum(endTime, type, vcode);

            //停车场车位（日期段）总体使用情况  月
            jqTimelyParkingServiceOp.findJqTimelyPercent(vcode, endTime, type);

            //车位使用趋势（年 ，月 ，日）
            jqTimelyParkingServiceOp.findCountParkingTrend(endTime, type, vcode);
            Date e=new Date();
            return String.valueOf(e.getTime()-s.getTime());
        }
    }

    public void findByMonth(String endTime,String type,String vcode){
        ////////车流分析-各类车辆变动趋势
        //月
        carFlowService.findChangeCarSumByMonth(endTime, type, vcode);

        ////////车流分析-各类车辆总体数量分布
        //月
        carFlowService.findCarTypePrecentByTime(endTime, type, vcode);

        ////////车流分析-小客车与散客量关系
        //月
        realTimePeopleService.findScenicTimePeople(endTime, type, vcode);
        //carFlowService.getCarFlowDay(vcode, endTime);


        ////////车流分析-车流趋势
        //每月
        carFlowService.getCarFlowDay(vcode, endTime);

        ////////车流分析-车流来源地

        //月 地图（按每天）车流来源地（按每天日期分析）
        carComeFromService.getCarComeFromMonth(vcode, endTime);
        //车辆来源地按月查询（省份）排行榜
        carProvinceService.getCarComeFromMonth(vcode, endTime);

        ////////车流分析-停车场使用
        //月
        jqTvpmCheckoutService.getCheckoutNum(endTime, type, vcode);

        //停车场车位（日期段）总体使用情况  月
        jqTimelyParkingServiceOp.findJqTimelyPercent(vcode, endTime, type);

        //车位使用趋势（年 ，月 ，日）
        jqTimelyParkingServiceOp.findCountParkingTrend(endTime, type, vcode);
    }

    /***
     * 多线程处理 日期段
     */
    private class FindByDayToDay implements Callable<String>  {

        private String startTime;
        private String endTime;
        private String type;
        private String vcode;

        public FindByDayToDay(String startTime, String endTime, String type, String vcode) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.type = type;
            this.vcode = vcode;
        }

        public FindByDayToDay() {
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        @Override
        public String call() {
            Date s=new Date();
            System.out.println(String.format("线程:%s 正在处理[车流分析30天内日期段]数据 vcode:%s", Thread.currentThread().getName(), vcode));
            ///////////车流分析-各类车辆变动趋势
            //日期段
            carFlowService.findChangeCarSum(startTime, endTime, type, vcode);

            //////////车流分析-各类车辆总体数量分布
            //日期段
            carFlowService.findCarTypePrecent(startTime, endTime, type, vcode);

            /////////车流分析-车流来源地
            //日期段 地图
            carComeFromService.getCarComeFromDay2Day(vcode, startTime, endTime);

            //车辆来源地时间段查询（省份）排行榜
            carProvinceService.getCarComeFromDay2Day(vcode, startTime, endTime);

            /////////车流分析-停车场使用

            //日期段
            jqTvpmCheckoutService.getCheckoutBetween(startTime, endTime, vcode);
            //停车场车位（日期段）总体使用情况
            jqTimelyParkingServiceOp.findJqTimelyBetweenTime(vcode, startTime, endTime);
            Date e=new Date();
            return String.valueOf(e.getTime()-s.getTime());

        }
    }

    public void findByDayToDay(String startTime,String endTime,String type,String vcode){
        ///////////车流分析-各类车辆变动趋势
        //日期段
        carFlowService.findChangeCarSum(startTime, endTime, type, vcode);

        //////////车流分析-各类车辆总体数量分布
        //日期段
        carFlowService.findCarTypePrecent(startTime, endTime, type, vcode);

        /////////车流分析-车流来源地
        //日期段 地图
        carComeFromService.getCarComeFromDay2Day(vcode, startTime, endTime);

        //车辆来源地时间段查询（省份）排行榜
        carProvinceService.getCarComeFromDay2Day(vcode, startTime, endTime);

        /////////车流分析-停车场使用

        //日期段
        jqTvpmCheckoutService.getCheckoutBetween(startTime, endTime, vcode);
        //停车场车位（日期段）总体使用情况
        jqTimelyParkingServiceOp.findJqTimelyBetweenTime(vcode, startTime, endTime);
    }


    /***
     * 多线程处理 季度
     */
    private class FindByQuarter implements Callable<String>  {

        private String quarter;
        private String type;
        private String vcode;

        public FindByQuarter(String quarter, String type, String vcode) {
            this.quarter = quarter;
            this.type = type;
            this.vcode = vcode;
        }

        public FindByQuarter() {
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        public String getQuarter() {
            return quarter;
        }

        public void setQuarter(String quarter) {
            this.quarter = quarter;
        }

        @Override
        public String call() {
            Date s=new Date();
            System.out.println(String.format("线程:%s 正在处理[车流分析4个季度]数据 vcode:%s", Thread.currentThread().getName(), vcode));

            ////////////////车流分析-车流来源地
            //季度 地图
            carComeFromService.getCarComeFromQuarter(vcode, quarter);
            //车辆来源地季度查询（省份）排行榜
            carProvinceService.getCarComeFromQuarter(vcode, quarter);
            Date e=new Date();
            return String.valueOf(e.getTime()-s.getTime());
        }
    }

    public void findByQuarter(String vcode,String quarter){
        ////////////////车流分析-车流来源地
        //季度 地图
        carComeFromService.getCarComeFromQuarter(vcode, quarter);
        //车辆来源地季度查询（省份）排行榜
        carProvinceService.getCarComeFromQuarter(vcode, quarter);
    }


}
