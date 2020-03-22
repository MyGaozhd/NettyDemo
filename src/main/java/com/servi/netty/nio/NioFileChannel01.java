package com.servi.netty.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel01 {
    public static void main(String[] args) throws IOException {
        String s = "我肯定会成功";
        FileOutputStream fileOutputStream = new FileOutputStream("1.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(s.getBytes());

        try {
            buffer.flip();
            fileChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileOutputStream.close();
        }
    }
}
