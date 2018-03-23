package com.daqsoft.controller.client.comprehensive;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.service.client.comprehensiveService.CommonResourceService;
import com.daqsoft.validator.Assert;
import com.daqsoft.vo.client.madeVoBean.DutyPersonEmerVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: superziy .
 * @Date: Created in 9:08 2017/6/20.
 * @Version: 1.0
 * @describe: 综合展示页面公共资源模块
 */
@RestController
@RequestMapping("/rest/comprehensiveCommon")
public class CommonResourceController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CommonResourceService commonResourceService;

    /**
     * @param vcode
     * @Author: superziy .
     * @Date: Created in 10:54 2017/6/20.
     * @Version:
     * @describe: 流量统计 (官网访问&微信访问数量)
     * lrd:2017-08-22
     * 此方法目前只有云台山在用。
     * apikey写死的。
     */
    @RequestMapping("/getStatsticsCount")
    public Object getStatsticsCount(String vcode) throws JsonProcessingException {
        Assert.paramIsBlank(vcode, "请输入景区编码！");
        List dataList = commonResourceService.getStatsticsCount(vcode,"no");//获取景区官网，微官网，APP等浏览量
        return ResponseBuilder.custom().success("success",200).data(dataList).build();
    }

    /**
     * 主页查询值班人员信息（提供给应急指挥子系统首页展示）
     * @param resourcecode (根据统一的资源编码进行查询，该接口提供给应急子系统首页值班人员情况模块)
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/getDutyPersonEmergency")
    public Object getDutyPersonEmergency(String resourcecode) throws JsonProcessingException {
        Assert.isBlank(resourcecode,"景区编码不能为空！");
        DutyPersonEmerVo personInfo =  commonResourceService.getDutyPersonEmergency(resourcecode);
        return ResponseBuilder.custom().success("success",0).data(personInfo).build();
    }
}
