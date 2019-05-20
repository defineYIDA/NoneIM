package com.none.im.protocol.response;

import com.none.im.protocol.Packet;
import com.none.im.session.Session;
import lombok.Data;

import static com.none.im.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

/**
 * @Author: zl
 * @Date: 2019/5/21 0:16
 */
@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
