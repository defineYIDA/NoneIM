package com.none.common.protocol.request;

import com.none.common.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.none.common.protocol.command.Command.GROUP_MESSAGE_REQUEST;


/**
 * @Author: zl
 * @Date: 2019/5/21 0:08
 */
@Data
@NoArgsConstructor //fastjson必须提供一个无参构造才能序列化
public class GroupMessageRequestPacket extends Packet {
    private String toGroupId;
    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
