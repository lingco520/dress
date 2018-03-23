/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.businessdata;

import com.daqsoft.entity.travelAgency.ResTravelAgency;
import com.daqsoft.mapper.resource.DataBusinessMapper;
import com.daqsoft.mapper.resource.TravelAgencyMapper;
import com.daqsoft.utils.DataImitateUtil;
import com.daqsoft.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: TravelDataService
 * @Author: superziy
 * @Date: 2018/02/09 17:15
 * @Description: TODO 业务数据-旅行社团队模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class TravelDataService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;
    @Autowired
    private TravelAgencyMapper travelAgencyMapper;

    /**
     * 旅行社团队模拟
     *
     * @param minDate   时间开始
     * @param maxDate   时间结束
     * @param minTravel 团队最小
     * @param maxTravel 团队最大
     * @param vcode     景区编码
     * @return
     */
    public long saveTravelData(String minDate, String maxDate,
                               Integer minTravel, Integer maxTravel, String vcode) throws Exception {
        Map map = new HashMap();
        map.put("vcode", vcode);
        String sql = "DELETE FROM ussd_travel_data WHERE VCODE = '"
                + vcode + "' AND datetime BETWEEN '"
                + minDate + " 00:00:00' AND '"
                + maxDate + " 23:59:59' ";
        dataBusinessMapper.deleteData(sql);
        String headSql = " INSERT INTO ussd_travel_data (NAME,TEAMNUM, DATETIME,VCODE ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        Integer sum = 0;
        int maxInsert = 6000;
        int k = 0;
        List<String> dateList = DateUtil.getSubDateListByString(minDate, maxDate);
        //首先查询系统中是否有该景区的旅行社或者合作旅行社
        List<ResTravelAgency> list = travelAgencyMapper.selectList(map);
        //如果有则使用，没有则默认使用其他旅行社
        if (list.size() > 0) {
            //循环时间段
            for (String date : dateList) {
                List<ResTravelAgency> listData = new ArrayList<>(list);
                //数量随机
                int random = DataImitateUtil.getRandom(0, listData.size() - 1);
                for (int i = 0; i < random; i++) {
                    //随机下标
                    int teamRandom = DataImitateUtil.getRandom(0, listData.size() - 1);
                    //随机团队区间范围
                    int num = DataImitateUtil.getRandom(minTravel, maxTravel);
                    insertSql.append("('");
                    insertSql.append(listData.get(teamRandom).getName());
                    insertSql.append("', '");
                    insertSql.append(num);
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
                    listData.remove(teamRandom);
                }
            }
            dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        } else {
            String[] travelName = {"中旅总社CTS", "中国国旅", "中青旅CYTS", "康辉旅行社", "春秋旅行社"};
            //数量随机
            for (String date : dateList) {
                int random = DataImitateUtil.getRandom(0, travelName.length);
                for (int i = 0; i < random; i++) {
                    //随机团队区间范围
                    int num = DataImitateUtil.getRandom(minTravel, maxTravel);
                    insertSql.append("('");
                    insertSql.append(travelName[i]);
                    insertSql.append("', '");
                    insertSql.append(num);
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
            }
            dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        }
        return sum;
    }
}
