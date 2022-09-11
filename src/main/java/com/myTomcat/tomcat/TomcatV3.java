package com.myTomcat.tomcat;

import com.myTomcat.tomcat.handler.RequestHandler;
import com.myTomcat.tomcat.servlet.MyHttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TomcatV3 {

    public static String path;

    public static void main(String[] args) {
        TomcatV3 tomcatV3 = new TomcatV3();
        tomcatV3.init();

        //启动容器
        tomcatV3.run();
    }

    //启动容器
    public void run(){

        try {
            System.out.println("=====正在监听======");
            ServerSocket serverSocket = new ServerSocket(8080);
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
                new Thread(requestHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //1.存放容器 servletMapping、servletUrlMapping
    public static final ConcurrentHashMap<String , MyHttpServlet>
    servletMapping=new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<String,String>
    servletUrlMapping=new ConcurrentHashMap<>();

    //直接对2个容器进行初始化
    public void init(){
        //读取web.xml => dom4j
        //得到web.xml 文件的路径
        path = TomcatV3.class.getResource("/").getPath();
        System.out.println("path="+path);

        //使用dom4j完成读取！
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(new File(path+"web.xml"));
            System.out.println(document);
            //得到根元素
            Element rootElement = document.getRootElement();
            //得到根元素下面的所有元素
            List<Element> elements = rootElement.elements();

            //遍历 过滤
            for (Element element:elements){
                if("servlet".equalsIgnoreCase(element.getName())){
                    //这是servlet
                    Element servletName = element.element("servlet-name");
                    Element servletClass = element.element("servlet-class");
                    System.out.println("servlet-name="+servletName.getText());
                    System.out.println("servlet-class="+servletClass.getText());
                    servletMapping.put(servletName.getText(), (MyHttpServlet)Class.forName(servletClass.getText().trim()
                            ).newInstance());
                }else if ("servlet-mapping".equalsIgnoreCase(element.getName())){
                    //这是servlet-mapping
                    Element servletName = element.element("servlet-name");
                    Element urlPattern = element.element("url-pattern");
                    System.out.println("servlet-name="+servletName.getText());
                    System.out.println("url-pattern="+urlPattern.getText());

                    servletUrlMapping.put(urlPattern.getText(),servletName.getText());

                }
            }

        }catch (ClassNotFoundException | DocumentException | MalformedURLException | InstantiationException | IllegalAccessException e){
            System.out.println("爬！！");
            e.printStackTrace();
        }

        //验证
        System.out.println("servletMapping="+servletMapping);
        System.out.println("servletUrlMapping="+servletUrlMapping);

    }

}
