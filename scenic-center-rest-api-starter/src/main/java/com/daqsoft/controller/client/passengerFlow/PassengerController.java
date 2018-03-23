package com.daqsoft.controller.client.passengerFlow;

import com.daqsoft.commons.responseentity.DataResponse;
import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.MyProps;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.passengerFlowService.*;
import com.daqsoft.utils.client.EUtils;
import com.daqsoft.utils.client.Growth;
import com.daqsoft.utils.client.MathUtils;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: lyl .lrd(2017-08-08)
 * @Date: Created in  2017/6/18.
 * @Version:V3.1
 * 注：客流分析类只放所有针对客流分析的方法，其他方法绕道
 * @describe: 客流分析
 */
@Api("客流分析-大数据")
@RestController
@RequestMapping("/rest/bigDataPassenger")
public class PassengerController {
    private Logger logger = LoggerFactory.getLogger(PassengerController.class);
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private RealBigPeopleService bigRealPeopleService;
    @Autowired
    private BigDataPageService bigDataPageService;
    @Autowired
    private CarFlowService carFlowService;
    @Autowired
    private JqScenicMsgService jqScenicMsgService;
    @Autowired
    private MyProps myProps;

    /**
     * 景区一年人数，节假日人数，客流来源（地图）展示
     * @param vcode    景区编码
     * @param dateTime 根据日期类型传入对应的时间
     *                 此接口“省外数据”来源为云台山当地移动提供
     *                 接口为定制接口。
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("客流来源分析-云台山定制数据")
    @ApiOperation(value = "客流来源分析-云台山定制数据", notes = "客流来源分析-云台山定制数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTime", value = "时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/getPeopleYearAndFrom")
    public Object getPeopleYearAndFrom(String dateTime, String vcode) throws JsonProcessingException {
        Assert.paramIsYear(dateTime, "请输入正确的年份！");
        Assert.paramIsBlank(vcode, "请输入景区编码或景区编码不正确！");
        //no参数代表不走DB 走缓存
        Map<String,Object>  map = passengerService.getBigPassengerAll(vcode,dateTime,"no");
        return ResponseBuilder.custom().success("success", 0).data(map).build();
    }


    /**
     * 景区一年人数，节假日人数，客流来源（地图）展示
     * @param vcode    景区编码
     * @param dateTime 根据日期类型传入对应的时间
     * 接口类型：通用；所有数据均取闸机、票务、停车场
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("客流来源分析（地图展示）-通用接口")
    @ApiOperation(value = "客流来源分析（地图展示）-通用接口", notes = "客流来源分析（地图展示）-通用接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTime", value = "时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/getPeopleYearCommon")
    public Object getPeopleYearCommon(String dateTime, String vcode) throws JsonProcessingException {
        Assert.paramIsYear(dateTime, "请输入正确的年份！");
        Assert.paramIsBlank(vcode, "请输入景区编码或景区编码不正确！");
        //no参数代表不走DB 走缓存
        Map<String,Object>  map = passengerService.getPeopleYearCommon(vcode,dateTime,"no");
        return ResponseBuilder.custom().success("success", 0).data(map).build();
    }


    /**
     * 大数据页面景区日客流游客统计
     *
     * @param vcode    景区编码
     * @param dateTime 根据日期类型传入对应的时间
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("景区日客流游客统计")
    @ApiOperation(value = "景区日客流游客统计", notes = "景区日客流游客统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTime", value = "时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String")
    })
    @GetMapping("/getPeopleDay")
    public Object getPeopleDay(String dateTime, String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(dateTime, "请输入时间！");
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List<RealPeopleDayVo> realPeopleDay = new LinkedList<>();
        try {
            //查询景区日客流
            realPeopleDay = passengerService.getPeopleDay(vcode, dateTime);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(realPeopleDay).build();
    }

    /**
     * 大数据页面景区日客流趋势分析
     *
     * @param vcode     景区编码
     * @param startTime 开始时间(不能为空！格式为yyyy-MM-dd)
     * @param endTime   结束时间(不能为空！格式为yyyy-MM-dd)
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("大数据页面景区日客流趋势分析")
    @ApiOperation(value = "大数据页面景区日客流趋势分析", notes = "大数据页面景区日客流趋势分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String"),
    })
    @GetMapping("/getPeopleDayTrend")
    public Object getPeopleDayTrend(String startTime, String endTime, String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsDate(startTime, "非法的日期参数!");
        Assert.paramIsDate(endTime, "非法的日期参数!");
        List<RealPeopleDayTrendVo> realPeopleDay = new ArrayList<>();
        //查询景区日客流趋势分析
        Map map = new HashMap();
        try {
            realPeopleDay = passengerService.getPeopleDayTrend(startTime, endTime, vcode);
            // 返回数据封装
            List<Map> residentList = new ArrayList<>();
            List<Map> newaddList = new ArrayList<>();
            List<Map> leaveList = new ArrayList<>();
            for (RealPeopleDayTrendVo realPeopleDayTrendVo:realPeopleDay){
                Map residentMap = new HashMap();
                residentMap.put("name", realPeopleDayTrendVo.getDay());
                residentMap.put("value", realPeopleDayTrendVo.getResident());
                residentList.add(residentMap);
                Map newaddMap = new HashMap();
                newaddMap.put("name", realPeopleDayTrendVo.getDay());
                newaddMap.put("value", realPeopleDayTrendVo.getNewadd());
                newaddList.add(newaddMap);
                Map leaveMap = new HashMap();
                leaveMap.put("name", realPeopleDayTrendVo.getDay());
                leaveMap.put("value", realPeopleDayTrendVo.getLeave());
                leaveList.add(leaveMap);
            }
            map.put("residentList", residentList);
            map.put("newaddList", newaddList);
            map.put("leaveList", leaveList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(map).build();
    }

    /**
     * 大数据页面景区日客流驻留时长分析
     *
     * @param vcode     景区编码
     * @param startTime 开始时间(不能为空！格式为yyyy-MM-dd)
     * @param endTime   结束时间(不能为空！格式为yyyy-MM-dd)
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("大数据页面景区日客流驻留时长分析")
    @ApiOperation(value = "大数据页面景区日客流驻留时长分析", notes = "大数据页面景区日客流驻留时长分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String"),
    })
    @GetMapping("/getVisitorAnalyse")
    public Object getVisitorAnalyse(String startTime, String endTime, String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsDate(startTime, "非法的日期参数!");
        Assert.paramIsDate(endTime, "非法的日期参数!");
        List<RealPeopleDayTrendVo> realPeopleDay = new LinkedList<>();
        Map map = new HashMap();
        //查询景区日客流趋势分析
        try {
            realPeopleDay = passengerService.getVisitorAnalyse(startTime, endTime, vcode);
            // 返回数据封装
            List<Map> residentList = new ArrayList<>();
            List<Map> newaddList = new ArrayList<>();
            List<Map> leaveList = new ArrayList<>();
            for (RealPeopleDayTrendVo realPeopleDayTrendVo:realPeopleDay){
                Map residentMap = new HashMap();
                residentMap.put("name", realPeopleDayTrendVo.getDay());
                residentMap.put("value", realPeopleDayTrendVo.getResident());
                residentList.add(residentMap);
                Map newaddMap = new HashMap();
                newaddMap.put("name", realPeopleDayTrendVo.getDay());
                newaddMap.put("value", realPeopleDayTrendVo.getNewadd());
                newaddList.add(newaddMap);
                Map leaveMap = new HashMap();
                leaveMap.put("name", realPeopleDayTrendVo.getDay());
                leaveMap.put("value", realPeopleDayTrendVo.getLeave());
                leaveList.add(leaveMap);
            }
            map.put("residentList", residentList);
            map.put("newaddList", newaddList);
            map.put("leaveList", leaveList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(map).build();

    }

    /**
     * 大数据页面景区客流驻留时长
     *
     * @param vcode    景区编码
     * @param dateTime 时间(不能为空！格式为yyyy-MM-dd)
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("大数据页面景区客流驻留时长")
    @ApiOperation(value = "大数据页面景区客流驻留时长", notes = "大数据页面景区客流驻留时长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "dateTime", value = "时间", required = true, paramType = "String")
    })
    @GetMapping("/getPeopleRemain")
    public Object getPeopleRemain(String dateTime, String vcode) throws
            JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsDate(dateTime, "非法的日期参数!");
        Map<String, Object> map = new HashMap<>();
        Map resMap = new HashMap();
        //查询景区日客流趋势分析
        try {
            map = passengerService.getPeopleRemain(dateTime, vcode);
            List dataList = new ArrayList();
            Object object = map.get("dataList");
            if(object != null){
                List<RealPeopleRemainVo> timeNumList = (List)object;
                for(RealPeopleRemainVo realPeopleRemainVo : timeNumList){
                    Map timeNumMap = new HashMap();
                    timeNumMap.put("name", realPeopleRemainVo.getTime());
                    timeNumMap.put("value", realPeopleRemainVo.getNum());
                    dataList.add(timeNumMap);
                }
            }
            resMap.put("dataList", dataList);
            resMap.put("percent", map.get("percent"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(resMap).build();
    }

    /**
     * 年统计省内客流分析前十
     *
     * @param year  年：2017
     * @param vcode 景区编码
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("年统计省内客流分析前十")
    @ApiOperation(value = "年统计省内客流分析前十", notes = "年统计省内客流分析前十")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "时间年", required = true, paramType = "String")
    })
    @GetMapping("/getProvincePlateRankTen")
    public Object getProvincePlateRankTen(String year, String vcode) throws
            JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(year, "年份不能为空!");
        List<ProvinceLicensePlateVo> plateVo = new LinkedList<>();
        Map resMap = new HashMap();
        List sumCarList = new ArrayList();
        List percentCarList = new ArrayList();
        //景区年统计省内客流分析排名前十
        try {
            plateVo = passengerService.getProvinceLicensePlateRank(year, vcode);
            for(ProvinceLicensePlateVo provinceLicensePlateVo : plateVo){
                Map sumMap = new HashMap();
                sumMap.put("name", provinceLicensePlateVo.getCARFROM());
                sumMap.put("value", provinceLicensePlateVo.getSUMCAR());
                sumCarList.add(sumMap);
                Map percentMap = new HashMap();
                percentMap.put("name", provinceLicensePlateVo.getCARFROM());
                percentMap.put("value", provinceLicensePlateVo.getPercentage());
                percentCarList.add(percentMap);
            }
            resMap.put("sumCarList", sumCarList);
            resMap.put("percentCarList", percentCarList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(resMap).build();
    }

    /**
     * 年统计省外客流分析
     *
     * @param year  年：2017
     * @param vcode 景区编码
     * @return
     * @throws JsonProcessingException
     * @update zf 20170904
     */
    @SysLog("年统计省外客流分析")
    @ApiOperation(value = "年统计省外客流分析", notes = "年统计省外客流分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "时间年", required = true, paramType = "String")
    })
    @GetMapping("/getProvinceOutside")
    public Object getProvinceOutside(String year, String vcode) throws
            JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(year, "年份不能为空!");
        List<ProvinceLicensePlateVo> plateVo = new LinkedList<>();
        Map resMap = new HashMap();
        List sumCarList = new ArrayList();
        List percentCarList = new ArrayList();
        try {
            plateVo = passengerService.getProvinceOutside(year, vcode);
            for(ProvinceLicensePlateVo provinceLicensePlateVo : plateVo){
                Map sumMap = new HashMap();
                sumMap.put("name", provinceLicensePlateVo.getCARFROM());
                sumMap.put("value", provinceLicensePlateVo.getSUMCAR());
                sumCarList.add(sumMap);
                Map percentMap = new HashMap();
                percentMap.put("name", provinceLicensePlateVo.getCARFROM());
                percentMap.put("value", provinceLicensePlateVo.getPercentage());
                percentCarList.add(percentMap);
            }
            resMap.put("sumCarList", sumCarList);
            resMap.put("percentCarList", percentCarList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(resMap).build();
    }

    /**
     * 景区省外客流来源  排名前十
     *--定制微件：云台山
     * @param year  年：2017
     * @param vcode 景区编码
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("景区省外客流来源排名前十--定制微件：云台山")
    @ApiOperation(value = "景区省外客流来源排名前十", notes = "景区省外客流来源排名前十")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "String")
    })
    @GetMapping("/getPeopleComeFromRank")
    public Object getPeopleComeFromRank(String year, String vcode) throws
            JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(year, "年份不能为空!");
        List<RealPeopleComeFromVo> PlateVo = new LinkedList<>();
        //景区年统计省外客流分析排名前十
        try {
            PlateVo = passengerService.getPeopleComeFromRank(year, vcode);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(PlateVo).build();
    }

    /**
     * 大数据客流来源分析
     *
     * @param year  年份
     * @param vcode 景区编码
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("大数据客流来源分析")
    @ApiOperation(value = "华蓥山版本大数据客流来源分析", notes = "华蓥山版本大数据客流来源分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "String")
    })
    @GetMapping("/findBigPeople")
    public Object findBigPeople(String year, String vcode) throws
            JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(year, "年份不能为空!");
        List<CarComeFromVo> peopleList = new ArrayList<>();
        try {
            peopleList = bigRealPeopleService.getBigPeopleYear(vcode, year);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(peopleList).build();
    }

    /**
     * 大数据页面。团体与散客对比
     *
     * @param date  日期
     * @param vcode 景区编码
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("团体与散客对比")
    @ApiOperation(value = "团体与散客对比", notes = "团体与散客对比")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/classIfication")
    public Object classIfication(String date, String vcode) throws
            JsonProcessingException {
        Assert.paramIsBlank(date, "请输入时间！");
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map<String, Object> map = new HashMap<>();
        try {
            map = bigDataPageService.classIfication(vcode, date);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 返回值
        return ResponseBuilder.custom().success("success", 0).data(map).build();
    }

    /**
     * 按年月日不同时段查询客流人次
     *
     * @param date  开始时间
     * @param type  类型
     * @param vcode 景区编码
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("查询客流人次")
    @ApiOperation(value = "查询客流人次", notes = "查询客流人次")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "日期类型", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/fundAllDate")
    public Object fundAllDate(String date, String type, String vcode) throws
            JsonProcessingException {
        Assert.paramIsBlank(date, "请输入时间！");
        Assert.paramIsBlank(type, "请输入时间类型！");
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        DataResponse dataResponse = new DataResponse();
        List<Big_RealP_Tend> thisTimeList = new ArrayList<>();
        Map<String, Object> myMap = new HashMap<>();
        try {

            thisTimeList = (List<Big_RealP_Tend>)bigRealPeopleService.getPassengerFlowByMonth(date, vcode);
            if ("month".equals(type) && EUtils.isNotEmpty(date) && date.length() == 4) {
                // 查询环比
                Object lastTimeList = bigRealPeopleService.getPassengerFlowByMonth(Integer.parseInt(date) - 1 + "",
                        vcode);
                Map<String, String> thisMap = new HashMap<>();
                Map<String, String> lastMap = new HashMap<>();

                for (Big_RealP_Tend tend : thisTimeList) {
                    thisMap.put(tend.getTime(), tend.getNum());
                }
                for (Big_RealP_Tend tend : (List<Big_RealP_Tend>)lastTimeList) {
                    lastMap.put(tend.getTime(), tend.getNum());
                }
                List<Growth> yearOnYearGrowth = new MathUtils().getYearOnYearGrowth(lastMap, thisMap, 12);
                List<Growth> annulusGrowth = new MathUtils().getAnnulusGrowth(thisMap, 12);
                myMap.put("thisTimeList", thisTimeList);
                myMap.put("yearOnYearGrowth", yearOnYearGrowth);
                myMap.put("annulusGrowth", annulusGrowth);
                // 前端判断单位类型
                boolean flag = false;
                for (int i = 0; i < thisTimeList.size(); i++) {
                    String unit = thisTimeList.get(i).getNum();
                    int unitType = Integer.parseInt(unit);
                    if (unitType >= 10000) {
                        flag = true;
                    }
                }
                myMap.put("unit", "人");
                dataResponse.setData(myMap);

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(myMap).build();
    }


    /**
     * 大数据页面。景区模拟游客画像  新版
     * auth tanggm
     * date 2017-08-07
     * desc 之前随机数每个阶段的数字与总数对应不上，而且差一个55岁以上数量分布，现更改。
     *
     * @param vcode
     * @param time
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/getPeoplePortrayal")
    public Object getPeoplePortrayal(String time, String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(time, "请输入时间！");
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map simulateMap = new HashMap();
        simulateMap.put("vcode",vcode);
        simulateMap.put("key","portrayalMonitor");
        Map simulateTour = passengerService.getSimulateTour(simulateMap);
        String []randomStr = (simulateTour.get("simulateTour") + "").split(",");
        String sixteen = String.valueOf((int) (Math.random() * (Integer.parseInt(randomStr[0]) - Integer.parseInt(randomStr[1]) + 1) + Integer.parseInt(randomStr[1])));//1-600
        String twentyFive = String.valueOf((int) (Math.random() * (Integer.parseInt(randomStr[2]) - Integer.parseInt(randomStr[3]) + 1) + Integer.parseInt(randomStr[3])));//1000-9999
        String thirtyFive = String.valueOf((int) (Math.random() * (Integer.parseInt(randomStr[4]) - Integer.parseInt(randomStr[5]) + 1) + Integer.parseInt(randomStr[5])));//1000-9999
        String fiftyFive = String.valueOf((int) (Math.random() * (Integer.parseInt(randomStr[6]) - Integer.parseInt(randomStr[7]) + 1) + Integer.parseInt(randomStr[7])));//1-2000
        String sixtyFive = String.valueOf((int) (Math.random() * (Integer.parseInt(randomStr[8]) - Integer.parseInt(randomStr[1]) + 1) + Integer.parseInt(randomStr[9])));//1-1000
        String fiveFive = String.valueOf((int) (Math.random() * (Integer.parseInt(randomStr[10]) - Integer.parseInt(randomStr[11]) + 1) + Integer.parseInt(randomStr[11])));//1-1000
        String[] peopleCount = {sixteen, twentyFive, thirtyFive, fiftyFive, sixtyFive, fiveFive};
        String[] peopleName = {"16岁以下", "16-25岁", "26-35岁", "36-45岁", "46-55岁", "55岁以上"};
        List dataList = new ArrayList();

        for (int x = 0; x < 6; x++) {
            Map map = new HashMap();
            map.put("name", peopleName[x]);
            map.put("value", peopleCount[x]);
            dataList.add(map);
        }
        Integer sumPeople = Integer.valueOf(sixteen) + Integer.valueOf(twentyFive) + Integer.valueOf(thirtyFive) + Integer.valueOf(fiftyFive) + Integer.valueOf(sixtyFive) + Integer.valueOf(fiveFive);//总人数
        Integer woman = (int) (Math.random() * (55 - 34 + 1) + 40);//模拟女人占比 40-55
        Integer man = 100 - woman;//模拟男人占比
        Map data = new HashMap();
        data.put("pie", dataList);
        data.put("sum", sumPeople);
        data.put("woman", woman + "%");
        data.put("man", man + "%");

        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }


    /**
     * 大数据页面。景区未来七天客流分析  新版
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/getPeopleFutureSevenDay")
    public Object getPeopleFutureSevenDay(String vcode) throws JsonProcessingException {
        try {
            Assert.paramIsBlank(vcode,"请输入景区编码！");
            List dataList = new LinkedList();
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(day);
            Date futureTime = MathUtils.fomatDateTime(date);
            //TODO LYL 修改模拟数据模式
            Map simulateMap = new HashMap();
            simulateMap.put("vcode",vcode);
            simulateMap.put("key","getPeopleFutureSevenDay");
            Map simulateTour = passengerService.getSimulateTour(simulateMap);
            String []randomStr = (simulateTour.get("simulateTour") + "").split(",");
            Map resMap = new HashMap();
            List historyPeopleList = new ArrayList();
            List futurePeopleList = new ArrayList();
            for (int i = 1; i < 7; i++) {
                //预计时间7天
                Calendar c = Calendar.getInstance();
                c.clear();
                c.setTime(futureTime);
                c.add(Calendar.DAY_OF_MONTH, i);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Map historyPeopleMap = new HashMap();
                historyPeopleMap.put("name", dateFormat.format(c.getTime()));
                historyPeopleMap.put("value", String.valueOf((int) (Math.random() * Integer.parseInt(randomStr[0]) - Integer.parseInt(randomStr[1]) + 1) + Integer.parseInt(randomStr[1])));
                historyPeopleList.add(historyPeopleMap);
                Map futurePeopleMap = new HashMap();
                futurePeopleMap.put("name", dateFormat.format(c.getTime()));
                futurePeopleMap.put("value", String.valueOf((int) (Math.random() * Integer.parseInt(randomStr[0]) - Integer.parseInt(randomStr[1]) + 1) + Integer.parseInt(randomStr[1])));
                futurePeopleList.add(futurePeopleMap);
            }
            resMap.put("historyPeopleList", historyPeopleList);
            resMap.put("futurePeopleList", futurePeopleList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            return ResponseBuilder.custom().failed("error", 1).build();
        }

    }
    /**
     *  获取景区所在省份大数据客流来源分析
     * @param vcode 景区编码
     * @param year 年份
     * @return
     */
    @SysLog("获取景区所在省份大数据客流来源分析")
    @ApiOperation(value = "获取景区所在省份大数据客流来源分析", notes = "获取景区所在省份大数据客流来源分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "日期", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/findBigPeopleCity")
    public Object findBigPeopleCity(String year, String vcode) throws
            JsonProcessingException {
        Assert.paramIsBlank(year, "请输入时间！");
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map<String, Object> resMap = new HashMap<>();
        List<CarComeFromCarVo> list = new ArrayList<>();
        String shortName = "";
        if (StringUtils.isBlank(vcode) || StringUtils.isBlank(year)) {
            resMap.put("provinceEnName", shortName);
            resMap.put("cityPeopleList", list);
        }
        try {
            //如果参数有一个为空返回空list
            if (StringUtils.isBlank(vcode) || StringUtils.isBlank(year)) {
                return new ArrayList();
            }
            // 查询景区省份简称
            String pname = carFlowService.getRegionByVcode(vcode);
            // 转换成前端的地图文件名称
            //shortName = ProvinceUtil.provinceNameToShort(pname);
            Map<String, Object> parMap = new HashMap<>();
            parMap.put("vcode", vcode);
            parMap.put("startTime", year + "-01-01");
            parMap.put("endTime", year + "-12-31");
            parMap.put("pname", pname);
            list = bigRealPeopleService.getBigPeopleYearCity(parMap);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        // 返回region，如果区域没有region,则返回上一级region
        String region = jqScenicMsgService.getJqScenicProvinceRegion(vcode);
        resMap.put("provinceEnName", shortName);
        resMap.put("cityPeopleList", list);
        resMap.put("region", region+"");
        return ResponseBuilder.custom().success("success", 0).data(resMap).build();
    }

    /**
     * 获取景区年实时人数 默认当前年
     * 用于高频率获取实时人数
     * @param vcode
     * @return
     * @throws Exception
     */
    @SysLog("获取景区年实时人数")
    @ApiOperation(value = "获取景区年实时人数", notes = "获取景区年实时人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/yearPeople")
    public Object yearPeople(String vcode) throws Exception{
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        int num = bigRealPeopleService.getYearPeople(vcode);
        Map resMap =new HashMap();
        resMap.put("num",num);
        return ResponseBuilder.custom().success("success", 0).data(resMap).build();

    }

    /**
     * 获取景区日实时人数 默认当前日期
     * 用于高频率获取实时人数
     * @param vcode
     * @return
     * @throws Exception
     */
    @SysLog("获取景区日实时人数")
    @ApiOperation(value = "获取景区日实时人数", notes = "获取景区日实时人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/dayPeople")
    public Object dayPeople(String vcode) throws Exception{
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        int num = bigRealPeopleService.dayPeople(vcode);
        Map resMap =new HashMap();
        resMap.put("num",num);
        return ResponseBuilder.custom().success("success", 0).data(resMap).build();

    }


    /**
     * 获取景区日实时人数，默认当前年份。
     * @param vcode
     * @return
     * @throws Exception
     */
    @SysLog("获取景区节假日实时人数")
    @ApiOperation(value = "获取景区节假日实时人数", notes = "获取景区节假日实时人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/holidayPeople")
    public Object holidayPeople(String vcode) throws Exception{
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        int num = bigRealPeopleService.holidayPeople(vcode);
        Map resMap =new HashMap();
        resMap.put("num",num);
        return ResponseBuilder.custom().success("success", 0).data(resMap).build();

    }

    /**
     * 七个法定节假日游客数（闸机）。
     * @param vcode
     * @return
     * @throws Exception
     */
    @SysLog("七个法定节假日游客数")
    @ApiOperation(value = "七个法定节假日游客数", notes = "七个法定节假日游客数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/legalHolidayPeople")
    public Object legalHolidayPeople(String vcode) throws Exception{
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List<Map> list = bigRealPeopleService.legalHolidayPeople(vcode);
//        Map resMap =new HashMap();
//        resMap.put("num",num);
        return ResponseBuilder.custom().success("success", 0).data(list).build();

    }

    /**
     * 华清宫大数据地图下面一栏定制组合接口
     * @param vcode
     * @return
     * @throws Exception
     */
    @SysLog("华清宫大数据地图下面一栏定制组合接口")
    @ApiOperation(value = "华清宫大数据地图下面一栏定制组合接口", notes = "华清宫大数据地图下面一栏定制组合接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/hqgBigDataMapBottom")
    public Object hqgBigDataMapBottom(String vcode) throws Exception{
        Assert.paramIsBlank(vcode, "请输入景区编码！");

        Map mapList = new HashMap();
        Map map = new HashMap();
        map = bigRealPeopleService.maxPeople(vcode);
        mapList.put("maxPeople",map);
        Map teamMap = new HashMap();
        teamMap = bigRealPeopleService.teamSumYear(vcode);
        mapList.put("teamSumYear",teamMap);
        Map enterTourist = new HashMap();
        enterTourist.put("sum",0);
        mapList.put("enterCountry",enterTourist);
        Map avergeMap = new HashMap();
        avergeMap = bigRealPeopleService.avergePassengerYear(vcode);
        mapList.put("avergePassenger",avergeMap);

        return ResponseBuilder.custom().success("success", 0).data(mapList).build();

    }


    /**
     * 本年人数峰值（闸机）。
     * @param vcode
     * @return
     * @throws Exception
     */
    @SysLog("本年人数峰值")
    @ApiOperation(value = "本年人数峰值", notes = "本年人数峰值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/maxPeople")
    public Object maxPeople(String vcode) throws Exception{
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map map = new HashMap();
        map = bigRealPeopleService.maxPeople(vcode);
        return ResponseBuilder.custom().success("success", 0).data(map).build();

    }

    /**
     * 本年团队数。
     * @param vcode
     * @return
     * @throws Exception
     */
    @SysLog("本年团队数")
    @ApiOperation(value = "本年团队数", notes = "本年团队数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/teamSumYear")
    public Object teamSumYear(String vcode) throws Exception{
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map map = new HashMap();
        map = bigRealPeopleService.teamSumYear(vcode);
        return ResponseBuilder.custom().success("success", 0).data(map).build();

    }

    /**
     * 入境游客总数。
     * @param vcode
     * @return
     * @throws Exception
     */
    @SysLog("入境游客总数")
    @ApiOperation(value = "入境游客总数", notes = "入境游客总数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/enterTouristSumYear")
    public Object enterTouristSumYear(String vcode) throws Exception{
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map map = new HashMap();
        map.put("sum",0);
        return ResponseBuilder.custom().success("success", 0).data(map).build();

    }


    /**
     * 年日均接待游客数量。
     * @param vcode
     * @return
     * @throws Exception
     */
    @SysLog("年日均接待游客数量")
    @ApiOperation(value = "年日均接待游客数量", notes = "年日均接待游客数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/avergePassengerYear")
    public Object avergePassengerYear(String vcode) throws Exception{
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map map = new HashMap();
        map = bigRealPeopleService.avergePassengerYear(vcode);
        return ResponseBuilder.custom().success("success", 0).data(map).build();

    }

    /**
     * 游客交通工具分析
     * @param vcode
     * @return
     * @throws Exception
     */
    @SysLog("游客交通工具分析")
    @ApiOperation(value = "游客交通工具分析", notes = "游客交通工具分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/vehicleAnalysisYear")
    public Object vehicleAnalysisYear(String vcode) throws Exception{
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map map = new HashMap();
        List<Map> list = new LinkedList();
        Map<String,String> trafficIcon = myProps.getTrafficIcon();
        Map plane = new HashMap();
        plane.put("sum",0);
        plane.put("image",trafficIcon.get("plane"));
        plane.put("name","飞机");

        Map train = new HashMap();
        train.put("sum",0);
        train.put("image",trafficIcon.get("train"));
        train.put("name","火车");

        Map bulletTrain = new HashMap();
        bulletTrain.put("sum",0);
        bulletTrain.put("image",trafficIcon.get("bulletTrain"));
        bulletTrain.put("name","动车");

        Map car = new HashMap();
        car.put("sum",0);
        car.put("image",trafficIcon.get("car"));
        car.put("name","汽车");
        list.add(plane);
        list.add(bulletTrain);
        list.add(train);
        list.add(car);
        return ResponseBuilder.custom().success("success", 0).data(list).build();

    }



    /////////////////////////////////景区景点接口部分开始/////////////////////////////////////////////////
    /**
     * 微件：景区景点数据分析
     * 今日景点游客数
     * 昨日景点游客数
     * 景点最大承载量
     * lrd 2017-09-05 v3.1.1版扩充的景点人数微件
     * @param vcode
     * @return
     * @throws Exception
     */
    @RequestMapping("/scenicPeople")
    public Object getScenicPeople(String vcode) throws Exception{
        try {
            Assert.paramIsBlank(vcode, "请输入景区编码！");
            List<Map<String,Object>> list = bigRealPeopleService.getScenicPeople(vcode);
            Map resMap = new HashMap();
            List thisDayNumList = new ArrayList();
            List lastDayNumList = new ArrayList();
            List maxquantityList = new ArrayList();
            for (Map map : list){
                Map thisDayMap = new HashMap();
                thisDayMap.put("name", map.get("scenicName"));
                thisDayMap.put("value", map.get("thisDayNum") != null?Integer.valueOf(map.get("thisDayNum").toString()):0);
                thisDayNumList.add(thisDayMap);
                Map lastDayMap = new HashMap();
                lastDayMap.put("name", map.get("scenicName"));
                lastDayMap.put("value", map.get("lastDayNum") != null?Integer.valueOf(map.get("lastDayNum").toString()):0);
                lastDayNumList.add(lastDayMap);
                Map maxquantityMap = new HashMap();
                maxquantityMap.put("name", map.get("scenicName"));
                maxquantityMap.put("value", map.get("maxquantity") != null?Integer.valueOf(map.get("maxquantity").toString()):0);
                maxquantityList.add(maxquantityMap);
            }
            resMap.put("thisDayNumList", thisDayNumList);
            resMap.put("lastDayNumList", lastDayNumList);
            resMap.put("maxquantityList", maxquantityList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("景区景点数据分析", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }
    /**
     * 微件：景区景点数据分析
     * 今日景点游客数
     * 昨日景点游客数
     * 求平均景点游客数
     * lrd 2017-09-05 v3.1.1版扩充的景点人数微件
     * @param vcode
     * @return
     * @throws Exception
     */
    @RequestMapping("/scenicPeopleAvg")
    public Object getScenicPeopleAvg(String vcode) throws Exception{
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List<Map<String,Object>> list = bigRealPeopleService.getScenicPeopleAvg(vcode);
        return ResponseBuilder.custom().success("success", 0).data(list).build();

    }
/////////////////////////////////景区景点接口部分结束/////////////////////////////////////////////////

}
