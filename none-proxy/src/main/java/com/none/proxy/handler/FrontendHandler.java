package com.none.proxy.handler;

import com.none.common.protocol.Packet;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @Author: zl
 * @Date: 2019/7/21 0:30
 */
@Slf4j
public class FrontendHandler extends SimpleChannelInboundHandler<Packet> {

    private volatile Channel outboundChannel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final Channel inboundChannel = ctx.channel();
        InetSocketAddress address = (InetSocketAddress) inboundChannel.remoteAddress();
        log.info("【IP:{} port:{}】", address.getAddress(), address.getPort() + "连接代理成功!");
        // Start the connection attempt.
        Bootstrap b = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        b.group(workerGroup)
                .channel(ctx.channel().getClass())
                .option(ChannelOption.AUTO_READ, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();

                        //p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(new BackendHandler(inboundChannel), new LoggingHandler(LogLevel.INFO));
                    }
                });
        ChannelFuture f = b.connect("127.0.0.1", 8080);
        outboundChannel = f.channel();

        f.addListener(future -> {
            if (future.isSuccess()) {
                // connection complete start to read first data
                inboundChannel.read();
            } else {
                // Close the connection if the connection attempt has failed.
                inboundChannel.close();
            }
        });
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        ctx.writeAndFlush(packet);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InetSocketAddress fromAddress = (InetSocketAddress)ctx.channel().remoteAddress();
        log.debug("数据来自:{}",fromAddress.getHostName());

        if (outboundChannel.isActive()) {
            outboundChannel.writeAndFlush(msg).addListener(future -> {
                if (future.isSuccess()) {
                    InetSocketAddress toAddress = (InetSocketAddress)outboundChannel.remoteAddress();
                    log.debug("数据发往:{}",toAddress.getHostName());
                    // was able to flush out data, start to read the next chunk
                    ctx.channel().read();
                } else {
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            });
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        InetSocketAddress address = (InetSocketAddress)ch.remoteAddress();
        log.info("【IP:{} port:{}】",address.getAddress(),address.getPort(), "与代理服务器端口断开连接");

        if (outboundChannel != null) {
            if (outboundChannel.isActive()) {
                outboundChannel.flush();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel ch = ctx.channel();
        InetSocketAddress address = (InetSocketAddress)ch.remoteAddress();
        log.info("【IP:{} port:{}】",address.getAddress(),address.getPort() + "与代理服务器端口端口连接异常");
        cause.printStackTrace();
        if (outboundChannel != null) {
            if (outboundChannel.isActive()) {
                outboundChannel.flush();
            }
        }
    }
}
