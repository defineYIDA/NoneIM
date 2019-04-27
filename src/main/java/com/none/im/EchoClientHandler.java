package com.none.im;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * @Author: zl
 * @Date: 2019/4/24 0:48
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Scanner sc = new Scanner(System.in);
        String str="";
        while (sc.hasNext()) {
            String newStr=sc.next();
            if("end".equals(newStr))
                break;
            str =str.concat(newStr);
        }
        ctx.writeAndFlush(Unpooled.copiedBuffer(str, CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("Client received:"+byteBuf.toString(CharsetUtil.UTF_8));
    }
}
