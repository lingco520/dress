package com.daqsoft.service.client.forecastwarning.impl;

import com.daqsoft.mapper.client.carflow.CarFlowDao;
import com.daqsoft.mapper.client.realpeople.PassengerFlowDao;
import com.daqsoft.mapper.client.realpeople.RealPeopleDao;
import com.daqsoft.mapper.client.scenicspotinfo.ScenicSpotInfoDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.forecastwarning.ScenicMonitorService;
import com.daqsoft.utils.client.ConvertDataUtil;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.MathUtils;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.*;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.daqsoft.utils.client.RandomNumberUtils.RandomNumber;


/**
 * @author tanggm
 * @version V1.0.0
 * @description
 * @date 2017-06-23 14:43
 */
@Service
public class ScenicMonitorServiceImpl implements ScenicMonitorService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RealPeopleDao realPeopleDao;
    @Autowired
    private ScenicSpotInfoDao scenicSpotInfoDao;
    @Autowired
    private PassengerFlowDao passengerFlowDao;
    @Autowired
    private CarFlowDao carFlowDao;
    //redis中的key值
    private static final String TABLE_NAME = "parkspace:";
    private static final String PARK_DAY = "parking:";
    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String YEAR = "year";
    private static final String COLON = ":";

    private static final String TABLE_NAME_SPOTS = "scenicSpots:";
    private static final String BIG_SPOTS_NAME = "spots:";//景点
    private static final String BIG_TEAM_NAME = "team:";//省团队

    @Override
    public Object getPeopleRealByDate(Map map) {
        String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
        String hk = map.get("date")+"|"+map.get("vcode");
        Object object = RedisCache.getHash(redisTemplate,datKey,hk);
        if(object!=null){
            Map tmap = (Map)object;
            return tmap.get("num");
        }
        return 0;
    }

    @Override
    public int getToursByDate(String year, String vcode) {
        String dateformat="%Y";
        //TODO 旅行团。走DB  后期如果有多维度的。考虑缓存跑
        Map map = new HashMap();
        map.put("year",year);
        map.put("vcode",vcode);
        map.put("dateformat",dateformat);
        int total = 0;
        List<Map<String,Object>> list = realPeopleDao.getToursByDateYear(map);
        if(list.size()>0 && list!=null){
            String tours = list.get(0).get("tours").toString();
            String[] tosrsArray =tours.split(",");
            for (int i = 0; i <tosrsArray.length ; i++) {
                total+=Integer.parseInt(tosrsArray[i]);
            }
        }

        // 这里云台山暂时没有运行团队数据，暂时模拟，后期有数据了，进行真实数据对接
        // 为了数据一致性，这里如果是云台山，就先处理特殊情况，从redis拿模拟数据
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
            total = 0;
            String str = "";
            Object so = hash.get("scenicSpots:team:day:", DateUtil.getCurDateStr()+DigestUtils.md5Hex(vcode));
            String nameStr = "";
            String count = "";
            if(null != so){
                str = so.toString();
                //转换hash为json
                JSONObject jasonObject = JSONObject.fromObject(str);
                // 今日团队数量
                count = jasonObject.getString("tours");
                if(count != null){
                    String[] countArr = count.split(",");
                    for(String strcount : countArr){
                        total += Integer.valueOf(strcount);
                    }
                }
            }else{
                String provinceNames = "河南,辽宁,山东,江苏,浙江";
                String dateTime = DateUtil.getCurDateStr();
                String key = "";
                Map<Object, String> resMapRedis = new HashMap<Object, String>();
                Integer[] numbers = new Integer[]{RandomNumber(10, 6),RandomNumber(8, 5),RandomNumber(7, 6),RandomNumber(13, 8),RandomNumber(11, 4)};
                Map<Object, Object> mapres = new TreeMap<Object, Object>();
                String numberstr = numbers[0]+","+numbers[1]+","+numbers[2]+","+numbers[3]+","+numbers[4];
                mapres.put("tours",numberstr);
                mapres.put("tourFroms",provinceNames);
                mapres.put("dateTime",dateTime);
                key = dateTime + DigestUtils.md5Hex(vcode);
                resMapRedis.put(key, ConvertDataUtil.mapToJsonStr(mapres));
                String tableKey = TABLE_NAME_SPOTS + BIG_TEAM_NAME + "day:";
                hash.putAll(tableKey, resMapRedis);
                for(Integer num : numbers){
                    total+=num;
                }
            }
        }

       return total;

    }

    @Override
    public BigDecimal getTicketMoneyByDate(Map map) {
        return realPeopleDao.getTicketMoneyByDate(map);
    }

    @Override
    public Object getYearTotalPeople(String year, String vcode) {
        String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"year:"+"getPassengerFlowByYear:";
        String hk = year+"|"+vcode;
        Object object = RedisCache.getHash(redisTemplate,datKey,hk);
        if(object!=null){
            Map tmap = (Map)object;
            return tmap.get("num");
        }
        return 0;
    }
    @Override
    public Map<String, Object> scenicPassenger(String date, String vcode) {
        String hoursKey = RedisKey.MOUDEL_PASSENGERFLOW+"hours:"+"getPassengerFlowByHour:";
        String hourHk = date+"|"+vcode;
        Object obj= RedisCache.getHash(redisTemplate,hoursKey, hourHk);
        List<Map<String,Object>> dataList = new ArrayList<>();
        Map resMap = new HashMap();
        if(obj!=null){
            List<Map<String,Object>> list =(List<Map<String,Object>>)obj;
            for (int i = 0; i <list.size() ; i++) {
                Map dataMap = new HashMap();
                dataMap.put("value",list.get(i).get("num"));
                dataMap.put("name",list.get(i).get("time")+":00");
                dataList.add(dataMap);
            }
            String maxquantity = list.get(0).get("maxquantity").toString();
            resMap.put("timeList", dataList);
            resMap.put("maxquantity", maxquantity);
            return resMap;
        }else{
            resMap.put("timeList", dataList);
            resMap.put("maxquantity", 0);
            return resMap;
        }
    }

    @Override
    public Map<String, Object> complaints(String nowyear, String mdfVcode, String vcode) {
        //创建has接受字符串
        String str = "";
        // 昨天的
        String yesstr = "";
        //创建哈希对象
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        // 景区人数数据
        Object so = hash.get("scenicSpots:complaints:year:", nowyear+mdfVcode);
        Map<String, Object> resMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        Long total = Long.valueOf(0);
        if(so != null && !"".equals(so)){
            str = so.toString();
            //转换hash为json
            JSONObject jasonObject = JSONObject.fromObject(str);
            if(jasonObject != null){
                String typesStr = jasonObject.getString("types");
                String numsStr = jasonObject.getString("nums");
                String[] nums = null;
                if(typesStr != null && !"".equals(typesStr)){
                    if(numsStr != null && !"".equals(numsStr)){
                        nums = numsStr.split(",");
                    }
                    String[] types = typesStr.split(",");
                    for (int i=0;i<types.length;i++){
                        Map<String, Object> lMap = new HashMap<>();
                        lMap.put("name", types[i]);
                        String num = nums[i];
                        if(num == null || "".equals(num)){
                            num = String.valueOf(0);
                        }
                        total += Long.valueOf(num);
                        lMap.put("num", num);
                        list.add(lMap);
                    }
                }
            }
            resMap.put("complaintsList", list);
            resMap.put("num", total);
        }else {
            Map map = new HashMap<>();
            map.put("vcode",vcode);
            map.put("dateFormat","%Y");
            map.put("strTime",nowyear +"-01-01");
            map.put("endTime",nowyear +"-12-31");
            List<ComplantsVo> complants = scenicSpotInfoDao.getScenicComplaintsAll(map);
            if(complants == null || complants.size() == 0){
                resMap.put("complaintsList", list);
                resMap.put("num", total);
            }else {
                String numsStr = complants.get(0).getNum();
                String[] nums = null;
                if(numsStr != null && !"".equals(numsStr)){
                    nums = numsStr.split(",");
                }
                for (int i=0;i<nums.length;i++){
                    String num = nums[i];
                    if(num == null || "".equals(num)){
                        num = String.valueOf(0);
                    }
                    total += Long.valueOf(num);
                }
                Map data = new HashMap<>();
                for (ComplantsVo complantsVo : complants){
                    data.put("dataTime",complantsVo.getDateTime());
                    data.put("nums",complantsVo.getNum());
                    data.put("types",complantsVo.getName());
                }
//                RedisCache.putHash(redisTemplate,"scenicSpots:complaints:year:",nowyear+mdfVcode,data);
                Map maps = new HashMap<>();
                maps.put(nowyear+mdfVcode,ConvertDataUtil.mapToJsonStr(data));
                hash.putAll("scenicSpots:complaints:year:", maps);
                resMap.put("complaintsList", complants);
                resMap.put("num", total);
            }

        }

        return resMap;
    }

    @Override
    public List parkingList(String nowdate, String vcode) throws Exception{
        List<ParkingVo> parkingList = new ArrayList<>();
        List<Map> scenicNames = scenicSpotInfoDao.getScenicNamesList(vcode);
        for (Map rmap : scenicNames){
            Map parMap = new HashMap();
            parMap.put("parkid", rmap.get("id"));
            parMap.put("startTime", nowdate+" 00:00:00");
            parMap.put("endTime", nowdate+" 23:59:59");
            parMap.put("vcode", vcode);
            ParkingVo parkingVo = scenicSpotInfoDao.getParkingInfo(parMap);
            parkingVo.setPname(rmap.get("name")+"");
            parkingList.add(parkingVo);
        }
        return parkingList;
    }

    @Override
    public Map<String, Object> spotCrowList(String vcode, String mdfVcode, String date) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map> list1 = new ArrayList<>();
        Map<String, String> smap = new HashMap<>();
        smap.put("vcode", vcode);
        // 景区总人数，由景点人数之和计算,用于计算景点舒适度
        BigDecimal scenicTotal = BigDecimal.ZERO;
        // 取当前时间往前推两个小时
        String nowdate = DateUtil.getCurDateTimeStr();
        smap.put("startTime", MathUtils.getAfterDelHour(nowdate,-2));
        smap.put("endTime", nowdate);
        // 查询景区景点人数  sql出现了严重问题 11.8
        List<Map> spotsNumsList = realPeopleDao.getSpotsNumList(smap);
        if(spotsNumsList != null && spotsNumsList.size() > 0) {
            String scenicName = "";
            BigDecimal per = BigDecimal.ZERO;
            String maxcount = "";
            for (Map spotsMap : spotsNumsList) {
                Map scenicInfoMap = new HashMap();
                BigDecimal count = new BigDecimal(spotsMap.get("count").toString());
                scenicName = spotsMap.get("name") + "";
                smap.put("scenicName", scenicName);
                maxcount = realPeopleDao.getComfortMaxCount(smap);//查询景点的最大承载量
                if (!"".equals(maxcount) && maxcount != null) {
                    BigDecimal dPload = BigDecimal.ZERO;
                    if(count.compareTo(new BigDecimal(0)) != 0){
                        dPload = count.multiply(new BigDecimal(100)).divide(new BigDecimal(maxcount), 2, BigDecimal.ROUND_HALF_UP);
                        // 计算景区所有景点的人数总和，用于下面计算负荷度
                        scenicTotal = scenicTotal.add(count);
                    }
                    scenicInfoMap.put("maxCount",maxcount!=null?Integer.valueOf(maxcount):0);
                    scenicInfoMap.put("name",scenicName);
                    scenicInfoMap.put("per",dPload+"%");
                }else {
                    scenicInfoMap.put("maxCount",maxcount!=null?Integer.valueOf(maxcount):0);
                    scenicInfoMap.put("name",scenicName);
                    scenicInfoMap.put("per","0%");
                }

                list1.add(scenicInfoMap);
            }
        }
        resultMap.put("spotList", list1);
        // 景区负荷度
        BigDecimal load = BigDecimal.ZERO;
        // 实时人数
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        String endTime = format.format(new Date());//获取当前的时间精确到小时号查询实时人数
        String startTime = MathUtils.getBeforeHourTimeAtHOUR(2);//获取当前时间之前两个小时
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("startTime", startTime);
        map.put("endTime", endTime);//作为时间区间2个小时来查询实时人数
        Long realTimePeople = realPeopleDao.getPeopleRealByDate(map);

        // 查询景区最大承载量
        Long maxCount = realPeopleDao.getScenicMaxCount(vcode);
        if(realTimePeople != null && maxCount != null && maxCount.compareTo(0L) != 0){
            //舒适度没有乘100  11.8
            load = new BigDecimal(realTimePeople).divide(new BigDecimal(maxCount),2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        }
        // 查询景区舒适度情况
        Map parMap = new HashMap();
        parMap.put("vcode", vcode);
        parMap.put("num", scenicTotal);
        Map<String, Object> comfortInfo = realPeopleDao.getScenicComfort(parMap);
        String rating = "";
        String colorvalue = "";
        if(comfortInfo != null){
            rating = comfortInfo.get("rating")+"";
            colorvalue = comfortInfo.get("colorvalue")+"";
        }
        List<Map> loadNameList = new ArrayList<>();
        Map loadName = new HashMap();
        loadName.put("name", "负荷度");
        loadName.put("load", load);
        loadNameList.add(loadName);
        resultMap.put("loadNameList", loadNameList);
        resultMap.put("rating", rating);
        return resultMap;
    }

    @Override
    public Map<String, Object> crowdStatus(String date, String vcode) {
        Map<String, Object> resMap = new HashMap<>();
        List<SpotCrowdVo> spotCrowdVoList = new ArrayList<>();
        if(vcode.equals("a02dbca3d06409ba7fbf444e0565242d")){
            SpotCrowdVo spotCrowdVo1 = new SpotCrowdVo();
            spotCrowdVo1.setId(1);
            spotCrowdVo1.setSpeed("68km/h");
            spotCrowdVo1.setStatus("正常");
            spotCrowdVo1.setVehicleno("新A50X78");
            spotCrowdVo1.setVehicleType("大巴车");
            SpotCrowdVo spotCrowdVo2 = new SpotCrowdVo();
            spotCrowdVo2.setId(9);
            spotCrowdVo2.setSpeed("65km/h");
            spotCrowdVo2.setStatus("正常");
            spotCrowdVo2.setVehicleno("新A789X3");
            spotCrowdVo2.setVehicleType("中巴车");
            SpotCrowdVo spotCrowdVo3 = new SpotCrowdVo();
            spotCrowdVo3.setId(1);
            spotCrowdVo3.setSpeed("64km/h");
            spotCrowdVo3.setStatus("正常");
            spotCrowdVo3.setVehicleno("新A789X4");
            spotCrowdVo3.setVehicleType("小轿车");
            spotCrowdVoList.add(spotCrowdVo1);
            spotCrowdVoList.add(spotCrowdVo2);
            spotCrowdVoList.add(spotCrowdVo3);
        }else {
            SpotCrowdVo spotCrowdVo1 = new SpotCrowdVo();
            spotCrowdVo1.setId(1);
            spotCrowdVo1.setSpeed("68km/h");
            spotCrowdVo1.setStatus("正常");
            spotCrowdVo1.setVehicleno("豫H50X78");
            spotCrowdVo1.setVehicleType("大巴车");
            SpotCrowdVo spotCrowdVo2 = new SpotCrowdVo();
            spotCrowdVo2.setId(9);
            spotCrowdVo2.setSpeed("65km/h");
            spotCrowdVo2.setStatus("正常");
            spotCrowdVo2.setVehicleno("豫H789X3");
            spotCrowdVo2.setVehicleType("中巴车");
            SpotCrowdVo spotCrowdVo3 = new SpotCrowdVo();
            spotCrowdVo3.setId(1);
            spotCrowdVo3.setSpeed("64km/h");
            spotCrowdVo3.setStatus("正常");
            spotCrowdVo3.setVehicleno("豫H789X4");
            spotCrowdVo3.setVehicleType("小轿车");
            spotCrowdVoList.add(spotCrowdVo1);
            spotCrowdVoList.add(spotCrowdVo2);
            spotCrowdVoList.add(spotCrowdVo3);
        }

        // 车辆集合
        resMap.put("spotCrowdList", spotCrowdVoList);
        // 总数
        resMap.put("totalNum", "12427");
        // 运行数
        resMap.put("normalNum", "1214");
        // 异常数
        resMap.put("abnormalNum", "11213");

        return resMap;
    }

    @Override
    public Map<String, Object> carStatusHQG(String date, String vcode) {
        List<Map> spotCrowdVoList = new ArrayList<>();
        String[] names = {"巡逻车","游览车","工程车"};
        String[] status = {"1","2","3","0","4","5","6"};
        Random random = new Random();
        for(int i = 0; i < 25; i ++){
            Map map = new HashMap();
            map.put("vehicleType", names[random.nextInt(3)]);
            map.put("vehicleNo", "内-"+i);
            String type = status[random.nextInt(4)];
            if(type.equals("0")){
                map.put("status", "异常");
                map.put("speed", 0+"km/h");
            }else{
                map.put("status", "正常");
                map.put("speed", random.nextInt(45)+"km/h");
            }

            spotCrowdVoList.add(map);
        }
        Map<String, Object> resMap = new HashMap<>();
        // 车辆集合
        resMap.put("spotCrowdList", spotCrowdVoList);
        // 总数
        resMap.put("totalNum", spotCrowdVoList.size() + "");
        return resMap;
    }

    @Override
    public Map<String, Object> deviceStatus(String date, String vcode) {
        Map<String, Object> resMap = new HashMap<>();
        List<DeviceVo> spotDeviceVoList = realPeopleDao.getMonitorList(vcode);
        Integer totalNum = 0;
        Integer totalNormalNum = 0;
        Integer totalAbnormalNum = 0;
        for(int i = 0; i < spotDeviceVoList.size(); i++){
            totalNormalNum+=Integer.valueOf(spotDeviceVoList.get(i).getNormalNum());
            totalAbnormalNum+=Integer.valueOf(spotDeviceVoList.get(i).getAbnormalNum());
        }
        totalNum = totalNormalNum + totalAbnormalNum;
        // 监控设备列表
        resMap.put("monitors", spotDeviceVoList);
        // 总数
        resMap.put("totalNum", totalNum);
        // 运行数
        resMap.put("normalNum", totalNormalNum);
        // 异常数
        resMap.put("abnormalNum", totalAbnormalNum);

        return resMap;
    }

    @Override
    public Map monitorStatus(String vcode) {
        Map map = new HashMap();
        String valuek= RedisKey.MONITOR_LIST+"monitorList:";
        String valueHk= "monitor|"+ vcode;
        Object obj= RedisCache.getHash(redisTemplate,valuek, valueHk);

        if(obj == null) {
            map.put("dataList",new ArrayList<>());
            map.put("normal","0");
            map.put("total","0");
            map.put("abnormal","0");
        }else {
            List list = (List)obj;
            if(list.size() > 0){
                map.put("dataList",list);
                map.put("normal",list.size());
                map.put("total",list.size());
                map.put("abnormal",0);
            }else{
                map.put("dataList",new ArrayList<>());
                map.put("normal","0");
                map.put("total","0");
                map.put("abnormal","0");
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> getInformation(String date, String vcode) throws Exception{
        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> parMap = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date ddate = format.parse(date);
        String dateDay = format.format(ddate);
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        String dateYear = formatYear.format(ddate);
        parMap.put("vcode", vcode);
        // 总数
        Long todayTotal = Long.valueOf(0);
        Long historyTotal = Long.valueOf(0);
        // 今日发布，累计发布
        Long gwTodayCount = Long.valueOf(0);
        Long appTodayCount = Long.valueOf(0);
        Long wwzTodayCount = Long.valueOf(0);

        Long gwYearCount = Long.valueOf(0);
        Long appYearCount = Long.valueOf(0);
        Long wwzYearCount = Long.valueOf(0);

        Long gwTotalCount = Long.valueOf(0);
        Long appTotalCount = Long.valueOf(0);
        Long wwzTotalCount = Long.valueOf(0);

        parMap.put("type", "官网");
        // 今日
        parMap.put("date", dateDay);
        Long dbgwcount = scenicSpotInfoDao.getInformationCounts(parMap);
        if(dbgwcount != null){
            gwTodayCount = Long.valueOf(dbgwcount);
        } if(vcode.equals("a02dbca3d06409ba7fbf444e0565242d")) {
            gwTodayCount = Long.valueOf(2);
        }
        // 今年
        parMap.put("date", dateYear);
        Long dbyeargwcount = scenicSpotInfoDao.getInformationCountsByYear(parMap);
        if(dbyeargwcount != null){
            gwYearCount = Long.valueOf(dbyeargwcount);
        } if(vcode.equals("a02dbca3d06409ba7fbf444e0565242d")) {
            gwYearCount = Long.valueOf(6);
        }
        // 累计
        parMap.put("date", "");
        Long dbtotalgwcount = scenicSpotInfoDao.getInformationCounts(parMap);
        if(dbtotalgwcount != null){
            gwTotalCount = Long.valueOf(dbtotalgwcount);
        } if(vcode.equals("a02dbca3d06409ba7fbf444e0565242d")) {
            gwTotalCount = Long.valueOf(6);
        }

        parMap.put("type", "APP");
        // 今日
        parMap.put("date", dateDay);
        Long dbappcount = scenicSpotInfoDao.getInformationCounts(parMap);
        if(dbappcount != null){
            appTodayCount = Long.valueOf(dbappcount);
        } if(vcode.equals("a02dbca3d06409ba7fbf444e0565242d")) {
            appTodayCount = Long.valueOf(1);
        }
        // 今年
        parMap.put("date", dateYear);
        Long dbyearappcount = scenicSpotInfoDao.getInformationCountsByYear(parMap);
        if(dbyearappcount != null){
            appYearCount = Long.valueOf(dbyearappcount);
        } if(vcode.equals("a02dbca3d06409ba7fbf444e0565242d")) {
            appYearCount = Long.valueOf(7);
        }
        // 累计
        parMap.put("date", "");
        Long dbtotalappcount = scenicSpotInfoDao.getInformationCounts(parMap);
        if(dbtotalappcount != null){
            appTotalCount = Long.valueOf(dbtotalappcount);
        } if(vcode.equals("a02dbca3d06409ba7fbf444e0565242d")) {
            appTotalCount = Long.valueOf(7);
        }

        parMap.put("type", "微网站");
        // 今日
        parMap.put("date", dateDay);
        Long dbwwzcount = scenicSpotInfoDao.getInformationCounts(parMap);
        if(dbwwzcount != null){
            wwzTodayCount = Long.valueOf(dbwwzcount);
        } if(vcode.equals("a02dbca3d06409ba7fbf444e0565242d")) {
            wwzTodayCount = Long.valueOf(3);
        }
        // 今年
        parMap.put("date", dateYear);
        Long dbyearwwzcount = scenicSpotInfoDao.getInformationCountsByYear(parMap);
        if(dbyearwwzcount != null){
            wwzYearCount = Long.valueOf(dbyearwwzcount);
        } if(vcode.equals("a02dbca3d06409ba7fbf444e0565242d")) {
            wwzYearCount = Long.valueOf(9);
        }
        // 累计
        parMap.put("date", "");
        Long dbtotalwwzcount = scenicSpotInfoDao.getInformationCounts(parMap);
        if(dbtotalwwzcount != null){
            wwzTotalCount = Long.valueOf(dbtotalwwzcount);
        } if(vcode.equals("a02dbca3d06409ba7fbf444e0565242d")) {
            wwzTotalCount = Long.valueOf(9);
        }

        // 实时发布来源列表及数据量
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> gwmap = new HashMap<>();
        gwmap.put("name", "官网");
        gwmap.put("todayNum", gwTodayCount);
        gwmap.put("yearNum", gwYearCount);
        Map<String, Object> appmap = new HashMap<>();
        appmap.put("name", "APP");
        appmap.put("todayNum", appTodayCount);
        appmap.put("yearNum", appYearCount);
        Map<String, Object> wwzmap = new HashMap<>();
        wwzmap.put("name", "微网站");
        wwzmap.put("todayNum", wwzTodayCount);
        wwzmap.put("yearNum", wwzYearCount);

        list.add(gwmap);
        list.add(appmap);
        list.add(wwzmap);

        // 计算今日总数
        todayTotal = gwTodayCount + appTodayCount + wwzTodayCount;
        // 计算累计总数
        historyTotal = gwTotalCount + appTotalCount + wwzTotalCount;
        resMap.put("informationList", list);
        resMap.put("todayTotal", todayTotal);
        resMap.put("historyTotal", historyTotal);
        return resMap;
    }

    @Override
    public Map getComfortInfo(Map map) {
        Map<String, Object> comfortInfo = realPeopleDao.getScenicComfort(map);
        return comfortInfo;
    }

    @Override
    public List<SpotsWifiVo> getSpotsWifiList(Map<String, Object> parMap) {
        return scenicSpotInfoDao.getSpotsWifiList(parMap);
    }

    @Override
    public List<Map> getScenicNamesList(String vcode) {
        return scenicSpotInfoDao.getScenicNamesList(vcode);
    }

    @Override
    public ParkingVo getParkingInfo(Map parMap) {
        return scenicSpotInfoDao.getParkingInfo(parMap);
    }

    @Override
    public ParkingNewVo getParkingNewInfo(Map map) {
        return scenicSpotInfoDao.getParkingNewInfo(map);
    }

    @Override
    public Integer getParkingCount(Map parMap) {
        return scenicSpotInfoDao.getParkingCount(parMap);
    }

    @Override
    public Map getScenicParkingCount(String vcode,String stime, String etime) {
        Integer total = 0;
        List<Map> resList = new ArrayList<>();
        Map<String, Object> resMap = new HashMap<>();
        if (vcode == null || stime == null || etime == null) {
            resMap.put("total", total);
            resMap.put("list", resList);
        }
        if (stime.compareTo(etime) > 0) {
            resMap.put("total", total);
            resMap.put("list", resList);
        }
        String startTime = "";
        String endTime = "";
        String md5key = DigestUtils.md5Hex(vcode);
        //redis 缓存key
        String h = TABLE_NAME + PARK_DAY + DAY + COLON;
        List<String> dateList = DateUtil.getSubsectionDateList(stime, etime);
        if (dateList.size()==0){
            resMap.put("total", total);
            resMap.put("list", resList);
        }
            List<Map> scenicNames = scenicSpotInfoDao.getScenicNamesList(vcode);
            //tanggm 判断停车场个数，因为停车场只有一个就根据景区编码查询（因为一个时，停车场id存入的是0），如果多个，就根据停车场id查询
        for (Map scenicname : scenicNames) {
            Integer daysCount = 0;
            for (String dateStr : dateList) {
                String hk = DAY + "|" + dateStr + "|" + md5key;
                List<Map> dayMapList = new ArrayList<>();
                // 先读取缓存，如果有数据就直接取缓存数据，如果没有查询数据库，并把数据写入缓存中
                dayMapList = (List<Map>) RedisCache.getHash(redisTemplate, h, hk);
                if (dayMapList != null && dayMapList.size() > 0) {
                    for(Map m : dayMapList){
                        if(scenicname.get("name").equals(m.get("name"))){
                            daysCount += Integer.valueOf(m.get("count")+"");
                        }
                    }
                } else {
                    List<Map<String, Object>> dayMap = new ArrayList<Map<String, Object>>();
                    for (Map rmap : scenicNames) {
                        Map parMap = new HashMap();
                        if (scenicNames.size() <= 1) {
                            parMap.put("parkid", "");
                        } else {
                            parMap.put("parkid", rmap.get("id"));
                        }
                        parMap.put("startTime", dateStr + " 00:00:00");
                        parMap.put("endTime", dateStr + " 23:59:59");
                        parMap.put("vcode", vcode);
                        Integer count = scenicSpotInfoDao.getParkingCount(parMap);
                        Map resultMap = new HashMap();
                        resultMap.put("name", rmap.get("name") + "");
                        resultMap.put("count", count);
                        dayMap.add(resultMap);
                        if(scenicname.equals(rmap.get("name"))){
                            daysCount += Integer.valueOf(rmap.get("count")+"");
                        }
                    }
                    RedisCache.putHash(redisTemplate, h, hk, dayMap);
                }
            }
            Map resultMap = new HashMap();
            resultMap.put("name", scenicname.get("name") + "");
            resultMap.put("count", daysCount);
            resList.add(resultMap);
            // 计算所有停车场的总数
            total+=daysCount;
        }
        resMap.put("total", total);
        resMap.put("list", resList);
        return resMap;
    }

    @Override
    public void getParkingByThisHors(String vcode, String startTime, String endTime) {
        List<Map> scenicNames = scenicSpotInfoDao.getScenicNamesList(vcode);
        String md5key = DigestUtils.md5Hex(vcode);
        String h = TABLE_NAME + PARK_DAY + DAY + COLON;

        List<String> timeList = DateUtil.getSubsectionDateList(startTime,endTime);
        for (int i = 0; i <timeList.size() ; i++) {
            List<Map<String, Object>> dayMap = new ArrayList<Map<String, Object>>();
            String hk = DAY + "|" + timeList.get(i) + "|" + md5key;
            for (Map rmap : scenicNames) {
                Map parMap = new HashMap();
                parMap.put("parkid", rmap.get("id"));
                parMap.put("startTime", timeList.get(i)+" 00:00:00");
                parMap.put("endTime", timeList.get(i)+" 23:59:59");
                parMap.put("vcode", vcode);
                Integer count = scenicSpotInfoDao.getParkingCount(parMap);
                Map resultMap = new HashMap();
                resultMap.put("name", rmap.get("name") + "");
                resultMap.put("count", count);
                dayMap.add(resultMap);
            }
            RedisCache.putHash(redisTemplate, h, hk, dayMap);
        }
    }

    @Override
    public void getParkingByMonth(String vcode, String year, String month, String startTime, String endTime) {
        List<Map> scenicNames = scenicSpotInfoDao.getScenicNamesList(vcode);
        String md5Vcode = DigestUtils.md5Hex(vcode);
        //获取当前日期时间段的所有日期，查询每日缓存，进行累加得出月缓存
        List<String> list = DateUtil.getSubsectionDateList(startTime,endTime);
        List<Map<String, Object>> monthMap = new ArrayList<Map<String, Object>>();
        for (Map rmap : scenicNames) {
            int monthCount =0;
            if(list.size()>0 && list!=null){
                //日统计的主KEY
                String datKey = TABLE_NAME + PARK_DAY + DAY + COLON;
                for (int i = 0; i <list.size() ; i++) {
                    //日统计每天实时人数的小KEY
                    String hk = DAY + "|" + list.get(i) + "|" + md5Vcode;
                    List<Map> dayMapList = new ArrayList<>();
                    dayMapList = (List<Map>) RedisCache.getHash(redisTemplate, datKey, hk);
                    if (dayMapList != null && dayMapList.size() > 0) {
                        for(Map m : dayMapList){
                            if(rmap.get("name").equals(m.get("name"))){
                                monthCount += Integer.valueOf(m.get("count")+"");
                            }
                        }
                    }
                }
            }
            Map resultMap = new HashMap();
            resultMap.put("name", rmap.get("name") + "");
            resultMap.put("count", monthCount);
            monthMap.add(resultMap);
        }
        //月统计主KEY
        String monthKey = TABLE_NAME+PARK_DAY+MONTH+COLON;
        //月统计小KEY
        String montHk = MONTH + "|" + year + "-" + month+"|"+md5Vcode;
        RedisCache.putHash(redisTemplate,monthKey,montHk,monthMap);
    }

    @Override
    public void getParkingByYear(String vcode, String year) {
        List<Map> scenicNames = scenicSpotInfoDao.getScenicNamesList(vcode);
        String md5Vcode = DigestUtils.md5Hex(vcode);
        String[] month = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        List<Map<String, Object>> yearMap = new ArrayList<Map<String, Object>>();
        for (Map rmap : scenicNames) {
            String monthKey = TABLE_NAME + PARK_DAY + MONTH + COLON;
            int yearCount =0;
            for (int i=0;i<month.length;i++){
                //日统计每天实时人数的小KEY
                String hk = MONTH + "|" + year + "-" + month[i] + "|" + md5Vcode;
                List<Map> dayMapList = new ArrayList<>();
                dayMapList = (List<Map>) RedisCache.getHash(redisTemplate, monthKey, hk);
                if (dayMapList != null && dayMapList.size() > 0) {
                    for(Map m : dayMapList){
                        if(rmap.get("name").equals(m.get("name"))){
                            yearCount += Integer.valueOf(m.get("count")+"");
                        }
                    }
                }
            }
            Map resultMap = new HashMap();
            resultMap.put("name", rmap.get("name") + "");
            resultMap.put("count", yearCount);
            yearMap.add(resultMap);
        }
        //年统计主KEY
        String yearKey = TABLE_NAME+PARK_DAY+YEAR+COLON;
        //年统计小KEY
        String yearHk = YEAR + "|" + year +"|"+md5Vcode;
        RedisCache.putHash(redisTemplate,yearKey,yearHk,yearMap);
    }

    @Override
    public Object getRealPeopleByTicket(String mdfVcode, String date) {
        String lineKey = "scenicSpots:Line:day:";//线下key
        String offline = getRedis(lineKey, date, mdfVcode);//获取线下数据
        return offline;
    }

    @Override
    public void getScenicSpotsDataAnalysisTimeAllHistory(String vcode, String startTime, String endTime) {
        String h = "passengerflow:scenic:time:getPassengerFlowByScenic:";
        String dateFormat = "%Y-%m-%d %H";
        Map mapMapper = new HashMap();
        String hk = "";
        mapMapper.put("vcode",vcode);
        mapMapper.put("dateFormat",dateFormat);
        mapMapper.put("startTime", startTime+" 00:00:00");
        mapMapper.put("endTime", endTime+" 23:59:59");
        List<ScenicSpotsTimeVo> scenicSpotsTimeVos = passengerFlowDao.getScenicSpotsDataTime(mapMapper);
        for (int i = 0; i < scenicSpotsTimeVos.size(); i++) {
            Map<Object, Object> map = new TreeMap<Object, Object>();
            map.put("time", scenicSpotsTimeVos.get(i).getTime());
            map.put("name", scenicSpotsTimeVos.get(i).getName());
            map.put("count", scenicSpotsTimeVos.get(i).getCount());
            hk = scenicSpotsTimeVos.get(i).getDateTime() + DigestUtils.md5Hex(vcode);
            RedisCache.putHash(redisTemplate, h, hk, map);
        }
    }

    @Override
    public void getCarNumByType(String startTime, String endTime, String vcode) {
        String h = "carflow:carType:day:";
        Map mapMapper = new HashMap();
        String hk = "day|"+DateUtil.getCurDateStr()+"|"+DigestUtils.md5Hex(vcode);
        mapMapper.put("vcode",vcode);
        mapMapper.put("startTime", startTime);
        mapMapper.put("endTime", endTime);
        List<Map> carTypeNumList = carFlowDao.getCarNumByType(mapMapper);
        RedisCache.putHash(redisTemplate, h, hk, carTypeNumList);
    }

    @Override
    public List actualPassenger(String vcode, String date) {
        //创建list返回集合
        List listData = new ArrayList<>();
        // 查询景区景点列表
        List<String> scenicNames = scenicSpotInfoDao.getScenicNames(vcode);
        if(scenicNames == null || scenicNames.size() < 1){
            return listData;
        }
        //创建has接受字符串
        String str = "";
        // 景区人数数据
        String timeK = "passengerflow:scenic:time:getPassengerFlowByScenic:";
        String hk = date+ DigestUtils.md5Hex(vcode);
        Object obj= RedisCache.getHash(redisTemplate,timeK,hk);
        if(obj != null){
            Map resMap = (Map) obj;
            String namestr = resMap.get("name")+"";
            String countstr = resMap.get("count")+"";
            String timestr = resMap.get("time")+"";
            String[] names = null;
            String[] counts = null;
            String[] times = null;
            if(namestr != null && !"".equals(namestr)){
                names = namestr.split(",");
                counts = countstr.split(",");
                times = timestr.split(",");
            }
            // 由于显示的是 08:00-18:00的数据，进行两小时相加

            // 对数据进行封装
            String[] hours = new String[]{"08","10","12","14","16","18"};
            // 获取当前小时
            String nowHour = DateUtil.getCurHourStr().split(" ")[1];
            List<Map> timecount = new ArrayList<>();
            for (int k = 0; k < scenicNames.size(); k++){
                for(int p = 0; p < names.length; p++){
                    if(scenicNames.get(k).equals(names[p])){
                        Map map = new HashMap();
                        map.put("time", times[p]);
                        map.put("count", counts[p]);
                        map.put("name", scenicNames.get(k));
                        timecount.add(map);
                    }
                }
            }

            // 考虑到页面会传入查询以往数据，此时，时间轴应该是以前时间天数的所有时间点
            String datestr = DateUtil.getCurDateStr();
            Date nowDate = DateUtil.strToDate(datestr, "yyyy-MM-dd");
            long nowTimes = nowDate.getTime();
            Date yesDate = DateUtil.strToDate(date, "yyyy-MM-dd");
            long yesTimes = yesDate.getTime();
            if(nowTimes <= yesTimes){
                for(int y = 0; y <hours.length; y ++) {
                    if (nowHour.compareTo(hours[y]) >= 0) {
                        List<SpotPassengerVo> list1 = new ArrayList<>();
                        // 时间维度
                        SpotPassengerVo spotPassengerVo = new SpotPassengerVo();
                        spotPassengerVo.setScenicName("时间");
                        spotPassengerVo.setLoadNum(hours[y] + ":00");
                        list1.add(spotPassengerVo);
                        // 下面遍历 景区景点维度
                        for (int w = 0; w < scenicNames.size(); w++) {
                            Integer countss = 0;
                            if (nowHour.compareTo(hours[y]) >= 0) {
                                // 计算前两个小时的数据
                                for (int h = 0; h < timecount.size(); h++) {
                                    Integer max = Integer.valueOf(hours[y]);
                                    Integer min = Integer.valueOf(hours[y]) - 2;
                                    Integer mytime = Integer.valueOf(timecount.get(h).get("time").toString());
                                    Integer mycount = Integer.valueOf(timecount.get(h).get("count").toString());
                                    String scenicNamestr = timecount.get(h).get("name").toString();
                                    // 景点名称判断、时间节点判断
                                    if (min < mytime && mytime < max && scenicNamestr.equals(scenicNames.get(w))) {
                                        countss += mycount;
                                    }
                                }
                            }
                            // 返回数据封装
                            SpotPassengerVo spotPassengerVo1 = new SpotPassengerVo();
                            spotPassengerVo1.setScenicName(scenicNames.get(w));
                            spotPassengerVo1.setLoadNum(countss + "");
                            list1.add(spotPassengerVo1);
                        }
                        listData.add(list1);
                    }
                }
            }else{
                for(int y = 0; y <hours.length; y ++) {
                    List<SpotPassengerVo> list1 = new ArrayList<>();
                    // 时间维度
                    SpotPassengerVo spotPassengerVo = new SpotPassengerVo();
                    spotPassengerVo.setScenicName("时间");
                    spotPassengerVo.setLoadNum(hours[y] + ":00");
                    list1.add(spotPassengerVo);
                    // 下面遍历 景区景点维度
                    for (int w = 0; w < scenicNames.size(); w++) {
                        Integer countss = 0;
                        // 计算前两个小时的数据
                        for (int h = 0; h < timecount.size(); h++) {
                            Integer max = Integer.valueOf(hours[y]);
                            Integer min = Integer.valueOf(hours[y]) - 2;
                            Integer mytime = Integer.valueOf(timecount.get(h).get("time").toString());
                            Integer mycount = Integer.valueOf(timecount.get(h).get("count").toString());
                            String scenicNamestr = timecount.get(h).get("name").toString();
                            // 景点名称判断、时间节点判断
                            if (min < mytime && mytime < max && scenicNamestr.equals(scenicNames.get(w))) {
                                countss += mycount;
                            }
                        }
                        // 返回数据封装
                        SpotPassengerVo spotPassengerVo1 = new SpotPassengerVo();
                        spotPassengerVo1.setScenicName(scenicNames.get(w));
                        spotPassengerVo1.setLoadNum(countss + "");
                        list1.add(spotPassengerVo1);
                    }
                    listData.add(list1);
                }
            }
        }

        return listData;
    }
    //获取redis查询数量数量
    private String getRedis(String key, String date, String vcode) {
        String str = "";
        String num = "";
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        str = (String) hash.get(key, date + vcode);
        //捕获异常
        if ("".equals(str) || str == null) {
            num = "0";
        } else {
            try {
                JSONObject jasonObject = JSONObject.fromObject(str);
                //取nums
                num = jasonObject.getString("nums");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return num;
    }
}
