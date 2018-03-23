package com.daqsoft.service.client.ticketanalys;

import com.daqsoft.vo.client.madeVoBean.TicketClTendVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-09-04 14:31
 * @Version: V1.0.0
 * @Describe: 票务统计分析 --- 线上(OTA) 线下 service
 */
@Component
public class TicketOnLineOffLineService {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketOtaAndOnlineService otaAndOnlineService;


    /**
     * 按天获取线上线下 票务销售数据
     *
     * @param vcode
     * @param day
     * @return
     */
    public Map<String, Object> getOnLineOffLineByDay(String vcode, String day) {

        TicketClTendVo otaSaleByDay = otaAndOnlineService.getOnlineTicketByDay(vcode, day);

        TicketClTendVo ticketCountAndPriceByDay = ticketService.getTicketCountAndPriceByDay(vcode, day);

        Map<String, Object> map = new HashMap<>();
        map.put("onLine", otaSaleByDay);
        map.put("offLine", ticketCountAndPriceByDay);
        return map;
    }

    /**
     * 按天区间获取线上线下 销售数据
     *
     * @param vcode
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @return
     */
    public Map<String, Object> getOnLineOffLineByDayRange(String vcode, String startTime, String endTime) {

        //线下
        TicketClTendVo offLine = ticketService.getTicketCountAndPriceByDayRange(vcode, startTime, endTime);

        //线上
        TicketClTendVo onLine = otaAndOnlineService.getOnLineTicketBYDayRange(vcode, startTime, endTime);

        Map<String, Object> map = new HashMap<>();
        map.put("onLine", onLine);
        map.put("offLine", offLine);
        return map;
    }


    /**
     * 按月份获取线上线下 销售数据
     *
     * @param vcode
     * @param month
     * @return
     */
    public Map<String, Object> getOnLineOffLineByMonth(String vcode, String month) {

        TicketClTendVo otaSaleByMonth = otaAndOnlineService.getOnlineTicketByMonth(vcode, month);
        TicketClTendVo ticketCountAndPriceByMonth = ticketService.getTicketCountAndPriceByMonth(vcode, month);

        Map<String, Object> map = new HashMap<>();
        map.put("onLine", otaSaleByMonth);
        map.put("offLine", ticketCountAndPriceByMonth);
        return map;
    }


    /**
     * 按季度获取线上线下销售数据
     *
     * @param vcode
     * @param year
     * @param quarter
     * @return
     */
    public Map<String, Object> getOnLineOffLineByQuarter(String vcode, String year, String quarter) {
        TicketClTendVo ticketCountAndPriceByQuarter = ticketService.getTicketCountAndPriceByQuarter(vcode, year, quarter);
        TicketClTendVo otaSaleByQuarter = otaAndOnlineService.getOnlineTicketByQuarter(vcode, year, quarter);
        Map<String, Object> map = new HashMap<>();
        map.put("onLine", otaSaleByQuarter);
        map.put("offLine", ticketCountAndPriceByQuarter);
        return map;
    }

    /**
     * 按年获取线上线下 销售数据
     *
     * @param vcode
     * @param year  年份
     * @return
     */
    public Map<String, Object> getOnLineOffLineByYear(String vcode, String year) {

        TicketClTendVo ticketCountAndPriceByYear = ticketService.getTicketCountAndPriceByYear(vcode, year);

        TicketClTendVo otaSaleByYear = otaAndOnlineService.getOnlineTicketByYear(vcode, year);

        Map<String, Object> map = new HashMap<>();
        map.put("onLine", otaSaleByYear);
        map.put("offLine", ticketCountAndPriceByYear);
        return map;
    }


    /**
     * 按年区间获取线上线下 销售数据
     *
     * @param vcode
     * @param start 开始年份
     * @param end   结束年份
     * @return
     */
    public Map<String, Object> getOnLineOffLineByYearRange(String vcode, int start, int end) {

        TicketClTendVo otaSaleByYearRange = otaAndOnlineService.getOnlineTicketByYearRange(vcode, start, end);

        TicketClTendVo ticketCountAndPriceByYearRange = ticketService.getTicketCountAndPriceByYearRange(vcode, start, end);

        Map<String, Object> map = new HashMap<>();
        map.put("onLine", otaSaleByYearRange);
        map.put("offLine", ticketCountAndPriceByYearRange);
        return map;
    }


    /**
     * 按年区间获取 线上 线下 票务销售数据.
     *
     * @param vcode
     * @param start
     * @param end
     * @return
     */
    public List<Map<String, Object>> getOnLineOffLineTicketListByYearRange(String vcode, int start, int end) {

        if (start > end)
            new ArrayList<>();

        List<Map<String, Object>> list = new ArrayList<>();

        TicketClTendVo onlineTicketByYear;
        TicketClTendVo ticketCountAndPriceByYear;
        Map<String, Object> map;

        for (int i = start; i <= end; i++) {
            map = new HashMap<>();
            onlineTicketByYear = otaAndOnlineService.getOnlineTicketByYear(vcode, String.valueOf(i));
            ticketCountAndPriceByYear = ticketService.getTicketCountAndPriceByYear(vcode, String.valueOf(i));
            map.put("ticket_time", i + "");
            map.put("ticket_online_num", onlineTicketByYear.getTicket_num());
            map.put("ticket_online_totalPrice", onlineTicketByYear.getTicket_totalPrice());
            map.put("ticket_offline_num", ticketCountAndPriceByYear.getTicket_num());
            map.put("ticket_offline_totalPrice", ticketCountAndPriceByYear.getTicket_totalPrice());
            list.add(map);
        }

        return list;
    }


}
