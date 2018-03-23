package com.daqsoft.service.client.passengerFlowService.impl;

import com.daqsoft.commons.core.StringUtil;
import com.daqsoft.constants.Constants;
import com.daqsoft.mapper.client.realpeople.PassengerFlowDao;
import com.daqsoft.mapper.client.scenicspotinfo.BigRealPeopleDao;
import com.daqsoft.mapper.client.ticket.TicketBigDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.passengerFlowService.PassengerTicketService;
import com.daqsoft.service.client.passengerFlowService.RealBigPeopleService;
import com.daqsoft.service.client.ticketanalys.TicketService;
import com.daqsoft.utils.client.DateTools;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.HttpRequestUtil;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 客流量分析
 *
 * @author fy
 */
@Service
public class RealBigPeopleServiceImpl implements RealBigPeopleService {
    // redis中的key值
    private static final String TABLE_NAME = "people:";
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    @Autowired
    private BigRealPeopleDao bigRealPeopleDao;
    @Autowired
    private PassengerTicketService passengerTicketService;
    @Autowired
    private TicketBigDao ticketBigDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PassengerFlowDao passengerFlowDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TicketService ticketService;

    /***
     * 每年月度客流趋势
     * @update lrd 20170920
     * @param vcode
     * @return
     */
    @Override
    public Object getPassengerFlowByMonth(String year, String vcode) {
        List<Big_RealP_Tend> propleVoList = new ArrayList<>();
        //月统计主KEY
        String monthKey = RedisKey.MOUDEL_PASSENGERFLOW+"month:"+"getPassengerFlowByMonth:";
        String[] monthTime ={"01","02","03","04","05","06","07","08","09","10","11","12"};
        //当年
        String nowYear = DateTools.getCurrentYear();
        //当前月
        String nowMonth = DateTools.getCurrentMonths();
        int month = Integer.parseInt(nowMonth);
        int length = 0;
        if(nowYear.equals(year)){
            length = month;
        } else {
            length = monthTime.length;
        }

        for (int i = 0; i < length; i++) {
            Big_RealP_Tend tend = new Big_RealP_Tend();
            String montHk = year+"-"+monthTime[i]+"|"+vcode;
            Object obj= RedisCache.getHash(redisTemplate,monthKey, montHk);
            if(obj!=null){
                Map map = (Map)obj;
                tend.setNum(map.get("num").toString());
                tend.setTime(year+"-"+monthTime[i]);
            }else{
                tend.setNum("0");
                tend.setTime(year+"-"+monthTime[i]);
            }
            propleVoList.add(tend);
        }
        return propleVoList;

    }

    /***
     * 每月客流趋势
     * @update lrd 20170920
     * @param date
     * @param vcode
     * @return
     */
    @Override
    public Object getPassengerFlowByMonth_day(String date, String vcode) {
        List<Big_RealP_Tend> list = new ArrayList<>();
        String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
        String[] day ={"01","02","03","04","05","06","07","08","10","11","12","13","14","15",
                "16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"
        };

        for (int i = 0; i <day.length ; i++) {
            Big_RealP_Tend tend = new Big_RealP_Tend();
            String hk = date+"-"+day[i]+"|"+vcode;
            Object dayObj= RedisCache.getHash(redisTemplate,datKey, hk);
            if(dayObj!=null){
                Map map = (Map)dayObj;
                String num= map.get("num")+"";
                if(!"null".equals(num) && num!=null){
                    tend.setTime(date+"-"+day[i]);
                    tend.setNum(map.get("num").toString());
                    list.add(tend);
                }
            }
        }
             return list;
    }

    @Override
    public int dayPeople(String vcode) {
        //华清宫取的票务数据
        if("f561aaf6ef0bf14d4208bb46a4ccb3ad".equals(vcode)){
//            TicketClTendVo ticketClTendToday = ticketService.getTicketCountAndPriceByDay(vcode, DateUtil.getCurDateStr());
            String vcodemdh = DigestUtils.md5Hex(vcode);
            SumPeopleIndexVo sumPeopleIndexRedis = passengerTicketService.sumPeopleIndexRedis(vcode, vcodemdh, "day");
            Integer atDay = 0;
            if(sumPeopleIndexRedis != null) {
                atDay = Integer.parseInt(sumPeopleIndexRedis.getTodayPeople());
            }
            return atDay;
        }else {
            String dayKey = RedisKey.MOUDEL_PASSENGERFLOW + "day:" + "getPassengerFlowByDay:";
            String time = DateUtil.getCurDateStr();
            String dayhk = time + "|" + vcode;
            Object dayObject = RedisCache.getHash(redisTemplate, dayKey, dayhk);
            if (dayObject != null) {
                Map dmap = (Map) dayObject;
                return Integer.parseInt(dmap.get("num") + "");
            }
            return 0;
        }
    }

    @Override
    public int holidayPeople(String vcode) {
        String year = DateUtil.getCurYearStr();
        String time = DateUtil.getCurDateStr();
        String condition = "&year=" + year;//拼上条件
        //从大数据获取今年的节假日
        String str = HttpRequestUtil.sendGet(Constants.Bigdata + "searchHolidayByYear", condition);//url调用接口
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        JSONArray value = null;
        //创建对象与封装
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Integer result = 0;//节假日总人数
        //节假日的主key
        String holidayKey = RedisKey.MOUDEL_PASSENGERFLOW+"holiday:"+"getPassengerFlowByHoliay:";
        String holidayhk = year+"|"+vcode;
        Object holidayObj= RedisCache.getHash(redisTemplate,holidayKey,holidayhk);
        if(holidayObj != null){
            Map hmap= (Map)holidayObj;
            result = Integer.parseInt(hmap.get("num")+"");
        }
        try {
            value = jasonObject.getJSONArray("datas");
            if (value.size() < 1) {//节假日接口无数据判断
            } else {
                JSONArray json = JSONArray.fromObject(value);
                //日统计的主KEY
                Date thisday = df.parse(time);
                for (int i = 0; i < json.size(); i++) {//循环节假日有几天，查询统计数据
                    JSONObject job = json.getJSONObject(i);
                    String dayTime = String.valueOf(job.get("searchDate"));//循环获取节假日的时间
                    //如果大数据获取的节假日和当前日期相等。证明当前日期属于节假日。
                    //那么需要把当前日期的日缓存取出来。和节假日缓存相加。得到最实时的节假日数据
                    Date holiday = df.parse(dayTime);
                    if(holiday.getTime()==thisday.getTime()){
                        //日期主key
                        String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
                        //按日的实时人数key
                        String hk =dayTime+"|"+vcode;
                        Object obj= RedisCache.getHash(redisTemplate,datKey, hk);
                        if(obj!=null){
                            Map map = (Map)obj;
                            result+=Integer.parseInt(map.get("num")+"");
                           }
                        }
                    }
                }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return result;
    }

    @Override
    public List<Map> legalHolidayPeople(String vcode) {
        List<Map> result = new LinkedList<>();//各节假日总人数
        String []num = {"1","3","4","5","6","7","8"};
        String []names = {"元旦","春节","清明节","五一劳动节","端午节","国庆节","中秋节"};
        String year = DateUtil.getCurYearStr();
        String time = DateUtil.getCurDateStr();
        String yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"year:"+"getPassengerFlowByYear:";
        String yearhk = year+"|"+vcode;
        Object yearobj= RedisCache.getHash(redisTemplate,yearKey,yearhk);
        Map yearSumMap = (Map) yearobj;
        int yearSum = Integer.parseInt(yearSumMap.get("num")+ "");
        NumberFormat numberFormat = NumberFormat.getInstance();
        for(int j=0; j < num.length; j++) {
            String condition = "&year=" + year + "&holiday=" + num[j];//拼上条件
            //从大数据获取今年的节假日
            String str = HttpRequestUtil.sendGet(Constants.Bigdata + "searchHolidayByYear", condition);//url调用接口
            //转换hash为json
            JSONObject jasonObject = JSONObject.fromObject(str);
            //遍历封装
            JSONArray value = null;
            //创建对象与封装
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Map mapHoliday = new HashMap();
            //节假日的主key
            try {
                value = jasonObject.getJSONArray("datas");
                if (value.size() < 1) {//节假日接口无数据判断
                } else {
                    JSONArray json = JSONArray.fromObject(value);
                    //日统计的主KEY
                    Date thisday = df.parse(time);
                    Integer sum = 0;//节假日总人数
                    String holidayName = names[j];
                    mapHoliday.put("holidayName",holidayName);
                    String startDate = json.getJSONObject(0).getString("searchDate");
                    String endDate = json.getJSONObject(json.size() - 1).getString("searchDate");
                    mapHoliday.put("startDate",startDate);
                    mapHoliday.put("endDate",endDate);
                    for (int i = 0; i < json.size(); i++) {//循环节假日有几天，查询统计数据
                        JSONObject job = json.getJSONObject(i);
                        String dayTime = String.valueOf(job.get("searchDate"));//循环获取节假日的时间
                        Date holiday = df.parse(dayTime);
                        if (holiday.getTime() <= thisday.getTime()) {
                            //日期主key
                            String datKey = RedisKey.MOUDEL_PASSENGERFLOW + "day:" + "getPassengerFlowByDay:";
                            //按日的实时人数key
                            String hk = dayTime + "|" + vcode;
                            Object obj = RedisCache.getHash(redisTemplate, datKey, hk);
                            if (obj != null) {
                                Map map = (Map) obj;
                                sum += Integer.parseInt(map.get("num") + "");
                            }
                        }else {
                            sum += 0;
                        }
                    }
                    mapHoliday.put("sum",sum);
                    numberFormat.setMaximumFractionDigits(2);
                    String percentage = numberFormat.format((float) sum / (float) yearSum * 100);
                    mapHoliday.put("percentage",percentage);
                }
            } catch (Exception e) {
                e.getStackTrace();
            }
            result.add(mapHoliday);
        }
        return result;
    }

    @Override
    public Map maxPeople(String vcode) {
        String year = DateTools.getCurrentYear();
        String yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"MaxPassenger:";
        String yearhk = year+"|"+vcode;
        Object yearObject= RedisCache.getHash(redisTemplate,yearKey, yearhk);
        Map map = new HashMap();
        if(yearObject != null) {
            map = (Map) yearObject;
        }
        return map;
    }

    @Override
    public Map teamSumYear(String vcode) {
        String year = DateTools.getCurrentYear();
        Map parMap = new HashMap();
        parMap.put("vcode",vcode);
        parMap.put("startTime",year+"-01-01 00:00:00");
        parMap.put("endTime",year+"-12-31 23:59:59");
        int toursNums = ticketBigDao.findToursNumsByDay(parMap);
        Map map = new HashMap();
        map.put("sum",toursNums);
        return map;
    }

    @Override
    public Map avergePassengerYear(String vcode) {
        Map map = new HashMap();
        String yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"year:"+"getPassengerFlowByYear:";
        String year = DateUtil.getCurYearStr();
        String yearhk = year+"|"+vcode;
        Object yearobj= RedisCache.getHash(redisTemplate,yearKey,yearhk);
        if(yearobj!=null) {
            Map mapParm = new HashMap();
            mapParm.put("vcode",vcode);
            mapParm.put("year",year);
            String key = RedisKey.MOUDEL_PASSENGERFLOW+"numberOfDays:"+"getTotalDays:";
            String hk = year+"|"+vcode;
            Object obj= RedisCache.getHash(redisTemplate,key,hk);
            Map num = (Map) obj;
            int days = 0;
            if(num != null && num.get("num") != null){
                days = Integer.parseInt(num.get("num") +"");
            }
            if(days > 0) {
                Map dmap = (Map) yearobj;
                int sum = Integer.parseInt(dmap.get("num")+"");
                int day = Math.round(sum / days);
                map.put("num",day);
            }else {
                map.put("num",0);
            }
        }else {
            map.put("num",0);
        }
        return map;
    }

    @Override
    public int getYearPeople(String vcode) {
        String yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"year:"+"getPassengerFlowByYear:";
        String year = DateUtil.getCurYearStr();
        String yearhk = year+"|"+vcode;
        Object yearobj= RedisCache.getHash(redisTemplate,yearKey,yearhk);
        if(yearobj!=null){
            Map dmap = (Map)yearobj;
            return Integer.parseInt(dmap.get("num")+"");
        }
        return 0;
    }

    @Override
    public List<Big_RealP_Tend> getPassengerFlowByDay(String date, String vcode, String end) {
            List<Big_RealP_Tend> list = new ArrayList<>();
             //获取当前结束时间前6天的时间
             List<String> dayList = DateUtil.getSubsectionDateList(date,end);
            //日统计的主KEY
             String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
             for (int i = 0; i <dayList.size() ; i++) {
                Big_RealP_Tend tend2 = new Big_RealP_Tend();
                String dayHk = dayList.get(i)+"|"+vcode;
                Object dayObj= RedisCache.getHash(redisTemplate,datKey, dayHk);
                if(dayObj!=null){
                    Map dayMap = (Map)dayObj;
                    tend2.setNum(dayMap.get("num")+"");
                    tend2.setTime(dayList.get(i));
                }else{
                    tend2.setNum("0");
                    tend2.setTime(dayList.get(i));
                }
                list.add(tend2);
            }
            return list;
    }

    @Override
    public List<Map> getPassengerFlowByYear(String date, String vcode, String end) {
        List<Map> list = new ArrayList<>();
            //年的大KEY TODO
            String yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"year:"+"getPassengerFlowByYear:";
            for (int i = Integer.parseInt(date); i <=Integer.parseInt(end) ; i++) {
                Map tend1 = new HashMap();
                String yearHk = i+"|"+vcode;
                Object yearObj= RedisCache.getHash(redisTemplate,yearKey, yearHk);
                if(yearObj!=null){
                    Map map =(Map)yearObj;
                    tend1.put("num",map.get("num")+"");
                    tend1.put("time",i+"");
                }else{
                    tend1.put("num","0");
                    tend1.put("time",i+"");
                }
                list.add(tend1);
            }
        return list;
    }

    /**
     * 每日客流趋势  TODO 2018 1.4
     *
     * @param date
     * @param vcode
     * @return
     */
    @Override
    public List<Tend> getPassengerFlowByHours(String date, String vcode) {
           List<Tend> tendVoList = new ArrayList<>();
            //按小时主KEY
            String hoursKey = RedisKey.MOUDEL_PASSENGERFLOW+"hours:"+"getPassengerFlowByHour:";
            String hourHk = date+"|"+vcode;
            Object obj= RedisCache.getHash(redisTemplate,hoursKey, hourHk);
            if(null!=obj){
                List<Map<String,Object>> realPeopleList =(List<Map<String,Object>>)obj;
                //景区第一次有数据的时间
                int strT = Integer.parseInt(realPeopleList.get(0).get("time").toString());
                //当前小时
                int now = Integer.parseInt(DateTools.getCurrentHH());
                int i = 0;
                if(now > 20){
                    now = 20;
                }
                for (int j = strT; j<= now; j++) {
                    Tend tend = new Tend();
                    //判断循环取值不溢出，若溢出将赋值为0同时取之前的总和
                    if(i < realPeopleList.size()) {
                        int time = Integer.parseInt(realPeopleList.get(i).get("time").toString());
                        //判断循环时间和对应数据的时间是否相同，不同复制0同时取之前的总和
                        if (time == j) {
                            tend.setTime(realPeopleList.get(i).get("time").toString());
                            String num = realPeopleList.get(i).get("num").toString();
                            String percent = Math.round(Double.valueOf(num) * 1000000 / Double.valueOf(realPeopleList.get(i).get("maxquantity").toString())) / 10000.0 + "";
                            tend.setNum(num);
                            tend.setPercent(percent);
                            String gt_num = realPeopleList.get(i).get("sum").toString();
                            tend.setSum(String.valueOf(gt_num));
                            i++;
                        } else {
                            tend.setTime(j + "");
                            tend.setNum("0");
                            tend.setSum(realPeopleList.get(i - 1).get("sum").toString());
                            String percent = Math.round(Double.valueOf(realPeopleList.get(i - 1).get("num").toString()) * 1000000 / Double.valueOf(realPeopleList.get(i - 1).get("maxquantity").toString())) / 10000.0 + "";
                            tend.setPercent(percent);
                            j++;
                        }
                    }else {
                        tend.setTime(j + "");
                        tend.setNum("0");
                        tend.setSum(realPeopleList.get(i - 1).get("sum").toString());
                        String percent = Math.round(Double.valueOf(realPeopleList.get(i - 1).get("num").toString()) * 1000000 / Double.valueOf(realPeopleList.get(i - 1).get("maxquantity").toString())) / 10000.0 + "";
                        tend.setPercent(percent);
                    }
                        tendVoList.add(tend);
                }
            }
        return tendVoList;
    }

    @Override
    public Object getPassengerFlowByQuarter(String year, String vcode) {
            List<Big_RealP_Tend> propleVoList = new ArrayList<>();
            //按月的主key
            String dayKey = RedisKey.MOUDEL_PASSENGERFLOW+"month:"+"getPassengerFlowByMonth:";
            String[] oneQuater ={"-01","-02","-03"};
            //获取第一季度取缓存读取
            int oneNum = 0;
            for (int i = 0; i < oneQuater.length; i++) {
                String hk = year+oneQuater[i]+"|"+vcode;
                Object obj1= RedisCache.getHash(redisTemplate,dayKey, hk);
                if(obj1!=null){
                    Map map1= (Map)obj1;
                    oneNum += Integer.parseInt(map1.get("num").toString());
                }else{
                    oneNum += 0;
                }
            }
            Big_RealP_Tend tend1=new Big_RealP_Tend();
            tend1.setTime("第一季度");
            tend1.setNum(String.valueOf(oneNum));
            propleVoList.add(tend1);
            //获取第二季度日期取缓存读取
            String[] twoQuater ={"-04","-05","-06"};
            int twoNum = 0;
            for (int i = 0; i < twoQuater.length; i++) {
                String hk = year+twoQuater[i]+"|"+vcode;
                Object obj2= RedisCache.getHash(redisTemplate,dayKey, hk);
                if(obj2!=null){
                    Map Map2 = (Map)obj2;
                    twoNum += Integer.parseInt(Map2.get("num").toString());
                }else{
                    twoNum += 0;
                }
            }
            Big_RealP_Tend tend2=new Big_RealP_Tend();
            tend2.setTime("第二季度");
            tend2.setNum(String.valueOf(twoNum));
            propleVoList.add(tend2);
            //获取第三季度日期取缓存读取
            String[] threeQuater ={"-07","-08","-09"};
            int threeNum = 0;
            for (int i = 0; i < threeQuater.length; i++) {
                String hk = year+threeQuater[i]+"|"+vcode;
                Object obj3= RedisCache.getHash(redisTemplate,dayKey, hk);
                if(obj3!=null){
                    Map Map3 = (Map)obj3;
                    threeNum += Integer.parseInt(Map3.get("num").toString());
                }else{
                    threeNum += 0;
                }
            }
            Big_RealP_Tend tend3=new Big_RealP_Tend();
            tend3.setTime("第三季度");
            tend3.setNum(String.valueOf(threeNum));
            propleVoList.add(tend3);
            String[] fourQuater ={"-10","-11","-12"};
            int FourNum = 0;
            for (int i = 0; i < fourQuater.length; i++) {
                String hk = year+fourQuater[i]+"|"+vcode;
                Object obj4= RedisCache.getHash(redisTemplate,dayKey, hk);
                if(obj4!=null){
                    Map Map4 = (Map)obj4;
                    FourNum += Integer.parseInt(Map4.get("num").toString());
                }else{
                    FourNum += 0;
                }
            }
            Big_RealP_Tend tend4=new Big_RealP_Tend();
            tend4.setTime("第四季度");
            tend4.setNum(String.valueOf(FourNum));
            propleVoList.add(tend4);
            return propleVoList;
    }

    @Override
    public List<Home_page> find_home_page(String vcode) {
        List<Home_page> home_pageList = new LinkedList<Home_page>();
        Map paramMap = new HashMap<>();
        paramMap.put("vcode", vcode);
        home_pageList = bigRealPeopleDao.find_home_page(paramMap);
        return home_pageList;
    }

    @Override
    public List<Big_RealP_Tend> findPeakByDay(Map map) {
        String dayKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
        List<Big_RealP_Tend> data = new ArrayList<>();
        //获取日期段内的所有日期
        List<String> dayList = DateUtil.getSubsectionDateList(map.get("startTime").toString(),map.get("endTime").toString());
        //初始值
        int min,max;
        min=max=0;//将最大值和最小值都设置为0
        String maxTime = null;
        String minTime = null;
        for (int i = 0; i <dayList.size() ; i++) {
            String dayHk = dayList.get(i)+"|"+map.get("vcode");
            Object dayObj= RedisCache.getHash(redisTemplate,dayKey, dayHk);
            if(dayObj!=null){
                Map map4 = (Map)dayObj;
                int num = Integer.parseInt(map4.get("num").toString());
                if(num>max){
                    // 判断最大值
                    max=num;
                    min = num;
                    maxTime = dayList.get(i);
                }
                if(num<min) {
                    // 判断最小值
                    min=num;
                    minTime =dayList.get(i);
                }
              }
        }
        if(max!=0 && min!=0){//实际比较有值产生。不然无异议
            Big_RealP_Tend maxTend = new Big_RealP_Tend();
            maxTend.setNum(String.valueOf(max));
            maxTend.setTime(maxTime);
            Big_RealP_Tend minTend = new Big_RealP_Tend();
            minTend.setNum(String.valueOf(min));
            minTend.setTime(minTime);
            data.add(maxTend);
            data.add(minTend);
        }
        return data;
    }

    @Override
    public List<Big_RealP_Tend> findPeakByMonth(Map map) {
        String monthKey =  RedisKey.MOUDEL_PASSENGERFLOW+"month:"+"getPassengerFlowByMonth:";
        String date =map.get("date").toString();
        List<Big_RealP_Tend> data = new ArrayList<>();
        int mmax,mmin;
        mmax = mmin = 0;
        String maxMonth = null;
        String minMonth = null;
        //取12个月的数据进行对比
        String[] month = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        for (int i = 0; i <month.length ; i++) {
            String  monthHk =date+"-"+month[i]+"|"+map.get("vcode");
            Object monthobj = RedisCache.getHash(redisTemplate, monthKey, monthHk);
            if(monthobj!=null){
                Map mmap = (Map)monthobj;
                int mnum = Integer.parseInt(mmap.get("num").toString());
                if(mnum>mmax){
                    // 判断最大值
                    mmax = mnum;
                    mmin = mnum;
                    maxMonth = date+"-"+month[i];
                }
                if(mnum<mmin) {
                    // 判断最小值
                    mmin = mnum;
                    minMonth =date+"-"+month[i];
                }
            }
        }
        if(mmax!=0 && mmin!=0) {//实际比较有值产生。不然无异议
            Big_RealP_Tend maxTendMonth = new Big_RealP_Tend();
            maxTendMonth.setNum(String.valueOf(mmax));
            maxTendMonth.setTime(maxMonth);
            Big_RealP_Tend minTendMonth = new Big_RealP_Tend();
            minTendMonth.setNum(String.valueOf(mmin));
            minTendMonth.setTime(minMonth);
            data.add(maxTendMonth);
            data.add(minTendMonth);
        }
        return data;
    }

    /**
     * 根据表名和key值获取对应的redis数据
     *
     * @param table redis的key
     * @param key   获取redis数据的hashKey
     * @return
     */
    private Object getDataFromRedis(String table, String key) {
        HashOperations hash = stringRedisTemplate.opsForHash();
        Object str = hash.get(table, key);
        System.out.println(table);
        System.out.println(key);
        return str;
    }

    /**
     * 将json对象转存到list中（大数据）
     *
     * @param resultJson json对象
     * @return
     */
    private List<Big_RealP_Tend> jsonToList(JSONObject resultJson, List<String> list) {
        List<Big_RealP_Tend> Big_RealP_TendVoList = new ArrayList<>();
        // 若list不为空，则需要以list进行循环作为key返回给前端，无数据的补0
        if (list == null || list.size() == 0) {
            for (Object jsonKey : resultJson.keySet()) {
                Big_RealP_Tend big_RealP_TendVo = new Big_RealP_Tend();
                Object value = resultJson.get(jsonKey);
                // 若key值为空，则直接不用往前端返数据
                if (jsonKey == null) {
                    continue;
                }
                big_RealP_TendVo.setTime(jsonKey.toString());
                big_RealP_TendVo.setNum(value == null ? "0" : value.toString());
                Big_RealP_TendVoList.add(big_RealP_TendVo);
            }
        } else {
            for (String time : list) {
                Big_RealP_Tend big_RealP_TendVo = new Big_RealP_Tend();
                Object value = resultJson.get(time);
                big_RealP_TendVo.setTime(time);
                big_RealP_TendVo.setNum(value == null ? "0" : value.toString());
                Big_RealP_TendVoList.add(big_RealP_TendVo);
            }
        }
        return Big_RealP_TendVoList;
    }

    @Override
    public List<Big_RealP_Tend> findHistoryPassengerByDay(String start, String vcode, String end) {
        //获取时间段内的所有日期，并获取缓存进行排序。
        List<String> list = DateUtil.getSubsectionDateList(start,end);
        //日主KEY
        String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
        List<Big_RealP_Tend> datalist= new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            Big_RealP_Tend tend =new Big_RealP_Tend();
            String hk = list.get(i)+"|"+vcode;
            Object dayObj =RedisCache.getHash(redisTemplate,datKey, hk);
            if(dayObj!=null){
                Map map = (Map)dayObj;
                tend.setTime(list.get(i));
                tend.setNum(map.get("num").toString());
                datalist.add(tend);
           }
        }
        Collections.sort(datalist,new Comparator<Big_RealP_Tend>() {
            public int compare(Big_RealP_Tend o1, Big_RealP_Tend o2) {
                //进行降序排列
                if (Integer.parseInt(o1.getNum()) < Integer.parseInt(o2.getNum())) {
                    return 1;
                }
                if (Integer.parseInt(o1.getNum()) == Integer.parseInt(o2.getNum())) {
                    return 0;
                }
                return -1;
            }
        });
        //当大于10的时候去前10
        if(datalist.size()>10){
            return datalist.subList(0,10);
        }else{
            return datalist;
        }
    }

    @Override
    public List<Big_Ticket_Money> findMoneyByDay(Map<String, String> map) {
        return bigRealPeopleDao.find_money_date(map);
    }


    @Override
    public List<Big_Ticket_Money> findMoneyByMonth(Map<String, String> map) {
        return bigRealPeopleDao.find_money_date(map);
    }


    /**
     * 大数据客流来源分析
     *
     * @param vcode 景区编码
     * @param year  年份
     * @return
     */
    @Override
    public List<CarComeFromVo> getBigPeopleYear(String vcode, String year) {
        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("year", year);
        List<CarComeFromVo> carComeFromVos = bigRealPeopleDao.getBigPeopleYear(map);
        return carComeFromVos;
    }

    @Override
    public List<CarComeFromCarVo> getBigPeopleYearCity(Map<String, Object> parMap) {
        return bigRealPeopleDao.getBigPeopleYearCity(parMap);
    }

    public Map getQuarter(String type, String vcode, String date) {
        Map map = new HashMap();
        if (type.equals("vitality")) {
            map.put("date", date + "-01");
            map.put("end", date + "-03");
        } else if (type.equals("summer")) {
            map.put("date", date + "-04");
            map.put("end", date + "-06");
        } else if (type.equals("autumn")) {
            map.put("date", date + "-07");
            map.put("end", date + "-09");
        } else if (type.equals("wintertide")) {
            map.put("date", date + "-10");
            map.put("end", date + "-12");
        }
        map.put("vcode", vcode);
        return map;
    }

    /**
     * 实时客流监测 景区舒适度分析
     * @param vcode
     * @return
     * @Comment:
     */
    @Override
    public Map<String,Object> find_oneday_dateAndReal(String date, String vcode) {
        Map<String,Object> resultMap = new HashMap<>();

        List<Tend> tendVoList = new ArrayList<>();
        try {
            if (StringUtil.isEmpty(date) || StringUtil.isEmpty(vcode)) {
                return resultMap;
            }
            date = new SimpleDateFormat("yyyy年MM月dd日").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));

            // 拼接redis的hashKey
            String key = date + DigestUtils.md5Hex(vcode);
            // 从redis中获取数据
            Object resultStr = getDataFromRedis(TABLE_NAME + TABLE_NAME, key);
            if (resultStr == null) {
                return resultMap;
            }
            JSONObject resultJson = JSONObject.fromObject(resultStr);
            if (resultJson == null) {
                return resultMap;
            }
            long gt_num = 0;
            String maxQuantity = resultJson.getString("maxquantity");
            List<String> list = DateUtil.getTimeList("time");
            List<Big_RealP_Tend> realPeopleList = jsonToList(resultJson, list);
            for (Big_RealP_Tend real : realPeopleList) {
                Tend tend = new Tend();
                tend.setTime(real.getTime()+":00");

                String percent = Math.round(Double.valueOf(real.getNum()) * 1000000 / Double.valueOf(maxQuantity))/10000.0 + "";
                tend.setNum(real.getNum());
                tend.setPercent(percent);
                gt_num = gt_num + Long.valueOf(real.getNum());
                tend.setSum(String.valueOf(gt_num));
                tendVoList.add(tend);

                if (real.getTime().equals(DateUtil.getCurSimpleHourStr())){
                    resultMap.put("num",real.getNum());
                    resultMap.put("percent",percent);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //DateUtil.getCurSimpleHourStr()
        resultMap.put("tendVoList",tendVoList);
        return resultMap;
    }



    /**
     * 微件：景区景点数据分析
     * 今日景点游客数
     * 昨日景点游客数
     * 景点最大承载量
     * @param vcode
     * @return LIST<map>
     */
    @Override
    public List<Map<String, Object>> getScenicPeople(String vcode) {
        //获取今天的日期
        String thisDay = DateUtil.getCurDateStr();
        //获取昨天的日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date1 = calendar.getTime();
        String lastDay =new SimpleDateFormat("yyyy-MM-dd").format(date1);
        //景点主KEY
        String scenicKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenic:day:"+"getPassengerFlowByScenic:";
        String thisDayKey= thisDay+"|"+vcode;
        String lastDayKey = lastDay+"|"+vcode;
        Object thisObj = RedisCache.getHash(redisTemplate,scenicKey,thisDayKey);
        Object lastObj = RedisCache.getHash(redisTemplate,scenicKey,lastDayKey);
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<Map<String,Object>> thisList = new ArrayList<>();
        List<Map<String,Object>> lastList = new ArrayList<>();
        if(thisObj!=null){
            thisList = (List<Map<String,Object>>)thisObj;
        }
        if(lastObj!=null){
            lastList = (List<Map<String,Object>>)lastObj;
        }
        //两天都有数据
        if(thisList!=null && lastList!=null && thisList.size() > 0 && lastList.size() > 0){
            for (int i = 0; i <thisList.size() ; i++) {
                for (int j = 0; j <lastList.size() ; j++) {
                    String scenicOne = thisList.get(i).get("scenicName")+"";
                    String scenicTwo= lastList.get(j).get("scenicName")+"";
                    if(scenicOne.equals(scenicTwo)){
                        Map<String,Object> map = new HashMap<>();
                        map.put("scenicName",scenicTwo);
                        map.put("thisDayNum",thisList.get(i).get("num"));
                        map.put("lastDayNum",lastList.get(j).get("num"));
                        map.put("maxquantity",thisList.get(j).get("maxquantity"));
                        resultList.add(map);
                        continue;
                    }
                }

            }
        }else if(thisList!=null && thisList.size() > 0){//只有今天有数据
            for (int i = 0; i <thisList.size() ; i++) {
                String scenicOne = thisList.get(i).get("scenicName")+"";
                Map<String,Object> map = new HashMap<>();
                map.put("scenicName",scenicOne);
                map.put("thisDayNum",thisList.get(i).get("num"));
                map.put("lastDayNum",0);
                map.put("maxquantity",thisList.get(i).get("maxquantity"));
                resultList.add(map);
                }
        }else{//只有昨天有数据
            for (int i = 0; i <lastList.size() ; i++) {
                String scenicOne = lastList.get(i).get("scenicName")+"";
                Map<String,Object> map = new HashMap<>();
                map.put("scenicName",scenicOne);
                map.put("thisDayNum",0);
                map.put("lastDayNum",lastList.get(i).get("num"));
                map.put("maxquantity",lastList.get(i).get("maxquantity"));
                resultList.add(map);
            }
        }
        return resultList;
    }
    /**
     * 微件：景区景点数据分析
     * 今日景点游客数
     * 昨日景点游客数
     * 平均景点人数
     * @param vcode
     * @return LIST<map>
     */
    @Override
    public List<Map<String, Object>> getScenicPeopleAvg(String vcode) {
        //获取今天的日期
        String thisDay = DateUtil.getCurDateStr();
        //获取昨天的日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date1 = calendar.getTime();
        String lastDay =new SimpleDateFormat("yyyy-MM-dd").format(date1);
        //景点主KEY
        String scenicKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenic:day:"+"getPassengerFlowByScenic:";
        String thisDayKey= thisDay+"|"+vcode;
        String lastDayKey = lastDay+"|"+vcode;
        Object thisObj = RedisCache.getHash(redisTemplate,scenicKey,thisDayKey);
        Object lastObj = RedisCache.getHash(redisTemplate,scenicKey,lastDayKey);
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<Map<String,Object>> thisList = new ArrayList<>();
        List<Map<String,Object>> lastList = new ArrayList<>();
        if(thisObj!=null){
            thisList = (List<Map<String,Object>>)thisObj;
        }
        if(lastObj!=null){
            lastList = (List<Map<String,Object>>)lastObj;
        }
        //两天都有数据
        if(thisList!=null && lastList!=null){
            for (int i = 0; i <thisList.size() ; i++) {
                for (int j = 0; j <lastList.size() ; j++) {
                    String scenicOne = thisList.get(i).get("scenicName")+"";
                    String scenicTwo= lastList.get(j).get("scenicName")+"";
                    if(scenicOne.equals(scenicTwo)){
                        Map<String,Object> map = new HashMap<>();
                        map.put("scenicName",scenicTwo);
                        int thisNum = Integer.parseInt(thisList.get(i).get("num")+"");
                        int lastNum = Integer.parseInt(lastList.get(j).get("num")+"");
                        map.put("num",(thisNum+lastNum)/2);
                        resultList.add(map);
                        continue;
                    }
                }

            }
        }else if(thisList!=null){//只有今天有数据
            for (int i = 0; i <thisList.size() ; i++) {
                String scenicName = thisList.get(i).get("scenicName")+"";
                Map<String,Object> map = new HashMap<>();
                map.put("scenicName",scenicName);
                map.put("num",thisList.get(i).get("num"));
                resultList.add(map);
            }
        }else{//只有昨天有数据
            for (int i = 0; i <lastList.size() ; i++) {
                String scenicName = lastList.get(i).get("scenicName")+"";
                Map<String,Object> map = new HashMap<>();
                map.put("scenicName",scenicName);
                map.put("num",lastList.get(i).get("num")+"");
                resultList.add(map);
            }
        }
        return resultList;
    }
}
