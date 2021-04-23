package main.netty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest2 {
    public static void main(String[] args) throws IOException {
        //读本地文件
        File file = new File("D://halloNetty.txt");
        System.out.println(file.isFile());
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate((int)file.length());

        int read = channel.read(allocate);
        byte[] array = allocate.array();
        String s = new String(array);
        System.out.println(s);

        fileInputStream.close();
    }


}
