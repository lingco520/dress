package com.daqsoft.service.client.statisticAnalysisService.impl;

import com.daqsoft.constants.Constants;
import com.daqsoft.mapper.client.scenicspotinfo.ScenicSpotInfoDao;
import com.daqsoft.mapper.client.ticket.TicketDao;
import com.daqsoft.mapper.client.timelyparking.TimelyparkingDao;
import com.daqsoft.mapper.client.travelResource.TravelResourceDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.passengerFlowService.PassengerService;
import com.daqsoft.service.client.statisticAnalysisService.StatisticAnalysisServie;
import com.daqsoft.utils.client.*;
import com.daqsoft.vo.client.madeVoBean.CarFromProvinceVo;
import com.daqsoft.vo.client.madeVoBean.NumberOfScenicSpotsVO;
import com.daqsoft.vo.client.madeVoBean.StatisticsOfTheBusVO;
import com.daqsoft.vo.client.madeVoBean.TeamTypeVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-27 10:56
 * @Version:
 * @Describe: 统计分析微件service 实现
 */
@Service
public class StatisticAnalysisServieImpl implements StatisticAnalysisServie {

    private static final String URL = "http://data.daqsoft.com/dataService/";
    private static final String SCENICCODE = "scenicCode=410800B010100002";
    private static final String TYPES = "{\"traffic\":\"交通\",\"scenic\":\"景点\",\"facilities\":\"设施\",\"diet\":\"饮食\",\"admin\":\"管理\",\"hygiene\":\"卫生\",                                                     \"price\":\"价格\",\"service\":\"服务\",\"emotion\":\"情感\",\"tourist\":\"游客\"}";
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TimelyparkingDao timelyparkingDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TravelResourceDao travelResourceDao;
    @Autowired
    private ScenicSpotInfoDao scenicSpotInfoDao;

    @Autowired
    private PassengerService passengerService;

    @Override
    public Map<String, Object> totalVisitorNumbers(String vcode, String stime, String etime) {
        Map paramap = new HashMap();
        paramap.put("vcode", vcode);
        if (stime == null || etime == null) {
            String now = DateUtils.getYear();
            stime = now + "-01-01";
            etime = now + "-12-31";
        }
        paramap.put("stime", stime);
        paramap.put("etime", etime);
        Map onLine = ticketDao.getOnLineByDay(paramap);
        Map offLine = ticketDao.getOffLineByDay(paramap);
        Map resMap = new HashMap();
        int on = onLine == null ? 0 : Integer.valueOf(String.valueOf(onLine.get("count")));
        int off = offLine == null ? 0 : Integer.valueOf(String.valueOf(offLine.get("count")));
        resMap.put("onLine", onLine == null ? 0 : onLine.get("count"));
        resMap.put("offLine", offLine == null ? 0 : offLine.get("count"));
        resMap.put("total", off + on);
        return resMap;
    }

    @Override
    public Map totalParkingNum(String vcode, String startTime, String endTime) {
        Map map = new HashMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("vcode", vcode);
        List<Map> list = timelyparkingDao.getParkByDate(map);
        // 计算总停车位数（未使用的）
        Integer total = 0;
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null) {
                    String count = list.get(i).get("count") + "";
                    if (count != null && !"".equals(count)) {
                        total += Integer.valueOf(count);
                    }
                }
            }
        }
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("total", total);
        resMap.put("list", list);
        return resMap;
    }

    @Override
    public List getParks(String vcode) {
        List list = new ArrayList();
        // 首先查询景区所有停车场
        List<Map> parkNames = scenicSpotInfoDao.getScenicNamesList(vcode);
        int thisYear = Integer.parseInt(DateUtils.getYear());
        String h = "parkspace:parking:year:";
        int k = thisYear - 4;
        for(Map map : parkNames){
            Map resMap = new HashMap();
            resMap.put("name", map.get("name"));
            List parkList = new ArrayList();
            for (int i =k; i <= thisYear; i++) {
                // 对数据进行标记,如果有存真实值，如果没有，存入0
                boolean flag = false;
                String hk = "year|"+i+"|"+DigestUtils.md5Hex(vcode);
                List<Map> dayMapList = new ArrayList<>();
                dayMapList = (List<Map>) RedisCache.getHash(redisTemplate, h, hk);
                if(!StringUtils.isEmpty(dayMapList)){
                    for(Map dayMap : dayMapList){
                        // 对停车场进行判断
                        if(map.get("name").equals(dayMap.get("name"))){
                            flag = true;
                            Map resmap = new HashMap();
                            resmap.put("year", i);
                            resmap.put("count", dayMap.get("count"));
                            parkList.add(resmap);
                            // 相等跳出循环
                            break;
                        }
                    }
                }
                // 如果停车场在此年份没有找到数据，则给默认值0
                if(!flag){
                    Map resmap = new HashMap();
                    resmap.put("year", i);
                    resmap.put("count", 0);
                    parkList.add(resmap);
                }
            }
            resMap.put("datas", parkList);
            list.add(resMap);
        }
        return list;
    }

    @Override
    public List totalTeamNum(String vcode, String stime, String etime) {
        // 因现在旅行社没有真实数据，故模拟随机数据以供展示
        Map simulateMap = new HashMap();
        simulateMap.put("vcode", vcode);
        simulateMap.put("key", "totalTeamNum");
        Map simulateTour = passengerService.getSimulateTour(simulateMap);
        String[] regionList = (simulateTour.get("simulateTour") + "").split(";");
        String lxs = regionList[0];
        String[] lxss = lxs.split(",");
        String num = regionList[1];
        String[] nums = num.split(",");
//        String[] lxss = new String[]{"中旅总社CTS", "中国国旅", "中青旅CYTS", "康辉旅行社", "春秋旅行社"};
        List list = new ArrayList();
        Integer[] counts = new Integer[lxss.length];
        int total = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sdate = DateUtil.fomatDate(stime);
        Date edate = DateUtil.fomatDate(etime);
        int days = (int) ((edate.getTime() - sdate.getTime()) / (1000 * 3600 * 24));
        if (days < 1) {
            days = 1;
        }
        // 根据不同景区，进行数据模拟
        // 赛里木湖特殊处理，数量偏小  2018-01-03
//        if("28c8ee033e7286d791e9654929a08ecb".equals(vcode)){
        for (int i = 0; i < lxss.length; i++) {
            // 通过开始和结束时间，获取到对应相差天数，然后进行数据模拟
            counts[i] = days * RandomNumber(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
            total += counts[i];
        }
//    }
        double pro = 0.00;
        double proi = 0.00;
        for (int i = 0; i < lxss.length; i++) {
            TeamTypeVo vo = new TeamTypeVo();
            vo.setName(lxss[i]);
            vo.setCount(counts[i]);
            //又是没有乘100  11.8
            proi = new BigDecimal(counts[i] / Double.valueOf(total)).setScale(2, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100)).doubleValue();
            if (i == 4) {
                vo.setProportion(new BigDecimal(100 - pro).setScale(2, RoundingMode.HALF_DOWN).doubleValue());
            } else {
                vo.setProportion(proi);
            }
            pro += proi;
            list.add(vo);
        }

        return list;
    }

    @Override
    public List onlineComment(String vcode, String startTime, String endTime) {
        String url = "http://data.daqsoft.com/commentAnalyse/scenicCommentInfoAnalyseByDate";
        if (startTime == null || endTime == null) {
            String now = DateUtils.getYear();
            startTime = now + "-01-01";
            endTime = now + "-12-31";
        }
        List<Map> list = new ArrayList();
        try {
            String scode = travelResourceDao.getSourceByVcode(vcode);
            StringBuffer para = new StringBuffer("scenicCode=" + scode + "&startTime=" + startTime + "&endTime=" + endTime);
            String res = HttpRequestUtil.sendGet(url, para.toString());
            JSONObject jsonObject = JSONObject.fromObject(res);
            JSONObject dimension = JSONObject.fromObject(JSONObject.fromObject(jsonObject.get("data")).get("dimension"));
            JSONObject type = JSONObject.fromObject(TYPES);
            Iterator it = dimension.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Map map = new HashMap();
                map.put("type", key);
                map.put("name", type.get(key));
                map.put("count", dimension.get(key));
                list.add(map);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    @Override
    public List onlineCommentGrade(String vcode) {
        int thisYear = Integer.parseInt(DateUtils.getYear());
        String url = "http://data.daqsoft.com/commentAnalyse/scenicCommentInfoAnalyseByYears";
        String scode = travelResourceDao.getSourceByVcode(vcode);
        String para = "scenicCode=" + scode + "&years=" + thisYear + "," + (thisYear - 1);
        List list = new ArrayList();
        try {
            String res = HttpRequestUtil.sendGet(url, para);
            JSONObject jsonObject = JSONObject.fromObject(res);
            JSONArray array = JSONArray.fromObject(jsonObject.get("datas"));
            List<Map> list1 = com.alibaba.fastjson.JSONObject.parseArray(array.toString(), Map.class);
            for (Map map : list1) {
                Map map1 = new HashMap();
                map1.put("year", map.get("year"));
                map1.put("total", map.get("total"));
                map1.put("avgScore", map.get("avgScore"));
                list.add(map1);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map totalReceptionNum(String vcode, String year) {
        String thisYear = DateUtils.getYear();
        if (year != null) {
            thisYear = year;
        }
        int  lastYear = Integer.parseInt(thisYear)-1;
        String yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"year:"+"getPassengerFlowByYear:";
        Map map = new HashMap();
        map.put("year",year);
        map.put("vcode",vcode);
        String lastYearHk = lastYear+"|"+vcode;
        Object lastObj= RedisCache.getHash(redisTemplate,yearKey, lastYearHk);
        if(lastObj!=null){
            Map ymap=(Map)lastObj;
            map.put("lastYear",ymap.get("num")+"");
        }else{
            map.put("lastYear",0);
        }
        String thisYearHk = thisYear+"|"+vcode;
        Object thisObj= RedisCache.getHash(redisTemplate,yearKey, thisYearHk);
        if(thisObj!=null){
            Map lmap=(Map)thisObj;
            map.put("thisYear",lmap.get("num")+"");
        }else{
            map.put("thisYear",0);
        }
        return map;
    }

    @Override
    public Map commentAnalysis(String vcode, String year, String month, String pageSize, String page, String emotion) {
        String url = "http://data.daqsoft.com/commentAnalyse/commentDisplay";
        String scode = travelResourceDao.getSourceByVcode(vcode);
        StringBuffer str = new StringBuffer("scenicCode=" + scode + "&year=" + year +
                "&month=" + month + "&size=" + pageSize + "&page=" + page);
        if (emotion != null && !"".equals(emotion)) {
            str.append("&emotion=" + emotion);
        }
        List list = new ArrayList();
        int count = 0;
        String data = HttpRequestUtil.sendGet(url, str.toString());
        JSONObject jsonObject = JSONObject.fromObject(data);
        if(jsonObject != null){
            JSONArray datas = jsonObject.getJSONArray("datas");
            JSONObject dataObject = (JSONObject) datas.get(0);
            if(dataObject != null){
                JSONArray array = (JSONArray) dataObject.get("comments");
                count = dataObject.get("emotionTotal")!=null?(int)(dataObject.get("emotionTotal")):0;
                list = com.alibaba.fastjson.JSONObject.parseArray(array.toString());
            }
        }
        Map map = new HashMap();
        map.put("list", list);
        map.put("count", count);
        return map;
    }


    public Integer RandomNumber(Integer max, Integer min) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    /**
     * 获取景区过去5年人数统计
     * auth:tanggm
     * date:2017-07-30 15:30
     * desc:新增返回累计接待人数
     *
     * @param vcode 景区代码
     * @return
     */
    @Override
    public Map<String, Object> getNumberOfScenicSpots(String vcode) {
        String yearKey = RedisKey.MOUDEL_PASSENGERFLOW+"year:"+"getPassengerFlowByYear:";
        String curYear = DateUtils.getYear();
        int startYear = Integer.parseInt(curYear)-4;
        int tatol = 0;
        List<NumberOfScenicSpotsVO> list = new ArrayList<>();
        for (int i = startYear; i <= Integer.parseInt(curYear) ; i++) {
            NumberOfScenicSpotsVO vo =new NumberOfScenicSpotsVO();
            String yearHk = i+"|"+vcode;
            Object yearObj= RedisCache.getHash(redisTemplate,yearKey, yearHk);
            if(yearObj!=null){
                Map map = (Map)yearObj;
                vo.setNum(Integer.parseInt(map.get("num")+""));
                vo.setYear(String.valueOf(i));
                tatol += Integer.parseInt(map.get("num")+"");
            }else{
                vo.setNum(0);
                vo.setYear(String.valueOf(i));
                tatol += 0;
            }
            list.add(vo);


        }
        String dPload = "";
        if(tatol == 0){
            dPload = "0";
        }else{
            dPload = new BigDecimal(tatol).divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)+"";
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("dataList", list);
        resultMap.put("totalNum", dPload);
        return resultMap;
    }

    /**
     * 获取景区过去5年大巴统计
     *
     * @param vcode 景区代码
     * @return
     */
    @Override
    public Map<String, Object> getStatisticsOfTheBus(String vcode) {
        DecimalFormat df2 = new DecimalFormat("00.00");
        //当前年
        String curYear = DateUtils.getYear();

//        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();

        //暂时模拟数据
        List<StatisticsOfTheBusVO> list = new ArrayList<>();
        StatisticsOfTheBusVO old;
        StatisticsOfTheBusVO vo;
        for (int i = 4; i >= 0; i--) {
            vo = new StatisticsOfTheBusVO();
            vo.setNum(RandomNumber(10000, 5000));
            vo.setYear(String.valueOf(Integer.valueOf(curYear) - i));
            vo.setYearOnYear(0);
            if (i != 4) {
                old = list.get(4 - i - 1);
                if (old.getNum() != 0) {
                    String format = df2.format((Double.valueOf(vo.getNum()) - Double.valueOf(old.getNum())) / Double.valueOf(old.getNum()) * 100);
                    vo.setYearOnYear(Double.valueOf(format));
                }
            }

            list.add(vo);


        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("dataList", list);
        return resultMap;
    }

    /**
     * 景区客流来源地分析
     *
     * @param vcode     景区代码
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @Override
    public List<Map> getSourceOfPassengerFlow(String vcode, String startDate, String endDate) {
        // timeType这个是表示类型：1 天、2 周、3 月、 4 季度、5 年
        // 这里按天查询，然后对天数据进行累加
        String condition = "&timeType=1&visitorType=1&startDate=" + startDate + "&endDate=" + endDate;//拼上条件
        String str = HttpRequestUtil.sendGet(Constants.Bigdata + "visitorSource/list", SCENICCODE + condition);//url调用接口

        //检查景区是否存在

        //转换为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //遍历封装
        String value = null;
        //创建对象与封装
        value = jasonObject.getString("datas");
        List<Map> passengerList = new ArrayList<>();
        if (!value.equals("[]")) {
            JSONArray jsonArray = JSONArray.fromObject(value);
            List<Map> list = (List<Map>) jsonArray;
            // 境外总数
            Integer internationNewTotal = 0;
            // 省外总数
            Integer outsideNewTotal = 0;
            // 省内总数
            Integer provinceNewTotal = 0;
            for (Map dayMap : list) {
                if (!"".equals(dayMap.get("internationNew")) && null != dayMap.get("internationNew")) {
                    internationNewTotal += Integer.valueOf(dayMap.get("internationNew") + "");
                }
                if (!"".equals(dayMap.get("outsideNew")) && null != dayMap.get("outsideNew")) {
                    outsideNewTotal += Integer.valueOf(dayMap.get("outsideNew") + "");
                }
                if (!"".equals(dayMap.get("provinceNew")) && null != dayMap.get("provinceNew")) {
                    provinceNewTotal += Integer.valueOf(dayMap.get("provinceNew") + "");
                }
            }
            JSONObject internationNew = new JSONObject();
            internationNew.put("name", "境外");
            internationNew.put("value", internationNewTotal);
            passengerList.add(internationNew);

            JSONObject outsideNew = new JSONObject();
            outsideNew.put("name", "省外");
            outsideNew.put("value", outsideNewTotal);
            passengerList.add(outsideNew);

            JSONObject provinceNew = new JSONObject();
            provinceNew.put("name", "省内");
            provinceNew.put("value", provinceNewTotal);
            passengerList.add(provinceNew);
        }
        return passengerList;
    }

    /**
     * 景区车辆来源统计分析
     *
     * @param vcode     景区代码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    @Override
    public Map<String, Object> getVehicleStatistics(String vcode, String startDate, String endDate) {
        //缓存添加 lrd 2017-08-09
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARCOMETOCITY + "vehicleStatistics:getVehicleStatistics:";
        String hk = startDate + "--" + endDate + "|" + vcode;
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        Map<String, String> map = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<>();
        if (obj == null) {
            String stTime = MathUtils.dateDayStart(startDate);//时间拼串开始
            String edTime = MathUtils.dateDayEnd(endDate);//时间拼串结束
            List<CarFromProvinceVo> carFromRank = new LinkedList<>();
            map.put("startTime", stTime);
            map.put("endTime", edTime);
            map.put("vcode", vcode);
            List<CarFromProvinceVo> carFromByAll = timelyparkingDao.getCarFromByAll(map);
            resultMap.put("dataList", carFromByAll);
            RedisCache.putHash(redisTemplate, k, hk, resultMap);
        } else {
            resultMap = (Map<String, Object>) obj;
        }
        return resultMap;
    }
    @Override
    public List<Map> getParksByMonth(String vcode, String month) {
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        String h = "parkspace:parking:month:";
        String hk = "month|"+month+"|"+ DigestUtils.md5Hex(vcode);
        List<Map> list = (List<Map>) RedisCache.getHash(redisTemplate, h, hk);
        if(null == list || list.size() == 0){
            list = new ArrayList<>();
        }
        return list;
    }
}
