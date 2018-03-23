package com.daqsoft.service.scenicRelationService;

import com.daqsoft.entity.scenicEntity.SysUserScenic;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.control.ScenicRelationMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ScenicRelationService{

    @Autowired
    private ScenicRelationMapper scenicRelationMapper;


    public PageForm list(PageForm pageForm, String account) {
        Map parMap = new HashMap();
        parMap.put("account", account);
        // PageForm是查询对象，包括分页数据，查询时，xml中 SQL 不用再去拼接 limit 分页参数
        // 下面这句参数中，如果涉及分页表格的数据，count参数值必须是 true，这样才会有 总记录数存在，如果传入 false，则total总记录数是不会查询，PageHelper返回是 -1
        // 当只需要取列表数据list时，又想查询分页条数的记录，则可以传入 false
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<SysUserScenic> list = scenicRelationMapper.selectList(parMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    public SysUserScenic get(Long id) {
        return scenicRelationMapper.selectByPrimaryKey(id);
    }
    public void save(SysUserScenic ddo) {
        scenicRelationMapper.insert(ddo);
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
            scenicRelationMapper.deleteByPrimaryKey(id);
        }
    }

    public void update(SysUserScenic dto) {
        scenicRelationMapper.updateByPrimaryKey(dto);
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
