/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.hospital;

import com.daqsoft.entity.hospital.Hospital;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResHospitalMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: HospitalService
 * @Author: lrd
 * @Date: 2018/01/20 9:58
 * @Description: 医疗点
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Service
public class HospitalService {

    @Autowired
    private ResHospitalMapper resHospitalMapper;

    public PageForm list(PageForm pageForm, String name, String address) throws Exception{
        Map parMap = new HashMap<>();
        parMap.put("name", name);
        parMap.put("vcode", pageForm.getVcode());
        parMap.put("address", address);
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<Hospital> list = resHospitalMapper.selectList(parMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    public Hospital get(Long id) throws Exception{
        return resHospitalMapper.selectByPrimaryKey(id);
    }

    public void save(Hospital dto) throws Exception{
        resHospitalMapper.insert(dto);
    }

    public void update(Hospital dto) throws Exception{
        resHospitalMapper.updateByPrimaryKey(dto);
    }

    public void delete(Long[] ids) throws Exception{
        for (Long id : ids){
            resHospitalMapper.deleteByPrimaryKey(id);
        }
    }
}
