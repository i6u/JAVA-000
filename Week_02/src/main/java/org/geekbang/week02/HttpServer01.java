package org.geekbang.week02;

import org.geekbang.week02.service.ServerService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * HttpServer01
 * <p>
 * 单线程
 */
public class HttpServer01 {

    public static void main(String[] args) throws IOException {
        int port = 8881;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("单线程 Port:" + port);
        while (true) {
            Socket socket = serverSocket.accept();
            ServerService.service(socket);
        }
    }

} 