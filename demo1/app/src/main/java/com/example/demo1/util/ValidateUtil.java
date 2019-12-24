package com.example.demo1.util;

public class ValidateUtil {

    /**
     * 检查字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if(str.equals("") || str == null) {
            return true;
        }
        return false;
    }
}
