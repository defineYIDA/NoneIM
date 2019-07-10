package com.none.common.protocol.response;


import com.none.common.protocol.Packet;
import com.none.common.session.Session;
import lombok.Data;

import java.util.List;

import static com.none.common.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

/**
 * @Author: zl
 * @Date: 2019/5/20 0:28
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
