package com.daqsoft.controller.scenicGis;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.gisModuleManage.GisModuleManageService;
import com.daqsoft.vo.ScenicGisConfigVo;
import com.daqsoft.vo.resource.GisModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: superziy .
 * @Date: Created in 9:47 2018/1/29
 * @Version: 4.0.0
 * @describe: 景区地图
 */
@Controller
@RequestMapping(value = "/scenicGis")
public class ScenicGisController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScenicGisController.class);
    private static final String PATH = "scenicGis/";
    @Autowired
    private CommonService commonService;
    @Autowired
    private GisModuleManageService gisModuleManageService;


    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(ModelMap model, HttpServletRequest request) throws Exception {
        //查询相关配置
        ScenicGisConfigVo gisConfig = commonService.getGisConfig(getVcode(request));
        model.addAttribute("dto", gisConfig);
        //查询gis配置模块
        List<GisModule> gisModuleList = gisModuleManageService.getGisModule(getVcode(request));
        model.addAttribute("gisModule", gisModuleList);
        return PATH + "scenic_gis_view";
    }

    /**
     * 跳转公共页面
     *
     * @return 模板页面
     */
    @GetMapping("/mapPoint")
    @ResponseBody
    public BaseResponse mapPoint(ModelMap model, HttpServletRequest request) {
        ScenicGisConfigVo gisConfig = new ScenicGisConfigVo();
        try {
            //查询相关配置
            gisConfig = commonService.getGisConfig(getVcode(request));
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }
        return dataBuilderSuccess(gisConfig);

    }

    /**
     * 查询所属模块 （尚未调用的地方）
     *
     * @return 模板页面
     */
    @GetMapping("/getGisModule")
    @ResponseBody
    public BaseResponse getGisModule(HttpServletRequest request) {
        List<GisModule> list = new ArrayList<>();
        try {
            //查询相关配置
            list = gisModuleManageService.getGisModule(getVcode(request));
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }
        return dataBuilderSuccess(list);

    }

}
