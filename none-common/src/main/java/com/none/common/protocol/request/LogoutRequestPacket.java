package com.none.common.protocol.request;


import com.none.common.protocol.Packet;
import com.none.common.protocol.command.Command;

/**
 * @Author: zl
 * @Date: 2019/5/19 0:35
 */
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
