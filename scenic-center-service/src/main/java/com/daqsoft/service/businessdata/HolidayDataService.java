/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.businessdata;

import com.daqsoft.mapper.resource.DataBusinessMapper;
import com.daqsoft.utils.DataImitateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: HolidayDataService
 * @Author: superziy
 * @Date: 2018/02/09 17:15
 * @Description: TODO 业务数据-节假日预测数据模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class HolidayDataService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;


    /**
     * 节假日预测数据模拟
     *
     * @param spring       春节
     * @param tombSweeping 清明节
     * @param labour       劳动节
     * @param dragon       端午节
     * @param midAutumn    中秋节
     * @param national     国庆节
     * @param newYear      元旦
     * @param vcode        景区编码
     * @return 模拟数量
     */
    public long saveHolidayData(Integer spring, Integer tombSweeping, Integer labour,
                                Integer dragon, Integer midAutumn,
                                Integer national, Integer newYear, String vcode) throws Exception {
        String sql = "DELETE FROM ussd_holiday_forecast WHERE VCODE = '" + vcode + "'";
        dataBusinessMapper.deleteData(sql);
        String headSql = " INSERT INTO ussd_holiday_forecast (HOLIDAY_NAME,NUM, DATETIME,vcode ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        Integer[] holidayData = {spring, tombSweeping, labour, dragon, midAutumn, national, newYear};
        String[] holidays = {"春节", "清明节", "劳动节", "端午节", "中秋节", "国庆节", "元旦节"};
        Integer sum = 0;
        for (int i = 0; i < holidayData.length; i++) {
            insertSql.append("('");
            insertSql.append(holidays[i]);
            insertSql.append("', '");
            insertSql.append(holidayData[i]);
            insertSql.append("', '");
            insertSql.append(DataImitateUtil.getNowDateStr());
            insertSql.append("', '");
            insertSql.append(vcode);
            insertSql.append("')");
            insertSql.append(",");
            sum++;
        }
        //保存aqi数据
        dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        return sum;
    }
}
