/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.gisModuleManage;

import com.daqsoft.entity.gisClickManage.ResGisClickManage;
import com.daqsoft.entity.gisModuleManage.ResGisModuleManage;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResGisModuleManageMapper;
import com.daqsoft.vo.resource.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: GisModuleManageService
 * @Author: superziy
 * @Date: 2018/02/05 17:00
 * @Description: TODO
 * @Comment：
 * @Version: gis模块管理
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class GisModuleManageService {

    @Autowired
    private ResGisModuleManageMapper gisModuleManageMapper;

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
        List<ResGisModuleManage> list = gisModuleManageMapper.selectList(parmMap);
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
    public ResGisModuleManage get(Long id) throws Exception {
        return gisModuleManageMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据保存
     *
     * @param dto
     * @throws Exception
     */
    public void save(ResGisModuleManage dto) throws Exception {
        gisModuleManageMapper.insert(dto);
    }

    /**
     * 数据更新
     *
     * @param dto
     * @throws Exception
     */
    public void update(ResGisModuleManage dto) throws Exception {
        gisModuleManageMapper.updateByPrimaryKey(dto);
    }

    /**
     * 批量和单条删除
     *
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            gisModuleManageMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 验证资源模块是否重复
     *
     * @param gisName
     * @throws Exception
     */
    public Integer isCheckGisName(String gisName) throws Exception {
        return gisModuleManageMapper.isCheckGisName(gisName);
    }

    /**
     * 查询点击类型
     *
     * @return
     * @throws Exception
     */
    public List<ResGisClickManage> getClickType() throws Exception {
        return gisModuleManageMapper.getClickType();
    }

    /**
     * 查询点击类型
     *
     * @return
     * @throws Exception
     */
    public List<GisModule> getGisModule(String vcode) throws Exception {
        List data = new ArrayList();
        List<GisModule> list = gisModuleManageMapper.getGisModule(vcode);
        for (GisModule listDate : list) {
            GisModule gisModule = new GisModule();
            //模块名称,判断统计表
            String clickType = listDate.getClickType();
            Long num = countModuleNum(clickType, vcode);

            gisModule.setClickType(listDate.getClickType());
            gisModule.setCode(listDate.getCode());
            gisModule.setId(listDate.getId());
            gisModule.setImage(listDate.getImage());
            gisModule.setName(listDate.getName());
            gisModule.setNum(num);
            gisModule.setVcode(listDate.getVcode());
            data.add(gisModule);
        }
        return data;
    }


    /**
     * 统计模块下资源数量
     *
     * @return
     */
    public Long countModuleNum(String clickType, String vcode) {
        Long sum = 0L;
        if ("scenic".equals(clickType)) {
            List list = gisModuleManageMapper.getGisScenic(vcode);
            sum = Long.valueOf(list.size());
        }
        if ("toilet".equals(clickType)) {
            List list = gisModuleManageMapper.getGisToilet(vcode);
            sum = Long.valueOf(list.size());
        }
        if ("dining".equals(clickType)) {
            List list = gisModuleManageMapper.getGisDining(vcode);
            sum = Long.valueOf(list.size());
        }
        if ("parking".equals(clickType)) {
            List list = gisModuleManageMapper.getGisParking(vcode);
            sum = Long.valueOf(list.size());
        }
        if ("hotel".equals(clickType)) {
            List list = gisModuleManageMapper.getGisHotel(vcode);
            sum = Long.valueOf(list.size());
        }
        if ("entertainment".equals(clickType)) {
            List list = gisModuleManageMapper.getGisEntertainment(vcode);
            sum = Long.valueOf(list.size());
        }
        if ("personLocation".equals(clickType)) {
            sum = 2L;
        }
        return sum;
    }

    /**
     * 景点信息
     *
     * @param vcode
     * @return
     */
    public List<GisScenicSpots> getGisScenic(String vcode) {
        return gisModuleManageMapper.getGisScenic(vcode);
    }

    /**
     * 景点厕所
     *
     * @param vcode
     * @return
     */
    public List<GisScenicToilet> getGisToilet(String vcode) {
        return gisModuleManageMapper.getGisToilet(vcode);
    }

    /**
     * 餐饮场所
     *
     * @param vcode
     * @return
     */
    public List<GisScenicDining> getGisDining(String vcode) {
        return gisModuleManageMapper.getGisDining(vcode);
    }

    /**
     * 停车场
     *
     * @param vcode
     * @return
     */
    public List<GisScenicParking> getGisParking(String vcode) {
        return gisModuleManageMapper.getGisParking(vcode);
    }

    /**
     * 宾馆酒店
     *
     * @param vcode
     * @return
     */
    public List<GisCommonVo> getGisHotel(String vcode) {
        return gisModuleManageMapper.getGisHotel(vcode);
    }

    /**
     * 娱乐场所
     *
     * @param vcode
     * @return
     */
    public List<GisCommonVo> getGisEntertainment(String vcode) {
        return gisModuleManageMapper.getGisEntertainment(vcode);
    }

    /**
     * 查询定位人员路径
     *
     * @param map
     * @return
     */
    public List<GisScenicPersonLoca> getPersonLocation(Map map) {
        return gisModuleManageMapper.getPersonLocation(map);
    }
}
