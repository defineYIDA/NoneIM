package com.none.proxy.handler;

import com.none.common.protocol.Packet;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @Author: zl
 * @Date: 2019/7/21 0:30
 */
@Slf4j
public class BackendHandler extends SimpleChannelInboundHandler<Packet> {

    private volatile Channel outboundChannel;

    public BackendHandler(Channel outboundChannel) {
        this.outboundChannel = outboundChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final Channel inboundChannel = ctx.channel();
        InetSocketAddress address = (InetSocketAddress)inboundChannel.remoteAddress();
        log.info("【 ip:{} port:{}】",address.getHostName(),address.getPort() + "服务端口连接成功！");
        ctx.read();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        ctx.writeAndFlush(packet);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        outboundChannel.writeAndFlush(msg).addListener(future -> {
            if (future.isSuccess()) {
                InetSocketAddress toAddress = (InetSocketAddress)outboundChannel.remoteAddress();
                ctx.channel().read();
            } else {
                Throwable cause = future.cause();
                cause.printStackTrace();
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("代理和目标地址端口断开连接");
        super.channelInactive(ctx);
    }
}
