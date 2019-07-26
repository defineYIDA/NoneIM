package com.none.proxy.handler;

import com.none.common.protocol.Packet;
import com.none.proxy.util.Router;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @Author: zl
 * @Date: 2019/7/21 0:30
 */
@Slf4j
public class BackendHandler extends SimpleChannelInboundHandler<Packet> {


    public BackendHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final Channel inboundChannel = ctx.channel();
        InetSocketAddress address = (InetSocketAddress)inboundChannel.remoteAddress();
        log.info("【 ip:{} port:{}】",address.getAddress(),address.getPort() + "服务端口连接成功！");
        ctx.read();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        ctx.writeAndFlush(packet);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel outboundChannel = Router.getClientChannel(ctx.channel());
        if (outboundChannel == null) {
            InetSocketAddress address = (InetSocketAddress)ctx.channel().remoteAddress();
            log.error("IMServer：【 ip:{} port:{}】的消息转发失败，Client连接断开" , address.getAddress(), address.getPort());
            return;
        }
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
