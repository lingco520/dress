package com.daqsoft.mapper.client.otadata;

import com.daqsoft.vo.client.madeVoBean.OtaDataSealVo;
import com.daqsoft.vo.client.madeVoBean.OtaDataTendVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/18.
 * @Version: V3.0.0.
 * @describe:监控数据
 */

@Repository
public interface OtaDataDao {
   List<OtaDataSealVo> getOtaData(Map map);

   /**
    * 年时间段ota数据查询
    * @param map
    * @return
    */
   List<OtaDataSealVo> getOtaDataSlotYear(Map map);

   /**
    * 月时间段ota数据查询
    * @param map
    * @return
    */
   List<OtaDataSealVo> getOtaDataSlotMonth(Map map);

   /**
    * 月时间段ota数据查询
    * @param map
    * @return
    */
   List<OtaDataSealVo> getOtaDataSlotDay(Map map);

   /**
    * 时时间段ota数据查询
    * @param map
    * @return
    */
   List<OtaDataSealVo> getOtaDataSlotTime(Map map);

   /**
    * 查询景区ota占比，各占比数量
    * @param map
    * @return
    */
   List<OtaDataTendVo> getOtaDataList(Map map);


   /**
    * OTA当天总数
    *
    * @param map
    * @return
     */
   int getOtaSumData(Map map);

    /**
     * 神龙峡OTA，定制查询
     *
     * @param map
     * @return
     */
   Map getslxOtaDataList(Map map);
}
