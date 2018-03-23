/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.businessdata;

import com.daqsoft.mapper.resource.DataBusinessMapper;
import com.daqsoft.utils.DataImitateUtil;
import com.daqsoft.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * @Title: 流量数据模拟
 * @Author: lyl
 * @Date: 2018/03/1 15:15
 * @Description: TODO 业务数据-流量数据模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class FlowService {
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
    public void deleteHistory(String minDate, String maxDate, String source, String vcode) throws Exception {
        String sql = "DELETE FROM ussd_flow_date WHERE VCODE = '"
                + vcode + "'AND SOURCE ='" + source + "' AND datetime BETWEEN '"
                + minDate + " 00:00:00' AND '"
                + maxDate + " 23:59:59' ";
        dataBusinessMapper.deleteData(sql);
    }

    /**
     * 流量数据模拟
     *
     * @param minDate 时间区间最小
     * @param maxDate 时间区间最大
     * @param source 来源类型
     * @param flowHourMin 流量数量最小
     * @param flowHourMax 流量数据最大
     * @param vcode
     * @return
     * @throws ParseException
     */
    public long saveFlow(String minDate, String maxDate, String source,
                         String flowHourMin, String flowHourMax, String vcode) throws ParseException {
        int sum = 0;
        String headSql = " INSERT INTO ussd_flow_date (SOURCE,NUM, DATETIME, VCODE ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        List<String> dateList = DateUtil.getSubDateListByString(minDate, maxDate);
        int maxInsert = 6000;
        int k = 0;
        String startHMS = "00:00:00";//24小时
        for (String days : dateList) {
            for (int i = 0; i < 24; i++) {
                //每小时的随机数量
                int todayNum = DataImitateUtil.getRandom(Integer.valueOf(flowHourMin),
                        Integer.valueOf(flowHourMax));
                String teamTime = DateUtil.getHourTime(i, DateUtil.getDate(days + " " + startHMS));
                insertSql.append("('");
                insertSql.append(source);
                insertSql.append("', '");
                insertSql.append(todayNum);
                insertSql.append("', '");
                insertSql.append(teamTime);
                insertSql.append("', '");
                insertSql.append(vcode);
                insertSql.append("')");
                insertSql.append(",");
                sum++;
                k++;
                // 经过测试，数据一次性大概峰值是 30000 条
                if (k >= maxInsert) {
                    dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
                    k = 0;
                    insertSql = new StringBuffer(headSql);
                }
            }
        }
        dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        return sum;
    }
}
