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
 * @Title: OxygenDataService
 * @Author: superziy
 * @Date: 2018/02/09 17:15
 * @Description: TODO 业务数据-负氧离子数据模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class OxygenDataService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;

    /**
     * 负氧离子数据模拟
     *
     * @param minDate   时间开始
     * @param maxDate   时间结束
     * @param oxyMinNum 数量最小
     * @param oxyMaxNum 数量最大
     * @return
     */
    public long saveOxyGenData(String minDate, String maxDate,
                               Integer oxyMinNum, Integer oxyMaxNum, String vcode)  throws Exception{
        String sql = "DELETE FROM ussd_oxygen_data WHERE VCODE = '"
                + vcode + "' AND datetime BETWEEN '"
                + minDate + " 00:00:00' AND '"
                + maxDate + " 23:59:59' ";
        dataBusinessMapper.deleteData(sql);
        String headSql = "INSERT INTO ussd_oxygen_data (OXY_NUM, DATETIME,vcode ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        //时间区间内的时间
        List<String> dateList = DateUtil.getSubDateListByString(minDate, maxDate);
        String startHMS = "00:00:00";//24小时制
        Integer sum = 0;
        int maxInsert = 6000;
        int k = 0;
        for (String date : dateList) {
            for (int i = 0; i < 24; i++) {
                //当天的随机数量
                int todayNum = DataImitateUtil.getRandom(oxyMinNum, oxyMaxNum);
                String oxyTime = DateUtil.getHourTime(i, DateUtil.getDate(date + " " + startHMS));
                insertSql.append("('");
                insertSql.append(todayNum);
                insertSql.append("', '");
                insertSql.append(oxyTime);
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
        if (k > 0) {
            //保存负氧离子数据
            dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        }
        return sum;
    }
}
