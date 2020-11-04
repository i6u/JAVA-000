package org.geekbang.week03.handler;

import org.geekbang.week02.utils.HttpUtil;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public enum OutboundHandler {
    INSTANCE;

    public void handler(Socket socket, String proxyServer) {
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type: text/html;charset=UTF-8");
            String body = "<html>Hello NIO</html>";
            String responseBody = HttpUtil.INSTANCE.loadString(proxyServer);
            printWriter.println("Content-Length:" + responseBody.getBytes().length);
            printWriter.println();
            printWriter.println(body);
            printWriter.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
