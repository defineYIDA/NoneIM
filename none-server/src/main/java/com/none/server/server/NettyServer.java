package com.none.server.server;

import com.none.common.codec.PacketCodecHandler;
import com.none.common.codec.Spliter;
import com.none.common.handler.IMIdleStateHandler;
import com.none.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: zl
 * @Date: 2019/4/23 0:53
 */
@Component
@Slf4j
public class NettyServer {

    @Value("${im.server.port}")
    private int imServerPort;

    @PostConstruct
    public void start() {
        //定义两大线程组，这种方式类似于AIO中的AsynchronousChannelGroupchannelGroup，
        //提前设置工作或者是接收线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                //配置两大线程组， NIO模式对应的线程组，accptor/Poller
                .group(bossGroup, workerGroup)
                //NIO模式
                .channel(NioServerSocketChannel.class)
                //回调，
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        //ChannelPipeline，是实例链
                        //ch.pipeline().addLast(new EchoServerHandler());
                        //空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);//用户认证
                        ch.pipeline().addLast(IMHandler.INSTANCE);

                    }
                });
        int port = imServerPort;
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("监听端口[" + port + "]成功");
            } else {
                //从future中检索Throwable
                Throwable cause = future.cause();
                cause.printStackTrace();
                log.info("监听端口[" + port + "]失败");
            }
        });
        //方式一： new GenericFutureListener<Future<? super Void>>()
        /*serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {

            }
        });*/
        /*ChannelFuture f=serverBootstrap.bind(port).sync();
        f.channel().closeFuture().sync();*/
    }

       /*public static void main(String[] args) {
    }*/
}
