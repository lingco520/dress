package com.daqsoft.mapper.client.timelyparking;

import com.daqsoft.vo.client.madeVoBean.JqTimelyParkingWidget;
import com.daqsoft.vo.client.madeVoBean.JqTimely_Parking_TendWidget;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 停车场使用
 *
 * @author lianch.
 * @date 2017-06-07 14:37.
 * @update zf 20170822
 */
@Repository
public interface JqTimelyParkingWidgetDao {
    List<JqTimely_Parking_TendWidget> getJqTimelyParking(String vcode);

    /**
     * @update zf 20170822
     * @param map
     * @return
     */
    List<JqTimelyParkingWidget> getJqTimelyPercent(Map map);

    /**
     * @update zf 20170822
     * @param map
     * @return
     */
    List<JqTimelyParkingWidget> getJqTimelyBetween(Map map);

    /***
     * @update zf 20170822
     * @param map
     * @return
     */
    List<JqTimelyParkingWidget> getJqTimelyByDay(Map map);
    /***
     * @update zf 20170822
     * @param map
     * @return
     */
    List<JqTimelyParkingWidget> getJqTimelyByMonth(Map map);
    /***
     * @update zf 20170822
     * @param map
     * @return
     */
    List<JqTimelyParkingWidget> getJqTimelyByYear(Map map);

    List<JqTimelyParkingWidget> isfindTimelyParkings(Map map);

    List<JqTimelyParkingWidget> findTimelyParkings(Map map);

    void insertTimelyParking(Map map);


    /***
     * 线程查询基础数据   天 停车场  zf
     * @param map
     * @return
     */
    List<JqTimelyParkingWidget> callGetJqTimelyByDay(Map map);
}
