/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller.businessdata;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.businessData.BusinessData;
import com.daqsoft.framework.service.SysRegionServiceImpl;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.dataimitate.BusinessDataService;
import com.daqsoft.service.roadTraffic.RoadTrafficService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Title: HtmlTempletController
 * @Author: lyl
 * @Date: 2018/02/11 14:10
 * @Description: TODO HTML公共模板页面样式等调整，新增业务模拟时，在此功能中新增
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/businessData")
public class BusinessDataController extends BaseResponseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessDataController.class);
    @Autowired
    private BusinessDataService businessDataService;
    @Autowired
    private CommonService commonService;
    private final SysRegionServiceImpl sysRegionService;
    @Autowired
    private RoadTrafficService roadTrafficService;

    @Autowired
    public BusinessDataController(SysRegionServiceImpl sysRegionService) {
        this.sysRegionService = sysRegionService;
    }
    /**
     *
     */
    @GetMapping(Constants.EDITACTION)
    public String edit(Model model, HttpServletRequest request) {
        String vcode = getVcode(request);
        // 查询记录列表
        List<Map> list = businessDataService.getDataImitateList(getVcode(request));
        Map businessDataMap = new HashMap();
        try {
            for(Map kvMap : list){
                if(null != kvMap.get("skey")){
                    businessDataMap.put(kvMap.get("skey"), kvMap.get("svalue"));
                }
            }
        } catch (Exception e){
            LOGGER.error("业务数据模拟list转javabean发生异常：", e);
        } finally {
            // 查询所有配置模板的key
            List<String> templateHkeyList = businessDataService.getTemplateHkeyList();
            // 查询景区配置的key
            List<String> hkeyList = businessDataService.getHkeyList(vcode);

            // 地区回显
            model.addAttribute("teamRegions", !StringUtils.isEmpty(businessDataMap.get("teamRegion")) ? sysRegionService.queryDeepPathByRegion(Long.valueOf(businessDataMap.get("teamRegion")+"")) : "");
            model.addAttribute("weatherRegions", !StringUtils.isEmpty(businessDataMap.get("weatherRegion")) ? sysRegionService.queryDeepPathByRegion(Long.valueOf(businessDataMap.get("weatherRegion")+"")) : "");
            model.addAttribute("windRegions", !StringUtils.isEmpty(businessDataMap.get("windRegion")) ? sysRegionService.queryDeepPathByRegion(Long.valueOf(businessDataMap.get("windRegion")+"")) : "");

            model.addAttribute("dto", businessDataMap);
            model.addAttribute("templateHkeyList", templateHkeyList);
            model.addAttribute("hkeyList", hkeyList);

            // 流量统计多行数据回显问题
            List<Map> flowHourList = new ArrayList<>();
            if(businessDataMap.size() > 0 && businessDataMap.get("flowSource") != null){
            String flowSource = businessDataMap.get("flowSource")+"";
            String flowHourMin = businessDataMap.get("flowHourMin")+"";
            String flowHourMax = businessDataMap.get("flowHourMax")+"";
            String flowHourSureStr = businessDataMap.get("flowHourSureStr")+"";

                String[] flowSourceArr = flowSource.split(",");
                String[] flowHourMinArr = flowHourMin.split(",");
                String[] flowHourMaxArr = flowHourMax.split(",");
                String[] flowHourSureStrArr = flowHourSureStr.split(",");
                for (int i = 0; i < flowSourceArr.length; i++){
                    Map map = new HashMap();
                    map.put("flowSource", flowSourceArr[i]);
                    map.put("flowHourMin", flowHourMinArr[i]);
                    map.put("flowHourMax", flowHourMaxArr[i]);
                    map.put("flowHourSureStr", flowHourSureStrArr[i]);
                    flowHourList.add(map);
                }
            }else {
                Map map = new HashMap();
                map.put("flowSource", "");
                map.put("flowHourMin", "");
                map.put("flowHourMax", "");
                map.put("flowHourSureStr", "");
                flowHourList.add(map);
            }
            model.addAttribute("flowHourList", flowHourList);
            // 返回region，用于地图初始化地理位置, key 必须为 CURRENT_REGION 这个值,js代码默认是取这个值
            String memo = commonService.getSysRegionByRegion(getSysUserRegion(request)).getMemo();
            model.addAttribute("CURRENT_REGION", memo);
            model.addAttribute("roadTraffics", roadTrafficService.list(getVcode(request)));
            return "businessData/business_data";
        }
    }
    /**
     * 保存业务数据模拟配置及数据生成
     *
     * @return 成功信息
     */
    @RequestMapping(value = "/saveBusinessData", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") BusinessData businessData, HttpServletRequest request) {
        Date sTime = new Date();
        try {
            String vcode = getVcode(request);
            Long total = businessDataService.saveBusinessData(vcode, businessData);
            Date eTime = new Date();
            String msg = "业务数据生成成功，共生成记录数" + total + "条，耗时" + new BigDecimal(eTime.getTime()-sTime.getTime()).divide(new BigDecimal(1000),0, BigDecimal.ROUND_HALF_UP) + "秒";
            LOGGER.info("业务数据：" + total);
            return builderSuccess(msg);
        } catch (Exception e) {
            LOGGER.error("业务数据生成失败:", e);
            return builderFailed("业务数据生成失败");
        }
    }

}