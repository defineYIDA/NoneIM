package com.none.server.handler;

import com.none.common.protocol.request.LogoutRequestPacket;
import com.none.common.protocol.response.LogoutResponsePacket;
import com.none.common.util.SessionUtil;
import com.none.server.Server.UserInfoServer;
import com.none.server.Server.impl.UserInfoServerImpl;
import com.none.server.util.SpringBeanFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Author: zl
 * @Date: 2019/5/19 1:31
 */
@ChannelHandler.Sharable
@Slf4j
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private UserInfoServer userInfoServer = SpringBeanFactory.getBean(UserInfoServerImpl.class);

    private LogoutRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) {
        System.out.println("用户：["+ SessionUtil.getSession(ctx.channel()).getUserName() + "] 已注销！");
        //清除redis的登陆信息
        String userName = SessionUtil.getSession(ctx.channel()).getUserName();
        String sessionID = SessionUtil.getSession(ctx.channel()).getSessionID();
        userInfoServer.removeLoginStatus(userName, sessionID);
        log.info(new Date() + ":[K: " + userName + ", V: " + sessionID + "] 移除成功!");
        //清除对于channel
        SessionUtil.unBindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
