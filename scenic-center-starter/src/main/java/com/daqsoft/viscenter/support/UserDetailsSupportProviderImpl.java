package com.daqsoft.viscenter.support;

import com.daqsoft.framework.entity.SysRole;
import com.daqsoft.framework.entity.SysUser;
import com.daqsoft.framework.service.SysUserServiceImpl;
import com.daqsoft.framework.support.UserDetailsSupportProvider;
import com.daqsoft.framework.support.model.UserContext;
import com.daqsoft.framework.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class UserDetailsSupportProviderImpl implements UserDetailsSupportProvider {

    private final SysUserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsSupportProviderImpl(SysUserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public UserContext create(String username) {
        SysUser user;
        try {
            user = userService.getByAccount (username);
        }catch (Exception e) {
            user = new SysUser ();
            user.setAccount (username);
            user.setName (username);
            user.setPassword (passwordEncoder.encode ("daqsoft"));
            user.setStatus (1L);
            user.setValidity (DateUtils.addYear (new Date(),1));
            userService.save (user);
        }
        return new UserContext(
                user.getAccount (),
                user.getPassword (),
                user.getRoles ().stream ().map(SysRole::getCode).map (SimpleGrantedAuthority::new).collect(Collectors.toList()));

    }
}
