package org.geekbang.week03;

import org.geekbang.week03.handler.InboundHandler;
import org.geekbang.week03.handler.OutboundHandler;
import org.geekbang.week03.handler.PrintInboundHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InboundServer {

    private final static Executor executor = Executors.newFixedThreadPool(40);

    private final int port;
    private final String proxyServer;

    public InboundServer(int port, String proxyServer) {
        this.port = port;
        this.proxyServer = proxyServer;
    }

    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new PrintInboundHandler(socket);
                executor.execute(() -> OutboundHandler.INSTANCE.handler(socket, proxyServer));
            } catch (IOException e) {
                System.out.println("代理失败...");
                e.printStackTrace();
                break;
            }
        }
    }
}
