package com.daqsoft.service.client.passengerFlowService;


import com.daqsoft.vo.client.madeVoBean.OtaDataTendVo;
import com.daqsoft.vo.client.madeVoBean.RealPeopleIndexSumVo;
import com.daqsoft.vo.client.madeVoBean.SumPeopleIndexVo;
import com.daqsoft.vo.client.madeVoBean.TicketIndexOnAndOffVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: lyl .
 * @Date: Created in 2017/6/18.
 * @Version: V3.0.0.
 * @describe:客流与票务service
 */
public interface PassengerTicketService {

    /**
     * 前、去、今年人数及同比(redis)
     *
     * @param vcode
     * @param type
     * @return
     */
    SumPeopleIndexVo sumPeopleIndexRedis(String oldVcode, String vcode, String type);

    /**
     * 前、去、今年人数及同比(数据库)
     *
     * @param vcode
     * @return
     */
    SumPeopleIndexVo sumPeopleIndexDB(String vcode);

    /**
     * 前、去、今年人数(取得票务)及同比(redis)
     *
     * @param oldVcode
     * @param vcode
     * @return
     */
    SumPeopleIndexVo sumPeopleIndexRedisTicket(String oldVcode, String vcode);


    /**
     * 线上线下票务销售量（数据库）
     *
     * @param vcode
     * @return
     */
    TicketIndexOnAndOffVo ticketIndexOnAndOffRedis(String vcode, String mdfvcode);


    /**
     * 线上线下票务销售量（赛里木湖）
     *
     * @param vcode
     * @param mdfvcode
     */
    Map ticketIndexOnAndOffRedisSLMH(String vcode, String mdfvcode);


    /**
     * 今日OTA数量及占比(数据库）
     *
     * @param vcode
     * @param time
     * @return
     */
    List<OtaDataTendVo> otaDataTendDB(String vcode, String time);

    /**
     * 今日OTA数量及占比(redis)
     *
     * @param vcode
     * @param oldVcode 没有经过加密的vcode
     * @param time
     * @return
     */
    List<OtaDataTendVo> otaDataTendRedis(String vcode, String time, String oldVcode);


    /**
     * 今日OTA数量及占比(redis)华清宫
     *
     * @param vcode
     * @param oldVcode 没有经过加密的vcode
     * @param time
     * @return
     */
    Map otaDataTendRedisHQG(String vcode, String time, String oldVcode);

    /**
     * 神龙峡客流与票务OTA模块定制
     *
     * @param vcode
     * @param oldVcode 没有经过加密的vcode
     * @param time
     * @return
     */
    Object slxOtaDataTendRedis(String vcode, String time, String oldVcode);

    /**
     * 眉山农业嘉年华客流与票务OTA模块定制
     *
     * @param vcode
     * @param time
     * @param oldVcode
     * @return
     */
    Object msOtaDataTendRedis(String vcode, String time, String oldVcode);

    /**
     * 今日实时人数，DB
     *
     * @param map
     * @return
     */
    RealPeopleIndexSumVo getPeopleRealTime(Map map);

    /**
     * 查询景区景点人数  今日 昨天  承载量
     * @return
     */
    Map<String, Object> getSpotsPeoples(String vcode, String date) throws Exception;

    /**
     * 景区查询团队数
     * @param vcode
     * @param date
     * @return
     */
    Map<String, Object> getTeamList(String oldVcode, String vcode, String date) throws Exception;

    /**
     * 景区景点实时人数
     * @param vcode
     * @param date
     * @return
     */
    List actualPassenger(String vcode, String date);

    /**
     * 景区实时游客量
     * @param date
     * @param mdfVcode
     * @return
     */
    Map<String,Object> scenicPassenger(String date, String mdfVcode);

    /**
     * 景区投诉
     * @param nowyear
     * @param mdfVcode
     * @return
     */
    Map<String,Object> complaints(String nowyear, String mdfVcode);

    /**
     * 统计票务今日、去年今日、前年今日数量
     * @param vcodemdh
     * @return
     */
    SumPeopleIndexVo sumTicketRedis(String vcode, String vcodemdh);

    /**
     * 查询景区平均团队数
     * @param vcode
     * @param date
     * @return
     */
    Map<String,Object> getScenicAvgTeam(String vcode, String date);

    /**
     * 云台山票务特殊处理，客户要求，线下比线上多10%
     * @param vcode
     * @param vcodemdh
     * @return
     */
    TicketIndexOnAndOffVo ticketIndexOnAndOffRedisYTS(String vcode, String vcodemdh);

    /**
     * 云台山OTA特殊处理，客户要求固定值
     * @param vcodemdh
     * @param today
     * @param vcode
     * @return
     */
    List<OtaDataTendVo> otaDataTendRedisYTS(String vcodemdh, String today, String vcode);
}
