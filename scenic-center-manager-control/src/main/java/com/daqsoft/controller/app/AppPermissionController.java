package com.daqsoft.controller.app;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.appPermission.AppPermission;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.appPermission.AppPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * app权限管理器
 */
@Controller
@RequestMapping(value = "/appPermission")
public class AppPermissionController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppPermissionController.class);
    private static final String PATH = "appPermission/";
    @Autowired
    private AppPermissionService appPermissionService;

    /**
     * 返回app权限信息页面,页面通过ajax再请求数据
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(HttpServletRequest request) {
        return PATH + "app_permission_list";
    }

    /**
     * app权限列表
     *
     * @param name 账号
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name) {
        return pageBuilderSuccess(appPermissionService.list(pageForm, name));
    }

    /**
     * 编辑
     *
     * @param id    数据id
     * @param model 数据对象
     * @return 新增/编辑页面
     */
    @GetMapping(Constants.EDITACTION)
    public String edit(Long id, ModelMap model) {

        AppPermission dto;
        if (id != null) {
            dto = appPermissionService.get(id);
        } else {
            dto = new AppPermission();
        }
        List<AppPermission> validParentList = appPermissionService.validParentList(dto);
        //父级
        model.addAttribute("appPermissions", validParentList);
        model.addAttribute("dto", dto);
        return PATH + "app_permission_input";
    }


    /**
     * 保存
     *
     * @param dto 实体
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") AppPermission dto) {
        try {
            if (dto.getId() == null) {
                appPermissionService.save(dto);
            } else {
                appPermissionService.update(dto);
            }
        } catch (DuplicateKeyException e) {
            return builderFailed("已存在CODE为" + dto.getCode() + "的权限");
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
            appPermissionService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!存在下级权限或已绑定在app角色中");
        }
        return builderSuccess("删除成功");
    }
}
