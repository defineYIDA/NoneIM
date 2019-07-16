package com.none.common.codec;

import com.none.common.protocol.Packet;
import com.none.common.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: zl
 * @Date: 2019/5/11 23:49
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        PacketCodec.INSTANCE.encode(byteBuf, packet);
    }
}
