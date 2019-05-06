package com.none.im.client;


import com.none.im.client.handler.EchoClientHandler;
import com.none.im.client.handler.LoginResponseHandler;
import com.none.im.protocol.PacketCodec;
import com.none.im.protocol.request.MessageRequestPacket;
import com.none.im.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zl
 * @Date: 2019/4/23 0:54
 */
public class NettyClient {

    static int MAX_RETRY=3;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        //ch.pipeline().addLast(new EchoClientHandler());
                        //登陆响应处理器
                        ch.pipeline().addLast(new LoginResponseHandler());
                    }
                });
        connect(bootstrap,"localhost",8080,MAX_RETRY);
        // 4.建立连接
        /*bootstrap.connect("localhost", 8080).addListener( future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else {
                System.err.println("连接失败!");
            }
        });*/
        /*ChannelFuture f= bootstrap.connect("localhost", 80).sync();
        f.channel().closeFuture().sync();*/
    }

    private static void connect(Bootstrap bootstrap,String host, int port , int retry) {
        bootstrap.connect(host,port).addListener(future -> {
           if(future.isSuccess()) {
               System.out.println("连接成功!");
               //connect 成功返回的是ChannelFuture
               startConsoleThread(((ChannelFuture) future).channel());
           } else if (retry == 0) {
               System.out.println("重试次数用完，放弃连接！");
           } else {
             //第几次重连
             int order = (MAX_RETRY-retry) + 1;
             int delay = 1<<order;
               System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
               //使用schedule来执行延时任务
               bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                       .SECONDS);
           }
        });
    }
    private static void startConsoleThread(Channel channel) {
        new Thread(() ->{
            while (!Thread.interrupted()) {
                if (SessionUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端: ");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                    ByteBuf byteBuf = PacketCodec.INSTANCE.encode(channel.alloc().ioBuffer(),packet);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}