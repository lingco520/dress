package com.daqsoft.service.client.ticketanalys.impl;

import com.daqsoft.config.MyProps;
import com.daqsoft.mapper.client.ticket.TicketDao;
import com.daqsoft.redis.ExecutorServiceUtils;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.ticketanalys.TicketService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.DateUtils;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.validator.RRException;
import com.daqsoft.vo.client.madeVoBean.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.list.TreeList;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author zhouq
 * @version V1.0.0
 * @date 2017-07-27 11:12.
 * @description 票务分析 service
 */
@Service
public class TicketServiceImpl implements TicketService {

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private MyProps myProps;

    @Autowired
    private TicketDao ticketDao;

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
    private static final String COUNT_TOTALPRICE = "count_totalPrice:";
    private static final String BASE = "base:";
    private static final String INCOME = "income";

    /**
     * 获取当前票务总数及金额
     *
     * @param vcode 景区代码
     * @param date  日期
     * @return
     */
    @Override
    public TicketClTendVo getTicketCountAndPriceByDay(String vcode, String date) {

        //redis 缓存key
        String h = TABLE_NAME + COUNT_TOTALPRICE + DAY + COLON;
        String hk = DAY + "|" + date + "|" + DigestUtils.md5Hex(vcode);

        TicketClTendVo clTendVo = (TicketClTendVo) RedisCache.getHash(redisTemplate, h, hk);
        if (clTendVo != null)
            return clTendVo;

        String startTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";

        clTendVo = ticketDao.getTicketCountAndPriceByDay(vcode, startTime, endTime);
        if (clTendVo.getTicket_num() == null)
            return clTendVo;

        clTendVo.setTicket_time(date);
        if (clTendVo != null)
            RedisCache.putHash(redisTemplate, h, hk, clTendVo);

        return clTendVo != null ? clTendVo : new TicketClTendVo();
    }

    /**
     * 按天获取票务总数及金额
     *
     * @param vcode     景区代码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    @Override
    public TicketClTendVo getTicketCountAndPriceByDayRange(String vcode, String startDate, String endDate) {
        TicketClTendVo vo = new TicketClTendVo();
        if (vcode == null || startDate == null || endDate == null) {
            return vo;
        }

        List<String> dayReport = DateUtil.getSubsectionDateList(startDate, endDate);


        List<Future<TicketClTendVo>> futureList = new ArrayList<>();

        //使用多线程去拉取每天的数据.
        ExecutorService es = Executors.newCachedThreadPool();
        //提交多线程任务
        for (String dateStr : dayReport) {
            futureList.add(es.submit(new TicketNumAndPriceByDay(vcode, dateStr)));
        }

        List<TicketClTendVo> resuList = new ArrayList<>();

        //获取每天数据
        for (Future<TicketClTendVo> ticketClTendVoFuture : futureList) {

            try {
                TicketClTendVo tendVo = ticketClTendVoFuture.get();
                resuList.add(tendVo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        //汇总
        vo = ticketClTendVoStatistics(resuList);

        return vo;
    }

    /**
     * 按月份获取票务总数及金额
     *
     * @param vcode 景区代码
     * @param month 月份
     * @return
     */
    @Override
    public TicketClTendVo getTicketCountAndPriceByMonth(String vcode, String month) {
        //redis 缓存key
        String h = TABLE_NAME + COUNT_TOTALPRICE + RedisKey.MONTH + RedisKey.COLON;
        String hk = RedisKey.MONTH + "|" + month + "|" + DigestUtils.md5Hex(vcode);

        TicketClTendVo vo;
        vo = (TicketClTendVo) RedisCache.getHash(redisTemplate, h, hk);

        if (vo != null) {
            return vo;
        }
        try {

            Date parse = new SimpleDateFormat("yyyy-MM").parse(month);
            //得到此月份的所有天
            List<String> dayReport = DateUtil.getDayReport(parse);


            List<Future<TicketClTendVo>> futureList = new ArrayList<>();

            //使用多线程去拉取每天的数据.
            ExecutorService es = Executors.newCachedThreadPool();
            //提交多线程任务
            for (String dateStr : dayReport) {
                futureList.add(es.submit(new TicketNumAndPriceByDay(vcode, dateStr)));
            }

            List<TicketClTendVo> resuList = new ArrayList<>();

            //获取每天数据
            for (Future<TicketClTendVo> ticketClTendVoFuture : futureList) {
                TicketClTendVo tendVo = ticketClTendVoFuture.get();
                resuList.add(tendVo);
            }

            //汇总
            vo = ticketClTendVoStatistics(resuList);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        vo.setTicket_time(month);
        //写入缓存
//        if (StringUtils.isNotBlank(vo.getTicket_num()) && !"0".equals(vo.getTicket_num()))
        RedisCache.putHash(redisTemplate, h, hk, vo);

        return vo;
    }

    /**
     * 按季度获取票务总数及金额
     *
     * @param vcode   景区代码
     * @param year    年
     * @param quarter 季度
     * @return
     */
    @Override
    public TicketClTendVo getTicketCountAndPriceByQuarter(String vcode, String year, String quarter) {
        if (StringUtils.isBlank(vcode) || StringUtils.isBlank(year) || StringUtils.isBlank(quarter))
            return new TicketClTendVo();

        String monthStr = "";
        switch (quarter) {
            case RedisKey.QUARTER_Q1:
                monthStr = "01,02,03";
                break;
            case RedisKey.QUARTER_Q2:
                monthStr = "04,05,06";
                break;
            case RedisKey.QUARTER_Q3:
                monthStr = "07,08,09";
                break;
            case RedisKey.QUARTER_Q4:
                monthStr = "10,11,12";
                break;
            default:
                throw new RRException("季度参数格式错误:1/2/3/4", 400);
        }

        if (StringUtils.isBlank(monthStr))
            return new TicketClTendVo();

        TicketClTendVo ticketClTendVo;
        //redis 缓存key
        String H = TABLE_NAME + COUNT_TOTALPRICE + RedisKey.QUARTER + RedisKey.COLON;
        String HK = RedisKey.QUARTER + "|" + year + "_" + quarter + "|" + DigestUtils.md5Hex(vcode);

        ticketClTendVo = (TicketClTendVo) RedisCache.getHash(redisTemplate, H, HK);

        if (ticketClTendVo != null)
            return ticketClTendVo;

        String[] months = monthStr.split(",");

        List<TicketClTendVo> resuList = new ArrayList<>();
        //获取各月份数据
        for (String month : months) {
            TicketClTendVo otaSaleByMonth = getTicketCountAndPriceByMonth(vcode, year + "-" + month);
            resuList.add(otaSaleByMonth);
        }

        //汇总
        ticketClTendVo = ticketClTendVoStatistics(resuList);

        ticketClTendVo.setTicket_time(year + "_" + quarter);
        //写入缓存
        //if (StringUtils.isNotBlank(ticketClTendVo.getTicket_num()) && !"0".equals(ticketClTendVo.getTicket_num()))
        RedisCache.putHash(redisTemplate, H, HK, ticketClTendVo);

        return ticketClTendVo;

    }

    /**
     * 按年获取票务总数及金额
     *
     * @param vcode 景区代码
     * @param year  年
     * @return
     */
    @Override
    public TicketClTendVo getTicketCountAndPriceByYear(String vcode, String year) {
        //redis 缓存key
        String H = TABLE_NAME + COUNT_TOTALPRICE + RedisKey.YEAR + RedisKey.COLON;
        String HK = RedisKey.YEAR + "|" + year + "|" + DigestUtils.md5Hex(vcode);

        TicketClTendVo tendVo;

        //读取缓存
        tendVo = (TicketClTendVo) RedisCache.getHash(redisTemplate, H, HK);
        if (tendVo != null) {
            return tendVo;
        }

        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        List<TicketClTendVo> list = new ArrayList<>();
        for (String month : months) {
            TicketClTendVo otaSaleByMonth = getTicketCountAndPriceByMonth(vcode, year + "-" + month);
            list.add(otaSaleByMonth);
        }

        tendVo = ticketClTendVoStatistics(list);

        tendVo.setTicket_time(year);
        //写入缓存
//        if (StringUtils.isNotBlank(tendVo.getTicket_num()))
        RedisCache.putHash(redisTemplate, H, HK, tendVo);

        return tendVo;
    }

    /**
     * 按年区间获取票务总数及金额
     *
     * @param vcode
     * @param start
     * @param end
     * @return
     */
    @Override
    public TicketClTendVo getTicketCountAndPriceByYearRange(String vcode, int start, int end) {

        if (start > end)
            return new TicketClTendVo();
        if (start == end)
            return getTicketCountAndPriceByYear(vcode, String.valueOf(start));

        List<TicketClTendVo> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(getTicketCountAndPriceByYear(vcode, String.valueOf(i)));
        }

        TicketClTendVo tendVo = ticketClTendVoStatistics(list);
        return tendVo;
    }

    /**
     * 按天 获取当天每小时票务数据
     *
     * @param vcode 景区代码
     * @param date  日期
     * @return
     */
    @Override
    public List<TicketClSsTendVO> getRealTimeTicketListByDay(String vcode, String date) {
        //按天获取个小时数据
        String h = TABLE_NAME + BIG_TABLENAME + TIME + COLON;
        String md5Key = DigestUtils.md5Hex(vcode);
        String hk = DAY + "|" + date + "|" + md5Key;
        List<TicketClSsTendVO> tendVOList = (List<TicketClSsTendVO>) RedisCache.getHash(redisTemplate, h, hk);

        if (tendVOList == null || tendVOList.size() == 0) {
            tendVOList = ticketDao.getRealTimeTicketListByDay(vcode, date);
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

    @Override
    public List<TicketPriceClSsTendVO> getRealTimeTicketAllListByDay(String vcode, String date) {
        //按天获取个小时数据
        String h = TABLE_NAME + BIG_TABLENAME + INCOME+ COLON;
        String md5Key = DigestUtils.md5Hex(vcode);
        String hk = DAY + "|" + date + "|" + md5Key;
        List<TicketPriceClSsTendVO> tendVOList = (List<TicketPriceClSsTendVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (tendVOList == null || tendVOList.size() == 0) {
            tendVOList = ticketDao.getRealTimeTicketAllListByDay(vcode, date);
            if (tendVOList.size() > 0) {
                /*变量*/
                long ticket_nums = 0;
                long ticketprice_nums = 0;
                for (TicketPriceClSsTendVO ticketPriceClSsTendVO : tendVOList) {
                    ticket_nums += Long.valueOf(ticketPriceClSsTendVO.getTicket_num());
                    ticketprice_nums += Long.valueOf(ticketPriceClSsTendVO.getTicket_price());
                    ticketPriceClSsTendVO.setTicket_nums(ticket_nums + "");
                    ticketPriceClSsTendVO.setTicket_pricenums(ticketprice_nums+ "");
                }
                RedisCache.putHash(redisTemplate, h, hk, tendVOList);
            }
        }

        return tendVOList;
    }

    /**
     * 按月份 获取每天票务总数 及金额
     *
     * @param vcode 景区代码
     * @param month 月份
     * @return
     */
    @Override
    public List<TicketClTendVo> getDayTicketListByMonth(String vcode, String month) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTendVo> bigTicket;

        //redis 缓存key
        String h = TABLE_NAME + BIG_TABLENAME + DAY + COLON;
        String hk = MONTH + "|" + month + "|" + md5key;

        //缓存读取
        bigTicket = (List<TicketClTendVo>) RedisCache.getHash(redisTemplate, h, hk);
        if (bigTicket != null && bigTicket.size() > 0) {
            return bigTicket;
        }

//        //无缓存 走sql 查询
//        bigTicket = ticketDao.getTicketNumByTimeSlot(vcode, month + "-01 00:00:00", month + "-31 23:59:59", "%Y-%m-%d");
        bigTicket = new ArrayList<>();

        try {
            Date parse = new SimpleDateFormat("yyyy-MM").parse(month);
            List<String> dayReport = DateUtil.getDayReport(parse);
            Date now = new Date();

            List<Future<TicketClTendVo>> futureList = new ArrayList<>();
            ExecutorService es = Executors.newCachedThreadPool();
//            ExecutorService es = ExecutorServiceUtils.getFixedThreadPool();
            for (String s : dayReport) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
                if (date.getTime() > now.getTime())
                    continue;
                if (es.isShutdown()) {
                    es = ExecutorServiceUtils.getCachedThreadPool();
                }
                futureList.add(es.submit(new TicketNumAndPriceByDay(vcode, s)));
            }

            for (Future<TicketClTendVo> clTendVoFuture : futureList) {
                bigTicket.add(clTendVoFuture.get());
            }

            //关闭线程池.释放资源
            es.shutdown();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //写入缓存
        if (bigTicket != null && bigTicket.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, bigTicket);
        }

        return bigTicket != null ? bigTicket : new ArrayList<>();
    }

    /**
     * 按年份 获取每月票务总数 及金额
     *
     * @param vcode 景区代码
     * @param year  年份
     * @return
     */
    @Override
    public List<TicketClTendVo> getMonthTicketListByYear(String vcode, String year) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTendVo> bigTicket;

        //redis 缓存key
        String h = TABLE_NAME + BIG_TABLENAME + MONTH + COLON;
        String hk = YEAR + "|" + year + "|" + md5key;

        //先走缓存.
//        bigTicket = (List<TicketClTendVo>) RedisCache.getHash(redisTemplate, h, hk);
//        if (bigTicket != null && bigTicket.size() > 0) {
//            return bigTicket;
//        }
        //无缓存 走sql 查询
        //bigTicket = ticketDao.getTicketNumByTimeSlot(vcode, year + "-01-01 00:00:00", year + "-12-31 23:59:59", "%Y-%m");
        bigTicket = new ArrayList<>();
        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        TicketClTendVo tendVo;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        //拉取各月份基础统计数据
        for (String month : months) {

            String mon = year + "-" + month;
            try {
                if (sdf.parse(mon).getTime() > new Date().getTime()) {
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //获取每月所有天数的数据 累计
            List<TicketClTendVo> dayTicketListByMonth = getDayTicketListByMonth(vcode, mon);
            if (dayTicketListByMonth.size() == 0)
                continue;
            long num = 0;
            long price = 0;
            for (TicketClTendVo tendVoByMonth : dayTicketListByMonth) {
                num += Long.parseLong(tendVoByMonth.getTicket_num());
                price += Long.parseLong(tendVoByMonth.getTicket_totalPrice());
            }

            tendVo = new TicketClTendVo(year + "-" + month, String.valueOf(num), String.valueOf(price));
            bigTicket.add(tendVo);
        }

        //写入缓存
        if (bigTicket != null && bigTicket.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, bigTicket);
        }
        return bigTicket != null ? bigTicket : new ArrayList<>();
    }

    /**
     * 按年份 获取每季度票务总数 及金额
     *
     * @param vcode
     * @param year
     * @return
     */
    @Override
    public List<TicketClTendVo> getQuarterTicketListByYear(String vcode, String year) {
        String md5key = DigestUtils.md5Hex(vcode);

        //redis 缓存key
        String h = TABLE_NAME + BIG_TABLENAME + QUARTER + COLON;
        String hk = YEAR + "|" + year + "|" + md5key;
        //优先读取缓存
        List<TicketClTendVo> bigTicketMonthByYear = (List<TicketClTendVo>) RedisCache.getHash(redisTemplate, h, hk);
        if (bigTicketMonthByYear != null && bigTicketMonthByYear.size() > 0)
            return bigTicketMonthByYear;

        //获取当年所有月份的数据
        bigTicketMonthByYear = getMonthTicketListByYear(vcode, year);
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
     * 获取按天区间的票务总数及金额
     *
     * @param vcode
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 返回对应时间段按天统计数据的list
     */
    @Override
    public List<TicketClTendVo> getTicketNumSlotGroupByDay(String vcode, String startDate, String endDate) {
        List<TicketClTendVo> result = new ArrayList<>();
        if (vcode == null || startDate == null || endDate == null) {
            return result;
        }
        String startTime = startDate + " 00:00:00";
        String endTime = endDate + " 23:59:59";

        List<String> dateList = DateUtil.getSubsectionDateList(startDate, endDate);

        for (String s : dateList) {
            TicketClTendVo ticketCountAndPriceByDay = getTicketCountAndPriceByDay(vcode, s);
            result.add(ticketCountAndPriceByDay);
        }

        return result;

    }

    /**
     * 获取按月份区间段的票务总数及金额
     *
     * @param vcode
     * @param startMonth 开始月份
     * @param endMonth   结束月份
     * @return 返回对应时间段按月统计数据的list
     */
    @Override
    public List<TicketClTendVo> getTicketNumByTimeSlotGroupByMonth(String vcode, String startMonth, String endMonth) {
        List<TicketClTendVo> result = new ArrayList<>();
        if (vcode == null || startMonth == null || endMonth == null) {
            return result;
        }
        String startTime = startMonth + "-1 00:00:00";
        String endTime = endMonth + "-31 23:59:59";

        return getTicketNumByTimeSlot(vcode, startTime, endTime, "%Y-%m");
    }

    /**
     * 获取传入年区间的票务总数及金额
     *
     * @param vcode
     * @param startYear 开始年份
     * @param endYear   结束年份
     * @return 返回对应时间段按年统计数据的list
     */
    @Override
    public List<TicketClTendVo> getTicketNumByTimeSlotGroupByYear(String vcode, int startYear, int endYear) {
        List<TicketClTendVo> ticketClTendVos = new ArrayList<>();
        if (startYear > endYear)
            return ticketClTendVos;
        if (startYear == endYear) {
            ticketClTendVos.add(getTicketNumByYear(vcode, startYear));
            return ticketClTendVos;
        }


        for (int year = startYear; year <= endYear; year++) {
            ticketClTendVos.add(getTicketNumByYear(vcode, year));
        }

        return ticketClTendVos;
    }

    /**
     * 根据年份获取景区票务数量和金额
     *
     * @param vcode
     * @param year
     * @return
     */
    @Override
    public TicketClTendVo getTicketNumByYear(String vcode, int year) {
         String h = TABLE_NAME + BIG_TABLENAME + YEAR + COLON;
        String hk = YEAR + "|" + year + "|" + DigestUtils.md5Hex(vcode);

        //优先读取缓存
        TicketClTendVo tendVo = (TicketClTendVo) RedisCache.getHash(redisTemplate, h, hk);
        if (tendVo != null && tendVo.getTicket_num() != null && !"0".equals(tendVo.getTicket_num()))
            return tendVo;
        //获取每年各月份数据kjnjk
        List<TicketClTendVo> bigTicketMonthByYear = getMonthTicketListByYear(vcode, String.valueOf(year));
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
     * 查询指定年、月、日 的票务类型统计数据
     *
     * @param date
     * @param vcode
     * @param type  day/month/year
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketTypeList(String vcode, String date, String type) {
        List<TicketClTypeVO> result = new ArrayList<>();
        if (DAY.equals(type)) {
            result = getTicketTypeListByDay(vcode, date);
        } else if (MONTH.equals(type)) {
            result = getTicketTypeListByMonth(vcode, date);
        } else if (YEAR.equals(type)) {
            result = getTicketTypeListByYear(vcode, date);
        }
        return result;
    }

    /**
     * 按年统计 票务类型数量及占比
     *
     * @param vcode
     * @param year  年份 2017
     * @return
     */
    @Override
    public List<JqticketSeal> getTicketTypesAndProportionByYear(String vcode, String year) {

        String h = TABLE_NAME + BIG_TABLENAME + BIG_DATE + YEAR + COLON;
        String hk = YEAR + "|" + year + "|" + DigestUtils.md5Hex(vcode);

        List<JqticketSeal> jqticketSealList;
        //读取缓存
        jqticketSealList = (List<JqticketSeal>) RedisCache.getHash(redisTemplate, h, hk);
        if (jqticketSealList != null && jqticketSealList.size() > 0)
            return jqticketSealList;

        jqticketSealList = new ArrayList<>();
        //获取年度票务类型数据
        List<TicketClTypeVO> ticketTypeByYear = getTicketTypeListByYear(vcode, year);
        if (ticketTypeByYear.size() == 0)
            return jqticketSealList;

        long sum = 0;
        for (TicketClTypeVO clTypeVO : ticketTypeByYear) {
            sum += clTypeVO.getNums();
        }
        DecimalFormat format = new DecimalFormat("#.##");
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
     * 查询指定时间段票务类型统计数据
     *
     * @param date  开始时间
     * @param end   结束时间
     * @param vcode 景区编码
     * @param type  类型 day/month/year
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketTypeListBySlot(String vcode, String start, String end, String type) {

        List<TicketClTypeVO> result = new ArrayList<>();
        if (vcode == null || start == null || end == null) {
            return result;
        }
        if (start.compareTo(end) > 0) {
            return result;
        }
        String startTime = "";
        String endTime = "";
        //由于sql中使用的between字段来进行判断，所以此处需要对字符串进行拼接成完整时间
        if (DAY.equals(type)) {
            //获取景区分段数据
            return getTicketTypeByDaySlot(vcode, start, end);

        } else if (MONTH.equals(type)) {
            startTime = start + "-01 00:00:00";
            endTime = end + "-31 23:59:59";

        } else if (YEAR.equals(type)) {

            /**
             * 2017-08-07 zhouq 添加
             *
             * 分段获取数据 分别获取各年份数据再组装.
             * 避免直接读取库
             */
            return getTicketTypeListByYearSlot(vcode, Integer.valueOf(start), Integer.valueOf(end));
        }
        return ticketDao.getTicketTypeByTimeSlot(vcode, startTime, endTime);
    }

    /**
     * 按季度获取票务类型统计数据 数据按照实际票务类型进行分类统计
     *
     * @param vcode   景区编码
     * @param year    年份
     * @param quarter 季度 1/2/3/4
     * @return 返回按年度中季度 的票务类型统计数据
     */
    @Override
    public List<TicketClTypeVO> getTicketBaseTypeListByQuarter(String vcode, String year, String quarter) {
        if (StringUtils.isBlank(vcode) || StringUtils.isBlank(year) || StringUtils.isBlank(quarter))
            return new ArrayList<>();

        String monthStr = "";
        switch (quarter) {
            case RedisKey.QUARTER_Q1:
                monthStr = "01,02,03";
                break;
            case RedisKey.QUARTER_Q2:
                monthStr = "04,05,06";
                break;
            case RedisKey.QUARTER_Q3:
                monthStr = "07,08,09";
                break;
            case RedisKey.QUARTER_Q4:
                monthStr = "10,11,12";
                break;
        }

        if (StringUtils.isBlank(monthStr))
            return new ArrayList<>();

        List<TicketClTypeVO> ticketClTypeVOList;
        //redis 缓存key
        String H = TABLE_NAME + TABLE_NAME + BASE + QUARTER + COLON;
        String HK = QUARTER + "|" + year + "_" + quarter + "|" + DigestUtils.md5Hex(vcode);
        //读取缓存
        ticketClTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, H, HK);
        if (ticketClTypeVOList != null && ticketClTypeVOList.size() > 0)
            return ticketClTypeVOList;

        ticketClTypeVOList = new ArrayList<>();
        //读取当前季度下各月份的具体数据
        String[] months = monthStr.split(",");
        for (String month : months) {
            //获取各月份 基础分类统计数据
            List<TicketClTypeVO> ticketTypeByMonth = this.getTicketBaseTypeListByMonth(vcode, year + "-" + month);
            ticketClTypeVOList.addAll(ticketTypeByMonth);
        }

        //合并同类数据
        ticketClTypeVOList = ticketBaseTypeGroupStatistics(ticketClTypeVOList);

        //写入缓存
        if (ticketClTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, H, HK, ticketClTypeVOList);
        }
        return ticketClTypeVOList;
    }

    /**
     * 按季度获取票务类型统计数据  数据按照通用规则进行分组统计
     *
     * @param vcode   景区编码
     * @param year    年份
     * @param quarter 季度 1/2/3/4
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketTypeListByQuarter(String vcode, String year, String quarter) {
        if (StringUtils.isBlank(vcode) || StringUtils.isBlank(year) || StringUtils.isBlank(quarter))
            return new ArrayList<>();

        List<TicketClTypeVO> ticketClTypeVOList;
        //redis 缓存key
        String H = TABLE_NAME + TABLE_NAME + QUARTER + COLON;
        String HK = QUARTER + "|" + year + "_" + quarter + "|" + DigestUtils.md5Hex(vcode);
        //读取缓存
        ticketClTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, H, HK);
        if (ticketClTypeVOList != null && ticketClTypeVOList.size() > 0){
            // 这里对数据进行排序处理，按数量降序排序
            Collections.sort(ticketClTypeVOList, new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                    TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                    if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                        return 1;
                    }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                        return 0;
                    }else {
                        return -1;
                    }
                }
            });
            return ticketClTypeVOList;
        }
        //获取基础分类数据
        List<TicketClTypeVO> ticketBaseTypeListByQuarter = this.getTicketBaseTypeListByQuarter(vcode, year, quarter);

        //TODO 云台山分类 其他暂不分类 后期处理
        if(vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")) {
            //对数据进行分类.
            ticketClTypeVOList = ticketTypeGroupStatistics(ticketBaseTypeListByQuarter);
        }else {
            ticketClTypeVOList = ticketBaseTypeListByQuarter;
        }
        //写入缓存
        if (ticketClTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, H, HK, ticketClTypeVOList);
        }
        // 这里对数据进行排序处理，按数量降序排序
        Collections.sort(ticketClTypeVOList, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                    return 1;
                }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
        return ticketClTypeVOList;
    }
    /**
     * 按 指定时间 获取 游客构成统计（年月日）
     *
     * @param vcode 景区编码
     * @param type  类型 day/month/year
     * @param time  时间
     * @return 返回按 日、月、年统计的 游客构成统计数据
     */
    @Override
    public List<TicketClTypeVO> getTouristBaseTypeList(String vcode, String type, String time) {
        List<TicketClTypeVO> ticketClTypeVOList = new ArrayList<>();
        if (RedisKey.DAY.equals(type)) {
            ticketClTypeVOList = getTouristBaseTypeListByDay(vcode, time);
        } else if (RedisKey.MONTH.equals(type)) {
            ticketClTypeVOList = getTouristBaseTypeListByMonth(vcode, time);
        } else if (RedisKey.YEAR.equals(type)) {
            ticketClTypeVOList = getTouristBaseTypeListByYear(vcode, time);
        }
        // 这里对数据进行排序处理，按数量降序排序
        Collections.sort(ticketClTypeVOList, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                    return 1;
                }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
        return ticketClTypeVOList;
    }

    /**
     * 获取季度游客构成
     *
     * @param vcode   景区代码
     * @param year    年份
     * @param quarter 季度 1/2/3/4
     * @return 返回季度游客构成
     */
    @Override
    public List<TicketClTypeVO> getTouristBaseTypeListByQuarter(String vcode, String year, String quarter) {
        //老版本中参数可能为  vitality  summer autumn wintertide
        //做一次转化
        if ("vitality".equals(quarter)) {
            quarter = "1";
        } else if ("summer".equals(quarter)) {
            quarter = "2";
        } else if ("autumn".equals(quarter)) {
            quarter = "3";
        } else if ("wintertide".equals(quarter)) {
            quarter = "4";
        }
        return this.getTouristTypeByQuarter(vcode, year, quarter);
    }

    /**
     * 按 指定时间段 获取 游客构成统计(年月日)
     *
     * @param vcode 景区编码
     * @param type  类型 day/month/year
     * @param start 开始
     * @param end   结束
     * @return 返回按 日、月、年 时间段 统计的 游客构成统计数据
     */
    @Override
    public List<TicketClTypeVO> getTouristBaseTypeListBySlot(String vcode, String type, String start, String end) {
        List<TicketClTypeVO> jqTicketPeopleFormVoList = new ArrayList<>();
        switch (type) {
            case RedisKey.DAY:
                jqTicketPeopleFormVoList = getTouristBaseTypeListByDaySlot(vcode, start, end);
                // 这里对数据进行排序处理，按数量降序排序
                Collections.sort(jqTicketPeopleFormVoList, new Comparator(){
                    @Override
                    public int compare(Object o1, Object o2) {
                        TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                        TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                        if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                            return 1;
                        }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                            return 0;
                        }else {
                            return -1;
                        }
                    }
                });
                return jqTicketPeopleFormVoList;
            case RedisKey.MONTH:
                /**
                 * 2017-08-17 zhouq
                 * 暂时不实现 目前没有业务按照 月区间去获取数据
                 */
                return jqTicketPeopleFormVoList;
            case RedisKey.YEAR:
                jqTicketPeopleFormVoList = getTouristBaseTypeListByYearSlot(vcode, Integer.valueOf(start), Integer.valueOf(end));
                // 这里对数据进行排序处理，按数量降序排序
                Collections.sort(jqTicketPeopleFormVoList, new Comparator(){
                    @Override
                    public int compare(Object o1, Object o2) {
                        TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                        TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                        if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                            return 1;
                        }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                            return 0;
                        }else {
                            return -1;
                        }
                    }
                });
                return jqTicketPeopleFormVoList;
        }

        return jqTicketPeopleFormVoList;
    }

    /**
     * 2017-08-17 zhouq
     * 为兼容老版本.返回实体vo 为 JqticketGcSeal.
     * 获取大数据页面游客统计
     *
     * @param vcode
     * @param year
     * @return
     */
    @Override
    public List<JqticketGcSeal> getBigDateTouristTypeByYear(String vcode, String year) {

        List<TicketClTypeVO> ticketClTypeVOList = getTouristBaseTypeListByYear(vcode, year);
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
     * 根据传入时间段来获取票务统计数据，并根据传入format格式对时间进行格式化并进行分组
     * 开始时间和结束时间只支持yyyy-MM-dd
     *
     * @param vcode     景区编码
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    用于对sql语句中日期进行format的数据
     * @return 返回时间list
     */
    private List<TicketClTendVo> getTicketNumByTimeSlot(String vcode, String startTime, String endTime, String format) {
        List<TicketClTendVo> result = new ArrayList<>();
        if (vcode == null || startTime == null || endTime == null || format == null) {
            return result;
        }
        result = ticketDao.getTicketNumByTimeSlot(vcode, startTime, endTime, format);
        return result;
    }


    /**
     * 2017-08-04 zhouq
     * 按月份获取票务类型及金额数据
     *
     * @param vcode
     * @param day   天
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketTypeListByDay(String vcode, String day) {
        if (StringUtils.isBlank(vcode) || StringUtils.isBlank(day))
            return new ArrayList<>();
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

        //读取基础分类数据
        clTypeVOList = this.getTicketBaseTypeListByDay(vcode, day);

        //TODO 云台山分类 其他暂不分类 后期处理
        if(vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")) {
            //对数据按规则进行分类.
            clTypeVOList = ticketTypeGroupStatistics(clTypeVOList);
        }
        //写入缓存
        if (clTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, clTypeVOList);
        }

        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();
    }

    /**
     * 按天获取票务类型及金额数据 数据按照实际票务类型进行分类统计
     *
     * @param vcode
     * @param day
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketBaseTypeListByDay(String vcode, String day) {
        if (StringUtils.isBlank(vcode) || StringUtils.isBlank(day))
            return new ArrayList<>();
        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTypeVO> clTypeVOList;

        //redis 缓存key
        String h = TABLE_NAME + TABLE_NAME + BASE + DAY + COLON;
        String hk = DAY + "|" + day + "|" + md5key;

        //读取缓存
        clTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (clTypeVOList != null && clTypeVOList.size() > 0) {
            return clTypeVOList;
        }
        //读取db
        clTypeVOList = ticketDao.getTicketTypeByTimeSlot(vcode, day + " 00:00:00", day + " 23:59:59");

        //写入缓存
        if (clTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, clTypeVOList);
        }

        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();
    }

    /**
     * 2017-08-04 zhouq
     * 按月份获取票务类型及金额数据 按照通用规则分类.
     *
     * @param vcode
     * @param month 月份
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketTypeListByMonth(String vcode, String month) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTypeVO> clTypeVOList;

        //redis 缓存key
        String h = TABLE_NAME + TABLE_NAME + MONTH + COLON;
        String hk = MONTH + "|" + month + "|" + md5key;

        //读取缓存
        clTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (clTypeVOList != null && clTypeVOList.size() > 0) {
            // 这里对数据进行排序处理，按数量降序排序
            Collections.sort(clTypeVOList, new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                    TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                    if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                        return 1;
                    }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                        return 0;
                    }else {
                        return -1;
                    }
                }
            });
            return clTypeVOList;
        }

        //获取基础分类数据
        clTypeVOList = this.getTicketBaseTypeListByMonth(vcode, month);

        //TODO 云台山分类 其他暂不分类 后期处理
        if(vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")) {

            //对数据进行通用分类.
            clTypeVOList = ticketTypeGroupStatistics(clTypeVOList);
        }

        //写入缓存
        if (clTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, clTypeVOList);
        }
        // 这里对数据进行排序处理，按数量降序排序
        Collections.sort(clTypeVOList, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                    return 1;
                }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();
    }

    /**
     * 2017-09-06 zhouq
     * 按天获取票务类型及金额数据 按照实际票务类型进行分类统计
     *
     * @param vcode
     * @param month
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketBaseTypeListByMonth(String vcode, String month) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTypeVO> clTypeVOList;

        //redis 缓存key
        String h = TABLE_NAME + TABLE_NAME + BASE + MONTH + COLON;
        String hk = MONTH + "|" + month + "|" + md5key;

        //读取缓存
        clTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (clTypeVOList != null && clTypeVOList.size() > 0) {
            return clTypeVOList;
        }

        clTypeVOList = new ArrayList<>();

        try {
            Date parse = new SimpleDateFormat("yyyy-MM").parse(month);
            //得到此月份的所有天
            List<String> dayReport = DateUtil.getDayReport(parse);

            List<Future<List<TicketClTypeVO>>> futureList = new ArrayList<>();

            //使用多线程去拉取每天的基础统计数据.
            ExecutorService es = Executors.newCachedThreadPool();

            for (String dateStr : dayReport) {
                futureList.add(es.submit(new TicketTypeBaseByDayTask(vcode, dateStr)));
            }
            for (Future<List<TicketClTypeVO>> listFuture : futureList) {
                try {
                    clTypeVOList.addAll(listFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            //关闭线程池.释放资源
            es.shutdown();
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("按月份获取票务类型及金额数据 处理错误:" + e.getLocalizedMessage());
        }

            //针对同一类数据进行合并
            clTypeVOList = ticketBaseTypeGroupStatistics(clTypeVOList);
        //写入缓存
        if (clTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, clTypeVOList);
        }

        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();
    }

    /**
     * 2017-08-04 zhouq
     * 按年份获取票务类型及金额数据 按照通用规则分类统计
     *
     * @param vcode
     * @param year  年份
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketTypeListByYear(String vcode, String year) {
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

        //按年获取基础分类数据
        List<TicketClTypeVO> ticketBaseTypeListByYear = this.getTicketBaseTypeListByYear(vcode, year);

        //TODO 云台山分类 其他暂不分类 后期处理
        if(vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")) {
            //对数据按统一分组规则进行分类.
            clTypeVOList = ticketTypeGroupStatistics(ticketBaseTypeListByYear);
        }else {
            clTypeVOList = ticketBaseTypeListByYear;
        }

        //写入缓存
        if (clTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, clTypeVOList);
        }

        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();

    }

    /**
     * 按年获取票务类型统计数据 数据按照实际票务类型进行分类统计
     *
     * @param vcode 景区代码
     * @param year  年
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketBaseTypeListByYear(String vcode, String year) {
        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTypeVO> clTypeVOList;

        //redis 缓存key
        String h = TABLE_NAME + TABLE_NAME + BASE + YEAR + COLON;
        String hk = YEAR + "|" + year + "|" + md5key;

        //读取缓存
        clTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (clTypeVOList != null && clTypeVOList.size() > 0) {
            return clTypeVOList;
        }
        clTypeVOList = new ArrayList<>();
        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");


        //拉取各月份基础统计数据
        for (String month : months) {
            String mon =year + "-" + month;
            try {
                if (sdf.parse(mon).getTime()>new Date().getTime()){
                    break;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            List<TicketClTypeVO> ticketTypeByMonth = this.getTicketBaseTypeListByMonth(vcode, year + "-" + month);
            clTypeVOList.addAll(ticketTypeByMonth);
        }

            //针对同一类数据进行合并
            clTypeVOList = ticketBaseTypeGroupStatistics(clTypeVOList);
        //写入缓存
        if (clTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, clTypeVOList);
        }

        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();

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
     * 针对基础票务类型数据进行整合.同一类行数据进行合并.
     *
     * @param source
     * @return
     */
    private List<TicketClTypeVO> ticketBaseTypeGroupStatistics(List<TicketClTypeVO> source) {

        String type;
        Map<String, Long> numsMap = new HashMap<>();
        Map<String, Integer> typecalMap = new HashMap<>();
        for (TicketClTypeVO clTypeVO : source) {
            type = clTypeVO.getTypes();
            int typecal = clTypeVO.getTypecal();
            long nums = clTypeVO.getNums();

            if (StringUtils.isBlank(type)) {
                type = "其他";
            }

            //获取历史数据 累计
            long oldNums = numsMap.get(type) != null ? numsMap.get(type) : 0;
            int oldTypecl = typecalMap.get(type) != null ? typecalMap.get(type) : 0;
            numsMap.put(type, nums + oldNums);
            typecalMap.put(type, typecal + oldTypecl);
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
     * 获取按年区间的票务类型统计数据 按统一规则进行分类
     *
     * @param vcode     景区代码
     * @param startYear 开始年
     * @param endYear   结束年
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketTypeListByYearSlot(String vcode, int startYear, int endYear) {
        List<TicketClTypeVO> clTypeVOList = new ArrayList<>();
        if (startYear > endYear)
            return new ArrayList<>();
        if (startYear == endYear){
            clTypeVOList = getTicketTypeListByYear(vcode, String.valueOf(startYear));
            // 这里对数据进行排序处理，按数量降序排序
            Collections.sort(clTypeVOList, new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                    TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                    if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                        return 1;
                    }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                        return 0;
                    }else {
                        return -1;
                    }
                }
            });
            return clTypeVOList;
        }

        Map<String, Long> countMap = new HashMap<>();
        Map<String, Integer> priceMap = new HashMap<>();
        //获取每年票务类型统计数据.
        long nums;
        int typecal;
        Long oldCount;
        Integer oldPrice;
        for (int i = startYear; i <= endYear; i++) {
            List<TicketClTypeVO> ticketTypeByYear = getTicketTypeListByYear(vcode, String.valueOf(i));
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
        // 这里对数据进行排序处理，按数量降序排序
        Collections.sort(clTypeVOList, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                    return 1;
                }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
        return clTypeVOList;
    }

    /**
     * 按年区间 获取 票务类型统计数据.  按原始票务类型进行分组
     *
     * @param vcode
     * @param startYear
     * @param endYear
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketBaseTypeListByYearSlot(String vcode, int startYear, int endYear) {

        List<TicketClTypeVO> clTypeVOList = new ArrayList<>();
        if (startYear > endYear)
            return new ArrayList<>();
        if (startYear == endYear)
            return this.getTicketBaseTypeListByYear(vcode, String.valueOf(startYear));

        Map<String, Long> countMap = new HashMap<>();
        Map<String, Integer> priceMap = new HashMap<>();
        //获取每年票务类型基础统计数据.
        long nums;
        int typecal;
        Long oldCount;
        Integer oldPrice;
        for (int i = startYear; i <= endYear; i++) {
            //获取每年统计数据
            List<TicketClTypeVO> ticketTypeByYear = this.getTicketBaseTypeListByYear(vcode, String.valueOf(i));
            //合并各类数据
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
     * 获取按天区间的票务类型统计数据 按通用规则进行分类统计
     *
     * @param vcode     景区编码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketTypeByDaySlot(String vcode, String startDate, String endDate) {


        //按天区间获取基础分类数据
        List<TicketClTypeVO> ticketBaseTypeByDaySlot = this.getTicketBaseTypeByDaySlot(vcode, startDate, endDate);

        //TODO 此处临时处理 云台山分类需求其他不分类 后期做改进
        if(vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")){
            //按照统一分类规则对数据进行数据归类
            List<TicketClTypeVO> ticketClTypeVOS = ticketTypeGroupStatistics(ticketBaseTypeByDaySlot);
            // 这里对数据进行排序处理，按数量降序排序
            Collections.sort(ticketClTypeVOS, new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                    TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                    if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                        return 1;
                    }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                        return 0;
                    }else {
                        return -1;
                    }
                }
            });
            return ticketClTypeVOS;
        }else {
            // 这里对数据进行排序处理，按数量降序排序
            Collections.sort(ticketBaseTypeByDaySlot, new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                    TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                    if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                        return 1;
                    }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                        return 0;
                    }else {
                        return -1;
                    }
                }
            });
            return ticketBaseTypeByDaySlot;
        }
    }

    /**
     * 按天区间 获取票务类型统计数据 按原始票务类型进行分组
     *
     * @param vcode
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTicketBaseTypeByDaySlot(String vcode, String startDate, String endDate) {
        List<String> dateList = DateUtil.getSubsectionDateList(startDate, endDate);
        if (dateList.size() == 0)
            return new ArrayList<>();

        List<Future<List<TicketClTypeVO>>> futureList = new ArrayList<>();
        //使用多线程去拉取每天的数据.
        ExecutorService es = Executors.newFixedThreadPool(31);

        for (String dateStr : dateList) {
            futureList.add(es.submit(new TicketTypeBaseByDayTask(vcode, dateStr)));
        }

        List<TicketClTypeVO> resultList = new ArrayList<>();

        for (Future<List<TicketClTypeVO>> listFuture : futureList) {
            try {
                resultList.addAll(listFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        //关闭线程池.释放资源
        es.shutdown();

        //针对同一类数据进行合并
        resultList = ticketBaseTypeGroupStatistics(resultList);

        return resultList;
    }


    /**
     * 获取按月区间的票务类型统计数据
     *
     * @param vcode 景区编码
     * @param start 开始月份
     * @param end   结束月份
     * @return
     */
    private List<TicketClTypeVO> getTicketTypeByMonthSlot(String vcode, String start, String end) {
        //
        return new ArrayList<>();
    }

    /**
     * 按年获取游客统计数据 按原始类型进行分组
     *
     * @param vcode
     * @param day   日期 格式: yyyy-MM-dd
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTouristBaseTypeListByDay(String vcode, String day) {

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
        ticketClTypeVOS = ticketDao.getTouristNumByTimeSlot(vcode, day + " 00:00:00", day + " 23:59:59");
        if (ticketClTypeVOS.size() > 0)
            RedisCache.putHash(redisTemplate, h, hk, ticketClTypeVOS);
        return ticketClTypeVOS;
    }

    /**
     * 2017-08-07 zhouq
     * 按月份 获取游客构成统计
     *
     * @param vcode 景区代码
     * @param month 月份
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTouristBaseTypeListByMonth(String vcode, String month) {

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

        List<TicketClTypeVO> touristTypeS = new ArrayList<>();
        try {
            //无缓存数据
            //第一种方式: 查询db 并写入缓存.
            //ticketClTypeVOS = ticketOldDao.getTouristNumByTimeSlot(vcode, month + "-01 00:00:00", month + "-31 23:59:59");

            /**
             * 第二种方式: 在缓存中获取每月的各天数据进行累加
             *
             */
            //获取当月的所有天
            Date parse = new SimpleDateFormat("yyyy-MM").parse(month);
            List<String> dayReport = DateUtil.getDayReport(parse);

            List<Future<List<TicketClTypeVO>>> futureList = new ArrayList<>();

            ExecutorService es = Executors.newCachedThreadPool();
            //每月份 最多31 天.无需设定过大的线程池.
//            ExecutorService es = ExecutorServiceUtils.getCachedThreadPool();
            for (String s : dayReport) {
//                if (es.isShutdown()){
//                    es =  ExecutorServiceUtils.getCachedThreadPool();
//                }
                futureList.add(es.submit(new TouristBaseTypeByDayTask(vcode, s)));
            }

            //关闭线程池.释放资源
            es.shutdown();

            for (Future<List<TicketClTypeVO>> listFuture : futureList) {
                try {
                    touristTypeS.addAll(listFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            //数据分类
            ticketClTypeVOS = touristBaseTypeGroupStatistics(touristTypeS);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (ticketClTypeVOS != null && ticketClTypeVOS.size() > 0)
            RedisCache.putHash(redisTemplate, h, hk, ticketClTypeVOS);
        return ticketClTypeVOS != null ? ticketClTypeVOS : new ArrayList<>();
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
        if (jqTicketSealVoList != null && jqTicketSealVoList.size() > 0){
            // 这里对数据进行排序处理，按数量降序排序
            Collections.sort(jqTicketSealVoList, new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                    TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                    if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                        return 1;
                    }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                        return 0;
                    }else {
                        return -1;
                    }
                }
            });
            return jqTicketSealVoList;
        }

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
            List<TicketClTypeVO> touristTypeByMonth = this.getTouristBaseTypeListByMonth(vcode, year + "-" + month);
            jqTicketSealVoList.addAll(touristTypeByMonth);
        }

        //最后对数据进行分类处理.
        jqTicketSealVoList = touristBaseTypeGroupStatistics(jqTicketSealVoList);

        //写入缓存
        if (jqTicketSealVoList.size() > 0) {
            RedisCache.putHash(redisTemplate, H, HK, jqTicketSealVoList);
        }
        // 这里对数据进行排序处理，按数量降序排序
        Collections.sort(jqTicketSealVoList, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                TicketClTypeVO ticketClTypeVO1 = (TicketClTypeVO) o1;
                TicketClTypeVO ticketClTypeVO2 = (TicketClTypeVO) o2;
                if(ticketClTypeVO1.getNums() < ticketClTypeVO2.getNums()){
                    return 1;
                }else if(ticketClTypeVO1.getNums() == ticketClTypeVO2.getNums()){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
        return jqTicketSealVoList;

    }


    /**
     * 按年获取游客统计数据 按原始类型进行分组
     *
     * @param vcode 景区代码
     * @param year  年份
     * @return
     */
    @Override
    public List<TicketClTypeVO> getTouristBaseTypeListByYear(String vcode, String year) {

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
//        ticketClTypeVOS = ticketOldDao.getTouristNumByTimeSlot(vcode, year + "-01-01 00:00:00", year + "-12-31 23:59:59");

        /**
         * 第二种方式: 在缓存中获取当年每月的数据进行累加
         *
         */
        ticketClTypeVOS = new ArrayList<>();
        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
//        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Future<List<TicketClTypeVO>>> futureList = new ArrayList<>();
        StringBuffer month;
        for (String monthStr : months) {
//            month = new StringBuffer();
//            month.append(year).append("-").append(monthStr);
//            Future<List<TicketClTypeVO>> submit = executorService.submit(new TouristTypeByMonthTask(vcode, month.toString()));
//            futureList.add(submit);
            List<TicketClTypeVO> touristTypeByMonth = getTouristBaseTypeListByMonth(vcode, year + "-" + monthStr);
            ticketClTypeVOS.addAll(touristTypeByMonth);
        }

//        for (Future<List<TicketClTypeVO>> listFuture : futureList) {
//            try {
//                ticketClTypeVOS.addAll(listFuture.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }

//        executorService.shutdown();
        //数据进行分类
        ticketClTypeVOS = touristBaseTypeGroupStatistics(ticketClTypeVOS);

        if (ticketClTypeVOS.size() > 0)
            RedisCache.putHash(redisTemplate, h, hk, ticketClTypeVOS);
        return ticketClTypeVOS;
    }


    /** 按天区间获取游客种类数据  数据按照原始类型进行分组统计.
     * @param vcode
     * @param startDate
     * @param endDate
     * @return
     */
    private List<TicketClTypeVO> getTouristBaseTypeListByDaySlot(String vcode, String startDate, String endDate) {

        List<String> dateList = DateUtil.getSubsectionDateList(startDate, endDate);
        if (dateList.size() == 0)
            return new ArrayList<>();

        List<Future<List<TicketClTypeVO>>> futureList = new ArrayList<>();
        //使用多线程去拉取每天的数据.
        ExecutorService es = Executors.newFixedThreadPool(31);
        for (String dateStr : dateList) {
            futureList.add(es.submit(new TouristBaseTypeByDayTask(vcode, dateStr)));
        }

        List<TicketClTypeVO> resultList = new ArrayList<>();
        for (Future<List<TicketClTypeVO>> listFuture : futureList) {
            try {
                resultList.addAll(listFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        //关闭线程池.释放资源
        es.shutdown();
        //数据汇总
        resultList = touristBaseTypeGroupStatistics(resultList);

        return resultList;
    }

    /**
     * 2017-08-17 zhouq
     * 按年区间 获取游客构成统计
     *
     * @param vcoe
     * @param startYear
     * @param endYear
     * @return
     */
    private List<TicketClTypeVO> getTouristBaseTypeListByYearSlot(String vcoe, int startYear, int endYear) {

        if (endYear < startYear)
            return new ArrayList<>();

        //开始年份结束年份 相等 直接返回一个即可 返回年份的数据不需要再 归类
        if (endYear == startYear) {
            List<TicketClTypeVO> touristTypeByYear = this.getTouristBaseTypeListByYear(vcoe, String.valueOf(startYear));
            return touristTypeByYear;
        }

        //开始年份小于结束年份 则获取各年份的数据 然后需要进行归类.
        List<TicketClTypeVO> clTypeVOList = new ArrayList<>();
        for (int year = startYear; year <= endYear; year++) {
            clTypeVOList.addAll(this.getTouristBaseTypeListByYear(vcoe, String.valueOf(year)));
        }

        //数据进行一次归类统计
        clTypeVOList = this.touristBaseTypeGroupStatistics(clTypeVOList);

        return clTypeVOList;
    }


    /**
     * 对(游客)客票种类数据进行分类合并
     *
     * @param source
     * @return
     */
    private List<TicketClTypeVO> touristBaseTypeGroupStatistics(List<TicketClTypeVO> source) {

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
     * 汇总票务数据
     *
     * @param source
     * @return
     */
    private TicketClTendVo ticketClTendVoStatistics(List<TicketClTendVo> source) {

        if (source == null || source.size() == 0)
            return new TicketClTendVo();

        int sumNums = 0, sumPrice = 0;

        for (TicketClTendVo tendVo : source) {
            if (tendVo == null)
                continue;
            sumNums += tendVo.getTicket_num() != null ? Integer.valueOf(tendVo.getTicket_num()) : 0;
            sumPrice += tendVo.getTicket_totalPrice() != null ? Integer.valueOf(tendVo.getTicket_totalPrice()) : 0;
        }

        return new TicketClTendVo("", String.valueOf(sumNums), String.valueOf(sumPrice));
    }

    /**
     * 多线程处理按天获取票务基础类型数据任务.
     */
    private class TicketTypeBaseByDayTask implements Callable<List<TicketClTypeVO>> {

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

        public TicketTypeBaseByDayTask(String vcode, String date) {
            this.date = date;
            this.vcode = vcode;
        }

        public TicketTypeBaseByDayTask() {
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return 返回按天查询的票务类型数据统计
         * @throws Exception if unable to compute a result
         */
        @Override
        public List<TicketClTypeVO> call() throws Exception {
            logger.info(String.format("线程:%s 正在处理数据 vcode:%s,date:%s", Thread.currentThread().getName(), vcode, date));
            //按天获取基础统计数据
            List<TicketClTypeVO> ticketTypeByDay = getTicketBaseTypeListByDay(vcode, date);
            return ticketTypeByDay;
        }
    }

    /**
     * 多线程读取 按天获取客票种类数据
     */
    private class TouristBaseTypeByDayTask implements Callable<List<TicketClTypeVO>> {

        private String date;
        private String vcode;

        public TouristBaseTypeByDayTask(String vcode, String date) {
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

        public TouristBaseTypeByDayTask() {
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
            System.out.println(String.format("线程:%s 正在处理数据 vcode:%s,date:%s", Thread.currentThread().getName(), vcode, date));
            List<TicketClTypeVO> touristTypeByDay = getTouristBaseTypeListByDay(vcode, date);
            return touristTypeByDay;
        }
    }


    /**
     * 多线程读取 按月获取客票种类数据
     */
    private class TouristTypeByMonthTask implements Callable<List<TicketClTypeVO>> {

        private String date;
        private String vcode;

        public TouristTypeByMonthTask() {
        }

        public TouristTypeByMonthTask(String vcode, String date) {
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

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public List<TicketClTypeVO> call() throws Exception {
//            System.out.println(String.format("正在获取月份数据:vcode:%s,月份:%s",vcode,date));
            return getTouristBaseTypeListByMonth(vcode, date);
        }
    }

    private class TicketNumAndPriceByDay implements Callable<TicketClTendVo> {

        private String vcode;
        private String date;

        public TicketNumAndPriceByDay() {
        }

        public TicketNumAndPriceByDay(String vcode, String date) {
            this.vcode = vcode;
            this.date = date;
        }

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public TicketClTendVo call() throws Exception {
//            logger.debug(String.format("线程:%s 正在处理数据 vcode:%s,date:%s",Thread.currentThread().getName(),vcode,date));
            TicketClTendVo ticketCountAndPriceByDay = getTicketCountAndPriceByDay(vcode, date);
            return ticketCountAndPriceByDay;
        }
    }
}
