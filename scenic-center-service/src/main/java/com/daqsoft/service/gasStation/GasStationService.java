/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.gasStation;

import com.daqsoft.entity.gasStation.GasStation;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResGasStationMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: GasService
 * @Author: AH
 * @Date: 2018/01/20 9:58
 * @Description: 加油站
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Service
public class GasStationService {

    @Autowired
    private ResGasStationMapper gasMapper;

    public PageForm list(PageForm pageForm, String name, String address) throws Exception{
        Map parMap = new HashMap<>();
        parMap.put("name", name);
        parMap.put("vcode", pageForm.getVcode());
        parMap.put("address", address);
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<GasStation> list = gasMapper.selectList(parMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    public GasStation get(Long id) throws Exception{
        return gasMapper.selectByPrimaryKey(id);
    }

    public void save(GasStation dto) throws Exception{
        gasMapper.insert(dto);
    }

    public void update(GasStation dto) throws Exception{
        gasMapper.updateByPrimaryKey(dto);
    }

    public void delete(Long[] ids) throws Exception{
        for (Long id : ids){
            gasMapper.deleteByPrimaryKey(id);
        }
    }
}
