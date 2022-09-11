package com.myTomcat.utils;

public class WebUtils {
    public static int parseInt(String strNum,int defaultVal){
        //将字符串转为int,如果转换不成功，返回defaultVal
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println("转换失败，爬！");
        }
        return defaultVal;
    }





}
