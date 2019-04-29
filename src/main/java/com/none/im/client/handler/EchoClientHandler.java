package com.none.im.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * @Author: zl
 * @Date: 2019/4/24 0:48
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 连接建立成功时调用，当server往client写数据时也会调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //ctx.writeAndFlush(Unpooled.copiedBuffer(str, CharsetUtil.UTF_8));
        //获得键盘输入
        ctx.writeAndFlush(getByteBuf(ctx));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /*@Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("Client received:"+byteBuf.toString(CharsetUtil.UTF_8));
    }*/

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf=(ByteBuf)msg;
        System.out.println("Client received:"+byteBuf.toString(CharsetUtil.UTF_8));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        Scanner sc = new Scanner(System.in);

        String str="";
        while (sc.hasNext()) {
            String newStr=sc.next();
            if("end".equals(newStr))
                break;
            str =str.concat(newStr);
        }

        // 1. 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        // 2. 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = str.getBytes(CharsetUtil.UTF_8);

        // 3. 填充数据到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
