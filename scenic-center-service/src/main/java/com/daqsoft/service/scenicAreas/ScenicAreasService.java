/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.scenicAreas;

import com.daqsoft.entity.scenicAreas.ResScenicAreas;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResScenicAreasMapper;
import com.daqsoft.vo.resource.ScenicAreaVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: ScenicAreasService
 * @Author: superziy
 * @Date: 2018/01/10 17:00
 * @Description: TODO
 * @Comment：
 * @Version: 景区信息
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class ScenicAreasService {

    @Autowired
    private ResScenicAreasMapper resScenicAreasMapper;

    /**
     * 列表查询
     * @param pageForm
     * @param name
     * @param vcode
     * @param level
     * @param status
     * @return
     * @throws Exception
     */
    public PageForm list(PageForm pageForm, String name,String vcode,String level,String status) throws Exception {
        Map parmMap = new HashMap();
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        parmMap.put("vcode",vcode);
        parmMap.put("name",name);
        parmMap.put("level",level);
        parmMap.put("status",status);
        List<ResScenicAreas> list = resScenicAreasMapper.selectList(parmMap);
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
    public ResScenicAreas get(Long id) throws Exception {
        return resScenicAreasMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据保存
     * @param dto
     * @throws Exception
     */
    public void save(ResScenicAreas dto) throws Exception {
        resScenicAreasMapper.insert(dto);
    }

    /**
     * 数据更新
     * @param dto
     * @throws Exception
     */
    public void update(ResScenicAreas dto) throws Exception {
        resScenicAreasMapper.updateByPrimaryKey(dto);
    }

    /**
     * 单条和批量删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            resScenicAreasMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 查询景区是否已经添加
     * @param vcode
     * @throws Exception
     */
    public Integer getAreaData(String vcode) throws Exception {
          return  resScenicAreasMapper.getAreaData(vcode);
    }

    /**
     * 根据景区vcode查询景区表中地图中心点经纬度
     * @param vcode
     * @throws Exception
     */
    public ScenicAreaVo getScenicArea(String vcode) throws Exception {
          return  resScenicAreasMapper.getScenicArea(vcode);
    }


}
