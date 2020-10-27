package org.geekbang;

import org.geekbang.service.ServerService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * HttpServer03
 * <p>
 * 线程池
 */
public class HttpServer03 {

    public static void main(String[] args) throws IOException {
        int port = 8883;
        Executor executor = Executors.newFixedThreadPool(40);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("单线程 Port:" + port);
        while (true) {
            Socket socket = serverSocket.accept();
            executor.execute(() -> ServerService.service(socket));
        }
    }
}