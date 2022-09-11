package com.myTomcat.tomcat.http;

import java.io.OutputStream;

/**
 *  1.Response 可以封装OutputStream(是socket关联)
 *  2. 即可以通过 Response对象，返回Http 响应给浏览器/客户端
 *  3.Response 对象 的作用等价于原生的servlet 的 HttpServletResponse 对象
 */
public class MyResponse {
    private OutputStream outputStream=null;
    public static final String respHeader="HTTP/1.1 200 OK \r\n"+
            "Content-Type: text/html;charset=utf-8\r\n\r\n";

    public OutputStream getOutputStream() {
        return outputStream;
    }

    //这个 outputStream 是与socket关联滴
    public MyResponse(OutputStream outputStream){
        this.outputStream=outputStream;
    }

    //当我们需要给浏览器返回数据时，可以通过HspResponse 的输出流完成


}
