package com.daqsoft.mapper.client.threedimensionalmap;

import com.daqsoft.vo.client.madeVoBean.MonitorPlayVo;
import com.daqsoft.vo.client.madeVoBean.ScenicSpotsHeatSealVo;
import com.daqsoft.vo.client.madeVoBean.SuperMapConfig;
import com.daqsoft.vo.client.madeVoBean.SysConfigTendVo;
import com.daqsoft.vo.client.mysqlBean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yangkang .
 * @Date: Created in 2017/5/23.
 * @Version: V3.0.0.
 * @describe:三维地图dao
 */
@Repository
public interface ThreeDimensionalMapDao {

    List<Monitor> getMonitoring(String vcode);

    List<ApiParking> getParking(String vcode);

    List<ApiScenicSpots> getScenicSpots(String vcode);

    List<ApiTourismToilet> getToilet(String vcode);

    List<ApiMedical> getApiMedical(String vcode);

    List<ApiPolice> getPolice(String vcode);

    List<ScenicSpotsHeatSealVo> getSpotsHeat(String vcode);

    List<MoniterBoundsPolice> getMoniterBoundsPolice(String vcode);

    List<MonitorPlayVo> getMonitorById(Map map);

    List<SysConfigTendVo> getSysConfig(String vcode);
    List<Map> getGisSysConfig(String vcode);

    List<SuperMapConfig> getSuperMapConfig(Map<String, Object> paramMap);

    List<Dining> getDining(String vcode);

    List<Dining> getDiningByType(@Param("vcode") String vcode, @Param("type") String type, @Param("longkey") String longkey, @Param("latkey") String latkey);

    List<Dining> getDiningListByVcode(String vcode);

}
