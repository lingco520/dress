/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.guide;

import com.daqsoft.entity.guide.ResGuide;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResGuideMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: GuideService
 * @Author: tanggm
 * @Date: 2018/01/09 17:00
 * @Description: TODO 导游
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Transactional
@Service
public class GuideService {

    @Autowired
    private ResGuideMapper resGuideMapper;

    /**
     * 列表
     * @param pageForm
     * @param name
     * @param guideLevel
     * @param gender
     * @return
     * @throws Exception
     */
    public PageForm list(PageForm pageForm, String name, String guideLevel, String gender, Integer status) throws Exception{
        Map parMap = new HashMap<>();
        parMap.put("name", name);
        parMap.put("vcode", pageForm.getVcode());
        parMap.put("guideLevel", guideLevel);
        parMap.put("gender", gender);
        parMap.put("status", status);
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<ResGuide> list = resGuideMapper.selectList(parMap);
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
    public ResGuide get(Long id) throws Exception{
        return resGuideMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     * @param dto
     * @throws Exception
     */
    public void save(ResGuide dto) throws Exception{
        resGuideMapper.insert(dto);
    }

    /**
     * 更新
     * @param dto
     * @throws Exception
     */
    public void update(ResGuide dto) throws Exception{
        resGuideMapper.updateByPrimaryKey(dto);
    }

    /**
     * 删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception{
        for (Long id : ids){
            resGuideMapper.deleteByPrimaryKey(id);
        }
    }
}
