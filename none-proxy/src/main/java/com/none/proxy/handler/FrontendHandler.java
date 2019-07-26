package com.none.proxy.handler;

import com.none.common.protocol.Packet;
import com.none.common.util.ThreadPollUtil;
import com.none.proxy.conn.ConnServer;
import com.none.proxy.util.Router;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: zl
 * @Date: 2019/7/21 0:30
 */
@Slf4j
public class FrontendHandler extends SimpleChannelInboundHandler<Packet> {

    private volatile Channel outboundChannel;

    protected ThreadPoolExecutor pool;

    public FrontendHandler() {
        this.pool = ThreadPollUtil.createThreadPoll("Proxy-Worker");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        pool.submit(() -> {
            final Channel inboundChannel = ctx.channel();
            InetSocketAddress address = (InetSocketAddress) inboundChannel.remoteAddress();
            log.info("【IP:{} port:{}】", address.getAddress(), address.getPort() + "连接代理成功!");
            //获得需要转发的Channel
            if (Router.getServerChannel(inboundChannel) == null) {
                ConnServer conn = new ConnServer();
                //outboundChannel = conn.connIMServer("169.254.1.212", 8080, address.toString());
                outboundChannel = conn.connIMServer(address.toString());
                if (outboundChannel == null) {
                    log.error("连接服务异常！");
                    //TODO feedback exception msg
                    return;
                }
                Router.bindForwordInfo(inboundChannel, outboundChannel);
            }
            if (outboundChannel.isActive()) {
                //todo mean?
                inboundChannel.read();
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
