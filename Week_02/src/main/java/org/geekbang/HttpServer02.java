package org.geekbang;

import org.geekbang.service.ServerService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * HttpServer02
 * <p>
 * 多线程
 */
public class HttpServer02 {

    public static void main(String[] args) throws IOException {
        int port = 8882;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("单线程 Port:" + port);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                ServerService.service(socket);
            }).start();
        }
    }


} 