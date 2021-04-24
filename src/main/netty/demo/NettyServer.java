package main.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
        //创建 boosGroup 和 workGroup
        //NioEventLoop 的默认数量为 cpu核心数量 * 2
        //boos一个就够了 boss主要处理 accept 然后把 socketChannel 注册到 workGroup
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //work就多几个呗
        NioEventLoopGroup workGroup = new NioEventLoopGroup(1);
        //
        //服务启动对象
        ServerBootstrap serverBootStrap = new ServerBootstrap();

        serverBootStrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class) // 用NioServerSocketChannel 作为Channel对象 用NioServerSocketChannel 是netty对 ServerSocketChannel做的封装
                .option(ChannelOption.SO_BACKLOG,128) //设置线程队列得到的连接个数
                .childOption(ChannelOption.SO_KEEPALIVE,true) //设置保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {//通道测试对象

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                }) ;// 给workGroup的 NioEventLoop设置对象的管道处理器

        //配置好了设置
        //启动 sync有监听的意思

        try {
            ChannelFuture cf = serverBootStrap.bind(6668).sync();
            System.out.println("netty服务器启动成功 牛逼X");
            cf.channel().closeFuture().sync(); // 监听 future 未来的 Channel关闭情况
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }



    }
}
