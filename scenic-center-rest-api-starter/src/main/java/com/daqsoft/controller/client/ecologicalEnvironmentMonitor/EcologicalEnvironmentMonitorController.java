package com.daqsoft.controller.client.ecologicalEnvironmentMonitor;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.ecologicalEnvironmentMonitor.EcologicalEnvironmentMonitorService;
import com.daqsoft.validator.Assert;
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

import java.util.List;
import java.util.Map;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-28 9:26
 * @Version:
 * @Describe: 生态环境监测
 */
@Api("生态环境监测")
@RestController
@RequestMapping("/rest/ecologicalEnvironmentMonitor")
public class EcologicalEnvironmentMonitorController {

    @Autowired
    private EcologicalEnvironmentMonitorService environmentMonitorService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取景区天气预报
     *
     * @param vcode 景区代码
     * @return
     */
    @SysLog("获取景区天气预报")
    @ApiOperation(value = "天气预报", notes = "获取景区实时天气与未来七天的天气信息")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/weatherForecast")
    public Object getWeatherForecast(String vcode) {
//        System.out.println(1/0);
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Map<String, Object> weatherForecast = environmentMonitorService.getWeatherForecast(vcode);
        return ResponseBuilder.custom().success("success", 0).data(weatherForecast).build();
    }


    /**
     * 获取景区气象预警
     *
     * @param vcode 景区代码
     * @return
     */
    @SysLog("获取景区气象预警")
    @ApiOperation(value = "气象预警", notes = "获取景区气象预警")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/weatherWarning")
    public Object getWeatherWarning(String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Map<String, Object> weatherWarning = environmentMonitorService.getWeatherWarning(vcode);
        return ResponseBuilder.custom().success("success", 0).data(weatherWarning).build();
    }


    /**
     * 获取整点AQI 实况
     *
     * @param vcode 景区代码
     * @return
     */
    @SysLog("获取整点AQI实况")
    @ApiOperation(value = "AQI", notes = "获取整点AQI 实况 过去24小时")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/fqiLive")
    public Object getFQILive(String vcode) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        try {
            Map<String, Object> fqiLive = environmentMonitorService.getFQILive(vcode);
            return ResponseBuilder.custom().success("success", 0).data(fqiLive).build();
        } catch (Exception e){
            logger.error("获取整点AQI实况", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 获取负氧离子指数
     *
     * @param vcode 景区编码
     * @param time  时间
     * @return
     */
    @SysLog("获取负氧离子指数")
    @ApiOperation(value = "负氧离子指数", notes = "获取过去24小时景区负氧离子指数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "time", value = "时间 (整点:格式 2017-07-01 09:00)", required = false, paramType = "String")
    })
    @GetMapping("/negativeOxygenIonIndex")
    public Object getNegativeOxygenIonIndex(String vcode, String time) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Map<String, Object> negativeOxygenIonIndex = environmentMonitorService.getNegativeOxygenIonIndex(vcode, time);
        return ResponseBuilder.custom().success("success", 0).data(negativeOxygenIonIndex).build();
    }

    /**
     * 获取景区整点水文监测
     *
     * @param vcode 景区编码
     * @param time  时间
     * @return
     */
    @SysLog("获取景区整点水文监测")
    @ApiOperation(value = "景区水文监测", notes = "获取景区整点水文监测")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "time", value = "时间 (整点:格式 2017-07-01 09:00)", required = false, paramType = "String")
    })
    @GetMapping("/hydrologicalMonitor")
    public Object getHydrologicalMonitor(String vcode, String time) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Map<String, Object> hydrologicalMonitor = environmentMonitorService.getHydrologicalMonitor(vcode, time);
        List list= (List) hydrologicalMonitor.get("list");
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }


    /**
     * 获取景区雷电监测
     *
     * @param vcode 景区编码
     * @param time  时间
     * @return
     */
    @SysLog("获取景区雷电监测")
    @ApiOperation(value = "景区雷电监测", notes = "获取景区雷电监测")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "time", value = "时间 (整点:格式 2017-07-01 09:00)", required = false, paramType = "String")
    })
    @GetMapping("/thunderstormMonitor")
    public Object getThunderstormMonitor(String vcode, String time) {

        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Map<String, Object> thunderstormMonitor = environmentMonitorService.getThunderstormMonitor(vcode, time);
        List list= (List) thunderstormMonitor.get("list");
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

    /**
     * 获取景区空气湿度
     *
     * @param vcode 景区编码
     * @param time  时间
     * @return
     */
    @SysLog("获取景区空气湿度")
    @ApiOperation(value = "景区空气湿度", notes = "获取过去24小时景区空气湿度")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "time", value = "时间 (整点:格式 2017-07-01 09:00)", required = false, paramType = "String")
    })
    @GetMapping("/airMonitor")
    public Object getAirMonitor(String vcode, String time) {

        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Map<String, Object> airMonitor = environmentMonitorService.getAirMonitor(vcode, time);
        return ResponseBuilder.custom().success("success", 0).data(airMonitor).build();
    }


    /**
     * 获取景区风力风向
     *
     * @param vcode 景区编码
     * @param time  时间
     * @return
     */
    @SysLog("获取景区风力风向")
    @ApiOperation(value = "景区风力风向", notes = "获取过去24小时景区风力风向")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "time", value = "时间 (整点:格式 2017-07-01 09:00)", required = false, paramType = "String")
    })
    @GetMapping("/windDirectionMonitor")
    public Object getWindDirectionMonitor(String vcode, String time) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Map<String, Object> windDirectionMonitor = environmentMonitorService.getWindDirectionMonitor(vcode, time);
        return ResponseBuilder.custom().success("success", 0).data(windDirectionMonitor).build();
    }

    /**
     * 获取景区景点天气信息
     *
     * @param vcode 景区编码
     * @param time  时间
     * @return
     */
    @SysLog("获取景区景点天气信息")
    @ApiOperation(value = "景点天气信息", notes = "获取景区景点天气信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "time", value = "时间 (整点:格式 2017-07-01 09:00)", required = false, paramType = "String")
    })
    @GetMapping("/scenicSpotWeather")
    public Object getScenicSpotWeather(String vcode, String time) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Map<String, Object> scenicSpotWeather = environmentMonitorService.getScenicSpotWeather(vcode, time);
        return ResponseBuilder.custom().success("success", 0).data(scenicSpotWeather).build();
    }

    /**
     * 获取景区降水量
     *
     * @param vcode 景区编码
     * @param time  时间
     * @return
     */
    @SysLog("获取景区降水量")
    @ApiOperation(value = "景区降水量", notes = "获取景区过去24小时降水量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "time", value = "时间 (整点:格式 2017-07-01 09:00)", required = false, paramType = "String")
    })
    @GetMapping("/amountOfPrecipitation")
    public Object getAmountOfPrecipitation(String vcode, String time) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        try {
            Map<String, Object> amountOfPrecipitation = environmentMonitorService.getAmountOfPrecipitation(vcode, time);
            return ResponseBuilder.custom().success("success", 0).data(amountOfPrecipitation).build();
        } catch (Exception e){
            logger.error("获取景区降水量", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 获取景区紫外线
     *
     * @param vcode 景区编码
     * @param time  时间
     * @return
     */
    @SysLog("获取景区紫外线")
    @ApiOperation(value = "紫外线", notes = "获取景区过去24小时紫外线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "time", value = "时间 (整点:格式 2017-07-01 09:00)", required = false, paramType = "String")
    })
    @GetMapping("/uv")
    public Object getUV(String vcode, String time) {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Map<String, Object> uv = environmentMonitorService.getUV(vcode, time);
        return ResponseBuilder.custom().success("success", 0).data(uv).build();
    }
}
