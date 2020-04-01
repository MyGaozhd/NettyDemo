package com.servi.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * @param ctx 含有管道、通道
     * @param msg 客户端的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取客户端发送的student
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("id=" + student.getId() + " name=" + student.getName());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,您好", CharsetUtil.UTF_8));
    }

    //发生异常时关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
