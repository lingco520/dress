/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.gisModuleConfig;

import com.daqsoft.entity.gisMapTypeManage.ResGisMapTypeManage;
import com.daqsoft.entity.gisModuleConfig.ResGisModuleConfig;
import com.daqsoft.entity.gisModuleManage.ResGisModuleManage;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResGisModuleConfigMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: GisModuleConfigService
 * @Author: superziy
 * @Date: 2018/02/05 17:00
 * @Description: TODO
 * @Comment：
 * @Version: gis资源模块配置
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class GisModuleConfigService {

    @Autowired
    private ResGisModuleConfigMapper resGisModuleConfigMapper;

    /**
     * 列表查询
     *
     * @param pageForm
     * @param name
     * @param vcode
     * @param status
     * @return
     * @throws Exception
     */
    public PageForm list(PageForm pageForm, String name, String vcode, String status) throws Exception {
        Map parmMap = new HashMap();
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        parmMap.put("name", name);
        parmMap.put("status", status);
        parmMap.put("vcode", vcode);
        List<ResGisModuleConfig> list = resGisModuleConfigMapper.selectList(parmMap);
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
    public ResGisModuleConfig get(Long id) throws Exception {
        return resGisModuleConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据保存
     *
     * @param dto
     * @throws Exception
     */
    public void save(ResGisModuleConfig dto) throws Exception {
        resGisModuleConfigMapper.insert(dto);
    }

    /**
     * 数据更新
     *
     * @param dto
     * @throws Exception
     */
    public void update(ResGisModuleConfig dto) throws Exception {
        resGisModuleConfigMapper.updateByPrimaryKey(dto);
    }

    /**
     * 批量和单条删除
     *
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            resGisModuleConfigMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 验证是否重复
     *
     * @param gisName
     * @throws Exception
     */
    public Integer isCheckGisName(String gisName) throws Exception {
        return resGisModuleConfigMapper.isCheckGisName(gisName);
    }

    /**
     * 所有模块
     *
     * @throws Exception
     */
    public List<ResGisModuleManage> getGisModule() throws Exception {
        return resGisModuleConfigMapper.getGisModule();
    }

    /**
     * 地图类型
     *
     * @throws Exception
     */
    public List<ResGisMapTypeManage> getGisMapType() throws Exception {
        return resGisModuleConfigMapper.getGisMapType();
    }

    /**
     * 查询gis模块名称
     *
     * @throws Exception
     */
    public ResGisModuleManage getGisName(String id) throws Exception {
        return resGisModuleConfigMapper.getGisName(id);
    }

    /**
     * 查询模块id是否已经有模块
     *
     * @throws Exception
     */
    public Integer getIsTrue(Map map) throws Exception {
        return resGisModuleConfigMapper.getIsTrue(map);
    }

    /**
     * 查询gis模块名称
     *
     * @throws Exception
     */
    public void removeGisMoudle(String vcode) throws Exception {
        resGisModuleConfigMapper.removeGisMoudle(vcode);
    }

    /**
     * 数据排序更新
     *
     * @param dto
     * @throws Exception
     */
    public void updateSort(ResGisModuleConfig dto) throws Exception {
        resGisModuleConfigMapper.updateSort(dto);
    }
}
