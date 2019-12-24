package com.example.demo1.util;

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
}
