package com.servi.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class ClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //发送10条数据
        for (int i = 0; i < 5; i++) {
            String message = "我想吃火锅，！！！@@@----->" + i;
            byte[] content = message.getBytes(Charset.forName("utf-8"));
            int length = content.length;
            //创建协议包
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setContent(content);
            messageProtocol.setLen(length);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收数据并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("客户端收到数据长度：" + len);
        System.out.println("客户端收到数据内容：" + new String(content, Charset.forName("utf-8")));

        System.out.println("客户端接收到数据数量：" + (++count));
    }
}
