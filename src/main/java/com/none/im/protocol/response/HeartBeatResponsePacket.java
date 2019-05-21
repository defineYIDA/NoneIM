package com.none.im.protocol.response;

import com.none.im.protocol.Packet;

import static com.none.im.protocol.command.Command.HEARTBEAT_RESPONSE;

/**
 * @Author: zl
 * @Date: 2019/5/21 23:12
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
