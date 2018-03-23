/**
 * @Copyright: <a htef="http://www.daqsoft.com">成都中科大旗软件有限公司Copyright © 2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.client.comprehensiveService;


import com.daqsoft.vo.client.madeVoBean.JqScenic_ScenicLoadPeople_Tend;
import com.daqsoft.vo.client.madeVoBean.RealPeopleChoiceTimeVo;
import com.daqsoft.vo.client.madeVoBean.SpotsWifiBeeComfortVo;

import java.util.List;

/**
 * @Title: 实时客流监测
 * @Author: yangmei
 * @Date: 2017/08/02 11:48
 * @Description: TODO
 * @Comment：主要用于综合展示主页的实时客流监测等接口
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
public interface RealPeopleMonitorService {


    /**
     * 景区景点实时客流监测，这方法用于华蓥山
     *
     * @param vcode
     * @return
     * @Comment: 每个景点的实时人数都是通过jq_wifi_part这张表来查询的
     */
    List<JqScenic_ScenicLoadPeople_Tend> getRealpeopleMonitorHyj(String vcode);
    //实时人数按年统计
    List<RealPeopleChoiceTimeVo> getRealTimeMethYear(String vcode);

    //实时人数按月统计
    List<RealPeopleChoiceTimeVo> getRealTimeMethMonth(String vcode, String dateTime);

    //实时人数按天统计
    List<RealPeopleChoiceTimeVo> getRealTimeMethDay(String vcode, String dateTime);

    //实时人数按时统计
    List<RealPeopleChoiceTimeVo> getRealTimeMethTime(String vcode, String dateTime);

    /**
     * 景区景点实时客流监测，通过小蜜蜂wifi数据分析
     * @param vcode
     * @return
     */
    List<SpotsWifiBeeComfortVo> realpeopleMonitorBee(String vcode);
}
