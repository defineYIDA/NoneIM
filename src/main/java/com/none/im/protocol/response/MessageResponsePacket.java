package com.none.im.protocol.response;

import com.none.im.protocol.Packet;
import lombok.Data;

import static com.none.im.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * @Author: zl
 * @Date: 2019/5/6 22:53
 */
@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
