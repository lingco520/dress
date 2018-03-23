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
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.dataimitate.HtmlTempletService;
import com.daqsoft.service.roadTraffic.RoadTrafficService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: HtmlTempletController
 * @Author: tanggm
 * @Date: 2018/02/11 14:10
 * @Description: TODO HTML公共模板页面样式等调整，新增业务模拟时，在此功能中新增
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Controller
@RequestMapping(value = "/htmlTemplet")
public class HtmlTempletController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlTempletController.class);
    @Autowired
    private HtmlTempletService htmlTempletService;

    @Autowired
    private RoadTrafficService roadTrafficService;
    @Autowired
    private CommonService commonService;

    /**
     * 跳转到客户端屏数配置页面
     */
    @GetMapping(Constants.EDITACTION)
    public String edit(Model model, HttpServletRequest request) {
        BusinessData dto = new BusinessData();
        // 流量统计多行数据回显问题
        List<Map> flowHourList = new ArrayList<>();
        Map map = new HashMap();
        map.put("flowSource", "");
        map.put("flowHourMin", "");
        map.put("flowHourMax", "");
        map.put("flowHourSureStr", "");
        flowHourList.add(map);
        model.addAttribute("flowHourList", flowHourList);
        model.addAttribute("dto", dto);
        String memo = commonService.getSysRegionByRegion(getSysUserRegion(request)).getMemo();
        model.addAttribute("CURRENT_REGION", memo);
        model.addAttribute("roadTraffics", roadTrafficService.list(getVcode(request)));
        return "htmlTemplet/html_templet";
    }


    /**
     * 保存模板
     *
     * @return 成功信息
     */
    @RequestMapping(value = "/saveHtmlTempletCode", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@RequestParam("keys") String[] keys, @RequestParam("names") String[] names) {
        try {
            htmlTempletService.save(keys, names);
            return builderSuccess(Constants.SAVESUCCESSOPERATION);
        } catch (Exception e) {
            LOGGER.error("保存失败:", e);
            return builderFailed("保存失败");
        }
    }
}
