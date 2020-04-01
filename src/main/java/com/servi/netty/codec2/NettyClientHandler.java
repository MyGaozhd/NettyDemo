package com.servi.netty.codec2;

import com.servi.netty.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ProtoDataInfo.ProtoMessage student = ProtoDataInfo.ProtoMessage.newBuilder()
//                .setDataType(ProtoDataInfo.ProtoMessage.DataType.StrudentType)
//                .setStudent(ProtoDataInfo.Student.newBuilder().setId(3).setName("student").build()).build();
//        ctx.writeAndFlush(student);

        ProtoDataInfo.ProtoMessage worker = ProtoDataInfo.ProtoMessage.newBuilder()
                .setDataType(ProtoDataInfo.ProtoMessage.DataType.WorkerType)
                .setWorker(ProtoDataInfo.Worker.newBuilder().setAge(36).setName("worker").build()).build();
        ctx.writeAndFlush(worker);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回复的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
