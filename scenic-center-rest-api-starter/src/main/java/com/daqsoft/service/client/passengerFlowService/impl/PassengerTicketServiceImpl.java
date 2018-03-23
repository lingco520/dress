package com.daqsoft.service.client.passengerFlowService.impl;

import com.daqsoft.mapper.client.otadata.OtaDataDao;
import com.daqsoft.mapper.client.realpeople.PassengerFlowDao;
import com.daqsoft.mapper.client.realpeople.RealPeopleDao;
import com.daqsoft.mapper.client.scenicspotinfo.ScenicSpotInfoDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.passengerFlowService.PassengerTicketService;
import com.daqsoft.service.client.ticketanalys.TicketService;
import com.daqsoft.utils.client.*;
import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.RealPeople;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.daqsoft.utils.client.RandomNumberUtils.RandomNumber;


/**
 * @Author: lyl .
 * @Date: Created in 2017/6/18.
 * @Version: V3.0.0.
 * @describe:客流与票务逻辑实现
 */


@Service
public class PassengerTicketServiceImpl implements PassengerTicketService {


    @Autowired
    private PassengerFlowDao passengerFlowDao;
    @Autowired
    private OtaDataDao otaDataDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ScenicSpotInfoDao scenicSpotInfoDao;
    @Autowired
    private RealPeopleDao realPeopleDao;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private PassengerTicketService passengerTicketService;


    private static String collectionNameTime = "big_realpeopleNow";
    private static final String TABLE_NAME = "scenicSpots:";
    private static final String BIG_SPOTS_NAME = "spots:";//景点
    private static final String BIG_TEAM_NAME = "team:";//省团队



    /**
     * @param vcode
     * @param type
     * @Author: superziy .  / tanggm  今日查询的是数据库，去年今日和前年今日查redis，数据对不上，去年和前年redis数据不准确，改为查询数据库，这个数据不大，比较快
     * @Date: Created in 17:03 2017/6/21.
     * @Version:1.0
     * @describe:主页实时人数
     */
    @Override
    public SumPeopleIndexVo sumPeopleIndexRedis(String oldVcode, String vcode, String type) {
        //创建has接受字符串
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());//今日时间(参数）
        String yesterday = DateTools.getbeforeDay(today);
        String LastYearTime = MathUtils.getAfterYear(today);//去年今日时
        String beforelastTime = MathUtils.getAfterYear(LastYearTime);//前年今日时间
        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = formatdate.format(new Date());//获取当前的时间精确到小时号查询实时人数
        String startTime = DateUtil.getCurDateStr()+" 00:00:00";
        Map map = new HashMap();
        map.put("vcode", oldVcode);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        //今日人数读缓存
        String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
        String hk = today + "|" + oldVcode;

        String yhk = yesterday + "|" + oldVcode;
        Object todayNum = RedisCache.getHash(redisTemplate,datKey, hk);
        Object yesterdayNum = RedisCache.getHash(redisTemplate,datKey, yhk);
        RealPeopleIndexSumVo realPeopleIndexSumVo = new RealPeopleIndexSumVo();
        Integer atDay = 0;
        Integer yesDay = 0;
        if(todayNum!=null){
            Map tmap = (Map)todayNum;
            realPeopleIndexSumVo.setSumPeople(tmap.get("num").toString());
            atDay = Integer.parseInt(tmap.get("num").toString());
        }else{
            realPeopleIndexSumVo = passengerFlowDao.getPeopleRealTime(map);
            if(realPeopleIndexSumVo != null){
                atDay = Integer.valueOf(realPeopleIndexSumVo.getSumPeople());
            }
        }
        if(yesterdayNum!=null){
            Map ymap = (Map)yesterdayNum;
            yesDay = Integer.parseInt(ymap.get("num").toString());
        }else{
            Map yesMap = new HashMap();
            yesMap.put("vcode", oldVcode);
            yesMap.put("startTime", yesterday + " 00:00:00");
            yesMap.put("endTime", yesterday + " 23:59:59");
            realPeopleIndexSumVo = passengerFlowDao.getPeopleRealTime(yesMap);
            if(realPeopleIndexSumVo != null){
                yesDay = Integer.valueOf(realPeopleIndexSumVo.getSumPeople());
            }
        }
        // 去年
        Integer lastYearDay = 0;
        Integer beforeYearDay = 0;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        String lastEndTime = formatdate.format(c.getTime());
        String lastStartTime = format.format(c.getTime())+" 00:00:00";
        map.put("startTime", lastStartTime);
        map.put("endTime", lastEndTime);
        RealPeopleIndexSumVo lastPeopleIndexSumVo = passengerFlowDao.getPeopleRealTime(map);
        if(lastPeopleIndexSumVo != null){
            lastYearDay = Integer.valueOf(lastPeopleIndexSumVo.getSumPeople());
        }else if (oldVcode.equals("a02dbca3d06409ba7fbf444e0565242d")){
            lastYearDay = 430;
        }
        // 前年
        c.add(Calendar.YEAR, -1);
        String beforeEndTime = formatdate.format(c.getTime());
        String beforeStartTime = format.format(c.getTime())+" 00:00:00";
        map.put("startTime", beforeStartTime);
        map.put("endTime", beforeEndTime);
        RealPeopleIndexSumVo beforePeopleIndexSumVo = passengerFlowDao.getPeopleRealTime(map);
        if(beforePeopleIndexSumVo != null){
            beforeYearDay = Integer.valueOf(beforePeopleIndexSumVo.getSumPeople());
        }else if (oldVcode.equals("a02dbca3d06409ba7fbf444e0565242d")){
            beforeYearDay = 453;
        }
        String lastYearOnYearType;//去年总数同比箭头类型
        String beforeYearOnYearType;//前年总数同比箭头类型
//       //今年今日比去年今日的同比
        // 需要对数据进行判断
        String yearOnYear = "0";
        if(atDay == null || atDay.compareTo(0) == 0){
            yearOnYear = "0";
        }else if(lastYearDay == null || lastYearDay.compareTo(0) == 0){
            yearOnYear = "-";
//        }else if (oldVcode.equals("a02dbca3d06409ba7fbf444e0565242d")){
//            yearOnYear = "4";
        }
        else{
            String yearOnYearGrowth = (int) (Double.valueOf(atDay) - Double.valueOf(lastYearDay)) / Double.valueOf(lastYearDay) * 100.0 + "";
            yearOnYear = MathUtils.subPercent(MathUtils.percent(yearOnYearGrowth));//百分比去整数,之后去负号
        }
        //今年今日比前年今日的同比
        String yearOnBeforeYear = "0";
        if(atDay == null || atDay.compareTo(0) == 0){
            yearOnBeforeYear = "0";
        }else if(beforeYearDay == null || beforeYearDay.compareTo(0) == 0){
            yearOnBeforeYear = "-";
//        }else if (oldVcode.equals("a02dbca3d06409ba7fbf444e0565242d")){
//            yearOnBeforeYear = "6";
        }
        else{
            String yearOnBeforeYearGrowth = (int) (Double.valueOf(atDay) - Double.valueOf(beforeYearDay)) / Double.valueOf(beforeYearDay) * 100.0 + "";
            yearOnBeforeYear = MathUtils.subPercent(MathUtils.percent(yearOnBeforeYearGrowth));//百分比去整数,之后去负号
        }

        //判断今日人数同比去年今日人数
        if (atDay > lastYearDay) {
            lastYearOnYearType = "1";//类型1为同比上升，2为下降，0 为平衡
        } else if (atDay < lastYearDay) {
            lastYearOnYearType = "2";
        } else {
            lastYearOnYearType = "0";
        }
        //判断今日人数同比前年今日人数
        if (atDay > beforeYearDay) {
            beforeYearOnYearType = "1";//类型1为同比上升，2为下降，0 为平衡
        } else if (atDay < beforeYearDay) {
            beforeYearOnYearType = "2";
        } else {
            beforeYearOnYearType = "0";
        }


        //放入实体类
        SumPeopleIndexVo sumPeople = new SumPeopleIndexVo();
        sumPeople.setTodayPeople(String.valueOf(atDay));//今日人数
        sumPeople.setYesterDayPeople(String.valueOf(yesDay));//昨天的人数
        //赛里木湖模拟数据
        if("28c8ee033e7286d791e9654929a08ecb".equals(oldVcode)){
            SimpleDateFormat formatY = new SimpleDateFormat("yyyy");
            String year = formatY.format(new Date());//今日时间(参数）
            String yKey = RedisKey.MOUDEL_PASSENGERFLOW+"SLMHONnOffLine:";
            String yHk = year + "|28c8ee033e7286d791e9654929a08ecb";
            Object obj= RedisCache.getHash(redisTemplate,yKey, yHk);
            Map maps = (Map) obj;
            String onlineYear = maps.get("onLine").toString();
            String offlineYear = maps.get("offLine").toString();
            sumPeople.setLastYearPeople(String.valueOf(onlineYear));//去年今日人数
            sumPeople.setBeforeYearPeople(String.valueOf(offlineYear));//前年今日人数
            sumPeople.setLastYearOnYear("12.53");//今日同比去年今日
            sumPeople.setBeforeYearOnYear("11.26");//今日同比前年今日
            sumPeople.setLastYearOnYearType("1");//今日同比前年今日显示类型
            sumPeople.setBeforeYearOnYearType("1");//今日同比前年今日显示类型
        }else {
            sumPeople.setLastYearPeople(String.valueOf(lastYearDay));//去年今日人数
            sumPeople.setBeforeYearPeople(String.valueOf(beforeYearDay));//前年今日人数
            sumPeople.setLastYearOnYear(yearOnYear);//今日同比去年今日
            sumPeople.setBeforeYearOnYear(yearOnBeforeYear);//今日同比前年今日
            sumPeople.setLastYearOnYearType(lastYearOnYearType);//今日同比前年今日显示类型
            sumPeople.setBeforeYearOnYearType(beforeYearOnYearType);//今日同比前年今日显示类型
        }
        sumPeople.setSource("人数对比");//人数对比类型

        return sumPeople;
    }


    @Override
    public SumPeopleIndexVo sumPeopleIndexDB(String vcode) {

        //调用统一接口，解释数据
        String todayUrl = "http://localhost:8090/scdatasvc/realPeople/getCountPeopleSum/" + vcode;
        String jsonStrToday = HttpRequestUtil.sendGet(todayUrl, null);
        JSONObject jsonToday = JSONObject.fromObject(jsonStrToday);
        JSONArray arrayToday = JSONArray.fromObject(jsonToday.get("datas"));
        List<SumPeopleIndexVo> listToday = JSONArray.toList(arrayToday, new SumPeopleIndexVo(), new JsonConfig());
        SumPeopleIndexVo sumPeople = new SumPeopleIndexVo();
        for (SumPeopleIndexVo sumPeopleIndexVo : listToday) {
            sumPeople.setTodayPeople(sumPeopleIndexVo.getTodayPeople());
            sumPeople.setLastYearPeople(sumPeopleIndexVo.getLastYearPeople());
            sumPeople.setBeforeYearPeople(sumPeopleIndexVo.getBeforeYearPeople());
            sumPeople.setLastYearOnYear(sumPeopleIndexVo.getLastYearOnYear());
            sumPeople.setBeforeYearOnYear(sumPeopleIndexVo.getBeforeYearOnYear());
            sumPeople.setLastYearOnYearType(sumPeopleIndexVo.getLastYearOnYearType());
            sumPeople.setBeforeYearOnYearType(sumPeopleIndexVo.getBeforeYearOnYearType());
        }
        return sumPeople;
    }

    @Override
    public SumPeopleIndexVo sumPeopleIndexRedisTicket(String oldVcode, String vcode) {
        //创建has接受字符串
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());//今日时间(参数）
        String LastYearTime = MathUtils.getAfterYear(today);//去年今日时
        String beforelastTime = MathUtils.getAfterYear(LastYearTime);//前年今日时间
        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = formatdate.format(new Date());//获取当前的时间精确到小时号查询实时人数
        String startTime = DateUtil.getCurDateStr()+" 00:00:00";
        Map map = new HashMap();
        map.put("vcode", oldVcode);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        //今日人数读缓存
//        TicketClTendVo ticketClTendToday = ticketService.getTicketCountAndPriceByDay(oldVcode, DateUtil.getCurDateStr());
        String vcodemdh = DigestUtils.md5Hex(vcode);
        SumPeopleIndexVo sumPeopleIndexRedis = passengerTicketService.sumPeopleIndexRedis(oldVcode, vcode, "day");
        Integer atDay = 0;
        Integer yesDay = 0;
        if(sumPeopleIndexRedis != null){
            atDay = Integer.parseInt(sumPeopleIndexRedis.getTodayPeople());
            yesDay = Integer.parseInt(sumPeopleIndexRedis.getYesterDayPeople());
        }

        // 去年
        Integer lastYearDay = 0;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        String lastYear = format.format(c.getTime());
        TicketClTendVo ticketClTendLast = ticketService.getTicketCountAndPriceByDay(oldVcode, lastYear);
        if(ticketClTendLast != null){
            lastYearDay = Integer.parseInt(ticketClTendLast.getTicket_num());
        }

        // 前年
        Integer beforeYearDay = 0;
        c.add(Calendar.YEAR, -1);
        String beforeYear = format.format(c.getTime());
        TicketClTendVo ticketClTendBefore = ticketService.getTicketCountAndPriceByDay(oldVcode, beforeYear);
        if(ticketClTendBefore != null){
            beforeYearDay = Integer.parseInt(ticketClTendBefore.getTicket_num());
        }

        String lastYearOnYearType;//去年总数同比箭头类型
        String beforeYearOnYearType;//前年总数同比箭头类型
        //今年今日比去年今日的同比
        // 需要对数据进行判断
        String yearOnYear = "0";
        if(atDay == null || atDay.compareTo(0) == 0){
            yearOnYear = "0";
        }else if(lastYearDay == null || lastYearDay.compareTo(0) == 0){
            yearOnYear = "-";
        }else{
            String yearOnYearGrowth = (int) (Double.valueOf(atDay) - Double.valueOf(lastYearDay)) / Double.valueOf(lastYearDay) * 100.0 + "";
            yearOnYear = MathUtils.subPercent(MathUtils.percent(yearOnYearGrowth));//百分比去整数,之后去负号
        }
        //今年今日比前年今日的同比
        String yearOnBeforeYear = "0";
        if(atDay == null || atDay.compareTo(0) == 0){
            yearOnBeforeYear = "0";
        }else if(beforeYearDay == null || beforeYearDay.compareTo(0) == 0){
            yearOnBeforeYear = "-";
        }else{
            String yearOnBeforeYearGrowth = (int) (Double.valueOf(atDay) - Double.valueOf(beforeYearDay)) / Double.valueOf(beforeYearDay) * 100.0 + "";
            yearOnBeforeYear = MathUtils.subPercent(MathUtils.percent(yearOnBeforeYearGrowth));//百分比去整数,之后去负号
        }

        //判断今日人数同比去年今日人数
        if (atDay > lastYearDay) {
            lastYearOnYearType = "1";//类型1为同比上升，2为下降，0 为平衡
        } else if (atDay < lastYearDay) {
            lastYearOnYearType = "2";
        } else {
            lastYearOnYearType = "0";
        }
        //判断今日人数同比前年今日人数
        if (atDay > beforeYearDay) {
            beforeYearOnYearType = "1";//类型1为同比上升，2为下降，0 为平衡
        } else if (atDay < beforeYearDay) {
            beforeYearOnYearType = "2";
        } else {
            beforeYearOnYearType = "0";
        }

        //放入实体类
        SumPeopleIndexVo sumPeople = new SumPeopleIndexVo();
        sumPeople.setTodayPeople(String.valueOf(atDay));//今日人数
        sumPeople.setYesterDayPeople(String.valueOf(yesDay));//今日人数

        sumPeople.setLastYearPeople(String.valueOf(lastYearDay));//去年今日人数
        sumPeople.setBeforeYearPeople(String.valueOf(beforeYearDay));//前年今日人数
        sumPeople.setLastYearOnYear(yearOnYear);//今日同比去年今日
        sumPeople.setBeforeYearOnYear(yearOnBeforeYear);//今日同比前年今日
        sumPeople.setLastYearOnYearType(lastYearOnYearType);//今日同比前年今日显示类型
        sumPeople.setBeforeYearOnYearType(beforeYearOnYearType);//今日同比前年今日显示类型

        sumPeople.setSource("人数对比");//人数对比类型

        return sumPeople;
    }

    /**
     * @param vcode
     * @Author: superziy .
     * @Date: Created in 17:04 2017/6/21.
     * @Version:
     * @describe:获取线上线下票务数据
     */
    @Override
    public TicketIndexOnAndOffVo ticketIndexOnAndOffRedis(String vcode, String mdfvcode) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());//今日时间(参数）
        String onlineKey = "scenicSpots:onLine:day:";//线上key
        String online = getRedis(onlineKey, today, mdfvcode);//获取线上数据
//        String onlines = getRealTimeTicket(onlineKey, mdfvcode, today, online);
        String lineKey = "scenicSpots:Line:day:";//线下key
        String offline = getRedis(lineKey, today, mdfvcode);//获取线下数据
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("startTime", today + " 00:00:00");
        map.put("endTime", today + " 23:59:59");
        // 如果缓存中没有，去数据库查询某一天的数据
        if("0".equals(online) || "".equals(online)){
            online = passengerFlowDao.getOnlineByDay(map);
        }
        if("0".equals(offline) || "".equals(offline)){
            offline = passengerFlowDao.getOfflineByDay(map);
        }
        // 云台山客户有特殊需求，线下比线上多10%，现在云台山线上没有数据，是研发模拟数据，这里如果是云台山，进行特殊处理
        // 云台山特殊处理
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
            Integer offd = Integer.valueOf(offline);
            BigDecimal onlineNum = BigDecimal.ZERO;
            // 云台山线上销售计算规则   线上=（线上+线下）*（%5+%4+%3+%2）  y:线下   x:线上
            // 即  x=(x+y)*0.14    x=0.14x+0.14y   0.86x=0.14y   x=0.16y
            onlineNum = new BigDecimal(offd).multiply(new BigDecimal(0.16)).setScale(0,BigDecimal.ROUND_HALF_UP);
            online = String.valueOf(onlineNum);
        }
        TicketIndexOnAndOffVo vo = new TicketIndexOnAndOffVo();
        vo.setOffLine(offline);
        vo.setOnLine(online);
        vo.setSource("线上线下数据当天");

        return vo;
    }

    /**
     * 获取线上线下票务数据(赛里木湖模拟)
     *
     * @param vcode
     * @param mdfvcode
     * @return
     */
    public Map ticketIndexOnAndOffRedisSLMH(String vcode, String mdfvcode) {
        Map dataList = new HashMap();

        SimpleDateFormat formatY = new SimpleDateFormat("yyyy");
        String year = formatY.format(new Date());//今日时间(参数）
        String onlineKeyYear = "scenicSpots:onLine:year:";//线上key
        String onlineYear = getRedis(onlineKeyYear, year, mdfvcode);//获取线上数据
        String lineKeyYear = "scenicSpots:Line:year:";//线下key
        String offlineYear = getRedis(lineKeyYear, year, mdfvcode);//获取线下数据
        TicketIndexOnAndOffVo voYear = new TicketIndexOnAndOffVo();
        voYear.setOffLine(offlineYear);
        voYear.setOnLine(onlineYear);
        voYear.setSource("线上线下数据累计");


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());//今日时间(参数）
        String onlineKey = "scenicSpots:onLine:day:";//线上key
        String online = getRedis(onlineKey, today, mdfvcode);//获取线上数据
//        String onlines = getRealTimeTicket(onlineKey, mdfvcode, today, online);
        String lineKey = "scenicSpots:Line:day:";//线下key
        String offline = getRedis(lineKey, today, mdfvcode);//获取线下数据
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("startTime", today + " 00:00:00");
        map.put("endTime", today + " 23:59:59");
        // 如果缓存中没有，去数据库查询某一天的数据
        if("0".equals(online) || "".equals(online)){
            online = passengerFlowDao.getOnlineByDay(map);
        }
        if("0".equals(offline) || "".equals(offline)){
            offline = passengerFlowDao.getOfflineByDay(map);
        }
        // 云台山客户有特殊需求，线下比线上多10%，现在云台山线上没有数据，是研发模拟数据，这里如果是云台山，进行特殊处理
        // 云台山特殊处理
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
            Integer offd = Integer.valueOf(offline);
            BigDecimal onlineNum = BigDecimal.ZERO;
            // 云台山线上销售计算规则   线上=（线上+线下）*（%5+%4+%3+%2）  y:线下   x:线上
            // 即  x=(x+y)*0.14    x=0.14x+0.14y   0.86x=0.14y   x=0.16y
            onlineNum = new BigDecimal(offd).multiply(new BigDecimal(0.16)).setScale(0,BigDecimal.ROUND_HALF_UP);
            online = String.valueOf(onlineNum);
        }
        TicketIndexOnAndOffVo vo = new TicketIndexOnAndOffVo();
        vo.setOffLine(offline);
        vo.setOnLine(online);
        vo.setSource("线上线下数据当天");

        dataList.put("day",vo);
        dataList.put("accumulate",voYear);
        return dataList;
    }

    /**
     * 云台山票务
     * @describe:获取线上线下票务数据
     */
    @Override
    public TicketIndexOnAndOffVo ticketIndexOnAndOffRedisYTS(String vcode, String mdfvcode) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());//今日时间(参数）
        String online = "";//获取线上数据
        String lineKey = "scenicSpots:Line:day:";//线下key
        String offline = getRedis(lineKey, today, mdfvcode);//获取线下数据
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("startTime", today + " 00:00:00");
        map.put("endTime", today + " 23:59:59");
        // 如果缓存中没有，去数据库查询某一天的数据
        if("0".equals(offline) || "".equals(offline)){
            offline = passengerFlowDao.getOfflineByDay(map);
        }
        // 云台山客户有特殊需求，线下比线上多10%，现在云台山线上没有数据，是研发模拟数据，这里如果是云台山，进行特殊处理
        // 云台山特殊处理
        Integer offd = Integer.valueOf(offline);
        online = String.valueOf(Math.round(new BigDecimal(offd).multiply(new BigDecimal(0.9)).doubleValue()));
        TicketIndexOnAndOffVo vo = new TicketIndexOnAndOffVo();
        vo.setOffLine(offline);
        vo.setOnLine(online);
        vo.setSource("线上线下数据");
        return vo;
    }

    //获取redis查询数量数量
    public String getRedis(String key, String date, String vcode) {
        String str = "";
        String num = "";
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        str = (String) hash.get(key, date + vcode);
        //捕获异常
        if ("".equals(str) || str == null) {
            num = "0";
        } else {
            try {
                JSONObject jasonObject = JSONObject.fromObject(str);
                //取nums
                num = jasonObject.getString("nums");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return num;
    }

//    /**
//     * 按天 获取当天每小时票务数据
//     *
//     * @param vcode 景区代码
//     * @param date  日期
//     * @return
//     */
//    public List<TicketClSsTendVO> getRealTimeTicket(String key, String vcode, String date, String type) {
//        //按天获取个小时数据
////        String h = TABLE_NAME + BIG_TABLENAME + TIME + COLON;
//        String md5Key = DigestUtils.md5Hex(vcode);
//        String hk =  date + md5Key;
//        String data = (String) RedisCache.getHash(redisTemplate, key, hk);
//
//        if (data == null || data == "") {
//            data = passengerFlowDao.getOnlineByDay(map);
//            if (data.size() > 0) {
//                /*变量*/
//                long ticket_nums = 0;
//                for (TicketClSsTendVO ticketClSs_tendVO : tendVOList) {
//                    ticket_nums += Long.valueOf(ticketClSs_tendVO.getTicket_num());
//                    ticketClSs_tendVO.setTicket_nums(ticket_nums + "");
//                }
//                RedisCache.putHash(redisTemplate, h, hk, tendVOList);
//            }
//        }
//
//        return tendVOList;
//    }


    @Override
    public List<OtaDataTendVo> otaDataTendDB(String vcode, String time) {
        //调用统一接口，解释数据
        String todayUrl = "http://localhost:8090/scdatasvc/otaData/getOtaData/" + time + "/" + vcode;
        String jsonStr = HttpRequestUtil.sendGet(todayUrl, null);
        JSONObject json = JSONObject.fromObject(jsonStr);
        JSONArray array = JSONArray.fromObject(json.get("datas"));
        List<OtaDataTendVo> list = JSONArray.toList(array, new OtaDataTendVo(), new JsonConfig());
        OtaDataTendVo otaDataTend = new OtaDataTendVo();
        return list;
    }


    /**
     * @param vcode
     * @param time
     * @Author: tanggm
     * 修改：百分比*100
     * 修改：ota占比统计之前采用redis数据，而总数是数据库查询数据，导致数据不一致，改为查询数据库方式
     * @Date: Created in 19:38 2017/6/21.
     * @Version:
     * @describe:ota占比百分比查询redis
     */
    @Override
    public List<OtaDataTendVo> otaDataTendRedis(String vcode, String time, String oldVcode) {
        //创建list返回集合
        List<OtaDataTendVo> percentData = new LinkedList<>();
        //创建hash接受字符串
        String str = "";
        Integer sum = 0;
        // 采用查询数据库方式
        Map  map =new HashMap();
        map.put("vcode",oldVcode);
        map.put("time",DateUtil.getCurDateStr());
        List<OtaDataTendVo > listData = otaDataDao.getOtaDataList(map);
        //备注：这里接产品经理需求，云台山客户方要求将各ota数据写为固定值，其它景区取真实值
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(oldVcode)){
            OtaDataTendVo otaDataTendVo1 = new OtaDataTendVo();
            otaDataTendVo1.setName("同程网");
            otaDataTendVo1.setCount(300);
            otaDataTendVo1.setPercent("5");
            OtaDataTendVo otaDataTendVo2 = new OtaDataTendVo();
            otaDataTendVo2.setName("携程网");
            otaDataTendVo2.setCount(240);
            otaDataTendVo2.setPercent("4");
            OtaDataTendVo otaDataTendVo3 = new OtaDataTendVo();
            otaDataTendVo3.setName("去哪儿网");
            otaDataTendVo3.setCount(180);
            otaDataTendVo3.setPercent("3");
            OtaDataTendVo otaDataTendVo4 = new OtaDataTendVo();
            otaDataTendVo4.setName("驴妈妈网");
            otaDataTendVo4.setCount(120);
            otaDataTendVo4.setPercent("2");
            percentData.add(otaDataTendVo1);
            percentData.add(otaDataTendVo2);
            percentData.add(otaDataTendVo3);
            percentData.add(otaDataTendVo4);
        }else if("f561aaf6ef0bf14d4208bb46a4ccb3ad".equals(oldVcode)) {
            OtaDataTendVo otaDataTendVo1 = new OtaDataTendVo();
            otaDataTendVo1.setName("长恨歌");
            otaDataTendVo1.setCount(0);
            otaDataTendVo1.setPercent("0");
            OtaDataTendVo otaDataTendVo2 = new OtaDataTendVo();
            otaDataTendVo2.setName("《12·12》");
            otaDataTendVo2.setCount(RandomNumber(130, 100));
            otaDataTendVo2.setPercent("6");
            OtaDataTendVo otaDataTendVo3 = new OtaDataTendVo();
            otaDataTendVo3.setName("团队票");
            otaDataTendVo3.setCount(RandomNumber(250, 170));
            otaDataTendVo3.setPercent("10");
            OtaDataTendVo otaDataTendVo4 = new OtaDataTendVo();
            otaDataTendVo4.setName("其他票");
            otaDataTendVo4.setCount(RandomNumber(100, 80));
            otaDataTendVo4.setPercent("4");
            percentData.add(otaDataTendVo1);
            percentData.add(otaDataTendVo2);
            percentData.add(otaDataTendVo3);
            percentData.add(otaDataTendVo4);

//        }else if("28c8ee033e7286d791e9654929a08ecb".equals(oldVcode)) {
//            String now = DateTools.getCurrentDate();
//            String before = DateTools.getbeforeDay(now);
//            String Key = RedisKey.MOUDEL_PASSENGERFLOW+"SLMHPassengerTicketOta:";
//            String hk = now + "|28c8ee033e7286d791e9654929a08ecb";
//            Object scenicDayObject= RedisCache.getHash(redisTemplate,Key, hk);
//            percentData = (List<OtaDataTendVo>) scenicDayObject;

//            OtaDataTendVo otaDataTendVo1 = new OtaDataTendVo();
//            otaDataTendVo1.setName("美团");
//            otaDataTendVo1.setCount(10109);
//            OtaDataTendVo otaDataTendVo2 = new OtaDataTendVo();
//            otaDataTendVo2.setName("驴妈妈");
//            otaDataTendVo2.setCount(1700);
//            OtaDataTendVo otaDataTendVo3 = new OtaDataTendVo();
//            otaDataTendVo3.setName("携程");
//            otaDataTendVo3.setCount(1317);
//            OtaDataTendVo otaDataTendVo4 = new OtaDataTendVo();
//            otaDataTendVo4.setName("新疆旅游为天猫旗舰店");
//            otaDataTendVo4.setCount(574);
//            OtaDataTendVo otaDataTendVo5 = new OtaDataTendVo();
//            otaDataTendVo5.setName("赛里木湖微信");
//            otaDataTendVo5.setCount(163);
//            OtaDataTendVo otaDataTendVo6 = new OtaDataTendVo();
//            otaDataTendVo6.setName("途牛");
//            otaDataTendVo6.setCount(10);
//            OtaDataTendVo otaDataTendVo7 = new OtaDataTendVo();
//            otaDataTendVo7.setName("同程");
//            otaDataTendVo7.setCount(266);
//            OtaDataTendVo otaDataTendVo8 = new OtaDataTendVo();
//            otaDataTendVo8.setName("去哪儿");
//            otaDataTendVo8.setCount(411);
//            percentData.add(otaDataTendVo1);
//            percentData.add(otaDataTendVo2);
//            percentData.add(otaDataTendVo3);
//            percentData.add(otaDataTendVo4);
//            percentData.add(otaDataTendVo5);
//            percentData.add(otaDataTendVo6);
//            percentData.add(otaDataTendVo7);
//            percentData.add(otaDataTendVo8);

        }
        else{
            // 这里如果没有值，进行模拟值
            if (listData.size() < 1) {
//                OtaDataTendVo otaDataTendVo1 = new OtaDataTendVo();
//                otaDataTendVo1.setName("极客旅游");
//                otaDataTendVo1.setCount(300);
//                otaDataTendVo1.setPercent("5");
//                OtaDataTendVo otaDataTendVo2 = new OtaDataTendVo();
//                otaDataTendVo2.setName("携程网");
//                otaDataTendVo2.setCount(240);
//                otaDataTendVo2.setPercent("4");
//                OtaDataTendVo otaDataTendVo3 = new OtaDataTendVo();
//                otaDataTendVo3.setName("去哪儿网");
//                otaDataTendVo3.setCount(180);
//                otaDataTendVo3.setPercent("3");
//                OtaDataTendVo otaDataTendVo4 = new OtaDataTendVo();
//                otaDataTendVo4.setName("驴妈妈网");
//                otaDataTendVo4.setCount(120);
//                otaDataTendVo4.setPercent("2");
//                percentData.add(otaDataTendVo1);
//                percentData.add(otaDataTendVo2);
//                percentData.add(otaDataTendVo3);
//                percentData.add(otaDataTendVo4);
            } else {
                for (int i = 0; i < listData.size(); i++) {//循环算总数
                    sum += (int) listData.get(i).getCount();//算ota票总数
                }
                for (int i = 0; i < listData.size(); i++) {//循环算百分比
                    OtaDataTendVo otaVo = new OtaDataTendVo();
                    BigDecimal b1 = new BigDecimal(Double.valueOf(listData.get(i).getCount()*100));
                    BigDecimal b2 = new BigDecimal(Double.valueOf(sum));
                    if(b2.compareTo(BigDecimal.ZERO) == 0){
                        otaVo.setPercent(String.valueOf(0));
                    }else{
                        otaVo.setPercent(String.valueOf(b1.divide(b2, 2, RoundingMode.HALF_UP)));
                    }
                    otaVo.setCount(listData.get(i).getCount());
                    otaVo.setName(listData.get(i).getName());
                    percentData.add(otaVo);
                }
            }
        }
        return percentData;
    }

    @Override
    public Map otaDataTendRedisHQG(String vcode, String time, String oldVcode) {
        Map map = new LinkedHashMap();

        List<Map> chg = new LinkedList<>();
        List<Map> onetwo = new LinkedList<>();
        Map map1 = new HashMap();
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        Map map4 = new HashMap();
        Map map5 = new HashMap();
        Map map6 = new HashMap();
        map1.put("name","长恨歌预约总数");
        map1.put("num","0");
        map2.put("name","第一场");
        map2.put("num","0");
        map3.put("name","第二场");
        map3.put("num","0");
        chg.add(map1);
        chg.add(map2);
        chg.add(map3);
        map4.put("name","《12.12》销售总数");
        map4.put("num","0");
        map5.put("name","第一场");
        map5.put("num","0");
        map6.put("name","第二场");
        map6.put("num","0");
        onetwo.add(map4);
        onetwo.add(map5);
        onetwo.add(map6);

        map.put("chg",chg);
        map.put("onetwo",onetwo);

        return map;
    }

    @Override
    public List<OtaDataTendVo> otaDataTendRedisYTS(String vcode, String time, String oldVcode) {
        //创建list返回集合
        List<OtaDataTendVo> percentData = new LinkedList<>();
        //备注：这里接产品经理需求，云台山客户方要求将各ota数据写为固定值，其它景区取真实值
        OtaDataTendVo otaDataTendVo1 = new OtaDataTendVo();
        otaDataTendVo1.setName("同程网");
        otaDataTendVo1.setCount(300);
        otaDataTendVo1.setPercent("5");
        OtaDataTendVo otaDataTendVo2 = new OtaDataTendVo();
        otaDataTendVo2.setName("携程网");
        otaDataTendVo2.setCount(240);
        otaDataTendVo2.setPercent("4");
        OtaDataTendVo otaDataTendVo3 = new OtaDataTendVo();
        otaDataTendVo3.setName("去哪儿网");
        otaDataTendVo3.setCount(180);
        otaDataTendVo3.setPercent("3");
        OtaDataTendVo otaDataTendVo4 = new OtaDataTendVo();
        otaDataTendVo4.setName("驴妈妈网");
        otaDataTendVo4.setCount(120);
        otaDataTendVo4.setPercent("2");
        percentData.add(otaDataTendVo1);
        percentData.add(otaDataTendVo2);
        percentData.add(otaDataTendVo3);
        percentData.add(otaDataTendVo4);
        return percentData;
    }

    /**
     * 神龙峡客流与票务OTA模块定制
     *
     * @param vcode
     * @param time
     * @param oldVcode 没有经过加密的vcode
     * @return
     */
    @Override
    public Object slxOtaDataTendRedis(String vcode, String time, String oldVcode) {
        // 采用查询数据库方式
        String[] names = {"极客旅游","仁义在线","微信","其他"};
        List list = new ArrayList<>();
        Map param = new HashMap<>();
        param.put("vcode",oldVcode);
        param.put("startTime",time+" 00:00:00");
        param.put("endTime",time+" 23:59:59");
        int sum = otaDataDao.getOtaSumData(param);
        for (int i = 0; i< names.length; i++) {
            Map  map =new HashMap();
            map.put("vcode",oldVcode);
            param.put("startTime",time+" 00:00:00");
            param.put("endTime",time+" 23:59:59");
            map.put("name",names[i]);
            Map mapData = otaDataDao.getslxOtaDataList(map);
            int count = Integer.parseInt(mapData.get("count").toString());
            if(count == 0) {
                mapData.put("name",names[i]);
                mapData.put("count",0);
                mapData.put("percent",0);
                list.add(mapData);
            } else {
                BigDecimal b1 = new BigDecimal(count).multiply(new BigDecimal(100));
                BigDecimal b2 = new BigDecimal(sum);
                if(b2.compareTo(BigDecimal.ZERO) == 0){
                    mapData.put("percent",String.valueOf(0));
                }else{
                    mapData.put("percent",String.valueOf(b1.divide(b2, 2, RoundingMode.HALF_UP)));
                }
                list.add(mapData);
            }
        }
        // 计算今日人数
        String peopleK = "passengerflow:day:getPassengerFlowByDay:";//实时人数
        Map peopleMap = (Map) RedisCache.getHash(redisTemplate,peopleK, time+"|" + oldVcode);
        String todayPeopleNum = "0";
        if(peopleMap != null){
            todayPeopleNum = peopleMap.get("num")+"";
        }
        // 计算近30日平均值
        String before30Day = "";
        try {
            before30Day = DateUtil.getHistDate(29);
        } catch (Exception e){
            e.printStackTrace();
        }
        List<String> timeList = DateUtil.getSubsectionDateList(before30Day,time);
        int daysNum = 0;
        Integer peopleNum = 0;
        Integer maxNum = 0;
        String maxDay = "";
        for(String day : timeList){
            Object obj= RedisCache.getHash(redisTemplate,peopleK, day+"|" + oldVcode);
            Map dayMap = (Map)obj;
            if(dayMap != null){
                daysNum++;
                peopleNum += Integer.valueOf(dayMap.get("num")+"");
                if(Integer.valueOf(dayMap.get("num")+"") > maxNum){
                    maxNum = Integer.valueOf(dayMap.get("num")+"");
                    maxDay = day;
                }
            }
        }
        BigDecimal days30Num = BigDecimal.ZERO;
        if(daysNum != 0 && peopleNum != null && peopleNum.compareTo(0) != 0){
            days30Num = new BigDecimal(peopleNum).divide(new BigDecimal(daysNum), 0, BigDecimal.ROUND_HALF_UP);
        }
        // 计算近30日峰值(某天最大数据)
        Map maxDayMap = new HashMap();
        maxDayMap.put("num",maxNum);
        maxDayMap.put("maxDay",maxDay);

        // 计算今日票务
        String onlineKey = "scenicSpots:onLine:day:";//线上key
        String lineKey = "scenicSpots:Line:day:";//线下key
        //今日票务缓存
        Integer atDay = 0;
        String online = getRedis(onlineKey, time, vcode);//获取线上数据
        String offline = getRedis(lineKey, time, vcode);//获取线下数据
        Map map = new HashMap();
        map.put("vcode", oldVcode);
        map.put("startTime", time + " 00:00:00");
        map.put("endTime", time + " 23:59:59");
        // 如果缓存中没有，去数据库查询某一天的数据
        if("0".equals(online) || "".equals(online)){
            online = passengerFlowDao.getOnlineByDay(map);
        }
        if("0".equals(offline) || "".equals(offline)){
            offline = passengerFlowDao.getOfflineByDay(map);
        }
        atDay+= Integer.valueOf(online);
        atDay+= Integer.valueOf(offline);

        // 计算实时负荷度
        String now = DateUtil.getCurDateTimeStr();
        String nows = MathUtils.getAfterDelHour(now,-2);
        String yesterDay = "";
        try {
            yesterDay = MathUtils.getBeforeHourTime(24);
        } catch (Exception e){
            e.printStackTrace();
        }
        String yesterDays = MathUtils.getAfterDelHour(yesterDay,-2);
        Map timeMap = new HashMap<>();
        timeMap.put("now",now);
        timeMap.put("nowTime",nows);
        timeMap.put("old",yesterDay);
        timeMap.put("oldTime",yesterDays);
        timeMap.put("vcode",oldVcode);
        String oldLoad = "0";
        String nowLoad = "0";
        //今天负荷
        List<RealPeople> nowTimeOnTimeList = realPeopleDao.getScenicTimeOnTime(timeMap);
        for (RealPeople nowPopulation : nowTimeOnTimeList) {
            // 这里计算不要用 double ，会有经度的损失
            if(nowPopulation.getTOTAL() == null || "".equals(nowPopulation.getTOTAL()) || nowPopulation.getMAXQUANTITY() == null || "".equals(nowPopulation.getMAXQUANTITY())){
                nowLoad = "0";
            }else{
                nowLoad = (new BigDecimal(nowPopulation.getTOTAL()).multiply(new BigDecimal(100)).divide(new BigDecimal(nowPopulation.getMAXQUANTITY()),2,BigDecimal.ROUND_HALF_UP))+"";
                if(new BigDecimal(nowLoad).compareTo(new BigDecimal(0.00)) == 0){
                    nowLoad = "0";
                }
            }

        }
        //昨天负荷
        List<RealPeople> oldTimeOnTimeList = realPeopleDao.getScenicTimeOnTimeYesterday(timeMap);
        for (RealPeople oldPopulation : oldTimeOnTimeList) {
            if(oldPopulation.getTOTAL() == null || "".equals(oldPopulation.getTOTAL()) || oldPopulation.getMAXQUANTITY() == null || "".equals(oldPopulation.getMAXQUANTITY())){
                oldLoad = "0";
            }else{
                oldLoad = (new BigDecimal(oldPopulation.getTOTAL()).multiply(new BigDecimal(100)).divide(new BigDecimal(oldPopulation.getMAXQUANTITY()),2,BigDecimal.ROUND_HALF_UP))+"";
                if(new BigDecimal(nowLoad).compareTo(new BigDecimal(0.00)) == 0){
                    oldLoad = "0";
                }
            }
        }
        Map datas = new HashMap<>();

        datas.put("nowLoad",nowLoad);
        if (Double.parseDouble(nowLoad) > Double.parseDouble(oldLoad)) {
            datas.put("type",1);
        } else if (Double.parseDouble(nowLoad) < Double.parseDouble(oldLoad)) {
            datas.put("type",2);
        } else {
            datas.put("type",0);
        }


        Map sums = new HashMap<>();
        sums.put("todaySum",sum);
        sums.put("tmrSum",Math.ceil(sum * 0.88));

        // 票务列表
        datas.put("ota",list);
        // 今日网购、明日网购
        datas.put("ticket",sums);
        // 今日累计人数
        datas.put("todayPeople",todayPeopleNum);
        // 实时人数
        datas.put("timelyPeople",todayPeopleNum);
        // 近30日平均数
        datas.put("avgDayPeople",days30Num);
        // 近30日峰值
        datas.put("maxDayPeople",maxDayMap);
        // 今日总票数
        datas.put("ticketNum",atDay);
        return datas;
    }

    @Override
    public Object msOtaDataTendRedis(String vcode, String time, String oldVcode) {
        // 采用查询数据库方式
        String[] names = {"集客宝","微信","明日预购"};
        List list = new ArrayList<>();
        Map param = new HashMap<>();
        param.put("vcode",oldVcode);
        param.put("startTime",time+" 00:00:00");
        param.put("endTime",time+" 23:59:59");
        int sum = otaDataDao.getOtaSumData(param);
        for (int i = 0; i< names.length; i++) {
            Map  map =new HashMap();
            map.put("vcode",oldVcode);
            map.put("startTime",time+" 00:00:00");
            map.put("endTime",time+" 23:59:59");
            map.put("name",names[i]);
            Map mapData = otaDataDao.getslxOtaDataList(map);
            int count = Integer.parseInt(mapData.get("count").toString());
            if(count == 0) {
                mapData.put("name",names[i]);
                mapData.put("count",0);
                mapData.put("percent",0);
                list.add(mapData);
            } else {
                BigDecimal b1 = new BigDecimal(count).multiply(new BigDecimal(100));
                BigDecimal b2 = new BigDecimal(sum);
                if(b2.compareTo(BigDecimal.ZERO) == 0){
                    mapData.put("percent",String.valueOf(0));
                }else{
                    mapData.put("percent",String.valueOf(b1.divide(b2, 2, RoundingMode.HALF_UP)));
                }
                list.add(mapData);
            }
        }
        // 计算今日人数
        String peopleK = "passengerflow:day:getPassengerFlowByDay:";//实时人数
        Map peopleMap = (Map) RedisCache.getHash(redisTemplate,peopleK, time+"|" + oldVcode);
        //累计人数
        String todayPeopleNum = "0";
        String todayPeopleNumReal = "0";
        if(peopleMap != null){
            todayPeopleNum = peopleMap.get("num")+"";
            todayPeopleNumReal = peopleMap.get("leave") +"";// 离开人数
        }

        // 计算近30日平均值
        String before30Day = "";
        String yesterTime = "";
        try {
            before30Day = DateUtil.getHistDate(29);
            yesterTime = DateUtil.getHistDate(1);
        } catch (Exception e){
            e.printStackTrace();
        }
        List<String> timeList = DateUtil.getSubsectionDateList(before30Day,yesterTime);
        int daysNum = 0;
        Integer peopleNum = 0;
        Integer maxNum = 0;
        Integer minNum = 9000000;
        String maxDay = "";
        String minDay = "";
        for(String day : timeList){
            Object obj= RedisCache.getHash(redisTemplate,peopleK, day+"|" + oldVcode);
            Map dayMap = (Map)obj;
            if(dayMap != null){
                daysNum++;
                peopleNum += Integer.valueOf(dayMap.get("num")+"");
                if(Integer.valueOf(dayMap.get("num")+"") > maxNum){
                    maxNum = Integer.valueOf(dayMap.get("num")+"");
                    maxDay = day;
                }
                if(Integer.valueOf(dayMap.get("num")+"") <= minNum){
                    minNum = Integer.valueOf(dayMap.get("num")+"");
                    minDay = day;
                }
            }else {
                minNum = 0;
                minDay = day;
            }
        }
        BigDecimal days30Num = BigDecimal.ZERO;
        if(daysNum != 0 && peopleNum != null && peopleNum.compareTo(0) != 0){
            days30Num = new BigDecimal(peopleNum).divide(new BigDecimal(daysNum), 0, BigDecimal.ROUND_HALF_UP);
        }
        // 计算近30日峰值(某天最大数据)
        Map maxDayMap = new HashMap();
        maxDayMap.put("num",maxNum);
        maxDayMap.put("maxDay",maxDay);

        // 计算近30日谷值(某天最小数据)
        Map minDayMap = new HashMap();
        minDayMap.put("num",minNum);
        minDayMap.put("minDay",minDay);

        // 计算今日票务
        String onlineKey = "scenicSpots:onLine:day:";//线上key
        String lineKey = "scenicSpots:Line:day:";//线下key
        //今日票务缓存
        Integer atDay = 0;
        String online = getRedis(onlineKey, time, vcode);//获取线上数据
        String offline = getRedis(lineKey, time, vcode);//获取线下数据
        Map map = new HashMap();
        map.put("vcode", oldVcode);
        map.put("startTime", time + " 00:00:00");
        map.put("endTime", time + " 23:59:59");
        // 如果缓存中没有，去数据库查询某一天的数据
        if("0".equals(online) || "".equals(online)){
            online = passengerFlowDao.getOnlineByDay(map);
        }
        if("0".equals(offline) || "".equals(offline)){
            offline = passengerFlowDao.getOfflineByDay(map);
        }
        atDay+= Integer.valueOf(online);
        atDay+= Integer.valueOf(offline);

        // 计算实时负荷度
        String now = DateUtil.getCurDateTimeStr();
        String nows = MathUtils.getAfterDelHour(now,-2);
        String yesterDay = "";
        try {
            yesterDay = MathUtils.getBeforeHourTime(24);
        } catch (Exception e){
            e.printStackTrace();
        }
        String yesterDays = MathUtils.getAfterDelHour(yesterDay,-2);
        Map timeMap = new HashMap<>();
        timeMap.put("now",now);
        timeMap.put("nowTime",nows);
        timeMap.put("old",yesterDay);
        timeMap.put("oldTime",yesterDays);
        timeMap.put("vcode",oldVcode);
        String oldLoad = "0";
        String nowLoad = "0";
        //今天负荷
        List<RealPeople> nowTimeOnTimeList = realPeopleDao.getScenicTimeOnTime(timeMap);
        for (RealPeople nowPopulation : nowTimeOnTimeList) {
            // 这里计算不要用 double ，会有经度的损失
            if(nowPopulation.getTOTAL() == null || "".equals(nowPopulation.getTOTAL()) || nowPopulation.getMAXQUANTITY() == null || "".equals(nowPopulation.getMAXQUANTITY())){
                nowLoad = "0";
            }else{
                nowLoad = (new BigDecimal(nowPopulation.getTOTAL()).multiply(new BigDecimal(100)).divide(new BigDecimal(nowPopulation.getMAXQUANTITY()),2,BigDecimal.ROUND_HALF_UP))+"";
                if(new BigDecimal(nowLoad).compareTo(new BigDecimal(0.00)) == 0){
                    nowLoad = "0";
                }
            }

        }
        //昨天负荷
        List<RealPeople> oldTimeOnTimeList = realPeopleDao.getScenicTimeOnTimeYesterday(timeMap);
        for (RealPeople oldPopulation : oldTimeOnTimeList) {
            if(oldPopulation.getTOTAL() == null || "".equals(oldPopulation.getTOTAL()) || oldPopulation.getMAXQUANTITY() == null || "".equals(oldPopulation.getMAXQUANTITY())){
                oldLoad = "0";
            }else{
                oldLoad = (new BigDecimal(oldPopulation.getTOTAL()).multiply(new BigDecimal(100)).divide(new BigDecimal(oldPopulation.getMAXQUANTITY()),2,BigDecimal.ROUND_HALF_UP))+"";
                if(new BigDecimal(nowLoad).compareTo(new BigDecimal(0.00)) == 0){
                    oldLoad = "0";
                }
            }
        }
        Map datas = new HashMap<>();

        datas.put("nowLoad",nowLoad);
        if (Double.parseDouble(nowLoad) > Double.parseDouble(oldLoad)) {
            datas.put("type",1);
        } else if (Double.parseDouble(nowLoad) < Double.parseDouble(oldLoad)) {
            datas.put("type",2);
        } else {
            datas.put("type",0);
        }


        Map sums = new HashMap<>();
        sums.put("online",online);
        sums.put("offline",offline);

        // 票务列表
        datas.put("ota",list);
        // 今日线上线下票务
        datas.put("ticket",sums);
        // 今日累计人数  TODO 改成出园人数
        datas.put("todayPeople",todayPeopleNumReal);
        // 实时人数  TODO 改成进园人数
        datas.put("timelyPeople",todayPeopleNum);
        // 近30日平均数
        datas.put("avgDayPeople",days30Num);
        // 近30日谷值
        datas.put("minDayPeople",minDayMap);
        // 近30日峰值
        datas.put("maxDayPeople",maxDayMap);
        // 今日总票数
        datas.put("ticketNum",atDay);
        return datas;
    }

    //实时人数
    @Override
    public RealPeopleIndexSumVo getPeopleRealTime(Map map) {

        return passengerFlowDao.getPeopleRealTime(map);

    }


    @Override
    public Map<String, Object> getSpotsPeoples(String vcode, String date) throws Exception{
        String scenicKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenic:day:"+"getPassengerFlowByScenic:";
        String yesDate = MathUtils.getAfterDelDay(date);
        String yesScenicHk = yesDate+"|"+vcode;
        String scenicHk = date+"|"+vcode;
        Object object=RedisCache.getHash(redisTemplate,scenicKey,scenicHk);
        Object yesObject=RedisCache.getHash(redisTemplate,scenicKey,yesScenicHk);
        List<PassengerVo> listData = new ArrayList<>();
        List<Map<String, Object>> scenicNames = passengerFlowDao.getScenicNames(vcode);
        List<Map<String,Object>> todaylist = null;
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("vcode", vcode);
        if(object!=null){
            todaylist = (List<Map<String,Object>>)object;
        }else{
            // 缓存中没有数据，就去数据库查询
            parMap.put("startTime", date+" 00:00:00");
            parMap.put("endTime", date+" 23:59:59");
            todaylist = passengerFlowDao.getScenicPopulation(parMap);
        }
        List<Map<String,Object>> yeslist = null;
        if(yesObject!=null){
            yeslist = (List<Map<String,Object>>)yesObject;
        }else{
            // 缓存中没有数据，就去数据库查询
            parMap.put("startTime", yesDate+" 00:00:00");
            parMap.put("endTime", yesDate+" 23:59:59");
            yeslist = passengerFlowDao.getScenicPopulation(parMap);
        }
        for(Map<String, Object> scenicNameMap : scenicNames){
            String scenicName = scenicNameMap.get("name")+"";
            PassengerVo vo = new PassengerVo();
            vo.setName(scenicName);
            vo.setMaxCount(scenicNameMap.get("maxCount")+"");
            boolean todayFlag = false;
            for (Map<String,Object> map : todaylist) {
                if(scenicName.equals(map.get("scenicName"))){
                    vo.setTodayNum(map.get("num")+"");
                    todayFlag = true;
                    break;
                }
            }
            // 如果没有匹配，就存为 0
            if(!todayFlag){
                vo.setTodayNum("0");
            }
            boolean yesFlag = false;
            for (Map<String,Object> yesmap : yeslist) {
                if(scenicName.equals(yesmap.get("scenicName"))){
                    vo.setYesterdayNum(yesmap.get("num")+"");
                    yesFlag = true;
                    break;
                }
            }
            // 如果没有匹配，就存为 0
            if(!yesFlag){
                vo.setYesterdayNum("0");
            }
            listData.add(vo);
        }
        Map resMap = new HashMap();
        resMap.put("spotsList", listData);
        return resMap;
    }

    @Override
    public Map<String, Object> getTeamList(String oldVcode, String vcode, String date) throws Exception {
        Map<String, Object> resMap = new HashMap<>();
        //创建list
        List<PassengerTeamVo> listData = new LinkedList<>();
        int todayCounts = 0;
        int yestodayCounts = 0;
        //创建has接受字符串
        String str = "";
        // 昨天的
        String yesstr = "";
        //创建哈希对象
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        // 景区人数数据
        Object so = hash.get("scenicSpots:team:day:", date+vcode);
        String nameStr = "";
        String count = "";
        if(null != so){
            str = so.toString();
            //转换hash为json
            JSONObject jasonObject = JSONObject.fromObject(str);
            // 名字
            nameStr = jasonObject.getString("tourFroms");
            // 今日团队数量
            count = jasonObject.getString("tours");
        }
        // 景区昨日数据
        Object sto = hash.get("scenicSpots:team:day:", DateTools.addDate(date, -1)+vcode);
        String yesterdayCount = "";
        if(null != sto){
            yesstr = sto.toString();
            JSONObject jasonObjectYes = JSONObject.fromObject(yesstr);
            // 昨日团队数量
            yesterdayCount = jasonObjectYes.getString("tours");
        }

        // 封装数据到前台
        if(null != nameStr && !"".equals(nameStr)){
            String[] names = nameStr.split(",");
            String[] counts = new String[]{};
            if(!"".equals(count)){
                counts = count.split(",");
            }
            String[] yesCounts = new String[]{};
            if(!"".equals(yesterdayCount)){
                yesCounts = yesterdayCount.split(",");
            }
            for (int i=0;i<names.length;i++) {
                PassengerTeamVo passengerTeamVo = new PassengerTeamVo();
                passengerTeamVo.setName(names[i]);
                passengerTeamVo.setTodayNum(counts[i]);
                if(yesCounts.length>0 && yesCounts != null && !"".equals(yesCounts[i])){
                    passengerTeamVo.setYesterdayNum(yesCounts[i]);
                }else{
                    passengerTeamVo.setYesterdayNum("0");
                }
                todayCounts += Integer.valueOf(counts[i]);
                yestodayCounts += Integer.valueOf(yesCounts[i]);
                listData.add(passengerTeamVo);
            }
        }
        // 判断如果没有数据，暂时模拟数据
        if(listData.size() < 1){
            String[] provinceNamesArr ;
            String provinceNames = "";
            //获取模拟团队来源地名称
            Map simulateMap = new HashMap();
            simulateMap.put("vcode",oldVcode);
            simulateMap.put("key","tourMonitor");
            Map simulateTour = passengerFlowDao.getSimulateTour(simulateMap);
            provinceNamesArr = (simulateTour.get("simulateTour")+"").split(",");
            provinceNames = simulateTour.get("simulateTour").toString();
//            if(vcode.equals("d8b55e237f075fc17006dcd11ff5b92b")) {
//                provinceNamesArr = new String[]{"河南", "辽宁", "山东", "江苏", "浙江"};
//                provinceNames = "河南,辽宁,山东,江苏,浙江";
//            }else {
//                provinceNamesArr = new String[]{"四川", "重庆", "陕西", "云南", "贵州"};
//                provinceNames = "四川,重庆,陕西,云南,贵州";
//            }
                Integer[] numbers = new Integer[]{RandomNumber(10, 6), RandomNumber(8, 5), RandomNumber(7, 6), RandomNumber(13, 8), RandomNumber(11, 4)};
                Integer[] yesnumbers = new Integer[]{RandomNumber(10, 4), RandomNumber(10, 7), RandomNumber(8, 5), RandomNumber(13, 5), RandomNumber(15, 10)};
                for (int i = 0; i < provinceNamesArr.length; i++) {
                    PassengerTeamVo passengerTeamVo = new PassengerTeamVo();
                    passengerTeamVo.setName(provinceNamesArr[i]);
                    Integer todayNum = numbers[i];
                    Integer yesterdayNum = yesnumbers[i];
                    passengerTeamVo.setTodayNum(todayNum + "");
                    passengerTeamVo.setYesterdayNum(yesterdayNum + "");
                    todayCounts += todayNum;
                    yestodayCounts += yesterdayNum;
                    listData.add(passengerTeamVo);
                }

                // 存入今日团队redis
                String dateTime = DateUtil.getCurDateStr();
                String key = "";
                Map<Object, String> resMapRedis = new HashMap<Object, String>();

                Map<Object, Object> map = new TreeMap<Object, Object>();
                String numberstr = numbers[0] + "," + numbers[1] + "," + numbers[2] + "," + numbers[3] + "," + numbers[4];
                map.put("tours", numberstr);
                map.put("tourFroms", provinceNames);
                map.put("dateTime", dateTime);
                key = dateTime + vcode;
                resMapRedis.put(key, ConvertDataUtil.mapToJsonStr(map));
                String tableKey = TABLE_NAME + BIG_TEAM_NAME + "day:";
                hash.putAll(tableKey, resMapRedis);
                // 存入昨日团队redis
                String yesdateTime = MathUtils.getAfterDelDay(dateTime);
                String yeskey = "";
                Map<Object, String> yesresMapRedis = new HashMap<Object, String>();
                Map<Object, Object> yesmap = new TreeMap<Object, Object>();
                String yesnumberstr = yesnumbers[0] + "," + yesnumbers[1] + "," + yesnumbers[2] + "," + yesnumbers[3] + "," + yesnumbers[4];
                yesmap.put("tours", yesnumberstr);
                yesmap.put("tourFroms", provinceNames);
                yesmap.put("dateTime", yesdateTime);
                yeskey = yesdateTime + vcode;
                yesresMapRedis.put(yeskey, ConvertDataUtil.mapToJsonStr(yesmap));
                String yestableKey = TABLE_NAME + BIG_TEAM_NAME + "day:";
                hash.putAll(yestableKey, yesresMapRedis);

        }
        // 数据列表集合
        resMap.put("provinceList", listData);
        // 今日团队数量
        resMap.put("todayCounts", todayCounts);
        // 昨日团队数量
        resMap.put("yestodayCounts", yestodayCounts);
        return resMap;
    }
    @Override
    public List actualPassenger(String vcode, String date) {
        //创建list返回集合
        List<ActualPassengerVo> listData = new LinkedList<>();
        //创建has接受字符串
        String str = "";
        //创建哈希对象
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        // 景区人数数据
        Object so = hash.get("scenicSpots:spots:day:", date+vcode);
        // 模拟死数据
        ActualPassengerVo actualPassengerVo = new ActualPassengerVo();
        actualPassengerVo.setScenicName("红石峡");
        actualPassengerVo.setLoadNum("78.02%");
        actualPassengerVo.setMaxCount("6000");
        Map<String, Object> map = new HashMap<>();
        map.put("date", "18:00");
        map.put("num", 2500);
        actualPassengerVo.getDateNumList().add(map);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("date", "14:00");
        map2.put("num", 5500);
        actualPassengerVo.getDateNumList().add(map2);
        ActualPassengerVo actualPassengerVo2 = new ActualPassengerVo();
        actualPassengerVo2.setScenicName("小寨沟");
        actualPassengerVo.setLoadNum("48.02%");
        actualPassengerVo.setMaxCount("9000");
        Map<String, Object> map22 = new HashMap<>();
        map22.put("date", "18:00");
        map22.put("num", 2000);
        actualPassengerVo2.getDateNumList().add(map22);
        Map<String, Object> map33 = new HashMap<>();
        map33.put("date", "14:00");
        map33.put("num", 1500);
        actualPassengerVo2.getDateNumList().add(map33);

        listData.add(actualPassengerVo);
        listData.add(actualPassengerVo2);

        return listData;
    }

    @Override
    public Map<String, Object> scenicPassenger(String date, String mdfVcode) {
        //创建has接受字符串
        String str = "";
        // 昨天的
        String yesstr = "";
        //创建哈希对象
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        // 景区人数数据
        Object so = hash.get("people:people:", date+mdfVcode);
        Map<String, Object> resMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        Long maxquantity = Long.valueOf(0);
        if(so != null && !"".equals(so)){
            str = so.toString();
            //转换hash为json
            JSONObject jasonObject = JSONObject.fromObject(str);
            //捕获异常
            Iterator<String> sIterator = null;
            try {
                sIterator = jasonObject.keys();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //遍历封装
            while (sIterator.hasNext()) {
                Map<String, Object> lMap = new HashMap<>();
                String key = sIterator.next();
                String value = jasonObject.getString(key);
                if("maxquantity".equals(key)){
                    maxquantity = Long.valueOf(value);
                }else{
                    lMap.put("time", key);
                    lMap.put("num", value);
                    list.add(lMap);
                }
            }

        }
        resMap.put("rows", list);
        resMap.put("maxquantity", maxquantity);
        return resMap;
    }

    @Override
    public Map<String, Object> complaints(String nowyear, String mdfVcode) {
        //创建has接受字符串
        String str = "";
        // 昨天的
        String yesstr = "";
        //创建哈希对象
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        // 景区人数数据
        Object so = hash.get("scenicSpots:complaints:year:", nowyear+mdfVcode);
        Map<String, Object> resMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        Long total = Long.valueOf(0);
        if(so != null && !"".equals(so)){
            str = so.toString();
            //转换hash为json
            JSONObject jasonObject = JSONObject.fromObject(str);
            if(jasonObject != null){
                String typesStr = jasonObject.getString("types");
                String numsStr = jasonObject.getString("nums");
                String[] nums = null;
                if(typesStr != null && !"".equals(typesStr)){
                    if(numsStr != null && !"".equals(numsStr)){
                        nums = numsStr.split(",");
                    }
                    String[] types = typesStr.split(",");
                    for (int i=0;i<types.length;i++){
                        Map<String, Object> lMap = new HashMap<>();
                        lMap.put("name", types[i]);
                        String num = nums[i];
                        if(num == null || "".equals(num)){
                            num = String.valueOf(0);
                        }
                        total += Long.valueOf(num);
                        lMap.put("num", num);
                        list.add(lMap);
                    }
                }
            }
        }
        resMap.put("rows", list);
        resMap.put("num", total);
        return resMap;
    }

    @Override
    public SumPeopleIndexVo sumTicketRedis(String vcode, String vcodemdh) {
        String onlineKey = "scenicSpots:onLine:day:";//线上key
        String lineKey = "scenicSpots:Line:day:";//线下key
        //创建has接受字符串
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());//今日时间(参数）
        String LastYearTime = MathUtils.getAfterYear(today);//去年今日时
        String beforelastTime = MathUtils.getAfterYear(LastYearTime);//前年今日时间
        //今日票务缓存
        Integer atDay = 0;
        String online = getRedis(onlineKey, today, vcodemdh);//获取线上数据
        String offline = getRedis(lineKey, today, vcodemdh);//获取线下数据
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("startTime", today + " 00:00:00");
        map.put("endTime", today + " 23:59:59");
        // 如果缓存中没有，去数据库查询某一天的数据
        if("0".equals(online) || "".equals(online)){
            online = passengerFlowDao.getOnlineByDay(map);
        }
        if("0".equals(offline) || "".equals(offline)){
            offline = passengerFlowDao.getOfflineByDay(map);
        }
        atDay+= Integer.valueOf(online);
        atDay+= Integer.valueOf(offline);
        //去年今日票务缓存
        Integer lastYearDay = 0;
        String lastYearonline = getRedis(onlineKey, LastYearTime, vcodemdh);//获取线上数据
        String lastYearoffline = getRedis(lineKey, LastYearTime, vcodemdh);//获取线下数据
        // 如果缓存中没有，去数据库查询某一天的数据
        map.put("startTime", LastYearTime + " 00:00:00");
        map.put("endTime", LastYearTime + " 23:59:59");
        if("0".equals(lastYearonline) || "".equals(lastYearonline)){
            lastYearonline = passengerFlowDao.getOnlineByDay(map);
        }
        if("0".equals(lastYearoffline) || "".equals(lastYearoffline)){
            lastYearoffline = passengerFlowDao.getOfflineByDay(map);
        }
        lastYearDay+= Integer.valueOf(lastYearonline);
        lastYearDay+= Integer.valueOf(lastYearoffline);
        //前年今日票务缓存
        Integer beforeYearDay = 0;
        String beforeYearonline = getRedis(onlineKey, beforelastTime, vcodemdh);//获取线上数据
        String beforeYearoffline = getRedis(lineKey, beforelastTime, vcodemdh);//获取线下数据
        // 如果缓存中没有，去数据库查询某一天的数据
        map.put("startTime", beforelastTime + " 00:00:00");
        map.put("endTime", beforelastTime + " 23:59:59");
        if("0".equals(beforeYearonline) || "".equals(beforeYearonline)){
            beforeYearonline = passengerFlowDao.getOnlineByDay(map);
        }
        if("0".equals(beforeYearoffline) || "".equals(beforeYearoffline)){
            beforeYearoffline = passengerFlowDao.getOfflineByDay(map);
        }
        beforeYearDay+= Integer.valueOf(beforeYearonline);
        beforeYearDay+= Integer.valueOf(beforeYearoffline);

        String lastYearOnYearType;//去年总数同比箭头类型
        String beforeYearOnYearType;//前年总数同比箭头类型
        //今年今日比去年今日的同比
        // 需要对数据进行判断
        String yearOnYear = "0";
        if(atDay == null || atDay.compareTo(0) == 0){
            yearOnYear = "-";
        }else if(lastYearDay == null || lastYearDay.compareTo(0) == 0){
            yearOnYear = "-";
        }else{
            double d = (new BigDecimal(atDay).subtract(new BigDecimal(lastYearDay))).multiply(new BigDecimal(100)).divide(new BigDecimal(lastYearDay), 2, BigDecimal.ROUND_HALF_UP).abs().doubleValue();
            //yearOnYear = MathUtils.subPercent(MathUtils.percent(yearOnYearGrowth));//百分比去整数,之后去负号
            yearOnYear = d+"";//百分比去整数,之后去负号
        }
        //今年今日比前年今日的同比
        String yearOnBeforeYear = "0";
        if(atDay == null || atDay.compareTo(0) == 0){
            yearOnBeforeYear = "-";
        }else if(beforeYearDay == null || beforeYearDay.compareTo(0) == 0){
            yearOnBeforeYear = "-";
        }else{
            double d = (new BigDecimal(atDay).subtract(new BigDecimal(beforeYearDay))).multiply(new BigDecimal(100)).divide(new BigDecimal(beforeYearDay), 2, BigDecimal.ROUND_HALF_UP).abs().doubleValue();
            yearOnBeforeYear = d+"";//百分比去整数,之后去负号
        }
        //判断今日人数同比去年今日人数
        if (atDay.compareTo(0)==0 || lastYearDay.compareTo(0)==0) {
            //类型1为同比上升，2为下降，0 为平衡，3 不显示箭头
            lastYearOnYearType = "3";
        } else if (atDay < lastYearDay) {
            lastYearOnYearType = "2";
        } else if(atDay > lastYearDay){
            lastYearOnYearType = "1";
        }else{
            lastYearOnYearType = "0";
            // 如果持平，这里对返回数字做处理 0.0%显示不好，改成 0%
            yearOnYear = "0";
        }
        //判断今日人数同比前年今日人数
        if (atDay.compareTo(0)==0 || beforeYearDay.compareTo(0)==0) {
            //类型1为同比上升，2为下降，0 为平衡，3 不显示箭头
            beforeYearOnYearType = "3";
        } else if (atDay < beforeYearDay) {
            beforeYearOnYearType = "2";
        } else if(atDay > beforeYearDay){
            beforeYearOnYearType = "1";
        }else {
            beforeYearOnYearType = "0";
            // 如果持平，这里对返回数字做处理 0.0%显示不好，改成 0%
            yearOnBeforeYear = "0";
        }
        //放入实体类
        SumPeopleIndexVo sumPeople = new SumPeopleIndexVo();
        sumPeople.setTodayPeople(String.valueOf(atDay));//今日人数
        sumPeople.setLastYearPeople(String.valueOf(lastYearDay));//去年今日人数
        sumPeople.setBeforeYearPeople(String.valueOf(beforeYearDay));//前年今日人数
        sumPeople.setLastYearOnYear(yearOnYear);//今日同比去年今日
        sumPeople.setBeforeYearOnYear(yearOnBeforeYear);//今日同比前年今日
        sumPeople.setLastYearOnYearType(lastYearOnYearType);//今日同比前年今日显示类型
        sumPeople.setBeforeYearOnYearType(beforeYearOnYearType);//今日同比前年今日显示类型
        sumPeople.setSource("票务对比");//人数对比类型
        return sumPeople;
    }
    @Override
    public Map<String, Object> getScenicAvgTeam(String vcode, String date) {
        Map<String, Object> resMap = new HashMap<>();
        //创建list
        List<Map> listData = new ArrayList<>();
        // 今日团队
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("vcode", vcode);
        parMap.put("startTime", date+" 00:00:00");
        parMap.put("endTime", date+" 23:59:59");
        List<Map> todayTeams = passengerFlowDao.getTodayTeams(parMap);
        // 昨日团队
        String yesterday = "";
        try {
            yesterday = MathUtils.getAfterDelDay(date);
        } catch (Exception e){
            e.printStackTrace();
        }
        parMap.put("startTime",yesterday+" 00:00:00");
        parMap.put("endTime", yesterday+" 23:59:59");
        List<Map> yesterdayTeams = passengerFlowDao.getTodayTeams(parMap);
        for (Map rmap : todayTeams){
            String tourFrom = rmap.get("tourFrom")+"";
            Map passengerTeamMap = new HashMap();
            passengerTeamMap.put("name",tourFrom);
            String todayTeamNum = rmap.get("num")+"";
            BigDecimal todayNum = BigDecimal.ZERO;
            BigDecimal todayTotalNum = BigDecimal.ZERO;
            BigDecimal yesNum = BigDecimal.ZERO;
            BigDecimal yesTotalNum = BigDecimal.ZERO;
            if(todayTeamNum != null && !"".equals(todayTeamNum)){
                todayNum = new BigDecimal(todayTeamNum);
            }
            for (Map ymap : yesterdayTeams){
                // 这里做一个 今日团队与昨日团队对应比较，如果不比较，则会出现今日 和 昨日的 团队 省份名称不对应
                if(tourFrom.equals(ymap.get("tourFrom"))){
                    String yesterdayTeamNum = ymap.get("num")+"";
                    if(yesterdayTeamNum != null && !"".equals(yesterdayTeamNum)){
                        yesNum = new BigDecimal(yesterdayTeamNum);
                    }
                    break;
                }
            }
            BigDecimal nums = todayNum.add(yesNum);
            // 取两天平均值，除以 2 四舍五入，保留整数
            passengerTeamMap.put("num", nums.divide(new BigDecimal(2), 0, BigDecimal.ROUND_HALF_UP));
            listData.add(passengerTeamMap);
        }
        // 数据列表集合
        resMap.put("provinceList", listData);
        return resMap;
    }
}
