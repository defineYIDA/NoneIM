package com.none.proxy.handler;

import com.none.common.codec.PacketCodecHandler;
import com.none.common.codec.PacketDecoder;
import com.none.common.codec.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: zl
 * @Date: 2019/7/21 0:21
 */
public class ProxyHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
        ch.pipeline().addLast(new MessageRequestHandler());
        ch.pipeline().addLast(new FrontendHandler());
    }
}
