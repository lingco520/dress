package com.daqsoft.controller.client.travelResource;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.passengerFlowService.JqScenicMsgService;
import com.daqsoft.service.client.travelResource.TravelResourceService;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.BaseCommonVo;
import com.daqsoft.vo.client.madeVoBean.JqScenicAreas;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author znb
 * on 2017/6/28.
 */
@Api("旅游资源系统")
@RestController
@RequestMapping("/rest/travelResource")
public class TravelResourceController {
    private Logger logger = LoggerFactory.getLogger(TravelResourceController.class);
    @Autowired
    TravelResourceService travelResourceService;
    @Autowired
    private JqScenicMsgService jqScenicMsgService;


    @SysLog("基础资源信息")
    @ApiOperation(value = "基础资源信息", notes = "获取基础资源信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getBasicResource")
    public Object getBasicResource(String vcode){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        try {
            List<JqScenicAreas> regionList = jqScenicMsgService.getJqScenicRegion(vcode);
            String region = "";
            if(regionList.size() > 0){
                String cityRegion = regionList.get(0).getCityRegion();
                String qRegion = regionList.get(0).getRegion();
                if(!"".equals(qRegion) && qRegion!=null){
                    region = qRegion;
                }else if(!"".equals(cityRegion) && cityRegion!=null){
                    region = cityRegion;
                }
            }
            List list =  travelResourceService.getBasicResource(vcode,region);
            Map resMap = new HashMap();
            resMap.put("resourceList", list);
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        } catch (Exception e){
            logger.error("基础资源信息", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }

    @SysLog("多媒体库")
    @ApiOperation(value = "多媒体库", notes = "获取多媒体库信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getMultimedia")
    public Object getMultimedia(String vcode){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        try {
            List list =  travelResourceService.getMultimedia(vcode);
            Map resMap = new HashMap();
            resMap.put("mediaList", list);
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        } catch (Exception e){
            logger.error("多媒体库", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }

    @SysLog("景区设备")
    @ApiOperation(value = "景区设备", notes = "获取景区设备信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getEquipment")
    public Object getEquipment(String vcode){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        try {
            List list =  travelResourceService.getEquipment(vcode);
            Map resMap = new HashMap();
            resMap.put("equipmentList", list);
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        } catch (Exception e){
            logger.error("景区设备", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }

    @SysLog("景区设备详情")
    @ApiOperation(value = "景区设备详情", notes = "获取景区设备详情信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getEquipmentDetail")
    public Object getEquipmentDetail(String vcode,String year){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Assert.paramIsNumber(year,"非法的日期参数");
        List list =  travelResourceService.getEquipmentDetail(vcode,year);
        return ResponseBuilder.custom().success("success",0).data(list).build();
    }

    @SysLog("景区设备损坏详情")
    @ApiOperation(value = "景区设备损坏详情", notes = "获取景区设备详情信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getEquipmentDamage")
    public Object getEquipmentDamage(String vcode,Integer year){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        List list =  travelResourceService.getEquipmentDamage(vcode,year);
        return ResponseBuilder.custom().success("success",0).data(list).build();
    }


    @SysLog("人力资源")
    @ApiOperation(value = "人力资源", notes = "获取景区人力资源信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getHR")
    public Object getHR(String vcode){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        List list =  travelResourceService.getHR(vcode);
        return ResponseBuilder.custom().success("success",0).data(list).build();
    }

    
    @SysLog("人力资源情况")
    @ApiOperation(value = "人力资源情况", notes = "获取人力资源情况信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getHRDetail")
    public Object getHRDetail(String vcode){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        List list =  travelResourceService.getHRDetail(vcode);
        return ResponseBuilder.custom().success("success",0).data(list).build();
    }

    @SysLog("公共资源")
    @ApiOperation(value = "公共资源", notes = "获取公共资源详情信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getPublicResource")
    public Object getPublicResource(String vcode){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        List list =  travelResourceService.getPublicResource(vcode);
        return ResponseBuilder.custom().success("success",0).data(list).build();
    }

    @SysLog("公共资源情况")
    @ApiOperation(value = "公共资源情况", notes = "获取公共资源情况信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getPublicDetail")
    public Object getPublicDetail(String vcode){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        try {
            List<Map> list =  travelResourceService.getPublicDetail(vcode);
            Map resMap = new HashMap();
            List damageList = new ArrayList();
            List damageRatioList = new ArrayList();
            List newaddList = new ArrayList();
            List totalList = new ArrayList();
            for (Map obj : list){
                BaseCommonVo damageVo = new BaseCommonVo();
                damageVo.setName(obj.get("year"));
                damageVo.setValue(obj.get("damage"));
                damageList.add(damageVo);
                BaseCommonVo ratioVo = new BaseCommonVo();
                ratioVo.setName(obj.get("year"));
                if(!StringUtils.isEmpty(obj.get("damage")) && Integer.valueOf(obj.get("damage").toString()).compareTo(0) != 0 && !StringUtils.isEmpty(obj.get("total")) && Integer.valueOf(obj.get("total").toString()).compareTo(0) != 0){
                    ratioVo.setValue(new BigDecimal(obj.get("damage").toString()).divide(new BigDecimal(obj.get("total").toString()),2, BigDecimal.ROUND_HALF_UP)+"%");
                }else{
                    ratioVo.setValue("0%");
                }

                damageRatioList.add(ratioVo);
                BaseCommonVo newaddVo = new BaseCommonVo();
                newaddVo.setName(obj.get("year"));
                newaddVo.setValue(obj.get("newadd"));
                newaddList.add(newaddVo);
                BaseCommonVo totalVo = new BaseCommonVo();
                totalVo.setName(obj.get("year"));
                totalVo.setValue(obj.get("total"));
                totalList.add(totalVo);
            }
            resMap.put("damageList", damageList);
            resMap.put("damageRatioList", damageRatioList);
            resMap.put("newaddList", newaddList);
            resMap.put("totalList", totalList);
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        } catch (Exception e){
            logger.error("公共资源情况", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }

    @SysLog("应急资源-应急指挥子系统数据")
    @ApiOperation(value = "应急资源-应急指挥子系统数据", notes = "获取应急资源信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getEmergenciesResource")
    public Object getEmergenciesResource(String vcode){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Map resMap =  travelResourceService.getEmergenciesResource(vcode);
        return ResponseBuilder.custom().success("success",0).data(resMap).build();
    }

    @SysLog("应急资源-后台资源管理")
    @ApiOperation(value = "应急资源-后台资源管理", notes = "获取后台资源管理")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getEmergencyGoods")
    public Object getEmergencyGoods(String vcode){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        Map resMap =  travelResourceService.getEmergencyGoods(vcode);
        return ResponseBuilder.custom().success("success",0).data(resMap).build();
    }


    @SysLog("员工统计（年龄、学历、工龄）")
    @ApiOperation(value = "员工统计（年龄、学历、工龄）", notes = "获取景区人力资源员工信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getStaffInfo")
    public Object getStaffInfo(String vcode){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        try {
            Map map =  travelResourceService.getStaffInfo(vcode);
            Map resMap = new HashMap();
            List educationList = new ArrayList();
            List workTimeList = new ArrayList();
            List ageList = new ArrayList();
            if(map != null){
                if(map.get("education") != null){
                    List<Map> educations = (List)map.get("education");
                    for (Map educationMap : educations){
                        BaseCommonVo baseCommonVo = new BaseCommonVo();
                        baseCommonVo.setName(educationMap.get("type"));
                        baseCommonVo.setValue(educationMap.get("num"));
                        educationList.add(baseCommonVo);
                    }
                }
                if(map.get("workTime") != null){
                    List<Map> workTimes = (List)map.get("workTime");
                    for (Map workTimeMap : workTimes){
                        BaseCommonVo baseCommonVo = new BaseCommonVo();
                        baseCommonVo.setName(workTimeMap.get("type"));
                        baseCommonVo.setValue(workTimeMap.get("num"));
                        workTimeList.add(baseCommonVo);
                    }
                }
                if(map.get("age") != null){
                    List<Map> ages = (List)map.get("age");
                    for (Map ageMap : ages){
                        BaseCommonVo baseCommonVo = new BaseCommonVo();
                        baseCommonVo.setName(ageMap.get("type"));
                        baseCommonVo.setValue(ageMap.get("num"));
                        ageList.add(baseCommonVo);
                    }
                }
            }
            resMap.put("educationList", educationList);
            resMap.put("workTimeList", workTimeList);
            resMap.put("ageList", ageList);
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        } catch (Exception e){
            logger.error("员工统计", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }

    @SysLog("部门人数统")
    @ApiOperation(value = "部门人数统", notes = "获取景区人力资源部门人数统")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/getDepartmentInfo")
    public Object getDepartmentInfo(String vcode){
        Assert.paramIsBlank(vcode,"景区编码不能为空!");
        try {
            List<Map> list =  travelResourceService.getDepartmentInfo(vcode);
            Map resMap = new HashMap();
            List departmentList = new ArrayList();
            for (Map map : list){
                BaseCommonVo baseCommonVo = new BaseCommonVo();
                baseCommonVo.setName(map.get("name"));
                baseCommonVo.setValue(map.get("num"));
                departmentList.add(baseCommonVo);
            }
            resMap.put("departmentList", departmentList);
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        } catch (Exception e){
            logger.error("部门人数统计", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }



}
