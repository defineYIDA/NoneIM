package com.none.proxy.handler;

import com.none.common.protocol.request.MessageRequestPacket;
import com.none.proxy.util.Router;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: zl
 * @Date: 2019/7/25 1:57
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        //TODO 暂时采用泛洪的方式
        Router.flooding(messageRequestPacket);
    }
}
