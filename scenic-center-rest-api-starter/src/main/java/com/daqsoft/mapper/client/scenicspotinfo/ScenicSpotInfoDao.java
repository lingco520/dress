package com.daqsoft.mapper.client.scenicspotinfo;


import com.daqsoft.vo.client.madeVoBean.ComplantsVo;
import com.daqsoft.vo.client.madeVoBean.ParkingNewVo;
import com.daqsoft.vo.client.madeVoBean.ParkingVo;
import com.daqsoft.vo.client.madeVoBean.SpotsWifiVo;
import com.daqsoft.vo.client.mysqlBean.ArticleActivity;
import com.daqsoft.vo.client.mysqlBean.PlanMountain;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yangkang .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:景区资讯dao
 */
@Repository
public interface ScenicSpotInfoDao {

    List<PlanMountain> getInformationss(String vcode);


    List<ArticleActivity> getAll(Map map);


    List<ArticleActivity> getType(Map map);

    ArticleActivity getDetails(Map map);

    void putScans(Map map);

    Long getInformationCounts(Map<String, Object> parMap);

    Long getInformationCountsByYear(Map<String, Object> parMap);

    List<SpotsWifiVo> getSpotsWifiList(Map<String, Object> parMap);

    List<String> getScenicNames(String vcode);

    List<ComplantsVo> getScenicComplaintsAll(Map map);

    /**
     * 获取各景点的最大承载量
     */
    List<Map<String,Object>> getScenicMaxquantity(String vcode);

    List<Map> getScenicNamesList(String vcode);

    ParkingVo getParkingInfo(Map parMap);

    ParkingNewVo getParkingNewInfo(Map map);

    List<Map<String, Object>> getScenicMaxCounts(String vcode);

    Integer getParkingCount(Map parMap);



}
