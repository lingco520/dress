package com.daqsoft.controller.client.statisticAnalysis;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.controller.client.passengerFlow.PassengerController;
import com.daqsoft.service.client.commonService.CommonService;
import com.daqsoft.service.client.forecastwarning.ScenicMonitorService;
import com.daqsoft.service.client.passengerFlowService.PassengerService;
import com.daqsoft.service.client.passengerFlowService.RealBigPeopleService;
import com.daqsoft.service.client.statisticAnalysisService.StatisticAnalysisServie;
import com.daqsoft.timer.ParkingTimer;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.Page;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
 * @date 2017-06-27 10:53
 * @Version:
 * @Describe: 统计分析微件
 */
@Api("统计分析系统")
@RestController
@RequestMapping("/rest/statisticAnalysis")
public class StatisticAnalysisController {
    private Logger logger = LoggerFactory.getLogger(PassengerController.class);
    @Autowired
    private StatisticAnalysisServie statisticAnalysisServie;
    @Autowired
    private ScenicMonitorService scenicMonitorService;
    @Autowired
    private ParkingTimer parkingTimer;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RealBigPeopleService realBigPeopleService;
    @Autowired
    private PassengerService passengerService;

    /**
     * 获取景区人数统计分析
     *
     * @param vcode
     * @return
     */
    @SysLog("获取景区人数统计分析")
    @ApiOperation(value = "景区人数统计", notes = "获取包含当年在内的前五年景区人数统计")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/numberOfPeople")
    public Object getNumberOfScenicSpots(String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        try {
            Map<String, Object> numberOfScenicSpots = statisticAnalysisServie.getNumberOfScenicSpots(vcode);
            Map resMap = new HashMap();
            List numList = new ArrayList();
            List yearOnYearList = new ArrayList();
            if(numberOfScenicSpots != null){
                List<NumberOfScenicSpotsVO> dataList = (List)numberOfScenicSpots.get("dataList");
                for(NumberOfScenicSpotsVO numberOfScenicSpotsVO : dataList){
                    Map numMap = new HashMap();
                    numMap.put("name", numberOfScenicSpotsVO.getYear());
                    numMap.put("value", numberOfScenicSpotsVO.getNum());
                    numList.add(numMap);
                    Map yearOnYearMap = new HashMap();
                    yearOnYearMap.put("name", numberOfScenicSpotsVO.getYear());
                    yearOnYearMap.put("value", numberOfScenicSpotsVO.getYearOnYear());
                    yearOnYearList.add(yearOnYearMap);
                }
            }
            resMap.put("numList", numList);
            resMap.put("yearOnYearList", yearOnYearList);
            resMap.put("totalNum", numberOfScenicSpots.get("totalNum"));
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("获取景区人数统计分析", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 获取景区大巴统计分析
     *
     * @param vcode
     * @return
     */
    @SysLog("获取景区大巴统计分析")
    @ApiOperation(value = "景区大巴统计", notes = "获取包含当年在内的前五年景区大巴统计")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
//    @GetMapping("/statisticsOfTheBus/{vcode}")
    @GetMapping("/statisticsOfTheBus")
    public Object getStatisticsOfTheBus(String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Map<String, Object> statisticsOfTheBus = statisticAnalysisServie.getStatisticsOfTheBus(vcode);
        return ResponseBuilder.custom().success("success", 0).data(statisticsOfTheBus).build();
    }
    /**
     * 客流来源地统计
     *
     * @param vcode
     * @param startDate
     * @param endDate
     * @return
     */
    @SysLog("客流来源地统计")
    @ApiOperation(value = "客流来源地统计", notes = "客流来源地统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = false, paramType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = false, paramType = "String")
    })
    @GetMapping("/vehiclePassengerFlow")
    public Object vehiclePassengerFlow(String vcode, String startDate, String endDate) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(startDate, "开始日期不能为空");
        Assert.paramIsBlank(endDate, "结束日期不能为空");
        try {
            Map resMap = new HashMap();
            Map<String, Object> vehicleStatistics = statisticAnalysisServie.getVehicleStatistics(vcode, startDate, endDate);
            List<BaseCommonVo> sourceVehicleList = new ArrayList<>();
            if(vehicleStatistics != null && vehicleStatistics.get("dataList") != null){
                List<CarFromProvinceVo> vehicleList = (List<CarFromProvinceVo>)vehicleStatistics.get("dataList");
                for(CarFromProvinceVo carFromProvinceVo : vehicleList){
                    BaseCommonVo baseCommonVo = new BaseCommonVo();
                    baseCommonVo.setName(carFromProvinceVo.getCARFROM());
                    baseCommonVo.setValue(!StringUtils.isEmpty(carFromProvinceVo.getSUMCAR())?Long.valueOf(carFromProvinceVo.getSUMCAR()):0);
                    sourceVehicleList.add(baseCommonVo);
                }
            }

            List<Map> sourceOfPassengerFlow = statisticAnalysisServie.getSourceOfPassengerFlow(vcode, startDate, endDate);
            //TODO 此处为模拟数据，若没有数据则模拟成此数据 华清宫宫要求
            if(sourceOfPassengerFlow.size() < 1){
                List<String> timeList = DateUtil.getSubsectionDateList(startDate, endDate);
                int i = timeList.size();
                Map internationNew = new HashMap();
                internationNew.put("name", "境外");
                internationNew.put("value", 11 * i);
                sourceOfPassengerFlow.add(internationNew);
                Map outsideNew = new HashMap();
                outsideNew.put("name", "省外");
                outsideNew.put("value", 321 * i);
                sourceOfPassengerFlow.add(outsideNew);
                Map provinceNew = new HashMap();
                provinceNew.put("name", "省内");
                provinceNew.put("value", 233 * i);
                sourceOfPassengerFlow.add(provinceNew);
            }

            resMap.put("sourcePassengerList", sourceOfPassengerFlow);
            resMap.put("sourceVehicleList", sourceVehicleList);

            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("客流来源地统计", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 景区累计游客量
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("景区累计游客量")
    @ApiOperation(value = "景区累计游客量", notes = "景区累计一年的线上和线下人数")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/totalVisitorNumbers")
    public Object totalVisitorNumbers(String vcode,String stime,String etime) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        if(stime!=null){
            Assert.paramIsBlank(stime, "非法的日期参数");
        }
        if(etime!=null){
            Assert.paramIsBlank(etime, "非法的日期参数");
        }
        Map obj = statisticAnalysisServie.totalVisitorNumbers(vcode,stime,etime);
        return ResponseBuilder.custom().success("success", 0).data(obj).build();
    }
    /**
     * 景区停车数
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("景区停车数")
    @ApiOperation(value = "景区停车数", notes = "景区累计一年停车数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "stime", value = "开始时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "etime", value = "结束时间", required = true, paramType = "String")
    })
    @GetMapping(value = "/totalParkingNum")
    public Object totalParkingNum(String vcode,String stime,String etime) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
       if(stime!=null){
            Assert.paramIsDate(stime, "非法的日期参数");
        }
        if(etime!=null){
            Assert.paramIsDate(etime, "非法的日期参数");
        }
        Map resMap = scenicMonitorService.getScenicParkingCount(vcode,stime,etime);
        return ResponseBuilder.custom().success("success", 0).data(resMap).build();
    }

    /**
     * 团队总数
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("团队总数")
    @ApiOperation(value = "团队总数", notes = "景区累计一年团队总数")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/totalTeamNum")
    public Object totalTeamNum(String vcode,String stime,String etime) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        if(stime!=null){
            Assert.paramIsDate(stime, "非法的日期参数");
        }
        if(etime!=null){
            Assert.paramIsDate(etime, "非法的日期参数");
        }
        List<TeamTypeVo> list = statisticAnalysisServie.totalTeamNum(vcode,stime,etime);
        // 现数据暂时模拟的
        Map map = new HashMap();
        Integer total = 0;
        // 这里计算总数应该在实现方法里面一起计算了，这里考虑到返回数据格式问题，在这里简单处理
        for (TeamTypeVo teamTypeVo: list) {
            total += teamTypeVo.getCount();
        }
        map.put("total",total+"");
        return ResponseBuilder.custom().success("success", 0).data(list).extend(map).build();
    }

    /**
     * 停车场统计分析
     * @param vcode
     * @return
     */
    @SysLog("停车场统计分析")
    @ApiOperation(value = "停车场统计分析", notes = "景区累计五年停车场统计分析")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/getParks")
    public Object getParks(String vcode){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        List list = statisticAnalysisServie.getParks(vcode);
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }


    /**
     * 接待人数统计
     * 今年和去年的
     * lrd 2017-08-17 走统一缓存
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("接待人数统计")
    @ApiOperation(value = "接待人数统计", notes = "景区近两年接待人数统计")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/totalReceptionNum")
    public Object totalReceptionNum(String vcode,String year){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        if(year!=null){
            Assert.paramIsYear(year,"非法的年份参数");
        }
        Map list = statisticAnalysisServie.totalReceptionNum(vcode,year);
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

    /**
     * 网评统计
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("网评统计")
    @ApiOperation(value = "网评统计", notes = "景区累计一年网评统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "String")
    })
    @GetMapping(value = "/onlineComment")
    public Object onlineComment(String vcode, String startTime, String endTime) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        if (startTime != null) {
            Assert.paramIsDate(startTime, "非法的日期参数");
        }
        if (endTime != null) {
            Assert.paramIsDate(endTime, "非法的日期参数");
        }
        List list = statisticAnalysisServie.onlineComment(vcode, startTime, endTime);
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

    /**
     * 网评评分统计
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("网评评分统计")
    @ApiOperation(value = "网评评分统计", notes = "景区累计网评评分统计")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/onlineCommentGrade")
    public Object onlineCommentGrade(String vcode){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        List list = statisticAnalysisServie.onlineCommentGrade(vcode);
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

    /**
     * 网评评分统计
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("网评分析")
    @ApiOperation(value = "网评分析", notes = "网评分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "String"),
            @ApiImplicitParam(name = "month", value = "月份", required = true, paramType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页数据", required = true, paramType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "emotion", value = "评轮类型", paramType = "String")}
    )
    @GetMapping(value = "/commentAnalysis")
    public Object commentAnalysis(String vcode, String year, String month, String pageSize, String page, String emotion) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsNumber(year, "非法的年格式");
        Assert.paramIsNumber(month, "非法的月份");
        Assert.paramIsNumber(pageSize, "非法的分页参数");
        Assert.paramIsNumber(page, "非法的分页参数");
        try {
            Map map = statisticAnalysisServie.commentAnalysis(vcode, year, month, pageSize, page, emotion);
            int count = (int)map.get("count");
            Page pager = Page.newPage(Integer.parseInt(pageSize),page, count);
            return ResponseBuilder.custom().success("success", 0).data(map.get("list")).totalCount(count).
                    pageSize(pager.getpageSize()).totalPage(pager.getTotalPage()).currPage(pager.getCurrentPage()).build();
        } catch (Exception e){
            logger.error("网评分析", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }


    @Test
    public void builder() {

        String x = null;
        // 如果需要验证格式等等 若需要统一处理 直接抛出异常 RRException code 为 400 显示效果与下面参数验证一直
        //Assert.paramIsBlank(x,"景区编码不能为空!");

        System.out.println(JSONObject.fromObject(ResponseBuilder.custom().failed("error").build()).toString());
        System.out.println(JSONObject.fromObject(ResponseBuilder.custom().failed("error", 2).build()).toString());
        System.out.println(JSONObject.fromObject(ResponseBuilder.custom().success().build()).toString());
        System.out.println(JSONObject.fromObject(ResponseBuilder.custom().success("success").build()).toString());
        System.out.println(JSONObject.fromObject(ResponseBuilder.custom().success("success", 5001).build()).toString());
        //演示错误情况,如果只设置了页码，并没有数据，那么作为BaseResponse返回
        System.out.println(JSONObject.fromObject(ResponseBuilder.custom().success().currPage(1).pageSize(10).totalCount(50).totalPage(5).build()).toString());
        List<NumberOfScenicSpotsVO> list = new ArrayList<>(2);
        list.add(new NumberOfScenicSpotsVO());
        list.add(new NumberOfScenicSpotsVO());
        System.out.println(JSONObject.fromObject(ResponseBuilder.custom().success().currPage(1).pageSize(10).totalCount(50).totalPage(5).data(new NumberOfScenicSpotsVO()).build()).toString());
        System.out.println(JSONObject.fromObject(ResponseBuilder.custom().success().currPage(1).pageSize(10).totalCount(50).data(list).build()).toString());
//        System.out.println(toJson(ResponseBuilder.custom().success().data(new Foo("ShawnShoper","addr")).build()));
    }
    /**
     * 停车场历史补偿方法
     * @param
     * @return
     */
    @SysLog("停车场redis数据补偿")
    @ApiOperation(value = "停车场redis数据补偿", notes = "停车场redis数据补偿")
    @ApiImplicitParam(name = "date", value = "日期", required = true, paramType = "String")
    @GetMapping(value = "/getParkingHistory")
    public Object getParkingHistory(String startDate, String endDate) throws  Exception{
        try{
            Assert.paramIsBlank(startDate, "开始日期不能为空");
            Assert.paramIsBlank(endDate, "结束日期不能为空");
            List<Map<String, Object>> vcodeList = commonService.getParkingVcode();
            if(vcodeList.size() > 0){
                for (Map<String, Object> map : vcodeList){
                    // 计算天的数据
                    String vcode = map.get("vcode")+"";
                    scenicMonitorService.getParkingByThisHors(vcode,startDate,endDate);

                    // 计算月的数据
                    // 传入时间的年份
                    String year = DateUtil.getCurYearStrByDate(startDate);
                    // 当前年份
                    String nowyear = DateUtil.getCurYearStr();
                    String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
                    if(Integer.valueOf(year).compareTo(Integer.valueOf(nowyear)) >= 0){
                        String nowmonth = DateUtil.getCurMonthNumStr();
                        for(String month : months){
                            if(Integer.valueOf(month).compareTo(Integer.valueOf(nowmonth)) <= 0){
                                parkingTimer.getParkingByMonth(year,month,vcode);
                            }
                        }
                    }else{
                        for(String month : months){
                            parkingTimer.getParkingByMonth(year,month,vcode);
                        }
                    }
                    // 计算年的数据
                    parkingTimer.getParkingByYear(year,vcode);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return true;
    }
    /**
     * 停车场统计分析--按月份统计景区每个停车场总的停车数量
     * @param vcode
     * @return
     */
    @SysLog("根据月份统计分析停车场")
    @ApiOperation(value = "根据月份统计分析停车场", notes = "根据月份统计分析停车场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "month", value = "月份", required = true, paramType = "String")
    })
    @GetMapping(value = "/getParksByMonth")
    public Object getParksByMonth(String vcode, String month){
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsBlank(month, "日期不能为空");
        Assert.paramIsMonth(month, "日期格式不对(如2017-12)");
        try {
            List<Map> list = statisticAnalysisServie.getParksByMonth(vcode, month);
            Map resMap = new HashMap();
            List<BaseCommonVo> parkingList = new ArrayList<>();
            for (Map map : list){
                BaseCommonVo parking = new BaseCommonVo();
                parking.setName(map.get("name"));
                parking.setValue(map.get("count")!=null?map.get("count"):0);
                parkingList.add(parking);
            }
            resMap.put("parkingList", parkingList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 景区游客来源分析(微件升级，返回数据在一个接口里面)
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("景区游客来源分析")
    @ApiOperation(value = "景区游客来源分析", notes = "景区游客来源分析")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/getPassengerAnalysis")
    public Object getPassengerAnalysis(String vcode,String year) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        year = DateUtil.getCurYearStr();
        try {
            Map resMap = new HashMap();
            List holidayList = new ArrayList();
            List<Map> holidayVoList = realBigPeopleService.legalHolidayPeople(vcode);
            for (Map map : holidayVoList){
                Map holidayMap = new HashMap();
                holidayMap.put("name", map.get("holidayName"));
                holidayMap.put("time", DateUtil.getYMDStr(map.get("startDate")+"")+"-"+DateUtil.getYMDStr(map.get("endDate")+""));
                holidayMap.put("peopleNumber", map.get("sum"));
                holidayMap.put("percent", map.get("percentage"));
                holidayList.add(holidayMap);
            }
            // 接待游客数
            int totalNumberPeople = realBigPeopleService.getYearPeople(vcode);
            Map totalNumberPeopleMap = new HashMap();
            totalNumberPeopleMap.put("unit", "人");
            totalNumberPeopleMap.put("value", totalNumberPeople);
            totalNumberPeopleMap.put("year", year);
            // 节假日累计人数
            int totalHolidayPeople = realBigPeopleService.holidayPeople(vcode);
            Map totalHolidayPeopleMap = new HashMap();
            totalHolidayPeopleMap.put("unit", "人");
            totalHolidayPeopleMap.put("value", totalHolidayPeople);
            totalHolidayPeopleMap.put("year", year);

            // 下面四个模块值
            Map touristMap = realBigPeopleService.maxPeople(vcode);
            Map touristPeakMap = new HashMap();
            touristPeakMap.put("value", touristMap.get("num") != null ? touristMap.get("num") : 0);
            touristPeakMap.put("time", touristMap.get("time")!= null ? touristMap.get("time") : DateUtil.getCurDateStr());

            Map teamMap = realBigPeopleService.teamSumYear(vcode);
            Map teamTotalMap = new HashMap();
            teamTotalMap.put("value", teamMap.get("sum"));

            Map dailyReception = realBigPeopleService.avergePassengerYear(vcode);
            Map dailyReceptionMap = new HashMap();
            dailyReceptionMap.put("value", dailyReception.get("num"));

            Map entryTouristsMap = new HashMap();
            // 以前的代码都是固定写死一个 0
            entryTouristsMap.put("value", "0");

            // mapList
            List<Map> mapList = new ArrayList<>();
            Map<String,Object>  map = passengerService.getPeopleYearCommon(vcode,year,"no");
            if(map != null){
                List<Map> comeFromByMapList = (List<Map>)map.get("comeFromByMap");
                // 景区数据
                JqScenicAreas jqScenicAreas = (JqScenicAreas)map.get("scenicList");
                for(Map comeFromMap : comeFromByMapList){
                    Map mapVo = new HashMap();
                    Map startMap = new HashMap();
                    startMap.put("name", comeFromMap.get("sourceCity"));
                    startMap.put("value", comeFromMap.get("sourcecityNew"));
                    Map regionMap = passengerService.getRegionByName(comeFromMap.get("sourceCity")+"");
                    String[] region = new String[2];
                    region[0] = regionMap.get("longitude") !=null ? regionMap.get("longitude").toString():"";
                    region[1] = regionMap.get("latitude")!=null?regionMap.get("latitude").toString():"";
                    startMap.put("region", region);
                    mapVo.put("start", startMap);

                    Map endMap = new HashMap();
                    endMap.put("name", jqScenicAreas.getName());
                    mapVo.put("end", endMap);
                    mapList.add(mapVo);
                }
            }

            resMap.put("holidayList", holidayList);
            resMap.put("totalNumberPeople", totalNumberPeopleMap);
            resMap.put("totalHolidayPeople", totalHolidayPeopleMap);
            resMap.put("touristPeak", touristPeakMap);
            resMap.put("teamTotal", teamTotalMap);
            resMap.put("dailyReception", dailyReceptionMap);
            resMap.put("entryTourists", entryTouristsMap);
            resMap.put("mapList", mapList);

            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("景区游客来源分析-微件升级", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }
}
