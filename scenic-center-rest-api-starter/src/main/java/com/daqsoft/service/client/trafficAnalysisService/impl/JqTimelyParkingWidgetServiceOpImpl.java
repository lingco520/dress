package com.daqsoft.service.client.trafficAnalysisService.impl;


import com.daqsoft.mapper.client.timelyparking.JqTimelyParkingWidgetDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.trafficAnalysisService.JqTimelyParkingWidgetServiceOp;
import com.daqsoft.utils.client.DateTools;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.PerCentUtil;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.JqTimelyParkingWidget;
import com.daqsoft.vo.client.madeVoBean.JqTimely_Parking_TendWidget;
import com.daqsoft.vo.client.madeVoBean.JqTimely_TimeOnTime_TendWidget;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lianch
 * @version V3.0.0
 * @date 2017-06-07.
 * @describe:景区实时停车场实现类
 */
@Service
public class JqTimelyParkingWidgetServiceOpImpl implements JqTimelyParkingWidgetServiceOp {

    @Autowired
    private JqTimelyParkingWidgetDao jqTimelyParkingdao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 实时停车场数量
     *
     * @param vcode 景区编码
     * @return
     */
    @Override
    public List<JqTimely_Parking_TendWidget> findJqTimelyParking(String vcode) {
        List<JqTimely_Parking_TendWidget> JqTimelyParkingList = jqTimelyParkingdao.getJqTimelyParking(vcode);
        return JqTimelyParkingList;
    }

    /**
     * 实时停车场所占百分比 年月日
     *
     * @param vcode 景区编码
     * @param time  时间
     * @param type  时间类型
     * @return
     * @update zf 20170822
     */
    @Override
    public Object findJqTimelyPercent(String vcode, String time, String type) {
        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_PARKINGWIDGET + "getJqTimelyPercent:findJqTimelyPercent:";
        //hashkey
        String hk = type + time + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            JqTimely_TimeOnTime_TendWidget JqTimelyListPercent = new JqTimely_TimeOnTime_TendWidget();
            String peopleSurplusPercent = "";
            String peopleUsefulPercen = "";
            String peopleSurplusPercentNew = "";
            String peopleUsefulPercentNew = "";
            Map map = new HashMap<>();
            map.put("vcode", vcode);

            Date dates = null;
            DateFormat df = null;
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
                List<JqTimelyParkingWidget> JqTimelyList = jqTimelyParkingdao.getJqTimelyPercent(map);
                for (JqTimelyParkingWidget parkingList : JqTimelyList) {
                    JqTimely_TimeOnTime_TendWidget jqParking = new JqTimely_TimeOnTime_TendWidget();
                    if ("".equals(parkingList) || parkingList == null) {
//                    peopleSurplusPercentNew = "0";
//                    peopleUsefulPercentNew = "0";
                    } else {
                        peopleSurplusPercentNew = parkingList.getSurplusPaking();
                        peopleUsefulPercentNew = parkingList.getParkingUseFul();
                        JqTimelyListPercent.setParkingSurplusPercent(peopleSurplusPercentNew);
                        JqTimelyListPercent.setParkingUsefulPercent(peopleUsefulPercentNew);
//                        JqTimelyListPercent.add(jqParking);
                    }
                }
            } else if (!"".equals(type) && type.equals("month")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                try {
                    dates = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //每月
                df = new SimpleDateFormat("yyyy-MM");
                map.put("time", df.format(dates));
                map.put("startTime",df.format(dates)+"-01 00:00:00");
                map.put("endTime",df.format(dates)+"-31 23:59:59");
                List<JqTimelyParkingWidget> JqTimelyList = jqTimelyParkingdao.getJqTimelyPercent(map);
                for (JqTimelyParkingWidget parkingListMonth : JqTimelyList) {
                    JqTimely_TimeOnTime_TendWidget jqParkingMonth = new JqTimely_TimeOnTime_TendWidget();
                    if ("".equals(parkingListMonth) || parkingListMonth == null) {
//                    peopleSurplusPercentNew = "0";
//                    peopleUsefulPercentNew = "0";
                    } else {
                        peopleSurplusPercentNew = parkingListMonth.getSurplusPaking();
                        peopleUsefulPercentNew = parkingListMonth.getParkingUseFul();
                        JqTimelyListPercent.setParkingSurplusPercent(peopleSurplusPercentNew);
                        JqTimelyListPercent.setParkingUsefulPercent(peopleUsefulPercentNew);
//                        JqTimelyListPercent.add(jqParkingMonth);
                    }

                }
            } else if (!"".equals(type) && type.equals("year")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                try {
                    dates = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //每年
                df = new SimpleDateFormat("yyyy");
                map.put("time", df.format(dates));
                map.put("startTime",df.format(dates)+"-01-01 00:00:00");
                map.put("endTime",df.format(dates)+"-12-31 23:59:59");
                List<JqTimelyParkingWidget> JqTimelyList = jqTimelyParkingdao.getJqTimelyPercent(map);
                for (JqTimelyParkingWidget parkingListMonth : JqTimelyList) {
                    if (null != parkingListMonth) {
                        JqTimely_TimeOnTime_TendWidget jqParkingMonth = new JqTimely_TimeOnTime_TendWidget();
                        if ("".equals(jqParkingMonth) || jqParkingMonth == null) {
//                    peopleSurplusPercentNew = "0";
//                    peopleUsefulPercentNew = "0";
                        } else {
                            peopleSurplusPercentNew = parkingListMonth.getSurplusPaking();
                            peopleUsefulPercentNew = parkingListMonth.getParkingUseFul();
                            JqTimelyListPercent.setParkingSurplusPercent(peopleSurplusPercentNew);
                            JqTimelyListPercent.setParkingUsefulPercent(peopleUsefulPercentNew);
//                            JqTimelyListPercent.add(jqParkingMonth);
                        }

                    }
                }
            }
            RedisCache.putHash(redisTemplate, k, hk, JqTimelyListPercent);
            obj = JqTimelyListPercent;
        }

        return obj;
    }

    /**
     * 实时停车场所占百分比 时间段
     *
     * @param vcode     景区编码
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public Object findJqTimelyBetweenTime(String vcode, String startTime, String endTime) {
        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_PARKINGWIDGET + "getJqTimelyBetweenTime:findJqTimelyBetweenTime:";
        //hashkey
        String hk = "day" + startTime + endTime + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
//            List<JqTimely_TimeOnTime_TendWidget> JqTimelyBetween = new LinkedList<JqTimely_TimeOnTime_TendWidget>();
            Map map = new HashMap<>();
            map.put("vcode", vcode);
            Date stTime = null;
            Date enTime = null;
            DateFormat df = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                stTime = sdf.parse(startTime);
                enTime = sdf.parse(endTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            df = new SimpleDateFormat("yyyy-MM-dd");
            map.put("stTime", df.format(stTime)+" 00:00:00");
            map.put("enTime", df.format(enTime)+" 23:59:59");
            String peopleSurplusPercent = "";
            String peopleUsefulPercent = "";
            List<JqTimelyParkingWidget> JqTimelyList = jqTimelyParkingdao.getJqTimelyBetween(map);
            /*不用线程了List<JqTimelyParkingWidget> JqTimelyList = new ArrayList<>();
            //获取区间内所有日期list,用于后面多线程循环读取数据
            List<String> subsectionDateList = DateUtil.getSubsectionDateList(df.format(stTime), df.format(enTime));

            List<Future<List<JqTimelyParkingWidget>>> futureList = new ArrayList<>();
            //使用多线程去拉取每天的数据.
            ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
            for (String dateStr : subsectionDateList) {
                if(es.isShutdown()){
                    es = ExecutorServiceUtils.getFixedThreadPool();
                }
                futureList.add(es.submit(new JqTimelyTask(vcode, dateStr)));
            }

            for (Future<List<JqTimelyParkingWidget>> listFuture : futureList) {
                try {
                    JqTimelyList.addAll(listFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            es.shutdown();*/
            /*while(!es.isTerminated()) {
                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

            JqTimely_TimeOnTime_TendWidget jqParkingMonth = new JqTimely_TimeOnTime_TendWidget();
            for (JqTimelyParkingWidget parkingListBetween : JqTimelyList) {
                if ("".equals(parkingListBetween) || parkingListBetween == null) {
                    peopleSurplusPercent = "0";
                    peopleUsefulPercent = "0";
                } else {
                    peopleSurplusPercent = parkingListBetween.getSurplusPaking();
                    peopleUsefulPercent = parkingListBetween.getParkingUseFul();
                }
                jqParkingMonth.setParkingSurplusPercent(peopleSurplusPercent);
                jqParkingMonth.setParkingUsefulPercent(peopleUsefulPercent);
//                JqTimelyBetween.add(jqParkingMonth);
            }
            RedisCache.putHash(redisTemplate, k, hk, jqParkingMonth);
            obj = jqParkingMonth;
        }


        return obj;
    }

    /**
     * 实时停车场所占百分比 趋势图
     *
     * @param time  时间
     * @param type  时间类型
     * @param vcode 景区编码
     * @return
     * @update zf 20170822
     */
    public Object findCountParkingTrend(String time, String type, String vcode) {
        String parkName="";

        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_PARKINGWIDGET + "getCountParkingTrend:findCountParkingTrend:";
        //hashkey
        String hk = type + time + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<JqTimely_TimeOnTime_TendWidget> JqTimelyTrend = new LinkedList<JqTimely_TimeOnTime_TendWidget>();
            List<JqTimely_TimeOnTime_TendWidget> JqTimelyTrend2 = new LinkedList<JqTimely_TimeOnTime_TendWidget>();
            Map map = new HashMap<>();
            map.put("vcode", vcode);
            String peopleSurplusPercent = "";
            String peopleUsefulPercent = "";
            String peopleSurplusPercentNew = "";
            String peopleUsefulPercentNew = "";
            if (!"".equals(type) && type.equals("day")) {
                Date date = null;
                DateFormat df = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                df = new SimpleDateFormat("yyyy-MM-dd");
                map.put("date", df.format(date));
                map.put("startTime",df.format(date)+" 00:00:00");
                map.put("endTime",df.format(date)+" 23:59:59");
                List<JqTimelyParkingWidget> JqTimelyListTrend = jqTimelyParkingdao.getJqTimelyByDay(map);
                if (JqTimelyListTrend != null || JqTimelyListTrend.size() > 1) {
                    for (JqTimelyParkingWidget parkingListTrend : JqTimelyListTrend) {
                        JqTimely_TimeOnTime_TendWidget jqParking = new JqTimely_TimeOnTime_TendWidget();
                        peopleSurplusPercent = Double.valueOf(parkingListTrend.getSurplusPaking()) / Double.valueOf(parkingListTrend.getParkingTotal()) * 100.0D + "";
                        peopleUsefulPercent = Double.valueOf(parkingListTrend.getParkingUseFul()) / Double.valueOf(parkingListTrend.getParkingTotal()) * 100.0D + "";

                        if (peopleSurplusPercent.equals(0) || peopleSurplusPercent.equals("0.0")) {
                            peopleSurplusPercentNew = "0";
                        } else {
                            peopleSurplusPercentNew = PerCentUtil.percent(peopleSurplusPercent);
                        }
                        if (peopleUsefulPercent.equals(0) || peopleUsefulPercent.equals("0.0")) {
                            peopleUsefulPercentNew = "0";
                        } else {
                            peopleUsefulPercentNew = PerCentUtil.percent(peopleUsefulPercent);
                        }
                        jqParking.setParkingSurplusPercent(peopleSurplusPercentNew);
                        jqParking.setParkingUsefulPercent(peopleUsefulPercentNew);
                        jqParking.setChoiceTime(parkingListTrend.getChoiceTime());
                        jqParking.setName(parkingListTrend.getName());
                        JqTimelyTrend.add(jqParking);
                    }
                }
                JqTimelyTrend2=JqTimelyTrend;
            }
            if (!"".equals(type) && type.equals("month")) {
                Date date = null;
                DateFormat df = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                try {
                    date = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                df = new SimpleDateFormat("yyyy-MM");
                map.put("date", df.format(date));
                map.put("startTime", df.format(date)+"-01 00:00:00");
                String nowM = DateTools.getCurrentMonth();
                if(nowM.equals(time)) {
                    String now = DateTools.getCurrentDate();
                    map.put("endTime", now+" 23:59:59");
                } else {
                    map.put("endTime", df.format(date) + "-31 23:59:59");
                }
                List<JqTimelyParkingWidget> JqTimelyListTrend1 = jqTimelyParkingdao.getJqTimelyByMonth(map);
                /*//线程调用 此处不用线程了
                List<JqTimelyParkingWidget> JqTimelyListTrend1 = this.callGetJqTimelyByMonth(vcode, df.format(date));*/
                if (JqTimelyListTrend1 != null || JqTimelyListTrend1.size() > 1) {
                    for (JqTimelyParkingWidget parkingListTrend1 : JqTimelyListTrend1) {
                        JqTimely_TimeOnTime_TendWidget jqParking1 = new JqTimely_TimeOnTime_TendWidget();

                        if(null!=parkingListTrend1.getParkingTotal() && !"0".equals(parkingListTrend1.getParkingTotal())){
                            peopleSurplusPercent = Double.valueOf(parkingListTrend1.getSurplusPaking()) / Double.valueOf(parkingListTrend1.getParkingTotal()) * 100.0D + "";
                            peopleUsefulPercent = Double.valueOf(parkingListTrend1.getParkingUseFul()) / Double.valueOf(parkingListTrend1.getParkingTotal()) * 100.0D + "";
                        }else{
                            peopleSurplusPercent = "0.0";
                            peopleUsefulPercent  = "0.0";
                        }
                        if (peopleSurplusPercent.equals(0) || peopleSurplusPercent.equals("0.0")) {
                            peopleSurplusPercentNew = "0";
                        } else {
                            peopleSurplusPercentNew = PerCentUtil.percent(peopleSurplusPercent);
                        }
                        if (peopleUsefulPercent.equals(0) || peopleUsefulPercent.equals("0.0")) {
                            peopleUsefulPercentNew = "0";
                        } else {
                            peopleUsefulPercentNew = PerCentUtil.percent(peopleUsefulPercent);
                        }
                        jqParking1.setParkingSurplusPercent(peopleSurplusPercentNew);
                        jqParking1.setParkingUsefulPercent(peopleUsefulPercentNew);
                        jqParking1.setChoiceTime(parkingListTrend1.getChoiceTime());
                        jqParking1.setName(parkingListTrend1.getName());
                        JqTimelyTrend.add(jqParking1);
                        parkName=parkingListTrend1.getName();
                    }
                }
                //填充map
                Map<String,JqTimely_TimeOnTime_TendWidget> map1 = new HashMap<>();
                JqTimelyTrend.forEach(item -> map1.put(item.getChoiceTime(),item) );

//                填充每月天数  @update lyl 20171106
                Date parse = null;
                try {
                    parse = DateUtil.MONTH_SDF.parse(df.format(date));
                    List<String> dayReport = DateUtil.getNowDayReport(parse, time);

                    for (int i=1;i<=dayReport.size();i++) {
                        String day=df.format(date);
                        if(i<10){
                            day += "-0" + i;
                        }else {
                            day += "-" + i;
                        }
                        //循环匹配日期，没获取到就给此日期填充0
                        if (map1.get(day)!=null){
                            JqTimelyTrend2.add(map1.get(day));
                        }else {
                            JqTimely_TimeOnTime_TendWidget jq1 = new JqTimely_TimeOnTime_TendWidget();
                            jq1.setParkingSurplusPercent("0");
                            jq1.setParkingUsefulPercent("0");
                            jq1.setChoiceTime(day);
                            jq1.setName(parkName);
                            JqTimelyTrend2.add(jq1);
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            if (!"".equals(type) && type.equals("year")) {
                Date date = null;
                DateFormat df = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                try {
                    date = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                df = new SimpleDateFormat("yyyy");
                map.put("date", df.format(date));
                map.put("startTime", df.format(date)+"-01-01 00:00:00");
                String nowY = DateTools.getCurrentYear();
                if(nowY.equals(time)) {
                    String now = DateTools.getCurrentDate();
                    map.put("endTime", now+" 23:59:59");
                } else {
                    map.put("endTime", df.format(date)+"-12-31 23:59:59");
                }
                List<JqTimelyParkingWidget> JqTimelyListTrend2 = jqTimelyParkingdao.getJqTimelyByYear(map);
                /*//线程调用 此处不用线程了
                List<JqTimelyParkingWidget> JqTimelyListTrend2 = this.callGetJqTimelyByYear(vcode, df.format(date));*/
                if (JqTimelyListTrend2 != null || JqTimelyListTrend2.size() > 1) {
                    for (JqTimelyParkingWidget parkingListTrend2 : JqTimelyListTrend2) {
                        JqTimely_TimeOnTime_TendWidget jqParking2 = new JqTimely_TimeOnTime_TendWidget();
                        if(null!=parkingListTrend2.getParkingTotal() && !"0".equals(parkingListTrend2.getParkingTotal())){
                            peopleSurplusPercent = Double.valueOf(parkingListTrend2.getSurplusPaking()) / Double.valueOf(parkingListTrend2.getParkingTotal()) * 100.0D + "";
                            peopleUsefulPercent = Double.valueOf(parkingListTrend2.getParkingUseFul()) / Double.valueOf(parkingListTrend2.getParkingTotal()) * 100.0D + "";
                        }else{
                            peopleSurplusPercent="0.0";
                            peopleUsefulPercent="0.0";
                        }
                        if (peopleSurplusPercent.equals(0) || peopleSurplusPercent.equals("0.0")) {
                            peopleSurplusPercentNew = "0";
                        } else {
                            peopleSurplusPercentNew = PerCentUtil.percent(peopleSurplusPercent);
                        }
                        if (peopleUsefulPercent.equals(0) || peopleUsefulPercent.equals("0.0")) {
                            peopleUsefulPercentNew = "0";
                        } else {
                            peopleUsefulPercentNew = PerCentUtil.percent(peopleUsefulPercent);
                        }
                        jqParking2.setParkingSurplusPercent(peopleSurplusPercentNew);
                        jqParking2.setParkingUsefulPercent(peopleUsefulPercentNew);
                        jqParking2.setChoiceTime(parkingListTrend2.getChoiceTime());
                        jqParking2.setName(parkingListTrend2.getName());
                        JqTimelyTrend.add(jqParking2);
                        parkName=parkingListTrend2.getName();
                    }
                }


                //填充map
                Map<String,JqTimely_TimeOnTime_TendWidget> map1 = new HashMap<>();
                JqTimelyTrend.forEach(item -> map1.put(item.getChoiceTime(),item) );

                //填充12个月 @update lyl 20171106

                int y = 12;
                if(nowY.equals(time)) {
                    y = Integer.parseInt(DateTools.getCurrentMonths());
                } else {
                    y = 12;
                }
                for (int i = 1; i <= y; i++) {
                    String day = df.format(date);
                    if (i < 10) {
                        day += "-0" + i;
                    } else {
                        day += "-" + i;
                    }
                    //循环匹配日期，没获取到就给此日期填充0
                    if (map1.get(day) != null) {
                        JqTimelyTrend2.add(map1.get(day));
                    } else {
                        JqTimely_TimeOnTime_TendWidget jq1 = new JqTimely_TimeOnTime_TendWidget();
                        jq1.setParkingSurplusPercent("0");
                        jq1.setParkingUsefulPercent("0");
                        jq1.setChoiceTime(day);
                        jq1.setName(parkName);
                        JqTimelyTrend2.add(jq1);
                    }
                }

            }
            RedisCache.putHash(redisTemplate, k, hk, JqTimelyTrend2);
            obj = JqTimelyTrend2;
        }


        return obj;
    }

    /**
     * 2017-08-17 zf
     * 组装停车场结算表  年 分组车牌
     *
     * @param vcode
     * @param year  年份
     * @return
     *//*
    private List<JqTimelyParkingWidget> callGetJqTimelyByYear(String vcode, String year) {
        String md5key = DigestUtils.md5Hex(vcode);
        List<JqTimelyParkingWidget> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_PARKINGWIDGET + "year:";
        String hk = "year" + "|" + year + "|" + md5key;

        //读取缓存
        list = (List<JqTimelyParkingWidget>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        list = new ArrayList<>();
        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        for (String month : months) {
            List<JqTimelyParkingWidget> ticketTypeByMonth = callGetJqTimelyByMonth(vcode, year + "-" + month);
            list.addAll(ticketTypeByMonth);
        }

        list= groupByData(list);
        //写入缓存
        if (list.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, list);
        }

        return list != null ? list : new ArrayList<>();

    }

    *//**
     * 2017-08-17 zf
     * 组装停车场结算表  月 分组车牌
     *
     * @param vcode
     * @param month 月份
     * @return
     *//*
    private List<JqTimelyParkingWidget> callGetJqTimelyByMonth(String vcode, String month) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<JqTimelyParkingWidget> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_PARKINGWIDGET + "month:";
        String hk = "month" + "|" + month + "|" + md5key;

        //读取缓存
        list = (List<JqTimelyParkingWidget>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        list = new ArrayList<>();
        try {
            Date parse = DateUtil.MONTH_SDF.parse(month);
            //得到此月份的所有天
            List<String> dayReport = DateUtil.getDayReport(parse);
            List<Future<List<JqTimelyParkingWidget>>> futureList = new ArrayList<>();
            //使用多线程去拉取每天的数据.
            ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
            for (String dateStr : dayReport) {
                if(es.isShutdown()){
                    es = ExecutorServiceUtils.getFixedThreadPool();
                }
                futureList.add(es.submit(new JqTimelyTask(vcode, dateStr)));
            }

            for (Future<List<JqTimelyParkingWidget>> listFuture : futureList) {
                try {
                    list.addAll(listFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            es.shutdown();
            *//*while(!es.isTerminated()) {
                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*//*
        } catch (ParseException e) {
            e.printStackTrace();
        }


        list= groupByData(list);

        //写入缓存
        if (list.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, list);
        }

        return list != null ? list : new ArrayList<>();
    }

    *//***
     * 组装 停车长数据
     * @param source
     * @return
     *//*
    public List<JqTimelyParkingWidget> groupByData(List<JqTimelyParkingWidget> source) {
//        String type;
        String name;
        String parkingUseFul;
        String surplusPaking;
        String choiceTime;
        List<Map> listmap = new ArrayList<>();
        for (JqTimelyParkingWidget clTypeVO : source) {
            Map<String, Object> numsMap = new HashMap<>();
            name = clTypeVO.getName() != null ? clTypeVO.getName() : "";
            choiceTime = clTypeVO.getChoiceTime() != null ? clTypeVO.getChoiceTime() : "";
            parkingUseFul = clTypeVO.getParkingUseFul() !=null ? clTypeVO.getParkingUseFul():"0";
            surplusPaking = clTypeVO.getSurplusPaking() !=null ? clTypeVO.getSurplusPaking() : "0";
            numsMap.put("name", name);
            numsMap.put("choiceTime", choiceTime);
            long nums_parkingUseFul = Long.parseLong(parkingUseFul);
            //获取历史数据 累计
            long oldNums_parkingUseFul = numsMap.get(parkingUseFul) != null ? Long.parseLong((String) numsMap.get(parkingUseFul)) : 0;
            numsMap.put("parkingUseFul", nums_parkingUseFul + oldNums_parkingUseFul);


            long nums_surplusPaking = Long.parseLong(surplusPaking);
            //获取历史数据 累计
            long oldNums_surplusPaking = numsMap.get(surplusPaking) != null ? Long.parseLong((String) numsMap.get(surplusPaking)) : 0;
            numsMap.put("surplusPaking", nums_surplusPaking + oldNums_surplusPaking);
            listmap.add(numsMap);
        }

        List<JqTimelyParkingWidget> resultList = new ArrayList<>();
        JqTimelyParkingWidget clTypeVO;
        for (Map map : listmap) {
            clTypeVO = new JqTimelyParkingWidget(map.get("surplusPaking").toString(),
                    map.get("parkingUseFul").toString(),
                    map.get("name").toString(),map.get("choiceTime").toString());
            resultList.add(clTypeVO);
        }

        return resultList;
    }

    *//***
     * 线程查询基础数据   天 停车场  zf
     * 2017-08-17 zf
     * @param vcode
     * @param day
     * @return
     *//*
    public List<JqTimelyParkingWidget> callGetJqTimelyByDay(String vcode, String day) {
        String md5key = DigestUtils.md5Hex(vcode);
        List<JqTimelyParkingWidget> list;

        //redis 缓存key
        String h = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_PARKINGWIDGET + "day:";
        String hk = "day" + "|" + day + "|" + md5key;

        //读取缓存
        list = (List<JqTimelyParkingWidget>) RedisCache.getHash(redisTemplate, h, hk);
        if (list != null && list.size() > 0) {
            return list;
        }
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("startTime", day + " 00:00:00");
        map.put("endTime", day + " 23:59:59");
        //读取db
        list = jqTimelyParkingdao.callGetJqTimelyByDay(map);

        //写入缓存
        if (list.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, list);
        }else{
            JqTimelyParkingWidget po=new JqTimelyParkingWidget();

            po.setChoiceTime(day);
            po.setParkingSurplusPercent("0");
            po.setParkingUsefulPercent("0");
            po.setName("");
            list.add(po);

        }

        return list != null ? list : new ArrayList<>();
    }

    *//**
     * 多线程处理按天获取数据任务.停车场总数量
     *//*
    private class JqTimelyTask implements Callable<List<JqTimelyParkingWidget>> {

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

        public JqTimelyTask(String vcode, String date) {
            this.date = date;
            this.vcode = vcode;
        }

        public JqTimelyTask() {
        }

        *//**
         * @return 返回停车场结算表  天
         * @throws Exception if unable to compute a result
         *//*
        @Override
        public List<JqTimelyParkingWidget> call() throws Exception {
            System.out.println(String.format("线程:%s 正在处理[停车场车位使用趋势]数据 vcode:%s,date:%s", Thread.currentThread().getName(), vcode, date));
            List<JqTimelyParkingWidget> list = callGetJqTimelyByDay(vcode, date);
            return list;
        }
    }*/

}
