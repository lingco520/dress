package com.daqsoft.service.appPermission;

import com.daqsoft.entity.appPermission.AppRole;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.control.AppRoleMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    public PageForm list(PageForm pageForm) throws Exception {
        Map parMap = new HashMap<>();
        parMap.put("keyword", pageForm.getKeyword());
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<AppRole> list = resAppRoleMapper.selectList(parMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    public AppRole get(Long id) throws Exception {
        return resAppRoleMapper.selectByPrimaryKey(id);
    }

    public void save(AppRole dto) throws Exception {
        resAppRoleMapper.insert(dto);
    }

    public void update(AppRole dto) throws Exception {
        resAppRoleMapper.updateByPrimaryKey(dto);
    }

    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            resAppRoleMapper.deleteByPrimaryKey(id);
        }
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
     *
     * @param roleId
     */
    public void deleteRolePermissionByRoleId(Long roleId) {
        resAppRoleMapper.deleteRolePermissionByRoleId(roleId);
    }

    /**
     * 添加app角色的权限关系
     *
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
     *
     * @return
     */
    public List<AppRole> findAllValidData() {
        Map parMap = new HashMap<>();
        return resAppRoleMapper.selectList(parMap);
    }
}
