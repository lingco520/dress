package com.daqsoft.controller.client.forecastwarning;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.forecastwarning.ForecastWarningService;
import com.daqsoft.utils.client.Page;
import com.daqsoft.utils.client.RandomNumberUtils;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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
 * @date 2017-06-22 10:52
 * @Version: v1.0.0
 * @Describe: 预测预警
 */
@Api("预测预警")
@RestController
@RequestMapping("/rest/forecastWarning")
public class ForecastWarningController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ForecastWarningService forecastWarningService;


    /**
     * 客流预测预警-未来七天
     *
     * @param vcode 景区代码
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("获取客流未来七天客流预警")
    @ApiOperation(value = "客流预测预警", notes = "客流预测预警-未来七天")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/passengerFlow")
    public Object getTassengerFlowForecast(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        try {
            //获取景区客流量预测数据
            PassengerFlowVO passengerFlow = forecastWarningService.getPassengerFlow(vcode);
            Map resMap = new HashMap();
            List dataList = new ArrayList();
            if(null != passengerFlow && passengerFlow.getDataList().size() > 0){
                for(RealPeopleRemainVo realPeopleRemainVo : passengerFlow.getDataList()){
                    Map map = new HashMap();
                    map.put("name", realPeopleRemainVo.getTime());
//                    map.put("value", realPeopleRemainVo.getNum());  打包没有数据，先暂时处理
                    map.put("value", RandomNumberUtils.RandomNumber(3000, 2000));
                    dataList.add(map);
                }
            }
            resMap.put("maxquantity", passengerFlow.getMaxquantity());
            resMap.put("dataList", dataList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("获取客流未来七天客流预警", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 票务预测预警-未来七天
     *
     * @param vcode 景区代码
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("票务预测预警-未来六天")
    @ApiOperation(value = "票务预测预警", notes = "票务预测预警-未来七天")
    @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String")
    @GetMapping("/ticketForecast")
    public Object getTicketForecast(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        try {
            TicketForecastVO ticketForecast = forecastWarningService.getTicketForecast(vcode);
            Map resMap = new HashMap();
            List onLineList = new ArrayList();
            List offLineList = new ArrayList();
            if(null != ticketForecast && ticketForecast.getDataList().size() > 0){
                for(TicketForecastIndexOnAndOffVO ticketForecastIndexOnAndOffVO : ticketForecast.getDataList()){
                    BaseCommonVo onLineVo = new BaseCommonVo();
                    onLineVo.setName(ticketForecastIndexOnAndOffVO.getTime());

//                    onLineVo.setValue(ticketForecastIndexOnAndOffVO.getOnLine()!=null?Integer.valueOf(ticketForecastIndexOnAndOffVO.getOnLine()):0);
                    onLineVo.setValue(RandomNumberUtils.RandomNumber(3000,2000));
                    onLineList.add(onLineVo);
                    BaseCommonVo offLineVo = new BaseCommonVo();
                    offLineVo.setName(ticketForecastIndexOnAndOffVO.getTime());
//                    offLineVo.setValue(ticketForecastIndexOnAndOffVO.getOffLine()!=null?Integer.valueOf(ticketForecastIndexOnAndOffVO.getOffLine()):0);
                    offLineVo.setValue(RandomNumberUtils.RandomNumber(2000,1000));
                    offLineList.add(offLineVo);
                }
            }
            resMap.put("maxquantity", ticketForecast.getMaxquantity());
            resMap.put("onLineList", onLineList);
            resMap.put("offLineList", offLineList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("票务预测预警-未来六天", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 节假日预测预警
     *
     * @param vcode 景区代码
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("节假日客流量预测")
    @ApiOperation(value = "节假日客流量预测", notes = "节假日客流量预测-当年")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/holidayForecast")
    public Object getHolidayForecast(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        try {
            PassengerFlowVO passengerFlowVO = forecastWarningService.getHolidayForecast(vcode);
            Map resMap = new HashMap();
            List dataList = new ArrayList();
            if(null != passengerFlowVO && passengerFlowVO.getDataList().size() > 0){
                for(RealPeopleRemainVo realPeopleRemainVo : passengerFlowVO.getDataList()){
                    BaseCommonVo baseCommonVo = new BaseCommonVo();
                    baseCommonVo.setName(realPeopleRemainVo.getTime());
                    baseCommonVo.setValue(realPeopleRemainVo.getNum()!=null?Integer.valueOf(realPeopleRemainVo.getNum()):0);
                    dataList.add(baseCommonVo);
                }
            }
            resMap.put("maxquantity", passengerFlowVO.getMaxquantity());
            resMap.put("dataList", dataList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("节假日客流量预测", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 未来七天团队个数预测
     *
     * @param vcode 景区代码
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("团队个数预测")
    @ApiOperation(value = "团队数预测", notes = "预测未来七天景区的团队数量")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping("/teamForecast")
    public Object getTeamForecast(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        try {
            Map<String, Object> teamFarecast = forecastWarningService.getTeamFarecast(vcode);
            Map resMap = new HashMap();
            List dataList = new ArrayList();
            if (teamFarecast != null && teamFarecast.get("dataList") != null){
                List<RealPeopleRemainVo> list = (List)teamFarecast.get("dataList");
                for (RealPeopleRemainVo realPeopleRemainVo : list){
                    BaseCommonVo baseCommonVo = new BaseCommonVo();
                    baseCommonVo.setName(realPeopleRemainVo.getTime());
                    baseCommonVo.setValue(realPeopleRemainVo.getNum()!=null?Integer.valueOf(realPeopleRemainVo.getNum()):0);
                    dataList.add(baseCommonVo);
                }
            }
            resMap.put("today", teamFarecast.get("today"));
            resMap.put("maxquantity", teamFarecast.get("maxquantity"));
            resMap.put("dataList", dataList);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("团队个数预测", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 道路交通预测预警
     * auth:tanggm
     * date:2017-08-02 16:10
     * desc:景区经纬度获取
     * @param vcode
     * @param pageNum
     * @param pageSize
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("道路交通预测预警")
    @ApiOperation(value = "道路交通预测预警", notes = "道路交通预测预警")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页数据", required = true, paramType = "String"),
    })
    @GetMapping(value = "/getTrafficForecast")
    public Object getTrafficForecast(String vcode, String pageNum, String pageSize, String level)
            throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空");
        Assert.paramIsNumber(pageNum, "非法的分页参数");
        Assert.paramIsNumber(pageSize, "非法的分页参数");
        Map map =new HashMap();
        map.put("vcode",vcode);
        map.put("level",level);
        int  count = forecastWarningService.getTrafficCount(map);
        Page page = Page.newPage(Integer.parseInt(pageSize), pageNum, count);
        map.put("startRow",page.getStartRow());
        map.put("endRow",page.getEndRow());
        List<TrafficWarningVo> list = forecastWarningService.getTrafficForecast(map);
        Map map1 = new HashMap();
        // 地图中心点的定位，定位到景区地址
        map1.put("defaultlongitude", "");
        map1.put("defaultlatitude", "");
        Map<String, Object> maps = forecastWarningService.getMaps(vcode);
        if(maps != null){
            map1.put("defaultlongitude", maps.get("longitude"));
            map1.put("defaultlatitude", maps.get("latitude"));
        }
        return ResponseBuilder.custom().success("success", 0).data(list).totalCount(count).extend(map1).
                pageSize(page.getpageSize()).totalPage(page.getTotalPage()).currPage(page.getCurrentPage()).build();
    }
}
