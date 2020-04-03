package com.servi.netty.inouthandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LongToByteEncoder());
        pipeline.addLast(new ClientHandler());
    }

    class LongToByteEncoder extends MessageToByteEncoder<Long> {

        @Override
        protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
            System.out.println("LongToByteEncoder 被调用");
            System.out.println("msg= " + msg);
            out.writeLong(msg);
        }
    }

    class ClientHandler extends SimpleChannelInboundHandler<Long>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("ClientHandler发送数据");
            ctx.writeAndFlush(123456L);
        }
    }
}
