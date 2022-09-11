package com.myTomcat.servlet;

import com.myTomcat.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class CalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 接受提交的数据进行计算
        String StrNum1 = request.getParameter("num1");
        String StrNum2 = request.getParameter("num2");

        int num1= WebUtils.parseInt(StrNum1,0);
        int num2= WebUtils.parseInt(StrNum2,0);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print("<h1>"+num1+" + "+num2+"="+(num1+num2)+"<h1>");
        writer.flush();writer.close();



    }
}
