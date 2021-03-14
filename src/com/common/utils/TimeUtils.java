package com.common.utils;

import com.ai.acctcomp.base.util.StringUtil;
import com.ailk.acctcomp.cache.CacheUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * description: 时间工具类
 *
 * @author: xiewy
 * @date: Created in 2020/9/18 4:45 下午
 * @version: V1.0
 */
public class TimeUtils {

    private static final String[] FORMAT_LEN = new String[]{null, null, null, null, "yyyy", null, "yyyyMM", "yyyy-MM", "yyyyMMdd", null, "yyyy-MM-dd", null, null, "yyyy-MM-dd HH", "yyyyMMddHHmmss", "yyyyMMddHHmmssS", "yyyy-MM-dd HH:mm", null, null, "yyyy-MM-dd HH:mm:ss", null, "yyyy-MM-dd HH:mm:ss.S"};
    private static final Map<Integer, Map<String, String>> formatMap = new HashMap();
    /**
     * description: <b>每个月的最后一天</b>
     *
     * @author xiewy
     * @date 2020/9/18 4:48 下午
     */
    private static final int[] MONTH_DAYS = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * 当前时间
     *
     * @param format 返回时间格式
     * @return
     * @throws Exception
     */
    public static final String getSysDate(String format) throws Exception {
        //校验日期格式
        String timestampFormat = getTimestampFormat(format);
        //当前时间
        long now = System.currentTimeMillis();
        return format(new Date(now), format);
    }

    /**
     * 当前日期 (默认格式 : yyyy-MM-dd)
     *
     * @return
     * @throws Exception
     */
    public static final String getSysDate() throws Exception {
        //当前时间
        long now = System.currentTimeMillis();
        return format(new Date(now), "yyyy-MM-dd");
    }

    /**
     * 日期格式化
     *
     * @param time   时间
     * @param format 日期格式
     * @return
     */
    public static final String format(Date time, String format) {
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        return sdf.format(time);
    }

    /**
     * 获取日期格式
     *
     * @param format
     * @return
     */
    public static final SimpleDateFormat getSimpleDateFormat(final String format) {
        try {
            return new SimpleDateFormat(format);
        } catch (Exception e) {
            try {
                throw new Exception("获取日期格式类发生错误！", e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 校验日期格式类型是否正确 或者 根据传入时间获取格式类型
     *
     * @param value
     * @return
     * @throws Exception
     */
    private static final String getTimestampFormat(String value) throws Exception {
        //判断传入的日期格式 value 在 FORMAT_LEN 中是否存在
        String format = null;
        try {
            format = FORMAT_LEN[value.length()];
        } catch (Exception e) {
            throw new Exception("未配置该日期格式[" + value + "]");
        }
        if (format == null) {
            throw new Exception("无法解析正确的日期格式[" + value + "]");
        } else {
            return format;
        }
    }

    /**
     * description: <b>获取传入时间月份的最后一天,返回格式保持一致</b>
     *
     * @param dateStr
     * @return java.lang.String
     * @author xiewy
     * @date 2020/9/18 4:49 下午
     */
    public static final String getLastDateOfMonth(String dateStr) {
        return getTheDateOfMonth(dateStr, 31);
    }

    private static final String getTheDateOfMonth(String dateStr, int day) {
        int yyyy = Integer.parseInt(dateStr.substring(0, 4));
        boolean hasSep = dateStr.indexOf(45) > 0;//45 : 减号/破折号( - )
        int mmIndex = hasSep ? 5 : 4;
        int mm = Integer.parseInt(dateStr.substring(mmIndex, mmIndex + 2));
        int maxDay = getLastDay(yyyy * 100 + mm);
        if (day > maxDay) {
            day = maxDay;
        }
        return yyyy + (hasSep ? "-" : "") + (mm > 9 ? mm : "0" + mm) + (hasSep ? '-' : "") + (day > 9 ? day : "0" + day);
    }

    /**
     * <p>指定月份的最后一天</p>
     *
     * @param yyyyMM
     * @return 28, 29, 30, 31
     */
    public static final int getLastDay(int yyyyMM) {
        int yyyy = yyyyMM / 100;
        int mm = yyyyMM % 100;
        if (mm != 2) {
            return MONTH_DAYS[mm - 1];
        } else {
            return yyyy % 400 != 0 && (yyyy % 100 == 0 || yyyy % 4 != 0) ? 28 : 29;
        }
    }

    public static final String dateAddMonth(String date, int months) throws Exception {
        return dateAddAmount((String) date, 2, months);
    }

    public static final String dateAddAmount(String dateStr, int field, int amount) throws Exception {
        return dateAddAmount(dateStr, field, amount, getTimestampFormat(dateStr));
    }

    public static final String dateAddAmount(String dateStr, int field, int amount, String format) throws Exception {
        String inFormat = getTimestampFormat(dateStr);
        Date date = parse(dateStr, inFormat);
        Date retDate = dateAddAmount((Date) date, field, amount);
        return format(retDate, format);
    }

    public static final Date dateAddAmount(Date date, int field, int amount) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(field, amount);
        return cd.getTime();
    }

    public static final Timestamp parse(String timeStr, String format) throws Exception {
        if (StringUtils.isBlank(timeStr)) {
            return null;
        } else {
            if (StringUtils.isBlank(format)) {
                format = getTimestampFormat(timeStr);
            }

            SimpleDateFormat sdf = getSimpleDateFormat(format);

            try {
                Date date = sdf.parse(timeStr);
                return new Timestamp(date.getTime());
            } catch (ParseException var4) {
                throw new Exception("timeutil-10001", var4);
            }
        }
    }

    public static final String dateAddDay(String date, int days) throws Exception {
        return dateAddAmount((String) date, 5, days);
    }

    public static int genCycle(int yyyymm, int months) {
        return addCycleId(yyyymm, months);
    }

    public static int addCycleId(int cycleId, int amount) {
        int year = cycleId / 100;
        int mon = cycleId % 100 - 1;
        int yearMonth = year * 12 + mon;
        yearMonth += amount;
        year = yearMonth / 12;
        mon = yearMonth % 12 + 1;
        return year * 100 + mon;
    }

    public static long compareDate(String date1, String date2, int field) throws Exception {
        return compareDate((Date) parse(date1), (Date) parse(date2), field);
    }

    public static final Timestamp parse(String timeStr) throws Exception {
        return parse(timeStr, (String) null);
    }

    public static long compareDate(Date date1, Date date2, int field) throws Exception {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return compareCalendar(c1, c2, field);
    }

    public static long compareCalendar(Calendar c1, Calendar c2, int field) throws Exception {
        long t1 = c1.getTimeInMillis();
        long t2 = c2.getTimeInMillis();
        switch (field) {
            case 1:
                return (long) (c1.get(1) - c2.get(1));
            case 2:
                return (long) (c1.get(1) * 12 - c2.get(1) * 12 + c1.get(2) - c2.get(2));
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            default:
                return t1 - t2;
            case 5:
                int rawOffset = c1.getTimeZone().getRawOffset();
                return (t1 + (long) rawOffset) / 86400000L - (t2 + (long) rawOffset) / 86400000L;
            case 10:
                return t1 / 3600000L - t2 / 3600000L;
            case 12:
                return t1 / 600000L - t2 / 60000L;
            case 13:
                return t1 / 1000L - t2 / 1000L;
        }
    }

    public static final int daysBetween(String dateStr1, String dateStr2) throws Exception {
        Date d1 = parse(dateStr1);
        Date d2 = parse(dateStr2);
        return daysBetween((Date)d1, (Date)d2);
    }

    public static final int daysBetween(Date date1, Date date2) throws Exception {
        return (int)compareDate((Date)date1, (Date)date2, 5);
    }
}
