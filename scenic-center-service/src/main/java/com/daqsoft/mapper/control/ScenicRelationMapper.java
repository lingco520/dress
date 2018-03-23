package com.daqsoft.mapper.control;

import com.daqsoft.entity.scenicEntity.SysUserScenic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScenicRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserScenic record);

    SysUserScenic selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SysUserScenic record);

    List<SysUserScenic> selectList(Map parMap);

    /**
     * 根据上级创建者查询vcode
     *
     * @param creater
     * @return
     */
    String getCreaterVcode(long creater);

    /**
     * 通过账号获取登录用户  唯一
     * @param account
     * @return
     */
    SysUserScenic getUserByAccount(String account);

}