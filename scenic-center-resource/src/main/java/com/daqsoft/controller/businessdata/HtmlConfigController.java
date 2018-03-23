/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller.businessdata;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.controller.accommodation.AccommodationController;
import com.daqsoft.entity.htmlConfig.HtmlConfig;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.dataimitate.HtmlConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Title: HtmlTempletController
 * @Author: tanggm
 * @Date: 2018/02/11 14:10
 * @Description: TODO
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/htmlConfig")
public class HtmlConfigController extends BaseResponseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(AccommodationController.class);
    @Autowired
    private HtmlConfigService htmlConfigService;

    /**
     * 跳转到客户端屏数配置页面
     */
    @GetMapping(Constants.EDITACTION)
    public String edit(ModelMap model, HttpServletRequest request) {
        String vcode = getVcode(request);
        List<Map> moduleSelect = htmlConfigService.moduleSelect();
        List<HtmlConfig> getOne = htmlConfigService.getOne(vcode);
        model.addAttribute("moduleSelect", moduleSelect);
        if(getOne.size() > 0) {
            HtmlConfig htmlConfig = new HtmlConfig();
            String hkey = "";
            for(int i = 0; i < getOne.size(); i ++) {
                hkey = hkey + getOne.get(i).getHkey() + ",";
                if(i == getOne.size() -1) {
                    hkey = hkey.substring(0,hkey.length()-1);
                }
            }
            htmlConfig.setHkey(hkey);
            model.addAttribute("dto", htmlConfig);
        }else {
            HtmlConfig dto = new HtmlConfig();
            model.addAttribute("dto", dto);
        }
        return "htmlConfig/html_config";
    }

    /**
     * 保存模板
     *
     * @return 成功信息
     */
    @RequestMapping(value = "/saveHtmlConfig", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@RequestParam("HKEY") String hkey, HttpServletRequest request) {
        HtmlConfig htmlConfig = new HtmlConfig();
        String vcode = getVcode(request);
        htmlConfig.setVcode(getVcode(request));//设置vcode
        htmlConfig.setHkey(hkey);
        try {
            //当前登录用户
            htmlConfigService.save(vcode, htmlConfig);
        } catch (Exception e) {
            LOGGER.error("保存失败:", e);
            return builderFailed("保存失败");
        }
        return builderSuccess(Constants.SAVESUCCESSOPERATION);
    }

}
