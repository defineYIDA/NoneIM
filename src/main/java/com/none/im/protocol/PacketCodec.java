package com.none.im.protocol;

import com.none.im.protocol.request.LoginRequestPacket;
import com.none.im.protocol.request.MessageRequestPacket;
import com.none.im.protocol.response.LoginResponsePacket;
import com.none.im.protocol.response.MessageResponsePacket;
import com.none.im.serialize.Serializer;
import com.none.im.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.none.im.protocol.command.Command.*;

/**
 * @Author: zl
 * @Date: 2019/5/4 23:30
 * 自定义协议的编解码器
 */
@Slf4j
public class PacketCodec {
    public static final PacketCodec INSTANCE = new PacketCodec();

    private static final int MAGIC_NUMBER = 0x12345678;

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(),serializer);
    }

    /**
     * 编码
     * @param byteBuf
     * @param packet
     * @return
     */
    public ByteBuf encode(ByteBuf byteBuf,Packet packet) {
        // 1. 创建ByteBuf 对象
        //ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        //2. 序列化对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3. 实际的编码过程
        byteBuf.writeInt(MAGIC_NUMBER); //标识自定义协议
        byteBuf.writeByte(packet.getVersion()); //协议版本
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm()); //序列化算法
        byteBuf.writeByte(packet.getCommand()); //指令
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes); //数据

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过magic number
        byteBuf.skipBytes(4);

        //跳过版本号
        byteBuf.skipBytes(1);

        //序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        //指令
        byte command = byteBuf.readByte();

        //数据包长度
        int length =byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Class<? extends Packet> requestType =getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if(requestType != null && serializer != null) {
            //解码
            return serializer.deserialize(requestType,bytes);
        } else {
            System.out.println("指令或者序列化算法不存在！");
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }
}
