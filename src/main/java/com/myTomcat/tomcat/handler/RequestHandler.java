package com.myTomcat.tomcat.handler;

import com.myTomcat.tomcat.TomcatV3;
import com.myTomcat.tomcat.http.MyRequest;
import com.myTomcat.tomcat.http.MyResponse;
import com.myTomcat.tomcat.servlet.MyHttpServlet;
import com.myTomcat.tomcat.utils.WebUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 *  1.这是一个线程对象
 *  2。用来处理 HTTP 请求
 */

public class RequestHandler implements Runnable{

    //定义socket
    public Socket socket;

    public RequestHandler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {

        //这里对客户端、浏览器进行IO编程！
        try {
           // InputStream inputStream=socket.getInputStream();

//            //转成buf字符流  bufferedReader -> 方便按行读取
//            BufferedReader bufferedReader = new BufferedReader(
//                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));
//
//           System.out.println("=====hspTomcatV2接收到的数据如下========");
//            String mes;
//            while ((mes=bufferedReader.readLine())!=null){
//                if (mes.length()==0){
//                    break;
//                }
//                System.out.println(mes);
//
//            }

            MyRequest myRequest =new MyRequest(socket.getInputStream());
//            String num1=hspRequest.getParameter("num1");
//            String num2 = hspRequest.getParameter("num2");
//            System.out.println(hspRequest);
//            System.out.println("num1="+num1);
//            System.out.println("num2="+num2);

            //下面返回数据给我们的浏览器 => 封装成http响应

            //使用hsp.....
            MyResponse myResponse = new MyResponse(socket.getOutputStream());

            // 一会用反射....

            //1. URI -> servletName
            String uri= myRequest.getURI();

            //判断uri是什么资源
            //如果是静态资源就读取
            if (WebUtils.isHtml(uri)){
                String content = MyResponse.respHeader+WebUtils.readHtml(uri.substring(1));
                OutputStream outputStream = myResponse.getOutputStream();
                outputStream.write(content.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();outputStream.close();
                socket.close();
                return;
                //返回喽！！！

            }

            String servletName= TomcatV3.servletUrlMapping.get(uri);
            System.out.println("URI="+uri+"的请求");
            //2. uri -> servletName -> servlet 实例
            // 真正运行类型是其子类 CalServlet
            MyHttpServlet myHttpServlet = TomcatV3.servletMapping.get(servletName);
               //3.调用service 通过动态绑定机制，调用运行类型的doPost/doGet方法
               if (myHttpServlet != null) {
                   System.out.println("调用service!");
                   myHttpServlet.service(myRequest, myResponse);
               } else {
                   //没有这个servlet
                   String resp = MyResponse.respHeader + "<h1>404 NOT FOUND</h1>";
                   OutputStream outputStream = myResponse.getOutputStream();
                   outputStream.write(resp.getBytes(StandardCharsets.UTF_8));
                   outputStream.flush();
                   outputStream.close();
               }





//            MyCalServlet hspCalServlet = new MyCalServlet();
//            hspCalServlet.doGet(hspRequest,hspResponse);

//            String resp = HspResponse.respHeader + "<h1>hi,我是你爹！这是response！！！</h1>";
//            OutputStream outputStream = hspResponse.getOutputStream();
//            outputStream.write(resp.getBytes(StandardCharsets.UTF_8));
//            outputStream.flush();outputStream.close();

//            //构建一下http响应头！
//
//            //!!!!!!!! HTTP响应头和响应体之间有两个换行！！！！
//
//            String respHeader="HTTP/2 200 OK\r\n" +
//                    "Content-type: text/html;charset=utf-8\r\n\r\n";
//            String resp=respHeader+"<h1>hi,我是你爹！</h1>";
//
//            outputStream.write(resp.getBytes(StandardCharsets.UTF_8));
//            System.out.println("返回的数据为");
//            System.out.println(resp);


        } catch (Exception e) {
            System.out.println("爬");
            e.printStackTrace();
        }finally {
            //关闭！！！
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
