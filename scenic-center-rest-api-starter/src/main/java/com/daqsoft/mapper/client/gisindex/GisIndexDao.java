package com.daqsoft.mapper.client.gisindex;

import com.daqsoft.vo.client.madeVoBean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * gis地图
 */
@Repository
public interface GisIndexDao {
    /**
     * 获取gis停车场
     * @param vcode
     * @return
     */
    List<GisLocationVo> getParking(String vcode);


    /**
     * 停车场已用剩余数据接口
     *
     * @param map
     * @return
     */
    List<Map> getParkingData(Map map);

    /**
     * 单个停车场信息
     *
     * @param map
     * @return
     */
    Map getOnePark(Map map);

    /**
     * 获取gis厕所
     * @param vcode
     * @return
     */
    List<GisLocationVo> getToilet(String vcode);

    /**
     * 获取gis报警亭
     * @param vcode
     * @return
     */
    List<GisLocationVo> getPolice(String vcode);

    /**
     * 获取gis医疗点
     * @param vcode
     * @return
     */
    List<GisLocationVo> getMedical(String vcode);


    /**
     * 获取gis监控点
     * @param vcode
     * @return
     */
    List<GisLocationVo> getMonitor(String vcode);

    /**
     * 获取gis监控点（条数限制）
     * @param map
     * @return
     */
    List<GisLocationVo> getMonitorSize(Map map);

    /**
     * 获取gis景点
     * @param vcode
     * @return
     */
    List<GisSourceGpsVo> getScenic(String vcode);

    /**
     * 获取gis寺庙
     * @param vcode
     * @return
     */
    List<GisLocationVo> getTemple(String vcode);

    /**
     * 获取gis越界报警
     * @param vcode
     * @return
     */
    List<GisBoundsPoliceVo> getBoundsPolice(String vcode);

    /**
     * 获取gis越界报警为查看数量
     *
     * @param vcode
     * @return
     */
    String getWaitBoundsPolice(String vcode);

    /**
     * 更新gis越界报警查看状态
     *
     * @param map
     * @return
     */
    Integer updateBoundsPolice(Map map);


    /**
     * 获取景区资源的统计
     * @param vcode
     * @return
     */
    List<GisSourcestatiVo> getSourceStatistics(String vcode);

    /**
     * 获取景区资源的统计
     * @param map
     * @return
     */
    List<GisSourceDetailVo> getSourceDetail(Map map);

    /**
     * 获取景区资源的统计查询方法
     *
     * @param map
     * @return
     */
    List<GisSourceDetailVo> getSourceDetailSearch(Map map);

    /**
     * 获取景区资源Gis地图定位
     *
     * @param map
     * @return
     */
    List<GisSourceGpsVo> getSourceGPS(Map map);

    /**
     * 获取景区资源Gis地图酒店饭店
     *
     * @param vcode
     * @return
     */
    List<GisHotelVo> getGisHotel(String vcode);

    /**
     * 获取景区资源Gis餐饮场所
     *
     * @param vcode
     * @return
     */
    List<GisHotelVo> getDiningPlace(String vcode);

    /**
     * 获取景区资源Gis娱乐场所
     *
     * @param vcode
     * @return
     */
    List<GisHotelVo> getEntertainment(String vcode);

    /**
     * 获取景区资源Gis购物场所
     *
     * @param vcode
     * @return
     */
    List<GisHotelVo> getShop(String vcode);

    /**
     * 查询监控详情
     * @param vcode
     * @param id
     * @return
     */
    MonitorVo getMonitorById(@Param("vcode") String vcode, @Param("id") Long id);

    /**
     * GIS地图资源模块选择
     *
     * @param map
     * @return
     */
//    List<Map> getGISResources(String vcode);
    List<Map> getGISResources(Map map);

    /**
     * 华清宫运行监测地图重点监控
     *
     * @param vcode
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
     * 通过经纬度查询数据
     * @param vcode
     * @param type
     * @param longkey
     * @param latkey
     * @return
     */
    List<GisSourceGpsVo> getScenicByType(@Param("vcode") String vcode, @Param("type") String type, @Param("longkey") String longkey, @Param("latkey") String latkey);

    List<GisLocationVo> getParkingByType(@Param("vcode") String vcode, @Param("type") String type, @Param("longkey") String longkey, @Param("latkey") String latkey);

    List<GisLocationVo> getMonitorByType(@Param("vcode") String vcode, @Param("type") String type, @Param("longkey") String longkey, @Param("latkey") String latkey);

    List<GisLocationVo> getMonitorSizeByType(Map map);

    List<GisHotelVo> getEntertainmentByType(@Param("vcode") String vcode, @Param("type") String type, @Param("longkey") String longkey, @Param("latkey") String latkey);

    List<GisHotelVo> getGisHotelByType(@Param("vcode") String vcode, @Param("type") String type, @Param("longkey") String longkey, @Param("latkey") String latkey);

    List<GisLocationVo> getToiletByType(@Param("vcode") String vcode, @Param("type") String type, @Param("longkey") String longkey, @Param("latkey") String latkey);

    /**
     * 查询地图类型列表
     * @param type 字典表中type字段存的值，地图固定值是  map_type
     * @return
     */
    List<String> getMapType(@Param("type") String type);

    /**
     * 查询每个类型的地图的字段名称，与每个业务数据表的地图的经纬度与之对应
     * @param type
     * @param pkey
     * @return
     */
    List<String> getMapKeyList(@Param("type") String type, @Param("pkey") String pkey);

    List<GisLocationVo> getMonitorHQGByType(@Param("vcode") String vcode, @Param("type") String type, @Param("longkey") String longkey, @Param("latkey") String latkey);

    List<GisLocationVo> getFireproofByType(@Param("vcode") String vcode, @Param("type") String type, @Param("longkey") String longkey, @Param("latkey") String latkey);

    List<GisFireVo> getFireControlByType(@Param("vcode") String vcode, @Param("type") String type, @Param("longkey") String longkey, @Param("latkey") String latkey);

    /**
     * 通过vcode查询景区景点列表
     * @param vcode
     * @return
     */
    List<GisSourceGpsVo> getScenicListByVcode(String vcode);

    /**
     * 通过vcode查询景区监控
     * @param vcode
     * @return
     */
    List<GisLocationVo> getMonitorListByVcode(String vcode);

    /**
     * 查询停车场
     * @param vcode
     * @return
     */
    List<GisLocationVo> getParkingListByVcode(String vcode);

    /**
     * 旅行社列表
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
     * 查询旅游厕所列表
     * @param vcode
     * @return
     */
    List<GisLocationVo> getToiletListByVcode(String vcode);
}
