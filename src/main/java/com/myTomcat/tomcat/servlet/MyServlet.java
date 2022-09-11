package com.myTomcat.tomcat.servlet;

import com.myTomcat.tomcat.http.MyRequest;
import com.myTomcat.tomcat.http.MyResponse;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 *  先搭建结构，后面再写内容
 */
public interface MyServlet {
    void init() throws Exception;

    void service(MyRequest var1, MyResponse var2) throws ServletException, IOException;


    void destroy();
}
