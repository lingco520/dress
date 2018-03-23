/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.audio;

import com.daqsoft.entity.audio.MediaAudio;
import com.daqsoft.entity.pic.MediaPic;
import com.daqsoft.form.PageForm;
import com.daqsoft.mapper.resource.MediaAudioMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: AudioService
 * @Author: tanggm
 * @Date: 2018/01/25 14:30
 * @Description: TODO 语音功能
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Transactional
@Service
public class AudioService {

    @Autowired
    private MediaAudioMapper mediaAudioMapper;

    /**
     * 列表
     * @param pageForm
     * @param title
     * @param scenicId
     * @param audioType
     * @return
     * @throws Exception
     */
    public PageForm list(PageForm pageForm, String title, String scenicId, String audioType, String languageType, Integer status) throws Exception{
        Map parMap = new HashMap<>();
        parMap.put("title", title);
        parMap.put("vcode", pageForm.getVcode());
        parMap.put("scenicId", scenicId);
        parMap.put("audioType", audioType);
        parMap.put("languageType", languageType);
        parMap.put("status", status);
        Page page = PageHelper.startPage(pageForm.getCurrPage(), pageForm.getPageSize(), true);
        List<MediaPic> list = mediaAudioMapper.selectList(parMap);
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
    public MediaAudio get(Long id) throws Exception{
        return mediaAudioMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     * @param dto
     * @throws Exception
     */
    public void save(MediaAudio dto) throws Exception{
        mediaAudioMapper.insert(dto);
    }

    /**
     * 更新
     * @param dto
     * @throws Exception
     */
    public void update(MediaAudio dto) throws Exception{
        mediaAudioMapper.updateByPrimaryKey(dto);
    }

    /**
     * 删除
     * @param ids
     * @throws Exception
     */
    public void delete(Long[] ids) throws Exception{
        for (Long id : ids){
            mediaAudioMapper.deleteByPrimaryKey(id);
        }
    }
}
