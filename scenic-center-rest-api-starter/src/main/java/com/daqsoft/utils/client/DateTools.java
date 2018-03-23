package com.daqsoft.utils.client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: liyl .
 * @Date: Created in 2017/03/31
 * @Version: V3.0.0.
 * @describe:时间工具类
 *
 */
public class DateTools {



    public static String date2Str(Date date) {
        //return date2Str(date, "yyyy-MM-dd HH:mm:ss");
        return date2Str(date, "yyyy-MM-dd");
    }
    public static String date2Str(String strdate, String format) {
        String now = "";
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(strdate);
            now = new SimpleDateFormat(format).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return now;
    }


    public static String date2Str(Date date, String format) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }



    /**
     * 起始日期加上天数
     *
     * @param date
     * @param d
     * @return
     */
    public static String addDate(String date, int d) {
        String ret = null;
        if (date.length() < 10)
            return null;
        try {
            Calendar e = getTime(date, "yyyy-MM-dd");
            e.add(Calendar.DATE, d);
            Date endDate = e.getTime();
            ret = formatDate(endDate, "yyyy-MM-dd");
        } catch (Exception ex) {
            System.out.println("not date format");
        }
        return ret;
    }
    /**
     * 返回输入的字符串代表的Calendar对象.
     *
     * @param
     *            str 输入的字符串
     * @param
     *            str 输入的字符串日期格式, 缺省为yyyy-MM-dd
     * @return Calendar 返回代表输入字符串的Calendar对象
     */
    public static Calendar getTime(String str, String pattern) {
        Calendar cal = Calendar.getInstance();
        if (pattern == null)
            pattern = "yyyy-MM-dd";
        try {
            DateFormat myformat = new SimpleDateFormat(pattern);
            Date date = myformat.parse(str);
            cal.setTime(date);
        } catch (Exception e) {
            System.out.println(e);
        }
        return cal;
    }


    /**
     * 将Date数据类型转换为特定的格式, 如格式为null, 则使用缺省格式yyyy-MM-dd.
     *
     * @param
     *            day 日期
     * @param
     *            toPattern 要转换成的日期格式
     * @return String 返回日期字符串
     */
    public static String formatDate(Date day, String toPattern) {
        String date = null;
        if (day != null) {
            try {
                SimpleDateFormat formatter = null;
                if (toPattern != null)
                    formatter = new SimpleDateFormat(toPattern);
                else
                    formatter = new SimpleDateFormat("yyyy-MM-dd");
                date = formatter.format(day);
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
            return date;
        } else
            return null;
    }


    /**
     * 获取传入日期的 后一天日期
     *
     * @param days - yyyy-MM-dd (2015-01-24)
     * @return returndays - yyyy-MM-dd (2015-01-25)
     */
    public static String getAfterDay(String days){
        Date dates=DateUtils.fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.setTime(dates);
        c.add(Calendar.DAY_OF_MONTH, +1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }


    /**
     * 获取传入日期的 前后n天日期
     *
     * @param days - yyyy-MM-dd (2015-01-24)
     * @return returndays - yyyy-MM-dd (2015-01-25)
     */
    public static String getFABDay(String days, int n){
        Date dates=DateUtils.fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.setTime(dates);
        c.add(Calendar.DAY_OF_MONTH, n);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }




    /**
     * 获取传入日期的 前一天日期（小时）
     *
     * @return returndays - yyyy-MM-dd (2015-01-23 hh)
     */
    public static String getDayBeforeHour(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        Date today = new Date();
        Date yesterday = new Date(today.getTime() - 1000 * 60 * 60 * 24);
        String todayTime = format.format(today);
        String beforeTime = format.format(yesterday);
        return beforeTime;
    }

    /**
     * 获取传入日期的 前一天日期（小时）
     *
     * @return returndays - yyyy-MM-dd (2015-01-23 hh)
     */
    public static String getbeforeDayHour(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        Date today = new Date();
        Date yesterday = new Date(today.getTime() - 1000 * 60 * 60 * 25);
        String todayTime = format.format(today);
        String beforeTime = format.format(yesterday);
        return beforeTime;
    }




    /**
     * 获取传入日期的 前一天日期
     *
     * @param days - yyyy-MM-dd (2015-01-24)
     * @return returndays - yyyy-MM-dd (2015-01-23)
     */
    public static String getbeforeDay(String days){
        Date dates=DateUtils.fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.setTime(dates);
        c.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

    /**
     * 获取输入日期的前n天日期
     *
     * @return returndays - yyyy-MM-dd (2015-01-23 HH:mm:ss)
     */
    public static String getbeforeNTime(int n, String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            today = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date yesterday = new Date(today.getTime() - 1000 * 60 * 60 * 24 * n);
        String todayTime = format.format(today);
        String yesterdayTime = format.format(yesterday);
        return yesterdayTime;
    }


    /**
     * 获取前一天日期
     *
     * @return returndays - yyyy-MM-dd (2015-01-23 HH:mm:ss)
     */
    public static String getbeforeTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        Date yesterday = new Date(today.getTime() - 1000 * 60 * 60 * 24);
        String todayTime = format.format(today);
        String yesterdayTime = format.format(yesterday);
        return yesterdayTime;
    }
    /**
     * 获取传入日期的 前7天日期
     *
     * @param days - yyyy-MM-dd (2015-01-24)
     * @return returndays - yyyy-MM-dd (2015-01-17)
     */
    public static String getSevenDay(String days){
        Date dates=DateUtils.fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.setTime(dates);
        c.add(Calendar.DAY_OF_MONTH, -7);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }


    /**
     * 获取传入日期的 上月日期
     *
     * @param days - yyyy-MM-dd (2015-01-24)
     * @return returndays - yyyy-MM-dd (2014-12-24)
     */
    public static String getBeforeMonth(String days){
        Date dates=DateUtils.fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.setTime(dates);
        c.add(Calendar.MONTH, -1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }
    /**
     * 返回当前年月日期,格式yyyy-MM-dd
     *
     * @return String 年月日期
     */
    public static String getCurrentDate() {
        String date = null;
        try {
            DateFormat myformat = new SimpleDateFormat("yyyy-MM-dd");
            date = myformat.format(new Date());
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
}

    /**
     * 返回当前年月日期,格式yyyy-MM-dd
     *
     * @return String 年月日期
     */
    public static String getChinaDate() {
        String date = null;
        try {
            DateFormat myformat = new SimpleDateFormat("yyyy年MM月dd日");
            date = myformat.format(new Date());
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }

    /**
     * 获取传入日期的 前一天日期
     *
     * @param days - yyyy-MM-dd (2015-01-24)
     * @return returndays - yyyy-MM-dd (2015年01月23日)
     */
    public static String getbeforeChinaDay(String days){
        Date dates=DateUtils.fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.setTime(dates);
        c.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }
    public static String getbeforeChinaDays(String days){
        Date dates=DateUtils.fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.setTime(dates);
        c.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }


    public static String getCurrentDates() {
        String date = null;
        try {
            DateFormat myformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = myformat.format(new Date());
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }

    public static String getDateBystr(String strDate) {
        String date = null;
        try {
            DateFormat myformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = myformat.format(strDate);
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }

    /**
     * 返回当前年月,格式yyyy-MM
     * @return
     */
    public static String getCurrentMonth() {
        String date = null;
        try {
            DateFormat myformat = new SimpleDateFormat("yyyy-MM");
            date = myformat.format(new Date());
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }

    /**
     * 返回当前年月,格式MM-dd
     * @return
     */
    public static String getCurrentMonthDay() {
        String date = null;
        try {
            DateFormat myformat = new SimpleDateFormat("MM-dd");
            date = myformat.format(new Date());
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }

    /**
     * 返回当前年月,格式MM
     * @return
     */
    public static String getCurrentMonths() {
        String date = null;
        try {
            DateFormat myformat = new SimpleDateFormat("MM");
            date = myformat.format(new Date());
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }
    /**
     * 返回当前天,格式dd
     * @return
     */
    public static String getCurrentdd() {
        String date = null;
        try {
            DateFormat myformat = new SimpleDateFormat("dd");
            date = myformat.format(new Date());
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }

    /**
     * 返回当前小时,格式HH
     * @return
     */
    public static String getCurrentHH() {
        String date = null;
        try {
            DateFormat myformat = new SimpleDateFormat("HH");
            date = myformat.format(new Date());
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }

    /**
     * 比较两个日期大小
     *
     * @param day1
     * @param day2
     * @return
     */
    public static boolean compareDay(String day1, String day2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = df.parse(day1);
            dt2 = df.parse(day2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

            if (dt1.getTime() > dt2.getTime()) {
                return true;
            }else {
                return false;
            }
    }

    /**
     * 返回当前月份的第一天
     */
    public  static  String getMonthDay( String date) {
        String first=null;
        try {
            String  time= date+"-01";
            Date dates=DateUtils.fomatDate(time);
            Calendar c = Calendar.getInstance();
            c.setTime(dates);
            c.add(Calendar.MONTH, 0);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         first = format.format(c.getTime());
//        System.out.println("===============first:" + first);
        }
        catch (Exception e){
        }
        return  first;
    }
    /**
     * 返回当前月份的第一天
     */
    public  static  String getMonthEndDay( String date) {
        String first=null;
        try {
            String  time= date+"-01";
            Date dates=DateUtils.fomatDate(time);
            Calendar c = Calendar.getInstance();
            c.setTime(dates);
//            c.add(Calendar.MONTH, 0);
//            Calendar cale = Calendar.getInstance();
            c.add(Calendar.MONTH, 1);
            c.set(Calendar.DAY_OF_MONTH, 0);
//            lastday = format.format(cale.getTime());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            first = format.format(c.getTime());
//            System.out.println("===============first:" + first);
        }
        catch (Exception e){
        }
        return  first;
    }


    /**
     * 获取某年某月最后天日期
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }


    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取传入日期的 后一个小时
     *
     * @return returndays
     */
    public static String getbeforeHour(String days) {
        Date dates = fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.HOUR_OF_DAY, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }


    /**
     * 返回当前年月日期,格式yyyy-MM-dd hh:mm:ss  HH:mm:ss
     *
     * @return String 年月日期
     */
    public static String getCurrentTime() {

        String date = null;
        try {
            SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = myformat.format(new Date());
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }

    /**
     * 返回当前年月日期年,格式yyyy
     *
     * @return String 年月日期
     */
    public static String getCurrentYear() {

        String date = null;
        try {
            SimpleDateFormat myformat = new SimpleDateFormat("yyyy");
            date = myformat.format(new Date());
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }


    /**
     * 获取当前时间到小时
     * @return
     */
    public static String getCurrentDateH() {
        String date = null;
        try {
            DateFormat myformat = new SimpleDateFormat("yyyy-MM-dd HH");
            date = myformat.format(new Date());
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }


    /**
     * 获取当前时间前几小时
     * @param ihour
     * @return yyyy-MM-dd HH
     */
    public static String getBeforeHour(int ihour){
        String returnstr = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - ihour);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
        returnstr = df.format(c.getTime());
        return returnstr;
    }

    /**
     * 获取当前时间前几小时
     * @param ihour
     * @return
     */
    public static String getBeforeHourTime(int ihour){
        String returnstr = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - ihour);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        returnstr = df.format(c.getTime());
        return returnstr;
    }



    /**
     * 返回日期的月
     *
     * @param dateStr
     *            输入的字符串 格式YYYY-MM-DD
     * @return int 月
     */
    public static int getMonth(String dateStr) {
        if (dateStr == null || dateStr.length() < 7)
            return 0;
        String month = dateStr.substring(5, 7);
        return parseInt(month);
    }
    public static String formartdaytoAtt(int maxNo){
        String newNo;
        int len = String.valueOf(maxNo).length();
        newNo = ("0" + String.valueOf(maxNo)).substring(len-1,len+1);
        return newNo;
    }




    /**
     * 格式化String为int型，如果字符串为空，返回0.
     *
     * @param
     *            s 双精度数字
     * @return int 返回int类型数值
     */
    public static int parseInt(String s) {
        int i = 0;
        if (s != null) {
            s = s.trim();
            try {
                i = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                i = 0;
                double t = parseDouble(s);
                if (t != 0) {
                    i = (int) t;
                }

            }
        }
        return i;
    }






    /**
     * 格式化String为double型，如果字符串为空，返回0.
     *
     * @param
     *            s 双精度数字
     * @return double 返回double类型数值
     */
    public static double parseDouble(String s) {
        double d = 0;
        if (s != null) {
            try {
                d = Double.parseDouble(s);
            } catch (NumberFormatException e) {
                try {
                    String t = s.replaceAll(",", "");
                    d = Double.parseDouble(t);
                } catch (NumberFormatException e2) {
                    d = 0;

                }
            }
        }
        return d;
    }
    /**
     * 返回日期的年
     *
     * @param dateStr
     *            输入的字符串 格式YYYY-MM-DD
     * @return int 年
     */
    public static int getYear(String dateStr) {
        if (dateStr == null || dateStr.length() < 4)
            return 0;
        String year = dateStr.substring(0, 4);
        return parseInt(year);
    }
    /**
     * 返回两个String(格式是：yyyy-MM-dd HH:mm:ss)对象的之间的天数
     *
     * @param
     *            date1 作为被减数的String对象
     * @param
     *            date2 作为减数的String对象
     * @return int 两个String对象的之间的天数
     */
    public static int getDaysTween(String date1, String date2) {
        String s1 = date1;
        String s2 = date2;
        s1 = formatDate(s1, "yyyy-MM-dd");
        s2 = formatDate(s2, "yyyy-MM-dd");
        if (s1.length() < 11) {
            s1 = s1 + " 00:00:00";
        }
        if (s2.length() < 11) {
            s2 = s2 + " 00:00:00";
        }
        Calendar cal1 = getTime(formatDate(s1, "yyyy-MM-dd HH:mm:ss"));
        Calendar cal2 = getTime(formatDate(s2, "yyyy-MM-dd HH:mm:ss"));
        Date d1 = cal1.getTime();
        Date d2 = cal2.getTime();
        return getDaysTween(d1, d2);
    }



    /**
     * 将原有的日期格式的字符串转换为特定的格式, 原有格式为yyyy-MM-dd.
     *
     * @param
     *            value 日期格式的字符串
     * @param
     *            toPattern 转换成的日期格式
     * @return String 返回日期字符串
     */
    public static String formatDate(String value, String toPattern) {
        return formatDate(value, toPattern, toPattern);
    }


    /**
     * 将原有的日期格式的字符串转换为特定的格式, 如(原有和转换)格式为null, 则使用缺省格式yyyy-MM-dd.
     *
     * @param
     *            value 日期格式的字符串
     * @param
     *            fromPattern 原有的日期格式
     * @param
     *            toPattern 转换成的日期格式
     * @return String 返回日期字符串
     */
    public static String formatDate(String value, String fromPattern,
                                    String toPattern) {
        String date = null;
        if (toPattern == null)
            toPattern = "yyyy-MM-dd";
        if (value != null) {
            try {
                SimpleDateFormat formatter = null;
                if (fromPattern != null)
                    formatter = new SimpleDateFormat(fromPattern);
                else
                    formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date day = formatter.parse(value);
                formatter.applyPattern(toPattern);
                date = formatter.format(day);
            } catch (Exception e) {

                return value;
            }
            return date;
        } else
            return null;
    }



    /**
     * 返回输入的字符串代表的Calendar对象.
     *
     * @param
     *            str 输入的字符串,格式=yyyy-MM-dd HH:mm:ss.
     * @return Calendar 返回代表输入字符串的Calendar对象.
     */
    public static Calendar getTime(String str) {
        Calendar cal = Calendar.getInstance();
        try {
            DateFormat myformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = myformat.parse(str);
            cal.setTime(date);
        } catch (Exception e) {
            System.out.println(e);
        }
        return cal;
    }

    /**
     * 获取下个月日期
     * @return
     */
    public static String getPreMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 0);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }


    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
     *
     * @param date
     * @return
     */
    public static Date str2Date(String date) {
        if (notEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Date();
        } else {
            return null;
        }
    }
    /**
     * 检测字符串是否不为空(null,"","null")
     *
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s) {
        return s != null && !"".equals(s) && !"null".equals(s);
    }
    public static int getDaysTween(Date date1, Date date2) {
        // return toJulian(date1) - toJulian(date2);
        long mill1 = date1.getTime();
        long mill2 = date2.getTime();
        return (int) ((mill1 - mill2) / (1000 * 60 * 60 * 24));
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     *
     * @param beginDate
     * @param endDate
     * @return List
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(beginDate);// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);// 把结束时间加入集合
        return lDate;
    }
    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static String  getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
//        Date currYearFirst = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preYear = dateFormat.format(calendar.getTime());
        return preYear;
    }
    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static String  getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
//        Date currYearLast = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preYear = dateFormat.format(calendar.getTime());
        return preYear;
    }

    /**
     * 获取日，数组循环获取
     *
     * @return
     */
    public static String getDay(int i) {
        String []days = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21",
                "22","23","24","25","26","27","28","29","30","31",};
        String day = days[i];
        return day;
    }



}
