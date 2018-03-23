/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.client.commonService.impl;

import com.daqsoft.mapper.client.common.CommonDao;
import com.daqsoft.service.client.commonService.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Title:
 * @Author: AHBird
 * @Date: 2017/10/19 19:21
 * @Date: 2017/10/19 19:21
 * @Description: 数据抓取实现类
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonDao commonDao;
    @Override
    public List<Map<String, Object>> getPassengerVcode() {
        return commonDao.getPassengerVcode();
    }

    @Override
    public List<Map<String, Object>> getParkingVcode() {
        return commonDao.getPassengerParking();
    }

    @Override
    public List<Map<String, Object>> getCarFlowVcode() {
        return commonDao.getPassengerCarflow();
    }

    @Override
    public List<Map<String, String>> getWifiVcode() {
        return commonDao.getPassengerWifi();
    }
}