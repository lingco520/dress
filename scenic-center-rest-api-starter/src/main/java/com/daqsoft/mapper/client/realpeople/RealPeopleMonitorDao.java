/**
 * @Copyright: <a htef="http://www.daqsoft.com">成都中科大旗软件有限公司Copyright © 2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.mapper.client.realpeople;

import com.daqsoft.vo.client.madeVoBean.SpotsWifiBeeComfortVo;
import com.daqsoft.vo.client.mysqlBean.JqScenicTimelyPopulation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Title: 实时客流监测
 * @Author: yangmei
 * @Date: 2017/08/02 13:51
 * @Description: TODO
 * @Comment：主要用于景区实时客流监测，或者景点，同时会结合wifi来统计每个景点的实时负荷度
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Repository
public interface RealPeopleMonitorDao {

    /**
     * 景区实时客流监测，同时会结合wifi来统计每个景点的实时负荷度
     *
     * @param vcode
     * @return
     */
    List<JqScenicTimelyPopulation> getRealpeopleMonitorHyj(String vcode);

    /**
     * 景区景点实时客流监测，通过小蜜蜂wifi数据分析
     * @param paramMap
     * @return
     */
    List<SpotsWifiBeeComfortVo> getRealpeopleMonitorBee(Map<String, Object> paramMap);
}
