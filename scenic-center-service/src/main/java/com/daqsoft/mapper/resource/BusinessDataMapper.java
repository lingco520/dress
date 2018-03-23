package com.daqsoft.mapper.resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: 业务数据模拟
 * @Author: lyl
 * @Date: 2018/02/28 10:15
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Repository
public interface BusinessDataMapper {

    /**
     * 保存业务数据模拟配置值
     * @param sql
     */
    void saveBusinessData(@Param("sql") String sql);

    /**
     * 删除历史配置数据
     * @param vcode
     */
    void deleteBusinessData(String vcode);

    /**
     * 查询数据配置列表
     * @param vcode
     * @return
     */
    List<Map> getDataImitateList(String vcode);

    /**
     * 查询Html模板的Hkey列表
     * @return
     */
    List<String> getTemplateHkeyList();
    /**
     * 查询Html模板的Hkey列表
     * @param vcode
     * @return
     */
    List<String> getHkeyList(String vcode);
}
