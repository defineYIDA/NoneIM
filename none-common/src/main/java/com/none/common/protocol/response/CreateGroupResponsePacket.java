package com.none.common.protocol.response;


import com.none.common.protocol.Packet;
import com.none.common.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @Author: zl
 * @Date: 2019/5/19 0:39
 */
@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
