package main.netty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 之间利用操作系统内存修改 堆外内存
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile rw = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = rw.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 4);
        map.put(0,(byte)'9');
        map.put(1,(byte)'U');
        map.put(2,(byte)'8');
    }
}
