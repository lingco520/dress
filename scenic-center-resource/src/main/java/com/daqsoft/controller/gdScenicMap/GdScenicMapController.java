package com.daqsoft.controller.gdScenicMap;

import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.framework.entity.SysRegion;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.gisModuleManage.GisModuleManageService;
import com.daqsoft.service.scenicAreas.ScenicAreasService;
import com.daqsoft.vo.resource.GisModule;
import com.daqsoft.vo.resource.ScenicAreaVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Title: 高德map
 * @Author: superziy .
 * @Date: Created in 11:00 2018/3/20.
 * @Version: 4.0.0
 * @describe: 高德map
 * @since:JDK 1.8
 */
@Controller
@RequestMapping(value = "/gdScenicMap")
public class GdScenicMapController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GdScenicMapController.class);
    private static final String PATH = "gdScenicMap/";
    @Autowired
    private GisModuleManageService gisModuleManageService;
    @Autowired
    private ScenicAreasService scenicAreasService;

    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(ModelMap model, HttpServletRequest request) throws Exception {
        //根据景区vcode查询景区表中地图中心点经纬度
        ScenicAreaVo scenicAreaVo = scenicAreasService.getScenicArea(getVcode(request));
        model.addAttribute("dto", scenicAreaVo);
        //查询gis配置模块
        List<GisModule> gisModuleList = gisModuleManageService.getGisModule(getVcode(request));
        model.addAttribute("gisModule", gisModuleList);
        return PATH + "gdScenicMap_list";
    }


}
