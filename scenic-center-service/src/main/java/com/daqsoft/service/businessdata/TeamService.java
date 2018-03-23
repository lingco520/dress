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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title: 团队数量
 * @Author: lyl
 * @Date: 2018/03/1 15:15
 * @Description: TODO 业务数据-团队数量模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class TeamService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;

    /**
     * 团队数量模拟
     *
     * @param minDate 时间最小区间
     * @param maxDate 时间最大区间
     * @param teamMin 团队最小模拟区间
     * @param teamMax 团队最大模拟区间
     * @param vcode 景区编码
     * @return
     */
    public long saveTeam(String minDate, String maxDate, int teamMin, int teamMax, String vcode) throws ParseException {
        int sum = 0;
        String sql = "DELETE FROM ussd_team_data WHERE VCODE = '"
                + vcode + "' AND datetime BETWEEN '"
                + minDate + " 00:00:00' AND '"
                + maxDate + " 23:59:59' ";
        dataBusinessMapper.deleteData(sql);
        String headSql = " INSERT INTO ussd_team_data (ADDRESS,TEAM_NUM, DATETIME, VCODE ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        List<String> dateList = DateUtil.getSubDateListByString(minDate, maxDate);
        //查询所有地区
        List<Map> cityCarList = dataBusinessMapper.getAddressNameList();
        //随机五个地区
        List<Map> addressList = new ArrayList();
        for (int i = 0; i < 5; i++) {
            addressList.add(cityCarList.get(DataImitateUtil.getRandom(0, 33)));
        }
        int maxInsert = 6000;
        int k = 0;
        String startHMS = "07:00:00";//14小时
        for (String days : dateList) {
            //当天的随机数量
            int todayNum = DataImitateUtil.getRandom(teamMin, teamMax) + 14;//假如开始结束数量一致，随机数没法分配时间
            for (int i = 0; i < 14; i++) {
                //每个小时的随机数量
                int hour = todayNum / 14;
                String teamTime = DateUtil.getHourTime(i, DateUtil.getDate(days + " " + startHMS));
                insertSql.append("('");
                //随机省份
                insertSql.append(addressList.get(DataImitateUtil.getRandom(0, 4)).get("NAME"));
                insertSql.append("', '");
                insertSql.append(hour);
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
