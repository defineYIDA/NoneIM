package com.none.server.handler;

import com.none.common.protocol.request.LoginRequestPacket;
import com.none.common.protocol.response.LoginResponsePacket;
import com.none.common.session.Session;
import com.none.common.util.IDUtil;
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
 * @Date: 2019/5/5 1:00
 */
@ChannelHandler.Sharable
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private UserInfoServer userInfoServer = SpringBeanFactory.getBean(UserInfoServerImpl.class);

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录……");
        // 登录流程
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());
        String userName = loginRequestPacket.getUsername();

        if (valid(loginRequestPacket)) {

            //登陆成功后,注册session
            String sessionID = IDUtil.randomId();
            loginResponsePacket.setSessionID(sessionID);
            //注册到redis
            boolean status = userInfoServer.saveUserLoginStatus(userName, sessionID);
            if (status) {
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ": 登录成功!");

                //在登陆成功时给连接分配一个userId，类似于sessionId，在真实环境中标识接收方还是
                //应该是用户id，通过接收方的id在去连接map里检查接收方是否活跃，活跃的话应该直接写到接收方的channel
                //不活跃的话应该是使用某种缓存机制(mq,redis)，等接受方接入的时候再转发给他
                SessionUtil.bindSession(new Session(sessionID, userName), ctx.channel());

                //注册客户机连接的服务器信息到redis
                userInfoServer.saveRouterInfo(userName, sessionID);
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号：" + userName + "已经登陆！");
            }
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }


}
