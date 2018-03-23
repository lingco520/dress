package com.daqsoft.mapper.common;

import com.daqsoft.framework.entity.SysRegion;
import com.daqsoft.vo.MediaTypeVo;
import com.daqsoft.vo.ScenicSpotsVo;
import com.daqsoft.vo.SysDictVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface DictByTypeMapper {

    /**
     * 查询数据列表
     *
     * @param type
     * @return
     */
    List<SysDictVo> getDictByType(String type);

    /**
     * 查询账号数据
     *
     * @param type
     * @return
     */
    List<SysDictVo> getAccount(String type);
    /**
     * 查询部门数据
     *
     * @param type
     * @return
     */
    List<SysDictVo> getDepartment(String type);

    /**
     * 查询该vcode下启用的景点
     *
     * @param vcode
     * @return
     */
    List<ScenicSpotsVo> getScenicByVcode(String vcode);

    /**
     * 查询多媒体类型
     *
     * @param map
     * @return
     */
    List<MediaTypeVo> getMediaType(Map map);


    /**
     * 查询两个类型的值的集合
     * @param type1
     * @param type2
     * @return
     */
    List<SysDictVo> getDictListByTwo(@Param("type1") String type1, @Param("type2") String type2);
    /**
     * 查询一个类型的值的集合
     * @param type
     * @return
     */
    List<SysDictVo> getDictListByOne(@Param("type") String type);

    /**
     * 通过region获取地区region对象数据
     * @param region
     * @return
     */
    SysRegion getSysRegionByRegion(String region);
}