/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.scenicGisConfig;

import com.daqsoft.entity.scenicGisConfig.ResScenicGisConfig;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResScenicGisConfigMapper;
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
 * @Date: 2018/01/30 17:00
 * @Description: TODO
 * @Comment：
 * @Version: 景区gis地图配置
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class ScenicGisConfigService {

    @Autowired
    private ResScenicGisConfigMapper resScenicGisConfigMapper;

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
        List<ResScenicGisConfig> list = resScenicGisConfigMapper.selectList(parmMap);
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
    public ResScenicGisConfig get(Long id) throws Exception {
        return resScenicGisConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据保存
     *
     * @param dto
     * @throws Exception
     */
    public void save(ResScenicGisConfig dto) throws Exception {
        resScenicGisConfigMapper.insert(dto);
    }

    /**
     * 数据更新
     *
     * @param dto
     * @throws Exception
     */
    public void update(ResScenicGisConfig dto) throws Exception {
        resScenicGisConfigMapper.updateByPrimaryKey(dto);
    }

    /**
     * 批量和单条删除
     *
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            resScenicGisConfigMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 是否有gis地图
     *
     * @param vcode
     * @throws Exception
     */
    public Integer getIsGis(String vcode) throws Exception {
      return resScenicGisConfigMapper.getIsGis(vcode);
    }

}
