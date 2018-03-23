package com.daqsoft.service.app.login;

import com.daqsoft.entity.app.login.AppRole;
import com.daqsoft.mapper.app.login.AppRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * App角色管理服务类
 */
@Service
public class AppRoleService {

    @Autowired
    private AppRoleMapper resAppRoleMapper;


    public AppRole get(Long id) throws Exception {
        return resAppRoleMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取角色已有的权限
     *
     * @param roleId
     * @return
     */
    public List<Long> getRolePermission(Long roleId) {
        return resAppRoleMapper.getRolePermission(roleId);
    }

    /**
     * 删除角色的权限
     * @param roleId
     */
    public void deleteRolePermissionByRoleId(Long roleId) {
        resAppRoleMapper.deleteRolePermissionByRoleId(roleId);
    }

    /**
     * 添加app角色的权限关系
     * @param roleId
     * @param permissionIds
     */
    public void addRolePermission(Long roleId, String permissionIds) {
        Map parMap = new HashMap<>();
        parMap.put("roleId", roleId);
        String[] permissionids = permissionIds.split(",");
        for (String permissionid : permissionids) {
            parMap.put("permissionId", permissionid);
            resAppRoleMapper.addRolePermission(parMap);
        }
    }

    /**
     * 获取所有app角色
     * @return
     */
    public List<AppRole> findAllValidData() {
        Map parMap = new HashMap<>();
        return resAppRoleMapper.selectList(parMap);
    }
}
