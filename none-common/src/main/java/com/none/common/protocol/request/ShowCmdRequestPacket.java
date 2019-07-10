package com.none.common.protocol.request;


import com.none.common.protocol.Packet;

import static com.none.common.protocol.command.Command.SHOW_CMD_REQUEST;

/**
 * @Author: zl
 * @Date: 2019/5/20 22:51
 */
public class ShowCmdRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return SHOW_CMD_REQUEST;
    }
}
