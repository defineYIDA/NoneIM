package com.none.server.handler;

import com.none.common.protocol.request.LoginRequestPacket;
import com.none.common.protocol.response.LoginResponsePacket;
import com.none.common.session.Session;
import com.none.common.util.IDUtil;
import com.none.common.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @Author: zl
 * @Date: 2019/5/5 1:00
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

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

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            //登陆成功后,注册session
            String userId = IDUtil.randomId();
            loginResponsePacket.setUserId(userId);
            System.out.println(new Date() + ": 登录成功!");
            //在登陆成功时给连接分配一个userId，类似于sessionId，在真实环境中标识接收方还是
            //应该是用户id，通过接收方的id在去连接map里检查接收方是否活跃，活跃的话应该直接写到接收方的channel
            //不活跃的话应该是使用某种缓存机制(mq,redis)，等接受方接入的时候再转发给他
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), ctx.channel());
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
