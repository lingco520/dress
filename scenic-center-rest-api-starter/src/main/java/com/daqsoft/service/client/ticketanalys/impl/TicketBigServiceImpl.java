package com.daqsoft.service.client.ticketanalys.impl;

import com.daqsoft.config.MyProps;
import com.daqsoft.mapper.client.onlineticketing.OnlineTicketingDao;
import com.daqsoft.mapper.client.ticket.TicketBigDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.ticketanalys.TicketBigService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.DateUtils;
import com.daqsoft.utils.client.Growth;
import com.daqsoft.utils.client.MathUtils;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.*;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.list.TreeList;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-06 15:38.
 * @description 票务分析业务类
 */
@Service
public class TicketBigServiceImpl implements TicketBigService {

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private MyProps myProps;

    public static final int PAGESIZE = 6;

    @Autowired
    private TicketBigDao ticketBigDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    private OnlineTicketingDao onlineTicketingDao;


    @Autowired
    private RedisTemplate redisTemplate;

    //redis中的key值
    private static final String TABLE_NAME = "ticket:";
    private static final String OTA_TABLE_NAME = "ota:";
    private static final String BIG_TABLENAME = "big_ticket:";
    private static final String BIG_DATE = "big_data:";
    private static final String OTA_BIG_TABLENAME = "big_otabar:";
    private static final String DB_TABLENAME = "db_ticket:";
    private static final String TOUR_TYPE = "tourtype:";
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TIME = "time";
    private static final String QUARTER = "quarter";
    private static final String OTA_TICKET_TYPE = "ota:ticket_type:";
    private static final String COLON = ":";
    private static final String TIMESLOT = "timeSlot";

    /**
     * 获取每日各小时票务数据
     * 2017-08-03 zhouq 修改 加入缓存
     *
     * @param vcode
     * @param date
     * @return
     */
    @Override
    public List<TicketClSsTendVO> getRealTimeTicketByDay(String vcode, String date) {

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("date", date);
        paramMap.put("vcode", vcode);

        //按天获取个小时数据
        String h = TABLE_NAME + BIG_TABLENAME + TIME + COLON;
        String md5Key = DigestUtils.md5Hex(vcode);
        String hk = DAY + "|" + date + "|" + md5Key;
        List<TicketClSsTendVO> tendVOList = (List<TicketClSsTendVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (tendVOList == null || tendVOList.size() == 0) {
            tendVOList = ticketBigDao.getRealTimeData(paramMap);
            if (tendVOList.size() > 0) {
                /*变量*/
                long ticket_nums = 0;
                for (TicketClSsTendVO ticketClSs_tendVO : tendVOList) {
                    ticket_nums += Long.valueOf(ticketClSs_tendVO.getTicket_num());
                    ticketClSs_tendVO.setTicket_nums(ticket_nums + "");
                }
                RedisCache.putHash(redisTemplate, h, hk, tendVOList);
            }
        }

        return tendVOList;
    }

    /**
     * 获取指定年份月度票务数据
     * 2017-08-03 zhouq 修改 加入缓存
     *
     * @param dateTime
     * @param vcode
     * @param types
     * @return
     */
    @Override
    public List<TicketClTendVo> fundAll_date(String dateTime, String vcode, String types) {
        List<TicketClTendVo> result = new LinkedList<TicketClTendVo>();
        if (MONTH.equals(types)) {
            result = getBigTicketDayByMonth(vcode, dateTime);
        } else if (YEAR.equals(types)) {
            result = getBigTicketMonthByYear(vcode, dateTime);
        }
        return result;
    }

    /**
     * 票务分析-线上线下对比，通过不用的时间维度查询景区在ota上的销量数据
     *
     * @param paramMap 类型、时间维度等参数
     * @return
     */
    @Override
    public List<Map> getOnlineOfflineTikCompare(Map<String, Object> paramMap) {
        //暂时微件只有年度12个月的线上线线下对比，多个时间维度参数为后面做预留
        List<Map> showList = new ArrayList();
        //获取月数组，尝到到本月
        Integer currMonth = Integer.parseInt(DateUtil.getCurMonthStr().split("-")[1]);
        String[] monthArr = new String[currMonth];
        Object year = paramMap.get("year");
        for (int i = 0; i < currMonth; i++) {
            if (i < 9) {
                monthArr[i] = year+"-"+"0" + (i + 1);
            } else {
                monthArr[i] = year+"-"+String.valueOf(i + 1);
            }
        }
        //线上、线下处理
        showList = this.getDataHandleByYear(paramMap, monthArr);
        return showList;
    }

    @Override
    public Map findToursByDay(String day, String vcode) throws Exception{
        Map resMap = new HashMap();
        Map parMap = new HashMap();
        parMap.put("vcode", vcode);
        parMap.put("startTime", day + " 00:00:00");
        parMap.put("endTime", day + " 23:59:59");
        List<ToursVo> toursList = ticketBigDao.findToursByDay(parMap);
        int toursNums = ticketBigDao.findToursNumsByDay(parMap);
        int peopleNums = ticketBigDao.findToursPeopleNumsByDay(parMap);
        resMap.put("toursNums", toursNums);
        resMap.put("peopleNums", peopleNums);
        resMap.put("toursList", toursList);
        return resMap;
    }

    private List<Map> getDataHandleByYear(Map<String, Object> paramMap, String[] timeArr) {
        List<Map> dataList = new ArrayList();
        try {
            String vcode = paramMap.get("vcode").toString();
            String startYear = paramMap.get("year").toString();
            String endTime = String.valueOf(Integer.parseInt(startYear) + 1);
            paramMap.put("startTime", startYear + "-01-01 00:00:00");
            paramMap.put("endTime", endTime + "-01-01 00:00:00");

            List<TicketClTendVo> offlineList = new LinkedList<TicketClTendVo>();
            List<OtaTendVo> onlineList = new LinkedList<OtaTendVo>();

            //查询出线上线下的数据List 票务组了缓存。。ota是没有做缓存的
            offlineList = getBigTicketMonthByYear(vcode, startYear);
            onlineList = onlineTicketingDao.getOnlineCountByMonth(paramMap);
            for (String month : timeArr) {
                Map map = new HashMap();
                // ota 返回数据封装
                String ota_num = "0";
                String ota_totalPrice = "0";
                for (OtaTendVo otaTendVo : onlineList) {
                    if (month.equals(otaTendVo.getOta_time())) {
                        ota_num = otaTendVo.getOta_num();
                        ota_totalPrice = otaTendVo.getOta_totalPrice();
                        break;
                    }
                }
                // ticket 返回数据封装
                String ticket_num = "0";
                String ticket_totalPrice = "0";
                for (TicketClTendVo ticketClTendVo : offlineList) {
                    if (month.equals(ticketClTendVo.getTicket_time())) {
                        ticket_num = ticketClTendVo.getTicket_num();
                        ticket_totalPrice = ticketClTendVo.getTicket_totalPrice();
                        break;
                    }
                }
                // 返回数据
                map.put("time", month);
                map.put("ota_num", ota_num);
                map.put("ota_totalPrice", ota_totalPrice);

                map.put("ticket_num", ticket_num);
                map.put("ticket_totalPrice", ticket_totalPrice);
                dataList.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dataList;
    }



    /**
     * 获取时间段内数据（年月日）
     *
     * @param date  date根据对应数据 传入相应参数 如month：2017-06
     * @param vcode
     * @param types
     * @param end
     * @return
     */
    @Override
    public List<TicketClTendVo> fundAll_date_end(String date, String vcode, String types, String end) {
        List<TicketClTendVo> ticketIndexSumVoList = new ArrayList<>();
        String startTime = "";
        String endTime = "";
        if ("day".equals(types)) {
            startTime = date + " 00:00:00";
            endTime = end + " 23:59:59";
            ticketIndexSumVoList = getBigTicketNumByTimeSlotByDay(vcode, startTime, endTime);
        } else if ("month".equals(types)) {
            startTime = date + "-01 00:00:00";
            endTime = end + "-31 23:59:59";
            ticketIndexSumVoList = getBigTicketNumByTimeSlotByMonth(vcode, startTime, endTime);
        } else if ("year".equals(types)) {
            startTime = date + "-01-01 00:00:00";
            endTime = end + "-12-31 23:59:59";

            /**
             * 2017-08-04 zhouq
             * 先获取缓存,无数据再走历史数据
             */
            ticketIndexSumVoList = getBigTicketNumByTimeSlotByYear(vcode, Integer.valueOf(date), Integer.valueOf(end));
//            ticketIndexSumVoList = getBigTicketNumByTimeSlotByYear(vcode, startTime, endTime);
        }

        return ticketIndexSumVoList;
    }

    /**
     * 按季度查询票务张数
     * 2017-08-04 zhouq 修改 已加入缓存
     *
     * @param
     * @param vcode
     * @return
     */
    @Override
    public List<TicketClTendVo> find_quarter_date(String year, String vcode) {

        String md5key = DigestUtils.md5Hex(vcode);

        //redis 缓存key
        String h = TABLE_NAME + BIG_TABLENAME + QUARTER + COLON;
        String hk = YEAR + "|" + year + "|" + md5key;
        //优先读取缓存
        List<TicketClTendVo> bigTicketMonthByYear = (List<TicketClTendVo>) RedisCache.getHash(redisTemplate, h, hk);
        if (bigTicketMonthByYear != null && bigTicketMonthByYear.size() > 0)
            return bigTicketMonthByYear;

        bigTicketMonthByYear = getBigTicketMonthByYear(vcode, year);
        int q1, q2, q3, q4, q11, q22, q33, q44;
        q1 = q2 = q3 = q4 = 0;
        q11 = q22 = q33 = q44 = 0;


        String time;
        String mon;
        for (TicketClTendVo tendVo : bigTicketMonthByYear) {
            time = tendVo.getTicket_time();
            if (time == null || time.split("-").length != 2)
                continue;
            mon = time.split("-")[1];
            switch (mon) {
                case "01":
                case "02":
                case "03":
                    q1 += Integer.valueOf(tendVo.getTicket_num());
                    q11 += Integer.valueOf(tendVo.getTicket_totalPrice());
                    break;
                case "04":
                case "05":
                case "06":
                    q2 += Integer.valueOf(tendVo.getTicket_num());
                    q22 += Integer.valueOf(tendVo.getTicket_totalPrice());
                    break;
                case "07":
                case "08":
                case "09":
                    q3 += Integer.valueOf(tendVo.getTicket_num());
                    q33 += Integer.valueOf(tendVo.getTicket_totalPrice());
                    break;
                case "10":
                case "11":
                case "12":
                    q4 += Integer.valueOf(tendVo.getTicket_num());
                    q44 += Integer.valueOf(tendVo.getTicket_totalPrice());
                    break;
            }
        }

        //List<TicketClTendVo> ticket_cl_tendList = getBigTicket(vcode, strToFormatStrYear(year), QUARTER);
        List<TicketClTendVo> result = new TreeList();
        result.add(new TicketClTendVo("第一季度", String.valueOf(q1), String.valueOf(q11)));
        result.add(new TicketClTendVo("第二季度", String.valueOf(q2), String.valueOf(q22)));
        result.add(new TicketClTendVo("第三季度", String.valueOf(q3), String.valueOf(q33)));
        result.add(new TicketClTendVo("第四季度", String.valueOf(q4), String.valueOf(q44)));
        //写入缓存
        RedisCache.putHash(redisTemplate, h, hk, result);

        return result;
    }

    /**
     * 每月客票种类销售分布
     *
     * @param date
     * @param vcode
     * @param types
     * @return
     */
    @Override
    public List<TicketClTypeVO> find_Ticket_cl_Type_s(String date, String vcode, String types) {
        List<TicketClTypeVO> result = new ArrayList<>();
        if (DAY.equals(types)) {
            result = getTicketTypeByDay(vcode, date);
        } else if (MONTH.equals(types)) {
            result = getTicketTypeByMonth(vcode, date);
        } else if (YEAR.equals(types)) {
            result = getTicketTypeByYear(vcode, date);
        }
        return result;
    }

    /**
     * 分段日期客票种类销量分布 （按年月日）
     *
     * @param date
     * @param end
     * @param vcode
     * @param types
     * @return
     */
    @Override
    public List<TicketClTypeVO> find_Ticket_cl_Type_se(String date, String end, String vcode, String types) {
        List<TicketClTypeVO> result = new ArrayList<>();
        if (vcode == null || date == null || end == null) {
            return result;
        }
        if (date.compareTo(end) > 0) {
            return result;
        }
        String startTime = "";
        String endTime = "";
        //由于sql中使用的between字段来进行判断，所以此处需要对字符串进行拼接成完整时间
        if (DAY.equals(types)) {
            startTime = date + " 00:00:00";
            endTime = end + " 23:59:59";

            //获取景区分段数据
            return ticketBigDao.getTicketTypeByTimeSlot(vcode, date, end);

        } else if (MONTH.equals(types)) {
            startTime = date + "-01 00:00:00";
            endTime = end + "-31 23:59:59";
        } else if (YEAR.equals(types)) {
//            startTime = date + "-01-01 00:00:00";
//            endTime = end + "-12-31 23:59:59";
            /**
             * 2017-08-07 zhouq 添加
             *
             * 分段获取数据 分别获取各年份数据再组装.
             * 避免直接读取库
             */
            return getTicketTypeByYearSlot(vcode, Integer.valueOf(date), Integer.valueOf(end));
        }
        result = ticketBigDao.getTicketTypeByTimeSlot(vcode, startTime, endTime);
        return result;
    }

    /**
     * 每季度客票种类销售分布
     *
     * @param date
     * @param vcode
     * @param type
     * @return
     */
    @Override
    public List<TicketClTypeVO> find_Ticket_cl_Type_quarter(String date, String vcode, String type) {
        String quarter = "";
        if (type.equals("vitality")) {
            quarter = "1";
        } else if (type.equals("summer")) {
            quarter = "2";
        } else if (type.equals("autumn")) {
            quarter = "3";
        } else if (type.equals("wintertide")) {
            quarter = "4";
        }
        return getTicketTypeByQuarter(vcode, date, quarter);
    }

    /**
     * 查询年月日游客构成
     *
     * @param date
     * @param types
     * @param vcode
     * @return
     */
    @Override
    public List<TicketClTypeVO> find_tourist_cl_Type_s(String date, String types, String vcode) {
        List<TicketClTypeVO> result = new ArrayList<>();
        if (DAY.equals(types)) {
            return getTouristTypeByDay(vcode, date);
            // result = getTouristType(vcode, strToFormatStrDay(date), DAY);
        } else if (MONTH.equals(types)) {
//            result = getTouristType(vcode, strToFormatStrMonth(date), MONTH);
            return getTouristTypeByMonth(vcode, date);
        } else if (YEAR.equals(types)) {
//            result = getTouristType(vcode, strToFormatStrYear(date), YEAR);
            return getTouristTypeByYear(vcode, date);
        }
        return result;
    }

    /**
     * 获取时间段内游客构成统计
     *
     * @param date
     * @param end
     * @param vcode
     * @param types
     * @return
     */
    @Override
    public List<TicketClTypeVO> find_tourist_cl_Type_se(String date, String end, String vcode, String types) {
        List<TicketClTypeVO> jqTicketPeopleFormVoList = new ArrayList<>();
        if (vcode == null || date == null || "".equals(date) || end == null || "".equals(end)) {
            return jqTicketPeopleFormVoList;
        }
        String startTime = "";
        String endTime = "";
        if (DAY.equals(types)) {

            startTime = date + " 00:00:00";
            endTime = end + " 23:59:59";


        } else if (MONTH.equals(types)) {
            startTime = date + "-01 00:00:00";
            endTime = end + "-31 23:59:59";
        } else if (YEAR.equals(types)) {
//            startTime = date + "-01-01 00:00:00";
//            endTime = end + "-12-31 23:59:59";
            Assert.paramIsYear(date, "开始年份格式不正确");
            Assert.paramIsYear(end, "结束年份格式不正确");
            return getTouristTypeByYearSlot(vcode, Integer.valueOf(date), Integer.valueOf(end));
        }
        jqTicketPeopleFormVoList = ticketBigDao.getTouristNumByTimeSlot(vcode, startTime, endTime);
        return jqTicketPeopleFormVoList;
    }

    /**
     * 按季度统计游客构成
     *
     * @param year
     * @param vcode
     * @param type
     * @return
     */
    @Override
    public List<TicketClTypeVO> find_tourist_cl_Type_quarter(String year, String vcode, String type) {

        String quarter = "";
        if ("vitality".equals(type)) {
            quarter = "1";
        } else if ("summer".equals(type)) {
            quarter = "2";
        } else if ("autumn".equals(type)) {
            quarter = "3";
        } else if ("wintertide".equals(type)) {
            quarter = "4";
        }
        return getTouristTypeByQuarter(vcode, year, quarter);
    }


    /**
     * 获取游客构成统计
     *
     * @param vcode
     * @param date
     * @param type
     * @return
     */
    private List<TicketClTypeVO> getTouristType(String vcode, String date, String type) {
        List<TicketClTypeVO> jqTicketPeopleFormVoList = new ArrayList<>();
        if (vcode == null || date == null || "".equals(date)) {
            return jqTicketPeopleFormVoList;
        }
        String key = date + DigestUtils.md5Hex(vcode);
        Object resultStr = getDataFromRedis(TABLE_NAME + TOUR_TYPE + type + COLON, key);
        if (resultStr == null) {
            return jqTicketPeopleFormVoList;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        for (Object jsonKey : resultJson.keySet()) {
            if (jsonKey == null) {
                continue;
            }
            Object valueO = resultJson.get(jsonKey);
            long value = Long.valueOf(valueO.toString());
            TicketClTypeVO jqTicketPeopleFormVo = new TicketClTypeVO();
            jqTicketPeopleFormVo.setTypes(jsonKey.toString());
            jqTicketPeopleFormVo.setNums(value);
            jqTicketPeopleFormVoList.add(jqTicketPeopleFormVo);
        }
        return jqTicketPeopleFormVoList;
    }

    /**
     * 2017-08-07 zhouq
     * 按天 获取游客构成统计
     *
     * @param vcode 景区代码
     * @param day   日期
     * @return
     */
    private List<TicketClTypeVO> getTouristTypeByDay(String vcode, String day) {

        List<TicketClTypeVO> ticketClTypeVOS = new ArrayList<>();
        if (StringUtils.isBlank(vcode) || StringUtils.isBlank(day) || !DateUtils.isValidDate(day)) {
            return ticketClTypeVOS;
        }

        String md5Key = DigestUtils.md5Hex(vcode);
        //优先读取缓存
        String h = TABLE_NAME + TOUR_TYPE + DAY + COLON;
        String hk = DAY + "|" + day + "|" + md5Key;
        ticketClTypeVOS = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (ticketClTypeVOS != null && ticketClTypeVOS.size() > 0)
            return ticketClTypeVOS;

        //无缓存数据 查询db 并写入缓存.
        ticketClTypeVOS = ticketBigDao.getTouristNumByTimeSlot(vcode, day + " 00:00:00", day + " 23:59:59");
        if (ticketClTypeVOS.size() > 0)
            RedisCache.putHash(redisTemplate, h, hk, ticketClTypeVOS);
        return ticketClTypeVOS;
    }

    /**
     * 2017-08-14 zhouq
     * 分日期区间获取游客构成统计
     *
     * @param vcode
     * @param startDate
     * @param endDate
     * @return
     */
    private List<TicketClTypeVO> getTouristTypeByDaySlot(String vcode, String startDate, String endDate) {

        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);

        String h = TABLE_NAME + TOUR_TYPE + DAY + COLON + "slot:";
        String hk = startDate + "|" + endDate + "|" + DigestUtils.md5Hex(vcode);
        //获取缓存数据
        List<TicketClTypeVO> resultList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (resultList != null && resultList.size() > 0)
            return resultList;

        resultList = new ArrayList<>();

        //获取日期区间所有的日期
        List<String> subsectionDateList = DateUtil.getSubsectionDateList(startDate, endDate);

        List<Future<List<TicketClTypeVO>>> futureList = new ArrayList<>();
        //使用多线程去拉取数据.
        //线程池的大小 暂不设定
        ExecutorService es = Executors.newCachedThreadPool();
        for (String date : subsectionDateList) {
            futureList.add(es.submit(new TouristTypeByDayTask(vcode, date)));
        }

        for (Future<List<TicketClTypeVO>> listFuture : futureList) {
            try {
                resultList.addAll(listFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

//        for (String s : subsectionDateList) {
//            List<TicketClTypeVO> touristTypeByDay = getTouristTypeByDay(vcode, s);
//            resultList.addAll(touristTypeByDay);
//        }

        resultList = touristTypeGroupStatistics(resultList);

        if (resultList.size() > 0)
            RedisCache.putHash(redisTemplate, h, hk, resultList);

        return resultList;
    }

    /**
     * 2017-08-07 zhouq
     * 按月份 获取游客构成统计
     *
     * @param vcode 景区代码
     * @param month 月份
     * @return
     */
    private List<TicketClTypeVO> getTouristTypeByMonth(String vcode, String month) {

        List<TicketClTypeVO> ticketClTypeVOS = new ArrayList<>();
        if (StringUtils.isBlank(vcode) || StringUtils.isBlank(month) || !DateUtils.isValidMonth(month)) {
            return ticketClTypeVOS;
        }

        String md5Key = DigestUtils.md5Hex(vcode);
        //优先读取缓存
        String h = TABLE_NAME + TOUR_TYPE + MONTH + COLON;
        String hk = MONTH + "|" + month + "|" + md5Key;
        ticketClTypeVOS = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (ticketClTypeVOS != null && ticketClTypeVOS.size() > 0)
            return ticketClTypeVOS;
        try {
            //无缓存数据
            //第一种方式: 查询db 并写入缓存.
            //ticketClTypeVOS = ticketBigDao.getTouristNumByTimeSlot(vcode, month + "-01 00:00:00", month + "-31 23:59:59");

            List<TicketClTypeVO> touristTypeS = new ArrayList<>();

            Date parse = DateUtil.MONTH_SDF.parse(month);
            /**
             * 第二种方式: 在缓存中获取每月的各天数据进行累加
             *
             */
            List<String> dayReport = DateUtil.getDayReport(parse);
            for (String s : dayReport) {
                List<TicketClTypeVO> touristTypeByDay = getTouristTypeByDay(vcode, s);
                touristTypeS.addAll(touristTypeByDay);
            }

            //数据分类
            ticketClTypeVOS = touristTypeGroupStatistics(touristTypeS);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (ticketClTypeVOS != null && ticketClTypeVOS.size() > 0)
            RedisCache.putHash(redisTemplate, h, hk, ticketClTypeVOS);
        return ticketClTypeVOS;
    }

    /**
     * 2017-08-07 zhouq
     * 按年 获取游客构成统计
     *
     * @param vcode 景区代码
     * @param year  年份
     * @return
     */
    private List<TicketClTypeVO> getTouristTypeByYear(String vcode, String year) {

        List<TicketClTypeVO> ticketClTypeVOS = new ArrayList<>();
        if (StringUtils.isBlank(vcode) || StringUtils.isBlank(year) || !DateUtils.isValidYear(year)) {
            return ticketClTypeVOS;
        }

        String md5Key = DigestUtils.md5Hex(vcode);
        //优先读取缓存
        String h = TABLE_NAME + TOUR_TYPE + YEAR + COLON;
        String hk = YEAR + "|" + year + "|" + md5Key;
        ticketClTypeVOS = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (ticketClTypeVOS != null && ticketClTypeVOS.size() > 0)
            return ticketClTypeVOS;

//        //无缓存数据 查询db 并写入缓存.
//        ticketClTypeVOS = ticketBigDao.getTouristNumByTimeSlot(vcode, year + "-01-01 00:00:00", year + "-12-31 23:59:59");

        /**
         * 第二种方式: 在缓存中获取当年每月的数据进行累加
         *
         */
        ticketClTypeVOS = new ArrayList<>();
        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        for (String month : months) {
            List<TicketClTypeVO> touristTypeByMonth = getTouristTypeByMonth(vcode, year + "-" + month);
            ticketClTypeVOS.addAll(touristTypeByMonth);
        }

        //数据进行分类
        ticketClTypeVOS = touristTypeGroupStatistics(ticketClTypeVOS);
        if (ticketClTypeVOS.size() > 0)
            RedisCache.putHash(redisTemplate, h, hk, ticketClTypeVOS);
        return ticketClTypeVOS;
    }

    /**
     * 2017-08-11 zhouq
     * 按年区间 获取游客构成统计
     *
     * @param vcoe
     * @param startYear
     * @param endYear
     * @return
     */
    private List<TicketClTypeVO> getTouristTypeByYearSlot(String vcoe, int startYear, int endYear) {

        if (endYear > startYear)
            return new ArrayList<>();

        //开始年份结束年份 相等 直接返回一个即可 返回年份的数据不需要再 归类
        if (endYear == startYear) {
            List<TicketClTypeVO> touristTypeByYear = getTouristTypeByYear(vcoe, String.valueOf(startYear));
            return touristTypeByYear;
        }

        //开始年份小于结束年份 则获取各年份的数据 然后需要进行归类.
        List<TicketClTypeVO> clTypeVOList = new ArrayList<>();
        for (int year = startYear; year <= endYear; year++) {
            clTypeVOList.addAll(getTouristTypeByYear(vcoe, String.valueOf(year)));
        }

        //数据进行一次归类统计
        clTypeVOList = touristTypeGroupStatistics(clTypeVOList);

        return clTypeVOList;
    }

    /**
     * 2017-08-07 zhouq
     * 按年 获取游客构成统计
     *
     * @param vcode   景区代码
     * @param year    年份
     * @param quarter 季度 1,2,3,4
     * @return
     */
    private List<TicketClTypeVO> getTouristTypeByQuarter(String vcode, String year, String quarter) {

        List<TicketClTypeVO> jqTicketSealVoList = new ArrayList<>();
        if (vcode == null || year == null || "".equals(year) || quarter == null || "".equals(quarter)) {
            return jqTicketSealVoList;
        }

        String md5key = DigestUtils.md5Hex(vcode);

        //redis 缓存key
        String H = TABLE_NAME + TOUR_TYPE + QUARTER + COLON;
        String HK = QUARTER + "|" + year + "_" + quarter + "|" + md5key;

        //读取缓存
        jqTicketSealVoList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, H, HK);
        if (jqTicketSealVoList != null && jqTicketSealVoList.size() > 0)
            return jqTicketSealVoList;

//        //缓存无数据 执行查询db
//        String startTime = "";
//        String endTime = "";
//        switch (quarter) {
//            case "1":
//                startTime = year + "-01-01 00:00:00";
//                endTime = year + "-03-31 23:59:59";
//                break;
//            case "2":
//                startTime = year + "-04-01 00:00:00";
//                endTime = year + "-06-31 23:59:59";
//                break;
//            case "3":
//                startTime = year + "-07-01 00:00:00";
//                endTime = year + "-09-31 23:59:59";
//                break;
//            case "4":
//                startTime = year + "-10-01 00:00:00";
//                endTime = year + "-12-31 23:59:59";
//                break;
//        }
//
//
//
//        jqTicketSealVoList = ticketBigDao.getTouristNumByTimeSlot(vcode, startTime, endTime);

        String monthStr = "";
        switch (quarter) {
            case "1":
                monthStr = "01,02,03";
                break;
            case "2":
                monthStr = "04,05,06";
                break;
            case "3":
                monthStr = "07,08,09";
                break;
            case "4":
                monthStr = "10,11,12";
                break;
        }

        if (StringUtils.isBlank(monthStr))
            return new ArrayList<>();

        //读取当前季度下各月份的具体数据
        String[] months = monthStr.split(",");
        jqTicketSealVoList = new ArrayList<>();
        for (String month : months) {
            List<TicketClTypeVO> touristTypeByMonth = getTouristTypeByMonth(vcode, year + "-" + month);
            jqTicketSealVoList.addAll(touristTypeByMonth);
        }

        //最后对数据进行分类处理.
        jqTicketSealVoList = touristTypeGroupStatistics(jqTicketSealVoList);

        //写入缓存
        if (jqTicketSealVoList.size() > 0) {
            RedisCache.putHash(redisTemplate, H, HK, jqTicketSealVoList);
        }

        return jqTicketSealVoList;

    }


    /**
     * 2017-08-04 zhouq 修改. 新增了缓存
     * 按季度查询票务类型数量及金额
     *
     * @param vcode
     * @param year
     * @param quarter
     * @return
     */
    private List<TicketClTypeVO> getTicketTypeByQuarter(String vcode, String year, String quarter) {
        List<TicketClTypeVO> jqTicketSealVoList = new ArrayList<>();
        if (vcode == null || year == null || "".equals(year) || quarter == null || "".equals(quarter)) {
            return jqTicketSealVoList;
        }

        String md5key = DigestUtils.md5Hex(vcode);

        //redis 缓存key
        String H = TABLE_NAME + TABLE_NAME + QUARTER + COLON;
        String HK = QUARTER + "|" + year + "_" + quarter + "|" + md5key;

        //读取缓存
        jqTicketSealVoList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, H, HK);
        if (jqTicketSealVoList != null && jqTicketSealVoList.size() > 0)
            return jqTicketSealVoList;

        //缓存无数据 执行查询db
        String startTime = "";
        String endTime = "";
        switch (quarter) {
            case "1":
                startTime = year + "-01-01 00:00:00";
                endTime = year + "-03-31 23:59:59";
                break;
            case "2":
                startTime = year + "-04-01 00:00:00";
                endTime = year + "-06-31 23:59:59";
                break;
            case "3":
                startTime = year + "-07-01 00:00:00";
                endTime = year + "-09-31 23:59:59";
                break;
            case "4":
                startTime = year + "-10-01 00:00:00";
                endTime = year + "-12-31 23:59:59";
                break;
        }

        jqTicketSealVoList = ticketBigDao.getTicketTypeByTimeSlot(vcode, startTime, endTime);

        //对票务类型进行分类
        jqTicketSealVoList = ticketTypeGroupStatistics(jqTicketSealVoList);
        //写入缓存
        if (jqTicketSealVoList.size() > 0) {
            RedisCache.putHash(redisTemplate, H, HK, jqTicketSealVoList);
        }

        return jqTicketSealVoList;
    }

    private String getQuarterName(String str) {
        String quarter = "";
        switch (str) {
            case "1":
                quarter = "第一季度";
                break;
            case "2":
                quarter = "第二季度";
                break;
            case "3":
                quarter = "第三季度";
                break;
            case "4":
                quarter = "第四季度";
                break;
            default:
                break;
        }
        return quarter;
    }

    /**
     * 获取票务类型数据
     *
     * @param vcode 景区编码
     * @param date  日期 2017-05-12
     * @return
     */
    private List<TicketClTypeVO> getTicketType(String vcode, String date, String type) {
        List<TicketClTypeVO> jqTicketSealVoList = new ArrayList<>();
        if (vcode == null || date == null || "".equals(date)) {
            return jqTicketSealVoList;
        }
        String key = date + DigestUtils.md5Hex(vcode);
        Object resultStr = getDataFromRedis(TABLE_NAME + TABLE_NAME + type + COLON, key);
        if (resultStr == null) {
            return jqTicketSealVoList;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        double count = 0.00;
        for (Object jsonKey : resultJson.keySet()) {
            if (jsonKey == null) {
                continue;
            }
            Object valueO = resultJson.get(jsonKey);
            long value = Long.valueOf(valueO.toString());
            count += value;
            TicketClTypeVO jqTicketSealVo = new TicketClTypeVO();
            jqTicketSealVo.setTypes(jsonKey.toString());
            jqTicketSealVo.setNums(value);
            jqTicketSealVoList.add(jqTicketSealVo);
        }
        return jqTicketSealVoList;
    }

    /**
     * 2017-08-04 zhouq
     * 按月份获取票务类型及金额数据
     *
     * @param vcode
     * @param month 月份
     * @return
     */
    private List<TicketClTypeVO> getTicketTypeByMonth(String vcode, String month) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTypeVO> clTypeVOList;

        //redis 缓存key
        String h = TABLE_NAME + TABLE_NAME + MONTH + COLON;
        String hk = MONTH + "|" + month + "|" + md5key;

        //读取缓存
        clTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (clTypeVOList != null && clTypeVOList.size() > 0) {
            return clTypeVOList;
        }
        //读取db
        clTypeVOList = ticketBigDao.getTicketTypeByTimeSlot(vcode, month + "-1 00:00:00", month + "-31 23:59:59");

        //对数据进行分类.
        clTypeVOList = ticketTypeGroupStatistics(clTypeVOList);
        //写入缓存
        if (clTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, clTypeVOList);
        }

        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();
    }

    /**
     * 2017-08-04 zhouq
     * 按月份获取票务类型及金额数据
     *
     * @param vcode
     * @param day   天
     * @return
     */
    private List<TicketClTypeVO> getTicketTypeByDay(String vcode, String day) {
//        List<TicketClTypeVO> ticketClTypeVOS = ticketBigDao.getTicketTypeByTimeSlot(vcode, day + " 00:00:00", day + " 23:59:59");
//
//        //return ticketClTypeVOS;

        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTypeVO> clTypeVOList;

        //redis 缓存key
        String h = TABLE_NAME + TABLE_NAME + DAY + COLON;
        String hk = DAY + "|" + day + "|" + md5key;

        //读取缓存
        clTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (clTypeVOList != null && clTypeVOList.size() > 0) {
            return clTypeVOList;
        }
        //读取db
        clTypeVOList = ticketBigDao.getTicketTypeByTimeSlot(vcode, day + " 00:00:00", day + " 23:59:59");

        //对数据进行分类.
        clTypeVOList = ticketTypeGroupStatistics(clTypeVOList);
        //写入缓存
        if (clTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, clTypeVOList);
        }

        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();

    }

    /**
     * 2017-08-04 zhouq
     * 按月份获取票务类型及金额数据
     *
     * @param vcode
     * @param year  年份
     * @return
     */
    private List<TicketClTypeVO> getTicketTypeByYear(String vcode, String year) {
        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTypeVO> clTypeVOList;

        //redis 缓存key
        String h = TABLE_NAME + TABLE_NAME + YEAR + COLON;
        String hk = YEAR + "|" + year + "|" + md5key;

        //读取缓存
        clTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (clTypeVOList != null && clTypeVOList.size() > 0) {
            return clTypeVOList;
        }
        //读取db
        clTypeVOList = ticketBigDao.getTicketTypeByTimeSlot(vcode, year + "-01-01 00:00:00", year + "-12-31 23:59:59");

        //对数据进行分类.
        clTypeVOList = ticketTypeGroupStatistics(clTypeVOList);

        //写入缓存
        if (clTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, clTypeVOList);
        }

        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();

    }

    /**
     * 获取按年区间的票务类型
     *
     * @param vcode     景区代码
     * @param startYear 开始年
     * @param endYear   结束年
     * @return
     */
    private List<TicketClTypeVO> getTicketTypeByYearSlot(String vcode, int startYear, int endYear) {
        List<TicketClTypeVO> clTypeVOList = new ArrayList<>();
        if (startYear > endYear)
            return new ArrayList<>();
        if (startYear == endYear)
            return getTicketTypeByYear(vcode, String.valueOf(startYear));

        Map<String, Long> countMap = new HashMap<>();
        Map<String, Integer> priceMap = new HashMap<>();
        //获取每年票务类型统计数据.
        long nums;
        int typecal;
        Long oldCount;
        Integer oldPrice;
        for (int i = startYear; i <= endYear; i++) {
            List<TicketClTypeVO> ticketTypeByYear = getTicketTypeByYear(vcode, String.valueOf(i));
            for (TicketClTypeVO clTypeVO : ticketTypeByYear) {
                String types = clTypeVO.getTypes();
                nums = clTypeVO.getNums();
                typecal = clTypeVO.getTypecal();
                oldCount = countMap.get(types) != null ? countMap.get(types) : 0L;
                oldPrice = priceMap.get(types) != null ? priceMap.get(types) : 0;
                countMap.put(types, nums + oldCount);
                priceMap.put(types, typecal + oldPrice);
            }
        }

        //循环

        TicketClTypeVO clTypeVO;
        for (Map.Entry<String, Long> entry : countMap.entrySet()) {
            clTypeVO = new TicketClTypeVO();
            clTypeVO.setTypes(entry.getKey());
            clTypeVO.setNums(entry.getValue());
            clTypeVO.setTypecal(priceMap.get(entry.getKey()));
            clTypeVOList.add(clTypeVO);
        }
        return clTypeVOList;
    }

    /**
     * 获取传入时间段的数据按天进行统计返回
     *
     * @param vcode
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回对应时间段按天统计数据的list
     */
    private List<TicketClTendVo> getBigTicketNumByTimeSlotByDay(String vcode, String startTime, String endTime) {
        List<TicketClTendVo> result = new ArrayList<>();
        if (vcode == null || startTime == null || endTime == null) {
            return result;
        }
        if (!compareDateStr(startTime, endTime)) {
            return result;
        }

        //先从缓存获取数据
        String md5Key = DigestUtils.md5Hex(vcode);
        //redis 缓存key
        String h = TABLE_NAME + BIG_TABLENAME + DAY + COLON + TIMESLOT + COLON;
        String hk = startTime + "|" + endTime + "|" + md5Key;
        result = (List<TicketClTendVo>) RedisCache.getHash(redisTemplate, h, hk);


        //有数据 直接返回
        if (result != null && result.size() > 0) {
            return result;
        } else {
            //无数据 查询db 写入Redis
            result = getBigTicketNumByTimeSlot(vcode, startTime, endTime, "%Y-%m-%d");
            if (result.size() > 0) {
                RedisCache.putHash(redisTemplate, h, hk, result);
            }
        }
        return result;
    }


    /**
     * 获取传入时间段的数据按月进行统计返回
     *
     * @param vcode
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回对应时间段按月统计数据的list
     */
    private List<TicketClTendVo> getBigTicketNumByTimeSlotByMonth(String vcode, String startTime, String endTime) {
        return getBigTicketNumByTimeSlot(vcode, startTime, endTime, "%Y-%m");
    }

    /**
     * 获取传入时间段的数据按年进行统计返回
     *
     * @param vcode
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回对应时间段按年统计数据的list
     */
    private List<TicketClTendVo> getBigTicketNumByTimeSlotByYear(String vcode, String startTime, String endTime) {
        return getBigTicketNumByTimeSlot(vcode, startTime, endTime, "%Y");
    }

    /**
     * 2017-08-04 zhouq
     * <p>
     * 传入景区代码及 年份区间 获取票务年度统计数据
     * 优先获取缓存,无数据再获取年度数据
     *
     * @param vcode
     * @param startYear
     * @param endYear
     * @return
     */
    private List<TicketClTendVo> getBigTicketNumByTimeSlotByYear(String vcode, int startYear, int endYear) {
        List<TicketClTendVo> ticketClTendVos = new ArrayList<>();
        if (startYear > endYear)
            return ticketClTendVos;
        if (startYear == endYear) {
            ticketClTendVos.add(getBigTicketNumByYear(vcode, startYear));
            return ticketClTendVos;
        }

        for (int year = startYear; year <= endYear; year++) {
            ticketClTendVos.add(getBigTicketNumByYear(vcode, year));
        }

        return ticketClTendVos;
    }


    /**
     * 根据年份获取景区票务数据
     *
     * @param vcode
     * @param year
     * @return
     */
    @Override
    public TicketClTendVo getBigTicketNumByYear(String vcode, int year) {

        String h = TABLE_NAME + BIG_TABLENAME + YEAR + COLON;
        String hk = YEAR + "|" + year + "|" + DigestUtils.md5Hex(vcode);

        //优先读取缓存
        TicketClTendVo tendVo = (TicketClTendVo) RedisCache.getHash(redisTemplate, h, hk);
        if (tendVo != null && tendVo.getTicket_num() != null && !"0".equals(tendVo.getTicket_num()))
            return tendVo;
        //获取每年各月份数据kjnjk
        List<TicketClTendVo> bigTicketMonthByYear = getBigTicketMonthByYear(vcode, String.valueOf(year));
        if (bigTicketMonthByYear == null || bigTicketMonthByYear.size() == 0)
            return new TicketClTendVo(String.valueOf(year), "0");

        int num = 0;
        int totalPrice = 0;
        for (TicketClTendVo tendVo1 : bigTicketMonthByYear) {
            num += Integer.valueOf(tendVo1.getTicket_num());
            totalPrice += Integer.valueOf(tendVo1.getTicket_totalPrice());
        }
        //写入缓存
        tendVo = new TicketClTendVo(String.valueOf(year), String.valueOf(num), String.valueOf(totalPrice));
        RedisCache.putHash(redisTemplate, h, hk, tendVo);
        return tendVo;
    }

    /**
     * 根据传入时间段来获取统计数据，并根据传入format格式对时间进行格式化并进行分组
     * 开始时间和结束时间只支持yyyy-MM-dd
     *
     * @param vcode     景区编码
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    用于对sql语句中日期进行format的数据
     * @return 返回时间list
     */
    private List<TicketClTendVo> getBigTicketNumByTimeSlot(String vcode, String startTime, String endTime, String format) {
        List<TicketClTendVo> result = new ArrayList<>();
        if (vcode == null || startTime == null || endTime == null || format == null) {
            return result;
        }
        if (!compareDateStr(startTime, endTime)) {
            return result;
        }
        //由于sql中使用的between字段来进行判断，所以此处需要对字符串拼接时分秒，以免漏掉部分数据
        String st = startTime + " 00:00:00";
        String end = endTime + " 23:59:59";

        result = ticketBigDao.getBigTicketNumByTimeSlot(vcode, st, end, format);
        return result;
    }

    /**
     * 比较两个时间字符串的大小 开始时间小于或等于结束时间返回true
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    private boolean compareDateStr(String startTime, String endTime) {
        String format = "yyyy-MM-dd";
        boolean flag = false;
        if (!DateUtil.isDateStr(startTime, format) || !DateUtil.isDateStr(endTime, format)) {
            return flag;
        }
        int num = startTime.compareTo(endTime);
        if (num == 0 || num < 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取票务分析大数据统计数据（按各景区年进行获取）
     *
     * @param vcode 景区编码
     * @return 返回所有年份list
     */
    public List<TicketClTendVo> getBigTicketYear(String vcode) {
        List<TicketClTendVo> ticketIndexSumVoList = new ArrayList<>();
        if (vcode == null) {
            return ticketIndexSumVoList;
        }
        //拼接redis的hashKey
        String key = DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis(TABLE_NAME + BIG_TABLENAME + YEAR + COLON, key);
        if (resultStr == null) {
            return ticketIndexSumVoList;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return ticketIndexSumVoList;
        }
        ticketIndexSumVoList = jsonToList(resultJson, null);
        return ticketIndexSumVoList;
    }

    /**
     * 2017-08-04 zhouq 修改 加入缓存
     * <p>
     * 获取当前年份每个月的票务情况（获取一年中所有月份情况）
     *
     * @param vcode 景区编码
     * @param year  年份
     * @return 返回每个月的数据list
     */
    public List<TicketClTendVo> getBigTicketMonthByYear(String vcode, String year) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTendVo> bigTicket = new ArrayList<>();

        //redis 缓存key
        String h = TABLE_NAME + BIG_TABLENAME + MONTH + COLON;
        String hk = YEAR + "|" + year + "|" + md5key;

        //先走缓存.
        bigTicket = (List<TicketClTendVo>) RedisCache.getHash(redisTemplate, h, hk);
        if (bigTicket != null && bigTicket.size() > 0) {
            return bigTicket;
        }
        //无缓存 走sql 查询
        bigTicket = ticketBigDao.getBigTicketNumByTimeSlot(vcode, year + "-01-01 00:00:00", year + "-12-31 23:59:59", "%Y-%m");

        //写入缓存
        if (bigTicket != null && bigTicket.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, bigTicket);
        }
        return bigTicket != null ? bigTicket : new ArrayList<>();
    }

    /**
     * 根据月份获取本月所有天的票务数据
     * 2017-08-03 zhouq 修改 加入缓存
     *
     * @param vcode
     * @param
     * @return
     */
    public List<TicketClTendVo> getBigTicketDayByMonth(String vcode, String month) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTendVo> bigTicket = new ArrayList<>();

        //redis 缓存key
        String h = TABLE_NAME + BIG_TABLENAME + DAY + COLON;
        String hk = MONTH + "|" + month + "|" + md5key;

        //不管是否是上月数据先走缓存.
        bigTicket = (List<TicketClTendVo>) RedisCache.getHash(redisTemplate, h, hk);
        if (bigTicket != null && bigTicket.size() > 0) {
            return bigTicket;
        }

        //无缓存 走sql 查询
        bigTicket = ticketBigDao.getBigTicketNumByTimeSlot(vcode, month + "-01 00:00:00", month + "-31 23:59:59", "%Y-%m-%d");
        //写入缓存
        if (bigTicket != null && bigTicket.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, bigTicket);
        }

        return bigTicket != null ? bigTicket : new ArrayList<>();
    }

    /**
     * 根据传入的景区vcode以及类型和时间来获取对应的票务统计数据list
     *
     * @param vcode 景区编码
     * @param date  日期 必须传入格式：2017年06月-06日
     * @param type  time/day/month
     * @return 返回对应的数据list 格式：{[time:01月,count:23]}
     */
    public List<TicketClTendVo> getBigTicket(String vcode, String date, String type) {
        List<TicketClTendVo> ticketIndexSumVoList = new ArrayList<>();
        if (vcode == null || date == null || "".equals(date)) {
            return ticketIndexSumVoList;
        }

        //拼接redis的hashKey
        String key = date + DigestUtils.md5Hex(vcode);
        //从redis中获取数据
        Object resultStr = getDataFromRedis(TABLE_NAME + BIG_TABLENAME + type + COLON, key);
        if (resultStr == null) {
            return ticketIndexSumVoList;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return ticketIndexSumVoList;
        }

        List<String> list = DateUtil.getTimeList(type);
        ticketIndexSumVoList = jsonToList(resultJson, list);
        return ticketIndexSumVoList;
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
        return str;
    }

    /**
     * 将json对象转存到list中（大数据）
     *
     * @param resultJson json对象
     * @return
     */
    private List<TicketClTendVo> jsonToList(JSONObject resultJson, List<String> list) {
        List<TicketClTendVo> ticketIndexSumVoList = new ArrayList<>();
        // 若list不为空，则需要以list进行循环作为key返回给前端，无数据的补0
        if (list == null || list.size() == 0) {
            for (Object jsonKey : resultJson.keySet()) {
                TicketClTendVo ticketIndexSumVo = new TicketClTendVo();
                Object value = resultJson.get(jsonKey);
                //若key值为空，则直接不用往前端返数据
                if (jsonKey == null) {
                    continue;
                }
                ticketIndexSumVo.setTicket_time(jsonKey.toString());
                ticketIndexSumVo.setTicket_num(value == null ? "0" : value.toString());
                ticketIndexSumVoList.add(ticketIndexSumVo);
            }
        } else {
            for (String time : list) {
                TicketClTendVo ticketIndexSumVo = new TicketClTendVo();
                Object value = resultJson.get(time);
                ticketIndexSumVo.setTicket_time(time);
                ticketIndexSumVo.setTicket_num(value == null ? "0" : value.toString());
                ticketIndexSumVoList.add(ticketIndexSumVo);
            }
        }
        return ticketIndexSumVoList;
    }

    /**
     * 将日期天转化为标准格式
     *
     * @param dateStr 格式：2017-09-09
     * @return 格式：2017年09月09日
     */
    private String strToFormatStrDay(String dateStr) {
        String str1 = "yyyy-MM-dd";
        String str2 = "yyyy年MM月dd日";
        return strToFormatStr(dateStr, str1, str2);
    }

    /**
     * 将日期月转化为标准格式
     *
     * @param dateStr 格式：2017-09
     * @return 格式：2017年09月
     */
    private String strToFormatStrMonth(String dateStr) {
        String str1 = "yyyy-MM";
        String str2 = "yyyy年MM月";
        return strToFormatStr(dateStr, str1, str2);
    }

    /**
     * 将日期年转化为标准格式
     *
     * @param dateStr 格式：2017
     * @return 格式：2017年
     */
    private String strToFormatStrYear(String dateStr) {
        String str1 = "yyyy";
        String str2 = "yyyy年";
        return strToFormatStr(dateStr, str1, str2);
    }

    /**
     * 将时间格式1换成2
     *
     * @param str
     * @param format1
     * @param format2
     * @return 返回format2格式的字符串
     */
    private String strToFormatStr(String str, String format1, String format2) {
        Date date = DateUtil.strToDate(str, format1);
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
        String res = sdf2.format(date);
        //若长度不相等，则说明传入的时间格式不对 例如：传入2017-01-01 format2:yyyy年  返回的结果则为2017年
        //此处作为对其传值格式的验证
        if (res.length() != format2.length()) {
            return "";
        }
        return res;
    }

    @Override
    public List<Tours_cl_total> findToursTotalByDay(String day, String vcode) {
        List<Tours_cl_total> tours_cl_totalList = new LinkedList<Tours_cl_total>();
        Map paramMap = new HashMap<>();
        paramMap.put("date", day);
        paramMap.put("vcode", vcode);
        tours_cl_totalList = ticketBigDao.find_Tours_cl_total_dd(paramMap);
        return tours_cl_totalList;
    }


    @Override
    public List<Tours_cl_total> findToursTotalByMonth(String month, String vcode) {
        List<Tours_cl_total> tours_cl_totalList = new LinkedList<Tours_cl_total>();
        Map paramMap = new HashMap<>();
        paramMap.put("date", month);
        paramMap.put("vcode", vcode);
        tours_cl_totalList = ticketBigDao.find_Tours_cl_total_mm(paramMap);
        return tours_cl_totalList;
    }


    @Override
    public List<Tours_cl_total> findToursTotalByYear(String year, String vcode) {
        List<Tours_cl_total> tours_cl_totalList = new LinkedList<Tours_cl_total>();
        Map paramMap = new HashMap<>();
        paramMap.put("date", year);
        paramMap.put("vcode", vcode);
        tours_cl_totalList = ticketBigDao.find_Tours_cl_total_yy(paramMap);
        return tours_cl_totalList;
    }


    @Override
    public List<Tours_cl_tendency> findToursTendencyByDay(String day, String vcode) {
        List<Tours_cl_tendency> tours_cl_tendencyList = new LinkedList<Tours_cl_tendency>();
        Map paramMap = new HashMap<>();
        paramMap.put("date", day);
        paramMap.put("vcode", vcode);
        tours_cl_tendencyList = ticketBigDao.find_Tours_cl_tendency_dd(paramMap);
        return tours_cl_tendencyList;
    }

    @Override
    public List<Tours_cl_tendency> findToursTendencyByMonth(String month, String vcode) {
        List<Tours_cl_tendency> tours_cl_tendencyList = new LinkedList<Tours_cl_tendency>();
        Map paramMap = new HashMap<>();
        paramMap.put("date", month);
        paramMap.put("vcode", vcode);
        tours_cl_tendencyList = ticketBigDao.find_Tours_cl_tendency_mm(paramMap);
        return tours_cl_tendencyList;
    }

    @Override
    public List<Tours_cl_tendency> findToursTendencyByYear(String year, String vcode) {
        List<Tours_cl_tendency> tours_cl_tendencyList = new LinkedList<Tours_cl_tendency>();
        Map paramMap = new HashMap<>();
        paramMap.put("date", year);
        paramMap.put("vcode", vcode);
        tours_cl_tendencyList = ticketBigDao.find_Tours_cl_tendency_yy(paramMap);
        return tours_cl_tendencyList;
    }

    @Override
    public List<CutPageBean> findToursLimitByDay(Integer page, String day, String vcode) {
        List<CutPageBean> find_Tours_limit = new LinkedList<CutPageBean>();
        CutPageBean cutPageBean = new CutPageBean();
        Map paramMap = new HashMap<>();
        int pageNo = (page - 1) * PAGESIZE;
        paramMap.put("page", pageNo);
        paramMap.put("date", day);
        paramMap.put("vcode", vcode);
        List<Tours_cl_Tend> tours_cl_tends = new LinkedList<Tours_cl_Tend>();
        Integer count = 0;
        tours_cl_tends = ticketBigDao.find_Tours_limit_dd(paramMap);
        count = ticketBigDao.count_tours_dd(paramMap);
        cutPageBean.setList(tours_cl_tends);
        cutPageBean.setCount(count);
        Integer totalPage = totalPage(count);
        cutPageBean.setTotalPage(totalPage);
        find_Tours_limit.add(cutPageBean);
        return find_Tours_limit;
    }

    @Override
    public List<CutPageBean> findToursLimitByMonth(Integer page, String month, String vcode) {
        List<CutPageBean> find_Tours_limit = new LinkedList<CutPageBean>();
        CutPageBean cutPageBean = new CutPageBean();
        Map paramMap = new HashMap<>();
        int pageNo = (page - 1) * PAGESIZE;
        paramMap.put("page", pageNo);
        paramMap.put("date", month);
        paramMap.put("vcode", vcode);
        List<Tours_cl_Tend> tours_cl_tends = new LinkedList<Tours_cl_Tend>();
        Integer count = 0;
        tours_cl_tends = ticketBigDao.find_Tours_limit_mm(paramMap);
        count = ticketBigDao.count_tours_mm(paramMap);
        cutPageBean.setList(tours_cl_tends);
        cutPageBean.setCount(count);
        Integer totalPage = totalPage(count);
        cutPageBean.setTotalPage(totalPage);
        find_Tours_limit.add(cutPageBean);
        return find_Tours_limit;
    }



    @Override
    public List<CutPageBean> findToursLimitByYear(Integer page, String year, String vcode) {
        List<CutPageBean> find_Tours_limit = new LinkedList<CutPageBean>();
        CutPageBean cutPageBean = new CutPageBean();
        Map paramMap = new HashMap<>();
        int pageNo = (page - 1) * PAGESIZE;
        paramMap.put("page", pageNo);
        paramMap.put("date", year);
        paramMap.put("vcode", vcode);
        List<Tours_cl_Tend> tours_cl_tends = new LinkedList<Tours_cl_Tend>();
        Integer count = 0;
        tours_cl_tends = ticketBigDao.find_Tours_limit_yy(paramMap);
        count = ticketBigDao.count_tours_yy(paramMap);
        cutPageBean.setList(tours_cl_tends);
        cutPageBean.setCount(count);
        Integer totalPage = totalPage(count);
        cutPageBean.setTotalPage(totalPage);
        find_Tours_limit.add(cutPageBean);
        return find_Tours_limit;
    }


    @Override
    public List<Ota_DateSale_Tend> otaSaleByQuarter(String time, String season, String vcode) {
        List<Ota_DateSale_Tend> otaSale = new LinkedList<Ota_DateSale_Tend>();
        Map<String, Object> map = new HashMap<>();
        Date dates = null;
        DateFormat df = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        try {
            dates = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        df = new SimpleDateFormat("yyyy");
        String Quarter = df.format(dates);
        if (season.equals("spring")) {
            map.put("start", Quarter + "-01");
            map.put("end", Quarter + "-03");
            map.put("vcode", vcode);
            List<Ota_Date> otaList = ticketBigDao.otaSaleByQuarter(map);
            for (Ota_Date otaCount : otaList) {
                Ota_DateSale_Tend ota = new Ota_DateSale_Tend();
                ota.setCount(otaCount.getCount());
                ota.setName(otaCount.getName());
                ota.setTotalprice(otaCount.getTOTALPRICE() == null ? 0 : otaCount.getTOTALPRICE());
                otaSale.add(ota);
            }
        } else if (season.equals("summer")) {
            map.put("start", Quarter + "-04");
            map.put("end", Quarter + "-06");
            map.put("vcode", vcode);
            List<Ota_Date> otaList = ticketBigDao.otaSaleByQuarter(map);
            for (Ota_Date otaCount : otaList) {
                Ota_DateSale_Tend ota = new Ota_DateSale_Tend();
                ota.setCount(otaCount.getCount());
                ota.setName(otaCount.getName());
                ota.setTotalprice(otaCount.getTOTALPRICE() == null ? 0 : otaCount.getTOTALPRICE());
                otaSale.add(ota);
            }
        } else if (season.equals("autumn")) {
            map.put("start", Quarter + "-07");
            map.put("end", Quarter + "-09");
            map.put("vcode", vcode);
            List<Ota_Date> otaList = ticketBigDao.otaSaleByQuarter(map);
            for (Ota_Date otaCount : otaList) {
                Ota_DateSale_Tend ota = new Ota_DateSale_Tend();
                ota.setCount(otaCount.getCount());
                ota.setName(otaCount.getName());
                ota.setTotalprice(otaCount.getTOTALPRICE() == null ? 0 : otaCount.getTOTALPRICE());
                otaSale.add(ota);
            }
        } else if (season.equals("winter")) {
            map.put("start", Quarter + "-10");
            map.put("end", Quarter + "-12");
            map.put("vcode", vcode);
            List<Ota_Date> otaList = ticketBigDao.otaSaleByQuarter(map);
            for (Ota_Date otaCount : otaList) {
                Ota_DateSale_Tend ota = new Ota_DateSale_Tend();
                ota.setCount(otaCount.getCount());
                ota.setName(otaCount.getName());
                ota.setTotalprice(otaCount.getTOTALPRICE() == null ? 0 : otaCount.getTOTALPRICE());
                otaSale.add(ota);
            }
        }
        return otaSale;
    }

    /**
     * 按天获取ota销售统计
     *
     * @param startTime
     * @param endTime
     * @param vcode
     * @return
     */
    @Override
    public List<Ota_DateSale_Tend> otaSaleByDay(String startTime, String endTime, String vcode) {


        List<Ota_DateSale_Tend> otaSale = new LinkedList<Ota_DateSale_Tend>();
        Map<String, Object> map = new HashMap<>();
        Date stDate = null;
        Date enDate = null;
        DateFormat df = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            stDate = sdf.parse(startTime);
            enDate = sdf.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        df = new SimpleDateFormat("yyyy-MM-dd");
        map.put("stDate", df.format(stDate));
        map.put("enDate", df.format(enDate));
        map.put("vcode", vcode);
        List<Ota_Date> otaList = ticketBigDao.otaSaleByDay(map);
        for (Ota_Date otaCount : otaList) {
            Ota_DateSale_Tend ota = new Ota_DateSale_Tend();
            ota.setCount(otaCount.getCount());
            ota.setName(otaCount.getName());
            ota.setTotalprice(otaCount.getTOTALPRICE() == null ? 0 : otaCount.getTOTALPRICE());
            otaSale.add(ota);
        }
        return otaSale;
    }

    /**
     * 按月获取ota销售统计
     *
     * @param time
     * @param vcode
     * @return
     */
    @Override
    public List<Ota_DateSale_Tend> otaSaleByMonth(String time, String vcode) {
        List<Ota_DateSale_Tend> otaSale = new LinkedList<Ota_DateSale_Tend>();
        Map<String, Object> map = new HashMap<>();
        Date dates = null;
        DateFormat df = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            dates = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        df = new SimpleDateFormat("yyyy-MM");
        map.put("dates", df.format(dates));
        map.put("vcode", vcode);
        List<Ota_Date> otaList = ticketBigDao.otaSaleByMonth(map);
        for (Ota_Date otaCount : otaList) {
            Ota_DateSale_Tend ota = new Ota_DateSale_Tend();
            ota.setCount(otaCount.getCount());
            ota.setName(otaCount.getName());
            ota.setTotalprice(otaCount.getTOTALPRICE() == null ? 0 : otaCount.getTOTALPRICE());
            ota.setName(otaCount.getName());
            otaSale.add(ota);
        }
        return otaSale;
    }

    /**
     * 按年获取ota销售统计
     *
     * @param startYear
     * @param endYear
     * @param vcode
     * @return
     */
    @Override
    public List<Ota_DateSale_Tend> otaSaleByYear(String startYear, String endYear, String vcode) {
        List<Ota_DateSale_Tend> otaSale = new LinkedList<Ota_DateSale_Tend>();
        Map<String, Object> map = new HashMap<>();
        Date stYear = null;
        Date enYear = null;
        DateFormat df = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        try {
            stYear = sdf.parse(startYear);
            enYear = sdf.parse(endYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
        df = new SimpleDateFormat("yyyy");
        map.put("stYear", df.format(stYear));
        map.put("enYear", df.format(enYear));
        map.put("vcode", vcode);
        List<Ota_Date> otaList = ticketBigDao.otaSaleByYear(map);
        for (Ota_Date otaCount : otaList) {
            Ota_DateSale_Tend ota = new Ota_DateSale_Tend();
            ota.setCount(otaCount.getCount());
            ota.setName(otaCount.getName());
            ota.setTotalprice(otaCount.getTOTALPRICE() == null ? 0 : otaCount.getTOTALPRICE());
            otaSale.add(ota);
        }
        return otaSale;
    }

    /**
     * 票务分析线上线日
     *
     * @param startTime
     * @param endTime
     * @param vcode
     * @return
     */
    public Map<String, Object> onLineOffLine(String startTime, String endTime, String vcode) {
        Map<String, String> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("vcode", vcode);
        List list = new LinkedList<>();
        List<JqticketXsSeal> onLine = ticketBigDao.onOffLine(map);
        List<JqticketXxSeal> offLine = ticketBigDao.offLine(map);
        Map mapline = new HashMap<>();
        if (onLine == null || onLine.isEmpty()) {
            mapline.put("onLine", list);
        } else {
            for (JqticketXsSeal jqticketXsSeal : onLine) {
                if (jqticketXsSeal == null) {
                    mapline.put("onLine", list);
                    break;
                } else {
                    mapline.put("onLine", onLine);
                }
            }
        }


        if (offLine == null || offLine.isEmpty()) {
            mapline.put("offLine", list);
        } else {
            for (JqticketXxSeal jqticketXxSeal : offLine) {
                if (jqticketXxSeal == null) {
                    mapline.put("offLine", list);
                    break;
                } else {
                    mapline.put("offLine", offLine);
                }
            }
        }
        return mapline;
    }

    /**
     * 票务分析线上线月份
     *
     * @param startTime
     * @param vcode
     * @return
     */
    public Map<String, Object> onLineOffLineMonth(String startTime, String vcode) {
        Map<String, String> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("vcode", vcode);
        List<JqticketXsSeal> onLine = ticketBigDao.onOffLineMonth(map);
        List<JqticketXxSeal> offLine = ticketBigDao.offLineMonth(map);
        List list = new LinkedList<>();
        Map<String, Object> mapline = new HashMap<>();
        if (onLine == null || onLine.isEmpty()) {
            mapline.put("onLine", list);
        } else {
            for (JqticketXsSeal jqticketXsSeal : onLine) {
                if (jqticketXsSeal == null) {
                    mapline.put("onLine", list);
                    break;
                } else {
                    mapline.put("onLine", onLine);
                }
            }
        }
        if (offLine == null || offLine.isEmpty()) {
            mapline.put("offLine", list);
        } else {
            for (JqticketXxSeal jqticketXxSeal : offLine) {
                if (jqticketXxSeal == null) {
                    mapline.put("offLine", list);
                    break;
                } else {
                    mapline.put("offLine", offLine);
                }
            }
        }
        return mapline;
    }

    /**
     * 票务分析线上线季度
     *
     * @param date
     * @param vcode
     * @param type
     * @return
     */
    public Map<String, Object> onLineOffLineQuarter(String date, String vcode, String type) {
        Map<String, String> map = new HashMap<>();
        if (type.equals("vitality")) {
            map.put("startTime", date + "-01");
            map.put("endTime", date + "-03");
        } else if (type.equals("summer")) {
            map.put("startTime", date + "-04");
            map.put("endTime", date + "-06");
        } else if (type.equals("autumn")) {
            map.put("startTime", date + "-07");
            map.put("endTime", date + "-09");
        } else if (type.equals("winter")) {
            map.put("startTime", date + "-10");
            map.put("endTime", date + "-12");
        }
        map.put("vcode", vcode);
        List<JqticketXsSeal> onLine = ticketBigDao.onOffLineQuarter(map);
        List<JqticketXxSeal> offLine = ticketBigDao.offLineQuarter(map);
        Map<String, Object> mapline = new HashMap<>();
        if (onLine == null || onLine.isEmpty()) {
            mapline.put("onLine", new LinkedList<>());
        } else {
            for (JqticketXsSeal jqticketXsSeal : onLine) {
                if (jqticketXsSeal == null) {
                    mapline.put("onLine", new LinkedList<>());
                    break;
                } else {
                    mapline.put("onLine", onLine);
                }
            }

        }
        if (offLine == null || offLine.isEmpty()) {
            mapline.put("offLine", new LinkedList<>());
        } else {
            for (JqticketXxSeal jqticketXxSeal : offLine) {
                if (jqticketXxSeal == null) {
                    mapline.put("offLine", new LinkedList<>());
                } else {
                    mapline.put("offLine", offLine);
                }
            }
        }

        return mapline;
    }

    /**
     * 大屏實時票数（人数）
     *
     * @param vcode
     * @return
     */
    @Override
    public List<JqTicket_Tend> findJqTicketNow(String vcode) {
        List<JqTicket_Tend> JqTicketList = ticketBigDao.findJqTicketNow(vcode);
        return JqTicketList;
    }

    /**
     * 主页线上销量
     *
     * @param vcode
     * @return
     */
    @Override
    public List<JqTicketOnline_Tend> findJqTicketOnLine(String vcode) {
        List<JqTicketOnline_Tend> JqTicketOnLineList = new LinkedList<JqTicketOnline_Tend>();
        List<JqTicket> JqTicketList = ticketBigDao.findJqTicketOnLine(vcode);

        if (JqTicketList == null || JqTicketList.size() < 1) {

            return null;
        }

        for (JqTicket JqTicketOnLine : JqTicketList) {
            JqTicketOnline_Tend jqTicket = new JqTicketOnline_Tend();
            jqTicket.setCount(JqTicketOnLine.getCount());
            JqTicketOnLineList.add(jqTicket);
        }
        return JqTicketOnLineList;
    }

    /**
     * 按年份查询票务类型数据
     *
     * @param vcode
     * @param date  2017
     * @return
     */
    @Override
    public List<Ota_DateSeal> queryYears(String vcode, String date) {
        return getOtaTicketType(vcode, strToFormatStrYear(date), YEAR);
    }

    /**
     * 2017-08-09 zhouq 修改
     * <p>
     * 按年份统计票务类型 及占比
     *
     * @param vcode
     * @param year  年份 2017
     * @return
     */
    @Override
    public List<JqticketSeal> getTicketTypeAndProportionByYear(String vcode, String year) {

        String h = TABLE_NAME + BIG_TABLENAME + BIG_DATE + YEAR + COLON;
        String hk = YEAR + "|" + year + "|" + DigestUtils.md5Hex(vcode);

        List<JqticketSeal> jqticketSealList;
        //读取缓存
        jqticketSealList = (List<JqticketSeal>) RedisCache.getHash(redisTemplate, h, hk);
        if (jqticketSealList != null && jqticketSealList.size() > 0)
            return jqticketSealList;

        jqticketSealList = new ArrayList<>();
        //获取年度票务类型数据
        List<TicketClTypeVO> ticketTypeByYear = getTicketTypeByYear(vcode, year);
        if (ticketTypeByYear.size() == 0)
            return jqticketSealList;

        long sum = 0;
        for (TicketClTypeVO clTypeVO : ticketTypeByYear) {
            sum += clTypeVO.getNums();
        }
        DecimalFormat format = new DecimalFormat("#.#");
        JqticketSeal jqticketSeal;
        for (TicketClTypeVO clTypeVO : ticketTypeByYear) {
            jqticketSeal = new JqticketSeal();
            String proportion = format.format(((double) clTypeVO.getNums() / sum) * 100);
            jqticketSeal.setName(clTypeVO.getTypes());
            jqticketSeal.setNumber(clTypeVO.getNums());
            jqticketSeal.setProportion(proportion);
            jqticketSealList.add(jqticketSeal);
        }
        //写入缓存
        RedisCache.putHash(redisTemplate, h, hk, jqticketSealList);
        return jqticketSealList;
    }

    /**
     * 获取游客构成按年份进行统计数据
     *
     * @param vcode
     * @param date  日期 2017
     * @return
     */
    @Override
    public List<JqticketGcSeal> touristList(String vcode, String date) {
        List<TicketClTypeVO> ticketClTypeVOList = getTouristTypeByYear(vcode, date);
        List<JqticketGcSeal> jqticketGcSealList = new ArrayList<>();
        for (TicketClTypeVO TicketClTypeVO : ticketClTypeVOList) {
            JqticketGcSeal jqticketGcSeal = new JqticketGcSeal();
            jqticketGcSeal.setName(TicketClTypeVO.getTypes());
            jqticketGcSeal.setNumber(TicketClTypeVO.getNums());
            jqticketGcSealList.add(jqticketGcSeal);
        }
        return jqticketGcSealList;
    }

    /**
     * 获取线上和线下数据并进行对比，将统计后的数据返回给前端
     *
     * @param vcode 景区编码
     * @param date  年份：2017
     * @return
     */
    @Override
    public Map<String, Object> getOnlineAndOffline(String vcode, String date) {
        Map<String, Object> thisYear = new HashMap<>();
        if (vcode == null || date == null || "".equals(date)) {
            return thisYear;
        }
        String key = strToFormatStrYear(date) + DigestUtils.md5Hex(vcode);
        Object onlineResultStr = getDataFromRedis(OTA_TABLE_NAME + OTA_BIG_TABLENAME + MONTH + COLON, key);
        Object offlineResultStr = getDataFromRedis(TABLE_NAME + BIG_TABLENAME + MONTH + COLON, key);
        List<JqTicketMonthSealVo> thisYearOnLine = jsonStrToTicketMonthList(onlineResultStr);
        List<JqTicketMonthSealVo> thisYearOffLine = jsonStrToTicketMonthList(offlineResultStr);

        thisYear.put("onLine", thisYearOnLine);
        thisYear.put("offLine", thisYearOffLine);

        Map<String, String> thisOnLineMap = new HashMap<String, String>();
        Map<String, String> thisOffLineMap = new HashMap<String, String>();

        boolean onLineType = false;
        boolean offLineType = false;
        for (JqTicketMonthSealVo line : thisYearOnLine) {
            thisOnLineMap.put(line.getMonth(), line.getNumber() + "");
            if (line.getNumber() >= 10000) {
                onLineType = true;
            }
        }
        for (JqTicketMonthSealVo line : thisYearOffLine) {
            thisOffLineMap.put(line.getMonth(), line.getNumber() + "");
            if (line.getNumber() >= 10000) {
                offLineType = true;
            }
        }
        //前端显示单位
//        if (onLineType && offLineType) {
//            thisYear.put("type", "万张");
//        } else if (!onLineType && !offLineType) {
//            thisYear.put("type", "张");
//        } else if (!onLineType && offLineType) {
//            thisYear.put("type", "万张");
//        } else if (onLineType && !offLineType) {
//            thisYear.put("type", "万张");
//        }
        thisYear.put("type", "张");
        String lastYear = String.valueOf(Integer.parseInt(date) - 1);
        String lastYearKey = strToFormatStrYear(lastYear) + DigestUtils.md5Hex(vcode);
        Object lastYearOnLineResultStr = getDataFromRedis(OTA_TABLE_NAME + OTA_BIG_TABLENAME + YEAR, lastYearKey);
        Object lastYearOffLineResultStr = getDataFromRedis(TABLE_NAME + BIG_TABLENAME + YEAR, lastYearKey);
        List<JqTicketMonthSealVo> lastYearOnLine = jsonStrToTicketMonthList(lastYearOnLineResultStr);
        List<JqTicketMonthSealVo> lastYearOffLine = jsonStrToTicketMonthList(lastYearOffLineResultStr);

        Map<String, String> lastOnLineMap = new HashMap<String, String>();
        Map<String, String> lastOffLineMap = new HashMap<String, String>();

        for (JqTicketMonthSealVo line : lastYearOnLine) {
            lastOnLineMap.put(line.getMonth(), line.getNumber() + "");
        }
        for (JqTicketMonthSealVo line : lastYearOffLine) {
            lastOffLineMap.put(line.getMonth(), line.getNumber() + "");
        }

        List<Growth> onLineAnnulusGrowth = new MathUtils().getAnnulusGrowth(thisOnLineMap, 12);
        List<Growth> offLineAnnulusGrowth = new MathUtils().getAnnulusGrowth(thisOffLineMap, 12);
        List<Growth> onLineYearOnYearGrowth = new MathUtils().getYearOnYearGrowth(lastOnLineMap, thisOnLineMap, 12);
        List<Growth> offLineYearOnYearGrowth = new MathUtils().getYearOnYearGrowth(lastOffLineMap, thisOffLineMap, 12);

        thisYear.put("onLineAnnulusGrowth", onLineAnnulusGrowth);
        thisYear.put("offLineAnnulusGrowth", offLineAnnulusGrowth);
        thisYear.put("onLineYearOnYearGrowth", onLineYearOnYearGrowth);
        thisYear.put("offLineYearOnYearGrowth", offLineYearOnYearGrowth);

        return thisYear;
    }

    /**
     * 将json字符串转化为对应对象list
     *
     * @param resultStr
     * @return
     */
    private List<JqTicketMonthSealVo> jsonStrToTicketMonthList(Object resultStr) {
        List<JqTicketMonthSealVo> result = new ArrayList<>();
        if (resultStr == null) {
            return result;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        if (resultJson == null) {
            return result;
        }
        for (Object jsonKey : resultJson.keySet()) {
            if (jsonKey == null) {
                continue;
            }
            Object valueO = resultJson.get(jsonKey);
            long value = Long.valueOf(valueO.toString());
            JqTicketMonthSealVo jqTicketMonthSealVo = new JqTicketMonthSealVo();
            jqTicketMonthSealVo.setMonth(strToFormatStr(jsonKey.toString(), "MM月", "MM"));
            jqTicketMonthSealVo.setNumber(value);
            result.add(jqTicketMonthSealVo);
        }
        return result;
    }

    /**
     * 获取票务类型数据
     *
     * @param vcode 景区编码
     * @param date  日期 2017-05-12
     * @return
     */
    private List<JqticketSeal> getTicketTypeAndPercent(String vcode, String date, String type) {
        List<JqticketSeal> jqTicketSealVoList = new ArrayList<>();
        if (vcode == null || date == null || "".equals(date)) {
            return jqTicketSealVoList;
        }
        String key = date + DigestUtils.md5Hex(vcode);
        Object resultStr = getDataFromRedis(TABLE_NAME + TABLE_NAME + type + COLON, key);
        if (resultStr == null) {
            return jqTicketSealVoList;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        double count = 0.00;
        for (Object jsonKey : resultJson.keySet()) {
            if (jsonKey == null) {
                continue;
            }
            Object valueO = resultJson.get(jsonKey);
            long value = Long.valueOf(valueO.toString());
            count += value;
            JqticketSeal jqTicketSealVo = new JqticketSeal();
            jqTicketSealVo.setName(jsonKey.toString());
            jqTicketSealVo.setNumber(value);
            jqTicketSealVoList.add(jqTicketSealVo);
        }
        for (JqticketSeal jqTicketSealVo : jqTicketSealVoList) {
            String coun = String.valueOf(Double.parseDouble(Double.toString((jqTicketSealVo.getNumber() / count) * 100)));
            jqTicketSealVo.setProportion(coun.substring(0, coun.indexOf(".")));//百分比
            if (jqTicketSealVo.getProportion().equals("0")) {//如果百分比为0 ，则显示1
                jqTicketSealVo.setProportion("1");
            }
        }
        return jqTicketSealVoList;
    }

    /**
     * 获取对应的ota票务类型数据
     *
     * @param vcode 景区编码
     * @param date  日期
     * @param type  类型 用于决定是获取年、月、日的统计数据
     * @return 返回从redis中获取的数据转成的对象list
     */
    private List<Ota_DateSeal> getOtaTicketType(String vcode, String date, String type) {
        List<Ota_DateSeal> result = new ArrayList<>();
        if (vcode == null || date == null || "".equals(date)) {
            return result;
        }
        String key = date + DigestUtils.md5Hex(vcode);
        Object resultStr = getDataFromRedis(OTA_TICKET_TYPE + type + COLON, key);
        if (resultStr == null) {
            return result;
        }
        JSONObject resultJson = JSONObject.fromObject(resultStr);
        for (Object jsonKey : resultJson.keySet()) {
            if (jsonKey == null) {
                continue;
            }
            Object valueO = resultJson.get(jsonKey);
            Integer value = Integer.valueOf(valueO.toString());
            Ota_DateSeal otaDataSealVo = new Ota_DateSeal();
            otaDataSealVo.setName(jsonKey.toString());
            otaDataSealVo.setCount(value);
            result.add(otaDataSealVo);
        }
        return result;
    }

    /**
     * 票务分析线上线下年
     *
     * @param startTime
     * @param endTime
     * @param vcode
     * @return
     */
    public Map<String, Object> onLineOffLineYear(String startTime, String endTime, String vcode) {
        Map<String, String> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("vcode", vcode);
        List list = new LinkedList<>();
        List<JqticketXsSeal> onLine = ticketBigDao.onOffLineYear(map);
        List<JqticketXxSeal> offLine = ticketBigDao.offLineYear(map);
        Map<String, Object> mapline = new HashMap<>();
        if (onLine == null || onLine.isEmpty()) {
            mapline.put("onLine", list);
        } else {
            for (JqticketXsSeal jqticketXsSeal : onLine) {
                if (jqticketXsSeal == null) {
                    mapline.put("onLine", list);
                } else {
                    mapline.put("onLine", onLine);
                }
            }

        }
        if (offLine == null || offLine.isEmpty()) {
            mapline.put("offLine", list);
        } else {
            for (JqticketXxSeal jqticketXxSeal : offLine) {
                if (jqticketXxSeal == null) {
                    mapline.put("offLine", list);
                } else {
                    mapline.put("offLine", offLine);
                }
            }
        }
        return mapline;
    }

    private Integer totalPage(Integer count) {
        if (count % PAGESIZE == 0) {
            return count / PAGESIZE;
        } else {
            return count / PAGESIZE + 1;
        }
    }


    /**
     * 对(游客)客票种类数据进行分类
     *
     * @param source
     * @return
     */
    private List<TicketClTypeVO> touristTypeGroupStatistics(List<TicketClTypeVO> source) {

        String type;
        Map<String, Long> numsMap = new HashMap<>();
        for (TicketClTypeVO clTypeVO : source) {
            type = clTypeVO.getTypes();
            long nums = clTypeVO.getNums();
            //获取历史数据 累计
            long oldNums = numsMap.get(type) != null ? numsMap.get(type) : 0;
            numsMap.put(type, nums + oldNums);

        }

        List<TicketClTypeVO> resultList = new ArrayList<>();
        TicketClTypeVO clTypeVO;
        for (String k : numsMap.keySet()) {
            clTypeVO = new TicketClTypeVO(numsMap.get(k), k, 0);
            resultList.add(clTypeVO);
        }

        return resultList;
    }

    /**
     * 2017-08-10 zhouq
     * <p>
     * 对票务类型数据进行分类
     * <p>
     * 从数据读取的数据是按票务类型 进行详细划分的.由于部分景区存在的详细类型过多.
     * 所以我们尽可能的去针对景区做一个票务类型的归类.
     *
     * @param source
     * @return
     */
    private List<TicketClTypeVO> ticketTypeGroupStatistics(List<TicketClTypeVO> source) {

        Map<String, String> myPropsTicketType = myProps.getTicketType();
//        for (Map.Entry<String, String> entry : myPropsTicketType.entrySet()) {
//            System.out.println(entry.getKey() + ":" +entry.getValue());
//        }

        String type;
        String type1;
        Map<String, Long> numsMap = new HashMap<>();
        Map<String, Integer> typecalMap = new HashMap<>();
        for (TicketClTypeVO clTypeVO : source) {
            type = clTypeVO.getTypes();
            int typecal = clTypeVO.getTypecal();
            long nums = clTypeVO.getNums();

            type1 = myPropsTicketType.get(type);
            if (type1 == null) {
                type1 = "其他";
            }
            //获取历史数据 累计
            long oldNums = numsMap.get(type1) != null ? numsMap.get(type1) : 0;
            int oldTypecl = typecalMap.get(type1) != null ? typecalMap.get(type1) : 0;
            numsMap.put(type1, nums + oldNums);
            typecalMap.put(type1, typecal + oldTypecl);

        }

        List<TicketClTypeVO> resultList = new ArrayList<>();
        TicketClTypeVO clTypeVO;
        for (String k : numsMap.keySet()) {
            clTypeVO = new TicketClTypeVO(numsMap.get(k), k, typecalMap.get(k));
            resultList.add(clTypeVO);
        }

        return resultList;
    }

    /**
     *
     * 多线程读取 按天获取客票种类数据
     */
    private class TouristTypeByDayTask implements Callable<List<TicketClTypeVO>> {

        private String date;
        private String vcode;

        public TouristTypeByDayTask(String vcode, String date) {
            this.date = date;
            this.vcode = vcode;
        }

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

        public TouristTypeByDayTask() {
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public List<TicketClTypeVO> call() throws Exception {
//            System.out.println();
//            System.out.println(+" 正在处理: "+date);
            logger.info(String.format("线程:%s 正在处理数据 vcode:%s,date:%s", Thread.currentThread().getName(), vcode, date));
            List<TicketClTypeVO> touristTypeByDay = getTouristTypeByDay(vcode, date);
            return touristTypeByDay;
        }
    }
}
