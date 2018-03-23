package com.daqsoft.mapper.resource;

import com.daqsoft.entity.acommodation.ResAccommodation;

import java.util.List;
import java.util.Map;

public interface ResAccommodationMapper {

    /**
     * 查询列表数据
     *
     * @param map
     * @return
     */
    List<ResAccommodation> selectList(Map map);

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    int deleteByPrimaryKey(Long id) throws Exception;

    /**
     * 新增操作
     * @param record
     * @return
     */
    int insert(ResAccommodation record) throws Exception;

    /**
     * 通过id查询数据
     * @param id
     * @return
     */
    ResAccommodation selectByPrimaryKey(Long id) throws Exception;

    /**
     * 更新操作
     * @param record
     * @return
     */
    int updateByPrimaryKey(ResAccommodation record) throws Exception;
}