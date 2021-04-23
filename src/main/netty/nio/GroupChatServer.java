package main.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {

    private Selector selector;//选择器
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 6666;

    //初始化
    public GroupChatServer()  {
        try {
            selector =  Selector.open();
           serverSocketChannel =   ServerSocketChannel.open();
           serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
           serverSocketChannel.configureBlocking(false);
           serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listen(){
        while(true){
            try {
                int count = selector.select();
                if(count > 0 ){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        //看看有啥事件
                        if (key.isAcceptable()) { //有接受的事件
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                          //注册 seletor
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            System.out.println("有个用户上线了！ "+ socketChannel.getRemoteAddress());
                        }
                        //
                        if (key.isReadable()) {
                            readData(key);
                        }

                    }
                    //处理完事件
                    iterator.remove();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readData(SelectionKey key) {
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = 0;
        try {
            read = channel.read(buffer);
            //发给除自己以外的其他人
            if(read > 0 ) {
                System.out.println(new String(buffer.array()));
                buffer.flip();
                sendInfoAllClinets(buffer,channel);
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }


    }

    private void sendInfoAllClinets(ByteBuffer buffer, SocketChannel channel) throws IOException {
        System.out.println("开始转发所有的人消息");
        for (SelectionKey key : selector.keys()) {
            SelectableChannel channel1 = key.channel();
            if (channel1 instanceof SocketChannel && channel1 != channel) {
                //写消息
                ((SocketChannel) channel1).write(buffer);
                buffer.flip();
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
