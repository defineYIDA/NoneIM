package com.none.im.protocol.request;

import com.none.im.protocol.Packet;
import lombok.Data;

import static com.none.im.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

/**
 * @Author: zl
 * @Date: 2019/5/19 23:47
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
