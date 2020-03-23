package com.servi.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 基于一个buffer完成文件的拷贝
 */
public class NioFileChannel03 {

    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("1.txt");
        FileChannel inpuChannel = inputStream.getChannel();
        FileOutputStream outputStream = new FileOutputStream("2.txt");
        FileChannel outChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (inpuChannel.read(buffer) != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }

        inputStream.close();
        outputStream.close();
    }
}

