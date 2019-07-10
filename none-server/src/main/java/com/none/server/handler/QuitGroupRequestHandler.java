package com.none.server.handler;

import com.none.common.protocol.request.QuitGroupRequestPacket;
import com.none.common.protocol.response.MessageResponsePacket;
import com.none.common.protocol.response.QuitGroupResponsePacket;
import com.none.common.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @Author: zl
 * @Date: 2019/5/20 0:09
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    private QuitGroupRequestHandler() {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) throws Exception{
        //1. 获取群对应的 channelGroup，然后将当前用户的 channel 移除
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        //群不存在
        if (channelGroup == null) {
            throw new Exception("群号：" + groupId + "不存在！");
        }
        channelGroup.remove(ctx.channel());

        //2. 构造退群响应发送给客户端
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();

        responsePacket.setGroupId(groupId);
        responsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(responsePacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().writeAndFlush(MessageResponsePacket.exceptionMsg(cause.getMessage()));
    }
}
