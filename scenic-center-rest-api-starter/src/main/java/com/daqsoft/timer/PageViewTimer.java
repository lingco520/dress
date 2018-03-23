/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.timer;

import com.daqsoft.config.MyProps;
import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.comprehensiveService.CommonResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

/**
 * @Title:
 * @Author: AHBird
 * @Date: 2017/08/22 17:42
 * @Description: TODO
 * @Comment：
 * @see
 * @Version:V3.0
 * 景区官网实时数据推送缓存
 * @since JDK 1.8
 * @Warning:
 */
//@Component
public class PageViewTimer {
    @Autowired
    private MyProps props;

    @Autowired
    private CommonResourceService commonResourceService;

    /**
     * 拉取云台山流量统计缓存数据
     * 2个小时获取一次。
     * open为yes代表覆盖缓存
     * 默认获取当前年份的
     * @throws Exception
     */
    @SysLog
    @Async
    @Scheduled(initialDelay = 600000L , fixedRate = 7200000L)
    public  void bigCarFlowNumbers() throws Exception{
        String open = "yes";
        Map<String,String> map=props.getBigPassenger();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            String vcode = entry.getValue();
            System.out.print("获取流量统计数据"+"---"+entry.getKey());
            commonResourceService.getStatsticsCount(vcode,open);
        }
    }
}