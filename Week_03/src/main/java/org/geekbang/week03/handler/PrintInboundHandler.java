package org.geekbang.week03.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PrintInboundHandler implements InboundHandler {

    private Socket socket;

    public PrintInboundHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void handler() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder params = new StringBuilder();
            String s;
            while ((s = reader.readLine()) != null) {
                params.append(s).append("\r\n");
                if (s.length() <= 0) {
                    break;
                }
            }
            System.out.println(params.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
