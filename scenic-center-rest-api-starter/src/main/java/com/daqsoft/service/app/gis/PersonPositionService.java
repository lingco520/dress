package com.daqsoft.service.app.gis;

import com.daqsoft.mapper.app.gis.PersonPositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @Title: app人员定位
 * @Author: superziy .
 * @Date: Created in 9:54 2018/3/10.
 * @Version: 4.0.0
 * @describe:
 * @since:JDK 1.8
 */
@Service
public class PersonPositionService {

    @Autowired
    private PersonPositionMapper personPositionMapper;


    /**
     * 新增人员点位
     *
     * @param map
     * @return
     */
    public Integer insertLocation(Map map) {
        return personPositionMapper.insertLocation(map);
    }

    /**
     * 人员点位信息
     *
     * @param map
     * @return
     */
    public List<Map> personLocaInfo(Map map) {
        return personPositionMapper.personLocaInfo(map);
    }


}
