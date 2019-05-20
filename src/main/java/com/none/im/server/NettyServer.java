package com.none.im.server;

import com.none.im.client.handler.ShowCmdResponseHandler;
import com.none.im.codec.PacketDecoder;
import com.none.im.codec.PacketEncoder;
import com.none.im.codec.Spliter;
import com.none.im.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: zl
 * @Date: 2019/4/23 0:53
 */
public class NettyServer {
    public static void main(String[] args) {
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
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(AuthHandler.INSTANCE);//用户认证
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new CreateGroupRequestHandler());
                        // 加群请求处理器
                        ch.pipeline().addLast(new JoinGroupRequestHandler());
                        // 获取群成员请求处理器
                        ch.pipeline().addLast(new ListGroupMembersRequestHandler());
                        // 退群请求处理器
                        ch.pipeline().addLast(new QuitGroupRequestHandler());
                        //查看命令
                        ch.pipeline().addLast(new ShowCmdRequestHandler());
                        ch.pipeline().addLast(new LogoutRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        int port =8080;
        serverBootstrap.bind(port).addListener( future-> {
            if(future.isSuccess()){
                System.out.print("监听端口["+port+"]成功");
            }else {
                //从future中检索Throwable
                Throwable cause=future.cause();
                cause.printStackTrace();
                System.out.print("监听端口["+port+"]失败");
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

}
