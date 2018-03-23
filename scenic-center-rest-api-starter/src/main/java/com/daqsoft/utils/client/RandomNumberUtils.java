package com.daqsoft.utils.client;

import java.util.Random;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-28 18:22
 * @Version:
 * @Describe:
 */
public class RandomNumberUtils {

    public static Integer RandomNumber(Integer max, Integer min) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
}
