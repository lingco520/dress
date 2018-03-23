package com.daqsoft.controller.client.information;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.forecastwarning.ScenicMonitorService;
import com.daqsoft.service.client.informationService.InformationService;
import com.daqsoft.utils.client.Page;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.JqInformationContent;
import com.daqsoft.vo.client.madeVoBean.ParkingVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tanggm
 * @version V3.1.0
 * @description 信息发布
 * @date 2017-06-23 14:40
 */
@Api("信息发布")
@RestController
@RequestMapping(value = "/rest/information")
public class InformationController {
    @Autowired
    private InformationService informationService;
    @Autowired
    private ScenicMonitorService scenicMonitorService;
    Logger LOGGER = LoggerFactory.getLogger(InformationController.class);
    /**
     * 景区景点实时人数---景区景点舒适度
     * @return
     */
    @SysLog("景区景点实时人数")
    @ApiOperation(value = "景区景点实时人数---景区景点舒适度", notes = "景区景点实时人数---景区景点舒适度")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/actualPeople", method = RequestMethod.GET)
    public Object actualPeople(String vcode, String date) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsBlank(date,"日期不能为空!");
        List list = new ArrayList();
        try {
            // 查询列表
            list =  informationService.actualPassenger(vcode,date);
        }catch (Exception e) {
            LOGGER.error("景区景点实时人数", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success",0).data(list).build();
    }
    /**
     * 景区官网视频
     * @return
     */
    @SysLog("景区官网视频")
    @ApiOperation(value = "景区官网视频", notes = "景区官网视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页数据", required = true, paramType = "String")
    })
    @RequestMapping(value = "/video", method = RequestMethod.GET)
    public Object video(String vcode, String pageNum, String pageSize) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsNumber(pageNum, "非法的分页参数");
        Assert.paramIsNumber(pageSize, "非法的分页参数");
        List list = new ArrayList();
        int count = 0;
        Page page = null;
        try {
            Map map =new HashMap();
            map.put("vcode",vcode);
            count = informationService.getVideoCount(map);
            page = Page.newPage(Integer.parseInt(pageSize), pageNum, count);
            map.put("startRow",page.getStartRow());
            map.put("endRow",page.getEndRow());
            // 查询列表
            list =  informationService.getVideo(map);
        }catch (Exception e) {
            LOGGER.error("景区官网视频", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(list).totalCount(count).
                pageSize(page.getpageSize()).totalPage(page.getTotalPage()).currPage(page.getCurrentPage()).build();
    }
    /**
     * 景点新闻
     * @return
     */
    @SysLog("景点新闻")
    @ApiOperation(value = "景点新闻", notes = "景点新闻")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/scenicNews", method = RequestMethod.GET)
    public Object scenicNews(String vcode, String name, String ditchName) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        List list = new ArrayList();
        try {
            // 查询列表
            Map<String, Object> parMap = new HashMap<>();
            parMap.put("vcode", vcode);
            parMap.put("name", name);
            parMap.put("ditchName", ditchName);
            list =  informationService.getScenicNews(parMap);
        }catch (Exception e) {
            LOGGER.error("景点新闻", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success",0).data(list).build();
    }
    /**
     * 景区二维码
     * @return
     */
    @SysLog("景区二维码")
    @ApiOperation(value = "景区二维码", notes = "景区二维码")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/scenicQrcode", method = RequestMethod.GET)
    public Object scenicQrcode(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        List list = new ArrayList();
        try {
            // 查询列表
            Map<String, Object> parMap = new HashMap<>();
            parMap.put("vcode", vcode);
            list =  informationService.getScenicQrcode(parMap);
        }catch (Exception e) {
            LOGGER.error("景区二维码", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success",0).data(list).build();
    }
    /**
     * 首页值班人员情况
     *
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("景区值班人员")
    @ApiOperation(value = "景区值班人员", notes = "景区值班人员")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/getDuty", method = RequestMethod.GET)
    public Object getDutypersonnel(String vcode) {
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        List<Map> dutypersonnel = new ArrayList<>();
        try {
            // 查询列表
            dutypersonnel = informationService.getDutypersonnel(vcode);
            Map resMap = new HashMap();
            Map map = new HashMap();
            if(dutypersonnel.size() > 0){
                map.put("name", dutypersonnel.get(0).get("name"));
                map.put("value", dutypersonnel.get(0).get("phone"));
            }
            resMap.put("duty", map);
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        }catch (Exception e) {
            LOGGER.error("景区值班人员", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }
    /**
     * 景区停车场使用情况
     * @return
     */
    @SysLog("景区停车场使用情况")
    @ApiOperation(value = "景区停车场使用情况", notes = "景区停车场使用情况")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @RequestMapping(value = "/parkings")
    public Object parkingStatus(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        // 查询列表
        List<ParkingVo> parkingList = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String nowdate = format.format(new Date());
            // 查询
            parkingList =  scenicMonitorService.parkingList(nowdate, vcode);
        }catch (Exception e) {
            LOGGER.error("景区停车场使用情况", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success",0).data(parkingList).build();
    }
    /**
     * 景区信息发布客户端公告添加
     * @return
     */
    @SysLog("景区信息发布客户端公告添加")
    @ApiOperation(value = "景区信息发布客户端公告添加", notes = "景区信息发布客户端公告添加")
    @RequestMapping(value = "/addInformation", method = RequestMethod.POST)
    public Object addInformation(@RequestParam Map<String, String> strTest) throws JsonProcessingException {
        Map<String,Object> resMap = new HashMap<>();
        int i = 0;
        try{
            JSONObject jsonobject = JSONObject.fromObject(strTest.get("params"));
            JqInformationContent jqInformationContent = (JqInformationContent)JSONObject.toBean(jsonobject,JqInformationContent.class);
            Assert.paramIsBlank(jqInformationContent.getDitchId().toString(),"渠道不能为空!");
            Assert.paramIsBlank(jqInformationContent.getPlatformId().toString(),"平台不能为空!");
            Assert.paramIsBlank(jqInformationContent.getTitle(),"标题不能为空!");
            Assert.paramIsBlank(jqInformationContent.getPublishTime().toString(),"发布时间不能为空!");
            Assert.paramIsBlank(jqInformationContent.getContent(),"内容不能为空!");
            Assert.paramIsBlank(jqInformationContent.getVcode(),"景区编码不能为空!");
            jqInformationContent.setStatus(1);
            jqInformationContent.setState(1);
            i = informationService.saveInformation(jqInformationContent);
        }catch(Exception e){
            resMap.put("result", false);
            LOGGER.error("景区信息发布客户端公告添加", e);
        }
        if(i > 0){
            resMap.put("result", true);
        }else{
            resMap.put("result", false);
        }
        return ResponseBuilder.custom().success("success",0).data(resMap).build();
    }
    /**
     * 景区信息发布客户端平台渠道列表
     * @return
     */
    @SysLog("景区信息发布客户端平台渠道列表")
    @ApiOperation(value = "景区信息发布客户端平台渠道列表", notes = "景区信息发布客户端平台渠道列表")
    @RequestMapping(value = "/platformDitchList", method = RequestMethod.GET)
    public Object platformDitchList(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Map<String,Object> resMap = new HashMap<>();
        try {
            List<Map<String,Object>> platformList = informationService.getPlatformList(vcode);
            // 查询渠道列表
            List<Map<String,Object>> ditchList = new ArrayList<>();
            if(platformList.size() > 0){
                Map<String, Object> parMap = new HashMap<>();
                parMap.put("vcode", vcode);
                parMap.put("platformid", platformList.get(0).get("id"));
                ditchList = informationService.getDitchListByPlatformId(parMap);
            }
            resMap.put("platformList",platformList);
            resMap.put("ditchList",ditchList);
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        } catch (Exception e){
            LOGGER.error("景区信息发布客户端平台渠道列表", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }
    /**
     * 景区信息发布客户端根据平台id查询渠道列表
     * @return
     */
    @SysLog("景区信息发布客户端根据平台id查询渠道列表")
    @ApiOperation(value = "景区信息发布客户端根据平台id查询渠道列表", notes = "景区信息发布客户端根据平台id查询渠道列表")
    @RequestMapping(value = "/platformDitchListByPid", method = RequestMethod.GET)
    public Object platformDitchListByPid(String vcode, Integer platformid) throws JsonProcessingException {
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsBlank((platformid+"").toString(),"平台不能为空!");
        List<Map<String,Object>> ditchList = new ArrayList<>();
        try {
            // 查询渠道列表
            Map<String, Object> parMap = new HashMap<>();
            parMap.put("vcode", vcode);
            parMap.put("platformid", platformid);
            ditchList = informationService.getDitchListByPlatformId(parMap);
        } catch (Exception e) {
            LOGGER.error("景区信息发布客户端根据平台id查询渠道列表", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success",0).data(ditchList).build();
    }
}
