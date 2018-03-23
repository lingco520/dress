package com.daqsoft.controller.client.gisbased;

import com.daqsoft.commons.responseentity.DataResponse;
import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.gisbasedService.GisBasicService;
import com.daqsoft.utils.client.DateTools;
import com.daqsoft.utils.client.Page;
import com.daqsoft.utils.client.RandomNumberUtils;
import com.daqsoft.validator.Assert;
import com.daqsoft.validator.RRException;
import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.Dining;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author: lyl .
 * @Date: Created in  2017/6/19.
 * @Version:
 * @describe:gis地图基础数据
 */
@Api("gis地图基础数据")
@RestController
@RequestMapping("/rest/GisBasic")
public class GisBasicController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GisBasicService gisBasicService;

    /**
     * @param vcode
     * @Author: superziy .
     * @Date: Created in 15:56 2017/6/21.
     * @Version: 1.0
     * @describe:gis地图配置
     */
    @SysLog("gis地图配置")
    @ApiOperation(value = "gis地图配置", notes = "gis地图配置")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getSysConfig", method = RequestMethod.GET)
    public Object getSysConfig(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List<SysConfigTendVo> sVolist = new LinkedList<>();
        try {
            sVolist = gisBasicService.getSysConfig(vcode);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(sVolist).build();
    }
    /**
     * @param vcode
     * @Author: superziy .
     * @Date: Created in 15:56 2018/8/29.
     * @Version: 1.0
     * @describe:gis地图配置
     */
    @SysLog("gis地图配置")
    @ApiOperation(value = "gis超图配置", notes = "gis超图配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "userplatform", value = "所使用平台", required = true, paramType = "String")
    })
    @RequestMapping(value = "/getSuperMapConfig", method = RequestMethod.GET)
    public Object getSuperMapConfig(String vcode,String userplatform) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Assert.paramIsBlank(userplatform, "请输入景区编码！");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("vcode",vcode);
        paramMap.put("userplatform",userplatform);
        List<SuperMapConfig> sVolist = new LinkedList<>();
        try {
            sVolist = gisBasicService.getSuperMapConfig(paramMap);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(sVolist).build();
    }

    /**
     * gis停车场
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis停车场")
    @ApiOperation(value = "gis停车场", notes = "gis停车场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "地图类别(高德 gd，百度 bd，超图 ct等)", paramType = "String")
    })
    @RequestMapping(value = "/getParking", method = RequestMethod.GET)
    public Object getParking(String vcode, String type) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String longkey = "";
        String latkey = "";
        if(!StringUtils.isEmpty(type)){
            List<String> longLatList = gisBasicService.getLongLatByMapType(type);
            if(longLatList.size() < 1){
                throw new RRException("此类型地图暂不支持",1);
            }
            for(String pkey : longLatList){
                if(pkey.contains("Lat")){
                    latkey = pkey;
                }
                if(pkey.contains("Long")){
                    longkey = pkey;
                }
            }
        }
        List<GisLocationVo> gis = new LinkedList<>();
        try {
            //查询gis地图停车位
            gis = gisBasicService.getParkingByType(vcode, type, longkey, latkey);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 这里为了应急云台山，数据做处理，后期会回复
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
            GisLocationVo gisLocationVo = new GisLocationVo();
            gis.add(gisLocationVo);
            gis.add(gisLocationVo);
        }
        return ResponseBuilder.custom().success("success", 0).data(gis).build();
    }

    /**
     * 停车场已用剩余数据接口
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis停车场")
    @ApiOperation(value = "gis停车场", notes = "gis停车场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "id", value = "停车场Id", required = true, paramType = "String")
    })
    @RequestMapping(value = "/getParkingData", method = RequestMethod.GET)
    public Object getParkingData(String vcode, String id) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Assert.paramIsBlank(id, "请输入停车场id！");
        Map gis = new HashMap();
        try {
            //查询gis地图停车位
            Map map = new HashMap();
            map.put("vcode",vcode);
            map.put("id",id);
            String startTime = DateTools.getCurrentDate() + " 00:00:00";
            String endTime = DateTools.getCurrentDate() + " 23:59:59";
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            gis = gisBasicService.getParkingData(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(gis).build();
    }

    /**
     * gis厕所
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis厕所")
    @ApiOperation(value = "gis厕所", notes = "gis厕所")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "地图类别(高德 gd，百度 bd，超图 ct等)", paramType = "String")
    })
    @RequestMapping(value = "/getToilet", method = RequestMethod.GET)
    public Object getToilet(String vcode, String type) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String longkey = "";
        String latkey = "";
        if(!StringUtils.isEmpty(type)){
            List<String> longLatList = gisBasicService.getLongLatByMapType(type);
            if(longLatList.size() < 1){
                throw new RRException("此类型地图暂不支持",1);
            }
            for(String pkey : longLatList){
                if(pkey.contains("Lat")){
                    latkey = pkey;
                }
                if(pkey.contains("Long")){
                    longkey = pkey;
                }
            }
        }
        List<GisLocationVo> gis = new LinkedList<>();
        try {
            //查询gis地图厕所
            gis = gisBasicService.getToiletByType(vcode, type, longkey, latkey);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 这里为了应急云台山，数据做处理，后期会回复
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)) {
            int oldSize = 53 - gis.size();
            for (int i = 1; i <= oldSize; i++) {
                GisLocationVo gisLocationVo = new GisLocationVo();
                gis.add(gisLocationVo);
            }
        }
        return ResponseBuilder.custom().success("success", 0).data(gis).build();
    }

    /**
     * gis报警亭
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis报警亭")
    @ApiOperation(value = "gis报警亭", notes = "gis报警亭")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getPolice", method = RequestMethod.GET)
    public Object getPolice(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List<GisLocationVo> gis = new LinkedList<>();
        try {
            //查询gis报警亭
            gis = gisBasicService.getPolice(vcode);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(gis).build();

    }

    /**
     * gis餐饮场所
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis餐饮场所")
    @ApiOperation(value = "gis餐饮场所", notes = "gis餐饮场所")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "地图类别(高德 gd，百度 bd，超图 ct等)", paramType = "String")
    })
    @RequestMapping(value = "/getDining", method = RequestMethod.GET)
    public Object getDining(String vcode, String type) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String longkey = "";
        String latkey = "";
        if(!StringUtils.isEmpty(type)){
            List<String> longLatList = gisBasicService.getLongLatByMapType(type);
            if(longLatList.size() < 1){
                throw new RRException("此类型地图暂不支持",1);
            }
            for(String pkey : longLatList){
                if(pkey.contains("Lat")){
                    latkey = pkey;
                }
                if(pkey.contains("Long")){
                    longkey = pkey;
                }
            }
        }
        List<Dining> gis = new LinkedList<>();
        try {
            //查询gis报警亭
            gis = gisBasicService.getDiningByType(vcode, type, longkey, latkey);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 这里为了应急云台山，数据做处理，后期会回复
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)) {
            int oldSize = 246 - gis.size();
            for (int i = 1; i <= oldSize; i++) {
                Dining dining = new Dining();
                gis.add(dining);
            }
        }
        return ResponseBuilder.custom().success("success", 0).data(gis).build();

    }

    /**
     * gis医疗点
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis医疗点")
    @ApiOperation(value = "gis医疗点", notes = "gis医疗点")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getMedical", method = RequestMethod.GET)
    public Object getMedical(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List<GisLocationVo> gis = new LinkedList<>();
        try {
            //查询gis医疗点
            gis = gisBasicService.getMedical(vcode);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(gis).build();

    }

    /**
     * gis监控
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis监控")
    @ApiOperation(value = "gis监控", notes = "gis监控")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "size", value = "展示条数", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "地图类别(高德 gd，百度 bd，超图 ct等)", paramType = "String")
    })
    @RequestMapping(value = "/getMonitor", method = RequestMethod.GET)
    public Object getMonitor(String vcode, String size, String type) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String longkey = "";
        String latkey = "";
        if(!StringUtils.isEmpty(type)){
            List<String> longLatList = gisBasicService.getLongLatByMapType(type);
            if(longLatList.size() < 1){
                throw new RRException("此类型地图暂不支持",1);
            }
            for(String pkey : longLatList){
                if(pkey.contains("Lat")){
                    latkey = pkey;
                }
                if(pkey.contains("Long")){
                    longkey = pkey;
                }
            }
        }
        List<GisLocationVo> gis = new LinkedList<>();
        try {
            //查询gis监控
            gis = gisBasicService.getMonitorByType(vcode,size, type, longkey, latkey);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
            int oldSize = 396-gis.size();
            for(int i=1;i<=oldSize;i++){
                GisLocationVo gisLocationVo = new GisLocationVo();
                gis.add(gisLocationVo);
            }
        }
        return ResponseBuilder.custom().success("success", 0).data(gis).build();

    }

    /**
     * gis景点
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis景点")
    @ApiOperation(value = "gis景点", notes = "gis景点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "地图类别(高德 gd，百度 bd，超图 ct等)", paramType = "String")
    })
    @RequestMapping(value = "/getScenic", method = RequestMethod.GET)
    public Object getScenic(String vcode, String type) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String longkey = "";
        String latkey = "";
        if(!StringUtils.isEmpty(type)){
            List<String> longLatList = gisBasicService.getLongLatByMapType(type);
            if(longLatList.size() < 1){
                throw new RRException("此类型地图暂不支持",1);
            }
            for(String pkey : longLatList){
                if(pkey.contains("Lat")){
                    latkey = pkey;
                }
                if(pkey.contains("Long")){
                    longkey = pkey;
                }
            }
        }
        List<GisSourceGpsVo> gis = new LinkedList<>();
        try {
            gis = gisBasicService.getScenicByType(vcode, type, longkey, latkey);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
            int oldSize = 11-gis.size();
            for(int i=1;i<=oldSize;i++){
                GisSourceGpsVo gisSourceGpsVo = new GisSourceGpsVo();
                gis.add(gisSourceGpsVo);
            }
        }
        return ResponseBuilder.custom().success("success", 0).data(gis).build();

    }

    /**
     * gis寺庙
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis寺庙")
    @ApiOperation(value = "gis寺庙", notes = "gis寺庙")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getTemple", method = RequestMethod.GET)
    public Object getTemple(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List<GisLocationVo> gis = new LinkedList<>();
        try {
            //查询gis监控
            gis = gisBasicService.getTemple(vcode);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(gis).build();

    }

    /**
     * gis地图显示娱乐场所
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis娱乐场所")
    @ApiOperation(value = "gis娱乐场所", notes = "gis娱乐场所")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "地图类别(高德 gd，百度 bd，超图 ct等)", paramType = "String")
    })
    @RequestMapping(value = "/getEntertainment", method = RequestMethod.GET)
    public Object getEntertainment(String vcode, String type) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String longkey = "";
        String latkey = "";
        if(!StringUtils.isEmpty(type)){
            List<String> longLatList = gisBasicService.getLongLatByMapType(type);
            if(longLatList.size() < 1){
                throw new RRException("此类型地图暂不支持",1);
            }
            for(String pkey : longLatList){
                if(pkey.contains("Lat")){
                    latkey = pkey;
                }
                if(pkey.contains("Long")){
                    longkey = pkey;
                }
            }
        }
        List<GisHotelVo> gis = new LinkedList<>();
        try {
            //查询gis娱乐场所
            gis = gisBasicService.getEntertainmentByType(vcode, type, longkey, latkey);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 这里为了应急云台山，数据做处理，后期会回复
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)) {
            if (gis.size() > 1) {
                gis = new LinkedList<>();
                GisHotelVo gisHotelVo = new GisHotelVo();
                gis.add(gisHotelVo);
            }
        }
        return ResponseBuilder.custom().success("success", 0).data(gis).build();

    }


    /**
     * gis地图显示购物场所
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis购物场所")
    @ApiOperation(value = "gis购物场所", notes = "gis购物场所")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getShop", method = RequestMethod.GET)
    public Object getShop(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List<GisHotelVo> gis = new LinkedList<>();
        try {
            //查询gis娱乐场所
            gis = gisBasicService.getShop(vcode);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(gis).build();

    }

    /**
     * gis 中查看宾馆饭店
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis宾馆饭店")
    @ApiOperation(value = "gis宾馆饭店", notes = "gis宾馆饭店")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "地图类别(高德 gd，百度 bd，超图 ct等)", paramType = "String")
    })
    @RequestMapping(value = "/getGisHotel", method = RequestMethod.GET)
    public Object getGisHotel(String vcode, String type) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String longkey = "";
        String latkey = "";
        if(!StringUtils.isEmpty(type)){
            List<String> longLatList = gisBasicService.getLongLatByMapType(type);
            if(longLatList.size() < 1){
                throw new RRException("此类型地图暂不支持",1);
            }
            for(String pkey : longLatList){
                if(pkey.contains("Lat")){
                    latkey = pkey;
                }
                if(pkey.contains("Long")){
                    longkey = pkey;
                }
            }
        }
        List<GisHotelVo> hotelList = new LinkedList<>();
        try {
            //查询gis中宾馆饭店
            hotelList = gisBasicService.getGisHotelByType(vcode, type, longkey, latkey);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 这里为了应急云台山，数据做处理，后期会回复
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)) {
            // 这里为了云台山应急，暂时模拟死数据
            int oldSize = 568 - hotelList.size();
            for (int i = 1; i <= oldSize; i++) {
                GisHotelVo gisHotelVo = new GisHotelVo();
                hotelList.add(gisHotelVo);
            }
        }
        return ResponseBuilder.custom().success("success", 0).data(hotelList).build();

    }

    /**
     * gis地图景区旅游资源统计
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis旅游资源统计")
    @ApiOperation(value = "gis旅游资源统计", notes = "gis旅游资源统计")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getSourceStatistics", method = RequestMethod.GET)
    public String getSourceStatistics(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        DataResponse dataResponse = new DataResponse();
        List<GisSourcestatiVo> sourcestatiVos = new LinkedList<>();
        try {
            //查询景区资源统计
            sourcestatiVos = gisBasicService.getSourceStatistics(vcode);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        dataResponse.setData(sourcestatiVos);
        dataResponse.setResponseTime(System.currentTimeMillis());
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(dataResponse);
        return data;
    }
    /**
     * gis停车场详情（使用情况）
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis停车场详情")
    @ApiOperation(value = "gis停车场详情", notes = "gis停车场详情")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/scenicParkingInfo", method = RequestMethod.GET)
    public Object getSourceStatistics(String vcode, String date, String id) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Assert.paramIsBlank(date, "请输入日期");
        Assert.paramIsBlank(id, "请输入停车场id");
        Map parkingInfo = new HashMap<>();
        try {
            //查询景区资源统计
            Map map = new HashMap<>();
            map.put("vcode", vcode);
            map.put("startTime", date+" 00:00:00");
            map.put("endTime", date+" 23:59:59");
            map.put("id", id);
            parkingInfo = gisBasicService.getParkingUseInfo(map);
            if(parkingInfo == null){
                parkingInfo = new HashMap<>();
                parkingInfo.put("used", "0");
                parkingInfo.put("noUsed", "0");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(parkingInfo).build();
    }
    /**
     * 监控详情
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("监控详情")
    @ApiOperation(value = "监控详情", notes = "监控详情")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getMonitorById", method = RequestMethod.GET)
    public Object getMonitorById(String vcode, Long id) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Assert.paramIsBlank(id+"", "请输入监控id");
        MonitorVo monitorVo = new MonitorVo();
        try {
            //查询景区资源统计
            monitorVo = gisBasicService.getMonitorById(vcode,id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(monitorVo).build();
    }

    /**
     * 车辆管理（华清宫）
     * 预留，只做展示 功能再说
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("车辆管理")
    @ApiOperation(value = "车辆管理", notes = "车辆管理")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getCarManage", method = RequestMethod.GET)
    public Object getCarManage(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");

        List<Map> list = new ArrayList<>();
        for(int i =0; i<20; i++){
            Map map = new HashMap();
            map.put("num",i);
            list.add(map);
        }

        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

    /**
     * 旅行社（华清宫）
     * 预留，只做展示 功能再说
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("旅行社")
    @ApiOperation(value = "旅行社", notes = "旅行社")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getTravelAgency", method = RequestMethod.GET)
    public Object getTravelAgency(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List<Map> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("num",1);
        list.add(map);
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }


    /**
     * GIS地图资源模块选择
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("GIS地图资源模块选择")
    @ApiOperation(value = "GIS地图资源模块选择", notes = "GIS地图资源模块选择")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "地图类型", required = true, paramType = "String")
    })
    @RequestMapping(value = "/getGISResources", method = RequestMethod.GET)
    public Object getGISResources(String vcode, String type) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List<Map> data = new ArrayList<>();
        try {
            //GIS地图资源模块选择
            if(type == null || "".equals(type)) {
                type = "1";
            }
//            data = gisBasicService.getGISResources(vcode);
            data = gisBasicService.getGISResources(vcode,type);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }

    /**
     * 华清宫运行监测地图巡检人员定位
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("华清宫运行监测地图巡检人员定位")
    @ApiOperation(value = "华清宫运行监测地图巡检人员定位", notes = "华清宫运行监测地图巡检人员定位")
    @RequestMapping(value = "/getGPSLocationHQG", method = RequestMethod.GET)
    public Object getGPSLocationHQG() throws JsonProcessingException {
        List<Map> data = new ArrayList<>();
        try {
            //GIS地图资源模块选择
            data = gisBasicService.getGPSLocationHQG();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }

    /**
     * 华清宫运行监测地图报警事件
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("华清宫运行监测地图报警事件")
    @ApiOperation(value = "华清宫运行监测地图报警事件", notes = "华清宫运行监测地图报警事件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, paramType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "条数", required = true, paramType = "String")
    })
    @RequestMapping(value = "/getGPSEventHQG", method = RequestMethod.GET)
    public Object getGPSEventHQG(int page, int pageSize) throws JsonProcessingException {
        Map data = new HashMap();
        try {
            //GIS地图资源模块选择
            data = gisBasicService.getGPSEventHQG(pageSize, page);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        int count = (int)data.get("count");
        Page pager = Page.newPage(pageSize,page + "", count);
        return ResponseBuilder.custom().success("success", 0).data(data.get("list")).totalCount(count).
                pageSize(pager.getpageSize()).totalPage(pager.getTotalPage()).currPage(pager.getCurrentPage()).build();
    }

    /**
     * 华清宫运行监测地图报警装置位置
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("华清宫运行监测地图报警装置位置")
    @ApiOperation(value = "华清宫运行监测地图报警装置位置", notes = "华清宫运行监测地图报警装置位置")
    @RequestMapping(value = "/getInfostakeHQG", method = RequestMethod.GET)
    public Object getInfostakeHQG() throws JsonProcessingException {
        List<Map> data = new ArrayList<>();
        try {
            //GIS地图资源模块选择
            data = gisBasicService.getInfostakeHQG();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }

    /**
     * 华清宫运行监测地图重点监控
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("华清宫运行监测地图重点监控")
    @ApiOperation(value = "华清宫运行监测地图重点监控", notes = "华清宫运行监测地图重点监控")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "地图类别(高德 gd，百度 bd，超图 ct等)", paramType = "String")
    })
    @RequestMapping(value = "/getMonitorHQG", method = RequestMethod.GET)
    public Object getMonitorHQG(String vcode, String type) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String longkey = "";
        String latkey = "";
        if(!StringUtils.isEmpty(type)){
            List<String> longLatList = gisBasicService.getLongLatByMapType(type);
            if(longLatList.size() < 1){
                throw new RRException("此类型地图暂不支持",1);
            }
            for(String pkey : longLatList){
                if(pkey.contains("Lat")){
                    latkey = pkey;
                }
                if(pkey.contains("Long")){
                    longkey = pkey;
                }
            }
        }
        List<GisLocationVo> data = new ArrayList<>();
        try {
            //GIS地图资源模块选择
            data = gisBasicService.getMonitorHQGByType(vcode, type, longkey, latkey);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }


    /**
     * 气象站
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("气象站")
    @ApiOperation(value = "气象站", notes = "气象站")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getMeteorological", method = RequestMethod.GET)
    public Object getMeteorological(String vcode) throws JsonProcessingException {
        List<Map> data = new ArrayList<>();
//        try {
//            //GIS地图资源模块选择
//            data = gisBasicService.getMonitorHQG(vcode);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }

    /**
     * 水文监测
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("水文监测")
    @ApiOperation(value = "水文监测", notes = "水文监测")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getHydrologic", method = RequestMethod.GET)
    public Object getHydrologic(String vcode) throws JsonProcessingException {
        List<Map> data = new ArrayList<>();
//        try {
//            //GIS地图资源模块选择
//            data = gisBasicService.getMonitorHQG(vcode);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }

    /**
     * 文物监测
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("文物监测")
    @ApiOperation(value = "文物监测", notes = "文物监测")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getAntique", method = RequestMethod.GET)
    public Object getAntique(String vcode) throws JsonProcessingException {
        List<Map> data = new ArrayList<>();
//        try {
//            //GIS地图资源模块选择
//            data = gisBasicService.getMonitorHQG(vcode);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }

    /**
     * 地质监测
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("地质监测")
    @ApiOperation(value = "地质监测", notes = "地质监测")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getGeology", method = RequestMethod.GET)
    public Object getGeology(String vcode) throws JsonProcessingException {
        List<Map> data = new ArrayList<>();
//        try {
//            //GIS地图资源模块选择
//            data = gisBasicService.getMonitorHQG(vcode);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }

    /**
     * 防火监测
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("防火监测")
    @ApiOperation(value = "防火监测", notes = "防火监测")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "地图类别(高德 gd，百度 bd，超图 ct等)", paramType = "String")
    })
    @RequestMapping(value = "/getFireproof", method = RequestMethod.GET)
    public Object getFireproof(String vcode, String type) throws JsonProcessingException {
        List<GisLocationVo> data = new ArrayList<>();
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String longkey = "";
        String latkey = "";
        if(!StringUtils.isEmpty(type)){
            List<String> longLatList = gisBasicService.getLongLatByMapType(type);
            if(longLatList.size() < 1){
                throw new RRException("此类型地图暂不支持",1);
            }
            for(String pkey : longLatList){
                if(pkey.contains("Lat")){
                    latkey = pkey;
                }
                if(pkey.contains("Long")){
                    longkey = pkey;
                }
            }
        }
        try {
            //GIS地图资源模块选择
            data = gisBasicService.getFireproofByType(vcode, type, longkey, latkey);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }

    /**
     * 防火设备
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("防火设备")
    @ApiOperation(value = "防火设备", notes = "防火设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "type", value = "地图类别(高德 gd，百度 bd，超图 ct等)", paramType = "String")
    })
    @RequestMapping(value = "/getFireControl", method = RequestMethod.GET)
    public Object getFireControl(String vcode, String type) throws JsonProcessingException {
        List<GisFireVo> data = new ArrayList<>();
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String longkey = "";
        String latkey = "";
        if(!StringUtils.isEmpty(type)){
            List<String> longLatList = gisBasicService.getLongLatByMapType(type);
            if(longLatList.size() < 1){
                throw new RRException("此类型地图暂不支持",1);
            }
            for(String pkey : longLatList){
                if(pkey.contains("Lat")){
                    latkey = pkey;
                }
                if(pkey.contains("Long")){
                    longkey = pkey;
                }
            }
        }
        try {
            //GIS地图资源模块选择
            data = gisBasicService.getFireControlByType(vcode, type, longkey, latkey);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }
    /**
     * 查询地图类型以及数据库对应经纬度字段的方法
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("查询地图类型以及数据库对应经纬度字段的方法")
    @ApiOperation(value = "查询地图类型以及数据库字段", notes = "查询地图类型以及数据库字段")
    @RequestMapping(value = "/getMapType", method = RequestMethod.GET)
    public Object getMapType() throws JsonProcessingException {
        List<Map> data = new ArrayList<>();
        try {
            //GIS地图资源模块选择
            data = gisBasicService.getMapType();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseBuilder.custom().success("error", 1).build();

        }
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }

    /**
     * 景点热度
     *
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("景点热度")
    @ApiOperation(value = "景点热度", notes = "景点热度")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getScenicHot", method = RequestMethod.GET)
    public Object getScenicHot(String vcode) throws JsonProcessingException {
        List<Map> data = new ArrayList<>();
        return ResponseBuilder.custom().success("success", 0).data(data).build();
    }

    /**
     * @param vcode
     * @Author: tanggm .
     * @Version: 1.0
     * @describe:gis地图配置,微件升级，地图所有信息在一个接口返回
     */
    @SysLog("gis地图配置")
    @ApiOperation(value = "gis地图配置", notes = "gis地图配置")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @CrossOrigin
    @RequestMapping(value = "/getGisMapConfig", method = RequestMethod.GET)
    public Object getGisMapConfig(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        try {
            Map resMap = new HashMap();
            // 地图标题等信息
            Map frame = new HashMap();
            frame.put("title", "GIS地图");
            frame.put("icon", "&#xe69f;");

            // 设置数据返回
            Map content = new HashMap();
            Map mapData = new HashMap();

            List<Map> layersList = gisBasicService.getGisSysConfig(vcode);
            // 因为查询语句，name 不是字段名称，而是 记录值，由于mapper.xml中子查询问题，如果没有数据，mybatis会返回一个数据，list里面值为 All elements are null
            // 返回到接口会出现list里面是一个 null,所以当 list的size为1 时，就给返回一个空的 list,因为地图数据返回很多，真正一个数据也不会渲染出来地图，这里特殊处理
            if("All elements are null".equals(layersList)){
                layersList = new ArrayList<>();
            }
            mapData.put("layers", layersList);

            // 资源列表信息
            List<Map> groupsList = new ArrayList<>();

            // 景区景点
            Map scenicMap = new HashMap();
            scenicMap.put("types", "scenic");
            scenicMap.put("name", "景点");
            List<GisSourceGpsVo> scenicMarkers = gisBasicService.getScenicListByVcode(vcode);
            // 考虑到原来代码涉及业务逻辑，原scdatasvc项目做了特殊判断，这里微件升级，先沿用以前的业务逻辑
            if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
                int oldSize = 11-scenicMarkers.size();
                for(int i=1;i<=oldSize;i++){
                    GisSourceGpsVo gisSourceGpsVo = new GisSourceGpsVo();
                    scenicMarkers.add(gisSourceGpsVo);
                }
            }
            scenicMap.put("markers", scenicMarkers);
            scenicMap.put("count", scenicMarkers.size());
            groupsList.add(scenicMap);

            // 监控
            Map monitorMap = new HashMap();
            monitorMap.put("types", "monitor");
            monitorMap.put("name", "监控");
            List<GisLocationVo> monitorMarkers = gisBasicService.getMonitorListByVcode(vcode);
            if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
                int oldSize = 396-monitorMarkers.size();
                for(int i=1;i<=oldSize;i++){
                    GisLocationVo gisLocationVo = new GisLocationVo();
                    monitorMarkers.add(gisLocationVo);
                }
            }

            monitorMap.put("markers", monitorMarkers);
            monitorMap.put("count", monitorMarkers.size());
            groupsList.add(monitorMap);

            // 停车场
            Map parkingMap = new HashMap();
            parkingMap.put("types", "parking");
            parkingMap.put("name", "停车场");
            List<GisLocationVo> parkingMarkers = gisBasicService.getParkingListByVcode(vcode);
            // 这里为了应急云台山，数据做处理，后期会回复
            if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
                GisLocationVo gisLocationVo = new GisLocationVo();
                parkingMarkers.add(gisLocationVo);
                parkingMarkers.add(gisLocationVo);
            }

            parkingMap.put("markers", parkingMarkers);
            parkingMap.put("count", parkingMarkers.size());
            groupsList.add(parkingMap);

            // 旅行社
            Map tAgencyMap = new HashMap();
            tAgencyMap.put("types", "tAgency");
            tAgencyMap.put("name", "旅行社");
            //
            List<Map> tAgencyMarkers = gisBasicService.getTagencyListByVcode(vcode);
            tAgencyMap.put("markers", tAgencyMarkers);
            tAgencyMap.put("count", tAgencyMarkers.size());
            groupsList.add(tAgencyMap);

            // 车辆管理
            Map vehiclesMap = new HashMap();
            vehiclesMap.put("types", "vehicles");
            vehiclesMap.put("name", "车辆管理");
            // 车辆管理暂时后台没有功能，先按照原来代码，写死20
            double baseLatitude = 35.403287;
            double baseLongitude = 113.431728;
            double[] randDouble = new double[]{0.04523, 0.06412};
            List<Map> vehiclesMarkers = new ArrayList<>();
            for(int i =0; i<20; i++){
                Map vehicleMap = new HashMap();
                vehicleMap.put("latitude", baseLatitude- randDouble[RandomNumberUtils.RandomNumber(1,0)]);
                vehicleMap.put("longitude", baseLongitude- randDouble[RandomNumberUtils.RandomNumber(1,0)]);
                vehicleMap.put("name", "大巴"+i);
                vehicleMap.put("introduction", "注意安全，减速行驶");
                vehicleMap.put("id", i);
                Map map = new HashMap();
                map.put("num",i);
                vehiclesMarkers.add(map);
            }
            vehiclesMap.put("markers", vehiclesMarkers);
            vehiclesMap.put("count", vehiclesMarkers.size());
            groupsList.add(vehiclesMap);

            // 宾馆饭店
            Map hotelMap = new HashMap();
            hotelMap.put("types", "tAgency");
            hotelMap.put("name", "旅行社");

            List<GisHotelVo> hotelMarkers = gisBasicService.getGisHotelListByVcode(vcode);
            // 这里为了应急云台山，数据做处理，后期会回复
            if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)) {
                // 这里为了云台山应急，暂时模拟死数据
                int oldSize = 568 - hotelMarkers.size();
                for (int i = 1; i <= oldSize; i++) {
                    GisHotelVo gisHotelVo = new GisHotelVo();
                    hotelMarkers.add(gisHotelVo);
                }
            }
            hotelMap.put("markers", hotelMarkers);
            hotelMap.put("count", hotelMarkers.size());
            groupsList.add(hotelMap);

            // 餐饮场所
            Map restaurantMap = new HashMap();
            restaurantMap.put("types", "restaurant");
            restaurantMap.put("name", "餐饮场所");

            List<Dining> restaurantMarkers = gisBasicService.getDiningListByVcode(vcode);
            // 这里为了应急云台山，数据做处理，后期会回复
            if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)) {
                int oldSize = 246 - restaurantMarkers.size();
                for (int i = 1; i <= oldSize; i++) {
                    Dining dining = new Dining();
                    restaurantMarkers.add(dining);
                }
            }
            restaurantMap.put("markers", restaurantMarkers);
            restaurantMap.put("count", restaurantMarkers.size());
            groupsList.add(restaurantMap);

            // 购物场所
            Map shoppingMap = new HashMap();
            shoppingMap.put("types", "shopping");
            shoppingMap.put("name", "购物场所");

            List<GisHotelVo> shoppingMarkers = gisBasicService.getShop(vcode);
            shoppingMap.put("markers", shoppingMarkers);
            shoppingMap.put("count", shoppingMarkers.size());
            groupsList.add(shoppingMap);

            // 购物场所
            Map toiletMap = new HashMap();
            toiletMap.put("types", "toilet");
            toiletMap.put("name", "旅游厕所");

            List<GisLocationVo> toiletMarkers = gisBasicService.getToiletListByVcode(vcode);
            // 这里为了应急云台山，数据做处理，后期会回复
            if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)) {
                int oldSize = 53 - toiletMarkers.size();
                for (int i = 1; i <= oldSize; i++) {
                    GisLocationVo gisLocationVo = new GisLocationVo();
                    toiletMarkers.add(gisLocationVo);
                }
            }
            toiletMap.put("markers", toiletMarkers);
            toiletMap.put("count", toiletMarkers.size());
            groupsList.add(toiletMap);

            mapData.put("groups", groupsList);

            content.put("mapData", mapData);

            resMap.put("frame", frame);
            resMap.put("content", content);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e) {
            logger.error("gis地图升级版接口", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }
}
