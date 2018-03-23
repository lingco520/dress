package com.daqsoft.service.client.informationService;


import com.daqsoft.vo.client.madeVoBean.JqInformationContent;

import java.util.List;
import java.util.Map;

/**
 * @author tanggm
 * @description 信息发布系统
 * @date 2017-06-26 18:00
 */
public interface InformationService {
    /**
     * 景区景点实时人数
     * @param vcode
     * @param date
     * @return
     */
    List actualPassenger(String vcode, String date);

    /**
     * 查询景区宣传视频
     * @param parMap
     * @return
     */
    List getVideo(Map<String, Object> parMap);

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
     * 查询值班人员
     * @param vcode
     * @return
     */
    List<Map> getDutypersonnel(String vcode);

    /**
     * 客户端信息发布数据添加
     * @param jqInformationContent
     * @return
     */
    int saveInformation(JqInformationContent jqInformationContent);
    /**
     * 查询平台列表
     * @param vcode
     * @return
     */
    List<Map<String, Object>> getPlatformList(String vcode);
    /**
     * 通过平台id查询渠道列表
     * @return
     */
    List<Map<String,Object>> getDitchListByPlatformId(Map<String, Object> parMap);

    /**
     * 查询视频总记录数
     * @param map
     * @return
     */
    int getVideoCount(Map map);
}
