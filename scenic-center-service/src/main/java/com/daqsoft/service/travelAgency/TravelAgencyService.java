/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.travelAgency;

import com.daqsoft.entity.travelAgency.ResTravelAgency;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.TravelAgencyMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: TravelAgencyService
 * @Author: superziy
 * @Date: 2018/01/19 17:00
 * @Description: TODO
 * @Comment：
 * @Version: 旅行社
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class TravelAgencyService {

    @Autowired
    private TravelAgencyMapper travelAgencyMapper;

    /**
     * 列表数据
     * @param pageForm
     * @param vcode
     * @param status
     * @return
     * @throws Exception
     */
    public PageForm list(PageForm pageForm, String name,String vcode,String status) throws Exception {
        Map parmMap = new HashMap();
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        parmMap.put("vcode",vcode);
        parmMap.put("name",name);
        parmMap.put("status",status);
        List<ResTravelAgency> list = travelAgencyMapper.selectList(parmMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    /**
     * 单条数据获取
     * @param id
     * @return
     * @throws Exception
     */
    public ResTravelAgency get(Long id) throws Exception {
        return travelAgencyMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据保存
     * @param dto
     * @throws Exception
     */
    public void save(ResTravelAgency dto) throws Exception {
        travelAgencyMapper.insert(dto);
    }

    /**
     * 数据更新
     * @param dto
     * @throws Exception
     */
    public void update(ResTravelAgency dto) throws Exception {
        travelAgencyMapper.updateByPrimaryKey(dto);
    }

    /**
     * 批量和单条删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            travelAgencyMapper.deleteByPrimaryKey(id);
        }
    }
}
