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
 * @Title: 景区日客流趋势数据模拟
 * @Author: lyl
 * @Date: 2018/03/1 15:15
 * @Description: TODO 业务数据-景区日客流趋势数据模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class PassengerDayService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;

    /**
     * 景区日客流趋势模拟
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param enterMin 进入最小人数
     * @param enterMax 进入人数最大
     * @param leaveMin 离开人最小
     * @param leaveMax 离开人数最大
     * @param vcode 景区编码
     * @return
     * @throws ParseException
     */
    public long savePassengerDay(String startTime,String endTime,Integer enterMin,
                                 Integer enterMax,Integer leaveMin,Integer leaveMax,
            String vcode) throws ParseException {
        String sql = "DELETE FROM ussd_passenger_data WHERE VCODE = '"
                + vcode + "' AND datetime BETWEEN '"
                + startTime + " 00:00:00' AND '"
                + endTime + " 23:59:59' ";
        dataBusinessMapper.deleteData(sql);
        String headSql = "INSERT INTO  ussd_passenger_data (ENTER_NUM,LEAVE_NUM, DATETIME,vcode ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        //时间区间内的时间
        List<String> dateList = DateUtil.getSubDateListByString(startTime, endTime);
        long sum = 0;
        int maxInsert = 6000;
        int k = 0;
        for (String date : dateList) {
                //当天的进入随机
                int enterNum = DataImitateUtil.getRandom(enterMin, enterMax);
                //当天的离开随机
                int leaveNum = DataImitateUtil.getRandom(leaveMin, enterNum);
                insertSql.append("('");
                insertSql.append(enterNum);
                insertSql.append("', '");
                insertSql.append(leaveNum);
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
        if (k > 0) {
            //保存负氧离子数据
            dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        }
        return sum;
    }


}
