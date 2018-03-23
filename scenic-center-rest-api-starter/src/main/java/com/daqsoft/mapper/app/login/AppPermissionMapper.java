package com.daqsoft.mapper.app.login;

import com.daqsoft.entity.app.login.AppPermission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AppPermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AppPermission record);

    AppPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKey(AppPermission record);

    List<AppPermission> selectList(Map parMap);

    /**
     * 存储过程更新树结构.
     */
    void callPermissionTree();

    /**
     * 下级权限状态跟着父级权限状态改变.
     *
     * @param dto
     */
    void updateSubStatus(AppPermission dto);

    AppPermission findByCode(String code);
}