package com.daqsoft.service.client.passengerFlowService.impl;

import com.daqsoft.config.MyProps;
import com.daqsoft.constants.Constants;
import com.daqsoft.mapper.client.realpeople.PassengerFlowDao;
import com.daqsoft.mapper.client.realwifi.RealWifiDao;
import com.daqsoft.mapper.client.scenicspotinfo.ScenicSpotInfoDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.passengerFlowService.CarFlowService;
import com.daqsoft.service.client.passengerFlowService.JqScenicMsgService;
import com.daqsoft.service.client.passengerFlowService.PassengerService;
import com.daqsoft.utils.client.*;
import com.daqsoft.vo.client.madeVoBean.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: lyl .lrd(2017-08-11)
 * @Date: Created in 2017/6/18.
 * @Version: V3.0.0.
 * @describe:客流分析实现类
 */
@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String SCENICCODE = "scenicCode=410800B010100002";//云台山资源编码
    private static final Logger logger = LoggerFactory.getLogger(PassengerServiceImpl.class);
    @Autowired
    private PassengerFlowDao passengerFlowDao;
    @Autowired
    private CarFlowService carFlowService;
    @Autowired
    private RealWifiDao realWifiDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ScenicSpotInfoDao scenicSpotInfoDao;
    @Autowired
    private MyProps props;
    @Autowired
    private JqScenicMsgService jqScenicMsgService;


    @Override
    public Map getSimulateTour(Map map) {
        return passengerFlowDao.getSimulateTour(map);
    }

    /**
     * 银川花博会客流与票务
     * 查询当天累计检票数、出票数和全年累计检票数
     *
     * @param open
     * @return
     */
    @Override
    public Map<String, Object> getYCHBHPassenger(String open) {
        String dateTime = DateTools.getCurrentDate();
        //缓存添加
        String k= RedisKey.MOUDEL_PASSENGERFLOW+"ychbh:getPassenger:";
        //hashkey
        String hk="day|"+dateTime;
        Object obj= RedisCache.getHash(redisTemplate,k, hk);
        Map map = new HashMap();
        Map mapT = new HashMap<>();
        mapT.put("st",dateTime + " 00:00:00");
        mapT.put("et",dateTime + " 23:59:59");
        if(obj==null || "yes".equals(open)){
            Map todayInfo = new HashMap<>();
            Map allInfo = new HashMap<>();
            try {
                //当天检票数和出票数
                todayInfo =getYCHBHPassengerToday(mapT);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                //累计检票数
                allInfo =getYCHBHPassengerAll(mapT);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            int checkNum = 0;
            int totalNum = 0;
            if(todayInfo == null){
                checkNum = 0;
            }else {
                checkNum = Integer.valueOf(todayInfo.get("checkNum").toString());
                totalNum = Integer.valueOf(todayInfo.get("totalNum").toString());
            }
            int checkSum = 0;
            if(allInfo == null){
                checkSum = 0;
            }else {
                checkSum = Integer.valueOf(allInfo.get("checkSum").toString());
            }
            map.put("checkNum", checkNum);
            map.put("totalNum", totalNum);
            map.put("checkSum", checkSum);
            RedisCache.putHash(redisTemplate,k,hk,map);
        }else{
            map =(Map) obj;
        }
        return map;
    }

    /**
     * 银川花博会景区景点数据分析
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getYCHBHSpotPassenger(String vcode) {

        String time = DateTools.getCurrentDate();
        List<Map<String, Object>> list = new LinkedList<>();
            Map pram = new HashMap<>();
            pram.put("vcode",vcode);
            pram.put("dateTime",time);
        String st = time + " 00:00:00";
        String et = time + " 23:59:59";
        pram.put("st",st);
        pram.put("et",et);
            try {
                //当天检票数和出票数
                list = passengerFlowDao.getYCHBHSpotPassenger(pram);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        return list;
    }

//    /**
//     * 银川花博会wifi与客流关系
//     *
//     * @return
//     */
//    @Override
//    public List<List<YCHBHWifiVo>> getYCHBHWifiPassenger(String time) {
//        Map maps = new LinkedHashMap<>();
//        List<List<YCHBHWifiVo>> mapList = new ArrayList<>();
//        List<YCHBHWifiVo> list = new ArrayList<>();
//        List<List<YCHBHWifiVo>> datas = new ArrayList<>();
//        List<Map<String, Object>> spots = new ArrayList<>();
//        //遍历出景点
//        Map tmap =new HashMap();
//        tmap.put("time",time);
//        spots = passengerFlowDao.getYCHBHSpot(tmap);
//        //求当前小时，若小于十点，去零，并限制在8点-18点
//            String now = DateTools.getCurrentHH();
//            if(now == "08" || now == "09") {
//                now = now.substring(1,1);
//            }
//            int hour = Integer.valueOf(now);
//            int  j = 0;
//            if(hour > 19) {
//                j = 18;
//            }else {
//                j = hour;
//            }
//                //循环现有数据
//                for (int i = 8; i <= j; i++) {
//                    List<YCHBHWifiVo> listData = new ArrayList<>();
//                    String t = "";
//                    if (i < 10) {
//                        t = " 0" + i + ":00";
//                    } else {
//                        t = i + ":00";
//                    }
//                    String st = time + " " + t + ":00";
//                    String et = time + " " + t + ":59:59";
//                    Map map = new HashMap<>();
//                    map.put("st", st);
//                    map.put("et", et);
//                    map.put("hour",t);
//                    //通过景点查询数据，补全所有景点数据，没有在为0
//                    for (int z = 0;z < spots.size(); z++) {
//                        String spot = spots.get(z).get("ap_name").toString();
//                        map.put("apName",spot);
//                        list = passengerFlowDao.getYCHBHWifiPassenger(map);
//                        if(list.size() > 0) {
//                            for (YCHBHWifiVo ychbhWifiVo : list) {
//                                YCHBHWifiVo ychbhWifi = new YCHBHWifiVo();
//                                ychbhWifi.setNum(ychbhWifiVo.getNum());
//                                ychbhWifi.setName(ychbhWifiVo.getName());
//                                ychbhWifi.setTime(ychbhWifiVo.getTime());
//                                listData.add(ychbhWifi);
//                            }
//                        }else {
//                            YCHBHWifiVo ychbhWifi = new YCHBHWifiVo();
//                            ychbhWifi.setNum(0);
//                            ychbhWifi.setName(spot);
//                            ychbhWifi.setTime(t);
//                            listData.add(ychbhWifi);
//                        }
//
//                    }
//                    datas.add(listData);
////                    maps.put(key(i), listData);
//                }
//            //补全当前小时到18点间的数据，默认为0
//            if(j < 18) {
//                for (int i = j+1;i <= 18; i++) {
//                    List<YCHBHWifiVo> listData = new ArrayList<>();
//                    String t = "";
//                    if (i < 10) {
//                        t = " 0" + i + ":00";
//                    } else {
//                        t = i + ":00";
//                    }
//                    for (int z = 0;z < spots.size(); z++) {
//                        String spot = spots.get(z).get("ap_name").toString();
//                        YCHBHWifiVo ychbhWifi = new YCHBHWifiVo();
//                        ychbhWifi.setNum(0);
//                        ychbhWifi.setName(spot);
//                        ychbhWifi.setTime(t);
//                        listData.add(ychbhWifi);
//                    }
//                    datas.add(listData);
////                    maps.put(key(i), listData);
//                }
//            }
//        return datas;
//    }

    /**
     * 银川花博会wifi与客流关系(新)
     * 2017-09-06修改
     * @return
     */
    @Override
    public List<List<YCHBHWifiVo>> getYCHBHWifiPassengerOne(String time) {
        List<List<YCHBHWifiVo>> mapList = new ArrayList<>();
        //遍历出景点
        Map tmap = new HashMap();
        tmap.put("time",time);
        List<Map<String, Object>> spots = passengerFlowDao.getYCHBHSpot(tmap);
        //小时段
        String[] times = {"08","09","10","11","12","13","14","15","16","17","18"};
        for (int i = 0; i <spots.size() ; i++) {
            Map map = new HashMap();
            map.put("name",spots.get(i).get("ap_name"));
            map.put("time",time);
            List<Map<String,Object>> dataList = passengerFlowDao.getWIFIByYCHBH(map);
            List<YCHBHWifiVo> list = new ArrayList<>();
            int n = 0;
             for (int j = 0; j <dataList.size() ; j++) {
                 YCHBHWifiVo vo = new YCHBHWifiVo();
                 String hour = dataList.get(j).get("time").toString();
                 String nowHour = times[n];
                 if(!nowHour.equals(hour)) {
                     int differ = Integer.parseInt(hour) - Integer.parseInt(nowHour);
                     for (int z = 0; z < differ; z++) {
                         YCHBHWifiVo vo1 = new YCHBHWifiVo();
                        vo1.setName(spots.get(i).get("ap_name") + "");
                        vo1.setTime(times[z + j] + ":00");
                        vo1.setNum(Integer.parseInt(0 + ""));
                        list.add(vo1);
                        n ++;
                 }
                     vo.setName(spots.get(i).get("ap_name") + "");
                     vo.setTime(dataList.get(j).get("time") + ":00");
                     vo.setNum(Integer.parseInt(dataList.get(j).get("num") + ""));
                     list.add(vo);
                     n ++;
                 } else {
                     vo.setName(spots.get(i).get("ap_name") + "");
                     vo.setTime(dataList.get(j).get("time") + ":00");
                     vo.setNum(Integer.parseInt(dataList.get(j).get("num") + ""));
                     list.add(vo);
                     n ++;
                 }
             }
            for (int k = n; k <times.length; k++) {
                YCHBHWifiVo vo = new YCHBHWifiVo();
                vo.setName(spots.get(i).get("ap_name")+"");
                vo.setTime(times[k]+":00");
                vo.setNum(0);
                list.add(vo);
            }
            mapList.add(list);
        }
        return mapList;
    }






    /**
     * 生成key（银川花博会）
     *
     * @param i
     * @return
     */
    private String key(int i) {
        String key = "";
        switch(i)
        {
            case 8:
                key = "eight";
            break;
            case 9:
                key = "nine";
            break;
            case 10:
                key = "ten";
            break;
            case 11:
                key = "eleven";
            break;
            case 12:
                key = "twelve";
            break;
            case 13:
                key = "thirteen";
            break;
            case 14:
                key = "fourteen";
            break;
            case 15:
                key = "fifteen";
            break;
            case 16:
                key = "sixteen";
            break;
            case 17:
                key = "seventeen";
            break;
            case 18:
                key = "eighteen";
            break;
        }
        return key;
    }



    /**
     * 银川花博会当天检票数和出票数
     *
     * @param maps
     * @return
     */
    public Map getYCHBHPassengerToday(Map maps) {
        Map map = passengerFlowDao.getYCHBHPassengerToday(maps);
        return map;
    }

    /**
     * 银川花博会累计检票数
     *
     * @param maps
     * @return
     */
    public Map getYCHBHPassengerAll(Map maps) {
        Map map = passengerFlowDao.getYCHBHPassengerAll(maps);
        return map;
    }

    /**
     * 统计景区当前年份的景区总人数
     * 按年份进行分组
     * 普通方法，走SQL
     * @param vcode
     * @return
     */
    @Override
    public List<Map<String,Object>> getRealTimeMethYear(String vcode,String dateTime) {
        Map map =new HashMap();
        map.put("vcode",vcode);
        map.put("year",dateTime);
        List<Map<String,Object>> list = passengerFlowDao.getRealPeopleSumByYear(map);
        return list;
    }

    /**
     * 获取假日人数
     *
     * @param vcode
     * @param date
     * @return
     * @update 2017-08-08节假日调用大数据实验室接口取节假日人数
     */
    @Override
    public HolidayPeopleVo getHolidayPeople(String vcode, String date) {
        HolidayPeopleVo peopleFlowVO = new HolidayPeopleVo();
        String condition = "&year=" + date;//拼上条件
        String str = HttpRequestUtil.sendGet(Constants.Bigdata + "searchHolidayByYear", condition);//url调用接口
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        JSONArray value = null;
        //创建对象与封装
        try {
            value = jasonObject.getJSONArray("datas");
            if (value.size() < 1) {//节假日接口无数据判断
            } else {
                JSONArray json = JSONArray.fromObject(value);
                Integer result = 0;//节假日总人数
                //日统计的主KEY
                String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
                for (int i = 0; i < json.size(); i++) {//循环节假日有几天，查询统计数据
                    JSONObject job = json.getJSONObject(i);
                    String dayTime = String.valueOf(job.get("searchDate"));//循环获取节假日的时间
                    //按日的实时人数key
                    String hk =dayTime+"|"+vcode;
                    Object obj= RedisCache.getHash(redisTemplate,datKey, hk);
                    if(obj!=null){
                        Map map = (Map)obj;
                        result += Integer.parseInt(map.get("num").toString());
                    }else{
                        result += 0;
                    }
                }
                peopleFlowVO.setNum(String.valueOf(result));
                peopleFlowVO.setName("总数");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return peopleFlowVO;
    }


    /**
     * 获取地图客流来源
     * //TODO  此方法目前只有云台山用。获取的是移动数据
     * @param vcode
     * @param date
     * @return
     */
    @Override
    public List<RealPeopleComeFromTopVo> getPeopleComeTopTen(String vcode, String date) {
        //根据VCODE查询资源编码
        String startTime = date + "-01-01";//开始时间
        String endTime = date + "-12-31";//结束时间
        String condition = "&timeType=5&visitorType=2&startDate=" + startTime + "&endDate=" + endTime;//拼上条件
        String str = HttpRequestUtil.sendGet(Constants.Bigdata + "visitorSource/list", SCENICCODE + condition);//url调用接口
        //转换为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        String value = null;
        //创建对象与封装
        value = jasonObject.getString("datas");
        List<RealPeopleComeFromTopVo> list = new LinkedList();
        if (value.equals("[]")) {
            System.out.println("无数据！");
        } else {
            JSONArray json = JSONArray.fromObject(value);
            for (int i = 0; i < json.size(); i++) {
                RealPeopleComeFromTopVo comeFromVo = new RealPeopleComeFromTopVo();
                JSONObject job = json.getJSONObject(i);
                String sourceCity = String.valueOf(job.get("sourceCity"));//来源城市
                if (!"".equals(vcode)) {
                    if (!"".equals(sourceCity) && !sourceCity.equals("未知")) {//过滤来源省份为未知的
                        String sourcecityNew = String.valueOf(job.get("sourcecityNew"));//省份游客数
                        comeFromVo.setSourceCity(sourceCity);
                        comeFromVo.setSourcecityNew(sourcecityNew);
                        list.add(comeFromVo);
                    }
                }
            }
        }
        return list;
    }


    /**
     * 获取地图客流来源
     * 数据来源停车场
     * 每辆车*5人
     * @param vcode
     * @param date
     * @return
     */
    public List<Map<String,Object>> getPeopleComeTopTenCommon(String vcode, String date) {
        //根据VCODE查询资源编码
        Map map = new HashMap();
        map.put("vcode",vcode);
        map.put("time",date);
        List<Map<String,Object>> list = passengerFlowDao.getPeopleComeTopTenCommon(map);
        if(list.size()>0 && list!=null){
            return list ;
        }
        return new ArrayList<>();
    }


    /**
     * 省内游客TOP10
     * @param vcode
     * @param date
     * @return
     */
    @Override
    public List<ProvinceLicensePlateVo> getProvinceLicensePlate(String vcode, String date) {
        Map map = new HashMap();
        map.put("vcode", vcode);
        // 查询景区省份名称
        String pname = carFlowService.getRegionByVcode(vcode);
        //替换省份或城市名称
        pname = ProvionceAndCityName.convertName(pname);
        map.put("time", date);
        map.put("province", pname);
        List<ProvinceLicensePlateVo>   platVo = passengerFlowDao.getProvinceLicensePlate(map);
            Integer total = 0;
            for (int t = 0; t < platVo.size(); t++) {//获取总数
                total = total + platVo.get(t).getSUMCAR();
            }
            for (int i = 0; i < platVo.size(); i++) {
                double sumCar = Double.valueOf(platVo.get(i).getSUMCAR() * 5);
                double totals = Double.valueOf(total * 5);
                double num = (sumCar / totals) * 100.0D;
                BigDecimal bigDecimal = new BigDecimal(num);
                double dPload = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                platVo.get(i).setPercentage(String.valueOf(dPload) + "%");
                platVo.get(i).setSUMCAR(platVo.get(i).getSUMCAR() * 5);
            }
        return platVo;
    }

    /**
     * 省内游客
     * lrd  2017-08-10
     * 优化sql 方法
     * @param vcode
     * @param year
     * @return
     * //TODO  LRD
     */
    @Override
    public List<ProvinceLicensePlateVo> getProvinceLicensePlateRank(String year, String vcode) {
        //缓存添加
        String k= RedisKey.MOUDEL_BIGDATAPASSENGER+ RedisKey.CLASS_BIG_PASSENGER+"year:getProvincePlateRankTen:getProvinceLicensePlateRank:";
        String hk="year|" + year+"|"+vcode;  //之前的key有问题导致不走缓存机制 数据一致是9月27号的数据  11.8
        Object obj= RedisCache.getHash(redisTemplate,k, hk);
        // 查询景区省份名称
        String pname = carFlowService.getRegionByVcode(vcode);
        //替换省份或城市名称
        pname = ProvionceAndCityName.convertName(pname);
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("startDate", year + "-01-01");
        map.put("endDate", year + "-12-31");
        map.put("province", pname);
        map.put("time",year);
        List<ProvinceLicensePlateVo> platVo = null;
        List<ProvinceLicensePlateVo> platVoRank = new LinkedList<>();
        if(obj==null){
            platVo = passengerFlowDao.getProvinceLicensePlate(map); //省外游客分析top10
            Integer total = 0;
            for (int t = 0; t < platVo.size(); t++) {//获取总数
                total = total + platVo.get(t).getSUMCAR();
            }
            if (!platVo.isEmpty()) {
                //因为2个地方调用了这个方法。这个地方取的top10 所以判断
                int size=0;
                if(platVo.size()<10){
                    size = platVo.size();
                }else{
                    size=10;
                }
                for (int i = 0; i < size; i++) {//获取排名前十的数据
                    ProvinceLicensePlateVo provinceLicensePlateVo = new ProvinceLicensePlateVo();//创建前10封装对象
                    double sumCar = Double.valueOf(platVo.get(i).getSUMCAR() * 5);
                    double totals = Double.valueOf(total * 5);
                    double num = (sumCar / totals) * 100.0D;
                    BigDecimal bigDecimal = new BigDecimal(num);
                    double dPload = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    provinceLicensePlateVo.setPercentage(String.valueOf(dPload));
                    provinceLicensePlateVo.setSUMCAR(platVo.get(i).getSUMCAR() * 5);
                    provinceLicensePlateVo.setCARFROM(platVo.get(i).getCARFROM());
                    platVoRank.add(provinceLicensePlateVo);//封装对象进入 list
                }
                RedisCache.putHash(redisTemplate,k,hk,platVoRank);
            }
        }else{
            platVoRank= (List<ProvinceLicensePlateVo>)obj;
        }
        return platVoRank;
    }

    /**
     * 省外游客
     * 通用
     * @param vcode
     * @param year
     * @return
     * @update zf 20170904
     */
    @Override
    public List<ProvinceLicensePlateVo> getProvinceOutside(String year, String vcode) {
        //缓存添加
        String k = RedisKey.MOUDEL_BIGDATAPASSENGER + RedisKey.CLASS_BIG_PASSENGER + "year:getProvinceOutside:getProvinceOutside:";
        String hk = "year" + "|" + year + "|" + vcode;
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        // 查询景区省份名称
        String pname = carFlowService.getRegionByVcode(vcode);
        Map map = new HashMap();
        map.put("vcode", vcode);
        map.put("startDate", year + "-01-01");
        map.put("endDate", year + "-12-31");
        map.put("pname", pname);
        List<ProvinceLicensePlateVo> platVo = null;
        List<ProvinceLicensePlateVo> platVoRank = new LinkedList<>();
        if (obj == null) {
            platVo = passengerFlowDao.getProvinceOutside(map); //省外游客分析top10
            Integer total = 0;
            for (int t = 0; t < platVo.size(); t++) {//获取总数
                total = total + platVo.get(t).getSUMCAR();
            }
            if (!platVo.isEmpty()) {
                //因为2个地方调用了这个方法。这个地方取的top10 所以判断
                int size = 0;
                if (platVo.size() < 10) {
                    size = platVo.size();
                } else {
                    size = 10;
                }
                for (int i = 0; i < size; i++) {//获取排名前十的数据
                    ProvinceLicensePlateVo provinceLicensePlateVo = new ProvinceLicensePlateVo();//创建前10封装对象
                    double sumCar = Double.valueOf(platVo.get(i).getSUMCAR() * 5);
                    double totals = Double.valueOf(total * 5);
                    double num = (sumCar / totals) * 100.0D;
                    BigDecimal bigDecimal = new BigDecimal(num);
                    double dPload = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    provinceLicensePlateVo.setPercentage(String.valueOf(dPload) + "%");
                    provinceLicensePlateVo.setSUMCAR(platVo.get(i).getSUMCAR() * 5);
                    provinceLicensePlateVo.setCARFROM(platVo.get(i).getCARFROM());
                    platVoRank.add(provinceLicensePlateVo);//封装对象进入 list
                }
                RedisCache.putHash(redisTemplate, k, hk, platVoRank);
            }
        } else {
            platVoRank = (List<ProvinceLicensePlateVo>) obj;
        }
        return platVoRank;
    }

    /**
     * 省外游客来源地
     *  调用的大数据
     *  云台山定制
     * @param vcode
     * @param date
     * @return
     */
    @Override
    public List<RealPeopleComeFromVo> getPeopleComeFrom(String vcode, String date) {
        String startTime = date + "-01-01";
        String endTime = date + "-12-31";
        List<RealPeopleComeFromVo> list = new LinkedList<>();
            String condition = "&timeType=5&visitorType=2&startDate=" + startTime + "&endDate=" + endTime;//拼上条件
            String str = HttpRequestUtil.sendGet(Constants.Bigdata + "visitorSource/list", SCENICCODE + condition);//url调用接口
            //转换hash为json
            JSONObject jasonObject = JSONObject.fromObject(str);
            //遍历封装
            String value = null;
            //创建对象与封装
            value = jasonObject.getString("datas");
            if (value.equals("[]")) {
                System.out.println("无数据！");
            } else {
                JSONArray json = JSONArray.fromObject(value);
                for (int i = 0; i < json.size(); i++) {
                    JSONObject job = json.getJSONObject(i);
                    String sourceCity = String.valueOf(job.get("sourceCity"));//来源城市
                    String sourcecityNew = String.valueOf(job.get("sourcecityNew"));//省份游客数
                    String sourcecityPro = String.valueOf(job.get("sourcecityPro"));//游客占比
                    RealPeopleComeFromVo comeFromVo = FiltrationUtil.getRealPeopleList(vcode, sourceCity, sourcecityNew, sourcecityPro);
                    if (comeFromVo.getSourceCity() != null) {
                        list.add(comeFromVo);
                    } else {
                        continue;
                    }
                }
            }
        return list;
    }

    /**
     * 景区日客流趋势
     *
     * @param vcode
     * @param date
     * @return
     */
    @Override
    public List<RealPeopleDayVo> getPeopleDay(String vcode, String date) {
        //发送 GET 请求  visitorType  1 代表 “日” 2 代表 “周” ，3 代表 “月”
        String str = null;
        if(vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")){
            str = HttpRequestUtil.sendGet(Constants.Bigdata + "visitorRealTime/list", SCENICCODE + "&visitorType=1&Date=" + date);
        }
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        String value = null;
        //创建对象与封装
        if(jasonObject.size() == 0){
            value = "[]";
        }else {
            value = jasonObject.getString("datas");
        }
        List list = new LinkedList();
        //TODO lrd 于2017-10-16，接项目经理需求调整模拟数据
        //TODO lyl 修改模拟数据模式
        if (value.equals("[]")) {
            System.out.println("无数据--取模拟数据或者取对接数据");
            Map map = new HashMap();
            map.put("thisDay",date);
            map.put("vcode",vcode);
            list = passengerFlowDao.getYtsPassengerByDay(map);
            if(list.equals(null) || list.size() <1) {
                Map simulateMap = new HashMap();
                simulateMap.put("vcode",vcode);
                simulateMap.put("key","getPeopleDay");
                Map simulateTour = passengerFlowDao.getSimulateTour(simulateMap);
                if(!simulateTour.equals(null) || simulateTour.size() > 0) {
                    String dataStr = simulateTour.get("simulateTour") + "";
                    String[] datas = dataStr.split(",");
                    int now = Integer.parseInt(DateTools.getCurrentHH());
                    //0时为全天24小时都模拟，1则模拟8点到20点数据
                    if(datas[0] == "0"){
                        for(int i =0; i< now; i++) {
                            RealPeopleDayVo dayVo = new RealPeopleDayVo();
                            dayVo.setCurrentDate(date);
                            dayVo.setCurrentTime(i+"时-" + (i+1) + "时" );
                            if(i>=8 && i<=10) {
                                dayVo.setNewTotal(getRandom(Integer.parseInt(datas[2]) * 10, Integer.parseInt(datas[1]) * 10));
                            }else {
                                dayVo.setNewTotal(getRandom(Integer.parseInt(datas[2]), Integer.parseInt(datas[1])));
                            }
                            if(i>=17 && i<=19) {
                                dayVo.setLeaveTotal(getRandom(Integer.parseInt(datas[4]) * 10, Integer.parseInt(datas[3]) * 10));
                            }else {
                                dayVo.setLeaveTotal(getRandom(Integer.parseInt(datas[4]), Integer.parseInt(datas[3])));
                            }

                            list.add(dayVo);
                        }
                    }else {
                        for(int i = 0; i< now; i++) {
                            RealPeopleDayVo dayVo = new RealPeopleDayVo();
                            dayVo.setCurrentDate(date);
                            dayVo.setCurrentTime(i+"时-" + (i+1) + "时" );
                            if(i < 8 || i > 20){
                                dayVo.setLeaveTotal("0");
                                dayVo.setNewTotal("0");
                            }else {
                                if (i >= 8 && i <= 10) {
                                    dayVo.setNewTotal(getRandom(Integer.parseInt(datas[2]) * 10, Integer.parseInt(datas[1]) * 10));
                                } else {
                                    dayVo.setNewTotal(getRandom(Integer.parseInt(datas[2]), Integer.parseInt(datas[1])));
                                }
                                if (i >= 17 && i <= 19) {
                                    dayVo.setLeaveTotal(getRandom(Integer.parseInt(datas[4]) * 10, Integer.parseInt(datas[3]) * 10));
                                } else {
                                    dayVo.setLeaveTotal(getRandom(Integer.parseInt(datas[4]), Integer.parseInt(datas[3])));
                                }
                            }
                            list.add(dayVo);
                        }
                    }
                }

            }
        } else {
            JSONArray json = JSONArray.fromObject(value);
            for (int i = 0; i < json.size(); i++) {
                RealPeopleDayVo dayVo = new RealPeopleDayVo();
                JSONObject job = json.getJSONObject(i);
                String currentTime = String.valueOf(job.get("currentTime"));//时间
                String currentDate = String.valueOf(job.get("currentDate"));//日期
                String leaveTotal = String.valueOf(job.get("leaveTotal"));//离开人数
                String newTotal = String.valueOf(job.get("newTotal"));//新增人数
                dayVo.setCurrentDate(currentDate);
                dayVo.setCurrentTime(currentTime);
                dayVo.setLeaveTotal(leaveTotal);
                dayVo.setNewTotal(newTotal);
                list.add(dayVo);
            }
        }
        return list;
    }

    /**
     * 景区日客流趋势分析
     *
     * @param startTime
     * @param endTime
     * @param vcode
     * @return
     */
    @Override
    public List<RealPeopleDayTrendVo> getPeopleDayTrend(String startTime, String endTime, String vcode) {
        //发送 GET 请求  visitorType  1 代表 “日” 2 代表 “周” ，3 代表 “月”
        String condition = "&dateType=1&visitorType=1&startDate=" + startTime + "&endDate=" + endTime;//拼上条件
        String str = null;
        if(vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")) {
            str = HttpRequestUtil.sendGet(Constants.Bigdata + "visitorAnalyse/list", SCENICCODE + condition);//url调用接口
        }
            //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        String value = null;
        //创建对象与封装
        if(jasonObject.size() == 0){
            value = "[]";
        }else {
            value = jasonObject.getString("datas");
        }
        List list = new ArrayList();
        //TODO lrd 于2017-10-16，接项目经理需求调整模拟数据
        //TODO lyl 修改模拟数据模式
        if (value.equals("[]")) {
            System.out.println("无真实数据，取模拟数据或对接数据--");
            Map map = new HashMap();
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            map.put("vcode",vcode);
            List<Map<String,Object>> listTwo =passengerFlowDao.getYtsPassengerDetailByDay(map);
            if(listTwo.equals(null) || listTwo.size() <1) {
                Map simulateMap = new HashMap();
                simulateMap.put("vcode",vcode);
                simulateMap.put("key","getPeopleDayTrend");
                Map simulateTour = passengerFlowDao.getSimulateTour(simulateMap);
                if(!simulateTour.equals(null) || simulateTour.size() > 0) {
                    List<String> listTime = DateUtil.getSubsectionDateList(startTime,endTime);
                    for (int i = 0; i <listTime.size() ; i++) {
                    String k= RedisKey.SIMULATE+"getPeopleDayTrend:day:";
                    String hk= listTime.get(i)+"|"+vcode;
                    Object obj= RedisCache.getHash(redisTemplate,k, hk);
                    if(obj == null) {
                        DecimalFormat df = new DecimalFormat("#.00");
                        String dataStr = simulateTour.get("simulateTour") + "";
                        String[] datas = dataStr.split(",");
                        RealPeopleDayTrendVo trendVo = new RealPeopleDayTrendVo();
                        trendVo.setNewadd(getRandom(Integer.parseInt(datas[3]), Integer.parseInt(datas[2])));
                        trendVo.setResident(getRandom(Integer.parseInt(datas[1]), Integer.parseInt(datas[0])));
                        trendVo.setLeave(getRandom(Integer.parseInt(datas[5]), Integer.parseInt(datas[4])));
                        trendVo.setDay(listTime.get(i));
                        trendVo.setAvgtime(df.format(Math.random() + Integer.parseInt(datas[6])));
                        list.add(trendVo);
                        RedisCache.putHash(redisTemplate,k,hk,trendVo);
                    }else {
                        list.add(obj);
                    }
                    }
                }

            }else {
                for (int i = 0; i < listTwo.size(); i++) {
                    RealPeopleDayTrendVo trendVo = new RealPeopleDayTrendVo();
                    trendVo.setNewadd(listTwo.get(i).get("newadd")+"");
                    trendVo.setResident(listTwo.get(i).get("resident")+"");
                    trendVo.setLeave(listTwo.get(i).get("leavenum")+"");
                    trendVo.setDay(listTwo.get(i).get("currentDate")+"");
                    list.add(trendVo);
                }
            }
        } else {
            JSONArray json = JSONArray.fromObject(value);
            for (int i = 0; i < json.size(); i++) {
                RealPeopleDayTrendVo trendVo = new RealPeopleDayTrendVo();
                JSONObject job = json.getJSONObject(i);
                String resident = String.valueOf(job.get("resident"));//驻留游客
                String newadd = String.valueOf(job.get("newadd"));//新增人数
                String leave = String.valueOf(job.get("leave"));//离开人数
                String day = String.valueOf(job.get("day"));//日期
                trendVo.setDay(day);
                trendVo.setLeave(leave);
                trendVo.setNewadd(newadd);
                trendVo.setResident(resident);
                list.add(trendVo);
            }
        }
        return list;
    }

    /**
     * 景区日客流趋势分析
     *
     * @param startTime
     * @param endTime
     * @param vcode
     * @return
     */
    @Override
    public List<RealPeopleDayTrendVo> getVisitorAnalyse(String startTime, String endTime, String vcode) {
        String condition = "&startDate=" + startTime + "&endDate=" + endTime + "&dateType=1&visitorType=1";//拼上条件
        String str = null;
        if(vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")) {
            str = HttpRequestUtil.sendGet(Constants.Bigdata + "visitorAnalyse/list", SCENICCODE + condition);//url调用接口
        }
            //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        String value = null;
        //创建对象与封装
        if(jasonObject.size() == 0){
            value = "[]";
        }else {
            value = jasonObject.getString("datas");
        }
        List<RealPeopleDayTrendVo> list = new LinkedList();
        if (value.equals("[]")) {
            System.out.println("无数据,获取模拟数据/对接数据");
            Map dataMap = new HashMap();
            dataMap.put("startTime", startTime);
            dataMap.put("endTime", endTime);
            dataMap.put("vcode", vcode);
            List<Map<String, Object>> dataList = passengerFlowDao.getYtsPassengerDetailByTime(dataMap);
            if (dataList.equals(null) || dataList.size() < 1) {
                Map simulateMap = new HashMap();
                simulateMap.put("vcode", vcode);
                simulateMap.put("key", "getPeopleDayTrend");
                Map simulateTour = passengerFlowDao.getSimulateTour(simulateMap);
                if (!simulateTour.equals(null) || simulateTour.size() > 0) {
                    List<String> listTime = DateUtil.getSubsectionDateList(startTime, endTime);
                    for (int i = 0; i < listTime.size(); i++) {
                        String k = RedisKey.SIMULATE + "getPeopleDayTrend:day:";
                        String hk = listTime.get(i) + "|" + vcode;
                        Object obj = RedisCache.getHash(redisTemplate, k, hk);
                        if (obj != null) {
                            RealPeopleDayTrendVo realPeopleDayTrendVo = (RealPeopleDayTrendVo) obj;
                            RealPeopleDayTrendVo vo = new RealPeopleDayTrendVo();
                            vo.setResident(realPeopleDayTrendVo.getAvgtime());
                            vo.setNewadd(realPeopleDayTrendVo.getResident());
                            vo.setDay(realPeopleDayTrendVo.getDay());
                            list.add(vo);
                        }
                    }
                } else {
                    for (int i = 0; i < dataList.size(); i++) {
                        RealPeopleDayTrendVo vo = new RealPeopleDayTrendVo();
                        vo.setResident(dataList.get(i).get("resident") + "");
                        vo.setNewadd(dataList.get(i).get("newadd") + "");
                        vo.setDay(dataList.get(i).get("currentDate") + "");
                        list.add(vo);
                    }
                }
            }
            } else {
                JSONArray json = JSONArray.fromObject(value);
                for (int i = 0; i < json.size(); i++) {
                    RealPeopleDayTrendVo realPeopleDayTrendVo = new RealPeopleDayTrendVo();
                    JSONObject job = json.getJSONObject(i);
                    String resident = String.valueOf(job.get("resident"));//游客人数
                    String leaveAvgtime = String.valueOf(job.get("leave_avgtime"));//平均驻留时长
                    String dayDate = String.valueOf(job.get("day"));//日期
                    realPeopleDayTrendVo.setNewadd(resident);
                    realPeopleDayTrendVo.setResident(leaveAvgtime);
                    realPeopleDayTrendVo.setDay(dayDate);
                    list.add(realPeopleDayTrendVo);
                }
            }

        return list;
    }

    /**
     * 景区游客驻留时长
     *
     * @param dateTime
     * @param vcode
     * @return
     */
    @Override
    public Map<String, Object> getPeopleRemain(String dateTime, String vcode) {
        //发送 GET 请求
        //visitorType游客类型  1 代表 “全部游客” 2 代表 “国内游客” 3 代表“国际游客”
        //timeType 1 代表 日；2 代表 周 ；3 代表 月； 4 代表 季 ；5 代表 年；
        String condition = "&date=" + dateTime;//拼上条件
        String str = HttpRequestUtil.sendGet(Constants.Bigdata + "visitorStayTime/list", SCENICCODE + condition);//url调用接口
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        String value = null;
        //创建对象与封装
        try {
            value = jasonObject.getString("data");
        } catch (Exception e) {
            e.getStackTrace();
        }
        List<RealPeopleRemainVo> list = new LinkedList();
        JSONObject jsonvalue = JSONObject.fromObject(value);
        Map map = new HashMap();
        String todayavg = "0"; //今日百分比
        if (jsonvalue.size() > 0) {
            for (int i = 0; i < 1; i++) {
                todayavg = String.valueOf(jsonvalue.get("todayavg"));//今日百分比
                String totalnum = String.valueOf(jsonvalue.get("totalnum"));//人数时间数据
                JSONArray json = JSONArray.fromObject(totalnum);
                for (int j = 0; j < json.size(); j++) {//循环json中的数据
                    RealPeopleRemainVo remainVo = new RealPeopleRemainVo();
                    JSONObject job = json.getJSONObject(j);
                    String num = String.valueOf(job.get("totalNum"));
                    String date = String.valueOf(job.get("hoursegment"));
                    if (!"".equals(date) && date.equals("48")) {
                        remainVo.setNum(num);
                        remainVo.setTime(date + "小时以上");//前端展示拼串，48小时以上
                        list.add(remainVo);
                    } else {
                        remainVo.setNum(num);
                        remainVo.setTime(date + "小时");//前端展示拼串
                        list.add(remainVo);
                    }
                }
            }
            map.put("percent", todayavg);//百分比
            map.put("dataList", list);//数据集合
        }else{
            System.out.println("无数数据，获取模拟数据或对接数据--");
            Map paramMap = new HashMap();
            paramMap.put("currentDate",dateTime);
            paramMap.put("vcode",vcode);
            List<Map<String,Object>> resultList = passengerFlowDao.getPeopleRemainByYts(paramMap);
            //TODO 若没有数据 此处模拟上数据 华清宫需求
            if(resultList.size() == 0 ||resultList.equals(null)){
                int two = (int) (9 * Math.random() +1);
                map.put("percent", "5." + two);//百分比
                Random random = new Random();
                RealPeopleRemainVo realPeopleRemainVo1 = new RealPeopleRemainVo();
                int hh = Integer.parseInt(DateTools.getCurrentHH());
                realPeopleRemainVo1.setNum(hh + "");
                realPeopleRemainVo1.setTime("0-1小时");
                list.add(realPeopleRemainVo1);
                RealPeopleRemainVo realPeopleRemainVo2 = new RealPeopleRemainVo();
                realPeopleRemainVo2.setNum(hh * 3 + "");
                realPeopleRemainVo2.setTime("1-2小时");
                list.add(realPeopleRemainVo2);
                RealPeopleRemainVo realPeopleRemainVo3 = new RealPeopleRemainVo();
                realPeopleRemainVo3.setNum(hh * 16 + random.nextInt(3)*26 + 16 + "");
                realPeopleRemainVo3.setTime("2-6小时");
                list.add(realPeopleRemainVo3);
                RealPeopleRemainVo realPeopleRemainVo4 = new RealPeopleRemainVo();
                realPeopleRemainVo4.setNum(hh * 13 + random.nextInt(3)*23 + 19 + "");
                realPeopleRemainVo4.setTime("6小时以上");
                list.add(realPeopleRemainVo4);

                map.put("dataList", list);//数据集合
            }else {
                for (int i = 0; i < resultList.size(); i++) {
                    RealPeopleRemainVo vo = new RealPeopleRemainVo();
                    vo.setNum(resultList.get(i).get("num") + "");
                    vo.setTime(resultList.get(i).get("hoursTime") + "");
                    list.add(vo);
                }
                int two = (int) (11 * Math.random() + 88);
                map.put("percent", "21." + two);//百分比
                map.put("dataList", list);//数据集合
            }
        }
        return map;
    }

    /**
     * 景区省外客流来源排名前十
     * 此接口只能云台山景区使用
     * 调用的大数据
     * @param year
     * @param vcode
     * @return
     */
    @Override
    public List<RealPeopleComeFromVo> getPeopleComeFromRank(String year, String vcode) {
        String startTime = year + "-01-01";//拼一个接口需要的完整时间
        String endTime = year + "-12-31";
        //发送 GET 请求
        //visitorType游客类型  1 代表 “全部游客” 2 代表 “国内游客” 3 代表“国际游客”
        //timeType 1 代表 日；2 代表 周 ；3 代表 月； 4 代表 季 ；5 代表 年；
        String condition = "&timeType=5&visitorType=2&startDate=" + startTime + "&endDate=" + endTime;//拼上条件
        String str = HttpRequestUtil.sendGet(Constants.Bigdata + "visitorSource/list", SCENICCODE + condition);//url调用接口
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        String value = null;
        //创建对象与封装
        value = jasonObject.getString("datas");
        List list = new LinkedList();
        if (value.equals("[]")) {
            System.out.println("无数据！");
        } else {
            JSONArray json = JSONArray.fromObject(value);
            for (int i = 0; i < 12; i++) {//取前十名，因为会过滤二个省内，所以循环12
                JSONObject job = json.getJSONObject(i);
                String sourceCity = String.valueOf(job.get("sourceCity"));//来源城市
                String sourcecityNew = String.valueOf(job.get("sourcecityNew"));//省份游客数
                String sourcecityPro = String.valueOf(job.get("sourcecityPro"));//游客占比
                RealPeopleComeFromVo comeFromVo = FiltrationUtil.getRealPeopleList(vcode, sourceCity, sourcecityNew, sourcecityPro);
                if (comeFromVo.getSourceCity() != null) {
                    list.add(comeFromVo);
                } else {
                    continue;
                }
            }
        }
        return list;
    }


    /**
     * 云台山定制微件数据
     * @param vcode
     * @param dateTime   当前的年份
     * @param open
     * @return
     */
    @Override
    public Map<String, Object> getBigPassengerAll(String vcode, String dateTime,String open) {
        //缓存添加
        String k= RedisKey.MOUDEL_PASSENGERFLOW+"getPeopleComeFrom:getBigPassengerAll:";
        //hashkey
        String hk="year|"+dateTime+"|"+vcode;
        Object obj= RedisCache.getHash(redisTemplate,k, hk);
        Map map = new HashMap();
        //根据VCODE获取景区名称和经纬度
        List<JqScenicAreas> scenicList = jqScenicMsgService.getJqScenicList(vcode);
        if(obj==null || "yes".equals(open)){
            List<RealPeopleComeFromTopVo> comeTopTen = new LinkedList<>();
            List<ProvinceLicensePlateVo> PlateVo = new LinkedList<>();
            List<RealPeopleComeFromVo> comeFromVo = new LinkedList<>();
            try {
                //景区地图来源
                comeTopTen = getPeopleComeTopTen(vcode, dateTime);//调用的大数据
                RealPeopleComeFromTopVo taiwan = new  RealPeopleComeFromTopVo();
                taiwan.setSourceCity("台湾省");
                taiwan.setSourcecityNew("1246");
                comeTopTen.add(taiwan);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                //景区年统计省内客流分析 TOP 10  TODO 省内游客TOP10  非常慢
                PlateVo = getProvinceLicensePlate(vcode, dateTime);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                //景区年统计省外游客数量 TOP 10 //TODO 省外游客TOP10
                comeFromVo =getPeopleComeFrom(vcode, dateTime);
                Collections.sort(comeFromVo,new Comparator<RealPeopleComeFromVo>() {
                    @Override
                    public int compare(RealPeopleComeFromVo o1, RealPeopleComeFromVo o2) {
                        //进行降序排列
                        if (Integer.parseInt(o1.getSourcecityNew()) < Integer.parseInt(o2.getSourcecityNew())) {
                            return 1;
                        }
                        if (Integer.parseInt(o1.getSourcecityNew()) == Integer.parseInt(o2.getSourcecityNew())) {
                            return 0;
                        }
                        return -1;
                    }
                });
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            map.put("comeFromByMap", comeTopTen);
            map.put("platePeople", PlateVo);
            map.put("comeFromPeople", comeFromVo);
            if(scenicList.size()>0){
                map.put("scenicList",scenicList.get(0));
            }else{
                JqScenicAreas jq = new JqScenicAreas();
                map.put("scenicList",jq);
            }
            RedisCache.putHash(redisTemplate,k,hk,map);
        }else{
            map =(Map) obj;
        }
        return map;
    }


    /**
     * 景区游客来源分析
     * 通用大微件
     * lrd 2017-09-08
     * @param vcode
     * @param dateTime
     * @param open
     * @return
     */
    @Override
    public Map<String, Object> getPeopleYearCommon(String vcode, String dateTime, String open) {
        //缓存添加
        String k= RedisKey.MOUDEL_PASSENGERFLOW+"getPeopleComeFrom:getPeopleYearCommon:";
        //hashkey
        String hk="year|"+dateTime+"|"+vcode;
        Object obj= RedisCache.getHash(redisTemplate,k, hk);
        Map map = new HashMap();
        //根据VCODE获取景区名称和经纬度
        List<JqScenicAreas> scenicList = jqScenicMsgService.getJqScenicList(vcode);
        if(obj==null || "yes".equals(open)){
            List<Map<String,Object>> comeTopTen = new LinkedList<>();
            List<ProvinceLicensePlateVo> PlateVo = new LinkedList<>();
            List<ProvinceLicensePlateVo> comeFromVo = new LinkedList<>();
            try {
                //景区地图来源
                comeTopTen = getPeopleComeTopTenCommon(vcode, dateTime);//通用微件。
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                //景区年统计省内客流分析 TOP 10  TODO 省内游客TOP10
                PlateVo = getProvinceLicensePlate(vcode, dateTime);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                //景区年统计省外游客数量 TOP 10 //TODO 省外游客TOP10
                comeFromVo =getProvinceOutside(dateTime,vcode);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            map.put("comeFromByMap", comeTopTen);
            map.put("platePeople", PlateVo);
            map.put("comeFromPeople", comeFromVo);
            RedisCache.putHash(redisTemplate,k,hk,map);
        }else{
            map =(Map) obj;
        }
        if(scenicList.size()>0){
            map.put("scenicList",scenicList.get(0));
        }else{
            JqScenicAreas jq = new JqScenicAreas();
            map.put("scenicList",jq);
        }
        return map;
    }

    //按日方法获取计算(补偿的是6天)
//    @Override
//    public void getPassengerFlowByDay(String vcode, String startTime, String endTime) {
//        Map  map = new HashMap();
//        map.put("vcode",vcode);
//        map.put("startTime",startTime);
//        map.put("endTime",endTime);
//        List<Map<String, Object>> list = passengerFlowDao.getPassengerFlowByDay(map);
//        if(list.size()>0 && list!=null){
//            String k = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
//                for (int i = 0; i <list.size() ; i++) {
//                    String hk = list.get(i).get("time")+"|"+vcode;
//                    RedisCache.putHash(redisTemplate,k,hk,list.get(i));
//                }
//        }
//    }

    @Override
    public void getPassengerFlowByDay(String vcode, String startTime, String endTime) {

        List<String> list = DateUtil.getSubsectionDateList(startTime,endTime);
        if(list.size()>0 && list!=null){
            for (int i = 0; i <list.size() ; i++) {
                Map  map = new HashMap();
                map.put("vcode",vcode);
                String start = list.get(i)+" 00:00:00";
                String end = list.get(i)+" 23:59:59";
                map.put("startTime",start);
                map.put("endTime",end);
                //进入人数
                List<Map<String, Object>> listEnter = passengerFlowDao.getPassengerFlowByThisDay(map);
                //离开人数
                List<Map<String, Object>> listLeave = passengerFlowDao.getPassengerFlowByThisDayLeave(map);

                int enter = Integer.parseInt(listEnter.get(0).get("num").toString());
                int leave = Integer.parseInt(listLeave.get(0).get("num").toString());
                int num = enter - leave;
                Map  data = new HashMap();
                //TODO 此处增加实时人数 进园累计人数 出园累计人数  12.19
                data.put("num",enter);//累计
                data.put("real",num);//实时
                data.put("leave",leave);//出园
                data.put("time",list.get(i));

                String k = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
                String hk = list.get(i)+"|"+vcode;
                RedisCache.putHash(redisTemplate,k,hk,data);
            }

        }
    }

    //按月方法获取计算
    @Override
    public void getPassengerFlowByMonth(String vcode,String month,String startTime,String endTime) {
        //获取当前日期时间段的所有日期，查询每日缓存，进行累加得出月缓存
        List<String> list = DateUtil.getSubsectionDateList(startTime,endTime);
        int result =0;
        if(list.size()>0 && list!=null){
            //日统计的主KEY
            String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
            for (int i = 0; i <list.size() ; i++) {
                //日统计每天实时人数的小KEY
                String hk = list.get(i)+"|"+vcode;
                Object obj= RedisCache.getHash(redisTemplate,datKey, hk);
                if(obj!=null){
                    Map map = (Map)obj;
                    result += Integer.parseInt(map.get("num")+"");
                }
            }

            //月统计主KEY
            String monthKey = RedisKey.MOUDEL_PASSENGERFLOW+"month:"+"getPassengerFlowByMonth:";
            //月统计小KEY
            String montHk = month+"|"+vcode;
            Map resultMap= new HashMap();
            resultMap.put("num",result);
            RedisCache.putHash(redisTemplate,monthKey,montHk,resultMap);

        }
    }
    //按年方法获取计算（获取月份的缓存进行累加，统计年份缓存）
    @Override
    public void getPassengerFlowByYear(String vcode,String year) {
        //月份统计主KEY
        String monthKey = RedisKey.MOUDEL_PASSENGERFLOW+"month:"+"getPassengerFlowByMonth:";
        int result=0;
        String[] month = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        for (int i=0;i<month.length;i++){
            //月份统计小KEY
            String monthHk = year+"-"+month[i]+"|"+vcode;
            Object obj= RedisCache.getHash(redisTemplate,monthKey, monthHk);
            if(obj!=null){
                Map map = (Map)obj;
                result += Integer.parseInt(map.get("num").toString());
            }
        }
        //年份统计主KEY
        String yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"year:"+"getPassengerFlowByYear:";
        String yearHk = year+"|"+vcode;
        Map<String,Object> resultMap= new HashMap();
        resultMap.put("num",result);
        RedisCache.putHash(redisTemplate,yearKey,yearHk,resultMap);

    }

    //统计景区每天各小时的实时人数
    @Override
    public void getPassengerFlowByHour(String vcode, String stime,String time) {
        //小时统计主KEY
        String hoursKey = RedisKey.MOUDEL_PASSENGERFLOW+"hours:"+"getPassengerFlowByHour:";
        List<String> timeList = DateUtil.getSubsectionDateList(stime,time);
        for (int i = 0; i < timeList.size(); i++) {
            String hourHk = timeList.get(i)+"|"+vcode;
            Map map = new HashMap();
            map.put("time",timeList.get(i));
            map.put("vcode",vcode);
            //TODO 修改小时人数统计 分实时，小时，累计   12.19
            List<Map<String,Object>> list = passengerFlowDao.getPassengerFlowByHour(map);
            int sum = 0;
            int leave = 0;
            List<Map<String,Object>> dataList = new ArrayList<>();
            if(list.size()> 0 && list!=null){
                for(Map data : list){
                    Map maps = new HashMap();
                    maps.put("time",data.get("time"));
                    sum = sum + Integer.parseInt(data.get("num").toString());
                    maps.put("sum",sum);
                    maps.put("num",Integer.parseInt(data.get("num").toString()));
                    leave = leave + Integer.parseInt(data.get("leaveNum").toString());
                    maps.put("real",sum + leave);
                    maps.put("maxquantity",data.get("maxquantity"));
                    dataList.add(maps);
                }
                RedisCache.putHash(redisTemplate,hoursKey,hourHk,dataList);
            }
        }
    }

    //景点实时人数数据的获取
    @Override
    public void getPassengerFlowByScenic(String vcode) {
        String time = DateUtil.getCurDateStr();
        String lastTime = DateTools.addDate(time, -6);
        List<String> timeList = DateUtil.getSubsectionDateList(lastTime, time);
        //获取景区的景点进行循环查询
        List<Map<String,Object>> scenicNames = scenicSpotInfoDao.getScenicMaxquantity(vcode);
        if(scenicNames == null || scenicNames.size() < 1){
            System.out.print("该景区没添加景点数据！");
        }
        Map datamap =new HashMap();
        datamap.put("vcode",vcode);
        //获取每个景点今天--前6天的实时数据
        for (int k  = 0;  k< timeList.size(); k++) {
            List<Map<String,Object>> list = new ArrayList<>();
            for (int i = 0; i < scenicNames.size(); i++) {
                datamap.put("name", scenicNames.get(i).get("name"));
                Map<String, Object> map = new HashMap<>();
                map.put("scenicName", scenicNames.get(i).get("name"));
                datamap.put("time",timeList.get(k));
                Map<String, Object> result = passengerFlowDao.getPassengerFlowByScenic(datamap);
                map.put("num", result.get("num"));
                map.put("maxquantity", scenicNames.get(i).get("maxquantity") != null?Integer.valueOf(scenicNames.get(i).get("maxquantity").toString()):0);//最大承载量
                list.add(map);
            }
            if(list.size()>0 && list!=null){//景点有数据才存。无数据不存
                //景点统计主KEY
                String scenicKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenic:day:"+"getPassengerFlowByScenic:";
                String scenicHk = timeList.get(k)+"|"+vcode;
                RedisCache.putHash(redisTemplate,scenicKey,scenicHk,list);
            }
            System.out.print(String.format(timeList.get(k)+"-----实时景点人数数据缓存完成！"));
        }
    }

//    @Override
//    public void getPassengerFlowByThisHours(String vcode, String startTime, String endTime,String time,String year,String month) {
//        Map  map = new HashMap();
//        map.put("vcode",vcode);
//        map.put("startTime",startTime);
//        map.put("endTime",endTime);
//        List<Map<String, Object>> list = passengerFlowDao.getPassengerFlowByThisDay(map);
//        if(list.size()>0 && list!=null){
//            if(list.get(0).get("num").toString()!="0"){//如果人数为0.没必须走下面方法
//                int num = Integer.parseInt(list.get(0).get("num").toString());
//                //更新天缓存
//                String dayKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
//                String dayhk = time+"|"+vcode;
//                Object dayObject= RedisCache.getHash(redisTemplate,dayKey, dayhk);
//                //获取当前缓存中今天的数据  月缓存-之前的当天缓存+最实时的缓存
//                int daynum = 0;
//                if(dayObject!=null){
//                    Map dmap = (Map)dayObject;
//                    Map<String,Object> dayMap = new HashMap<>();
//                    daynum = Integer.parseInt(dmap.get("num")+"");
//                    //存入新缓存
//                    dayMap.put("num",num);
//                    dayMap.put("time",time);
//                    RedisCache.putHash(redisTemplate,dayKey,dayhk,dayMap);
//                }
//                //更新月缓存
//                String monthKey = RedisKey.MOUDEL_PASSENGERFLOW+"month:"+"getPassengerFlowByMonth:";
//                String monthhk = month+"|"+vcode;
//                Object monthobj= RedisCache.getHash(redisTemplate,monthKey, monthhk);
//                if(monthobj!=null){
//                    Map mmap = (Map)monthobj;
//                    Map<String,Object> monthMap = new HashMap<>();
//                    monthMap.put("num",Integer.parseInt(mmap.get("num")+"")-daynum+num);
//                    RedisCache.putHash(redisTemplate,monthKey,monthhk,monthMap);
//                }
//                //更新年缓存
//                String yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"year:"+"getPassengerFlowByYear:";
//                String yearhk = year+"|"+vcode;
//                Object yearobj= RedisCache.getHash(redisTemplate,yearKey,yearhk);
//                if(yearobj!=null){
//                    Map<String,Object> yearMap = new HashMap<>();
//                    Map ymap= (Map)yearobj;
//                    yearMap.put("num",Integer.parseInt(ymap.get("num")+"")-daynum+num);
//                    RedisCache.putHash(redisTemplate,yearKey,yearhk,yearMap);
//                }
//            }
//        }
//    }



    //lyl 12.15  修改数据模式，增加出园人数
    @Override
    public void getPassengerFlowByThisHours(String vcode, String startTime, String endTime,String time,String year,String month) {
        Map  map = new HashMap();
        map.put("vcode",vcode);
        map.put("startTime",startTime);
        map.put("endTime",endTime);

        //获取同一时间进园人数和出园人数
        List<Map<String, Object>> list = passengerFlowDao.getPassengerFlowByThisDay(map);
        List<Map<String, Object>> listLeave = passengerFlowDao.getPassengerFlowByThisDayLeave(map);

        //判断是否都没数据
        if(list.size()>0 && list!=null || listLeave.size()>0 && listLeave != null){
            int enter = 0;
            int leave =0;
            if(list.size()>0 && list!=null) {
                enter = Integer.parseInt(list.get(0).get("num").toString());
            }
            if(listLeave.size()>0 && listLeave!=null) {
                leave = Integer.parseInt(listLeave.get(0).get("num").toString());
            }
           int num = enter - leave;
            if(num != 0){//如果人数为0.没必须走下面方法
                //更新天缓存
                String dayKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
                String dayhk = time+"|"+vcode;
                Object dayObject= RedisCache.getHash(redisTemplate,dayKey, dayhk);
                //获取当前缓存中今天的数据  月缓存-之前的当天缓存+最实时的缓存
                int daynum = 0;
                if(dayObject!=null){
                    Map dmap = (Map)dayObject;
                    Map<String,Object> dayMap = new HashMap<>();
                    daynum = Integer.parseInt(dmap.get("num")+"");
                    //存入新缓存
                    //TODO 此处增加实时人数 进园累计人数 出园累计人数  12.19
                    dayMap.put("num",enter);//累计
                    dayMap.put("real",num);//实时
                    dayMap.put("leave",leave);//出园
                    dayMap.put("time",time);
                    RedisCache.putHash(redisTemplate,dayKey,dayhk,dayMap);
                }
                //更新月缓存
                String monthKey = RedisKey.MOUDEL_PASSENGERFLOW+"month:"+"getPassengerFlowByMonth:";
                String monthhk = month+"|"+vcode;
                Object monthobj= RedisCache.getHash(redisTemplate,monthKey, monthhk);
                if(monthobj!=null){
                    Map mmap = (Map)monthobj;
                    Map<String,Object> monthMap = new HashMap<>();
                    monthMap.put("num",Integer.parseInt(mmap.get("num")+"")-daynum+num);
                    RedisCache.putHash(redisTemplate,monthKey,monthhk,monthMap);
                }
                //更新年缓存
                String yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"year:"+"getPassengerFlowByYear:";
                String yearhk = year+"|"+vcode;
                Object yearobj= RedisCache.getHash(redisTemplate,yearKey,yearhk);
                if(yearobj!=null){
                    Map<String,Object> yearMap = new HashMap<>();
                    Map ymap= (Map)yearobj;
                    yearMap.put("num",Integer.parseInt(ymap.get("num")+"")-daynum+num);
                    RedisCache.putHash(redisTemplate,yearKey,yearhk,yearMap);
                }
            }
        }
    }

   //节假日数据实时人数  lrd 2017-08-19 这样做是保证如果当天是节假日。那么展示的时候，实时人数需求滚动，避免请求这个方法太频繁。
    @Override
    public void getPassengerFlowByHoliay(String vcode, String time,String year) {
        //节假日的主key
        String holidayKey = RedisKey.MOUDEL_PASSENGERFLOW+"holiday:"+"getPassengerFlowByHoliay:";
        String holidayhk = year+"|"+vcode;
        String condition = "&year=" + year;//拼上条件
       //从大数据获取今年的节假日
        String str = HttpRequestUtil.sendGet(Constants.Bigdata + "searchHolidayByYear", condition);//url调用接口
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        JSONArray value = null;
        //创建对象与封装
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Integer result = 0;//节假日总人数
        try {
            value = jasonObject.getJSONArray("datas");
            if (value.size() < 1) {//节假日接口无数据判断
            } else {
                JSONArray json = JSONArray.fromObject(value);
                //日统计的主KEY
                String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
                Date thisday = df.parse(time);
                for (int i = 0; i < json.size(); i++) {//循环节假日有几天，查询统计数据
                    JSONObject job = json.getJSONObject(i);
                    String dayTime = String.valueOf(job.get("searchDate"));//循环获取节假日的时间
                    //如果当前节假日期小与当前日期，才进行。当前日期和当前日期之后的，不需要计算。如果当前日期是节假日。那么会在节假日实时人数接口处理。保证人数实时。
                    Date holiday = df.parse(dayTime);
                    if(holiday.getTime()<thisday.getTime()){
                        //按日的实时人数key
                        String hk =dayTime+"|"+vcode;
                        Object obj= RedisCache.getHash(redisTemplate,datKey, hk);
                        if(obj!=null){
                            Map map = (Map)obj;
                            result += Integer.parseInt(map.get("num").toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        //存入缓存。
        Map  resMap = new HashMap();
        resMap.put("num",result);
        RedisCache.putHash(redisTemplate,holidayKey,holidayhk,resMap);

    }

    @Override
    public void getMaxPassengerFlowvoid(String vcode, String time, String year) {

        String startTime = year+"-01-01";
        List<String> list = DateUtil.getSubsectionDateList(startTime,time);

        //当天的人数
        String dayKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
        String dayhk = time+"|"+vcode;
        Object dayObject= RedisCache.getHash(redisTemplate,dayKey, dayhk);
        Map dmap = (Map)dayObject;
        Map<String,Object> dayMap = new HashMap<>();
        int daynum = Integer.parseInt(dmap.get("num")+"");
        Map<String,Object> yearMap = new HashMap<>();
        yearMap.put("time",time);
        yearMap.put("num",daynum);
        //原峰值
        String yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"MaxPassenger:";
        String yearhk = year+"|"+vcode;
        Object yearObject= RedisCache.getHash(redisTemplate,yearKey, yearhk);
        int num = 0;
        //判断大小更新大的
        if(yearObject!=null){
            Map map = (Map)yearObject;
            num = Integer.parseInt(map.get("num")+"");
            //存入新缓存
            if(num < daynum){
                RedisCache.putHash(redisTemplate,yearKey,yearhk,yearMap);
            }
        }else{
            String fhey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
            String fhk = startTime+"|"+vcode;
            Object fObject= RedisCache.getHash(redisTemplate,dayKey, dayhk);
            Map<String,Object> mapList = new HashMap<>();
            mapList.put("time",startTime);
            mapList.put("num",0);
            int fnum = 0;
            for(String str : list) {
                String hey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
                String hk = str+"|"+vcode;
                Object object= RedisCache.getHash(redisTemplate,dayKey, dayhk);
                if(object !=null){
                    Map fMap = (Map) object;
                    int nums = Integer.parseInt(fMap.get("num")+"");
                    if(nums > fnum){
                        fnum = nums;
                        mapList.put("time",str);
                        mapList.put("num",fnum);
                    }
                }
            }
            RedisCache.putHash(redisTemplate, yearKey, yearhk, mapList);
        }
    }

    @Override
    public void getAvergePassengerYear(String vcode, String year) {
        Map mapParm = new HashMap();
        mapParm.put("vcode",vcode);
        mapParm.put("year",year);
        Map num = passengerFlowDao.getYearPassengerDays(mapParm);
        String hey = RedisKey.MOUDEL_PASSENGERFLOW+"numberOfDays:"+"getTotalDays:";
        String hk = year+"|"+vcode;
        RedisCache.putHash(redisTemplate, hey, hk, num);
    }

    @Override
    public List<Map> getYCHBHWifiList(String vcode) {
        return realWifiDao.getYCHBHWifiList(vcode);
    }

    @Override
    public Map getYCHBHWifiNums(Map parMap) {
        return realWifiDao.getYCHBHWifiNums(parMap);
    }

    @Override
    public List<Map> getYCHBHPassengerFlowByYear(String strTime, String endTime) {
        List<Map> list = new ArrayList<>();
        for(int i = Integer.parseInt(strTime) ; i <= Integer.parseInt(endTime) ; i++) {
            String start = i + "-01-01";
            String end = i + "-12-31";
            Map map = new HashMap<>();
            map.put("strTime", start);
            map.put("endTime", end);
            Map data = new HashMap<>();
            data = passengerFlowDao.getYCHBHPassengerFlowByYear(map);
            if(data == null || data.size() == 0){
                Map datas = new HashMap<>();
                datas.put("num",0);
                datas.put("time",i+"");
                list.add(datas);
            }else {
                list.add(data);
            }
        }
        return list;
    }

    @Override
    public Map getYCHBHPassengerFlowByYMonth(String time) {
        Map dataList = new HashMap<>();
        String[] times = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        String nowYear = DateTools.getCurrentYear();
        String oldYear = Integer.parseInt(time) - 1 + "";
        int a = times.length;
        if(nowYear.equals(time)) {
            int nowMonth = Integer.parseInt(DateTools.getCurrentMonths());
            a = nowMonth;
        }
        List<Map> nowDatas = new ArrayList<>();
        List<Map> oldDatas = new ArrayList<>();
        for (int i = 0; i < a; i++) {
            Map map = new HashMap<>();
            String month = times[i];
            String strTime = time + "-" + month + "-01";
            String endTime = time + "-" + month + "-31";
            Map param = new HashMap<>();
            param.put("strTime",strTime);
            param.put("endTime",endTime);
            List<Map> data = new ArrayList<>();
            data = passengerFlowDao.getYCHBHPassengerFlowByYMonth(param);
            String judge = data.get(0).get("num").toString();
            if(judge.equals("-99")) {
                map.put("num",0);
                map.put("time",month);
            }else {
                map.put("num",data.get(0).get("num"));
                map.put("time",data.get(0).get("time"));
            }
            nowDatas.add(map);
        }
        for (int i = 0; i < a; i++) {
            Map map = new HashMap<>();
            String month = times[i];
            String strTime = oldYear + "-" + month + "-01";
            String endTime = oldYear + "-" + month + "-31";
            Map param = new HashMap<>();
            param.put("strTime",strTime);
            param.put("endTime",endTime);
            List<Map> data = new ArrayList<>();
            data = passengerFlowDao.getYCHBHPassengerFlowByYMonth(param);
            String judge = data.get(0).get("num").toString();
            if(judge.equals("-99")) {
                map.put("num",0);
                map.put("time",month);
            }else {
                map.put("num",data.get(0).get("num"));
                map.put("time",data.get(0).get("time"));
            }
            oldDatas.add(map);
        }
        Date nowDate = DateUtil.strToDate(nowYear, "yyyy");
        long nowTimes = nowDate.getTime();
        Date yesDate = DateUtil.strToDate(time, "yyyy");
        long yesTimes = yesDate.getTime();
        int monthSize = 12;
        if(nowTimes == yesTimes){
            String nowMonth = DateUtil.getCurSimpleMonthStr();
            monthSize = Integer.valueOf(nowMonth);
        }
        Map<String, String> thisMap = new HashMap<>();
        Map<String, String> lastMap = new HashMap<>();
        for (Map mapThis : nowDatas) {
            thisMap.put(mapThis.get("time").toString(), mapThis.get("num").toString());
        }
        for (Map mapOld : oldDatas) {
            lastMap.put(mapOld.get("time").toString(), mapOld.get("num").toString());
        }
        List<Growth> yearOnYearGrowth = new MathUtils().getYearOnYearGrowthRedisA(time,lastMap, thisMap, monthSize);
        List<Growth> annulusGrowth = new MathUtils().getAnnulusGrowthRedisA(time,thisMap, monthSize);

        dataList.put("thisTimeList",nowDatas);
        dataList.put("annulusGrowth",annulusGrowth);
        dataList.put("yearOnYearGrowth",yearOnYearGrowth);
        dataList.put("unit","人");

        return dataList;
    }

    @Override
    public List<Map> getYCHBHPassengerFlowByMonth(String year, String month) {
        String  time = year + "-" + month;
        //当前年月
        String nowTime = DateTools.getCurrentMonth();
        //当前月
        String nowMonth = DateTools.getCurrentMonths();
        //当前年
        String nowYear = DateTools.getCurrentYear();
        //当前年月日
        String now = DateTools.getCurrentDate();
        //传入月份最后一天的年月日
        String stipulate = DateTools.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));

        List<Map> list = new ArrayList<>();
        Map param = new HashMap<>();
        Date parse = null;
        Date parses = null;
        int n=2;
        String b = "01";
        try {
            parse = new SimpleDateFormat("yyyy-MM").parse(time);
            parses = new SimpleDateFormat("yyyy-MM").parse(nowTime);
            //判断查询日期与当前日期的大小，不返回数据
            if(parse.getTime() > parses.getTime()) {
               return list;
            }else if(parse.getTime() == parses.getTime()) {
                b = now.substring(now.length() - n, now.length());
            }else {
                b = stipulate.substring(stipulate.length()-n,stipulate.length());
            }
                for(int i = 0; i< Integer.parseInt(b); i++) {
                    Map map = new HashMap<>();
                    param.put("strTime",time + "-" + DateTools.getDay(i));
                    param.put("endTime",time + "-" + DateTools.getDay(i));
                    map = passengerFlowDao.getYCHBHPassengerFlowByMonth(param);
                    if(map == null){
                        map = new HashMap<>();
                        map.put("num",0);
                        map.put("time",time + "-" + DateTools.getDay(i));
                    }
                    list.add(map);
                }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getYCHBHPassengerFlowByQuarter(String year) {

        List<Map> list = new ArrayList<>();
        for(int i = 1; i<= 4; i++) {
            Map data = new HashMap<>();
            Map map = new HashMap<>();
            map = quaterDate(i,year);
            data = passengerFlowDao.getYCHBHPassengerFlowByQuarter(map);
            data.put("time",String.valueOf(i));
            list.add(data);
        }
        return list;
    }

    //无数据返回0
    private Map quaterDate (int i, String year) {
        Map map = new HashMap<>();
        if(i == 1) {
            map.put("strTime", year + "-01-01");
            map.put("endTime", year + "-03-31");
        }else if(i == 2){
            map.put("strTime", year + "-04-01");
            map.put("endTime", year + "-06-30");
        }else if(i == 3){
            map.put("strTime", year + "-07-01");
            map.put("endTime", year + "-09-31");
        }else if(i == 4){
            map.put("strTime", year + "-10-01");
            map.put("endTime", year + "-12-31");
        }
        return map;
    }


    @Override
    public List<Map> getYCHBHPassengerFlowByTime(String startTime, String endTime) {
        Map map = new HashMap<>();
        map.put("strTime",startTime);
        map.put("endTime",endTime);
        return passengerFlowDao.getYCHBHPassengerFlowByTime(map);
    }

    @Override
    public List<Map> getYCHBHPassengerFlowByMax(String startTime, String endTime) {
        Map map = new HashMap<>();
        map.put("strTime",startTime);
        map.put("endTime",endTime);
        return passengerFlowDao.getYCHBHPassengerFlowByMax(map);
    }

    @Override
    public List<Map> getYCHBHPassengerFlowBySeven() {
        List<Map> list = new ArrayList<>();
        String today = DateTools.getCurrentDate();
        String beforeDay = DateTools.getFABDay(today, -6);
        List<Date> result = new ArrayList<Date>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = null;
        Date end = null;
        try {
            end = sdf.parse(today);
            begin = sdf.parse(beforeDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while(begin.getTime()<=end.getTime()){
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }
        for(Date date : result) {
            Map map = new HashMap<>();
            String time = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
            map = passengerFlowDao.getYCHBHPassengerFlowBySeven(time);
            if(map == null) {
                Map mapResult = new HashMap();
                mapResult.put("num",0);
                mapResult.put("time",time);
                list.add(mapResult);
            }else {
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public Map getYCHBHVisitorsFlowByYear(String startTime, String endTime, String name, String vcode) {
        Map mapData = new HashMap<>();
        List<Map> list = new ArrayList<>();
        String yearKey = null;
        String yearHk = null;
        for (int i = Integer.parseInt(startTime); i <=Integer.parseInt(endTime) ; i++) {
            //判断查询全部还是景点
            if(name == null || name.equals("")) {
                yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"year:"+"getPassengerFlowByYear:";
                yearHk = i+"|"+ vcode;
            }else {
                yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenicTime:"+"year:";
                yearHk = name + "|" + i+"|"+ vcode;
            }
            Map tend1 = new HashMap();
            Object yearObj= RedisCache.getHash(redisTemplate,yearKey, yearHk);
            if(yearObj!=null){
                Map map =(Map)yearObj;
                tend1.put("num",map.get("num")+"");
                tend1.put("time",i+"");
            }else{
                tend1.put("num","0");
                tend1.put("time",i+"");
            }
            list.add(tend1);
        }
        mapData.put("dataList",list);
        //去缓存景点数据
        String scenicDayKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenicName:";
        String scenicDayhk = vcode;
        Object scenicDayObject= RedisCache.getHash(redisTemplate,scenicDayKey, scenicDayhk);
        List<Map> scenic = (List<Map>) scenicDayObject;
        List<String> listStr = new ArrayList<>();
        for(Map map : scenic){
            listStr.add(map.get("name") + "");
        }
        mapData.put("scenics",listStr);

        return mapData;
    }

    @Override
    public Map getYCHBHVisitorsFlowByQuarter(String year, String name, String vcode) {
        Map mapData = new HashMap<>();
        List<Map> list = new ArrayList<>();
        String quaterKey = null;
        String quaterHk = null;
        //判断查询全部还是景点
            if(name == null || name.equals("")) {
                quaterKey = RedisKey.MOUDEL_PASSENGERFLOW + "quarter:" + "getPassengerFlowByQuarter:";
                quaterHk = year + "|" + vcode;
            }else {
                quaterKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicTime:" + "quarter:";
                quaterHk = name + "|" + year +"|"+ vcode;
            }

            Object yearObj= RedisCache.getHash(redisTemplate, quaterKey, quaterHk);

            if(yearObj!=null){
                list = (List<Map>) yearObj;
            }else{
                for(int i = 1; i <=4; i++){
                    Map tend1 = new HashMap();
                    tend1.put("num","0");
                    tend1.put("time",i);
                    list.add(tend1);
                }
            }

        mapData.put("dataList",list);
        //去缓存景点数据
        String scenicDayKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenicName:";
        String scenicDayhk = vcode;
        Object scenicDayObject= RedisCache.getHash(redisTemplate,scenicDayKey, scenicDayhk);
        List<Map> scenic = (List<Map>) scenicDayObject;
        List<String> listStr = new ArrayList<>();
        for(Map map : scenic){
            listStr.add(map.get("name") + "");
        }
        mapData.put("scenics",listStr);
        return mapData;
    }

    @Override
    public Map getYCHBHVisitorsFlowByMonth(String year, String month, String name, String vcode) {
        Map mapData = new HashMap<>();
        String now = DateTools.getCurrentdd();
        String noewM = DateTools.getCurrentMonth();
        String monthKey = null;
        String monthHk = null;
        List<Map> list = new ArrayList<>();
        String[] day ={"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
                "16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"
        };
        int time = day.length;
        if(noewM.equals(year + "-" +month)){
            time = Integer.parseInt(now);
        }
        for (int i = 0; i < time ; i++) {
            //判断查询全部还是景点
            if(name == null || name.equals("")) {
                monthKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
                monthHk = year + "-" + month + "-"+day[i]+"|"+vcode;
            }else {
                monthKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenicTime:"+"day:";
                monthHk = name + "|" + year + "-" + month + "-"+day[i]+"|"+vcode;
            }
            Map tend = new HashMap<>();
            Object dayObj= RedisCache.getHash(redisTemplate,monthKey, monthHk);
            if(dayObj!=null){
                Map map = (Map)dayObj;
                tend.put("time",year + "-" + month + "-" + day[i]);
                tend.put("num",map.get("num"));
            }else{
                tend.put("time",year + "-" + month + "-" + day[i]);
                tend.put("num","0");
            }
            list.add(tend);
        }
        // 这里需要判断一次，选择的月份是否超过了当前的月份，如果超过了，就返回空的list，暂无数据
        boolean b = DateUtil.isNowDayToDate(year+"-"+month, DateUtil.getCurMonthStr(), "yyyy-MM");
        if(!b){
            if(Integer.valueOf(month).compareTo(Integer.valueOf(DateUtil.getCurMonthNumStr())) > 0){
                list = new ArrayList<>();
            }
        }
        mapData.put("dataList",list);
        //去缓存景点数据
        String scenicDayKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenicName:";
        String scenicDayhk = vcode;
        Object scenicDayObject= RedisCache.getHash(redisTemplate,scenicDayKey, scenicDayhk);
        List<Map> scenic = (List<Map>) scenicDayObject;
        List<String> listStr = new ArrayList<>();
        for(Map map : scenic){
            listStr.add(map.get("name") + "");
        }
        mapData.put("scenics",listStr);
        return mapData;
    }

    @Override
    public Map getYCHBHVisitorsFlowByDay(String time, String name, String vcode) {
        Map mapData = new HashMap<>();
        List<Map> listData = new ArrayList<>();
        String dayKey = null;
        String dayHk = null;
        //判断查询全部还是景点
        if(name == null || name.equals("")) {
            dayKey = RedisKey.MOUDEL_PASSENGERFLOW+"hours:"+"getPassengerFlowByHour:";
            dayHk = time +"|"+vcode;
        }else {
            dayKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenicTime:"+"hour:";
            dayHk = name + "|" + time +"|"+vcode;
        }
        Object dayObj= RedisCache.getHash(redisTemplate,dayKey, dayHk);
        List<Map> list = new ArrayList<>();

        String[] hours ={"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
                "16","17","18","19","20","21","22","23"};
        String now = DateTools.getCurrentHH();
        int nowInt = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = DateTools.getCurrentDate();
        boolean judge = DateTools.compareDay(nowTime, time);
        if(judge) {
            nowInt = 23;
        }else {
            nowInt = Integer.parseInt(now);
        }
        if(dayObj != null) {
            list = (List<Map>) dayObj;
            int n = 0;
            for(int j = 0; j < nowInt + 1; j ++) {
                String b1 = hours[j];
                if(n < list.size()) {
                    String a1 = list.get(n).get("time").toString();
                    Map mapHour = new HashMap();
                    if (b1.equals(a1)) {
                        mapHour.put("time", a1);
                        mapHour.put("num", list.get(n).get("num"));
                        n = n + 1;
                        listData.add(mapHour);
                    } else {
                        mapHour.put("time", b1);
                        mapHour.put("num", 0);
                        listData.add(mapHour);
                    }
                } else {
                    int size = list.size();
                    String hour = list.get(size-1).get("time").toString();
                    int hourInt = Integer.parseInt(hour);
                    Map mapHour = new HashMap();
                    mapHour.put("time", b1);
                    mapHour.put("num", 0);
                    listData.add(mapHour);
                }
            }
        }else {

        }
        mapData.put("dataList",listData);

        //去缓存景点数据
        String scenicDayKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenicName:";
        String scenicDayhk = vcode;
        Object scenicDayObject= RedisCache.getHash(redisTemplate,scenicDayKey, scenicDayhk);
        List<Map> scenic = (List<Map>) scenicDayObject;
        List<String> listStr = new ArrayList<>();
        for(Map map : scenic){
            listStr.add(map.get("name") + "");
        }
        mapData.put("scenics",listStr);
        return mapData;
    }

    @Override
    public Map getYCHBHVisitorsFlowByTime(String startTime, String endTime, String name, String vcode) {
        Map mapData = new HashMap<>();
        String timeKey = null;
        String timeHk = null;
        //时间段列表
        List<String> dayList = DateUtil.getSubsectionDateList(startTime,endTime);
        List<Map> list = new ArrayList<>();
        for(int i = 0; i <dayList.size() ; i++) {
            //判断查询全部还是景点
            if (name == null || name.equals("")) {
                timeKey = RedisKey.MOUDEL_PASSENGERFLOW + "day:" + "getPassengerFlowByDay:";
                timeHk = dayList.get(i) + "|" + vcode;
            } else {
                timeKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicTime:" + "day:";
                timeHk = name + "|" + dayList.get(i) + "|" + vcode;
            }
            Map tend = new HashMap<>();
            Object dayObj = RedisCache.getHash(redisTemplate, timeKey, timeHk);
            if (dayObj != null) {
                Map map = (Map) dayObj;
                tend.put("time", dayList.get(i));
                tend.put("num", map.get("num"));
            } else {
                tend.put("time", dayList.get(i));
                tend.put("num", "0");
            }
            list.add(tend);
        }
        mapData.put("dataList",list);

        //去缓存景点数据
        String scenicDayKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenicName:";
        String scenicDayhk = vcode;
        Object scenicDayObject= RedisCache.getHash(redisTemplate,scenicDayKey, scenicDayhk);
        List<Map> scenic = (List<Map>) scenicDayObject;
        List<String> listStr = new ArrayList<>();
        for(Map map : scenic){
            listStr.add(map.get("name") + "");
        }
        mapData.put("scenics",listStr);
        return mapData;
    }

    @Override
    public Map getYCHBHVisitorsFlowByYMonth(String year, String name, String vcode) {
        Map mapData = new HashMap<>();

        String[] times = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        String nowYear = DateTools.getCurrentYear();
        String oldYear = Integer.parseInt(year) - 1 + "";
        int a = times.length;
        if(nowYear.equals(year)) {
            int nowMonth = Integer.parseInt(DateTools.getCurrentMonths());
            a = nowMonth;
        }
        List<Map> nowDatas = new ArrayList<>();
        List<Map> oldDatas = new ArrayList<>();
        String timeKey = null;
        String timeHk = null;
        //循环2年数据
        for(int j = 0; j < 2 ; j++) {
            String years = "";
            if(j == 0){
                years = year;
            }else {
                years = oldYear;
            }
            for (int i = 0; i < a; i++) {
                Map map = new HashMap<>();
                String month = times[i];
                String time = years + "-" + month;
                //判断查询全部还是景点
                if (name == null || name.equals("")) {
                    timeKey = RedisKey.MOUDEL_PASSENGERFLOW + "month:" + "getPassengerFlowByMonth:";
                    timeHk = time + "|" + vcode;
                } else {
                    timeKey = RedisKey.MOUDEL_PASSENGERFLOW + "scenicTime:" + "month:";
                    timeHk = name + "|" + time + "|" + vcode;
                }
                Object dayObj = RedisCache.getHash(redisTemplate, timeKey, timeHk);
                if (dayObj == null || dayObj.equals("")) {
                    map.put("num", 0);
                    map.put("time", month);
                } else {
                    Map maps = new HashMap<>();
                    maps = (Map) dayObj;
                    map.put("num", maps.get("num"));
                    map.put("time", month);
                }
                if(j == 0) {
                    nowDatas.add(map);
                }else {
                    oldDatas.add(map);
                }
            }
        }
        Date nowDate = DateUtil.strToDate(nowYear, "yyyy");
        long nowTimes = nowDate.getTime();
        Date yesDate = DateUtil.strToDate(year, "yyyy");
        long yesTimes = yesDate.getTime();
        int monthSize = 12;
        if(nowTimes == yesTimes){
            String nowMonth = DateUtil.getCurSimpleMonthStr();
            monthSize = Integer.valueOf(nowMonth);
        }
        Map<String, String> thisMap = new HashMap<>();
        Map<String, String> lastMap = new HashMap<>();
        for (Map mapThis : nowDatas) {
            thisMap.put(mapThis.get("time").toString(), mapThis.get("num").toString());
        }
        for (Map mapOld : oldDatas) {
            lastMap.put(mapOld.get("time").toString(), mapOld.get("num").toString());
        }
        //同比
        List<Growth> yearOnYearGrowth = new MathUtils().getYearOnYearGrowthRedisA(year,lastMap, thisMap, monthSize);
        //环比
        List<Growth> annulusGrowth = new MathUtils().getAnnulusGrowthRedisA(year,thisMap, monthSize);

        mapData.put("thisTimeList",nowDatas);
        mapData.put("annulusGrowth",annulusGrowth);
        mapData.put("yearOnYearGrowth",yearOnYearGrowth);
        mapData.put("unit","人");

        //去缓存景点数据
        String scenicDayKey = RedisKey.MOUDEL_PASSENGERFLOW+"scenicName:";
        String scenicDayhk = vcode;
        Object scenicDayObject= RedisCache.getHash(redisTemplate,scenicDayKey, scenicDayhk);
        List<Map> scenic = (List<Map>) scenicDayObject;
        List<String> listStr = new ArrayList<>();
        for(Map map : scenic){
            listStr.add(map.get("name") + "");
        }
        mapData.put("scenics",listStr);

        return mapData;
    }

    @Override
    public List<Map<String, Object>> getScenicOne() {
        return passengerFlowDao.getScenicOne();
    }

    @Override
    public List<Map<String, Object>> getScenicTwo() {
        return passengerFlowDao.getScenicTwo();
    }

    @Override
    public void insertScenicOne(Map map)    {
        passengerFlowDao.insertScenicOne(map);
    }

    @Override
    public void getScenicSpotsDataAnalysisTimeAll(String vcode) {
        SimpleDateFormat TIME_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String h = "passengerflow:scenic:time:getPassengerFlowByScenic:";
        String dateFormat = "%Y-%m-%d %H";
        Date start = new Date();
        Map mapMapper = new HashMap();
        String hk = "";
        String nowDate = DateUtil.getCurDateStr();
        mapMapper.put("vcode",vcode);
        mapMapper.put("dateFormat",dateFormat);
        mapMapper.put("startTime", nowDate+" 00:00:00");
        mapMapper.put("endTime", nowDate+" 23:59:59");
        List<ScenicSpotsTimeVo> scenicSpotsTimeVos = passengerFlowDao.getScenicSpotsDataTime(mapMapper);
        for (int i = 0; i < scenicSpotsTimeVos.size(); i++) {
            Map<Object, Object> map = new TreeMap<Object, Object>();
            map.put("time", scenicSpotsTimeVos.get(i).getTime());
            map.put("name", scenicSpotsTimeVos.get(i).getName());
            map.put("count", scenicSpotsTimeVos.get(i).getCount());
            hk = scenicSpotsTimeVos.get(i).getDateTime() + DigestUtils.md5Hex(vcode);
            RedisCache.putHash(redisTemplate, h, hk, map);
        }
        Date end = new Date();
        logger.info("景区景点数据分析(公共调用存储redis方法).-------开始:%s,结束:%s,耗时:%s ", TIME_SDF.format(start), TIME_SDF.format(end), (end.getTime() - start.getTime()));
    }

    @Override
    public void getPassengerFlowByQuarter(String vcode, String year) {
        //季度统计主KEY
        String monthKey = RedisKey.MOUDEL_PASSENGERFLOW+"month:"+"getPassengerFlowByMonth:";
        List<Map> quarterMonth = DateUtil.getQuarterMonthByYear(year);
        List<Map> resList = new ArrayList<>();
        for (Map qm : quarterMonth){
            Map resMap = new HashMap();
            Integer quarter = Integer.valueOf(qm.get("quarter")+"");
            int result=0;
            //月份统计小KEY
            for(String month : (List<String >)qm.get("months")){
                String monthHk = year+"-"+month+"|"+vcode;
                Object obj= RedisCache.getHash(redisTemplate,monthKey, monthHk);
                if(obj!=null){
                    Map map = (Map)obj;
                    if(map.get("num") != null && !"".equals(map.get("num"))){
                        result += Integer.parseInt(map.get("num")+"");
                    }
                }
            }
            resMap.put("time", quarter);
            resMap.put("num", result);
            resList.add(resMap);
        }

        //年份统计主KEY
        String quarterKey = RedisKey.MOUDEL_PASSENGERFLOW+"quarter:"+"getPassengerFlowByQuarter:";
        String yearHk = year+"|"+vcode;
        RedisCache.putHash(redisTemplate,quarterKey,yearHk,resList);
    }


    /**
     * 获取随机数
     *
     * @param min 最小
     * @param max 最大
     * @return
     */
    public static String getRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return String.valueOf(s);
    }

    @Override
    public void saveYtsPassengerByDay(Map map) {
        passengerFlowDao.saveYtsPassengerByDay(map);
    }

    @Override
    public void savePassengerByDayYts(Map map) {
        passengerFlowDao.savePassengerByDayYts(map);
    }

    @Override
    public void savePassengerByTimeYts(Map map) {
        passengerFlowDao.savePassengerByTimeYts(map);
    }

    @Override
    public void savePeopleRemainByYts(Map map) {
        passengerFlowDao.savePeopleRemainByYts(map);
    }

    @Override
    public Map getRegionByName(String sourceCity) {
        return passengerFlowDao.getRegionByName(sourceCity);
    }
}
