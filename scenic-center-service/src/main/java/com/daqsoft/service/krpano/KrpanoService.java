/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.krpano;

import com.daqsoft.entity.krpano.Krpano;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.MediaKrpanoMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: KrpanoService
 * @Author: lrd
 * @Date: 2018/01/20 9:58
 * @Description: 多媒体-全景
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Service
public class KrpanoService {

    @Autowired
    private MediaKrpanoMapper krpanoMapper;

    public PageForm list(PageForm pageForm, String name) throws Exception{
        Map parMap = new HashMap<>();
        parMap.put("name", name);
        parMap.put("vcode", pageForm.getVcode());
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<Krpano> list = krpanoMapper.selectList(parMap);
        pageForm.setRows(list);
        pageForm.setTotalCount(page.getTotal());
        return pageForm;
    }

    public Krpano get(Long id) throws Exception{
        return krpanoMapper.selectByPrimaryKey(id);
    }

    public void save(Krpano dto) throws Exception{
        krpanoMapper.insert(dto);
    }

    public void update(Krpano dto) throws Exception{
        krpanoMapper.updateByPrimaryKey(dto);
    }

    public void delete(Long[] ids) throws Exception{
        for (Long id : ids){
            krpanoMapper.deleteByPrimaryKey(id);
        }
    }
}
