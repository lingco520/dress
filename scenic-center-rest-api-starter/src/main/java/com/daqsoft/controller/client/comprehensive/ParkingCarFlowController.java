package com.daqsoft.controller.client.comprehensive;

import com.daqsoft.commons.core.StringUtil;
import com.daqsoft.commons.responseentity.DatasResponse;
import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.comprehensiveService.CarFlowStatisticService;
import com.daqsoft.service.client.comprehensiveService.TimelyParkingCarFlowService;
import com.daqsoft.service.client.forecastwarning.ScenicMonitorService;
import com.daqsoft.service.client.trafficAnalysisService.CarFlowWidgetService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.MathUtils;
import com.daqsoft.validator.Assert;
import com.daqsoft.validator.RRException;
import com.daqsoft.validator.ValidateUtil;
import com.daqsoft.vo.client.madeVoBean.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: superziy .
 * @Date: Created in 9:08 2017/6/20.
 * @Version: 1.0
 * @describe: 综合展示页面模块化划分 停车场与停车场类型查询
 */
@RestController
@RequestMapping("/rest/comprehensiveParkingCarFlow")
public class ParkingCarFlowController {
    private Logger logger = LoggerFactory.getLogger(ParkingCarFlowController.class);
    @Autowired
    private TimelyParkingCarFlowService timelyParkingService;
    @Autowired
    private CarFlowStatisticService carFlowStatisticService;
    @Autowired
    private CarFlowWidgetService carFlowService;
    @Autowired
    private ScenicMonitorService scenicMonitorService;

    /**
     * @param vcode   景区vcode
     * @Author: superziy .  /  tanggm / tanggm
     * @Date: Created in 9:18 2017/6/20. / 2017-08-07 / 2017-08-14
     * @Version:1.0
     * @describe:停车场和停车场车辆类型微件 / 之前页面有通过时间范围查询，现在产品这边取消了。
     * 2017-08-14 修改停车场总停车数取值，以前取的是后台值，这个值和研发推送的值不对应，而且后天可以随便维护，导致数据不准确，现在取的实时停车位中的 已使用和未使用之和。
     * 这个总和与研发沟通过，就是停车场的总车位数
     */
    @SysLog("停车场和停车场车辆类型")
    @ApiOperation(value = "停车场和停车场车辆类型", notes = "停车场和停车场车辆类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String")
    })
    @GetMapping("/getParkingCountCarType")
    public Object getParkingCountCarType(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        String date = DateUtil.getCurDateStr();
        List<Map> jqTimelyParkingCarType = new LinkedList<>();
        Map carType = new HashMap();
        Integer total = 0;
        Integer parkingTotal = 0;
        Integer surplusPaking = 0;
        try {
            //查询停车场 总车位、已使用、未使用数量
            List<Map> scenicNames = scenicMonitorService.getScenicNamesList(vcode);
            for (Map rmap : scenicNames){
                Map parMap = new HashMap();
                parMap.put("parkid", rmap.get("id"));
                parMap.put("startTime", date+" 00:00:00");
                parMap.put("endTime", date+" 23:59:59");
                parMap.put("vcode", vcode);
                ParkingVo parkingVo = scenicMonitorService.getParkingInfo(parMap);
                if(parkingVo != null){
                    parkingTotal+=Integer.valueOf(parkingVo.getMaxParkSpace());   // 总停车位
                    surplusPaking+=Integer.valueOf(parkingVo.getTotalParking()); // 剩余
                    total+=Integer.valueOf(parkingVo.getMaxParkSpace())-Integer.valueOf(parkingVo.getTotalParking()); // 已使用
                }
            }
            carType.put("parkingTotal", parkingTotal); // 总数
            carType.put("surplusPaking", surplusPaking); // 剩余
            carType.put("total", total); // 已使用
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            //查询停车场时间段车辆类型
            Map map = new HashMap();
            map.put("vcode", vcode);
            map.put("date",  date);
            jqTimelyParkingCarType = timelyParkingService.getCarTypeParkByDay(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        carType.put("carType", jqTimelyParkingCarType);

        return ResponseBuilder.custom().success("success", 0).data(carType).build();

    }


    /**
     * @param startTime
     * @param endTime
     * @param vcode
     * @Author: superziy .
     * @Date: Created in 10:16 2017/6/20.
     * @Version:
     * @describe:车辆来源地排名前十
     */
    @SysLog("车辆来源地排名前十")
    @ApiOperation(value = "车辆来源地排名前十", notes = "车辆来源地排名前十")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String"),
    })
    @GetMapping("/getCarFromByDay")
    public Object getCarFromByDay(String startTime, String endTime, String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Assert.paramIsDate(startTime, "非法的日期格式！");
        Assert.paramIsDate(endTime, "非法的日期格式！");
        try {
            Map<String, String> map = new HashMap<String, String>();
            String stTime = MathUtils.dateDayStart(startTime);//时间拼串开始
            String edTime = MathUtils.dateDayEnd(endTime);//时间拼串结束
            List<CarFromProvinceVo> carFromRank = new LinkedList<>();
            map.put("startTime", stTime);
            map.put("endTime", edTime);
            map.put("vcode", vcode);
            Integer sumCar = 0;
            Integer car = 0;
            Double percent = 0d;
            List<Map> list = new ArrayList();
            Map resMap = new HashMap();
            carFromRank = timelyParkingService.getCarFromByMonth(map);//排名前十名
            for (CarFromProvinceVo vo : carFromRank) {
                String sum = vo.getSUMCAR();
                sumCar += Integer.valueOf(sum);//车辆总数
            }
            for (CarFromProvinceVo per : carFromRank) {//循环车辆来源
                String num = per.getSUMCAR();
                car = Integer.valueOf(num);
                percent = new BigDecimal(car).divide(new BigDecimal(sumCar),2,RoundingMode.HALF_UP).doubleValue() * 100D;//算省份占比


                String percentCar = MathUtils.percent(String.valueOf(percent));
                Map carMap = new HashMap();
                carMap.put("carFromName", per.getCARFROM());
                carMap.put("carNum", per.getSUMCAR() != null?Integer.valueOf(per.getSUMCAR()):0);
                carMap.put("percent", percentCar + "%");
                list.add(carMap);
            }
            resMap.put("dataList", list);
            return ResponseBuilder.custom().success("success", 0).data(resMap).build();
        } catch (Exception e){
            logger.error("车辆来源地排名前十", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
    }

    /**
     * 时段内车流量的统计
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */

    @SysLog("时段内车流量的统计")
    @ApiOperation(value = "时段内车流量的统计", notes = "时段内车流量的统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String"),
    })
    @GetMapping("/getCarFlow")
    public Object getCarFlow(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
          DatasResponse<CarFlowStatisticVo> datasResponse = new DatasResponse();
        //当前天的实时车流量
        List<CarFlowStatisticVo> carFlowList = carFlowStatisticService.getCarFlow(vcode);
        return ResponseBuilder.custom().success("success", 0).data(carFlowList).build();
    }

    /**
     * 时段内车流量的统计 -- 分卡口
     * @auth yangmei
     * @date 2017-08-11 17:00
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */

    @SysLog("时段内车流量的统计 -- 分卡口")
    @ApiOperation(value = "时段内车流量的统计 -- 分卡口", notes = "时段内车流量的统计 -- 分卡口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "time", value = "日期", required = false, paramType = "String")
    })
    @GetMapping("/getCarFlowCountByGate")
    public Object getCarFlowCountByGate(String vcode,String time) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空！");

        if(!StringUtil.isEmpty(time)){
            if(!ValidateUtil.Validate(time,ValidateUtil.DATE)){
                throw new RRException("日期格式错误,格式为： yyyy-mm-dd 例如：2017-01-01",400);
            }
        }
        Map map = new HashMap();
        map.put("vcode",vcode);

        map.put("time", StringUtil.isNull(time) ? DateUtil.getCurDateStr() : time);

        //Map<String,Object> dataMap = new HashMap<>();
        List<CarFlowStatisticVo> dataList = new ArrayList<>();

        try {
            dataList = carFlowStatisticService.getCarFlowCountByGate(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseBuilder.custom().success("success", 0).data(dataList).build();
    }


    /**
     * 停车场使用情况
     * @param vcode
     * @return
     * @throws JsonProcessingException
     */

    @SysLog("停车场使用情况")
    @ApiOperation(value = "停车场使用情况", notes = "停车场使用情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区编码", required = true, paramType = "String"),
    })
    @GetMapping("/realparKing")
    public Object getrealparKing(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String date = format.format(new Date());//系统当前时间
        String date ="2017-06-04";
        DatasResponse<CarFlowStatisticVo> datasResponse = new DatasResponse();
        Object carFlowSum = carFlowService.getCarFlowSum(vcode, date);
        //获取小时数据
        Object carFlowHour = carFlowService.getCarFlowHour(vcode, date);
        Map<String, Object> myMap = new HashMap<>();
        myMap.put("carFlowSum", carFlowSum);
        myMap.put("carFlowHour", carFlowHour);
        Map map =new HashMap();
        //获取停车场使用情况
        List<TimelyParkingSurplusVo> jqTimelyParkingList = timelyParkingService.getJqTimelyParking(vcode);
//        myMap =(Map) timelyParkingService.getJqTimelyParking(vcode);
        if(jqTimelyParkingList.size()>0) {
            myMap.put("total", jqTimelyParkingList.get(0).getTotal());
            myMap.put("surplusPaking", jqTimelyParkingList.get(0).getSurplusPaking());
            myMap.put("parkingTotal", jqTimelyParkingList.get(0).getParkingTotal());
        }
        return ResponseBuilder.custom().success("success", 0).data(myMap).build();

    }
}
