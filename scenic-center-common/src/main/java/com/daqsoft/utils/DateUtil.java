/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Title: DateUtil
 * @Author: tanggm
 * @Date: 2018/02/05 15:26
 * @Description: TODO 日期工具类
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */

public class DateUtil {
    public static final SimpleDateFormat TIME_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat TIME_H = new SimpleDateFormat("yyyy年MM月dd日HH时");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_CUR_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");
    public static final SimpleDateFormat MONTH_SDF = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat MONTH = new SimpleDateFormat("MM");
    public static final SimpleDateFormat MONTH_CUR_SDF = new SimpleDateFormat("yyyy年MM月");
    public static final SimpleDateFormat YEAR_SDF = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat YEAR_CUR_SDF = new SimpleDateFormat("yyyy年");

    /**
     * 获取两个日期中间的所有日期 包含开始与结束日期
     *
     * @param start 开始日期 格式 yyyy-MM-dd
     * @param end   结束日期 格式 yyyy-MM-dd
     * @return
     */
    public static List<String> getSubDateList(Date start, Date end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<String> result = new ArrayList<>();
            if (start.getTime() == end.getTime()) {
                String format = sdf.format(start);
                result.add(format);
                return result;
            } else if (start.getTime() > end.getTime()) {
                return new ArrayList<>();
            } else {
                long daySub = getDaySub(start, end);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(start);
                for (int i = 0; i <= daySub; i++, calendar.add(Calendar.DATE, 1)) {
                    Date d = calendar.getTime();
                    result.add(sdf.format(d));
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 获取两个日期中间的所有日期 包含开始与结束日期
     *
     * @param start 开始日期 格式 yyyy-MM-dd
     * @param end   结束日期 格式 yyyy-MM-dd
     * @return
     */
    public static List<String> getSubDateListByString(String start, String end) {
       Date startTime =  DataImitateUtil.getYmdDate(start);
       Date endTime =  DataImitateUtil.getYmdDate(end);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<String> result = new ArrayList<>();
            if (startTime.getTime() == endTime.getTime()) {
                String format = sdf.format(startTime);
                result.add(format);
                return result;
            } else if (startTime.getTime() > endTime.getTime()) {
                return new ArrayList<>();
            } else {
                long daySub = getDaySub(startTime, endTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startTime);
                for (int i = 0; i <= daySub; i++, calendar.add(Calendar.DATE, 1)) {
                    Date d = calendar.getTime();
                    result.add(sdf.format(d));
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 时间相减得到天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return long
     * @author Administrator
     */
    public static long getDaySub(Date start, Date end) {
        long day = 0;
        try {
            day = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return day;
        }
    }

    /**
     * 时间相减得到相差秒数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return long
     * @author Administrator
     */
    public static long getSecSub(Date start, Date end) {
        long second = 0;
        try {
            second = (end.getTime() - start.getTime()) / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return second;
        }
    }

    /**
     * 获取当前时间前几小时
     *
     * @param ihour
     * @return
     */
    public static String getHourTime(int ihour, Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.setTime(time);
        ca.add(Calendar.HOUR_OF_DAY, ihour);// ihour小时，可以改变的
        String enddate = format.format(ca.getTime());
        return enddate;
    }

    /**
     * 时间减年份
     *
     * @param ihour
     * @return
     */
    public static String getYearTime(int ihour, Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.setTime(time);
        ca.add(Calendar.YEAR, -ihour);//当前时间减去一年，即一年前的时间
        String enddate = format.format(ca.getTime());
        return enddate;
    }

    /**
     * 时间减年份 按年
     *
     * @param ihour
     * @return
     */
    public static String getLastYear(int ihour, Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Calendar ca = Calendar.getInstance();
        ca.setTime(time);
        ca.add(Calendar.YEAR, -ihour);//当前时间减去一年，即一年前的时间
        String enddate = format.format(ca.getTime());
        return enddate;
    }

    /**
     * 字符串转时间类型
     *
     * @param time
     * @return
     */
    public static Date getDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间减年份
     *
     * @param ihour
     * @return
     */
    public static String getYearSubTime(int ihour, String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String enddate = "";
        try {
            date = format.parse(time);
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            ca.add(Calendar.YEAR, -ihour);//当前时间减去一年，即一年前的时间
            enddate = formatDate.format(ca.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return enddate;
    }

}
