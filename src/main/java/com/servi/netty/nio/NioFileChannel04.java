package com.servi.netty.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NioFileChannel04 {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("1.txt");
        FileOutputStream outputStream = new FileOutputStream("2.txt");

        FileChannel fileChannelInput = inputStream.getChannel();
        FileChannel fileChannelOutput = outputStream.getChannel();

        fileChannelOutput.transferFrom(fileChannelInput,0,fileChannelInput.size());
        fileChannelInput.close();
        fileChannelOutput.close();
        inputStream.close();
        outputStream.close();

    }
}
