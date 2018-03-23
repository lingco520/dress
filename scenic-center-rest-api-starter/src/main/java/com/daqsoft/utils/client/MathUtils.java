package com.daqsoft.utils.client;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
public class MathUtils {
    /**
     * @param lastMap 上次统计map
     * @param thisMap 本次统计map
     * @param size    尺寸
     * @return 同比map
     */
    public List<Growth> getYearOnYearGrowth(Map<String, String> lastMap, Map<String, String> thisMap, int size) {
        List<Growth> lists = new LinkedList<Growth>();
        String s1;
        String s2;
        String percent;
        size = size + 1;
        int i = 1;
        while (i < size) {
            String key = i + "";
            if ((10 - i) > 0) {
                key = "0" + i;
            }

            s1 = lastMap.get(key);
            s2 = thisMap.get(key);
            if (s1 == null || s1.equals("0") || s2 == null) {
                percent = "0";
            } else {
                percent = new BigDecimal(s2).subtract(new BigDecimal(s1)).multiply(new BigDecimal(100)).divide(new BigDecimal(s1), 0, BigDecimal.ROUND_HALF_UP)+"";
            }
            lists.add(new Growth(key, percent));
            i++;
        }

        return lists;
    }

    /**
     * @param lastMap 上次统计map
     * @param thisMap 本次统计map
     * @param size    尺寸
     * @return 同比map
     */
    public List<Growth> getYearOnYearGrowth2(String year,Map<String, String> lastMap, Map<String, String> thisMap, int size) {
        List<Growth> lists = new LinkedList<Growth>();
        String s1;
        String s2;
        String percent;
        size = size + 1;
        int i = 1;
        while (i < size) {
            String key = year ;
            if ((10 - i) > 0) {
                key += "-0" + i;
            }else {
                key += "-" + i;
            }

            s1 = lastMap.get(key);
            s2 = thisMap.get(key);
            if (s1 == null || s1.equals("0") || s2 == null) {
                percent = "0";
            } else {
                percent = new BigDecimal(s2).subtract(new BigDecimal(s1)).multiply(new BigDecimal(100)).divide(new BigDecimal(s1), 0, BigDecimal.ROUND_HALF_UP)+"";
            }
            lists.add(new Growth(key, percent));
            i++;
        }

        return lists;
    }

    /**
     * 环比生成工具
     *
     * @param thisMap 本次数据
     * @param size    数据大小 12月,24H,size
     * @return
     */
    public List<Growth> getAnnulusGrowth(Map<String, String> thisMap, int size) {

        List<Growth> lists = new LinkedList<Growth>();
        int i = 1;
        size = size + 1;
        String lastValue;
        String thisValue;
        String percent;
        int j;
        while (i < size) {
            j = i - 1;
            String thisKey = i + "";
            String lastKey = j + "";
            if ((10 - i) > 0) {
                thisKey = "0" + i;
            }
            if ((10 - j) > 0) {
                lastKey = "0" + j;
            }
            thisValue = thisMap.get(thisKey);
            lastValue = thisMap.get(lastKey);
            if (thisValue == null || lastValue == null || ("0").equals(lastValue)) {
                percent = "0";
            } else {
                percent = new BigDecimal(thisValue).subtract(new BigDecimal(lastValue)).multiply(new BigDecimal(100)).divide(new BigDecimal(lastValue), 0, BigDecimal.ROUND_HALF_UP)+"";
            }
            lists.add(new Growth(thisKey, percent));
//            myMap.put(thisKey, percent);
            i++;
        }
        return lists;
    }

    /**
     * 环比生成工具
     *
     * @param thisMap 本次数据
     * @param size    数据大小 12月,24H,size
     * @return
     */
    public List<Growth> getAnnulusGrowth2(String year,Map<String, String> thisMap, int size) {

        List<Growth> lists = new LinkedList<Growth>();
        int i = 1;
        size = size + 1;
        String lastValue;
        String thisValue;
        String percent;
        int j;
        while (i < size) {
            j = i - 1;
            String thisKey = year ;
            String lastKey = year ;
            if ((10 - i) > 0) {
                thisKey += "-0" + i;
            }else {
                thisKey += "-" + i;
            }
            if ((10 - j) > 0) {
                lastKey += "-0" + j;
            }else {
                lastKey += "-" + j;
            }
            thisValue = thisMap.get(thisKey);
            lastValue = thisMap.get(lastKey);
            if (thisValue == null || lastValue == null || ("0").equals(lastValue)) {
                percent = "0";
            } else {
                percent = new BigDecimal(thisValue).subtract(new BigDecimal(lastValue)).multiply(new BigDecimal(100)).divide(new BigDecimal(lastValue), 0, BigDecimal.ROUND_HALF_UP)+"";
            }
            lists.add(new Growth(thisKey, percent));
//            myMap.put(thisKey, percent);
            i++;
        }
        return lists;
    }

    public List<Growth> getRatio(Map<String, String> oneMap, Map<String, String> twoMap, int size) {
//        Map<String, String> chain = new HashMap<>();
        List<Growth> lists = new LinkedList<Growth>();
        String s1;
        String s2;
        String percent;
        size = size + 1;
        int i = 1;
        while (i < size) {
            String key = i + "";
            if ((10 - i) > 0) {
                key = "0" + i;
            }

            s1 = oneMap.get(key);
            s2 = twoMap.get(key);
            if (s1 == null || s2 == null || s2.equals("0")) {
                percent = "-";
            } else {
                percent = (int) ((Double.parseDouble(s1) / Double.parseDouble(s2)) * 100) + "";
            }
            lists.add(new Growth(key, percent));

//            chain.put(key, percent);
            i++;
        }

        return lists;
    }

    /**
     * 季度计算,注意，这里的季度传入进来的值是   1,2,3,4  这种格式的才能使用，后期做统一规范
     * @param oneMap
     * @param twoMap
     * @param size
     * @return
     */
    public List<Growth> getRatioByQuarter(Map<String, String> oneMap, Map<String, String> twoMap, int size) {
        List<Growth> lists = new LinkedList<Growth>();
        String s1;
        String s2;
        String percent;
        int i = 1;
        while (i <= size) {
            String key = i + "";
            s1 = oneMap.get(key);
            s2 = twoMap.get(key);
            if (s1 == null || s2 == null || "0".equals(s2)) {
                percent = "-";
            } else {
                percent = (int) ((Double.parseDouble(s1) / Double.parseDouble(s2)) * 100) + "";
            }
            lists.add(new Growth(key, percent));
            i++;
        }
        return lists;
    }


    /**
     * wifi连接比值去空值
     *
     * @param ratio
     * @return
     */
    public List<GrowthSymbol> getRatioNew(List<Growth> ratio) {
        //剔除-的数据
        List<GrowthSymbol> notSymbol = new LinkedList<>();
        for (int i = 0; i < ratio.size(); i++) {
            if (!"-".equals(ratio.get(i).getPersent()) || ratio.get(i).getPersent() != "-") {
                GrowthSymbol gro = new GrowthSymbol();
                gro.setPersent(ratio.get(i).getPersent());
                gro.setTime(ratio.get(i).getTime());
                notSymbol.add(gro);
            }
        }
        return notSymbol;
    }

    //模拟百分之七十的人数
    public int getWifiLikePeople(String num) {
        Double count = 0d;
        count = Double.valueOf(num);
        double sum = count * 0.7;
        int i = (int) sum;
        return i;
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
     * 获取当前时间前几小时,精确到小时
     *
     * @param ihour
     * @return
     */
    public static String getBeforeHourTimeAtHOUR(int ihour) {
        String returnstr = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - ihour);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
        returnstr = df.format(c.getTime());
        return returnstr;
    }

    /**
     * 获取传入日期的 后一个小时
     *
     * @return returndays
     */
    public static String getAfterDay(String days) {
        Date dates = fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.HOUR_OF_DAY, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

    /**
     * 获取传入日期的 减一个小时
     *
     * @return returndays
     */
    public static String getAfterDelHour(String days) {
        Date dates = fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.HOUR_OF_DAY, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }
    /**
     * 获取传入日期的 减(加)小时数后的日期
     * days 日期
     * hours 小时数，整数表示添加，负数表示减少
     *
     * @return returndays
     */
    public static String getAfterDelHour(String days, int hours) {
        Date dates = fomatDate(days);
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.HOUR_OF_DAY, hours);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

    /**
     * 获取传入日期的 后一天
     *
     * @return returndays
     */
    public static String getAfterAddDay(String days) {
        Date dates = fomatDateTime(days);
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

    /**
     * 获取传入日期的 减一天(日期格式：yyyy-MM-dd)
     *
     * @return returndays
     */
    public static String getAfterDelDay(String days) {
        Date dates = fomatDateTime(days);
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

    /**
     * 获取传入日期的 减一天(日期格式：yyyyMMdd)
     *
     * @param days
     * @return
     */
    public static String getAfterDelDayType(String days) {
        Date dates = fomatDateTimeType(days);
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

    /**
     * 获取传入日期的 加一年
     *
     * @return
     */
    public static String getAfterAddYear(String year) {
        Date dates = fomatDateYear(year);
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.YEAR, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

    /**
     * 获取传入日期的 减一年
     *
     * @return
     */
    public static String getAfterSubYear(String year) {
        Date dates = fomatDateYear(year);
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.YEAR, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

    /**
     * 获取传入日期的 减一年
     *
     * @return
     */
    public static String getAfterYear(String year) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = null;
        try {
            dates = fmt.parse(year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.YEAR, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

    /**
     * 获取传入日期的 减一年
     *
     * @return
     */
    public static String getLastYear(String year) {
        DateFormat fmt = new SimpleDateFormat("yyyy");
        Date dates = null;
        try {
            dates = fmt.parse(year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.YEAR, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

    /**
     * 获取传入日期的 减一年(参数)
     *
     * @return
     */
    public static String getAfterYearMPram(String year) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM");
        Date dates = null;
        try {
            dates = fmt.parse(year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.YEAR, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
    }

    /**
     * 获取传入日期的 减一年(精确到月）
     *
     * @return
     */
    public static String getAfterYearM(String year) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = null;
        try {
            dates = fmt.parse(year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(dates);
        c.add(Calendar.YEAR, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");
        String preMonth = dateFormat.format(c.getTime());
        return preMonth;
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

    public static Date fomatDateTime(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date fomatDateTimeType(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date fomatDateYear(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //拼时间优化sql 开始
    public static String dateYearStart(String date) {
        String year = date + "-01-01 00:00:00";
        return year;
    }

    //拼时间优化sql 结束
    public static String dateYearEnd(String date) {
        String year = date + "-12-31 23:59:59";
        return year;
    }

    //拼时间优化sql 开始
    public static String dateMonthStart(String date) {
        String year = date + "-01 00:00:00";
        return year;
    }

    //拼时间优化sql 结束
    public static String dateMonthEnd(String date) {
        String year = date + "-31 23:59:59";
        return year;
    }

    //拼时间优化sql 开始
    public static String dateDayStart(String date) {
        String year = date + " 00:00:00";
        return year;
    }

    //拼时间优化sql 结束
    public static String dateDayEnd(String date) {
        String year = date + " 23:59:59";
        return year;
    }

    //当前时间
    public static String nowTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        return time;
    }

    //当前时间加一天
    public static String tomorrow() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//        String time = df.format(new Date());// new Date()为获取当前系统时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        Date time = calendar.getTime();
        String tomorrow = df.format(time);
        return tomorrow;

    }

    /**
     * 百分比取整数
     *
     * @param num
     * @return
     */
    public static String percent(String num) {
        String intNumber = null;
        if (num.equals("Infinity")) {
            intNumber = "1";
        } else if (num.equals("NaN")) {
            intNumber = "1";
        } else {
            intNumber = num.substring(0, num.indexOf("."));
            if (intNumber.equals("0")) {
                intNumber = "1";
            }
        }
        return intNumber;
    }

    /**
     * 百分比去负号
     *
     * @param num
     * @return
     */
    public static String subPercent(String num) {
        if (num.indexOf("-") != -1) {//去百分比负号
            String[] end = num.split("-");
            num = end[1];
        } else {
            return num;
        }
        return num;
    }


    /**
     * @param lastMap 上次统计map
     * @param thisMap 本次统计map
     * @param size    尺寸
     * @return 同比map get的key为redis中01月格式的数据
     */
    public List<Growth> getYearOnYearGrowthRedis(String date,Map<String, String> lastMap, Map<String, String> thisMap, int size) {
        List<Growth> lists = new LinkedList<Growth>();
        String s1;
        String s2;
        String percent;
        size = size + 1;
        int i = 1;
        while (i < size) {
            String key = i + "";
            if ((10 - i) > 0) {
                key = "0" + i + "";
            }

            s1 = lastMap.get(date+"-"+key);
            s2 = thisMap.get(date+"-"+key);
            if (s1 == null || s1.equals("0") || s2 == null) {
                percent = "0";
            } else {
                //percent = (int) ((Double.parseDouble(s2) - Double.parseDouble(s1)) / Double.parseDouble(s1) * 100.0) + "";
                percent = new BigDecimal(s2).subtract(new BigDecimal(s1)).multiply(new BigDecimal(100)).divide(new BigDecimal(s1), 0, BigDecimal.ROUND_HALF_UP)+"";
            }
            lists.add(new Growth(date+"-"+key, percent));
            i++;
        }

        return lists;
    }

    /**
     * @param lastMap 上次统计map
     * @param thisMap 本次统计map
     * @param size    尺寸
     * @return 同比map get的key为redis中01月格式的数据
     */
    public List<Growth> getYearOnYearGrowthRedisA(String date,Map<String, String> lastMap, Map<String, String> thisMap, int size) {
        List<Growth> lists = new LinkedList<Growth>();
        String s1;
        String s2;
        String percent;
        size = size + 1;
        int i = 1;
        while (i < size) {
            String key = i + "";
            if ((10 - i) > 0) {
                key = "0" + i + "";
            }

            s1 = lastMap.get(key);
            s2 = thisMap.get(key);
            if (s1 == null || s1.equals("0") || s2 == null) {
                percent = "0";
            } else {
                //percent = (int) ((Double.parseDouble(s2) - Double.parseDouble(s1)) / Double.parseDouble(s1) * 100.0) + "";
                percent = new BigDecimal(s2).subtract(new BigDecimal(s1)).multiply(new BigDecimal(100)).divide(new BigDecimal(s1), 0, BigDecimal.ROUND_HALF_UP)+"";
            }
//            lists.add(new Growth(date+"-"+key, percent));
            lists.add(new Growth(key, percent));
            i++;
        }

        return lists;
    }

    /**
     * auth tanggm
     * date 2017-07-25 10:26
     * desc 此方法提供不返回 “月” 字样，只返回月份数字
     * @param lastMap 上次统计map
     * @param thisMap 本次统计map
     * @param size    尺寸
     * @return 同比map get的key为redis中01月格式的数据
     */
    public List<Growth> getYearOnYearGrowthMonth(Map<String, String> lastMap, Map<String, String> thisMap, int size) {
        List<Growth> lists = new LinkedList<Growth>();
        String s1;
        String s2;
        String percent;
        size = size + 1;
        int i = 1;
        while (i < size) {
            String key = i + "";
            if ((10 - i) > 0) {
                key = "0" + i;
            }

            s1 = lastMap.get(key);
            s2 = thisMap.get(key);
            if (s1 == null || s1.equals("0") || s2 == null) {
                percent = "0";
            } else {
                percent = (int) ((Double.parseDouble(s2) - Double.parseDouble(s1)) / Double.parseDouble(s1) * 100.0) + "";
            }
            lists.add(new Growth(key, percent));
            i++;
        }

        return lists;
    }

    /**
     * 环比生成工具
     *
     * @param thisMap 本次数据
     * @param size    数据大小 12月,24H,size
     * @return 环比 get的key为redis中01月格式的数据
     */
    public List<Growth> getAnnulusGrowthRedis(String date,Map<String, String> thisMap, int size) {

        List<Growth> lists = new LinkedList<Growth>();
        int i = 1;
        size = size + 1;
        String lastValue;
        String thisValue;
        String percent;
        int j;
        while (i < size) {
            j = i - 1;
            String thisKey = i + "";
            String lastKey = j +"";
            if ((10 - i) > 0) {
                thisKey = "0" + i;
            }
            if ((10 - j) > 0) {
                lastKey = "0" + j;
            }
            thisValue = thisMap.get(date+"-"+thisKey);
            lastValue = thisMap.get(date+"-"+lastKey);
            if (thisValue == null || lastValue == null || ("0").equals(lastValue)) {
                percent = "0";
            } else {
                //percent = (int) (((Double.parseDouble(thisValue) - Double.parseDouble(lastValue)) / Double.parseDouble(lastValue)) * 100.0) + "";
                // 这里四舍五入，不要直接强转int
                percent = new BigDecimal(thisValue).subtract(new BigDecimal(lastValue)).multiply(new BigDecimal(100)).divide(new BigDecimal(lastValue), 0, BigDecimal.ROUND_HALF_UP)+"";
            }
            lists.add(new Growth(date+"-"+thisKey, percent));
            i++;
        }
        return lists;
    }

    /**
     * 环比生成工具
     *
     * @param thisMap 本次数据
     * @param size    数据大小 12月,24H,size
     * @return 环比 get的key为redis中01月格式的数据
     */
    public List<Growth> getAnnulusGrowthRedisA(String date,Map<String, String> thisMap, int size) {

        List<Growth> lists = new LinkedList<Growth>();
        int i = 1;
        size = size + 1;
        String lastValue;
        String thisValue;
        String percent;
        int j;
        while (i < size) {
            j = i - 1;
            String thisKey = i + "";
            String lastKey = j +"";
            if ((10 - i) > 0) {
                thisKey = "0" + i;
            }
            if ((10 - j) > 0) {
                lastKey = "0" + j;
            }
            thisValue = thisMap.get(thisKey);
            lastValue = thisMap.get(lastKey);
            if (thisValue == null || lastValue == null || ("0").equals(lastValue)) {
                percent = "0";
            } else {
                //percent = (int) (((Double.parseDouble(thisValue) - Double.parseDouble(lastValue)) / Double.parseDouble(lastValue)) * 100.0) + "";
                // 这里四舍五入，不要直接强转int
                percent = new BigDecimal(thisValue).subtract(new BigDecimal(lastValue)).multiply(new BigDecimal(100)).divide(new BigDecimal(lastValue), 0, BigDecimal.ROUND_HALF_UP)+"";
            }
//            lists.add(new Growth(date+"-"+thisKey, percent));
            lists.add(new Growth(thisKey, percent));
            i++;
        }
        return lists;
    }
    /**
     * 环比生成工具---不返回 “月” 字样
     * auth tanggm
     * date 2017-07-25 10:26
     * desc 此方法提供不返回 “月” 字样，只返回月份数字
     * @param thisMap 本次数据
     * @param size    数据大小 12月,24H,size
     * @return 环比 get的key为redis中01月格式的数据
     */
    public List<Growth> getAnnulusGrowthMonth(Map<String, String> thisMap, int size) {

        List<Growth> lists = new LinkedList<Growth>();
        int i = 1;
        size = size + 1;
        String lastValue;
        String thisValue;
        String percent;
        int j;
        while (i < size) {
            j = i - 1;
            String thisKey = i + "";
            String lastKey = j + "";
            if ((10 - i) > 0) {
                thisKey = "0" + i;
            }
            if ((10 - j) > 0) {
                lastKey = "0" + j;
            }
            thisValue = thisMap.get(thisKey);
            lastValue = thisMap.get(lastKey);
            if (thisValue == null || lastValue == null || ("0").equals(lastValue)) {
                percent = "0";
            } else {
                percent = (int) (((Double.parseDouble(thisValue) - Double.parseDouble(lastValue)) / Double.parseDouble(lastValue)) * 100.0) + "";
            }
            lists.add(new Growth(thisKey, percent));
//            myMap.put(thisKey, percent);
            i++;
        }
        return lists;
    }
}
