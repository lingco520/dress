package com.daqsoft.controller.scenicGis;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.service.gisModuleManage.GisModuleManageService;
import com.daqsoft.utils.LanAndLonCalcUtil;
import com.daqsoft.vo.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: superziy .
 * @Date: Created in 9:47 2018/1/29
 * @Version: 4.0.0
 * @describe: 景区地图
 */
@Controller
@RequestMapping(value = "/permitted/gisModuleInfo")
public class ScenicGisModuleController extends BaseResponseController {

    @Autowired
    private GisModuleManageService gisModuleManageService;

    /**
     * 景点信息
     *
     * @param vcode
     * @return
     */
    @GetMapping(value = "/getGisScenic")
    @ResponseBody
    @CrossOrigin
    public Object getGisScenic(String vcode) {
        if ("".equals(vcode) || vcode == null) {
            return ResponseBuilder.custom().success("景区唯一编码必填", 1).build();
        }
        Map map = new HashMap();
        map.put("vcode", vcode);
        List<GisScenicSpots> list;
        try {
            list = gisModuleManageService.getGisScenic(vcode);
        } catch (Exception e) {
            return ResponseBuilder.custom().success("景点信息列表错误：", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }


    /**
     * 景点厕所
     *
     * @param vcode
     * @return
     */
    @GetMapping(value = "/getGisToilet")
    @ResponseBody
    @CrossOrigin
    public Object getGisToilet(String vcode) {
        if ("".equals(vcode) || vcode == null) {
            return ResponseBuilder.custom().success("景区唯一编码必填", 1).build();
        }
        Map map = new HashMap();
        map.put("vcode", vcode);
        List<GisScenicToilet> list;
        try {
            list = gisModuleManageService.getGisToilet(vcode);
        } catch (Exception e) {
            return ResponseBuilder.custom().success("景点厕所列表错误：", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

    /**
     * 餐饮场所
     *
     * @param vcode
     * @return
     */
    @GetMapping(value = "/getGisDining")
    @ResponseBody
    @CrossOrigin
    public Object getGisDining(String vcode) {
        if ("".equals(vcode) || vcode == null) {
            return ResponseBuilder.custom().success("景区唯一编码必填", 1).build();
        }
        Map map = new HashMap();
        map.put("vcode", vcode);
        List<GisScenicDining> list;
        try {
            list = gisModuleManageService.getGisDining(vcode);
        } catch (Exception e) {
            return ResponseBuilder.custom().success("餐饮场所列表错误：", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

    /**
     * 停车场
     *
     * @param vcode
     * @return
     */
    @GetMapping(value = "/getGisParking")
    @ResponseBody
    @CrossOrigin
    public Object getGisParking(String vcode) {
        if ("".equals(vcode) || vcode == null) {
            return ResponseBuilder.custom().success("景区唯一编码必填", 1).build();
        }
        Map map = new HashMap();
        map.put("vcode", vcode);
        List<GisScenicParking> list;
        try {
            list = gisModuleManageService.getGisParking(vcode);
        } catch (Exception e) {
            return ResponseBuilder.custom().success("停车场列表错误：", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

    /**
     * 宾馆酒店模块
     *
     * @param vcode
     * @return
     */
    @GetMapping(value = "/getGisHotel")
    @ResponseBody
    @CrossOrigin
    public Object getGisHotel(String vcode) {
        if ("".equals(vcode) || vcode == null) {
            return ResponseBuilder.custom().success("景区唯一编码必填", 1).build();
        }
        Map map = new HashMap();
        map.put("vcode", vcode);
        List<GisCommonVo> list;
        try {
            list = gisModuleManageService.getGisHotel(vcode);
        } catch (Exception e) {
            return ResponseBuilder.custom().success("宾馆酒店模块列表错误：", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }

    /**
     * 娱乐场所模块
     *
     * @param vcode
     * @return
     */
    @GetMapping(value = "/getGisEntertainment")
    @ResponseBody
    @CrossOrigin
    public Object getGisEntertainment(String vcode) {
        if ("".equals(vcode) || vcode == null) {
            return ResponseBuilder.custom().success("景区唯一编码必填", 1).build();
        }
        Map map = new HashMap();
        map.put("vcode", vcode);
        List<GisCommonVo> list;
        try {
            list = gisModuleManageService.getGisEntertainment(vcode);
        } catch (Exception e) {
            return ResponseBuilder.custom().success("娱乐场所模块列表错误：", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }


    /**
     * 后台人员id
     *
     * @param vcode
     * @return
     */
    @GetMapping(value = "/getPersonId")
    @ResponseBody
    @CrossOrigin
    public Object getPersonId(String vcode) {
        if ("".equals(vcode) || vcode == null) {
            return ResponseBuilder.custom().success("景区唯一编码必填", 1).build();
        }
        //后台暂时没有人员管理定位人员信息 fixme：后台方法新增后修改，分页显示

        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("personId", "10");
        map.put("name", "定位人员一");
        Map mapTwo = new HashMap();
        mapTwo.put("vcode", vcode);
        mapTwo.put("personId", "11");
        mapTwo.put("name", "定位人员二");
        List<Map> list = new ArrayList<>();
        list.add(map);
        list.add(mapTwo);

//        try {
////            list = gisModuleManageService.getPersonId(vcode);
//        } catch (Exception e) {
//            return ResponseBuilder.custom().success("停车场列表错误：", 1).build();
//        }
        return ResponseBuilder.custom().success("success", 0).data(list).build();
    }


    /**
     * 后台人员定位路径
     *
     * @param vcode
     * @return
     */
    @GetMapping(value = "/getPersonLocation")
    @ResponseBody
    @CrossOrigin
    public Object getPersonLocation(String vcode, String id, String date) {
        if ("".equals(vcode) || vcode == null) {
            return ResponseBuilder.custom().success("景区唯一编码必填", 1).build();
        }
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("id", id);
        map.put("date", date);
        List<GisScenicPersonLoca> list;
        Integer sumDistance = 0;
        Map distance = new HashMap();
        try {
            list = gisModuleManageService.getPersonLocation(map);
            for (int i = 0; i < list.size(); i++) {
                //获取第一个经纬度
                double lonOne = Double.parseDouble(list.get(i).getLongitude());
                double latOne = Double.parseDouble(list.get(i).getLatitude());
                //获取第二个经纬度
                double lonTwo = 0d;
                double latTwo = 0d;
                if (list.size() - 1 != i) {
                    lonTwo = Double.parseDouble(list.get(i + 1).getLongitude());
                    latTwo = Double.parseDouble(list.get(i + 1).getLatitude());
                    sumDistance += LanAndLonCalcUtil.getDistance(latOne, lonOne, latTwo, lonTwo);
                }
            }
            if(sumDistance >= 800){
                sumDistance = Integer.valueOf(new BigDecimal(sumDistance).divide(new BigDecimal("2"), 0, BigDecimal.ROUND_UP).toString());
            }

            distance.put("sumDistance", sumDistance);
            distance.put("datas", list);
        } catch (Exception e) {
            return ResponseBuilder.custom().success("后台人员定位路径列表错误：", 1).build();
        }
        return ResponseBuilder.custom().success("success", 0).data(distance).build();
    }


}
