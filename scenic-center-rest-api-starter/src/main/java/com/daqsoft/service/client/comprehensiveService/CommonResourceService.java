package com.daqsoft.service.client.comprehensiveService;


import com.daqsoft.vo.client.madeVoBean.DutyPersonEmerVo;
import com.daqsoft.vo.client.madeVoBean.TrafficStatisticsVo;
import com.daqsoft.vo.client.madeVoBean.WeChatCountIndex;

import java.util.List;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:公共资源数据service
 */


public interface CommonResourceService {
    List<TrafficStatisticsVo> getWebCount(String type, String vcode);

    List<WeChatCountIndex> getWechatCount(String vcode);

    DutyPersonEmerVo getDutyPersonEmergency(String resourcecode);

    /**
     * 获取景区官网，微官网的浏览量
     * @param vcode
     * @return
     */
    List  getStatsticsCount(String vcode, String open);


}
