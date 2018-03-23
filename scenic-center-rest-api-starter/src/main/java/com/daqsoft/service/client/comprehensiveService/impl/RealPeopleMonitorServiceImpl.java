/**
 * @Copyright: <a htef="http://www.daqsoft.com">成都中科大旗软件有限公司Copyright © 2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.client.comprehensiveService.impl;

import com.daqsoft.commons.core.StringUtil;
import com.daqsoft.mapper.client.realpeople.RealPeopleMonitorDao;
import com.daqsoft.service.client.comprehensiveService.RealPeopleMonitorService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.PerCentUtil;
import com.daqsoft.vo.client.madeVoBean.JqScenic_ScenicLoadPeople_Tend;
import com.daqsoft.vo.client.madeVoBean.RealPeopleChoiceTimeVo;
import com.daqsoft.vo.client.madeVoBean.SpotsWifiBeeComfortVo;
import com.daqsoft.vo.client.mysqlBean.JqScenicTimelyPopulation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title: 实时客流监测
 * @Author: yangmei
 * @Date: 2017/08/02 11:56
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class RealPeopleMonitorServiceImpl implements RealPeopleMonitorService {

    @Autowired
    private RealPeopleMonitorDao realPeopleMonitorDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 景区景点实时客流监测，通过小蜜蜂wifi数据分析
     * @param vcode
     * @return
     */
    @Override
    public List<SpotsWifiBeeComfortVo> realpeopleMonitorBee(String vcode) {
        List<SpotsWifiBeeComfortVo> dataList = new ArrayList<>();

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("vcode", vcode);
        paramMap.put("startTime", DateUtil.getCurHourStr()+":00:00");
        paramMap.put("endTime", DateUtil.getCurHourStr()+":59:59");
        List<SpotsWifiBeeComfortVo> peoList = realPeopleMonitorDao.getRealpeopleMonitorBee(paramMap);
        DecimalFormat df = new DecimalFormat("######0.00");
        //进行舒适度计算
        for (int i = 0; i < peoList.size(); i++) {
            SpotsWifiBeeComfortVo sc = new SpotsWifiBeeComfortVo();
            sc.setBdcode(peoList.get(i).getBdcode());
            sc.setCrtime(peoList.get(i).getCrtime());
            sc.setCrtime(peoList.get(i).getCrtime());
            //保留两位小数

            Double degree = null;
            if (StringUtil.isEmpty(peoList.get(i).getTotalnum()) || StringUtil.isEmpty(peoList.get(i).getMaxCount())) {
                degree = 0.0;
            } else {
                degree = Double.parseDouble(peoList.get(i).getTotalnum()) / Double.parseDouble(peoList.get(i).getMaxCount()) * 100;
            }
            sc.setDegree(degree.toString());
            dataList.add(sc);
        }
        return dataList;
    }


    /**
     * 景区景点实时客流监测，这方法用于华蓥山
     *
     * @param vcode
     * @return
     * @Comment: 每个景点的实时人数都是通过jq_wifi_part这张表来查询的
     */
    @Override
    public List<JqScenic_ScenicLoadPeople_Tend> getRealpeopleMonitorHyj(String vcode) {

        List<JqScenic_ScenicLoadPeople_Tend> loadVo = new LinkedList<JqScenic_ScenicLoadPeople_Tend>();
        List<JqScenicTimelyPopulation> loadList = realPeopleMonitorDao.getRealpeopleMonitorHyj(vcode);
        String peopleLoad = "";
        String pLoad = "0";
        String sumPeople = "0";
        String scName = "";
        for (JqScenicTimelyPopulation loadPeoplelist : loadList) {
            JqScenic_ScenicLoadPeople_Tend jqLoadPeople = new JqScenic_ScenicLoadPeople_Tend();
            if (!StringUtil.isEmpty(loadPeoplelist.getTEMPLYQUANTITY())) {
                peopleLoad = Double.valueOf(loadPeoplelist.getTEMPLYQUANTITY()) * 100.0D + "";
                if ("".equals(peopleLoad) || peopleLoad.equals("0.0")) {
                    pLoad = "0";
                } else {
                    pLoad = PerCentUtil.percent(peopleLoad);
                }
            }
            if (loadPeoplelist.getTEMPLYQUANTITY() == null) {
                pLoad = "0";
            }
            if (!StringUtil.isEmpty(loadPeoplelist.getSumPeople())) {
                sumPeople = loadPeoplelist.getSumPeople();
            }
            if (!StringUtil.isEmpty(loadPeoplelist.getSumPeople())) {
                sumPeople = "0";
            }
            if (!StringUtil.isEmpty(loadPeoplelist.getNAME())) {
                scName = loadPeoplelist.getNAME();
            }
            jqLoadPeople.setLoadPeople(pLoad);
            jqLoadPeople.setPeople(sumPeople);
            jqLoadPeople.setScenicName(scName);
            loadVo.add(jqLoadPeople);
        }
        return loadVo;
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
            e.printStackTrace();
        }
        dateTime = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        str = (String) hash.get("people:people:", dateTime + vcode);
        //转换hash为json
        JSONObject jasonObject = JSONObject.fromObject(str);
//        ydata =  new String[jasonObject.size()];
//        xdata =  new String[jasonObject.size()];
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


}
