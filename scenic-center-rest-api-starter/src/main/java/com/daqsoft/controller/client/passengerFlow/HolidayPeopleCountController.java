package com.daqsoft.controller.client.passengerFlow;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.passengerFlowService.PassengerFlowAnalysisService;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.BaseCommonVo;
import com.daqsoft.vo.client.mysqlBean.HolidayFiveYearCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author superziy
 * @version V3.1
 * @description 客流分析--节假日人数统计
 * @date 2017-07-27 18
 */
@Api("客流分析-节假日人数统计")
@RestController
@RequestMapping("/rest/holidayPeople")
public class HolidayPeopleCountController {
    private Logger logger = LoggerFactory.getLogger(RealBigPeopleController.class);
    @Autowired
    private PassengerFlowAnalysisService passengerFlowAnalysisService;

    /**
     * 节假日五年每年的累计客流量对比
     * 2016-08-16 lrd  重构方法
     * 统计的是五年的。比如2011年劳动节，2012年劳动
     * @param year
     * @param vcode
     * @return
     */
    @SysLog("累计客流量对比")
    @ApiOperation(value = "累计客流量对比", notes = "累计客流量对比")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "时间年份", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "假日类型", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String")
    })
    @RequestMapping(value = "/countHolidayPeople", method = RequestMethod.GET)
    public Object countHolidayPeople(String year, String type, String vcode) {
//        参照大数据实验室类型	1 代表 元旦；2 代表 除夕；3 代表 春节；4 代表 清明节； 5 代表 劳动节；6 代表 端午节；7 代表 国庆节；8 代表 中秋节；
        Assert.paramIsBlank(year, "请输入正确时间！");
        Assert.paramIsBlank(type, "请输入假日类型：1 代表 元旦；2 代表 除夕；3 代表 春节；4 代表 清明节； 5 代表 劳动节；6 代表 端午节；7 代表 国庆节；8 代表 中秋节");
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        try {
            List<HolidayFiveYearCount> allList = passengerFlowAnalysisService.countHolidayPeople(year, type,vcode);
            Map resMap = new HashMap();
            List<BaseCommonVo> holidayPassengerList = new ArrayList<>();
            for (HolidayFiveYearCount holidayFiveYearCount : allList){
                BaseCommonVo baseCommonVo = new BaseCommonVo();
                baseCommonVo.setName(holidayFiveYearCount.getTime());
                baseCommonVo.setValue(holidayFiveYearCount.getNum());
                holidayPassengerList.add(baseCommonVo);
            }
            resMap.put("holidayPassengerList", holidayPassengerList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("累计客流量对比", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 近五年节假日统计人数
     * @param year
     * @param type
     * @param vcode
     * @return
     */
    @SysLog("近五年节假日统计人数")
    @ApiOperation(value = "近五年节假日统计人数", notes = "近五年节假日统计人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "时间年份", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "假日类型", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "numYear", value = "查询几年数据", paramType = "String"),
    })
    @RequestMapping(value = "/countFiveYearHoliday", method = RequestMethod.GET)
    public Object countFiveYearHoliday(String year, String type, String vcode,Integer numyear) {
        Assert.paramIsBlank(year, "请输入正确年份！");
        Assert.paramIsBlank(type, "请输入假日类型：1 代表 元旦；2 代表 除夕；3 代表 春节；4 代表 清明节； 5 代表 劳动节；6 代表 端午节；7 代表 国庆节；8 代表 中秋节");
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        try {
            List<HolidayFiveYearCount> allList = passengerFlowAnalysisService.countFiveYearHoliday(year, type,vcode,numyear);
            Map resMap = new HashMap<>();
            List<BaseCommonVo> holidayPassengerList = new ArrayList<>();
            for (HolidayFiveYearCount holidayFiveYearCount : allList){
                BaseCommonVo baseCommonVo = new BaseCommonVo();
                baseCommonVo.setName(holidayFiveYearCount.getTime());
                baseCommonVo.setValue(holidayFiveYearCount.getNum());
                holidayPassengerList.add(baseCommonVo);
            }
            resMap.put("holidayPassengerList", holidayPassengerList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("近五年节假日统计人数", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }
}
