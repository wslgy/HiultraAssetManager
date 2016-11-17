package com.hiultra.assetManagerNeutral.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;

/**
 * 时间和日期的处理函数
 * 
 * @author Administrator
 *
 */
public class DateUtil {

    /**
     * 获取本地日期和时间
     * 
     * @return
     */
    public static String GetLocalDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取本地日期
     * 
     * @return
     */
    public static String GetLoaclDate() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);// 获取年份
        int month = calendar.get(Calendar.MONTH);// 获取月份
        int day = calendar.get(Calendar.DATE);// 获取日
        String str_time = year + "-" + (month + 1) + "-" + day;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);

        return str_time;
    }

    /**
     * 获取本地时间
     * 
     * @return
     */
    public static String GetLocalTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }
    
    /**
     * 将字符串形式的毫秒值转换为日期信息返回,格式为yyyy-MM-dd
     * @param msec 字符串格式的毫秒值
     * @return 转换出的日期信息,格式为yyyy-MM-dd
     */
    public static String parseMsecToDate(String msec) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(Long.parseLong(msec));
        return formatter.format(date);
    }

    /**
     * 格式化时间戳
     * 
     * @param timestamp
     * @return
     */
    public static String FormatTimeStamp(String timestamp, SimpleDateFormat format) {
        if (timestamp != null && !timestamp.isEmpty()) {
            if (timestamp.contains("Date")) {
                try {
                    int dateTimeLeft = timestamp.indexOf("(");
                    int dateTimeRight = timestamp.indexOf(")");
                    String unixTimeStamp = timestamp.substring(dateTimeLeft + 1, dateTimeRight);
                    long time = Long.parseLong(unixTimeStamp);
                    return format.format(time);
                } catch (IndexOutOfBoundsException e) {
                    Log.e("SDDKSD", "转换日期格式失败,截取日期字符串错误");
                    throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    Log.e("SDDKSD", "Unix时间戳错误");
                    return "";
                } catch (IllegalArgumentException e) {
                    Log.e("SDDKSD", "格式化Unix时间戳错误");
                    throw new NumberFormatException();
                }
            } else {
                long time = Long.parseLong(timestamp);
                return format.format(time);
            }
        }
        return timestamp;
    }
}
