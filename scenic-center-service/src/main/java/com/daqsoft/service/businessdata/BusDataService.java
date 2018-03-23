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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: BusDataService
 * @Author: superziy
 * @Date: 2018/02/09 17:15
 * @Description: TODO 业务数据-大巴车数据模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class BusDataService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;

    /**
     * 大巴车数据
     *
     * @param one   第一年数据模拟
     * @param two   第二年数据模拟
     * @param three 第三年数据模拟
     * @param four  第四年数据模拟
     * @param five  第五年数据模拟
     * @param vcode
     * @return
     */
    public long saveBusData(String maxDate, Integer one, Integer two,
                            Integer three, Integer four, Integer five, String vcode) throws Exception {
        Map map = new HashMap();
        map.put("vcode", vcode);
        String sql = "DELETE FROM ussd_bus_data WHERE VCODE = '" + vcode + "'";
        dataBusinessMapper.deleteData(sql);
        String headSql = " INSERT INTO ussd_bus_data (NUM, DATE,VCODE ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        Integer sum = 0;
        Integer[] bus = {one, two, three, four, five};
        String oldYear = DateUtil.getLastYear(4, DataImitateUtil.getYmdDate(maxDate));//时间相减得到前五年
        for (int i = 0; i < 5; i++) {//循环前五年的时间
            Integer num = bus[i];//这一年总数
            String indexYear = (Integer.valueOf(oldYear) + i)+"" ;
            //平均每天数量
            //每年的天数
            List<String> dateList = DateUtil.getSubDateList(
                    DateUtil.getDate(indexYear + "-01-01 00:00:00"),
                    DateUtil.getDate(indexYear + "-12-31 23:59:59"));
            Integer dayAvgNum = 0;
            if(dateList.size() > 0){
                dayAvgNum = Integer.valueOf(new BigDecimal(num).divide(new BigDecimal(dateList.size()), 0, BigDecimal.ROUND_UP).toString());
            }
            // 声明一个标记
            boolean flag = true;
            //循环时间区间
            for (String date : dateList) {
                insertSql.append("('");
                if(!flag) {
                    insertSql.append(num);
                } else {
                    insertSql.append(dayAvgNum);
                }
                insertSql.append("', '");
                insertSql.append(date);
                insertSql.append("', '");
                insertSql.append(vcode);
                insertSql.append("')");
                insertSql.append(",");
                sum++;
                //总数减少每天数量
                num -= dayAvgNum;
                if(num < dayAvgNum){
                    flag = false;
                }
                if(num <= 0){
                    break;
                }
            }
        }
        dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        return sum;
    }


    //两个Double数相减
    public static Double sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }
}
