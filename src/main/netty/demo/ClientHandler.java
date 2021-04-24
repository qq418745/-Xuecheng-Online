package main.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client"+ ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好服务端",CharsetUtil.UTF_8));
    }

    //读事件

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器来的消息： = " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址 = "+ ctx.channel().remoteAddress());
    }
    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }




}
