package com.daqsoft.controller.app.login;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.entity.app.login.AppPermission;
import com.daqsoft.entity.app.login.SysUserScenic;
import com.daqsoft.service.app.login.AppPermissionService;
import com.daqsoft.service.app.login.AppRoleService;
import com.daqsoft.service.app.login.ScenicRelationService;
import com.daqsoft.validator.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiongj
 * @since 1.0.0
 */
@Controller
@RequestMapping("/rest/app")
public class PermissionRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAppRestController.class);

    @Autowired
    private AppRoleService appRoleService;

    @Autowired
    private AppPermissionService appPermissionService;

    @Autowired
    private ScenicRelationService scenicRelationService;

    /**
     * 根据权限代码获取下级权限
     *
     * @param userId         用户id
     * @param permissionCode 权限代码
     * @return
     */
    @RequestMapping(value = "/getPermissionByParentCode", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getPermissionByParentCode(Long userId, String permissionCode) {
        ResponseBuilder builder = ResponseBuilder.custom();
        Assert.isNull(userId, "用户ID不能为空");
        Assert.isBlank(permissionCode, "权限代码不能为空");
        SysUserScenic loginUser = scenicRelationService.get(userId);
        if (loginUser == null) {
            return builder.failed("找不到用户").build();
        }
        try {
            List<Map> result = new ArrayList<>();
            AppPermission permission = appPermissionService.findByCode(permissionCode);
            if (permission == null) {
                return builder.failed("找不到CODE为" + permissionCode + "的权限").build();
            } else {
                List<Long> rolePermission = appRoleService.getRolePermission(loginUser.getAppUserRole());
                Map parMap = new HashMap();
                parMap.put("status", 1);
                parMap.put("pid", permission.getId());
                List<AppPermission> appPermissions = appPermissionService.selectList(parMap);
                for (AppPermission appPermission : appPermissions) {
                    for (Long id : rolePermission) {
                        if (appPermission.getId().equals(id)) {
                            Map map = new HashMap();
                            map.put("id", id);
                            map.put("name", appPermission.getName());
                            map.put("code", appPermission.getCode());
                            map.put("level", appPermission.getNlevel());
                            result.add(map);
                            break;
                        }
                    }
                }
                return builder.success().data(result).build();
            }
        } catch (Exception e) {
            LOGGER.error("查询错误:", e);
            return builder.failed("查询错误").build();
        }
    }
}
