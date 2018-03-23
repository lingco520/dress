package com.daqsoft.service.client.passengerFlowService.impl;

import com.daqsoft.mapper.client.jqScenicMsg.JqScenicMsgDao;
import com.daqsoft.service.client.passengerFlowService.JqScenicMsgService;
import com.daqsoft.vo.client.madeVoBean.JqScenicAreas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-26 18:09
 * @Version:
 * @Describe: 景区信息service 实现
 */
@Service
public class JqScenicMsgServiceImpl implements JqScenicMsgService {


    @Autowired
    private JqScenicMsgDao jqScenicMsgDao;



    @Override
    public List<JqScenicAreas> getJqScenicList(String vcode) {
        List list = jqScenicMsgDao.getJqScenicList(vcode);
        return list;
    }
    @Override
    public List<JqScenicAreas> getJqScenicRegion(String vcode) {
        return jqScenicMsgDao.getJqScenicRegion(vcode);
    }

    @Override
    public String getJqScenicProvinceRegion(String vcode) {
        return jqScenicMsgDao.getJqScenicProvinceRegion(vcode);
    }
}
