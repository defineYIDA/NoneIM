package com.none.common.protocol.response;


import com.none.common.protocol.Packet;
import lombok.Data;

import static com.none.common.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * @Author: zl
 * @Date: 2019/5/6 22:53
 */
@Data
public class MessageResponsePacket extends Packet {

    private String fromSessionID;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }

    public static MessageResponsePacket exceptionMsg(String msg) {
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setMessage(msg);
        responsePacket.setFromSessionID("Exception");
        responsePacket.setFromUserName("Server");
        return responsePacket;
    }
}
