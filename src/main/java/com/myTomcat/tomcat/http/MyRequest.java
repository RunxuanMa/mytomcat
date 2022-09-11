package com.myTomcat.tomcat.http;


import java.io.*;
import java.util.HashMap;

/**
 *  1. HttpServlet 作用是封装 http请求数据
 *  例: get /CalServlet?num1=10&num2=30
 *  2. 比如 method(get), uri(/CalServlet), 还有参数列表num1=10&num2=30
 *  3.  HttpServlet 作用等价于 servlet 里面的 HttpServletRequest
 */

public class MyRequest {
    private String method;
    private String URI;
    private InputStream inputStream=null;

    @Override
    public String toString() {
        return "HspRequest{" +
                "method='" + method + '\'' +
                ", URI='" + URI + '\'' +
                ", parametersMapping=" + parametersMapping +
                '}';
    }

    //存放参数列表 (这里采用哈希表)
    private HashMap<String ,String> parametersMapping=new HashMap<>();

    //构造器

    /**
     *  对HTTP请求进行封装！！！
     *
     */
    private void init(){
        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader
                    (inputStream,"utf-8"));

            //读取 先读第一行
            //GET /calServlet?num1=11&num2=4514 HTTP/1.1

            String requestLine=bufferedReader.readLine();
            String[]requestArray=requestLine.split(" ");

            //字符串被分割成了3个部分 GET /calServlet?num1=11&num2=4514 HTTP/1.1

            //得到方法
            method= requestArray[0];
            //解析得到 URI (/calServlet)

            //1. 先看URI 有没有参数列表 (带有?标志有参数列表)
            int index=requestArray[1].indexOf("?");
            if(index==-1){ //说明没有参数列表
                URI=requestArray[1];
            }else {
                URI=requestArray[1].substring(0,index);

                //并得到参数列表
                String paraMeters=requestArray[1].substring(index+1);
                //num1=11&num2=4514
                String[]parametersPair=paraMeters.split("&");
                // num1=11    num2=4514

                if (null!=parametersPair&&!"".equals(parametersPair)){

                    //再次分割
                    for(String parameter:parametersPair){
                        String []parameterVal= parameter.split("=");
                        //num1 11       num2  4514
                        if(parameterVal.length==2){
                            parametersMapping.put(parameterVal[0],parameterVal[1]);
                        }

                    }

                }


            }
            //这里不能关
//            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public MyRequest(InputStream inputStream){
        this.inputStream=inputStream;
         init();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
    public String getParameter(String name){
       if(parametersMapping.containsKey(name)){
           return parametersMapping.get(name);
       }else {
           return null;
       }
    }
}
