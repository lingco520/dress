/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title: DataImitateUtil
 * @Author: tanggm
 * @Date: 2018/02/05 16:18
 * @Description: TODO 数据模拟专用工具类
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class DataImitateUtil {
    public static final SimpleDateFormat Y_FORMAT = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat YM_FORMAT = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat YMD_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat YMDHMS_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat HM_FORMAT = new SimpleDateFormat("HH:mm");
    /**
     * 获取当前时间
     * @return
     */
    public static String getNowDateStr(){
        try {
            return YMDHMS_FORMAT.format(new Date());
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取当前年
     * @return
     */
    public static String getNowDateYearStr(){
        try {
            return Y_FORMAT.format(new Date());
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取年月日时分秒
     * @param ymdHmsStr 日期字符串
     * @return
     */
    public static String getYmdHmsStr(String ymdHmsStr){
        try {
            Date date = YMDHMS_FORMAT.parse(ymdHmsStr);
            return YMDHMS_FORMAT.format(date);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取年月日时分秒
     * @param date 日期
     * @return
     */
    public static String getYmdHmsStr(Date date){
        try {
            return YMDHMS_FORMAT.format(date);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取年月日
     * @param ymdStr 日期字符串
     * @return
     */
    public static String getYmdStr(String ymdStr){
        try {
            Date date = YMD_FORMAT.parse(ymdStr);
            return YMD_FORMAT.format(date);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取年月日
     * @param ymdStr 日期字符串
     * @return Date
     */
    public static Date getYmdDate(String ymdStr){
        try {
            Date date = YMD_FORMAT.parse(ymdStr);
            return date;
        } catch (Exception e){
            e.printStackTrace();
        }
        return new Date();
    }
    /**
     * 获取年月日
     * @param ymd 年月日日期
     * @return
     */
    public static String getYmdStr(Date ymd){
        try {
            return YMD_FORMAT.format(ymd);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取年月
     * @param ymStr 日期字符串
     * @return
     */
    public static String getYmStr(String ymStr){
        try {
            Date date = YM_FORMAT.parse(ymStr);
            return YM_FORMAT.format(date);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取年
     * @param yStr 日期字符串
     * @return
     */
    public static String getYStr(String yStr){
        try {
            Date date = Y_FORMAT.parse(yStr);
            return Y_FORMAT.format(date);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 生成有范围的随机数
     * @param min 范围最小值
     * @param max 范围最大值
     * @return
     */
    public static int getRandom(int min, int max){
        Random random = new Random();
        int s = random.nextInt(max - min + 1) + min;
        return s;
    }


    /**
     * 生成有范围的随机数
     * @param min 范围最小值
     * @param max 范围最大值
     * @return
     */
    public static int getRandoma(int min, int max){
        Random random = new Random();
        int s = random.nextInt(max - min + 1) + min;
        return s;
    }
    /**
     * 数据模拟每次递增秒数
     * @param randomNum 模拟记录数
     * @param totalSecond 记录总的秒数和
     * @return
     */
    public static int getRecordSecond(int randomNum, int totalSecond){
        if(randomNum == 0){
            return 0;
        }
        return totalSecond / randomNum;
    }
    /**
     * 获取一天中的两个时间点之间的 秒数
     * @param startHms 开始时间(不包括年月日)  06:00:00
     * @param endHms 结束时间(不包括年月日)    18:00:00
     * @return
     * long
     * @author Administrator
     */
    public static int getSecSub(String startHms, String endHms){
        Date now = new Date();
        int second=0;
        try {
            String ymd = YMD_FORMAT.format(now);
            String startYmdhms = ymd + " " + startHms;
            String endYmdhms = ymd + " " + endHms;
            second=(int)(YMDHMS_FORMAT.parse(endYmdhms).getTime()-YMDHMS_FORMAT.parse(startYmdhms).getTime())/1000;
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            return second;
        }
    }

    /**
     * 获取时间添加秒数后的时间
     * @param startTime 初始时间
     * @param second 秒数
     * @return
     */
    public static String getDateAddSecond(String startTime, int second){
        try {
            Calendar cal = Calendar.getInstance();
            Date ymdhms = YMDHMS_FORMAT.parse(startTime);
            cal.setTime(ymdhms);
            cal.add(Calendar.SECOND, second);
            startTime = YMDHMS_FORMAT.format(cal.getTime());
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            return startTime;
        }
    }

    /**
     * 获取车牌号
     * @return
     */
    public static String getCarNumber(){
        // 车牌号由一位字母+四位数字组成(0001-9999)
        String[] AZ = new String[]{"A","B","C","D","E","F","G","H","K","W","Z"};
        int azIndex = getRandom(0, (AZ.length - 1));
        return AZ[azIndex]+getRandom(1000, 9999);
    }

    /**
     * 获取随机各年龄分段比例
     *
     * @return
     */
    public static Map getPortrayalAgeScale(){
        Random ran = new Random();
        float f = (float) (ran.nextFloat()/5 + 0.3);
        DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(f);
        double q = 1 -  Double.parseDouble(p);
        String w = decimalFormat.format(q);
        double a = ran.nextInt(2) + 5.5;
        double b = a / 10;
        double e = Double.parseDouble(w) * b;
        String r = decimalFormat.format(e);
        double t = 1 - Double.parseDouble(p) - e;
        String y = decimalFormat.format(t);
        double u = ran.nextInt(2) + 6.5;
        double i = u / 10;
        double o = Double.parseDouble(y) * i;
        String d = decimalFormat.format(o);
        double g = ran.nextInt(3) + 4;
        double h = g / 10;
        double j = Double.parseDouble(d) * h;
        String k = decimalFormat.format(j);
        double l = Double.parseDouble(d) - Double.parseDouble(k);
        String z = decimalFormat.format(l);
        double x = Double.parseDouble(y) - Double.parseDouble(d);
        String c = decimalFormat.format(x);
        double v = ran.nextInt(3) + 2;
        double n = v / 10;
        double m = Double.parseDouble(c) * n;
        String mm = decimalFormat.format(m);
        double mmm = Double.parseDouble(c) - Double.parseDouble(mm);
        String mmmm = decimalFormat.format(mmm);
        Map map = new HashMap();
        map.put("16-25岁",p);
        map.put("26-35岁",r);
        map.put("36-45岁",k);
        map.put("55岁以上",z);
        map.put("16岁以下",mm);
        map.put("46-55岁",mmmm);
        return map;
    }

    /**
     * 道路交通高峰时间段模拟
     *
     * @return
     */
    public static String getTimeFrame() {
        Calendar ca = Calendar.getInstance();
        //时间段早高峰/晚高峰
        ca.set(Calendar.HOUR_OF_DAY, getRandom(0, 2) == 0 ? getRandom(7, 10) : getRandom(17, 20));
        ca.set(Calendar.MINUTE, getRandom(0, 6) * 10);
        String result = HM_FORMAT.format(ca.getTime());
        ca.add(Calendar.MINUTE, getRandom(4, 12) * 10);
        result = result + "," + HM_FORMAT.format(ca.getTime());
        return result;
    }
}
