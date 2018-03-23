package com.daqsoft.controller.client.ticketanalys;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.ticketanalys.TicketBigService;
import com.daqsoft.service.client.ticketanalys.TicketOnLineOffLineService;
import com.daqsoft.service.client.ticketanalys.TicketService;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.validator.Assert;
import com.daqsoft.validator.RRException;
import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.CutPageBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author zhouq
 * @version V1.0.0
 * @date 2017-07-27 11:12.
 * @description 票务分析-客票销售趋势(线下)分析 微件库   从华蓥山迁移
 */
@Api("票务分析-客票销售趋势")
@RestController
@RequestMapping("/rest/ticketAnalysis")
public class TicketAnalysisController {
    private Logger logger = LoggerFactory.getLogger(TicketAnalysisController.class);
    @Autowired
    private TicketBigService ticketBigService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketOnLineOffLineService ticketOnLineOffLineService;

    /**
     * 2017-07-27 zhouq
     * 获取每天各小时的票务数据
     *
     * @param date
     * @param vcode
     * @return
     */
    @SysLog("获取每天各小时的票务数据")
    @ApiOperation(value = "获取每天各小时的票务数据", notes = "获取每天各小时的票务数据 实时销售量、累计销售量、时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "date", value = "日期 格式:2017-07-01", required = true, paramType = "String")
    })
    @GetMapping("/getTimeTicketsByDay")
    public Object getTimeTicketsByDay(String date, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(date, "日期不能为空");
        Assert.paramIsDate(date, "日期格式不规范");
        try {
            List<TicketClSsTendVO> ticketClSs_tendVOList = ticketService.getRealTimeTicketListByDay(vcode, date);
            Map resMap = new HashMap();
            List<BaseCommonVo> hourTicketNumList = new ArrayList<>();
            List<BaseCommonVo> hourTicketTotalNumList = new ArrayList<>();
            for (TicketClSsTendVO ticketClSsTendVO : ticketClSs_tendVOList){
                BaseCommonVo numVo = new BaseCommonVo();
                numVo.setName(ticketClSsTendVO.getTicket_time());
                numVo.setValue(ticketClSsTendVO.getTicket_num());
                hourTicketNumList.add(numVo);
                BaseCommonVo totalNumVo = new BaseCommonVo();
                totalNumVo.setName(ticketClSsTendVO.getTicket_time());
                totalNumVo.setValue(ticketClSsTendVO.getTicket_nums());
                hourTicketTotalNumList.add(totalNumVo);
            }
            resMap.put("hourTicketNumList", hourTicketNumList);
            resMap.put("hourTicketTotalNumList", hourTicketTotalNumList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("获取每天各小时的票务数据", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 用于跨域访问
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/getTimeTicketsByDayJp")
    public void getTimeTicketsByDayJp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vcode = request.getParameter("vcode");
        String date = request.getParameter("date");
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(date, "日期不能为空");
        Assert.paramIsDate(date, "日期格式不规范");
        List<TicketClSsTendVO> ticketClSs_tendVOList = ticketService.getRealTimeTicketListByDay(vcode, date);
        //服务器端接到回调函数名字输出回调函数，客户端根据回调函数进行解析取得函数中json对象
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("responseTime", System.currentTimeMillis());
        result.put("code", "200");
        result.put("message", "success");
        result.put("datas", ticketClSs_tendVOList);


        response.setContentType("text/html; charset=utf-8");
        String callback = request.getParameter("callback");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        String json = gson.toJson(ticketClSs_tendVOList);
        JSONObject resultJSON = JSONObject.fromObject(result); //根据需要拼装json
        writer.print(callback + "(" + resultJSON.toString(1, 1) + ")");


    }


    /**
     * 2017-07-27 zhouq
     * 根据月获取每天票务数据
     *
     * @param month
     * @param vcode
     * @return
     */
    @SysLog("根据月获取每天票务数据")
    @ApiOperation(value = "根据月获取每天票务数据", notes = "根据月获取每天票务数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "month", value = "月 格式:2017-07", required = true, paramType = "String")
    })
    @GetMapping("/getDayTicketsByMonth")
    public Object getDayTicketsByMonth(String month, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(month, "月份不能为空");
        Assert.paramIsMonth(month, "月份格式错误:格式为 2017-01");
        try {
            List<TicketClTendVo> ticket_cl_tendVos = ticketService.getDayTicketListByMonth(vcode, month);
            Map resMap = new HashMap();
            List<BaseCommonVo> dayTicketNumList = new ArrayList<>();
            List<BaseCommonVo> dayTicketPricesList = new ArrayList<>();
            for (TicketClTendVo ticketClTendVo : ticket_cl_tendVos){
                BaseCommonVo numVo = new BaseCommonVo();
                numVo.setName(ticketClTendVo.getTicket_time());
                numVo.setValue(ticketClTendVo.getTicket_num());
                dayTicketNumList.add(numVo);
                BaseCommonVo dayPriceVo = new BaseCommonVo();
                dayPriceVo.setName(ticketClTendVo.getTicket_time());
                dayPriceVo.setValue(ticketClTendVo.getTicket_totalPrice());
                dayTicketPricesList.add(dayPriceVo);
            }
            resMap.put("dayTicketNumList", dayTicketNumList);
            resMap.put("dayTicketPricesList", dayTicketPricesList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("根据月获取每天票务数据", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }


    /**
     * 按年获取各季度票务总数
     *
     * @param year  年
     * @param vcode
     * @return
     */
    @SysLog("按年获取各季度票务总数")
    @ApiOperation(value = "按年获取各季度票务总数", notes = "按年获取各季度票务总数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年 格式:2017", required = true, paramType = "String")
    })
    @GetMapping("/getQuarterTicketsByYear")
    public Object getQuarterTicketsByYear(String vcode, String year) {
        Assert.paramIsBlank(vcode, "景区代码不能为空");
        Assert.paramIsBlank(year, "年份不能为空");
        Assert.paramIsYear(year, "年份格式错误,格式为: 2017");
        try {
            List<TicketClTendVo> ticket_cl_tendVoList = ticketService.getQuarterTicketListByYear(vcode, year);
            Map resMap = new HashMap();
            List<BaseCommonVo> quarterTicketNumList = new ArrayList<>();
            List<BaseCommonVo> quarterTicketPricesList = new ArrayList<>();
            for (TicketClTendVo ticketClTendVo : ticket_cl_tendVoList){
                BaseCommonVo numVo = new BaseCommonVo();
                numVo.setName(ticketClTendVo.getTicket_time());
                numVo.setValue(ticketClTendVo.getTicket_num());
                quarterTicketNumList.add(numVo);
                BaseCommonVo dayPriceVo = new BaseCommonVo();
                dayPriceVo.setName(ticketClTendVo.getTicket_time());
                dayPriceVo.setValue(ticketClTendVo.getTicket_totalPrice());
                quarterTicketPricesList.add(dayPriceVo);
            }
            resMap.put("quarterTicketNumList", quarterTicketNumList);
            resMap.put("quarterTicketPricesList", quarterTicketPricesList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("按年获取各季度票务总数", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }


    /**
     * 2017-07-27 zhouq
     * <p>
     * 根据年获取每月票务数据
     * @param year  年份
     * @param vcode 景区代码
     * @return
     */
    @SysLog("根据年获取每月票务数据")
    @ApiOperation(value = "根据年获取每月票务数据", notes = "根据年获取每月票务数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年 格式:2017", required = true, paramType = "String")
    })
    @GetMapping("/getMonthTicketsByYear")
    public Object getMonthTicketsByYear(String year, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(year, "年份不能为空");
        Assert.paramIsYear(year, "年份格式错误,格式为: 2017");
        try {
            List<TicketClTendVo> ticket_cl_tendVos = ticketService.getMonthTicketListByYear(vcode, year);
            Map resMap = new HashMap();
            List<BaseCommonVo> monthTicketNumList = new ArrayList<>();
            List<BaseCommonVo> monthTicketPricesList = new ArrayList<>();
            for (TicketClTendVo ticketClTendVo : ticket_cl_tendVos){
                BaseCommonVo numVo = new BaseCommonVo();
                numVo.setName(ticketClTendVo.getTicket_time());
                numVo.setValue(ticketClTendVo.getTicket_num());
                monthTicketNumList.add(numVo);
                BaseCommonVo dayPriceVo = new BaseCommonVo();
                dayPriceVo.setName(ticketClTendVo.getTicket_time());
                dayPriceVo.setValue(ticketClTendVo.getTicket_totalPrice());
                monthTicketPricesList.add(dayPriceVo);
            }
            resMap.put("monthTicketNumList", monthTicketNumList);
            resMap.put("monthTicketPricesList", monthTicketPricesList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("根据年获取每月票务数据", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 票务分析-线上线下对比，通过不用的时间维度查询景区在ota上的销量数据
     * @auth yangmei
     * @date 2017-08-09
     * @param year  年份
     * @param vcode 景区代码
     * @Comment 部分jq_ota_data 是没有数据，存在模拟数据
     * @return
     */
    @SysLog("票务分析-线上线下对比，通过不用的时间维度查询景区在ota上的销量数据")
    @ApiOperation(value = "票务分析-线上线下对比，通过不用的时间维度查询景区在ota上的销量数据", notes = "票务分析-线上线下对比，通过不用的时间维度查询景区在ota上的销量数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "allyear", value = "历年", required = false, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年份", required = false, paramType = "String"),
            @ApiImplicitParam(name = "month", value = "月份", required = false, paramType = "String"),
            @ApiImplicitParam(name = "day", value = "某天", required = false, paramType = "String"),
            @ApiImplicitParam(name = "quarter", value = "季度", required = false, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "日期类型", required = true, paramType = "String")
    })
    @GetMapping("/getOnlineOfflineTikCompare")
    public Object getOnlineOfflineTikCompare(String vcode,String allyear,String year,
                    String month,String day,String quarter,String type) {
        //暂时微件只有年度12个月的线上线下对比，多个参数为后面做预留
        Assert.paramIsBlank(vcode, "景区vcode不能为空!");
        Assert.paramIsBlank(type, "查询类型不能为空!");
        if (type.equals("day")) {
            Assert.paramIsDate(day, "日期时间参数不正确! 格式为： yyyy-mm-dd 例如：2017-01-01");
        } else if (type.equals("month")) {
            Assert.paramIsYear(year, "年参数不正确! 格式为： yyyy 例如：2017");
            Assert.paramIsSimpleMonth(month, "月份参数不正确!  格式为： mm 例如：01、02、10等");
        } else if (type.equals("quarter")) {
            Assert.paramIsQuarter(quarter, "季度参数不正确! 例如：1、2、3、4");
            Assert.paramIsYear(year, "年参数不正确!");
        } else if (type.equals("year")) {
            Assert.paramIsYear(year, "年参数不正确! 格式为： yyyy 例如：2017");
        } else if (type.equals("allyear")) {
            Assert.paramIsBlank(allyear, "历年参数不能为空!");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("day", day);
        paramMap.put("month", month);
        paramMap.put("quarter", quarter);
        paramMap.put("year", year);
        paramMap.put("allyear", allyear);
        paramMap.put("type", type);
        paramMap.put("vcode", vcode);
        //返回接口信息线上线下对比
        try {
            List<Map> list = ticketBigService.getOnlineOfflineTikCompare(paramMap);
            Map resMap = new HashMap();
            List<BaseCommonVo> ticketNumList = new ArrayList<>();
            List<BaseCommonVo> ticketPriceList = new ArrayList<>();
            List<BaseCommonVo> otaNumList = new ArrayList<>();
            List<BaseCommonVo> otaPriceList = new ArrayList<>();
            for (Map map : list){
                BaseCommonVo ticketNum = new BaseCommonVo();
                ticketNum.setName(map.get("time"));
                ticketNum.setValue(map.get("ticket_num") != null?map.get("ticket_num"):0);
                ticketNumList.add(ticketNum);
                BaseCommonVo ticketPrice = new BaseCommonVo();
                ticketPrice.setName(map.get("time"));
                ticketPrice.setValue(map.get("ticket_totalPrice") != null?map.get("ticket_totalPrice"):0);
                ticketPriceList.add(ticketPrice);
                BaseCommonVo otaNum = new BaseCommonVo();
                otaNum.setName(map.get("time"));
                otaNum.setValue(map.get("ota_num") != null?map.get("ota_num"):0);
                otaNumList.add(otaNum);
                BaseCommonVo otaPrice = new BaseCommonVo();
                otaPrice.setName(map.get("time"));
                otaPrice.setValue(map.get("ota_totalPrice") != null?map.get("ota_totalPrice"):0);
                otaPriceList.add(otaPrice);
            }
            resMap.put("ticketNumList", ticketNumList);
            resMap.put("ticketPriceList", ticketPriceList);
            resMap.put("otaNumList", otaNumList);
            resMap.put("otaPriceList", otaPriceList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("票务分析-线上线下对比", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 2017-07-27 zhouq
     * 分段日期票务数据
     *
     * @param vcode
     * @param start 开始日期
     * @param end   结束日期
     * @return
     */
    @SysLog("分段日期票务数据")
    @ApiOperation(value = "获取分段日期区间客流数据", notes = "获取分段日期间客流数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "start", value = "开始日期 :2017-07-01 ", required = true, paramType = "String"),
            @ApiImplicitParam(name = "end", value = "结束日期 日:2017-07-01", required = true, paramType = "String")
    })
    @GetMapping("/getDayTicketsByDateRange")
    public Object getDayTicketsByDateRange(String vcode, String start, String end) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(start, "开始日期不能为空");
        Assert.paramIsBlank(end, "结束日期不能为空");
        Assert.paramIsDate(start, "开始日期格式错误,格式为: 2017-01-01");
        Assert.paramIsDate(end, "结束日期格式错误,格式为: 2017-01-01");
        try {
            List<TicketClTendVo> ticketNumByTimeSlotGroupByDay = ticketService.getTicketNumSlotGroupByDay(vcode, start, end);
            Map resMap = new HashMap();
            List<BaseCommonVo> dayTicketNumList = new ArrayList<>();
            List<BaseCommonVo> dayTicketPricesList = new ArrayList<>();
            for (TicketClTendVo ticketClTendVo : ticketNumByTimeSlotGroupByDay){
                BaseCommonVo numVo = new BaseCommonVo();
                numVo.setName(ticketClTendVo.getTicket_time());
                numVo.setValue(ticketClTendVo.getTicket_num());
                dayTicketNumList.add(numVo);
                BaseCommonVo dayPriceVo = new BaseCommonVo();
                dayPriceVo.setName(ticketClTendVo.getTicket_time());
                dayPriceVo.setValue(ticketClTendVo.getTicket_totalPrice());
                dayTicketPricesList.add(dayPriceVo);
            }
            resMap.put("dayTicketNumList", dayTicketNumList);
            resMap.put("dayTicketPricesList", dayTicketPricesList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("分段日期票务数据", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 2017-07-27 zhouq
     * 获取分段年区间票务数据
     *
     * @param vcode
     * @param start 开始日期
     * @param end   结束日期
     * @return
     */
    @SysLog("获取分段年区间客流数据")
    @ApiOperation(value = "获取分段年区间客流数据", notes = "根据年区间获取客流数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "start", value = "开始年份", required = true, paramType = "String"),
            @ApiImplicitParam(name = "end", value = "结束年份", required = true, paramType = "String")
    })
    @GetMapping("/getYearTicketByYearRange")
    public Object getYearTicketByYearRange(String vcode, String start, String end) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(start, "开始年份不能为空");
        Assert.paramIsBlank(end, "结束年份不能为空");
        Assert.paramIsYear(start, "开始年份格式错误,格式为: 2017");
        Assert.paramIsYear(end, "结束年份格式错误,格式为: 2017");
        try {
            List<TicketClTendVo> ticketNumByTimeSlotGroupByYear = ticketService.getTicketNumByTimeSlotGroupByYear(vcode, Integer.valueOf(start), Integer.valueOf(end));
            Map resMap = new HashMap();
            List<BaseCommonVo> yearTicketNumList = new ArrayList<>();
            List<BaseCommonVo> yearTicketPricesList = new ArrayList<>();
            for (TicketClTendVo ticketClTendVo : ticketNumByTimeSlotGroupByYear){
                BaseCommonVo numVo = new BaseCommonVo();
                numVo.setName(ticketClTendVo.getTicket_time());
                numVo.setValue(ticketClTendVo.getTicket_num());
                yearTicketNumList.add(numVo);
                BaseCommonVo dayPriceVo = new BaseCommonVo();
                dayPriceVo.setName(ticketClTendVo.getTicket_time());
                dayPriceVo.setValue(ticketClTendVo.getTicket_totalPrice());
                yearTicketPricesList.add(dayPriceVo);
            }
            resMap.put("yearTicketNumList", yearTicketNumList);
            resMap.put("yearTicketPricesList", yearTicketPricesList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("获取分段年区间客流数据", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }


    /**
     * 按年月日不同时段查询票务张数
     *
     * @param date
     * @param type
     * @param vcode
     * @return
     */
    @GetMapping("/fundAll_date")
    public Object findAll_date(String date, String type, String vcode) {
        List<TicketClTendVo> ticket_cl_tendVos = ticketBigService.fundAll_date(date, vcode, type);
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_tendVos).build();
    }


    /**
     * 2017-07-27 zhouq
     * 按年月日时间段查询
     *
     * @param date
     * @param type
     * @param vcode
     * @param end
     * @return
     */
    @SysLog("按年月日时间段查询票务数据")
    @ApiOperation(value = "按年月日时间段票务数据", notes = "按年月日时间段票务数据 分段日期票务趋势")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "查询类型： day/month/year ", required = true, paramType = "String"),
            @ApiImplicitParam(name = "date", value = "开始日期 日:2017-07-01 月:2017-07 年:2017", required = true, paramType = "String"),
            @ApiImplicitParam(name = "end", value = "结束日期 日:2017-07-01 月:2017-07 年:2017", required = true, paramType = "String")
    })
    @GetMapping("/fundAll_date_end")
    public Object fundAll_date_end(String date, String type, String vcode, String end) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(type, "查询类型不能为空");
        List<TicketClTendVo> ticket_cl_tendVoList = ticketBigService.fundAll_date_end(date, vcode, type, end);

        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_tendVoList).build();
    }


    //=================================================票务分析--客票种类==========================================================

    /**
     * 票务分析--客票种类 按天
     * <p>
     * 按天获取景区客票种类销售分布
     */
    @SysLog("客票种类 按天获取景区客票种类销售分布")
    @ApiOperation(value = "客票种类 --按天获取景区客票种类销售分布", notes = "票务分析--客票种类 按天获取景区客票种类销售分布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "day", value = "日期 2017-07-01", required = true, paramType = "String")
    })
    @GetMapping("/getTicketTypesByDay")
    public Object getTicketTypesByDay(String day, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(day, "日期不能为空");
        Assert.paramIsDate(day, "日期格式错误,格式为: 2017-01-01");
        List<TicketClTypeVO> ticket_cl_typeList = ticketService.getTicketTypeList(vcode, day, "day");
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_typeList).build();
    }

    /**
     * 票务分析--客票种类 按月
     * <p>
     * 按月获取景区客票种类销售分布
     */
    @SysLog("客票种类 按月获取景区客票种类销售分布")
    @ApiOperation(value = "客票种类 --按月获取景区客票种类销售分布", notes = "票务分析--客票种类 按月获取景区客票种类销售分布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "month", value = "月份 : 2017-07", required = true, paramType = "String")
    })
    @GetMapping("/getTicketTypesByMonth")
    public Object getTicketTypesByMonth(String month, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(month, "月份不能为空");
        Assert.paramIsMonth(month, "月份格式错误,格式为: 2017-01");
        List<TicketClTypeVO> ticket_cl_typeList = ticketService.getTicketTypeList(vcode, month, "month");
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_typeList).build();
    }


    /**
     * 票务分析--客票种类 按年
     * <p>
     * 获取景区客票种类销售分布
     */
    @SysLog("客票种类 按年获取景区客票种类销售分布")
    @ApiOperation(value = "客票种类 --按年获取景区客票种类销售分布", notes = "客票种类 --按年获取景区客票种类销售分布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = " 年:2017", required = true, paramType = "String")
    })
    @GetMapping("/getTicketTypesByYear")
    public Object getTicketTypesByYear(String year, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(year, "年份不能为空");
        Assert.paramIsYear(year, "非法的年份,格式为: 2017");
        List<TicketClTypeVO> ticket_cl_typeList = ticketService.getTicketTypeList(vcode, year, "year");
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_typeList).build();
    }


    /**
     * 2017-08-09 zhouq 修改
     * 按年获取票务类型数量及占比 大数据页
     *
     * 2017-0
     *
     * @param vcode
     * @param
     * @return
     */
    @SysLog("客票种类 按年获取票务类型数量及占比")
    @ApiOperation(value = "客票种类 按年获取票务类型数量及占比", notes = "客票种类 按年获取票务类型数量及占比")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @GetMapping("/getTicketTypesAndProportionByYear")
    public Object getTicketTypesAndProportionByYear(String vcode, String year) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"请输入景区编码！");
        Assert.paramIsBlank(year,"请输入年份！");
        List<JqticketSeal> all = ticketService.getTicketTypesAndProportionByYear(vcode, year);
        return ResponseBuilder.custom().success("success", 0).data(all).build();
    }

    /**
     * 票务分析 --客票种类
     * 分段日期客票种类销量分布 （按年月日）华蓥山版
     *
     * @param
     * @param end
     * @param type
     * @param vcode
     * @return
     */
    @SysLog("客票种类 --分段日期客票种类销量分布 （按年月日）")
    @ApiOperation(value = "客票种类 --分段日期客票种类销量分布 （按年月日）", notes = "分段日期客票种类销量分布 （按年月日）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "查询类型： day/month/year ", required = true, paramType = "String"),
            @ApiImplicitParam(name = "start", value = " 开始:2017-07-01 月:2017-07 年:2017", required = true, paramType = "String"),
            @ApiImplicitParam(name = "end", value = " 结束:2017-07-01 月:2017-07 年:2017", required = true, paramType = "String")
    })
    @GetMapping("/find_Ticket_cl_Type_se")
    public Object find_Ticket_cl_Type_se(String start, String end, String type, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(type, "查询类型不能为空");
        List<TicketClTypeVO> ticket_cl_typeList = ticketService.getTicketTypeListBySlot(vcode,start, end, type);
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_typeList).build();
    }

    /**
     * 票务分析 --客票种类
     * 每季度客票种类销售分布 --old
     *
     * @param date
     * @param type
     * @param vcode
     * @return
     */
    @SysLog("客票种类 --每季度客票种类销售分布 --old")
    @ApiOperation(value = "客票种类 --按季度获取客票种类销售分布 --old", notes = "分段日期客票种类销量分布 --old")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "一季、二季、三季、四季 vitality、summer、autumn、wintertide ", required = true, paramType = "String"),
            @ApiImplicitParam(name = "date", value = "年份", required = true, paramType = "String")
    })
    @GetMapping("/find_Ticket_cl_Type_quarter")
    public Object find_Ticket_cl_Type_quarter(String date, String type, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(type, "季度类型不能为空");
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
        List<TicketClTypeVO> ticketTypeListByQuarter = ticketService.getTicketTypeListByQuarter(vcode, date, quarter);
        return ResponseBuilder.custom().success("success", 0).data(ticketTypeListByQuarter).build();
    }

    /**
     * 2017-08-16 zhouq 新增
     * 票务分析 --客票种类
     * 每季度客票种类销售分布 --new
     *
     * @param
     * @param
     * @param vcode
     * @return
     */
    @SysLog("客票种类 --每季度客票种类销售分布 --new")
    @ApiOperation(value = "客票种类 --按季度获取客票种类销售分布 --new", notes = "按季度获取客票种类销售分布 --new")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "quarter", value = " 季度 1/2/3/4", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "String")
    })
    @GetMapping("/getTicketTypeListByQuarter")
    public Object getTicketTypeListByQuarter(String vcode,String year,String quarter){
        Assert.paramIsBlank(vcode,"参数 vcode 不能为空!");
        Assert.paramIsBlank(year,"参数:year 不能为空!");
        Assert.paramIsYear(year,"参数:year 格式不正确!正确格式为:2017");
        Assert.paramIsBlank(quarter,"参数:quarter 不能为空!");
        String[] q = new String[]{"1","2","3","4"};
        List<String> quarterList = Arrays.asList(q);
        if (!quarterList.contains(quarter))
            throw new RRException("quarter 参数不正确.参考: 1/2/3/4 ",400);
        List<TicketClTypeVO> ticketTypeListByQuarter = ticketService.getTicketTypeListByQuarter(vcode, year, quarter);
        return ResponseBuilder.custom().success("success", 0).data(ticketTypeListByQuarter).build();
    }


    //===========================================票务分析--(游客)客票种类=================================================


    /**
     * 票务分析 --(游客)客票种类
     * 每日游客类型销售分布
     * @param date
     * @param vcode
     * @return
     */
    @SysLog("票务分析 --(游客)客票种类 每日游客类型分布")
    @ApiOperation(value = "票务分析 --(游客)客票种类 每日游客类型分布", notes = "票务分析 --(游客)客票种类 每日游客类型分布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "date", value = "日期", required = true, paramType = "String")
    })
    @GetMapping("/find_tourist_cl_Type_day")
    public Object getTouristTypeListByDay(String date,String vcode){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(date, "日期不能为空");
        Assert.paramIsDate(date, "日期格式错误,正确格式为:2017-01-01");
        List<TicketClTypeVO> ticketClTypeVOS = ticketService.getTouristBaseTypeList(vcode, "day", date);
        return ResponseBuilder.custom().success("success", 0).data(ticketClTypeVOS).build();
    }


    /**
     * 票务分析 --(游客类型)客票种类
     * 每月游客类型分布
     *
     * @param month 月份
     * @param vcode 景区编码
     * @return
     */
    @SysLog("票务分析 --(游客)客票种类 每月游客类型分布")
    @ApiOperation(value = "票务分析 --(游客)客票种类 每月游客类型分布", notes = "票务分析 --(游客)客票种类 每月游客类型分布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "month", value = "月份", required = true, paramType = "String")
    })
    @GetMapping("/find_tourist_cl_Type_month")
    public Object getTouristTypeListBymonth(String month, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(month, "月份不能为空");
        Assert.paramIsMonth(month, "月份格式错误,正确格式为:2017-01");
        //List<TicketClTypeVO> ticket_cl_typeList = ticketBigService.find_tourist_cl_Type_s(month, "month", vcode);
        List<TicketClTypeVO> ticketClTypeVOS = ticketService.getTouristBaseTypeList(vcode, "month", month);
        return ResponseBuilder.custom().success("success", 0).data(ticketClTypeVOS).build();
    }

    /**
     * 票务分析 --(游客)客票种类
     * 每季度游客类型分布 --old
     *
     * @param year 年份
     * @param quarter 季度 一季、二季、三季、四季 vitality、summer、autumn、wintertide
     * @param vcode 景区代码
     * @return
     */
    @SysLog("票务分析 --(游客)客票种类 每季度游客类型分布 --old")
    @ApiOperation(value = "票务分析 --(游客)客票种类 每季度游客类型分布 --old", notes = "每季度游客类型分布 --old")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "quarter", value = "一季、二季、三季、四季 vitality、summer、autumn、wintertide ", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "String")
    })
    @GetMapping("/find_tourist_cl_Type_quarter")
    public Object getTouristTypeListByQuarter(String year, String quarter, String vcode) {
        List<TicketClTypeVO> touristTypeListByQuarter = ticketService.getTouristBaseTypeListByQuarter(vcode, year, quarter);
        return ResponseBuilder.custom().success("success", 0).data(touristTypeListByQuarter).build();
    }

    /**
     * 票务分析 --(游客类型)客票种类
     * 每年游客类型分布 --new
     *
     * @param year 年份
     * @param vcode 景区编码
     * @return
     */
    @SysLog("票务分析 --(游客)客票种类 每年游客类型分布")
    @ApiOperation(value = "票务分析 --(游客)客票种类 每年游客类型分布", notes = "票务分析 --(游客)客票种类 每年游客类型分布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年", required = true, paramType = "String")
    })
    @GetMapping("/find_tourist_cl_Type_year")
    public Object getTouristTypeListByYear(String year, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(year, "年份不能为空");
        Assert.paramIsYear(year, "年份格式错误,正确格式为:2017");

        List<TicketClTypeVO> touristTypeList = ticketService.getTouristBaseTypeList(vcode, "year", year);
        return ResponseBuilder.custom().success("success", 0).data(touristTypeList).build();
    }



    /**
     * 票务分析 --(游客类型)客票种类
     * 大数据页游客构成
     *
     * @param vcode
     * @param year 年份
     * @return
     */
    @SysLog("票务分析 --(游客)客票种类 大数据页游客构成")
    @ApiOperation(value = "票务分析 --(游客)客票种类 大数据页游客构成", notes = "票务分析 --(游客)客票种类 大数据页游客构成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @GetMapping("/getTouristsTypeByYear")
    public Object getBigDateTouristTypeByYear(String vcode, String year) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"请输入景区编码！");
        Assert.paramIsBlank(year,"请输入年份");
        List<JqticketGcSeal> all = ticketService.getBigDateTouristTypeByYear(vcode, year);
        return ResponseBuilder.custom().success("success", 0).data(all).build();
    }

    /**
     * 票务分析 --(游客类型)客票种类  华蓥山版
     * 分段日期游客类型分布 --old
     *
     *
     * @param start  开始时间
     * @param end   结束时间
     * @param type  类型
     * @param vcode
     * @return
     */
    @SysLog("票务分析 --游客类型)客游客类型 获取分段日期游客类型分布 (按年月日）--old")
    @ApiOperation(value = "票务分析 --(游客类型)客游客类型 获取分段日期游客类型分布 (按年月日）--old", notes = "获取分段日期游客类型分布 (按年月日）--old")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "查询类型： day/month/year ", required = true, paramType = "String"),
            @ApiImplicitParam(name = "start", value = " 开始:2017-07-01 月:2017-07 年:2017", required = true, paramType = "String"),
            @ApiImplicitParam(name = "end", value = " 结束:2017-07-01 月:2017-07 年:2017", required = true, paramType = "String")
    })
    @GetMapping("/find_tourist_cl_Type_se")
    public Object getTouristTypeListBySlot(String start, String end, String type, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(type, "查询类型不能为空");
        if(RedisKey.DAY.equals(type)){
            Assert.paramIsBlank(start,"开始日期不能为空!");
            Assert.paramIsBlank(end,"开始日期不能为空!");
            Assert.paramIsDate(start,"开始日期格式不正确!");
            Assert.paramIsDate(end,"开始日期格式不正确!");
        }else if (RedisKey.MONTH.equals(type)){
            Assert.paramIsBlank(start,"开始月份不能为空!");
            Assert.paramIsBlank(end,"开始月份不能为空!");
            Assert.paramIsMonth(start,"开始月份格式不正确!");
            Assert.paramIsMonth(end,"开始月份格式不正确!");
        }else if (RedisKey.YEAR.equals(type)){
            Assert.paramIsBlank(start,"开始年份不能为空!");
            Assert.paramIsBlank(end,"开始年份不能为空!");
            Assert.paramIsYear(start,"开始年份格式不正确!");
            Assert.paramIsYear(end,"开始年份格式不正确!");
        }else {
            throw new RRException("查询类型参数错误!",400);
        }
//        List<TicketClTypeVO> ticket_cl_typeList = ticketBigService.find_tourist_cl_Type_se(start, end, vcode, type);
        List<TicketClTypeVO> touristTypeListBySlot = ticketService.getTouristBaseTypeListBySlot(vcode, type, start, end);
        return ResponseBuilder.custom().success("success", 0).data(touristTypeListBySlot).build();
    }




    /**
     *
     * 票务分析 --(游客类型)客票种类
     * 每月游客类型销售分布 --old
     *  2017-08-08 zhouq
     *  兼容老系统调用方法.  新接口为 getTouristTypeListBymonth
     *
     * @param month 月份
     * @param vcode 景区编码
     * @return
     */
    @SysLog("票务分析 --(游客)客票种类 每月游客类型分布 --old")
    @ApiOperation(value = "票务分析 --(游客)客票种类 每月游客类型分布 --old", notes = "票务分析 --(游客)客票种类 每月游客类型分布 --old")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "month", value = "月份", required = true, paramType = "String")
    })
    @GetMapping("/find_tourist_cl_Type_s")
    public Object find_tourist_cl_Type_s(String month, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(month, "月份不能为空");
        Assert.paramIsMonth(month, "月份格式错误,正确格式为:2017-01");
        List<TicketClTypeVO> ticketClTypeVOS = ticketService.getTouristBaseTypeList(vcode, "month", month);
        return ResponseBuilder.custom().success("success", 0).data(ticketClTypeVOS).build();
    }

    /**
     * 团队总数与游客总数
     * 按日
     * @param day
     * @param vcode
     * @param type
     * @return
     */
    @GetMapping("/findToursTotalByDay")
    public Object findToursTotalByDay(String day, String type, String vcode) {
        List<Tours_cl_total> ticket_cl_tendList = ticketBigService.findToursTotalByDay(day, vcode);
        List<Tours_cl_total> ticket_cl_tendList2 = new LinkedList<Tours_cl_total>();
        if (ticket_cl_tendList.isEmpty()) {
            return 0;
        }
        for (Tours_cl_total tours_cl_total : ticket_cl_tendList) {
            Tours_cl_total toursClTotal = new Tours_cl_total();
            if (tours_cl_total.getTours_num() == null || tours_cl_total.getTours_num() == "") {
                toursClTotal.setTours_num("0");
            } else {
                toursClTotal.setTours_num(tours_cl_total.getTours_num());
            }
            if (tours_cl_total.getTouts_totalNum() == null || tours_cl_total.getTouts_totalNum() == "") {
                toursClTotal.setTouts_totalNum("0");
            } else {
                toursClTotal.setTouts_totalNum(tours_cl_total.getTouts_totalNum());
            }
            ticket_cl_tendList2.add(toursClTotal);
        }
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_tendList).build();
    }

    /**
     * 团队总数与游客总数
     * 按月
     * @param month
     * @param vcode
     * @param type
     * @return
     */
    @GetMapping("/findToursTotalByMonth")
    public Object findToursTotalByMonth(String month, String type, String vcode) {
        try {
            List<Tours_cl_total> ticket_cl_tendList = ticketBigService.findToursTotalByMonth(month, vcode);
            return ResponseBuilder.custom().success("success", 0).data(ticket_cl_tendList).build();
        } catch (Exception e){
            logger.error("团队总数与游客总数", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }


    /**
     * 团队总数与游客总数
     * 按年
     * @param year
     * @param vcode
     * @param type
     * @return
     */
    @GetMapping("/findToursTotalByYear")
    public Object findToursTotalByYear(String year, String type, String vcode) {
        List<Tours_cl_total> ticket_cl_tendList = ticketBigService.findToursTotalByYear(year, vcode);
        List<Tours_cl_total> ticket_cl_tendList2 = new LinkedList<Tours_cl_total>();
        if (ticket_cl_tendList.isEmpty()) {
            return 0;
        }
        for (Tours_cl_total tours_cl_total : ticket_cl_tendList) {
            Tours_cl_total toursClTotal = new Tours_cl_total();
            if (tours_cl_total.getTours_num() == null || tours_cl_total.getTours_num() == "") {
                toursClTotal.setTours_num("0");
            } else {
                toursClTotal.setTours_num(tours_cl_total.getTours_num());
            }
            if (tours_cl_total.getTouts_totalNum() == null || tours_cl_total.getTouts_totalNum() == "") {
                toursClTotal.setTouts_totalNum("0");
            } else {
                toursClTotal.setTouts_totalNum(tours_cl_total.getTouts_totalNum());
            }
            ticket_cl_tendList2.add(toursClTotal);
        }
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_tendList).build();
    }

    /**
     * 客流分析——团队分析分页
     * 按日
     * @param page
     * @param day
     * @param vcode
     * @return
     */
    @GetMapping("/findToursLimitByDay")
    public Object findToursLimitByDay(Integer page, String day, String vcode) {
        List<CutPageBean> ticket_cl_tendList = ticketBigService.findToursLimitByDay(page, day, vcode);
        int count = 0;
        int totalPage = 0;
        if (ticket_cl_tendList != null) {
            count = ticket_cl_tendList.get(0).getCount();
            totalPage = ticket_cl_tendList.get(0).getTotalPage();
        }
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_tendList.get(0).getList()).totalCount(count).
            pageSize(6).totalPage(totalPage).currPage(page).build();
    }

    /**
     * 客流分析——团队分析分页
     * 按月
     * @param page
     * @param month
     * @param vcode
     * @return
     */
    @GetMapping("/findToursLimitByMonth")
    public Object findToursLimitByMonth(Integer page, String month, String vcode) {
        List<CutPageBean> ticket_cl_tendList = ticketBigService.findToursLimitByMonth(page, month, vcode);
        int count = 0;
        int totalPage = 0;
        if (ticket_cl_tendList != null) {
            count = ticket_cl_tendList.get(0).getCount();
            totalPage = ticket_cl_tendList.get(0).getTotalPage();
        }
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_tendList.get(0).getList()).totalCount(count).
           pageSize(6).totalPage(totalPage).currPage(page).build();

    }


    /**
     * 客流分析——团队分析分页
     * 按年
     * @param page
     * @param year
     * @param vcode
     * @return
     */
    @GetMapping("/findToursLimitByYear")
    public Object findToursLimitByYear(Integer page, String year,  String vcode) {
        List<CutPageBean> ticket_cl_tendList = ticketBigService.findToursLimitByYear(page, year, vcode);
        int count = 0;
        int totalPage = 0;
        if (ticket_cl_tendList != null) {
            count = ticket_cl_tendList.get(0).getCount();
            totalPage = ticket_cl_tendList.get(0).getTotalPage();
        }
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_tendList.get(0).getList()).totalCount(count).
            pageSize(6).totalPage(totalPage).currPage(page).build();

    }


    /**
     * 团队趋势
     * 日
     * @param day
     * @param vcode
     * @return
     */
    @GetMapping("/findToursTendencyByDay")
    public Object findToursTendencyByDay(String day,String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(day, "日期不能为空");
        List<Tours_cl_tendency> ticket_cl_tendList = ticketBigService.findToursTendencyByDay(day, vcode);
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_tendList).build();
    }

    /**
     * 团队趋势
     * 月份
     * @param month
     * @param vcode
     * @return
     */
    @GetMapping("/findToursTendencyByMonth")
    public Object findToursTendencyByMonth(String month,String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(month, "月份不能为空");
        List<Tours_cl_tendency> ticket_cl_tendList = ticketBigService.findToursTendencyByMonth(month, vcode);
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_tendList).build();
    }


    /**
     * 团队趋势
     * 年份
     * @param year
     * @param vcode
     * @return
     */
    @GetMapping("/findToursTendencyByYear")
    public Object findToursTendencyByYear(String year, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(year, "年份不能为空");
        List<Tours_cl_tendency> ticket_cl_tendList = ticketBigService.findToursTendencyByYear(year, vcode);
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_tendList).build();
    }
    /**
     * 统计某日团队数量
     *
     * @param day
     * @param vcode
     * @return
     */
    @GetMapping("/findToursByDay")
    public Object findToursByDay(String day, String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(day, "日期不能为空");
        try {
            Map toursInfoMap = ticketBigService.findToursByDay(day, vcode);
            return ResponseBuilder.custom().success("success", 0).data(toursInfoMap).build();
        } catch (Exception e){
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }
}
