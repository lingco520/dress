package com.daqsoft.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-08-10 9:39
 * @Version: v1
 * @Describe: 自定义配置文件
 */
@Component
@ConfigurationProperties(prefix="myProps") //接收application.yml中的myProps下面的属性
public class MyProps {

    /**
     * 图标获取
     *
     */
    private Map<String,String> trafficIcon = new HashMap<>();

    /**
     * 周边天气配置region
     *
     */
    private Map<String,String> circumWeather = new HashMap<>();

    /**
     * 一体机Logo地址
     *
     */
    private Map<String,String> aioLogo = new HashMap<>();

    //票务类型 k 小类 v 所属大类
    private Map<String,String> ticketType = new HashMap<>();

    //景区大数据--通过移动运营商拿数据的景区
    private Map<String,String> bigPassenger = new HashMap<>();

    //通用景区
//    private Map<String,String> commonScenic = new HashMap<>();

    //环境
    private Map<String,String> environment = new HashMap<>();

    //本地外地票务分类配置
    private Map<String,Map<String,String>> localAndField = new HashMap<>();

    //游客类型分类配置
    private Map<String,Map<String,String>> touristType = new HashMap<>();

    public Map<String, Map<String, String>> getTouristType() {
        return touristType;
    }

    public void setTouristType(Map<String, Map<String, String>> touristType) {
        this.touristType = touristType;
    }

    public Map<String, Map<String, String>> getLocalAndField() {
        return localAndField;
    }

    public void setLocalAndField(Map<String, Map<String, String>> localAndField) {
        this.localAndField = localAndField;
    }

//    public Map<String, String> getCommonScenic() {
//        return commonScenic;
//    }
//
//    public void setCommonScenic(Map<String, String> commonScenic) {
//        this.commonScenic = commonScenic;
//    }

    public Map<String, String> getTicketType() {
        return ticketType;
    }

    public void setTicketType(Map<String, String> ticketType) {
        this.ticketType = ticketType;
    }

    public Map<String, String> getBigPassenger() {
        return bigPassenger;
    }
    public void setBigPassenger(Map<String, String> bigPassenger) {
        this.bigPassenger = bigPassenger;
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, String> environment) {
        this.environment = environment;
    }

    public Map<String, String> getCircumWeather() {
        return circumWeather;
    }

    public void setCircumWeather(Map<String, String> circumWeather) {
        this.circumWeather = circumWeather;
    }

    public Map<String, String> getAioLogo() {
        return aioLogo;
    }

    public void setAioLogo(Map<String, String> aioLogo) {
        this.aioLogo = aioLogo;
    }

    public Map<String, String> getTrafficIcon() {
        return trafficIcon;
    }

    public void setTrafficIcon(Map<String, String> trafficIcon) {
        this.trafficIcon = trafficIcon;
    }
}
