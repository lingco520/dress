package com.daqsoft.service.dictByTypeService;

import com.daqsoft.entity.scenicEntity.SysUserScenic;
import com.daqsoft.mapper.control.ScenicRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DictByTypeService {

    @Autowired
    private ScenicRelationMapper scenicRelationMapper;

    public SysUserScenic get(Long id) {
        return scenicRelationMapper.selectByPrimaryKey(id);
    }

}
