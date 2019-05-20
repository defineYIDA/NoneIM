package com.none.im.server.handler;

import com.none.im.client.console.ConsoleCommandManager;
import com.none.im.protocol.request.ShowCmdRequestPacket;
import com.none.im.protocol.response.ShowCmdResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: zl
 * @Date: 2019/5/20 23:04
 */
public class ShowCmdRequestHandler extends SimpleChannelInboundHandler<ShowCmdRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ShowCmdRequestPacket ShowCmdRequestPacket) throws Exception {
        ShowCmdResponsePacket responsePacket = new ShowCmdResponsePacket();
        ConsoleCommandManager manager = new ConsoleCommandManager();
        responsePacket.setCmd(manager.getCmd());
        ctx.channel().writeAndFlush(responsePacket);
    }
}
