package com.none.im;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * @Author: zl
 * @Date: 2019/4/23 0:53
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {
        //定义两大线程组，这种方式类似于AIO中的AsynchronousChannelGroupchannelGroup，
        //提前设置工作或者是接收线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                //配置两大线程组
                .group(bossGroup, workerGroup)
                //NIO模式
                .channel(NioServerSocketChannel.class)
                //回调
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new EchoServerHandler());
                    }
                });
        int port =8080;
        //方式一： new GenericFutureListener<Future<? super Void>>()
        serverBootstrap.bind(port).addListener( future-> {
            if(future.isSuccess()){
                System.out.print("监听端口["+port+"]成功");
            }else {
                System.out.print("监听端口["+port+"]失败");
            }
        });
        /*ChannelFuture f=serverBootstrap.bind(port).sync();
        f.channel().closeFuture().sync();*/
    }

}
