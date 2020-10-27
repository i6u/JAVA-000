import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * HttpServer01
 */
public class HttpServer03 {

    public static void main(String[] args) throws IOException {
        Executor executor = Executors.newFixedThreadPool(40);
        ServerSocket serverSocket = new ServerSocket(8883);
        while (true) {
            Socket socket = serverSocket.accept();
            executor.execute(() -> {service(socket);});
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