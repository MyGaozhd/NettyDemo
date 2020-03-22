package com.servi.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *开启服务----
 等待链接----
 收到请求----
 等待链接----
 pool-1-thread-1: read----
 收到请求----
 等待链接----
 pool-1-thread-1:111
 pool-1-thread-1: read----
 pool-1-thread-1:111
 pool-1-thread-1: read----
 pool-1-thread-1:111
 pool-1-thread-1: read----
 pool-1-thread-1:1112
 pool-1-thread-1: read----
 pool-1-thread-1: read----
 pool-1-thread-1:222222222
 pool-1-thread-1: read----
 */
public class BioServer {

    public static void main(String[] args) throws IOException {

        ExecutorService service = Executors.newFixedThreadPool(1);

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("开启服务----");
        while (true) {

            System.out.println("等待链接----");
            final Socket socket = serverSocket.accept();
            System.out.println("收到请求----");

            service.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    public static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();

            while (true) {
                System.out.println(Thread.currentThread().getName() + ": read----");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(Thread.currentThread().getName() + ":" + new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
