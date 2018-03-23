package com.daqsoft.controller.agritainment;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.constants.DictConstants;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.agritainment.ResAgritainment;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.service.SysRegionServiceImpl;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.agritainment.AgritainmentService;
import com.daqsoft.vo.ScenicGisConfigVo;
import com.daqsoft.vo.SysDictVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @Author: superziy .
 * @Date: Created in 9:47 2018/1/24.
 * @Version: 4.0.0
 * @describe: 农家乐（增删改查）
 */
@Controller
@RequestMapping(value = "/agritainment")
public class AgritainmentController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgritainmentController.class);
    private static final String PATH = "agritainment/";
    @Autowired
    private AgritainmentService agritainmentService;
    @Autowired
    private CommonService commonService;

    private final SysRegionServiceImpl sysRegionService;

    @Autowired
    public AgritainmentController(SysRegionServiceImpl sysRegionService) {
        this.sysRegionService = sysRegionService;
    }

    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(ModelMap model) {
        //获取农家乐等级
        List<SysDictVo> agritainmentLevelDict = commonService.getDictByType(DictConstants.AGRITAINMENT_LEVEL);
        model.addAttribute("agritainmentLevel", agritainmentLevelDict);
        return PATH + "agritainment_list";
    }

    /**
     * 列表数据
     *
     * @param name 特产名称
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name,String level,String status, HttpServletRequest request) {
        try {
            return pageBuilderSuccess(agritainmentService.list(pageForm, name, getVcode(request),level,status));
        } catch (Exception e) {
            LOGGER.error("查询错误:", e);
            return builderFailed("查询错误");
        }
    }

    /**
     * 新增/编辑
     *
     * @param id    数据id
     * @param model 数据对象
     * @return 新增/编辑页面
     */
    @GetMapping(Constants.EDITACTION)
    public String edit(Long id, ModelMap model, HttpServletRequest request) throws Exception {
        ResAgritainment dto = new ResAgritainment();
        //获取农家乐等级
        List<SysDictVo> agritainmentLevelDict = commonService.getDictByType(DictConstants.AGRITAINMENT_LEVEL);
        try {
            if (id != null) {
                dto = agritainmentService.get(id);
            } else {
                dto = new ResAgritainment();
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }
        model.addAttribute("regions", dto.getRegion() != null ? sysRegionService.queryDeepPathByRegion(Long.valueOf(dto.getRegion())) : "");
        // 返回region，用于地图初始化地理位置, key 必须为 CURRENT_REGION 这个值,js代码默认是取这个值
        String memo = commonService.getSysRegionByRegion(getSysUserRegion(request)).getMemo();
        model.addAttribute("CURRENT_REGION", memo);
        model.addAttribute("dto", dto);
        model.addAttribute("agritainmentLevel", agritainmentLevelDict);
        return PATH + "agritainment_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResAgritainment dto, HttpServletRequest request) {
        dto.setVcode(getVcode(request));//设置vcode
        dto.setUpdateTime(new Date());
        try {
            //当前登录用户
            if (dto.getId() == null) {
                agritainmentService.save(dto);
            } else {
                agritainmentService.update(dto);
            }
        } catch (Exception e) {
            LOGGER.error("保存失败:", e);
            return builderFailed("保存失败");
        }
        return builderSuccess(Constants.SAVESUCCESSOPERATION);
    }

    /**
     * 删除
     *
     * @param ids 数据集
     * @return 成功信息
     */
    @PostMapping(Constants.DELETEACTION)
    @ResponseBody
    public BaseResponse delete(Long[] ids) {
        try {
            agritainmentService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }


    /**
     * 查看页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.VIEWACTION)
    public String list(ModelMap model, HttpServletRequest request) {
        //查询相关配置
        ScenicGisConfigVo gisConfig = commonService.getGisConfig(getVcode(request));
        model.addAttribute("dto", gisConfig);
        //gis地图页面
        return "scenicGis/scenic_gis_view";
    }
}
