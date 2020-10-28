package org.geekbang.service;

import java.io.*;
import java.net.Socket;

/**
 * 服务
 */
public class ServerService {

    public static void service(Socket socket) {
        try {
            //request
            //BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //StringBuilder params = new StringBuilder();
            //String s;
            //while ((s = reader.readLine()) != null) {
            //    params.append(s).append("\r\n");
            //    if (s.length() <= 0) {
            //        break;
            //    }
            //}
            //System.out.println(params.toString());

            //response
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type: text/html;charset=UTF-8");
            String body = "<html>Hello NIO</html>";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.println(body);
            //socket.shutdownOutput();
            printWriter.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
