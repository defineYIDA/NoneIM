package com.none.common.protocol.response;


import com.none.common.protocol.Packet;
import lombok.Data;

import static com.none.common.protocol.command.Command.JOIN_GROUP_RESPONSE;

/**
 * @Author: zl
 * @Date: 2019/5/20 0:25
 */
@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
