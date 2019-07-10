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

    private String toUserId;//消息接收方id

    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
