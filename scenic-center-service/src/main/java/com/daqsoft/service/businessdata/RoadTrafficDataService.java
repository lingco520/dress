package com.daqsoft.service.businessdata;

import com.daqsoft.mapper.resource.DataBusinessMapper;
import com.daqsoft.utils.DataImitateUtil;
import com.daqsoft.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * 道路交通预警模拟
 */
@Service
public class RoadTrafficDataService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;

    /**
     * 删除历史数据
     *
     * @param minDate 时间区间最小
     * @param maxDate 时间区间最大
     * @param vcode
     * @return
     * @throws ParseException
     */
    public void deleteHistory(String minDate, String maxDate, String vcode) throws Exception {
        String sql = "DELETE FROM ussd_road_traffic_data WHERE VCODE = '"
                + vcode + "' AND datetime BETWEEN '"
                + minDate + " 00:00:00' AND '"
                + maxDate + " 23:59:59' ";
        System.out.println(sql);
        dataBusinessMapper.deleteData(sql);
    }

    /**
     * 道路交通预警模拟
     *
     * @param minDate     时间区间最小
     * @param maxDate     时间区间最大
     * @param vcode
     * @param roadTraffic 道路交通
     * @return
     * @throws ParseException
     */
    public long saveData(String minDate, String maxDate, String roadTraffic, String vcode) throws ParseException {
        int sum = 0;
        String headSql = " INSERT INTO ussd_road_traffic_data (LEVEL,TRAFFIC_ID, DATETIME, VCODE,START_TIME,END_TIME ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        List<String> dateList = DateUtil.getSubDateListByString(minDate, maxDate);
        String startHMS = "00:00:00";//24小时
        for (String days : dateList) {
            for (String roadTrafficId : roadTraffic.split(",")) {
                insertSql.append("('");
                insertSql.append(DataImitateUtil.getRandom(1, 3));
                insertSql.append("', '");
                insertSql.append(roadTrafficId);
                insertSql.append("', '");
                insertSql.append(days + " " + startHMS);
                insertSql.append("', '");
                insertSql.append(vcode);
                insertSql.append("', '");
                //模拟时间段
                String timeFrame = DataImitateUtil.getTimeFrame();
                String[] split = timeFrame.split(",");
                insertSql.append(split[0]);
                insertSql.append("', '");
                insertSql.append(split[1]);
                insertSql.append("')");
                insertSql.append(",");
                ++sum;
            }
        }
        if (sum > 0) {
            dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        }
        return sum;
    }
}
