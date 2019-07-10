package com.none.common.protocol.response;


import com.none.common.protocol.Packet;
import com.none.common.session.Session;
import lombok.Data;

import static com.none.common.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

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
