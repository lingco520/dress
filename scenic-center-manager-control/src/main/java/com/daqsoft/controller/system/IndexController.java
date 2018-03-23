package com.daqsoft.controller.system;

import com.daqsoft.constants.ScrsConstants;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.scenicEntity.SysUserScenic;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.gisModuleManage.GisModuleManageService;
import com.daqsoft.service.scenicAreas.ScenicAreasService;
import com.daqsoft.service.scenicRelationService.ScenicRelationService;
import com.daqsoft.vo.resource.GisModule;
import com.daqsoft.vo.resource.ScenicAreaVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/scrsIndex")
public class IndexController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private ScenicRelationService scenicRelationService;

    @Autowired
    private GisModuleManageService gisModuleManageService;
    @Autowired
    private ScenicAreasService scenicAreasService;

    /**
     * 返回账号信息页面,页面通过ajax再请求数据
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(ModelMap model, HttpServletRequest request, Principal principal) throws Exception {
        //景区唯一标示 之前这里只获取了vcode,现在改成获取整个用户
        //String vcode = String.valueOf(stringRedisTemplate.opsForHash().get(k, principal.getName()));
        // 通过账号获取登录用户
        SysUserScenic sysUserScenic = scenicRelationService.getUserByAccount(principal.getName());
        if (ObjectUtils.isEmpty(sysUserScenic)) {
            throw new UsernameNotFoundException("用户未找到!");
        }
        HttpSession session = request.getSession();
        session.setAttribute(ScrsConstants.VCODE, sysUserScenic.getVcode());
        session.setAttribute(ScrsConstants.ACCOUNT, principal.getName());
        session.setAttribute(ScrsConstants.USER_REGION, sysUserScenic.getRegion());

        //根据景区vcode查询景区表中地图中心点经纬度
        ScenicAreaVo scenicAreaVo = scenicAreasService.getScenicArea(getVcode(request));
        model.addAttribute("dto", scenicAreaVo);
        //查询gis配置模块
        List<GisModule> gisModuleList = gisModuleManageService.getGisModule(getVcode(request));
        model.addAttribute("gisModule", gisModuleList);
        return "gdScenicMap/gdScenicMap_list";
    }
}
