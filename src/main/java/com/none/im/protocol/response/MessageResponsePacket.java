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

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }

    public static MessageResponsePacket exceptionMsg(String msg) {
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setMessage(msg);
        responsePacket.setFromUserId("Exception");
        responsePacket.setFromUserName("Server");
        return responsePacket;
    }
}
