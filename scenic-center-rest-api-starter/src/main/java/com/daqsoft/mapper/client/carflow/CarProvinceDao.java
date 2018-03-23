package com.daqsoft.mapper.client.carflow;

import com.daqsoft.vo.client.madeVoBean.CarComeFromWidgetVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/***
 * 车流来源排行榜
 * @update zf 20170822
 */
@Repository
public interface CarProvinceDao {

    List<CarComeFromWidgetVo> getCarComeFromDay(Map map);//当日车辆来源排行

    /***
     * @update zf 20170822
     * @param map
     * @return
     */
    List<CarComeFromWidgetVo> getCarComeFromDay2Day(Map map);//选择日期车辆来源排行

    /***
     * @update zf 20170822
     * @param map
     * @return
     */
    List<CarComeFromWidgetVo> getCarComeFromQuarter(Map map);//季度城市车辆排行
    List<CarComeFromWidgetVo> getCarComeFromYear(Map map);//当年城市车辆来源
    List<CarComeFromWidgetVo> getCarComeFromYear2Year(Map map);//选择年城市车辆来源

    /***
     * @update zf 20170822
     * @param map
     * @return
     */
    List<CarComeFromWidgetVo> getCarComeFromMonth(Map map);//当月城市车辆来源

}
