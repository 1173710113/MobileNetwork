package com.example.demo.util;

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
        df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return df.format(new Date());// new Date()为获取当前系统时间
    }
}
