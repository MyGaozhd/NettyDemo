package com.servi.netty.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel02 {

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");

        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            fileChannel.read(buffer);
            buffer.flip();
            System.out.println(new String(buffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                fileInputStream.close();
                fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
