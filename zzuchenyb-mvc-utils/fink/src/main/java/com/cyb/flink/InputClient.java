package com.cyb.flink;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 描述：
 *
 * @author iechenyb
 * @create --
 */
public class InputClient {
    public static void connect(String host,int port){
        try {
            Socket sock = new Socket(host, port);
            // 创建一个写线程
            new TelnetWriter(sock.getOutputStream()).start();
            // 创建一个读线程
            new TelnetReader(sock.getInputStream()).start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    static class TelnetReader extends Thread {
        private InputStreamReader in;

        public TelnetReader(InputStream in) {
            this.in = new InputStreamReader(in);
        }
        public void run() {
            try {
                // 反复将Telnet服务程序的反馈信息显示在控制台屏幕上
                while (true) {
                    // 从Telnet服务程序读取数据
                    int b = in.read();
                    if (b == -1)
                        System.exit(0);
                    // 将数据显示在控制台屏幕上
                    System.out.print((char) b);
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
   static class TelnetWriter extends Thread {
        private PrintStream out;

        public TelnetWriter(OutputStream out) {
            this.out = new PrintStream(out);
        }
        public void run() {
            try {
                // 包装控制台输入流
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                // 反复将控制台输入写到Telnet服务程序
                while (true)
                    out.println(in.readLine());
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
    public static void sendTCP(String sendStr){
        int port = 9000;
        try {
            ServerSocket server = new ServerSocket(port);
            Socket client = server.accept();

            System.out.println(client.getInetAddress() + "已建立连接！");
            // 输入流
            InputStream is = client.getInputStream();
            BufferedReader bri = new BufferedReader(new InputStreamReader(is));
            // 输出流
            OutputStream os = client.getOutputStream();

            PrintWriter pw = new PrintWriter(os);
            // PrintWriter把数据写到目的地
            pw.print(sendStr);
            //关闭资源
            is.close();
            bri.close();
            os.close();
            pw.close();
            client.close();
            server.close();
            System.out.println("send success! The length:" + sendStr.length());
        } catch (Exception e) {
            System.out.println("connection exit!");
            System.out.println();
        } finally {

        }
    }
    public static void main(String[] args) {
        connect("192.168.16.211", 9000);
        //sendTCP("Hi");
    /*try {
        //1.建立客户端socket连接，指定服务器位置及端口
        Socket socket =new Socket("localhost",9000);
        //2.得到socket读写流
        OutputStream os=socket.getOutputStream();
        PrintWriter pw=new PrintWriter(os);
        //输入流
        InputStream is=socket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        //3.利用流按照一定的操作，对socket进行读写操作
        String info="用户名：Tom,用户密码：123456";
        socket.shutdownOutput();
        //接收服务器的相应
        String reply=null;
        while(!((reply=br.readLine())!="exit")){
            System.out.println("接收服务器的信息："+reply);
            pw.write(info);
            pw.flush();
        }
        //4.关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
    } catch (Exception e) {
        e.printStackTrace();
    }*/
}
}

