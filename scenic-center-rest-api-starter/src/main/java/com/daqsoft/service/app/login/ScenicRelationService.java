package com.daqsoft.service.app.login;

import com.daqsoft.entity.app.login.SysUserScenic;
import com.daqsoft.mapper.app.login.ScenicRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ScenicRelationService {

    @Autowired
    private ScenicRelationMapper scenicRelationMapper;


    public SysUserScenic get(Long id) {
        return scenicRelationMapper.selectByPrimaryKey(id);
    }

    /**
     *  根据上级创建者查询vcode
     * @param creater
     * @return
     */
    public String getCreaterVcode(Long creater) {
        return scenicRelationMapper.getCreaterVcode(creater);
    }

    public SysUserScenic getUserByAccount(String account) {
        return scenicRelationMapper.getUserByAccount(account);
    }

}
