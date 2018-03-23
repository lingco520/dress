package com.daqsoft.mapper.resource;

import com.daqsoft.entity.htmlConfig.HtmlConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: 业务数据模块选择
 * @Author: lyl
 * @Date: 2018/02/26 17:08
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Repository
public interface HtmlConfigMapper {

    /**
     * 所有模块查询
     *
     * @return
     */
     List<Map> moduleSelect();

    /**
     * 当前景区查询
     *
     * @return
     */
    List<HtmlConfig> getOne(String vcode);

    /**
     * 插入模块配置
     *
     * @param map
     * @return
     */
    int insert(Map map);

    /**
     * 清楚数据
     *
     * @param vcode
     * @return
     */
    int emptyTable(String vcode);
}
