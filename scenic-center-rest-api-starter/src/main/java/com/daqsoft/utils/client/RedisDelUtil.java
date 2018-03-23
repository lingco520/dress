package com.daqsoft.utils.client;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Title: redis删除缓存工具
 * @Author: lyl
 * @Date: 2017/12/27 14:30
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
public class RedisDelUtil {


    public static void delRedis(RedisTemplate redisTemplate, String rawKey, String rawHashKeys) {
        //插入删除key
        redisTemplate.opsForHash().delete(rawKey,rawHashKeys);
        System.out.println("执行删除！");

    }


}