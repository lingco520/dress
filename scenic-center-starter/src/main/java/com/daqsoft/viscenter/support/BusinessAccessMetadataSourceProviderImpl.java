package com.daqsoft.viscenter.support;

import com.daqsoft.framework.authentication.BusinessAccessMetadataSourceProvider;
import com.daqsoft.framework.service.SysOperateServiceImpl;
import com.daqsoft.framework.support.AuthorityConfig;
import com.daqsoft.framework.util.ReflectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 本类是权限控制业务数据提供器.
 * @author ANONYM
 */
@Component
public class BusinessAccessMetadataSourceProviderImpl implements BusinessAccessMetadataSourceProvider {

    private final SysOperateServiceImpl operateService;

    @Autowired
    public BusinessAccessMetadataSourceProviderImpl(SysOperateServiceImpl operateService) {
        this.operateService = operateService;
    }

    @Override
    public List<AuthorityConfig> loadResourceDefine() {
        return operateService.queryForAuthorization ().stream ().map
                (map -> ReflectUtils.map2Pojo (map,AuthorityConfig.class)).collect (Collectors.toList ());
    }
}
