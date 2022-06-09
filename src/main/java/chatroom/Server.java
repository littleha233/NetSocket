package chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args)throws IOException {

        // 初始化服务端socket并且绑定9999端口
        ServerSocket serverSocket = new ServerSocket(9999);

        //创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        while (true) {
        //等待客户端的连接
            Socket socket = serverSocket.accept();
            Runnable runnable = () -> {
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    //读取一行数据
                    String str;
                    //通过while循环不断读取信息，
                    while ((str = bufferedReader.readLine()) != null) {
                        //输出打印
                        // 使用 bufferedReader.hashCode()为每一个客户端线程生成一个唯一的id来标识
                        System.out.println("From client " + bufferedReader.hashCode()+ " " + str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            executorService.submit(runnable);
        }
    }

    // 获取主机IP地址
    public static String  getIP() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "Query failed...";
    }
}
