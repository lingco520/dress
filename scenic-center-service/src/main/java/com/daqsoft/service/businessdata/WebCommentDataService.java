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
 * @Title: WebCommentDataService
 * @Author: superziy
 * @Date: 2018/02/09 17:15
 * @Description: TODO 业务数据-网评数据模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class WebCommentDataService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;

    /**
     * 网评数据模拟
     *
     * @param minDate  时间开始
     * @param maxDate  时间结束
     * @param wpMinNum 数量最小
     * @param wpMaxNum 数量最大
     * @return
     */
    public long saveWebCommentData(String minDate, String maxDate,
                                   Integer wpMinNum, Integer wpMaxNum, String vcode) throws Exception {
        String sql = "DELETE FROM ussd_web_comment WHERE VCODE = '"
                + vcode + "' AND datetime BETWEEN '"
                + minDate + " 00:00:00' AND '"
                + maxDate + " 23:59:59' ";
        dataBusinessMapper.deleteData(sql);
        String headSql = " INSERT INTO ussd_web_comment (TYPE, DATETIME,vcode ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        //网评数据的类型

        String[] webComment = {"服务", "价格", "卫生", "管理", "饮食", "设施", "景点", "交通", "游客", "其他"};
        //时间区间内的时间
        List<String> dateList = DateUtil.getSubDateListByString(minDate, maxDate);
        String startHMS = "00:00:00";
        // 数据每天结束时间点
        String endHMS = "23:59:59";
        Integer sum = 0;
        int maxInsert = 6000;
        int k = 0;
        for (String date : dateList) {
            //当天的随机数量
            int todayNum = DataImitateUtil.getRandom(wpMinNum, wpMaxNum);
            // 获取每条记录之间的相差秒数
            int recordSecond = DataImitateUtil.getRecordSecond(todayNum, DataImitateUtil.getSecSub(startHMS, endHMS));
            String startTime = date + " " + startHMS;
            for (int i = 1; i <= todayNum; i++) {
                int num = DataImitateUtil.getRandom(0, 9);
                //网评类型
                String wpType = webComment[num];
                // 时间，记录添加时间 模拟成一致
                String time = DataImitateUtil.getDateAddSecond(startTime, recordSecond * i);

                insertSql.append("('");
                insertSql.append(wpType);
                insertSql.append("', '");
                insertSql.append(time);
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
            //保存网评数据
            dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        }
        return sum;
    }
}
