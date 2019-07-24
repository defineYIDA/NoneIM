package com.none.proxy.conn;

import com.none.proxy.handler.BackendHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zl
 * @Date: 2019/7/23 0:55
 */
@Slf4j
public class ConnServer {
    /**
     * 服务接入
     *
     * @param ip
     * @param port
     * @return
     */
    public Channel connIMServer(String ip, int port) {
        Bootstrap b = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.AUTO_READ, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new BackendHandler(), new LoggingHandler(LogLevel.INFO));
                    }
                });
        ChannelFuture f = null;
        try {
            f = b.connect(ip, port).sync().addListener(future -> {
                if (future.isSuccess()) {
                    log.info("已连接到im服务：【" + ip + ":" + port + "】");
                } else {
                    //TODO 这里设置重新分配服务的策略
                    log.error("连接im服务：【" + ip + ":" + port + "】失败！");
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return f == null ? null : f.channel();
    }
}
