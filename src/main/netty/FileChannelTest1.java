package main.netty;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest1 {
    public static void main(String[] args) throws IOException {
        //写本地文件
        String str = "你好! netty";
        byte[] bytes = str.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream("D://halloNetty.txt");

      FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate(bytes.length);
        allocate.put(bytes);

        allocate.flip();
        int write = channel.write(allocate);
        fileOutputStream.close();
    }
}
