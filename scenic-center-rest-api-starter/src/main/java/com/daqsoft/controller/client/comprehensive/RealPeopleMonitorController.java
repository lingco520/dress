/**
 * @Copyright: <a htef="http://www.daqsoft.com">成都中科大旗软件有限公司Copyright © 2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller.client.comprehensive;

import com.daqsoft.commons.responseentity.DatasResponse;
import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.comprehensive.RealPeopleService;
import com.daqsoft.service.client.comprehensiveService.RealPeopleMonitorService;
import com.daqsoft.service.client.passengerFlowService.RealBigPeopleService;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.JqScenic_ScenicLoadPeople_Tend;
import com.daqsoft.vo.client.madeVoBean.RealPeopleChoiceTimeVo;
import com.daqsoft.vo.client.madeVoBean.SpotsWifiBeeComfortVo;
import com.daqsoft.vo.client.mysqlBean.RealPeople;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Title: 实时客流监测
 * @Author: yangmei
 * @Date: 2017/08/02 09:35
 * @Description: TODO
 * @Comment：综合展示页面模块化划分 实时客流监测类型
 * @Version:
 * @Warning: 由于某些景区的wifi设备特殊，所有不同的景区会有不同的数据源，所有个别景区比较特殊
 * @see
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/rest/realpeoplemonitor")
public class RealPeopleMonitorController {

    @Autowired
    private RealPeopleMonitorService realPeopleMonitorService;
    @Autowired
    private RealBigPeopleService bigRealPeopleService;
    @Autowired
    private RealPeopleService realPeopleService;
    /**
     * 年、月、日、时(实时人数统计数据)
     *
     * @param vcode    景区编码
     * @param types    日期类型（年：year,月：month,日：day,时：time）
     * @param dateTime 根据日期类型传入对应的时间
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/getRealTimeMethPeop")
    public Object getRealTimeMethPeop(String vcode, String types, String dateTime
    ) throws JsonProcessingException {
        List<Map> mapList = null;
        DatasResponse<RealPeopleChoiceTimeVo> datasResponse = new DatasResponse();
        List<RealPeopleChoiceTimeVo> navigationManageHomeVos = new LinkedList<RealPeopleChoiceTimeVo>();
        List<RealPeople> jqSumPeopleList = new ArrayList<>();
        Map<Object, Object> map = new HashMap<>();

        Map<String, Object> dataMap = new HashMap<>();
        String vcodemdh = DigestUtils.md5Hex(vcode);
        if (types.equals("year")) {
            navigationManageHomeVos = realPeopleService.getRealTimeMethYear(vcodemdh);
        } else if (types.equals("month")) {
            navigationManageHomeVos = realPeopleService.getRealTimeMethMonth(vcodemdh, dateTime);
        } else if (types.equals("day")) {
            navigationManageHomeVos = realPeopleService.getRealTimeMethDay(vcodemdh, dateTime);
        } else if (types.equals("time")) {
            //jqSumPeopleList = realPeopleService.getSyncPeopleSum(vcode);
            //获取最大承载量
            map = realPeopleService.getMaxQuantity(vcode);
            //获取实时人数列表
            navigationManageHomeVos = realPeopleService.getRealTimeMethTime(vcodemdh, dateTime);
        }
        Integer syncNum = 0;
        String maxquantity = "";

        //获取
        for (int i = 0; i < navigationManageHomeVos.size(); i++) {
            syncNum = syncNum + Integer.parseInt(navigationManageHomeVos.get(i).getNum());
        }
        if (map == null || map.size() < 1) {
            maxquantity = "0";
        } else {
            maxquantity = map.get("maxquantity").toString();
        }

        dataMap.put("listdata", navigationManageHomeVos);
        //这个老接口不知其他地方用没，所以为了不影响之前的，进行2中情况的return
        if (types.equals("time")) {
            dataMap.put("syncNum", syncNum);
            dataMap.put("maxquantity", maxquantity);
            return ResponseBuilder.custom().success("success", 0).data(dataMap).build();
        } else {
            return ResponseBuilder.custom().success("success", 0).data(navigationManageHomeVos).build();
        }

    }
    /**
     * 景区景点实时客流监测，这方法用于华蓥山
     * @auth yangmei
     * @param vcode
     * @Comment: 每个景点的实时人数符合都是通过jq_wifi_part这张表关联来查询的
     * @return 景点名称 实时游客数 负荷度
     */
    @SysLog("景区景点人数分析—-华蓥山")
    @ApiOperation(value = "景区景点人数分析", notes = "景区景点人数分析")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/realpeopleMonitorHyj")
    public Object realpeopleMonitorHyj(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空！");
        List<JqScenic_ScenicLoadPeople_Tend> peopleLoadVo = realPeopleMonitorService.getRealpeopleMonitorHyj(vcode);
        return ResponseBuilder.custom().success("success", 0).data(peopleLoadVo).build();
    }

    /**
     * 景区景点实时客流监测，通过小蜜蜂wifi数据分析
     * @auth yangmei
     * @date 2017-08-14 14:00:00
     * @param vcode
     * @Comment: jq_wifi_detail 是小蜜蜂数据来源,关联景点表来分析
     * @return 景点名称 实时游客数 负荷度
     */
    @SysLog("景区景点实时客流监测，通过小蜜蜂wifi数据分析")
    @ApiOperation(value = "景区景点实时客流监测，通过小蜜蜂wifi数据分析", notes = "景区景点实时客流监测，通过小蜜蜂wifi数据分析")
    @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    @GetMapping(value = "/realpeopleMonitorBee")
    public Object realpeopleMonitorBee(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空！");
        List<SpotsWifiBeeComfortVo>  pepList  = realPeopleMonitorService.realpeopleMonitorBee(vcode);
        return ResponseBuilder.custom().success("success", 0).data(pepList).build();
    }

    /**
     * 实时客流监测 景区舒适度分析
     * @auth yangmei
     * @date 2017-08-04 10:59
     * @param vcode
     * @return
     * @Comment:
     */
    @SysLog("景区实时客流监测 舒适度分析")
    @ApiOperation(value = "景区实时客流监测 舒适度分析", notes = "景区实时客流监测 舒适度分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "date", value = "日期", required = true, paramType = "String")
    })
    @GetMapping(value = "/realPeoplCcomfortAnalysis")
    public Object realPeoplCcomfortAnalysis(String vcode,String date) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        Assert.paramIsBlank(date, "日期参数不能为空!");
        Assert.paramIsDate(date, "日期参数不正确! 格式为： yyyy-mm-dd 例如：2017-01-01");
        //List<Tend> allList = bigRealPeopleService.find_oneday_dateAndReal(date, vcode);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap = bigRealPeopleService.find_oneday_dateAndReal(date, vcode);
        return ResponseBuilder.custom().success("success", 0).data(resultMap).build();
    }
    /**
     * 年、月、日、时(实时人数统计数据)
     *
     * @param vcode    景区编码
     * @param types    日期类型（年：year,月：month,日：day,时：time）
     * @param dateTime 根据日期类型传入对应的时间
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("实时人数统计数据")
    @ApiOperation(value = "实时人数统计数据", notes = "实时人数统计数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTime", value = "时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "types", value = "传参数类型", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @GetMapping("/getRealTimeMeth")
    public Object getRealTimeMeth(String vcode, String types, String dateTime
    ) throws JsonProcessingException {
        Assert.paramIsBlank(dateTime, "请输入时间！");
        Assert.paramIsBlank(types, "请输入传参类型！");
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List<RealPeopleChoiceTimeVo> navigationManageHomeVos = new LinkedList<RealPeopleChoiceTimeVo>();
        String vcodemdh = DigestUtils.md5Hex(vcode);
        if (types.equals("year")) {
            navigationManageHomeVos = realPeopleMonitorService.getRealTimeMethYear(vcodemdh);
        } else if (types.equals("month")) {
            navigationManageHomeVos = realPeopleMonitorService.getRealTimeMethMonth(vcodemdh, dateTime);
        } else if (types.equals("day")) {
            navigationManageHomeVos = realPeopleMonitorService.getRealTimeMethDay(vcodemdh, dateTime);
        } else if (types.equals("time")) {
            navigationManageHomeVos = realPeopleMonitorService.getRealTimeMethTime(vcodemdh, dateTime);
        }
        return ResponseBuilder.custom().success("success", 0).data(navigationManageHomeVos).build();
    }
}
