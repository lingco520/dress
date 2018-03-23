package com.daqsoft.mapper.client.carflow;


import com.daqsoft.vo.client.madeVoBean.JqTvpmCheckoutWidget;
import com.daqsoft.vo.client.madeVoBean.JqTvpm_Percent_TendWidget;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: zf.
 * @Date: Created in 2017/07/27.
 * @Version: V3.0.0.
 * @describe: 停车场dao
 * @update zf 20170822
 */
@Repository
public interface JqTvpmCheckoutWidgetDao {

    /***
     * @update zf 20170822
     * @param map
     * @return
     */
    List<JqTvpmCheckoutWidget> getJqTvpmCheckout(Map map);

    List<JqTvpmCheckoutWidget> getJqTvpmBetween(Map map);

    /***
     * @update zf 20170822
     * @param map
     * @return
     */
    List<JqTvpm_Percent_TendWidget> getJqTvpmSum(Map map);

    List<JqTvpm_Percent_TendWidget> getJqTvpmSumBtweenSum(Map map);

    Integer isFindCheckOut(Map map);

    List<JqTvpmCheckoutWidget> findCheckOutData(Map map);

    void insertCheckOut(Map map);

    /***
     * 基础查询天的方法
     * @param map
     * @return
     */
    List<JqTvpm_Percent_TendWidget> getJqTvpmSumByDay(Map map);


    /***
     * 分组车牌基础查询天的方法
     * @param map
     * @return
     */
    List<JqTvpmCheckoutWidget> getJqTvpmCheckoutByDay(Map map);


}
