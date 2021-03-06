package com.none.server.handler;

import com.none.common.protocol.request.ListGroupMembersRequestPacket;
import com.none.common.protocol.response.ListGroupMembersResponsePacket;
import com.none.common.protocol.response.MessageResponsePacket;
import com.none.common.session.Session;
import com.none.common.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zl
 * @Date: 2019/5/20 0:08
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket requestPacket) throws Exception{
        //1. 获取群的ChannelGroup
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            throw new Exception("群号：" + groupId + "不存在！");
        }
        //2. 遍历群成员的channel
        List<Session> sessionList = new ArrayList<>();
        //这里的遍历的原理
        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        //3. 构建响应
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();

        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);
        ctx.channel().writeAndFlush(responsePacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().writeAndFlush(MessageResponsePacket.exceptionMsg(cause.getMessage()));
    }
}
