package com.none.common.protocol.response;


import com.none.common.protocol.Packet;

import static com.none.common.protocol.command.Command.HEARTBEAT_RESPONSE;

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
