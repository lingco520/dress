package com.daqsoft.controller.app;

import com.alibaba.fastjson.JSONObject;
import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.appPermission.AppRole;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.appPermission.AppPermissionService;
import com.daqsoft.service.appPermission.AppRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * app角色管理器
 */
@Controller
@RequestMapping(value = "/appRole")
public class AppRoleController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppRoleController.class);
    private static final String PATH = "appRole/";
    @Autowired
    private AppRoleService appRoleService;

    @Autowired
    private AppPermissionService appPermissionService;

    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list() {
        return PATH + "app_role_list";
    }

    /**
     * 列表数据
     *
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, HttpServletRequest request) {
        try {
            pageForm.setVcode(getVcode(request));
            return pageBuilderSuccess(appRoleService.list(pageForm));
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
    public String edit(Long id, ModelMap model) {
        AppRole dto = new AppRole();
        List<Long> rolePermissionList = new ArrayList<>();
        try {
            if (id != null) {
                dto = appRoleService.get(id);
                rolePermissionList = appRoleService.getRolePermission(id);
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }
        List<Map> treeOfPermission = appPermissionService.getTreeOfPermission(true);
        //权限树
        model.addAttribute("tree", JSONObject.toJSONString(treeOfPermission));
        //当前角色已有的权限
        model.addAttribute("rolePermissionList", JSONObject.toJSONString(rolePermissionList));
        model.addAttribute("dto", dto);
        return PATH + "app_role_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") AppRole dto, String permissionId, HttpServletRequest request) {
        try {
            if (permissionId != null && !"".equals(permissionId)) {
                dto.setUser(getAccount(request));
                //当前登录用户
                if (dto.getId() == null) {
                    appRoleService.save(dto);
                    //添加app角色与权限的关系
                    appRoleService.addRolePermission(dto.getId(), permissionId);
                } else {
                    appRoleService.update(dto);
                    //删除app角色与权限的关系
                    appRoleService.deleteRolePermissionByRoleId(dto.getId());
                    //添加app角色与权限的关系
                    appRoleService.addRolePermission(dto.getId(), permissionId);
                }
            } else {
                return builderFailed("请选择权限.");
            }
        } catch (DuplicateKeyException e) {
            return builderFailed("已存在CODE为" + dto.getCode() + "的角色");
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
            appRoleService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
