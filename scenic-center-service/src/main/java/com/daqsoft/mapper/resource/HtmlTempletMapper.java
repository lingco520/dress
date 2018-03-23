package com.daqsoft.mapper.resource;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @Title: 业务数据模板存储Mapper
 * @Author: lyl
 * @Date: 2018/02/26 11:57
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Repository
public interface HtmlTempletMapper {

    /**
     * 存储模板操作
     *
     * @param map
     * @return
     */
    int insert(Map map) throws Exception;

    /**
     * 清空模板
     *
     * @return
     */
    int emptyTable();
}
