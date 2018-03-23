/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.roadTraffic;

import com.daqsoft.entity.roadTraffic.ResScenicRoadTraffic;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResScenicRoadTrafficMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: RoadTrafficService
 * @Author: lyl
 * @Date: 2018/03/8 16:37
 * @Description: TODO
 * @Comment：
 * @Version: 道路交通
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class RoadTrafficService {

    @Autowired
    private ResScenicRoadTrafficMapper resScenicRoadTrafficMapper;

    /**
     * 列表查询
     *
     * @param pageForm
     * @param name
     * @param vcode
     * @return
     * @throws Exception
     */
    public PageForm list(PageForm pageForm, String name,String vcode) throws Exception {
        Map parmMap = new HashMap();
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        parmMap.put("vcode",vcode);
        parmMap.put("name",name);
        List<ResScenicRoadTraffic> list = resScenicRoadTrafficMapper.selectList(parmMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    /**
     * 列表查询
     *
     * @param vcode
     * @return
     */
    public List<ResScenicRoadTraffic> list(String vcode) {
        Map parmMap = new HashMap();
        parmMap.put("vcode", vcode);
        return resScenicRoadTrafficMapper.selectList(parmMap);
    }

    /**
     * 单条数据获取
     *
     * @param id
     * @return
     * @throws Exception
     */
    public ResScenicRoadTraffic get(Long id) throws Exception {
        return resScenicRoadTrafficMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据保存
     *
     * @param dto
     * @throws Exception
     */
    public void save(ResScenicRoadTraffic dto) throws Exception {
        resScenicRoadTrafficMapper.insert(dto);
    }

    /**
     * 数据更新
     * @param dto
     * @throws Exception
     */
    public void update(ResScenicRoadTraffic dto) throws Exception {
        resScenicRoadTrafficMapper.updateByPrimaryKey(dto);
    }

    /**
     * 批量和单条删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            resScenicRoadTrafficMapper.deleteByPrimaryKey(id);
        }
    }
}
