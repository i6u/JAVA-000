package org.geekbang.week03;

import org.geekbang.week03.handler.InboundHandler;

import java.io.IOException;

public class ProxyServer {

    public static void main(String[] args) {
        String proxyServer = System.getProperty("proxyServer", "http://localhost:8881");
        String proxyPort = System.getProperty("proxyPort", "8888");
        int port = Integer.parseInt(proxyPort);
        System.out.println("启动代理服务：Proxy Port:" + port);
        InboundServer server = new InboundServer(port, proxyServer);
        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务启动失败...");
        }
    }
}
