package com.daqsoft.service.client.trafficAnalysisService.impl;

import com.daqsoft.mapper.client.timelyparking.JqScenicTimelyPopulationWidgetDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.passengerFlowService.RealBigPeopleService;
import com.daqsoft.service.client.trafficAnalysisService.RealTimePeopleWidgetService;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.Big_RealP_Tend;
import com.daqsoft.vo.client.madeVoBean.JqScenic_carAndPeopleRelation_TendWidget;
import com.daqsoft.vo.client.mysqlBean.JqScenicTimelyPopulation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zf
 * @version V1.0.0
 * @date 2017-07-28
 */
@Service
public class RealTimePeopleWidgetServiceImpl implements RealTimePeopleWidgetService {


    @Autowired
    private JqScenicTimelyPopulationWidgetDao jqScenicTimelyPopulationMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RealBigPeopleService realBigPeopleService;


    /**
     * 车流分析，小客车与散客流关系
     *
     * @param time
     * @param type
     * @param vcode
     * @return
     * @update zf 20170802
     * @update zf 20170822
     */
    @Override
    public Object findScenicTimePeople(String time, String type, String vcode) {
        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_TIMELYPEOPLEWIDGET + "getJqScenicPeople:findScenicTimePeople:";
        //hashkey
        String hk = type + time + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<JqScenic_carAndPeopleRelation_TendWidget> carFlow_tendList = new LinkedList<JqScenic_carAndPeopleRelation_TendWidget>();
            Map map = new HashMap<>();
            map.put("vcode", vcode);
            if (type.equals("day")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date s_date = null;
                try {
                    s_date = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DateFormat df = null;
                df = new SimpleDateFormat("yyyy-MM-dd");
                map.put("startTime", df.format(s_date) + " 00:00:00");
                map.put("endTime", df.format(s_date) + " 23:59:59");
                List<JqScenicTimelyPopulation> jqScenicList = jqScenicTimelyPopulationMapper.getScenicTimePeople(map);
                for (JqScenicTimelyPopulation jqScenic : jqScenicList) {
                    JqScenic_carAndPeopleRelation_TendWidget JqScenicTend = new JqScenic_carAndPeopleRelation_TendWidget();
                    JqScenicTend.setNum(jqScenic.getSumPeople());
                    JqScenicTend.setTime(jqScenic.getPopTime());
                    JqScenicTend.setType(jqScenic.getSOURCE());
                    carFlow_tendList.add(JqScenicTend);
                }
            } else if (type.equals("month")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date s_date = null;
                try {
                    s_date = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DateFormat df = null;
                df = new SimpleDateFormat("yyyy-MM");
                List<Big_RealP_Tend> list = (List<Big_RealP_Tend>) realBigPeopleService.getPassengerFlowByMonth_day(df.format(s_date), vcode);
                for (Big_RealP_Tend tend : list) {
                    JqScenic_carAndPeopleRelation_TendWidget JqScenicTend = new JqScenic_carAndPeopleRelation_TendWidget();
                    JqScenicTend.setNum(tend.getNum());
                    JqScenicTend.setTime(tend.getTime());
                    carFlow_tendList.add(JqScenicTend);
                }
            } else if (type.equals("year")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date s_date = null;
                try {
                    s_date = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DateFormat df = null;
                df = new SimpleDateFormat("yyyy");
                List<Big_RealP_Tend> list = (List<Big_RealP_Tend>) realBigPeopleService.getPassengerFlowByMonth(df.format(s_date), vcode);
                for (Big_RealP_Tend tend : list) {
                    JqScenic_carAndPeopleRelation_TendWidget JqScenicTend = new JqScenic_carAndPeopleRelation_TendWidget();
                    JqScenicTend.setNum(tend.getNum());
                    JqScenicTend.setTime(tend.getTime());
                    carFlow_tendList.add(JqScenicTend);
                }
            }
            RedisCache.putHash(redisTemplate, k, hk, carFlow_tendList);
            obj = carFlow_tendList;
        }


        return obj;
    }


}
