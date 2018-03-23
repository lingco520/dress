package com.daqsoft.service.client.informationService.impl;

import com.daqsoft.mapper.client.information.SendInfoSysMapper;
import com.daqsoft.service.client.informationService.SendInfoSysService;
import com.daqsoft.vo.client.madeVoBean.JqSendInfoSystem;
import com.daqsoft.vo.client.madeVoBean.SendInfoEmergency;
import com.daqsoft.vo.client.madeVoBean.SendInfoServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: superziy .
 * @Date: Created in 15:41 2017/10/11.
 * @Version: V3.1.0
 * @describe:信息发布
 */
@Service
public class SendInfoSysServiceImpl implements SendInfoSysService {
    @Autowired
    private SendInfoSysMapper sendInfoSysMapper;

    @Override
    public List sendInfoList(String vcode, String date) {
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("date", date);
        List list = new ArrayList();
        Map data = new HashMap();
        JqSendInfoSystem jqSendInfoSystem = sendInfoSysMapper.sendInfoList(map);
        if (jqSendInfoSystem != null) {
            //服务信息
            SendInfoServiceInfo sendInfoServiceInfo = new SendInfoServiceInfo();
            sendInfoServiceInfo.setWeather(jqSendInfoSystem.getWeather());
            sendInfoServiceInfo.setTemperature(jqSendInfoSystem.getTemperature());
            sendInfoServiceInfo.setHumidity(jqSendInfoSystem.getHumidity());
            sendInfoServiceInfo.setIllumination(jqSendInfoSystem.getIllumination());
            sendInfoServiceInfo.setUltraviolet(jqSendInfoSystem.getUltraviolet());
            sendInfoServiceInfo.setAir(jqSendInfoSystem.getAir());
            sendInfoServiceInfo.setWaterquality(jqSendInfoSystem.getWaterquality());
            sendInfoServiceInfo.setWatertemperature(jqSendInfoSystem.getWatertemperature());
            sendInfoServiceInfo.setParkingsurplus(jqSendInfoSystem.getParkingsurplus());
            sendInfoServiceInfo.setTrafficconditions(jqSendInfoSystem.getTrafficconditions());
            sendInfoServiceInfo.setWeathericon(jqSendInfoSystem.getWeathericon());

            //应急信息
            SendInfoEmergency sendInfoEmergency = new SendInfoEmergency();
            sendInfoEmergency.setCarflowcrowd(jqSendInfoSystem.getCarflowcrowd());
            sendInfoEmergency.setOtherinfo(jqSendInfoSystem.getOtherinfo());
            sendInfoEmergency.setRealpeople(jqSendInfoSystem.getRealpeople());
            sendInfoEmergency.setVisitorscrowd(jqSendInfoSystem.getVisitorscrowd());

            //诚信信息
            Map integrity = new HashMap();
            integrity.put("content",jqSendInfoSystem.getIntegrityinfo());

            //旅游信息
            Map travel = new HashMap();
            travel.put("content",jqSendInfoSystem.getTravelinfo());

            //政治类信息
            Map politics = new HashMap();
            politics.put("content",jqSendInfoSystem.getPoliticsinfo());

            //知识类
            Map knowledge = new HashMap();
            knowledge.put("content",jqSendInfoSystem.getKnowledgeinfo());

            //新闻类
            Map news = new HashMap();
            news.put("content",jqSendInfoSystem.getNewsinfo());

            data.put("serviceInfo", sendInfoServiceInfo);
            data.put("emergencyInfo", sendInfoEmergency);
            data.put("integrityInfo", integrity);
            data.put("travelInfo", travel);
            data.put("politicsInfo", politics);
            data.put("newsInfo", news);
            list.add(data);
        }
        return list;
    }


}
