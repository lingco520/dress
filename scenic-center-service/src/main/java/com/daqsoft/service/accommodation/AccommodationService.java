/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.accommodation;

import com.daqsoft.entity.acommodation.ResAccommodation;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResAccommodationMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: 酒店宾馆Service
 * @Author: lyl
 * @Date: 2018/01/12 17:50
 * @Description: TODO
 * @Comment：
 * @Version: 4.0.0
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
    public class AccommodationService {

    @Autowired
    private ResAccommodationMapper resAccommodationMapper;

    public PageForm list(PageForm pageForm, String name,String vcode) throws Exception {
        Map parmMap = new HashMap();
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        parmMap.put("vcode",vcode);
        parmMap.put("name",name);
        List<ResAccommodation> list = resAccommodationMapper.selectList(parmMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    /**
     * 单条数据获取
     *
     * @param id
     * @return
     * @throws Exception
     */
    public ResAccommodation get(Long id) throws Exception {
        return resAccommodationMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据保存
     *
     * @param dto
     * @throws Exception
     */
    public void save(ResAccommodation dto) throws Exception {
        resAccommodationMapper.insert(dto);
    }

    /**
     * 数据更新
     * @param dto
     * @throws Exception
     */
    public void update(ResAccommodation dto) throws Exception {
        resAccommodationMapper.updateByPrimaryKey(dto);
    }


    /**
     * 单条和批量删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            resAccommodationMapper.deleteByPrimaryKey(id);
        }
    }

}
