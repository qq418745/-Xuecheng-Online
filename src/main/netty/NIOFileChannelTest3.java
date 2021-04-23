package main.netty;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannelTest3 {
    public static void main(String[] args) throws Exception{

        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel inChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel outChannel = fileOutputStream.getChannel();


        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            buffer.clear();
            int read = inChannel.read(buffer);

            System.out.println(read);
            if (read == -1) {
                break;
            }
            buffer.flip();
            int write = outChannel.write(buffer);
        }



    }
}
