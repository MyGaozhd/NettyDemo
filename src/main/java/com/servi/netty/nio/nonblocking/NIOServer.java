package com.servi.netty.nio.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws Exception {
        //1、创建 ServerSocketChannel -> SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2、创建 selector对象
        Selector selector = Selector.open();
        //3、绑定6666端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //4、设置成非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //5、把serverSocketChannel 注册到selector，并且关心 OP_ACCEPT 接收连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //等待一秒钟，每有事件发生就继续
            if (selector.select(1000) == 0) {
                System.out.println("服务等待了1秒 无连接");
                continue;
            }

            // 获取到有事件发生的事件集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                //获取到SelectionKey
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
                    if (serverSocketChannel1 == serverSocketChannel) {
                        System.out.println("serverSocketChannel1 == serverSocketChannel");
                    }
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //注册SocketChannel 关注OP_READ，并关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if (key.isValid() && key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();

                    int read = socketChannel.read(buffer);
                    if (read > 0) {
                        System.out.println(new String(buffer.array()));
                    }else {
                        key.cancel();
                    }
                }

                iterator.remove();
            }
        }
    }
}
