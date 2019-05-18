package com.none.im.client.handler;

import com.none.im.protocol.Packet;
import com.none.im.protocol.PacketCodec;
import com.none.im.protocol.request.LoginRequestPacket;
import com.none.im.protocol.request.MessageRequestPacket;
import com.none.im.protocol.response.LoginResponsePacket;
import com.none.im.protocol.response.MessageResponsePacket;
import com.none.im.session.Session;
import com.none.im.util.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: zl
 * @Date: 2019/5/5 1:03
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    /*@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {


        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("pwd");

        // 写数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        String userName = loginResponsePacket.getUserName();
        String userId = loginResponsePacket.getUserId();
        if (loginResponsePacket.isSuccess()) {
            //注意这里为什么要给客户端的channel绑定，思考
            //将服务器和客户端看为一个对等方，那么服务端的channel绑定session是为了标识客户端，减少
            //鉴权等系列操作,客户channel这里绑定session的效果只用于客户机的标识，和服务器的channel是
            //独立的channel。
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
            System.out.println("[" + userName + "]登录成功，userId 为: " + userId);
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //会话结束，删除channel
        SessionUtil.unBindSession(ctx.channel());
    }
}
