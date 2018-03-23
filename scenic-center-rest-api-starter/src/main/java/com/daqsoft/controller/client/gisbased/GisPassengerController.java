package com.daqsoft.controller.client.gisbased;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.gisbasedService.GisBasicService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.MathUtils;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.jQScenicSpotZJ;
import com.daqsoft.vo.client.madeVoBean.jQScenicSpotZJList;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: lyl .
 * @Date: Created in  2017/6/19.
 * @Version:
 * @describe:gis地图实时人数排名统计
 */
@Api("gis实时人数")
@RestController
@RequestMapping("/rest/GisPassenger")
public class GisPassengerController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GisBasicService gisBasicService;

    /**
     * 景点舒适度（咂机统计）
     * auth tanggm
     * date 2017-08-08
     * desc 返回数据格式修改，不规范
     *
     * @param vcode     景区编码
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @throws JsonProcessingException
     */
    @SysLog("gis景点舒适度")
    @ApiOperation(value = "gis景点舒适度", notes = "gis景点舒适度")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "String"),
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String")
    })
    @RequestMapping("/getScenicSpotZJMeth")
    public Object getScenicSpotZJMeth(String startTime, String endTime, String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(startTime, "请输入开始时间！");
        Assert.paramIsBlank(endTime, "请输入结束时间！");
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        Map<String, Object> resMap = new HashMap<>();
        List<jQScenicSpotZJ> navigationManageHomeVos = new LinkedList<>();
        Map<String, String> map = new HashMap<>();
        map.put("vcode", vcode);
        try {
            /*String[] start = startTime.split("_");
            String[] end = endTime.split("_");
            map.put("startTime", start[0] + " " + start[1] + ":00:00");
            map.put("endTime", end[0] + " " + end[1] + ":59:59");*/
            //（这是项目需求，现在不要了）
//            //设置两个历史时间，当当前时间没有实时数据时候，推迟到一个小时前查询
//            String historyStrTime = start[0] + " " + start[1] + ":00:00";
//            String historyEndTime = end[0] + " " + end[1] + ":59:59";

            // 取当前时间往前推两个小时
            String nowdate = DateUtil.getCurDateTimeStr();
            map.put("startTime", MathUtils.getAfterDelHour(nowdate,-2));
            map.put("endTime", nowdate);
            String scenicName = "";
            String maxcount = "";
            navigationManageHomeVos = gisBasicService.getScenicSpotBTPeople(map);//查询sql获取传入时间段统计景点数据数据
            for (int i = 0; i < navigationManageHomeVos.size(); i++) {
                scenicName = navigationManageHomeVos.get(i).getName();//得到实时人数表中的景点名称
                BigDecimal count = new BigDecimal(navigationManageHomeVos.get(i).getCount());//获取人数
                map.put("scenicName", scenicName);
                maxcount = gisBasicService.getComfortMaxCount(map);//查询景点的最大承载量
                if (!"".equals(maxcount) && maxcount != null) {//如果景点最大承载量不为空
                    BigDecimal dPload = BigDecimal.ZERO;
                    if(count.compareTo(new BigDecimal(0)) != 0){
                        dPload = count.multiply(new BigDecimal(100)).divide(new BigDecimal(maxcount), 2, BigDecimal.ROUND_HALF_UP);
                    }
                    navigationManageHomeVos.get(i).setMaxcount(dPload+"");
                } else {
                    navigationManageHomeVos.get(i).setMaxcount("0");//如果实时人数中景点没有对应的景点，则舒适度为0
                    navigationManageHomeVos.get(i).setCount("0");//如果实时人数中景点没有对应的景点，则人数为0
                }
            }
            //12.4  之前没有取前十  此处取前十
            if(navigationManageHomeVos.size() > 10) {
                List<jQScenicSpotZJ> tenList = new LinkedList<>();
                for(int i = 0; i < 10; i++) {
                    tenList.add(navigationManageHomeVos.get(i));
                }
                        resMap.put("list", tenList);
            }else {
                resMap.put("list", navigationManageHomeVos);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        //景区景点数量，最大承载量
        List<jQScenicSpotZJList> jQScenicSpotZJLists = gisBasicService.getScenicSpotZJCountMeth(map);
        if (jQScenicSpotZJLists.size() > 0) {
            resMap.put("count", navigationManageHomeVos.size());//实时人数中有景点人数则为多少个景点
            resMap.put("maxquantity", jQScenicSpotZJLists.get(0).getMaxquantity());
        } else {
            resMap.put("count", 0);
            resMap.put("maxquantity", 0);
        }

        return ResponseBuilder.custom().success("success", 0).data(resMap).build();

    }


}
