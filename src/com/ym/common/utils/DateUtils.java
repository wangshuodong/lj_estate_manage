package com.ym.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ym.iadpush.manage.entity.WeekBean;

public class DateUtils {

    /**
     * dateDiff()方法的unit参数,以年为单位
     */
    public final static byte DIFF_YEAR = 0;

    /**
     * dateDiff()方法的unit参数,以月为单位
     */
    public final static byte DIFF_MONTH = 1;

    /**
     * dateDiff()方法的unit参数,以日为单位
     */
    public final static byte DIFF_DAY = 2;

    /**
     * dateDiff()方法的unit参数,以小时为单位
     */
    public final static byte DIFF_HOUR = 3;

    /**
     * dateDiff()方法的unit参数,以分钟为单位
     */
    public final static byte DIFF_MINUTE = 4;

    /**
     * dateDiff()方法的unit参数,以秒为单位
     */
    public final static byte DIFF_SECONDE = 5;

    /**
     * dateDiff()方法的unit参数,以毫秒为单位
     */
    public final static byte DIFF_MILLISECOND = 6;

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     * 
     * @param beginDate
     * @param endDate
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List lDate = new ArrayList();
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

    public static List<String> getDatesBetweenTwoDate(String startDate, String endDate) {
        List<String> list = new ArrayList<String>();
        list.add(startDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(startDate));
        boolean bContinue = true;
        while (bContinue) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (new Date(endDate).after(cal.getTime())) {
                list.add(getDate(cal.getTime(), "yyyy-MM-dd"));
            } else {
                break;
            }
        }

        return list;
    }


    public static boolean compare(String date1, String date2) {
        boolean isSuccess = false;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            if (d1.getTime() > d2.getTime()) {
                isSuccess = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return isSuccess;
    }

    /**
     * 取得当前日期对象
     * 
     * @return 返回java.util.Date日期对象
     */
    public static Date getCurDate() {
        return getCurCalendar().getTime();
    }

    /**
     * 获取当前时间日历对象
     * 
     * @return 返回java.util.Calendar日期对象
     */
    public static Calendar getCurCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 取得当前时间,格式为HH:MM:SS
     * 
     * @return 返回当前时间
     */
    public static String getCurTime() {
        return getDate(getCurDate(), "HH:mm:ss");
    }

    /**
     * 取得当前日期的字符串表示,格式为 yyyy-MM-dd
     * 
     * @return 返回日期的字符串表示
     */
    public static String getDate() {
        return getDate(getCurDate(), "yyyy-MM-dd");
    }

    /**
     * 取得7天前的日期
     */
    public static String get7DayBeforDate() {

        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) - 7);

        return getDate(now.getTime(), "yyyy-MM-dd");
    }

    /**
     * 方法描述：获取N天之前的日期
     */
    public static String getNDayBeforDate(int number) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) - number);
        return getDate(now.getTime(), "yyyy-MM-dd");
    }

    /**
     * 取得1天前的日期
     */
    public static String get1DayBeforDate() {

        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) - 1);

        return getDate(now.getTime(), "yyyy-MM-dd");
    }

    /**
     * 取得7天前的日期
     */
    public static String get30DayBeforDate() {

        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) - 30);

        return getDate(now.getTime(), "yyyy-MM-dd");
    }

    public static int getDay(String startDay, String endDay) {

        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = format.parse(startDay);
            endDate = format.parse(endDay);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return (int) ((endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000));
    }

    /**
     * 获取一小时以前的时刻
     */
    public static Date getHourBeforeTime(int hour) {

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) - hour);
        return now.getTime();
    }

    /**
     * 获取当前日期时间字符串,格式为 yyyy-MM-dd hh:mm:ss
     * 
     * @return 返回当前字符串
     */
    public static String getDatetime() {
        return getDate(getCurDate(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将指定Date类型转换成指定格式的字符串,格式串参见类注释
     * 
     * @param date
     * 日期方式
     * @param format
     * 指定的格式,当format为NULL或空串时,<BR>
     * 默认为 yyyy-MM-dd 格式
     * @return 当date为NULL时,返回空串
     */
    public static String getDate(Date date, String format) {

        String dtstr = "";
        if (date == null) {
            return dtstr;
        }

        if (format == null || "".equals(format.trim())) {
            format = "yyyy-MM-dd";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        dtstr = sdf.format(date);
        return (dtstr == null ? "" : dtstr);

    }

    public static String getDate(Date date) {
        return getDate(date, "yyyy-MM-dd");
    }

    /**
     * 取得标准格式的日期: HH:mm:ss
     * 
     * @param c
     * 日期对象
     * @return 返回指定时间的小时:分钟:秒数
     */
    public static String getTime(Calendar c) {
        return getDate(c.getTime(), "HH:mm:ss");
    }

    /**
     * 取得当前年份
     * 
     * @return 四位年份 yyyy
     */
    public static String getYear() {
        Calendar c = getCurCalendar();
        return "" + c.get(Calendar.YEAR);
    }

    public static String getYearMonth() {
        Calendar c = getCurCalendar();
        String str1 = "" + (c.get(Calendar.YEAR));
        String str2 = "" + (c.get(Calendar.MONTH) + 1);

        str1 = str1 + "." + str2;
        return str1;
    }

    /**
     * 取得当前月份
     * 
     * @return
     */
    public static String getMonth() {
        Calendar c = getCurCalendar();
        return "" + (c.get(Calendar.MONTH) + 1);
    }

    public static String getLastMothEnd() {

        Calendar c = getCurCalendar();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);

        return formartRandomDate(c.getTime(), "yyyy-MM-dd");
    }

    public static String getLastMothStart() {

        Calendar c = getCurCalendar();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);

        return formartRandomDate(c.getTime(), "yyyy-MM-dd");
    }

    public static String getThisMothStart() {

        Calendar c = getCurCalendar();
        c.set(Calendar.DAY_OF_MONTH, 1);

        return formartRandomDate(c.getTime(), "yyyy-MM-dd");
    }

    /**
     * 取得当前日
     * 
     * @return
     */
    public static String getDay() {
        Calendar c = getCurCalendar();
        return "" + (c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 取得当前星期数 1,2,3,4,5,6,7 代表 星期一.....
     * 
     * @return
     */
    public static String getWeek() {
        Calendar c = getCurCalendar();
        int week = c.get(Calendar.DAY_OF_WEEK);
        if (week > 1) {
            week--;
        } else {
            week = 7;
        }
        return "" + week;
    }

    /**
     * 将指定字串转换按指定格式转换成java.util.Date类型
     * 
     * @param dateStr
     * 日期字串
     * @param format
     * 指定的格式,当format为NULL或空串时,<BR>
     * 默认为 yyyy-MM-dd 格式
     * @return 当dateStr 为null或者转换出错时,返回NULL值
     * @throws RuntimeException
     * 日期格式与格式串不一致时，抛出RuntimeException
     */
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        if (dateStr == null || "".equals(dateStr.trim())) {
            return getCurDate();
        }
        if (format == null || "".equals(format.trim())) {
            format = "yyyy-MM-dd";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException ex) {
            throw new RuntimeException("日期格式与格式串不一致", ex);
        }

        return date;
    }

    /**
     * 将指定字串转换按指定格式转换成java.util.Date类型
     * 
     * @param dateStr
     * 日期字串 yyyy-MM-dd 格式
     * @return 当dateStr 为null或者转换出错时,返回NULL值
     * @throws RuntimeException
     * 日期格式与格式串不一致时，抛出RuntimeException
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * 将指定字串转换按指定格式转换成java.util.Date类型
     * 
     * @param dateStr
     * 日期字串 yyyy-MM-dd HH:mm:ss 格式
     * @return 当dateStr 为null或者转换出错时,返回NULL值
     * @throws RuntimeException
     * 日期格式与格式串不一致时，抛出RuntimeException
     */
    public static Date paseDatetime(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将java.util.Date转换成 java.sql.Date类型
     * 
     * @param date
     * java.util.Date对象
     * @return java.sql.Date对象,如果date为NULL,则返回NULL值
     */
    public static java.sql.Date parseSQLDate(Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    /**
     * 将java.util.Date转换成 java.sql.Timestamp类型
     * 
     * @param date
     * java.util.Date对象
     * @return ava.sql.Timestamp对象,如果date为NULL,则返回NULL值
     */
    public static Timestamp parseTimestamp(Date date) {

        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());

    }

    /**
     * 得到由c指定的日期所在月的开始日期
     * 
     * @param c
     * @return
     */
    public static Calendar getMonthBegin(Calendar c) {
        Calendar r = getCurCalendar();
        r.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
        return (r);
    }

    /**
     * 得到由c指定的日期所在月的结束日期
     * 
     * @param c
     * @return
     */
    public static Calendar getMonthEnd(Calendar c) {
        Calendar r = getCurCalendar();
        r.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 1, 23, 59, 59);
        r.add(Calendar.DATE, -1);
        return (r);
    }

    /**
     * 将指定时间串转换成日期时间对象
     * 
     * @param dateStr
     * 时间串,可以是yyyy-MM-dd格式 或者 yyyy-MM-dd HH:mm:ss 格式
     * @return 返回指定时间的Calendar对象
     * @throws NullPointerException
     * 当日期时间格式不正确时
     */
    public static Calendar parseCalendar(String dateStr) {
        Date dt = null;
        dt = parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
        if (dt == null) {
            dt = parseDate(dateStr);
        }
        Calendar c = getCurCalendar();
        c.setTime(dt);
        return (c);
    }

    /**
     * @see #dateDiff(Calendar, Calendar, byte)
     * 
     * @return 相差时差
     * 
     */
    public static long dateDiff(String dateFrom, String dateTo, byte unit) {
        Calendar from = parseCalendar(dateFrom);
        Calendar to = parseCalendar(dateTo);
        return dateDiff(from, to, unit);
    }

    /**
     * @see #dateDiff(Calendar, Calendar, byte)
     * 
     * @param dateFrom
     * 开始时间
     * @param dateTo
     * 结束时间
     * @param unit
     * 单位
     * @return
     */
    public static long dateDiff(Date dateFrom, Date dateTo, byte unit) {
        Calendar from = Calendar.getInstance();
        from.setTime(dateFrom);
        Calendar to = Calendar.getInstance();
        to.setTime(dateTo);
        return dateDiff(from, to, unit);
    }

    /**
     * 返回2个日期之间的差距 unit是时间计算的单位,为本类常量DIFF_&lgt;XXXX&rgt;中的任一类型
     * 
     * @param dateFrom
     * 开始时间的时间串,可以是yyyy-MM-dd格式 或者 yyyy-MM-dd HH:mm:ss 格式
     * @param dateTo
     * 结束时间的时间串,可以是yyyy-MM-dd格式 或者 yyyy-MM-dd HH:mm:ss 格式
     * @param unit
     * 是时间计算的单位,为以下值中的任一值:<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;DIFF_YEAR &nbsp;以年为单位&nbsp;<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;DIFF_MONTH &nbsp;以月为单位&nbsp;<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;DIFF_DAY &nbsp;以日为单位&nbsp;<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;DIFF_HOUR &nbsp;以小时为单位&nbsp;<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;DIFF_MINUTE &nbsp;以分钟为单位&nbsp;<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;DIFF_SECONDE &nbsp;以秒为单位&nbsp;<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;DIFF_MILLISECOND &nbsp;以毫秒为单位&nbsp;
     * 
     * @return 相差时差
     */
    @SuppressWarnings("static-access")
    public static long dateDiff(Calendar dateFrom, Calendar dateTo, byte unit) {
        long diff = dateTo.getTimeInMillis() - dateFrom.getTimeInMillis();
        long interval = 0;
        switch (unit) {
            case 0: {
                Calendar from = dateFrom;
                Calendar to = dateTo;
                interval = to.get(to.YEAR) - from.get(from.YEAR);
                from.set(from.YEAR, to.get(to.YEAR));
                if (from.after(to)) {
                    if (interval < 0) {
                        interval++;
                    } else {
                        interval--;
                    }
                }
                break;
            }
            case 1: {
                int year = dateTo.get(dateTo.YEAR) - dateFrom.get(dateFrom.YEAR);
                int month = dateTo.get(dateTo.MONTH) - dateFrom.get(dateFrom.MONTH);
                Calendar from = dateFrom;
                Calendar to = dateTo;
                from.set(from.YEAR, dateTo.get(dateTo.YEAR));
                from.set(from.MONTH, dateTo.get(dateTo.MONTH));
                interval = year * 12 + month;
                if (from.after(to)) {
                    if (interval < 0) {
                        interval++;
                    } else {
                        interval--;
                    }
                }
                break;
            }
            case 2:
                interval = (int) (diff / (1000 * 60 * 60 * 24));
                break;

            case 3:
                interval = (int) (diff / (1000 * 60 * 60));
                break;

            case 4:
                interval = (int) (diff / (1000 * 60));
                break;

            case 5:
                interval = (int) (diff / 1000);
                break;

            default:
                interval = diff;
        }
        return interval;
    }

    /**
     * 自由串格式化日期串,对于下列表中的字符支持\转义<br>
     * 例如:<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;\yyyy 结果为 y08y (原因为第一个被转义,剩下的字串只能构造成两位年份)<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;\y 结果为 y <br>
     * &nbsp;&nbsp;&nbsp;&nbsp;\yyyyy 结果为 y2008 <br>
     * &nbsp;&nbsp;&nbsp;&nbsp;\yyyyyy 结果为 y2008y <br>
     * 其它字符如此类同
     * 
     * @param date
     * 日期对象
     * @param strFormat
     * 格式串,含义如下,注意大小写区分:<br>
     * 
     * <table border=0 cellspacing=3 cellpadding=2 style='font-size:10pt;border:1px solid #000' summary="Chart shows pattern letters, date/time component, presentation, and examples.">
     * <tr bgcolor="#ccccff">
     * <th align=left>字符表达式
     * <th align=left>日期或时间表达含义
     * <th align=left>例子
     * <tr>
     * <td><code>yyyy</code>
     * <td>四位年
     * <td><code>2000, 2009</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>yy</code>
     * <td>两位年
     * <td><code>00, 09</code>
     * <tr>
     * <td><code>MM</code>
     * <td>两位月
     * <td><code>07 , 23</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>M</code>
     * <td>普通月
     * <td><code>7 , 23</code>
     * <tr>
     * <td><code>dd</code>
     * <td>两位天
     * <td><code>03, 12</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>d</code>
     * <td>普通天
     * <td><code>3, 12</code>
     * <tr>
     * <td><code>hh</code>
     * <td>两位小时
     * <td><code>03, 12</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>h</code>
     * <td>普通小时
     * <td><code> 3, 12</code>
     * <tr>
     * <td><code>mm</code>
     * <td>分
     * <td><code>03 , 12</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>m</code>
     * <td>分
     * <td><code>3 , 12</code>
     * <tr>
     * <td><code>ss</code>
     * <td>秒
     * <td><code>03 , 12</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>s</code>
     * <td>秒
     * <td><code>3 , 12</code>
     * <tr>
     * <td><code>SSS</code>
     * <td>三位微秒
     * <td><code>003, 012 , 199</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>S</code>
     * <td>微秒
     * <td><code>3, 12 , 199</code>
     * <tr>
     * <td><code>F</code>
     * <td>周几 ,数值
     * <td><code>3</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>E</code>
     * <td>周几 ,文本
     * <td><code>星期三, Tuesday</code>
     * <tr>
     * <td><code>a</code>
     * <td>Am/pm 标识
     * <td><code> PM, 下午</code>
     * </table>
     * @since 1.0.1
     * @return 如果date 或者 strFormat 为null,则返回空串，否则返回指定格式串
     */
    public static String formartRandomDate(Date date, String strFormat) {
        if (date == null || strFormat == null) {
            return "";
        }

        String key = strFormat;
        key = key.replaceAll("(?<!\\\\)yyyy", getDate(date, "yyyy"));
        key = key.replaceAll("(?<!\\\\)yy", getDate(date, "yy"));
        key = key.replaceAll("\\\\y", "y");

        key = key.replaceAll("(?<!\\\\)MM", getDate(date, "MM"));
        key = key.replaceAll("(?<!\\\\)M", getDate(date, "M"));
        key = key.replaceAll("(?<!\\\\)mm", getDate(date, "mm"));
        key = key.replaceAll("(?<!\\\\)m", getDate(date, "m"));
        key = key.replaceAll("\\\\(M|m)", "$1");

        key = key.replaceAll("(?<!\\\\)dd", getDate(date, "dd"));
        key = key.replaceAll("(?<!\\\\)d", getDate(date, "d"));
        key = key.replaceAll("\\\\d", "d");

        key = key.replaceAll("(?<!\\\\)hh", getDate(date, "hh"));
        key = key.replaceAll("(?<!\\\\)h", getDate(date, "h"));
        key = key.replaceAll("(?<!\\\\)HH", getDate(date, "HH"));
        key = key.replaceAll("(?<!\\\\)H", getDate(date, "H"));
        key = key.replaceAll("\\\\(H|h)", "$1");

        key = key.replaceAll("(?<!\\\\)ss", getDate(date, "ss"));
        key = key.replaceAll("(?<!\\\\)s", getDate(date, "s"));
        key = key.replaceAll("(?<!\\\\)SSS", getDate(date, "SSS"));
        key = key.replaceAll("(?<!\\\\)SS", getDate(date, "SS"));
        key = key.replaceAll("(?<!\\\\)s", getDate(date, "S"));
        key = key.replaceAll("\\\\(S|s)", "$1");

        key = key.replaceAll("(?<!\\\\)F", getDate(date, "F"));
        key = key.replaceAll("\\\\F", "F");
        key = key.replaceAll("(?<!\\\\)E", getDate(date, "E"));
        key = key.replaceAll("\\\\E", "E");
        key = key.replaceAll("(?<!\\\\)a", getDate(date, "a"));
        key = key.replaceAll("\\\\a", "a");

        return key;
    }

    public static Date getSpecifiedDayBefore(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        return c.getTime();
    }

    public static Date addWeek(Date date, int week) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.WEEK_OF_YEAR, week);

        return c.getTime();
    }

    public static class A {

        private String status;

        /**
         * @return Returns the status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status
         * The status to set.
         */
        public void setStatus(String status) {
            this.status = status;
        }
    }

    /**
     * 查询以此计算前几天的日期
     * 
     * @param d
     * @return
     */
    public static String getBeforeDate(int d) {

        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) - d);

        return getDate(now.getTime(), "yyyy-MM-dd");
    }

    /**
     * 计算日期date增加day天后的日期
     * 
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.DAY_OF_MONTH, day);

        return c.getTime();
    }

    /**
     * 获取上一月第一天
     * 
     * @return
     */
    public static String getLastMonFirtDay() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String months = "";
        String days = "";
        if (month > 1) {
            month--;
        } else {
            year--;
            month = 12;
        }
        if (!(String.valueOf(month).length() > 1)) {
            months = "0" + month;
        } else {
            months = String.valueOf(month);
        }
        if (!(String.valueOf(day).length() > 1)) {
            days = "0" + day;
        } else {
            days = String.valueOf(day);
        }
        String firstDay = "" + year + "-" + months + "-01";
        String[] lastMonth = new String[2];
        lastMonth[0] = firstDay;
        return firstDay;
    }

    /**
     * 获取上一月最后一天
     * 
     * @return
     */
    public static String getLastMonLastDay() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String months = "";
        String days = "";
        if (month > 1) {
            month--;
        } else {
            year--;
            month = 12;
        }
        if (!(String.valueOf(month).length() > 1)) {
            months = "0" + month;
        } else {
            months = String.valueOf(month);
        }
        if (!(String.valueOf(day).length() > 1)) {
            days = "0" + day;
        } else {
            days = String.valueOf(day);
        }
        String lastDay = "" + year + "-" + months + "-" + days;
        String[] lastMonth = new String[2];
        lastMonth[1] = lastDay;
        return lastDay;
    }

    public static WeekBean lastWeek(int week) {
        WeekBean bean = new WeekBean();
        Date date = new Date();

        int wday = date.getDay();// 周几

        // 获取上周最后一天 +1
        String lastWeekEnd = getBeforeDate(wday + (week - 1) * 7);
        // 获取上周第一天开始日期
        String lastWeekStart = getBeforeDate(wday + 7 * week - 1);

        bean.setStartDate(lastWeekStart);
        bean.setEndDate(lastWeekEnd);
        return bean;
    }


    public static String getFirstDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
        return simpleFormate.format(calendar.getTime());
    }


    public static String getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        // calendar.set( Calendar.DATE, 1);
        // calendar.roll(Calendar.DATE, - 1);
        SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
        return simpleFormate.format(calendar.getTime());
    }

}
