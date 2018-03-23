package com.daqsoft.mapper.client.information;


import com.daqsoft.vo.client.madeVoBean.JqInformationContent;
import com.daqsoft.vo.client.madeVoBean.ScenicComfortVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface JqInformationMapper{
    /**
     * 查询景点最小最大舒适度值
     * @param parMap
     * @return
     */
    List<ScenicComfortVo> getMinMaxComfort(Map<String, Object> parMap);

    /**
     * 查询视频
     * @param parMap
     * @return
     */
    List getInformationVideo(Map<String, Object> parMap);

    /**
     * 获取公告
     * @param parMap
     * @return
     */
    List getScenicNews(Map<String, Object> parMap);

    /**
     * 景区二维码
     * @param parMap
     * @return
     */
    List getScenicQrcode(Map<String, Object> parMap);

    /**
     * 景区客户端信息发布
     * @param jqInformationContent
     * @return
     */
    int saveInformation(JqInformationContent jqInformationContent);

    List<Map<String, Object>> getPlatformList(String vcode);

    List<Map<String,Object>> getDitchListByPlatformId(Map<String, Object> parMap);

    int getVideoCount(Map map);
}