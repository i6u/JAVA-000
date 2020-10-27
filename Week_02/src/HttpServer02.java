import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * HttpServer01
 */
public class HttpServer02 {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8882);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {service(socket);}).start();
        }
    }

    public static void service(Socket socket) {
        try {
            Thread.sleep(10);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.println("Hello NIO");
            printWriter.close();
            socket.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
} 