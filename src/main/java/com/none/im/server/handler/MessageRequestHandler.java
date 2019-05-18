package com.none.im.server.handler;

import com.none.im.protocol.request.MessageRequestPacket;
import com.none.im.protocol.response.MessageResponsePacket;
import com.none.im.session.Session;
import com.none.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @Author: zl
 * @Date: 2019/5/12 0:09
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        //获得session
        Session session = SessionUtil.getSession(ctx.channel());

        // 2.通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        /*MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");*/

        //拿到接收方的channel
        Channel toChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 4.将消息发送给消息接收方
        if (toChannel != null && SessionUtil.hasLogin(toChannel)) {
            toChannel.writeAndFlush(messageResponsePacket);
        } else {
            //回写到发送方
            messageResponsePacket.setMessage("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
            messageResponsePacket.setFromUserId("Server");
            messageResponsePacket.setFromUserName("Server");
            ctx.channel().writeAndFlush(messageResponsePacket);
        }
    }
}
