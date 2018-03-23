package com.daqsoft.utils.client;

import org.springframework.util.StringUtils;

/**
 * @Title: 省份城市名称转换
 * @Author: lyl
 * @Date: 2017/12/05 14:55
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
public class ProvionceAndCityName {

    /**
     * 名称转换region表里有不同的需要转换的可以在这里添加
     *
     * @param name
     * @return
     */
    public static String convertName(String name) {
        if(StringUtils.isEmpty(name)){
            return "";
        }else if(name.equals("新疆")) {
            return "新疆维吾尔自治区";
        }else {
            return name;
        }
    }
}