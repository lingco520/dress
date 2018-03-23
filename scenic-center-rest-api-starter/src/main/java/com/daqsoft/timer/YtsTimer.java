/**
 * @Copyright: <a htef="http://www.daqsoft.com">成都中科大旗软件有限公司Copyright © 2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.timer;

import com.daqsoft.config.SysLog;
import com.daqsoft.service.client.passengerFlowService.PassengerService;
import com.daqsoft.utils.client.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;


/**
 * @Title: 云台山定制微信模拟数据推送
 * @Author: liuruidong
 * @Date: 2017/10/16 18:15
 * @Comment：云台山有4个微件取的移动数据，考虑移动网站经常改版情况
 * 有时候数据没有，故开发模拟数据推送，当有真实数据时，接口取大数据那边对接的
 * 移动数据，当接口无真实数据时，接口取模拟数据
 * @Version: V3.0.0
 * @since JDK 1.8
 */
//@Component
public class YtsTimer {

    @Autowired
    private PassengerService passengerService;



    /**
     * 景区大数据-通过运营商获取的数据
     * 初始化加载一次当前的年份时间点的数据
     * 定制微件
     */
    @SysLog
    @Async
    @Scheduled(initialDelay = 60000L , fixedRate = 3650000L)
    public  void bigCarFlowNumbers() throws Exception{
        //早7到晚8执行
        boolean isTure = DateUtil.getHoursByMinite();
        if(isTure==true) {
            String year = DateUtil.getCurYearStr();
            System.out.print(String.format("移动大数据获取--云台山定制"));
            passengerService.getBigPassengerAll("d5034caae86f1081e0e6ae5337e48e9f", year, "yes");//云台山定制的
        }
    }

    /**
     * 景区日客流游客统计
     * 小时段
     * 每小时执行一次
     * 启动后2分钟开始执行
     * @throws Exception
     */
    @SysLog
    @Async
    @Scheduled(initialDelay = 900000L , fixedRate = 3600000L)
    public void savePassengerByHoursYts() throws Exception{
        Map map = new HashMap();
        //获取当前小时段
        String thisHours = DateUtil.getCurSimpleHourStr();
        if(!"00".equals(thisHours) && !"0".equals(thisHours)){
            //获取当前日期
            String date  = DateUtil.getCurDateStr();
            //模拟当前小时数据
            String  time = (Integer.parseInt(thisHours)-1)+"时-"+thisHours+"时";
            //随机获取一个100-300以内的数
            int ran = (int)(100*Math.random()+200);
            map.put("currentTime",time);
            map.put("currentDate",date);
            map.put("newTotal",Integer.parseInt(thisHours)*ran);
            map.put("leaveTotal",(int)Math.floor((Integer.parseInt(thisHours)*ran)/1.5));
            map.put("vcode", "d5034caae86f1081e0e6ae5337e48e9f");
            //插入数据库
            passengerService.saveYtsPassengerByDay(map);
        }
    }


    /**
     * 景区日客流趋势
     * 按日段
     * 每天，每天下午6点执行当天数据
     * 启动后1分钟开始执行
     * @throws Exception
     */
    @SysLog
    @Async
    @Scheduled(cron = "0 0 18 * * ?")
    //@Scheduled(initialDelay = 20000L , fixedRate = 3600000L)
    public void savePassengerByDayYts() throws Exception{
        Map map = new HashMap();
        //获取当天时间
        String thisDay = DateUtil.getCurDateStr();
            //随机数
            map.put("newadd",(int)(5000*Math.random()+12045)+"");
            map.put("resident",(int)(1101*Math.random()+25252)+"");
            map.put("leave",(int)(4899*Math.random()+6432)+"");
            map.put("day",thisDay);
            map.put("vcode", "d5034caae86f1081e0e6ae5337e48e9f");
            //插入数据库
            passengerService.savePassengerByDayYts(map);
    }


    /**
     * 景区日客流驻留时长分析（按日）
     * 按日段
     * 每天，每天下午6点执行当天数据
     * 启动后1分钟开始执行
     * @throws Exception
     */
    @SysLog
    @Async
    @Scheduled(cron = "0 0 18 * * ?")
    //@Scheduled(initialDelay = 20000L , fixedRate = 3600000L)
    public void savePassengerByTimeYts() throws Exception{
        Map map = new HashMap();
        //获取当天时间
        String thisDay = DateUtil.getCurDateStr();
        //随机数
        map.put("newadd",(int)(8230*Math.random()+16345)+"");
        int one = (int)(18*Math.random()+24);
        int two = (int)(11*Math.random()+88);
        map.put("resident",one+"."+two);
        map.put("time",thisDay);
        map.put("vcode", "d5034caae86f1081e0e6ae5337e48e9f");
        //插入数据库
        passengerService.savePassengerByTimeYts(map);
    }


    /**
     * 景区日客流驻留时长分析（按小时范围段）
     * 按日段
     * 每天，每天下午6点执行当天数据
     * 启动后1分钟开始执行
     * @throws Exception
     */
    @SysLog
    @Async
    @Scheduled(cron = "0 0 18 * * ?")
    public void getPeopleRemainByYts() throws Exception{
        //获取当天时间
        String thisDay = DateUtil.getCurDateStr();
        //准备时间段
        String[] hours = {"0-1小时","1-2小时","2-6小时","6-24小时","24-48小时","48小时以上"};
        //随机数据准备
        int[] hoursData = new int[6];
        hoursData[0] = (int)(150*Math.random()+100);
        hoursData[1] = (int)(250*Math.random()+150);
        hoursData[2] = (int)(350*Math.random()+850);
        hoursData[3] = (int)(1000*Math.random()+600);
        hoursData[4] = (int)(1600*Math.random()+3400);
        hoursData[5] = (int)(500*Math.random()+750);
        for (int i = 0; i <hours.length ; i++) {
            Map map = new HashMap();
            map.put("hoursTime",hours[i]);
            map.put("num",hoursData[i]);
            map.put("currentDate",thisDay);
            map.put("vcode", "d5034caae86f1081e0e6ae5337e48e9f");
            //插入数据库
            passengerService.savePeopleRemainByYts(map);
        }
    }
}
