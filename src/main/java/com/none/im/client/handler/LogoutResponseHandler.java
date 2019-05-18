package com.none.im.client.handler;

import com.none.im.protocol.response.LogoutResponsePacket;
import com.none.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: zl
 * @Date: 2019/5/19 1:30
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) {
        SessionUtil.unBindSession(ctx.channel());
        System.out.println("已注销！");
    }
}
