package com.daqsoft.mapper.client.jqmonitor;


import com.daqsoft.vo.client.mysqlBean.JqMonitorVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/18.
 * @Version: V3.0.0.
 * @describe:监控数据
 */

@Repository
public interface JqMonitorDao {
   List<JqMonitorVo> getJqMonitor(String vcode);
   List<JqMonitorVo> getJqMonitorById(Map map);

   /**
    * 获取监控数据 存储
    *
    * @param vcode
    * @return
    */
   List<Map> getMonitorList(String vcode);
}
