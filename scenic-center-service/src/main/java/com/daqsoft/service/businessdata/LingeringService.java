/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.businessdata;

import com.daqsoft.entity.businessData.BusinessData;
import com.daqsoft.mapper.resource.DataBusinessMapper;
import com.daqsoft.utils.DataImitateUtil;
import com.daqsoft.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * @Title: 驻留时长
 * @Author: lyl
 * @Date: 2018/03/1 15:15
 * @Description: TODO 业务数据-驻留时长模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class LingeringService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;

    /**
     * 驻留时长模拟
     *
     * @param dto
     * @return
     */
    public long saveLingering(BusinessData dto, String vcode) throws ParseException {
        long sum = 0L;
        //判断是否模拟该时间段的数据
        if ("1".equals(dto.getKindOne())) {
            long one = insertSqlSpell(dto,
                    dto.getLingeringAmountMinOne(),
                    dto.getLingeringAmountMaxOne(), dto.getKindOne(), vcode);
            sum += one;
        }
        if ("2".equals(dto.getKindTwo())) {
            long two = insertSqlSpell(dto,
                    dto.getLingeringAmountMinTwo(),
                    dto.getLingeringAmountMaxTwo(), dto.getKindTwo(), vcode);
            sum += two;
        }
        if ("3".equals(dto.getKindThree())) {
            long three = insertSqlSpell(dto,
                    dto.getLingeringAmountMinThree(),
                    dto.getLingeringAmountMaxThree(), dto.getKindThree(), vcode);
            sum += three;
        }
        if ("4".equals(dto.getKindFour())) {
            long four = insertSqlSpell(dto,
                    dto.getLingeringAmountMinFour(),
                    dto.getLingeringAmountMaxFour(), dto.getKindFour(), vcode);
            sum += four;
        }
        if ("5".equals(dto.getKindFive())) {
            long five = insertSqlSpell(dto,
                    dto.getLingeringAmountMinFive(),
                    dto.getLingeringAmountMaxFive(), dto.getKindFive(), vcode);
            sum += five;
        }
        if ("6".equals(dto.getKindSix())) {
            long six = insertSqlSpell(dto,
                    dto.getLingeringAmountMinSix(),
                    dto.getLingeringAmountMaxSix(), dto.getKindSix(), vcode);
            sum += six;
        }
        if ("7".equals(dto.getKindSeven())) {
            long seven = insertSqlSpell(dto,
                    dto.getLingeringAmountMinSeven(),
                    dto.getLingeringAmountMaxSeven(), dto.getKindSeven(), vcode);
            sum += seven;
        }
        return sum;
    }

    /**
     * 拼接执行新增
     * @param dto 实体类
     * @param minNum 最小数量
     * @param maxNum 最大数量
     * @param type 类型
     * @param vcode 景区编码
     * @return
     */
    public Long insertSqlSpell(BusinessData dto, Integer minNum, Integer maxNum, String type, String vcode) {
        //时间区间
        List<String> dateList = DateUtil.getSubDateListByString(dto.getStartDate(), dto.getEndDate());
        String sql = "DELETE FROM ussd_lingering_data WHERE VCODE = '"
                + vcode + "' AND TYPE = '" + type + "' AND datetime BETWEEN '"
                + dto.getStartDate() + " 00:00:00' AND '"
                + dto.getEndDate() + " 23:59:59' ";
        dataBusinessMapper.deleteData(sql);
        long count = 0L;//统计数量
        int maxInsert = 6000;
        int k = 0;//统计最多数量
        String headSql = " INSERT INTO ussd_lingering_data (TYPE,NUM, DATETIME, VCODE ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        for (String days : dateList) {
            //当天的随机数量
            int todayNum = DataImitateUtil.getRandom(minNum, maxNum);
            insertSql.append("('");
            insertSql.append(type);
            insertSql.append("', '");
            insertSql.append(todayNum);
            insertSql.append("', '");
            insertSql.append(days);
            insertSql.append("', '");
            insertSql.append(vcode);
            insertSql.append("')");
            insertSql.append(",");
            count++;
            k++;
            // 经过测试，数据一次性大概峰值是 30000 条
            if (k >= maxInsert) {
                dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
                k = 0;
                insertSql = new StringBuffer(headSql);
            }
        }
        dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        return count;
    }
}
