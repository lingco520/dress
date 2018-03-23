package com.daqsoft.service.monitorConfig;

import org.springframework.stereotype.Repository;

/**
 * @Title: 客户端监控配置供外部使用接口
 * @Author: lyl
 * @Date: 2018/03/21 17:36
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Repository
public interface MonitorConfigService {

    /**
     * 获取监控配置信息
     *
     * @param vcode
     * @return
     */
    String getMonitorConfigXML(String vcode, int div);


}
