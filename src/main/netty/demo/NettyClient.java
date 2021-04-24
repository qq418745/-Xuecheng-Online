package main.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NettyClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });

        try {
        ChannelFuture cf = bootstrap.connect("127.0.0.1", 6668).sync();
            System.out.println("客户端启动完成");
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }




    }
}
