package com.daqsoft.service.client.trafficAnalysisService.impl;

import com.daqsoft.mapper.client.carflow.CarProvinceDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.trafficAnalysisService.CarProvinceService;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.madeVoBean.CarComeFromWidgetVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 省份车辆来源实现类
 * Created by lianch on 2017/6/7.
 */
@Service
public class CarProvinceServiceImpl implements CarProvinceService {
    //省份简称数组
    private static final String[] proArr = new String[]{"冀", "辽", "皖", "苏", "鄂", "晋", "吉", "粤", "宁", "京", "川",
            "豫", "黑", "鲁", "浙", "桂", "蒙", "闽", "渝", "津", "云", "湘", "新", "赣", "甘", "陕", "贵", "青", "藏", "琼", "沪", "澳", "港", "台"};

    private static final String[] proArr2 = new String[]{"河北省", "辽宁省", "安徽省", "江苏省", "湖北省", "山西省", "吉林省", "广东省", "宁夏回族自治区", "北京市", "四川省",
            "河南省", "黑龙江省", "山东省", "浙江省", "广西壮族自治区", "内蒙古自治区", "福建省", "重庆市", "天津市", "云南省", "湖南省", "新疆维吾尔自治区", "江西省", "甘肃省", "陕西省", "贵州省", "青海省", "西藏自治区", "海南省", "上海市", "澳门", "香港", "台湾"};
    @Autowired
    private CarProvinceDao carProvinceDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 当日车辆来源排行
     *
     * @param vcode 景区编码
     * @param day   时间
     * @return
     */
    @Override
    public List<CarComeFromWidgetVo> getCarComeFromDay(String vcode, String day) {
        //新的返回list集合
        List<CarComeFromWidgetVo> carComeFromVosNew = new ArrayList<>();
        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("day", day);
        List<CarComeFromWidgetVo> carComeFromVos = carProvinceDao.getCarComeFromDay(map);
        //没有的省份的数量封装0
        getProNum(carComeFromVosNew, carComeFromVos);
        return carComeFromVosNew;
    }

    /**
     * 选择日期车辆来源排行
     *
     * @param vcode    景区编码
     * @param beginDay 开始日期
     * @param endDay   结束日期
     * @return
     * @update zf 20170822
     */
    @Override
    public Object getCarComeFromDay2Day(String vcode, String beginDay, String endDay) {
        //新的返回list集合
        List<CarComeFromWidgetVo> carComeFromVosNew = new ArrayList<>();
        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("beginDay", beginDay + " 00:00:00");
        map.put("endDay", endDay + " 23:59:59");
//        List<CarComeFromWidgetVo> carComeFromVos = carProvinceDao.getCarComeFromDay2Day(map);
//        //没有的省份的数量封装0
//        getProNum(carComeFromVosNew, carComeFromVos);

        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARPROVINCE + "dayToday:getCarComeFromDay2Day:";
        //hashkey
        String hk = "day" + beginDay + endDay + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarComeFromWidgetVo> carComeFromVos = carProvinceDao.getCarComeFromDay2Day(map);
            //没有的省份的数量封装0 作废
            getProNum(carComeFromVosNew, carComeFromVos);
            RedisCache.putHash(redisTemplate, k, hk, carComeFromVosNew);
            obj = carComeFromVosNew;
        }
        return obj;
    }

    /**
     * 季度城市车辆来源
     *
     * @param vcode   景区编码
     * @param quarter 年份季度
     * @return
     * @update zf 20170822
     */
    @Override
    public Object getCarComeFromQuarter(String vcode, String quarter) {
        //新的返回list集合
        List<CarComeFromWidgetVo> carComeFromVosNew = new ArrayList<>();
        Map<String, String> map = new HashMap();
        String[] concat = quarter.split("_");
        String year = concat[0];//获取第几年
        String quarterNum = concat[1];//判断是第几季度
        String beginTime = "";
        String endTime = "";
        if (quarterNum.equals("1")) {//拼装季度，不在数据库中拼字符串判断季度
            beginTime = year + "-01-01 00:00:00";//第一季度
            endTime = year + "-03-31 23:59:59";
        } else if (quarterNum.equals("2")) {
            beginTime = year + "-04-01 00:00:00";//第二季度
            endTime = year + "-06-30 23:59:59";
        } else if (quarterNum.equals("3")) {
            beginTime = year + "-07-01 00:00:00";//第三季度
            endTime = year + "-09-30 23:59:59";
        } else if (quarterNum.equals("4")) {
            beginTime = year + "-10-01 00:00:00";//第四季度
            endTime = year + "-12-31 23:59:59";
        }
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
//        List<CarComeFromWidgetVo> carComeFromVos = carProvinceDao.getCarComeFromQuarter(map);
//        //没有的省份的数量封装0
//        getProNum(carComeFromVosNew, carComeFromVos);

        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARPROVINCE + "quarter:getCarComeFromQuarter:";
        //hashkey
        String hk = "quarter" + quarter + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarComeFromWidgetVo> carComeFromVos = carProvinceDao.getCarComeFromQuarter(map);
            //没有的省份的数量封装0
            getProNum(carComeFromVosNew, carComeFromVos);
            RedisCache.putHash(redisTemplate, k, hk, carComeFromVosNew);
            obj = carComeFromVosNew;
        }
        return obj;
    }

    /**
     * 当年城市车辆来源
     *
     * @param vcode 景区编码
     * @param year  年份
     * @return
     */
    @Override
    public List<CarComeFromWidgetVo> getCarComeFromYear(String vcode, String year) {
        //新的返回list集合
        List<CarComeFromWidgetVo> carComeFromVosNew = new ArrayList<>();
        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("year", year);
        List<CarComeFromWidgetVo> carComeFromVos = carProvinceDao.getCarComeFromYear(map);
        //没有的省份的数量封装0
        getProNum(carComeFromVosNew, carComeFromVos);
        return carComeFromVosNew;
    }

    /**
     * 选择年城市车辆来源
     *
     * @param vcode     景区编码
     * @param beginYear 开始年份
     * @param endYear   结束年份
     * @return
     * @update zf 20170802
     */
    @Override
    public Object getCarComeFromYear2Year(String vcode, String beginYear, String endYear) {
        //新的返回list集合
        List<CarComeFromWidgetVo> carComeFromVosNew = new ArrayList<>();
        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("beginYear", beginYear + "-01-01 00:00:00");
        map.put("endYear", endYear + "-12-31 23:59:59");
//        List<CarComeFromWidgetVo> carComeFromVos = carProvinceDao.getCarComeFromYear2Year(map);
//        //没有的省份的数量封装0
//        getProNum(carComeFromVosNew, carComeFromVos);

        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARPROVINCE + "yearToyear:getCarComeFromYear2Year:";
        //hashkey
        String hk = "year" + beginYear + endYear + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarComeFromWidgetVo> carComeFromVos = carProvinceDao.getCarComeFromYear2Year(map);
            //没有的省份的数量封装0
            getProNum(carComeFromVosNew, carComeFromVos);
            RedisCache.putHash(redisTemplate, k, hk, carComeFromVosNew);
            obj = carComeFromVosNew;
        }
        return obj;
    }

    /**
     * 当月城市车辆来源
     *
     * @param vcode 景区编码
     * @param month 月份
     * @return
     * @update zf 20170822
     */
    @Override
    public Object getCarComeFromMonth(String vcode, String month) {
        //新的返回list集合
        List<CarComeFromWidgetVo> carComeFromVosNew = new ArrayList<>();
        Map<String, String> map = new HashMap();
        //sql查询where条件
        map.put("vcode", vcode);
        map.put("month", month);
        map.put("startTime", month + "-01 00:00:00");
        map.put("endTime", month + "-31 23:59:59");
        /*List<CarComeFromWidgetVo> carComeFromVos = carProvinceDao.getCarComeFromMonth(map);
        //没有的省份的数量封装0
        getProNum(carComeFromVosNew, carComeFromVos);*/

        //大key
        String k = RedisKey.MOUDEL_CARFLOW + RedisKey.CLASS_CARPROVINCE + "month:getCarComeFromMonth:";
        //hashkey
        String hk = "month" + month + DigestUtils.md5Hex(vcode);
        Object obj = RedisCache.getHash(redisTemplate, k, hk);
        if (null == obj) {
            List<CarComeFromWidgetVo> carComeFromVos = carProvinceDao.getCarComeFromMonth(map);
            //没有的省份的数量封装0 作废
            getProNum(carComeFromVosNew, carComeFromVos);
            RedisCache.putHash(redisTemplate, k, hk, carComeFromVosNew);
            obj = carComeFromVosNew;
        }
        return obj;
    }

    /**
     * 封装返回数据
     *
     * @param carComeFromVosNew 返回的数据
     * @param carComeFromVos    查出来的数据
     */
    private void getProNum(List<CarComeFromWidgetVo> carComeFromVosNew, List<CarComeFromWidgetVo> carComeFromVos) {
        for (String str : proArr2) {
            CarComeFromWidgetVo carComeFromVo = new CarComeFromWidgetVo();
            boolean flag = true;
            for (CarComeFromWidgetVo obj : carComeFromVos) {
                if (str.equals(obj.getCarNum())) {
                    carComeFromVo.setProvince(str);
                    carComeFromVo.setNum(obj.getNum());
                    carComeFromVosNew.add(carComeFromVo);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                carComeFromVo.setProvince(str);
                carComeFromVo.setNum(0);
                carComeFromVosNew.add(carComeFromVo);
            }
        }
        sort(carComeFromVosNew);
    }

    //排序重写
    public void sort(List carComeFromVosNew) {
        Collections.sort(carComeFromVosNew, (Comparator<CarComeFromWidgetVo>) (o1, o2) -> {
            if (o1.getNum() > o2.getNum()) {
                return -1;
            } else if (o1.getNum() < o2.getNum()) {
                return 1;
            } else {
                return 0;
            }
        });
    }
}
