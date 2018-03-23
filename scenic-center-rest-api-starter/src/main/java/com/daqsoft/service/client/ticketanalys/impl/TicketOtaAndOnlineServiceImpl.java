package com.daqsoft.service.client.ticketanalys.impl;

import com.daqsoft.mapper.client.ticket.TicketOTADao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.ticketanalys.TicketOtaAndOnlineService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.validator.RRException;
import com.daqsoft.vo.client.madeVoBean.TicketClTendVo;
import com.daqsoft.vo.client.madeVoBean.TicketClTypeVO;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-09-01 9:07
 * @Version: v1.0.0
 * @Describe:  票务统计分析  OTA 和线上票务 分析统计 service 实现
 */
@Service
public class TicketOtaAndOnlineServiceImpl implements TicketOtaAndOnlineService {

    /**
     * 1.线上 / ota  统计数据 来自 jq_ota_data 表. 表中 name 字段记录了各OTA
     * <p>
     * 2.ota 为各供应商 例如 携程 去哪儿 驴妈妈 等...
     */

    //redis中的key值
    private static final String TABLE_NAME = "ticket:";
    private static final String OTA = "ota:";
    private static final String ON_LINE = "online:";
    private static final String ON_LINE_LIST = "onlinelist:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TicketOTADao ticketOTADao;

    /**
     * 获取当日OTA 总销量
     *
     * @param vcode 景区编码
     * @param day   日期
     * @return
     */
    @Override
    public TicketClTendVo getOnlineTicketByDay(String vcode, String day) {

        String start = day + " 00:00:00";
        String end = day + " 23:59:59";
        //redis 缓存key
        String h = TABLE_NAME + ON_LINE + RedisKey.DAY + RedisKey.COLON;
        String hk = RedisKey.DAY + "|" + day + "|" + DigestUtils.md5Hex(vcode);

        TicketClTendVo vo;

        Object hash = RedisCache.getHash(redisTemplate, h, hk);
        if (hash != null) {
            JSONObject jsonObject = JSONObject.fromObject(hash);
            vo = (TicketClTendVo) JSONObject.toBean(jsonObject, TicketClTendVo.class);
            return vo;
        }

        vo = ticketOTADao.getOtaSale(vcode, start, end);
        vo.setTicket_time(day);
//        if (vo != null && !"0".equals(vo.getTicket_num())) {
//
//        }
        RedisCache.putHash(redisTemplate, h, hk, vo);
        return vo;
    }

    /**
     * 按月份获取线上票务销售数量及金额
     *
     * @param vcode 景区编码
     * @param month 月份
     * @return 返回当月票务数量及金额
     */
    @Override
    public TicketClTendVo getOnlineTicketByMonth(String vcode, String month) {

        //redis 缓存key
        String h = TABLE_NAME + ON_LINE + RedisKey.MONTH + RedisKey.COLON;
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
                futureList.add(es.submit(new OTASaleByDayTask(vcode, dateStr)));
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
//        if (StringUtils.isNotBlank(vo.getTicket_num()))
        RedisCache.putHash(redisTemplate, h, hk, vo);

        return vo;
    }

    /**
     * 按季度获取线上票务销售数量及金额
     *
     * @param vcode   景区编码
     * @param year    年份
     * @param quarter 季度
     * @return 返回当月票务数量及金额
     */
    @Override
    public TicketClTendVo getOnlineTicketByQuarter(String vcode, String year, String quarter) {

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
        String H = TABLE_NAME + ON_LINE + RedisKey.QUARTER + RedisKey.COLON;
        String HK = RedisKey.QUARTER + "|" + year + "_" + quarter + "|" + DigestUtils.md5Hex(vcode);

        ticketClTendVo = (TicketClTendVo) RedisCache.getHash(redisTemplate, H, HK);

        if (ticketClTendVo != null)
            return ticketClTendVo;

        String[] months = monthStr.split(",");

        List<TicketClTendVo> resuList = new ArrayList<>();
        //获取各月份数据
        for (String month : months) {
            TicketClTendVo otaSaleByMonth = getOnlineTicketByMonth(vcode, year + "-" + month);
            resuList.add(otaSaleByMonth);
        }

        //汇总
        ticketClTendVo = ticketClTendVoStatistics(resuList);

        ticketClTendVo.setTicket_time(year + "_" + quarter);
        //写入缓存
        if (StringUtils.isNotBlank(ticketClTendVo.getTicket_num()))
            RedisCache.putHash(redisTemplate, H, HK, ticketClTendVo);

        return ticketClTendVo;
    }

    /**
     * 按年获取线上票务销售数量及金额
     *
     * @param vcode 景区编码
     * @param year  月份
     * @return 返回当月票务数量及金额
     */
    @Override
    public TicketClTendVo getOnlineTicketByYear(String vcode, String year) {

        //redis 缓存key
        String H = TABLE_NAME + ON_LINE + RedisKey.YEAR + RedisKey.COLON;
        String HK = RedisKey.YEAR + "|" + year + "|" + DigestUtils.md5Hex(vcode);

        TicketClTendVo tendVo;

        //读取缓存
        tendVo = (TicketClTendVo) RedisCache.getHash(redisTemplate, H, HK);
        if (tendVo != null) {
            return tendVo;
        }

        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        List<TicketClTendVo> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        //拉取各月份基础统计数据
        String mon;
        for (String month : months) {
            mon = year + "-" + month;
            try {
                if (sdf.parse(mon).getTime() > new Date().getTime()) {
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            TicketClTendVo otaSaleByMonth = getOnlineTicketByMonth(vcode, mon);
            list.add(otaSaleByMonth);
        }

        tendVo = ticketClTendVoStatistics(list);
        tendVo.setTicket_time(year);
        //写入缓存
//        if (StringUtils.isNotBlank(tendVo.getTicket_num()) )
        RedisCache.putHash(redisTemplate, H, HK, tendVo);

        return tendVo;

    }

    /**
     * 按年份获取  线上 各月份票务销售数据.
     * @param vcode
     * @param year
     * @return
     */
    @Override
    public List<TicketClTendVo> getMonthsOfOnlineTicketListByYear(String vcode, String year) {

        //redis 缓存key
        String H = TABLE_NAME + ON_LINE_LIST + RedisKey.YEAR + RedisKey.COLON;
        String HK = RedisKey.YEAR + "|" + year + "|" + DigestUtils.md5Hex(vcode);

        //读取缓存
        List<TicketClTendVo> list = (List<TicketClTendVo>) RedisCache.getHash(redisTemplate, H, HK);
        if (list != null) {
            return list;
        }

        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        list = new ArrayList<>();

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

            TicketClTendVo otaSaleByMonth = getOnlineTicketByMonth(vcode, mon);
            list.add(otaSaleByMonth);
        }

        //写入缓存
        RedisCache.putHash(redisTemplate, H, HK, list);

        return list;
    }

    /**
     * 按年获取线上票务销售数量及金额
     *
     * @param vcode 景区编码
     * @param start
     * @param end   @return 返回当月票务数量及金额
     */
    @Override
    public TicketClTendVo getOnlineTicketByYearRange(String vcode, int start, int end) {
        if (start > end)
            return new TicketClTendVo();
        if (start == end)
            return getOnlineTicketByYear(vcode, String.valueOf(start));

        List<TicketClTendVo> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(getOnlineTicketByYear(vcode, String.valueOf(i)));
        }

        TicketClTendVo tendVo = ticketClTendVoStatistics(list);
        return tendVo;
    }

    /**
     * 按日区间获取 每日OTA总销量
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param vcode
     */
    @Override
    public TicketClTendVo getOnLineTicketBYDayRange(String vcode, String startDate, String endDate) {

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
            futureList.add(es.submit(new OTASaleByDayTask(vcode, dateStr)));
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
     * 按天获取 各ota 销售情况
     *
     * @param vcode
     * @param day
     * @return
     */
    @Override
    public List<TicketClTypeVO> getOtaSaleListByDay(String vcode, String day) {

        String start = day + " 00:00:00";
        String end = day + " 23:59:59";

        //redis 缓存key
        String h = TABLE_NAME + OTA + RedisKey.DAY + RedisKey.COLON;
        String hk = RedisKey.DAY + "|" + day + "|" + DigestUtils.md5Hex(vcode);

        List<TicketClTypeVO> typeVOList;
        typeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
//        Object hash = RedisCache.getHash(redisTemplate, h, hk);
//        JSONArray array = JSONArray.fromObject(hash);
//        List list = JSONArray.toList(array, new TicketClTypeVO(), new JsonConfig());

        if (typeVOList != null && typeVOList.size() > 0) {
            return typeVOList;
        }

        typeVOList = ticketOTADao.getOtaSaleListByTimeSlot(vcode, start, end);

        if (typeVOList != null && typeVOList.size() > 0)
            RedisCache.putHash(redisTemplate, h, hk, typeVOList);

        return typeVOList != null ? typeVOList : new ArrayList<>();
    }

    /**
     * 按天区间获取 各OTA 销售数据
     *
     * @param vcode
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<TicketClTypeVO> getOtaSaleListByDayRange(String vcode, String startDate, String endDate) {


        List<String> dateList = DateUtil.getSubsectionDateList(startDate, endDate);

        List<TicketClTypeVO> clTypeVOList = new ArrayList<>();

        //获取每天的OTA 各销售情况
        for (String date : dateList) {
            List<TicketClTypeVO> otaSaleListByDay = getOtaSaleListByDay(vcode, date);
            clTypeVOList.addAll(otaSaleListByDay);
        }

        //汇总
        clTypeVOList = otaTypeGroupStatistics(clTypeVOList);

        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();
    }

    /**
     * 按月份 获取 各ota 销售情况
     *
     * @param vcode
     * @param month 月份 格式:yyyy-MM
     * @return
     */
    @Override
    public List<TicketClTypeVO> getOtaSaleListByMonth(String vcode, String month) {

        List<TicketClTypeVO> clTypeVOList;

        //redis 缓存key
        String h = TABLE_NAME + OTA + RedisKey.MONTH + RedisKey.COLON;
        String hk = RedisKey.MONTH + "|" + month + "|" + DigestUtils.md5Hex(vcode);

        //读取缓存
        clTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (clTypeVOList != null && clTypeVOList.size() > 0) {
            return clTypeVOList;
        }

        try {

            Date parse = new SimpleDateFormat("yyyy-MM").parse(month);
            //得到此月份的所有天
            List<String> dayReport = DateUtil.getDayReport(parse);


            List<Future<List<TicketClTypeVO>>> futureList = new ArrayList<>();
            //使用多线程去拉取每天的数据.

            ExecutorService es = Executors.newCachedThreadPool();

            for (String dateStr : dayReport) {
                futureList.add(es.submit(new OtaSaleListByDayTask(vcode, dateStr)));
            }

            clTypeVOList = new ArrayList<>();
            for (Future<List<TicketClTypeVO>> listFuture : futureList) {
                try {
                    List<TicketClTypeVO> ticketClTypeVOS = listFuture.get();
                    if (ticketClTypeVOS != null) {
                        clTypeVOList.addAll(ticketClTypeVOS);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            //关闭线程池.释放资源
            es.shutdown();

            //数据分类
            clTypeVOList = otaTypeGroupStatistics(clTypeVOList);

            if (clTypeVOList.size() > 0)
                RedisCache.putHash(redisTemplate, h, hk, clTypeVOList);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();
    }

    /**
     * 按季度获取 各ota 销售情况
     *
     * @param vcode   景区编码
     * @param year    年份
     * @param quarter 季度 1/2/3/4
     * @return 返回按年度中季度 的票务类型统计数据
     */
    @Override
    public List<TicketClTypeVO> getOtaSaleListByQuarter(String vcode, String year, String quarter) {
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
            default:
                throw new RRException("季度参数格式错误:1/2/3/4", 400);
        }

        if (StringUtils.isBlank(monthStr))
            return new ArrayList<>();

        List<TicketClTypeVO> ticketClTypeVOList;
        //redis 缓存key
        String H = TABLE_NAME + OTA + RedisKey.QUARTER + RedisKey.COLON;
        String HK = RedisKey.QUARTER + "|" + year + "_" + quarter + "|" + DigestUtils.md5Hex(vcode);
        //读取缓存
        ticketClTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, H, HK);
        if (ticketClTypeVOList != null && ticketClTypeVOList.size() > 0)
            return ticketClTypeVOList;

        ticketClTypeVOList = new ArrayList<>();
        //读取当前季度下各月份的具体数据
        String[] months = monthStr.split(",");
        for (String month : months) {
            List<TicketClTypeVO> ticketTypeByMonth = getOtaSaleListByMonth(vcode, year + "-" + month);
            ticketClTypeVOList.addAll(ticketTypeByMonth);
        }

        //对数据进行分类.
        ticketClTypeVOList = otaTypeGroupStatistics(ticketClTypeVOList);
        //写入缓存
        if (ticketClTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, H, HK, ticketClTypeVOList);
        }

        return ticketClTypeVOList;
    }

    /**
     * 按年 获取 各ota 销售情况
     *
     * @param vcode
     * @param year  年 格式:yyyy
     * @return
     */
    @Override
    public List<TicketClTypeVO> getOtaSaleListByYear(String vcode, int year) {

        String md5key = DigestUtils.md5Hex(vcode);
        List<TicketClTypeVO> clTypeVOList = new ArrayList<>();

        //redis 缓存key
        String h = TABLE_NAME + OTA + RedisKey.YEAR + RedisKey.COLON;
        String hk = RedisKey.YEAR + "|" + year + "|" + md5key;

        //读取缓存
        clTypeVOList = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, h, hk);
        if (clTypeVOList != null) {
            return clTypeVOList;
        }
        clTypeVOList = new ArrayList<>();
        String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        //拉取各月份基础统计数据
        String mon;
        for (String month : months) {
            mon = year + "-" + month;
            try {
                if (sdf.parse(mon).getTime() > new Date().getTime()) {
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<TicketClTypeVO> ticketTypeByMonth = getOtaSaleListByMonth(vcode, year + "-" + month);
            clTypeVOList.addAll(ticketTypeByMonth);
        }

        //对数据进行分类.
        clTypeVOList = otaTypeGroupStatistics(clTypeVOList);

        //写入缓存
//        if (clTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, h, hk, clTypeVOList);
//        }

        return clTypeVOList != null ? clTypeVOList : new ArrayList<>();

    }

    /**
     * 按年区间 获取 各ota 销售情况
     *
     * @param vcode
     * @param startYear 开始年份
     * @param endYear   结束年份
     * @return
     */
    @Override
    public List<TicketClTypeVO> getOtaSaleListByYearRange(String vcode, int startYear, int endYear) {

        List<TicketClTypeVO> clTypeVOList = new ArrayList<>();
        if (startYear > endYear)
            return new ArrayList<>();
        if (startYear == endYear)
            return getOtaSaleListByYear(vcode, startYear);

        Map<String, Long> countMap = new HashMap<>();
        Map<String, Integer> priceMap = new HashMap<>();
        //获取每年票务类型统计数据.
        long nums;
        int typecal;
        Long oldCount;
        Integer oldPrice;
        for (int i = startYear; i <= endYear; i++) {
            List<TicketClTypeVO> ticketTypeByYear = getOtaSaleListByYear(vcode, i);
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

    @Override
    public List<Map> otaSaleByYear(String vcode, int year) {
        List datas = new ArrayList();
        List<TicketClTypeVO> list = getOtaSaleListByYear(vcode, year);
        for (TicketClTypeVO ticketClTypeVO : list) {
            Map map = new HashMap();
            String otaName = ticketClTypeVO.getTypes();
            Integer totalQuantity = ticketClTypeVO.getTypecal();
            map.put("name",otaName);
            map.put("value",totalQuantity);
            datas.add(map);
        }
        return datas;
    }

    @Override
    public List<Map> otaSaleByQuarter(String vcode, String year) {
        if (StringUtils.isBlank(vcode) || StringUtils.isBlank(year))
            return new ArrayList<>();

        List<Map> datas = new ArrayList();

        String[] quarter = {"01,02,03","04,05,06","07,08,09","10,11,12"};
        String[] quarterStrs = {"第一季度", "第二季度", "第三季度", "第四季度"};
        for(int i = 0; i < quarter.length; i++) {
            String monthStr = quarter[i];
            List<TicketClTypeVO> ticketClTypeVOListT;
            List<TicketClTypeVO> ticketClTypeVOListY;
            Map map = new HashMap();
            map.put("quarter", quarterStrs[i]);
            //redis 缓存key
            String H = TABLE_NAME + OTA + RedisKey.QUARTER + RedisKey.COLON;
            String tHK = RedisKey.QUARTER + "|" + year + "_" + (i+1) + "|" + DigestUtils.md5Hex(vcode);
            String yHK = RedisKey.QUARTER + "|" + (Integer.parseInt(year) - 1) + "_" + (i+1) + "|" + DigestUtils.md5Hex(vcode);
            //读取缓存
            //今年
            ticketClTypeVOListT = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, H, tHK);
            int thisAmount = 0;
            if(ticketClTypeVOListT != null) {
                for(TicketClTypeVO ticketClTypeVOt : ticketClTypeVOListT) {
                    thisAmount = thisAmount + Integer.parseInt(ticketClTypeVOt.getNums() + "");
                }
                map.put("thisAmount",thisAmount);
            }else {
                ticketClTypeVOListT = new ArrayList<>();
                Map tMap = new HashMap();
                //通过累加重新查询季度数据并存入缓存
                tMap = putOTAListMonthDayToRedis(monthStr, ticketClTypeVOListT, H, tHK, vcode, year);
                map.put("thisAmount",tMap.get("amount") != null?Integer.valueOf(tMap.get("amount").toString()):0);
            }

            //去年
            ticketClTypeVOListY = (List<TicketClTypeVO>) RedisCache.getHash(redisTemplate, H, yHK);
            if(ticketClTypeVOListY != null) {
                int lastAmount = 0;
                for(TicketClTypeVO ticketClTypeVOy : ticketClTypeVOListY) {
                    lastAmount = lastAmount + Integer.parseInt(ticketClTypeVOy.getNums() + "");
                }
                map.put("lastAmount",lastAmount);
            }else {
                ticketClTypeVOListY = new ArrayList<>();
                Map yMap = new HashMap();
                //通过累加重新查询季度数据并存入缓存
                String yearY = Integer.parseInt(year)-1 +"";
                yMap = putOTAListMonthDayToRedis(monthStr, ticketClTypeVOListY, H, yHK, vcode, yearY);
                map.put("lastAmount", yMap.get("amount") != null?Integer.valueOf(yMap.get("amount").toString()):0);
            }
            datas.add(map);

        }
        return datas;
    }

    /**
     *累加重新查询季度票务数据并存入缓存
     *
     * @param monthStr
     * @param ticketClTypeVOList
     * @param H
     * @param HK
     * @param vcode
     * @param year
     * @return
     */
    private Map putOTAListMonthDayToRedis(String monthStr, List<TicketClTypeVO> ticketClTypeVOList, String H, String HK, String vcode, String year) {
        Map map = new HashMap();
        int amount = 0;
        String[] months = monthStr.split(",");
        for (String month : months) {
            List<TicketClTypeVO> ticketTypeByMonth = getOtaSaleListByMonth(vcode, year + "-" + month);
            ticketClTypeVOList.addAll(ticketTypeByMonth);
        }
        if(ticketClTypeVOList.isEmpty() || ticketClTypeVOList.size() == 0){
            map.put("amount","0");
        }else {
            //对数据进行分类.
            ticketClTypeVOList = otaTypeGroupStatistics(ticketClTypeVOList);
            for(TicketClTypeVO ticketClTypeVOt : ticketClTypeVOList) {
                amount = amount + Integer.parseInt(ticketClTypeVOt.getNums() + "");
            }
            map.put("amount",amount);
        }
        //写入缓存
        if (ticketClTypeVOList.size() > 0) {
            RedisCache.putHash(redisTemplate, H, HK, ticketClTypeVOList);
        } else {
            RedisCache.putHash(redisTemplate, H, HK, new ArrayList<>());
        }

        return map;
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
     * 对OTA种类数据进行分类
     *
     * @param source
     * @return
     */
    private List<TicketClTypeVO> otaTypeGroupStatistics(List<TicketClTypeVO> source) {

        String type;
        Map<String, Long> numsMap = new HashMap<>();
        Map<String, Integer> priceMap = new HashMap<>();
        for (TicketClTypeVO clTypeVO : source) {
            type = clTypeVO.getTypes();
            long nums = clTypeVO.getNums();
            int price = clTypeVO.getTypecal();
            //获取历史数据 累计
            long oldNums = numsMap.get(type) != null ? numsMap.get(type) : 0;
            int oldPrice = priceMap.get(type) != null ? priceMap.get(type) : 0;
            numsMap.put(type, nums + oldNums);
            priceMap.put(type, price + oldPrice);

        }

        List<TicketClTypeVO> resultList = new ArrayList<>();
        TicketClTypeVO clTypeVO;
        for (String k : numsMap.keySet()) {
            if (k == null)
                continue;
            clTypeVO = new TicketClTypeVO(numsMap.get(k), k, priceMap.get(k));
            resultList.add(clTypeVO);
        }

        return resultList;
    }


    /**
     * 多线程处理按天获 各OTA销售数据任务.
     */
    private class OtaSaleListByDayTask implements Callable<List<TicketClTypeVO>> {

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

        public OtaSaleListByDayTask(String vcode, String date) {
            this.date = date;
            this.vcode = vcode;
        }

        public OtaSaleListByDayTask() {
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return 返回按天查询的 各OTA 销售数据
         * @throws Exception if unable to compute a result
         */
        @Override
        public List<TicketClTypeVO> call() throws Exception {
            System.out.println(String.format("线程:%s 正在处理数据 vcode:%s,date:%s", Thread.currentThread().getName(), vcode, date));
            List<TicketClTypeVO> ticketTypeByDay = getOtaSaleListByDay(vcode, date);
            return ticketTypeByDay;
        }
    }


    /**
     * 多线程拉取 按天 OTA 销售总数
     */
    private class OTASaleByDayTask implements Callable<TicketClTendVo> {

        private String vcode;
        private String day;

        public OTASaleByDayTask() {
        }

        public OTASaleByDayTask(String vcode, String day) {
            this.vcode = vcode;
            this.day = day;
        }

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public TicketClTendVo call() throws Exception {
//            System.out.println(String.format("线程:%s 正在处理数据 vcode:%s,date:%s", Thread.currentThread().getName(), vcode, day));
            return getOnlineTicketByDay(vcode, day);
        }
    }
}
