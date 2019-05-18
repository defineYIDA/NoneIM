package com.none.im.protocol.response;

import com.none.im.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.none.im.protocol.command.Command.CREATE_GROUP_RESPONSE;

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
        return CREATE_GROUP_RESPONSE;
    }
}
