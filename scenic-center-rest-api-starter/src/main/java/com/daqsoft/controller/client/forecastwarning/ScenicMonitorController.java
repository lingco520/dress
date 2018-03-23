package com.daqsoft.controller.client.forecastwarning;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.comprehensiveService.TimelyParkingCarFlowService;
import com.daqsoft.service.client.forecastwarning.ScenicMonitorService;
import com.daqsoft.service.client.gisbasedService.GisBasicService;
import com.daqsoft.service.client.informationService.InformationService;
import com.daqsoft.service.client.passengerFlowService.RealBigPeopleService;
import com.daqsoft.service.client.passengerFlowService.ThreeDimensionalMapService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tanggm
 * @version V3.1.0
 * @description 景区运行监测
 * @date 2017-06-23 14:40
 */
@Api("景区运行监测")
@RestController
@RequestMapping(value = "/rest/scenicMonitor")
public class ScenicMonitorController {
    private Logger logger = LoggerFactory.getLogger(ScenicMonitorController.class);
    @Autowired
    private ThreeDimensionalMapService threeDimensionalMapService;
    @Autowired
    private ScenicMonitorService scenicMonitorService;
    @Autowired
    private TimelyParkingCarFlowService timelyParkingService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private GisBasicService gisBasicService;
    @Autowired
    RealBigPeopleService realBigPeopleService;
    /**
     * 景区运行监测-实时人数、团队数、累计游客、人流热力图
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("景区运行监测")
    @ApiOperation(value = "景区运行监测", notes = "景区运行监测-实时人数、团队数、累计游客、人流热力图")
    @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String")
    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public Object monitor(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Map<String, Object> resMap = new HashMap<>();
        List<GisSourceGpsVo> gis = new LinkedList<>();
        try{
            // 三维地图数据
            List<SysConfigTendVo> sVolist = threeDimensionalMapService.getSysConfig(vcode);
            //查询gis景点
            gis = gisBasicService.getScenic(vcode);
            // 实时人数,这里由于云台山客户需求不同，这里取线下票务(jq_ticket)数量，不取咂机数据
            Map map = new HashMap();
            map.put("vcode", vcode);
            map.put("date", DateUtil.getCurDateStr());

            //12.6  修改 将云台山和其他景区区分开，其他景区取人数
            Object realTimePeople = null;
            if(vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")) {
                realTimePeople = scenicMonitorService.getRealPeopleByTicket(DigestUtils.md5Hex(vcode), DateUtil.getCurDateStr());
            } else {
                realTimePeople = scenicMonitorService.getPeopleRealByDate(map);
            }
            // 实时旅行团
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
            String year = format2.format(new Date());
            int toursNum = scenicMonitorService.getToursByDate(year, vcode);
            // 本年累计游客
            Object yearTotalPeople = scenicMonitorService.getYearTotalPeople(year, vcode);
            // 人流热力图
            // 地图数据
            resMap.put("sVolist", sVolist);
            // 地图数据
            resMap.put("gisList", gis);
            //　实时人数
            resMap.put("realTimePeople", realTimePeople);
            // 实时团队
            resMap.put("toursNum", toursNum);
            // 年旅客数量
            resMap.put("yearTotalPeople", yearTotalPeople);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseBuilder.custom().success("success",200).data(resMap).build();
    }

    /**
     * 华清宫景区运行监测地图
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("华清宫景区运行监测地图")
    @ApiOperation(value = "华清宫景区运行监测地图", notes = "华清宫景区运行监测地图")
    @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String")
    @RequestMapping(value = "/monitorHQG", method = RequestMethod.GET)
    public Object monitorHQG(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Map<String, Object> resMap = new HashMap<>();
        List<GisSourceGpsVo> gis = new LinkedList<>();
        try{
            // 实时人数,这里由于华清宫客户需求不同，这里取线下票务(jq_ticket)数量，不取咂机数据
            int realTimePeople = 0;
            realTimePeople = realBigPeopleService.dayPeople(vcode);

            BigDecimal load = BigDecimal.ZERO;
            load = new BigDecimal(realTimePeople).divide(new BigDecimal(92000),2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));

            Map parMap = new HashMap();
            parMap.put("vcode", vcode);
            parMap.put("num", realTimePeople);
            Map<String, Object> comfortInfo = scenicMonitorService.getComfortInfo(parMap);
            String rating = "";
            String colorvalue = "";
            if(comfortInfo != null){
                rating = comfortInfo.get("rating")+"";
                colorvalue = comfortInfo.get("colorvalue")+"";
            }
            // 人流热力图
            // 地图数据
            resMap.put("centerLon", "109.212921021633");
            resMap.put("centerLat", "34.36328614067683");
            resMap.put("mapType","gd");
            // 地图数据
            resMap.put("loading", load);
            resMap.put("rating", rating);
            resMap.put("colorvalue", colorvalue);
            resMap.put("mapScale", 18);
            //　实时人数
            resMap.put("realTimePeople", realTimePeople);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseBuilder.custom().success("success",200).data(resMap).build();
    }


    /**
     * wifi热力图
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("wifi热力图")
    @ApiOperation(value = "wifi热力图", notes = "wifi热力图")
    @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String")
    @RequestMapping(value = "/wifi", method = RequestMethod.GET)
    public Object wifi(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        // 查询列表
        Map<String, Object> resMap = new HashMap<>();
        List<SpotsWifiVo> spotsWifiVoList = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
            String date = format.format(new Date());
            date = "2017-06-05";
            // 查询
            Map<String, Object> parMap = new HashMap<>();
            parMap.put("vcode", vcode);
            parMap.put("date", date);
            spotsWifiVoList =  scenicMonitorService.getSpotsWifiList(parMap);
        }catch (Exception e) {
            e.printStackTrace();
        }
        resMap.put("spotsWifiList",spotsWifiVoList);
        return ResponseBuilder.custom().success("success",200).data(resMap).build();
    }
    /**
     * 景区实时游客量
     * 按小时
     * @return
     */
    @SysLog("景区实时游客量")
    @ApiOperation(value = "景区实时游客量", notes = "景区实时游客量")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/scenicPassenger")
    public Object scenicPassenger(String vcode, String date) throws JsonProcessingException{
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsBlank(date,"日期不能为空!");
        // 查询列表
        Map<String, Object> resMap = new HashMap<>();
        try {
            resMap =  scenicMonitorService.scenicPassenger(date, vcode);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBuilder.custom().success("success",200).data(resMap).build();
    }
    /**
     * 景区投诉
     * @return
     */
    @SysLog("景区投诉")
    @ApiOperation(value = "景区投诉", notes = "景区投诉")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/complaints")
    public Object complaints(String vcode, String year) throws JsonProcessingException{
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsBlank(year,"年份值不能为空!");
        String mdfVcode = DigestUtils.md5Hex(vcode);
        // 查询列表
        Map<String, Object> resMap = new HashMap<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            Date ddate = new SimpleDateFormat("yyyy").parse(year);
            String nowyear = format.format(ddate);
            // 查询
            resMap =  scenicMonitorService.complaints(nowyear, mdfVcode, vcode);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBuilder.custom().success("success",200).data(resMap).build();
    }
    /**
     * 景区停车场使用情况
     * @return
     */
    @SysLog("景区停车场使用情况")
    @ApiOperation(value = "景区停车场使用情况", notes = "景区停车场使用情况")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/parkingStatus")
    public Object parkingStatus(String vcode, String date) throws JsonProcessingException{
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsBlank(date,"日期不能为空!");
        // 查询列表
        Map<String, Object> resMap = new HashMap<>();
        List<ParkingNewVo> parkingList = new ArrayList<>();
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("stTime", DateUtil.getCurDateStr() + " 00:00:00");
        map.put("edTime", DateUtil.getCurDateStr() + " 23:59:59");
        Integer total = 0;
        Integer parkingTotal = 0;
        Integer surplusPaking = 0;
        try {
            //查询停车场 总车位、已使用、未使用数量
            //timelyParkingSurplusVo = timelyParkingService.getJqTimelyParkingData(map);
            List<Map> scenicNames = scenicMonitorService.getScenicNamesList(vcode);
            for (Map rmap : scenicNames){
                Map parMap = new HashMap();
                parMap.put("parkid", rmap.get("id"));
                parMap.put("startTime", date+" 00:00:00");
                parMap.put("endTime", date+" 23:59:59");
                parMap.put("vcode", vcode);
                ParkingNewVo parkingVo = scenicMonitorService.getParkingNewInfo(parMap);
                if(parkingVo != null){
                    parkingVo.setPname(rmap.get("name")+"");
                    parkingTotal+=Integer.valueOf(parkingVo.getMaxParkSpace());// 总停车位
                    surplusPaking+=Integer.valueOf(parkingVo.getTotalParking());// 剩余数量
                    total+=Integer.valueOf(parkingVo.getMaxParkSpace())-Integer.valueOf(parkingVo.getTotalParking());// 已使用数量
                    parkingList.add(parkingVo);
                }else {
                    parkingTotal+=Integer.valueOf(rmap.get("parkingTotal").toString());
                    surplusPaking += Integer.valueOf(rmap.get("parkingTotal").toString());
                    ParkingNewVo parkingNull = new ParkingNewVo();
                    parkingNull.setPname(rmap.get("name")+"");
                    parkingNull.setMaxParkSpace(rmap.get("parkingTotal").toString());
                    parkingNull.setTotalParking(rmap.get("parkingTotal").toString());
                    parkingList.add(parkingNull);
                }
            }
            // 返回数据处理
            List usedList = new ArrayList();
            List surplusList = new ArrayList();
            List totalList = new ArrayList();
            for (ParkingNewVo parkingNewVo : parkingList){
                BaseCommonVo usedVo = new BaseCommonVo();
                usedVo.setName(parkingNewVo.getPname());
                usedVo.setValue(parkingNewVo.getTotal()!=null?Integer.valueOf(parkingNewVo.getTotal()):0);
                usedList.add(usedVo);
                BaseCommonVo surplusVo = new BaseCommonVo();
                surplusVo.setName(parkingNewVo.getPname());
                surplusVo.setValue(parkingNewVo.getTotalParking()!=null?Integer.valueOf(parkingNewVo.getTotalParking()):0);
                surplusList.add(surplusVo);
                BaseCommonVo totalVo = new BaseCommonVo();
                totalVo.setName(parkingNewVo.getPname());
                totalVo.setValue(parkingNewVo.getMaxParkSpace()!=null?Integer.valueOf(parkingNewVo.getMaxParkSpace()):0);
                totalList.add(totalVo);
            }
            resMap.put("used", total);
            resMap.put("surplus", surplusPaking);
            resMap.put("parkingTotal", parkingTotal);
            resMap.put("usedList", usedList);
            resMap.put("surplusList", surplusList);
            resMap.put("totalList", totalList);
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        }catch (Exception e) {
            logger.error("景区停车场使用情况", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }
    /**
     * 景点拥挤指数
     * @return
     */
    @SysLog("景点拥挤指数")
    @ApiOperation(value = "景点拥挤指数", notes = "景点拥挤指数")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/crowdStatus")
    public Object crowdStatus(String vcode) throws JsonProcessingException{
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        String mdfVcode = DigestUtils.md5Hex(vcode);
        Map<String, Object> resMap = new HashMap<>();
        try {
            String day = DateUtil.getCurDateStr();
            // 查询列表
            resMap =  scenicMonitorService.spotCrowList(vcode,mdfVcode,day);
        }catch (Exception e) {
            List<Map> list1 = new ArrayList<>();
            resMap.put("spotList", list1);

            List<Map> loadNameList = new ArrayList<>();
            Map loadName = new HashMap();
            loadName.put("name", "负荷度");
            loadName.put("load", 0);
            loadNameList.add(loadName);
            resMap.put("loadNameList", loadNameList);

            resMap.put("rating", "");

            e.printStackTrace();
        }
        return ResponseBuilder.custom().success("success",0).data(resMap).build();
    }
    /**
     * 车载系统
     * @return
     */
    @SysLog("车载系统")
    @ApiOperation(value = "车载系统", notes = "车载系统")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/carStatus")
    public Object carStatus(String vcode, String date) throws JsonProcessingException{
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsBlank(date,"日期不能为空!");
        Map<String, Object> resMap = new HashMap<>();
        try {
            // 查询列表
            resMap =  scenicMonitorService.crowdStatus(date,vcode);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBuilder.custom().success("success",0).data(resMap).build();
    }

    /**
     * 华清宫车载系统
     * @return
     */
    @SysLog("华清宫车载系统")
    @ApiOperation(value = "华清宫车载系统", notes = "华清宫车载系统")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/carStatusHQG")
    public Object carStatusHQG(String vcode, String date) throws JsonProcessingException{
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsBlank(date,"日期不能为空!");
        Map<String, Object> resMap = new HashMap<>();
        try {
            // 查询列表
            resMap =  scenicMonitorService.carStatusHQG(date,vcode);
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        }catch (Exception e) {
            logger.error("华清宫车载系统", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }

    /**
     * 设备运行情况
     * @return
     */
    @SysLog("设备运行情况")
    @ApiOperation(value = "设备运行情况", notes = "设备运行情况-未来七天")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/deviceStatus")
    public Object deviceStatus(String vcode, String date) throws JsonProcessingException{
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsBlank(date,"日期不能为空!");
        Map<String, Object> resMap = new HashMap<>();
        try {
            // 查询数据
            resMap =  scenicMonitorService.deviceStatus(date,vcode);
            // 信息发布数
            Map<String, Object> information =  scenicMonitorService.getInformation(date,vcode);
            // 信息发布列表
            resMap.put("informations", information);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBuilder.custom().success("success",0).data(resMap).build();
    }

    /**
     * 监控运行情况
     * @return
     */
    @SysLog("监控运行情况")
    @ApiOperation(value = "监控运行情况", notes = "监控运行情况")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/monitorStatus")
    public Object monitorStatus(String vcode) throws JsonProcessingException{
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Map<String, Object> resMap = new HashMap<>();
        try {
            // 查询数据
            resMap =  scenicMonitorService.monitorStatus(vcode);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBuilder.custom().success("success",0).data(resMap).build();
    }


    /**
     * 景点实时人数
     * @return
     */
    @SysLog("景点实时人数")
    @ApiOperation(value = "景点实时人数", notes = "景点实时人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "date", value = "日期", required = true, paramType = "String")}
    )
    @RequestMapping(value = "/actualPassenger", method = RequestMethod.GET)
    public Object actualPassenger(String vcode, String date) throws JsonProcessingException{
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsDate(date,"非法的日期参数!");
        List list = new ArrayList();
        try {
            // 查询列表
            list =  scenicMonitorService.actualPassenger(vcode,date);
            Map resMap = new HashMap();
            List<Map> headList = new ArrayList();
            List hourPassengerList = new ArrayList();
            if(list.size() > 0){
                // 取head的list
                List<SpotPassengerVo> passengerHeadVos = (List<SpotPassengerVo>)list.get(0);
                if(passengerHeadVos.size() > 0){
                    for(int k = 0; k < passengerHeadVos.size(); k++){
                        Map headMap = new HashMap();
                        headMap.put("name", passengerHeadVos.get(k).getScenicName());
                        headMap.put("field", "field"+k);
                        headList.add(headMap);
                    }
                }
                // 封装返回数据
                for (int i = 0; i < list.size(); i++){
                    List<SpotPassengerVo> passengerVos = (List<SpotPassengerVo>)list.get(i);
                    // 返回数据的 map
                    Map rowMap = new HashMap();
                    for(int j = 0; j < passengerVos.size(); j++){
                        rowMap.put("field"+j, passengerVos.get(j).getLoadNum());
                    }
                    hourPassengerList.add(rowMap);
                }
            }
            resMap.put("headList", headList);
            resMap.put("hourPassengerList", hourPassengerList);
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }
    /**
     * 景区景点实时人数---景区景点舒适度
     * @return
     */
    @SysLog("景区景点实时人数")
    @ApiOperation(value = "景区景点实时人数---景区景点舒适度", notes = "景区景点实时人数---景区景点舒适度")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/actualPeople")
    public Object actualPeople(String vcode, String date) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsBlank(date,"日期不能为空!");
        List list = new ArrayList();
        vcode = DigestUtils.md5Hex(vcode);
        try {
            // 查询列表
            list =  informationService.actualPassenger(vcode,date);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBuilder.custom().success("success",0).data(list).build();
    }
    /**
     * 景点实时人数--补偿历史方法
     * @return
     */
    @SysLog("景点实时人数--补偿历史方法")
    @ApiOperation(value = "景点实时人数", notes = "景点实时人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始日期", required = true, paramType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束日期", required = true, paramType = "String")}
    )
    @RequestMapping(value = "/actualPassengerHistory", method = RequestMethod.GET)
    public Object actualPassengerHistory(String vcode, String startTime, String endTime) throws JsonProcessingException{
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsDate(startTime,"非法的开始日期参数!");
        Assert.paramIsDate(endTime,"非法的结束日期参数!");
        try {
            scenicMonitorService.getScenicSpotsDataAnalysisTimeAllHistory(vcode, startTime, endTime);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
