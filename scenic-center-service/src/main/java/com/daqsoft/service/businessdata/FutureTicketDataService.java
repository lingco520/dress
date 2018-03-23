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

import java.util.List;

/**
 * @Title: FutureTicketDataService
 * @Author: superziy
 * @Date: 2018/02/09 17:15
 * @Description: TODO 业务数据-未来票务数据模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class FutureTicketDataService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;


    /**
     * 未来票务数据模拟
     * @param minDate 时间开始
     * @param maxDate 时间结束
     * @param onlineMin 线上最小
     * @param onlineMax 线上最大
     * @param offLineMin 线下最小
     * @param offLineMax 线下最大
     * @param vcode 景区编码
     * @return 模拟数量
     */
    public long saveFutureTicketData(String minDate, String maxDate,
                                     Integer onlineMin, Integer onlineMax, Integer offLineMin,
                                     Integer offLineMax, String vcode) throws Exception{
        String sql = "DELETE FROM ussd_ticket_forecast WHERE VCODE = '"
                + vcode + "' AND datetime BETWEEN '"
                + minDate + " 00:00:00' AND '"
                + maxDate + " 23:59:59' ";
        dataBusinessMapper.deleteData(sql);
        String headSql = " INSERT INTO ussd_ticket_forecast (ONLINE,OFFLINE, DATETIME,vcode ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        List<String> dateList = DateUtil.getSubDateListByString(minDate, maxDate);
        int maxInsert = 6000;
        int k = 0;
        Integer sum = 0;
        for(String date : dateList){
            //预测线上
            int online = DataImitateUtil.getRandom(onlineMin,onlineMax);
            int offline = DataImitateUtil.getRandom(offLineMin,offLineMax);
            insertSql.append("('");
            insertSql.append(online);
            insertSql.append("', '");
            insertSql.append(offline);
            insertSql.append("', '");
            insertSql.append(date);
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

        //保存aqi数据
        dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        return sum;
    }
}
