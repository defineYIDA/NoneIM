package com.none.common.protocol.request;


import com.none.common.protocol.Packet;

import static com.none.common.protocol.command.Command.HEARTBEAT_REQUEST;

/**
 * @Author: zl
 * @Date: 2019/5/21 23:11
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
