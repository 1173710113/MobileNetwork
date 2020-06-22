package com.example.demo1.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {
    /**
     * 获得当前系统时间YYYY-MM-DD HH:mm:ss
     * @return
     */
    public static String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//设置时区为上海
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    /**
     * 将date转为字符串
     * @param date
     * @return
     */
    public static String parseTime(Date date) {
        if(date == null)
        {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//设置时区为上海
        return df.format(date);
    }

    /**
     * 判断时间是否晚于当前时间
     * @param dateStr
     * @return true如果时间晚于当前时间
     */
    public static boolean isAfter(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//设置时区为上海
        try {
            Date date = df.parse(dateStr);
            Date now = new Date();
            return date.after(now);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Date parseDate(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
