/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.parking;

import com.daqsoft.entity.parking.ResParking;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.ResParkingMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ParkingService
 * @Author: tanggm
 * @Date: 2018/01/23 11:00
 * @Description: TODO 停车场业务
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Transactional
@Service
public class ParkingService {

    @Autowired
    private ResParkingMapper resParkingMapper;

    /**
     * 列表
     * @param pageForm
     * @param name
     * @param parkingType
     * @return
     * @throws Exception
     */
    public PageForm list(PageForm pageForm, String name, String parkingType, Integer status) throws Exception{
        Map parMap = new HashMap<>();
        parMap.put("name", name);
        parMap.put("vcode", pageForm.getVcode());
        parMap.put("parkingType", parkingType);
        parMap.put("status", status);
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<ResParking> list = resParkingMapper.selectList(parMap);
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
    public ResParking get(Long id) throws Exception{
        return resParkingMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     * @param dto
     * @throws Exception
     */
    public void save(ResParking dto) throws Exception{
        resParkingMapper.insert(dto);
    }

    /**
     * 更新
     * @param dto
     * @throws Exception
     */
    public void update(ResParking dto) throws Exception{
        resParkingMapper.updateByPrimaryKey(dto);
    }

    /**
     * 删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception{
        for (Long id : ids){
            resParkingMapper.deleteByPrimaryKey(id);
        }
    }
}
