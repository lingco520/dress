package com.daqsoft.controller.client.passengerFlow;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.service.client.passengerFlowService.RealBigPeopleService;
import com.daqsoft.service.client.ticketanalys.TicketService;
import com.daqsoft.utils.client.*;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.*;

/**
 * 客流分析
 * lrd:2017-8-15开始重构
 * 取消多维度SQL发送查询
 * 通过缓存获取，日数据补偿方式。累计月，年。保证数据实时
 */
@Api("客流量分析--团队/散客-小时/日/月/年")
@RestController
@RequestMapping("/rest/bigRealPeople")
public class RealBigPeopleController {
    private Logger logger = LoggerFactory.getLogger(RealBigPeopleController.class);
    @Autowired
    private RealBigPeopleService bigRealPeopleService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TicketService ticketService;

    /**
     * 按 季度 筛选团散
     */
    @GetMapping("/findTsByQuarter")
    public Object findTsByQuarter(String year, String vcode, String quarter) {
        Assert.paramIsBlank(vcode, ExceptionConstant.VCODE_NOT_NULL);
        Assert.paramIsYear(year, ExceptionConstant.IS_NOT_YEAR);
        try {
            List<TicketClTypeVO> ticketTypeListByQuarter = ticketService.getTicketTypeListByQuarter(vcode, year, quarter);
            Map resMap = new HashMap();
            List numList = new ArrayList();
            List moneyList = new ArrayList();
            for (TicketClTypeVO clTypeVO : ticketTypeListByQuarter) {
                BaseCommonVo numVo = new BaseCommonVo();
                numVo.setName(clTypeVO.getTypes());
                numVo.setValue(clTypeVO.getNums());
                numList.add(numVo);
                BaseCommonVo moneyVo = new BaseCommonVo();
                moneyVo.setName(clTypeVO.getTypes());
                moneyVo.setValue(clTypeVO.getTypecal());
                moneyList.add(moneyVo);
            }
            resMap.put("numList", numList);
            resMap.put("moneyList", moneyList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("按季度筛选团散", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 按 年 时间区间 筛选团散
     */
    @GetMapping("/findTsByYear")
    public Object findTsByYear(String start, String vcode, String end) {
        Assert.paramIsBlank(vcode, ExceptionConstant.VCODE_NOT_NULL);
        Assert.paramIsYear(start, ExceptionConstant.IS_NOT_YEAR);
        Assert.paramIsYear(end, ExceptionConstant.IS_NOT_YEAR);
        try {
            List<TicketClTypeVO> ticketTypeListBySlot = ticketService.getTicketTypeListBySlot(vcode, start, end, RedisKey.YEAR);
            Map resMap = new HashMap();
            List numList = new ArrayList();
            List moneyList = new ArrayList();
            for (TicketClTypeVO clTypeVO : ticketTypeListBySlot) {
                BaseCommonVo numVo = new BaseCommonVo();
                numVo.setName(clTypeVO.getTypes());
                numVo.setValue(clTypeVO.getNums());
                numList.add(numVo);
                BaseCommonVo moneyVo = new BaseCommonVo();
                moneyVo.setName(clTypeVO.getTypes());
                moneyVo.setValue(clTypeVO.getTypecal());
                moneyList.add(moneyVo);
            }
            resMap.put("numList", numList);
            resMap.put("moneyList", moneyList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 团队散客_日期时间段筛选
     */
    @GetMapping("/findTsByDay")
    public Object findTsByDay(String start, String vcode, String end) {
        Assert.paramIsBlank(vcode, ExceptionConstant.VCODE_NOT_NULL);
        Assert.paramIsDate(start, ExceptionConstant.IS_NOT_DATE);
        Assert.paramIsDate(end, ExceptionConstant.IS_NOT_DATE);
        try {
            List<TicketClTypeVO> ticketClTypeVOS = ticketService.getTicketTypeListBySlot(vcode,start, end, RedisKey.DAY);
            Map resMap = new HashMap();
            List numList = new ArrayList();
            List moneyList = new ArrayList();
            for (TicketClTypeVO clTypeVO : ticketClTypeVOS) {
                BaseCommonVo numVo = new BaseCommonVo();
                numVo.setName(clTypeVO.getTypes());
                numVo.setValue(clTypeVO.getNums());
                numList.add(numVo);
                BaseCommonVo moneyVo = new BaseCommonVo();
                moneyVo.setName(clTypeVO.getTypes());
                moneyVo.setValue(clTypeVO.getTypecal());
                moneyList.add(moneyVo);
            }
            resMap.put("numList", numList);
            resMap.put("moneyList", moneyList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("团队散客_日期时间段筛选", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 团队散客_月份时间段筛选
     */
    @GetMapping("/findTsByMonth")
    public Object find_ts_mm(String month, String vcode) {
        Assert.paramIsBlank(vcode, ExceptionConstant.VCODE_NOT_NULL);
        Assert.paramIsMonth(month, ExceptionConstant.IS_NOT_MONTH);
        try {
            List<TicketClTypeVO> ticketClTypeVOS = ticketService.getTicketTypeList(vcode,month, RedisKey.MONTH);
            Map resMap = new HashMap();
            List numList = new ArrayList();
            List moneyList = new ArrayList();
            for (TicketClTypeVO clTypeVO : ticketClTypeVOS) {
                BaseCommonVo numVo = new BaseCommonVo();
                numVo.setName(clTypeVO.getTypes());
                numVo.setValue(clTypeVO.getNums());
                numList.add(numVo);
                BaseCommonVo moneyVo = new BaseCommonVo();
                moneyVo.setName(clTypeVO.getTypes());
                moneyVo.setValue(clTypeVO.getTypecal());
                moneyList.add(moneyVo);
            }
            resMap.put("numList", numList);
            resMap.put("moneyList", moneyList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("团队散客_月份时间段筛选", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 分段日期客流趋势-按天
     * 时间段范围日期客流
     * @update lrd 2017-09-20
     */
    @ApiOperation(value = "分段日期客流趋势-按天", notes = "分段日期客流趋势-按天")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "start", value = "开始日期", required = true, paramType = "String"),
            @ApiImplicitParam(name = "end", value = "结束日期", required = true, paramType = "String")
    })
    @GetMapping("/getPassengerFlowByDay")
    public Object getPassengerFlowByDay(String start, String vcode, String end) {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(start, "开始日期不能为空!");
        Assert.paramIsBlank(end, "结束日期不能为空!");
        try {
            List<Big_RealP_Tend> big_realP_tendList = bigRealPeopleService.getPassengerFlowByDay(start,vcode,end);
            Map resMap = new HashMap();
            List<BaseCommonVo> dayPassengerList = new ArrayList<>();
            for (Big_RealP_Tend big_realP_tend : big_realP_tendList){
                BaseCommonVo baseCommonVo = new BaseCommonVo();
                baseCommonVo.setName(big_realP_tend.getTime());
                baseCommonVo.setValue(big_realP_tend.getNum());
                dayPassengerList.add(baseCommonVo);
            }
            resMap.put("dayPassengerList", dayPassengerList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("分段日期客流趋势-按天", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }


    /**
     * 分段日期客流趋势-按年
     * 时间段范围日期客流
     * @update lrd 2017-09-20
     */
    @ApiOperation(value = "分段日期客流趋势-按年", notes = "分段日期客流趋势-按年")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "start", value = "开始年份", required = true, paramType = "String"),
            @ApiImplicitParam(name = "end", value = "结束年份", required = true, paramType = "String")
    })
    @GetMapping("/getPassengerFlowByYear")
    public Object getPassengerFlowByYear(String start, String vcode, String end) {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(start, "开始年份不能为空!");
        Assert.paramIsBlank(end, "结束年份不能为空!");
        try {
            List<Map> big_realP_tendList = bigRealPeopleService.getPassengerFlowByYear(start,vcode,end);
            Map resMap = new HashMap();
            List yearPassengerList = new ArrayList<>();
            for (Map map : big_realP_tendList){
                BaseCommonVo baseCommonVo = new BaseCommonVo();
                baseCommonVo.setName(map.get("time"));
                baseCommonVo.setValue(map.get("num"));
                yearPassengerList.add(baseCommonVo);
            }
            resMap.put("yearPassengerList", yearPassengerList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("分段日期客流趋势-按年", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 按季度查询
     * 改造成查缓存：2017-08-16 lrd
     * @update zf 20170727 每年季度客流趋势
     */
    @ApiOperation(value = "每年季度客流趋势", notes = "每年季度客流趋势")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "String")
    })
    @GetMapping("/getPassengerFlowByQuarter")
    public Object getPassengerFlowByQuarter(String year, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(year, "年份不能为空!");
        try {
            //直接读的redis库
            Object big_realP_tendList = bigRealPeopleService.getPassengerFlowByQuarter(year, vcode);
            Map resMap = new HashMap();
            List<BaseCommonVo> quarterPassengerList = new ArrayList<>();
            if(big_realP_tendList != null){
                for (Big_RealP_Tend bigRealPTend : (List<Big_RealP_Tend>)big_realP_tendList){
                    BaseCommonVo baseCommonVo = new BaseCommonVo();
                    baseCommonVo.setName(bigRealPTend.getTime());
                    baseCommonVo.setValue(bigRealPTend.getNum());
                    quarterPassengerList.add(baseCommonVo);
                }
            }
            resMap.put("quarterPassengerList", quarterPassengerList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("每日客流趋势", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 按时间段 筛选历史客流量最高的十天
     */
    @GetMapping("/findHistoryPassengerByDay")
    public Object findHistoryPassengerByDay(String start,String vcode, String end) {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(start, "开始时间不能为空!");
        Assert.paramIsBlank(end, "结束时间不能为空!");
        List<Big_RealP_Tend> big_realP_tendList = bigRealPeopleService.findHistoryPassengerByDay(start, vcode, end);
        if (big_realP_tendList == null || big_realP_tendList.isEmpty()) {
            return ResponseBuilder.custom().success("success", 0).data(new ArrayList<>()).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(big_realP_tendList).build();
    }

    /**
     * 每月客流趋势
     * @update zf 20170727
     * @update zf 20170804
     * @update lrd 2017-08-16 全走缓存
     * @return
     * @throws ParseException
     */
    @ApiOperation(value = "每月客流趋势", notes = "每月客流趋势")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "查询客流趋势类型", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "日期", required = true, paramType = "String")
    })
    @GetMapping("/getPassengerFlowByMonth")
    public Object getPassengerFlowByMonth(String year,String vcode) throws ParseException {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(year, "年份不能为空!");
        try {
            Object thisTimeList = bigRealPeopleService.getPassengerFlowByMonth(year, vcode);
            Object lastTimeList = bigRealPeopleService.getPassengerFlowByMonth(Integer.parseInt(year) - 1 + "", vcode);
            Map<String, String> thisMap = new HashMap<>();
            Map<String, String> lastMap = new HashMap<>();
            for (Big_RealP_Tend tend :(List<Big_RealP_Tend>) thisTimeList) {
                thisMap.put(tend.getTime(), tend.getNum());
            }
            for (Big_RealP_Tend tend : (List<Big_RealP_Tend>) lastTimeList) {
                lastMap.put(tend.getTime(), tend.getNum());
            }

            String nowYear = DateUtil.getCurYearStr();
            Date nowDate = DateUtil.strToDate(nowYear, "yyyy");
            long nowTimes = nowDate.getTime();
            Date yesDate = DateUtil.strToDate(year, "yyyy");
            long yesTimes = yesDate.getTime();
            int monthSize = 12;
            if(nowTimes == yesTimes){
                String nowMonth = DateUtil.getCurSimpleMonthStr();
                monthSize = Integer.valueOf(nowMonth);
            }
            List<Growth> yearOnYearGrowth = new MathUtils().getYearOnYearGrowthRedis(year,lastMap, thisMap, monthSize);
            List<Growth> annulusGrowth = new MathUtils().getAnnulusGrowthRedis(year,thisMap, monthSize);
            // 返回数据处理
            Map resMap = new HashMap();
            List thisTimeListNew = new ArrayList();
            List annulusGrowthList = new ArrayList();
            List yearOnYearGrowthList = new ArrayList();
            for (Big_RealP_Tend bigRealPTend : (List<Big_RealP_Tend>)thisTimeList){
                BaseCommonVo thisTimeVo = new BaseCommonVo();
                thisTimeVo.setName(bigRealPTend.getTime());
                thisTimeVo.setValue(bigRealPTend.getNum());
                thisTimeListNew.add(thisTimeVo);
            }
            for (Growth growth : annulusGrowth){
                BaseCommonVo growthVo = new BaseCommonVo();
                growthVo.setName(growth.getTime());
                growthVo.setValue(growth.getPersent());
                annulusGrowthList.add(growthVo);
            }
            for (Growth growth : yearOnYearGrowth){
                BaseCommonVo growthVo = new BaseCommonVo();
                growthVo.setName(growth.getTime());
                growthVo.setValue(growth.getPersent());
                yearOnYearGrowthList.add(growthVo);
            }
            resMap.put("thisTimeList",thisTimeListNew);
            resMap.put("annulusGrowthList",annulusGrowthList);
            resMap.put("yearOnYearGrowthList",yearOnYearGrowthList);
            resMap.put("unit","人");
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("每日客流趋势", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }


    /**
     * 获取当前月份每天的实时客流
     * @param vcode
     * @return
     * @throws ParseException
     */
    @GetMapping("/getPassengerFlowByMonth_day")
    public Object getPassengerFlowByMonth_day(String month,String vcode) throws ParseException {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(month, "年月份不能为空!");
        try {
            Object myMap = bigRealPeopleService.getPassengerFlowByMonth_day(month, vcode);
            Map resMap = new HashMap();
            List dayNumList = new ArrayList();
            if(myMap != null){
                List<Big_RealP_Tend> list = (List)myMap;
                for (Big_RealP_Tend big_realP_tend : list){
                    Map map = new HashMap();
                    map.put("name", big_realP_tend.getTime());
                    map.put("value", big_realP_tend.getNum() != null?Integer.valueOf(big_realP_tend.getNum()):0);
                    dayNumList.add(map);
                }
            }
            resMap.put("dayNumList", dayNumList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("获取当前月份每天的实时客流", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 首页导航
     *
     * @param vcode
     * @return
     */
    @GetMapping("/find_home_page")
    public Object find_home_page(String vcode) {
        List<Home_page> home_pageList = bigRealPeopleService.find_home_page(vcode);

        return ResponseBuilder.custom().success("success", 0).data(home_pageList).build();
    }

    /**
     * 根据年份日期中的最大人数峰值与最小人数峰值
     */
    @ApiOperation(value = "根据年份日期中的最大人数峰值与最小人数峰值", notes = "根据年份日期中的最大人数峰值与最小人数峰值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "时间", required = true, paramType = "String"),
    })
    @GetMapping("/findPeakByDay")
    public Object findPeakByDay(String year, String vcode) {
        Assert.paramIsBlank(vcode, ExceptionConstant.VCODE_NOT_NULL);
        Map<String, String> map = new HashMap<>();
        map.put("vcode", vcode);
        map.put("startTime", year + "-01-01");
        map.put("endTime", year + "-12-31");
        map.put("date",year);
        List<Big_RealP_Tend> big_realP_tendList = bigRealPeopleService.findPeakByDay(map);
        List<Big_RealP_Tend> list = new ArrayList<Big_RealP_Tend>();
        if (big_realP_tendList.isEmpty()) {
        } else {
            list.add(big_realP_tendList.get(0));
            if (big_realP_tendList.size() > 1) {
                list.add(big_realP_tendList.get(big_realP_tendList.size() - 1));
            }
        }
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }


    /**
     * 根据年份筛选月份中的最大人数峰值与最小人数峰值
     */
    @ApiOperation(value = "根据年份筛选月份中的最大人数峰值与最小人数峰值", notes = "根据年份筛选月份中的最大人数峰值与最小人数峰值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "String"),
    })
    @GetMapping("/findPeakByMonth")
    public Object findPeakByMonth(String year, String vcode) {
        Assert.paramIsBlank(vcode, ExceptionConstant.VCODE_NOT_NULL);
        Map<String, String> map = new HashMap<>();
        map.put("vcode", vcode);
        map.put("startTime", year + "-01-01");
        map.put("endTime", year + "-12-31");
        map.put("date",year);
        List<Big_RealP_Tend> big_realP_tendList = bigRealPeopleService.findPeakByMonth(map);
        List<Big_RealP_Tend> list = new ArrayList<Big_RealP_Tend>();
        if (big_realP_tendList.isEmpty()) {
        } else {
            list.add(big_realP_tendList.get(0));
            if (big_realP_tendList.size() > 1) {
                list.add(big_realP_tendList.get(big_realP_tendList.size() - 1));
            }
        }
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

    /**
     * 年份销售量金额占比--年份中最小销售日和最大销售日
     *
     * @param year
     * @param vcode
     * @return
     */
    @GetMapping("/findMoneyByDay")
    public Object findMoneyByDay(String year, String vcode) {
        Map<String, String> map = new HashMap<>();
        map.put("vcode", vcode);
        map.put("startTime", year + "-01-01 00:00:00");
        map.put("endTime", year + "-12-31 23:59:59");
        map.put("date_format", "%Y-%m-%d");
        List<Big_Ticket_Money> dayList = bigRealPeopleService.findMoneyByDay(map);
        Map data = new HashMap<>();
        if (dayList.size() > 0) {
            data.put("min", dayList.get(0));
            if (dayList.size() > 1) {
                data.put("max", dayList.get(dayList.size() - 1));
            } else {
                data.put("max", dayList.get(0));
            }
        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }


    /**
     * 销售量金额占比--年份中最小销售日和最大销售日
     *
     * @param year
     * @param vcode
     * @return
     */
    @GetMapping("/findMoneyByMonth")
    public Object findMoneyByMonth(String year, String vcode) {
        Map<String, String> map = new HashMap<>();
        map.put("vcode", vcode);
        map.put("startTime", year + "-01-01 00:00:00");
        map.put("endTime", year + "-12-31 23:59:59");
        map.put("date_format", "%Y-%m");
        List<Big_Ticket_Money> list = bigRealPeopleService.findMoneyByMonth(map);
        Map data = new HashMap<>();
        if (list.size() > 0) {
            data.put("min", list.get(0));
            if (list.size() > 1) {
                data.put("max", list.get(list.size() - 1));
            } else {
                data.put("max", list.get(0));
            }
        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }

    /**
     * 每日客流趋势
     * 按小时段
     * @param day
     * @param vcode
     * @return
     * @update lrd 20170920 每日客流趋势(按小时段查询)
     */
    @ApiOperation(value = "每日客流趋势", notes = "每日客流趋势")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "day", value = "日期", required = true, paramType = "String")
    })
    @GetMapping("/getPassengerFlowByHours")
    public Object getPassengerFlowByHours(String day, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(day, "日期不能为空!");
        try {
            List<Tend> allList = bigRealPeopleService.getPassengerFlowByHours(day, vcode);
            Map resMap = new HashMap();
            List<BaseCommonVo> numList = new ArrayList<>();
            List<BaseCommonVo> sumList = new ArrayList<>();
            List<BaseCommonVo> percentList = new ArrayList<>();
            for (Tend tend : allList){
                BaseCommonVo numVo = new BaseCommonVo();
                numVo.setName(tend.getTime());
                numVo.setValue(tend.getNum());
                numList.add(numVo);
                BaseCommonVo sumVo = new BaseCommonVo();
                sumVo.setName(tend.getTime());
                sumVo.setValue(tend.getSum());
                sumList.add(sumVo);
                BaseCommonVo percentVo = new BaseCommonVo();
                percentVo.setName(tend.getTime());
                percentVo.setValue(tend.getPercent());
                percentList.add(percentVo);
            }
            resMap.put("numList", numList);
            resMap.put("sumList", sumList);
            resMap.put("percentList", percentList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("每日客流趋势", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 大数据客流来源分析
     *
     * @param vcode 景区编码
     * @param year  年份
     * @return
     */
    @GetMapping("/findBigPeople")
    public Object getBigPeopleYear(String vcode, String year) {
        List list = bigRealPeopleService.getBigPeopleYear(vcode, year);
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }


}
