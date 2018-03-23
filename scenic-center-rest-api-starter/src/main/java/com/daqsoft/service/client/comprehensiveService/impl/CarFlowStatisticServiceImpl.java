package com.daqsoft.service.client.comprehensiveService.impl;

import com.daqsoft.commons.core.StringUtil;
import com.daqsoft.mapper.client.carflowstatistic.CarFlowStatisticDao;
import com.daqsoft.service.client.comprehensiveService.CarFlowStatisticService;
import com.daqsoft.vo.client.madeVoBean.CarFlowStatisticVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangkang.
 * @Date: Created in 2017/6/8.
 * @Version: V3.0.0.
 * @describe:车流量统计逻辑实现
 */
@Service
public class CarFlowStatisticServiceImpl implements CarFlowStatisticService {
    @Autowired
    CarFlowStatisticDao carFlowStatisticDao;

    @Override
    public List<CarFlowStatisticVo> getCarFlow(String vcode) {
        return carFlowStatisticDao.getCarFlow (vcode);
    }



    /**
     * 时段内车流量的统计 -- 分卡口
     * @param map
     * @return
     */
    @Override
    public List<CarFlowStatisticVo> getCarFlowCountByGate(Map map) {
        Map paramMap = new HashMap();
        String time = ""  ;

        paramMap.put("startTime",  map.get("time").toString()+ " 08:00:00");
        paramMap.put("endTime", map.get("time").toString() + " 17:59:59");
        paramMap.put("vcode",map.get("vcode"));

        List<CarFlowStatisticVo> carList = carFlowStatisticDao.getCarFlowCountByGate(paramMap);

        Map<String, Object> groupMap = new HashMap<>();

        List<CarFlowStatisticVo> dataList = null;
        List<CarFlowStatisticVo> cfDataList = new ArrayList<>();

        String flag = "true";
        String idFlag = "";
        String idStr = "";
        StringBuilder quantityStr = new StringBuilder();
        StringBuilder sourceStr = new StringBuilder();
        StringBuilder timesheetStr = new StringBuilder();




        //对资源进行合并为一组，利于前端对接
        for (int i = 0; i < carList.size(); i++) {
            String source = carList.get(i).getSource();

            if (StringUtil.isEmpty(idFlag)) {
                idFlag = source;
            } else if (source.equals(idFlag)) {
                idFlag = source;
            } else if (!source.equals(idFlag)){
                idFlag = source;

                CarFlowStatisticVo carFlowStatisticVo = new CarFlowStatisticVo();
                 carFlowStatisticVo.setSource(idStr);
                carFlowStatisticVo.setQuantity(quantityStr.substring(0, quantityStr.length() - 1));
                carFlowStatisticVo.setTimesheet(timesheetStr.substring(0, timesheetStr.length() - 1));

                dataList = new ArrayList<>();
                dataList.add(carFlowStatisticVo);
                groupMap.put(carFlowStatisticVo.getSource(),dataList);

                //不同的数据进行清空
                 quantityStr = new StringBuilder();
                // sourceStr = new StringBuilder();
                 timesheetStr = new StringBuilder();
            }


            quantityStr.append(carList.get(i).getQuantity() + ",");
            //sourceStr.append(carList.get(i).getSource() + ",");
            timesheetStr.append(carList.get(i).getTimesheet()+":00" + ",");

            idStr = source;
            //装载数据
            CarFlowStatisticVo carFlowStatisticVo = new CarFlowStatisticVo();
            carFlowStatisticVo.setSource(idStr);
            carFlowStatisticVo.setQuantity(quantityStr.substring(0, quantityStr.length() - 1));
            carFlowStatisticVo.setTimesheet(timesheetStr.substring(0, timesheetStr.length() - 1));

            dataList = new ArrayList<>();
            dataList.add(carFlowStatisticVo);
            groupMap.put(carFlowStatisticVo.getSource(),dataList);

        }

        //前端好对接，直接返回List
        for (String s : groupMap.keySet()) {
            CarFlowStatisticVo carFlowStatisticVo = new CarFlowStatisticVo();
            List<CarFlowStatisticVo> valList = (List<CarFlowStatisticVo>) groupMap.get(s);
            cfDataList.add(valList.get(0));
        }

        return cfDataList;
    }
}
