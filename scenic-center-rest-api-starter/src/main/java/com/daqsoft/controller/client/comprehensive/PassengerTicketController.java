package com.daqsoft.controller.client.comprehensive;

import com.daqsoft.commons.responseentity.DatasResponse;
import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.comprehensive.RealPeopleService;
import com.daqsoft.service.client.passengerFlowService.PassengerTicketService;
import com.daqsoft.service.client.passengerFlowService.ThreeDimensionalMapService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.DateUtils;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.RealPeople;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: lyl .
 * @Date: Created in  2017/6/18.
 * @Version:
 * @describe: 综合展示页面模块化划分 客流与票务模块
 */
@RestController
@RequestMapping("/rest/comprehensivePassengerTicket")
public class PassengerTicketController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PassengerTicketService passengerTicketService;
    @Autowired
    private ThreeDimensionalMapService threeDimensionalMapService;
    @Autowired
    private RealPeopleService realPeopleService;

    /**
     * 客流与票务分析微件
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("客流与票务分析")
    @ApiOperation(value = "客流与票务分析", notes = "客流与票务分析")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getPassengerTicket")
    public Object getCountPeopleSum(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());//今日时间
        String vcodemdh = DigestUtils.md5Hex(vcode);
        SumPeopleIndexVo sumPeopleIndexRedis = new SumPeopleIndexVo();
        TicketIndexOnAndOffVo ticketIndexOnAndOffDB = new TicketIndexOnAndOffVo();
        List<OtaDataTendVo> otaDataTendRedis = new LinkedList<>();
        try {
            Map resMap = new HashMap();
            //前、去、今年人数及同比（redis)  以前这个方法统计的是实时人数，后面因为产品经理需求变化，这里改成统计票务的数据(线上、线下),如果是云台山，实时人数走票务数据
            if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
                sumPeopleIndexRedis = passengerTicketService.sumTicketRedis(vcode, vcodemdh);
            }//华清宫  人数取票务数据
            else if("f561aaf6ef0bf14d4208bb46a4ccb3ad".equals(vcode)){
                sumPeopleIndexRedis = passengerTicketService.sumPeopleIndexRedisTicket(vcode, vcodemdh);
            }else{
                sumPeopleIndexRedis = passengerTicketService.sumPeopleIndexRedis(vcode, vcodemdh, "day");
            }
            //线上线下票务销售量（redis）
            ticketIndexOnAndOffDB = passengerTicketService.ticketIndexOnAndOffRedis(vcode,vcodemdh);
            //OTA数据（redis）
            otaDataTendRedis = passengerTicketService.otaDataTendRedis(vcodemdh, today, vcode);
            // 返回数据封装
            // 今日 去年今日 前年今日数据
            List<Map> visitorsTotal = new ArrayList<>();
            Map todayPeopleMap = new HashMap();
            todayPeopleMap.put("icon", "&#xe6d2;");
            todayPeopleMap.put("name", "今日游客总数");
            todayPeopleMap.put("value", sumPeopleIndexRedis.getTodayPeople() != null ? Integer.valueOf(sumPeopleIndexRedis.getTodayPeople()) : 0);
            visitorsTotal.add(todayPeopleMap);
            Map lastYearPeopleMap = new HashMap();
            lastYearPeopleMap.put("icon", "&#xe6ce;");
            lastYearPeopleMap.put("name", "去年今日游客总数");
            lastYearPeopleMap.put("value", sumPeopleIndexRedis.getLastYearPeople() != null ? Integer.valueOf(sumPeopleIndexRedis.getLastYearPeople()) : 0);
            visitorsTotal.add(lastYearPeopleMap);
            Map beforeYearPeopleMap = new HashMap();
            beforeYearPeopleMap.put("icon", "&#xe6cf;");
            beforeYearPeopleMap.put("name", "前年今日游客总数");
            beforeYearPeopleMap.put("value", sumPeopleIndexRedis.getBeforeYearPeople() != null ? Integer.valueOf(sumPeopleIndexRedis.getBeforeYearPeople()) : 0);
            visitorsTotal.add(beforeYearPeopleMap);

            // 线上 线下 销售
            List<Map> totalSales = new ArrayList<>();
            Map offLineMap = new HashMap();
            offLineMap.put("icon", "&#xe626;");
            offLineMap.put("name", "线下销售");
            offLineMap.put("value", ticketIndexOnAndOffDB.getOffLine() != null ? Long.valueOf(ticketIndexOnAndOffDB.getOffLine()) : 0);
            totalSales.add(offLineMap);
            Map onLineMap = new HashMap();
            onLineMap.put("icon", "&#xe626;");
            onLineMap.put("name", "线上销售");
            onLineMap.put("value", ticketIndexOnAndOffDB.getOnLine() != null ? Long.valueOf(ticketIndexOnAndOffDB.getOnLine()) : 0);
            totalSales.add(onLineMap);

            // 实时游客数
            Map realTimeVisitor = new HashMap();
            realTimeVisitor.put("name", "实时游客数");
            realTimeVisitor.put("value", sumPeopleIndexRedis.getTodayPeople() != null?Integer.valueOf(sumPeopleIndexRedis.getTodayPeople()):0);

            // 游客总数同比
            List<Map> totalAccounted = new ArrayList<>();
            Map lastYearOnYearMap = new HashMap();
            lastYearOnYearMap.put("name", "游客总数同比去年");
            lastYearOnYearMap.put("type", Integer.valueOf(sumPeopleIndexRedis.getLastYearOnYearType()));
            lastYearOnYearMap.put("value", sumPeopleIndexRedis.getLastYearOnYear() + "%");
            totalAccounted.add(lastYearOnYearMap);
            Map beforeYearOnYearMap = new HashMap();
            beforeYearOnYearMap.put("name", "游客总数同比前年");
            beforeYearOnYearMap.put("type", Integer.valueOf(sumPeopleIndexRedis.getBeforeYearOnYearType()));
            beforeYearOnYearMap.put("value", sumPeopleIndexRedis.getBeforeYearOnYear() + "%");
            totalAccounted.add(beforeYearOnYearMap);

            // 各OTA销售及占比情况
            List<Map> salesAccounted = new ArrayList<>();
            //如果redis为空
            for (OtaDataTendVo otaDataTendVo : otaDataTendRedis){
                Map map = new HashMap();
                map.put("name", otaDataTendVo.getName());
                map.put("num", otaDataTendVo.getCount());
                map.put("percent",  otaDataTendVo.getPercent() + "%");
                salesAccounted.add(map);
            }

            resMap.put("visitorsTotal", visitorsTotal);
            resMap.put("totalSales", totalSales);
            resMap.put("realTimeVisitor", realTimeVisitor);
            resMap.put("totalAccounted", totalAccounted);
            resMap.put("salesAccounted", salesAccounted);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e) {
            logger.error("客流与票务分析", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 客流与票务分析微件（华清宫）
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("客流与票务分析（华清宫）")
    @ApiOperation(value = "客流与票务分析（华清宫）", notes = "客流与票务分析（华清宫）")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getPassengerTicketHQG")
    public Object getCountPeopleSumHQG(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());//今日时间
        String vcodemdh = DigestUtils.md5Hex(vcode);
        SumPeopleIndexVo sumPeopleIndexRedis = new SumPeopleIndexVo();
        TicketIndexOnAndOffVo ticketIndexOnAndOffDB = new TicketIndexOnAndOffVo();
        Map otaDataTendRedis = new LinkedHashMap();
        RealPeopleIndexSumVo realTimePeople = new RealPeopleIndexSumVo();
        Map mapData = new HashMap();

        sumPeopleIndexRedis = passengerTicketService.sumPeopleIndexRedisTicket(vcode, vcodemdh);

        try {
            //线上线下票务销售量（redis）
            ticketIndexOnAndOffDB = passengerTicketService.ticketIndexOnAndOffRedis(vcode,vcodemdh);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            //OTA数据（redis）
            otaDataTendRedis = passengerTicketService.otaDataTendRedisHQG(vcodemdh, today, vcode);
            if (otaDataTendRedis == null) {//如果redis为空
                List<OtaDataTendVo> OtaDataEmpty = new LinkedList<>();
                mapData.put("otaDataTendRedis", OtaDataEmpty); //如果redis为空，new一个
            } else {
                mapData.put("otaDataTendRedis", otaDataTendRedis);//否则直接返回redis数据
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        realTimePeople.setSource("实时人数");
        realTimePeople.setSumPeople(sumPeopleIndexRedis.getTodayPeople());
        mapData.put("realPeople", realTimePeople);
        mapData.put("sumPeopleIndexRedis", sumPeopleIndexRedis);
        mapData.put("ticketIndexOnAndOffDB", ticketIndexOnAndOffDB);
        return ResponseBuilder.custom().success("success", 0).data(mapData).build();

    }


    /**
     * 客流与票务分析微件（赛里木湖）
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("客流与票务分析（赛里木湖）")
    @ApiOperation(value = "客流与票务分析（赛里木湖）", notes = "客流与票务分析（赛里木湖）")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getPassengerTicketSLMH")
    public Object getCountPeopleSumSLMH(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());//今日时间
        String vcodemdh = DigestUtils.md5Hex(vcode);
        SumPeopleIndexVo sumPeopleIndexRedis = new SumPeopleIndexVo();
        TicketIndexOnAndOffVo ticketIndexOnAndOffDB = new TicketIndexOnAndOffVo();
//        Map ticketIndexOnAndOffDB = new HashMap();
        List<OtaDataTendVo> otaDataTendRedis = new LinkedList<>();
        RealPeopleIndexSumVo realTimePeople = new RealPeopleIndexSumVo();
        Map mapData = new HashMap();
        try {
            //前、去、今年人数及同比（redis)  以前这个方法统计的是实时人数，后面因为产品经理需求变化，这里改成统计票务的数据(线上、线下),如果是云台山，实时人数走票务数据
            if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
                sumPeopleIndexRedis = passengerTicketService.sumTicketRedis(vcode, vcodemdh);
            }else{
                sumPeopleIndexRedis = passengerTicketService.sumPeopleIndexRedis(vcode, vcodemdh, "day");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        try {
            //线上线下票务销售量（redis）
            ticketIndexOnAndOffDB = passengerTicketService.ticketIndexOnAndOffRedis(vcode,vcodemdh);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            //OTA数据（redis）
            otaDataTendRedis = passengerTicketService.otaDataTendRedis(vcodemdh, today, vcode);
            if (otaDataTendRedis == null) {//如果redis为空
                List<OtaDataTendVo> OtaDataEmpty = new LinkedList<>();
                mapData.put("otaDataTendRedis", OtaDataEmpty); //如果redis为空，new一个
            } else {
                mapData.put("otaDataTendRedis", otaDataTendRedis);//否则直接返回redis数据
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        realTimePeople.setSource("实时人数");
        realTimePeople.setSumPeople(sumPeopleIndexRedis.getTodayPeople());
        mapData.put("realPeople", realTimePeople);
        mapData.put("sumPeopleIndexRedis", sumPeopleIndexRedis);
        mapData.put("ticketIndexOnAndOffDB", ticketIndexOnAndOffDB);
        return ResponseBuilder.custom().success("success", 0).data(mapData).build();

    }


    /**
     * 票务分析OTA模块定制接口（神龙峡）
     *
     * @return
     */

    @SysLog("票务分析OTA")
    @ApiOperation(value = "票务分析OTA", notes = "票务分析OTA")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/slxOTA")
    public Object slxOTA(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String today = DateUtil.getCurDateStr();//今日时间
        String vcodemdh = DigestUtils.md5Hex(vcode);
        Object otaDataTendRedis = new LinkedList<>();
        Map<String, Object> resMap = new HashMap<>();
        try {
            //OTA数据（redis）
            otaDataTendRedis = passengerTicketService.slxOtaDataTendRedis(vcodemdh, today, vcode);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(otaDataTendRedis).build();
    }

    /**
     * 票务分析OTA模块定制接口（眉山农业嘉年华）
     *
     * @return
     */

    @SysLog("票务分析OTA")
    @ApiOperation(value = "票务分析OTA", notes = "票务分析OTA")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/msOTA")
    public Object msOTA(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String today = DateUtil.getCurDateStr();//今日时间
        String vcodemdh = DigestUtils.md5Hex(vcode);
        Object otaDataTendRedis = new LinkedList<>();
        Map<String, Object> resMap = new HashMap<>();
        try {
            //OTA数据（redis）
            otaDataTendRedis = passengerTicketService.msOtaDataTendRedis(vcodemdh, today, vcode);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(otaDataTendRedis).build();
    }


    /**
     * 景区 今日、昨日、最大承载人数分析
     *
     * @return
     */

    @SysLog("景区景点人数分析")
    @ApiOperation(value = "景区景点人数分析", notes = "景区景点人数分析")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/spots")
    public Object spots(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map<String, Object> resMap = new HashMap<>();
        try {
            String date = DateUtils.getDay();
            resMap = passengerTicketService.getSpotsPeoples(vcode, date);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(resMap).build();
    }

    /**
     * 景区运行团队数统计
     *
     * @return
     */
    @SysLog("景区运行团队数统计")
    @ApiOperation(value = "景区运行团队数统计", notes = "景区运行团队数统计")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/team")
    public Object team(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map<String, Object> resMap = new HashMap<>();
        try {
            String date = DateUtils.getDay();
            // 查询列表
            resMap = passengerTicketService.getTeamList(vcode,DigestUtils.md5Hex(vcode), date);
            Map numMap = new HashMap();
            List todayNumList = new ArrayList();
            List yesterdayNumList = new ArrayList();
            if (null != resMap.get("provinceList")){
                List<PassengerTeamVo> provinceList = (List)resMap.get("provinceList");
                for (PassengerTeamVo passengerTeamVo : provinceList){
                    Map todayNumMap = new HashMap();
                    todayNumMap.put("name", passengerTeamVo.getName());
                    todayNumMap.put("value", passengerTeamVo.getTodayNum() != null?Integer.valueOf(passengerTeamVo.getTodayNum()):0);
                    todayNumList.add(todayNumMap);
                    Map yesterdayNumMap = new HashMap();
                    yesterdayNumMap.put("name", passengerTeamVo.getName());
                    yesterdayNumMap.put("value", passengerTeamVo.getYesterdayNum() != null?Integer.valueOf(passengerTeamVo.getYesterdayNum()):0);
                    yesterdayNumList.add(yesterdayNumMap);
                }
            }
            numMap.put("todayNumList", todayNumList);
            numMap.put("yesterdayNumList", yesterdayNumList);
            numMap.put("todayCounts", resMap.get("todayCounts"));
            numMap.put("yestodayCounts", resMap.get("yestodayCounts"));
            return ResponseBuilder.custom().success("success", 0).data(numMap).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 当天的累计游客量和景区最大承载量
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("累计游客量和景区最大承载量")
    @ApiOperation(value = "累计游客量和景区最大承载量", notes = "累计游客量和景区最大承载量")
   @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/getSyncPeopleSum")
    public Object getSyncPeopleSum(String vcode ) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map<Object, Object> map = new HashMap<>();
        //获取今日实时人数
        List<RealPeople> jqSumPeopleList = realPeopleService.getSyncPeopleSum(vcode);
        map = realPeopleService.getMaxQuantity(vcode);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());//系统当前时间
        if (jqSumPeopleList != null && map != null) {
            for (RealPeople sumPopulation : jqSumPeopleList) {
                map.put("syncNum", sumPopulation.getSumPeople() == null ? "0" : sumPopulation.getSumPeople());
            }
        } else {
            if (map == null) {
                map = new HashMap<>();
                map.put("syncNum", "0");
            }
            map.put("syncNum", "0");
        }
        List<Map> mapList = null;
        DatasResponse<RealPeopleChoiceTimeVo> datasResponse = new DatasResponse();
        List<RealPeopleChoiceTimeVo> navigationManageHomeVos = new LinkedList<RealPeopleChoiceTimeVo>();
        String vcodemdh = DigestUtils.md5Hex(vcode);
        //获取景区累计人数和景区最大存在量
            navigationManageHomeVos = realPeopleService.getRealTimeMethTime(vcodemdh, today );
        map.put("realList", navigationManageHomeVos);
        return ResponseBuilder.custom().success("success", 0).data(map).build();
    }
    /**
     * 景区平均团队数
     *
     * @return
     */
    @SysLog("景区平均团队数")
    @ApiOperation(value = "景区平均团队数", notes = "景区平均团队数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @GetMapping(value = "/scenicAvgTeam")
    public Object scenicAvgTeam(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map<String, Object> resMap = new HashMap<>();
        try {
            // 查询列表
            String date = DateUtil.getCurDateStr();
            resMap = passengerTicketService.getScenicAvgTeam(vcode, date);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(resMap).build();
    }
    /**
     * 客流与票务分析微件--云台山定制微件
     * （客户方要求：实时人数取票务数据，而不是取闸机实时人数的数据，所以这里定制微件）
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("客流与票务分析")
    @ApiOperation(value = "客流与票务分析", notes = "客流与票务分析")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getPassengerTicketYTS")
    public Object getPassengerTicketYTS(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());//今日时间
        String vcodemdh = DigestUtils.md5Hex(vcode);
        SumPeopleIndexVo sumPeopleIndexRedis = new SumPeopleIndexVo();
        TicketIndexOnAndOffVo ticketIndexOnAndOffDB = new TicketIndexOnAndOffVo();
        List<OtaDataTendVo> otaDataTendRedis = new LinkedList<>();
        RealPeopleIndexSumVo realTimePeople = new RealPeopleIndexSumVo();
        Map mapData = new HashMap();
        try {
            //前、去、今年人数及同比（redis)  以前这个方法统计的是实时人数，后面因为产品经理需求变化，这里改成统计票务的数据(线上、线下)
            sumPeopleIndexRedis = passengerTicketService.sumTicketRedis(vcode, vcodemdh);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        try {
            //线上线下票务销售量（redis）
            ticketIndexOnAndOffDB = passengerTicketService.ticketIndexOnAndOffRedisYTS(vcode,vcodemdh);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            //OTA数据（redis）
            otaDataTendRedis = passengerTicketService.otaDataTendRedisYTS(vcodemdh, today, vcode);
            if (otaDataTendRedis == null) {//如果redis为空
                List<OtaDataTendVo> OtaDataEmpty = new LinkedList<>();
                mapData.put("otaDataTendRedis", OtaDataEmpty); //如果redis为空，new一个
            } else {
                mapData.put("otaDataTendRedis", otaDataTendRedis);//否则直接返回redis数据
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        realTimePeople.setSource("实时人数");
        realTimePeople.setSumPeople(sumPeopleIndexRedis.getTodayPeople());
        mapData.put("realPeople", realTimePeople);
        mapData.put("sumPeopleIndexRedis", sumPeopleIndexRedis);
        mapData.put("ticketIndexOnAndOffDB", ticketIndexOnAndOffDB);
        return ResponseBuilder.custom().success("success", 0).data(mapData).build();

    }
}
