package com.daqsoft.controller.test;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.test.SysTest;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.service.SysRegionServiceImpl;
import com.daqsoft.framework.service.exceptions.SubSetNotNullException;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.test.SysTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/sysTest")
public class SysTestController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysTestController.class);
    private static final String PATH = "test/";
    @Autowired
    private SysTestService sysTestService;
    private final SysRegionServiceImpl sysRegionService;
    @Autowired
    public SysTestController(SysRegionServiceImpl sysRegionService) {
        this.sysRegionService = sysRegionService;
    }

    /**
     * 列表页面
     * @return
     */
    @GetMapping(Constants.LISTACTION)
    public String list() {
        return PATH + "sys_test_list";
    }

    /**
     * 列表数据
     * @param pageForm
     * @param name
     * @return
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name) {
        return pageBuilderSuccess(sysTestService.list(pageForm, name));
    }
    /**
     * 新增/编辑
     *
     * @param id    数据id
     * @param model 数据对象
     * @return 新增/编辑页面
     */
    @GetMapping(Constants.EDITACTION)
    public String edit(Long id, ModelMap model) {
        SysTest dto;
        if (id != null) {
            dto = sysTestService.get(id);
        } else {
            dto = new SysTest();
        }
        model.addAttribute("regions", dto.getRegion() != null ? sysRegionService.queryDeepPathByRegion(Long.valueOf(dto.getRegion())) : "");
        model.addAttribute("dto", dto);
        return PATH + "sys_test_input";
    }


    /**
     * 保存
     *
     * @param dto 大巴实体
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") SysTest dto, HttpServletRequest request) {
        try {
            //当前登录用户
            dto.setVcode(getVcode(request));
            if (dto.getId() == null) {
                sysTestService.save(dto);
            } else {
                sysTestService.update(dto);
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
            sysTestService.delete(ids);
        } catch (SubSetNotNullException e) {
            LOGGER.error("删除失败", e);
            return builderFailed(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
