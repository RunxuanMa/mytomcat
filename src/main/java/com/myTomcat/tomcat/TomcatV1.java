package com.myTomcat.tomcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 *  实现 接受浏览器的请求，并返回信息
 */

public class TomcatV1 {
    public static void main(String[] args) throws IOException {

        //1.创建
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("myTomcat在8080端口监听");

        while (!serverSocket.isClosed()){

            //等待浏览器/客户端的连接
            //这个socket就是服务端和浏览器端的连接(通道)
            Socket socket = serverSocket.accept();

            //先接受浏览器发送的数据
            //得到 buffer读取字节流
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            String mes=null;
            System.out.println("==========接收到浏览器发生的数据是这样的==========");
            while ((mes=bufferedReader.readLine())!=null){
                if(mes.length()==0){
                    break;
                }
                System.out.println(mes);
            }

            //tomcat回送
            OutputStream outputStream = socket.getOutputStream();
            //创建http响应头
            // \r\n 表示换行
            // ps: http响应体。需要前面有2个换行
            String respHeader="HTTP/2 200 OK\r\n" +
                    "Content-type: text/html;charset=utf-8\r\n\r\n";
            String resp=respHeader+"hi,我是你爹！";


            System.out.println("=====我们的tomcat给浏览器返回的数据=====");
            System.out.println(resp);

            outputStream.write(resp.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            inputStream.close();
            outputStream.close();
            socket.close();


        }


    }
}
