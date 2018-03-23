package com.daqsoft.utils.client;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: 通用假日
 * @Author: Administrator
 * @Date: 2017/12/23 14:39
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
public class CommonHolidaysStr {

    public static List<String> getCommonHolidaysStr(){
        List list = new ArrayList();
        String array [] = {"01-01","05-01","10-01","10-02","10-03","10-04","10-05","10-06","10-07"};
        for(int i =0; i<array.length; i ++){
            list.add(array[i]);
        }
        return list;
    }
}