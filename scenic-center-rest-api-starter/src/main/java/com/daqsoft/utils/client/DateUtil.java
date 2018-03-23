package com.daqsoft.utils.client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-05-22 17:17.
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
     * 获取当前日期
     *
     * @return
     */
    public static String getCurDateStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurDateNumStr() {
        Date date = new Date();
        return new SimpleDateFormat("dd").format(date);
    }
    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurDateTimeStr() {
        Date date = new Date();
        return TIME_SDF.format(date);
    }

    public static String getCurDateTimeStrHour() {
        Date date = new Date();
        return TIME_H.format(date);
    }

    public static String getCurDateStrFormat() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return DATE_CUR_FORMAT.format(date);
    }

    public static String getCurMonthStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    /**
     * 返回月份数字
     * @return
     */
    public static String getCurMonthNumStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(date);
    }

    /**
     * 获取年月日
     * @param date 日期
     * @return
     */
    public static String getYMDStr(String date) {
        SimpleDateFormat ymdsdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date ymddate = ymdsdf.parse(date);
            return sdf.format(ymddate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取对应月份
     * @param date 日期
     * @return
     */
    public static String getCurMonthStr(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            Date nowdate = sdf.parse(date);
            return sdf.format(nowdate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurMonthStrFormat() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        return MONTH_CUR_SDF.format(date);
    }

    public static String getCurYearStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return YEAR_SDF.format(date);
    }

    /**
     * 判断传入日期是否在当前日期之前
     * @param old  以前时间
     * @param now  现在时间
     * @param format 格式
     * @return 是：true; 否：false
     */
    public static boolean isNowDayToDate(String old, String now, String format){
        try {
            Date nowDate = DateUtil.strToDate(now, format);
            long nowTimes = nowDate.getTime();
            Date yesDate = DateUtil.strToDate(old, format);
            long yesTimes = yesDate.getTime();
            if(yesTimes < nowTimes){
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
    /**
     * 获取当前时间前几小时
     *
     * @param ihour
     * @return
     */
    public static String getBeforeHourTime(int ihour) {
        String returnstr = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - ihour);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        returnstr = df.format(c.getTime());
        return returnstr;
    }
    /**
     * 获取对应年份
     * @param year 月份日期  2017-08
     * @return
     */
    public static String getCurYearStr(String year){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        try {
        Date nowdate = sdf.parse(year);
        return sdf.format(nowdate);
    }catch (Exception e){
        e.printStackTrace();
    }
        return "";

    }
    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取传入日期的 后一天日期
     *
     * @param days - yyyy-MM-dd (2015-01-24)
     * @return returndays - yyyy-MM-dd (2015-01-25)
     */
    public static String getAfterDay(String days) {
        Date dates = DateUtil.fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.setTime(dates);
        c.add(Calendar.DAY_OF_MONTH, +1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }
    /**
     * 获取对应年份
     * @param date 日期  2017-08-25
     * @return
     */
    public static String getCurYearStrByDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date nowdate = sdf.parse(date);
            return YEAR_SDF.format(nowdate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";

    }

    public static String getCurSimpleMonthStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(date);
    }


    public static String getCurYearFormat() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
        return YEAR_CUR_SDF.format(date);
    }

    public static String getCurHourStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        return sdf.format(date);
    }

    public static String getCurSimpleHourStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return sdf.format(date);
    }

    /**
     * 判断是否是指定格式时间字符串
     *
     * @param dateStr 时间字符串
     * @param format  格式
     * @return flag
     */
    public static boolean isDateStr(String dateStr, String format) {
        boolean flag = false;
        if (dateStr == null || format == null) {
            return flag;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            sdf.parse(dateStr);
            flag = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 时间字符串转化为时间
     *
     * @param dateStr 时间字符串
     * @param format  对应时间格式
     * @return date
     */
    public static Date strToDate(String dateStr, String format) {
        Date date = null;
        SimpleDateFormat sdf1 = new SimpleDateFormat(format);
        try {
            date = sdf1.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param type 类型 month:返回一年的12个月列表   time:返回一天中7点至18点每个时间点列表
     * @return
     */
    public static List<String> getTimeList(String type) {
        List<String> list = new ArrayList<>();
        if (type == null) {
            return list;
        }

        if (type.equals("month")) {
            for (int i = 1; i <= 12; i++) {
                if (i > 9) {
                    list.add(i + "月");
                } else {
                    list.add("0" + i + "月");
                }
            }
        }
        if (type.equals("time")) {
            for (int i = 7; i < 19; i++) {
                if (i > 9) {
                    list.add(i + "");
                } else {
                    list.add("0" + i);
                }
            }
        }
        return list;
    }
    /**
     * 返回一天中7点至18点每个时间点列表
     * @param start 开始循环值
     * @param end 结束循环值
     * @return 格式  07:00
     */
    public static List<String> getTimeToList(int start, int end) {
        List<String> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (i > 9) {
                list.add(i + ":00");
            } else {
                list.add("0" + i + ":00");
            }
        }
        return list;
    }
    /**
     * 返回一天中7点至18点每个时间点列表
     * @param start 开始循环值
     * @param end 结束循环值
     * @return 格式  07
     */
    public static List<String> getHoursToList(int start, int end) {
        List<String> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (i > 9) {
                list.add(i + "");
            } else {
                list.add("0" + i);
            }
        }
        return list;
    }
    /**
     * 返回一天中7点至18点每个时间点列表
     * @param year 年份  2017
     * @return 格式  2017-01
     */
    public static List<String> getYearMonthList(String year) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            if (i > 9) {
                list.add(year+"-"+i);
            } else {
                list.add(year+"-"+"0" + i);
            }
        }
        return list;
    }

    /**
     * 获取年中所有的月份列表
     *  01  02  03 ...12
     * @return
     */
    public static List<String> getMonthList(){
        List<String> monthList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            if (i > 9) {
                monthList.add(i+"");
            } else {
                monthList.add("0" + i);
            }
        }
        return monthList;
    }
    /**
     * 获取当前年份的当前时间之前的月份列表
     *  01  02  03 ...
     * @return
     */
    public static List<String> getNowYearMonthList(){
        Integer nowMonth = Integer.valueOf(getCurMonthNumStr());
        List<String> monthList = new ArrayList<>();
        for (int i = 1; i <= nowMonth; i++) {
            if (i > 9) {
                monthList.add(i+"");
            } else {
                monthList.add("0" + i);
            }
        }
        return monthList;
    }
    /**
     * 获取当前月份的当前日期之前的日期列表
     *  01  02  03 ...
     * @return
     */
    public static List<String> getNowMonthDayList(){
        Integer nowDay = Integer.valueOf(getCurDateNumStr());
        List<String> dayList = new ArrayList<>();
        for (int i = 1; i <= nowDay; i++) {
            if (i > 9) {
                dayList.add(i+"");
            } else {
                dayList.add("0" + i);
            }
        }
        return dayList;
    }

    /**
     * 获取两个年份之间年份列表（包含开始和结束）
     * @param start 开始年份字符串 格式 yyyy
     * @param end 结束年份字符串 格式 yyyy
     * @return
     */
    public static List<String> getYearList(String start, String end){
        List<String> years = new ArrayList<>();
        try {
            Integer startYear = Integer.valueOf(start);
            Integer endYear = Integer.valueOf(end);
            for(Integer i = startYear; i <= endYear; i++){
                years.add(i+"");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return years;
    }
    /**
     * 通过当前时间获取当前是第几季度
     * @return
     */
    public static String getCurrentQuarter()
    {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String quarter = "";
        if (currentMonth >= 1 && currentMonth <= 3)
            quarter = "一";
        else if (currentMonth >= 4 && currentMonth <= 6)
            quarter = "二";
        else if (currentMonth >= 7 && currentMonth <= 9)
            quarter = "三";
        else if (currentMonth >= 10 && currentMonth <= 12)
            quarter = "四";
        String res = "第" + quarter + "季度";
        return res;
    }

    /**
     * 通过当前时间获取当前是第几季度 返回int类型
     *
     * @return
     */
    public static String getNumberCurrentQuarter() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String quarter = "";
        if (currentMonth >= 1 && currentMonth <= 3)
            quarter = "1";
        else if (currentMonth >= 4 && currentMonth <= 6)
            quarter = "2";
        else if (currentMonth >= 7 && currentMonth <= 9)
            quarter = "3";
        else if (currentMonth >= 10 && currentMonth <= 12)
            quarter = "4";

        return quarter;
    }

    /**
     * 获取一年四个季度的开始和结束时间
     *
     * @return Map<季度，List<开始，结束>>
     */
    public static Map<String, List<String>> getQuarters() {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        List<String> list3 = new ArrayList<String>();
        List<String> list4 = new ArrayList<String>();
        list1.add("01-01 00:00:00");
        list1.add("03-31 23:59:59");
        list2.add("04-01 00:00:00");
        list2.add("06-30 23:59:59");
        list3.add("07-01 00:00:00");
        list3.add("09-30 23:59:59");
        list4.add("10-01 00:00:00");
        list4.add("12-31 23:59:59");
        map.put("第一季度", list1);
        map.put("第二季度", list2);
        map.put("第三季度", list3);
        map.put("第四季度", list4);
        return map;
    }

    /***
     * zf季度参数 转换具体时间段   参数：2017_1     2017-01-01 00:00:00 到 2017-03-31 23:59:59
     * @param quarter
     * @return
     */
    public static Map getTimeByQuarters(String quarter){
        String year=quarter.split("_")[0].toString();
        String q=quarter.split("_")[1].toString();
        Map map=new HashMap();
        if("1".endsWith(q)){
            map.put("startTime",year+"-01-01 00:00:00");
            map.put("endTime",year+"-03-31 23:59:59");
        }else if("2".endsWith(q)){
            map.put("startTime",year+"-04-01 00:00:00");
            map.put("endTime",year+"-06-31 23:59:59");
        }else if("3".endsWith(q)){
            map.put("startTime",year+"-07-01 00:00:00");
            map.put("endTime",year+"-09-31 23:59:59");
        }else if("4".endsWith(q)){
            map.put("startTime",year+"-10-01 00:00:00");
            map.put("endTime",year+"-12-31 23:59:59");
        }
        return map;
    }

    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     *
     * @return
     */
    public static String getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = DATE_FORMAT.format(c.getTime()) + " 00:00:00";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 获取传入年份对应的每个季度的月份值
     *
     * @return
     */
    public static List<Map> getQuarterMonthByYear(String year) {
        List<Map> resList = new ArrayList<>();
        try {
            boolean b = isNowDayToDate(year, getCurYearStr(), "yyyy");
            int nowQuarter = Integer.valueOf(getNumberCurrentQuarter());
            Integer[] quarters = {1,2,3,4};
            String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
            if(b){
                for (Integer quarter : quarters){
                    Map<Object, Object> quarterMap = new HashMap<>();
                    List<String> monthList = new ArrayList<>();
                    for(int i = 0; i <=2; i++){
                        monthList.add(months[i+(quarter*3-3)]);
                    }
                    quarterMap.put("quarter", quarter);
                    quarterMap.put("months", monthList);
                    resList.add(quarterMap);
                }
            }else{
                for (int j = 0; j < nowQuarter; j++){
                    Map<Object, Object> quarterMap = new HashMap<>();
                    List<String> monthList = new ArrayList<>();
                    for(int i = 0; i <=2; i++){
                        monthList.add(months[i+(quarters[j]*3-3)]);
                    }
                    quarterMap.put("quarter", quarters[j]);
                    quarterMap.put("months", monthList);
                    resList.add(quarterMap);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return resList;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public static String getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = DATE_FORMAT.format(c.getTime()) + " 23:59:59";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }


    //当前日期减去相应天数；得到的日期 zf
    public static String getHistDate(int days) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - days);
        Date endDate = sdf.parse(sdf.format(date.getTime()));
        return sdf.format(endDate);
    }

    //当前日期减去相应月数；得到的月份 zf
    public static String getHistMonth(int month) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.MONTH, date.get(Calendar.MONTH) - month);
        Date endDate = sdf.parse(sdf.format(date.getTime()));
        return sdf.format(endDate);
    }

    /**
     * 获取当前日期前6天的日期
     * @return
     */
    public static String  getDateBefore(){
        String str = getCurDateStr();
        // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
        ;
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        calendar.add(Calendar.DATE, -3);
        Date date1 = calendar.getTime();
        String out = new SimpleDateFormat("yyyy-MM-dd").format(date1);
        return out;
    }
    /**
     * 获取当前日期前N天的日期
     * @return
     */
    public static String  getDateBefore(int days){
        String str = getCurDateStr();
        // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
        ;
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        calendar.add(Calendar.DATE, -days);
        Date date1 = calendar.getTime();
        String out = new SimpleDateFormat("yyyy-MM-dd").format(date1);
        return out;
    }
    /**
     * 获取传入日期前6天的日期
     * @return
     */
    public static String  getDateStrBefore(String dateStr){
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        calendar.add(Calendar.DATE, -6);
        Date date1 = calendar.getTime();
        String out = new SimpleDateFormat("yyyy-MM-dd").format(date1);
        return out;
    }

    /**
     * 计算某年的 某月 有多少天
     *
     * @param year  年份
     * @param month 月份
     * @return 返回当月的天数
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month -1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取某月下的所有日期
     * 默认格式化方式  yyyy-MM-dd
     * @param date 月份的任意日期
     * @return
     */
    public static List<String> getDayReport(Date date) {

        return getDayReport(date,DATE_FORMAT);
    }

    /**
     * 获取某月下的当天所有日期
     * 默认格式化方式  yyyy-MM-dd
     * @param date 月份的任意日期
     * @return
     */
    public static List<String> getNowDayReport(Date date, String month) {

        return getNowDayReport(date, month,DATE_FORMAT);
    }

    /**
     * 获取某月下的所有日期
     *
     * @param date 月份的任意日期
     * @param sdf 格式化方式
     * @return
     */
    public static List<String> getDayReport(Date date, SimpleDateFormat sdf) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int days = getDaysByYearMonth(year, month);
        //从一号开始
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        List<String> dayList = new ArrayList<>();
        for (int i = 0; i < days; i++,calendar.add(Calendar.DATE, 1)) {
            Date d = calendar.getTime();
            dayList.add(sdf != null ? sdf.format(d) : DATE_FORMAT.format(d));
        }
        return dayList;
    }

    /**
     * 获取某月下的当前所有日期
     *
     * @param date
     * @param sdf
     * @return
     */
    public static List<String> getNowDayReport(Date date, String month, SimpleDateFormat sdf) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH) + 1;
        String nowM = DateTools.getCurrentMonth();
        int days = 0;
        if(nowM.equals(month)) {
            days = Integer.parseInt(DateTools.getCurrentdd());
        } else {
            int year = calendar.get(Calendar.YEAR);
            int months = calendar.get(Calendar.MONTH) + 1;
            days = getDaysByYearMonth(year, months);
        }
        //从一号开始
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        List<String> dayList = new ArrayList<>();
        for (int i = 0; i < days; i++,calendar.add(Calendar.DATE, 1)) {
            Date d = calendar.getTime();
            dayList.add(sdf != null ? sdf.format(d) : DATE_FORMAT.format(d));
        }
        return dayList;
    }



    /**
     * 获取当前时间所在月份的所有日期
     *  默认格式化方式 yyyy-MM-dd
     * @return
     */
    public static List<String> getDayReport() {
        return getDayReport(new Date());
    }

    /**
     * 获取当前时间所在月份的所有日期
     *@param sdf 格式化方式
     * @return
     */
    public static List<String> getDayReport(SimpleDateFormat sdf) {
        return getDayReport(new Date(),sdf);
    }

    /**
     * 获取两个日期中间的所有日期 包含开始与结束日期
     * @param start 开始日期 格式 yyyy-MM-dd
     * @param end 结束日期 格式 yyyy-MM-dd
     * @return
     */
    public static List<String> getSubsectionDateList(String start,String end){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);

            List<String> result = new ArrayList<>();
            if (startDate.getTime() == endDate.getTime()){
                String format = sdf.format(startDate);
                result.add(format);
                return result;
            }else if (startDate.getTime() > endDate.getTime()){
                return new ArrayList<>();
            }else {
                long daySub = DateUtils.getDaySub(sdf.format(startDate), sdf.format(endDate));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(start));
                for (int i= 0;i <= daySub;i++,calendar.add(Calendar.DATE, 1)){
                    Date d = calendar.getTime();
                    result.add(sdf.format(d));
                }
            }
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    /**
     * 获取当前时间是否在早八点到晚八点
     * @return
     */
    public  static  boolean  getHoursByMinite(){
            Calendar cal = Calendar.getInstance();// 当前日期
            int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
            int minute = cal.get(Calendar.MINUTE);// 获取分钟
            int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
            final int start = 7* 60;// 起始时间早八点
            final int end = 20 * 60;// 结束时间晚八点
            if (minuteOfDay >= start && minuteOfDay <= end) {
                return true;
            } else {
                return false;
            }
    }


    public  static  List<String> getListMonth(String startTime,String endTime) throws Exception {
        Date d1 = MONTH_SDF.parse(startTime);//定义起始日期
        Date d2 = MONTH_SDF.parse(endTime);//定义结束日期
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.setTime(d1);//设置日期起始时间
        List<String> monthList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        while (dd.getTime().before(d2)) {//判断是否到结束日期
            String str = sdf.format(dd.getTime());
            monthList.add(str);
            dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
        }
        monthList.add(sdf.format(d2));
        return monthList;
    }

    public static void main(String[] args) throws Exception {

        System.out.println(getHistMonth(0));
        String start = "2017-01-01";
        String end = "2017-08-15";
        List<String> t = getListMonth(start,end);
    /*    List<String> subsectionDateList = getSubsectionDateList(start, end);
        for (String s : subsectionDateList) {
            System.out.println(s);
        }*/

//        long daySub = DateUtils.getDaySub(start, end);
//
//
//        try {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(DATE_FORMAT.parse(start));
//            for (int i= 0;i <= daySub;i++,calendar.add(Calendar.DATE, 1)){
//                Date d = calendar.getTime();
//                System.out.println(DATE_FORMAT.format(d));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        int daysByYearMonth = DateUtil.getDaysByYearMonth(2017, 7);
//        System.out.println(daysByYearMonth);
//        List<String> dayReport = getDayReport();
//        List<String> dayReport1 = getDayReport(DATE_CUR_FORMAT);
//
//        for (String s : dayReport1) {
//            System.out.println(s);
//        }

//        try {
//            Date parse = DateUtil.DATE_FORMAT.parse("2017-06-29");
//            List<String> dayReport = getDayReport(parse);
//            for (String s : dayReport) {
//                System.out.println(s);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        System.out.println("===========================");
//        Calendar calendar = Calendar.getInstance();
//        Date time = calendar.getTime();
//        List<String> dayReport = getDayReport(time);
//        for (String s : dayReport) {
//            System.out.println(s);
//        }





    }
}
