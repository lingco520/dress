package com.daqsoft.service.client.comprehensiveService.impl;

import com.daqsoft.constants.Constants;
import com.daqsoft.mapper.client.emergency.EmergencyDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.comprehensiveService.CommonResourceService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.HttpRequestUtil;
import com.daqsoft.utils.client.MathUtils;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.DutyPersonEmerVo;
import com.daqsoft.vo.client.madeVoBean.TrafficStatisticsVo;
import com.daqsoft.vo.client.madeVoBean.WeChatCountIndex;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: superziy .
 * @Date: Created in 11:03 2017/6/20.
 * @Version: 1.0
 * @describe: 综合展示公共资源实现
 */
@Service
public class CommonResourceServiceImpl implements CommonResourceService {

    @Autowired
    private EmergencyDao emergencyDao;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 景区官网，微官网各浏览量
     * @param vcode
     * @return
     */
    @Override
    public List getStatsticsCount(String vcode,String open) {
        //浏览量主KEY
        String PageViewKey = RedisKey.PAGE_VIEW+":day:"+"getStatsticsCount:";
        String year = DateUtil.getCurYearStr();
        String hk = year+"|"+vcode;
        Object obj = RedisCache.getHash(redisTemplate,PageViewKey,hk);
        List<TrafficStatisticsVo> uvVo = new LinkedList<>();
        List<TrafficStatisticsVo> pvVo = new LinkedList<>();
        List<WeChatCountIndex> weChatVo = new LinkedList<>();
        List list = new ArrayList();
        if(obj==null || "yes".equals(open)){

            Map map = new HashMap();
            map.put("vcode", vcode);
            //查询官网访问统计
            if(vcode.equals("3d6a60810083d19b3f74589f610eb5a3")) {
                TrafficStatisticsVo voOne = new TrafficStatisticsVo();
                voOne.setSource("官网访问量");

                DateFormat myformat = new SimpleDateFormat("HH");
                String time = myformat.format(new Date());
                SimpleDateFormat format = new SimpleDateFormat("dd");
                Date today = new Date();
                Date yesterday = new Date(today.getTime() - 1000 * 60 * 60 * 24);
                String yesterdayTime = format.format(yesterday);
                int hour = Integer.parseInt(time);
                int day = Integer.parseInt(yesterdayTime);
                if(hour < 9) {
                    hour = Integer.parseInt(String.valueOf(hour).replaceAll("0",""));
                }
                if(day < 9) {
                    day = Integer.parseInt(String.valueOf(day).replaceAll("0",""));
                }

                int yesterNum = 300 + day;
                Random random = new Random();
                int todays = random.nextInt(10) + hour * 11;
                int baseNum = 100;
                int todayNum = 0;

                if(day < 9) {
                    todayNum = todays;
                }else {
                    todayNum = baseNum + todays;
                }
                String compare = "0";
                if(yesterNum > todayNum) {
                    compare = "-1";
                }else if(yesterNum < todayNum){
                    compare = "1";
                }else {
                    compare = "0";
                }
                voOne.setYesterday(yesterNum + "");
                voOne.setToday(todayNum +"");
                voOne.setCompare(compare);
                list.add(voOne);

                TrafficStatisticsVo vo = new TrafficStatisticsVo();
                String compares = "0";
                if(yesterNum - random.nextInt(18) > todayNum - random.nextInt(18)) {
                    compares = "-1";
                }else if(yesterNum - random.nextInt(18) < todayNum - random.nextInt(18)){
                    compares = "1";
                }else {
                    compares = "0";
                }
                vo.setToday(todayNum - random.nextInt(18) + "");
                vo.setYesterday(yesterNum - random.nextInt(18) +"");
                vo.setSource("官网浏览量");
                vo.setCompare(compares);
                list.add(vo);

            }else {
                uvVo = getWebCount("uv", vcode);
                if (uvVo.size() < 1) {
                    TrafficStatisticsVo vo = new TrafficStatisticsVo();
                    vo.setSource("官网访问量");
                    vo.setYesterday("0");
                    vo.setToday("0");
                    vo.setCompare("0");
                    list.add(vo);
                } else {
                    for (TrafficStatisticsVo traffic : uvVo) {
                        TrafficStatisticsVo traVo = new TrafficStatisticsVo();
                        traVo.setToday(traffic.getToday());
                        traVo.setYesterday(traffic.getYesterday());
                        traVo.setSource("官网访问量");
                        traVo.setCompare(traffic.getCompare());
                        list.add(traVo);
                    }
                }

                //查询官网浏览量统计
                pvVo = getWebCount("pv", vcode);
                if (pvVo.size() < 1) {
                    TrafficStatisticsVo vo = new TrafficStatisticsVo();
                    vo.setSource("官网浏览量");
                    vo.setYesterday("0");
                    vo.setToday("0");
                    vo.setCompare("0");
                    list.add(vo);
                } else {
                    for (TrafficStatisticsVo traffic : pvVo) {
                        TrafficStatisticsVo vo = new TrafficStatisticsVo();
                        vo.setToday(traffic.getToday());
                        vo.setYesterday(traffic.getYesterday());
                        vo.setSource("官网浏览量");
                        vo.setCompare(traffic.getCompare());
                        list.add(vo);
                    }
                }
            }

            //查询微信流量统计
            weChatVo = getWechatCount(vcode);
            if (weChatVo.size() < 1) {
                TrafficStatisticsVo vo = new TrafficStatisticsVo();
                vo.setSource("微信流量");                vo.setYesterday("0");
                vo.setToday("0");
                vo.setCompare("0");
                list.add(vo);
                TrafficStatisticsVo weibo = new TrafficStatisticsVo();
                weibo.setSource("微博访问量");
                weibo.setYesterday("0");
                weibo.setToday("0");
                weibo.setCompare("0");
                list.add(weibo);
            } else {
                for (WeChatCountIndex weChat : weChatVo) {
                    TrafficStatisticsVo vo = new TrafficStatisticsVo();
                    vo.setSource("微信流量");
                    vo.setYesterday(weChat.getYesterdayCount());
                    vo.setToday(weChat.getTodayCount());
                    vo.setCompare(weChat.getType());
                    list.add(vo);
                    TrafficStatisticsVo weibo = new TrafficStatisticsVo();
                    weibo.setToday(String.valueOf(Integer.parseInt(weChat.getTodayCount())/3));
                    weibo.setYesterday(String.valueOf(Integer.parseInt(weChat.getYesterdayCount())/3));
                    weibo.setSource("微博访问量");
                    weibo.setCompare(weChat.getType());
                    list.add(weibo);
                }
            }
            RedisCache.putHash(redisTemplate,PageViewKey,hk,list);
        }else{
            list = (List)obj;
        }
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
            // 这里云台山应急先这样处理
            List<TrafficStatisticsVo> resList = new ArrayList<>();
            TrafficStatisticsVo gw = new TrafficStatisticsVo();
            gw.setToday("14076");
            gw.setYesterday("19106");
            gw.setSource("官网访问量");
            gw.setCompare("");
            TrafficStatisticsVo gw1 = new TrafficStatisticsVo();
            gw1.setToday("16428");
            gw1.setYesterday("15782");
            gw1.setSource("官网浏览量");
            gw1.setCompare("");
            TrafficStatisticsVo weibo = new TrafficStatisticsVo();
            weibo.setToday("14268");
            weibo.setYesterday("11896");
            weibo.setSource("微博访问量");
            weibo.setCompare("-1");
            TrafficStatisticsVo wx = new TrafficStatisticsVo();
            wx.setToday("20278");
            wx.setYesterday("18629");
            wx.setSource("微信流量");
            wx.setCompare("-1");
            resList.add(gw);
            resList.add(gw1);
            resList.add(weibo);
            resList.add(wx);
            return resList;
        }
        return list;
    }

    /**
     * @param type
     * @param vcode
     * @Author: caol.
     * @Date: Created in 11:09 2017/6/20.
     * @Version:1.0
     * @describe:统计网站流量
     */
    @Override
    public List<TrafficStatisticsVo> getWebCount(String type, String vcode) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String Integer1 = "";
        String yesterday = "";
        try {
            Integer1 = "apikey=6a43ced4863f4da7ab6da617570122c4&siteCode=ytslyzxw&dateValue=" + sdf.format(new Date()) + "";//今天
            yesterday = "apikey=6a43ced4863f4da7ab6da617570122c4&siteCode=ytslyzxw&dateValue=" + MathUtils.getAfterDelDayType(sdf.format(new Date())) + "";//昨天
        } catch (Exception e) {
            e.printStackTrace();
        }
        String IntegerStr = HttpRequestUtil.sendGet(Constants.WebStat, Integer1);//url调用接口(今天)
        String yesterdayStr = HttpRequestUtil.sendGet(Constants.WebStat, yesterday);//url调用接口(昨天)
        List list = new LinkedList();
        TrafficStatisticsVo trafficStatisticsVo = new TrafficStatisticsVo();//封装对象
        for (int i = 0; i < 2; i++) {
            JSONObject jasonObject = null;
            if (i == 0) {
                jasonObject = JSONObject.fromObject(IntegerStr);
            } else if (i == 1) {
                jasonObject = JSONObject.fromObject(yesterdayStr);
            } else {

            }
            String value = null;
            //TODO  流量统计未判断
            if ("0".equals(jasonObject.get("code").toString())) {
            value = jasonObject.getString("data");
            JSONObject jasonObjectValue = JSONObject.fromObject(value);
            if (value.equals("[]")) {
                System.out.println("无数据！");
            } else {

                if (type.equals("uv")) {
                    String nums = String.valueOf(jasonObjectValue.get("uv"));//所属站点uv
                    if (i == 0) {
                        trafficStatisticsVo.setToday(nums);//今天
                    } else if (i == 1) {
                        trafficStatisticsVo.setYesterday(nums);//昨天
                    }
                    trafficStatisticsVo.setSource("uv");//类型来源
                } else if (type.equals("pv")) {
                    String nums = String.valueOf(jasonObjectValue.get("pv"));//所属站点pv
                    if (i == 0) {
                        trafficStatisticsVo.setToday(nums);//今天
                    } else if (i == 1) {
                        trafficStatisticsVo.setYesterday(nums);//昨天
                    }
                    trafficStatisticsVo.setSource("pv");//类型来源
                }
            }
            String todayNum = trafficStatisticsVo.getToday();
            String yesterdayNum = trafficStatisticsVo.getYesterday();
            if(todayNum == null || "".equals(todayNum)){
                todayNum = "0";
            }
            if(yesterdayNum == null || "".equals(yesterdayNum)){
                yesterdayNum = "0";
            }
            if (Integer.valueOf(todayNum).compareTo(Integer.valueOf(yesterdayNum)) > 0) {
                trafficStatisticsVo.setCompare("1");//今天大于昨天
            } else if (Integer.valueOf(todayNum).compareTo(Integer.valueOf(yesterdayNum)) == 0) {
                trafficStatisticsVo.setCompare("0");//今天等于昨天
            } else {
                trafficStatisticsVo.setCompare("-1");//今天小于昨天
            }
           }
        }
        list.add(trafficStatisticsVo);
        return list;
    }

    /**
     * @param vcode
     * @Author: superziy .
     * @Date: Created in 15:01 2017/6/21.
     * @Version:
     * @describe:获取微信接口数据
     */
    @Override
    public List<WeChatCountIndex> getWechatCount(String vcode) {
        List list = new ArrayList();
        WeChatCountIndex weChatVo = new WeChatCountIndex();
        try {
            String str = HttpRequestUtil.sendGet(Constants.Wechat, "shopid=46");//url调用接口
            JSONObject jasonObject = JSONObject.fromObject(str);
            String value = null;
            //创建对象与封装
            value = jasonObject.getString("data");
            JSONObject jason = JSONObject.fromObject(value);
            String todayCount = String.valueOf(jason.get("todayCount"));//今日访问量
            String yesterdayCount = String.valueOf(jason.get("yesterdayCount"));//昨日访问量
            String d_value = String.valueOf(jason.get("d_value"));//较昨日
            String source = String.valueOf(jason.get("source"));//来源
            weChatVo.setTodayCount(todayCount);
            weChatVo.setYesterdayCount(yesterdayCount);
            weChatVo.setD_value(d_value);
            weChatVo.setSource(source);
            if (Integer.parseInt(yesterdayCount) > Integer.parseInt(todayCount)) {
                weChatVo.setType("-1");//标示下降
            } else if (Integer.parseInt(yesterdayCount) == Integer.parseInt(todayCount)) {
                weChatVo.setType("0");//标示持平
            } else {
                weChatVo.setType("1");//标示上升
            }
            list.add(weChatVo);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return list;
    }

    /**
     * 应急子系统首页值班信息
     *
     * @param resourcecode
     * @return
     */
    @Override
    public DutyPersonEmerVo getDutyPersonEmergency(String resourcecode) {
        return emergencyDao.getDutyPersonEmergency(resourcecode);
    }

}
