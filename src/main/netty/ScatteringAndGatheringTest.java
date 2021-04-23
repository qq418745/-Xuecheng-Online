package main.netty;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGatheringTest {

    public static void main(String[] args)throws Exception {
        ServerSocketChannel open = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        open.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //监听接受
        SocketChannel socketChannel = open.accept();

        int msgLength = 8 ;
        while (true) {
            int byteRead = 0 ;
            while (byteRead < msgLength) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                System.out.println("byteRead = "+byteRead);
                Arrays.asList(byteBuffers).stream().map(s-> "position= "+ s.position() + "limit = " + s.limit() + "capacity = "+ s.capacity()).forEach(b-> System.out.println(b));
            }

            Arrays.asList(byteBuffers).forEach(b->b.flip());
            long byteWirte = 0 ;
            while(byteWirte < msgLength){
                byteWirte +=  socketChannel.write(byteBuffers);

            }

            Arrays.asList(byteBuffers).forEach(p->p.clear());


        }

    }
}
