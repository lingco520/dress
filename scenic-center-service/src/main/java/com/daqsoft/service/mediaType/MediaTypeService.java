/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.mediaType;

import com.daqsoft.entity.mediaType.ResMediaType;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.MediaTypeMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: MediaTypeService
 * @Author: superziy
 * @Date: 2018/01/10 17:00
 * @Description: TODO
 * @Comment：
 * @Version: 多媒体类型配置信息
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class MediaTypeService {

    @Autowired
    private MediaTypeMapper mediaTypeMapper;

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
        List<ResMediaType> list = mediaTypeMapper.selectList(parmMap);
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
    public ResMediaType get(Long id) throws Exception {
        return mediaTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据保存
     * @param dto
     * @throws Exception
     */
    public void save(ResMediaType dto) throws Exception {
        mediaTypeMapper.insert(dto);
    }

    /**
     * 数据更新
     * @param dto
     * @throws Exception
     */
    public void update(ResMediaType dto) throws Exception {
        mediaTypeMapper.updateByPrimaryKey(dto);
    }

    /**
     * 单条和批量删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            mediaTypeMapper.deleteByPrimaryKey(id);
        }
    }
}
