/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.sendInfoChannel;

import com.daqsoft.entity.sendInfoChannel.ManagerSendInfoChannel;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.SendInfoChannelMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: SendInfoChannelService
 * @Author: superziy
 * @Date: 2018/03/17 17:00
 * @Description: TODO
 * @Comment：
 * @Version: 信息发布渠道配置
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class SendInfoChannelService {

    @Autowired
    private SendInfoChannelMapper sendInfoChannelMapper;

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
    public PageForm list(PageForm pageForm, String name,String vcode,String level,String status) throws Exception {
        Map parmMap = new HashMap();
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        parmMap.put("vcode",vcode);
        parmMap.put("name",name);
        parmMap.put("level",level);
        parmMap.put("status",status);
        List<ManagerSendInfoChannel> list = sendInfoChannelMapper.selectList(parmMap);
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
    public ManagerSendInfoChannel get(Long id) throws Exception {
        return sendInfoChannelMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据保存
     *
     * @param dto
     * @throws Exception
     */
    public void save(ManagerSendInfoChannel dto) throws Exception {
        sendInfoChannelMapper.insert(dto);
    }

    /**
     * 数据更新
     * @param dto
     * @throws Exception
     */
    public void update(ManagerSendInfoChannel dto) throws Exception {
        sendInfoChannelMapper.updateByPrimaryKey(dto);
    }

    /**
     * 批量和单条删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception {
        for (Long id : ids) {
            sendInfoChannelMapper.deleteByPrimaryKey(id);
        }
    }
}
