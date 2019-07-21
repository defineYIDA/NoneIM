package com.none.proxy.proxy;

import com.none.proxy.handler.ProxyHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: zl
 * @Date: 2019/7/21 0:01
 */
@Component
@Slf4j
public class NettyProxy {

    @Value("${proxy.port}")
    private int PROXY_PORT;

    @PostConstruct
    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.AUTO_READ, false)
                .childHandler(new ProxyHandler());

        serverBootstrap.bind(PROXY_PORT).addListener(future -> {
            if (future.isSuccess()) {
                log.info("代理监听端口[" + PROXY_PORT + "]成功");
            } else {
                //从future中检索Throwable
                Throwable cause = future.cause();
                cause.printStackTrace();
                log.info("代理监听端口[" + PROXY_PORT + "]失败");
            }
        }).channel().closeFuture().sync();
    }
}
