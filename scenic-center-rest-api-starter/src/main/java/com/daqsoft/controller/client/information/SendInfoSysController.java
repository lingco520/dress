package com.daqsoft.controller.client.information;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.informationService.SendInfoSysService;
import com.daqsoft.validator.Assert;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author superziy
 * @version V3.1.0
 * @description 罗浮山的信息发布
 * @date 2017-06-23 14:40
 */
@Api("罗浮山的信息发布")
@RestController
@RequestMapping(value = "/rest/sendInfo")
public class SendInfoSysController {
    @Autowired
    private SendInfoSysService sendInfoSysService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SendInfoSysController.class);

    /**
     * 罗浮山景区信息发布list
     *
     * @return
     */
    @SysLog("罗浮山景区信息发布list")
    @ApiOperation(value = "罗浮山景区信息发布list", notes = "罗浮山景区信息发布list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "景区代码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "date", value = "时间", required = true, paramType = "String")
    })
    @RequestMapping(value = "/sendInfoList", method = RequestMethod.GET)
    public Object sendInfoList(String vcode, String date) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "景区编码不能为空!");
        List list = new ArrayList();
        try {
            // 查询列表
            list = sendInfoSysService.sendInfoList(vcode, date);
        } catch (Exception e) {
            LOGGER.error("罗浮山景区信息发布list错误", e);
            return ResponseBuilder.custom().failed("error", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

}
