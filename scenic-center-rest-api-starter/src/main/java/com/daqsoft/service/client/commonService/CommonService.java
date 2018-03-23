/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.client.commonService;

import java.util.List;
import java.util.Map;

/**
 * @Title:
 * @Author: AHBird
 * @Date: 2017/10/19 19:18
 * @Description: 可配置化数据获取
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
public interface CommonService {

   //实时人数配置景区VCODE获取
    List<Map<String,Object>> getPassengerVcode();

    /**
     * 获取停车场配置的景区编码
     * @return
     */
    List<Map<String,Object>> getParkingVcode();

    /**
     * 获取车流配置的景区编码
     * @return
     */
    List<Map<String,Object>> getCarFlowVcode();

    /**
     * 获取wifi配置的景区编码
     * @return
     */
    List<Map<String,String>> getWifiVcode();
}