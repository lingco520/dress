package com.daqsoft.service.client.gisbasedService.impl;

import com.daqsoft.mapper.client.gisindex.GisIndexDao;
import com.daqsoft.mapper.client.realpeople.RealPeopleDao;
import com.daqsoft.mapper.client.threedimensionalmap.ThreeDimensionalMapDao;
import com.daqsoft.service.client.gisbasedService.GisBasicService;
import com.daqsoft.utils.client.DateTools;
import com.daqsoft.utils.client.HttpSend;
import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.Dining;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:gis逻辑实现
 */


@Service
public class GisBasicServiceImpl implements GisBasicService {

    @Autowired
    private GisIndexDao gisIndexDao;
    @Autowired
    private ThreeDimensionalMapDao threeDimensionalMapDao;
    @Autowired
    private RealPeopleDao realPeopleDao;

    //获取gis地图配置
    @Override
    public List<SysConfigTendVo> getSysConfig(String vcode) {
        return threeDimensionalMapDao.getSysConfig(vcode);
    }
    @Override
    public List<Map> getGisSysConfig(String vcode) {
        return threeDimensionalMapDao.getGisSysConfig(vcode);
    }

    @Override
    public List<Dining> getDining(String vcode) {
        return threeDimensionalMapDao.getDining(vcode);
    }

//    @Override
//    public List<Map> getGISResources(String vcode) {
//        return gisIndexDao.getGISResources(vcode);
//    }
        @Override
    public List<Map> getGISResources(String vcode, String type) {
        Map map = new HashMap();
        map.put("vcode",vcode);
        map.put("type",type);
        return gisIndexDao.getGISResources(map);
    }

    /**
     * 登录公共方法
     *
     * @return
     */
    public Map getGPSToken() {
        StringBuilder param = new StringBuilder();
        param.append("loginname=").append("hqc").append("&password=").append("hqc");
        String result = null;
        try {
            result = HttpSend.doPost("http://117.32.251.150:8888/gis/app/login", "utf-8", param.toString());
        } catch (Exception e) {

        }
        JSONObject jsStr = JSONObject.fromObject(result);
        String msg = jsStr.get("result").toString();
        Map map = new HashMap();
        map.put("msg",msg);
        map.put("data",jsStr);
        return map;
    }

    @Override
    public List<Map> getGPSLocationHQG() {

        Map map = getGPSToken();
        String msg = map.get("msg").toString();
        JSONObject jsStr = (JSONObject) map.get("data");
        if (msg.equals("true")) {
            String token = jsStr.get("token").toString();
            StringBuilder paramStr = new StringBuilder();
            paramStr.append("loginname=").append("hqc").append("&token=").append(token);
            String resultData = null;
            try {
                resultData = HttpSend.sendGet("http://117.32.251.150:8888/gis/app/getGPSData",  paramStr.toString());
            } catch (Exception e) {

            }
            JSONObject jsStrData = JSONObject.fromObject(resultData);
            String result = jsStrData.getString("result");
            if(result.equals("true")) {
                JSONArray jsonArray = jsStrData.getJSONArray("data");
                List<Map> list = jsonArray;
                return list;
            }else {
                List<Map> listNull = new ArrayList<>();
                return listNull;
            }
        } else {
            List<Map> listNull = new ArrayList<>();
            return listNull;
        }

    }

    @Override
    public Map getGPSEventHQG(int pageSize, int page) {
        Map map = getGPSToken();
        String msg = map.get("msg").toString();
        JSONObject jsStr = (JSONObject) map.get("data");
        if (msg.equals("true")) {
            String token = jsStr.get("token").toString();
            StringBuilder paramStr = new StringBuilder();
            paramStr.append("loginname=").append("hqc").append("&token=").append(token);
            String resultData = null;
            try {
                resultData = HttpSend.sendGet("http://117.32.251.150:8888/gis/app/getAllAlarms",  paramStr.toString());
            } catch (Exception e) {

            }
            JSONObject jsStrData = JSONObject.fromObject(resultData);
            String result = jsStrData.getString("result");
            if(result.equals("true")) {
                JSONArray jsonArray = jsStrData.getJSONArray("data");
                List<Map> list = jsonArray;
                List<Map> datas = list;
                if(pageSize <= list.size()) {
                    datas = list.subList((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
                }else {
                    datas = list;
                }
                Map maps = new HashMap();
                maps.put("list", datas);
                maps.put("count", list.size());
//                maps.put("currPage", page);
//                maps.put("pageSize", size);
//                int totalPage = (int)Math.ceil(list.size()/size);
//                maps.put("totalPage", totalPage);
                return maps;
            }else {
                List<Map> listNull = new ArrayList<>();
                Map maps = new HashMap();
                maps.put("list", listNull);
                maps.put("count", listNull.size());
                return maps;
            }
        } else {
            List<Map> listNull = new ArrayList<>();
            Map maps = new HashMap();
            maps.put("list", listNull);
            maps.put("count", listNull.size());
            return maps;
        }
    }

    @Override
    public List<Map> getInfostakeHQG() {

        Map map = getGPSToken();
        String msg = map.get("msg").toString();
        JSONObject jsStr = (JSONObject) map.get("data");
        if (msg.equals("true")) {
            String token = jsStr.get("token").toString();
            StringBuilder paramStr = new StringBuilder();
            paramStr.append("loginname=").append("hqc").append("&token=").append(token);
            String resultData = null;
            try {
                resultData = HttpSend.doPost("http://117.32.251.150:8888/gis/app/infostake/list", "" , paramStr.toString());
            } catch (Exception e) {

            }
            JSONObject jsStrData = JSONObject.fromObject(resultData);
            String result = jsStrData.getString("result");
            if(result.equals("true")) {
                String data = jsStrData.getString("data");
                JSONObject jb = JSONObject.fromObject(data);
                Map<String, Object> mapData = (Map<String, Object>)jb;
                List<Map> list = (List<Map>) mapData.get("rows");
                return list;
            }else {
                List<Map> listNull = new ArrayList<>();
                return listNull;
            }
        } else {
            List<Map> listNull = new ArrayList<>();
            return listNull;
        }
    }

    @Override
    public List<GisLocationVo> getMonitorHQG(String vcode) {

        return gisIndexDao.getMonitorHQG(vcode);
    }

    @Override
    public List<GisLocationVo> getFireproof(String vcode) {
        return gisIndexDao.getFireproof(vcode);
    }

    @Override
    public List<GisFireVo> getFireControl(String vcode) {
        return gisIndexDao.getFireControl(vcode);
    }

    @Override
    public List<GisSourceGpsVo> getScenicByType(String vcode, String type, String longkey, String latkey) {
        // 如果没有传具体地图类型，就走以前的查询方法，老的经纬度查询
        if(StringUtils.isEmpty(type)){
            return gisIndexDao.getScenic(vcode);
        }else{
            return gisIndexDao.getScenicByType(vcode, type, longkey, latkey);
        }
    }

    @Override
    public List<GisLocationVo> getParkingByType(String vcode, String type, String longkey, String latkey) {
        // 如果没有传具体地图类型，就走以前的查询方法，老的经纬度查询
        if(StringUtils.isEmpty(type)){
            return gisIndexDao.getParking(vcode);
        }else{
            return gisIndexDao.getParkingByType(vcode, type, longkey, latkey);
        }
    }

    @Override
    public List<GisLocationVo> getMonitorByType(String vcode, String size, String type, String longkey, String latkey) {
        // 如果没有传具体地图类型，就走以前的查询方法，老的经纬度查询
        if(StringUtils.isEmpty(type)){
            if(size == null || "".equals(size)) {
                return gisIndexDao.getMonitor(vcode);
            }else {
                Map map = new HashMap();
                map.put("vcode",vcode);
                map.put("size",size);
                return gisIndexDao.getMonitorSize(map);
            }
        }else{
            if(size == null || "".equals(size)) {
                return gisIndexDao.getMonitorByType(vcode, type, longkey, latkey);
            }else {
                Map map = new HashMap();
                map.put("vcode",vcode);
                map.put("size",size);
                map.put("type",type);
                map.put("longkey",longkey);
                map.put("latkey",latkey);
                return gisIndexDao.getMonitorSizeByType(map);
            }
        }
    }

    @Override
    public List<Dining> getDiningByType(String vcode, String type, String longkey, String latkey) {
        // 如果没有传具体地图类型，就走以前的查询方法，老的经纬度查询
        if(StringUtils.isEmpty(type)){
            return threeDimensionalMapDao.getDining(vcode);
        }else{
            return threeDimensionalMapDao.getDiningByType(vcode, type, longkey, latkey);
        }

    }

    @Override
    public List<GisHotelVo> getEntertainmentByType(String vcode, String type, String longkey, String latkey) {
        // 如果没有传具体地图类型，就走以前的查询方法，老的经纬度查询
        if(StringUtils.isEmpty(type)){
            return gisIndexDao.getEntertainment(vcode);
        }else{
            return gisIndexDao.getEntertainmentByType(vcode, type, longkey, latkey);
        }
    }

    @Override
    public List<GisHotelVo> getGisHotelByType(String vcode, String type, String longkey, String latkey) {
        // 如果没有传具体地图类型，就走以前的查询方法，老的经纬度查询
        if(StringUtils.isEmpty(type)){
            return gisIndexDao.getGisHotel(vcode);
        }else{
            return gisIndexDao.getGisHotelByType(vcode, type, longkey, latkey);
        }
    }

    @Override
    public List<GisLocationVo> getToiletByType(String vcode, String type, String longkey, String latkey) {
        // 如果没有传具体地图类型，就走以前的查询方法，老的经纬度查询
        if(StringUtils.isEmpty(type)){
            return gisIndexDao.getToilet(vcode);
        }else{
            return gisIndexDao.getToiletByType(vcode, type, longkey, latkey);
        }
    }

    @Override
    public List<Map> getMapType() {
        List<Map> resList = new ArrayList<>();
        String type = "map_type";
        List<String> mapTypeList = gisIndexDao.getMapType(type);
        if(mapTypeList.size() > 0){
            for(String pkey : mapTypeList){
                List<String> keyList = gisIndexDao.getMapKeyList(type, pkey);
                Map<String, Object> resMap = new HashMap<>();
                resMap.put("type", pkey);
                resMap.put("longLat", keyList);
                resList.add(resMap);
            }
        }
        return resList;
    }

    @Override
    public List<String> getLongLatByMapType(String type) {
        String mapType = "map_type";
        return gisIndexDao.getMapKeyList(mapType, type);
    }

    @Override
    public List<GisLocationVo> getMonitorHQGByType(String vcode, String type, String longkey, String latkey) {
        // 如果没有传具体地图类型，就走以前的查询方法，老的经纬度查询
        if(StringUtils.isEmpty(type)){
            return gisIndexDao.getMonitorHQG(vcode);
        }else{
            return gisIndexDao.getMonitorHQGByType(vcode, type, longkey, latkey);
        }
    }

    @Override
    public List<GisLocationVo> getFireproofByType(String vcode, String type, String longkey, String latkey) {
        // 如果没有传具体地图类型，就走以前的查询方法，老的经纬度查询
        if(StringUtils.isEmpty(type)){
            return gisIndexDao.getFireproof(vcode);
        }else{
            return gisIndexDao.getFireproofByType(vcode, type, longkey, latkey);
        }
    }

    @Override
    public List<GisFireVo> getFireControlByType(String vcode, String type, String longkey, String latkey) {
        // 如果没有传具体地图类型，就走以前的查询方法，老的经纬度查询
        List<GisFireVo> results = new LinkedList<>();
        List<GisFireVo> datas = new LinkedList<>();
        String now = DateTools.getCurrentTime();
        if(StringUtils.isEmpty(type)){
            datas = gisIndexDao.getFireControl(vcode);
        }else{
            datas = gisIndexDao.getFireControlByType(vcode, type, longkey, latkey);
        }

        //判断是否过期
        for(GisFireVo gisFireVo : datas) {
            GisFireVo gisFire = new GisFireVo();
            String pastTime = gisFireVo.getPastTime();
            if(pastTime != null) {
                String timing = DateTools.getbeforeNTime(5, pastTime);
                int res = timing.compareTo(now);
                if (res > 0) {
                    gisFire.setPastTime("1");
                } else {
                    gisFire.setPastTime("0");
                }
            }else {
                gisFire.setPastTime("1");
            }
            gisFire.setId(gisFireVo.getId());
            gisFire.setName(gisFireVo.getName());
            gisFire.setLatitude(gisFireVo.getLatitude());
            gisFire.setLongitude(gisFireVo.getLongitude());
            gisFire.setPerson(gisFireVo.getPerson());
            gisFire.setPhone(gisFireVo.getPhone());
            results.add(gisFire);
        }

        return results;
    }

    @Override
    public List<GisSourceGpsVo> getScenicListByVcode(String vcode) {
        return gisIndexDao.getScenicListByVcode(vcode);
    }

    @Override
    public List<GisLocationVo> getMonitorListByVcode(String vcode) {
        return gisIndexDao.getMonitorListByVcode(vcode);
    }

    @Override
    public List<GisLocationVo> getParkingListByVcode(String vcode) {
        return gisIndexDao.getParkingListByVcode(vcode);
    }

    @Override
    public List<Map> getTagencyListByVcode(String vcode) {
        return gisIndexDao.getTagencyListByVcode(vcode);
    }

    @Override
    public List<GisHotelVo> getGisHotelListByVcode(String vcode) {
        return gisIndexDao.getGisHotelListByVcode(vcode);
    }

    @Override
    public List<Dining> getDiningListByVcode(String vcode) {
        return threeDimensionalMapDao.getDiningListByVcode(vcode);
    }

    @Override
    public List<GisLocationVo> getToiletListByVcode(String vcode) {
        return gisIndexDao.getToiletListByVcode(vcode);
    }

    //获取超图GIS地图配置
    @Override
    public List<SuperMapConfig> getSuperMapConfig(Map<String, Object> paramMap) {
        return threeDimensionalMapDao.getSuperMapConfig(paramMap);
    }

    //获取gis停车场
    @Override
    public List<GisLocationVo> getParking(String vcode) {
        return gisIndexDao.getParking(vcode);
    }

    //停车场已用剩余数据接口
    @Override
    public Map getParkingData(Map map) {
        Map datas = new HashMap();
        List<Map> list = gisIndexDao.getParkingData(map);
        Map park = gisIndexDao.getOnePark(map);
        String name = park.get("name").toString();
        String max = park.get("maxParkSpace").toString();
        DecimalFormat df = new DecimalFormat("######0");
        if(list.size() > 0 && list != null) {
            for(Map mapData : list) {
                datas.put("name",name);
                datas.put("use",mapData.get("used").toString());
                datas.put("max",mapData.get("maxParkSpace").toString());
                datas.put("surplus",mapData.get("surplus").toString());
                Double percent = new BigDecimal(Integer.parseInt(mapData.get("used").toString())).divide(new BigDecimal(Integer.parseInt(mapData.get("maxParkSpace").toString())),2, RoundingMode.HALF_UP).doubleValue() * 100D;
                String percentStr = df.format(percent);
                datas.put("percent",percentStr);
            }

        } else {
            datas.put("name",name);
            datas.put("use","0");
            datas.put("max",max);
            datas.put("surplus",max);
            datas.put("percent","0");
        }
       return datas;
    }

    //获取gis厕所
    @Override
    public List<GisLocationVo> getToilet(String vcode) {
        return gisIndexDao.getToilet(vcode);
    }

    //获取gis报警亭
    @Override
    public List<GisLocationVo> getPolice(String vcode) {
        return gisIndexDao.getPolice(vcode);
    }

    //获取gis医疗点
    @Override
    public List<GisLocationVo> getMedical(String vcode) {
        return gisIndexDao.getMedical(vcode);
    }

    //获取gis监控
    @Override
    public List<GisLocationVo> getMonitor(String vcode, String size) {
        if(size == null || "".equals(size)) {
            return gisIndexDao.getMonitor(vcode);
        }else {
            Map map = new HashMap();
            map.put("vcode",vcode);
            map.put("size",size);
            return gisIndexDao.getMonitorSize(map);
        }
    }

    //获取gis景点
    @Override
    public List<GisSourceGpsVo> getScenic(String vcode) {
        return gisIndexDao.getScenic(vcode);
    }

    //获取gis景点
    @Override
    public List<GisLocationVo> getTemple(String vcode) {
        return gisIndexDao.getTemple(vcode);
    }

    //获取景区资源gis娱乐场所
    @Override
    public List<GisHotelVo> getEntertainment(String vcode) {
        return gisIndexDao.getEntertainment(vcode);
    }

    @Override
    public List<GisHotelVo> getShop(String vcode) {
        return gisIndexDao.getShop(vcode);
    }

    //获取景区资源gis地图酒店
    @Override
    public List<GisHotelVo> getGisHotel(String vcode) {
        return gisIndexDao.getGisHotel(vcode);
    }

    //获取景区资源统计
    @Override
    public List<GisSourcestatiVo> getSourceStatistics(String vcode) {
        return gisIndexDao.getSourceStatistics(vcode);
    }

    //闸机统计人数
    @Override
    public List<jQScenicSpotZJ> getScenicSpotZJMeth(Map map) {
        return realPeopleDao.getScenicSpotZJMeth(map);
    }

    //闸机统计人数 只是统计景点人数
    @Override
    public List<jQScenicSpotZJ> getScenicSpotBTPeople(Map map) {
        return realPeopleDao.getScenicSpotBTPeople(map);
    }

    //闸机统计人数 只是统计景点人数
    @Override
    public String getComfortMaxCount(Map map) {
        return realPeopleDao.getComfortMaxCount(map);
    }

    //闸机统计舒适度
    @Override
    public List<jQScenicSpotZJList> getScenicSpotZJCountMeth(Map map) {
        return realPeopleDao.getScenicSpotZJCountMeth(map);
    }

    //闸机统计舒适度
    @Override
    public List<jQScenicSpotZJ> getScenicSum(Map map) {
        return realPeopleDao.getScenicSum(map);
    }

    @Override
    public Map getParkingUseList(String vcode, String date, Long id) {
        Map map = new HashMap<>();
        map.put("vcode", vcode);
        map.put("startTime", date+" 00:00:00");
        map.put("endTime", date+" 23:59:59");
        map.put("id", id);
        BigDecimal use =realPeopleDao.getParkingUsePoint(map);
        BigDecimal noUse = new BigDecimal(100);
        BigDecimal useDec = use.setScale(2,   BigDecimal.ROUND_HALF_UP);
        noUse = noUse.subtract(useDec);
        Map<String,String> mapuse = new HashMap<>();
        mapuse.put("Use", use.doubleValue()+"");
        mapuse.put("noUse", noUse.doubleValue()+"");
        return  mapuse;
    }

    @Override
    public MonitorVo getMonitorById(String vcode, Long id) {
        return gisIndexDao.getMonitorById(vcode, id);
    }

    @Override
    public Map getParkingUseInfo(Map map) {
        return realPeopleDao.getParkingUseInfo(map);
    }


}
