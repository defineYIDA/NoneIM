package com.none.im.server.handler;

import com.none.im.protocol.request.JoinGroupRequestPacket;
import com.none.im.protocol.response.JoinGroupResponsePacket;
import com.none.im.protocol.response.MessageResponsePacket;
import com.none.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @Author: zl
 * @Date: 2019/5/20 0:06
 */
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) throws Exception {
        //1. 获取群对应的channelGroup，然后将当前用户的channel添加进去
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            throw new Exception("群组：" + groupId + "不存在！");
        }
        channelGroup.add(ctx.channel());

        //2. 构建加群响应发送给客户端
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();

        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        ctx.channel().writeAndFlush(responsePacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().writeAndFlush(MessageResponsePacket.exceptionMsg(cause.getMessage()));
    }
}
