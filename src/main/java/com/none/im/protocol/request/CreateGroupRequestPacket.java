package com.none.im.protocol.request;

import com.none.im.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.none.im.protocol.command.Command.CREATE_GROUP_REQUEST;

/**
 * @Author: zl
 * @Date: 2019/5/19 0:25
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
