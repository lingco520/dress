package com.daqsoft.service.client.comprehensive.impl;

import com.daqsoft.commons.core.StringUtil;
import com.daqsoft.mapper.client.realpeople.RealPeopleDao;
import com.daqsoft.service.client.comprehensive.RealPeopleService;
import com.daqsoft.utils.client.ConvertDataUtil;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.JqScenicTimelyPopulation;
import com.daqsoft.vo.client.mysqlBean.RealPeople;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:实时人数逻辑实现
 */


@Service
public class RealPeopleServiceImpl implements RealPeopleService {

    @Autowired
    private RealPeopleDao realPeopleDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    private static String collectionName = "big_realpeople";
    private static String collectionNameTime = "big_realpeopleNow";
    private static String collectionNameHour = "realpeopleNow";
    private static final String SYNC_DATA = "people:syncdata:";
    private static final String REALPEOPLE = "people:people:";

    //今日总人数
    @Override
    public List<RealPeopleIndexSumVo> getCountPeopleSum(String vcode) {

        return realPeopleDao.getCountPeopleSum(vcode);
    }

    /**
     * 获取今日实时人数
     * <p>
     * 注：此处是通过redis获取，getCountPeopleSum是通过写的mysql获取
     * redis中获取的是每天7:00——18.59:59的数据，而mysql中获取的是全天的
     */
    @Override
    public List<RealPeople> getSyncPeopleSum(String vcode) {
        if (vcode == null) {
            System.out.println("vcode is null!");
            return null;
        }
        HashOperations hash = stringRedisTemplate.opsForHash();
        Object str = hash.get(SYNC_DATA, DigestUtils.md5Hex(vcode));
        if (str == null) {
            return null;
        }
        Map<Object, Object> map = ConvertDataUtil.jsonstrToMap(str.toString());
        RealPeople realPeople = new RealPeople();
        realPeople.setSumPeople(map.get("realtimeData").toString());
        List<RealPeople> list = new ArrayList<>();
        list.add(realPeople);
        return list;
    }

    @Override
    public Map<Object, Object> getMaxQuantity(String vcode) {
        if (vcode == null) {
            System.out.println("vcode is null!");
            return null;
        }
        HashOperations hash = stringRedisTemplate.opsForHash();


        String dateTime = DateUtil.getCurDateStr();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateTime);
        } catch (ParseException e) {

        }
        dateTime = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        Object o = hash.get(REALPEOPLE, dateTime+DigestUtils.md5Hex(vcode));
        if (o == null) {
            return null;
        }
        Map<Object, Object> map = ConvertDataUtil.jsonstrToMap(o.toString());
        return map;
    }

    //昨日总人数
    @Override
    public List<RealPeople> getCountPeopleSumYesterday(String vcode) {

        return realPeopleDao.getCountPeopleSumYesterday(vcode);
    }

    //今日数据
    @Override
    public List<RealPeople> getScenicTimeOnTime(Map map) {
        return realPeopleDao.getScenicTimeOnTime(map);
    }

    //昨日数据
    @Override
    public List<RealPeople> getScenicTimeOnTimeYesterday(Map map) {
        return realPeopleDao.getScenicTimeOnTimeYesterday(map);
    }

    //30日平均和峰值
    @Override
    public List<RealPeople> getPeopleAvgByMonth(String vcode) {
        return realPeopleDao.getPeopleAvgByMonth(vcode);
    }

    //客流分析按日期分析
    @Override
    public List<RealPeopleChoiceTimeVo> getAllDateEnd(String date, String vcode, String type, String end) {
        return null;
    }

    @Override
    public List<RealPeopleChoiceTimeVo> getAllDate(String date, String vcode, String type) {
        return null;
    }

    //客流分析按季度查询
    @Override
    public List<RealPeopleChoiceTimeVo> getQuarterDate(String date, String vcode, String type) {

        return null;
    }


    @Override
    public List<JqScenicCarAndPeopleRelationVo> findScenicTimePeople(String time, String type, String vcode) {
        List<JqScenicCarAndPeopleRelationVo> carFlow_tendList = new LinkedList<JqScenicCarAndPeopleRelationVo>();

        Map map = new HashMap<>();
        map.put("vcode", vcode);
        if (type.equals("day")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date s_date = null;
            try {
                s_date = sdf.parse(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
            DateFormat df = null;
            df = new SimpleDateFormat("yyyy-MM-dd");
            map.put("time", df.format(s_date));
            List<JqScenicTimelyPopulation> jqScenicList = realPeopleDao.getScenicTimePeople(map);
            for (JqScenicTimelyPopulation jqScenic : jqScenicList) {
                JqScenicCarAndPeopleRelationVo JqScenicTend = new JqScenicCarAndPeopleRelationVo();
                JqScenicTend.setNum(jqScenic.getSumPeople());
                JqScenicTend.setTime(jqScenic.getPopTime());
                JqScenicTend.setType(jqScenic.getSOURCE());
                carFlow_tendList.add(JqScenicTend);
            }
        } else if (type.equals("month")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date s_date = null;
            try {
                s_date = sdf.parse(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
            DateFormat df = null;
            df = new SimpleDateFormat("yyyy-MM");
            map.put("time", df.format(s_date));
            List<JqScenicTimelyPopulation> jqScenicListMonth = realPeopleDao.getScenicTimePeopleByMonth(map);
            for (JqScenicTimelyPopulation jqScenicMonth : jqScenicListMonth) {
                JqScenicCarAndPeopleRelationVo JqScenicTend = new JqScenicCarAndPeopleRelationVo();
                JqScenicTend.setNum(jqScenicMonth.getSumPeople());
                JqScenicTend.setTime(jqScenicMonth.getPopTime());
                JqScenicTend.setType(jqScenicMonth.getSOURCE());
                carFlow_tendList.add(JqScenicTend);
            }
        } else if (type.equals("year")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date s_date = null;
            try {
                s_date = sdf.parse(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
            DateFormat df = null;
            df = new SimpleDateFormat("yyyy");
            map.put("time", df.format(s_date));
            List<JqScenicTimelyPopulation> jqScenicList = realPeopleDao.getScenicTimePeopleByYear(map);
            for (JqScenicTimelyPopulation jqScenic : jqScenicList) {
                JqScenicCarAndPeopleRelationVo JqScenicTend = new JqScenicCarAndPeopleRelationVo();
                JqScenicTend.setNum(jqScenic.getSumPeople());
                JqScenicTend.setTime(jqScenic.getPopTime());
                JqScenicTend.setType(jqScenic.getSOURCE());
                carFlow_tendList.add(JqScenicTend);
            }
        }
        return carFlow_tendList;
    }

    @Override
    public List<RealPeopleIndexTodayVo> getPeopleAtToday(Map map) {
        return realPeopleDao.getPeopleAtToday(map);
    }

    @Override
    public List<RealPeopleIndexYesterdayVo> getPeopleAtYesterday(Map map) {
        return realPeopleDao.getPeopleAtYesterday(map);
    }

    @Override
    public List<RealPeopleChoiceTimeVo> getPeopleFlowMonth(String date, String vcode) {
        Map map = new HashMap<>();
        map.put("date", date);
        map.put("vcode", vcode);
        return realPeopleDao.getPeopleFlowMonth(map);
    }

    @Override
    public List<RealPeopleIndexSumVo> getPeopleRealTime(Map map) {
        return realPeopleDao.getPeopleRealTime(map);
    }

    @Override
    public List<RealPeopleIndexTodayVo> getScenicPeopleToday(Map map) {
        return realPeopleDao.getScenicPeopleToday(map);
    }

    @Override
    public List<RealPeopleIndexSumVo> getPeopleSumBylastyear(Map map) {
        return realPeopleDao.getPeopleSumBylastyear(map);
    }

    @Override
    public List<RealPeopleIndexSumVo> getPeopleSumBeforelast(Map map) {
        return realPeopleDao.getPeopleSumBeforelast(map);
    }

    @Override
    public List<RealPeopleIndexYesterdayVo> getMaxCountPeople(Map map) {
        return realPeopleDao.getMaxCountPeople(map);
    }

    @Override
    public List<RealPeopleChoiceTimeVo> getRealTimeMethYear(String vcode) {
        //创建list返回集合
        List<RealPeopleChoiceTimeVo> listData = new LinkedList<>();
        //创建has接受字符串
        String str = "";
        //创建哈希对象
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        str = (String) hash.get("people:big_people:year:", vcode);
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //捕获异常
        Iterator<String> sIterator = null;
        try {
            sIterator = jasonObject.keys();
        } catch (Exception e) {
            return listData;
        }
        int j = 0;
        //遍历封装
        while (sIterator.hasNext()) {
            //创建对象与封装
            RealPeopleChoiceTimeVo realPeopleChoiceTimeVo = new RealPeopleChoiceTimeVo();
            String key = sIterator.next();
            if(key.equals("maxquantity"))
            {
                continue;
            }
            String value = jasonObject.getString(key);
            realPeopleChoiceTimeVo.setTime(key);
            realPeopleChoiceTimeVo.setNum(value);
            listData.add(realPeopleChoiceTimeVo);
            j++;
        }
        return listData;
    }

    @Override
    public List<RealPeopleChoiceTimeVo> getRealTimeMethMonth(String vcode, String dateTime) {
        //创建list返回集合
        List<RealPeopleChoiceTimeVo> listData = new LinkedList<>();
        //创建has接受字符串
        String str = "";
        //创建哈希对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = null;
        try {
            date = sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateTime = new SimpleDateFormat("yyyy年").format(date);
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        str = (String) hash.get("people:big_people:month:", dateTime + vcode);
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //捕获异常
        Iterator<String> sIterator = null;
        try {
            sIterator = jasonObject.keys();
        } catch (Exception e) {
            return listData;
        }
        int j = 0;
        //遍历封装
        while (sIterator.hasNext()) {
            RealPeopleChoiceTimeVo realPeopleChoiceTimeVo = new RealPeopleChoiceTimeVo();
            String key = sIterator.next();
            if(key.equals("maxquantity"))
            {
                continue;
            }
            String value = jasonObject.getString(key);
            realPeopleChoiceTimeVo.setTime(key);
            realPeopleChoiceTimeVo.setNum(value);
            listData.add(realPeopleChoiceTimeVo);
            j++;
        }
        return listData;
    }

    @Override
    public List<RealPeopleChoiceTimeVo> getRealTimeMethDay(String vcode, String dateTime) {
        //创建list返回集合
        List<RealPeopleChoiceTimeVo> listData = new LinkedList<>();
        //创建has接受字符串
        String str = "";
        //创建哈希对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateTime = new SimpleDateFormat("yyyy年MM月").format(date);
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        str = (String) hash.get("people:big_people:day:", dateTime + vcode);
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        //捕获异常
        Iterator<String> sIterator = null;
        try {
            sIterator = jasonObject.keys();
        } catch (Exception e) {
            return listData;
        }
        int j = 0;
        //遍历封装
        while (sIterator.hasNext()) {
            //创建对象与封装
            RealPeopleChoiceTimeVo realPeopleChoiceTimeVo = new RealPeopleChoiceTimeVo();
            String key = sIterator.next();
            if(key.equals("maxquantity"))
            {
                continue;
            }
            String value = jasonObject.getString(key);
            realPeopleChoiceTimeVo.setTime(key);
            realPeopleChoiceTimeVo.setNum(value);
            listData.add(realPeopleChoiceTimeVo);
            j++;
        }
        return listData;
    }

    @Override
    public List<RealPeopleChoiceTimeVo> getRealTimeMethTime(String vcode, String dateTime) {
        Map<String,Object> dataMap = new HashMap<>();
        //创建list返回集合
        List<RealPeopleChoiceTimeVo> listData = new LinkedList<>();
        //创建has接受字符串
        String str = "";
        //创建哈希对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateTime);
        } catch (ParseException e) {
            return listData;
        }
        dateTime = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        str = (String) hash.get("people:people:", dateTime + vcode);


        if (StringUtil.isEmpty(str)){
            return listData;
        }
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
//        ydata =  new String[jasonObject.size()];
//        xdata =  new String[jasonObject.size()];
        Iterator<String> sIterator = null;
        try {
            sIterator = jasonObject.keys();
        } catch (Exception e) {

        }
        int j = 0;
        String maxquantity = "";
        Integer sumPeople = 0;


        //遍历封装
        while (sIterator.hasNext()) {
            //创建对象与封装
            RealPeopleChoiceTimeVo realPeopleChoiceTimeVo = new RealPeopleChoiceTimeVo();
            String key = sIterator.next();
            if(key.equals("maxquantity"))
            {
                maxquantity = maxquantity;
                continue;
            }
            String value = jasonObject.getString(key);
            realPeopleChoiceTimeVo.setTime(key);
            realPeopleChoiceTimeVo.setNum(value);
            sumPeople = sumPeople + Integer.parseInt(value);
            listData.add(realPeopleChoiceTimeVo);
            j++;
        }
        return listData;
    }

    @Override
    public List<RealPeopleChoiceTimeVo> getRealTimeSlotMeth(Map map) {

        return realPeopleDao.getRealTimeSlotMeth(map);
    }

    @Override
    public List<RealPeopleChoiceTimeVo> getRealPeakValleyValueMeth(Map map) {
        return realPeopleDao.getRealPeakValleyValueMeth(map);
    }

    @Override
    public List<RealPeopleChoiceTimeVo> getMoneyPeakValleyValueMeth(Map map) {
        return realPeopleDao.getMoneyPeakValleyValueMeth(map);
    }

    @Override
    public List<RealPeopleChoiceTimeVo> getRealHistoryMeth(Map map) {
        return realPeopleDao.getRealHistoryMeth(map);
    }

    @Override
    public String getTeamFitMeth(Map map) {
        return realPeopleDao.getTeamFitMeth(map);
    }

    @Override
    public String getMapStartTimeFitMeth(Map map) {
        return realPeopleDao.getMapStartTimeFitMeth(map);
    }

    @Override
    public List<RealPeopleChoiceTimeVo> getComfortDegreeMeth(Map map) {
        return realPeopleDao.getComfortDegreeMeth(map);
    }

    @Override
    public List<ScenicSpotComfortDegreeVo> getScenicSpotComfortDegreeMeth(Map map) {
        return realPeopleDao.getScenicSpotComfortDegreeMeth(map);
    }

    @Override
    public List<jQScenicSpotZJ> getScenicSpotZJMeth(Map map) {
        return realPeopleDao.getScenicSpotZJMeth(map);
    }

    @Override
    public List<jQScenicSpotZJList> getScenicSpotZJCountMeth(Map map) {
        return realPeopleDao.getScenicSpotZJCountMeth(map);
    }

    @Override
    public Long getPeopleRealByDate(Map map) {
        return realPeopleDao.getPeopleRealByDate(map);
    }

    @Override
    public Long getToursByDate(String year, String vcode) {
        String str = "";
        Long total = Long.valueOf(0);
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        str = (String) hash.get("scenicSpots:team:year:", year + vcode);
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        String numStr = jasonObject.getString("tours");
        if(!"".equals(numStr)){
            String[] nums = numStr.split(",");
            for (String num : nums){
                total += Long.valueOf(num);
            }
        }
        return total;
    }

    @Override
    public BigDecimal getTicketMoneyByDate(Map map) {
        return realPeopleDao.getTicketMoneyByDate(map);
    }

    @Override
    public Long getYearTotalPeople(String year, String vcode) {
        String str = "";
        Long total = Long.valueOf(0);
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        str = (String) hash.get("scenicSpots:number:year:", year + vcode);
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
        String numStr = jasonObject.getString("nums");
        if(!"".equals(numStr)){
            total = Long.valueOf(numStr);
        }
        return total;
    }

    @Override
    public BigDecimal getYearTicketMoney(Map map) {
        return realPeopleDao.getYearTicketMoney(map);
    }

    @Override
    public List<ScenicPassengerVo> getPassengerList(Map map) {
        return realPeopleDao.getPassengerList(map);
    }

    @Override
    public List<TourCountVo> getTourCountDay(Map map) {
        return realPeopleDao.getTourCountDay(map);
    }

}