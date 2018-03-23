package com.daqsoft.controller.client.ticketanalys;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.ticketanalys.TicketOtaAndOnlineService;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.BaseCommonVo;
import com.daqsoft.vo.client.madeVoBean.TicketClTypeVO;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-09-01 13:57
 * @Version: v1.0.0
 * @Describe: 票务统计分析 OTA 微件 (OTA 即为 把线上数据按各经销商 分组统计.)
 */
@Api("票务分析 ---OTA.")
@RestController
@RequestMapping("/rest/ticketOTA")
public class JqTicketOTAController {

    private Logger logger = LoggerFactory.getLogger(JqTicketOTAController.class);
    @Autowired
    private TicketOtaAndOnlineService ticketOTAService;
    /**
     * 按天获取 各ota 销售数据
     * @param vcode
     * @param day
     * @return
     */
    @SysLog("按天获取 各ota 销售数据")
    @ApiOperation(value = "按天获取 各ota 销售数据", notes = "按天获取 各ota 销售数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "day", value = "日期 格式:2017-07-01", required = true, paramType = "String")
    })
    @GetMapping("/otaSaleListByDay")
    public Object getOtaSaleListByDay(String vcode,String day){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(day, "日期不能为空");
        Assert.paramIsDate(day, "日期格式错误,格式为: 2017-01-01");

        List<TicketClTypeVO> otaSaleListByDay = ticketOTAService.getOtaSaleListByDay(vcode, day);

        return ResponseBuilder.custom().success("success", 0).data(otaSaleListByDay).build();
    }

    @SysLog("按天区间获取 各ota 销售数据")
    @ApiOperation(value = "按天区间获取 各ota 销售数据", notes = "按天区间获取 各ota 销售数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "start", value = "开始日期 格式:2017-07-01", required = true, paramType = "String"),
            @ApiImplicitParam(name = "end", value = "结束日期 格式:2017-07-01", required = true, paramType = "String")
    })
    @GetMapping("/otaSaleListByDayRange")
    public Object getOtaSaleListByDayRange(String vcode,String start,String end){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(start, "开始日期不能为空");
        Assert.paramIsBlank(end, "结束日期不能为空");
        Assert.paramIsDate(start, "开始日期格式错误,格式为: 2017-01-01");
        Assert.paramIsDate(end, "结束日期格式错误,格式为: 2017-01-01");

        List<TicketClTypeVO> otaSaleListByDayRange = ticketOTAService.getOtaSaleListByDayRange(vcode, start, end);

        return ResponseBuilder.custom().success("success", 0).data(otaSaleListByDayRange).build();

    }

    /**
     * 按月份获取 各ota 销售数据
     * @param vcode
     * @param month 月份
     * @return
     */
    @SysLog("按月份获取 各ota 销售数据")
    @ApiOperation(value = "按月份获取 各ota 销售数据", notes = "按月份获取各ota 销售数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "month", value = "月份 格式:yyyy-MM", required = true, paramType = "String")
    })
    @GetMapping("/otaSaleListByMonth")
    public Object getOtaSaleListByMonth(String vcode,String month){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(month, "月份不能为空");
        Assert.paramIsMonth(month, "月份格式错误,格式为: yyyy-MM");

        List<TicketClTypeVO> otaSaleListByDay = ticketOTAService.getOtaSaleListByMonth(vcode, month);

        return ResponseBuilder.custom().success("success", 0).data(otaSaleListByDay).build();
    }


    /**
     * 按年份获取 各ota 销售数据
     * @param vcode
     * @param year 年份
     * @return
     */
    @SysLog("按年份获取 各ota 销售数据")
    @ApiOperation(value = "按年份获取 各ota 销售数据", notes = "按年份获取各ota 销售数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年 格式:yyyy", required = true, paramType = "String")
    })
    @GetMapping("/otaSaleListByYear")
    public Object getOtaSaleListByYear(String vcode,String year){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(year, "年份不能为空");
        Assert.paramIsYear(year, "年份格式错误,格式为:yyyy");

        List<TicketClTypeVO> otaSaleListByDay = ticketOTAService.getOtaSaleListByYear(vcode, Integer.valueOf(year));

        return ResponseBuilder.custom().success("success", 0).data(otaSaleListByDay).build();
    }


    /**
     * 按年区间获取 各ota 销售数据
     * @param vcode
     * @param start 开始年份
     * @param end 结束年份
     * @return
     */
    @SysLog("按年区间获取 各ota 销售数据")
    @ApiOperation(value = "按年区间获取 各ota 销售数据", notes = "按年区间获取 各ota 销售数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "start", value = "开始年 格式:yyyy", required = true, paramType = "String"),
            @ApiImplicitParam(name = "end", value = "结束年 格式:yyyy", required = true, paramType = "String")
    })
    @GetMapping("/otaSaleListByYearRange")
    public Object getOtaSaleListByYearRange(String vcode,String start,String end){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(start, "开始年份不能为空");
        Assert.paramIsYear(start, "开始年份格式错误,格式为:yyyy");
        Assert.paramIsBlank(end, "结束年份不能为空");
        Assert.paramIsYear(end, "结束年份格式错误,格式为:yyyy");

        List<TicketClTypeVO> otaSaleListByYearRange = ticketOTAService.getOtaSaleListByYearRange(vcode, Integer.valueOf(start),Integer.valueOf(end));

        return ResponseBuilder.custom().success("success", 0).data(otaSaleListByYearRange).build();
    }

    /**
     * 按季度获取 各ota 销售数据
     * @param vcode
     * @param year 年份
     * @param quarter 季度 1/2/3/4
     * @return
     */
    @SysLog("按季度获取 各ota 销售数据")
    @ApiOperation(value = "按季度获取 各ota 销售数据", notes = "按季度获取 各ota 销售数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年 格式:yyyy", required = true, paramType = "String"),
            @ApiImplicitParam(name = "quarter", value = "季度 1/2/3/4", required = true, paramType = "String")
    })
    @GetMapping("/otaSaleListByQuarter")
    public Object getOtaSaleListByQuarter(String vcode,String year ,String quarter){
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(quarter, "季度不能为空!");
        Assert.paramIsQuarter(quarter,"季度参数错误: 正确参数为 1/2/3/4");

        Assert.paramIsBlank(year,"年份不能为空!");
        Assert.paramIsYear(year, "年份格式错误,格式为:yyyy");

        List<TicketClTypeVO> otaSaleListByDay = ticketOTAService.getOtaSaleListByQuarter(vcode, year,quarter);

        return ResponseBuilder.custom().success("success", 0).data(otaSaleListByDay).build();
    }

    @SysLog("ota统计，全年和全年今年季度")
    @ApiOperation(value = "ota统计，全年和全年今年季度", notes = "ota统计，全年和全年今年季度")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年 格式:yyyy", required = true, paramType = "String")
    })
    @GetMapping("/otaByYearQuarterRange")
    public Object otaByYearQuarterRange(String vcode,String year){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(year, "年份不能为空");
        Assert.paramIsYear(year, "年份格式错误,格式为:yyyy");
        try {
            Map map = new HashMap();
            List<BaseCommonVo> thisAmountList = new ArrayList<>();
            List<BaseCommonVo> lastAmountList = new ArrayList<>();
            List<Map> otaSaleByYear = ticketOTAService.otaSaleByYear(vcode, Integer.valueOf(year));
            List<Map> otaSaleByQuarter = ticketOTAService.otaSaleByQuarter(vcode, year);
            for (Map quaterMap : otaSaleByQuarter){
                BaseCommonVo thisVo = new BaseCommonVo();
                thisVo.setName(quaterMap.get("quarter"));
                thisVo.setValue(quaterMap.get("thisAmount"));
                thisAmountList.add(thisVo);
                BaseCommonVo lastVo = new BaseCommonVo();
                lastVo.setName(quaterMap.get("quarter"));
                lastVo.setValue(quaterMap.get("lastAmount"));
                lastAmountList.add(lastVo);
            }

            map.put("otaList",otaSaleByYear);
            map.put("thisAmountList",thisAmountList);
            map.put("lastAmountList",lastAmountList);

            return ResponseBuilder.custom().success("success", 0).data(map).build();
        } catch (Exception e){
            logger.error("ota统计，全年和全年今年季度", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

}
