package com.daqsoft.controller.scenicSpots;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.scenicSpots.ResScenicSpots;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.service.SysRegionServiceImpl;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.scenicSpots.ScenicSpotsService;
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
 * @Date: Created in 9:47 2018/1/10.
 * @Version: 4.0.0
 * @describe: 景点信息（增删改查）
 */
@Controller
@RequestMapping(value = "/scenicSpots")
public class ScenicSpotsController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScenicSpotsController.class);
    private static final String PATH = "scenicSpots/";
    @Autowired
    private ScenicSpotsService scenicSpotsService;
    @Autowired
    private CommonService commonService;

    private final SysRegionServiceImpl sysRegionService;

    @Autowired
    public ScenicSpotsController(SysRegionServiceImpl sysRegionService) {
        this.sysRegionService = sysRegionService;
    }

    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list() {
        return PATH + "scenic_spots_list";
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
            return pageBuilderSuccess(scenicSpotsService.list(pageForm, name, getVcode(request),level,status));
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
        ResScenicSpots dto = new ResScenicSpots();
        List<SysDictVo> scenicTypeDict = commonService.getDictByType("scenicType");
        try {
            if (id != null) {
                dto = scenicSpotsService.get(id);
            } else {
                dto = new ResScenicSpots();
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }
        model.addAttribute("regions", dto.getRegion() != null ? sysRegionService.queryDeepPathByRegion(Long.valueOf(dto.getRegion())) : "");
        model.addAttribute("dto", dto);
        String memo = commonService.getSysRegionByRegion(getSysUserRegion(request)).getMemo();
        model.addAttribute("CURRENT_REGION", memo);
        model.addAttribute("scenicType", scenicTypeDict);
        return PATH + "scenic_spots_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResScenicSpots dto, HttpServletRequest request) {
        dto.setVcode(getVcode(request));//设置vcode
        try {
            //当前登录用户
            if (dto.getId() == null) {
                scenicSpotsService.save(dto);
            } else {
                scenicSpotsService.update(dto);
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
            scenicSpotsService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
