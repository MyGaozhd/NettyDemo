package com.servi.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class ServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收数据并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("服务器收到数据长度：" + len);
        System.out.println("服务器收到数据内容：" + new String(content, Charset.forName("utf-8")));

        System.out.println("服务器接收到数据数量：" + (++count));


        //回复客户端消息
        String response = UUID.randomUUID().toString();
        byte[] responseContent = response.getBytes(Charset.forName("utf-8"));
        int responseLen = response.getBytes(Charset.forName("utf-8")).length;
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responseLen);
        messageProtocol.setContent(responseContent);
        ctx.writeAndFlush(messageProtocol);
    }
}
