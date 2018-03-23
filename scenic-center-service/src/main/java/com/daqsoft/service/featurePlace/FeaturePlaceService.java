/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.featurePlace;

import com.daqsoft.entity.featurePlace.ResFeaturePlace;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResFeaturePlaceMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Title: FeaturePlaceService
 * @Author: tanggm
 * @Date: 2018/01/22 15:30
 * @Description: TODO 基础资源-特色旅游场所
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Service
@Transactional
public class FeaturePlaceService {

    @Autowired
    private ResFeaturePlaceMapper resFeaturePlaceMapper;

    /**
     * 列表
     * @param pageForm
     * @param name
     * @return
     * @throws Exception
     */
    public PageForm list(PageForm pageForm, String name, Integer status) throws Exception{
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<ResFeaturePlace> list = resFeaturePlaceMapper.selectList(name, pageForm.getVcode(), status);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    /**
     * 详情
     * @param id
     * @return
     * @throws Exception
     */
    public ResFeaturePlace get(Long id) throws Exception{
        return resFeaturePlaceMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     * @param dto
     * @throws Exception
     */
    public void save(ResFeaturePlace dto) throws Exception{
        resFeaturePlaceMapper.insert(dto);
    }

    /**
     * 更新
     * @param dto
     * @throws Exception
     */
    public void update(ResFeaturePlace dto) throws Exception{
        resFeaturePlaceMapper.updateByPrimaryKey(dto);
    }

    /**
     * 删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception{
        for (Long id : ids){
            resFeaturePlaceMapper.deleteByPrimaryKey(id);
        }
    }
}
