package com.daqsoft.mapper.resource;

import com.daqsoft.entity.audio.MediaAudio;
import com.daqsoft.entity.pic.MediaPic;
import com.daqsoft.mapper.common.BaseCommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface MediaAudioMapper extends BaseCommonMapper<MediaAudio>{
    /**
     * 查询数据列表
     * @param parMap
     * @return
     */
    List<MediaPic> selectList(Map parMap);
}