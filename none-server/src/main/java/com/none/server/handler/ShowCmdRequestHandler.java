package com.none.server.handler;

import com.none.common.protocol.request.ShowCmdRequestPacket;
import com.none.common.protocol.response.ShowCmdResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;

/**
 * @Author: zl
 * @Date: 2019/5/20 23:04
 */
@ChannelHandler.Sharable
public class ShowCmdRequestHandler extends SimpleChannelInboundHandler<ShowCmdRequestPacket> {
    public static final ShowCmdRequestHandler INSTANCE = new ShowCmdRequestHandler();

    private ShowCmdRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ShowCmdRequestPacket ShowCmdRequestPacket) throws Exception {
        ShowCmdResponsePacket responsePacket = new ShowCmdResponsePacket();
        //TODO 命令行的显示不需要经过Server
        //ConsoleCommandManager manager = new ConsoleCommandManager();
        ArrayList<String> list = new ArrayList<>();
        list.add("服务暂停");
        responsePacket.setCmd(list);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
