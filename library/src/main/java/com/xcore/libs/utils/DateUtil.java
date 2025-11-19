package com.xcore.libs.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间日期处理
 * author: created by 闹闹 on 2022/5/16
 * version: v1.0.0
 */
public class DateUtil {

    private static String TAG = "DateUtil";

    /************** 时间**************/
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String TIME_STRING_DAY = "yyyy-MM-dd";
    public static final String TIME_STRING_DAY_CN = "yyyy年MM月dd日";
    public static final String TIME_STRING_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String TIME_STRING_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_STRING = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String TIME_STRING_NUMBER = "yyyyMMddHHmmss";
    public static final String DEFAULT_TIME_STRING_CN = "yyyy年MM月dd日 HH时mm分";
    public static final String DEFAULT_SECOND_STRING_CN = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String STRING_HOUR = "HH:mm";
    public static final String STRING_MINUTE = "mm:ss";
    public static final String STRING_SECOND = "HH:mm:ss";
    public static final String STRING_MINUTE_CN = "HH时mm分";
    public static final String STRING_SECOND_CN = "HH小时mm分ss秒";

    public interface CallBack {
        void loadData(String dateTime, String date, String time);
    }

    /**
     * 获取当前时间
     */
    public static void getCurDate(CallBack callBack) {
        String s = dateToString(new Date(), TIME_STRING_DAY);
        callBack.loadData(s + "00:00:00", s, "00:00:00");
    }

    public static void getCurDateTime(CallBack callBack) {
        String s = dateToString(new Date(), TIME_STRING_MINUTE);
        String[] s1 = s.split(" ");
        callBack.loadData(s, s1[0], s1[1]);
    }

    public static void getZhCurDateTime(CallBack callBack) {
        String s = dateToString(new Date(), DEFAULT_TIME_STRING_CN);
        String[] s1 = s.split(" ");
        callBack.loadData(s, s1[0], s1[1]);
    }

    /**
     * 获取当前时间
     */
    public static String toNumber(int num) {
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
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
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
            Log.d(TAG, "date转成long -" + currentTime);
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


    /**
     * @param date
     * @return
     */
    public static String formatDate(Date date, String time, boolean isAdd) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME_STRING_CN);

        String[] split = time.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        int second = Integer.parseInt(split[2]);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        if (isAdd) {
            rightNow.add(Calendar.HOUR_OF_DAY, hour);
            rightNow.add(Calendar.MINUTE, minute);
            rightNow.add(Calendar.SECOND, second);
        } else {
            rightNow.add(Calendar.HOUR_OF_DAY, -hour);
            rightNow.add(Calendar.MINUTE, -minute);
            rightNow.add(Calendar.SECOND, -second);
        }

        Date afterDate = rightNow.getTime();
        String reStr = sdf.format(afterDate);

        return reStr;
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

    /**
     * 格式化时间
     */
    public static String formatTime(long time) {
        String hours = new DecimalFormat("00").format(time / 3600);
        String minutes = new DecimalFormat("00").format(time % 3600 / 60);
        String seconds = new DecimalFormat("00").format(time % 60);
        return hours + ":" + minutes + ":" + seconds;
    }
}
