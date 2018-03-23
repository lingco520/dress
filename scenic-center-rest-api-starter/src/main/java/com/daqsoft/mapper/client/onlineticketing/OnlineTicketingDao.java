package com.daqsoft.mapper.client.onlineticketing;

import com.daqsoft.vo.client.mysqlBean.OtaDate;
import com.daqsoft.vo.client.mysqlBean.OtaTendVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yangkang .
 * @Date: Created in 2017/5/23.
 * @Version: V3.0.0.
 * @describe:网络票务dao
 */
@Repository
public interface OnlineTicketingDao {

    List<OtaDate> getOta(String vcode);

    /**
     * 通过OTA分析 分销商 订单数 销售金额信息
     * @param dataMap
     * @return
     */
    List<OtaDate> getOtaOrderNumPrice(Map<String, String> dataMap);

    /**
     * 查询OTA数据。。年维度查询12个月
     * @param paramMap
     * @return
     */
    List<OtaTendVo> getOnlineCountByMonth(Map<String, Object> paramMap);
}
