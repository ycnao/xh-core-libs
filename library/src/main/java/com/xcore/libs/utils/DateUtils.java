package com.xcore.libs.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * 时间日期处理
 * author: created by 闹闹 on 2022/5/16
 * version: v1.0.0
 */
public class DateUtils {

    private static String TAG = "DateUtil";

    /************** 时间**************/
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String TIME_STRING_DAY = "yyyy-MM-dd";
    public static final String TIME_STRING_DAY_CN = "yyyy年MM月dd日";
    public static final String TIME_STRING_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String TIME_STRING_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_STRING_NUMBER = "yyyyMMddHHmmss";
    public static final String TIME_STRING_YYYY = "yyyy/MM/dd";
    public static final String DEFAULT_TIME_STRING = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DEFAULT_TIME_STRING_CN = "yyyy年MM月dd日 HH时mm分";
    public static final String DEFAULT_TIME_STRING_CH = "yyyy年MM月dd日 HH:mm";
    public static final String STRING_MONTH = "MM月dd日";
    public static final String STRING_HOUR = "HH:mm";
    public static final String STRING_MINUTE = "mm:ss";
    public static final String STRING_SECOND = "HH:mm:ss";
    public static final String STRING_MINUTE_CN = "HH时mm分";
    public static final String STRING_SECOND_CN = "HH小时mm分ss秒";
    public static final String STRING_DATE_WEEK = "yyyy-MM-dd EEEE";

    public interface CallBack {
        void loadData(String dateTime, String date, String time);
    }

    /**
     * 获取当前时间
     */
    public static void getCurDateTime(CallBack callBack) {
        String s = dateToString(new Date(), TIME_STRING_MINUTE);
        String[] s1 = s.split(" ");
        callBack.loadData(s, s1[0], s1[1]);
    }

    public static String getCurDateTime() {
        return dateToString(new Date(), TIME_STRING_SECOND);
    }

    public static String getToDay() {
        return DateUtils.dateToString(new Date(), DateUtils.TIME_STRING_DAY);
    }

    /**
     * 获取当前时间
     */
    public static String toNum(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return "" + num;
        }
    }


    /**
     * 系统值转换为日期
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static String timeToString(String time) throws ParseException {
        int timeInt = Integer.parseInt(time);
        int timeLong = timeInt * 60 * 1000;
        return longToString(timeLong, "mm:ss");
    }


    public static long timeToLong(String time) {
        int timeInt = Integer.parseInt(time);
        return (long) timeInt * 60 * 1000;
    }


    /**
     * date类型转换为String类型
     *
     * @param data
     * @param formatType
     * @return
     */
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * long类型转换为String类型
     *
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType) {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    /**
     * string类型转换为date类型
     *
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * long转换为Date类型
     *
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType) {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        return stringToDate(sDateTime, formatType);
    }

    /**
     * String类型转换为long类型
     *
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
//            Log.d(TAG, "date转成long -" + currentTime);
            return currentTime;
        }
    }

    /**
     * date类型转换为long类型
     *
     * @param date
     * @return
     */
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 一种格式转换成另一种格式
     *
     * @param date
     * @param form
     * @param to
     * @return
     */
    public static String stringToString(String date, String form, String to) {
        long parseLong = stringToLong(date, form);
        return longToString(parseLong, to);
    }

    public static String monthToInt(String month) {
        if (month.equals("January")) {
            return "01";
        } else if (month.equals("February")) {
            return "02";
        } else if (month.equals("March")) {
            return "03";
        } else if (month.equals("April")) {
            return "04";
        } else if (month.equals("May")) {
            return "05";
        } else if (month.equals("June")) {
            return "06";
        } else if (month.equals("July")) {
            return "07";
        } else if (month.equals("August")) {
            return "08";
        } else if (month.equals("September")) {
            return "09";
        } else if (month.equals("October")) {
            return "10";
        } else if (month.equals("November")) {
            return "11";
        } else if (month.equals("December")) {
            return "12";
        } else {
            return "01";
        }
    }

    public static String dateTimeToMMdd(String dateTime) {
        if (dateTime.isEmpty()) {
            return "";
        }
        if (!dateTime.contains(" ")) {
            return "";
        }
        return stringToString(dateTime, TIME_STRING_SECOND, "MM/dd/yyyy HH:mm");
    }

    public static String formatEnglish(String datetime) {
        if (datetime == null) {
            return "";
        }
        Date date = stringToDate(datetime, TIME_STRING_SECOND);
//        DateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
        DateFormat sdf = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
        return sdf.format(date);
    }

    public static String formatEnglish2(String datetime, String format) {
        if (datetime == null || format == null) {
            return "";
        }
        Date date = stringToDate(datetime, format);
//        DateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
        DateFormat sdf = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
        return sdf.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss TO  MMM dd HH:mm
     *
     * @param datetime
     * @return
     */
    public static String formatEnglish3(String datetime) {
        if (datetime == null) {
            return "";
        }
        Date date = stringToDate(datetime, TIME_STRING_SECOND);
        String s = dateToString(date, "HH:mm");
        DateFormat sdf = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
        String format = sdf.format(date);
        return format + " " + s;
    }

    /**
     * 00:00 to 00:00AM or 00:00PM
     *
     * @param time
     * @param format
     * @return
     */
    public static String timeToAm(String time, String format) {
        if (time == null || format == null) {
            return "";
        }
        Date date = stringToDate(time, format);
//        DateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
        DateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        return sdf.format(date);
    }

    /**
     * 00:00AM or 00:00PM to 00:00
     *
     * @param amTime
     * @return
     */
    public static String amToTime(String amTime) {
        if (amTime == null) {
            return "";
        }
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

        try {
            Date parse = date12Format.parse(amTime);
            return date24Format.format(parse);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("NewApi")
    public static int formatDate(String start, String end) {
        if (start == null || end == null) {
            return 0;
        }
        String startDate = stringToString(start, TIME_STRING_SECOND, TIME_STRING_DAY);
        String endDate = stringToString(end, TIME_STRING_SECOND, TIME_STRING_DAY);
        LocalDate date1 = LocalDate.parse(startDate);
        LocalDate date2 = LocalDate.parse(endDate);
        long days = ChronoUnit.DAYS.between(date1, date2);

        return (int) days;
    }

    @SuppressLint("NewApi")
    public static boolean beforeDate(String start, String end) {
        String startDate = stringToString(start, TIME_STRING_SECOND, TIME_STRING_DAY);
        String endDate = stringToString(end, TIME_STRING_SECOND, TIME_STRING_DAY);
        LocalDate date1 = LocalDate.parse(startDate);
        LocalDate date2 = LocalDate.parse(endDate);
        //>
        return date1.isBefore(date2);
    }

    @SuppressLint("NewApi")
    public static boolean beforeDate2(String start, String end) {
        LocalDate date1 = LocalDate.parse(start);
        LocalDate date2 = LocalDate.parse(end);
        //>
        return date1.isBefore(date2);
    }

    @SuppressLint("NewApi")
    public static boolean afterDate(String start, String end) {
        String startDate = stringToString(start, TIME_STRING_SECOND, TIME_STRING_DAY);
        String endDate = stringToString(end, TIME_STRING_SECOND, TIME_STRING_DAY);
        LocalDate date1 = LocalDate.parse(startDate);
        LocalDate date2 = LocalDate.parse(endDate);
        //<
        return date1.isAfter(date2);
    }

    /*
     * 计算两个时间间隔
     */
    public static String formatMinute(String startTime, String endTime) {
        DateFormat dateFormat = new SimpleDateFormat(TIME_STRING_MINUTE);
        Date beString = null;
        Date enString = null;
        try {
            beString = dateFormat.parse(startTime);
            enString = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long sub = Math.abs(beString.getTime() - enString.getTime());
        String minute = (sub / 1000 / 60) + "";

        return minute;
    }

    public static String getAge(String birth) {
        String[] split = birth.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        return getAge(year, month, day);
    }

    // 年龄计算函数
    public static String getAge(int year, int month, int day) {
        boolean b = true;
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DAY_OF_MONTH);
        int years, months = 0, days = 0;

        if (dayNow < day) {
            dayNow = dayNow + 30;
            monthNow = monthNow - 1;
        }

        if (monthNow < month) {
            monthNow = monthNow + 12;
            yearNow = yearNow - 1;
        }

        if (yearNow < year) {
            b = false;
            return "";
        }

        if (b) {
            years = yearNow - year;
            months = monthNow - month;
            days = dayNow - day;
            String result = years + "岁零" + months + "个月" + days + "天";
            return "" + years;
        }
        return "";
    }

    /**
     * 把毫秒转换成：1:20:30这里形式
     *
     * @param timeMs
     * @return
     */
    public static String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;

        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);

        return mFormatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean isToday(String day) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = stringToCalendar(day);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    public static Calendar stringToCalendar(String day) {
        Calendar cal = Calendar.getInstance();
        Date date = stringToDate(day, DateUtils.TIME_STRING_SECOND);
        cal.setTime(date);
        return cal;
    }

    public static List<String> getNumList(int start, int length) {
        List<String> list = new ArrayList<>();
        for (int i = start; i < length; i++) {
            String num = toNum(i);
            list.add(num);
        }
        return list;
    }
}
