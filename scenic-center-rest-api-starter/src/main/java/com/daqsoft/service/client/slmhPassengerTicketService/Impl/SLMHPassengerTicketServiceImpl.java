package com.daqsoft.service.client.slmhPassengerTicketService.Impl;

import com.daqsoft.mapper.client.jqmonitor.JqMonitorDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.slmhPassengerTicketService.SLMHPassengerTicketService;
import com.daqsoft.utils.client.DateTools;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.OtaDataTendVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title: 赛里木湖OTA票务模拟缓存
 * @Author: lyl
 * @Date: 2017/10/28 20:06
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class SLMHPassengerTicketServiceImpl implements SLMHPassengerTicketService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JqMonitorDao jqMonitorDao;

    @Override
    public void getPassengerTicketOta() {
        List<OtaDataTendVo> percentData = new LinkedList<>();
        Random random = new Random();
        String day = DateTools.getCurrentTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(day);
            Date dt2 = df.parse("2017-10-28 23:59:59");
            OtaDataTendVo otaDataTendVo1 = new OtaDataTendVo();
            if(dt1.getTime() > dt2.getTime()){
                List<OtaDataTendVo> list = new ArrayList<>();
                int onLineSum = 0;
                String now = DateTools.getCurrentDate();
                String time = DateTools.getbeforeDay(now);
                String Key = RedisKey.MOUDEL_PASSENGERFLOW+"SLMHPassengerTicketOta:";
                String hk = time + "|28c8ee033e7286d791e9654929a08ecb";
                String nhk = now + "|28c8ee033e7286d791e9654929a08ecb";
                Object scenicDayObject= RedisCache.getHash(redisTemplate,Key, hk);
                List<OtaDataTendVo> datas = (List<OtaDataTendVo>) scenicDayObject;
                for(OtaDataTendVo otaDataTendVo : datas){
                    OtaDataTendVo otaDataTendVos = new OtaDataTendVo();
                    String name = otaDataTendVo.getName();
                    otaDataTendVos.setName(name);
                    int max = 0;
                    int min = 0;
                    if(name.equals("美团")){
                        max = 200;
                        min = 100;
                    }else if(name.equals("驴妈妈")){
                        max = 50;
                        min = 10;
                    }else if(name.equals("携程")){
                        max = 30;
                        min = 10;
                    }else if(name.equals("新疆旅发委天猫旗舰店")){
                        max = 20;
                        min = 5;
                    }else if(name.equals("赛里木湖微信")){
                        max = 5;
                        min = 1;
                    }else if(name.equals("途牛")){
                        max = 1;
                        min = 0;
                    }else if(name.equals("同程")){
                        max = 10;
                        min = 1;
                    }else if(name.equals("酷秀")){
                        max = 10;
                        min = 1;
                    }else if(name.equals("去哪儿")){
                        max = 10;
                        min = 1;
                    }
                    int num = (int) (otaDataTendVo.getCount() + random.nextInt(max) % (max - min + 1) + min);
                    otaDataTendVos.setCount(num);
                    list.add(otaDataTendVos);
                    onLineSum = onLineSum + num;
                }
                RedisCache.putHash(redisTemplate,Key,nhk,list);

                String year = DateTools.getCurrentYear();
                String yKey = RedisKey.MOUDEL_PASSENGERFLOW+"SLMHONnOffLine:";
                String yHk = year + "|28c8ee033e7286d791e9654929a08ecb";
                Object obj= RedisCache.getHash(redisTemplate,yKey, yHk);
                String byHk = "";
                if(obj == null){
                    byHk = Integer.parseInt(year) - 1 +  "|28c8ee033e7286d791e9654929a08ecb";
                    obj= RedisCache.getHash(redisTemplate,yKey, byHk);
                }
                Map maps = (Map) obj;
                int offLine = Integer.parseInt(maps.get("offLine").toString());
                offLine = offLine + random.nextInt(500) % (500 - 300 + 1) + 300;
                Map onOffLine = new HashMap();
                onOffLine.put("onLine",onLineSum);
                onOffLine.put("offLine",offLine);
                RedisCache.putHash(redisTemplate,yKey,yHk,onOffLine);

            }else{
                otaDataTendVo1.setName("美团");
                otaDataTendVo1.setCount(10109);
                OtaDataTendVo otaDataTendVo2 = new OtaDataTendVo();
                otaDataTendVo2.setName("驴妈妈");
                otaDataTendVo2.setCount(1700);
                OtaDataTendVo otaDataTendVo3 = new OtaDataTendVo();
                otaDataTendVo3.setName("携程");
                otaDataTendVo3.setCount(1317);
                OtaDataTendVo otaDataTendVo4 = new OtaDataTendVo();
                otaDataTendVo4.setName("新疆旅发委天猫旗舰店");
                otaDataTendVo4.setCount(574);
                OtaDataTendVo otaDataTendVo5 = new OtaDataTendVo();
                otaDataTendVo5.setName("赛里木湖微信");
                otaDataTendVo5.setCount(163);
                OtaDataTendVo otaDataTendVo6 = new OtaDataTendVo();
                otaDataTendVo6.setName("途牛");
                otaDataTendVo6.setCount(10);
                OtaDataTendVo otaDataTendVo7 = new OtaDataTendVo();
                otaDataTendVo7.setName("同程");
                otaDataTendVo7.setCount(266);
                OtaDataTendVo otaDataTendVo8 = new OtaDataTendVo();
                otaDataTendVo8.setName("酷秀");
                otaDataTendVo8.setCount(318);
                OtaDataTendVo otaDataTendVo9 = new OtaDataTendVo();
                otaDataTendVo9.setName("去哪儿");
                otaDataTendVo9.setCount(411);
                percentData.add(otaDataTendVo1);
                percentData.add(otaDataTendVo2);
                percentData.add(otaDataTendVo3);
                percentData.add(otaDataTendVo4);
                percentData.add(otaDataTendVo5);
                percentData.add(otaDataTendVo6);
                percentData.add(otaDataTendVo7);
                percentData.add(otaDataTendVo8);
                percentData.add(otaDataTendVo9);
                String time = DateTools.getCurrentDate();
                String year = DateTools.getCurrentYear();
                String dayKey = RedisKey.MOUDEL_PASSENGERFLOW+"SLMHPassengerTicketOta:";
                String dayhk = time + "|28c8ee033e7286d791e9654929a08ecb";
                RedisCache.putHash(redisTemplate,dayKey,dayhk,percentData);
                String tKey = RedisKey.MOUDEL_PASSENGERFLOW+"SLMHONnOffLine:";
                String thk = year + "|28c8ee033e7286d791e9654929a08ecb";
                Map map = new HashMap();
                map.put("onLine",15868);
                map.put("offLine",409807);
                RedisCache.putHash(redisTemplate,tKey,thk,map);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getMonitor(String vcode) {
        String k= RedisKey.MONITOR_LIST+"monitorList:";
        String hk="monitor|"+ vcode;
        List<Map> datas = new LinkedList<>();
        datas = jqMonitorDao.getMonitorList(vcode);
        RedisCache.putHash(redisTemplate,k,hk,datas);
    }
}