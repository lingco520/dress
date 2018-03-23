package com.daqsoft.controller.app.gis;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.service.app.gis.PersonPositionService;
import com.daqsoft.validator.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: app景区人员定位
 * @Author: superziy .
 * @Date: Created in 9:32 2018/3/10.
 * @Version: 4.0.0
 * @describe:
 * @since:JDK 1.8
 */
@Controller
@RequestMapping(value = "/rest/personPosition")
public class PersonPositionController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonPositionController.class);
    @Autowired
    private PersonPositionService personPositionService;


    /**
     * 人员定位点位上传
     *
     * @param personId
     * @param longitude
     * @param latitude
     * @param locationName
     * @return
     */
    @RequestMapping(value = "/insertLocation", method = RequestMethod.POST)
    @ResponseBody
    public Object list(Long personId, String longitude,
                       String latitude, String locationName, String vcode) {
        Assert.isBlank(vcode, "景区编码不能为空！");
        Assert.isNull(personId, "人员id不能为空！");
        Assert.isBlank(longitude, "定位经度不能为空！");
        Assert.isBlank(latitude, "定位纬度不能为空！");
        Assert.isBlank(locationName, "位置名称不能为空！");
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("personId", personId);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("locationName", locationName);
        Object obj;
        try {
            obj = personPositionService.insertLocation(map);
        } catch (Exception e) {
            LOGGER.error("人员定位点位上传错误:", e);
            return ResponseBuilder.custom().failed("人员定位点位上传错误", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(obj).build();
    }

    /**
     * 人员定位点位列表
     *
     * @param personId
     * @return
     */
    @GetMapping(value = "/personLocaInfo")
    @ResponseBody
    public Object personLocaInfo(Long personId, String vcode, String date) {
        Assert.isBlank(vcode, "景区编码不能为空！");
        Assert.isNull(personId, "人员id不能为空！");
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("personId", personId);
        map.put("date", date);
        List<Map> list;
        try {
            list = personPositionService.personLocaInfo(map);
        } catch (Exception e) {
            return ResponseBuilder.custom().success("人员定位点位列表错误：", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

}
