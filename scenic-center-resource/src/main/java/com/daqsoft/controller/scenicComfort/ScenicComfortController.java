package com.daqsoft.controller.scenicComfort;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.scenicComfort.ResScenicComfort;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.scenicComfort.ScenicComfortService;
import com.daqsoft.vo.ScenicSpotsVo;
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
 * @Date: Created in 9:47 2018/1/16.
 * @Version: 4.0.0
 * @describe: 景点舒适度配置（增删改查）
 */
@Controller
@RequestMapping(value = "/scenicComfort")
public class ScenicComfortController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScenicComfortController.class);
    private static final String PATH = "scenicComfort/";
    @Autowired
    private CommonService commonService;
    @Autowired
    private ScenicComfortService scenicComfortService;


    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list() {
        return PATH + "scenic_comfort_list";
    }

    /**
     * 列表数据
     * @param pageForm
     * @param name 景点名称
     * @param rating 等级
     * @param status 状态
     * @param request
     * @return
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name, String rating, String status, HttpServletRequest request) {
        try {
            return pageBuilderSuccess(scenicComfortService.list(pageForm, name, getVcode(request), rating, status));
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
        ResScenicComfort dto = new ResScenicComfort();
        //查询景区下的景点
        List<ScenicSpotsVo> scenicName = commonService.getScenicByVcode(getVcode(request));
        try {
            if (id != null) {
                dto = scenicComfortService.get(id);
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }

        model.addAttribute("dto", dto);
        model.addAttribute("scenicName", scenicName);
        return PATH + "scenic_comfort_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResScenicComfort dto, HttpServletRequest request) {
        dto.setVcode(getVcode(request));//设置vcode
        try {
            //当前登录用户
            if (dto.getId() == null) {
                scenicComfortService.save(dto);
            } else {
                scenicComfortService.update(dto);
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
            scenicComfortService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
