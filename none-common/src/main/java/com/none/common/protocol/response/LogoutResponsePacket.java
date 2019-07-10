package com.none.common.protocol.response;


import com.none.common.protocol.Packet;
import lombok.Data;

import static com.none.common.protocol.command.Command.LOGOUT_RESPONSE;

/**
 * @Author: zl
 * @Date: 2019/5/19 0:37
 */
@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
