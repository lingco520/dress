package com.daqsoft.service.test;

import com.daqsoft.entity.test.BusTest;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.control.BusTestMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BusTestService{

    @Autowired
    private BusTestMapper busTestMapper;
    public List<Map<String, Object>> getDictsByType(String carLevel) {
        return busTestMapper.getDictsByType(carLevel);
    }

    public PageForm list(PageForm pageForm, String name, String level, String type, String peopleNum) {
        Map parMap = new HashMap();
        if (!StringUtils.isEmpty(peopleNum)){
            String[] numArr = peopleNum.split("-");
            parMap.put("peopleNumStart", numArr[0]);
            parMap.put("peopleNumEnd", numArr[1]);
        }
        parMap.put("name", name);
        parMap.put("level", level);
        parMap.put("type", type);
        // PageForm是查询对象，包括分页数据，查询时，xml中 SQL 不用再去拼接 limit 分页参数
        // 下面这句参数中，如果涉及分页表格的数据，count参数值必须是 true，这样才会有 总记录数存在，如果传入 false，则total总记录数是不会查询，PageHelper返回是 -1
        // 当只需要取列表数据list时，又想查询分页条数的记录，则可以传入 false
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<BusTest> list = busTestMapper.selectList(parMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    public BusTest get(Long id) {
        return busTestMapper.selectByPrimaryKey(id);
    }

    public void save(BusTest ddo) {
        busTestMapper.insert(ddo);
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
            busTestMapper.deleteByPrimaryKey(id);
        }
    }

    public void update(BusTest dto) {
        busTestMapper.updateByPrimaryKey(dto);
    }
}
