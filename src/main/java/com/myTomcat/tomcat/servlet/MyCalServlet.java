package com.myTomcat.tomcat.servlet;

import com.myTomcat.tomcat.http.MyRequest;
import com.myTomcat.tomcat.http.MyResponse;
import com.myTomcat.tomcat.utils.WebUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MyCalServlet extends MyHttpServlet {
    public MyCalServlet(){

    }
    @Override
    public void service(MyRequest request, MyResponse response) throws ServletException, IOException {
        super.service(request, response);
    }

    @Override
    public void doGet(MyRequest request, MyResponse response) {

        //.................
        int num1= WebUtils.parseInt(request.getParameter("num1"),0);
        int num2 = WebUtils.parseInt(request.getParameter("num2"), 0);

        int sum=num1+num2;

        //返回计算结果给浏览器
        OutputStream outputStream = response.getOutputStream();
        String respMes= MyResponse.respHeader+"<h1>"+num1+"+"+num2+"="+sum+"  这是V3版本！</h1>";
        try {
            outputStream.write(respMes.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void doPost(MyRequest request, MyResponse response) {
         doGet(request,response);
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() {

    }
}
