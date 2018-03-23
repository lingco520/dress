package com.daqsoft.service.client.gisbasedService;

import com.daqsoft.vo.client.madeVoBean.*;
import com.daqsoft.vo.client.mysqlBean.Dining;

import java.util.List;
import java.util.Map;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:主页gis service
 */


public interface GisBasicService {
    //获取gis地图
    List<SysConfigTendVo> getSysConfig(String vcode);
    List<Map> getGisSysConfig(String vcode);

    //获取gis停车场
    List<GisLocationVo> getParking(String vcode);

    //停车场已用剩余数据接口
    Map getParkingData(Map map);

    //获取gis厕所
    List<GisLocationVo> getToilet(String vcode);

    //获取gis报警亭
    List<GisLocationVo> getPolice(String vcode);

    //获取gis医疗点
    List<GisLocationVo> getMedical(String vcode);

    //获取gis监控
    List<GisLocationVo> getMonitor(String vcode, String size);

    //获取gis景点
    List<GisSourceGpsVo> getScenic(String vcode);

    //获取gis寺庙
    List<GisLocationVo> getTemple(String vcode);

    //获取景区资源gis娱乐场所
    List<GisHotelVo> getEntertainment(String vcode);

    //获取景区资源gis购物场所
    List<GisHotelVo> getShop(String vcode);

    //获取景区资源gis地图酒店
    List<GisHotelVo> getGisHotel(String vcode);

    //获取景区旅游资源统计
    List<GisSourcestatiVo> getSourceStatistics(String vcode);

    //景点舒适度（咂机统计）
    List<jQScenicSpotZJ> getScenicSpotZJMeth(Map map);

    //景点舒适度（咂机统计）统计人数
    List<jQScenicSpotZJ> getScenicSpotBTPeople(Map map);

    //    景点最大承载量
    String getComfortMaxCount(Map map);

    //景点舒适度（统计景点数量与最大承载量）
    List<jQScenicSpotZJList> getScenicSpotZJCountMeth(Map map);

    //统计景区景点数量
    List<jQScenicSpotZJ> getScenicSum(Map map);
    // 查询景区停车场使用情况
    Map getParkingUseList(String vcode, String date, Long id);
    // 查询监控详情
    MonitorVo getMonitorById(String vcode, Long id);

    /**
     * 获取停车场使用情况，返回具体数量
     * @param map
     * @return
     */
    Map getParkingUseInfo(Map map);

    /**
     * 获取超图配置基本信息
     * @param vcode
     * @return
     */
    List<SuperMapConfig> getSuperMapConfig(Map<String, Object> vcode);

    /**
     * 获取餐饮场所
     * @param vcode
     * @return
     */
    List<Dining> getDining(String vcode);

    /**
     * GIS地图资源模块选择
     *
     * @param vcode
     * @return
     */
//    List<Map> getGISResources(String vcode);
    List<Map> getGISResources(String vcode, String type);

    /**
     * 华清宫运行监测地图巡检人员定位
     *
     * @return
     */
    List<Map> getGPSLocationHQG();


    /**
     * 华清宫运行监测地图报警事件
     *
     * @return
     */
    Map getGPSEventHQG(int pageSize, int page);

    /**
     * 华清宫运行监测地图报警装置位置
     *
     * @return
     */
    List<Map> getInfostakeHQG();


    /**
     * 华清宫运行监测地图重点监控
     *
     * @return
     */
    List<GisLocationVo> getMonitorHQG(String vcode);

    /**
     * 防火监控
     *
     * @param vcode
     * @return
     */
    List<GisLocationVo> getFireproof(String vcode);

    /**
     * 消防设备
     * @param vcode
     * @return
     */
    List<GisFireVo> getFireControl(String vcode);

    /**
     * 通过经纬度和地图类型查询数据
     * @param vcode
     * @param type
     * @param longkey
     * @param latkey
     * @return
     */
    List<GisSourceGpsVo> getScenicByType(String vcode, String type, String longkey, String latkey);

    List<GisLocationVo> getParkingByType(String vcode, String type, String longkey, String latkey);

    List<GisLocationVo> getMonitorByType(String vcode, String size, String type, String longkey, String latkey);

    List<Dining> getDiningByType(String vcode, String type, String longkey, String latkey);

    List<GisHotelVo> getEntertainmentByType(String vcode, String type, String longkey, String latkey);

    List<GisHotelVo> getGisHotelByType(String vcode, String type, String longkey, String latkey);

    List<GisLocationVo> getToiletByType(String vcode, String type, String longkey, String latkey);

    /**
     * 查询地图类型（字典表获取 sys_dict）
     * @return
     */
    List<Map> getMapType();

    List<String> getLongLatByMapType(String type);

    List<GisLocationVo> getMonitorHQGByType(String vcode, String type, String longkey, String latkey);

    List<GisLocationVo> getFireproofByType(String vcode, String type, String longkey, String latkey);

    List<GisFireVo> getFireControlByType(String vcode, String type, String longkey, String latkey);

    /**
     * 通过景区编码查询景点列表
     * @param vcode
     * @return
     */
    List<GisSourceGpsVo> getScenicListByVcode(String vcode);

    /**
     * 查询监控列表
     * @param vcode
     * @return
     */
    List<GisLocationVo> getMonitorListByVcode(String vcode);

    /**
     * 查询停车场列表
     * @param vcode
     * @return
     */
    List<GisLocationVo> getParkingListByVcode(String vcode);

    /**
     * 查询旅行社列表
     * @param vcode
     * @return
     */
    List<Map> getTagencyListByVcode(String vcode);

    /**
     * 查询宾馆酒店列表
     * @param vcode
     * @return
     */
    List<GisHotelVo> getGisHotelListByVcode(String vcode);

    /**
     * 餐饮场所列表
     * @param vcode
     * @return
     */
    List<Dining> getDiningListByVcode(String vcode);

    /**
     * 通过景区编码查询旅游厕所列表
     * @param vcode
     * @return
     */
    List<GisLocationVo> getToiletListByVcode(String vcode);

}
