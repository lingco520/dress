package com.daqsoft.mapper.app.login;

import com.daqsoft.entity.app.login.AppRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * app角色管理
 */
@Repository
public interface AppRoleMapper {
    /**
     * 查询列表
     *
     * @param map 参数
     * @return
     */
    List<AppRole> selectList(Map map);

    /**
     * 获取角色已有的权限
     *
     * @param roleId
     * @return
     */
    List<Long> getRolePermission(Long roleId);

    /**
     * 删除角色的权限
     * @param roleId
     */
    void deleteRolePermissionByRoleId(Long roleId);

    /**
     * 添加app角色的权限关系
     * @param map
     */
    void addRolePermission(Map map);

    AppRole selectByPrimaryKey(Long id);
}