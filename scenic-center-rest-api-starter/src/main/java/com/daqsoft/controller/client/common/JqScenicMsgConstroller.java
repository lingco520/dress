package com.daqsoft.controller.client.common;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.MyProps;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.passengerFlowService.JqScenicMsgService;
import com.daqsoft.service.client.passengerFlowService.PassengerService;
import com.daqsoft.utils.client.HttpRequestUtil;
import com.daqsoft.vo.client.madeVoBean.JqScenicAreas;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
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
 * @date 2017-06-26 18:24
 * @Version:
 * @Describe: 景区信息
 */
@RestController
@RequestMapping("/rest/jqScenic")
@Api("景区信息接口")
public class JqScenicMsgConstroller {

    @Autowired
    private JqScenicMsgService jqScenicMsgService;
    @Autowired
    private PassengerService passengerService;

    @Autowired
    private MyProps myProps;

    private static final Logger LOGGER = LoggerFactory.getLogger(JqScenicMsgConstroller.class);

    @SysLog("获取景区列表")
    @ApiOperation(value = "获取景区列表", notes = "获取景区列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", paramType = "String"),
    })
    @GetMapping("/getJqScenicList")
    public Object getJqScenicList(String vcode) throws JsonProcessingException {
        try {
            List<JqScenicAreas> list = jqScenicMsgService.getJqScenicList(vcode);
            return ResponseBuilder.custom().success("success",0).data(list).build();
        } catch (Exception e){
            LOGGER.error("获取景区列表错误", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }

    @SysLog("获取景区天气")
    @ApiOperation(value = "获取景区天气", notes = "获取景区天气")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", paramType = "String"),
    })
    @GetMapping("/getJqScenicWeather")
    public Object getJqScenicWeather(String vcode) throws JsonProcessingException {
        try {
            // 这个地址暂时在代码中写死，以后有配置文件了，需要踢出去
            String url = "http://weather.service.geeker.com.cn/weather";
            List<JqScenicAreas> list = jqScenicMsgService.getJqScenicRegion(vcode);
            if(list.size() > 0){
                String region = list.get(0).getRegion();
                String respStr = HttpRequestUtil.sendGet(url,"code="+region);
                JSONObject jsonObject = JSONObject.fromObject(respStr);
                // 如果没有查询到，对region做处理，查询到了，就原样返回
                if("0".equals(jsonObject.get("msg"))){
                    // 如果区的region没有查询到天气数据，则将region返回为城市的region，只改变第一条，前端取的是list(0)
                    list.get(0).setRegion(list.get(0).getCityRegion());
                }
            }
            return ResponseBuilder.custom().success("success",0).data(list).build();
        } catch (Exception e){
            LOGGER.error("获取景区天气错误", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }


    @SysLog("天气环保数据")
    @ApiOperation(value = "天气环保数据", notes = "天气环保数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", paramType = "String"),
    })
    @GetMapping("/getScenicWeather")
    public Object getScenicWeather(String vcode) throws JsonProcessingException {
        try {
            // 这个地址暂时在代码中写死，以后有配置文件了，需要踢出去
            String url = "http://weather.service.geeker.com.cn/weather";
            List<JqScenicAreas> list = jqScenicMsgService.getJqScenicRegion(vcode);
            Map<String,Object> resMap = new HashMap<>();
            JSONObject jsonObject = null;
            if(list.size() > 0){
                String region = list.get(0).getRegion();
                String respStr = HttpRequestUtil.sendGet(url,"code="+region);
                jsonObject = JSONObject.fromObject(respStr);
                // 如果没有查询到，对region做处理，查询到了，就原样返回
                if("0".equals(jsonObject.get("msg"))){
                    // 如果区的region没有查询到天气数据，则用市的region去查询数据
                    String cityRegion = list.get(0).getCityRegion();
                    String respCityStr = HttpRequestUtil.sendGet(url,"code="+cityRegion);
                    jsonObject = JSONObject.fromObject(respCityStr);
                }
                //判断是否有API数据没有获取上一级
                Map a = (Map) jsonObject.get("weatherInfo");
                Object obj = a.get("aqi");
                Map d = (Map) obj;
                int judge = d.size();
                if(judge == 0) {
                    String cityRegion = list.get(0).getCityRegion();
                    String respCityStr = HttpRequestUtil.sendGet(url,"code="+cityRegion);
                    jsonObject = JSONObject.fromObject(respCityStr);
                    resMap.put("weatherInfo", jsonObject.get("weatherInfo"));
                }else {
                    resMap.put("weatherInfo", jsonObject.get("weatherInfo"));
                }
            }else{
                // 如果没有数据，则返回空的list,不然前端接口会报接口错误 tanggm
                Map<String, Object> weatherInfoMap = new HashMap<>();
                weatherInfoMap.put("aqi", new ArrayList<>());
                weatherInfoMap.put("daily_forecast", new ArrayList<>());
                weatherInfoMap.put("alarms", new ArrayList<>());
                resMap.put("weatherInfo", weatherInfoMap);
            }
            return ResponseBuilder.custom().success("success",0).data(resMap).build();
        } catch (Exception e){
            LOGGER.error("天气环保数据错误", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }
    @SysLog("云台山天气环保数据定制微件")
    @ApiOperation(value = "天气环保数据", notes = "天气环保数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", paramType = "String"),
    })
    @GetMapping("/getYTSScenicWeather")
    public Object getYTSScenicWeather(String vcode) throws JsonProcessingException {
        try {
            // 因为云台山自己的天气没有对接，所以这里处理4个附近的天气
            // region 从 表sys_region 获取  附近的    410821  云台山       410822   博爱县      410823  武陟县      410825 温县
            // 这个地址暂时在代码中写死，以后有配置文件了，需要踢出去
            Map simulateMap = new HashMap();
            simulateMap.put("vcode",vcode);
            simulateMap.put("key","getYTSScenicWeather");
            Map simulateTour = passengerService.getSimulateTour(simulateMap);
            String[] regionList = (simulateTour.get("simulateTour") + "").split(",");

//        Map regionsMap = myProps.getCircumWeather();
//        String regions = regionsMap.get(vcode) +"";
//        String[] regionList = regions.split(",");
            String url = "http://weather.service.geeker.com.cn/weather";
            List<Map> resList = new ArrayList<>();
            JSONObject jsonObject = null;
            for(String region : regionList){
                Map<String,Object> resMap = new HashMap<>();
                String respCityStr = HttpRequestUtil.sendGet(url,"code="+region);
                jsonObject = JSONObject.fromObject(respCityStr);
                if(jsonObject != null){
                    resMap.put("weatherInfo", jsonObject.get("weatherInfo"));
                }else{
                    // 如果没有数据，则返回空的list,不然前端接口会报接口错误 tanggm
                    Map<String, Object> weatherInfoMap = new HashMap<>();
                    weatherInfoMap.put("aqi", new ArrayList<>());
                    weatherInfoMap.put("daily_forecast", new ArrayList<>());
                    weatherInfoMap.put("alarms", new ArrayList<>());
                    resMap.put("weatherInfo", weatherInfoMap);
                }
                resList.add(resMap);
            }
            return ResponseBuilder.custom().success("success",0).data(resList).build();
        } catch (Exception e){
            LOGGER.error("", e);
            return ResponseBuilder.custom().failed("error",1).build();
        }
    }
}