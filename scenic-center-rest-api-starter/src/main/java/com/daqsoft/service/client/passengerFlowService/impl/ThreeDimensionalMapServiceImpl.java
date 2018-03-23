package com.daqsoft.service.client.passengerFlowService.impl;

import com.daqsoft.mapper.client.threedimensionalmap.ThreeDimensionalMapDao;
import com.daqsoft.service.client.passengerFlowService.ThreeDimensionalMapService;
import com.daqsoft.vo.client.madeVoBean.MonitorPlayVo;
import com.daqsoft.vo.client.madeVoBean.ScenicSpotsHeatSealVo;
import com.daqsoft.vo.client.madeVoBean.SysConfigTendVo;
import com.daqsoft.vo.client.mysqlBean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: yangkang .
 * @Date: Created in 2017/5/23.
 * @Version: V3.0.0.
 * @describe:海螺沟三维地图逻辑实现
 */
@Service
public class ThreeDimensionalMapServiceImpl implements ThreeDimensionalMapService {
    @Autowired
    ThreeDimensionalMapDao threeDimensionalMapDao;

    /**
     * 查询监控
     *
     * @param vcode
     * @return
     */
    @Override
    public List<Monitor> getMonitoring(String vcode) {
        return threeDimensionalMapDao.getMonitoring(vcode);
    }

    /**
     * 查询停车场
     *
     * @param vcode
     * @return
     */
    @Override
    public List<ApiParking> getParking(String vcode) {
        return threeDimensionalMapDao.getParking(vcode);
    }

    /**
     * 查询景点
     *
     * @param vcode
     * @return
     */
    @Override
    public List<ApiScenicSpots> getScenicSpots(String vcode) {
        return threeDimensionalMapDao.getScenicSpots(vcode);
    }

    /**
     * 查询厕所
     *
     * @param vcode
     * @return
     */
    @Override
    public List<ApiTourismToilet> getToilet(String vcode) {
        return threeDimensionalMapDao.getToilet(vcode);
    }

    /**
     * 查询医疗点
     *
     * @param vcode
     * @return
     */
    @Override
    public List<ApiMedical> getApiMedical(String vcode) {
        return threeDimensionalMapDao.getApiMedical(vcode);
    }

    /**
     * 查询报警亭
     *
     * @param vcode
     * @return
     */
    @Override
    public List<ApiPolice> getPolice(String vcode) {
        return threeDimensionalMapDao.getPolice(vcode);
    }

    /**
     * 查询热电开关
     *
     * @param vcode
     * @return
     */
    @Override
    public List<ScenicSpotsHeatSealVo> getSpotsHeat(String vcode) {
        return threeDimensionalMapDao.getSpotsHeat(vcode);
    }


    /**
     * 查询越界报警
     *
     * @param vcode
     * @return
     */
    @Override
    public List<MoniterBoundsPolice> getMoniterBoundsPolice(String vcode) {
        return threeDimensionalMapDao.getMoniterBoundsPolice(vcode);
    }
    /**
     * 查询监控播放
     *
     * @param map
     * @return
     */
    @Override
    public List<MonitorPlayVo> getMonitorById(Map map) {
        return threeDimensionalMapDao.getMonitorById(map);
    }

    @Override
    public List<SysConfigTendVo> getSysConfig(String vcode) {
        return threeDimensionalMapDao.getSysConfig(vcode);
    }
}
