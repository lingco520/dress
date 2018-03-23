package com.daqsoft.support;

import com.daqsoft.framework.entity.SysUser;
import com.daqsoft.framework.service.SysUserServiceImpl;
import com.daqsoft.framework.support.UserDetailsSupportProvider;
import com.daqsoft.framework.support.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDetailsSupportProviderImpl implements UserDetailsSupportProvider {

    private final SysUserServiceImpl userService;

    @Autowired
    public UserDetailsSupportProviderImpl(SysUserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public UserContext create(String username) {
        SysUser user = userService.getByAccount (username);
        return new UserContext(
                user.getAccount (),
                user.getPassword (),
                user.getRoles ().stream ().map (role ->
                        new SimpleGrantedAuthority (role.getCode ())).collect(Collectors.toList()));
    }
}
