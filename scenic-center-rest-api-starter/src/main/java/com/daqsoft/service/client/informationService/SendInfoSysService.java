package com.daqsoft.service.client.informationService;

import java.util.List;

/**
 * @Author: superziy .
 * @Date: Created in 15:33 2017/10/11/
 * @Version: 1.0.0
 * @describe: 信息发布列表
 */
public interface SendInfoSysService {
    /**
     * 景区景点实时人数
     * @param vcode
     * @param date
     * @return
     */
    List sendInfoList(String vcode, String date);


}
