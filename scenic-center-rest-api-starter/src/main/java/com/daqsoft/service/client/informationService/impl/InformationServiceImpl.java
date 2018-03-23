package com.daqsoft.service.client.informationService.impl;

import com.daqsoft.mapper.client.information.DutypersonnelMapper;
import com.daqsoft.mapper.client.information.JqInformationMapper;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.informationService.InformationService;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.JqInformationContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanggm
 * @version V3.1.0
 * @description 信息发布
 *
 * @date 2017-06-26 18:01
 */
@Service
public class InformationServiceImpl implements InformationService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JqInformationMapper jqInformationMapper;
    @Autowired
    private DutypersonnelMapper dutypersonnelMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List actualPassenger(String vcode, String date) {
        //取缓存
        List listData =new ArrayList();
        //景点主key
        String scenicKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenic:day:"+"getPassengerFlowByScenic:";
        String scenicHk = date+"|"+vcode;
        Object object= RedisCache.getHash(redisTemplate,scenicKey,scenicHk);
        if(object!=null){
            List<Map<String,Object>> list = (List<Map<String,Object>>)object;
            for (int i = 0; i <list.size() ; i++) {
                Map map = new HashMap();
                map.put("maxCount",list.get(i).get("maxquantity"));
                map.put("num",list.get(i).get(date+""));
                map.put("name",list.get(i).get("scenicName"));
                map.put("date",date);
                listData.add(map);
            }
            return  listData;
        }
        return listData;
    }

    @Override
    public List getVideo(Map<String, Object> parMap) {
        return jqInformationMapper.getInformationVideo(parMap);
    }

    @Override
    public List getScenicNews(Map<String, Object> parMap) {
        return jqInformationMapper.getScenicNews(parMap);
    }

    @Override
    public List getScenicQrcode(Map<String, Object> parMap) {
        return jqInformationMapper.getScenicQrcode(parMap);
    }

    @Override
    public List<Map> getDutypersonnel(String vcode) {
        // 查询值班人员id
        String ids = dutypersonnelMapper.getDutyPersonId(vcode);
        if(StringUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        String[] idArr = ids.split(",");
        return dutypersonnelMapper.getDutypersonnel(idArr);
    }

    @Override
    public int saveInformation(JqInformationContent jqInformationContent) {
        return jqInformationMapper.saveInformation(jqInformationContent);
    }

    @Override
    public List<Map<String, Object>> getPlatformList(String vcode) {
        return jqInformationMapper.getPlatformList(vcode);
    }

    @Override
    public List<Map<String,Object>> getDitchListByPlatformId(Map<String, Object> parMap) {
        return jqInformationMapper.getDitchListByPlatformId(parMap);
    }

    @Override
    public int getVideoCount(Map map) {
        return jqInformationMapper.getVideoCount(map);
    }
}
