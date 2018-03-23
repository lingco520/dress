package com.daqsoft.mapper.common;

/**
 * @author tanggm
 * @description 公共mapper,其它mapper继承此mapper; 此mapper提供公共基础的CURD操作,如果方法不满足时，需要在自定义的mapper中编写
 * @date 2018-01-22 11:50
 */
public interface BaseCommonMapper<T> {
    /**
     * 通过id删除数据
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id) throws Exception;

    /**
     * 新增操作
     * @param record
     * @return
     */
    int insert(T record) throws Exception;

    /**
     * 通过id查询数据
     * @param id
     * @return
     */
    T selectByPrimaryKey(Long id) throws Exception;

    /**
     * 更新操作
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record) throws Exception;
}
