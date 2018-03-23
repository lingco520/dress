/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.businessdata;

import com.daqsoft.mapper.resource.DataBusinessMapper;
import com.daqsoft.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ThunderDataService
 * @Author: superziy
 * @Date: 2018/02/09 17:15
 * @Description: TODO 业务数据-雷电数据模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class ThunderDataService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;

    /**
     * 雷电数据
     *
     * @param longitude 雷电经度
     * @param latitude 雷电纬度
     * @param vcode 景区编码
     * @return
     */
    public long saveThunder(String minDate, String maxDate,
                            String longitude, String latitude, String vcode) throws Exception {

        Map map = new HashMap();
        map.put("vcode", vcode);
        String sql = "DELETE FROM ussd_thunder_data WHERE VCODE = '"
                + vcode + "' AND datetime BETWEEN '"
                + minDate + " 00:00:00' AND '"
                + maxDate + " 23:59:59' ";
        dataBusinessMapper.deleteData(sql);
        List<String> dateList = DateUtil.getSubDateListByString(minDate, maxDate);
        String headSql = "INSERT INTO ussd_thunder_data (THUNDER_LONGITUDE, THUNDER_LATITUDE,DATETIME,vcode ) VALUES ";
        StringBuffer insertSql = new StringBuffer(headSql);
        Integer sum = 0;
        int maxInsert = 6000;
        int k = 0;
        for (String date : dateList) {
            k++;
            sum++;
            insertSql.append("('");
            insertSql.append(longitude);
            insertSql.append("', '");
            insertSql.append(latitude);
            insertSql.append("', '");
            insertSql.append(date);
            insertSql.append("', '");
            insertSql.append(vcode);
            insertSql.append("')");
            insertSql.append(",");
            // 经过测试，数据一次性大概峰值是 30000 条
            if (k >= maxInsert) {
                dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
                k = 0;
                insertSql = new StringBuffer(headSql);
            }
        }
        if (k > 0) {
            //保存水文数据
            dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        }

        return sum;
    }
}
