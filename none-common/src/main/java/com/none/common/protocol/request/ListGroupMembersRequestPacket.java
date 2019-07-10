package com.none.common.protocol.request;


import com.none.common.protocol.Packet;
import com.none.common.protocol.command.Command;
import lombok.Data;

/**
 * @Author: zl
 * @Date: 2019/5/19 23:47
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
