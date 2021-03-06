package com.none.common.protocol.request;


import com.none.common.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.none.common.protocol.command.Command.MESSAGE_REQUEST;

/**
 * @Author: zl
 * @Date: 2019/5/6 22:49
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserName;//消息接收方

    private String message;

    public MessageRequestPacket(String toUserName, String message) {
        this.toUserName = toUserName;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
