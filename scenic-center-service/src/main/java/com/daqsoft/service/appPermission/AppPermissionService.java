package com.daqsoft.service.appPermission;

import com.daqsoft.entity.appPermission.AppPermission;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.control.AppPermissionMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * app权限实现类
 */
@Service
public class AppPermissionService {

    @Autowired
    private AppPermissionMapper appPermissionMapper;


    public PageForm list(PageForm pageForm, String name) {
        Map parMap = new HashMap();
        parMap.put("name", name);
        // PageForm是查询对象，包括分页数据，查询时，xml中 SQL 不用再去拼接 limit 分页参数
        // 下面这句参数中，如果涉及分页表格的数据，count参数值必须是 true，这样才会有 总记录数存在，如果传入 false，则total总记录数是不会查询，PageHelper返回是 -1
        // 当只需要取列表数据list时，又想查询分页条数的记录，则可以传入 false
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<AppPermission> list = appPermissionMapper.selectList(parMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    public List<AppPermission> selectList(Map map) {
        return appPermissionMapper.selectList(map);
    }

    public AppPermission get(Long id) {
        return appPermissionMapper.selectByPrimaryKey(id);
    }

    public void save(AppPermission ddo) {
        appPermissionMapper.insert(ddo);
        appPermissionMapper.callPermissionTree();
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
            appPermissionMapper.deleteByPrimaryKey(id);
        }
        appPermissionMapper.callPermissionTree();
    }

    public void update(AppPermission dto) {
        appPermissionMapper.updateByPrimaryKey(dto);
        //更新树
        appPermissionMapper.callPermissionTree();
        //下级权限状态跟着父级权限状态改变.
        appPermissionMapper.updateSubStatus(dto);

    }

    /**
     * 查找符合条件的上级权限
     *
     * @param dto
     * @return
     */
    public List<AppPermission> validParentList(AppPermission dto) {
        Map parMap = new HashMap();
        parMap.put("status", 1);//状态正常
        //能选择的父级权限等级应该小于4,且不能高于当前实体的等级.
        parMap.put("ltNlevel", dto.getNlevel() == null ? 4 : dto.getNlevel());
        parMap.put("idNot", dto.getId());//不能选择自身作为上级.
        return appPermissionMapper.selectList(parMap);
    }

    /**
     * 获取有效的权限树
     * simple true 简单结构,返回所有数据,无层级,只返回下级数量
     * false 复杂结构,返回顶层数据,有下级
     *
     * @return
     */
    public List<Map> getTreeOfPermission(boolean simple) {

        List<Map> result = new ArrayList<>();
        Map parMap = new HashMap();
        parMap.put("status", 1);
        List<AppPermission> appPermissions = appPermissionMapper.selectList(parMap);

        for (AppPermission permission : appPermissions) {
            if (simple ? simple : permission.getPid() == null) {
                Map<String, Object> mapArr = new LinkedHashMap<String, Object>();
                mapArr.put("id", permission.getId());
                mapArr.put("name", permission.getName());
                mapArr.put("code", permission.getCode());
                mapArr.put("level", permission.getNlevel());
                mapArr.put("pid", permission.getPid());
                List<Map> child = permissionChild(appPermissions, permission.getId());
                mapArr.put("child", simple ? child.size() : child);
                result.add(mapArr);
            }
        }

        return result;
    }

    /**
     * 递归查询权限下级
     *
     * @param appPermissions
     * @param pid
     * @return
     */
    private List<Map> permissionChild(List<AppPermission> appPermissions, Long pid) {
        List<Map> result = new ArrayList<>();
        for (AppPermission permission : appPermissions) {
            Map<String, Object> childArray = new LinkedHashMap<String, Object>();
            if (permission.getPid() == pid) {
                childArray.put("id", permission.getId());
                childArray.put("name", permission.getName());
                childArray.put("code", permission.getCode());
                childArray.put("level", permission.getNlevel());
                childArray.put("pid", permission.getPid());
                childArray.put("child", permissionChild(appPermissions, permission.getId()));
                result.add(childArray);
            }
        }
        return result;
    }

    public AppPermission findByCode(String permissionCode) {
        return appPermissionMapper.findByCode(permissionCode);
    }
}
