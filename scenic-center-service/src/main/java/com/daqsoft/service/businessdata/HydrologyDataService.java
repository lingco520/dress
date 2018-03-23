/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.businessdata;

import com.daqsoft.entity.businessData.BusinessData;
import com.daqsoft.entity.scenicSpots.ResScenicSpots;
import com.daqsoft.mapper.resource.DataBusinessMapper;
import com.daqsoft.mapper.resource.ResScenicSpotsMapper;
import com.daqsoft.utils.DataImitateUtil;
import com.daqsoft.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: HydrologyDataService
 * @Author: superziy
 * @Date: 2018/02/09 17:15
 * @Description: TODO 业务数据-水文数据模拟
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class HydrologyDataService {
    @Autowired
    private DataBusinessMapper dataBusinessMapper;
    @Autowired
    private ResScenicSpotsMapper scenicSpotsMapper;

    /**
     * 水文数据模拟
     *
     * @param dto 水文数据
     * @return
     */
    public long saveHydrology(BusinessData dto, String vcode) throws Exception{

        Date minDate = DataImitateUtil.getYmdDate(dto.getStartDate());
        Date maxDate = DataImitateUtil.getYmdDate(dto.getEndDate());
        Map map = new HashMap();
        map.put("vcode", vcode);
        String sql = "DELETE FROM ussd_hydrology_data WHERE VCODE = '"
                + vcode + "' AND datetime BETWEEN '"
                + DataImitateUtil.getYmdStr(minDate) + " 00:00:00' AND '"
                + DataImitateUtil.getYmdStr(maxDate) + " 23:59:59' ";
        dataBusinessMapper.deleteData(sql);

        String headSql = "INSERT INTO ussd_hydrology_data " +
                "(NAME,TEMPERATURE,FLOW,SPEED,DEPTH,WIDTH, DATETIME,vcode ) VALUES";
        StringBuffer insertSql = new StringBuffer(headSql);
        //时间区间内的时间
        List<String> dateList = DateUtil.getSubDateList(minDate, maxDate);
        String startHMS = "00:00:00";//24小时模式
        Integer sum = 0;
        int maxInsert = 6000;
        int k = 0;
        List<ResScenicSpots> list = scenicSpotsMapper.selectList(map);
        for (String date : dateList) {
            for (int i = 0; i < 24; i++) {
                //查询景区的景点
                String rainTime = DateUtil.getHourTime(i, DateUtil.getDate(date + " " + startHMS));
                for (ResScenicSpots scenic : list) {
                    //当天的水温随机数量
                    int temperature = DataImitateUtil.getRandom(dto.getTemperatureMinNum(), dto.getTemperatureMaxNum());
                    //当天的流量随机数量
                    int flow = DataImitateUtil.getRandom(dto.getFlowMinNum(), dto.getFlowMaxNum());
                    //当天的流速随机数量
                    int speed = DataImitateUtil.getRandom(dto.getSpeedMinNum(), dto.getSpeedMaxNum());
                    //当天的水深随机数量
                    int depth = DataImitateUtil.getRandom(dto.getDepthMinNum(), dto.getDepthMaxNum());
                    //当天的水宽随机数量
                    int width = DataImitateUtil.getRandom(dto.getWidthMinNum(), dto.getWidthMaxNum());
                    insertSql.append("('");
                    insertSql.append(scenic.getName());
                    insertSql.append("', '");
                    insertSql.append(temperature);
                    insertSql.append("', '");
                    insertSql.append(flow);
                    insertSql.append("', '");
                    insertSql.append(speed);
                    insertSql.append("', '");
                    insertSql.append(depth);
                    insertSql.append("', '");
                    insertSql.append(width);
                    insertSql.append("', '");
                    insertSql.append(rainTime);
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
        }
        if (k > 0) {
            //保存水文数据
            dataBusinessMapper.saveSimulationData(insertSql.substring(0, insertSql.length() - 1));
        }
        return sum;
    }
}
