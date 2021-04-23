package main.netty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class BIOServer {

    public static void main(String[] args) throws IOException {
        //创建线程池
        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("start socket server");

        while(true){
            //收受方法  监听 阻塞
          final  Socket accept = serverSocket.accept();
            System.out.println("执行监听新Socket连接");

            System.out.println(Thread.currentThread().getId());
            System.out.println(Thread.currentThread().getName());
            System.out.println("当前线程信息结束");


           new Thread( new Runnable() {
               @Override
               public void run() {
                   // accept socket 对象异步处理
                   handler(accept);
               }
           }).start();



        }

    }

    public static void handler(Socket socket){


        byte[] buffer = new byte[1024];
        InputStream inputStream = null;
        try {
            System.out.println(Thread.currentThread().getId());
            System.out.println(Thread.currentThread().getName());
            inputStream = socket.getInputStream();
            while(true){
                //socket 阻塞监听 一直嘟嘟嘟  客户端关闭连接就读不到了
                int read = inputStream.read(buffer);
                if (read != -1){
                    System.out.println(new String(buffer, 0, read));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                System.out.println("close socket");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

}
