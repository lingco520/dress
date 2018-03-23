/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.mapper.client.common;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title:
 * @Author: AHBird
 * @Date: 2017/10/19 19:23
 * @Description: 公共commonDAO接口
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Repository
public interface CommonDao {
    List<Map<String,Object>> getPassengerVcode();

    List<Map<String,Object>> getPassengerParking();

    List<Map<String,Object>> getPassengerCarflow();

    List<Map<String,String>> getPassengerWifi();
}