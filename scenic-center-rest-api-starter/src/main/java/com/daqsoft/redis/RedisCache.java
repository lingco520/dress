package com.daqsoft.redis;

import com.daqsoft.utils.client.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/***
 * redis缓存工具类
 * @author zf
 * 
 * @date 2017年-7月-31日
 * @update zf 20170804
 */
public class RedisCache {

	private static final Logger logger  = LoggerFactory.getLogger(RedisCache.class);
	private static final String YEAR = "year";
	private static final String MONTH = "month";
	private static final String DAY = "day";
	private static final String QUARTER = "quarter";

	//过期时间   一小时
	private static final int LIVETIME=-1;

	//时间戳   hashvalue自定义的时间戳
	private static final String TIMEINMILLIS="timeInMillis";
	//hashvalue自定义的data,存储缓存数据
	private static final String DATA="data";

	/***
	 * 读取缓存
	 */
	public static Object getHash(RedisTemplate redisTemplate, String table, String key){
        HashOperations hash = redisTemplate.opsForHash();
        Map str = (Map) hash.get(table,key);

        if(str==null){
            return null;
        }
        String year= DateUtil.getCurYearStr();
        String month=DateUtil.getCurMonthStr();
        String quarter=year+"_"+DateUtil.getNumberCurrentQuarter();
        String[] len=key.toString().split(year);

        /**
         * 年份+区间  此处当年以外的历史数据，永不过期
         * 区间缓存时间规则 ：当前年份区间以内的，会设定过期，比如：2017-01-01到2017-12-31; 反之区间以外的则永不过期。
         */
        if((key.toString().indexOf(YEAR)!=-1 && key.toString().indexOf(year)!=-1) || (len.length-1)>1) {
            return getKey(str,table,key);
        }
        //月份   当前月，加入过期时间
        else if(key.toString().indexOf(MONTH)!=-1 && key.toString().indexOf(month)!=-1){
            return getKey(str,table,key);
        }
        //日
        else if(key.toString().indexOf(DAY)!=-1){
            boolean flag=true;
            //标记含义：当前7天内的数据，需要插入过期时间
            for(int i=0;i<7;i++){
                try {
                    String d=DateUtil.getHistDate(i);
                    if(key.toString().indexOf(d)!=-1){
                        flag=false;
                    }
                } catch (ParseException e) {
                    logger.error("读取日期"+(i+1)+"天内数据失败！");
                    e.printStackTrace();
                }
            }
            //标记含义：当前7天内的数据，需要插入过期时间
            if(flag==false){
                return getKey(str,table,key);
            }
        }
        //季度
        else if(key.toString().indexOf(QUARTER)!=-1 && key.toString().indexOf(quarter)!=-1){
            return getKey(str,table,key);
        }



		logger.debug("读取缓存H-->%s,HK-->%s",table,key);
		return str.get(DATA);
	}

    /***
     * 匹配1小时前的时间戳的运算，返回缓存数据
     * @param str
     */
	public static Object getKey(Map str,String table, String key){
        Calendar instance = Calendar.getInstance();
        //获取1小时前时间戳
        instance.add(Calendar.HOUR,LIVETIME);
        long timeInMillis = instance.getTimeInMillis();
        //如果过期；直接返回null；反之返回缓存数据。
        if((long)str.get(TIMEINMILLIS)<timeInMillis){
            return null;
        }else{
            logger.debug("读取缓存H-->%s,HK-->%s",table,key);
            return str.get(DATA);
        }
    }


	/***
	 * 写入缓存
	*/
	public static void putHash(RedisTemplate redisTemplate, String key, String hk, Object value) {
	    //给hashtable里hashvalue封装时间戳，自定义hashkey的过期处理
		Map<String,Object> map=new HashMap<>();
		map.put(DATA,value);
		map.put(TIMEINMILLIS, Calendar.getInstance().getTimeInMillis());

		//插入key
		redisTemplate.opsForHash().put(key,hk,map);

		logger.debug("写入缓存H-->%s,HK-->%s", key,hk);

      }









}
