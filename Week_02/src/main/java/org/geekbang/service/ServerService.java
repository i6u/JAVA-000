package org.geekbang.service;

import java.io.*;
import java.net.Socket;

/**
 * 服务
 */
public class ServerService {

    public static void service(Socket socket) {
        try (BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                     socket.getOutputStream())), true)) {

            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type: text/html;charset=UTF-8");
            printWriter.println("\r");
            printWriter.println("<html>Hello NIO</html>");
            StringBuilder data = new StringBuilder();
            String s;
            while ((s = request.readLine()) != null) {
                data.append(s);
                if (s.length() <= 0) {
                    break;
                }
            }
            System.out.println(data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignore) {
            }
        }
    }
}
