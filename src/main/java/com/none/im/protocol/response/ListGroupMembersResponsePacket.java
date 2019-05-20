package com.none.im.protocol.response;

import com.none.im.protocol.Packet;
import com.none.im.session.Session;
import lombok.Data;

import java.util.List;

import static com.none.im.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

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
