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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Title: 游客画像模拟PortrayalService
 * @Author: lyl
 * @Date: 2018/03/1 15:15
 * @Description: TODO 业务数据-游客画像模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class PortrayalService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;

    /**
     * 游客画像模拟
     *
     * @param minDate 最小时间区间
     * @param maxDate 最大时间区间
     * @param manMax 男性最大区间
     * @param manMin 男性最小区间
     * @param amountMax 人数最大区间
     * @param amountMin 人数最小区间
     * @param vcode
     * @return
     */
    public long savePortrayal(String minDate, String maxDate, int manMin, int manMax , int amountMin, int amountMax , String vcode) throws ParseException {

        int sum = 0;
        Map map = new HashMap();
        map.put("vcode", vcode);
        String sql = "DELETE FROM ussd_tourists_portrait WHERE VCODE = '" + vcode + "'";
        dataBusinessMapper.deleteData(sql);
        String headSql = " INSERT INTO ussd_tourists_portrait (TOTAL,MAN, WOMAN, AGEONE, AGETWO, AGETHREE, AGEFOUR, AGEFIVE, AGESIX, DATE_TIME, VCODE ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        List<String> dateList = DateUtil.getSubDateListByString(minDate, maxDate);
        for (String days : dateList) {
            for(int i = 0; i < 13; i++) {
                String time = days + " 08:00:00";
                //男女比例
                Random random = new Random();
                int man = DataImitateUtil.getRandom(manMin, manMax);
                int woman = 100 - man;
                //当天总人数
                int totalNum = DataImitateUtil.getRandom(amountMin, amountMax);
                //每小时人数
                int hourNum = totalNum / 12;
                double dhourMan = hourNum * man / 100;
                String shourMan = String.valueOf(dhourMan);
                //每小时男女人数
                int ihourMan = new BigDecimal(shourMan).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
                int ihourWoman = hourNum - ihourMan;

                //获取各年龄分段比例及人数
                Map mapAge = DataImitateUtil.getPortrayalAgeScale();
                DecimalFormat decimalFormatZero=new DecimalFormat("0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                String ageOne = decimalFormatZero.format((Double)(hourNum * Double.parseDouble(mapAge.get("16岁以下") + "")));
                String ageTwo = decimalFormatZero.format((Double)(hourNum * Double.parseDouble(mapAge.get("16-25岁") + "")));
                String ageThree = decimalFormatZero.format((Double)(hourNum * Double.parseDouble(mapAge.get("26-35岁") + "")));
                String ageFour = decimalFormatZero.format((Double)(hourNum * Double.parseDouble(mapAge.get("36-45岁") + "")));
                String ageFive = decimalFormatZero.format((Double)(hourNum * Double.parseDouble(mapAge.get("46-55岁") + "")));
                String ageSix = decimalFormatZero.format((Double)(hourNum * Double.parseDouble(mapAge.get("55岁以上") + "")));

                insertSql.append("('");
                insertSql.append(hourNum);
                insertSql.append("', '");
                insertSql.append(ihourMan);
                insertSql.append("', '");
                insertSql.append(ihourWoman);
                insertSql.append("', '");
                insertSql.append(ageOne);
                insertSql.append("', '");
                insertSql.append(ageTwo);
                insertSql.append("', '");
                insertSql.append(ageThree);
                insertSql.append("', '");
                insertSql.append(ageFour);
                insertSql.append("', '");
                insertSql.append(ageFive);
                insertSql.append("', '");
                insertSql.append(ageSix);
                insertSql.append("', '");
                insertSql.append(DataImitateUtil.getDateAddSecond(time,3600 * i ));
                insertSql.append("', '");
                insertSql.append(vcode);
                insertSql.append("')");
                insertSql.append(",");
                sum++;
            }
        }
        dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        return sum;
    }
}
