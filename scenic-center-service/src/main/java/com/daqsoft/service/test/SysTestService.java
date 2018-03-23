package com.daqsoft.service.test;

import com.daqsoft.entity.test.SysTest;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.control.SysTestMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysTestService {

    @Autowired
    private SysTestMapper sysTestMapper;
    public PageForm list(PageForm pageForm, String name) {
        Map parMap = new HashMap();
        parMap.put("name", name);
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<SysTest> list = sysTestMapper.selectList(parMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    public SysTest get(Long id) {
        return sysTestMapper.selectByPrimaryKey(id);
    }

    public void save(SysTest ddo) {
        sysTestMapper.insert(ddo);
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
            sysTestMapper.deleteByPrimaryKey(id);
        }
    }

    public void update(SysTest dto) {
        sysTestMapper.updateByPrimaryKey(dto);
    }
}
