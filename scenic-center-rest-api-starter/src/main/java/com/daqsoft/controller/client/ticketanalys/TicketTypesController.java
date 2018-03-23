package com.daqsoft.controller.client.ticketanalys;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.ticketanalys.TicketService;
import com.daqsoft.validator.Assert;
import com.daqsoft.validator.RRException;
import com.daqsoft.vo.client.madeVoBean.BaseCommonVo;
import com.daqsoft.vo.client.madeVoBean.JqticketSeal;
import com.daqsoft.vo.client.madeVoBean.TicketClTypeVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-09-08 14:53
 * @Version: v1.0.0
 * @Describe: 票务统计分析 ---票务类型统计微件
 */
@Api("票务统计分析 ---票务类型统计微件")
@RestController
@RequestMapping("/rest/ticketTypes")
public class TicketTypesController {
    private Logger logger = LoggerFactory.getLogger(TicketTypesController.class);
    @Autowired
    private TicketService ticketService;

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

        List<TicketClTypeVO> ticketTypeListByDay = ticketService.getTicketTypeListByDay(vcode, day);

        return ResponseBuilder.custom().success("success", 0).data(ticketTypeListByDay).build();
    }


    /**
     * 按分段日期获取 客票种类销量分布
     * @param start 开始
     * @param end 结束
     * @param vcode
     * @return
     */
    @SysLog("客票种类 --分段日期客票种类销量分布 ")
    @ApiOperation(value = "客票种类 --分段日期客票种类销量分布 ", notes = "分段日期客票种类销量分布 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "start", value = " 开始:2017-07-01 月:2017-07 年:2017", required = true, paramType = "String"),
            @ApiImplicitParam(name = "end", value = " 结束:2017-07-01 月:2017-07 年:2017", required = true, paramType = "String")
    })
    @GetMapping("/getTicketTypesByDayRange")
    public Object getTicketTypesByDayRange(String vcode,String start, String end) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(start, "开始日期不能为空");
        Assert.paramIsBlank(end, "结束日期不能为空");
        Assert.paramIsDate(start, "开始日期格式错误,格式为: 2017-01-01");
        Assert.paramIsDate(end, "结束日期格式错误,格式为: 2017-01-01");
        try {
            List<TicketClTypeVO> ticketTypeByDaySlot = ticketService.getTicketTypeByDaySlot(vcode, start, end);
            List<BaseCommonVo> ticketList = new ArrayList<>();
            for (TicketClTypeVO ticketClTypeVO : ticketTypeByDaySlot){
                BaseCommonVo ticketVo = new BaseCommonVo();
                ticketVo.setName(ticketClTypeVO.getTypes());
                ticketVo.setValue(ticketClTypeVO.getNums());
                ticketList.add(ticketVo);
            }
            Map resMap = new HashMap<>();
            resMap.put("ticketList", ticketList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("客票种类 --分段日期客票种类销量分布", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
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
        List<TicketClTypeVO> ticket_cl_typeList = ticketService.getTicketTypeListByMonth(vcode, month);
        return ResponseBuilder.custom().success("success", 0).data(ticket_cl_typeList).build();
    }



    /**
     * 2017-08-16 zhouq 新增
     * 票务分析 --客票种类
     * 每季度客票种类销售分布
     *
     * @param
     * @param
     * @param vcode
     * @return
     */
    @SysLog("客票种类 --每季度客票种类销售分布 ")
    @ApiOperation(value = "客票种类 --按季度获取客票种类销售分布 ", notes = "按季度获取客票种类销售分布 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "quarter", value = " 季度 1/2/3/4", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "String")
    })
    @GetMapping("/getTicketTypesByQuarter")
    public Object getTicketTypesByQuarter(String vcode,String year,String quarter){
        Assert.paramIsBlank(vcode,"参数 vcode 不能为空!");
        Assert.paramIsBlank(year,"参数:year 不能为空!");
        Assert.paramIsYear(year,"参数:year 格式不正确!正确格式为:2017");
        Assert.paramIsBlank(quarter,"参数:quarter 不能为空!");

        List<String> quarterList = Arrays.asList("1","2","3","4");
        if (!quarterList.contains(quarter))
            throw new RRException("quarter 参数不正确.参考: 1/2/3/4 ",400);

        List<TicketClTypeVO> ticketTypeListByQuarter = ticketService.getTicketTypeListByQuarter(vcode, year, quarter);

        return ResponseBuilder.custom().success("success", 0).data(ticketTypeListByQuarter).build();
    }


    /**
     * 客票种类 按月获取景区客票种类销售分布
     * @param vcode
     * @param start 开始年份
     * @param end 结束年份
     * @return
     */
    @SysLog("客票种类 按年区间获取景区客票种类销售分布")
    @ApiOperation(value = "客票种类 按年区间获取景区客票种类销售分布 ", notes = "客票种类 按年区间获取景区客票种类销售分布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "start", value = "开始年 格式:yyyy", required = true, paramType = "String"),
            @ApiImplicitParam(name = "end", value = "结束年 格式:yyyy", required = true, paramType = "String")
    })
    @GetMapping("/getTicketTypesYearRange")
    public Object getTicketTypesYearRange(String vcode,String start,String end){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(start, "开始年份不能为空");
        Assert.paramIsYear(start, "开始年份格式错误,格式为:yyyy");
        Assert.paramIsBlank(end, "结束年份不能为空");
        Assert.paramIsYear(end, "结束年份格式错误,格式为:yyyy");

        List<TicketClTypeVO> ticketTypeListByYearSlot = ticketService.getTicketTypeListByYearSlot(vcode, Integer.valueOf(start), Integer.valueOf(end));

        return ResponseBuilder.custom().success("success", 0).data(ticketTypeListByYearSlot).build();
    }


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

}
