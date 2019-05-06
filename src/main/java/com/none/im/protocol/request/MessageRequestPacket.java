package com.none.im.protocol.request;

import com.none.im.protocol.Packet;
import lombok.Data;

import static com.none.im.protocol.command.Command.MESSAGE_REQUEST;

/**
 * @Author: zl
 * @Date: 2019/5/6 22:49
 */
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
