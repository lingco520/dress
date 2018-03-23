/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.scenicComfort;

import com.daqsoft.entity.scenicComfort.ResScenicComfort;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResScenicComfortMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: ScenicComfortService
 * @Author: superziy
 * @Date: 2018/01/10 17:00
 * @Description: TODO
 * @Comment：
 * @Version: 景点舒适度配置信息
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class ScenicComfortService {

    @Autowired
    private ResScenicComfortMapper resScenicComfortMapper;

    /**
     * 列表查询
     *
     * @param pageForm
     * @param name
     * @param vcode
     * @param rating
     * @param status
     * @return
     * @throws Exception
     */
    public PageForm list(PageForm pageForm, String name, String vcode, String rating, String status) throws Exception {
        Map parmMap = new HashMap();
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        parmMap.put("vcode", vcode);
        parmMap.put("name", name);
        parmMap.put("rating", rating);
        parmMap.put("status", status);
        List<ResScenicComfort> list = resScenicComfortMapper.selectList(parmMap);
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
    public ResScenicComfort get(Long id) throws Exception {
        return resScenicComfortMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据保存
     *
     * @param dto
     * @throws Exception
     */
    public void save(ResScenicComfort dto) throws Exception {
        resScenicComfortMapper.insert(dto);
    }

    /**
     * 数据更新
     * @param dto
     * @throws Exception
     */
    public void update(ResScenicComfort dto) throws Exception {
        resScenicComfortMapper.updateByPrimaryKey(dto);
    }

    /**
     * 批量和单条删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            resScenicComfortMapper.deleteByPrimaryKey(id);
        }
    }
}
