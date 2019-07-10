package com.none.client.handler;

import com.none.common.protocol.response.ShowCmdResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * @Author: zl
 * @Date: 2019/5/20 23:05
 */
public class ShowCmdResponseHandler extends SimpleChannelInboundHandler<ShowCmdResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ShowCmdResponsePacket showCmdResponsePacket) throws Exception {
        List<String> cmd = showCmdResponsePacket.getCmd();
        System.out.println("---------------------");
        for (int i = 0; i < cmd.size(); i++) {
            System.out.println(cmd.get(i));
        }
        System.out.println("---------------------");
    }
}
