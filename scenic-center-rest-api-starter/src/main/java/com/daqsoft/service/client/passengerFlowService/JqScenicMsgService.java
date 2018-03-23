package com.daqsoft.service.client.passengerFlowService;


import com.daqsoft.vo.client.madeVoBean.JqScenicAreas;

import java.util.List;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-26 18:08
 * @Version:
 * @Describe: 景区service
 */
public interface JqScenicMsgService {


    /**
     * 获取景区列表
     * @param vcode
     * @return
     */
    List<JqScenicAreas> getJqScenicList(String vcode);

    /**
     * 获取景区行政编码
     * @param vcode
     * @return
     */
    List<JqScenicAreas> getJqScenicRegion(String vcode);

    /**
     * 通过景区编码，获取对应景区的省份region
     * @param vcode 景区编码
     * @return
     */
    String getJqScenicProvinceRegion(String vcode);
}
