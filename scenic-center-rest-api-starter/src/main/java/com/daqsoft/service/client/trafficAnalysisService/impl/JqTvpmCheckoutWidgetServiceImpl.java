package com.daqsoft.service.client.trafficAnalysisService.impl;

import com.daqsoft.mapper.client.carflow.JqTvpmCheckoutWidgetDao;
import com.daqsoft.redis.ExecutorServiceUtils;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.trafficAnalysisService.JqTvpmCheckoutWidgetService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.JqTvpmCheckoutWidget;
import com.daqsoft.vo.client.madeVoBean.JqTvpm_Percent_TendWidget;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Author: zf.
 * @Date: Created in 2017/07/27.
 * @Version: V3.0.0.
 * @describe: 停车场service实现类
 */
@Service
public class JqTvpmCheckoutWidgetServiceImpl implements JqTvpmCheckoutWidgetService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private JqTvpmCheckoutWidgetDao jqTvpmCheckoutDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 停车场区域统计
     *
     * @param time  时间
     * @param type  时间类型
     * @param vcode 景区编码
     * @return
     * @update zf 20170822
     */
    @Override
    public Object getCheckoutNum(String time, String type, String vcode) {
        List<JqTvpm_Percent_TendWidget> JqTvpmCheckoutList = new LinkedList<JqTvpm_Percent_TendWidget>();
        Map map = new HashMap<>();
        map.put("vcode", vcode);

        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_JQTVPMCHECKOUTWIDGET + "getCheckoutNum:getCheckoutNum:";
        //hashkey
        String hk = type + time + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            Date dates = null;
            DateFormat df = null;
            String areaSumCar = "";
            long otherCar = 0;
            long topFiveCar = 0;
            if (!"".equals(type) && type.equals("day")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dates = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //每天
                df = new SimpleDateFormat("yyyy-MM-dd");
                map.put("time", df.format(dates));
                map.put("startTime",df.format(dates)+" 00:00:00");
                map.put("endTime",df.format(dates)+" 23:59:59");
                /*List<JqTvpm_Percent_TendWidget> sumCar = jqTvpmCheckoutDao.getJqTvpmSum(map);
                for (JqTvpm_Percent_TendWidget carTotal : sumCar) {
                    areaSumCar = carTotal.getNum();
                }*/

                List<JqTvpmCheckoutWidget> CheckoutList = jqTvpmCheckoutDao.getJqTvpmCheckout(map);
                if (CheckoutList == null || CheckoutList.size() < 1) {
//                JqTvpm_Percent_TendWidget nullTend=new JqTvpm_Percent_TendWidget();
//                nullTend.setNumCar("No");
//                nullTend.setNum("0");
//                JqTvpmCheckoutList.add(nullTend);
                } else {
                    for (JqTvpmCheckoutWidget JqCheckoutList : CheckoutList) {
                        JqTvpm_Percent_TendWidget jqCheckout = new JqTvpm_Percent_TendWidget();
                        jqCheckout.setNumCar(JqCheckoutList.getCarNum());
                        jqCheckout.setNum(JqCheckoutList.getNum());
                        jqCheckout.setProvince(JqCheckoutList.getProvince());
                        JqTvpmCheckoutList.add(jqCheckout);
                    }
                    /*for (JqTvpmCheckoutWidget JqCheckoutList1 : CheckoutList) {
                        topFiveCar = topFiveCar + Long.valueOf(JqCheckoutList1.getNum());
                    }
                    otherCar = Long.valueOf(areaSumCar) - topFiveCar;
                    JqTvpm_Percent_TendWidget jqCheckout1 = new JqTvpm_Percent_TendWidget();
                    jqCheckout1.setNumCar("其他");
                    jqCheckout1.setProvince("其他");
                    if(otherCar>0){
                        jqCheckout1.setNum(String.valueOf(otherCar));
                    }else{
                        jqCheckout1.setNum("0");
                    }
                    JqTvpmCheckoutList.add(jqCheckout1);*/
                }
            }
            if (!"".equals(type) && type.equals("month")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                try {
                    dates = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //每月
                df = new SimpleDateFormat("yyyy-MM");
//                map.put("time",df.format(dates));
//                map.put("startTime",df.format(dates)+"-01 00:00:00");
//                map.put("endTime",df.format(dates)+"-31 23:59:59");
//                List<JqTvpm_Percent_TendWidget> sumCar=jqTvpmCheckoutDao.getJqTvpmSum(map);
                //调用线程方法
                /*List<JqTvpm_Percent_TendWidget> sumCar = this.getJqTvpmSumByMonth(vcode, df.format(dates));
                for (JqTvpm_Percent_TendWidget carTotal : sumCar) {
                    areaSumCar = carTotal.getNum();
                }*/
//                List<JqTvpmCheckoutWidget> CheckoutList = jqTvpmCheckoutDao.getJqTvpmCheckout(map);
                //调用线程方法
                List<JqTvpmCheckoutWidget> CheckoutList = this.getJqTvpmCheckoutByMonth(vcode, df.format(dates));
                if (CheckoutList == null || CheckoutList.size() < 1) {
//                JqTvpm_Percent_TendWidget nullTend=new JqTvpm_Percent_TendWidget();
//                nullTend.setNumCar("No");
//                nullTend.setNum("0");
//                JqTvpmCheckoutList.add(nullTend);
                } else {
                    for (JqTvpmCheckoutWidget JqCheckoutList1 : CheckoutList) {
                        JqTvpm_Percent_TendWidget jqCheckout1 = new JqTvpm_Percent_TendWidget();
                        jqCheckout1.setNumCar(JqCheckoutList1.getCarNum());
                        jqCheckout1.setNum(JqCheckoutList1.getNum());
                        jqCheckout1.setProvince(JqCheckoutList1.getProvince());
                        JqTvpmCheckoutList.add(jqCheckout1);
                    }
                    /*for (JqTvpmCheckoutWidget JqCheckoutList1 : CheckoutList) {
                        topFiveCar = topFiveCar + Long.valueOf(JqCheckoutList1.getNum());
                    }
                    otherCar = Long.valueOf(areaSumCar) - topFiveCar;
                    JqTvpm_Percent_TendWidget jqCheckout1 = new JqTvpm_Percent_TendWidget();
                    jqCheckout1.setNumCar("其他");
                    jqCheckout1.setProvince("其他");
                    if(otherCar>0){
                        jqCheckout1.setNum(String.valueOf(otherCar));
                    }else{
                        jqCheckout1.setNum("0");
                    }
                    JqTvpmCheckoutList.add(jqCheckout1);*/
                }
            }
            if (!"".equals(type) && type.equals("year")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                try {
                    dates = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //每年
                df = new SimpleDateFormat("yyyy");
//                map.put("time",df.format(dates));
//                List<JqTvpm_Percent_TendWidget> sumCar=jqTvpmCheckoutDao.getJqTvpmSum(map);
                //调用线程方法
                /*List<JqTvpm_Percent_TendWidget> sumCar = this.getJqTvpmSumByYear(vcode, df.format(dates));
                for (JqTvpm_Percent_TendWidget carTotal : sumCar) {
                    areaSumCar = carTotal.getNum();
                }*/
//                List<JqTvpmCheckoutWidget> CheckoutList = jqTvpmCheckoutDao.getJqTvpmCheckout(map);
                //调用线程方法
                List<JqTvpmCheckoutWidget> CheckoutList = this.getJqTvpmCheckoutByYear(vcode, df.format(dates));
                if (CheckoutList == null || CheckoutList.size() < 1) {
//                JqTvpm_Percent_TendWidget nullTend=new JqTvpm_Percent_TendWidget();
//                nullTend.setNumCar("No");
//                nullTend.setNum("0");
//                JqTvpmCheckoutList.add(nullTend);
                } else {
                    for (JqTvpmCheckoutWidget JqCheckoutList2 : CheckoutList) {
                        JqTvpm_Percent_TendWidget jqCheckout2 = new JqTvpm_Percent_TendWidget();
                        jqCheckout2.setNumCar(JqCheckoutList2.getCarNum());
                        jqCheckout2.setNum(JqCheckoutList2.getNum());
                        jqCheckout2.setProvince(JqCheckoutList2.getProvince());
                        JqTvpmCheckoutList.add(jqCheckout2);
                    }
                    /*for (JqTvpmCheckoutWidget JqCheckoutList1 : CheckoutList) {
                        topFiveCar = topFiveCar + Long.valueOf(JqCheckoutList1.getNum());
                    }
                    otherCar = Long.valueOf(areaSumCar) - topFiveCar;
                    JqTvpm_Percent_TendWidget jqCheckout1 = new JqTvpm_Percent_TendWidget();
                    jqCheckout1.setNumCar("其他");
                    jqCheckout1.setProvince("其他");
                    if(otherCar>0){
                        jqCheckout1.setNum(String.valueOf(otherCar));
                    }else{
                        jqCheckout1.setNum("0");
                    }
                    JqTvpmCheckoutList.add(jqCheckout1);*/
                }

            }
            RedisCache.putHash(redisTemplate, k, hk, JqTvpmCheckoutList);
            obj = JqTvpmCheckoutList;
        }


        return obj;
    }


    /**
     * 停车场区域统计时间段
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param vcode     景区编码
     * @return
     * @update zf 20170802
     */
    @Override
    public Object getCheckoutBetween(String startTime, String endTime, String vcode) {
        List<JqTvpm_Percent_TendWidget> JqTvpmCheckoutListBt = new LinkedList<JqTvpm_Percent_TendWidget>();
        Map map = new HashMap<>();
        map.put("vcode", vcode);

        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_JQTVPMCHECKOUTWIDGET + "getCheckoutBetween:getCheckoutBetween:";
        //hashkey
        String hk = "day" + startTime + endTime + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            Date stTime = null;
            Date enTime = null;
            DateFormat df = null;
            String areaSumCar = "";
            long otherCar = 0;
            long topFiveCar = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                stTime = sdf.parse(startTime);
                enTime = sdf.parse(endTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            df = new SimpleDateFormat("yyyy-MM-dd");
            map.put("stTime", df.format(stTime));
            map.put("enTime", df.format(enTime));
            List<JqTvpm_Percent_TendWidget> list = new ArrayList<>();
//            List<JqTvpm_Percent_TendWidget> sumCar=jqTvpmCheckoutDao.getJqTvpmSumBtweenSum(map);

            //获取区间内所有日期list,用于后面多线程循环读取数据
            List<String> subsectionDateList = DateUtil.getSubsectionDateList(df.format(stTime), df.format(enTime));

            List<Future<List<JqTvpm_Percent_TendWidget>>> futureList = new ArrayList<>();
            //使用多线程去拉取每天的数据.
            ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
            for (String dateStr : subsectionDateList) {
                if(es.isShutdown()){
                    es = ExecutorServiceUtils.getFixedThreadPool();
                }
                futureList.add(es.submit(new JqTvpmSumByDayTask(vcode, dateStr)));
            }
            for (Future<List<JqTvpm_Percent_TendWidget>> listFuture : futureList) {
                try {
                    list.addAll(listFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }


            list = groupByDataJqTvpmSum(list);// 组装区间

            for (JqTvpm_Percent_TendWidget carTotal : list) {
                areaSumCar = carTotal.getNum();
            }


//            List<JqTvpmCheckoutWidget> CheckoutBtList = jqTvpmCheckoutDao.getJqTvpmBetween(map);
            List<JqTvpmCheckoutWidget> CheckoutBtList = new ArrayList<>();
            List<Future<List<JqTvpmCheckoutWidget>>> futureList2 = new ArrayList<>();
            //使用多线程去拉取每天的数据.
            for (String dateStr : subsectionDateList) {
                futureList2.add(es.submit(new JqTvpmCheckoutByDayTask(vcode, dateStr)));
            }

            for (Future<List<JqTvpmCheckoutWidget>> listFuture : futureList2) {
                try {
                    CheckoutBtList.addAll(listFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            es.shutdown();
            /*while(!es.isTerminated()) {
                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

            CheckoutBtList = groupByData(CheckoutBtList);// 组装区间
            if (CheckoutBtList == null || CheckoutBtList.size() < 1) {
                JqTvpm_Percent_TendWidget nullTend = new JqTvpm_Percent_TendWidget();
                nullTend.setNumCar("");
                nullTend.setNum("");
                nullTend.setProvince("");
                JqTvpmCheckoutListBt.add(nullTend);
            } else {
                for (JqTvpmCheckoutWidget JqCheckoutListBt : CheckoutBtList) {
                    JqTvpm_Percent_TendWidget jqCheckoutBt = new JqTvpm_Percent_TendWidget();
                    jqCheckoutBt.setNumCar(JqCheckoutListBt.getCarNum());
                    jqCheckoutBt.setNum(JqCheckoutListBt.getNum());
                    jqCheckoutBt.setProvince(JqCheckoutListBt.getProvince());
                    JqTvpmCheckoutListBt.add(jqCheckoutBt);
                }
                for (JqTvpmCheckoutWidget JqCheckoutList1 : CheckoutBtList) {
                    topFiveCar = topFiveCar + Long.valueOf(JqCheckoutList1.getNum());
                }
                otherCar = Long.valueOf(areaSumCar) - topFiveCar;
                JqTvpm_Percent_TendWidget jqCheckout1 = new JqTvpm_Percent_TendWidget();
                jqCheckout1.setNumCar("其他");
                jqCheckout1.setProvince("其他");
                if(otherCar>0){
                    jqCheckout1.setNum(String.valueOf(otherCar));
                }else{
                    jqCheckout1.setNum("0");
                }
                JqTvpmCheckoutListBt.add(jqCheckout1);
            }
            RedisCache.putHash(redisTemplate, k, hk, JqTvpmCheckoutListBt);
            obj = JqTvpmCheckoutListBt;
        }


        return obj;
    }


    /**
     * 2017-08-17 zf
     * 组装停车场结算表  年
     *
     * @param vcode
     * @param year  年份
     * @return
     */
    private List<JqTvpm_Percent_TendWidget> getJqTvpmSumByYear(String vcode, String year) {
        String md5key = DigestUtils.md5Hex(vcode);
        List<JqTvpm_Percent_TendWidget> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_JQTVPMCHECKOUTWIDGET + "getJqTvpmSum:year:";
        String hk = "year" + "|" + year + "|" + md5key;

        //读取缓存
        list = (List<JqTvpm_Percent_TendWidget>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        list = new ArrayList<>();
        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        for (String month : months) {
            List<JqTvpm_Percent_TendWidget> ticketTypeByMonth = getJqTvpmSumByMonth( vcode, year + "-" + month);
            list.addAll(ticketTypeByMonth);
        }

        list = groupByDataJqTvpmSum(list);

        //写入缓存
        if (list.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, list);
        }

        return list != null ? list : new ArrayList<>();

    }

    /**
     * 2017-08-17 zf
     * 组装停车场结算表  月
     *
     * @param vcode
     * @param month 月份
     * @return
     */
    private List<JqTvpm_Percent_TendWidget> getJqTvpmSumByMonth( String vcode, String month) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<JqTvpm_Percent_TendWidget> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_JQTVPMCHECKOUTWIDGET + "getJqTvpmSum:month:";
        String hk = "month" + "|" + month + "|" + md5key;

        //读取缓存
        list = (List<JqTvpm_Percent_TendWidget>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        list = new ArrayList<>();
        try {
            Date parse = DateUtil.MONTH_SDF.parse(month);
            //得到此月份的所有天
            List<String> dayReport = DateUtil.getDayReport(parse);
            List<Future<List<JqTvpm_Percent_TendWidget>>> futureList = new ArrayList<>();
            //使用多线程去拉取每天的数据.
            ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
            for (String dateStr : dayReport) {
                if(es.isShutdown()){
                    es = ExecutorServiceUtils.getFixedThreadPool();
                }
                futureList.add(es.submit(new JqTvpmSumByDayTask(vcode, dateStr)));
            }

            for (Future<List<JqTvpm_Percent_TendWidget>> listFuture : futureList) {
                try {
                    list.addAll(listFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            es.shutdown();
            /*while(!es.isTerminated()) {
                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

        } catch (ParseException e) {
            e.printStackTrace();
        }

        list = groupByDataJqTvpmSum(list);

        //写入缓存
        if (list.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, list);
        }

        return list != null ? list : new ArrayList<>();
    }


    /***
     * 组装停车场结算表  天
     * 2017-08-17 zf
     * @param vcode
     * @param day
     * @return
     */
    public List<JqTvpm_Percent_TendWidget> getJqTvpmSumByDay(String vcode, String day) {
        String md5key = DigestUtils.md5Hex(vcode);
        List<JqTvpm_Percent_TendWidget> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_JQTVPMCHECKOUTWIDGET + "getJqTvpmSum:day:";
        String hk = "day" + "|" + day + "|" + md5key;

        //读取缓存
        list = (List<JqTvpm_Percent_TendWidget>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("startTime", day + " 00:00:00");
        map.put("endTime", day + " 23:59:59");
        //读取db
        list = jqTvpmCheckoutDao.getJqTvpmSumByDay(map);

        //写入缓存
        if (list.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, list);
        }

        return list != null ? list : new ArrayList<>();
    }

    /***
     * 组装 停车长数据
     * @param source
     * @return
     */
    public List<JqTvpm_Percent_TendWidget> groupByDataJqTvpmSum(List<JqTvpm_Percent_TendWidget> source) {
//        String type;
        long nums = 0;
//        Map<String, Long> numsMap = new HashMap<>();
        for (JqTvpm_Percent_TendWidget clTypeVO : source) {
//            type = clTypeVO.getCarNum();
            nums += Long.parseLong(clTypeVO.getNum());
            //获取历史数据 累计
//            long oldNums = numsMap.get(type) != null ? numsMap.get(type) : 0;
//            numsMap.put("null", nums );

        }

        List<JqTvpm_Percent_TendWidget> resultList = new ArrayList<>();
        JqTvpm_Percent_TendWidget clTypeVO = new JqTvpm_Percent_TendWidget(null, String.valueOf(nums));
        resultList.add(clTypeVO);
//        for (String k : numsMap.keySet()) {
//            clTypeVO = new JqTvpm_Percent_TendWidget(numsMap.get(k).toString(), k);
//            resultList.add(clTypeVO);
//        }

        return resultList;
    }


    /**
     * 多线程处理按天获取数据任务.停车场总数量
     */
    private class JqTvpmSumByDayTask implements Callable<List<JqTvpm_Percent_TendWidget>> {

        private String date;
        private String vcode;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        public JqTvpmSumByDayTask(String vcode, String date) {
            this.date = date;
            this.vcode = vcode;
        }

        public JqTvpmSumByDayTask() {
        }

        /**
         * @return 返回停车场结算表  天
         * @throws Exception if unable to compute a result
         */
        @Override
        public List<JqTvpm_Percent_TendWidget> call() throws Exception {
            System.out.println(String.format("线程:%s 正在处理[停车场车辆总数量]数据 vcode:%s,date:%s", Thread.currentThread().getName(), vcode, date));
            List<JqTvpm_Percent_TendWidget> list = getJqTvpmSumByDay(vcode, date);
            return list;
        }
    }


    /**
     * 2017-08-17 zf
     * 组装停车场结算表  年 分组车牌
     *
     * @param vcode
     * @param year  年份
     * @return
     */
    private List<JqTvpmCheckoutWidget> getJqTvpmCheckoutByYear(String vcode, String year) {
        String md5key = DigestUtils.md5Hex(vcode);
        List<JqTvpmCheckoutWidget> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_JQTVPMCHECKOUTWIDGET + "getJqTvpmCheckout:year:";
        String hk = "year" + "|" + year + "|" + md5key;

        //读取缓存
        list = (List<JqTvpmCheckoutWidget>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        list = new ArrayList<>();
        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        for (String month : months) {
            List<JqTvpmCheckoutWidget> ticketTypeByMonth = getJqTvpmCheckoutByMonth(vcode, year + "-" + month);
            list.addAll(ticketTypeByMonth);
        }

        list = groupByData(list);
        //写入缓存
        if (list.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, list);
        }

        return list != null ? list : new ArrayList<>();

    }

    /**
     * 2017-08-17 zf
     * 组装停车场结算表  月 分组车牌
     *
     * @param vcode
     * @param month 月份
     * @return
     */
    private List<JqTvpmCheckoutWidget> getJqTvpmCheckoutByMonth(String vcode, String month) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<JqTvpmCheckoutWidget> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_JQTVPMCHECKOUTWIDGET + "getJqTvpmCheckout:month:";
        String hk = "month" + "|" + month + "|" + md5key;

        //读取缓存
        list = (List<JqTvpmCheckoutWidget>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        list = new ArrayList<>();
        try {
            Date parse = DateUtil.MONTH_SDF.parse(month);
            //得到此月份的所有天
            List<String> dayReport = DateUtil.getDayReport(parse);
            List<Future<List<JqTvpmCheckoutWidget>>> futureList = new ArrayList<>();
            //使用多线程去拉取每天的数据.
            ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
            for (String dateStr : dayReport) {
                if(es.isShutdown()){
                    es = ExecutorServiceUtils.getFixedThreadPool();
                }
                futureList.add(es.submit(new JqTvpmCheckoutByDayTask(vcode, dateStr)));
            }

            for (Future<List<JqTvpmCheckoutWidget>> listFuture : futureList) {
                try {
                    list.addAll(listFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            es.shutdown();
            /*while(!es.isTerminated()) {
                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

        } catch (ParseException e) {
            e.printStackTrace();
        }


        list = groupByData(list);

        //写入缓存
        if (list.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, list);
        }

        return list != null ? list : new ArrayList<>();
    }

    /***
     * 组装 停车长数据
     * @param source
     * @return
     */
    public List<JqTvpmCheckoutWidget> groupByData(List<JqTvpmCheckoutWidget> source) {
        String type;
        Map<String, Long> numsMap = new HashMap<>();
        for (JqTvpmCheckoutWidget clTypeVO : source) {
            type =clTypeVO.getProvince()+"&"+ clTypeVO.getCarNum();
            long nums = Long.parseLong(clTypeVO.getNum());
            //获取历史数据 累计
            long oldNums = numsMap.get(type) != null ? numsMap.get(type) : 0;
            numsMap.put(type, nums + oldNums);

        }

        List<JqTvpmCheckoutWidget> resultList = new ArrayList<>();
        JqTvpmCheckoutWidget clTypeVO;
        for (String k : numsMap.keySet()) {
            String[] strings=k.split("&");
            clTypeVO = new JqTvpmCheckoutWidget(numsMap.get(k).toString(),strings[1],strings[0]);
            resultList.add(clTypeVO);
        }

        return resultList;
    }


    /***
     * 组装停车场结算表  天  分组车牌
     * 2017-08-17 zf
     * @param vcode
     * @param day
     * @return
     */
    public List<JqTvpmCheckoutWidget> getJqTvpmCheckoutByDay(String vcode, String day) {
        String md5key = DigestUtils.md5Hex(vcode);
        List<JqTvpmCheckoutWidget> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_JQTVPMCHECKOUTWIDGET + "getJqTvpmCheckout:day:";
        String hk = "day" + "|" + day + "|" + md5key;

        //读取缓存
        list = (List<JqTvpmCheckoutWidget>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("startTime", day + " 00:00:00");
        map.put("endTime", day + " 23:59:59");
        //读取db
        list = jqTvpmCheckoutDao.getJqTvpmCheckoutByDay(map);

        //写入缓存
        if (list.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, list);
        }

        return list != null ? list : new ArrayList<>();
    }


    /**
     * 多线程处理按天获取数据任务. 停车场 分组车牌数量
     */
    private class JqTvpmCheckoutByDayTask implements Callable<List<JqTvpmCheckoutWidget>> {

        private String date;
        private String vcode;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        public JqTvpmCheckoutByDayTask(String vcode, String date) {
            this.date = date;
            this.vcode = vcode;
        }

        public JqTvpmCheckoutByDayTask() {
        }

        /**
         * @return 返回停车场结算表  天
         * @throws Exception if unable to compute a result
         */
        @Override
        public List<JqTvpmCheckoutWidget> call() throws Exception {
//            Thread.sleep(2000);
            System.out.println(String.format("线程:%s 正在处理[停车场分组车牌数量]数据 vcode:%s,date:%s", Thread.currentThread().getName(), vcode, date));
            List<JqTvpmCheckoutWidget> list = getJqTvpmCheckoutByDay(vcode, date);
            return list;
        }
    }

}
