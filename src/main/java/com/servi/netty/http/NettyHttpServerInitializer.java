package com.servi.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class NettyHttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();
        //想channel加入加码器
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        pipeline.addLast("NettyHttpServerHandler",new NettyHttpServerHandler());
    }
}
