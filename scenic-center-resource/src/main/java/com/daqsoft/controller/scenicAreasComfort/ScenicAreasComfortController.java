package com.daqsoft.controller.scenicAreasComfort;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.scenicAreasComfort.ResScenicAreasComfort;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.scenicAreasComfort.ScenicAreasComfortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author: superziy .
 * @Date: Created in 9:47 2018/1/16.
 * @Version: 4.0.0
 * @describe: 景区舒适度配置（增删改查）
 */
@Controller
@RequestMapping(value = "/scenicAreasComfort")
public class ScenicAreasComfortController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScenicAreasComfortController.class);
    private static final String PATH = "scenicAreasComfort/";
    @Autowired
    private ScenicAreasComfortService scenicAreasComfortService;


    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list() {
        return PATH + "scenic_areas_comfort_list";
    }

    /**
     * 列表数据
     * @param pageForm
     * @param rating 等级
     * @param status 状态
     * @param request
     * @return
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String rating, String status, HttpServletRequest request) {
        try {
            return pageBuilderSuccess(scenicAreasComfortService.list(pageForm, getVcode(request), rating, status));
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
    public String edit(Long id, ModelMap model, HttpServletRequest request) {
        ResScenicAreasComfort dto = new ResScenicAreasComfort();
        try {
            if (id != null) {
                dto = scenicAreasComfortService.get(id);
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }

        model.addAttribute("dto", dto);
        return PATH + "scenic_areas_comfort_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResScenicAreasComfort dto, HttpServletRequest request) {
        dto.setVcode(getVcode(request));//设置vcode
        try {
            //当前登录用户
            if (dto.getId() == null) {
                scenicAreasComfortService.save(dto);
            } else {
                scenicAreasComfortService.update(dto);
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
            scenicAreasComfortService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
