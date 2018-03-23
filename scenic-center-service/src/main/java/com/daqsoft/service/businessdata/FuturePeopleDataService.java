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
 * @Title: FuturePeopleDataService
 * @Author: superziy
 * @Date: 2018/03/02 17:15
 * @Description: TODO 业务数据-未来客流数据模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class FuturePeopleDataService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;


    /**
     * 未来客流预测&未来6天客流分析
     *
     * @param minDate   时间开始
     * @param maxDate   时间结束
     * @param peopleMin 最小人数
     * @param peopleMax 最大人数
     * @param vcode     景区编码
     * @return 模拟数量
     */
    public long saveFuturePeopleData(String minDate, String maxDate,
                                     Integer peopleMin, Integer peopleMax, String vcode)  throws Exception{
        String sql = "DELETE FROM ussd_people_forecast WHERE VCODE = '"
                + vcode + "' AND datetime BETWEEN '"
                + DataImitateUtil.getYmdStr(minDate) + " 00:00:00' AND '"
                + DataImitateUtil.getYmdStr(maxDate) + " 23:59:59' ";
        dataBusinessMapper.deleteData(sql);
        String lastSql = "DELETE FROM ussd_people_forecast WHERE VCODE = '"
                + vcode + "' AND datetime BETWEEN '"
                + DateUtil.getYearSubTime(1, minDate) + " 00:00:00' AND '"
                + DateUtil.getYearSubTime(1, maxDate) + " 23:59:59' ";
        dataBusinessMapper.deleteData(lastSql);
        String headSql = " INSERT INTO ussd_people_forecast (NUM, DATETIME,vcode ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        List<String> dateList = DateUtil.getSubDateListByString(minDate, maxDate);
        int maxInsert = 6000;
        int k = 0;
        Integer sum = 0;
        for (String date : dateList) {
            //随机今年人数
            int num = DataImitateUtil.getRandom(peopleMin, peopleMax);
            insertSql.append("('");
            insertSql.append(num);
            insertSql.append("', '");
            insertSql.append(date);
            insertSql.append("', '");
            insertSql.append(vcode);
            insertSql.append("')");
            insertSql.append(",");
            //随机去年人数
            String lastYear = DateUtil.getYearSubTime(1, date);
            int lastNum = DataImitateUtil.getRandom(peopleMin, peopleMax);
            insertSql.append("('");
            insertSql.append(lastNum);
            insertSql.append("', '");
            insertSql.append(lastYear);
            insertSql.append("', '");
            insertSql.append(vcode);
            insertSql.append("')");
            insertSql.append(",");
            sum++;
            k++;
            // 经过测试限制最大拼接条件
            if (k >= maxInsert) {
                dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
                k = 0;
                insertSql = new StringBuffer(headSql);
            }
        }
        //保存未来客流数据
        dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        return sum;
    }
}
