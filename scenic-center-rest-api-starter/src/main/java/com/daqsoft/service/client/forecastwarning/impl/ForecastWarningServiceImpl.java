package com.daqsoft.service.client.forecastwarning.impl;

import com.daqsoft.mapper.client.forecastwarning.ForecastWarningDao;
import com.daqsoft.mapper.client.jqScenicMsg.JqScenicMsgDao;
import com.daqsoft.mapper.client.realpeople.RealPeopleDao;
import com.daqsoft.service.client.forecastwarning.ForecastWarningService;
import com.daqsoft.service.client.passengerFlowService.PassengerService;
import com.daqsoft.utils.client.*;
import com.daqsoft.vo.client.madeVoBean.*;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.daqsoft.utils.client.RandomNumberUtils.RandomNumber;


/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-22 16:24
 * @Version:
 * @Describe:
 */
@Service
public class ForecastWarningServiceImpl implements ForecastWarningService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private JqScenicMsgDao jqScenicMsgDao;
    @Autowired
    private ForecastWarningDao forecastWarningDao;
    @Autowired
    private RealPeopleDao realPeopleDao;
    private static final String TABLE_NAME = "scenicSpots:";
    private static final String BIG_SPOTS_NAME = "spots:";//景点
    private static final String BIG_TEAM_NAME = "team:";//省团队

    /**
     * 客流预测预警
     *
     * @param vcode 景区编码
     * @return
     */
    @Override
    public PassengerFlowVO getPassengerFlow(String vcode) {

        if (StringUtils.isBlank(vcode)) {
            return new PassengerFlowVO(vcode, "", 0, new ArrayList<>());
        }
        PassengerFlowVO passengerFlowVO = new PassengerFlowVO();
        passengerFlowVO.setVcode(vcode);
        passengerFlowVO.setName("");
        // 获取景区的最大承载量
        //景区景点数量，最大承载量
        Integer scenicMaxCount = 0;
        Long scenicMaxCountL = realPeopleDao.getScenicMaxCount(vcode);
        if (scenicMaxCountL != null) {
            scenicMaxCount = Integer.valueOf(scenicMaxCountL + "");
        }
        passengerFlowVO.setMaxquantity(scenicMaxCount);

        //TODO 海螺沟模拟
        if (vcode.equals("ac1d7d12306c5e60c89cfe9e6fc75027")) {
            ArrayList<RealPeopleRemainVo> resList = new ArrayList<>();
            Random random = new Random();
            for (int i = 1; i < 7; i++) {
                String date = DateTools.getFABDay(DateTools.getCurrentDate(),i);
                RealPeopleRemainVo realPeopleRemainVo = new RealPeopleRemainVo();
                realPeopleRemainVo.setTime(date);
                Calendar dayc = new GregorianCalendar();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date daystart = null;
                try {
                    daystart = df.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dayc.setTime(daystart);
                //TODO 判断是不是周末 周末人数多一点
                int week=dayc.get(Calendar.DAY_OF_WEEK)-1;
                String times = date.substring(5,10);
                if(week ==6 || week==0){//0代表周日，6代表周六
                    int num = 0;
                    List list = CommonHolidaysStr.getCommonHolidaysStr();
                    //TODO 判断是否为节假日
                    if(list.contains(times)){
                        num = RandomNumber(1000 + (random.nextInt(7)) * 10 + (random.nextInt(8) * random.nextInt(9)), 500);
                    }else {
                        num = RandomNumber(550 + (random.nextInt(7)) * 10 + (random.nextInt(8) * random.nextInt(9)), 320);
                    }
                    realPeopleRemainVo.setNum(num + "");
                }else{
                    int num = 0;
                    List list = CommonHolidaysStr.getCommonHolidaysStr();
                    //TODO 判断是否为节假日
                    if(list.contains(times)){
                        num = RandomNumber(1000 + (random.nextInt(7)) * 10 + (random.nextInt(8) * random.nextInt(9)), 500);
                    }else {
                        num = RandomNumber(320 + (random.nextInt(6)) * 10 + (random.nextInt(6) * random.nextInt(9)), 0);
                    }
                    realPeopleRemainVo.setNum(num + "");
                }
                resList.add(realPeopleRemainVo);
            }
            passengerFlowVO.setDataList(resList);

        } else {

            //设置未来七天预测人数
            // 这个地址暂时在代码中写死，以后有配置文件了，需要踢出去
            String url = "http://weather.service.geeker.com.cn/weather";
        // 返回数据定义
        ArrayList<RealPeopleRemainVo> resList = new ArrayList<>();
        try {
            List<JqScenicAreas> list = jqScenicMsgDao.getJqScenicRegion(vcode);
            JSONObject jsonObject = null;
            if (list.size() > 0) {
                String region = list.get(0).getRegion();
                String respStr = HttpRequestUtil.sendGet(url, "code=" + region);
                jsonObject = JSONObject.fromObject(respStr);
                // 如果没有查询到，对region做处理，查询到了，就原样返回
                if ("缺少参数".equals(jsonObject.get("weatherInfo")) || "对不起 暂不支持此城市天气预报".equals(jsonObject.get("weatherInfo"))) {
                    // 如果区的region没有查询到天气数据，则用市的region去查询数据
                    String cityRegion = list.get(0).getCityRegion();
                    String respCityStr = HttpRequestUtil.sendGet(url, "code=" + cityRegion);
                    jsonObject = JSONObject.fromObject(respCityStr);
                }
            }
            List<Map> dailyForecast = null;
            if (jsonObject != null) {
                Map weatherInfo = (Map) jsonObject.get("weatherInfo");
                // 获取未来7天天气数据
                dailyForecast = (List<Map>) weatherInfo.get("daily_forecast");
            }
            // 预测票务调用大数据接口地址
//            String tickUrl = "http://192.168.100.151:8100"; //这个是以前的内网地址
            String tickUrl = "http://piaowu.demo.daqsoft.com/";// 这个是映射出来的地址
            if (dailyForecast != null) {
                // 这里特殊处理，大数据返回的未来7天包括了现在的当天，预测数据不应该包括今天的数据，所以第一条不取
                for (int i = 1; i < dailyForecast.size(); i++) {
                    // 获取日期
                    String date = dailyForecast.get(i).get("date") + "";
                    // 获取温度数据
                    Map tmpMap = (Map) dailyForecast.get(i).get("tmp");
                    // 最高温度
                    String maxtem = tmpMap.get("max") + "";
                    // 最低温度
                    String mintem = tmpMap.get("min") + "";
                    // 获取天气数据
                    Map condMap = (Map) dailyForecast.get(i).get("cond");
                    // txt_d白天天气
                    String txtD = condMap.get("txt_d") + "";
                    // 获取风力数据
                    Map windMap = (Map) dailyForecast.get(i).get("wind");
                    // 风向 dir
                    String dir = windMap.get("dir") + "";
                    // 风力等级 sc
                    String sc = windMap.get("sc") + "";
                    // 数据源 name，占时大数据只支持云台山，如果有其它景区，会踢出去
                    String name = "yts";
                    // 数据类型 type   格式如：'pw' 表示票务数据 ，'zj' 表示闸机数据
                    String type = "zj";

                    // 封装请求参数,接口要求body传参的
                    Map<String, String> bodyParams = new HashMap<>();
                    bodyParams.put("date", date);
                    bodyParams.put("maxtem", maxtem);
                    bodyParams.put("mintem", mintem);
                    bodyParams.put("weather", txtD);
                    // 这里先做简单处理，大数据会优化，如果天气接口返回的值与这个预测需要传入的值对应不上，就没有数据，这里如果没有接口请求参数的值，就先默认“微风”处理
                    String[] winddirectionStrs = new String[]{"东北风", "东南风", "东风", "北风", "南风", "无持续风向", "西北风", "西南风", "西风"};
                    boolean isexists = false;
                    for (String str : winddirectionStrs) {
                        if (str.equals(dir)) {
                            isexists = true;
                            break;
                        }
                    }
                    if (!isexists) {
                        // 如果不存在，就默认暂时 取 “无持续风向”
                        dir = "无持续风向";
                    }
                    bodyParams.put("winddirection", dir);
                    bodyParams.put("windpower", sc);
                    bodyParams.put("name", name);
                    bodyParams.put("type", type);
                    // 这个返回值，咂机人数
                    String respStr = "";
                    try {
                        respStr = HttpRequestUtil.sendPostParamBody(tickUrl, bodyParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BigDecimal passengerNum = BigDecimal.ZERO;
                    // 返回值有两种情况，一种是浮点类型的，一种是返回没有数据的提示语，中文 （特征值不够，需要输入日期(date)、最高温度(maxtem)、最低温度(mintmp)、天气(weather)、风向(winddirection)、风力(windpower) 6个特征值，请重新输入）
                    // 返回值没有返回状态码，只有通过中文判断
                    String zwstr = "特征值不够，需要输入日期(date)、最高温度(maxtem)、最低温度(mintem)、天气(weather)、风向(winddirection)、风力(windpower) 数据源（nane)、数据类型（type)8个特征值，请重新输入";
                    // 返回值是double类型的值，需要做处理
                    if (!zwstr.equals(respStr) && respStr != null && !"".equals(respStr)) {
                        BigDecimal b = new BigDecimal(respStr);
                        passengerNum = b.setScale(0, BigDecimal.ROUND_HALF_UP);
                    }
                    RealPeopleRemainVo realPeopleRemainVo = new RealPeopleRemainVo();
                    realPeopleRemainVo.setTime(date);
                    realPeopleRemainVo.setNum(passengerNum.toString());
                    resList.add(realPeopleRemainVo);
                }
            }

            passengerFlowVO.setDataList(resList);
        } catch (Exception e) {
            e.printStackTrace();
            passengerFlowVO.setDataList(resList);
        }
    }
        return passengerFlowVO;
    }

    /**
     * 票务预警预测
     *
     * @param vcode 景区代码
     * @return
     */
    @Override
    public TicketForecastVO getTicketForecast(String vcode) {
        // 大数据取参数数据
        // 这个地址暂时在代码中写死，以后有配置文件了，需要踢出去
        String url = "http://weather.service.geeker.com.cn/weather";
        // 返回数据定义
        ArrayList<TicketForecastIndexOnAndOffVO> resList = new ArrayList<>();
        TicketForecastVO ticketForecastVO = new TicketForecastVO();
        try {
            List<JqScenicAreas> list = jqScenicMsgDao.getJqScenicRegion(vcode);
            JSONObject jsonObject = null;
            if(list.size() > 0){
                String region = list.get(0).getRegion();
                String respStr = HttpRequestUtil.sendGet(url,"code="+region);
                jsonObject = JSONObject.fromObject(respStr);
                // 如果没有查询到，对region做处理，查询到了，就原样返回
                if("缺少参数".equals(jsonObject.get("weatherInfo")) || "对不起 暂不支持此城市天气预报".equals(jsonObject.get("weatherInfo"))){
                    // 如果区的region没有查询到天气数据，则用市的region去查询数据
                    String cityRegion = list.get(0).getCityRegion();
                    String respCityStr = HttpRequestUtil.sendGet(url,"code="+cityRegion);
                    jsonObject = JSONObject.fromObject(respCityStr);
                }
            }
            List<Map> dailyForecast = null;
            if(jsonObject != null){
                Map weatherInfo = (Map)jsonObject.get("weatherInfo");
                // 获取未来7天天气数据
                dailyForecast = (List<Map>) weatherInfo.get("daily_forecast");
            }
            // 预测票务调用大数据接口地址
//            String tickUrl = "http://192.168.100.151:8100"; //这个是以前的内网地址
            String tickUrl = "http://piaowu.demo.daqsoft.com/";// 这个是映射出来的地址

            // 如果其它景区没有数据就查询 数据库 模拟数据
            //TODO 改变模式数据模式
            Map simulateMap = new HashMap();
            simulateMap.put("vcode", vcode);
            simulateMap.put("key", "ticketForecast");
            Map simulateTour = passengerService.getSimulateTour(simulateMap);
            String[] regionList = new String[]{};
            if(null != simulateTour){
                // ticketForecast 这个key的值，依次是 景区票务基数、票务百分比（与云台山相比较）
                regionList = (simulateTour.get("simulateTour") + "").split(",");
            }

            if(dailyForecast != null){
                // 这里特殊处理，大数据返回的未来7天包括了现在的当天，预测数据不应该包括今天的数据，所以第一条不取
                for(int i = 1; i < dailyForecast.size(); i++){
                    // 获取日期
                    String date = dailyForecast.get(i).get("date")+"";
                    // 获取温度数据
                    Map tmpMap = (Map)dailyForecast.get(i).get("tmp");
                    // 最高温度
                    String maxtem = tmpMap.get("max")+"";
                    // 最低温度
                    String mintem = tmpMap.get("min")+"";
                    // 获取天气数据
                    Map condMap = (Map)dailyForecast.get(i).get("cond");
                    // txt_d白天天气
                    String txtD = condMap.get("txt_d")+"";
                    // 获取风力数据
                    Map windMap = (Map)dailyForecast.get(i).get("wind");
                    // 风向 dir
                    String dir = windMap.get("dir")+"";
                    // 风力等级 sc
                    String sc = windMap.get("sc")+"";
                    // 数据源 name，占时大数据只支持云台山，如果有其它景区，会踢出去
                    String name = "yts";
                    // 数据类型 type   格式如：'pw' 表示票务数据 ，'zj' 表示闸机数据
                    String type = "pw";

                    // 封装请求参数,接口要求body传参的
                    Map<String, String> bodyParams = new HashMap<>();
                    bodyParams.put("date",date);
                    bodyParams.put("maxtem",maxtem);
                    bodyParams.put("mintem",mintem);
                    bodyParams.put("weather",txtD);
                    // 这里先做简单处理，大数据会优化，如果天气接口返回的值与这个预测需要传入的值对应不上，就没有数据，这里如果没有接口请求参数的值，就先默认“微风”处理
                    String[] winddirectionStrs = new String[]{"东北风","东南风","东风","北风","南风","无持续风向","西北风","西南风","西风"};
                    boolean isexists = false;
                    for(String str : winddirectionStrs){
                        if(str.equals(dir)){
                            isexists = true;
                            break;
                        }
                    }
                    if(!isexists){
                        // 如果不存在，就默认暂时 取 “无持续风向”
                        dir = "无持续风向";
                    }
                    bodyParams.put("winddirection",dir);
                    bodyParams.put("windpower",sc);
                    bodyParams.put("name",name);
                    bodyParams.put("type",type);
                    // 这个返回值，线下的
                    String respStr = "";
                    try {
                        respStr = HttpRequestUtil.sendPostParamBody(tickUrl, bodyParams);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    BigDecimal offline = BigDecimal.ZERO;
                    // 返回值有两种情况，一种是浮点类型的，一种是返回没有数据的提示语，中文 （特征值不够，需要输入日期(date)、最高温度(maxtem)、最低温度(mintmp)、天气(weather)、风向(winddirection)、风力(windpower) 6个特征值，请重新输入）
                    // 返回值没有返回状态码，只有通过中文判断
                    String zwstr = "特征值不够，需要输入日期(date)、最高温度(maxtem)、最低温度(mintem)、天气(weather)、风向(winddirection)、风力(windpower) 数据源（nane)、数据类型（type)8个特征值，请重新输入";
                    // 返回值是double类型的值，需要做处理
                    if(!zwstr.equals(respStr) && respStr != null && !"".equals(respStr)){
                        BigDecimal b = new BigDecimal(respStr);
                        offline = b.setScale(0, BigDecimal.ROUND_HALF_UP);
                    }
                    // 这里，因为大数据只对云台山做了预测，如果是其它景区，需要对数据做处理。
                    // 对返回数据进行封装
                    TicketForecastIndexOnAndOffVO indexOnAndOffVO = new TicketForecastIndexOnAndOffVO();
                    // 暂定云台山模拟基数(最大承载量)是  81000
                    // 其它景区模拟依据云台山来    81000 -> offline
                    BigDecimal ytsNum = new BigDecimal(81000);
                    // 云台山的数据取的真实数据，其它数据取数据库模拟方式的数据
                    if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
                        //线下
                        indexOnAndOffVO.setOffLine(offline+"");
                        // 线上的由于云台山客户要求，线上的数据比线下的少10%
                        String online =Math.round(offline.multiply(new BigDecimal(0.9)).doubleValue())+"";
                        indexOnAndOffVO.setOnLine(online);
                        String count = String.valueOf(Integer.valueOf(online) + Integer.valueOf(offline+""));
                        indexOnAndOffVO.setCount(count);
                    }else {
                        // 其它景区数据，因为占时大数据没有对接，所以走数据库模拟数据
                        if(regionList.length > 0){
                            BigDecimal hlgNum = new BigDecimal(regionList[0]);
                            //线下
                            offline = offline.multiply(hlgNum).divide(ytsNum, 0, BigDecimal.ROUND_HALF_UP);
                            indexOnAndOffVO.setOffLine(offline+"");
                            // 线上数据大概是线下的百分数
                            String online =Math.round(offline.multiply(new BigDecimal(regionList[1])).doubleValue())+"";
                            indexOnAndOffVO.setOnLine(online);
                            String count = String.valueOf(Integer.valueOf(online) + Integer.valueOf(offline+""));
                            indexOnAndOffVO.setCount(count);
                        }else{
                            indexOnAndOffVO.setOffLine("0");
                            indexOnAndOffVO.setOnLine("0");
                            indexOnAndOffVO.setCount("0");
                        }
                    }
                    indexOnAndOffVO.setTime(date);
                    resList.add(indexOnAndOffVO);
                }
            }
            // 获取景区的最大承载量
            //景区景点数量，最大承载量
            Integer scenicMaxCount = 0;
            Long scenicMaxCountL = realPeopleDao.getScenicMaxCount(vcode);
            if(scenicMaxCountL != null){
                scenicMaxCount = Integer.valueOf(scenicMaxCountL+"");
            }
            ticketForecastVO.setMaxquantity(scenicMaxCount);
            ticketForecastVO.setDataList(resList);
            ticketForecastVO.setName("");
            ticketForecastVO.setVcode("");
            return ticketForecastVO;
        } catch (Exception e){
            e.printStackTrace();
            ticketForecastVO.setMaxquantity(0);
            ticketForecastVO.setDataList(resList);
            ticketForecastVO.setName("");
            ticketForecastVO.setVcode("");
            return ticketForecastVO;
        }

    }
    /**
     * 节假客流预测
     *
     * @param vcode
     * @return
     */
    @Override
    public PassengerFlowVO getHolidayForecast(String vcode) {
        if (StringUtils.isBlank(vcode)) {
            return new PassengerFlowVO("","",0,new ArrayList<>());
        }
        PassengerFlowVO passengerFlowVO = new PassengerFlowVO();
        passengerFlowVO.setVcode(vcode);
        passengerFlowVO.setName("");
        passengerFlowVO.setMaxquantity( Integer.valueOf(getRandom(40000, 50000)));
        //设置节假日预测人数
        //=========模拟数据,若真实数据存在后,替换以下代码=======================
        List<RealPeopleRemainVo> list = new ArrayList<>();
        RealPeopleRemainVo remainVo;
        String[] tolidays = new String[]{"春节", "清明节", "劳动节", "端午节", "中秋节", "国庆节", "元旦节"};
        // 这里需要对景区不同，模拟数据也不同
        //TODO 改变模式数据模式
        Map simulateMap = new HashMap();
        simulateMap.put("vcode", vcode);
        simulateMap.put("key", "getHolidayForecast");
        Map simulateTour = passengerService.getSimulateTour(simulateMap);
        String[] regionList = (simulateTour.get("simulateTour") + "").split(",");
        int max = Integer.parseInt(regionList[0]);
        int min = Integer.parseInt(regionList[1]);
//        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
//            // 云台山
//            max = 500000;
//            min = 100000;
//        }else if("ac1d7d12306c5e60c89cfe9e6fc75027".equals(vcode)){
//            // 海螺沟
//            max = 30000;
//            min = 10000;
//        }
        for (String str : tolidays) {
            remainVo = new RealPeopleRemainVo();
            remainVo.setNum(getRandom(min, max));
            remainVo.setTime(str);
            list.add(remainVo);
        }
        //=========模拟数据 end=======================
        passengerFlowVO.setDataList(list);
        return passengerFlowVO;
    }

    /**
     * 团队个数预测
     *
     * @param vcode 景区代码
     * @return
     */
    @Override
    public Map<String, Object> getTeamFarecast(String vcode) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(vcode)) {
            return map;
        }

        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        //设置景区名称 最大承载量 若是节假日 最大承载量 是节假日天数的总和
        // 设置团队数预测
        //=========模拟数据,若真实数据存在后,替换以下代码=======================
        List<RealPeopleRemainVo> list = new ArrayList<>();

        // 查询今日团队数，真实数据
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("vcode", vcode);
        parMap.put("startTime", DateUtil.getCurDateStr() + " 00:00:00");
        parMap.put("endTime", DateUtil.getCurDateStr() + " 23:59:59");
        Integer todayTeams = forecastWarningDao.getTodayTeams(parMap);
        if(todayTeams == null){
            todayTeams = Integer.valueOf(0);
        }
        // 为了数据一致性，这里如果是云台山，就先处理特殊情况，从redis拿模拟数据
        if("d5034caae86f1081e0e6ae5337e48e9f".equals(vcode)){
            todayTeams = 0;
            String str = "";
            Object so = hash.get("scenicSpots:team:day:", DateUtil.getCurDateStr()+ DigestUtils.md5Hex(vcode));
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
                        todayTeams += Integer.valueOf(strcount);
                    }
                }
            }else{
                String provinceNames = "河南,辽宁,山东,江苏,浙江";
                String dateTime = DateUtil.getCurDateStr();
                String key = "";
                Map<Object, String> resMapRedis = new HashMap<Object, String>();
                Integer[] numbers = new Integer[]{RandomNumber(10, 6),RandomNumber(10, 6),RandomNumber(10, 6),RandomNumber(10, 6),RandomNumber(10, 6)};
                Map<Object, Object> mapres = new TreeMap<Object, Object>();
                String numberstr = numbers[0]+","+numbers[1]+","+numbers[2]+","+numbers[3]+","+numbers[4];
                mapres.put("tours",numberstr);
                mapres.put("tourFroms",provinceNames);
                mapres.put("dateTime",dateTime);
                key = dateTime + DigestUtils.md5Hex(vcode);
                resMapRedis.put(key, ConvertDataUtil.mapToJsonStr(mapres));
                String tableKey = TABLE_NAME + BIG_TEAM_NAME + "day:";
                hash.putAll(tableKey, resMapRedis);
                for(Integer num : numbers){
                    todayTeams+=num;
                }
            }
        }
        //TODO 改变模式数据模式
        Map simulateMap = new HashMap();
        simulateMap.put("vcode", vcode);
        simulateMap.put("key", "teamForecast");
        Map simulateTour = passengerService.getSimulateTour(simulateMap);
        String[] regionList = (simulateTour.get("simulateTour") + "").split(",");

        map.put("vcode", vcode);
        map.put("name", "");
        map.put("maxquantity", Integer.valueOf(getRandom(Integer.parseInt(regionList[3]), Integer.parseInt(regionList[2]))));
        map.put("today",todayTeams);
        RealPeopleRemainVo remainVo;
        SimpleDateFormat sdfMMdd = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 1; i <= 7; i++) {
            remainVo = new RealPeopleRemainVo();
            remainVo.setTime(sdfMMdd.format(getAfterDayDate(i)));
            remainVo.setNum(getRandom(Integer.parseInt(regionList[1]), Integer.parseInt(regionList[0])));
            list.add(remainVo);
        }

        map.put("dataList", list);
        return map;
    }

    @Override
    public List<TrafficWarningVo> getTrafficForecast(Map map) {
        return forecastWarningDao.getTrafficForecast(map);
    }

    @Override
    public int getTrafficCount(Map map) {
        return forecastWarningDao.getTrafficCount(map);
    }

    @Override
    public Map<String, Object> getMaps(String vcode) {
        return forecastWarningDao.getMaps(vcode);
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

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return MM-dd
     */
    public static Date getAfterDayDate(int days) {
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        return date;
    }

}
