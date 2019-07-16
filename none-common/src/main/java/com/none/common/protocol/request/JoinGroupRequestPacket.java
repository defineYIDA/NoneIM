package com.none.common.protocol.request;


import com.none.common.protocol.Packet;
import lombok.Data;

import static com.none.common.protocol.command.Command.JOIN_GROUP_REQUEST;

/**
 * @Author: zl
 * @Date: 2019/5/19 23:44
 */
@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return JOIN_GROUP_REQUEST;

    }
}
