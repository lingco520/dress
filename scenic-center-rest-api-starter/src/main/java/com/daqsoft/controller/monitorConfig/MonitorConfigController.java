package com.daqsoft.controller.monitorConfig;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.monitorConfig.MonitorConfigService;
import com.daqsoft.validator.Assert;
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

/**
 * @Title: 客户端监控配置供外部使用接口
 * @Author: lyl
 * @Date: 2018/03/21 17:30
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Api("客户端监控配置供外部使用接口")
@RestController
@RequestMapping("/rest/MonitorConfig")
public class MonitorConfigController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MonitorConfigService monitorConfigService;


    @SysLog("获取监控配置信息")
    @ApiOperation(value = "获取监控配置信息", notes = "获取监控配置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "div", value = "内外网字段标识", required = false, paramType = "String")
    })
    @GetMapping("/getMonitorConfigXML")
    public Object getMonitorConfigXML(String vcode, String div) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        int judge = 1;
        if (div.equals("2"))
        {
            judge = 2;
        }
        String monitorConfig = monitorConfigService.getMonitorConfigXML(vcode, judge);

        System.out.println(monitorConfig);

        return ResponseBuilder.custom().success("success",0).data(monitorConfig).build();
    }


}