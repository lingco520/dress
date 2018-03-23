package com.daqsoft.service.client.passengerFlowService.impl;

import com.daqsoft.constants.Constants;
import com.daqsoft.mapper.client.realpeople.RealPeopleDao;
import com.daqsoft.mapper.client.scenicspotinfo.BigRealPeopleDao;
import com.daqsoft.redis.RedisCache;
import com.daqsoft.service.client.passengerFlowService.PassengerFlowAnalysisService;
import com.daqsoft.utils.client.DateUtil;
import com.daqsoft.utils.client.HttpRequestUtil;
import com.daqsoft.utils.client.RedisKey;
import com.daqsoft.vo.client.mysqlBean.BigRealPersonTend;
import com.daqsoft.vo.client.mysqlBean.HolidayFiveYearCount;
import com.daqsoft.vo.client.mysqlBean.PassengerFlowByDay;
import com.daqsoft.vo.client.mysqlBean.RealPeople;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 客流量分析
 *
 * @author fy
 */
@Service
public class passengerFlowAnalysisServiceImpl implements PassengerFlowAnalysisService {

    // redis中的key值
    private static final String TABLE_NAME = "people:";
    private static final String BIG_TABLENAME = "big_people:";
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TIME = "time";
    private static final String QUARTER = "quarter";
    private static final String COLON = ":";
    @Autowired
    private BigRealPeopleDao bigRealPeopleDao;

    @Autowired
    private RealPeopleDao realPeopleDao;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//	/**
//	 * 客流分析 团队 季度
//	 */
//	@Override
//	public List<Citigroup_cl> find_ts_date_jidu(String date, String vcode, String type) {
//		List<Citigroup_cl> ticket_cl_typeList = new LinkedList<Citigroup_cl>();
//		Map paramMap = new HashMap<>();
//		if (type.equals("vitality")) {
//			paramMap.put("date", date + "-01");
//			paramMap.put("end", date + "-03");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.find_ts_date_jidu(paramMap);
//		} else if (type.equals("summer")) {
//			paramMap.put("date", date + "-04");
//			paramMap.put("end", date + "-06");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.find_ts_date_jidu(paramMap);
//		} else if (type.equals("autumn")) {
//			paramMap.put("date", date + "-07");
//			paramMap.put("end", date + "-09");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.find_ts_date_jidu(paramMap);
//		} else if (type.equals("wintertide")) {
//			paramMap.put("date", date + "-10");
//			paramMap.put("end", date + "-12");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.find_ts_date_jidu(paramMap);
//		}
//		return ticket_cl_typeList;
//	}
//
//	/**
//	 * 客流分析 团队 季度
//	 */
//	@Override
//	public List<Citigroup_cl> findSumTeamPeople_jidu(String date, String vcode, String type) {
//		List<Citigroup_cl> ticket_cl_typeList = new LinkedList<Citigroup_cl>();
//		Map paramMap = new HashMap<>();
//		if (type.equals("vitality")) {
//			paramMap.put("date", date + "-01");
//			paramMap.put("end", date + "-03");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.findSumTeamPeople_jidu(paramMap);
//		} else if (type.equals("summer")) {
//			paramMap.put("date", date + "-04");
//			paramMap.put("end", date + "-06");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.findSumTeamPeople_jidu(paramMap);
//		} else if (type.equals("autumn")) {
//			paramMap.put("date", date + "-07");
//			paramMap.put("end", date + "-09");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.findSumTeamPeople_jidu(paramMap);
//		} else if (type.equals("wintertide")) {
//			paramMap.put("date", date + "-10");
//			paramMap.put("end", date + "-12");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.findSumTeamPeople_jidu(paramMap);
//		}
//		return ticket_cl_typeList;
//	}
//
//	/**
//	 * 客流分析 散客 季度
//	 */
//	@Override
//	public List<Citigroup_cl> find_sk_date_jidu(String date, String vcode, String type) {
//		List<Citigroup_cl> ticket_cl_typeList = new LinkedList<Citigroup_cl>();
//		Map paramMap = new HashMap<>();
//		if (type.equals("vitality")) {
//			paramMap.put("date", date + "-01");
//			paramMap.put("end", date + "-03");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.find_sk_date_jidu(paramMap);
//		} else if (type.equals("summer")) {
//			paramMap.put("date", date + "-04");
//			paramMap.put("end", date + "-06");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.find_sk_date_jidu(paramMap);
//		} else if (type.equals("autumn")) {
//			paramMap.put("date", date + "-07");
//			paramMap.put("end", date + "-09");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.find_sk_date_jidu(paramMap);
//		} else if (type.equals("wintertide")) {
//			paramMap.put("date", date + "-10");
//			paramMap.put("end", date + "-12");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.find_sk_date_jidu(paramMap);
//		}
//		return ticket_cl_typeList;
//	}
//
//	/**
//	 * 客流分析 散客 季度
//	 */
//	@Override
//	public List<Citigroup_cl> findNotTeamPeople_jidu(String date, String vcode, String type) {
//		List<Citigroup_cl> ticket_cl_typeList = new LinkedList<Citigroup_cl>();
//		Map paramMap = new HashMap<>();
//		if (type.equals("vitality")) {
//			paramMap.put("date", date + "-01");
//			paramMap.put("end", date + "-03");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.findNotTeamPeople_jidu(paramMap);
//		} else if (type.equals("summer")) {
//			paramMap.put("date", date + "-04");
//			paramMap.put("end", date + "-06");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.findNotTeamPeople_jidu(paramMap);
//		} else if (type.equals("autumn")) {
//			paramMap.put("date", date + "-07");
//			paramMap.put("end", date + "-09");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.findNotTeamPeople_jidu(paramMap);
//		} else if (type.equals("wintertide")) {
//			paramMap.put("date", date + "-10");
//			paramMap.put("end", date + "-12");
//			paramMap.put("vcode", vcode);
//			ticket_cl_typeList = bigRealPeopleDao.findNotTeamPeople_jidu(paramMap);
//		}
//		return ticket_cl_typeList;
//	}
//
//	/**
//	 * 客流分析 团队 日
//	 */
//	@Override
//	public List<Citigroup_cl> find_ts_date(String date, String vcode, String end) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("end", end);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.find_ts_date_day(paramMap);
//	}
//
//	/**
//	 * 客流分析 团队 日
//	 */
//	@Override
//	public List<Citigroup_cl> findSumTeamPeople_day(String date, String vcode, String end) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("end", end);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.findSumTeamPeople_day(paramMap);
//	}
//
//	/**
//	 * 客流分析 团队 月
//	 */
//	@Override
//	public List<Citigroup_cl> find_ts_date_mm(String date, String vcode) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.find_ts_date_mm(paramMap);
//	}
//
//	/**
//	 * 客流分析 团队 月
//	 */
//	@Override
//	public List<Citigroup_cl> findSumTeamPeople_month(String date, String vcode) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.findSumTeamPeople_month(paramMap);
//	}
//
//	/**
//	 * 客流分析 团队 年
//	 */
//	@Override
//	public List<Citigroup_cl> find_ts_date_year(String date, String vcode, String end) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("end", end);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.find_ts_date_year(paramMap);
//	}

//	/**
//	 * 客流分析 团队 年
//	 */
//	@Override
//	public List<Citigroup_cl> findNotTeamPeople(String date, String vcode, String end) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("end", end);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.findNotTeamPeople(paramMap);
//	}
//
//	/**
//	 * 客流分析 散客 日
//	 */
//	@Override
//	public List<Citigroup_cl> find_sk_date(String date, String vcode, String end) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("end", end);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.find_sk_date_day(paramMap);
//	}
//
//	/**
//	 * 客流分析 散客 日
//	 */
//	@Override
//	public List<Citigroup_cl> findNotTeamPeople_day(String date, String vcode, String end) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("end", end);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.findNotTeamPeople_day(paramMap);
//	}
//
//	/**
//	 * 客流分析 散客 月
//	 */
//	@Override
//	public List<Citigroup_cl> find_sk_date_mm(String date, String vcode) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.find_sk_date_mm(paramMap);
//	}
//
//	/**
//	 * 客流分析 散客 月
//	 */
//	@Override
//	public List<Citigroup_cl> findNotTeamPeople_month(String date, String vcode) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.findNotTeamPeople_month(paramMap);
//	}
//
//	/**
//	 * 客流分析 散客 年
//	 */
//	@Override
//	public List<Citigroup_cl> find_sk_date_year(String date, String vcode, String end) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("end", end);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.find_sk_date_year(paramMap);
//	}
//
//	/**
//	 * 客流分析 散客 年
//	 */
//	@Override
//	public List<Citigroup_cl> findSumPeople(String date, String vcode, String end) {
//		Map paramMap = new HashMap<>();
//		paramMap.put("date", date);
//		paramMap.put("end", end);
//		paramMap.put("vcode", vcode);
//		return bigRealPeopleDao.findSumPeople(paramMap);
//	}
//
//	@Override
//	public List<Big_RealP_Tend> fundAll_date(String date, String vcode, String type) {
//		List<Big_RealP_Tend> propleVoList = new ArrayList<>();
//		if (vcode == null || date == null || "".equals(date)) {
//			return propleVoList;
//		}
//
//		// 拼接redis的hashKey
//		String key = "";
//		if (MONTH.equals(type)) {
//			key = date + "年" + DigestUtils.md5Hex(vcode);
//			type = MONTH;
//			if(EUtils.isNotEmpty(date) && date.length() > 4){
//				type = DAY;
//				key = date.replace("-", "年") + "月" + DigestUtils.md5Hex(vcode);
//			}
//
//		} else if (YEAR.equals(type)) {
//			key = DigestUtils.md5Hex(vcode);
//		}
//		// 从redis中获取数据
//		Object resultStr = getDataFromRedis(TABLE_NAME + BIG_TABLENAME + type + COLON, key);
//		if (resultStr == null) {
//			return propleVoList;
//		}
//		JSONObject resultJson = JSONObject.fromObject(resultStr);
//		if (resultJson == null) {
//			return propleVoList;
//		}
//
//		List<String> list = DateUtil.getTimeList(type);
//		propleVoList = jsonToList(resultJson, list);
//		return propleVoList;
//	}
//
//	@Override
//	public List<Big_RealP_Tend> fundAll_date_end(String date, String vcode, String type, String end) {
//		Map paramMap = new HashMap<>();
//		String date_format = "%Y年%m月%d日";
//		Calendar calendar = new GregorianCalendar();
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
//		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			if (YEAR.equals(type)) {
//				date_format = "%Y年";
//				Date endDate = sdf1.parse(end);
//				calendar.setTime(endDate);
//				calendar.add(calendar.YEAR, 1);
//				endDate = calendar.getTime();
//				end = sdf1.format(endDate);
//			} else if (MONTH.equals(type)) {
//				date_format = "%Y年%m月";
//				Date endDate = sdf2.parse(end);
//				calendar.setTime(endDate);
//				calendar.add(calendar.MONDAY, 1);
//				endDate = calendar.getTime();
//				end = sdf2.format(endDate);
//			} else {
//				Date endDate = sdf3.parse(end);
//				calendar.setTime(endDate);
//				calendar.add(calendar.DATE, 1);
//				endDate = calendar.getTime();
//				end = sdf3.format(endDate);
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		paramMap.put("date_format", date_format);
//		paramMap.put("date", date);
//		paramMap.put("end", end);
//		paramMap.put("vcode", vcode);
//		paramMap.put("type", type);
//		return bigRealPeopleDao.fundAll_date_end(paramMap);
//
//		//
//		// List<Big_RealP_Tend> propleVoList = new ArrayList<>();
//		// if(vcode == null || date == null || "".equals(date))
//		// {
//		// return propleVoList;
//		// }
//		//
//		// //拼接redis的hashKey
//		// String key = date+"年"+vcode;
//		// //从redis中获取数据
//		// Object resultStr = getDataFromRedis(TABLE_NAME + BIG_TABLENAME +
//		// type+ COLON, key);
//		// if(resultStr == null)
//		// {
//		// return propleVoList;
//		// }
//		// JSONObject resultJson = JSONObject.fromObject(resultStr);
//		// if(resultJson == null)
//		// {
//		// return propleVoList;
//		// }
//		//
//		// List<String> list = DateUtil.getTimeList(type);
//		// propleVoList = jsonToList(resultJson, list);
//		// return propleVoList;
//	}

    /**
     * 每日客流趋势
     *
     * @param date
     * @param vcode
     * @return
     */
    @Override
    public List<PassengerFlowByDay> find_oneday_date(String date, String vcode) {
        List<PassengerFlowByDay> tendVoList = new ArrayList<>();
        try {
            if (vcode == null || date == null || "".equals(date)) {
                return tendVoList;
            }
            date = new SimpleDateFormat("yyyy年MM月dd日").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));

            // 拼接redis的hashKey
            String key = date + DigestUtils.md5Hex(vcode);
            // 从redis中获取数据
            Object resultStr = getDataFromRedis(TABLE_NAME + TABLE_NAME, key);
            if (resultStr == null) {
                return tendVoList;
            }
            JSONObject resultJson = JSONObject.fromObject(resultStr);
            if (resultJson == null) {
                return tendVoList;
            }
            long gt_num = 0;
            String maxQuantity = resultJson.getString("maxquantity");
            List<String> list = DateUtil.getTimeList("time");
            List<BigRealPersonTend> realPeopleList = jsonToList(resultJson, list);
            for (BigRealPersonTend real : realPeopleList) {
                PassengerFlowByDay tend = new PassengerFlowByDay();
                tend.setTime(real.getTime());
//				String percent = (int)(Double.valueOf(real.getNum()) * 10000 / Double.valueOf(maxQuantity)  * 100)/(10000.0) + "";
                String percent = Math.round(Double.valueOf(real.getNum()) * 1000000 / Double.valueOf(maxQuantity)) / 10000.0 + "";
                tend.setNum(real.getNum());
                tend.setPercent(percent);
                gt_num = gt_num + Long.valueOf(real.getNum());
                tend.setSum(String.valueOf(gt_num));
                tendVoList.add(tend);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tendVoList;

    }

//	@Override
//	public List<Big_RealP_Tend> find_quarter_date(String date, String vcode, String type) {
//		List<Big_RealP_Tend> propleVoList = new ArrayList<>();
//		if (vcode == null || date == null || "".equals(date)) {
//			return propleVoList;
//		}
//
//		// 拼接redis的hashKey
//		String key = date + "年" + DigestUtils.md5Hex(vcode);
//		// 从redis中获取数据
//		Object resultStr = getDataFromRedis(TABLE_NAME + BIG_TABLENAME + QUARTER + COLON, key);
//		if (resultStr == null) {
//			return propleVoList;
//		}
//		JSONObject resultJson = JSONObject.fromObject(resultStr);
//		if (resultJson == null) {
//			return propleVoList;
//		}
//
//		List<String> list = DateUtil.getTimeList(type);
//		propleVoList = jsonToList(resultJson, list);
//		return propleVoList;
//	}
//
//	@Override
//	public List<Home_page> find_home_page(String vcode) {
//		List<Home_page> home_pageList = new LinkedList<Home_page>();
//		Map paramMap = new HashMap<>();
//		paramMap.put("vcode", vcode);
//		home_pageList = bigRealPeopleDao.find_home_page(paramMap);
//		return home_pageList;
//	}
//
//	@Override
//	public List<Big_RealP_Tend> find_peakV_date(Map map) {
//		return bigRealPeopleDao.find_peakV_date(map);
//	}

    /**
     * 根据表名和key值获取对应的redis数据
     *
     * @param table redis的key
     * @param key   获取redis数据的hashKey
     * @return
     */
    private Object getDataFromRedis(String table, String key) {
        HashOperations hash = stringRedisTemplate.opsForHash();
        Object str = hash.get(table, key);
        System.out.println(table);
        System.out.println(key);
        return str;
    }

    /**
     * 将json对象转存到list中（大数据）
     *
     * @param resultJson json对象
     * @return
     */
    private List<BigRealPersonTend> jsonToList(JSONObject resultJson, List<String> list) {
        List<BigRealPersonTend> Big_RealP_TendVoList = new ArrayList<>();
        // 若list不为空，则需要以list进行循环作为key返回给前端，无数据的补0
        if (list == null || list.size() == 0) {
            for (Object jsonKey : resultJson.keySet()) {
                BigRealPersonTend big_RealP_TendVo = new BigRealPersonTend();
                Object value = resultJson.get(jsonKey);
                // 若key值为空，则直接不用往前端返数据
                if (jsonKey == null) {
                    continue;
                }
                big_RealP_TendVo.setTime(jsonKey.toString());
                big_RealP_TendVo.setNum(value == null ? "0" : value.toString());
                Big_RealP_TendVoList.add(big_RealP_TendVo);
            }
        } else {
            for (String time : list) {
                BigRealPersonTend big_RealP_TendVo = new BigRealPersonTend();
                Object value = resultJson.get(time);
                big_RealP_TendVo.setTime(time);
                big_RealP_TendVo.setNum(value == null ? "0" : value.toString());
                Big_RealP_TendVoList.add(big_RealP_TendVo);
            }
        }
        return Big_RealP_TendVoList;
    }

    /**
     * 节假日游客统计
     *
     * @param date
     * @param vcode
     * @param type
     * @return
     */
    @Override
    public List<HolidayFiveYearCount> countHolidayPeople(String date, String type, String vcode) {
        String condition = "year=" + date + "&holiday=" + type;//拼上条件
        String str = HttpRequestUtil.sendGet(Constants.Bigdata + "searchHolidayByYear", condition);//url调用接口
        List list = new ArrayList();
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
                String backData = "";//节假日时间
                String holidayName = "";//节假日名称
                String key = "scenicSpots:number:day:";
                //日主KEY
                String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
                for (int i = 0; i < json.size(); i++) {//循环节假日有几天，查询统计数据
                    // 这里是计算节假日详情，不是计算总的数据和，每天的数据
                    Integer num = 0;
                    HolidayFiveYearCount bigRealPersonTend = new HolidayFiveYearCount();
                    JSONObject job = json.getJSONObject(i);
                    backData = String.valueOf(job.get("searchDate"));//循环获取节假日的时间
                    holidayName = String.valueOf(job.get("holidayName"));//循环获取节假日名称
                    String hk = backData+"|"+vcode;
                    Object obj= RedisCache.getHash(redisTemplate,datKey, hk);
                    if(obj!=null){
                        Map map = (Map)obj;
                        num = Integer.parseInt(map.get("num").toString());
                    }
                    bigRealPersonTend.setNum(String.valueOf(num));
                    bigRealPersonTend.setTime(backData);
                    bigRealPersonTend.setName(holidayName);
                    list.add(bigRealPersonTend);
                }

            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return list;
    }

    /**
     * 近五年的节假日游客统计
     *
     * @param date
     * @param vcode
     * @param type
     * @return
     */
    @Override
    public List<HolidayFiveYearCount> countFiveYearHoliday(String date, String type, String vcode, Integer numyear) {
        Integer year = Integer.valueOf(DateUtil.getCurYearStr());//获取当前年份
        List list = new ArrayList();
        for (int a = numyear; a >= 0; a--) {//循环需要查询的近几年的数字，去查询历史节假日
            Integer choiceyear = year - a;
            String condition = "year=" + choiceyear + "&holiday=" + type;//拼上条件
            String str = HttpRequestUtil.sendGet(Constants.Bigdata + "searchHolidayByYear", condition);//url调用接口
            //转换hash为json
            JSONObject jasonObject = JSONObject.fromObject(str);
            //遍历封装
            JSONArray value = null;
            //创建对象与封装
            try {
                value = jasonObject.getJSONArray("datas");
                if (value.size() < 1) {//节假日接口无数据判断
                    continue;
                } else {
                    JSONArray json = JSONArray.fromObject(value);
                    String backData = "";//节假日时间
                    String holidayName = "";
                    Integer sumPeople = 0;//统计节假日总人数
                    //日主KEY
                    String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
                    for (int i = 0; i < json.size(); i++) {//循环节假日有几天，查询统计数据
                        JSONObject job = json.getJSONObject(i);
                        backData = String.valueOf(job.get("searchDate"));//循环获取节假日的时间
                        holidayName = String.valueOf(job.get("holidayName"));
                        String hk = backData+"|"+vcode;
                        Object obj= RedisCache.getHash(redisTemplate,datKey, hk);
                        if(obj!=null){
                            Map map = (Map)obj;
                            sumPeople += Integer.parseInt(map.get("num").toString());
                        }else{
                            sumPeople += 0;
                        }
                    }
                    HolidayFiveYearCount holidayFiveYearCount = new HolidayFiveYearCount();
                    holidayFiveYearCount.setNum(String.valueOf(sumPeople));//假日总人数
                    holidayFiveYearCount.setName(holidayName);//假日名称
                    // 这里日期存年份，不是具体的某一天
                    holidayFiveYearCount.setTime(String.valueOf(choiceyear));//假日名称
                    list.add(holidayFiveYearCount);
                }
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return list;
    }

    //今日数据
    @Override
    public List<RealPeople> getScenicTimeOnTime(Map map) {
        List<RealPeople> list = realPeopleDao.getNowLoad(map);
        return list;
    }

    //昨日数据
    @Override
    public List<RealPeople> getScenicTimeOnTimeYesterday(Map map) {
        List<RealPeople> list = realPeopleDao.getYesterdayLoad(map);
        return list;
    }

    @Override
    public Map getAverageAndMaxThrity(String vcode, String start, String end) {
        String date_format = "%Y年%m月%d日";
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");

        Map map = new HashMap<>();
        int max = 0;
        int array[] = new int[31];
        int sum = 0;

        List<String> dayList = DateUtil.getSubsectionDateList(start,end);
        Map param = new HashMap<>();
        param.put("start",start);
        param.put("end",end);
        param.put("vcode",vcode);

        map = realPeopleDao.getMaxThrity(param);
        int sums = realPeopleDao.getSumThrity(param);
        int averages = 0;
        try {
            averages = (int) Math.floor(sums / dayList.size());
        }catch (Exception e) {
            e.getStackTrace();
        }
        map.put("average",averages);


        //走缓存
        //日统计的主KEY
//        String datKey = RedisKey.MOUDEL_PASSENGERFLOW+"day:"+"getPassengerFlowByDay:";
//        for (int i = 0; i <dayList.size() ; i++) {
//            Big_RealP_Tend tend2 = new Big_RealP_Tend();
//            String dayHk = dayList.get(i)+"|"+vcode;
//            Object dayObj= RedisCache.getHash(redisTemplate,datKey, dayHk);
//            if(dayObj!=null){
//                Map dayMap = (Map)dayObj;
//                int num = Integer.parseInt(dayMap.get("num")+"");
//                //判断人数大小，记录最大的人数和日期
//                if(num > max) {
//                    max = num;
//                    map.put("maxTime",dayList.get(i));
//                }
//                //将人数放入数组，用于求平均值
//                array[i] = num;
//            }else{
//                array[i] = 0;
//            }
//        }
//        //求人数平均值
//        for (int j = 0; j < array.length; j++) {
//            sum = sum + array[j];
//        }
//        int average = (int)Math.floor(sum / dayList.size());
//        map.put("average",average);
//        map.put("max",max);
        return map;
    }

    //获取redis查询数量数量 查询实时人数
    public String getRedis(String key, String date, String vcode) {
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
