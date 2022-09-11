package com.myTomcat.tomcat.servlet;

import com.myTomcat.tomcat.http.MyRequest;
import com.myTomcat.tomcat.http.MyResponse;

import javax.servlet.ServletException;
import java.io.IOException;

public abstract class MyHttpServlet implements MyServlet {
    public MyHttpServlet(){

    }
    @Override
    public void service(MyRequest request, MyResponse response) throws ServletException, IOException {
        //  equalsIgnoreCase()方法：比较字符串内容，不区分大小写
        if("GET".equalsIgnoreCase(request.getMethod())){
           this.doGet(request,response);   //这里存在动态绑定！！！
        }else if ("POST".equalsIgnoreCase(request.getMethod())){
            this.doPost(request,response);
        }
    }

    // 使用了模板设计模式
    // 让HspHttpServlet 子类 MyCalServlet 实现
    public abstract void doGet(MyRequest request, MyResponse response);
    public abstract void doPost(MyRequest request, MyResponse response);
}
