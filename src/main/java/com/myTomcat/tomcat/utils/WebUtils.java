package com.myTomcat.tomcat.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class WebUtils {
    public static int parseInt(String strNum,int defaultNum){
        try {
            return Integer.parseInt(strNum);
        }catch (NumberFormatException e){
            System.out.println("失败");
        }
        return defaultNum;
    }
    //判断uri是不是html文件
    public static boolean isHtml(String uri){
        return uri.endsWith(".html");
    }

    //根据传进来的文件名，来读取该文件,返回
    public static String readHtml(String fileName){
        String path= Objects.requireNonNull(WebUtils.class.getResource("/")).getPath();
        String buf;
        StringBuilder stringBuilder=new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader
                    (new FileReader("C:\\Users\\小马马\\Desktop\\jw\\hsptomcat\\target\\classes\\"+fileName));

            while ((buf=bufferedReader.readLine())!=null){
                stringBuilder.append(buf);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
