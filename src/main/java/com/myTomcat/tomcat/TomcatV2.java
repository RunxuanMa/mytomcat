package com.myTomcat.tomcat;

import com.myTomcat.tomcat.handler.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TomcatV2 {
    public static void main(String[] args) throws IOException {

        //监听
        ServerSocket serverSocket=new ServerSocket(8080);

        System.out.println("=====在8080端口监听=====");
        //只要 serverSocket没有关闭，就一直等待浏览器/客户端的连接
        while (!serverSocket.isClosed()){
            //1.接收到浏览器的连接后，如果成功就会得到socket
            //2.这个socket 就是浏览器与服务器的数据通道
            Socket socket = serverSocket.accept();
            //3.创建一个线程对象，把socket传给线程,再扔到thread里面
            RequestHandler requestHandler = new RequestHandler(socket);
            Thread thread = new Thread(requestHandler);
            thread.start();


        }

    }
}
