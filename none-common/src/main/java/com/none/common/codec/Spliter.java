package com.none.common.codec;

import com.none.common.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Author: zl
 * @Date: 2019/5/13 21:32
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        //7,协议中记录数据长度的偏移，4，长度域的长度，即代表在第7字节的后4四个字节代表数据长度
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            ctx.channel().close();
            System.out.println("["+ctx.channel().remoteAddress()+"]"+"非法报文，连接已关闭！");
            return null;
        }
        return super.decode(ctx, in);
    }
}
