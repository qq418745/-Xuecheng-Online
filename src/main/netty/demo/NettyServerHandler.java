package main.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //ChannelHandlerContext 上下文对象 里面啥都有 有 channel pipeline
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("handler目前线程 "+ Thread.currentThread().getName());
        System.out.println("server ctx" );
        System.out.println();
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline(); //pipeline本质是个双向链表  出站入站

        ByteBuf buf = (ByteBuf)msg; //这个 buf是Netty提供的
        System.out.println("打印客户端发来的消息： "+ buf.toString(CharsetUtil.UTF_8));
        System.out.println("打印客户端发来的地址: "+ channel.remoteAddress());

    }

  //complete 完成
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //读取完成后调用
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好啊 我的客户端",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
