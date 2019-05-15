package com.cyb.utils.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class NioSocketServer {
	 private volatile byte flag = 1;

	    public void setFlag(byte flag) {
	        this.flag = flag;
	    }

	    public void start() {
	        //创建serverSocketChannel，监听8888端口
	        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
	            serverSocketChannel.socket().bind(new InetSocketAddress(8888));
	            //设置为非阻塞模式
	            serverSocketChannel.configureBlocking(false);
	            //为serverChannel注册selector
	            Selector selector = Selector.open();
	            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

	            System.out.println("服务端开始工作：");

	            //创建消息处理器
	            ServerHandlerBs handler = new ServerHandlerImpl(1024);

	            while (flag == 1) {
	                selector.select();
	                System.out.println("开始处理请求 ： ");
	                //获取selectionKeys并处理
	                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
	                while (keyIterator.hasNext()) {
	                    SelectionKey key = keyIterator.next();
	                    try {
	                        //连接请求
	                        if (key.isAcceptable()) {
	                            handler.handleAccept(key);
	                        }
	                        //读请求
	                        if (key.isReadable()) {
	                            System.out.println(handler.handleRead(key));
	                        }
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                    //处理完后移除当前使用的key
	                    keyIterator.remove();
	                }
	                System.out.println("完成请求处理。");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
