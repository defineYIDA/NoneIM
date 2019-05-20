package com.none.im.protocol.request;

import com.none.im.protocol.Packet;
import lombok.Data;

import static com.none.im.protocol.command.Command.QUIT_GROUP_REQUEST;

/**
 * @Author: zl
 * @Date: 2019/5/19 23:48
 */
@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
