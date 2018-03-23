package com.daqsoft.utils.client;

/**
 * @Title: 微件库的redis缓存key常量(公共所有)
 * @Author: zf
 * @Date: 2017/08/02 10:10
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
public class RedisKey {


    //监控列表
    public static final String MONITOR_LIST="monitorList:";

    public static final String SIMULATE="simulate:";

    /*车流分析 zf*/

    //模块名 车流分析
    public static final String MOUDEL_CARFLOW="carflowV3_1:";

    //类名 车流来源地
    public static final String CLASS_CARCOMETOCITY = "carComeToCity:";

    //类名 省份车辆来源 排行榜
    public static final String CLASS_CARPROVINCE = "carProvince:";

    //类名 停车场
    public static final String CLASS_JQTVPMCHECKOUTWIDGET = "jqTvpmCheckoutWidget:";
    public static final String CLASS_PARKINGWIDGET = "parkingwidget:";

    //类名 车流趋势
    public static final String CLASS_BIGCARFLOW = "bigcarFlow:";

    //类名 各类车型分布
    public static final String CLASS_CARSFLOW = "carsFlow:";
    public static final String CLASS_CARTYPEANALYS = "carTypeAnalys:";

    //类名 小客车量与散客量关系
    public static final String CLASS_TIMELYPEOPLEWIDGET = "timelyPeopleWidget:";


    /*模块 客流分析 zf*/
    public static final String MOUDEL_PASSENGERFLOW="passengerflow:";

    //总体客流分析
    public static final String CLASS_BIGREALPEOPLE="bigRealPeople:";


    //类名 小客车量与散客量关系
    public static final String CLASS_BIGPEOPLE = "people:";

    //团队票务
    public static final String MODULE_PEOPLE_TEAM_MONEY = "peopleTeamMoney:";

    //散客票务
    public static final String MODULE_PEOPLE_FIT_MONEY = "peopleFitMoney:";

    //团队数量
    public static final String MODULE_PEOPLE_TEAM_NUM = "peopleTeamNum:";

    //散客数量
    public static final String MODULE_PEOPLE_FIT_NUM = "peopleFitNum:";

    //客流峰谷值
    public static final String MODULE_PEOPLE_PV = "peoplePv:";


    //票务相关
    public static final String  MODULE_TICKET = "ticket:";
    //线下票务
    public static final String  CLASS_TICKET_LINE = "lineTicket:";
    //OTA票务
    public static final String  CLASS_TICKET_OTA = "otadate:";


    //大数据游客统计
    public static final String MOUDEL_BIGDATAPASSENGER = "bigDataPassenger:";
    //省内游客统计
    public static final String CLASS_BIG_PASSENGER = "bigDataPassenger:";

    //常量
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    public static final String TIME = "time";
    public static final String QUARTER = "quarter";
    public static final String COLON = ":";
    public static final String TIMESLOT = "timeSlot";

    //季度
    public static final String QUARTER_Q1 = "1";
    public static final String QUARTER_Q2 = "2";
    public static final String QUARTER_Q3 = "3";
    public static final String QUARTER_Q4 = "4";

    //浏览统计
    public static  final  String PAGE_VIEW = "pageView";



}