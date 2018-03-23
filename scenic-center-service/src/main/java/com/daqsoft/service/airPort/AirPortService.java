/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.airPort;

import com.daqsoft.entity.airPort.AirPort;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResAirPortMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: AirPortService
 * @Author: lrd
 * @Date: 2018/01/20 9:58
 * @Description: 机场
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Service
public class AirPortService {

    @Autowired
    private ResAirPortMapper airPortMapper;

    public PageForm list(PageForm pageForm, String name, String address) throws Exception{
        Map parMap = new HashMap<>();
        parMap.put("name", name);
        parMap.put("vcode", pageForm.getVcode());
        parMap.put("address", address);
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<AirPort> list = airPortMapper.selectList(parMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    public AirPort get(Long id) throws Exception{
        return airPortMapper.selectByPrimaryKey(id);
    }

    public void save(AirPort dto) throws Exception{
        airPortMapper.insert(dto);
    }

    public void update(AirPort dto) throws Exception{
        airPortMapper.updateByPrimaryKey(dto);
    }

    public void delete(Long[] ids) throws Exception{
        for (Long id : ids){
            airPortMapper.deleteByPrimaryKey(id);
        }
    }
}
