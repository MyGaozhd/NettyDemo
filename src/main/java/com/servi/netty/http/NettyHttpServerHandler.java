package com.servi.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 的子类
 * HttpObject 客户端与服务器端通信数据被封装成HttpObject
 */
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {

            HttpRequest httpRequest = (HttpRequest) msg;

            System.out.println("msg类型：" + msg.getClass());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());
            ByteBuf byteBuf = Unpooled.copiedBuffer("我是服务端。", CharsetUtil.UTF_8);
            // 构造一个http响应
            DefaultFullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            fullHttpResponse.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            fullHttpResponse.headers().add(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            ctx.writeAndFlush(fullHttpResponse);
        }
    }
}
