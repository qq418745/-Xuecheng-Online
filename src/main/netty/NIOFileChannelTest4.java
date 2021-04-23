package main.netty;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class NIOFileChannelTest4 {

    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("1.png");
        FileOutputStream fileOutputStream = new FileOutputStream("2.png");


        FileChannel inCh= fileInputStream.getChannel();
        FileChannel outCh = fileOutputStream.getChannel();

        long l = outCh.transferFrom(inCh, 0,inCh.size());


    }
}
