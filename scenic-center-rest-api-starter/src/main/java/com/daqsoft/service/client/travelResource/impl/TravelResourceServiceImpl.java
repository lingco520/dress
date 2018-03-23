package com.daqsoft.service.client.travelResource.impl;

import com.daqsoft.mapper.client.gisindex.GisIndexDao;
import com.daqsoft.mapper.client.travelResource.TravelResourceDao;
import com.daqsoft.service.client.travelResource.TravelResourceService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.DateUtils;
import com.daqsoft.utils.client.HttpRequestUtil;
import com.daqsoft.utils.client.RedisDelUtil;
import com.daqsoft.vo.client.madeVoBean.EmergencyHQGVo;
import com.daqsoft.vo.client.madeVoBean.GisLocationVo;
import com.daqsoft.vo.client.madeVoBean.HumanResourcesVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Author znb
 * on 2017/6/28.
 */

@Service
public class TravelResourceServiceImpl implements TravelResourceService {

    @Autowired
    TravelResourceDao travelResourceDao;
    @Autowired
    GisIndexDao gisIndexDao;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取基础资源
     *
     * @param vcode
     * @return
     */
    @Override
    public List getBasicResource(String vcode, String region) {
        //旅行社不能根据vcode拿，需要景区地区编码
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("region", region);
        List<Map> list = null;
        //TODO 此处华清宫不要农家乐  后期做最好做成配置
        if(vcode.equals("f561aaf6ef0bf14d4208bb46a4ccb3ad")) {
            list = travelResourceDao.getBasicResourceHQG(map);
        }else {
            list = travelResourceDao.getBasicResource(map);
        }
        return list;
    }

    /**
     * 获取景区设备
     *
     * @param vcode
     * @return
     */
    @Override
    public List getEquipment(String vcode) {
        List<Map> list = travelResourceDao.getEquipment(vcode);
        if (vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")) {
            List<GisLocationVo> monitorList = gisIndexDao.getMonitor(vcode);
            for (Map map : list) {
                if ("monitoring".equals(map.get("type"))) {
                    map.put("name", map.get("name"));
                    map.put("type", map.get("type"));
                    map.put("count", monitorList.size());
                }
            }
        }
        return list;
    }

    /**
     * 景区设备详情
     *
     * @param vcode
     * @return
     */
    @Override
    public List getEquipmentDetail(String vcode, String year) {
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("year", year);
        List list = travelResourceDao.getEquipmentDetail(map);
        return list;
    }

    /**
     * 景区设备月份损坏详情
     *
     * @param vcode
     * @param year
     * @return
     */
    @Override
    public List getEquipmentDamage(String vcode, Integer year) {
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("year", year);
        List<Map> list = travelResourceDao.getEquipmentDamage(map);
        List real = new ArrayList();
        for (int j = 0; j < list.size(); j++) {
            Integer month = Integer.valueOf(String.valueOf(list.get(j).get("month")));
            real.add(month);
        }
        //若数据中未包含某些月份，则将该月份的数量设为0
        for (int i = 1; i < 13; i++) {
            if (!real.contains(i)) {
                Map map1 = new HashMap();
                map1.put("add", 0);
                map1.put("damage", 0);
                map1.put("month", i);
                map1.put("year", year);
                list.add(map1);
            }
        }

        Collections.sort(list, new Comparator<Map>() {
            @Override
            public int compare(Map o1, Map o2) {
                //按照月份升序排列
                if (Integer.valueOf(String.valueOf(o1.get("month"))) > Integer.valueOf(String.valueOf(o2.get("month")))) {
                    return 1;
                }
                if (Integer.valueOf(String.valueOf(o1.get("month"))) == Integer.valueOf(String.valueOf(o2.get("month")))) {
                    return 0;
                }
                return -1;
            }
        });
        return list;
    }

    /**
     * 获取多媒体信息
     *
     * @param vcode
     * @return
     */
    @Override
    public List getMultimedia(String vcode) {

        //List list = travelResourceDao.getMultimedia(vcode);
        // 音频、视频、图片、720全景这些是数据库有的数据，之前查询的是视图，现改为查询数据库表，因为广告和其它类型没有，暂时模拟数据
        List list = new LinkedList();
        if (vcode.equals("6a9f0acb18afb6b1a6cf2200b7d6afbc")) {
            list = travelResourceDao.getMultimediaBySqlYLX(vcode);
        } else {
            list = travelResourceDao.getMultimediaBySql(vcode);
        }

        // 这里特殊处理，如果是银川花博会，就模拟二维码数量的数据,其它景区模拟广告和其他
        if ("fcb456dac4f41a08d5d12943ae61665f".equals(vcode)) {
            Integer qrcodeNum = travelResourceDao.getQrcodeNum(vcode);
            if (qrcodeNum == null) {
                qrcodeNum = 0;
            }
            Map map = new HashMap();
            map.put("types", "others");
            map.put("num", qrcodeNum);
            map.put("name", "二维码");
            list.add(map);
        } else if ("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)) {
            //模拟广告和其他类型的数据
            Map map = new HashMap();
            map.put("types", "ads");
            map.put("num", 12);
            map.put("name", "广告");
            Map map1 = new HashMap();
            map1.put("types", "others");
            map1.put("num", 15);
            map1.put("name", "其他");
            list.add(map);
            list.add(map1);
        }
        return list;
    }

    /**
     * 获取人力资源信息
     *
     * @param vcode
     * @return
     */
    @Override
    public List getHR(String vcode) {
        List<Map> list = travelResourceDao.getHR(vcode);
        return list;
    }

    /**
     * 获取人力资源详情
     *
     * @param vcode
     * @return
     */
    @Override
    public List getHRDetail(String vcode) {
        List list = new ArrayList();
        //查询人力资源男女人数，年份，总人数
        List<HumanResourcesVO> humanResourcesVOList = travelResourceDao.getHRDetail(vcode);
        //查询人力资源离职人数
        List<HumanResourcesVO> leavePeople = travelResourceDao.getLeavePeople(vcode);
        Integer sum = 0;//总人数
        Integer mansum = 0;//总男人数
        Integer womensum = 0;//总女人数
        if (humanResourcesVOList.size() > 0) {
            // 获取最小年份
            Integer humanMinYear = humanResourcesVOList.get(0).getYear();
            Integer leaveMinYear = humanResourcesVOList.get(0).getYear();
            if (leavePeople.size() > 0) {
                leaveMinYear = humanResourcesVOList.get(0).getYear();
            }
            Integer minYear = humanMinYear;
            // 判断年份大小
            if (humanMinYear.compareTo(leaveMinYear) > 0) {
                minYear = leaveMinYear;
            }
            // 最大年份
            Integer maxYear = Integer.valueOf(DateUtil.getCurYearStr());
            for (Integer y = minYear; y <= maxYear; y++) {
                Integer entry = 0;
                for (HumanResourcesVO humanResourcesVO : humanResourcesVOList) {
                    if (y.compareTo(humanResourcesVO.getYear()) == 0) {
                        // 男人数
                        mansum += humanResourcesVO.getMan();
                        if (humanResourcesVO.getMan() != null) {
                            sum += humanResourcesVO.getMan();
                            entry += humanResourcesVO.getMan();
                        }
                        // 女人数
                        womensum += humanResourcesVO.getWomen();
                        if (humanResourcesVO.getWomen() != null) {
                            sum += humanResourcesVO.getWomen();
                            entry += humanResourcesVO.getWomen();
                        }
                        break;
                    }
                }

                Integer leave = 0;//离职人数
                //循环离职人数年份，新增到实体类
                if (leavePeople.size() > 0) {
                    for (HumanResourcesVO humanResourcesVO : leavePeople) {
                        Integer leaveYear = humanResourcesVO.getYear();
                        if (y.compareTo(leaveYear) == 0) {//如果年份相同
                            leave = humanResourcesVO.getLeaveman();
                            break;
                        }
                    }
                }

                Map map = new HashMap();
                map.put("man", mansum);
                map.put("women", womensum);
                map.put("entry", entry);
                map.put("total", sum);
                map.put("year", y);
                map.put("leave", leave);
                list.add(map);
            }

        }
        return list;
    }

    /**
     * 获取应急资源详情
     *
     * @param vcode
     * @return
     */
    @Override
    public Map getEmergenciesResource(String vcode) {
        Integer total = 0;
        Map<String, Object> resMap = new HashMap<>();
        List list = new ArrayList();
        String url = "http://emer.exp.daqsoft.com/webux/egstor/getByNameCount";
        String region = travelResourceDao.getRegionByVcode(vcode);
        String para = "region=" + region;
        try {
            String res = HttpRequestUtil.sendGet(url, para);
            JSONObject jsonObject = JSONObject.fromObject(res);
            JSONArray array = JSONArray.fromObject(jsonObject.get("datas"));
            list = com.alibaba.fastjson.JSONObject.parseArray(array.toString(), Map.class);
            //无数据时，模拟数据，总数暂时采用下面的值
            if (list.size() < 1) {
                String[] wznames = new String[]{"矿泉水", "面包", "彩条布", "手电筒", "对讲机", "其他"};
                for (int i = 0; i < wznames.length; i++) {
                    Integer count = RandomNumber(60, 10);
                    Integer newadd = new Random().nextInt(10);
                    Map map = new HashMap();
                    map.put("emename", wznames[i]);
                    map.put("count", count);
                    map.put("newadd", newadd);
                    list.add(map);
                    total += count + newadd;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resMap.put("list", list);
        resMap.put("count", total);
        return resMap;
    }

    /**
     * 应急物资，后台录入
     *
     * @param vcode
     * @return
     */
    @Override
    public Map getEmergencyGoods(String vcode) {
        Map<String, Object> resMap = new HashMap<>();
        //今日时间
        String day = DateUtil.getCurDateStr();
        Map map = new HashMap();
        map.put("vcode", vcode);
        //获取物资名称，数量
        List<Map<String, Object>> list = travelResourceDao.getEmergencyGoods(map);
        map.put("addTime", day);
        //按时间获取物资名称，新增数量
        List<Map<String, Object>> listByDay = travelResourceDao.getEmergencyGoods(map);
        Map endData = new HashMap();
        List listData = new ArrayList();
        //统计总数
        Integer sum = 0;
        for (int i = 0; i < list.size(); i++) {
            EmergencyHQGVo eme = new EmergencyHQGVo();
            //判断物资是否今日添加
            for (int j = 0; j < listByDay.size(); j++) {
                String addName = listByDay.get(j).get("name").toString();
                String name = list.get(i).get("name").toString();
                if (addName.equals(name)) {
                    eme.setNewadd(Long.valueOf(listByDay.get(j).get("num").toString()));
                }
            }
            eme.setEmeName(list.get(i).get("name").toString());
            eme.setCount(Long.valueOf(list.get(i).get("num").toString()));
            String getNum = list.get(i).get("num").toString();
            Integer num = Integer.valueOf(getNum);
            sum += num;
            listData.add(eme);
        }

        endData.put("list", listData);
        endData.put("count", sum);
        return endData;
    }

    /**
     * 获取公共资源
     *
     * @param vcode
     * @return
     */
    @Override
    public List getPublicResource(String vcode) {
        List list = new LinkedList();
        //无寺庙
        if (vcode.equals("6a9f0acb18afb6b1a6cf2200b7d6afbc")) {
            list = travelResourceDao.getPublicResourceTemple(vcode);
        } else if(vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")){
            //TODO 寺庙
            list = travelResourceDao.getPublicResourceYTS(vcode);
        }else {
            //TODO 建筑物
            list = travelResourceDao.getPublicResource(vcode);
        }
        return list;
    }

    /**
     * 获取公共资源详情
     *
     * @param vcode
     * @return
     */
    @Override
    public List<Map> getPublicDetail(String vcode) {

        int thisyear = Integer.parseInt(DateUtils.getYear());
        List<Map> list = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            String year = String.valueOf(thisyear - (5 - i));
            Map parMap = new HashMap();
            parMap.put("vcode", vcode);
            parMap.put("year", year);
            Map resMap = travelResourceDao.getTravelResource(parMap);
            Map map = new HashMap();
            map.put("year", year);
            map.put("newadd", resMap.get("add"));
            map.put("damage", resMap.get("damage"));
            map.put("total", new BigDecimal(String.valueOf(resMap.get("add"))).add(new BigDecimal(String.valueOf(resMap.get("damage")))));
            list.add(map);
        }
        return list;
    }

    @Override
    public Map getStaffInfo(String vcode) {
        List<Map> educationList = travelResourceDao.getEducationList(vcode);
        List<Map> ageList = travelResourceDao.getAgeList(vcode);
        List<Map> workTimeList = travelResourceDao.getWorkTimeList(vcode);

        Map map = new HashMap();
        map.put("education", educationList);
        map.put("age", ageList);
        map.put("workTime", workTimeList);

        return map;
    }

    @Override
    public List<Map> getDepartmentInfo(String vcode) {

        return travelResourceDao.getDepartmentInfo(vcode);
    }

    @Override
    public Map delRedis(String rawKey, String rawHashKeys) {
        RedisDelUtil.delRedis(redisTemplate,rawKey,rawHashKeys);
        return null;
    }

    public Integer RandomNumber(Integer max, Integer min) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
}
