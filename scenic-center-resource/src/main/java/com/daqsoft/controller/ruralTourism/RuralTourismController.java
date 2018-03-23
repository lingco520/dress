package com.daqsoft.controller.ruralTourism;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.constants.DictConstants;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.ruralTourism.ResRuralTourism;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.service.SysRegionServiceImpl;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.ruralTourism.RuralTourismService;
import com.daqsoft.vo.SysDictVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: superziy .
 * @Date: Created in 9:47 2018/1/24.
 * @Version: 4.0.0
 * @describe: 乡村旅游（增删改查）
 */
@Controller
@RequestMapping(value = "/ruralTourism")
public class RuralTourismController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuralTourismController.class);
    private static final String PATH = "ruralTourism/";
    @Autowired
    private RuralTourismService ruralTourismService;
    @Autowired
    private CommonService commonService;

    private final SysRegionServiceImpl sysRegionService;

    @Autowired
    public RuralTourismController(SysRegionServiceImpl sysRegionService) {
        this.sysRegionService = sysRegionService;
    }

    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(ModelMap model) {
        //获取乡村旅游等级
        List<SysDictVo> ruraltourismLevelDict = commonService.getDictByType(DictConstants.RURALTOURISM_LEVEL);
        //获取乡村旅游类型
        List<SysDictVo> ruraltourismTypeDict = commonService.getDictByType(DictConstants.RURALTOURISM_TYPE);
        model.addAttribute("ruralTourismLevel", ruraltourismLevelDict);
        model.addAttribute("ruralTourismType", ruraltourismTypeDict);
        return PATH + "rural_tourism_list";
    }

    /**
     * 列表数据
     *
     * @param name 特产名称
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name,String type,String level,String status, HttpServletRequest request) {
        try {
            return pageBuilderSuccess(ruralTourismService.list(pageForm, name, getVcode(request),type,level,status));
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
        ResRuralTourism dto = new ResRuralTourism();
        //获取乡村旅游等级
        List<SysDictVo> ruraltourismLevelDict = commonService.getDictByType(DictConstants.RURALTOURISM_LEVEL);
        //获取乡村旅游类型
        List<SysDictVo> ruraltourismTypeDict = commonService.getDictByType(DictConstants.RURALTOURISM_TYPE);
        try {
            if (id != null) {
                dto = ruralTourismService.get(id);
            } else {
                dto = new ResRuralTourism();
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }
        model.addAttribute("regions", dto.getRegion() != null ? sysRegionService.queryDeepPathByRegion(Long.valueOf(dto.getRegion())) : "");
        model.addAttribute("dto", dto);
        String memo = commonService.getSysRegionByRegion(getSysUserRegion(request)).getMemo();
        model.addAttribute("CURRENT_REGION", memo);
        model.addAttribute("ruralTourismLevel", ruraltourismLevelDict);
        model.addAttribute("ruralTourismType", ruraltourismTypeDict);
        return PATH + "rural_tourism_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResRuralTourism dto, HttpServletRequest request) {
        dto.setVcode(getVcode(request));//设置vcode
        try {
            //当前登录用户
            if (dto.getId() == null) {
                ruralTourismService.save(dto);
            } else {
                ruralTourismService.update(dto);
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
            ruralTourismService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
