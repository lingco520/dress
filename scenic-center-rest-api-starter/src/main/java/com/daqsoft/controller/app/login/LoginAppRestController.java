package com.daqsoft.controller.app.login;

import com.daqsoft.commons.core.StringUtil;
import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.entity.app.login.SysUserScenic;
import com.daqsoft.service.app.login.AppPermissionService;
import com.daqsoft.service.app.login.AppRoleService;
import com.daqsoft.service.app.login.ScenicRelationService;
import com.daqsoft.validator.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app登录接口
 *
 * @author xiongj
 * @since 1.0.0
 */
@Controller
@RequestMapping("/rest/app")
public class LoginAppRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAppRestController.class);

    @Autowired
    private ScenicRelationService scenicRelationService;

    @Autowired
    private AppPermissionService appPermissionService;

    @Autowired
    private AppRoleService appRoleService;

    @Autowired
    private PasswordEncoder bCryptPassword;

    /**
     * 登录账户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse Login(SysUserScenic user) {
        ResponseBuilder builder = ResponseBuilder.custom();
        String account = user.getAccount();
        String password = user.getPassword();
        Assert.isBlank(account, "请输入账号和密码");
        Assert.isBlank(password, "请输入账号和密码");
        try {
            SysUserScenic loginUser = scenicRelationService.getUserByAccount(account);
            boolean matches = this.bCryptPassword.matches(password, loginUser.getPassword());
            if (loginUser == null || !matches) {
                return builder.failed("账号或密码不正确").build();
            } else {
                String vcode = loginUser.getVcode();
                Long appUserRole = loginUser.getAppUserRole();
                String appUserType = loginUser.getAppUserType();
                if (StringUtil.isEmpty(vcode)) {
                    builder.failed("当前用户未绑定景区");
                } else if (appUserRole == null) {
                    builder.failed("当前用户未绑定app角色");
                } else if (StringUtil.isEmpty(appUserType)) {
                    builder.failed("当前用户未设置用户类型");
                } else {

                    List<Map> rolePermission = new ArrayList<>();
                    //当前用户角色拥有的权限
                    List<Long> rolePermissionId = appRoleService.getRolePermission(appUserRole);
                    List<Map> treeOfPermission = appPermissionService.getTreeOfPermission(false);
                    for (Map map : treeOfPermission) {
                        for (Long permissionId : rolePermissionId) {
                            if (String.valueOf(map.get("id")).equals(String.valueOf(permissionId))) {
                                rolePermission.add(map);
                                break;
                            }
                        }
                    }
                    if (rolePermission.size() == 0) {
                        builder.failed("当前用户无权限");
                    } else {
                        Map map = new HashMap();
                        map.put("userId", loginUser.getId());
                        map.put("vcode", vcode);
                        map.put("appUserType", appUserType);
                        map.put("rolePermission", rolePermission);
                        map.put("email", loginUser.getEmail() == null ? "" : loginUser.getEmail());
                        map.put("name", loginUser.getName() == null ? "" : loginUser.getName());
                        map.put("phone", loginUser.getPhone() == null ? "" : loginUser.getPhone());
                        builder.success().data(map);
                    }
                }
                return builder.build();
            }
        } catch (Exception e) {
            LOGGER.error("查询错误:", e);
            return builder.failed("查询错误").build();
        }
    }
}
