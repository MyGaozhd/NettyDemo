package com.servi.netty.inouthandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        //入栈的decoder
        pipeline.addLast(new ByteToLondDecoder());
        pipeline.addLast(new ServerHandler());
    }

    class ByteToLondDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            if (in.readableBytes() >= 8) {
                out.add(in.readLong());
            }
        }
    }

    class ServerHandler extends SimpleChannelInboundHandler<Long> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

            System.out.println("从客户端" + ctx.channel().remoteAddress() + "读取到long=" + msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
