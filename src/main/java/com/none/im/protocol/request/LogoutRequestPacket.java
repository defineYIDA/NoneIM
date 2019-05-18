package com.none.im.protocol.request;

import com.none.im.protocol.Packet;

import static com.none.im.protocol.command.Command.LOGOUT_REQUEST;

/**
 * @Author: zl
 * @Date: 2019/5/19 0:35
 */
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
