/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.gisClickManage;

import com.daqsoft.entity.gisClickManage.ResGisClickManage;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResGisClickManageMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: GisClickManageService
 * @Author: superziy
 * @Date: 2018/02/06 17:00
 * @Description: TODO
 * @Comment：
 * @Version: gis点击类型管理
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class GisClickManageService {

    @Autowired
    private ResGisClickManageMapper resGisClickManageMapper;

    /**
     * 列表查询
     *
     * @param pageForm
     * @param name
     * @param status
     * @return
     * @throws Exception
     */
    public PageForm list(PageForm pageForm, String name,String status) throws Exception {
        Map parmMap = new HashMap();
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        parmMap.put("name",name);
        parmMap.put("status",status);
        List<ResGisClickManage> list = resGisClickManageMapper.selectList(parmMap);
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
    public ResGisClickManage get(Long id) throws Exception {
        return resGisClickManageMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据保存
     *
     * @param dto
     * @throws Exception
     */
    public void save(ResGisClickManage dto) throws Exception {
        resGisClickManageMapper.insert(dto);
    }

    /**
     * 数据更新
     * @param dto
     * @throws Exception
     */
    public void update(ResGisClickManage dto) throws Exception {
        resGisClickManageMapper.updateByPrimaryKey(dto);
    }

    /**
     * 批量和单条删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            resGisClickManageMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 验证类型是否重复
     * @param gisName
     * @throws Exception
     */
    public Integer checkGisClickName(String gisName) throws Exception {
          return  resGisClickManageMapper.checkGisClickName(gisName);
    }
}
