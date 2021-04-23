package main.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;



public class NIBServer {

    public static void main(String[] args) throws IOException {
        //服务端连接建立
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);

        //打开一个Selector
        Selector selector = Selector.open();
        System.out.println("select keys 的总数量serverSocketChannel 注入前： " + selector.keys().size());
        System.out.println("注入前 ===="+ selector.selectedKeys().size());
        System.out.println(selector.getClass());
        //serverSocketChannel 注册到 selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("select keys 的总数量serverSocketChannel 注入后： " + selector.keys().size());
        System.out.println("注入后===="+ selector.selectedKeys().size());
        while (true) {
            //监听
            if(selector.select(1000) == 0){
                System.out.println("等待了1秒 无任何事件");
                System.out.println("监听无结构===="+ selector.selectedKeys().size());
                continue;
            }
            System.out.println("监听有结果===="+ selector.selectedKeys().size());
            //拿到所有的key key对应SocketChannel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next(); //key有很多功能
                if (key.isAcceptable()) { //有没有可接收的 OP_ACCEPT
                    //key还没SocketChannel
                    //生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("一个客户连接了");
                    socketChannel.configureBlocking(false);
                    //将 socketChannel 注册到 selector, 关注事件为 OP_READ， 同时给 socketChannel
                    //关联一个 Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("select keys 的总数量： " + selector.keys().size());
                }

                if(key.isReadable()){ //有没有可读的 OP_READ
                    //key取到channel和buffer
                    SocketChannel channel = (SocketChannel)key.channel();
                    ByteBuffer byteBuffer =  (ByteBuffer)key.attachment();
                    int read = channel.read(byteBuffer);
                    System.out.println("form 客户端 " + new String(byteBuffer.array()));
                    channel.write(ByteBuffer.wrap("你好 我的客户端".getBytes(StandardCharsets.UTF_8)));

                }
                //手动从集合中移动当前的 selectionKey, 防止重复操作
                System.out.println(iterator.getClass());
                iterator.remove();
            }
        }
    }
}
