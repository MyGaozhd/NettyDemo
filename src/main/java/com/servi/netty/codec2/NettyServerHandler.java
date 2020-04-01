package com.servi.netty.codec2;

import com.servi.netty.codec.StudentPOJO;
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
        //根据datatype显示不同的信息
        ProtoDataInfo.ProtoMessage message = (ProtoDataInfo.ProtoMessage) msg;

        if (message.getDataType() == ProtoDataInfo.ProtoMessage.DataType.StrudentType) {
            ProtoDataInfo.Student student = ((ProtoDataInfo.ProtoMessage) msg).getStudent();
            System.out.println("学生：" + student.getName() + ",id=" + student.getId());
        } else if (message.getDataType() == ProtoDataInfo.ProtoMessage.DataType.WorkerType) {
            ProtoDataInfo.Worker worker = ((ProtoDataInfo.ProtoMessage) msg).getWorker();
            System.out.println("工人：" + worker.getName() + ",age=" + worker.getAge());
        }
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
